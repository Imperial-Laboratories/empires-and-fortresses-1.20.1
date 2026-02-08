package empireandfortresses.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.MathHelper;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {

    @Inject(at = @At("HEAD"), method = "setExperiencePoints")
    private void setExperiencePointsInject(int experiencePoints, CallbackInfo ci) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        player.totalExperience = getLevelExperience(player.experienceLevel)
                + experiencePoints;
    }

    @Inject(at = @At("HEAD"), method = "setExperienceLevel")
    private void setExperienceLevelInject(int experienceLevel, CallbackInfo ci) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        player.totalExperience = getLevelExperience(experienceLevel)
                + MathHelper.floor(player.experienceProgress * player.getNextLevelExperience());
    }

    @Inject(at = @At("HEAD"), method = "addExperienceLevels")
    private void addExperienceLevelsInject(int levels, CallbackInfo ci) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        int newLevel = player.experienceLevel + levels;
        player.totalExperience = getLevelExperience(newLevel)
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
