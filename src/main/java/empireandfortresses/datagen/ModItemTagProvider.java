package empireandfortresses.datagen;

import java.util.concurrent.CompletableFuture;

import empireandfortresses.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.registry.tag.ItemTags;

@SuppressWarnings("java:S1186")
public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {

    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    @SuppressWarnings("java:S125")
    public void configure(WrapperLookup arg) {
        // Use this method to add items/blocks to tags
        // getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add();

        getOrCreateTagBuilder(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.COPPER_HELMET, ModItems.COPPER_CHESTPLATE, ModItems.COPPER_LEGGINGS,
                        ModItems.COPPER_BOOTS,
                        ModItems.OBSIDIAN_HELMET, ModItems.OBSIDIAN_CHESTPLATE, ModItems.OBSIDIAN_LEGGINGS,
                        ModItems.OBSIDIAN_BOOTS,
                        ModItems.STEEL_HELMET, ModItems.STEEL_CHESTPLATE, ModItems.STEEL_LEGGINGS,
                        ModItems.STEEL_BOOTS,
                        ModItems.AMETHYST_HELMET, ModItems.AMETHYST_CHESTPLATE, ModItems.AMETHYST_LEGGINGS,
                        ModItems.AMETHYST_BOOTS,
                        ModItems.PLATINUM_HELMET, ModItems.PLATINUM_CHESTPLATE, ModItems.PLATINUM_LEGGINGS,
                        ModItems.PLATINUM_BOOTS,
                        ModItems.MITHRIL_HELMET, ModItems.MITHRIL_CHESTPLATE, ModItems.MITHRIL_LEGGINGS,
                        ModItems.MITHRIL_BOOTS,
                        ModItems.ADAMANT_HELMET, ModItems.ADAMANT_CHESTPLATE, ModItems.ADAMANT_LEGGINGS,
                        ModItems.ADAMANT_BOOTS);
    }

}
