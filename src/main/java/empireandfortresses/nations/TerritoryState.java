package empireandfortresses.nations;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import empireandfortresses.EmpiresAndFortresses;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;

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
