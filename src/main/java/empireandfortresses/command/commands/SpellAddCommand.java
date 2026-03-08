package empireandfortresses.command.commands;

import java.util.concurrent.CompletableFuture;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import empireandfortresses.command.AbstractCommand;
import empireandfortresses.item.custom.SpellCastingItem;
import empireandfortresses.magic.Spell;
import empireandfortresses.magic.Spells;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static net.minecraft.server.command.CommandManager.argument;

public class SpellAddCommand extends AbstractCommand {

    @Override
    public String getPath() {
        return "spell add";
    }

    @Override
    protected void configure(LiteralArgumentBuilder<ServerCommandSource> builder) {
        builder.then(argument("spell", IdentifierArgumentType.identifier()).suggests(this::suggestSpells).executes(this));
    }

    private CompletableFuture<Suggestions> suggestSpells(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) throws CommandSyntaxException {
        return suggestIdentifiers(Spells.getAllSpellIds()).getSuggestions(context, builder);
    }

    @Override
    public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = getPlayer(context);
        String spellId = getArgument(context, "spell", Identifier.class).toString();

        if (player != null) {
            Spell spell = Spells.getSpellById(spellId);
            if (spell == null) {
                context.getSource().sendError(Text.literal("Spell not found: " + spellId));
                return 0;
            }

            // add spell to players wand inventory
            ItemStack stack = player.getMainHandStack();
            if (stack.getItem() instanceof SpellCastingItem) {
                NbtCompound nbt = stack.getNbt();
                NbtList spells = nbt.getList("Spells", NbtElement.COMPOUND_TYPE);
                int spellSlots = nbt.getInt("SpellSlots");

                if (spells.size() >= spellSlots) {
                    context.getSource().sendError(Text.literal("Your spellcasting item is full!"));
                    return 0;
                }

                if (spells.stream().anyMatch(s -> ((NbtCompound) s).getString("Id").equals(spellId))) {
                    context.getSource().sendError(Text.literal("You already have this spell!"));
                    return 0;
                }

                spells.add(spell.toNbt());
                nbt.put("Spells", spells);

                context.getSource().sendFeedback(() -> Text.literal("Added spell: " + spell.getSpellID()), false);
            } else {
                context.getSource().sendError(Text.literal("You must hold a spellcasting item to add a spell."));
                return 0;
            }
        }
        return 1;
    }

}
