package empireandfortresses.command;

import empireandfortresses.item.custom.SpellCastingItem;
import empireandfortresses.magic.Spell;
import empireandfortresses.magic.Spells;
import empireandfortresses.nations.TerritoryState;
import empireandfortresses.util.BorderVisibilityManager;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandSource;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class ModCommands {

    public static void registerCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("nation").then(literal("debug").then(literal("border").executes(context -> {
                ServerPlayerEntity player = context.getSource().getPlayer();
                if (player != null) {
                    BorderVisibilityManager.toggle(player.getUuid());
                    boolean nowVisible = BorderVisibilityManager.isVisible(player.getUuid());
                    player.sendMessage(Text.literal("Border visualization: " + (nowVisible ? "ON" : "OFF")), false);
                }
                return 1;
            })).then(literal("clear").requires(source -> source.hasPermissionLevel(4)).executes(context -> {
                TerritoryState state = TerritoryState.getServerState(context.getSource().getServer());
                state.claimedChunks.clear();
                state.nations.clear();
                state.markDirty();
                context.getSource().sendFeedback(() -> Text.literal("Cleared all nations and claimed chunks."), true);
                return 1;
            }))));
            dispatcher.register(literal("spell").then(literal("list").requires(source -> source.hasPermissionLevel(4)).executes(context -> {
                context.getSource().sendFeedback(() -> Text.literal("All available spells: " + String.join(", ", Spells.getAllSpellIdsAsString())), false);
                return 1;
            })).then(literal("add").requires(source -> source.hasPermissionLevel(4)).then(
                    argument("spell", IdentifierArgumentType.identifier()).suggests((context, builder) -> CommandSource.suggestIdentifiers(Spells.getAllSpellIds(), builder)).executes(content -> {
                        ServerPlayerEntity player = content.getSource().getPlayer();
                        String spellId = IdentifierArgumentType.getIdentifier(content, "spell").toString();

                        if (player != null) {

                            Spell spell = Spells.getSpellById(spellId);
                            if (spell == null) {
                                content.getSource().sendError(Text.literal("Spell not found: " + spellId));
                                return 0;
                            }

                            // add spell to players wand inventory
                            ItemStack stack = player.getMainHandStack();
                            if (stack.getItem() instanceof SpellCastingItem) {
                                NbtCompound nbt = stack.getNbt();
                                NbtList spells = nbt.getList("Spells", NbtElement.COMPOUND_TYPE);
                                int spellSlots = nbt.getInt("SpellSlots");

                                if (spells.size() >= spellSlots) {
                                    content.getSource().sendError(Text.literal("Your spellcasting item is full!"));
                                    return 0;
                                }

                                if (spells.stream().anyMatch(s -> ((NbtCompound) s).getString("Id").equals(spellId))) {
                                    content.getSource().sendError(Text.literal("You already have this spell!"));
                                    return 0;
                                }

                                spells.add(spell.toNbt());
                                nbt.put("Spells", spells);

                                content.getSource().sendFeedback(() -> Text.literal("Added spell: " + spell.getSpellID()), false);
                            } else {
                                content.getSource().sendError(Text.literal("You must hold a spellcasting item to add a spell."));
                                return 0;
                            }
                        }
                        return 1;
                    }))));
        });
    }

}
