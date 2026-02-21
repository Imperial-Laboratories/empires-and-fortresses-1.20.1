package empireandfortresses.client;

import com.mojang.blaze3d.systems.RenderSystem;

import empireandfortresses.EmpiresAndFortresses;
import empireandfortresses.event.KeyInputHandler;
import empireandfortresses.item.custom.SpellCastingItem;
import empireandfortresses.magic.Spells;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class MagicHudOverlay implements HudRenderCallback {

    public static final Identifier SPELL_SLOT = new Identifier(EmpiresAndFortresses.MOD_ID, "textures/gui/spell_slot.png");
    public static final Identifier SPELL_MARKER = new Identifier(EmpiresAndFortresses.MOD_ID, "textures/gui/spell_marker.png");

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        int x = 0;
        int y = 0;
        MinecraftClient client = MinecraftClient.getInstance();
        if (client != null) {
            int width = client.getWindow().getScaledWidth();
            int height = client.getWindow().getScaledHeight();

            x = width / 2;
            y = height;
        }

        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        drawContext.setShaderColor(1.0f, 1.0f, 1.0f, 0.85f);
        RenderSystem.setShaderTexture(0, SPELL_SLOT);

        if (KeyInputHandler.magicKey.isPressed()) {
            ClientPlayerEntity player = client.player;
            PlayerInventory inventory = player.getInventory();
            ItemStack stack = player.getMainHandStack();
            NbtCompound nbt = stack.getNbt();

            int spellSlots = 0;
            
            if (stack.getItem() instanceof SpellCastingItem) {
                int activeSpellSlot = ((SpellCastingItem) stack.getItem()).getActiveSpellIndex(stack);

                spellSlots = nbt.getInt("SpellSlots");
                drawContext.drawTexture(SPELL_SLOT, x - 91 + inventory.selectedSlot * 20, y - 44 - (spellSlots - 1) * 22, 0, 0, 22, 22 * spellSlots, 22, 22);

                NbtList list = nbt.getList("Spells", NbtElement.COMPOUND_TYPE);

                drawContext.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
                for (int i = 0; i <= list.size() - 1; i++) {
                    // player.sendMessage(Text.literal(list.getCompound(i).toString()));
                    Identifier texture = Spells.getSpellById(list.getCompound(i).getString("Id")).getSpellIcon();
                    drawContext.drawTexture(texture, x - 88 + inventory.selectedSlot * 20, y - 41 - 22 * i, 0, 0, 16, 16, 16, 16);
                }

                drawContext.drawTexture(SPELL_MARKER, x - 92 + inventory.selectedSlot * 20, y - 45 - activeSpellSlot * 22, 0, 0, 24, 24, 24, 24);
            }
        }

        drawContext.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
    }
}
