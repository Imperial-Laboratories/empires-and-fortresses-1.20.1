package empireandfortresses.command;

import empireandfortresses.command.commands.*;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;
import com.mojang.brigadier.CommandDispatcher;

public class ModCommands {

    public static void registerCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            register(dispatcher, new BorderCommand());
            register(dispatcher, new ClearCommand());

            register(dispatcher, new SpellListCommand());
            register(dispatcher, new SpellAddCommand());
        });
    }

    private static void register(CommandDispatcher<ServerCommandSource> dispatcher, AbstractCommand command) {
        dispatcher.register(command.build());
    }

}
