package empireandfortresses.component;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import empireandfortresses.magic.SpellCategory;

public interface CooldownComponent extends ComponentV3, ServerTickingComponent {
    int getCooldown(SpellCategory category);

    void setCooldown(SpellCategory category, int ticks);
}
