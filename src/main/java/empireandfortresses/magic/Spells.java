package empireandfortresses.magic;

import java.util.ArrayList;
import java.util.List;

import empireandfortresses.magic.spell.MagicBulletSpell;
import empireandfortresses.magic.spell.MultishotSpell;
import empireandfortresses.magic.spell.RapidBulletSpell;
import empireandfortresses.magic.spell.ReactiveBulletSpell;

public class Spells {
    protected static final List<Spell> SPELL_REGISTRY = new ArrayList<>();

    public static final Spell MAGIC_BULLET = registerSpell(new MagicBulletSpell());
    public static final Spell RAPID_BULLET = registerSpell(new RapidBulletSpell());
    public static final Spell REACTIVE_BULLET = registerSpell(new ReactiveBulletSpell());
    public static final Spell MULTISHOT = registerSpell(new MultishotSpell());

    public static Spell registerSpell(Spell spell) {
        SPELL_REGISTRY.add(spell);
        return spell;
    }

    public static Spell getSpellById(String id) {
        return SPELL_REGISTRY.stream().filter(s -> s.getSpellID().toString().equals(id)).findFirst().orElse(null);
    }
}
