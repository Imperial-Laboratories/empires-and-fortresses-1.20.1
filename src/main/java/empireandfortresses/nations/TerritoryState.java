package empireandfortresses.nations;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import empireandfortresses.EmpiresAndFortresses;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

public class TerritoryState extends PersistentState {

    public final Map<ChunkPos, UUID> claimedChunks = new HashMap<>();
    public final Map<UUID, Nation> nations = new HashMap<>();

    public Nation getNationOfPlayer(UUID playerUuid) {
        return nations.values().stream().filter(n -> n.getMembers().contains(playerUuid)).findFirst().orElse(null);
    }

    public static TerritoryState getServerState(MinecraftServer server) {
        PersistentStateManager psm = server.getOverworld().getPersistentStateManager();
        return psm.getOrCreate(TerritoryState::readNbt, TerritoryState::new, "territories");
    }

    public boolean isChunkClaimed(ChunkPos chunkPos) {
        return claimedChunks.containsKey(chunkPos);
    }

    public boolean isChunkClaimedBy(ChunkPos chunkPos, UUID nationId) {
        return Objects.equals(claimedChunks.get(chunkPos), nationId);
    }

    public boolean isAnyChunkClaimed(ChunkPos center, int radius) {
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                ChunkPos checkPos = new ChunkPos(center.x + dx, center.z + dz);
                if (claimedChunks.containsKey(checkPos)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isAnyChunkClaimedByOthers(ChunkPos center, int radius, UUID nationId) {
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                ChunkPos checkPos = new ChunkPos(center.x + dx, center.z + dz);
                if (claimedChunks.containsKey(checkPos)) {
                    UUID claimingNationId = claimedChunks.get(checkPos);
                    if (!claimingNationId.equals(nationId)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void claimArea(World world, UUID nationId, BlockPos pos, int radius) {
        TerritoryState state = TerritoryState.getServerState(world.getServer());
        ChunkPos center = new ChunkPos(pos);
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                if (state.isChunkClaimedBy(new ChunkPos(center.x + x, center.z + z), nationId)) {
                    continue;
                }
                state.claimedChunks.put(new ChunkPos(center.x + x, center.z + z), nationId);
            }
        }
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        NbtList nationList = new NbtList();
        nations.values().forEach(nation -> nationList.add(nation.toNbt()));
        nbt.put("Nations", nationList);

        NbtList claimList = new NbtList();
        claimedChunks.forEach((chunkPos, nationId) -> {
            NbtCompound claimNbt = new NbtCompound();
            claimNbt.putLong("Pos", chunkPos.toLong());
            claimNbt.putUuid("NationId", nationId);
            claimList.add(claimNbt);
        });
        nbt.put("Claims", claimList);

        return nbt;
    }

    public static TerritoryState readNbt(NbtCompound nbt) {
        EmpiresAndFortresses.LOGGER.info("Loading TerritoryState from NBT...");

        TerritoryState state = new TerritoryState();

        NbtList nationList = nbt.getList("Nations", NbtElement.COMPOUND_TYPE);
        for (int i = 0; i < nationList.size(); i++) {
            Nation nation = Nation.fromNbt(nationList.getCompound(i));
            EmpiresAndFortresses.LOGGER.info("Loaded nation: {}", nation);
            state.nations.put(nation.getId(), nation);
        }

        NbtList claimList = nbt.getList("Claims", NbtElement.COMPOUND_TYPE);
        for (int i = 0; i < claimList.size(); i++) {
            NbtCompound claimNbt = claimList.getCompound(i);
            ChunkPos chunkPos = new ChunkPos(claimNbt.getLong("Pos"));
            UUID nationId = claimNbt.getUuid("NationId");
            state.claimedChunks.put(chunkPos, nationId);
        }

        EmpiresAndFortresses.LOGGER.info("Loaded {} nations and {} claimed chunks", state.nations.size(),
                state.claimedChunks.size());

        return state;
    }

}
