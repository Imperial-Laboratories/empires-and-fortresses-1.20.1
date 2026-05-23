package empireandfortresses.screen;

import empireandfortresses.EmpiresAndFortresses;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {

    public static final ScreenHandlerType<CustomEnchantmentScreenHandler> ENCHANTING_SCREEN_HANDLER = register(new Identifier(EmpiresAndFortresses.MOD_ID, "enchanting").toString(),
            CustomEnchantmentScreenHandler::new);

    public static final ExtendedScreenHandlerType<TerritoryOverviewScreenHandler> TERRITORY_SCREEN_HANDLER = new ExtendedScreenHandlerType<>(TerritoryOverviewScreenHandler::new);

    private static <T extends ScreenHandler> ScreenHandlerType<T> register(String id, ScreenHandlerType.Factory<T> factory) {
        return Registry.register(Registries.SCREEN_HANDLER, id, new ScreenHandlerType<>(factory, FeatureFlags.VANILLA_FEATURES));
    }

    public static void registerScreenHandlers() {
        HandledScreens.register(ModScreenHandlers.ENCHANTING_SCREEN_HANDLER, CustomEnchantmentScreen::new);
        Registry.register(Registries.SCREEN_HANDLER, new Identifier(EmpiresAndFortresses.MOD_ID, "territory_overview"), TERRITORY_SCREEN_HANDLER);
        HandledScreens.register(ModScreenHandlers.TERRITORY_SCREEN_HANDLER, TerritoryOverviewScreen::new);
    }
}
