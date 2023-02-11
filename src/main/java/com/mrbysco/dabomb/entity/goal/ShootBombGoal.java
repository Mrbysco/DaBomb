package com.mrbysco.dabomb.entity.goal;

import com.mrbysco.dabomb.item.ThrowableItem;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class ShootBombGoal extends Goal {
	private final Mob bomber;

	@Nullable
	private LivingEntity target;
	private int attackTime = -1;
	private final double speedModifier;
	private int seeTime;
	private final int attackIntervalMin;
	private final int attackIntervalMax;
	private final float attackRadius;
	private final float attackRadiusSqr;
	private final EntityType<?> projectile;
	private final SoundEvent soundEvent;
	private final float velocity;
	private final float inaccuracy;
	private final float z;

	public ShootBombGoal(Mob bomber, EntityType<?> projectileType,
						 SoundEvent soundEvent, float bombVelocity, float bomInaccuracy, float z) {
		this.bomber = bomber;
		this.projectile = projectileType;
		this.soundEvent = soundEvent;
		this.speedModifier = 1.25D;
		this.attackIntervalMin = 40;
		this.attackIntervalMax = 40;
		this.attackRadius = 20F;
		this.attackRadiusSqr = 400F;
		this.velocity = bombVelocity;
		this.inaccuracy = bomInaccuracy;
		this.z = z;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
	}

	public boolean canUse() {
		LivingEntity livingentity = this.bomber.getTarget();
		if (livingentity != null && livingentity.isAlive()) {
			this.target = livingentity;
			return true;
		} else {
			return false;
		}
	}

	public boolean canContinueToUse() {
		return this.bomber.getMainHandItem().getItem() instanceof ThrowableItem && (this.canUse() || !this.bomber.getNavigation().isDone());
	}

	public void stop() {
		this.target = null;
		this.seeTime = 0;
		this.attackTime = -1;
	}

	public boolean requiresUpdateEveryTick() {
		return true;
	}

	public void tick() {
		double d0 = this.bomber.distanceToSqr(this.target.getX(), this.target.getY(), this.target.getZ());
		boolean flag = this.bomber.getSensing().hasLineOfSight(this.target);
		if (flag) {
			++this.seeTime;
		} else {
			this.seeTime = 0;
		}

		if (!(d0 > (double) this.attackRadiusSqr) && this.seeTime >= 5) {
			this.bomber.getNavigation().stop();
		} else {
			this.bomber.getNavigation().moveTo(this.target, this.speedModifier);
		}

		this.bomber.getLookControl().setLookAt(this.target, 30.0F, 30.0F);
		if (--this.attackTime == 0) {
			if (!flag) {
				return;
			}

			float f = (float) Math.sqrt(d0) / this.attackRadius;
			this.shootBomb();
			this.attackTime = Mth.floor(f * (float) (this.attackIntervalMax - this.attackIntervalMin) + (float) this.attackIntervalMin);
		} else if (this.attackTime < 0) {
			this.attackTime = Mth.floor(Mth.lerp(Math.sqrt(d0) / (double) this.attackRadius, (double) this.attackIntervalMin, (double) this.attackIntervalMax));
		}
	}

	private void shootBomb() {
		Level level = bomber.level;
		if (!level.isClientSide) {
			if (projectile.create(level) instanceof ThrowableItemProjectile bomb) {
				level.playSound((Player) null, bomber.getX(), bomber.getY(), bomber.getZ(),
						soundEvent, SoundSource.HOSTILE, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));

				bomb.setPosRaw(bomber.getX(), bomber.getEyeY() - (double) 0.1F, bomber.getZ());
				bomb.setItem(bomber.getMainHandItem());
				bomb.setOwner(bomber);
				bomb.shootFromRotation(bomber, bomber.getXRot(), bomber.getYRot(), z, velocity, inaccuracy);
				level.addFreshEntity(bomb);
				bomber.getMainHandItem().shrink(1);
			}
		}
	}
}
