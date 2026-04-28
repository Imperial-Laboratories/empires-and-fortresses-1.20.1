package empireandfortresses.magic.spell;

import empireandfortresses.entity.attribute.ModEntityAttributes;
import empireandfortresses.entity.spell.MagicEvokerFangsEntity;
import empireandfortresses.magic.Spell;
import empireandfortresses.magic.SpellCategory;
import empireandfortresses.magic.SpellTriggerCategory;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;

public class FangsVerticalSpell extends Spell {

    public FangsVerticalSpell() {
        super("fangs_vertical", SpellCategory.DEFENSE, SpellTriggerCategory.ATTACK, 50, false, 100, false, 0);
    }

    @Override
    public void cast(PlayerEntity user, ItemStack stack) {
        double magicAffinity = user.getAttributeValue(ModEntityAttributes.MAGIC_AFFINITY);
        double damage = user.getAttributeValue(ModEntityAttributes.MAGIC_ATTACK_DAMAGE) * magicAffinity / 5;

        int fangCount = (int) (5 + magicAffinity * 3);

        float yawRad = (float) Math.toRadians(user.getYaw());
        double dirX = -MathHelper.sin(yawRad);
        double dirZ = MathHelper.cos(yawRad);

        for (int i = 0; i < fangCount; i++) {
            double distance = 1.25f * (i + 1);
            double x = user.getX() + dirX * distance;
            double z = user.getZ() + dirZ * distance;

            conjureFangs(user, x, z, user.getY(), user.getY(), user.getYaw(), i * 2, (float) damage);
        }

        if (!user.isCreative()) {
            activateCooldown(user);
            consumeXP(user);
            super.cast(user, stack);
        }
    }

    // Copied from EvokerEntity
    // basically it just looks downward maxY blocks for a solid block to spawn the
    // fangs and with delay "warmup" (for animation)
    public static void conjureFangs(PlayerEntity user, double x, double z, double maxY, double y, float yaw, int warmup, float damage) {
        BlockPos currentPos = BlockPos.ofFloored(x, y, z);
        boolean foundSolidGround = false;
        double blockTopOffset = 0.0;

        do {
            BlockPos belowPos = currentPos.down();
            BlockState belowState = user.getWorld().getBlockState(belowPos);
            if (belowState.isSideSolidFullSquare(user.getWorld(), belowPos, Direction.UP)) {
                if (!user.getWorld().isAir(currentPos)) {
                    BlockState currentState = user.getWorld().getBlockState(currentPos);
                    VoxelShape collisionShape = currentState.getCollisionShape(user.getWorld(), currentPos);
                    if (!collisionShape.isEmpty()) {
                        blockTopOffset = collisionShape.getMax(Direction.Axis.Y);
                    }
                }

                foundSolidGround = true;
                break;
            }

            currentPos = currentPos.down();
        } while (currentPos.getY() >= MathHelper.floor(maxY) - 1);

        if (foundSolidGround) {
            user.getWorld().spawnEntity(new MagicEvokerFangsEntity(user.getWorld(), x, currentPos.getY() + blockTopOffset, z, yaw, warmup, user, damage));
        }
    }
}
