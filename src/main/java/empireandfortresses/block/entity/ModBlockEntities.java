package empireandfortresses.block.entity;

import empireandfortresses.EmpiresAndFortresses;
import empireandfortresses.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {

    public static final BlockEntityType<TerritoryMonumentBlockEntity> TERRITORY_MONUMENT_BLOCK_ENTITY =
        Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(EmpiresAndFortresses.MOD_ID, "territory_monument"),
            FabricBlockEntityTypeBuilder.create(TerritoryMonumentBlockEntity::new, ModBlocks.TERRITORY_MONUMENT).build()
    );

    public static void registerBlockEntities() {
        // empty
    }

}
