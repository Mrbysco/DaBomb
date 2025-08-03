package com.mrbysco.dabomb.datagen;

import com.mrbysco.dabomb.DaBomb;
import com.mrbysco.dabomb.client.DefaultColorTint;
import com.mrbysco.dabomb.registry.BombRegistry;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class BombDatagen {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent.Client event) {
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = generator.getPackOutput();
		CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

		generator.addProvider(true, new BombRecipes.Runner(packOutput, lookupProvider));

		generator.addProvider(true, new BombLanguage(packOutput));
		generator.addProvider(true, new BombSoundDefinitions(packOutput));
		generator.addProvider(true, new BombModels(packOutput));
	}

	private static class BombRecipes extends RecipeProvider {
		public BombRecipes(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
			super(provider, recipeOutput);
		}

		@Override
		protected void buildRecipes() {
			shaped(RecipeCategory.COMBAT, BombRegistry.BOMB_ITEM.get(), 2)
					.pattern(" G ").pattern("GSG").pattern(" G ")
					.define('G', Tags.Items.GUNPOWDERS)
					.define('S', Tags.Items.SANDS)
					.unlockedBy("has_gunpowder", has(Tags.Items.GUNPOWDERS))
					.save(this.output);
			shaped(RecipeCategory.COMBAT, BombRegistry.DIRT_BOMB_ITEM.get())
					.pattern(" D ").pattern("DBD").pattern(" D ")
					.define('D', ItemTags.DIRT)
					.define('B', BombRegistry.BOMB_ITEM.get())
					.unlockedBy("has_bomb", has(BombRegistry.BOMB_ITEM.get()))
					.save(this.output);
			shaped(RecipeCategory.COMBAT, BombRegistry.FLOWER_BOMB_ITEM.get())
					.pattern(" F ").pattern("FBF").pattern(" F ")
					.define('F', ItemTags.SMALL_FLOWERS)
					.define('B', BombRegistry.BOMB_ITEM.get())
					.unlockedBy("has_bomb", has(BombRegistry.BOMB_ITEM.get()))
					.save(this.output);
			shaped(RecipeCategory.COMBAT, BombRegistry.LAVA_BOMB_ITEM.get())
					.pattern(" G ").pattern("GLG").pattern(" G ")
					.define('G', Tags.Items.GUNPOWDERS)
					.define('L', Items.LAVA_BUCKET)
					.unlockedBy("has_lava_bucket", has(Items.LAVA_BUCKET))
					.save(this.output);
			shaped(RecipeCategory.COMBAT, BombRegistry.WATER_BOMB_ITEM.get())
					.pattern(" G ").pattern("GWG").pattern(" G ")
					.define('G', Tags.Items.GUNPOWDERS)
					.define('W', Items.WATER_BUCKET)
					.unlockedBy("has_water_bucket", has(Items.WATER_BUCKET))
					.save(this.output);
			shapeless(RecipeCategory.COMBAT, BombRegistry.STICKY_BOMB_ITEM.get())
					.requires(BombRegistry.BOMB_ITEM.get()).requires(Items.HONEY_BOTTLE)
					.unlockedBy("has_bomb", has(BombRegistry.BOMB_ITEM.get()))
					.save(this.output);
			shapeless(RecipeCategory.COMBAT, BombRegistry.BOUNCY_BOMB_ITEM.get())
					.requires(BombRegistry.BOMB_ITEM.get()).requires(Tags.Items.SLIME_BALLS)
					.unlockedBy("has_bomb", has(BombRegistry.BOMB_ITEM.get()))
					.save(this.output);
			shapeless(RecipeCategory.COMBAT, BombRegistry.DRY_BOMB_ITEM.get())
					.requires(BombRegistry.BOMB_ITEM.get()).requires(Items.SPONGE)
					.unlockedBy("has_bomb", has(BombRegistry.BOMB_ITEM.get()))
					.save(this.output);
			shaped(RecipeCategory.COMBAT, BombRegistry.BOMB_FISH_ITEM.get(), 2)
					.pattern(" P ").pattern("GSG").pattern(" G ")
					.define('G', Tags.Items.GUNPOWDERS)
					.define('S', Tags.Items.SANDS)
					.define('P', Items.PUFFERFISH)
					.unlockedBy("has_gunpowder", has(Tags.Items.GUNPOWDERS))
					.save(this.output);
			shaped(RecipeCategory.COMBAT, BombRegistry.BEE_BOMB_ITEM.get(), 2)
					.pattern("GBG").pattern(" G ")
					.define('G', Tags.Items.GUNPOWDERS)
					.define('B', Items.BEEHIVE)
					.unlockedBy("has_gunpowder", has(Tags.Items.GUNPOWDERS))
					.save(this.output);
			shaped(RecipeCategory.COMBAT, BombRegistry.ENDER_BOMB_ITEM.get(), 2)
					.pattern("GEG").pattern(" G ")
					.define('G', Tags.Items.GUNPOWDERS)
					.define('E', Tags.Items.ENDER_PEARLS)
					.unlockedBy("has_gunpowder", has(Tags.Items.GUNPOWDERS))
					.save(this.output);
			shaped(RecipeCategory.COMBAT, BombRegistry.CLUSTER_BOMB_ITEM.get(), 1)
					.pattern("GEG").pattern(" G ")
					.define('G', Tags.Items.GUNPOWDERS)
					.define('E', Ingredient.of(BombRegistry.BOMB_ITEM.get()))
					.unlockedBy("has_gunpowder", has(Tags.Items.GUNPOWDERS))
					.save(this.output);

			shaped(RecipeCategory.COMBAT, BombRegistry.DYNAMITE_ITEM.get(), 3)
					.pattern(" # ").pattern("GSG").pattern("GGG")
					.define('G', Tags.Items.GUNPOWDERS)
					.define('S', Tags.Items.SANDS)
					.define('#', Tags.Items.STRINGS)
					.unlockedBy("has_gunpowder", has(Tags.Items.GUNPOWDERS))
					.save(this.output);
			shapeless(RecipeCategory.COMBAT, BombRegistry.STICKY_DYNAMITE_ITEM.get())
					.requires(BombRegistry.DYNAMITE_ITEM.get()).requires(Items.HONEY_BOTTLE)
					.unlockedBy("has_dynamite", has(BombRegistry.DYNAMITE_ITEM.get()))
					.save(this.output);
			shapeless(RecipeCategory.COMBAT, BombRegistry.BOUNCY_DYNAMITE_ITEM.get())
					.requires(BombRegistry.DYNAMITE_ITEM.get()).requires(Tags.Items.SLIME_BALLS)
					.unlockedBy("has_dynamite", has(BombRegistry.DYNAMITE_ITEM.get()))
					.save(this.output);

			shaped(RecipeCategory.COMBAT, BombRegistry.C4_ITEM.get(), 2)
					.pattern(" R ").pattern("GSG").pattern("GHG")
					.define('G', Tags.Items.GUNPOWDERS)
					.define('S', Tags.Items.SANDS)
					.define('R', Tags.Items.DUSTS_REDSTONE)
					.define('H', Items.HONEY_BOTTLE)
					.unlockedBy("has_gunpowder", has(Tags.Items.GUNPOWDERS))
					.save(this.output);
			shaped(RecipeCategory.COMBAT, BombRegistry.REMOTE.get(), 1)
					.pattern(" RR").pattern("II ").pattern("II ")
					.define('I', Tags.Items.INGOTS_IRON)
					.define('R', Tags.Items.DUSTS_REDSTONE)
					.unlockedBy("has_redstone", has(Tags.Items.DUSTS_REDSTONE))
					.save(this.output);
		}

		public static class Runner extends RecipeProvider.Runner {
			public Runner(PackOutput output, CompletableFuture<Provider> completableFuture) {
				super(output, completableFuture);
			}

			@Override
			protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
				return new BombRecipes(provider, recipeOutput);
			}

			@Override
			public String getName() {
				return "DaBomb Recipes";
			}
		}
	}

	private static class BombLanguage extends LanguageProvider {
		public BombLanguage(PackOutput packOutput) {
			super(packOutput, DaBomb.MOD_ID, "en_us");
		}

		@Override
		protected void addTranslations() {
			this.add("itemGroup.dabomb", "Da Bomb");

			this.addItem(BombRegistry.BOMB_ITEM, "Bomb");
			this.addEntityType(BombRegistry.BOMB, "Bomb");
			this.addItem(BombRegistry.BOUNCY_BOMB_ITEM, "Bouncy Bomb");
			this.addEntityType(BombRegistry.BOUNCY_BOMB, "Bouncy Bomb");
			this.addItem(BombRegistry.BOMB_FISH_ITEM, "Bomb Fish");
			this.addEntityType(BombRegistry.BOMB_FISH, "Bomb Fish");
			this.addItem(BombRegistry.STICKY_BOMB_ITEM, "Sticky Bomb");
			this.addEntityType(BombRegistry.STICKY_BOMB, "Sticky Bomb");
			this.addItem(BombRegistry.DIRT_BOMB_ITEM, "Dirt Bomb");
			this.addEntityType(BombRegistry.DIRT_BOMB, "Dirt Bomb");
			this.addItem(BombRegistry.DRY_BOMB_ITEM, "Dry Bomb");
			this.addEntityType(BombRegistry.DRY_BOMB, "Dry Bomb");
			this.addItem(BombRegistry.WATER_BOMB_ITEM, "Water Bomb");
			this.addEntityType(BombRegistry.WATER_BOMB, "Water Bomb");
			this.addItem(BombRegistry.LAVA_BOMB_ITEM, "Lava Bomb");
			this.addEntityType(BombRegistry.LAVA_BOMB, "Lava Bomb");
			this.addItem(BombRegistry.BEE_BOMB_ITEM, "Bee Bomb");
			this.addEntityType(BombRegistry.BEE_BOMB, "Bee Bomb");
			this.addItem(BombRegistry.FLOWER_BOMB_ITEM, "Flower Bomb");
			this.addEntityType(BombRegistry.FLOWER_BOMB, "Flower Bomb");
			this.addItem(BombRegistry.ENDER_BOMB_ITEM, "Ender Bomb");
			this.addEntityType(BombRegistry.ENDER_BOMB, "Ender Bomb");
			this.addItem(BombRegistry.CLUSTER_BOMB_ITEM, "Cluster Bomb");
			this.addEntityType(BombRegistry.CLUSTER_BOMB, "Cluster Bomb");

			this.addItem(BombRegistry.DYNAMITE_ITEM, "Dynamite");
			this.addEntityType(BombRegistry.DYNAMITE, "Dynamite");
			this.addItem(BombRegistry.BOUNCY_DYNAMITE_ITEM, "Bouncy Dynamite");
			this.addEntityType(BombRegistry.BOUNCY_DYNAMITE, "Bouncy Dynamite");
			this.addItem(BombRegistry.STICKY_DYNAMITE_ITEM, "Sticky Dynamite");
			this.addEntityType(BombRegistry.STICKY_DYNAMITE, "Sticky Dynamite");

			this.addItem(BombRegistry.C4_ITEM, "C4");
			this.addEntityType(BombRegistry.C4_ENTITY, "C4");
			this.addItem(BombRegistry.REMOTE, "Remote");

			this.addSubtitle(BombRegistry.BOMB_SHOOT, "Bomb thrown");
			this.addSubtitle(BombRegistry.DYNAMITE_SHOOT, "Dynamite thrown");
			this.addSubtitle(BombRegistry.C4_SHOOT, "C4 thrown");

			this.addSubtitle(BombRegistry.BOMB_PLANTED, "Bomb has been planted");
			this.addSubtitle(BombRegistry.BOMB_DEFUSED, "Bomb has been defused");

			this.addConfig("bomb", "Bomb", "Bomb settings");
			this.addConfig("beeAmount", "Bee Bomb Amount", "Defines the amount of bees released by Bee bomb");
			this.addConfig("beeBombRadius", "Bee Bomb Radius", "Defines the blast radius of the Bee bomb");
			this.addConfig("bombRadius", "Bomb Radius", "Defines the blast radius of the regular Bomb");
			this.addConfig("bombFishRadius", "Bomb Fish Radius", "Defines the blast radius of the Bomb fish");
			this.addConfig("bouncyBombRadius", "Bouncy Bomb Radius", "Defines the blast radius of the Bouncy bomb");
			this.addConfig("dirtBombRadius", "Dirt Bomb Radius", "Defines the blast radius of the Dirt bomb");
			this.addConfig("dryBombRadius", "Dry Bomb Radius", "Defines the blast radius of the Dry bomb");
			this.addConfig("enderBombRadius", "Ender Bomb Radius", "Defines the blast radius of the Ender bomb");
			this.addConfig("flowerBombRadius", "Flower Bomb Radius", "Defines the blast radius of the Flower bomb");
			this.addConfig("flowerBombChance", "Flower Bomb Chance", "Defines the chance that the Flower bomb will place a random flower per valid location in the blast radius");
			this.addConfig("flowerBombBeeChance", "Flower Bomb Bee Chance", "Defines the chance that the Flower bomb will spawn a bee per valid location in the blast radius");
			this.addConfig("lavaBombRadius", "Lava Bomb Radius", "Defines the blast radius of the Lava bomb");
			this.addConfig("stickyBombRadius", "Sticky Bomb Radius", "Defines the blast radius of the Sticky bomb");
			this.addConfig("waterBombRadius", "Water Bomb Radius", "Defines the blast radius of the Water bomb");
			this.addConfig("clusterBombRadius", "Cluster Bomb Radius", "Defines the blast radius of the Cluster bomb");
			this.addConfig("bombFragmentRadius", "Bomb Fragment Radius", "Defines the blast radius of the Cluster Bomb's Fragment");

			this.addConfig("dynamite", "Dynamite", "Dynamite settings");
			this.addConfig("dynamiteRadius", "Dynamite Radius", "Defines the blast radius of the Dynamite");
			this.addConfig("bouncyDynamiteRadius", "Bouncy Dynamite Radius", "Defines the blast radius of the Bouncy Dynamite");
			this.addConfig("stickyDynamiteRadius", "Sticky Dynamite Radius", "Defines the blast radius of the Sticky Dynamite");

			this.addConfig("other", "Other", "Other settings");
			this.addConfig("c4Radius", "C4 Radius", "Defines the blast radius of the C4");

			this.addConfig("bomberman", "Bomberman", "Bomberman settings");
			this.addConfig("enableBomberman", "Enable Bomberman", "Enables the Bomberman game mode");
			this.addConfig("bombermanList", "Bomberman List", "Defines the list of bombs that can be used in Bomberman");
		}

		public void addSubtitle(Supplier<SoundEvent> sound, String name) {
			this.addSubtitle(sound.get(), name);
		}

		public void addSubtitle(SoundEvent sound, String name) {
			String path = DaBomb.MOD_ID + ".subtitle." + sound.location().getPath();
			this.add(path, name);
		}

		/**
		 * Add the translation for a config entry
		 *
		 * @param path        The path of the config entry
		 * @param name        The name of the config entry
		 * @param description The description of the config entry (optional in case of targeting "title" or similar entries that have no tooltip)
		 */
		private void addConfig(String path, String name, @Nullable String description) {
			this.add(DaBomb.MOD_ID + ".configuration." + path, name);
			if (description != null && !description.isEmpty())
				this.add(DaBomb.MOD_ID + ".configuration." + path + ".tooltip", description);
		}
	}

	private static class BombSoundDefinitions extends SoundDefinitionsProvider {
		public BombSoundDefinitions(PackOutput packOutput) {
			super(packOutput, DaBomb.MOD_ID);
		}

		@Override
		public void registerSounds() {
			this.add(BombRegistry.BOMB_SHOOT, definition()
					.subtitle(modSubtitle(BombRegistry.BOMB_SHOOT.getId()))
					.with(sound(ResourceLocation.withDefaultNamespace("random/bow"))));
			this.add(BombRegistry.DYNAMITE_SHOOT, definition()
					.subtitle(modSubtitle(BombRegistry.DYNAMITE_SHOOT.getId()))
					.with(sound(ResourceLocation.withDefaultNamespace("random/bow"))));
			this.add(BombRegistry.C4_SHOOT, definition()
					.subtitle(modSubtitle(BombRegistry.C4_SHOOT.getId()))
					.with(sound(ResourceLocation.withDefaultNamespace("random/bow"))));

			this.add(BombRegistry.BOMB_PLANTED, definition()
					.subtitle(modSubtitle(BombRegistry.BOMB_PLANTED.getId()))
					.with(sound(ResourceLocation.fromNamespaceAndPath(DaBomb.MOD_ID, "bomb_planted"))));
			this.add(BombRegistry.BOMB_DEFUSED, definition()
					.subtitle(modSubtitle(BombRegistry.BOMB_DEFUSED.getId()))
					.with(sound(ResourceLocation.fromNamespaceAndPath(DaBomb.MOD_ID, "bomb_defused"))));
		}

		public String modSubtitle(ResourceLocation id) {
			return DaBomb.MOD_ID + ".subtitle." + id.getPath();
		}
	}

	private static class BombModels extends ModelProvider {
		public BombModels(PackOutput packOutput) {
			super(packOutput, DaBomb.MOD_ID);
		}

		@Override
		protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
			BombRegistry.ITEMS.getEntries()
					.forEach(deferredItem -> {
						if (deferredItem.getId().equals(BombRegistry.WATER_BOMB_ITEM.getId())) {
							itemModels.generateItemWithTintedOverlay(deferredItem.get(), new DefaultColorTint());
						} else {
							itemModels.generateFlatItem(deferredItem.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
						}
					});
		}
	}
}
