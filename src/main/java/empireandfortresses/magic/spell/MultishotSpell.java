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

public class MultishotSpell extends Spell {

    public MultishotSpell() {
        super("multishot", SpellCategory.OFFENSE, SpellTriggerCategory.ATTACK, 2, true, 30, false, 0, new Identifier(EmpiresAndFortresses.MOD_ID, "textures/gui/spell/multishot.png"));
    }

    @Override
    public void cast(World world, PlayerEntity user, ItemStack stack) {

        for (int i = -1; i <= 1; i++) {
            MagicBulletEntity entity = new MagicBulletEntity(ModEntities.MAGIC_BULLET, world,
                    (float) user.getAttributeValue(ModEntityAttributes.MAGIC_ATTACK_DAMAGE) * (float) user.getAttributeValue(ModEntityAttributes.MAGIC_AFFINITY) / 3);
            entity.spawnBullet(world, user, 1.0f, 1f, 10f * i);
        }
        if (!user.isCreative()) {
            activateCooldown(user);
            consumeXP(user);
            super.cast(world, user, stack);
        }
    }
}
