package empireandfortresses.magic;

import empireandfortresses.EmpiresAndFortresses;
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
    private final int XPCost;
    private final boolean consumesXPLevel;
    private final int maxCooldown;
    private int cooldownTimer;
    private final boolean hasCharge;
    private final int castTime;

    public Spell(String id, int cost, boolean type, int maxCooldown, boolean hasCharge, int castTime) {
        this.spellID = new Identifier(EmpiresAndFortresses.MOD_ID, id);
        this.XPCost = cost;
        this.consumesXPLevel = type;
        this.maxCooldown = maxCooldown;
        this.hasCharge = hasCharge;
        this.castTime = castTime;
    }

    public abstract boolean condition();

    public abstract void cast(World world, PlayerEntity user, ItemStack stack);

    public boolean XPSufficient(PlayerEntity user) {
        if (consumesXPLevel) {
            return user.experienceLevel >= XPCost;
        }
        return user.totalExperience >= XPCost;
    }

    public boolean castable(PlayerEntity user) {
        return condition() && ((XPSufficient(user) && !onCooldown()) || user.isCreative());
    }

    public void cooldownTick() {
        int t = this.getCooldownTimer();
        t--;
        this.setCooldownTimer(t);
    }

    public boolean onCooldown() {
        return getCooldownTimer() > 0;
    }

    public void consumeXP(PlayerEntity user, int cost, boolean consumesLevel) {
        if (!consumesLevel) {
            user.totalExperience -= cost;
        } else {
            user.experienceLevel -= cost;
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

    public boolean hasCharge() {
        return hasCharge;
    }

    public boolean consumesXPLevel() {
        return consumesXPLevel;
    }
}
