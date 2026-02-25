package empireandfortresses.entity.spell;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ExplosiveMagicBulletEntity extends MagicBulletEntity {

    public ExplosiveMagicBulletEntity(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public ExplosiveMagicBulletEntity(EntityType<? extends ProjectileEntity> entityType, World world, float damage) {
        super(entityType, world, damage);
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        explode(blockHitResult.getPos());
        super.onBlockHit(blockHitResult);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        explode(entityHitResult.getPos());
        this.discard();
    }

    private void explode(Vec3d position) {
        this.getWorld().createExplosion(this, position.getX(), position.getY(), position.getZ(), this.getDamage(), World.ExplosionSourceType.MOB);
    }
}
