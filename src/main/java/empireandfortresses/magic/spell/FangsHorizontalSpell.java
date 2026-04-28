package empireandfortresses.magic.spell;

import empireandfortresses.entity.attribute.ModEntityAttributes;
import empireandfortresses.magic.Spell;
import empireandfortresses.magic.SpellCategory;
import empireandfortresses.magic.SpellTriggerCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

public class FangsHorizontalSpell extends Spell {

    public FangsHorizontalSpell() {
        super("fangs_horizontal", SpellCategory.DEFENSE, SpellTriggerCategory.ATTACK, 50, false, 100, false, 0);
    }

    @Override
    public void cast(PlayerEntity user, ItemStack stack) {
        double magicAffinity = user.getAttributeValue(ModEntityAttributes.MAGIC_AFFINITY);
        double damage = user.getAttributeValue(ModEntityAttributes.MAGIC_ATTACK_DAMAGE) * magicAffinity / 5;

        int fangCount = (int) (2 + magicAffinity * 3);
        double radius = 2.5;
        float arcDegrees = fangCount * 25f;

        float baseYawRad = (float) Math.toRadians(user.getYaw());
        float arcRad = (float) Math.toRadians(arcDegrees);
        float startAngle = baseYawRad - (arcRad / 2f);
        float angleStep = fangCount > 1 ? arcRad / (fangCount - 1) : 0f;

        for (int i = 0; i < fangCount; i++) {
            float angle = startAngle + (angleStep * i);
            double x = user.getX() + (-MathHelper.sin(angle) * radius);
            double z = user.getZ() + (MathHelper.cos(angle) * radius);

            float fangYaw = (float) Math.toDegrees(angle);

            FangsVerticalSpell.conjureFangs(user, x, z, user.getY(), user.getY(), fangYaw, i * 2, (float) damage);
        }

        if (!user.isCreative()) {
            activateCooldown(user);
            consumeXP(user);
            super.cast(user, stack);
        }
    }
}
