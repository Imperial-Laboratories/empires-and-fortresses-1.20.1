package empireandfortresses.mixin;

import net.minecraft.block.EnchantingTableBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.llamalad7.mixinextras.sugar.Local;

@Mixin(EnchantingTableBlock.class)
public class EnchantingTableBlockMixin {

    @Inject(at = @At("HEAD"), method = "onUse", cancellable = true)
    // @Local means you don't have to get all parameters in original order but you
    // can just pick the ones you need
    private void init(CallbackInfoReturnable<ActionResult> info, @Local World world, @Local PlayerEntity player,
            @Local Hand hand) {
        info.cancel();
        info.setReturnValue(ActionResult.PASS);

        if (!world.isClient && hand == Hand.MAIN_HAND) {
            player.sendMessage(
                    Text.literal("Enchanting Tables are disabled in Empires and Fortresses."), false);
        }
    }
}