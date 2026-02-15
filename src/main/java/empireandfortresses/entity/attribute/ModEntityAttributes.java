package empireandfortresses.entity.attribute;

import empireandfortresses.EmpiresAndFortresses;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntityAttributes {
	public static final EntityAttribute MAGIC_ATTACK_DAMAGE = Registry.register(
        Registries.ATTRIBUTE, new Identifier(EmpiresAndFortresses.MOD_ID, "magic_attack_damage"), new ClampedEntityAttribute("attribute.name.magic_attack_damage", 0.0, 0.0, 1024.0).setTracked(true)
	);

    public static final EntityAttribute MAGIC_AFFINITY = Registry.register(
        Registries.ATTRIBUTE, new Identifier(EmpiresAndFortresses.MOD_ID, "magic_affinity"), new ClampedEntityAttribute("attribute.name.magic_affinity", 1.0, 0.1, 16.0).setTracked(true)
	);

	public static final EntityAttribute XP_EFFICIENCY = Registry.register(
        Registries.ATTRIBUTE, new Identifier(EmpiresAndFortresses.MOD_ID, "xp_efficiency"), new ClampedEntityAttribute("attribute.name.xp_efficiency", 1.0, -16.0, 16.0).setTracked(true)
	);

    public static void registerAttributes() {

    }
}
