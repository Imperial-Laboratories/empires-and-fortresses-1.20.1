package empireandfortresses.item;

import empireandfortresses.EmpiresAndFortresses;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@SuppressWarnings("java:S1186")
public class ModItemGroups {

    public static final ItemGroup TESTING_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(EmpiresAndFortresses.MOD_ID, "ruby"),
            FabricItemGroup.builder().displayName(Text.translatable(EmpiresAndFortresses.MOD_ID + ".item_group.main"))
                    .icon(() -> new ItemStack(Items.STICK)).entries((displayContext, entries) -> {
                        entries.add(ModItems.RUBY);
                        entries.add(ModItems.SAPPHIRE);
                        entries.add(ModItems.TOPAZ);
                        entries.add(ModItems.STEEL_INGOT);
                        entries.add(ModItems.PLATINUM_INGOT);
                        entries.add(ModItems.MITHRIL);
                        entries.add(ModItems.ADAMANT);

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

    public static void registerItemGroups() {

    }

}
