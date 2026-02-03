package empireandfortresses.nations;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import empireandfortresses.EmpiresAndFortresses;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.math.BlockPos;

public class Nation {

    private UUID id;
    private String name;
    private UUID leader;
    private Map<NationLevelType, Integer> levels = new EnumMap<>(NationLevelType.class);
    private List<UUID> members = new ArrayList<>();
    private BlockPos monumentPos;

    public Nation(UUID id, String name, UUID leader, BlockPos monumentPos) {
        this.id = id;
        this.name = name;
        this.leader = leader;
        this.monumentPos = monumentPos;

        for (NationLevelType levelType : NationLevelType.values()) {
            levels.put(levelType, 0);
        }

        this.members.add(leader);
    }

    public NbtCompound toNbt() {
        NbtCompound nbt = new NbtCompound();
        nbt.putUuid("Id", id);
        nbt.putString("Name", name);
        nbt.putUuid("Leader", leader);
        nbt.putLong("MonumentPos", monumentPos.asLong()); // apparently blockpos can be stored as a long??

        NbtList levelList = new NbtList();
        for (Map.Entry<NationLevelType, Integer> entry : levels.entrySet()) {
            NationLevelType levelType = entry.getKey();
            Integer levelValue = entry.getValue();
            NbtCompound levelNbt = new NbtCompound();
            levelNbt.putString("Type", levelType.name());
            levelNbt.putInt("Level", levelValue);
            levelList.add(levelNbt);
        }
        nbt.put("Levels", levelList);

        NbtList memberList = new NbtList();
        for (UUID member : members) {
            NbtCompound m = new NbtCompound();
            m.putUuid("UUID", member);
            memberList.add(m);
        }
        nbt.put("Members", memberList);
        return nbt;
    }

    public static Nation fromNbt(NbtCompound nbt) {
        EmpiresAndFortresses.LOGGER.info("Deserializing Nation from NBT...");

        Nation nation = new Nation(
                nbt.getUuid("Id"),
                nbt.getString("Name"),
                nbt.getUuid("Leader"),
                BlockPos.fromLong(nbt.getLong("MonumentPos")));

        NbtList levelList = nbt.getList("Levels", NbtElement.COMPOUND_TYPE);
        for (int i = 0; i < levelList.size(); i++) {
            NbtCompound levelNbt = levelList.getCompound(i);
            NationLevelType type = NationLevelType.valueOf(levelNbt.getString("Type"));
            int level = levelNbt.getInt("Level");
            nation.levels.put(type, level);
        }

        NbtList memberList = nbt.getList("Members", NbtElement.COMPOUND_TYPE);
        for (int i = 0; i < memberList.size(); i++) {
            UUID member = memberList.getCompound(i).getUuid("UUID");
            if (!nation.members.contains(member)) {
                nation.members.add(member);
            }
        }

        return nation;
    }

    // Getters & Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getLeader() {
        return leader;
    }

    public void setLeader(UUID leader) {
        this.leader = leader;
    }

    public Map<NationLevelType, Integer> getLevels() {
        return levels;
    }

    public void setLevels(Map<NationLevelType, Integer> levels) {
        this.levels = levels;
    }

    public List<UUID> getMembers() {
        return members;
    }

    public void setMembers(List<UUID> members) {
        this.members = members;
    }

    public BlockPos getMonumentPos() {
        return monumentPos;
    }

    public void setMonumentPos(BlockPos monumentPos) {
        this.monumentPos = monumentPos;
    }
}
