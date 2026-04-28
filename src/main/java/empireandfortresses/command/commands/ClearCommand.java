package empireandfortresses.command.commands;

import java.util.function.Predicate;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import empireandfortresses.command.AbstractCommand;
import empireandfortresses.nations.TerritoryState;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class ClearCommand extends AbstractCommand {

    @Override
    public String getPath() {
        return "nation debug clear";
    }

    @Override
    public Predicate<ServerCommandSource> getRequirement() {
        return source -> source.hasPermissionLevel(4);
    }

    @Override
    public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        TerritoryState state = TerritoryState.getServerState(context.getSource().getServer());

        state.claimedChunks.clear();
        state.nations.clear();
        state.markDirty();

        context.getSource().sendFeedback(() -> Text.literal("Cleared all nations and claimed chunks."), true);
        return 1;
    }

}
