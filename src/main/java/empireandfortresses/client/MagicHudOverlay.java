package empireandfortresses.client;

import com.mojang.blaze3d.systems.RenderSystem;

import empireandfortresses.EmpiresAndFortresses;
import empireandfortresses.component.ModComponents;
import empireandfortresses.component.PlayerCooldownComponent;
import empireandfortresses.event.KeyInputHandler;
import empireandfortresses.item.custom.SpellCastingItem;
import empireandfortresses.magic.Spell;
import empireandfortresses.magic.SpellCategory;
import empireandfortresses.magic.Spells;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Identifier;

public class MagicHudOverlay implements HudRenderCallback {

    public static final Identifier SPELL_SLOT = new Identifier(EmpiresAndFortresses.MOD_ID, "textures/gui/spell_slot.png");
    public static final Identifier SPELL_MARKER = new Identifier(EmpiresAndFortresses.MOD_ID, "textures/gui/spell_marker.png");
    public static final Identifier SPELL_COOLDOWN = new Identifier(EmpiresAndFortresses.MOD_ID, "textures/gui/spell_cooldown.png");

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

        drawContext.getMatrices().push();
        drawContext.getMatrices().translate(0, 0, 101);

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

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

                for (int i = 0; i <= list.size() - 1; i++) {
                    Spell spell = Spells.getSpellById(list.getCompound(i).getString("Id"));
                    Identifier texture = spell.getSpellIcon();
                    SpellCategory category = spell.getCategory();
                    PlayerCooldownComponent component = (PlayerCooldownComponent)(ModComponents.COOLDOWN_COMPONENT.get((PlayerEntity)player));

                    int maxCooldown = component.getMaxCooldown(category);
                    int cooldownProgress = (int)(16 * ((float)component.getCooldown(category) / (float)maxCooldown));
                    int slotX = x - 88 + inventory.selectedSlot * 20;
                    int slotY = y - 41 - 22 * i;

                    drawContext.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
                    drawContext.drawTexture(texture, slotX, slotY, 0, 0, 16, 16, 16, 16);

                    drawContext.setShaderColor(1.0f, 1.0f, 1.0f, 0.5f);
                    drawContext.drawTexture(SPELL_COOLDOWN, slotX, slotY + 16 - cooldownProgress, 0, 0, 16, cooldownProgress, 16, 16);
                }

                drawContext.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
                drawContext.drawTexture(SPELL_MARKER, x - 92 + inventory.selectedSlot * 20, y - 45 - activeSpellSlot * 22, 0, 0, 24, 24, 24, 24);
            }
        }

        drawContext.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.disableBlend();
        drawContext.getMatrices().pop();
    }
}
