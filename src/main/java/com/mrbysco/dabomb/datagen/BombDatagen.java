package com.mrbysco.dabomb.datagen;

import com.mrbysco.dabomb.DaBomb;
import com.mrbysco.dabomb.registry.BombRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.common.data.SoundDefinitionsProvider;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.Objects;
import java.util.function.Consumer;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class BombDatagen {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		ExistingFileHelper helper = event.getExistingFileHelper();

		if (event.includeServer()) {
			generator.addProvider(new Recipes(generator));
		}
		if (event.includeClient()) {
			generator.addProvider(new Language(generator));
			generator.addProvider(new SoundDefinitions(generator, helper));
			generator.addProvider(new ItemModels(generator, helper));
		}
	}

	private static class Recipes extends RecipeProvider {
		public Recipes(DataGenerator gen) {
			super(gen);
		}

		@Override
		protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
			ShapedRecipeBuilder.shaped(BombRegistry.BOMB_ITEM.get(), 2)
					.pattern(" G ").pattern("GSG").pattern(" G ")
					.define('G', Ingredient.of(Tags.Items.GUNPOWDER))
					.define('S', Ingredient.of(Tags.Items.SAND))
					.unlockedBy("has_gunpowder", has(Tags.Items.GUNPOWDER))
					.save(consumer);
			ShapedRecipeBuilder.shaped(BombRegistry.DIRT_BOMB_ITEM.get())
					.pattern(" D ").pattern("DBD").pattern(" D ")
					.define('D', Ingredient.of(ItemTags.DIRT))
					.define('B', BombRegistry.BOMB_ITEM.get())
					.unlockedBy("has_bomb", has(BombRegistry.BOMB_ITEM.get()))
					.save(consumer);
			ShapedRecipeBuilder.shaped(BombRegistry.FLOWER_BOMB_ITEM.get())
					.pattern(" F ").pattern("FBF").pattern(" F ")
					.define('F', Ingredient.of(ItemTags.SMALL_FLOWERS))
					.define('B', BombRegistry.BOMB_ITEM.get())
					.unlockedBy("has_bomb", has(BombRegistry.BOMB_ITEM.get()))
					.save(consumer);
			ShapedRecipeBuilder.shaped(BombRegistry.LAVA_BOMB_ITEM.get())
					.pattern(" G ").pattern("GLG").pattern(" G ")
					.define('G', Ingredient.of(Tags.Items.GUNPOWDER))
					.define('L', Items.LAVA_BUCKET)
					.unlockedBy("has_lava_bucket", has(Items.LAVA_BUCKET))
					.save(consumer);
			ShapedRecipeBuilder.shaped(BombRegistry.WATER_BOMB_ITEM.get())
					.pattern(" G ").pattern("GWG").pattern(" G ")
					.define('G', Ingredient.of(Tags.Items.GUNPOWDER))
					.define('W', Items.WATER_BUCKET)
					.unlockedBy("has_water_bucket", has(Items.WATER_BUCKET))
					.save(consumer);
			ShapelessRecipeBuilder.shapeless(BombRegistry.STICKY_BOMB_ITEM.get())
					.requires(BombRegistry.BOMB_ITEM.get()).requires(Items.HONEY_BOTTLE)
					.unlockedBy("has_bomb", has(BombRegistry.BOMB_ITEM.get()))
					.save(consumer);
			ShapelessRecipeBuilder.shapeless(BombRegistry.BOUNCY_BOMB_ITEM.get())
					.requires(BombRegistry.BOMB_ITEM.get()).requires(Ingredient.of(Tags.Items.SLIMEBALLS))
					.unlockedBy("has_bomb", has(BombRegistry.BOMB_ITEM.get()))
					.save(consumer);
			ShapelessRecipeBuilder.shapeless(BombRegistry.DRY_BOMB_ITEM.get())
					.requires(BombRegistry.BOMB_ITEM.get()).requires(Items.SPONGE)
					.unlockedBy("has_bomb", has(BombRegistry.BOMB_ITEM.get()))
					.save(consumer);
			ShapedRecipeBuilder.shaped(BombRegistry.BOMB_FISH_ITEM.get(), 2)
					.pattern(" P ").pattern("GSG").pattern(" G ")
					.define('G', Ingredient.of(Tags.Items.GUNPOWDER))
					.define('S', Ingredient.of(Tags.Items.SAND))
					.define('P', Items.PUFFERFISH)
					.unlockedBy("has_gunpowder", has(Tags.Items.GUNPOWDER))
					.save(consumer);
			ShapedRecipeBuilder.shaped(BombRegistry.BEE_BOMB_ITEM.get(), 2)
					.pattern("GBG").pattern(" G ")
					.define('G', Ingredient.of(Tags.Items.GUNPOWDER))
					.define('B', Items.BEEHIVE)
					.unlockedBy("has_gunpowder", has(Tags.Items.GUNPOWDER))
					.save(consumer);
			ShapedRecipeBuilder.shaped(BombRegistry.ENDER_BOMB_ITEM.get(), 2)
					.pattern("GEG").pattern(" G ")
					.define('G', Ingredient.of(Tags.Items.GUNPOWDER))
					.define('E', Ingredient.of(Tags.Items.ENDER_PEARLS))
					.unlockedBy("has_gunpowder", has(Tags.Items.GUNPOWDER))
					.save(consumer);

			ShapedRecipeBuilder.shaped(BombRegistry.DYNAMITE_ITEM.get(), 3)
					.pattern(" # ").pattern("GSG").pattern("GGG")
					.define('G', Ingredient.of(Tags.Items.GUNPOWDER))
					.define('S', Ingredient.of(Tags.Items.SAND))
					.define('#', Ingredient.of(Tags.Items.STRING))
					.unlockedBy("has_gunpowder", has(Tags.Items.GUNPOWDER))
					.save(consumer);
			ShapelessRecipeBuilder.shapeless(BombRegistry.STICKY_DYNAMITE_ITEM.get())
					.requires(BombRegistry.DYNAMITE_ITEM.get()).requires(Items.HONEY_BOTTLE)
					.unlockedBy("has_dynamite", has(BombRegistry.DYNAMITE_ITEM.get()))
					.save(consumer);
			ShapelessRecipeBuilder.shapeless(BombRegistry.BOUNCY_DYNAMITE_ITEM.get())
					.requires(BombRegistry.DYNAMITE_ITEM.get()).requires(Ingredient.of(Tags.Items.SLIMEBALLS))
					.unlockedBy("has_dynamite", has(BombRegistry.DYNAMITE_ITEM.get()))
					.save(consumer);

			ShapedRecipeBuilder.shaped(BombRegistry.C4_ITEM.get(), 2)
					.pattern(" R ").pattern("GSG").pattern("GHG")
					.define('G', Ingredient.of(Tags.Items.GUNPOWDER))
					.define('S', Ingredient.of(Tags.Items.SAND))
					.define('R', Ingredient.of(Tags.Items.DUSTS_REDSTONE))
					.define('H', Items.HONEY_BOTTLE)
					.unlockedBy("has_gunpowder", has(Tags.Items.GUNPOWDER))
					.save(consumer);
			ShapedRecipeBuilder.shaped(BombRegistry.REMOTE.get(), 1)
					.pattern(" RR").pattern("II ").pattern("II ")
					.define('I', Ingredient.of(Tags.Items.INGOTS_IRON))
					.define('R', Ingredient.of(Tags.Items.DUSTS_REDSTONE))
					.unlockedBy("has_redstone", has(Tags.Items.DUSTS_REDSTONE))
					.save(consumer);
		}
	}

	private static class Language extends LanguageProvider {
		public Language(DataGenerator gen) {
			super(gen, DaBomb.MOD_ID, "en_us");
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
		}

		public void addSubtitle(RegistryObject<SoundEvent> sound, String name) {
			this.addSubtitle(sound.get(), name);
		}

		public void addSubtitle(SoundEvent sound, String name) {
			String path = DaBomb.MOD_ID + ".subtitle." + sound.getLocation().getPath();
			this.add(path, name);
		}
	}

	private static class SoundDefinitions extends SoundDefinitionsProvider {
		public SoundDefinitions(DataGenerator gen, ExistingFileHelper helper) {
			super(gen, DaBomb.MOD_ID, helper);
		}

		@Override
		public void registerSounds() {
			this.add(BombRegistry.BOMB_SHOOT, definition()
					.subtitle(modSubtitle(BombRegistry.BOMB_SHOOT.getId()))
					.with(sound(new ResourceLocation("random/bow"))));
			this.add(BombRegistry.DYNAMITE_SHOOT, definition()
					.subtitle(modSubtitle(BombRegistry.DYNAMITE_SHOOT.getId()))
					.with(sound(new ResourceLocation("random/bow"))));
			this.add(BombRegistry.C4_SHOOT, definition()
					.subtitle(modSubtitle(BombRegistry.C4_SHOOT.getId()))
					.with(sound(new ResourceLocation("random/bow"))));

			this.add(BombRegistry.BOMB_PLANTED, definition()
					.subtitle(modSubtitle(BombRegistry.BOMB_PLANTED.getId()))
					.with(sound(new ResourceLocation(DaBomb.MOD_ID, "bomb_planted"))));
			this.add(BombRegistry.BOMB_DEFUSED, definition()
					.subtitle(modSubtitle(BombRegistry.BOMB_DEFUSED.getId()))
					.with(sound(new ResourceLocation(DaBomb.MOD_ID, "bomb_defused"))));
		}

		public String modSubtitle(ResourceLocation id) {
			return DaBomb.MOD_ID + ".subtitle." + id.getPath();
		}
	}

	private static class ItemModels extends ItemModelProvider {
		public ItemModels(DataGenerator gen, ExistingFileHelper helper) {
			super(gen, DaBomb.MOD_ID, helper);
		}

		@Override
		protected void registerModels() {
			BombRegistry.ITEMS.getEntries().stream()
					.map(RegistryObject::get)
					.forEach(item -> {
						String path = Objects.requireNonNull(item.getRegistryName()).getPath();
						if (path.equals("water_bomb")) {
							withExistingParent(path, mcLoc("item/generated"))
									.texture("layer0", modLoc(ITEM_FOLDER + "/" + "fluid_bomb_overlay"))
									.texture("layer1", modLoc(ITEM_FOLDER + "/" + "fluid_bomb"));
						} else {
							singleTexture(path, mcLoc("item/generated"), "layer0", modLoc("item/" + path));
						}
					});
		}
	}
}
