package com.mrbysco.dabomb;

import net.minecraft.core.particles.ExplosionParticleInfo;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.random.WeightedList;

public class Reference {
	public static final WeightedList<ExplosionParticleInfo> DEFAULT_EXPLOSION_BLOCK_PARTICLES = WeightedList.<ExplosionParticleInfo>builder()
			.add(new ExplosionParticleInfo(ParticleTypes.POOF, 0.5F, 1.0F))
			.add(new ExplosionParticleInfo(ParticleTypes.SMOKE, 1.0F, 1.0F))
			.build();
}
