package empireandfortresses.datagen;

import java.util.function.Consumer;

import empireandfortresses.EmpiresAndFortresses;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.advancement.criterion.TickCriterion;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@SuppressWarnings("unused")
public class ModAdvancementProvider extends FabricAdvancementProvider {

    /*
     * For a guide on how to create advancements, see:
     * https://fabricmc.net/wiki/tutorial:datagen_advancements
     * 
     * CAREFUL: Make sure to use MOD_ID:name instead of MOD_ID/name like in the
     * wiki.
     */
    public ModAdvancementProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateAdvancement(Consumer<Advancement> consumer) {
        Advancement rootAdvancement = Advancement.Builder.createUntelemetered()
                .display(Items.STONE,
                        Text.translatable("advancements.empires-and-fortresses.root.title"),
                        Text.translatable("advancements.empires-and-fortresses.root.description"),
                        new Identifier("textures/block/stone.png"), // Background texture
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false)
                .criterion("played", TickCriterion.Conditions.createTick())
                .build(consumer, EmpiresAndFortresses.MOD_ID + ":root");

    }

}
