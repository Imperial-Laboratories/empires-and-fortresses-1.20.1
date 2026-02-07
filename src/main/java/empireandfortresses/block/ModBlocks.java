package empireandfortresses.block;

import empireandfortresses.EmpiresAndFortresses;
import empireandfortresses.block.custom.*;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

@SuppressWarnings("java:S1186")
public class ModBlocks {

    public static final Block MONUMENT_BLOCK = registerBlock("monument_block",
            new MonumentBlock(
                    FabricBlockSettings.copyOf(Blocks.GOLD_BLOCK).strength(5.0f).requiresTool()));
    public static final Block TERRITORY_ANCHOR = registerBlock("territory_anchor",
            new TerritoryAnchor(
                    FabricBlockSettings.copyOf(Blocks.GOLD_BLOCK).strength(5.0f).requiresTool()));

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(EmpiresAndFortresses.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, new Identifier(EmpiresAndFortresses.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerBlocks() {

    }

}
