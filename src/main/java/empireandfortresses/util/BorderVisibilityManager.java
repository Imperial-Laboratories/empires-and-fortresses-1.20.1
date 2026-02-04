package empireandfortresses.util;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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

}
