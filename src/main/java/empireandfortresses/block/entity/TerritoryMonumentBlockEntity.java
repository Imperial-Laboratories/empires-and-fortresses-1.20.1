package empireandfortresses.block.entity;

import empireandfortresses.EmpiresAndFortresses;
import empireandfortresses.nation.Nation;
import empireandfortresses.nation.TerritoryState;
import empireandfortresses.screen.TerritoryOverviewScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class TerritoryMonumentBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory {

    public TerritoryMonumentBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TERRITORY_MONUMENT_BLOCK_ENTITY, pos, state);
    }

    public Nation getNation() {
        TerritoryState serverState = TerritoryState.getServerState(world.getServer());
        Nation monumentNation = serverState.nations.values().stream()
            .filter(nation -> pos.equals(nation.getMonumentPos()))
            .findFirst()
            .orElse(null);
        return monumentNation;
    }

    @Override
    public Text getDisplayName() {
        return Text.literal(getCachedState().getBlock().getTranslationKey());
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new TerritoryOverviewScreenHandler(syncId, playerInventory);
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        EmpiresAndFortresses.LOGGER.info(String.valueOf(this.getNation()));
        buf.writeNbt(this.getNation().toNbt());
    }

}
