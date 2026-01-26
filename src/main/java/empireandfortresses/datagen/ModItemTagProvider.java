package empireandfortresses.datagen;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;

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
    }

}
