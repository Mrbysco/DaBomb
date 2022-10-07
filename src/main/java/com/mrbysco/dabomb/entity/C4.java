package com.mrbysco.dabomb.entity;

import com.mrbysco.dabomb.config.BombConfig;
import com.mrbysco.dabomb.registry.BombRegistry;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;

public class C4 extends ThrowableItemProjectile {

	public C4(EntityType<? extends C4> entityType, Level level) {
		super(entityType, level);
	}

	public C4(Level level, LivingEntity livingEntity) {
		super(BombRegistry.C4_ENTITY.get(), livingEntity, level);
	}

	public C4(Level level, double x, double y, double z) {
		super(BombRegistry.C4_ENTITY.get(), x, y, z, level);
	}

	public C4(PlayMessages.SpawnEntity spawnEntity, Level level) {
		this(BombRegistry.C4_ENTITY.get(), level);
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}


	protected Item getDefaultItem() {
		return BombRegistry.C4_ITEM.get();
	}

	private ParticleOptions getParticle() {
		ItemStack itemstack = this.getItemRaw();
		return (ParticleOptions) (itemstack.isEmpty() ? ParticleTypes.SMOKE : new ItemParticleOption(ParticleTypes.ITEM, itemstack));
	}

	public void handleEntityEvent(byte id) {
		if (id == 3) {
			ParticleOptions particleoptions = this.getParticle();

			for (int i = 0; i < 8; ++i) {
				this.level.addParticle(particleoptions, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
			}
		}
	}

	@Override
	public void tick() {
		super.tick();
		if (this.level.isClientSide) {
			this.level.addParticle(ParticleTypes.SMOKE, this.getX(), this.getY() + 0.5D, this.getZ(), 0.0D, 0.0D, 0.0D);
		}

//		if (tickCount >= 1200 && (this.isNoGravity() || isPassenger())) {
//			if (!this.level.isClientSide) {
//				this.explode();
//			}
//		}
	}

	protected void onHit(HitResult hitResult) {
		super.onHit(hitResult);
		if (random.nextDouble() <= 0.075D) {
			this.level.playSound(null, blockPosition(), BombRegistry.BOMB_PLANTED.get(), SoundSource.NEUTRAL, 0.65F, 1.0F);
		}
	}

	@Override
	protected void onHitBlock(BlockHitResult hitBlock) {
		this.setNoGravity(true);
		this.setDeltaMovement(new Vec3(0, 0.0001F, 0));
	}

	@Override
	protected void onHitEntity(EntityHitResult hitResult) {
		Entity entity = hitResult.getEntity();
		if (!level.isClientSide && canHitEntity(entity)) {
			if (entity instanceof LivingEntity hitentity && getOwner() instanceof LivingEntity owner) {
				hitentity.setLastHurtByMob(owner);
			}
			this.startRiding(entity, true);
		}
	}

	@Override
	public void shootFromRotation(Entity entity, float x, float y, float z, float velocity, float inaccuracy) {
		float f = -Mth.sin(y * ((float) Math.PI / 180F)) * Mth.cos(x * ((float) Math.PI / 180F));
		float f1 = -Mth.sin((x + z) * ((float) Math.PI / 180F));
		float f2 = Mth.cos(y * ((float) Math.PI / 180F)) * Mth.cos(x * ((float) Math.PI / 180F));
		this.shoot((double) f, (double) f1, (double) f2, velocity, inaccuracy);
		Vec3 vec3 = entity.getDeltaMovement();
		this.setDeltaMovement(this.getDeltaMovement().add(vec3.x, entity.isOnGround() ? 0.0D : vec3.y, vec3.z));
	}

	public void explode() {
		this.level.explode(this, this.getX(), this.getY(0.0625D) + 0.5F, this.getZ(), BombConfig.COMMON.c4Radius.get().floatValue(), Explosion.BlockInteraction.BREAK);
		this.level.broadcastEntityEvent(this, (byte) 3);
		this.discard();
	}

	@Override
	public double getMyRidingOffset() {
		return 0.5D;
	}

	@Override
	public InteractionResult interact(Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (stack.is(Tags.Items.SHEARS) && (getOwner() == null || player.getUUID().equals(getOwner().getUUID()))) {
			this.spawnAtLocation(BombRegistry.C4_ITEM.get());
			if (random.nextDouble() <= 0.075D) {
				this.level.playSound(null, blockPosition(), BombRegistry.BOMB_DEFUSED.get(), SoundSource.NEUTRAL, 0.65F, 1.0F);
			}
			this.level.broadcastEntityEvent(this, (byte) 3);
			this.discard();
		}
		return super.interact(player, hand);
	}

	@Override
	public boolean isPickable() {
		return true;
	}

	@Override
	protected boolean updateInWaterStateAndDoFluidPushing() {
		return false;
	}
}
