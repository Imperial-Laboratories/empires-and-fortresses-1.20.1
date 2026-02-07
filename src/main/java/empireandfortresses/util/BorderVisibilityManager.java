package empireandfortresses.util;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.joml.Vector3f;

import empireandfortresses.nations.TerritoryState;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;

public class BorderVisibilityManager {

    private static final Set<UUID> VISIBLE_PLAYERS = new HashSet<>();

    public static void toggle(UUID playerUuid) {
        if (VISIBLE_PLAYERS.contains(playerUuid)) {
            VISIBLE_PLAYERS.remove(playerUuid);
        } else {
            VISIBLE_PLAYERS.add(playerUuid);
        }
    }

    public static boolean isVisible(UUID playerUuid) {
        return VISIBLE_PLAYERS.contains(playerUuid);
    }

    public static void initialize() {
        ServerTickEvents.END_WORLD_TICK.register(world -> {
            // twice a second
            if (world.getServer().getTicks() % 10 != 0) {
                return;
            }

            TerritoryState state = TerritoryState.getServerState(world.getServer());

            for (ServerPlayerEntity player : world.getPlayers()) {
                if (!BorderVisibilityManager.isVisible(player.getUuid())) {
                    continue;
                }

                ChunkPos playerChunk = new ChunkPos(player.getBlockPos());
                for (int x = -6; x <= 6; x++) {
                    for (int z = -6; z <= 6; z++) {
                        ChunkPos currentChunk = new ChunkPos(playerChunk.x + x, playerChunk.z + z);
                        UUID nationId = state.claimedChunks.get(currentChunk);

                        if (nationId != null) {
                            spawnBorderParticles(world, player, currentChunk, state, nationId);
                        }
                    }
                }
            }
        });
    }

    private static void spawnBorderParticles(ServerWorld world, ServerPlayerEntity player, ChunkPos pos,
            TerritoryState state,
            UUID nationId) {
        int minX = pos.getStartX();
        int minZ = pos.getStartZ();
        int maxX = pos.getEndX();
        int maxZ = pos.getEndZ();
        double y = player.getY();

        ChunkPos[] neighbors = {
                new ChunkPos(pos.x, pos.z - 1), // North
                new ChunkPos(pos.x, pos.z + 1), // South
                new ChunkPos(pos.x - 1, pos.z), // West
                new ChunkPos(pos.x + 1, pos.z) // East
        };

        // Get a color from the nation id
        Vector3f color = new Vector3f(
                ((nationId.getMostSignificantBits() >> 16) & 0xFF) / 255.0f,
                ((nationId.getMostSignificantBits() >> 8) & 0xFF) / 255.0f,
                (nationId.getMostSignificantBits() & 0xFF) / 255.0f);

        // North Edge
        if (!nationId.equals(state.claimedChunks.get(neighbors[0]))) {
            for (int i = minX; i <= maxX; i++) {
                spawnParticle(world, player, i + 0.5, y, minZ, color);
            }
        }
        // South Edge
        if (!nationId.equals(state.claimedChunks.get(neighbors[1]))) {
            for (int i = minX; i <= maxX; i++) {
                spawnParticle(world, player, i + 0.5, y, maxZ + 1.0, color);
            }
        }
        // West Edge
        if (!nationId.equals(state.claimedChunks.get(neighbors[2]))) {
            for (int i = minZ; i <= maxZ; i++) {
                spawnParticle(world, player, minX, y, i + 0.5, color);
            }
        }
        // East Edge
        if (!nationId.equals(state.claimedChunks.get(neighbors[3]))) {
            for (int i = minZ; i <= maxZ; i++) {
                spawnParticle(world, player, maxX + 1.0, y, i + 0.5, color);
            }
        }
    }

    private static void spawnParticle(ServerWorld world, ServerPlayerEntity player, double x, double y, double z,
            Vector3f color) {
        world.spawnParticles(player,
                new DustParticleEffect(color, 1.0f),
                true, x, y, z, 5, 0, 0, 0, 0);
    }

}
