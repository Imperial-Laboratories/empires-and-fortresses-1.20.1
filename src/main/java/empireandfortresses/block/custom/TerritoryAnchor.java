package empireandfortresses.block.custom;

import empireandfortresses.nations.Nation;
import empireandfortresses.nations.TerritoryState;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

public class TerritoryAnchor extends Block {

    public TerritoryAnchor(Settings settings) {
        super(settings);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        if (world.isClient || !(placer instanceof ServerPlayerEntity player)) {
            return;
        }

        TerritoryState serverState = TerritoryState.getServerState(player.getServer());
        Nation existingNation = serverState.getNationOfPlayer(placer.getUuid());

        if (existingNation == null) {
            player.sendMessage(Text.literal("You have to be a part of a Nation to place a Territory Anchor!")
                    .formatted(Formatting.RED));
            world.breakBlock(pos, !player.isCreative());
            return;
        }

        if (serverState.isChunkClaimedBy(new ChunkPos(pos), existingNation.getId())) {
            player.sendMessage(Text.literal("Your nation already owns this chunk!").formatted(Formatting.RED));
            world.breakBlock(pos, !player.isCreative());
            return;
        }

        if (serverState.isAnyChunkClaimedByOthers(new ChunkPos(pos), 1, existingNation.getId())) {
            player.sendMessage(
                    Text.literal("Cannot place a Territory Anchor here; area is already claimed by another nation!")
                            .formatted(Formatting.RED));
            world.breakBlock(pos, !player.isCreative());
            return;
        }

        player.sendMessage(Text.literal("Chunks Claimed!").formatted(Formatting.GOLD));
        serverState.claimArea(world, existingNation.getId(), pos, 1);
        serverState.markDirty();
    }

}
