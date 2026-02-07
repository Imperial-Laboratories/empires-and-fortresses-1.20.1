package empireandfortresses.datagen;

import empireandfortresses.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;

@SuppressWarnings("java:S1186")
public class ModLootTableProvider extends FabricBlockLootTableProvider {

    public ModLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.MONUMENT_BLOCK);
        addDrop(ModBlocks.TERRITORY_ANCHOR);
    }

}
