package empireandfortresses.magic;

import java.util.ArrayList;
import java.util.List;

import empireandfortresses.magic.spell.MagicBulletSpell;
import empireandfortresses.magic.spell.RapidBulletSpell;


public class Spells {
    public static final List<Spell> SPELL_REGISTRY = new ArrayList<>();

    public static final Spell MAGIC_BULLET = registerSpell(new MagicBulletSpell());
    public static final Spell RAPId_BULLET = registerSpell(new RapidBulletSpell());

    public static Spell registerSpell(Spell spell) {
        SPELL_REGISTRY.add(spell);
        return spell;
    }

    public static Spell getSpellById(String id) {
        return SPELL_REGISTRY.stream().filter(s -> s.getSpellID().toString().equals(id)).findFirst().orElse(null);
    }
}
