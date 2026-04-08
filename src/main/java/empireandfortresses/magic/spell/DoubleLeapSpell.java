package empireandfortresses.magic.spell;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import empireandfortresses.component.ModComponents;
import empireandfortresses.entity.attribute.ModEntityAttributes;
import empireandfortresses.magic.Spell;
import empireandfortresses.magic.SpellCategory;
import empireandfortresses.magic.SpellTriggerCategory;

public class DoubleLeapSpell extends Spell {

    public DoubleLeapSpell() {
        super("double_leap", SpellCategory.MOBILITY, SpellTriggerCategory.JUMP, 2, true, 60, false, 0);
    }

    @Override
    public void cast(PlayerEntity user, ItemStack stack) {

        Vec3d look = user.getRotationVec(1f);
        user.setVelocity(look.x * 0.5, 0.42F * user.getJumpVelocityMultiplier() + user.getJumpBoostVelocityModifier(), look.z * 0.5);
        user.velocityModified = true;
        user.fallDistance = 0.0f;

        if (!user.isCreative()) {
            activateCooldown(user);
            consumeXP(user);
            super.cast(user, stack);
        }
    }

    @Override
    public boolean condition(PlayerEntity user) {
        return !user.isOnGround() && !user.isTouchingWater() && !user.isClimbing() && !user.getAbilities().flying && !user.hasVehicle() && user.fallDistance > 0.0f;
    }

    @Override
    public boolean isOnCooldown(PlayerEntity user) {
        return super.isOnCooldown(user) && condition(user);
    }

    @Override
    public boolean isXpSufficient(PlayerEntity user) {
        return !condition(user) || super.isXpSufficient(user) > 0 ;
    }

    @Override
    public boolean shouldNotifyOfFailingCondition(PlayerEntity user) {
        return false;
    }
}
