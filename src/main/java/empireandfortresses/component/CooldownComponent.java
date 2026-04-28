package empireandfortresses.component;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import empireandfortresses.magic.SpellCategory;

public interface CooldownComponent extends ComponentV3, ServerTickingComponent, AutoSyncedComponent {
    int getCooldown(SpellCategory category);

    void setCooldown(SpellCategory category, int ticks);

    int getMaxCooldown(SpellCategory category);

    void setMaxCooldown(SpellCategory category, int ticks);
}
