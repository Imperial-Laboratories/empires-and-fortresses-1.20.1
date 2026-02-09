package empireandfortresses.item.custom;

import java.util.UUID;
import java.util.stream.IntStream;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import empireandfortresses.entity.attribute.ModEntityAttributes;
import empireandfortresses.magic.Spell;
import empireandfortresses.magic.Spells;

public class SpellCastingItem extends ToolItem {

	private final float magicAttackDamage;
	private final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;
    private NbtList defaultSpells;
    private Spell defaultSpell;

    public SpellCastingItem(ToolMaterial material, int damage, Settings settings, Spell defaultSpell) {
        super(material, settings);

        this.defaultSpells = new NbtList();

        this.magicAttackDamage = damage;
		Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(
			ModEntityAttributes.GENERIC_MAGIC_ATTACK_DAMAGE,
			new EntityAttributeModifier(UUID.fromString("07e14470-a892-4032-9bcd-ee900f68f9e5"), "Weapon modifier", (double)this.magicAttackDamage, EntityAttributeModifier.Operation.ADDITION)
		);
		this.attributeModifiers = builder.build();

        this.appendSpell(defaultSpell);
        this.defaultSpell = defaultSpell;
    }

    public void appendSpell(Spell spell) {
        this.defaultSpells.add(spell.toNbt());
    }

    public int getActiveSpellIndex(ItemStack stack) {
        NbtCompound nbt = stack.getNbt();
        NbtList list = nbt.getList("Spells", NbtElement.COMPOUND_TYPE);
        String id = nbt.getString("ActiveSpell");
        return IntStream.range(0, list.size()).filter(i -> list.getCompound(i).getString("Id").equals(id)).findFirst().orElse(-1);
    }

    public void nextSpell(ItemStack stack) {
        NbtCompound nbt = stack.getNbt();
        NbtList list = nbt.getList("Spells", NbtElement.COMPOUND_TYPE);
        int index = getActiveSpellIndex(stack) + 1;
        if (index >= list.size()) {
            index = 0;
        }
        nbt.putString("ActiveSpell", list.getCompound(index).getString("Id"));
    }

    public void prevSpell(ItemStack stack) {
        NbtCompound nbt = stack.getNbt();
        NbtList list = nbt.getList("Spells", NbtElement.COMPOUND_TYPE);
        int index = getActiveSpellIndex(stack) - 1;
        if (index < 0) {
            index = list.size() - 1;
        }
        nbt.putString("ActiveSpell", list.getCompound(index).getString("Id"));
    }

    @Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (!world.isClient) {
            // user.sendMessage(Text.literal(String.valueOf(user.getAttributeValue(ModEntityAttributes.GENERIC_MAGIC_ATTACK_DAMAGE))), false);
            // user.sendMessage(Text.literal(stack.getNbt().getString("ActiveSpell")));

            Spell spell = Spells.getSpellById(stack.getNbt().getString("ActiveSpell"));
            if (spell != null && spell.castable(user)) {
                spell.cast(world, user, stack);
            } else if(!spell.XPSufficient(user)) {
                user.sendMessage(Text.literal("Not enough XP!").formatted(Formatting.RED), true);
            } else if(spell.onCooldown()) {
                user.sendMessage(Text.literal("Cooldown has not finished!").formatted(Formatting.RED), true);
            } else if(!spell.condition()) {
                user.sendMessage(Text.literal("Cannot cast right now!").formatted(Formatting.RED), true);
            }

            if (!user.isSneaking()) {
                nextSpell(stack);
            } else {
                prevSpell(stack);
            }
        }

		return TypedActionResult.success(stack);
	}

	@Override
	public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
		return slot == EquipmentSlot.MAINHAND ? this.attributeModifiers : super.getAttributeModifiers(slot);
	}

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (world.isClient) {
            return;
        }

        NbtCompound nbt = stack.getOrCreateNbt();

        if (!nbt.contains("Spells")) {
            nbt.put("Spells", defaultSpells);
        }

        if (!nbt.contains("ActiveSpell")) {
            nbt.putString("ActiveSpell", defaultSpell.getSpellID().toString());
        }

        NbtList list = nbt.getList("Spells", NbtElement.COMPOUND_TYPE);
        for (int i = 0; i < list.size(); i++) {
            Spell spell = Spells.getSpellById(list.getCompound(i).getString("Id"));
            if (spell.onCooldown()) {
                spell.cooldownTick();
            }
        }
    }
}
