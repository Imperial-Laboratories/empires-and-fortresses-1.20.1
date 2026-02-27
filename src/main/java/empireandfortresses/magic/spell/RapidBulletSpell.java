package empireandfortresses.magic.spell;

import empireandfortresses.EmpiresAndFortresses;
import empireandfortresses.entity.ModEntities;
import empireandfortresses.entity.attribute.ModEntityAttributes;
import empireandfortresses.entity.spell.MagicBulletEntity;
import empireandfortresses.magic.Spell;
import empireandfortresses.magic.SpellCategory;
import empireandfortresses.magic.SpellTriggerCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

public class RapidBulletSpell extends Spell {

    public RapidBulletSpell() {
        super("rapid_bullet", SpellCategory.OFFENSE, SpellTriggerCategory.HOLD_ATTACK, 1, false, 120, false, 100, new Identifier(EmpiresAndFortresses.MOD_ID, "textures/gui/spell/rapid_bullet.png"));
    }

    @Override
    public void cast(PlayerEntity user, ItemStack stack) {
        NbtCompound nbt = stack.getNbt();
        int useTimer = nbt.getInt("useTimer");
        if (useTimer % 5 == 0) {
            MagicBulletEntity entity = new MagicBulletEntity(ModEntities.MAGIC_BULLET, user.getWorld(),
                    (float) user.getAttributeValue(ModEntityAttributes.MAGIC_ATTACK_DAMAGE) * (float) user.getAttributeValue(ModEntityAttributes.MAGIC_AFFINITY) / 8);
            entity.spawnBullet(user.getWorld(), user, 2.0f, 10f, 5);
            if (!user.isCreative()) {
                super.cast(user, stack);
            }
        }

        // nbt modification every tick. Probably not a good idea
        ++useTimer;
        nbt.putInt("useTimer", useTimer);
        if (useTimer >= getCastTime()) {
            nbt.putInt("useTimer", 0);
            if (!user.isCreative()) {
                activateCooldown(user);
            }
        }
        if (!user.isCreative()) {
            consumeXP(user);
        }
    }
}
