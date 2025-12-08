package com.mrbysco.dabomb.item;

import com.mrbysco.dabomb.entity.C4;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RemoteItem extends Item {
	public RemoteItem(Properties properties) {
		super(properties);
	}

	@NotNull
	@Override
	public InteractionResult use(Level level, Player player, @NotNull InteractionHand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
		player.getCooldowns().addCooldown(itemstack, 20);
		level.playSound(null, player.blockPosition(), SoundEvents.UI_BUTTON_CLICK.value(), SoundSource.PLAYERS, 1.0F, 1.0F);

		List<C4> c4s = level.getEntitiesOfClass(C4.class, player.getBoundingBox().inflate(64D))
				.stream().filter(c4 -> c4.getOwner() != null && c4.getOwner().getUUID().equals(player.getUUID())).toList();
		if (!c4s.isEmpty()) {
			if (!level.isClientSide()) {
				for (C4 c4 : c4s) {
					c4.explode();
				}
			}
			player.awardStat(Stats.ITEM_USED.get(this));
			return InteractionResult.SUCCESS;
		}

		return super.use(level, player, hand);
	}
}