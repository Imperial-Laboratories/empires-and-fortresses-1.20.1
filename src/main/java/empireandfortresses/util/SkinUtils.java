package empireandfortresses.util;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftSessionService;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.texture.PlayerSkinProvider;
import net.minecraft.client.util.DefaultSkinHelper;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

public class SkinUtils {

    private static final Cache<UUID, Identifier> SKIN_CACHE = CacheBuilder.newBuilder().expireAfterAccess(10, TimeUnit.MINUTES).maximumSize(256).build();
    private static final Cache<UUID, Boolean> IN_FLIGHT = CacheBuilder.newBuilder().expireAfterWrite(30, TimeUnit.SECONDS).build();

    public static void loadSkinFromUuid(UUID uuid, Consumer<Identifier> callback) {
        if (uuid == null) {
            callback.accept(DefaultSkinHelper.getTexture());
            return;
        }

        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null) {
            callback.accept(DefaultSkinHelper.getTexture());
            return;
        }

        Identifier cached = SKIN_CACHE.getIfPresent(uuid);
        if (cached != null) {
            callback.accept(cached);
            return;
        }

        PlayerListEntry playerOnServer = client.getNetworkHandler().getPlayerListEntry(uuid);
        if (playerOnServer != null) {
            SKIN_CACHE.put(uuid, playerOnServer.getSkinTexture());
            callback.accept(playerOnServer.getSkinTexture());
            return;
        }

        if (IN_FLIGHT.getIfPresent(uuid) != null) {
            callback.accept(DefaultSkinHelper.getTexture());
            return;
        }
        IN_FLIGHT.put(uuid, true);

        PlayerSkinProvider skinProvider = client.getSkinProvider();
        MinecraftSessionService sessionService = client.getSessionService();

        Util.getMainWorkerExecutor().execute(() -> {
            try {
                GameProfile profile = new GameProfile(uuid, null);
                profile = sessionService.fillProfileProperties(profile, false);
                GameProfile finalProfile = profile;

                client.execute(() -> {
                    skinProvider.loadSkin(finalProfile, (type, id, textre) -> {
                        if (type == MinecraftProfileTexture.Type.SKIN) {
                            SKIN_CACHE.put(uuid, id);
                            IN_FLIGHT.invalidate(uuid);
                            callback.accept(id);
                        }
                    }, false);
                });
            } catch (Exception e) {
                IN_FLIGHT.invalidate(uuid);
                callback.accept(DefaultSkinHelper.getTexture());
            }
        });
    }
}
