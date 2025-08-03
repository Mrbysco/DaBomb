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
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.function.Supplier;

public class BombRegistry {
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(DaBomb.MOD_ID);
	public static final DeferredRegister.Entities ENTITY_TYPES = DeferredRegister.createEntities(DaBomb.MOD_ID);
	public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, DaBomb.MOD_ID);
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DaBomb.MOD_ID);

	//Bombs
	public static final DeferredItem<ThrowableItem> BOMB_ITEM = ITEMS.registerItem("bomb", (properties) -> new ThrowableItem(properties,
			BombRegistry.BOMB::get, BombRegistry.BOMB_SHOOT, 8, -20.0F, 0.45F, 1.0F));
	public static final DeferredItem<ThrowableItem> BOUNCY_BOMB_ITEM = ITEMS.registerItem("bouncy_bomb", (properties) -> new ThrowableItem(properties,
			BombRegistry.BOUNCY_BOMB::get, BombRegistry.BOMB_SHOOT, 8, -20.0F, 0.45F, 1.0F));
	public static final DeferredItem<ThrowableItem> STICKY_BOMB_ITEM = ITEMS.registerItem("sticky_bomb", (properties) -> new ThrowableItem(properties,
			BombRegistry.STICKY_BOMB::get, BombRegistry.BOMB_SHOOT, 8, -20.0F, 0.45F, 1.0F));
	public static final DeferredItem<ThrowableItem> BOMB_FISH_ITEM = ITEMS.registerItem("bomb_fish", (properties) -> new ThrowableItem(properties,
			BombRegistry.BOMB_FISH::get, BombRegistry.BOMB_SHOOT, 8, -20.0F, 0.45F, 1.0F));
	public static final DeferredItem<ThrowableItem> DIRT_BOMB_ITEM = ITEMS.registerItem("dirt_bomb", (properties) -> new ThrowableItem(properties,
			BombRegistry.DIRT_BOMB::get, BombRegistry.BOMB_SHOOT, 8, -10.0F, 0.65F, 1.0F));
	public static final DeferredItem<ThrowableItem> DRY_BOMB_ITEM = ITEMS.registerItem("dry_bomb", (properties) -> new ThrowableItem(properties,
			BombRegistry.DRY_BOMB::get, BombRegistry.BOMB_SHOOT, 8, -10.0F, 0.65F, 1.0F));
	public static final DeferredItem<ThrowableItem> WATER_BOMB_ITEM = ITEMS.registerItem("water_bomb", (properties) -> new ThrowableItem(properties,
			BombRegistry.WATER_BOMB::get, BombRegistry.BOMB_SHOOT, 8, -10.0F, 0.65F, 1.0F));
	public static final DeferredItem<ThrowableItem> LAVA_BOMB_ITEM = ITEMS.registerItem("lava_bomb", (properties) -> new ThrowableItem(properties,
			BombRegistry.LAVA_BOMB::get, BombRegistry.BOMB_SHOOT, 8, -10.0F, 0.65F, 1.0F));
	public static final DeferredItem<ThrowableItem> BEE_BOMB_ITEM = ITEMS.registerItem("bee_bomb", (properties) -> new ThrowableItem(properties,
			BombRegistry.BEE_BOMB::get, BombRegistry.BOMB_SHOOT, 8, -20.0F, 0.45F, 1.0F));
	public static final DeferredItem<ThrowableItem> FLOWER_BOMB_ITEM = ITEMS.registerItem("flower_bomb", (properties) -> new ThrowableItem(properties,
			BombRegistry.FLOWER_BOMB::get, BombRegistry.BOMB_SHOOT, 8, -20.0F, 0.45F, 1.0F));
	public static final DeferredItem<ThrowableItem> ENDER_BOMB_ITEM = ITEMS.registerItem("ender_bomb", (properties) -> new ThrowableItem(properties,
			BombRegistry.ENDER_BOMB::get, BombRegistry.BOMB_SHOOT, 8, -20.0F, 0.45F, 1.0F));
	public static final DeferredItem<ThrowableItem> CLUSTER_BOMB_ITEM = ITEMS.registerItem("cluster_bomb", (properties) -> new ThrowableItem(properties,
			BombRegistry.CLUSTER_BOMB::get, BombRegistry.BOMB_SHOOT, 8, -20.0F, 0.45F, 1.0F));
	//Dynamite
	public static final DeferredItem<ThrowableItem> DYNAMITE_ITEM = ITEMS.registerItem("dynamite", (properties) -> new ThrowableItem(properties,
			BombRegistry.DYNAMITE::get, BombRegistry.DYNAMITE_SHOOT, 14, -20.0F, 0.45F, 1.0F));
	public static final DeferredItem<ThrowableItem> STICKY_DYNAMITE_ITEM = ITEMS.registerItem("sticky_dynamite", (properties) -> new ThrowableItem(properties,
			BombRegistry.STICKY_DYNAMITE::get, BombRegistry.DYNAMITE_SHOOT, 14, -20.0F, 0.45F, 1.0F));
	public static final DeferredItem<ThrowableItem> BOUNCY_DYNAMITE_ITEM = ITEMS.registerItem("bouncy_dynamite", (properties) -> new ThrowableItem(properties,
			BombRegistry.BOUNCY_DYNAMITE::get, BombRegistry.DYNAMITE_SHOOT, 14, -20.0F, 0.45F, 1.0F));
	//C4 + Remote
	public static final DeferredItem<C4Item> C4_ITEM = ITEMS.registerItem("c4", (properties) -> new C4Item(properties,
			BombRegistry.C4_ENTITY::get, BombRegistry.C4_SHOOT, 14, -20.0F, 0.45F, 1.0F));
	public static final DeferredItem<RemoteItem> REMOTE = ITEMS.registerItem("remote", RemoteItem::new);

	public static final Supplier<EntityType<Bomb>> BOMB = ENTITY_TYPES.registerEntityType("bomb",
			Bomb::new,
			MobCategory.MISC,
			builder -> builder
					.sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10)
	);
	public static final Supplier<EntityType<BouncyBomb>> BOUNCY_BOMB = ENTITY_TYPES.registerEntityType("bouncy_bomb",
			BouncyBomb::new,
			MobCategory.MISC,
			builder -> builder
					.sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10)
	);
	public static final Supplier<EntityType<StickyBomb>> STICKY_BOMB = ENTITY_TYPES.registerEntityType("sticky_bomb",
			StickyBomb::new,
			MobCategory.MISC,
			builder -> builder
					.sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10)
	);
	public static final Supplier<EntityType<BombFish>> BOMB_FISH = ENTITY_TYPES.registerEntityType("bomb_fish",
			BombFish::new,
			MobCategory.MISC,
			builder -> builder
					.sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10)
	);
	public static final Supplier<EntityType<DirtBomb>> DIRT_BOMB = ENTITY_TYPES.registerEntityType("dirt_bomb",
			DirtBomb::new,
			MobCategory.MISC,
			builder -> builder
					.sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10)
	);
	public static final Supplier<EntityType<DryBomb>> DRY_BOMB = ENTITY_TYPES.registerEntityType("dry_bomb",
			DryBomb::new,
			MobCategory.MISC,
			builder -> builder
					.sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10)
	);
	public static final Supplier<EntityType<WaterBomb>> WATER_BOMB = ENTITY_TYPES.registerEntityType("water_bomb",
			WaterBomb::new,
			MobCategory.MISC,
			builder -> builder
					.sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10)
	);
	public static final Supplier<EntityType<LavaBomb>> LAVA_BOMB = ENTITY_TYPES.registerEntityType("lava_bomb",
			LavaBomb::new,
			MobCategory.MISC,
			builder -> builder
					.sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10)
	);
	public static final Supplier<EntityType<BeeBomb>> BEE_BOMB = ENTITY_TYPES.registerEntityType("bee_bomb",
			BeeBomb::new,
			MobCategory.MISC,
			builder -> builder
					.sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10)
	);
	public static final Supplier<EntityType<FlowerBomb>> FLOWER_BOMB = ENTITY_TYPES.registerEntityType("flower_bomb",
			FlowerBomb::new,
			MobCategory.MISC,
			builder -> builder
					.sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10)
	);
	public static final Supplier<EntityType<EnderBomb>> ENDER_BOMB = ENTITY_TYPES.registerEntityType("ender_bomb",
			EnderBomb::new,
			MobCategory.MISC,
			builder -> builder
					.sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10)
	);
	public static final Supplier<EntityType<ClusterBomb>> CLUSTER_BOMB = ENTITY_TYPES.registerEntityType("cluster_bomb",
			ClusterBomb::new,
			MobCategory.MISC,
			builder -> builder
					.sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10)
	);
	public static final Supplier<EntityType<BombFragment>> BOMB_FRAGMENT = ENTITY_TYPES.registerEntityType("bomb_fragment",
			BombFragment::new,
			MobCategory.MISC,
			builder -> builder
					.sized(0.15625F, 0.15625F).clientTrackingRange(4).updateInterval(10)
	);

	public static final Supplier<EntityType<Dynamite>> DYNAMITE = ENTITY_TYPES.registerEntityType("dynamite",
			Dynamite::new,
			MobCategory.MISC,
			builder -> builder
					.sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10)
	);
	public static final Supplier<EntityType<StickyDynamite>> STICKY_DYNAMITE = ENTITY_TYPES.registerEntityType("sticky_dynamite",
			StickyDynamite::new,
			MobCategory.MISC,
			builder -> builder
					.sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10)
	);
	public static final Supplier<EntityType<BouncyDynamite>> BOUNCY_DYNAMITE = ENTITY_TYPES.registerEntityType("bouncy_dynamite",
			BouncyDynamite::new,
			MobCategory.MISC,
			builder -> builder
					.sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10)
	);
	public static final Supplier<EntityType<C4>> C4_ENTITY = ENTITY_TYPES.registerEntityType("c4",
			C4::new,
			MobCategory.MISC,
			builder -> builder
					.sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10)
	);

	public static final DeferredHolder<SoundEvent, SoundEvent> BOMB_SHOOT = SOUND_EVENTS.register("bomb_shoot", () ->
			SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(DaBomb.MOD_ID, "bomb_shoot")));
	public static final DeferredHolder<SoundEvent, SoundEvent> DYNAMITE_SHOOT = SOUND_EVENTS.register("dynamite_shoot", () ->
			SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(DaBomb.MOD_ID, "dynamite_shoot")));
	public static final DeferredHolder<SoundEvent, SoundEvent> C4_SHOOT = SOUND_EVENTS.register("c4_shoot", () ->
			SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(DaBomb.MOD_ID, "c4_shoot")));

	public static final DeferredHolder<SoundEvent, SoundEvent> BOMB_PLANTED = SOUND_EVENTS.register("bomb_planted", () ->
			SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(DaBomb.MOD_ID, "bomb_planted")));
	public static final DeferredHolder<SoundEvent, SoundEvent> BOMB_DEFUSED = SOUND_EVENTS.register("bomb_defused", () ->
			SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(DaBomb.MOD_ID, "bomb_defused")));

	public static final Supplier<CreativeModeTab> BOMB_TAB = CREATIVE_MODE_TABS.register("tab", () -> CreativeModeTab.builder()
			.icon(() -> BombRegistry.BOMB_ITEM.get().getDefaultInstance())
			.withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
			.title(Component.translatable("itemGroup.dabomb"))
			.displayItems((parameters, output) -> {
				List<ItemStack> stacks = BombRegistry.ITEMS.getEntries().stream().map(reg -> new ItemStack(reg.get())).toList();
				output.acceptAll(stacks);
			}).build());
}
