package com.mrbysco.dabomb.registry;

import com.mrbysco.dabomb.DaBomb;
import com.mrbysco.dabomb.entity.BeeBomb;
import com.mrbysco.dabomb.entity.Bomb;
import com.mrbysco.dabomb.entity.BombFish;
import com.mrbysco.dabomb.entity.BombFragment;
import com.mrbysco.dabomb.entity.BouncyBomb;
import com.mrbysco.dabomb.entity.BouncyDynamite;
import com.mrbysco.dabomb.entity.C4;
import com.mrbysco.dabomb.entity.ClusterBomb;
import com.mrbysco.dabomb.entity.DirtBomb;
import com.mrbysco.dabomb.entity.DryBomb;
import com.mrbysco.dabomb.entity.Dynamite;
import com.mrbysco.dabomb.entity.EnderBomb;
import com.mrbysco.dabomb.entity.FlowerBomb;
import com.mrbysco.dabomb.entity.LavaBomb;
import com.mrbysco.dabomb.entity.StickyBomb;
import com.mrbysco.dabomb.entity.StickyDynamite;
import com.mrbysco.dabomb.entity.WaterBomb;
import com.mrbysco.dabomb.item.C4Item;
import com.mrbysco.dabomb.item.RemoteItem;
import com.mrbysco.dabomb.item.ThrowableItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BombRegistry {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DaBomb.MOD_ID);
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, DaBomb.MOD_ID);
	public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, DaBomb.MOD_ID);

	//Bombs
	public static final RegistryObject<Item> BOMB_ITEM = ITEMS.register("bomb", () -> new ThrowableItem(new Item.Properties().tab(DaBomb.tab),
			BombRegistry.BOMB::get, BombRegistry.BOMB_SHOOT, 8, -20.0F, 0.45F, 1.0F));
	public static final RegistryObject<Item> BOUNCY_BOMB_ITEM = ITEMS.register("bouncy_bomb", () -> new ThrowableItem(new Item.Properties().tab(DaBomb.tab),
			BombRegistry.BOUNCY_BOMB::get, BombRegistry.BOMB_SHOOT, 8, -20.0F, 0.45F, 1.0F));
	public static final RegistryObject<Item> STICKY_BOMB_ITEM = ITEMS.register("sticky_bomb", () -> new ThrowableItem(new Item.Properties().tab(DaBomb.tab),
			BombRegistry.STICKY_BOMB::get, BombRegistry.BOMB_SHOOT, 8, -20.0F, 0.45F, 1.0F));
	public static final RegistryObject<Item> BOMB_FISH_ITEM = ITEMS.register("bomb_fish", () -> new ThrowableItem(new Item.Properties().tab(DaBomb.tab),
			BombRegistry.BOMB_FISH::get, BombRegistry.BOMB_SHOOT, 8, -20.0F, 0.45F, 1.0F));
	public static final RegistryObject<Item> DIRT_BOMB_ITEM = ITEMS.register("dirt_bomb", () -> new ThrowableItem(new Item.Properties().tab(DaBomb.tab),
			BombRegistry.DIRT_BOMB::get, BombRegistry.BOMB_SHOOT, 8, -10.0F, 0.65F, 1.0F));
	public static final RegistryObject<Item> DRY_BOMB_ITEM = ITEMS.register("dry_bomb", () -> new ThrowableItem(new Item.Properties().tab(DaBomb.tab),
			BombRegistry.DRY_BOMB::get, BombRegistry.BOMB_SHOOT, 8, -10.0F, 0.65F, 1.0F));
	public static final RegistryObject<Item> WATER_BOMB_ITEM = ITEMS.register("water_bomb", () -> new ThrowableItem(new Item.Properties().tab(DaBomb.tab),
			BombRegistry.WATER_BOMB::get, BombRegistry.BOMB_SHOOT, 8, -10.0F, 0.65F, 1.0F));
	public static final RegistryObject<Item> LAVA_BOMB_ITEM = ITEMS.register("lava_bomb", () -> new ThrowableItem(new Item.Properties().tab(DaBomb.tab),
			BombRegistry.LAVA_BOMB::get, BombRegistry.BOMB_SHOOT, 8, -10.0F, 0.65F, 1.0F));
	public static final RegistryObject<Item> BEE_BOMB_ITEM = ITEMS.register("bee_bomb", () -> new ThrowableItem(new Item.Properties().tab(DaBomb.tab),
			BombRegistry.BEE_BOMB::get, BombRegistry.BOMB_SHOOT, 8, -20.0F, 0.45F, 1.0F));
	public static final RegistryObject<Item> FLOWER_BOMB_ITEM = ITEMS.register("flower_bomb", () -> new ThrowableItem(new Item.Properties().tab(DaBomb.tab),
			BombRegistry.FLOWER_BOMB::get, BombRegistry.BOMB_SHOOT, 8, -20.0F, 0.45F, 1.0F));
	public static final RegistryObject<Item> ENDER_BOMB_ITEM = ITEMS.register("ender_bomb", () -> new ThrowableItem(new Item.Properties().tab(DaBomb.tab),
			BombRegistry.ENDER_BOMB::get, BombRegistry.BOMB_SHOOT, 8, -20.0F, 0.45F, 1.0F));
	public static final RegistryObject<Item> CLUSTER_BOMB_ITEM = ITEMS.register("cluster_bomb", () -> new ThrowableItem(new Item.Properties().tab(DaBomb.tab),
			BombRegistry.CLUSTER_BOMB::get, BombRegistry.BOMB_SHOOT, 8, -20.0F, 0.45F, 1.0F));
	//Dynamite
	public static final RegistryObject<Item> DYNAMITE_ITEM = ITEMS.register("dynamite", () -> new ThrowableItem(new Item.Properties().tab(DaBomb.tab),
			BombRegistry.DYNAMITE::get, BombRegistry.DYNAMITE_SHOOT, 14, -20.0F, 0.45F, 1.0F));
	public static final RegistryObject<Item> STICKY_DYNAMITE_ITEM = ITEMS.register("sticky_dynamite", () -> new ThrowableItem(new Item.Properties().tab(DaBomb.tab),
			BombRegistry.STICKY_DYNAMITE::get, BombRegistry.DYNAMITE_SHOOT, 14, -20.0F, 0.45F, 1.0F));
	public static final RegistryObject<Item> BOUNCY_DYNAMITE_ITEM = ITEMS.register("bouncy_dynamite", () -> new ThrowableItem(new Item.Properties().tab(DaBomb.tab),
			BombRegistry.BOUNCY_DYNAMITE::get, BombRegistry.DYNAMITE_SHOOT, 14, -20.0F, 0.45F, 1.0F));
	//C4 + Remote
	public static final RegistryObject<Item> C4_ITEM = ITEMS.register("c4", () -> new C4Item(new Item.Properties().tab(DaBomb.tab),
			BombRegistry.C4_ENTITY::get, BombRegistry.C4_SHOOT, 14, -20.0F, 0.45F, 1.0F));
	public static final RegistryObject<Item> REMOTE = ITEMS.register("remote", () -> new RemoteItem(new Item.Properties().tab(DaBomb.tab)));

	public static final RegistryObject<EntityType<Bomb>> BOMB = ENTITY_TYPES.register("bomb", () ->
			EntityType.Builder.<Bomb>of(Bomb::new, MobCategory.MISC)
					.sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10)
					.setCustomClientFactory(Bomb::new).build("bomb"));
	public static final RegistryObject<EntityType<BouncyBomb>> BOUNCY_BOMB = ENTITY_TYPES.register("bouncy_bomb", () ->
			EntityType.Builder.<BouncyBomb>of(BouncyBomb::new, MobCategory.MISC)
					.sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10)
					.setCustomClientFactory(BouncyBomb::new).build("bouncy_bomb"));
	public static final RegistryObject<EntityType<StickyBomb>> STICKY_BOMB = ENTITY_TYPES.register("sticky_bomb", () ->
			EntityType.Builder.<StickyBomb>of(StickyBomb::new, MobCategory.MISC)
					.sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10)
					.setCustomClientFactory(StickyBomb::new).build("sticky_bomb"));
	public static final RegistryObject<EntityType<BombFish>> BOMB_FISH = ENTITY_TYPES.register("bomb_fish", () ->
			EntityType.Builder.<BombFish>of(BombFish::new, MobCategory.MISC)
					.sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10)
					.setCustomClientFactory(BombFish::new).build("bomb_fish"));
	public static final RegistryObject<EntityType<DirtBomb>> DIRT_BOMB = ENTITY_TYPES.register("dirt_bomb", () ->
			EntityType.Builder.<DirtBomb>of(DirtBomb::new, MobCategory.MISC)
					.sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10)
					.setCustomClientFactory(DirtBomb::new).build("dirt_bomb"));
	public static final RegistryObject<EntityType<DryBomb>> DRY_BOMB = ENTITY_TYPES.register("dry_bomb", () ->
			EntityType.Builder.<DryBomb>of(DryBomb::new, MobCategory.MISC)
					.sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10)
					.setCustomClientFactory(DryBomb::new).build("dry_bomb"));
	public static final RegistryObject<EntityType<WaterBomb>> WATER_BOMB = ENTITY_TYPES.register("water_bomb", () ->
			EntityType.Builder.<WaterBomb>of(WaterBomb::new, MobCategory.MISC)
					.sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10)
					.setCustomClientFactory(WaterBomb::new).build("water_bomb"));
	public static final RegistryObject<EntityType<LavaBomb>> LAVA_BOMB = ENTITY_TYPES.register("lava_bomb", () ->
			EntityType.Builder.<LavaBomb>of(LavaBomb::new, MobCategory.MISC)
					.sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10)
					.setCustomClientFactory(LavaBomb::new).build("lava_bomb"));
	public static final RegistryObject<EntityType<BeeBomb>> BEE_BOMB = ENTITY_TYPES.register("bee_bomb", () ->
			EntityType.Builder.<BeeBomb>of(BeeBomb::new, MobCategory.MISC)
					.sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10)
					.setCustomClientFactory(BeeBomb::new).build("bee_bomb"));
	public static final RegistryObject<EntityType<FlowerBomb>> FLOWER_BOMB = ENTITY_TYPES.register("flower_bomb", () ->
			EntityType.Builder.<FlowerBomb>of(FlowerBomb::new, MobCategory.MISC)
					.sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10)
					.setCustomClientFactory(FlowerBomb::new).build("flower_bomb"));
	public static final RegistryObject<EntityType<EnderBomb>> ENDER_BOMB = ENTITY_TYPES.register("ender_bomb", () ->
			EntityType.Builder.<EnderBomb>of(EnderBomb::new, MobCategory.MISC)
					.sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10)
					.setCustomClientFactory(EnderBomb::new).build("ender_bomb"));
	public static final RegistryObject<EntityType<ClusterBomb>> CLUSTER_BOMB = ENTITY_TYPES.register("cluster_bomb", () ->
			EntityType.Builder.<ClusterBomb>of(ClusterBomb::new, MobCategory.MISC)
					.sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10)
					.setCustomClientFactory(ClusterBomb::new).build("cluster_bomb"));
	public static final RegistryObject<EntityType<BombFragment>> BOMB_FRAGMENT = ENTITY_TYPES.register("bomb_fragment", () ->
			EntityType.Builder.<BombFragment>of(BombFragment::new, MobCategory.MISC)
					.sized(0.15625F, 0.15625F).clientTrackingRange(4).updateInterval(10)
					.setCustomClientFactory(BombFragment::new).build("bomb_fragment"));
	public static final RegistryObject<EntityType<Dynamite>> DYNAMITE = ENTITY_TYPES.register("dynamite", () ->
			EntityType.Builder.<Dynamite>of(Dynamite::new, MobCategory.MISC)
					.sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10)
					.setCustomClientFactory(Dynamite::new).build("dynamite"));
	public static final RegistryObject<EntityType<StickyDynamite>> STICKY_DYNAMITE = ENTITY_TYPES.register("sticky_dynamite", () ->
			EntityType.Builder.<StickyDynamite>of(StickyDynamite::new, MobCategory.MISC)
					.sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10)
					.setCustomClientFactory(StickyDynamite::new).build("sticky_dynamite"));
	public static final RegistryObject<EntityType<BouncyDynamite>> BOUNCY_DYNAMITE = ENTITY_TYPES.register("bouncy_dynamite", () ->
			EntityType.Builder.<BouncyDynamite>of(BouncyDynamite::new, MobCategory.MISC)
					.sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10)
					.setCustomClientFactory(BouncyDynamite::new).build("bouncy_dynamite"));
	public static final RegistryObject<EntityType<C4>> C4_ENTITY = ENTITY_TYPES.register("c4", () ->
			EntityType.Builder.<C4>of(C4::new, MobCategory.MISC)
					.sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10)
					.setCustomClientFactory(C4::new).build("c4"));

	public static final RegistryObject<SoundEvent> BOMB_SHOOT = SOUND_EVENTS.register("bomb_shoot", () ->
			new SoundEvent(new ResourceLocation(DaBomb.MOD_ID, "bomb_shoot")));
	public static final RegistryObject<SoundEvent> DYNAMITE_SHOOT = SOUND_EVENTS.register("dynamite_shoot", () ->
			new SoundEvent(new ResourceLocation(DaBomb.MOD_ID, "dynamite_shoot")));
	public static final RegistryObject<SoundEvent> C4_SHOOT = SOUND_EVENTS.register("c4_shoot", () ->
			new SoundEvent(new ResourceLocation(DaBomb.MOD_ID, "c4_shoot")));

	public static final RegistryObject<SoundEvent> BOMB_PLANTED = SOUND_EVENTS.register("bomb_planted", () ->
			new SoundEvent(new ResourceLocation(DaBomb.MOD_ID, "bomb_planted")));
	public static final RegistryObject<SoundEvent> BOMB_DEFUSED = SOUND_EVENTS.register("bomb_defused", () ->
			new SoundEvent(new ResourceLocation(DaBomb.MOD_ID, "bomb_defused")));
}
