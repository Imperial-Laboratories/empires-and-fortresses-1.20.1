package empireandfortresses.component;

import java.util.EnumMap;
import java.util.Map;

import empireandfortresses.magic.SpellCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerCooldownComponent implements CooldownComponent {
    private final Map<SpellCategory, Integer> cooldowns = new EnumMap<>(SpellCategory.class);
    private final Map<SpellCategory, Integer> maxCooldowns = new EnumMap<>(SpellCategory.class);

    private final PlayerEntity player;

    public PlayerCooldownComponent(PlayerEntity player) {
        this.player = player;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        NbtCompound list = tag.getCompound("Cooldowns");
        NbtCompound maxCooldownList = tag.getCompound("MaxCooldowns");
        for (SpellCategory category : SpellCategory.values()) {
            if (list.contains(category.name())) {
                cooldowns.put(category, list.getInt(category.name()));
            }
            if (maxCooldownList.contains(category.name())) {
                maxCooldowns.put(category, maxCooldownList.getInt(category.name()));
            }
        }
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        NbtCompound list = new NbtCompound();
        cooldowns.forEach((category, value) -> {
            if (value >= 0) {
                list.putInt(category.name(), value);
            }
        });
        tag.put("Cooldowns", list);

        NbtCompound maxCooldownList = new NbtCompound();
        maxCooldowns.forEach((category, value) -> {
            if (value > 0) {
                maxCooldownList.putInt(category.name(), value);
            }
        });
        tag.put("MaxCooldowns", maxCooldownList);
    }

    @Override
    public int getCooldown(SpellCategory category) {
        return cooldowns.getOrDefault(category, 0);
    }

    @Override
    public void setCooldown(SpellCategory category, int ticks) {
        cooldowns.put(category, ticks);
        ModComponents.COOLDOWN_COMPONENT.sync(this.player);
    }

    @Override
    public int getMaxCooldown(SpellCategory category) {
        return maxCooldowns.getOrDefault(category, 1);
    }

    @Override
    public void setMaxCooldown(SpellCategory category, int ticks) {
        maxCooldowns.put(category, ticks);
        ModComponents.COOLDOWN_COMPONENT.sync(this.player);
    }

    @Override
    public void serverTick() {
        cooldowns.entrySet().forEach(entry -> {
            var value = entry.getValue();
            if (value > 0) {
                entry.setValue(value - 1);
            }
        });
        ModComponents.COOLDOWN_COMPONENT.sync(this.player);
    }

    @Override
    public boolean shouldSyncWith(ServerPlayerEntity player) {
        return this.player == player;
    }
}
