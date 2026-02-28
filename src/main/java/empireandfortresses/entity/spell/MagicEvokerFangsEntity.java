package empireandfortresses.entity.spell;

import empireandfortresses.entity.damage.ModDamageTypes;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.EvokerFangsEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

@Getter
@Setter
public class MagicEvokerFangsEntity extends EvokerFangsEntity {

    private float damage;

    public MagicEvokerFangsEntity(EntityType<? extends EvokerFangsEntity> entityType, World world) {
        super(entityType, world);
        this.damage = 6.0F;
    }

    public MagicEvokerFangsEntity(World world, double x, double y, double z, float yaw, int warmup, LivingEntity owner, float damage) {
        super(world, x, y, z, yaw, warmup, owner);
        this.damage = damage;
    }

    @Override
    public void damage(LivingEntity target) {
        LivingEntity owner = this.getOwner();
        if (target.isAlive() && !target.isInvulnerable() && target != owner) {
            if (owner != null && owner.isTeammate(target)) {
                return;
            }
            target.damage(ModDamageTypes.of(getEntityWorld(), ModDamageTypes.SPELL), this.damage);

            double knockbackStrength = 0.7;
            double dx = target.getX() - this.getX();
            double dz = target.getZ() - this.getZ();
            double distance = Math.sqrt(dx * dx + dz * dz);
            if (distance > 0) {
                double knockbackX = (dx / distance) * knockbackStrength;
                double knockbackZ = (dz / distance) * knockbackStrength;
                target.addVelocity(knockbackX, 0.1, knockbackZ);
            }
        }
    }

}
