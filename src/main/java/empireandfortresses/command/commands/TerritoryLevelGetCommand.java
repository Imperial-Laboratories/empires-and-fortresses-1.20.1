package empireandfortresses.command.commands;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import com.google.common.base.Predicate;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import empireandfortresses.command.AbstractCommand;
import empireandfortresses.command.EnumArgumentType;
import empireandfortresses.nation.Nation;
import empireandfortresses.nation.TerritoryLevelType;
import empireandfortresses.nation.TerritoryState;
import net.minecraft.command.argument.UuidArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.argument;

public class TerritoryLevelGetCommand extends AbstractCommand {

    @Override
    public String getPath() {
        return "nation debug level get territory";
    }

    @Override
    public Predicate<ServerCommandSource> getRequirement() {
        return source -> source.hasPermissionLevel(4);
    }

    @Override
    protected void configure(LiteralArgumentBuilder<ServerCommandSource> builder) {
        builder.then(argument("nation", UuidArgumentType.uuid()).suggests(this::suggestNations)
                .then(argument("level_type", EnumArgumentType.enumArg(TerritoryLevelType.class)).executes(this)));
    }

    protected CompletableFuture<Suggestions> suggestNations(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) throws CommandSyntaxException {
        TerritoryState serverState = TerritoryState.getServerState(context.getSource().getServer());
        for (Nation nation : serverState.nations.values()) {
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
        TerritoryState serverState = TerritoryState.getServerState(context.getSource().getServer());
        Nation nation = serverState.nations.get(nationUuid);

        if (nation == null) {
            context.getSource().sendError(Text.literal("Nation not found"));
            return 0;
        }

        TerritoryLevelType levelType = context.getArgument("level_type", TerritoryLevelType.class);
        int level = nation.getMainTerritoryLevels().get(levelType);

        context.getSource().sendFeedback(() -> Text.literal("The Level of " + levelType.name() + " is " + String.valueOf(level)), false);

        return level;
    }

}
