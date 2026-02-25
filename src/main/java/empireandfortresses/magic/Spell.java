package empireandfortresses.magic;

import empireandfortresses.EmpiresAndFortresses;
import empireandfortresses.component.ModComponents;
import empireandfortresses.entity.attribute.ModEntityAttributes;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

@Getter
@Setter

public abstract class Spell {
    private final Identifier spellID;
    private final SpellCategory category;
    private final SpellTriggerCategory triggerCategory;
    private final int XPCost;
    private final boolean consumingXPLevel;
    private final int maxCooldown;
    private final boolean chargable;
    private final int castTime;
    private final Identifier spellIcon;

    public Spell(String id, SpellCategory category, SpellTriggerCategory triggerCategory, int cost, boolean consumingXPLevel, int maxCooldown, boolean chargable, int castTime, Identifier spellIcon) {
        this.spellID = new Identifier(EmpiresAndFortresses.MOD_ID, id);
        this.category = category;
        this.triggerCategory = triggerCategory;
        this.XPCost = cost;
        this.consumingXPLevel = consumingXPLevel;
        this.maxCooldown = maxCooldown;
        this.chargable = chargable;
        this.castTime = castTime;
        this.spellIcon = spellIcon;
    }

    public void cast(World world, PlayerEntity user, ItemStack stack) {
        cast(world, user, stack, 1);
    }

    public void cast(World world, PlayerEntity user, ItemStack stack, int itemDamage) {
        if (!user.isCreative()) {
            stack.damage(itemDamage, user, (e) -> {
                e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
            });
        }
    }

    public boolean condition() {
        return true;
    }

    public boolean XPSufficient(PlayerEntity user) {
        if (consumingXPLevel) {
            return user.experienceLevel >= XPCost * user.getAttributeValue(ModEntityAttributes.XP_EFFICIENCY);
        }
        return user.totalExperience >= XPCost * user.getAttributeValue(ModEntityAttributes.XP_EFFICIENCY);
    }

    public boolean castable(PlayerEntity user) {
        if (!this.XPSufficient(user)) {
            user.sendMessage(Text.translatable("spell.emp_fort.fail.xp").formatted(Formatting.RED), true);
            return false;
        }
        if (this.isOnCooldown(user)) {
            user.sendMessage(Text.translatable("spell.emp_fort.fail.cooldown").formatted(Formatting.RED), true);
            return false;
        }
        if (!this.condition()) {
            user.sendMessage(Text.translatable("spell.emp_fort.fail.condition").formatted(Formatting.RED), true);
            return false;
        }
        return true;
    }

    public boolean isOnCooldown(PlayerEntity user) {
        return ModComponents.COOLDOWN_COMPONENT.get(user).getCooldown(category) > 0;
    }

    public void consumeXP(PlayerEntity user, int cost, boolean consumesLevel) {
        if (!consumesLevel) {
            user.addExperience((int) (-cost * user.getAttributeValue(ModEntityAttributes.XP_EFFICIENCY)));
        } else {
            user.addExperienceLevels((int) (-cost * user.getAttributeValue(ModEntityAttributes.XP_EFFICIENCY)));
        }
    }

    public void consumeXP(PlayerEntity user) {
        consumeXP(user, getXPCost(), isConsumingXPLevel());
    }

    public NbtCompound toNbt() {
        NbtCompound nbt = new NbtCompound();
        nbt.putString("Id", spellID.toString());
        return nbt;
    }

    public void activateCooldown(PlayerEntity user) {
        int cooldown = (int) (maxCooldown * (1 / user.getAttributeValue(ModEntityAttributes.MAGIC_AFFINITY)));
        ModComponents.COOLDOWN_COMPONENT.get(user).setCooldown(category, cooldown);
        ModComponents.COOLDOWN_COMPONENT.get(user).setMaxCooldown(category, cooldown);
    }
}
