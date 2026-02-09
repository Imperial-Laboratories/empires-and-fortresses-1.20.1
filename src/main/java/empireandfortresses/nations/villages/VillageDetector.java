package empireandfortresses.nations.villages;

import java.util.Random;
import java.util.UUID;

import empireandfortresses.nations.TerritoryState;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.biome.Biome;

public class VillageDetector {

    private static final Random RANDOM = new Random();

    // TODO: currently based on biome, maybe change
    private static VillageType determineType(ServerWorld world, BlockPos pos) {
        RegistryEntry<Biome> biome = world.getBiome(pos);
        Identifier biomeId = world.getRegistryManager().get(RegistryKeys.BIOME).getId(biome.value());

        if (biomeId == null) {
            return VillageType.PEACEFUL;
        }

        String path = biomeId.getPath();

        if (path.contains("plains")) {
            return VillageType.AGRICULTURAL;
        } else if (path.contains("desert")) {
            return VillageType.MERCANTILE;
        } else if (path.contains("taiga")) {
            return VillageType.MILITANT;
        } else if (path.contains("savanna") || path.contains("badlands")) {
            return VillageType.AUTHORITARIAN;
        } else if (path.contains("snowy")) {
            return VillageType.SCHOLARLY;
        }

        VillageType[] types = VillageType.values();
        return types[RANDOM.nextInt(types.length)];
    }

    public static void tryRegisterVillage(ServerWorld world, ChunkPos chunkPos) {
        TerritoryState state = TerritoryState.getServerState(world.getServer());

        if (state.claimedChunks.containsKey(chunkPos)) {
            return;
        }

        boolean hasVillage = false;
        BlockPos villageCenter = null;

        for (StructureStart start : world.getStructureAccessor().getStructureStarts(chunkPos, s -> true)) {
            Identifier structId = world.getRegistryManager().get(RegistryKeys.STRUCTURE)
                    .getId(start.getStructure());
            if (structId != null && structId.getPath().contains("village")) {
                hasVillage = true;
                villageCenter = start.getBoundingBox().getCenter();
                break;
            }
        }

        if (!hasVillage || villageCenter == null) {
            return;
        }

        VillageType type = determineType(world, villageCenter);
        UUID villageId = UUID.randomUUID();
        // TODO: random name selection based on type
        String villageName = type.leaderTitle + "'s Village";

        VillageNation village = new VillageNation(villageId, villageName, type, villageCenter);
        state.villages.put(villageId, village);

        state.claimArea(villageId, villageCenter, 2);

        state.markDirty();
    }

}
