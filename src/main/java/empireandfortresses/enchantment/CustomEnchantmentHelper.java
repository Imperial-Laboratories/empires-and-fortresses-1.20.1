package empireandfortresses.enchantment;

import java.util.stream.IntStream;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;

public class CustomEnchantmentHelper {

	public static int getNextLevel(ItemStack stack, Enchantment enchantment) {
		NbtList enchantments = stack.getEnchantments();

		return containsEnchantment(stack, enchantment) ? enchantments.getCompound(getEnchantmentIndex(stack, enchantment)).getInt("lvl") + 1 : 1;
	}

    public static int getEnchantmentIndex(ItemStack stack, Enchantment enchantment) {
		NbtList enchantments = stack.getEnchantments();

        return IntStream.range(0, enchantments.size())
			.filter(l -> enchantments.getCompound(l).getString("id")
			.equals(Registries.ENCHANTMENT.getId(enchantment).toString()))
			.findFirst().orElse(-1);
    }

    public static boolean containsEnchantment(ItemStack stack, Enchantment enchantment) {
        NbtList enchantments = stack.getEnchantments();

        return IntStream.range(0, enchantments.size())
			.anyMatch(l -> enchantments.getCompound(l).getString("id")
			.equals(Registries.ENCHANTMENT.getId(enchantment).toString()));
    }

    public static int materialCost(int lvl) {
            return (int) Math.pow(2, lvl - 1);
    }

    public static int materialCost(int lvl1, int lvl2) {
        int cost = 0;
        for (int i = lvl1; i <= lvl2; i++) {
            cost += Math.pow(2, i - 1);
        }

        if (lvl1 == 1) {
            return cost;
        } else {
            return cost - materialCost(lvl1);
        }
    }
}
