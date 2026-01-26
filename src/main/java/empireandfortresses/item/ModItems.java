package empireandfortresses.item;

import javax.tools.Tool;

import empireandfortresses.EmpiresAndFortresses;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.AxeItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

@SuppressWarnings({ "unused", "java:S125" })
public class ModItems {

    public static final Item OBSIDIAN_SWORD = registerItem("obsidian_sword",
            new SwordItem(ModToolMaterial.OBSIDIAN, 6, -2.4f, new FabricItemSettings()));
    public static final Item OBSIDIAN_PICKAXE = registerItem("obsidian_pickaxe",
            new PickaxeItem(ModToolMaterial.OBSIDIAN, 6, -2.8f, new FabricItemSettings()));
    public static final Item OBSIDIAN_AXE = registerItem("obsidian_axe",
            new AxeItem(ModToolMaterial.OBSIDIAN, 6f, -3f, new FabricItemSettings()));
    public static final Item OBSIDIAN_SHOVEL = registerItem("obsidian_shovel",
            new ShovelItem(ModToolMaterial.OBSIDIAN, 6f, -2f, new FabricItemSettings()));
    public static final Item OBSIDIAN_HOE = registerItem("obsidian_hoe",
            new HoeItem(ModToolMaterial.OBSIDIAN, -6, 0f, new FabricItemSettings()));

    // Example on how to add to a vanilla item group
    private static void addItemsToCombatItemGroup(FabricItemGroupEntries entries) {
        // Swords
        entries.add(OBSIDIAN_SWORD);

        // Axes
        entries.add(OBSIDIAN_AXE);
    }

    private static void addItemsToToolsItemGroup(FabricItemGroupEntries entries) {
        // Obsidian Tools
        entries.add(OBSIDIAN_SHOVEL);
        entries.add(OBSIDIAN_PICKAXE);
        entries.add(OBSIDIAN_AXE);
        entries.add(OBSIDIAN_HOE);
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(EmpiresAndFortresses.MOD_ID, name), item);
    }

    public static void registerItems() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(ModItems::addItemsToToolsItemGroup);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(ModItems::addItemsToCombatItemGroup);
    }

}
