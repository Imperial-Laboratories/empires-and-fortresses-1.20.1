package empireandfortresses.screen;

import empireandfortresses.EmpiresAndFortresses;
import empireandfortresses.nation.Nation;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class TerritoryOverviewScreen extends HandledScreen<TerritoryOverviewScreenHandler> {

    private static final Identifier TEXTURE = new Identifier(EmpiresAndFortresses.MOD_ID, "textures/gui/container/territory_monument/0_overview.png");

    public TerritoryOverviewScreen(TerritoryOverviewScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.playerInventoryTitleY = this.backgroundHeight * 2;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;

        context.drawTexture(TEXTURE, i, j, 0, 0, 176, 166);

        Nation nation = this.handler.getNation();

        // * Levels
        context.drawText(textRenderer, Text.literal("Lvl: " + String.valueOf(nation.getLevels().values().stream().mapToInt(Integer::intValue).sum())), i + 94, j + 22, 0x555555, false);
        context.drawText(textRenderer, Text.literal("Nation Lvl: "  + String.valueOf(nation.getMainTerritoryLevels().values().stream().mapToInt(Integer::intValue).sum())), i + 94, j + 39, 0x555555, false);

        // * Buttons
        // add member & stat visibility
        context.drawTexture(TEXTURE, i + 77, j + 19, 0, 166, 13, 13);
        context.drawTexture(TEXTURE, i + 77, j + 36, 26, 166, 13, 13);
        // highlight
        if (mouseX > i + 77 && mouseX <= i + 90) {
            if (mouseY > j + 19 && mouseY <= j + 32) {
                context.drawTexture(TEXTURE, i + 77, j + 19, 13, 166, 13, 13);
            } else if (mouseY > j + 36 && mouseY <= j + 49) {
                context.drawTexture(TEXTURE, i + 77, j + 36, 39, 166, 13, 13);
            }
        }

        // * Members

        // * Relations

        // * Scroll Bars

    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }

}
