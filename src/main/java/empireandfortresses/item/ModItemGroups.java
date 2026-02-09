package empireandfortresses.item;

import empireandfortresses.EmpiresAndFortresses;
import empireandfortresses.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@SuppressWarnings("java:S1186")
public class ModItemGroups {

    public static final ItemGroup MAIN_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(EmpiresAndFortresses.MOD_ID, "main"),
            FabricItemGroup.builder().displayName(Text.translatable(EmpiresAndFortresses.MOD_ID + ".item_group.main"))
                    .icon(() -> new ItemStack(ModItems.RUBY)).entries((displayContext, entries) -> {
                        entries.add(ModItems.RUBY);
                        entries.add(ModItems.SAPPHIRE);
                        entries.add(ModItems.TOPAZ);
                        entries.add(ModItems.STEEL_INGOT);
                        entries.add(ModItems.PLATINUM_INGOT);
                        entries.add(ModItems.MITHRIL);
                        entries.add(ModItems.ADAMANT_CHUNK);

                        entries.add(ModBlocks.MONUMENT_BLOCK);
                    }).build());

    public static final ItemGroup TOOLS_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(EmpiresAndFortresses.MOD_ID, "tools"),
            FabricItemGroup.builder().displayName(Text.translatable(EmpiresAndFortresses.MOD_ID + ".item_group.tools"))
                    .icon(() -> new ItemStack(ModItems.COPPER_SWORD)).entries((displayContext, entries) -> {
                        entries.add(ModItems.FLINT_SWORD);
                        entries.add(ModItems.FLINT_PICKAXE);
                        entries.add(ModItems.FLINT_AXE);
                        entries.add(ModItems.FLINT_SHOVEL);
                        entries.add(ModItems.FLINT_HOE);

                        entries.add(ModItems.BONE_SWORD);
                        entries.add(ModItems.BONE_PICKAXE);
                        entries.add(ModItems.BONE_AXE);
                        entries.add(ModItems.BONE_SHOVEL);
                        entries.add(ModItems.BONE_HOE);

                        entries.add(ModItems.COPPER_SWORD);
                        entries.add(ModItems.COPPER_PICKAXE);
                        entries.add(ModItems.COPPER_AXE);
                        entries.add(ModItems.COPPER_SHOVEL);
                        entries.add(ModItems.COPPER_HOE);

                        entries.add(ModItems.GRANITE_SWORD);
                        entries.add(ModItems.GRANITE_PICKAXE);
                        entries.add(ModItems.GRANITE_AXE);
                        entries.add(ModItems.GRANITE_SHOVEL);
                        entries.add(ModItems.GRANITE_HOE);

                        entries.add(ModItems.OBSIDIAN_SWORD);
                        entries.add(ModItems.OBSIDIAN_PICKAXE);
                        entries.add(ModItems.OBSIDIAN_AXE);
                        entries.add(ModItems.OBSIDIAN_SHOVEL);
                        entries.add(ModItems.OBSIDIAN_HOE);

                        entries.add(ModItems.RUBY_SWORD);
                        entries.add(ModItems.RUBY_PICKAXE);
                        entries.add(ModItems.RUBY_AXE);
                        entries.add(ModItems.RUBY_SHOVEL);
                        entries.add(ModItems.RUBY_HOE);

                        entries.add(ModItems.SAPPHIRE_SWORD);
                        entries.add(ModItems.SAPPHIRE_PICKAXE);
                        entries.add(ModItems.SAPPHIRE_AXE);
                        entries.add(ModItems.SAPPHIRE_SHOVEL);
                        entries.add(ModItems.SAPPHIRE_HOE);

                        entries.add(ModItems.EMERALD_SWORD);
                        entries.add(ModItems.EMERALD_PICKAXE);
                        entries.add(ModItems.EMERALD_AXE);
                        entries.add(ModItems.EMERALD_SHOVEL);
                        entries.add(ModItems.EMERALD_HOE);

                        entries.add(ModItems.TOPAZ_SWORD);
                        entries.add(ModItems.TOPAZ_PICKAXE);
                        entries.add(ModItems.TOPAZ_AXE);
                        entries.add(ModItems.TOPAZ_SHOVEL);
                        entries.add(ModItems.TOPAZ_HOE);

                        entries.add(ModItems.STEEL_SWORD);
                        entries.add(ModItems.STEEL_PICKAXE);
                        entries.add(ModItems.STEEL_AXE);
                        entries.add(ModItems.STEEL_SHOVEL);
                        entries.add(ModItems.STEEL_HOE);

                        entries.add(ModItems.AMETHYST_SWORD);
                        entries.add(ModItems.AMETHYST_PICKAXE);
                        entries.add(ModItems.AMETHYST_AXE);
                        entries.add(ModItems.AMETHYST_SHOVEL);
                        entries.add(ModItems.AMETHYST_HOE);
                        entries.add(ModItems.AMETHYST_WAND);

                        entries.add(ModItems.PLATINUM_SWORD);
                        entries.add(ModItems.PLATINUM_PICKAXE);
                        entries.add(ModItems.PLATINUM_AXE);
                        entries.add(ModItems.PLATINUM_SHOVEL);
                        entries.add(ModItems.PLATINUM_HOE);

                        entries.add(ModItems.MITHRIL_SWORD);
                        entries.add(ModItems.MITHRIL_PICKAXE);
                        entries.add(ModItems.MITHRIL_AXE);
                        entries.add(ModItems.MITHRIL_SHOVEL);
                        entries.add(ModItems.MITHRIL_HOE);

                        entries.add(ModItems.ADAMANT_SWORD);
                        entries.add(ModItems.ADAMANT_PICKAXE);
                        entries.add(ModItems.ADAMANT_AXE);
                        entries.add(ModItems.ADAMANT_SHOVEL);
                        entries.add(ModItems.ADAMANT_HOE);
                    }).build());

    public static final ItemGroup ARMOR_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(EmpiresAndFortresses.MOD_ID, "armor"),
            FabricItemGroup.builder().displayName(Text.translatable(EmpiresAndFortresses.MOD_ID + ".item_group.armor"))
                    .icon(() -> new ItemStack(ModItems.OBSIDIAN_CHESTPLATE)).entries((displayContext, entries) -> {
                        entries.add(ModItems.COPPER_HELMET);
                        entries.add(ModItems.COPPER_CHESTPLATE);
                        entries.add(ModItems.COPPER_LEGGINGS);
                        entries.add(ModItems.COPPER_BOOTS);

                        entries.add(ModItems.OBSIDIAN_HELMET);
                        entries.add(ModItems.OBSIDIAN_CHESTPLATE);
                        entries.add(ModItems.OBSIDIAN_LEGGINGS);
                        entries.add(ModItems.OBSIDIAN_BOOTS);

                        entries.add(ModItems.STEEL_HELMET);
                        entries.add(ModItems.STEEL_CHESTPLATE);
                        entries.add(ModItems.STEEL_LEGGINGS);
                        entries.add(ModItems.STEEL_BOOTS);

                        entries.add(ModItems.AMETHYST_HELMET);
                        entries.add(ModItems.AMETHYST_CHESTPLATE);
                        entries.add(ModItems.AMETHYST_LEGGINGS);
                        entries.add(ModItems.AMETHYST_BOOTS);

                        entries.add(ModItems.PLATINUM_HELMET);
                        entries.add(ModItems.PLATINUM_CHESTPLATE);
                        entries.add(ModItems.PLATINUM_LEGGINGS);
                        entries.add(ModItems.PLATINUM_BOOTS);

                        entries.add(ModItems.MITHRIL_HELMET);
                        entries.add(ModItems.MITHRIL_CHESTPLATE);
                        entries.add(ModItems.MITHRIL_LEGGINGS);
                        entries.add(ModItems.MITHRIL_BOOTS);

                        entries.add(ModItems.ADAMANT_HELMET);
                        entries.add(ModItems.ADAMANT_CHESTPLATE);
                        entries.add(ModItems.ADAMANT_LEGGINGS);
                        entries.add(ModItems.ADAMANT_BOOTS);
                    }).build());

    public static void registerItemGroups() {

    }

}
