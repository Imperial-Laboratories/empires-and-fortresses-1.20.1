package empireandfortresses.screen;

import empireandfortresses.EmpiresAndFortresses;
import empireandfortresses.block.ModBlocks;
import empireandfortresses.nation.Nation;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;

public class TerritoryOverviewScreenHandler extends ScreenHandler {

    private final ScreenHandlerContext context;
    private Nation nation;

    public TerritoryOverviewScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
    }

    public TerritoryOverviewScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
        this.nation = Nation.fromNbt(buf.readNbt());
        EmpiresAndFortresses.LOGGER.info(String.valueOf(this.nation.getMainTerritoryLevels()));
    }

    public TerritoryOverviewScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(ModScreenHandlers.TERRITORY_SCREEN_HANDLER, syncId);
        this.context = context;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    public Nation getNation() {
        // EmpiresAndFortresses.LOGGER.info(String.valueOf(this.nation.getMainTerritoryLevels()));
        return this.nation;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return canUse(this.context, player, ModBlocks.TERRITORY_MONUMENT);
    }

}
