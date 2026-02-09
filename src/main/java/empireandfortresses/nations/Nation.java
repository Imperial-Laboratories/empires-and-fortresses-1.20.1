package empireandfortresses.nations;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import empireandfortresses.EmpiresAndFortresses;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

@Getter
@Setter
@ToString
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

        NbtCompound levelList = new NbtCompound();
        levels.forEach((type, level) -> levelList.putInt(type.name(), level));
        nbt.put("Levels", levelList);

        NbtCompound memberList = new NbtCompound();
        members.forEach(member -> memberList.putUuid(member.toString(), member));
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

        NbtCompound levelList = nbt.getCompound("Levels");
        levelList.getKeys().forEach(key -> {
            NationLevelType type = NationLevelType.valueOf(key);
            int level = levelList.getInt(key);
            nation.levels.put(type, level);
        });

        NbtCompound memberList = nbt.getCompound("Members");
        memberList.getKeys().forEach(key -> {
            UUID memberId = memberList.getUuid(key);
            nation.members.add(memberId);
        });

        return nation;
    }
}
