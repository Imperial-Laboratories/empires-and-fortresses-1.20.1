package empireandfortresses.networking.packet;

import empireandfortresses.item.custom.SpellCastingItem;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class NextSpellC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
        PacketByteBuf buf, PacketSender responseSender) {
            ItemStack stack = player.getMainHandStack();

            if (stack.getItem() instanceof SpellCastingItem) {
                ((SpellCastingItem) stack.getItem()).nextSpell(stack);
            }
        }
}
