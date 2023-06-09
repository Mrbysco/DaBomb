package com.mrbysco.dabomb;

import com.mojang.logging.LogUtils;
import com.mrbysco.dabomb.client.ClientHandler;
import com.mrbysco.dabomb.config.BombConfig;
import com.mrbysco.dabomb.handler.AIHandler;
import com.mrbysco.dabomb.handler.ExplosionHandler;
import com.mrbysco.dabomb.registry.BombRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(DaBomb.MOD_ID)
public class DaBomb {
	public static final String MOD_ID = "dabomb";
	public static final Logger LOGGER = LogUtils.getLogger();
	public static final CreativeModeTab tab = new CreativeModeTab(MOD_ID) {
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(BombRegistry.BOMB_ITEM.get());
		}
	};

	public DaBomb() {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, BombConfig.commonSpec);
		eventBus.register(BombConfig.class);

		BombRegistry.ITEMS.register(eventBus);
		BombRegistry.ENTITY_TYPES.register(eventBus);
		BombRegistry.SOUND_EVENTS.register(eventBus);

		MinecraftForge.EVENT_BUS.addListener(ExplosionHandler::onDetonate);
		MinecraftForge.EVENT_BUS.register(new AIHandler());

		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
			eventBus.addListener(ClientHandler::registerItemColors);
			eventBus.addListener(ClientHandler::registerEntityRenders);
		});
	}
}
