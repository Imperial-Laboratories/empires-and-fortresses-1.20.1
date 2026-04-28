package empireandfortresses.magic.spell;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import empireandfortresses.magic.Spell;
import empireandfortresses.magic.SpellCategory;
import empireandfortresses.magic.SpellTriggerCategory;

public class BackstepSpell extends Spell {

    public BackstepSpell() {
        super("backstep", SpellCategory.MOBILITY, SpellTriggerCategory.JUMP, 1, true, 60, false, 0);
    }

    @Override
    public void cast(PlayerEntity user, ItemStack stack) {

        Vec3d look = user.getRotationVec(1f);
        user.addVelocity(look.x * -0.5, 0, look.z * -0.5);
        user.velocityModified = true;

        if (!user.isCreative()) {
            activateCooldown(user);
            consumeXP(user);
            super.cast(user, stack);
        }
    }

    @Override
    public boolean condition(PlayerEntity user) {
        return !user.isTouchingWater() && !user.isClimbing() && !user.getAbilities().flying && !user.hasVehicle();
    }

    @Override
    public boolean shouldNotifyOfFailingCondition(PlayerEntity user) {
        return false;
    }
}
