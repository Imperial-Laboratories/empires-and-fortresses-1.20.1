package empireandfortresses.enchantment;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import empireandfortresses.EmpiresAndFortresses;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

@SuppressWarnings("java:S2386")
public class EnchantingItemsLoader implements SimpleSynchronousResourceReloadListener {

    public static final Map<Item, List<Enchantment>> ENCHANTING_ITEM_MAP = new HashMap<>();

    @Override
    public Identifier getFabricId() {
        return new Identifier(EmpiresAndFortresses.MOD_ID, "enchanting_items");
    }

    @Override
    public void reload(ResourceManager manager) {
        ENCHANTING_ITEM_MAP.clear();

        Map<Identifier, Resource> resources = manager.findResources("enchanting_items",
                path -> path.getPath().endsWith(".json"));
        for (var entry : resources.entrySet()) {
            try (InputStreamReader reader = new InputStreamReader(entry.getValue().getInputStream())) {
                JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();

                Identifier itemId = Identifier.tryParse(json.get("item").getAsString());
                Item item = Registries.ITEM.get(itemId);
                if (item == null) {
                    EmpiresAndFortresses.LOGGER.warn("Unknown item {} in {}", itemId, entry.getKey());
                    continue;
                }

                List<Enchantment> enchantments = new ArrayList<>();
                json.getAsJsonArray("enchantments").forEach(element -> {
                    Identifier enchantmentId = Identifier.tryParse(element.getAsString());
                    Enchantment enchantment = Registries.ENCHANTMENT.get(enchantmentId);
                    if (enchantment != null) {
                        enchantments.add(enchantment);
                    } else {
                        EmpiresAndFortresses.LOGGER.warn("Unknown enchantment {} in {}", enchantmentId, entry.getKey());
                    }
                });

                ENCHANTING_ITEM_MAP.put(item, enchantments);
            } catch (Exception e) {
                EmpiresAndFortresses.LOGGER.error("Failed to load enchanting item {}", entry.getKey(), e);
            }
        }
    }

}
