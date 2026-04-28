package empireandfortresses.util;

import empireandfortresses.EmpiresAndFortresses;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {

    public static class Items {

        public static final TagKey<Item> ENCHANTING_ITEM = createTag("enchanting_item");

        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, new Identifier(EmpiresAndFortresses.MOD_ID, name));
        }

    }

}
