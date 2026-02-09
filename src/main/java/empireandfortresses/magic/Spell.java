package empireandfortresses.magic;

import empireandfortresses.EmpiresAndFortresses;
import empireandfortresses.component.ModComponents;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

@Getter
@Setter

public abstract class Spell {
    private final Identifier spellID;
    private final SpellCategory category;
    private final int XPCost;
    private final boolean consumingXPLevel;
    private final int maxCooldown;
    private final boolean chargable;
    private final int castTime;

    public Spell(String id, SpellCategory category, int cost, boolean consumingXPLevel, int maxCooldown, boolean chargable, int castTime) {
        this.spellID = new Identifier(EmpiresAndFortresses.MOD_ID, id);
        this.category = category;
        this.XPCost = cost;
        this.consumingXPLevel = consumingXPLevel;
        this.maxCooldown = maxCooldown;
        this.chargable = chargable;
        this.castTime = castTime;
    }

    public abstract boolean condition();

    public abstract void cast(World world, PlayerEntity user, ItemStack stack);

    public boolean XPSufficient(PlayerEntity user) {
        if (consumingXPLevel) {
            return user.experienceLevel >= XPCost;
        }
        return user.totalExperience >= XPCost;
    }

    public boolean castable(PlayerEntity user) {
        return condition() && ((XPSufficient(user) && !isOnCooldown(user)) || user.isCreative());
    }

    public boolean isOnCooldown(PlayerEntity user) {
        return ModComponents.COOLDOWN_COMPONENT.get(user).getCooldown(category) > 0;
    }

    public void consumeXP(PlayerEntity user, int cost, boolean consumesLevel) {
        if (!consumesLevel) {
            user.addExperience(-cost);
        } else {
            user.addExperienceLevels(-cost);
        }
    }

    public NbtCompound toNbt() {
        NbtCompound nbt = new NbtCompound();
        nbt.putString("Id", spellID.toString());
        // nbt.putInt("Cost", XPCost);
        // nbt.putBoolean("RequiresXPLevel", requiresXPLevel);
        // nbt.putInt("Cooldown", cooldown);
        // nbt.putBoolean("HasCharge", hasCharge);
        // nbt.putInt("CastTime", castTime);
        return nbt;
    }

    public void activateCooldown(PlayerEntity user) {
        ModComponents.COOLDOWN_COMPONENT.get(user).setCooldown(category, maxCooldown);
    }
}
