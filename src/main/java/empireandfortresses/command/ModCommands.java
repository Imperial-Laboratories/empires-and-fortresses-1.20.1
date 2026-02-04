package empireandfortresses.command;

import empireandfortresses.nations.TerritoryState;
import empireandfortresses.util.BorderVisibilityManager;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class ModCommands {

    public static void registerCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(
                    CommandManager.literal("nation").then(
                            CommandManager.literal("debug").then(CommandManager.literal("border").executes(context -> {
                                ServerPlayerEntity player = context.getSource().getPlayer();
                                if (player != null) {
                                    BorderVisibilityManager.toggle(player.getUuid());
                                    boolean nowVisible = BorderVisibilityManager.isVisible(player.getUuid());
                                    player.sendMessage(
                                            Text.literal("Border visualization: " + (nowVisible ? "ON" : "OFF")),
                                            false);
                                }
                                return 1;
                            })).then(CommandManager.literal("clear").requires(source -> source.hasPermissionLevel(4))
                                    .executes(context -> {
                                        TerritoryState state = TerritoryState
                                                .getServerState(context.getSource().getServer());
                                        state.claimedChunks.clear();
                                        state.nations.clear();
                                        state.markDirty();
                                        context.getSource().sendFeedback(
                                                () -> Text.literal("Cleared all nations and claimed chunks."), true);
                                        return 1;
                                    }))));
        });
    }

}
