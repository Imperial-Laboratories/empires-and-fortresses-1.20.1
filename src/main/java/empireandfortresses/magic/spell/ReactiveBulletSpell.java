package empireandfortresses.magic.spell;

import empireandfortresses.EmpiresAndFortresses;
import empireandfortresses.entity.ModEntities;
import empireandfortresses.entity.attribute.ModEntityAttributes;
import empireandfortresses.entity.spell.ExplosiveMagicBulletEntity;
import empireandfortresses.magic.Spell;
import empireandfortresses.magic.SpellCategory;
import empireandfortresses.magic.SpellTriggerCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

public class ReactiveBulletSpell extends Spell {

    public ReactiveBulletSpell() {
        super("reactive_bullet", SpellCategory.OFFENSE, SpellTriggerCategory.HOLD_ATTACK, 1, true, 40, true, 30, new Identifier(EmpiresAndFortresses.MOD_ID, "textures/gui/spell/reactive_bullet.png"));
    }

    @Override
    public void cast(PlayerEntity user, ItemStack stack) {
        NbtCompound nbt = stack.getNbt();
        int useTimer = nbt.getInt("useTimer");
        ++useTimer;
        nbt.putInt("useTimer", useTimer);
        if (useTimer >= getCastTime()) {
            nbt.putInt("useTimer", 0);
            if (!user.isCreative()) {
                activateCooldown(user);
            }

            ExplosiveMagicBulletEntity entity = new ExplosiveMagicBulletEntity(ModEntities.MAGIC_BULLET, user.getWorld(),
                    (float) user.getAttributeValue(ModEntityAttributes.MAGIC_ATTACK_DAMAGE) * (float) user.getAttributeValue(ModEntityAttributes.MAGIC_AFFINITY));
            entity.spawnBullet(user.getWorld(), user, 1.5f, 1f, 30, 0);
            if (!user.isCreative()) {
                consumeXP(user);
                super.cast(user, stack);
            }
        }
    }
}
