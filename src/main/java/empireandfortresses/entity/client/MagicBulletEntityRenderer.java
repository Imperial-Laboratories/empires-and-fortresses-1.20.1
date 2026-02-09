package empireandfortresses.entity.client;

import empireandfortresses.EmpiresAndFortresses;
import empireandfortresses.entity.spell.MagicBulletEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

public class MagicBulletEntityRenderer extends EntityRenderer<MagicBulletEntity> {
    private final MagicBulletEntityModel model;

    public MagicBulletEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.model = new MagicBulletEntityModel(context.getPart(ModModelLayers.MAGIC_BULLET));
    }

    private static final Identifier TEXTURE = new Identifier(EmpiresAndFortresses.MOD_ID, "textures/entity/magic_bullet.png");

    @Override
    public Identifier getTexture(MagicBulletEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(MagicBulletEntity entity, float yaw, float tickDelta, MatrixStack matrices,
            VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(MathHelper.lerp(tickDelta, entity.prevYaw, entity.getYaw()) - 90.0F));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(MathHelper.lerp(tickDelta, entity.prevPitch, entity.getPitch()) + 90.0F));
        VertexConsumer vertexConsumer = ItemRenderer.getDirectItemGlintConsumer(vertexConsumers, RenderLayer.getEntityTranslucent(TEXTURE), false, false);
        this.model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1, 1, 1, 1.0F);
        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }
}
