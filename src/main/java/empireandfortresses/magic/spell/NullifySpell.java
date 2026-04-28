package empireandfortresses.magic.spell;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import empireandfortresses.magic.Spell;
import empireandfortresses.magic.SpellCategory;
import empireandfortresses.magic.SpellTriggerCategory;

public class NullifySpell extends Spell {

    public NullifySpell() {
        super("nullify", SpellCategory.SUPPORT, SpellTriggerCategory.USE, 5, true, 200, false, 0);
    }

    @Override
    public void cast(PlayerEntity user, ItemStack stack) {

        user.clearStatusEffects();

        if (!user.isCreative()) {
            activateCooldown(user);
            consumeXP(user);
            super.cast(user, stack);
        }
    }

    @Override
    public boolean condition(PlayerEntity user) {
        return user.getActiveStatusEffects().size() > 0;
    }
}
