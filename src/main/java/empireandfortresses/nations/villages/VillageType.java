package empireandfortresses.nations.villages;

import net.minecraft.util.Formatting;

public enum VillageType {
    MILITANT("militant", "Warlord", Formatting.RED,
            0.2f, 1.5f, 0.5f),
    AGRICULTURAL("agricultural", "Mayor", Formatting.GREEN,
            1.5f, 0.5f, 1.0f),
    MERCANTILE("mercantile", "Merchant Prince", Formatting.GOLD,
            0.8f, 0.8f, 1.8f),
    SCHOLARLY("scholarly", "Elder", Formatting.AQUA,
            0.6f, 0.6f, 1.0f),
    AUTHORITARIAN("authoritarian", "Dictator", Formatting.DARK_RED,
            0.5f, 1.2f, 1.2f),
    PEACEFUL("peaceful", "Council Speaker", Formatting.WHITE,
            1.2f, 0.3f, 0.8f);

    public final String id;
    public final String leaderTitle;
    public final Formatting color;

    // TODO: add more of these? change these? idk
    public final float cropGrowthMultiplier;
    public final float combatStrengthMultiplier;
    public final float tradeGenerosityMultiplier;

    VillageType(String id, String leaderTitle, Formatting color, float cropGrowth, float combatStrength,
            float tradeGenerosity) {
        this.id = id;
        this.leaderTitle = leaderTitle;
        this.color = color;
        this.cropGrowthMultiplier = cropGrowth;
        this.combatStrengthMultiplier = combatStrength;
        this.tradeGenerosityMultiplier = tradeGenerosity;
    }

    public static VillageType fromId(String id) {
        for (VillageType type : values()) {
            if (type.id.equals(id)) {
                return type;
            }
        }
        return PEACEFUL;
    }
}
