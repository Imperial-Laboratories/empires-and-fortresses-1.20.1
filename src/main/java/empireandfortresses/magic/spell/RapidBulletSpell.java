package empireandfortresses.magic.spell;

import empireandfortresses.entity.ModEntities;
import empireandfortresses.entity.attribute.ModEntityAttributes;
import empireandfortresses.entity.spell.MagicBulletEntity;
import empireandfortresses.magic.Spell;
import empireandfortresses.magic.SpellCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class RapidBulletSpell extends Spell {

    public RapidBulletSpell() {
        super("rapid_bullet", SpellCategory.OFFENSE, 1, false, 2, false, 100);
    }

    @Override
    public boolean condition() {
        return true;
    }

    @Override
    public void cast(World world, PlayerEntity user, ItemStack stack) {
        MagicBulletEntity entity = new MagicBulletEntity(ModEntities.MAGIC_BULLET, world, (float)user.getAttributeValue(ModEntityAttributes.GENERIC_MAGIC_ATTACK_DAMAGE) / 4);
        entity.setOwner(user);
        entity.setPos(user.getX(), user.getEyeY() - 0.25f, user.getZ());
        entity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 2.0f, 2.5f);

        entity.setDuration(5);

        world.spawnEntity(entity);

        if (!user.isCreative()) {
            activateCooldown(user);
            consumeXP(user, getXPCost(), isConsumingXPLevel());
        }
    }
}
