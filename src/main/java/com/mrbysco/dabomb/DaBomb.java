package com.mrbysco.dabomb;

import com.mojang.logging.LogUtils;
import com.mrbysco.dabomb.client.ClientHandler;
import com.mrbysco.dabomb.config.BombConfig;
import com.mrbysco.dabomb.handler.AIHandler;
import com.mrbysco.dabomb.handler.ExplosionHandler;
import com.mrbysco.dabomb.registry.BombRegistry;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

@Mod(DaBomb.MOD_ID)
public class DaBomb {
	public static final String MOD_ID = "dabomb";
	public static final Logger LOGGER = LogUtils.getLogger();

	public DaBomb(IEventBus eventBus) {
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, BombConfig.commonSpec);
		eventBus.register(BombConfig.class);

		BombRegistry.ITEMS.register(eventBus);
		BombRegistry.ENTITY_TYPES.register(eventBus);
		BombRegistry.SOUND_EVENTS.register(eventBus);
		BombRegistry.CREATIVE_MODE_TABS.register(eventBus);

		NeoForge.EVENT_BUS.addListener(ExplosionHandler::onDetonate);
		NeoForge.EVENT_BUS.register(new AIHandler());

		if (FMLEnvironment.dist.isClient()) {
			eventBus.addListener(ClientHandler::registerItemColors);
			eventBus.addListener(ClientHandler::registerEntityRenders);
		}
	}
}
