package com.mrbysco.dabomb.entity;

import com.mrbysco.dabomb.config.BombConfig;
import com.mrbysco.dabomb.registry.BombRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;

public class ClusterBomb extends ThrowableItemProjectile {
	private int bounceCount = 0;

	public ClusterBomb(EntityType<? extends ClusterBomb> entityType, Level level) {
		super(entityType, level);
	}

	public ClusterBomb(Level level, LivingEntity livingEntity) {
		super(BombRegistry.BOMB.get(), livingEntity, level);
	}

	public ClusterBomb(Level level, double x, double y, double z) {
		super(BombRegistry.BOMB.get(), x, y, z, level);
	}

	public ClusterBomb(PlayMessages.SpawnEntity spawnEntity, Level level) {
		this(BombRegistry.CLUSTER_BOMB.get(), level);
	}

	@Override
	public ItemStack getItem() {
		return new ItemStack(BombRegistry.CLUSTER_BOMB_ITEM.get());
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	protected Item getDefaultItem() {
		return BombRegistry.BOMB_ITEM.get();
	}

	private ParticleOptions getParticle() {
		return ParticleTypes.SMOKE;
	}

	public void handleEntityEvent(byte id) {
		if (id == 3) {
			ParticleOptions particleoptions = this.getParticle();

			for (int i = 0; i < 8; ++i) {
				this.level().addParticle(particleoptions, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
			}
		}
	}

	@Override
	public void tick() {
		super.tick();
		if (this.level().isClientSide) {
			this.level().addParticle(ParticleTypes.SMOKE, this.getX(), this.getY() + 0.5D, this.getZ(), 0.0D, 0.0D, 0.0D);
		}

		if (tickCount >= 100) {
			if (!this.level().isClientSide) {
				this.explode();

				//Spawn 4 fragment bombs
				for (int i = 0; i <= 4; i++) {
					BombFragment fragment = BombRegistry.BOMB_FRAGMENT.get().create(this.level());
					fragment.setDeltaMovement((this.level().random.nextDouble() * 6D - 3D) * 0.3D, 0, (this.level().random.nextDouble() * 6D - 3D) * 0.3D);
					fragment.setPos(getX(), getY() + 0.8, getZ());

					this.level().addFreshEntity(fragment);
				}
				this.level().broadcastEntityEvent(this, (byte) 3);
				this.discard();
			}
		}
	}

	protected void onHit(HitResult hitResult) {
		if (bounceCount >= 20) {
			this.setNoGravity(true);
			this.setDeltaMovement(new Vec3(0, 0.0001F, 0));
		} else {
			this.bounceCount++;
			super.onHit(hitResult);
		}
	}

	@Override
	protected void onHitBlock(BlockHitResult hitBlock) {
		double velX = this.getDeltaMovement().x * (bounceCount < 6 ? (1.0F - Math.min(1.0F, 0.1F * bounceCount)) : 0.7F);
		double velY = this.getDeltaMovement().y * (bounceCount < 6 ? (0.625F / bounceCount) : 0F);
		double velZ = this.getDeltaMovement().z * (bounceCount < 6 ? (1.0F - Math.min(1.0F, 0.1F * bounceCount)) : 0.7F);

		Direction direction = hitBlock.getDirection();
		BlockPos blockPos = hitBlock.getBlockPos();
		BlockState blockstate = this.level().getBlockState(blockPos);

		if (blockstate.blocksMotion()) {
			if (!level().isClientSide && bounceCount < 6) {
				this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.METAL_HIT, SoundSource.NEUTRAL, 1.0F, 4.0F);
			}

			if (direction == Direction.EAST || direction == Direction.WEST)
				this.setDeltaMovement(-velX, velY, velZ);
			if (direction == Direction.DOWN || direction == Direction.UP)
				this.setDeltaMovement(velX, -velY, velZ);
			if (direction == Direction.NORTH || direction == Direction.SOUTH)
				this.setDeltaMovement(velX, velY, -velZ);
		}
	}

	@Override
	protected void onHitEntity(EntityHitResult hitResult) {
		double velX = this.getDeltaMovement().x * (bounceCount < 6 ? (1.0F - Math.min(1.0F, 0.1F * bounceCount)) : 0.7F);
		double velY = this.getDeltaMovement().y * (bounceCount < 6 ? (0.625F / bounceCount) : 0F);
		double velZ = this.getDeltaMovement().z * (bounceCount < 6 ? (1.0F - Math.min(1.0F, 0.1F * bounceCount)) : 0.7F);
		double absVelX = Math.abs(velX);
		double absVelY = Math.abs(velY);
		double absVelZ = Math.abs(velZ);

		if (absVelX >= absVelY && absVelX >= absVelZ)
			this.setDeltaMovement(-velX, velY, velZ);
		if (absVelY >= absVelX && absVelY >= absVelZ)
			this.setDeltaMovement(velX, -velY, velZ);
		if (absVelZ >= absVelY && absVelZ >= absVelX)
			this.setDeltaMovement(velX, velY, -velZ);
	}

	@Override
	public void shootFromRotation(Entity entity, float x, float y, float z, float velocity, float inaccuracy) {
		float f = -Mth.sin(y * ((float) Math.PI / 180F)) * Mth.cos(x * ((float) Math.PI / 180F));
		float f1 = -Mth.sin((x + z) * ((float) Math.PI / 180F));
		float f2 = Mth.cos(y * ((float) Math.PI / 180F)) * Mth.cos(x * ((float) Math.PI / 180F));
		this.shoot((double) f, (double) f1, (double) f2, velocity, inaccuracy);
		Vec3 vec3 = entity.getDeltaMovement();
		this.setDeltaMovement(this.getDeltaMovement().add(vec3.x, entity.onGround() ? 0.0D : vec3.y, vec3.z));
	}

	protected void explode() {
		this.level().explode(this, this.getX(), this.getY(0.0625D) + 0.5F, this.getZ(), BombConfig.COMMON.clusterBombRadius.get().floatValue(), Level.ExplosionInteraction.TNT);
	}
}
