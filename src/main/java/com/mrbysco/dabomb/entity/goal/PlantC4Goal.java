package com.mrbysco.dabomb.entity.goal;

import com.mrbysco.dabomb.entity.C4;
import com.mrbysco.dabomb.item.ThrowableItem;
import com.mrbysco.dabomb.registry.BombRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.EnumSet;
import java.util.List;

public class PlantC4Goal extends Goal {
	private LivingEntity target;
	private Mob bomberMob;
	private int obstructionTick = 0;
	private int plantingTicker = 20;

	private BlockPos obsPos = null;

	public PlantC4Goal(Mob demolitionMob) {
		this.bomberMob = demolitionMob;
		this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
	}

	@Override
	public boolean canUse() {
		this.target = bomberMob.getTarget();

		if (target == null || target.isDeadOrDying() || !bomberMob.getNavigation().isDone() ||
				!(bomberMob.getMainHandItem().getItem() instanceof ThrowableItem))
			return false;

		BlockPos headPos = new BlockPos(bomberMob.getX(), bomberMob.getEyeY(), bomberMob.getZ());
		BlockPos relativePos = headPos.relative(bomberMob.getDirection());
		if (bomberMob.level.getBlockState(relativePos).isAir()) {
			obstructionTick = 0;
		} else {
			obstructionTick++;
		}
		if (obsPos != null)
			bomberMob.getNavigation().moveTo(target, 1.0D);

		return obstructionTick >= 20;
	}

	@Override
	public void start() {
		super.start();
		bomberMob.getNavigation().stop();
		obstructionTick = 0;
	}

	@Override
	public void stop() {
		super.stop();
		obstructionTick = 0;
		plantingTicker = 20;
		this.detonateC4();
	}

	@Override
	public boolean canContinueToUse() {
		return this.target != null && plantingTicker != 0;
	}

	@Override
	public void tick() {
		bomberMob.getNavigation().stop();
		bomberMob.getLookControl().setLookAt(target);

		if (plantingTicker == 20) {
			plantC4();
		}
		if (plantingTicker > 0)
			plantingTicker--;
	}

	private void plantC4() {
		Level level = bomberMob.level;
		if (!level.isClientSide) {
			C4 c4 = BombRegistry.C4_ENTITY.get().create(level);
			if (c4 != null) {
				level.playSound((Player) null, bomberMob.getX(), bomberMob.getY(), bomberMob.getZ(),
						BombRegistry.C4_SHOOT.get(), SoundSource.HOSTILE, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));

				c4.setPosRaw(bomberMob.getX(), bomberMob.getEyeY() - (double) 0.1F, bomberMob.getZ());
				c4.setItem(bomberMob.getMainHandItem());
				c4.setOwner(bomberMob);
				bomberMob.getLookControl().setLookAt(target);
				c4.shootFromRotation(bomberMob, bomberMob.getXRot(), bomberMob.getYRot(), -20.0F, 0.45F, 1.0F);
				level.addFreshEntity(c4);
				bomberMob.getMainHandItem().shrink(1);
			}
		}
	}

	private void detonateC4() {
		Level level = bomberMob.level;
		List<C4> c4s = level.getEntitiesOfClass(C4.class, bomberMob.getBoundingBox().inflate(64D))
				.stream().filter(c4 -> c4.getOwner() != null && c4.getOwner().getUUID().equals(bomberMob.getUUID())).toList();
		if (!c4s.isEmpty()) {
			if (!level.isClientSide) {
				for (C4 c4 : c4s) {
					c4.explode();
				}
			}
		}
	}
}
