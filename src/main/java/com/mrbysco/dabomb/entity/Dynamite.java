package com.mrbysco.dabomb.entity;

import com.mrbysco.dabomb.config.BombConfig;
import com.mrbysco.dabomb.registry.BombRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
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

public class Dynamite extends ThrowableItemProjectile {
	private int bounceCount = 0;

	public Dynamite(EntityType<? extends Dynamite> entityType, Level level) {
		super(entityType, level);
	}

	public Dynamite(Level level, LivingEntity livingEntity) {
		super(BombRegistry.DYNAMITE.get(), livingEntity, level);
	}

	public Dynamite(Level level, double x, double y, double z) {
		super(BombRegistry.DYNAMITE.get(), x, y, z, level);
	}

	protected Item getDefaultItem() {
		return BombRegistry.DYNAMITE_ITEM.get();
	}

	private ParticleOptions getParticle() {
		ItemStack itemstack = this.getItemRaw();
		return (ParticleOptions) (itemstack.isEmpty() ? ParticleTypes.SMOKE : new ItemParticleOption(ParticleTypes.ITEM, itemstack));
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

		if (tickCount >= 140) {
			if (!this.level().isClientSide) {
				this.explode();
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
		double velX = this.getDeltaMovement().x * (bounceCount < 8 ? (1.0F - Math.min(0.85F, 0.15F * bounceCount)) : 0.55F);
		double velY = this.getDeltaMovement().y * (bounceCount < 8 ? (0.625F / bounceCount) : 0F);
		double velZ = this.getDeltaMovement().z * (bounceCount < 8 ? (1.0F - Math.min(0.85F, 0.15F * bounceCount)) : 0.55F);

		Direction direction = hitBlock.getDirection();
		BlockPos blockPos = hitBlock.getBlockPos();
		BlockState blockstate = this.level().getBlockState(blockPos);

		if (blockstate.blocksMotion()) {
			if (!level().isClientSide && bounceCount < 8) {
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
		double velX = this.getDeltaMovement().x * (bounceCount < 8 ? (1.0F - Math.min(0.85F, 0.15F * bounceCount)) : 0.55F);
		double velY = this.getDeltaMovement().y * (bounceCount < 8 ? (0.625F / bounceCount) : 0F);
		double velZ = this.getDeltaMovement().z * (bounceCount < 8 ? (1.0F - Math.min(0.85F, 0.15F * bounceCount)) : 0.55F);
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
		this.level().explode(this, this.getX(), this.getY(0.0625D) + 0.5F, this.getZ(), BombConfig.COMMON.dynamiteRadius.get().floatValue(), Level.ExplosionInteraction.TNT);
	}
}
