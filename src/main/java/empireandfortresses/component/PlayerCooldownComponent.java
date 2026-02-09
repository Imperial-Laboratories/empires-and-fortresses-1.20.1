package empireandfortresses.component;

import java.util.EnumMap;
import java.util.Map;

import empireandfortresses.EmpiresAndFortresses;
import empireandfortresses.magic.SpellCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

public class PlayerCooldownComponent implements CooldownComponent {
    private final Map<SpellCategory, Integer> cooldowns = new EnumMap<>(SpellCategory.class);

    public PlayerCooldownComponent(PlayerEntity _PlayerEntity) {}

    @Override
    public void readFromNbt(NbtCompound tag) {
        NbtCompound list = tag.getCompound("Cooldowns");
        for (SpellCategory category : SpellCategory.values()) {
            if (list.contains(category.name())) {
                cooldowns.put(category, list.getInt(category.name()));
            }
        }
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        NbtCompound list = new NbtCompound();
        cooldowns.forEach((category, value) -> {
            if (value > 0) {
                list.putInt(category.name(), value);
            }
        });
        tag.put("Cooldowns", list);
    }

    @Override
    public int getCooldown(SpellCategory category) {
        return cooldowns.getOrDefault(category, 0);
    }

    @Override
    public void setCooldown(SpellCategory category, int ticks) {
        cooldowns.put(category, ticks);
    }

    @Override
    public void serverTick() {
        EmpiresAndFortresses.LOGGER.info("tick");
        cooldowns.entrySet().forEach(entry -> {
            // if (entry.getValue() > 0) {
                // entry.setValue(entry.getValue() - 1);
            // }
            var value = entry.getValue();
            if (value > 0) {
                entry.setValue(value - 1);
            }
        });
    }
}
