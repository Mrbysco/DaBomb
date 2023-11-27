package com.mrbysco.dabomb.config;

import com.mrbysco.dabomb.DaBomb;
import com.mrbysco.dabomb.handler.AIHandler;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class BombConfig {
	public static class Common {
		public final ModConfigSpec.DoubleValue beeBombRadius;
		public final ModConfigSpec.IntValue beeAmount;
		public final ModConfigSpec.DoubleValue bombRadius;
		public final ModConfigSpec.DoubleValue bombFishRadius;
		public final ModConfigSpec.DoubleValue bouncyBombRadius;
		public final ModConfigSpec.DoubleValue dirtBombRadius;
		public final ModConfigSpec.DoubleValue dryBombRadius;
		public final ModConfigSpec.DoubleValue enderBombRadius;
		public final ModConfigSpec.DoubleValue flowerBombRadius;
		public final ModConfigSpec.DoubleValue flowerBombChance;
		public final ModConfigSpec.DoubleValue flowerBombBeeChance;
		public final ModConfigSpec.DoubleValue lavaBombRadius;
		public final ModConfigSpec.DoubleValue stickyBombRadius;
		public final ModConfigSpec.DoubleValue waterBombRadius;
		public final ModConfigSpec.DoubleValue clusterBombRadius;
		public final ModConfigSpec.DoubleValue bombFragmentRadius;


		public final ModConfigSpec.DoubleValue bouncyDynamiteRadius;
		public final ModConfigSpec.DoubleValue dynamiteRadius;
		public final ModConfigSpec.DoubleValue stickyDynamiteRadius;

		public final ModConfigSpec.DoubleValue c4Radius;

		public final ModConfigSpec.BooleanValue enableBomberman;
		public final ModConfigSpec.ConfigValue<List<? extends String>> bomberman;

		Common(ModConfigSpec.Builder builder) {
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

			clusterBombRadius = builder
					.comment("Defines the blast radius of the Cluster bomb [Default: 1.0]")
					.defineInRange("clusterBombRadius", 1.0D, 0D, 20D);

			bombFragmentRadius = builder
					.comment("Defines the blast radius of the Cluster Bomb's Fragment [Default: 1.0]")
					.defineInRange("bombFragmentRadius", 1.0D, 0D, 20D);

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
			builder.comment("Bombermen settings")
					.push("Bombermen");

			enableBomberman = builder
					.comment("Defines if there should be a chance for a mob to spawn equipped with a bomb [Default: false]")
					.define("enableBomberman", false);

			bomberman = builder
					.comment("Defines which bombs can be given to which mobs and the chance",
							"The format \"MOBID,BOMB_TYPE,CHANCE\"",
							"Example: \"minecraft:zombie,bomb,0.04\"",
							"Types of bombs allowed: bomb, bouncy_bomb, sticky_bomb, bomb_fish, dirt_bomb",
							"dry_bomb, water_bomb, lava_bomb, bee_bomb, flower_bomb, ender_bomb",
							"cluster_bomb, dynamite, sticky_dynamite, bouncy_dynamite, c4")
					.defineListAllowEmpty(List.of("bomberman"), () -> List.of(
							"minecraft:zombie,c4,0.01",
							"minecraft:zombie,bomb,0.04",
							"minecraft:husk,bouncy_bomb,0.04",
							"minecraft:drowned,bomb_fish,0.04"
					), o -> (o instanceof String));

			builder.pop();
		}
	}

	public static final ModConfigSpec commonSpec;
	public static final Common COMMON;

	static {
		final Pair<Common, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(Common::new);
		commonSpec = specPair.getRight();
		COMMON = specPair.getLeft();
	}

	@SubscribeEvent
	public static void onLoad(final ModConfigEvent.Loading configEvent) {
		DaBomb.LOGGER.debug("Loaded Da Bomb's config file {}", configEvent.getConfig().getFileName());
		AIHandler.refreshCache();
	}

	@SubscribeEvent
	public static void onFileChange(final ModConfigEvent.Reloading configEvent) {
		DaBomb.LOGGER.debug("Da Bomb's config just got changed on the file system!");
		AIHandler.refreshCache();
	}
}