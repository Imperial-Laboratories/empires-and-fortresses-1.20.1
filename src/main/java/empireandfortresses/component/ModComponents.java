package empireandfortresses.component;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import empireandfortresses.EmpiresAndFortresses;
import net.minecraft.util.Identifier;

public class ModComponents implements EntityComponentInitializer {

    public static final ComponentKey<CooldownComponent> COOLDOWN_COMPONENT = ComponentRegistryV3.INSTANCE.getOrCreate(Identifier.of(EmpiresAndFortresses.MOD_ID, "cooldown_component"), CooldownComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(COOLDOWN_COMPONENT, PlayerCooldownComponent::new, RespawnCopyStrategy.ALWAYS_COPY);
    }
    
}
