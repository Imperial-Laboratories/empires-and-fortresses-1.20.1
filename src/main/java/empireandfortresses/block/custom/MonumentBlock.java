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
import net.minecraft.world.WorldAccess;

public class MonumentBlock extends Block {
    public MonumentBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        if (world.isClient || !(placer instanceof ServerPlayerEntity player)) {
            return;
        }

        if (world.getRegistryKey() != World.OVERWORLD) {
            player.sendMessage(
                    Text.literal("Monuments can only be placed in the Overworld!").formatted(Formatting.RED));
            world.breakBlock(pos, !player.isCreative());
            return;
        }

        TerritoryState serverState = TerritoryState.getServerState(player.getServer());
        Nation existingNation = serverState.getNationOfPlayer(placer.getUuid());

        if (existingNation != null) {
            if (existingNation.getMonumentPos() != null) {
                player.sendMessage(Text.literal("Your nation already has a monument!").formatted(Formatting.RED));
                world.breakBlock(pos, !player.isCreative());
                return;
            }

            ChunkPos monumentChunkPos = new ChunkPos(pos);
            if (serverState.isAnyChunkClaimedByOthers(monumentChunkPos, 2, existingNation.getId())) {
                player.sendMessage(Text.literal("Cannot move monument here; area is already claimed by another nation!")
                        .formatted(Formatting.RED));
                world.breakBlock(pos, !player.isCreative());
                return;
            }

            existingNation.setMonumentPos(pos);
            player.sendMessage(Text.literal("Monument Placed!").formatted(Formatting.GOLD));
        } else {
            if (serverState.isAnyChunkClaimed(new ChunkPos(pos), 2)) {
                player.sendMessage(Text.literal("Cannot create a nation here; area is already claimed!")
                        .formatted(Formatting.RED));
                world.breakBlock(pos, !player.isCreative());
                return;
            }

            Nation newNation = new Nation(
                    UUID.randomUUID(),
                    placer.getName().getString() + "'s Nation",
                    placer.getUuid(),
                    pos);

            serverState.nations.put(newNation.getId(), newNation);
            existingNation = newNation;
            player.sendMessage(Text.literal("Nation Created!").formatted(Formatting.GOLD));
        }

        serverState.claimArea(existingNation.getId(), pos, 2);
        serverState.markDirty();
    }

    @Override
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        if (world.isClient()) {
            return;
        }

        TerritoryState serverState = TerritoryState.getServerState(world.getServer());
        Nation monumentNation = serverState.nations.values().stream()
                .filter(nation -> pos.equals(nation.getMonumentPos()))
                .findFirst()
                .orElse(null);

        if (monumentNation != null) {
            monumentNation.setMonumentPos(null);
            serverState.markDirty();
        }
    }
}
