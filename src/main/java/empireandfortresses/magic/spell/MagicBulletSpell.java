package empireandfortresses.magic.spell;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import empireandfortresses.entity.ModEntities;
import empireandfortresses.entity.attribute.ModEntityAttributes;
import empireandfortresses.entity.spell.MagicBulletEntity;
import empireandfortresses.magic.Spell;
import empireandfortresses.magic.SpellCategory;
import empireandfortresses.magic.SpellTriggerCategory;

public class MagicBulletSpell extends Spell {

    public MagicBulletSpell() {
        super("magic_bullet", SpellCategory.OFFENSE, SpellTriggerCategory.ATTACK, 15, false, 20, false, 0);
    }

    @Override
    public void cast(World world, PlayerEntity user, ItemStack stack) {
        MagicBulletEntity entity = new MagicBulletEntity(ModEntities.MAGIC_BULLET, world, (float)user.getAttributeValue(ModEntityAttributes.GENERIC_MAGIC_ATTACK_DAMAGE));
        entity.setOwner(user);
        entity.setPos(user.getX(), user.getEyeY() - 0.25f, user.getZ());
        entity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.0F, 1);

        world.spawnEntity(entity);

        if (!user.isCreative()) {
            activateCooldown(user);
            consumeXP(user, getXPCost(), isConsumingXPLevel());
            stack.damage(1, user, (e) -> {
                e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
            });
        }
    }
}
