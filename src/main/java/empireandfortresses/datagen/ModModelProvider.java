package empireandfortresses.datagen;

import empireandfortresses.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

@SuppressWarnings("java:S1186")
public class ModModelProvider extends FabricModelProvider {

    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {

        itemModelGenerator.register(ModItems.FLINT_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.FLINT_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.FLINT_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.FLINT_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(ModItems.FLINT_HOE, Models.HANDHELD);

        itemModelGenerator.register(ModItems.BONE_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.BONE_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.BONE_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.BONE_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(ModItems.BONE_HOE, Models.HANDHELD);

        itemModelGenerator.register(ModItems.COPPER_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.COPPER_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.COPPER_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.COPPER_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(ModItems.COPPER_HOE, Models.HANDHELD);

        itemModelGenerator.register(ModItems.GRANITE_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.GRANITE_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.GRANITE_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.GRANITE_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(ModItems.GRANITE_HOE, Models.HANDHELD);

        itemModelGenerator.register(ModItems.OBSIDIAN_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.OBSIDIAN_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.OBSIDIAN_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.OBSIDIAN_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(ModItems.OBSIDIAN_HOE, Models.HANDHELD);

        itemModelGenerator.register(ModItems.RUBY_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.RUBY_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.RUBY_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.RUBY_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(ModItems.RUBY_HOE, Models.HANDHELD);

        itemModelGenerator.register(ModItems.SAPPHIRE_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.SAPPHIRE_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.SAPPHIRE_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.SAPPHIRE_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(ModItems.SAPPHIRE_HOE, Models.HANDHELD);

        itemModelGenerator.register(ModItems.EMERALD_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.EMERALD_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.EMERALD_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.EMERALD_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(ModItems.EMERALD_HOE, Models.HANDHELD);

        itemModelGenerator.register(ModItems.TOPAZ_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.TOPAZ_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.TOPAZ_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.TOPAZ_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(ModItems.TOPAZ_HOE, Models.HANDHELD);

        itemModelGenerator.register(ModItems.STEEL_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.STEEL_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.STEEL_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.STEEL_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(ModItems.STEEL_HOE, Models.HANDHELD);

        itemModelGenerator.register(ModItems.AMETHYST_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.AMETHYST_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.AMETHYST_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.AMETHYST_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(ModItems.AMETHYST_HOE, Models.HANDHELD);

        itemModelGenerator.register(ModItems.PLATINUM_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.PLATINUM_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.PLATINUM_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.PLATINUM_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(ModItems.PLATINUM_HOE, Models.HANDHELD);

        itemModelGenerator.register(ModItems.MITHRIL_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.MITHRIL_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.MITHRIL_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.MITHRIL_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(ModItems.MITHRIL_HOE, Models.HANDHELD);

        itemModelGenerator.register(ModItems.ADAMANT_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.ADAMANT_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.ADAMANT_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.ADAMANT_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(ModItems.ADAMANT_HOE, Models.HANDHELD);
    }

}
