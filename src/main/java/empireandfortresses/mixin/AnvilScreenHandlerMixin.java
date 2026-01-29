package empireandfortresses.mixin;

import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.Property;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AnvilScreenHandler.class)
public class AnvilScreenHandlerMixin {

    /*
     * We use Redirect and not Inject here, because we want to change a specific
     * method call inside the target method.
     * 
     * We try to target this line:
     * this.levelCost.set(j + i);
     * 
     * First, we need to target the class of levelCost, which is:
     * net/minecraft/screen/Property
     * 
     * The L at the beginning indicates it's an object type, and the ; at the end
     * indicates the end of the type.
     * 
     * Next, the method name and it's params. (I for int).
     * Finally, we add the return type, which is void (V).
     * 
     * Other types: I = int, Z = boolean, F = float, D = double, V = void and
     * classes same as above.
     */
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/Property;set(I)V"), method = "updateResult")
    private void init(Property property, int cost) {
        property.set(0);
    }
}