package com.mrbysco.dabomb.item;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public class ThrowableItem extends Item {
	private final Supplier<EntityType<?>> entityTypeSupplier;
	private final Supplier<SoundEvent> soundSupplier;
	private final int cooldown;
	private final float z, velocity, inaccuracy;

	public ThrowableItem(Item.Properties properties, Supplier<EntityType<?>> entityTypeSupplier,
						 Supplier<SoundEvent> soundSupplier, int cooldown, float z, float velocity, float inaccuracy) {
		super(properties);
		this.entityTypeSupplier = entityTypeSupplier;
		this.soundSupplier = soundSupplier;
		this.cooldown = cooldown;
		this.z = z;
		this.velocity = velocity;
		this.inaccuracy = inaccuracy;
	}

	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
		ItemStack itemstack = player.getItemInHand(interactionHand);
		if (soundSupplier.get() != null) {
			level.playSound((Player) null, player.getX(), player.getY(), player.getZ(), soundSupplier.get(), SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
		}
		player.getCooldowns().addCooldown(this, cooldown);
		if (!level.isClientSide && entityTypeSupplier.get() != null) {
			if (entityTypeSupplier.get().create(level) instanceof ThrowableItemProjectile projectile) {
				projectile.setPosRaw(player.getX(), player.getEyeY() - (double) 0.1F, player.getZ());
				projectile.setItem(itemstack);
				projectile.setOwner(player);
				projectile.shootFromRotation(player, player.getXRot(), player.getYRot(), z, velocity, inaccuracy);
				level.addFreshEntity(projectile);
			}
		}

		player.awardStat(Stats.ITEM_USED.get(this));
		if (!player.getAbilities().instabuild) {
			itemstack.shrink(1);
		}

		return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
	}
}
