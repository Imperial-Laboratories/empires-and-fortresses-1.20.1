package empireandfortresses.command.commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import empireandfortresses.command.AbstractCommand;
import empireandfortresses.util.BorderVisibilityManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class BorderCommand extends AbstractCommand {

    @Override
    public String getPath() {
        return "nation debug border";
    }

    @Override
    public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = getPlayer(context);
        if (player != null) {
            BorderVisibilityManager.toggle(player.getUuid());
            boolean nowVisible = BorderVisibilityManager.isVisible(player.getUuid());
            player.sendMessage(Text.literal("Border visualization: " + (nowVisible ? "ON" : "OFF")), false);
        }
        return 1;
    }

}
