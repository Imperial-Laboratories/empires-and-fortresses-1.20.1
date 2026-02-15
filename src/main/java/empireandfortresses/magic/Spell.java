package empireandfortresses.magic;

import empireandfortresses.EmpiresAndFortresses;
import empireandfortresses.component.ModComponents;
import empireandfortresses.entity.attribute.ModEntityAttributes;
import lombok.Getter;
import lombok.Setter;
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

    public Spell(String id, SpellCategory category, SpellTriggerCategory triggerCategory, int cost, boolean consumingXPLevel, int maxCooldown, boolean chargable, int castTime) {
        this.spellID = new Identifier(EmpiresAndFortresses.MOD_ID, id);
        this.category = category;
        this.triggerCategory = triggerCategory;
        this.XPCost = cost;
        this.consumingXPLevel = consumingXPLevel;
        this.maxCooldown = maxCooldown;
        this.chargable = chargable;
        this.castTime = castTime;
    }

    public abstract void cast(World world, PlayerEntity user, ItemStack stack);

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
        if (condition() && ((XPSufficient(user) && !isOnCooldown(user)) || user.isCreative())) {
            return true;
        } else {
            if(!this.XPSufficient(user)) {
                user.sendMessage(Text.translatable("spell.emp_fort.fail.xp").formatted(Formatting.RED), true);
            } else if(this.isOnCooldown(user)) {
                user.sendMessage(Text.translatable("spell.emp_fort.fail.cooldown").formatted(Formatting.RED), true);
            } else if(!this.condition()) {
                user.sendMessage(Text.translatable("spell.emp_fort.fail.condition").formatted(Formatting.RED), true);
            }
            return false;
        }
    }

    public boolean isOnCooldown(PlayerEntity user) {
        return ModComponents.COOLDOWN_COMPONENT.get(user).getCooldown(category) > 0;
    }

    public void consumeXP(PlayerEntity user, int cost, boolean consumesLevel) {
        if (!consumesLevel) {
            user.addExperience((int)(-cost * user.getAttributeValue(ModEntityAttributes.XP_EFFICIENCY)));
        } else {
            user.addExperienceLevels((int)(-cost * user.getAttributeValue(ModEntityAttributes.XP_EFFICIENCY)));
        }
    }

    public NbtCompound toNbt() {
        NbtCompound nbt = new NbtCompound();
        nbt.putString("Id", spellID.toString());
        return nbt;
    }

    public void activateCooldown(PlayerEntity user) {
        ModComponents.COOLDOWN_COMPONENT.get(user).setCooldown(category, (int)(maxCooldown * (1 / user.getAttributeValue(ModEntityAttributes.MAGIC_AFFINITY))));
    }
}
