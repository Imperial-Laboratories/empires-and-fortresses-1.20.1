package empireandfortresses.item;

import javax.tools.Tool;

import empireandfortresses.EmpiresAndFortresses;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ArmorItem;
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

// TODO: Balance ALL of the items added here
@SuppressWarnings({ "unused", "java:S125" })
public class ModItems {

    public static final Item RUBY = registerItem("ruby", new Item(new FabricItemSettings()));
    public static final Item SAPPHIRE = registerItem("sapphire", new Item(new FabricItemSettings()));
    public static final Item TOPAZ = registerItem("topaz", new Item(new FabricItemSettings()));
    public static final Item STEEL_INGOT = registerItem("steel_ingot", new Item(new FabricItemSettings()));
    public static final Item PLATINUM_INGOT = registerItem("platinum_ingot", new Item(new FabricItemSettings()));
    public static final Item MITHRIL = registerItem("mithril", new Item(new FabricItemSettings()));
    public static final Item ADAMANT = registerItem("adamant", new Item(new FabricItemSettings()));

    // Flint
    public static final Item FLINT_SWORD = registerItem("flint_sword",
            new SwordItem(ModToolMaterial.FLINT, 3, -2.4f, new FabricItemSettings()));
    public static final Item FLINT_PICKAXE = registerItem("flint_pickaxe",
            new PickaxeItem(ModToolMaterial.FLINT, 1, -2.8f, new FabricItemSettings()));
    public static final Item FLINT_AXE = registerItem("flint_axe",
            new AxeItem(ModToolMaterial.FLINT, 5f, -3f, new FabricItemSettings()));
    public static final Item FLINT_SHOVEL = registerItem("flint_shovel",
            new ShovelItem(ModToolMaterial.FLINT, 1.5f, -2f, new FabricItemSettings()));
    public static final Item FLINT_HOE = registerItem("flint_hoe",
            new HoeItem(ModToolMaterial.FLINT, -2, 0f, new FabricItemSettings()));

    // Bone
    public static final Item BONE_SWORD = registerItem("bone_sword",
            new SwordItem(ModToolMaterial.BONE, 3, -2.4f, new FabricItemSettings()));
    public static final Item BONE_PICKAXE = registerItem("bone_pickaxe",
            new PickaxeItem(ModToolMaterial.BONE, 1, -2.8f, new FabricItemSettings()));
    public static final Item BONE_AXE = registerItem("bone_axe",
            new AxeItem(ModToolMaterial.BONE, 5f, -3f, new FabricItemSettings()));
    public static final Item BONE_SHOVEL = registerItem("bone_shovel",
            new ShovelItem(ModToolMaterial.BONE, 1.5f, -2f, new FabricItemSettings()));
    public static final Item BONE_HOE = registerItem("bone_hoe",
            new HoeItem(ModToolMaterial.BONE, -2, 0f, new FabricItemSettings()));

    // Copper
    public static final Item COPPER_SWORD = registerItem("copper_sword",
            new SwordItem(ModToolMaterial.COPPER, 3, -2.4f, new FabricItemSettings()));
    public static final Item COPPER_PICKAXE = registerItem("copper_pickaxe",
            new PickaxeItem(ModToolMaterial.COPPER, 1, -2.8f, new FabricItemSettings()));
    public static final Item COPPER_AXE = registerItem("copper_axe",
            new AxeItem(ModToolMaterial.COPPER, 7f, -3.2f, new FabricItemSettings()));
    public static final Item COPPER_SHOVEL = registerItem("copper_shovel",
            new ShovelItem(ModToolMaterial.COPPER, 1.5f, -3f, new FabricItemSettings()));
    public static final Item COPPER_HOE = registerItem("copper_hoe",
            new HoeItem(ModToolMaterial.COPPER, -1, -2f, new FabricItemSettings()));

    public static final Item COPPER_HELMET = registerItem("copper_helmet",
            new ArmorItem(ModArmorMaterial.COPPER, ArmorItem.Type.HELMET, new FabricItemSettings()));
    public static final Item COPPER_CHESTPLATE = registerItem("copper_chestplate",
            new ArmorItem(ModArmorMaterial.COPPER, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()));
    public static final Item COPPER_LEGGINGS = registerItem("copper_leggings",
            new ArmorItem(ModArmorMaterial.COPPER, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item COPPER_BOOTS = registerItem("copper_boots",
            new ArmorItem(ModArmorMaterial.COPPER, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    // Granite
    public static final Item GRANITE_SWORD = registerItem("granite_sword",
            new SwordItem(ModToolMaterial.GRANITE, 4, -2.4f, new FabricItemSettings()));
    public static final Item GRANITE_PICKAXE = registerItem("granite_pickaxe",
            new PickaxeItem(ModToolMaterial.GRANITE, 2, -2.8f, new FabricItemSettings()));
    public static final Item GRANITE_AXE = registerItem("granite_axe",
            new AxeItem(ModToolMaterial.GRANITE, 6f, -3f, new FabricItemSettings()));
    public static final Item GRANITE_SHOVEL = registerItem("granite_shovel",
            new ShovelItem(ModToolMaterial.GRANITE, 2f, -2f, new FabricItemSettings()));
    public static final Item GRANITE_HOE = registerItem("granite_hoe",
            new HoeItem(ModToolMaterial.GRANITE, -3, 0f, new FabricItemSettings()));

    // Obsidian
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

    public static final Item OBSIDIAN_HELMET = registerItem("obsidian_helmet",
            new ArmorItem(ModArmorMaterial.OBSIDIAN, ArmorItem.Type.HELMET, new FabricItemSettings()));
    public static final Item OBSIDIAN_CHESTPLATE = registerItem("obsidian_chestplate",
            new ArmorItem(ModArmorMaterial.OBSIDIAN, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()));
    public static final Item OBSIDIAN_LEGGINGS = registerItem("obsidian_leggings",
            new ArmorItem(ModArmorMaterial.OBSIDIAN, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item OBSIDIAN_BOOTS = registerItem("obsidian_boots",
            new ArmorItem(ModArmorMaterial.OBSIDIAN, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    // Ruby
    public static final Item RUBY_SWORD = registerItem("ruby_sword",
            new SwordItem(ModToolMaterial.RUBY, 6, -2.4f, new FabricItemSettings()));
    public static final Item RUBY_PICKAXE = registerItem("ruby_pickaxe",
            new PickaxeItem(ModToolMaterial.RUBY, 6, -2.8f, new FabricItemSettings()));
    public static final Item RUBY_AXE = registerItem("ruby_axe",
            new AxeItem(ModToolMaterial.RUBY, 6f, -3f, new FabricItemSettings()));
    public static final Item RUBY_SHOVEL = registerItem("ruby_shovel",
            new ShovelItem(ModToolMaterial.RUBY, 6f, -2f, new FabricItemSettings()));
    public static final Item RUBY_HOE = registerItem("ruby_hoe",
            new HoeItem(ModToolMaterial.RUBY, -6, 0f, new FabricItemSettings()));

    // Sapphire
    public static final Item SAPPHIRE_SWORD = registerItem("sapphire_sword",
            new SwordItem(ModToolMaterial.SAPPHIRE, 6, -2.4f, new FabricItemSettings()));
    public static final Item SAPPHIRE_PICKAXE = registerItem("sapphire_pickaxe",
            new PickaxeItem(ModToolMaterial.SAPPHIRE, 6, -2.8f, new FabricItemSettings()));
    public static final Item SAPPHIRE_AXE = registerItem("sapphire_axe",
            new AxeItem(ModToolMaterial.SAPPHIRE, 6f, -3f, new FabricItemSettings()));
    public static final Item SAPPHIRE_SHOVEL = registerItem("sapphire_shovel",
            new ShovelItem(ModToolMaterial.SAPPHIRE, 6f, -2f, new FabricItemSettings()));
    public static final Item SAPPHIRE_HOE = registerItem("sapphire_hoe",
            new HoeItem(ModToolMaterial.SAPPHIRE, -6, 0f, new FabricItemSettings()));

    // Emerald
    public static final Item EMERALD_SWORD = registerItem("emerald_sword",
            new SwordItem(ModToolMaterial.EMERALD, 7, -2.4f, new FabricItemSettings()));
    public static final Item EMERALD_PICKAXE = registerItem("emerald_pickaxe",
            new PickaxeItem(ModToolMaterial.EMERALD, 7, -2.8f, new FabricItemSettings()));
    public static final Item EMERALD_AXE = registerItem("emerald_axe",
            new AxeItem(ModToolMaterial.EMERALD, 7f, -3f, new FabricItemSettings()));
    public static final Item EMERALD_SHOVEL = registerItem("emerald_shovel",
            new ShovelItem(ModToolMaterial.EMERALD, 7f, -2f, new FabricItemSettings()));
    public static final Item EMERALD_HOE = registerItem("emerald_hoe",
            new HoeItem(ModToolMaterial.EMERALD, -7, 0f, new FabricItemSettings()));

    // Topaz
    public static final Item TOPAZ_SWORD = registerItem("topaz_sword",
            new SwordItem(ModToolMaterial.TOPAZ, 6, -2.4f, new FabricItemSettings()));
    public static final Item TOPAZ_PICKAXE = registerItem("topaz_pickaxe",
            new PickaxeItem(ModToolMaterial.TOPAZ, 6, -2.8f, new FabricItemSettings()));
    public static final Item TOPAZ_AXE = registerItem("topaz_axe",
            new AxeItem(ModToolMaterial.TOPAZ, 6f, -3f, new FabricItemSettings()));
    public static final Item TOPAZ_SHOVEL = registerItem("topaz_shovel",
            new ShovelItem(ModToolMaterial.TOPAZ, 6f, -2f, new FabricItemSettings()));
    public static final Item TOPAZ_HOE = registerItem("topaz_hoe",
            new HoeItem(ModToolMaterial.TOPAZ, -6, 0f, new FabricItemSettings()));

    // Steel
    public static final Item STEEL_SWORD = registerItem("steel_sword",
            new SwordItem(ModToolMaterial.STEEL, 5, -2.4f, new FabricItemSettings()));
    public static final Item STEEL_PICKAXE = registerItem("steel_pickaxe",
            new PickaxeItem(ModToolMaterial.STEEL, 5, -2.8f, new FabricItemSettings()));
    public static final Item STEEL_AXE = registerItem("steel_axe",
            new AxeItem(ModToolMaterial.STEEL, 5f, -3f, new FabricItemSettings()));
    public static final Item STEEL_SHOVEL = registerItem("steel_shovel",
            new ShovelItem(ModToolMaterial.STEEL, 5f, -2f, new FabricItemSettings()));
    public static final Item STEEL_HOE = registerItem("steel_hoe",
            new HoeItem(ModToolMaterial.STEEL, -5, 0f, new FabricItemSettings()));

    public static final Item STEEL_HELMET = registerItem("steel_helmet",
            new ArmorItem(ModArmorMaterial.STEEL, ArmorItem.Type.HELMET, new FabricItemSettings()));
    public static final Item STEEL_CHESTPLATE = registerItem("steel_chestplate",
            new ArmorItem(ModArmorMaterial.STEEL, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()));
    public static final Item STEEL_LEGGINGS = registerItem("steel_leggings",
            new ArmorItem(ModArmorMaterial.STEEL, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item STEEL_BOOTS = registerItem("steel_boots",
            new ArmorItem(ModArmorMaterial.STEEL, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    // Amethyst
    public static final Item AMETHYST_SWORD = registerItem("amethyst_sword",
            new SwordItem(ModToolMaterial.AMETHYST, 7, -2.4f, new FabricItemSettings()));
    public static final Item AMETHYST_PICKAXE = registerItem("amethyst_pickaxe",
            new PickaxeItem(ModToolMaterial.AMETHYST, 7, -2.8f, new FabricItemSettings()));
    public static final Item AMETHYST_AXE = registerItem("amethyst_axe",
            new AxeItem(ModToolMaterial.AMETHYST, 7f, -3f, new FabricItemSettings()));
    public static final Item AMETHYST_SHOVEL = registerItem("amethyst_shovel",
            new ShovelItem(ModToolMaterial.AMETHYST, 7f, -2f, new FabricItemSettings()));
    public static final Item AMETHYST_HOE = registerItem("amethyst_hoe",
            new HoeItem(ModToolMaterial.AMETHYST, -7, 0f, new FabricItemSettings()));

    public static final Item AMETHYST_HELMET = registerItem("amethyst_helmet",
            new ArmorItem(ModArmorMaterial.AMETHYST, ArmorItem.Type.HELMET, new FabricItemSettings()));
    public static final Item AMETHYST_CHESTPLATE = registerItem("amethyst_chestplate",
            new ArmorItem(ModArmorMaterial.AMETHYST, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()));
    public static final Item AMETHYST_LEGGINGS = registerItem("amethyst_leggings",
            new ArmorItem(ModArmorMaterial.AMETHYST, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item AMETHYST_BOOTS = registerItem("amethyst_boots",
            new ArmorItem(ModArmorMaterial.AMETHYST, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    // Platinum
    public static final Item PLATINUM_SWORD = registerItem("platinum_sword",
            new SwordItem(ModToolMaterial.PLATINUM, 6, -2.4f, new FabricItemSettings()));
    public static final Item PLATINUM_PICKAXE = registerItem("platinum_pickaxe",
            new PickaxeItem(ModToolMaterial.PLATINUM, 6, -2.8f, new FabricItemSettings()));
    public static final Item PLATINUM_AXE = registerItem("platinum_axe",
            new AxeItem(ModToolMaterial.PLATINUM, 6f, -3f, new FabricItemSettings()));
    public static final Item PLATINUM_SHOVEL = registerItem("platinum_shovel",
            new ShovelItem(ModToolMaterial.PLATINUM, 6f, -2f, new FabricItemSettings()));
    public static final Item PLATINUM_HOE = registerItem("platinum_hoe",
            new HoeItem(ModToolMaterial.PLATINUM, -6, 0f, new FabricItemSettings()));

    public static final Item PLATINUM_HELMET = registerItem("platinum_helmet",
            new ArmorItem(ModArmorMaterial.PLATINUM, ArmorItem.Type.HELMET, new FabricItemSettings()));
    public static final Item PLATINUM_CHESTPLATE = registerItem("platinum_chestplate",
            new ArmorItem(ModArmorMaterial.PLATINUM, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()));
    public static final Item PLATINUM_LEGGINGS = registerItem("platinum_leggings",
            new ArmorItem(ModArmorMaterial.PLATINUM, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item PLATINUM_BOOTS = registerItem("platinum_boots",
            new ArmorItem(ModArmorMaterial.PLATINUM, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    // Mithril
    public static final Item MITHRIL_SWORD = registerItem("mithril_sword",
            new SwordItem(ModToolMaterial.MITHRIL, 8, -2.4f, new FabricItemSettings()));
    public static final Item MITHRIL_PICKAXE = registerItem("mithril_pickaxe",
            new PickaxeItem(ModToolMaterial.MITHRIL, 8, -2.8f, new FabricItemSettings()));
    public static final Item MITHRIL_AXE = registerItem("mithril_axe",
            new AxeItem(ModToolMaterial.MITHRIL, 8f, -3f, new FabricItemSettings()));
    public static final Item MITHRIL_SHOVEL = registerItem("mithril_shovel",
            new ShovelItem(ModToolMaterial.MITHRIL, 8f, -2f, new FabricItemSettings()));
    public static final Item MITHRIL_HOE = registerItem("mithril_hoe",
            new HoeItem(ModToolMaterial.MITHRIL, -8, 0f, new FabricItemSettings()));

    public static final Item MITHRIL_HELMET = registerItem("mithril_helmet",
            new ArmorItem(ModArmorMaterial.MITHRIL, ArmorItem.Type.HELMET, new FabricItemSettings()));
    public static final Item MITHRIL_CHESTPLATE = registerItem("mithril_chestplate",
            new ArmorItem(ModArmorMaterial.MITHRIL, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()));
    public static final Item MITHRIL_LEGGINGS = registerItem("mithril_leggings",
            new ArmorItem(ModArmorMaterial.MITHRIL, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item MITHRIL_BOOTS = registerItem("mithril_boots",
            new ArmorItem(ModArmorMaterial.MITHRIL, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    // Adamant
    public static final Item ADAMANT_SWORD = registerItem("adamant_sword",
            new SwordItem(ModToolMaterial.ADAMANT, 9, -2.4f, new FabricItemSettings()));
    public static final Item ADAMANT_PICKAXE = registerItem("adamant_pickaxe",
            new PickaxeItem(ModToolMaterial.ADAMANT, 9, -2.8f, new FabricItemSettings()));
    public static final Item ADAMANT_AXE = registerItem("adamant_axe",
            new AxeItem(ModToolMaterial.ADAMANT, 9f, -3f, new FabricItemSettings()));
    public static final Item ADAMANT_SHOVEL = registerItem("adamant_shovel",
            new ShovelItem(ModToolMaterial.ADAMANT, 9f, -2f, new FabricItemSettings()));
    public static final Item ADAMANT_HOE = registerItem("adamant_hoe",
            new HoeItem(ModToolMaterial.ADAMANT, -9, 0f, new FabricItemSettings()));

    public static final Item ADAMANT_HELMET = registerItem("adamant_helmet",
            new ArmorItem(ModArmorMaterial.ADAMANT, ArmorItem.Type.HELMET, new FabricItemSettings()));
    public static final Item ADAMANT_CHESTPLATE = registerItem("adamant_chestplate",
            new ArmorItem(ModArmorMaterial.ADAMANT, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()));
    public static final Item ADAMANT_LEGGINGS = registerItem("adamant_leggings",
            new ArmorItem(ModArmorMaterial.ADAMANT, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item ADAMANT_BOOTS = registerItem("adamant_boots",
            new ArmorItem(ModArmorMaterial.ADAMANT, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    // Example on how to add to a vanilla item group
    private static void addItemsToCombatItemGroup(FabricItemGroupEntries entries) {
        // Swords
        entries.add(FLINT_SWORD);
        entries.add(BONE_SWORD);
        entries.add(COPPER_SWORD);
        entries.add(GRANITE_SWORD);
        entries.add(OBSIDIAN_SWORD);
        entries.add(RUBY_SWORD);
        entries.add(SAPPHIRE_SWORD);
        entries.add(EMERALD_SWORD);
        entries.add(TOPAZ_SWORD);
        entries.add(STEEL_SWORD);
        entries.add(AMETHYST_SWORD);
        entries.add(PLATINUM_SWORD);
        entries.add(MITHRIL_SWORD);
        entries.add(ADAMANT_SWORD);

        // Axes
        entries.add(FLINT_AXE);
        entries.add(BONE_AXE);
        entries.add(COPPER_AXE);
        entries.add(GRANITE_AXE);
        entries.add(OBSIDIAN_AXE);
        entries.add(RUBY_AXE);
        entries.add(SAPPHIRE_AXE);
        entries.add(EMERALD_AXE);
        entries.add(TOPAZ_AXE);
        entries.add(STEEL_AXE);
        entries.add(AMETHYST_AXE);
        entries.add(PLATINUM_AXE);
        entries.add(MITHRIL_AXE);
        entries.add(ADAMANT_AXE);
    }

    private static void addItemsToToolsItemGroup(FabricItemGroupEntries entries) {
        // Flint Tools
        entries.add(FLINT_SHOVEL);
        entries.add(FLINT_PICKAXE);
        entries.add(FLINT_AXE);
        entries.add(FLINT_HOE);

        // Bone Tools
        entries.add(BONE_SHOVEL);
        entries.add(BONE_PICKAXE);
        entries.add(BONE_AXE);
        entries.add(BONE_HOE);

        // Copper Tools
        entries.add(COPPER_SHOVEL);
        entries.add(COPPER_PICKAXE);
        entries.add(COPPER_AXE);
        entries.add(COPPER_HOE);

        // Granite Tools
        entries.add(GRANITE_SHOVEL);
        entries.add(GRANITE_PICKAXE);
        entries.add(GRANITE_AXE);
        entries.add(GRANITE_HOE);

        // Obsidian Tools
        entries.add(OBSIDIAN_SHOVEL);
        entries.add(OBSIDIAN_PICKAXE);
        entries.add(OBSIDIAN_AXE);
        entries.add(OBSIDIAN_HOE);

        // Ruby Tools
        entries.add(RUBY_SHOVEL);
        entries.add(RUBY_PICKAXE);
        entries.add(RUBY_AXE);
        entries.add(RUBY_HOE);

        // Sapphire Tools
        entries.add(SAPPHIRE_SHOVEL);
        entries.add(SAPPHIRE_PICKAXE);
        entries.add(SAPPHIRE_AXE);
        entries.add(SAPPHIRE_HOE);

        // Emerald Tools
        entries.add(EMERALD_SHOVEL);
        entries.add(EMERALD_PICKAXE);
        entries.add(EMERALD_AXE);
        entries.add(EMERALD_HOE);

        // Topaz Tools
        entries.add(TOPAZ_SHOVEL);
        entries.add(TOPAZ_PICKAXE);
        entries.add(TOPAZ_AXE);
        entries.add(TOPAZ_HOE);

        // Steel Tools
        entries.add(STEEL_SHOVEL);
        entries.add(STEEL_PICKAXE);
        entries.add(STEEL_AXE);
        entries.add(STEEL_HOE);

        // Amethyst Tools
        entries.add(AMETHYST_SHOVEL);
        entries.add(AMETHYST_PICKAXE);
        entries.add(AMETHYST_AXE);
        entries.add(AMETHYST_HOE);

        // Platinum Tools
        entries.add(PLATINUM_SHOVEL);
        entries.add(PLATINUM_PICKAXE);
        entries.add(PLATINUM_AXE);
        entries.add(PLATINUM_HOE);

        // Mithril Tools
        entries.add(MITHRIL_SHOVEL);
        entries.add(MITHRIL_PICKAXE);
        entries.add(MITHRIL_AXE);
        entries.add(MITHRIL_HOE);

        // Adamant Tools
        entries.add(ADAMANT_SHOVEL);
        entries.add(ADAMANT_PICKAXE);
        entries.add(ADAMANT_AXE);
        entries.add(ADAMANT_HOE);
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(EmpiresAndFortresses.MOD_ID, name), item);
    }

    public static void registerItems() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(ModItems::addItemsToToolsItemGroup);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(ModItems::addItemsToCombatItemGroup);
    }

}
