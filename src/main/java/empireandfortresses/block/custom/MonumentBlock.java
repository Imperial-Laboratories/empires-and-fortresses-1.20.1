package empireandfortresses.block.custom;

import java.util.UUID;

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

public class MonumentBlock extends Block {
    public MonumentBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        if (world.isClient || !(placer instanceof ServerPlayerEntity player)) {
            return;
        }

        TerritoryState serverState = TerritoryState.getServerState(player.getServer());
        Nation existingNation = serverState.getNationOfPlayer(placer.getUuid());

        if (existingNation != null) {
            if (existingNation.getMonumentPos() != null) {
                player.sendMessage(Text.literal("Your nation already has a monument!").formatted(Formatting.RED));
                world.breakBlock(pos, true);
                return;
            }
            existingNation.setMonumentPos(pos);
        } else {
            Nation newNation = new Nation(
                    UUID.randomUUID(),
                    placer.getName().getString() + "'s Nation",
                    placer.getUuid(),
                    pos);

            serverState.nations.put(newNation.getId(), newNation);
            existingNation = newNation;
            player.sendMessage(Text.literal("Nation Created!").formatted(Formatting.GOLD));
        }

        claimArea(world, existingNation.getId(), pos, 1);
        serverState.markDirty();
    }

    private void claimArea(World world, UUID nationId, BlockPos pos, int radius) {
        TerritoryState state = TerritoryState.getServerState(world.getServer());
        ChunkPos center = new ChunkPos(pos);
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                state.claimedChunks.put(new ChunkPos(center.x + x, center.z + z), nationId);
            }
        }
    }

}
