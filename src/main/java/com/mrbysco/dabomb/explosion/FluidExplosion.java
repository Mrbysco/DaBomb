package com.mrbysco.dabomb.explosion;

import com.google.common.collect.Sets;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class FluidExplosion extends Explosion {
	private final Predicate<FluidState> fluidPredicate;

	public FluidExplosion(Level level, @Nullable Entity entity, double x, double y, double z, float radius, boolean flames, Predicate<FluidState> fluidPredicate, Explosion.BlockInteraction blockInteraction) {
		super(level, entity, x, y, z, radius, flames, blockInteraction);
		this.fluidPredicate = fluidPredicate;
	}

	public static float getSeenPercent(Vec3 p_46065_, Entity boundingBox) {
		AABB aabb = boundingBox.getBoundingBox();
		double d0 = 1.0D / ((aabb.maxX - aabb.minX) * 2.0D + 1.0D);
		double d1 = 1.0D / ((aabb.maxY - aabb.minY) * 2.0D + 1.0D);
		double d2 = 1.0D / ((aabb.maxZ - aabb.minZ) * 2.0D + 1.0D);
		double d3 = (1.0D - Math.floor(1.0D / d0) * d0) / 2.0D;
		double d4 = (1.0D - Math.floor(1.0D / d2) * d2) / 2.0D;
		if (!(d0 < 0.0D) && !(d1 < 0.0D) && !(d2 < 0.0D)) {
			int i = 0;
			int j = 0;

			for (double d5 = 0.0D; d5 <= 1.0D; d5 += d0) {
				for (double d6 = 0.0D; d6 <= 1.0D; d6 += d1) {
					for (double d7 = 0.0D; d7 <= 1.0D; d7 += d2) {
						double d8 = Mth.lerp(d5, aabb.minX, aabb.maxX);
						double d9 = Mth.lerp(d6, aabb.minY, aabb.maxY);
						double d10 = Mth.lerp(d7, aabb.minZ, aabb.maxZ);
						Vec3 vec3 = new Vec3(d8 + d3, d9, d10 + d4);
						if (boundingBox.level().clip(new ClipContext(vec3, p_46065_, ClipContext.Block.COLLIDER, ClipContext.Fluid.ANY, boundingBox)).getType() == HitResult.Type.MISS) {
							++i;
						}

						++j;
					}
				}
			}

			return (float) i / (float) j;
		} else {
			return 0.0F;
		}
	}

	@Override
	public void explode() {
		this.level.gameEvent(this.source, GameEvent.EXPLODE, BlockPos.containing(this.x, this.y, this.z));
		Set<BlockPos> set = Sets.newHashSet();
		for (int i = 0; i < 16; ++i) {
			for (int j = 0; j < 16; ++j) {
				for (int k = 0; k < 16; ++k) {
					if (i == 0 || i == 15 || j == 0 || j == 15 || k == 0 || k == 15) {
						double d0 = (double) ((float) i / 15.0F * 2.0F - 1.0F);
						double d1 = (double) ((float) j / 15.0F * 2.0F - 1.0F);
						double d2 = (double) ((float) k / 15.0F * 2.0F - 1.0F);
						double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
						d0 /= d3;
						d1 /= d3;
						d2 /= d3;
						float f = this.radius * (0.7F + this.level.random.nextFloat() * 0.6F);
						double x = this.x;
						double y = this.y;
						double z = this.z;

						for (float f1 = 0.3F; f > 0.0F; f -= 0.22500001F) {
							BlockPos blockpos = BlockPos.containing(x, y, z);
							BlockState blockstate = this.level.getBlockState(blockpos);
							FluidState fluidstate = this.level.getFluidState(blockpos);
							if (!this.level.isInWorldBounds(blockpos)) {
								break;
							}

							if ((fluidPredicate.test(fluidstate)) || blockstate.isAir() || blockstate.hasProperty(BlockStateProperties.WATERLOGGED)) {
								set.add(blockpos);
							}

							x += d0 * (double) 0.3F;
							y += d1 * (double) 0.3F;
							z += d2 * (double) 0.3F;
						}
					}
				}
			}
		}

		this.toBlow.addAll(set);
		float f2 = this.radius * 2.0F;
		int k1 = Mth.floor(this.x - (double) f2 - 1.0D);
		int l1 = Mth.floor(this.x + (double) f2 + 1.0D);
		int i2 = Mth.floor(this.y - (double) f2 - 1.0D);
		int i1 = Mth.floor(this.y + (double) f2 + 1.0D);
		int j2 = Mth.floor(this.z - (double) f2 - 1.0D);
		int j1 = Mth.floor(this.z + (double) f2 + 1.0D);
		List<Entity> list = this.level.getEntities(this.source, new AABB((double) k1, (double) i2, (double) j2, (double) l1, (double) i1, (double) j1));
		EventHooks.onExplosionDetonate(this.level, this, list, f2);
		Vec3 vec3 = new Vec3(this.x, this.y, this.z);

		for (int i = 0; i < list.size(); ++i) {
			Entity entity = list.get(i);
			if (!entity.ignoreExplosion(this)) {
				double d12 = Math.sqrt(entity.distanceToSqr(vec3)) / (double) f2;
				if (d12 <= 1.0D) {
					double d5 = entity.getX() - this.x;
					double d7 = (entity instanceof PrimedTnt ? entity.getY() : entity.getEyeY()) - this.y;
					double d9 = entity.getZ() - this.z;
					double d13 = Math.sqrt(d5 * d5 + d7 * d7 + d9 * d9);
					if (d13 != 0.0D) {
						d5 /= d13;
						d7 /= d13;
						d9 /= d13;
						double d14 = (double) getSeenPercent(vec3, entity);
						double d10 = (1.0D - d12) * d14;
						entity.hurt(entity.damageSources().explosion(this), (float) ((int) ((d10 * d10 + d10) / 2.0D * 7.0D * (double) f2 + 1.0D)));
						double d11 = d10;
						if (entity instanceof LivingEntity) {
							d11 = ProtectionEnchantment.getExplosionKnockbackAfterDampener((LivingEntity) entity, d10);
						}

						entity.setDeltaMovement(entity.getDeltaMovement().add(d5 * d11, d7 * d11, d9 * d11));
						if (entity instanceof Player player) {
							if (!player.isSpectator() && (!player.isCreative() || !player.getAbilities().flying)) {
								this.hitPlayers.put(player, new Vec3(d5 * d10, d7 * d10, d9 * d10));
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void finalizeExplosion(boolean spawnParticles) {
		if (!this.level.isClientSide) {
			this.level.playSound(null, BlockPos.containing(this.x, this.y, this.z), SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 4.0F, (1.0F + (this.level.random.nextFloat() - this.level.random.nextFloat()) * 0.2F) * 0.7F);
		}

		if (spawnParticles) {
			if (!(this.radius < 2.0F)) {
				this.level.addParticle(ParticleTypes.EXPLOSION_EMITTER, this.x, this.y, this.z, 1.0D, 0.0D, 0.0D);
			} else {
				this.level.addParticle(ParticleTypes.EXPLOSION, this.x, this.y, this.z, 1.0D, 0.0D, 0.0D);
			}
		}
	}
}
