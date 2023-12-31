package com.mrbysco.dabomb.handler;

import com.mrbysco.dabomb.config.BombConfig;
import com.mrbysco.dabomb.entity.BeeBomb;
import com.mrbysco.dabomb.entity.BombFish;
import com.mrbysco.dabomb.entity.DirtBomb;
import com.mrbysco.dabomb.entity.DryBomb;
import com.mrbysco.dabomb.entity.EnderBomb;
import com.mrbysco.dabomb.entity.FlowerBomb;
import com.mrbysco.dabomb.entity.LavaBomb;
import com.mrbysco.dabomb.entity.WaterBomb;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.event.level.ExplosionEvent;

import java.util.List;
import java.util.Optional;

public class ExplosionHandler {
	public static void onDetonate(ExplosionEvent.Detonate event) {
		final Explosion explosion = event.getExplosion();
		final Level level = event.getLevel();
		List<BlockPos> affectedBlocks = event.getAffectedBlocks();
		List<Entity> affectedEntities = event.getAffectedEntities();
		if (!level.isClientSide) {
			if (explosion.getDirectSourceEntity() instanceof DirtBomb) {
				for (BlockPos pos : affectedBlocks) {
					BlockState state = level.getBlockState(pos);
					if (state.isAir() || state.canBeReplaced()) {
						level.setBlockAndUpdate(pos, Blocks.DIRT.defaultBlockState());
					}
				}
			} else if (explosion.getDirectSourceEntity() instanceof WaterBomb) {
				for (BlockPos pos : affectedBlocks) {
					BlockState state = level.getBlockState(pos);
					if (state.isAir()) {
						level.setBlockAndUpdate(pos, Blocks.WATER.defaultBlockState());
					} else if (state.getBlock() instanceof SimpleWaterloggedBlock waterloggedBlock) {
						waterloggedBlock.placeLiquid(level, pos, state, Fluids.WATER.getSource(false));
					}
				}
			} else if (explosion.getDirectSourceEntity() instanceof LavaBomb) {
				for (BlockPos pos : affectedBlocks) {
					BlockState state = level.getBlockState(pos);
					if (state.isAir() || state.canBeReplaced()) {
						level.setBlockAndUpdate(pos, Blocks.LAVA.defaultBlockState());
					}
				}
			} else if (explosion.getDirectSourceEntity() instanceof DryBomb) {
				for (BlockPos pos : affectedBlocks) {
					BlockState state = level.getBlockState(pos);
					if (state.getBlock() instanceof LiquidBlock && state.canBeReplaced()) {
						level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
					} else {
						if (state.getBlock() instanceof SimpleWaterloggedBlock waterloggedBlock) {
							waterloggedBlock.pickupBlock(null, level, pos, state);
						}
					}
				}
			} else if (explosion.getDirectSourceEntity() instanceof BombFish bombFish) {
				for (Entity entity : affectedEntities) {
					if (entity instanceof LivingEntity livingEntity && !bombFish.getOwner().getUUID().equals(entity.getUUID())) {
						livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON, 20 * 10, 0), bombFish);
					}
				}
			} else if (explosion.getDirectSourceEntity() instanceof BeeBomb beeBomb) {
				final List<LivingEntity> livingEntities = affectedEntities.stream().filter(entity -> entity instanceof LivingEntity).map(entity -> (LivingEntity) entity).toList();
				for (int i = 0; i < BombConfig.COMMON.beeAmount.get(); i++) {
					Bee bee = EntityType.BEE.create(level);
					if (bee != null) {
						bee.setPosRaw(beeBomb.getX(), beeBomb.getY() + 0.5D, beeBomb.getZ());
						if (!livingEntities.isEmpty()) {
							bee.setPersistentAngerTarget(livingEntities.get(level.random.nextInt(livingEntities.size())).getUUID());
							bee.startPersistentAngerTimer();
						}
						level.addFreshEntity(bee);
					}
				}
			} else if (explosion.getDirectSourceEntity() instanceof FlowerBomb flowerBomb) {
				Optional<HolderSet.Named<Block>> optionalTag = BuiltInRegistries.BLOCK.getTag(BlockTags.SMALL_FLOWERS);
				if (optionalTag.isPresent()) {
					for (BlockPos pos : affectedBlocks) {
						BlockState state = level.getBlockState(pos);
						if (level.getBlockState(pos.below()).is(BlockTags.DIRT) && state.isAir()) {
							optionalTag.get().getRandomElement(level.random).ifPresent(holder -> {
								Block flower = holder.value();
								BlockState flowerState = flower.defaultBlockState();
								if (flower.canSurvive(flowerState, level, pos)) {
									if (level.random.nextDouble() <= BombConfig.COMMON.flowerBombChance.get()) {
										level.setBlockAndUpdate(pos, flowerState);
									}
									if (level.random.nextDouble() <= BombConfig.COMMON.flowerBombBeeChance.get()) {
										Bee bee = EntityType.BEE.create(level);
										if (bee != null) {
											bee.setPosRaw(flowerBomb.getX(), flowerBomb.getY() + 0.5D, flowerBomb.getZ());
											level.addFreshEntity(bee);
										}
									}
								}
							});
						}
					}

				}
			} else if (explosion.getDirectSourceEntity() instanceof EnderBomb bomb) {
				final List<LivingEntity> livingEntities = affectedEntities.stream().filter(entity -> entity instanceof LivingEntity).map(entity -> (LivingEntity) entity).toList();
				for (LivingEntity livingEntity : livingEntities) {
					double targetX = livingEntity.getX() + (level.random.nextDouble() - 0.5D) * 64.0D;
					double targetY = livingEntity.getY() + (double) (level.random.nextInt(64) - 32);
					double targetZ = livingEntity.getZ() + (level.random.nextDouble() - 0.5D) * 64.0D;
					livingEntity.randomTeleport(targetX, targetY, targetZ, true);
					level.playSound((Player) null, targetX, targetY, targetZ, SoundEvents.ENDERMAN_TELEPORT, SoundSource.NEUTRAL, 1.0F, 1.0F);
					livingEntity.hurt(bomb.damageSources().explosion(explosion), 2.0F);
					affectedEntities.remove(livingEntity);
				}
			}
		}
	}
}
