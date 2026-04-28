package empireandfortresses.screen;

import empireandfortresses.EmpiresAndFortresses;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {

    public static final ScreenHandlerType<CustomEnchantmentScreenHandler> ENCHANTING_SCREEN_HANDLER = register(new Identifier(EmpiresAndFortresses.MOD_ID, "enchanting").toString(),
            CustomEnchantmentScreenHandler::new);

    private static <T extends ScreenHandler> ScreenHandlerType<T> register(String id, ScreenHandlerType.Factory<T> factory) {
        return Registry.register(Registries.SCREEN_HANDLER, id, new ScreenHandlerType<>(factory, FeatureFlags.VANILLA_FEATURES));
    }

    public static void registerScreenHandlers() {
        // empty
    }
}
