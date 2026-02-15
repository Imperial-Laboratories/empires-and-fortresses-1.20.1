package empireandfortresses.magic.spell;

import empireandfortresses.entity.ModEntities;
import empireandfortresses.entity.attribute.ModEntityAttributes;
import empireandfortresses.entity.spell.MagicBulletEntity;
import empireandfortresses.magic.Spell;
import empireandfortresses.magic.SpellCategory;
import empireandfortresses.magic.SpellTriggerCategory;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

public class RapidBulletSpell extends Spell {

    public RapidBulletSpell() {
        super("rapid_bullet", SpellCategory.OFFENSE, SpellTriggerCategory.HOLD_ATTACK, 1, false, 120, false, 100);
    }

    @Override
    public void cast(World world, PlayerEntity user, ItemStack stack) {
        NbtCompound nbt = stack.getNbt();
        int useTimer = nbt.getInt("useTimer");
        if (useTimer % 5 == 0) {
            spawnBullet(world, user);
            if (!user.isCreative()) {
                stack.damage(1, user, (e) -> {
                    e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
                });
            }
        }
        // nbt modification every tick. Probably not a good idea
        if (!user.isCreative()) {
            ++useTimer;
            nbt.putInt("useTimer", useTimer);
            if (useTimer >= getCastTime()) {
                nbt.putInt("useTimer", 0);
                activateCooldown(user);
            }
            consumeXP(user, getXPCost(), isConsumingXPLevel());
        }
    }

    public void spawnBullet(World world, PlayerEntity user) {
        MagicBulletEntity entity = new MagicBulletEntity(ModEntities.MAGIC_BULLET, world, (float)user.getAttributeValue(ModEntityAttributes.MAGIC_ATTACK_DAMAGE) * (float)user.getAttributeValue(ModEntityAttributes.MAGIC_AFFINITY) / 8);
        entity.setOwner(user);
        entity.setPos(user.getX(), user.getEyeY() - 0.25f, user.getZ());
        entity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 2.0f, 2.5f);
        entity.setDuration(5);
        world.spawnEntity(entity);
    }
}
