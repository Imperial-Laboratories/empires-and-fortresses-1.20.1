package empireandfortresses.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import empireandfortresses.event.KeyInputHandler;
import empireandfortresses.item.custom.SpellCastingItem;
import empireandfortresses.magic.Spell;
import empireandfortresses.magic.SpellTriggerCategory;
import empireandfortresses.magic.Spells;
import empireandfortresses.networking.ModMessages;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

@Mixin(Mouse.class)
public class MouseMixin {
    @Inject(method = "onMouseScroll", at = @At("HEAD"), cancellable = true)
    private void onMouseScroll(long window, double horizontal, double vertical, CallbackInfo info) {
        if (vertical != 0) {
            ClientPlayerEntity player = MinecraftClient.getInstance().player;
            ItemStack stack = player.getMainHandStack();
            NbtCompound nbt = stack.getNbt();

            if (!(stack.getItem() instanceof SpellCastingItem)) {
                return;
            }

            Spell activeSpell = Spells.getSpellById(nbt.getString("ActiveSpell"));
            SpellTriggerCategory triggerCategory = activeSpell.getTriggerCategory();

            if ((((SpellCastingItem) stack.getItem()).isTriggeringSpell(player, nbt, activeSpell) && activeSpell.castable(player)
                    && (triggerCategory == SpellTriggerCategory.HOLD_ATTACK || triggerCategory == SpellTriggerCategory.HOLD_USE))) {
                info.cancel();
                return;
            }

            if (KeyInputHandler.magicKey.isPressed()) {
                if (vertical > 0) {
                    ClientPlayNetworking.send(ModMessages.NEXT_SPELL_ID, PacketByteBufs.create());
                } else {
                    ClientPlayNetworking.send(ModMessages.PREV_SPELL_ID, PacketByteBufs.create());
                }
                info.cancel();
            }
        }
    }
}
