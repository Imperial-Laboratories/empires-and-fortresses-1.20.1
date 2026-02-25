package empireandfortresses.magic.spell;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import empireandfortresses.EmpiresAndFortresses;
import empireandfortresses.entity.ModEntities;
import empireandfortresses.entity.attribute.ModEntityAttributes;
import empireandfortresses.entity.spell.MagicBulletEntity;
import empireandfortresses.magic.Spell;
import empireandfortresses.magic.SpellCategory;
import empireandfortresses.magic.SpellTriggerCategory;

public class MagicBulletSpell extends Spell {

    public MagicBulletSpell() {
        super("magic_bullet", SpellCategory.OFFENSE, SpellTriggerCategory.ATTACK, 15, false, 20, false, 0, new Identifier(EmpiresAndFortresses.MOD_ID, "textures/gui/spell/magic_bullet.png"));
    }

    @Override
    public void cast(World world, PlayerEntity user, ItemStack stack) {
        MagicBulletEntity entity = new MagicBulletEntity(ModEntities.MAGIC_BULLET, world, (float)user.getAttributeValue(ModEntityAttributes.MAGIC_ATTACK_DAMAGE) * (float)user.getAttributeValue(ModEntityAttributes.MAGIC_AFFINITY));
        entity.spawnBullet(world, user, 1.0f, 1.0f);
        if (!user.isCreative()) {
            activateCooldown(user);
            consumeXP(user, getXPCost(), isConsumingXPLevel());
            super.cast(world, user, stack);
        }
    }
}
