package empireandfortresses.mixin;

import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import com.llamalad7.mixinextras.sugar.Local;

import empireandfortresses.nation.Nation;
import empireandfortresses.nation.TerritoryLevelType;
import empireandfortresses.nation.TerritoryState;
import empireandfortresses.nation.village.VillageNation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.util.math.ChunkPos;

@Mixin(ServerChunkManager.class)
public class ServerChunkManagerMixin {

    @ModifyVariable(at = @At("LOAD"), method = "tickChunks()V", ordinal = 2)
    private boolean controlMobSpawn(boolean bl3, @Local(ordinal = 0) ChunkPos chunkPos) {
        //* accesses the mixin-ed instance
        MinecraftServer server = ((ServerChunkManager) (Object) this).getWorld().getServer();
        TerritoryState state = TerritoryState.getServerState(server);

        // TODO: disable mob spawns not completely
        // TODO: separate hostile and other mob spawn control
        return bl3 && !state.isChunkClaimed(chunkPos);
    }

    @ModifyArg(
        at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;tickChunk(Lnet/minecraft/world/chunk/WorldChunk;I)V"),
        method = "tickChunks()V",
        index = 1
    )
    private int controlRandomTick(int i, @Local ChunkPos chunkPos) {
        MinecraftServer server = ((ServerChunkManager) (Object) this).getWorld().getServer();
        TerritoryState state = TerritoryState.getServerState(server);

        if (state.isChunkClaimed(chunkPos)) {
            UUID nationId = state.claimedChunks.get(chunkPos);
            Nation nation = state.nations.get(nationId);

            // TODO: Give a formula that is not made up in 3 seconds
            // Crop growth speed multiplier = 1 + (Level * 0.1)
            // +10% per level
            if (nation != null) {
                return (int) (i * (1 + nation.getMainTerritoryLevels().get(TerritoryLevelType.CROP_GROWTH_SPEED) * 0.1));
            } else {
                VillageNation village = state.villages.get(nationId);
                return (int) (i * village.getType().cropGrowthMultiplier);
            }

        } else {
            return i;
        }
    }
}
