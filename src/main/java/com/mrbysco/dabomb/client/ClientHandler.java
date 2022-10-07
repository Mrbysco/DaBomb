package com.mrbysco.dabomb.client;

import com.mrbysco.dabomb.registry.BombRegistry;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.registries.RegistryObject;

public class ClientHandler {
	public static void registerItemColors(final ColorHandlerEvent.Item event) {
		ItemColors itemColors = event.getItemColors();
		itemColors.register((stack, tintIndex) -> tintIndex > 0 ? -1 : 4159204, BombRegistry.WATER_BOMB_ITEM.get());
	}

	public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
		for (RegistryObject<EntityType<?>> object : BombRegistry.ENTITIES.getEntries()) {
			event.registerEntityRenderer((EntityType<? extends ThrowableItemProjectile>) object.get(), (context) -> new ThrownItemRenderer<>(context, 1.0F, true));
		}
//		event.registerEntityRenderer(BombRegistry.BOMB.get(), (context) -> new ThrownItemRenderer<>(context, 1.0F, true));
//		event.registerEntityRenderer(BombRegistry.BOUNCY_BOMB.get(), (context) -> new ThrownItemRenderer<>(context, 1.0F, true));
//		event.registerEntityRenderer(BombRegistry.STICKY_BOMB.get(), (context) -> new ThrownItemRenderer<>(context, 1.0F, true));
//		event.registerEntityRenderer(BombRegistry.BOMB_FISH.get(), (context) -> new ThrownItemRenderer<>(context, 1.0F, true));
//		event.registerEntityRenderer(BombRegistry.DIRT_BOMB.get(), (context) -> new ThrownItemRenderer<>(context, 1.0F, true));
//		event.registerEntityRenderer(BombRegistry.WATER_BOMB.get(), (context) -> new ThrownItemRenderer<>(context, 1.0F, true));
//		event.registerEntityRenderer(BombRegistry.LAVA_BOMB.get(), (context) -> new ThrownItemRenderer<>(context, 1.0F, true));
//		event.registerEntityRenderer(BombRegistry.DRY_BOMB.get(), (context) -> new ThrownItemRenderer<>(context, 1.0F, true));
//		event.registerEntityRenderer(BombRegistry.BEE_BOMB.get(), (context) -> new ThrownItemRenderer<>(context, 1.0F, true));
//		event.registerEntityRenderer(BombRegistry.C4_ENTITY.get(), (context) -> new ThrownItemRenderer<>(context, 1.0F, true));
//
//		event.registerEntityRenderer(BombRegistry.DYNAMITE.get(), (context) -> new ThrownItemRenderer<>(context, 1.0F, true));
//		event.registerEntityRenderer(BombRegistry.BOUNCY_DYNAMITE.get(), (context) -> new ThrownItemRenderer<>(context, 1.0F, true));
//		event.registerEntityRenderer(BombRegistry.STICKY_DYNAMITE.get(), (context) -> new ThrownItemRenderer<>(context, 1.0F, true));
	}
}