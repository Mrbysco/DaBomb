package com.mrbysco.dabomb.client;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public record DefaultColorTint(int defaultColor) implements ItemTintSource {
	public static final MapCodec<DefaultColorTint> MAP_CODEC = RecordCodecBuilder.mapCodec(
			instance -> instance.group(ExtraCodecs.ARGB_COLOR_CODEC.fieldOf("default").forGetter(DefaultColorTint::defaultColor))
					.apply(instance, DefaultColorTint::new)
	);

	public DefaultColorTint() {
		this(-1270909212);
	}

	@Override
	public int calculate(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity) {
		return defaultColor();
	}

	@Override
	public MapCodec<? extends ItemTintSource> type() {
		return MAP_CODEC;
	}
}
