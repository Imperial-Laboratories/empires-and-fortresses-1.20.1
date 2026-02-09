package empireandfortresses.magic;

import java.util.ArrayList;
import java.util.List;

import empireandfortresses.magic.spell.MagicBulletSpell;


public class Spells {
    public static final List<Spell> SPELL_REGISTRY = new ArrayList<>();

    public static final Spell MAGIC_BULLET = registerSpell(new MagicBulletSpell());

    public static Spell registerSpell(Spell spell) {
        SPELL_REGISTRY.add(spell);
        return spell;
    }

    public static Spell getSpellById(String id) {
        return SPELL_REGISTRY.stream().filter(s -> s.getSpellID().toString().equals(id)).findFirst().orElse(null);
    }
}
