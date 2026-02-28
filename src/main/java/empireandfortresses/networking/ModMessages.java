package empireandfortresses.networking;

import empireandfortresses.EmpiresAndFortresses;
import empireandfortresses.networking.packet.*;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class ModMessages {
    public static final Identifier NEXT_SPELL_ID = new Identifier(EmpiresAndFortresses.MOD_ID, "next_spell");
    public static final Identifier PREV_SPELL_ID = new Identifier(EmpiresAndFortresses.MOD_ID, "prev_spell");
    public static final Identifier SET_SPELL_ID = new Identifier(EmpiresAndFortresses.MOD_ID, "set_spell");
    public static final Identifier PRESS_JUMP_KEY_ID = new Identifier(EmpiresAndFortresses.MOD_ID, "press_jump_key");

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(NEXT_SPELL_ID, NextSpellC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(PREV_SPELL_ID, PrevSpellC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(SET_SPELL_ID, SetSpellC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(PRESS_JUMP_KEY_ID, PressJumpKeyC2SPacket::receive);
    }

    public static void registerS2CPackets() {

    }
}
