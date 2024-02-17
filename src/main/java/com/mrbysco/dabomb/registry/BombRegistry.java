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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.function.Supplier;

public class BombRegistry {
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(DaBomb.MOD_ID);
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, DaBomb.MOD_ID);
	public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, DaBomb.MOD_ID);
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DaBomb.MOD_ID);

	//Bombs
	public static final DeferredItem<ThrowableItem> BOMB_ITEM = ITEMS.register("bomb", () -> new ThrowableItem(new Item.Properties(),
			BombRegistry.BOMB::get, BombRegistry.BOMB_SHOOT, 8, -20.0F, 0.45F, 1.0F));
	public static final DeferredItem<ThrowableItem> BOUNCY_BOMB_ITEM = ITEMS.register("bouncy_bomb", () -> new ThrowableItem(new Item.Properties(),
			BombRegistry.BOUNCY_BOMB::get, BombRegistry.BOMB_SHOOT, 8, -20.0F, 0.45F, 1.0F));
	public static final DeferredItem<ThrowableItem> STICKY_BOMB_ITEM = ITEMS.register("sticky_bomb", () -> new ThrowableItem(new Item.Properties(),
			BombRegistry.STICKY_BOMB::get, BombRegistry.BOMB_SHOOT, 8, -20.0F, 0.45F, 1.0F));
	public static final DeferredItem<ThrowableItem> BOMB_FISH_ITEM = ITEMS.register("bomb_fish", () -> new ThrowableItem(new Item.Properties(),
			BombRegistry.BOMB_FISH::get, BombRegistry.BOMB_SHOOT, 8, -20.0F, 0.45F, 1.0F));
	public static final DeferredItem<ThrowableItem> DIRT_BOMB_ITEM = ITEMS.register("dirt_bomb", () -> new ThrowableItem(new Item.Properties(),
			BombRegistry.DIRT_BOMB::get, BombRegistry.BOMB_SHOOT, 8, -10.0F, 0.65F, 1.0F));
	public static final DeferredItem<ThrowableItem> DRY_BOMB_ITEM = ITEMS.register("dry_bomb", () -> new ThrowableItem(new Item.Properties(),
			BombRegistry.DRY_BOMB::get, BombRegistry.BOMB_SHOOT, 8, -10.0F, 0.65F, 1.0F));
	public static final DeferredItem<ThrowableItem> WATER_BOMB_ITEM = ITEMS.register("water_bomb", () -> new ThrowableItem(new Item.Properties(),
			BombRegistry.WATER_BOMB::get, BombRegistry.BOMB_SHOOT, 8, -10.0F, 0.65F, 1.0F));
	public static final DeferredItem<ThrowableItem> LAVA_BOMB_ITEM = ITEMS.register("lava_bomb", () -> new ThrowableItem(new Item.Properties(),
			BombRegistry.LAVA_BOMB::get, BombRegistry.BOMB_SHOOT, 8, -10.0F, 0.65F, 1.0F));
	public static final DeferredItem<ThrowableItem> BEE_BOMB_ITEM = ITEMS.register("bee_bomb", () -> new ThrowableItem(new Item.Properties(),
			BombRegistry.BEE_BOMB::get, BombRegistry.BOMB_SHOOT, 8, -20.0F, 0.45F, 1.0F));
	public static final DeferredItem<ThrowableItem> FLOWER_BOMB_ITEM = ITEMS.register("flower_bomb", () -> new ThrowableItem(new Item.Properties(),
			BombRegistry.FLOWER_BOMB::get, BombRegistry.BOMB_SHOOT, 8, -20.0F, 0.45F, 1.0F));
	public static final DeferredItem<ThrowableItem> ENDER_BOMB_ITEM = ITEMS.register("ender_bomb", () -> new ThrowableItem(new Item.Properties(),
			BombRegistry.ENDER_BOMB::get, BombRegistry.BOMB_SHOOT, 8, -20.0F, 0.45F, 1.0F));
	public static final DeferredItem<ThrowableItem> CLUSTER_BOMB_ITEM = ITEMS.register("cluster_bomb", () -> new ThrowableItem(new Item.Properties(),
			BombRegistry.CLUSTER_BOMB::get, BombRegistry.BOMB_SHOOT, 8, -20.0F, 0.45F, 1.0F));
	//Dynamite
	public static final DeferredItem<ThrowableItem> DYNAMITE_ITEM = ITEMS.register("dynamite", () -> new ThrowableItem(new Item.Properties(),
			BombRegistry.DYNAMITE::get, BombRegistry.DYNAMITE_SHOOT, 14, -20.0F, 0.45F, 1.0F));
	public static final DeferredItem<ThrowableItem> STICKY_DYNAMITE_ITEM = ITEMS.register("sticky_dynamite", () -> new ThrowableItem(new Item.Properties(),
			BombRegistry.STICKY_DYNAMITE::get, BombRegistry.DYNAMITE_SHOOT, 14, -20.0F, 0.45F, 1.0F));
	public static final DeferredItem<ThrowableItem> BOUNCY_DYNAMITE_ITEM = ITEMS.register("bouncy_dynamite", () -> new ThrowableItem(new Item.Properties(),
			BombRegistry.BOUNCY_DYNAMITE::get, BombRegistry.DYNAMITE_SHOOT, 14, -20.0F, 0.45F, 1.0F));
	//C4 + Remote
	public static final DeferredItem<C4Item> C4_ITEM = ITEMS.register("c4", () -> new C4Item(new Item.Properties(),
			BombRegistry.C4_ENTITY::get, BombRegistry.C4_SHOOT, 14, -20.0F, 0.45F, 1.0F));
	public static final DeferredItem<RemoteItem> REMOTE = ITEMS.register("remote", () -> new RemoteItem(new Item.Properties()));

	public static final Supplier<EntityType<Bomb>> BOMB = ENTITY_TYPES.register("bomb", () ->
			EntityType.Builder.<Bomb>of(Bomb::new, MobCategory.MISC)
					.sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10)
					.build("bomb"));
	public static final Supplier<EntityType<BouncyBomb>> BOUNCY_BOMB = ENTITY_TYPES.register("bouncy_bomb", () ->
			EntityType.Builder.<BouncyBomb>of(BouncyBomb::new, MobCategory.MISC)
					.sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10)
					.build("bouncy_bomb"));
	public static final Supplier<EntityType<StickyBomb>> STICKY_BOMB = ENTITY_TYPES.register("sticky_bomb", () ->
			EntityType.Builder.<StickyBomb>of(StickyBomb::new, MobCategory.MISC)
					.sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10)
					.build("sticky_bomb"));
	public static final Supplier<EntityType<BombFish>> BOMB_FISH = ENTITY_TYPES.register("bomb_fish", () ->
			EntityType.Builder.<BombFish>of(BombFish::new, MobCategory.MISC)
					.sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10)
					.build("bomb_fish"));
	public static final Supplier<EntityType<DirtBomb>> DIRT_BOMB = ENTITY_TYPES.register("dirt_bomb", () ->
			EntityType.Builder.<DirtBomb>of(DirtBomb::new, MobCategory.MISC)
					.sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10)
					.build("dirt_bomb"));
	public static final Supplier<EntityType<DryBomb>> DRY_BOMB = ENTITY_TYPES.register("dry_bomb", () ->
			EntityType.Builder.<DryBomb>of(DryBomb::new, MobCategory.MISC)
					.sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10)
					.build("dry_bomb"));
	public static final Supplier<EntityType<WaterBomb>> WATER_BOMB = ENTITY_TYPES.register("water_bomb", () ->
			EntityType.Builder.<WaterBomb>of(WaterBomb::new, MobCategory.MISC)
					.sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10)
					.build("water_bomb"));
	public static final Supplier<EntityType<LavaBomb>> LAVA_BOMB = ENTITY_TYPES.register("lava_bomb", () ->
			EntityType.Builder.<LavaBomb>of(LavaBomb::new, MobCategory.MISC)
					.sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10)
					.build("lava_bomb"));
	public static final Supplier<EntityType<BeeBomb>> BEE_BOMB = ENTITY_TYPES.register("bee_bomb", () ->
			EntityType.Builder.<BeeBomb>of(BeeBomb::new, MobCategory.MISC)
					.sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10)
					.build("bee_bomb"));
	public static final Supplier<EntityType<FlowerBomb>> FLOWER_BOMB = ENTITY_TYPES.register("flower_bomb", () ->
			EntityType.Builder.<FlowerBomb>of(FlowerBomb::new, MobCategory.MISC)
					.sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10)
					.build("flower_bomb"));
	public static final Supplier<EntityType<EnderBomb>> ENDER_BOMB = ENTITY_TYPES.register("ender_bomb", () ->
			EntityType.Builder.<EnderBomb>of(EnderBomb::new, MobCategory.MISC)
					.sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10)
					.build("ender_bomb"));
	public static final Supplier<EntityType<ClusterBomb>> CLUSTER_BOMB = ENTITY_TYPES.register("cluster_bomb", () ->
			EntityType.Builder.<ClusterBomb>of(ClusterBomb::new, MobCategory.MISC)
					.sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10)
					.build("cluster_bomb"));
	public static final Supplier<EntityType<BombFragment>> BOMB_FRAGMENT = ENTITY_TYPES.register("bomb_fragment", () ->
			EntityType.Builder.<BombFragment>of(BombFragment::new, MobCategory.MISC)
					.sized(0.15625F, 0.15625F).clientTrackingRange(4).updateInterval(10)
					.build("bomb_fragment"));
	public static final Supplier<EntityType<Dynamite>> DYNAMITE = ENTITY_TYPES.register("dynamite", () ->
			EntityType.Builder.<Dynamite>of(Dynamite::new, MobCategory.MISC)
					.sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10)
					.build("dynamite"));
	public static final Supplier<EntityType<StickyDynamite>> STICKY_DYNAMITE = ENTITY_TYPES.register("sticky_dynamite", () ->
			EntityType.Builder.<StickyDynamite>of(StickyDynamite::new, MobCategory.MISC)
					.sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10)
					.build("sticky_dynamite"));
	public static final Supplier<EntityType<BouncyDynamite>> BOUNCY_DYNAMITE = ENTITY_TYPES.register("bouncy_dynamite", () ->
			EntityType.Builder.<BouncyDynamite>of(BouncyDynamite::new, MobCategory.MISC)
					.sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10)
					.build("bouncy_dynamite"));
	public static final Supplier<EntityType<C4>> C4_ENTITY = ENTITY_TYPES.register("c4", () ->
			EntityType.Builder.<C4>of(C4::new, MobCategory.MISC)
					.sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10)
					.build("c4"));

	public static final DeferredHolder<SoundEvent, SoundEvent> BOMB_SHOOT = SOUND_EVENTS.register("bomb_shoot", () ->
			SoundEvent.createVariableRangeEvent(new ResourceLocation(DaBomb.MOD_ID, "bomb_shoot")));
	public static final DeferredHolder<SoundEvent, SoundEvent> DYNAMITE_SHOOT = SOUND_EVENTS.register("dynamite_shoot", () ->
			SoundEvent.createVariableRangeEvent(new ResourceLocation(DaBomb.MOD_ID, "dynamite_shoot")));
	public static final DeferredHolder<SoundEvent, SoundEvent> C4_SHOOT = SOUND_EVENTS.register("c4_shoot", () ->
			SoundEvent.createVariableRangeEvent(new ResourceLocation(DaBomb.MOD_ID, "c4_shoot")));

	public static final DeferredHolder<SoundEvent, SoundEvent> BOMB_PLANTED = SOUND_EVENTS.register("bomb_planted", () ->
			SoundEvent.createVariableRangeEvent(new ResourceLocation(DaBomb.MOD_ID, "bomb_planted")));
	public static final DeferredHolder<SoundEvent, SoundEvent> BOMB_DEFUSED = SOUND_EVENTS.register("bomb_defused", () ->
			SoundEvent.createVariableRangeEvent(new ResourceLocation(DaBomb.MOD_ID, "bomb_defused")));

	public static final Supplier<CreativeModeTab> BOMB_TAB = CREATIVE_MODE_TABS.register("tab", () -> CreativeModeTab.builder()
			.icon(() -> BombRegistry.BOMB_ITEM.get().getDefaultInstance())
			.withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
			.title(Component.translatable("itemGroup.dabomb"))
			.displayItems((parameters, output) -> {
				List<ItemStack> stacks = BombRegistry.ITEMS.getEntries().stream().map(reg -> new ItemStack(reg.get())).toList();
				output.acceptAll(stacks);
			}).build());
}
