package empireandfortresses.mixin;

import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.player.PlayerEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import empireandfortresses.entity.attribute.ModEntityAttributes;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
	@Inject(method = "createPlayerAttributes", at = @At("Return"))
	private static void onAddAttributes(CallbackInfoReturnable<DefaultAttributeContainer.Builder> info) {
		info.getReturnValue().add(ModEntityAttributes.GENERIC_MAGIC_ATTACK_DAMAGE, 0.0);
	}
}
