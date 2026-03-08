package empireandfortresses.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.item.BookItem;

@Mixin(BookItem.class)
public class BookItemMixin {

    @Inject(at = @At("RETURN"), method = "isEnchantable", cancellable =  true)
    private void disableEnchanting(CallbackInfoReturnable<Boolean> info) {
        info.setReturnValue(false);
    }

}
