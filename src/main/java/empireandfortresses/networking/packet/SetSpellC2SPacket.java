package empireandfortresses.networking.packet;

import empireandfortresses.item.custom.SpellCastingItem;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class SetSpellC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
        PacketByteBuf buf, PacketSender responseSender) {
            ItemStack stack = player.getMainHandStack();
            NbtCompound nbt = stack.getNbt();
            NbtList list = nbt.getList("Spells", NbtElement.COMPOUND_TYPE);

            if (stack.getItem() instanceof SpellCastingItem) {
                nbt.putString("ActiveSpell", list.getCompound(buf.readInt()).getString("Id"));
            }
        }
}
