package empireandfortresses.entity;

import empireandfortresses.EmpiresAndFortresses;
import empireandfortresses.entity.spell.MagicBulletEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<MagicBulletEntity> MAGIC_BULLET = Registry.register(Registries.ENTITY_TYPE,
        new Identifier(EmpiresAndFortresses.MOD_ID, "magic_bullet"),
        FabricEntityTypeBuilder.<MagicBulletEntity>create(SpawnGroup.MISC, MagicBulletEntity::new)
        .dimensions(EntityDimensions.fixed(0.25f, 0.25f)).trackRangeChunks(4).build());

    public static final void registerEntities() {
        
    }
}
