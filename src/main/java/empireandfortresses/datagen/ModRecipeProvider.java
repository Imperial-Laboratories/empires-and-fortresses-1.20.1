package empireandfortresses.datagen;

import java.util.function.Consumer;

import empireandfortresses.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;

@SuppressWarnings({ "java:S1186", "java:S107" })
public class ModRecipeProvider extends FabricRecipeProvider {

    public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        // Flint Tool Recipes
        offerAllToolRecipes(exporter, ModItems.FLINT_SWORD, ModItems.FLINT_PICKAXE, ModItems.FLINT_AXE,
                ModItems.FLINT_SHOVEL, ModItems.FLINT_HOE, Items.FLINT, Items.STICK);

        // Bone Tool Recipes
        offerAllToolRecipes(exporter, ModItems.BONE_SWORD, ModItems.BONE_PICKAXE, ModItems.BONE_AXE,
                ModItems.BONE_SHOVEL, ModItems.BONE_HOE, Items.BONE, Items.STICK);

        // Copper Tool Recipes
        offerAllToolRecipes(exporter, ModItems.COPPER_SWORD, ModItems.COPPER_PICKAXE, ModItems.COPPER_AXE,
                ModItems.COPPER_SHOVEL, ModItems.COPPER_HOE, Items.COPPER_INGOT, Items.STICK);

        // Granite Tool Recipes
        offerAllToolRecipes(exporter, ModItems.GRANITE_SWORD, ModItems.GRANITE_PICKAXE, ModItems.GRANITE_AXE,
                ModItems.GRANITE_SHOVEL, ModItems.GRANITE_HOE, Items.GRANITE, Items.STICK);

        // Obsidian Tool Recipes
        offerAllToolRecipes(exporter, ModItems.OBSIDIAN_SWORD, ModItems.OBSIDIAN_PICKAXE, ModItems.OBSIDIAN_AXE,
                ModItems.OBSIDIAN_SHOVEL, ModItems.OBSIDIAN_HOE, Items.OBSIDIAN, Items.STICK);

        // Ruby Tool Recipes
        offerAllToolRecipes(exporter, ModItems.RUBY_SWORD, ModItems.RUBY_PICKAXE, ModItems.RUBY_AXE,
                ModItems.RUBY_SHOVEL, ModItems.RUBY_HOE, ModItems.RUBY, Items.STICK);

        // Sapphire Tool Recipes
        offerAllToolRecipes(exporter, ModItems.SAPPHIRE_SWORD, ModItems.SAPPHIRE_PICKAXE, ModItems.SAPPHIRE_AXE,
                ModItems.SAPPHIRE_SHOVEL, ModItems.SAPPHIRE_HOE, ModItems.SAPPHIRE, Items.STICK);

        // Emerald Tool Recipes
        offerAllToolRecipes(exporter, ModItems.EMERALD_SWORD, ModItems.EMERALD_PICKAXE, ModItems.EMERALD_AXE,
                ModItems.EMERALD_SHOVEL, ModItems.EMERALD_HOE, Items.EMERALD, Items.STICK);

        // Topaz Tool Recipes
        offerAllToolRecipes(exporter, ModItems.TOPAZ_SWORD, ModItems.TOPAZ_PICKAXE, ModItems.TOPAZ_AXE,
                ModItems.TOPAZ_SHOVEL, ModItems.TOPAZ_HOE, ModItems.TOPAZ, Items.STICK);

        // Steel Tool Recipes
        offerAllToolRecipes(exporter, ModItems.STEEL_SWORD, ModItems.STEEL_PICKAXE, ModItems.STEEL_AXE,
                ModItems.STEEL_SHOVEL, ModItems.STEEL_HOE, ModItems.STEEL_INGOT, Items.STICK);

        // Amethyst Tool Recipes
        offerAllToolRecipes(exporter, ModItems.AMETHYST_SWORD, ModItems.AMETHYST_PICKAXE, ModItems.AMETHYST_AXE,
                ModItems.AMETHYST_SHOVEL, ModItems.AMETHYST_HOE, Items.AMETHYST_SHARD, Items.STICK);

        // Platinum Tool Recipes
        offerAllToolRecipes(exporter, ModItems.PLATINUM_SWORD, ModItems.PLATINUM_PICKAXE, ModItems.PLATINUM_AXE,
                ModItems.PLATINUM_SHOVEL, ModItems.PLATINUM_HOE, ModItems.PLATINUM_INGOT, Items.STICK);

        // Mithril Tool Recipes
        offerAllToolRecipes(exporter, ModItems.MITHRIL_SWORD, ModItems.MITHRIL_PICKAXE, ModItems.MITHRIL_AXE,
                ModItems.MITHRIL_SHOVEL, ModItems.MITHRIL_HOE, ModItems.MITHRIL, Items.STICK);

        // Adamant Tool Recipes
        offerAllToolRecipes(exporter, ModItems.ADAMANT_SWORD, ModItems.ADAMANT_PICKAXE, ModItems.ADAMANT_AXE,
                ModItems.ADAMANT_SHOVEL, ModItems.ADAMANT_HOE, ModItems.ADAMANT, Items.STICK);

    }

    public static void offerAllToolRecipes(Consumer<RecipeJsonProvider> exporter,
            ItemConvertible swordOutput, ItemConvertible pickaxeOutput, ItemConvertible axeOutput,
            ItemConvertible shovelOutput, ItemConvertible hoeOutput, ItemConvertible material,
            ItemConvertible stick) {
        offerSwordRecipes(exporter, RecipeCategory.COMBAT, swordOutput, material, stick);
        offerPickaxeRecipes(exporter, RecipeCategory.TOOLS, pickaxeOutput, material, stick);
        offerAxeRecipes(exporter, RecipeCategory.TOOLS, axeOutput, material, stick);
        offerShovelRecipes(exporter, RecipeCategory.TOOLS, shovelOutput, material, stick);
        offerHoeRecipes(exporter, RecipeCategory.TOOLS, hoeOutput, material, stick);
    }

    public static void offerSwordRecipes(Consumer<RecipeJsonProvider> exporter, RecipeCategory category,
            ItemConvertible output, ItemConvertible material, ItemConvertible stick) {
        ShapedRecipeJsonBuilder.create(category, output)
                .pattern("M")
                .pattern("M")
                .pattern("S")
                .input('M', material)
                .input('S', stick)
                .criterion(hasItem(material), conditionsFromItem(material))
                .criterion(hasItem(stick), conditionsFromItem(stick))
                .offerTo(exporter, new Identifier(getRecipeName(output)));
    }

    public static void offerPickaxeRecipes(Consumer<RecipeJsonProvider> exporter, RecipeCategory category,
            ItemConvertible output, ItemConvertible material, ItemConvertible stick) {
        ShapedRecipeJsonBuilder.create(category, output)
                .pattern("MMM")
                .pattern(" S ")
                .pattern(" S ")
                .input('M', material)
                .input('S', stick)
                .criterion(hasItem(material), conditionsFromItem(material))
                .criterion(hasItem(stick), conditionsFromItem(stick))
                .offerTo(exporter, new Identifier(getRecipeName(output)));
    }

    public static void offerAxeRecipes(Consumer<RecipeJsonProvider> exporter, RecipeCategory category,
            ItemConvertible output, ItemConvertible material, ItemConvertible stick) {
        ShapedRecipeJsonBuilder.create(category, output)
                .pattern("MM")
                .pattern("SM")
                .pattern("S ")
                .input('M', material)
                .input('S', stick)
                .criterion(hasItem(material), conditionsFromItem(material))
                .criterion(hasItem(stick), conditionsFromItem(stick))
                .offerTo(exporter, new Identifier(getRecipeName(output)));
    }

    public static void offerShovelRecipes(Consumer<RecipeJsonProvider> exporter, RecipeCategory category,
            ItemConvertible output, ItemConvertible material, ItemConvertible stick) {
        ShapedRecipeJsonBuilder.create(category, output)
                .pattern("M")
                .pattern("S")
                .pattern("S")
                .input('M', material)
                .input('S', stick)
                .criterion(hasItem(material), conditionsFromItem(material))
                .criterion(hasItem(stick), conditionsFromItem(stick))
                .offerTo(exporter, new Identifier(getRecipeName(output)));
    }

    public static void offerHoeRecipes(Consumer<RecipeJsonProvider> exporter, RecipeCategory category,
            ItemConvertible output, ItemConvertible material, ItemConvertible stick) {
        ShapedRecipeJsonBuilder.create(category, output)
                .pattern("MM")
                .pattern("S ")
                .pattern("S ")
                .input('M', material)
                .input('S', stick)
                .criterion(hasItem(material), conditionsFromItem(material))
                .criterion(hasItem(stick), conditionsFromItem(stick))
                .offerTo(exporter, new Identifier(getRecipeName(output)));
    }

    public static void offerHelmetRecipes(Consumer<RecipeJsonProvider> exporter, RecipeCategory category,
            ItemConvertible output, ItemConvertible material) {
        ShapedRecipeJsonBuilder.create(category, output)
                .pattern("MMM")
                .pattern("M M")
                .input('M', material)
                .criterion(hasItem(material), conditionsFromItem(material))
                .offerTo(exporter, new Identifier(getRecipeName(output)));
    }

    public static void offerChestplateRecipes(Consumer<RecipeJsonProvider> exporter, RecipeCategory category,
            ItemConvertible output, ItemConvertible material) {
        ShapedRecipeJsonBuilder.create(category, output)
                .pattern("M M")
                .pattern("MMM")
                .pattern("MMM")
                .input('M', material)
                .criterion(hasItem(material), conditionsFromItem(material))
                .offerTo(exporter, new Identifier(getRecipeName(output)));
    }

    public static void offerLeggingsRecipes(Consumer<RecipeJsonProvider> exporter, RecipeCategory category,
            ItemConvertible output, ItemConvertible material) {
        ShapedRecipeJsonBuilder.create(category, output)
                .pattern("MMM")
                .pattern("M M")
                .pattern("M M")
                .input('M', material)
                .criterion(hasItem(material), conditionsFromItem(material))
                .offerTo(exporter, new Identifier(getRecipeName(output)));
    }

    public static void offerBootsRecipes(Consumer<RecipeJsonProvider> exporter, RecipeCategory category,
            ItemConvertible output, ItemConvertible material) {
        ShapedRecipeJsonBuilder.create(category, output)
                .pattern("M M")
                .pattern("M M")
                .input('M', material)
                .criterion(hasItem(material), conditionsFromItem(material))
                .offerTo(exporter, new Identifier(getRecipeName(output)));
    }

}