package empireandfortresses.nations.villages;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

@Getter
@Setter
@ToString
public class VillageNation {

    private UUID id;
    private String name;
    private VillageType type;
    private BlockPos monumentPos;
    private UUID leaderEntityUuid;

    private UUID ownerNationId = null; // nation that "owns" this village
    private boolean capturedByForce = false;

    private Map<UUID, Integer> reputation = new HashMap<>();

    private float tolerance;
    private float wealth;
    private float militaryPower;

    public VillageNation(UUID id, String name, VillageType type, BlockPos monumentPos) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.monumentPos = monumentPos;

        // TODO: pretty simple right now, maybe more complex and a bit of randomness
        this.tolerance = type == VillageType.PEACEFUL ? 0.8f : type == VillageType.AUTHORITARIAN ? 0.2f : 0.5f;
        this.wealth = type == VillageType.MERCANTILE ? 0.9f : 0.5f;
        this.militaryPower = type == VillageType.MILITANT ? 0.9f : type == VillageType.SCHOLARLY ? 0.2f : 0.5f;
    }

    public int getReputation(UUID nationId) {
        return reputation.getOrDefault(nationId, 0);
    }

    public void modifyReputation(UUID nationId, int amount) {
        int current = getReputation(nationId);

        // higher tolerance == negative actions have less impact
        // TODO: maybe change the formula? this one was made in like 2 seconds
        if (amount < 0) {
            amount = Math.round(amount * (1.0f - tolerance * 0.5f));
        }
        reputation.put(nationId, Math.max(-100, Math.min(100, current + amount)));
    }

    public boolean isIndependent() {
        return ownerNationId == null;
    }

    // Better reputation = lower chance
    // TODO: probably also adjust it, this is really basic right now
    public float getRebellionChance() {
        if (ownerNationId == null) {
            return 0;
        }
        int rep = getReputation(ownerNationId);
        float base = capturedByForce ? 0.3f : 0.05f;
        return Math.max(0, base - (rep / 200f));
    }

    public NbtCompound toNbt() {
        NbtCompound nbt = new NbtCompound();
        nbt.putUuid("Id", id);
        nbt.putString("Name", name);
        nbt.putString("Type", type.id);
        nbt.putLong("MonumentPos", monumentPos.asLong());
        nbt.putFloat("Tolerance", tolerance);
        nbt.putFloat("Wealth", wealth);
        nbt.putFloat("MilitaryPower", militaryPower);
        nbt.putBoolean("CapturedByForce", capturedByForce);

        if (leaderEntityUuid != null) {
            nbt.putUuid("LeaderEntity", leaderEntityUuid);
        }
        if (ownerNationId != null) {
            nbt.putUuid("OwnerNation", ownerNationId);
        }

        NbtCompound repNbt = new NbtCompound();
        reputation.forEach((nationId, rep) -> repNbt.putInt(nationId.toString(), rep));
        nbt.put("Reputation", repNbt);

        return nbt;
    }

    public static VillageNation fromNbt(NbtCompound nbt) {
        VillageNation village = new VillageNation(
                nbt.getUuid("Id"),
                nbt.getString("Name"),
                VillageType.fromId(nbt.getString("Type")),
                BlockPos.fromLong(nbt.getLong("MonumentPos")));
        village.tolerance = nbt.getFloat("Tolerance");
        village.wealth = nbt.getFloat("Wealth");
        village.militaryPower = nbt.getFloat("MilitaryPower");
        village.capturedByForce = nbt.getBoolean("CapturedByForce");

        if (nbt.contains("LeaderEntity")) {
            village.leaderEntityUuid = nbt.getUuid("LeaderEntity");
        }
        if (nbt.contains("OwnerNation")) {
            village.ownerNationId = nbt.getUuid("OwnerNation");
        }

        NbtCompound repNbt = nbt.getCompound("Reputation");
        repNbt.getKeys().forEach(key -> {
            UUID nationId = UUID.fromString(key);
            int rep = repNbt.getInt(key);
            village.reputation.put(nationId, rep);
        });
        return village;
    }
}
