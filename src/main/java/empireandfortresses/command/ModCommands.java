package empireandfortresses.command;

import empireandfortresses.EmpiresAndFortresses;
import empireandfortresses.command.commands.*;
import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Identifier;

import com.mojang.brigadier.CommandDispatcher;

@SuppressWarnings({"unchecked", "rawtypes"})
public class ModCommands {

    public static void registerCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            register(dispatcher, new BorderCommand());
            register(dispatcher, new ClearCommand());
            register(dispatcher, new TerritoryLevelCommand());

            register(dispatcher, new SpellListCommand());
            register(dispatcher, new SpellAddCommand());
        });
    }

    public static void registerArgumentTypes() {
        ArgumentTypeRegistry.registerArgumentType(new Identifier(EmpiresAndFortresses.MOD_ID, "enum"), (Class) EnumArgumentType.class, new EnumArgumentSeralizer());
    }

    private static void register(CommandDispatcher<ServerCommandSource> dispatcher, AbstractCommand command) {
        dispatcher.register(command.build());
    }

}
