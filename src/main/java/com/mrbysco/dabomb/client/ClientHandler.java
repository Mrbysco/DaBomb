package com.mrbysco.dabomb.client;

import com.mrbysco.dabomb.registry.BombRegistry;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class ClientHandler {
	public static void registerItemColors(final ColorHandlerEvent.Item event) {
		ItemColors itemColors = event.getItemColors();
		itemColors.register((stack, tintIndex) -> tintIndex > 0 ? -1 : 4159204, BombRegistry.WATER_BOMB_ITEM.get());
	}

	public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
		List<EntityType<?>> specialTypes = List.of(BombRegistry.BOMB_FRAGMENT.get());
		for (RegistryObject<EntityType<?>> registryObject : BombRegistry.ENTITIES.getEntries()) {
			if (!specialTypes.contains(registryObject.get()))
				event.registerEntityRenderer((EntityType<? extends ThrowableItemProjectile>) registryObject.get(), (context) ->
						new ThrownItemRenderer<>(context, 1.0F, true));
		}
		event.registerEntityRenderer(BombRegistry.BOMB_FRAGMENT.get(), (context) ->
				new ThrownItemRenderer<>(context, 0.5F, true));
	}
}