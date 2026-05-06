package empireandfortresses.command.commands;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import empireandfortresses.command.AbstractCommand;
import empireandfortresses.nation.Nation;
import empireandfortresses.nation.NationManager;
import empireandfortresses.nation.TerritoryLevelType;
import net.minecraft.command.argument.UuidArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.argument;

public class TerritoryLevelCommand extends AbstractCommand {

    @Override
    public String getPath() {
        return "nation debug level set territory";
    }

    @Override
    public Predicate<ServerCommandSource> getRequirement() {
        return source -> source.hasPermissionLevel(4);
    }

    @Override
    protected void configure(LiteralArgumentBuilder<ServerCommandSource> builder) {
        builder.then(argument("nation", UuidArgumentType.uuid()).suggests(this::suggestNations)
            .then(argument("level_type", StringArgumentType.string()).suggests(this::suggestLevelTypes)
                .then(argument("level", IntegerArgumentType.integer(0)).executes(this))));
    }

    protected CompletableFuture<Suggestions> suggestNations(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) throws CommandSyntaxException {
        for (Nation nation : NationManager.nations.values()) {
            builder.suggest(nation.getId().toString());
        }

        return builder.buildFuture();
    }

    protected CompletableFuture<Suggestions> suggestLevelTypes(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) throws CommandSyntaxException {
        for (TerritoryLevelType levelType : TerritoryLevelType.values()) {
            builder.suggest(levelType.name());
        }

        return builder.buildFuture();
    }

    @Override
    public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        UUID nationUuid = context.getArgument("nation", UUID.class);
        Nation nation = NationManager.nations.getOrDefault(nationUuid, null);

        if (nation == null) {
            context.getSource().sendError(Text.literal("Nation not found"));
            return 0;
        }

        TerritoryLevelType levelType = TerritoryLevelType.valueOf(context.getArgument("level_type", String.class));
        int level = context.getArgument("level", Integer.class);

        Map<TerritoryLevelType, Integer> mainTerritoryLevels = nation.getMainTerritoryLevels();
        mainTerritoryLevels.put(levelType, level);
        nation.setMainTerritoryLevels(mainTerritoryLevels);

        context.getSource().sendFeedback(() -> Text.literal("Modified Territory Level"), false);
        return 1;
    }
}
