package empireandfortresses;

import empireandfortresses.client.MagicHudOverlay;
import empireandfortresses.entity.ModEntities;
import empireandfortresses.entity.client.MagicBulletEntityModel;
import empireandfortresses.entity.client.MagicBulletEntityRenderer;
import empireandfortresses.entity.client.ModModelLayers;
import empireandfortresses.event.KeyInputHandler;
import empireandfortresses.networking.ModMessages;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.network.ClientPlayerEntity;

@SuppressWarnings("java:S1186")
public class EmpiresAndFortressesClient implements ClientModInitializer {

    private boolean jumpKeyWasPressed = false;

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.MAGIC_BULLET, MagicBulletEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.MAGIC_BULLET, MagicBulletEntityModel::getTexturedModelData);

        KeyInputHandler.registerKeyBindings();

        HudRenderCallback.EVENT.register(new MagicHudOverlay());

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            ClientPlayerEntity player = client.player;
            if (player == null) {
                return;
            }

            boolean jumpKeyPressed = client.options.jumpKey.isPressed();

            if (jumpKeyPressed && !jumpKeyWasPressed) {
                ClientPlayNetworking.send(ModMessages.PRESS_JUMP_KEY_ID, PacketByteBufs.empty());
            }

            jumpKeyWasPressed = jumpKeyPressed;
        });
    }

}
