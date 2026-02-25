package empireandfortresses.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import empireandfortresses.EmpiresAndFortresses;
import empireandfortresses.event.KeyInputHandler;
import empireandfortresses.item.custom.SpellCastingItem;
import empireandfortresses.magic.Spell;
import empireandfortresses.magic.SpellTriggerCategory;
import empireandfortresses.magic.Spells;
import empireandfortresses.networking.ModMessages;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.GameOptions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.PacketByteBuf;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @ModifyConstant(method = "handleInputEvents()V", constant = @Constant(intValue = 9))
    private int cancelIteration(int value) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        ItemStack stack = player.getMainHandStack();
        NbtCompound nbt = stack.getNbt();

        if (!(stack.getItem() instanceof SpellCastingItem)) {
            return 9;
        }

        Spell activeSpell = Spells.getSpellById(nbt.getString("ActiveSpell"));
        SpellTriggerCategory triggerCategory = activeSpell.getTriggerCategory();

        if ((((SpellCastingItem) stack.getItem()).isTriggeringSpell((PlayerEntity)player, nbt, activeSpell) && activeSpell.castable((PlayerEntity)player) && (triggerCategory == SpellTriggerCategory.HOLD_ATTACK || triggerCategory == SpellTriggerCategory.HOLD_USE))) {
            return 0;
        }

        if (KeyInputHandler.magicKey.isPressed()) {
            GameOptions options = MinecraftClient.getInstance().options;
            NbtList list = nbt.getList("Spells", NbtElement.COMPOUND_TYPE);
            for (int j = 0; j < list.size(); ++j) {
                if (options.hotbarKeys[j].wasPressed()) {
                    PacketByteBuf buf = PacketByteBufs.create();
                    buf.writeInt(j);
                    ClientPlayNetworking.send(ModMessages.SET_SPELL_ID, buf);
                }
            }
        }

        return 9;
    }
}
