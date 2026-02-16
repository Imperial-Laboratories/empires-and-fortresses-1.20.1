package empireandfortresses.item.custom;

import java.util.UUID;
import java.util.stream.IntStream;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
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
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import empireandfortresses.entity.attribute.ModEntityAttributes;
import empireandfortresses.magic.Spell;
import empireandfortresses.magic.SpellTriggerCategory;
import empireandfortresses.magic.Spells;

public class SpellCastingItem extends ToolItem {

	private final float magicAttackDamage;
	private final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;
    private final int spellSlots;
    private NbtList defaultSpells;
    private Spell defaultSpell;

    public SpellCastingItem(ToolMaterial material, int damage, Settings settings, int slots, Spell defaultSpell) {
        super(material, settings);

        this.spellSlots = slots;

        this.defaultSpells = new NbtList();

        this.magicAttackDamage = damage;
		Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(
			ModEntityAttributes.MAGIC_ATTACK_DAMAGE,
			new EntityAttributeModifier(UUID.fromString("07e14470-a892-4032-9bcd-ee900f68f9e5"), "Weapon modifier", (double)this.magicAttackDamage, EntityAttributeModifier.Operation.ADDITION)
		);
		this.attributeModifiers = builder.build();

        this.appendSpell(defaultSpell);
        this.defaultSpell = defaultSpell;
    }

    public void appendSpell(Spell spell) {
        if (this.defaultSpells.size() < this.spellSlots) {
            this.defaultSpells.add(spell.toNbt());
        }
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

        if (!world.isClient && stack.getItem().getClass() == SpellCastingItem.class) {
            // user.sendMessage(Text.literal(stack.getNbt().getString("ActiveSpell")));

            Spell spell = Spells.getSpellById(stack.getNbt().getString("ActiveSpell"));

            if (spell.getTriggerCategory() == SpellTriggerCategory.USE && spell.castable(user)) {
                spell.cast(world, user, stack);
            }

            if (!user.isSneaking()) {
                nextSpell(stack);
                user.sendMessage(Text.literal(stack.getNbt().getString("ActiveSpell")));
            } else {
                prevSpell(stack);
                user.sendMessage(Text.literal(stack.getNbt().getString("ActiveSpell")));
            }
        }

		return TypedActionResult.success(stack);
	}

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (miner instanceof PlayerEntity && miner.getActiveItem().getItem().getClass() == SpellCastingItem.class) {
            Spell spell = Spells.getSpellById(stack.getNbt().getString("ActiveSpell"));

            if (spell.getTriggerCategory() == SpellTriggerCategory.USE && spell.castable((PlayerEntity)miner)) {
                spell.cast(world, (PlayerEntity)miner, stack);
            }
        }
        
        return super.postMine(stack, world, state, pos, miner);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof PlayerEntity && attacker.getActiveItem().getItem().getClass() == SpellCastingItem.class) {
            Spell spell = Spells.getSpellById(stack.getNbt().getString("ActiveSpell"));
            World world = attacker.getWorld();

            if (spell.getTriggerCategory() == SpellTriggerCategory.USE && spell.castable((PlayerEntity)attacker)) {
                spell.cast(world, (PlayerEntity)attacker, stack);
            }
        }

        return super.postHit(stack, target, attacker);
    }

    // nbt modification every tick. Probably not a good idea
    // TODO: detect critical hits
    public boolean isTriggeringSpell(PlayerEntity player, NbtCompound nbt, Spell spell) {
        GameOptions options = MinecraftClient.getInstance().options;
        switch (spell.getTriggerCategory()) {
            case ATTACK:
                if (options.attackKey.isPressed() && !nbt.getBoolean("wasPressed")) {
                    nbt.putBoolean("wasPressed", true);
                    return true;
                } else if (!options.attackKey.isPressed()) {
                    nbt.putBoolean("wasPressed", false);
                }
                return false;
            case HOLD_ATTACK:
                if (options.attackKey.isPressed()) {
                    nbt.putBoolean("wasPressed", true);
                    return true;
                } else {
                    if (nbt.getBoolean("wasPressed") && !player.isCreative() && !spell.isOnCooldown(player)) {
                        spell.activateCooldown(player);
                    }
                    nbt.putBoolean("wasPressed", false);
                    return false;
                }
            case HOLD_USE:
                if (options.useKey.isPressed()) {
                    nbt.putBoolean("wasPressed", true);
                    return true;
                } else {
                    if (nbt.getBoolean("wasPressed") && !player.isCreative() && !spell.isOnCooldown(player)) {
                        spell.activateCooldown(player);
                    }
                    nbt.putBoolean("wasPressed", false);
                    return false;
                }
            default:
                return false;
            }
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

        if (!nbt.contains("wasPressed")) {
            nbt.putBoolean("wasPressed", false);
        }

        if(!nbt.contains("useTimer")) {
            nbt.putInt("useTimer", 0);
        }

        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity)entity;
            if (selected) {
                Spell spell = Spells.getSpellById(nbt.getString("ActiveSpell"));
                if (isTriggeringSpell(player, nbt, spell)) {
                    if (spell.castable(player)) {
                        spell.cast(world, player, stack);
                    }
                }
            }
        }
    }
}
