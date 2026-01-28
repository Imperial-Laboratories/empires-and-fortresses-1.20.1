package empireandfortresses.item;

import java.util.function.Supplier;

import net.fabricmc.yarn.constants.MiningLevels;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

@SuppressWarnings("java:S1144")
public enum ModToolMaterial implements ToolMaterial {
    // TODO: we probably want custom mining levels later, also, enchantability
    // TODO: values are currently placeholders.
    // TODO: Some values also need to be tweaked
    FLINT(MiningLevels.WOOD, 45, 2, 1, 66, () -> Ingredient.ofItems(Items.FLINT)),
    BONE(MiningLevels.WOOD, 32, 6f, 0, 66, () -> Ingredient.ofItems(Items.BONE)),
    COPPER(MiningLevels.STONE, 190, 5f, 1f, 13, () -> Ingredient.ofItems(Items.COPPER_INGOT)),
    GRANITE(MiningLevels.IRON, 500, 4f, 2f, 10, () -> Ingredient.ofItems(Items.GRANITE)), // TODO: tweak values
    OBSIDIAN(MiningLevels.IRON, 666, 7, 1, 66, () -> Ingredient.ofItems(Items.OBSIDIAN)), // TODO: tweak values
    RUBY(MiningLevels.DIAMOND, 1500, 8f, 3f, 22, () -> Ingredient.ofItems(ModItems.RUBY)), // TODO: tweak values
    SAPPHIRE(MiningLevels.DIAMOND, 1500, 8f, 3f, 22, () -> Ingredient.ofItems(ModItems.SAPPHIRE)), // TODO: tweak values
    EMERALD(MiningLevels.DIAMOND, 2031, 8f, 3f, 25, () -> Ingredient.ofItems(Items.EMERALD)), // TODO: tweak values
    TOPAZ(MiningLevels.DIAMOND, 1500, 8f, 3f, 22, () -> Ingredient.ofItems(ModItems.TOPAZ)), // TODO: tweak values
    STEEL(MiningLevels.DIAMOND, 750, 9f, 4f, 18, () -> Ingredient.ofItems(ModItems.STEEL_INGOT)), // TODO: tweak values
    AMETHYST(MiningLevels.NETHERITE, 2031, 9f, 4f, 30, () -> Ingredient.ofItems(Items.AMETHYST_SHARD)), // TODO: tweak
                                                                                                        // values
    PLATINUM(MiningLevels.NETHERITE, 1200, 10f, 4f, 22, () -> Ingredient.ofItems(ModItems.PLATINUM_INGOT)), // TODO:
                                                                                                            // tweak
                                                                                                            // values
    MITHRIL(MiningLevels.NETHERITE, 1800, 12f, 5f, 30, () -> Ingredient.ofItems(ModItems.MITHRIL)), // TODO: tweak
                                                                                                    // values
    ADAMANT(MiningLevels.NETHERITE, 2500, 14f, 6f, 35, () -> Ingredient.ofItems(ModItems.ADAMANT)); // TODO: tweak
                                                                                                    // values

    private final int miningLevel;
    private final int itemDurability;
    private final float miningSpeed;
    private final float attackDamage;
    private final int enchantability;
    private final Supplier<Ingredient> repairIngredient;

    private ModToolMaterial(int miningLevel, int itemDurability, float miningSpeed, float attackDamage,
            int enchantability, Supplier<Ingredient> repairIngredient) {
        this.miningLevel = miningLevel;
        this.itemDurability = itemDurability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getDurability() {
        return this.itemDurability;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return this.miningSpeed;
    }

    @Override
    public float getAttackDamage() {
        return this.attackDamage;
    }

    @Override
    public int getMiningLevel() {
        return this.miningLevel;
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
}
