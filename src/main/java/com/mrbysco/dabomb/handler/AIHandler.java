package com.mrbysco.dabomb.handler;

import com.mrbysco.dabomb.DaBomb;
import com.mrbysco.dabomb.config.BombConfig;
import com.mrbysco.dabomb.entity.goal.PlantC4Goal;
import com.mrbysco.dabomb.entity.goal.ShootBombGoal;
import com.mrbysco.dabomb.item.C4Item;
import com.mrbysco.dabomb.item.ThrowableItem;
import com.mrbysco.dabomb.registry.BombRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class AIHandler {
	private static final Map<EntityType<?>, List<BombData>> bombermanMap = new HashMap<>();

	@SubscribeEvent
	public void onEntityCreation(EntityJoinLevelEvent event) {
		if (!event.getLevel().isClientSide() && BombConfig.COMMON.enableBomberman.get()) {
			if (event.loadedFromDisk()) return;

			Entity entity = event.getEntity();
			if (entity instanceof Mob mob) {
				RandomSource random = mob.getRandom();
				EntityType<?> entityType = mob.getType();
				if (bombermanMap.containsKey(entityType)) {
					List<BombData> data = bombermanMap.get(entityType);
					if (data.isEmpty()) return;

					BombData chosenBomb = data.get(random.nextInt(data.size()));
					if (random.nextDouble() <= chosenBomb.chance) {
						Item item = chosenBomb.item().get();
						if (item instanceof ThrowableItem throwableItem) {
							mob.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(item));
							if (throwableItem instanceof C4Item) {
								mob.goalSelector.addGoal(1, new PlantC4Goal(mob));
								mob.targetSelector.getAvailableGoals().removeIf(goal -> goal.getGoal() instanceof NearestAttackableTargetGoal<?> targetGoal && targetGoal.targetType == Player.class);
								mob.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(mob, Player.class, false, false));
							} else {
								mob.goalSelector.addGoal(1, new ShootBombGoal(mob,
										throwableItem.getProjectile(), throwableItem.getSoundEvent(),
										throwableItem.getVelocity(), throwableItem.getInaccuracy(),
										throwableItem.getZ()));
							}
						}
					}
				}
			}
		}
	}

	public static void refreshCache() {
		bombermanMap.clear();

		List<? extends String> bomberman = BombConfig.COMMON.bomberman.get();
		Map<EntityType<?>, List<BombData>> bomberMap = new HashMap<>();
		if (!bomberman.isEmpty()) {
			for (String configValue : bomberman) {
				if (!configValue.contains(",")) {
					DaBomb.LOGGER.error(String.format("Invalid syntax '%s' found in 'bomberman' config config value, missing reference of bomb and chance", configValue));
				} else {
					String[] values = configValue.split(",");
					if (values.length == 3) {
						if (!values[0].contains(":")) {
							DaBomb.LOGGER.error(String.format("Invalid resourcelocation syntax in 'bomberman'. could not find \":\" in %s", configValue));
							return;
						}
						ResourceLocation registry = new ResourceLocation(values[0]);
						EntityType<?> entityType = BuiltInRegistries.ENTITY_TYPE.get(registry);
						List<BombData> dataList = bomberMap.getOrDefault(entityType, new ArrayList<>());

						Supplier<? extends Item> itemSupplier = getItemForName(values[1]);
						double chance = NumberUtils.isParsable(values[2]) ? Double.parseDouble(values[2]) : 0;

						dataList.add(new BombData(itemSupplier, chance));
						bomberMap.put(entityType, dataList);
					} else {
						DaBomb.LOGGER.error(String.format("Tried looking for 3 values in 'bomberman' but found too many/few making the config value %s invalid", configValue));
					}
				}
			}
		}
		bombermanMap.putAll(bomberMap);
	}

	private static Supplier<? extends Item> getItemForName(String name) {
		return switch (name) {
			default -> {
				DaBomb.LOGGER.error("Invalid bomb {} in `bomberman` config", name);
				yield (() -> Items.AIR);
			}
			case "bomb" -> BombRegistry.BOMB_ITEM;
			case "bouncy_bomb" -> BombRegistry.BOUNCY_BOMB_ITEM;
			case "sticky_bomb" -> BombRegistry.STICKY_BOMB_ITEM;
			case "bomb_fish" -> BombRegistry.BOMB_FISH_ITEM;
			case "dirt_bomb" -> BombRegistry.DIRT_BOMB_ITEM;
			case "dry_bomb" -> BombRegistry.DRY_BOMB_ITEM;
			case "water_bomb" -> BombRegistry.WATER_BOMB_ITEM;
			case "lava_bomb" -> BombRegistry.LAVA_BOMB_ITEM;
			case "bee_bomb" -> BombRegistry.BEE_BOMB_ITEM;
			case "flower_bomb" -> BombRegistry.FLOWER_BOMB_ITEM;
			case "ender_bomb" -> BombRegistry.ENDER_BOMB_ITEM;
			case "cluster_bomb" -> BombRegistry.CLUSTER_BOMB_ITEM;
			case "dynamite" -> BombRegistry.DYNAMITE_ITEM;
			case "sticky_dynamite" -> BombRegistry.STICKY_DYNAMITE_ITEM;
			case "bouncy_dynamite" -> BombRegistry.BOUNCY_DYNAMITE_ITEM;
			case "c4" -> BombRegistry.C4_ITEM;
		};
	}

	private record BombData(Supplier<? extends Item> item, double chance) {

	}
}
