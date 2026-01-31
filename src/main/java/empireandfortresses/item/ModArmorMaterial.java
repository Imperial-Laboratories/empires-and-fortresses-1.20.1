package empireandfortresses.item;

import java.util.function.Supplier;

import empireandfortresses.EmpiresAndFortresses;
import net.minecraft.item.ArmorItem.Type;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public enum ModArmorMaterial implements ArmorMaterial {
    COPPER("copper", 16, new int[] { 2, 4, 3, 1 }, 8, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0f, 0.0f,
            () -> Ingredient.ofItems(Items.COPPER_INGOT)),
    OBSIDIAN("obsidian", 37, new int[] { 3, 6, 5, 2 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, 2.0f, 0.1f,
            () -> Ingredient.ofItems(Items.OBSIDIAN)),
    STEEL("steel", 33, new int[] { 3, 6, 5, 2 }, 12, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1.0f, 0.0f,
            () -> Ingredient.ofItems(ModItems.STEEL_INGOT)),
    AMETHYST("amethyst", 37, new int[] { 3, 6, 5, 2 }, 25, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, 2.0f, 0.0f,
            () -> Ingredient.ofItems(Items.AMETHYST_SHARD)),
    PLATINUM("platinum", 40, new int[] { 4, 7, 6, 3 }, 15, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0f, 0.0f,
            () -> Ingredient.ofItems(ModItems.PLATINUM_INGOT)),
    MITHRIL("mithril", 45, new int[] { 4, 8, 7, 3 }, 22, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, 3.0f, 0.1f,
            () -> Ingredient.ofItems(ModItems.MITHRIL)),
    ADAMANT_CHUNK("adamant_chunk", 50, new int[] { 5, 9, 8, 4 }, 30, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, 4.0f, 0.2f,
            () -> Ingredient.ofItems(ModItems.ADAMANT_CHUNK));

    private final String name;
    private final int durabilityMultiplier;
    private final int[] protectionAmounts;
    private final int enchantability;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;

    private static final int[] BASE_DURABILITY = { 11, 16, 15, 13 };

    @SuppressWarnings("java:S107")
    private ModArmorMaterial(String name, int durabilityMultiplier, int[] protectionAmounts, int enchantability,
            SoundEvent equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionAmounts = protectionAmounts;
        this.enchantability = enchantability;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getDurability(Type type) {
        return BASE_DURABILITY[type.ordinal()] * this.durabilityMultiplier;
    }

    @Override
    public int getProtection(Type type) {
        return protectionAmounts[type.ordinal()];
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.equipSound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override
    public String getName() {
        return EmpiresAndFortresses.MOD_ID + ":" + this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }

}
