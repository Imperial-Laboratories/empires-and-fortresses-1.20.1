package empireandfortresses.entity.spell;

import empireandfortresses.entity.damage.ModDamageTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class MagicBulletEntity extends ProjectileEntity {

    private static final TrackedData<Integer> DURATION = DataTracker.registerData(MagicBulletEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private final float damage;

    public MagicBulletEntity(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
        this.damage = 0;
    }

    public MagicBulletEntity(EntityType<? extends ProjectileEntity> entityType, World world, float damage) {
        super(entityType, world);
        this.damage = damage;
    }

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(DURATION, 40);
    }

    public void spawnBullet(World world, PlayerEntity user, float speed, float divergence) {
        spawnBullet(world, user, speed, divergence, this.getDuration());
    }

    public void spawnBullet(World world, PlayerEntity user, float speed, float divergence, int duration) {
        this.setOwner(user);
        this.setPos(user.getX(), user.getEyeY() - 0.25f, user.getZ());
        this.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, speed, divergence);
        this.setDuration(duration);
        world.spawnEntity(this);
    }

    @Override
    public void tick() {
        super.tick();

        int d = this.getDuration() - 1;
        this.setDuration(d);

        if (d < 0) {
            this.discard();
            return;
        }

        HitResult result = ProjectileUtil.getCollision(this, this::canHit);
        if (result.getType() != HitResult.Type.MISS) {
            onCollision(result);
        }

        this.checkBlockCollision();
        this.setPosition(this.getX() + this.getVelocity().x, this.getY() + this.getVelocity().y, this.getZ() + this.getVelocity().z);

        if (this.getWorld().isClient) {
            return;
        }
        ((ServerWorld)this.getWorld()).spawnParticles(ParticleTypes.WITCH, this.getX(), this.getY() + 0.15d, this.getZ(), 1, 0, 0, 0, 0);
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        this.discard();
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        entityHitResult.getEntity().damage(ModDamageTypes.of(getEntityWorld(), ModDamageTypes.SPELL), this.damage);
        this.discard();
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putShort("Duration", (short)this.getDuration());
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        this.setDuration(nbt.getShort("Duration"));
    }

	public void setDuration(int duration) {
		this.dataTracker.set(DURATION, duration);
	}

	public int getDuration() {
		return this.dataTracker.get(DURATION);
	}

}
