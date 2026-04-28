package empireandfortresses.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.EnchantingTableBlock;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerFactory;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;
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

    @Inject(at = @At("RETURN"), method = "createScreenHandlerFactory", cancellable = true)
    private void redirectScreenHandler(BlockState state, World world, BlockPos pos, CallbackInfoReturnable<NamedScreenHandlerFactory> info, @Local Text text) {
        if (info.getReturnValue() != null) {
            ScreenHandlerFactory screenHandlerFactory = (syncId, inventory, player) -> new CustomEnchantmentScreenHandler(syncId, inventory, ScreenHandlerContext.create(world, pos));
            info.setReturnValue(new SimpleNamedScreenHandlerFactory(screenHandlerFactory, text));
        }
    }
}
