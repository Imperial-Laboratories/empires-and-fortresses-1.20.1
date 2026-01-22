package empireandfortresses.datagen;

import java.util.function.Consumer;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;

@SuppressWarnings("java:S1186")
public class ModRecipeProvider extends FabricRecipeProvider {

    public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
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