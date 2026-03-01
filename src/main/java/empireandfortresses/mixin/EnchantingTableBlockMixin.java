package empireandfortresses.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.EnchantingTableBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.llamalad7.mixinextras.sugar.Local;

import empireandfortresses.screen.CustomEnchantmentScreenHandler;

@Mixin(EnchantingTableBlock.class)
public class EnchantingTableBlockMixin {

    @Inject(at = @At("HEAD"), method = "onUse", cancellable = true)
    // @Local means you don't have to get all parameters in original order but you
    // can just pick the ones you need
    private void init(CallbackInfoReturnable<ActionResult> info, @Local BlockPos pos, @Local BlockState state, @Local World world,
            @Local PlayerEntity player, @Local Hand hand) {
        info.cancel();

        if (world.isClient) {
            info.setReturnValue(ActionResult.SUCCESS);
        } else {
            player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
            info.setReturnValue(ActionResult.CONSUME);
        }
    }

    @Inject(at = @At("RETURN"), method = "createScreenHandlerFactory", cancellable = true)
    private void redirectScreenHandler(BlockState state, World world, BlockPos pos, CallbackInfoReturnable<NamedScreenHandlerFactory> info, @Local Text text) {
        info.setReturnValue(new SimpleNamedScreenHandlerFactory((syncId, inventory, player) -> new CustomEnchantmentScreenHandler(syncId, inventory, ScreenHandlerContext.create(world, pos)), text));
    }
}
