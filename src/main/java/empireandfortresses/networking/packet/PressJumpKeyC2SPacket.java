package empireandfortresses.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;

import net.minecraft.server.network.ServerPlayNetworkHandler;

import net.minecraft.server.network.ServerPlayerEntity;

import empireandfortresses.item.custom.SpellCastingItem;

@SuppressWarnings("java:S1172")
public class PressJumpKeyC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        ItemStack stack = player.getMainHandStack();

        if (stack.getItem() instanceof SpellCastingItem item) {
            item.onJump(player, stack);
        }
    }
}
