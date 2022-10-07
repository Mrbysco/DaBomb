package com.mrbysco.dabomb.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class BombConfig {
	public static class Common {
		public final ForgeConfigSpec.DoubleValue beeBombRadius;
		public final ForgeConfigSpec.IntValue beeAmount;
		public final ForgeConfigSpec.DoubleValue bombRadius;
		public final ForgeConfigSpec.DoubleValue bombFishRadius;
		public final ForgeConfigSpec.DoubleValue bouncyBombRadius;
		public final ForgeConfigSpec.DoubleValue dirtBombRadius;
		public final ForgeConfigSpec.DoubleValue dryBombRadius;
		public final ForgeConfigSpec.DoubleValue enderBombRadius;
		public final ForgeConfigSpec.DoubleValue flowerBombRadius;
		public final ForgeConfigSpec.DoubleValue flowerBombChance;
		public final ForgeConfigSpec.DoubleValue flowerBombBeeChance;
		public final ForgeConfigSpec.DoubleValue lavaBombRadius;
		public final ForgeConfigSpec.DoubleValue stickyBombRadius;
		public final ForgeConfigSpec.DoubleValue waterBombRadius;


		public final ForgeConfigSpec.DoubleValue bouncyDynamiteRadius;
		public final ForgeConfigSpec.DoubleValue dynamiteRadius;
		public final ForgeConfigSpec.DoubleValue stickyDynamiteRadius;

		public final ForgeConfigSpec.DoubleValue c4Radius;

		Common(ForgeConfigSpec.Builder builder) {
			builder.comment("Bomb settings")
					.push("Bomb");

			beeAmount = builder
					.comment("Defines the amount of bees released by Bee bomb [Default: 10]")
					.defineInRange("beeAmount", 10, 0, Integer.MAX_VALUE);

			beeBombRadius = builder
					.comment("Defines the blast radius of the Bee bomb [Default: 2.0]")
					.defineInRange("beeBombRadius", 2.0D, 0D, 20D);

			bombRadius = builder
					.comment("Defines the blast radius of the regular Bomb [Default: 2.0]")
					.defineInRange("bombRadius", 2.0D, 0D, 20D);

			bombFishRadius = builder
					.comment("Defines the blast radius of the Bomb fish [Default: 2.5]")
					.defineInRange("bombFishRadius", 2.5D, 0D, 20D);

			bouncyBombRadius = builder
					.comment("Defines the blast radius of the Bouncy bomb [Default: 2.0]")
					.defineInRange("bouncyBombRadius", 2.0D, 0D, 20D);

			dirtBombRadius = builder
					.comment("Defines the blast radius of the Dirt bomb [Default: 2.0]")
					.defineInRange("dirtBombRadius", 2.0D, 0D, 20D);

			dryBombRadius = builder
					.comment("Defines the blast radius of the Dry bomb [Default: 1.5]")
					.defineInRange("dryBombRadius", 1.5D, 0D, 20D);

			enderBombRadius = builder
					.comment("Defines the blast radius of the Flower bomb [Default: 2.0]")
					.defineInRange("enderBombRadius", 2.0D, 0D, 20D);

			flowerBombRadius = builder
					.comment("Defines the blast radius of the Flower bomb [Default: 2.0]")
					.defineInRange("flowerBombRadius", 2.0D, 0D, 20D);

			flowerBombChance = builder
					.comment("Defines the chance that the Flower bomb will place a random flower per valid location in the blast radius [Default: 0.3]")
					.defineInRange("flowerBombChance", 0.3D, 0D, 1D);

			flowerBombBeeChance = builder
					.comment("Defines the chance that the Flower bomb will spawn a bee per valid location in the blast radius [Default: 0.01]")
					.defineInRange("flowerBombBeeChance", 0.01D, 0D, 1D);

			lavaBombRadius = builder
					.comment("Defines the blast radius of the Lava bomb [Default: 1.5]")
					.defineInRange("lavaBombRadius", 1.5D, 0D, 20D);

			stickyBombRadius = builder
					.comment("Defines the blast radius of the Sticky bomb [Default: 1.5]")
					.defineInRange("stickyBombRadius", 1.5D, 0D, 20D);

			waterBombRadius = builder
					.comment("Defines the blast radius of the Water bomb [Default: 1.5]")
					.defineInRange("waterBombRadius", 1.5D, 0D, 20D);

			builder.pop();
			builder.comment("Dynamite settings")
					.push("Dynamite");

			bouncyDynamiteRadius = builder
					.comment("Defines the blast radius of Bouncy dynamite [Default: 3.5]")
					.defineInRange("bouncyDynamiteRadius", 3.5D, 0D, 20D);

			dynamiteRadius = builder
					.comment("Defines the blast radius of Dynamite [Default: 3.5]")
					.defineInRange("dynamiteRadius", 3.5D, 0D, 20D);

			stickyDynamiteRadius = builder
					.comment("Defines the blast radius of Sticky Dynamite [Default: 3.5]")
					.defineInRange("stickyDynamiteRadius", 3.5D, 0D, 20D);

			builder.pop();
			builder.comment("Other settings")
					.push("Other");

			c4Radius = builder
					.comment("Defines the blast radius of C4 [Default: 4.5]")
					.defineInRange("c4Radius", 4.5D, 0D, 20D);

			builder.pop();
		}
	}

	public static final ForgeConfigSpec commonSpec;
	public static final Common COMMON;

	static {
		final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
		commonSpec = specPair.getRight();
		COMMON = specPair.getLeft();
	}
}
