package empireandfortresses;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;

import java.util.UUID;

import org.joml.Vector3f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import empireandfortresses.block.ModBlocks;
import empireandfortresses.command.ModCommands;
import empireandfortresses.item.ModItemGroups;
import empireandfortresses.item.ModItems;
import empireandfortresses.nations.TerritoryState;
import empireandfortresses.util.BorderVisibilityManager;

public class EmpiresAndFortresses implements ModInitializer {
    public static final String MOD_ID = "emp_fort";

    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        ModItemGroups.registerItemGroups();
        ModItems.registerItems();
        ModBlocks.registerBlocks();
        ModCommands.registerCommands();

        // I'll put this here for now
        // Show border visualization particles each tick for players who have it enabled
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

    private void spawnBorderParticles(ServerWorld world, ServerPlayerEntity player, ChunkPos pos, TerritoryState state,
            UUID nationId) {
        int minX = pos.getStartX();
        int minZ = pos.getStartZ();
        int maxX = pos.getEndX();
        int maxZ = pos.getEndZ();
        double y = player.getY() + 1.0;

        ChunkPos[] neighbors = {
                new ChunkPos(pos.x, pos.z - 1), // North
                new ChunkPos(pos.x, pos.z + 1), // South
                new ChunkPos(pos.x - 1, pos.z), // West
                new ChunkPos(pos.x + 1, pos.z) // East
        };

        // North Edge
        if (!nationId.equals(state.claimedChunks.get(neighbors[0]))) {
            for (int i = minX; i <= maxX; i++) {
                spawnParticle(world, player, i + 0.5, y, minZ);
            }
        }
        // South Edge
        if (!nationId.equals(state.claimedChunks.get(neighbors[1]))) {
            for (int i = minX; i <= maxX; i++) {
                spawnParticle(world, player, i + 0.5, y, maxZ + 1.0);
            }
        }
        // West Edge
        if (!nationId.equals(state.claimedChunks.get(neighbors[2]))) {
            for (int i = minZ; i <= maxZ; i++) {
                spawnParticle(world, player, minX, y, i + 0.5);
            }
        }
        // East Edge
        if (!nationId.equals(state.claimedChunks.get(neighbors[3]))) {
            for (int i = minZ; i <= maxZ; i++) {
                spawnParticle(world, player, maxX + 1.0, y, i + 0.5);
            }
        }
    }

    private void spawnParticle(ServerWorld world, ServerPlayerEntity player, double x, double y, double z) {
        world.spawnParticles(player,
                new DustParticleEffect(new Vector3f(1.0f, 0.8f, 0.0f), 1.0f),
                true, x, y, z, 5, 0, 0, 0, 0);
    }
}