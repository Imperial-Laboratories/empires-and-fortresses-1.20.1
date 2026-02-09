package empireandfortresses.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.authlib.GameProfile;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

@Mixin(ServerPlayerEntity.class)
abstract class ServerPlayerEntityMixin extends PlayerEntity {
    protected ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @Inject(at = @At("RETURN"), method = "setExperiencePoints")
    private void setExperiencePointsInject(int experiencePoints, CallbackInfo ci) {
        updateTotalExperience((ServerPlayerEntity) (Object) this);
    }

    @Inject(at = @At("RETURN"), method = "setExperienceLevel")
    private void setExperienceLevelInject(int experienceLevel, CallbackInfo ci) {
        updateTotalExperience((ServerPlayerEntity) (Object) this);
    }

    @Inject(at = @At("RETURN"), method = "addExperienceLevels")
    private void addExperienceLevelsInject(int levels, CallbackInfo ci) {
        updateTotalExperience((ServerPlayerEntity) (Object) this);
    }

    private void updateTotalExperience(ServerPlayerEntity player) {
        player.totalExperience = getLevelExperience(player.experienceLevel)
                + MathHelper.floor(player.experienceProgress * player.getNextLevelExperience());
    }

    private int getLevelExperience(int level) {
        int experience = 0;
        for (int i = 0; i < level; i++) {
            experience += getNextLevelExperience(i);
        }
        return experience;
    }

    private int getNextLevelExperience(int level) {
        if (level >= 30) {
            return 112 + (level - 30) * 9;
        } else {
            return level >= 15 ? 37 + (level - 15) * 5 : 7 + level * 2;
        }
    }

}
