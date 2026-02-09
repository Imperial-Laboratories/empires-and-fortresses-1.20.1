package empireandfortresses.entity.attribute;

import empireandfortresses.EmpiresAndFortresses;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntityAttributes {
	public static final EntityAttribute GENERIC_MAGIC_ATTACK_DAMAGE = Registry.register(
        Registries.ATTRIBUTE, new Identifier(EmpiresAndFortresses.MOD_ID, "generic.magic_attack_damage"), new ClampedEntityAttribute("attribute.name.generic.magic_attack_damage", 0.0, 0.0, 1024.0).setTracked(true)
	);

    public static void registerAttributes() {

    }
}
