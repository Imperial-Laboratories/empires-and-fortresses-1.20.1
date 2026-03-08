package empireandfortresses.enchantment;

import java.util.HashMap;
import java.util.List;

import empireandfortresses.item.ModItems;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

public class EnchantingItems {

    public static HashMap<Item, List<Enchantment>> enchantingItemMap = new HashMap<Item, List<Enchantment>>();

    public static void registerEnchantingItems() {
        enchantingItemMap.put(Items.LAPIS_LAZULI, List.of(Enchantments.SHARPNESS, Enchantments.SMITE, Enchantments.BANE_OF_ARTHROPODS));
        enchantingItemMap.put(ModItems.RUBY, List.of(Enchantments.FIRE_ASPECT, Enchantments.FLAME, Enchantments.FIRE_PROTECTION));
    }

}
