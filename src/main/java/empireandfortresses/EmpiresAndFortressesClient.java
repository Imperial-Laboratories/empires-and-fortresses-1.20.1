package empireandfortresses;

import empireandfortresses.client.MagicHudOverlay;
import empireandfortresses.entity.ModEntities;
import empireandfortresses.entity.client.MagicBulletEntityModel;
import empireandfortresses.entity.client.MagicBulletEntityRenderer;
import empireandfortresses.entity.client.ModModelLayers;
import empireandfortresses.event.KeyInputHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

@SuppressWarnings("java:S1186")
public class EmpiresAndFortressesClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.MAGIC_BULLET, MagicBulletEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.MAGIC_BULLET, MagicBulletEntityModel::getTexturedModelData);

        KeyInputHandler.registerKeyBindings();

        HudRenderCallback.EVENT.register(new MagicHudOverlay());
    }

}
