package com.mrbysco.dabomb.client;

import com.mrbysco.dabomb.DaBomb;
import com.mrbysco.dabomb.registry.BombRegistry;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

import java.util.List;

public class ClientHandler {
	public static void registerItemTint(final RegisterColorHandlersEvent.ItemTintSources event) {
		event.register(DaBomb.modLoc("default_color"), DefaultColorTint.MAP_CODEC);
	}


	@SuppressWarnings("unchecked")
	public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
		List<EntityType<?>> specialTypes = List.of(BombRegistry.BOMB_FRAGMENT.get());
		for (var registryObject : BombRegistry.ENTITY_TYPES.getEntries()) {
			if (!specialTypes.contains(registryObject.get()))
				event.registerEntityRenderer((EntityType<? extends ThrowableItemProjectile>) registryObject.get(), (context) ->
						new ThrownItemRenderer<>(context, 1.0F, true));
		}
		event.registerEntityRenderer(BombRegistry.BOMB_FRAGMENT.get(), (context) ->
				new ThrownItemRenderer<>(context, 0.5F, true));
	}
}