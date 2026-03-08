package empireandfortresses.magic.spell;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import empireandfortresses.entity.attribute.ModEntityAttributes;
import empireandfortresses.magic.Spell;
import empireandfortresses.magic.SpellCategory;
import empireandfortresses.magic.SpellTriggerCategory;

public class HealSpell extends Spell {

    public HealSpell() {
        super("heal", SpellCategory.HEAL, SpellTriggerCategory.USE, 3, true, 20, false, 0);
    }

    @Override
    public void cast(PlayerEntity user, ItemStack stack) {

        user.heal(5 * (float) user.getAttributeValue(ModEntityAttributes.MAGIC_AFFINITY));

        if (!user.isCreative()) {
            activateCooldown(user);
            consumeXP(user);
            super.cast(user, stack);
        }
    }
}
