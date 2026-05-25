package empireandfortresses.block.custom;

import java.util.UUID;

import empireandfortresses.block.entity.TerritoryMonumentBlockEntity;
import empireandfortresses.nation.Nation;
import empireandfortresses.nation.TerritoryState;
import empireandfortresses.screen.TerritoryOverviewScreenHandler;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class TerritoryMonumentBlock extends BlockWithEntity {
    public TerritoryMonumentBlock(Settings settings) {
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
                player.sendMessage(Text.literal("Your Nation already has a Monument!").formatted(Formatting.RED));
                world.breakBlock(pos, !player.isCreative());
                return;
            }

            ChunkPos monumentChunkPos = new ChunkPos(pos);
            if (serverState.isAnyChunkClaimedByOthers(monumentChunkPos, 2, existingNation.getId())) {
                player.sendMessage(Text.literal("Cannot move mMnument here; area is already claimed by another Nation!")
                        .formatted(Formatting.RED));
                world.breakBlock(pos, !player.isCreative());
                return;
            }

            existingNation.setMonumentPos(pos);
            player.sendMessage(Text.literal("Monument Placed!").formatted(Formatting.GOLD));
        } else {
            if (serverState.isAnyChunkClaimed(new ChunkPos(pos), 2)) {
                player.sendMessage(Text.literal("Cannot create a Nation here; area is already claimed!")
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

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
            BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        } else {
            player.openHandledScreen((TerritoryMonumentBlockEntity) world.getBlockEntity(pos));
            return ActionResult.CONSUME;
        }
    }

    @Override
    public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        TerritoryState serverState = TerritoryState.getServerState(world.getServer());
        Nation monumentNation = serverState.nations.values().stream()
                .filter(nation -> pos.equals(nation.getMonumentPos()))
                .findFirst()
                .orElse(null);

		return new SimpleNamedScreenHandlerFactory(
			(syncId, inventory, player) -> new TerritoryOverviewScreenHandler(syncId, inventory, ScreenHandlerContext.create(world, pos)), Text.literal(monumentNation.getName()).formatted(Formatting.DARK_GRAY)
		);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new TerritoryMonumentBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
