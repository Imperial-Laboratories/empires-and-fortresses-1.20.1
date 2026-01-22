package empireandfortresses.item;

import empireandfortresses.EmpiresAndFortresses;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

@SuppressWarnings({ "unused", "java:S125" })
public class ModItems {

    // Example on how to add to a vanilla item group
    private static void addItemsToIngredientItemGroup(FabricItemGroupEntries entries) {
        // entries.add({item_here});
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(EmpiresAndFortresses.MOD_ID, name), item);
    }

    public static void registerItems() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientItemGroup);
    }

}
