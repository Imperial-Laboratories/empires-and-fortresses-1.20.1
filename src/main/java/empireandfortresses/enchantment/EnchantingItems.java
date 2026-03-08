package empireandfortresses.enchantment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import empireandfortresses.item.ModItems;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

@SuppressWarnings("java:S2386")
public class EnchantingItems {

    public static final Map<Item, List<Enchantment>> enchantingItemMap = new HashMap<>();

    public static void registerEnchantingItems() {
        enchantingItemMap.put(Items.LAPIS_LAZULI, List.of(Enchantments.SHARPNESS, Enchantments.SMITE, Enchantments.BANE_OF_ARTHROPODS));
        enchantingItemMap.put(ModItems.RUBY, List.of(Enchantments.FIRE_ASPECT, Enchantments.FLAME, Enchantments.FIRE_PROTECTION));
    }

}
