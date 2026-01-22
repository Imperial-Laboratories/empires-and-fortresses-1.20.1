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
            FabricItemGroup.builder().displayName(Text.translatable("empires-and-fortresses.item_group.main"))
                    .icon(() -> new ItemStack(Items.STICK)).entries((displayContext, entries) -> {
                        entries.add(ModItems.OBSIDIAN_SWORD);
                        entries.add(ModItems.OBSIDIAN_PICKAXE);
                        entries.add(ModItems.OBSIDIAN_AXE);
                        entries.add(ModItems.OBSIDIAN_SHOVEL);
                        entries.add(ModItems.OBSIDIAN_HOE);
                    }).build());

    public static void registerItemGroups() {

    }

}
