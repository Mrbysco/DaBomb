package com.mrbysco.dabomb.item;

import com.mrbysco.dabomb.registry.BombRegistry;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public class C4Item extends ThrowableItem {

	public C4Item(Properties properties, Supplier<EntityType<?>> entityTypeSupplier,
				  Supplier<SoundEvent> soundSupplier, int cooldown, float z, float velocity, float inaccuracy) {
		super(properties, entityTypeSupplier, soundSupplier, cooldown, z, velocity, inaccuracy);
	}


	@Override
	public InteractionResult use(Level level, Player player, InteractionHand hand) {
		if (player.getInventory().contains(new ItemStack(BombRegistry.REMOTE.get()))) {
			super.use(level, player, hand);
		}

		return InteractionResult.PASS;
	}
}
