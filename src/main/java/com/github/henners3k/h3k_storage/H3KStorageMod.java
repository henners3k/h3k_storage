package com.github.henners3k.h3k_storage;

import com.github.henners3k.h3k_storage.mod.*;
import com.github.henners3k.h3k_storage.type.H3KChestType;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(H3KStorageStrings.MOD_ID)
public class H3KStorageMod {

    public H3KStorageMod() {
        H3KStorageBlocks.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        H3KStorageContainers.CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
        H3KStorageItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        H3KStorageTiles.TILES.register(FMLJavaModLoadingContext.get().getModEventBus());

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
    }

    private void setup(final FMLCommonSetupEvent event) {
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        // Chests
        ScreenManager.registerFactory(H3KStorageContainers.IRON_CHEST.get(), H3KChestType.IRON::createScreen);
        ScreenManager.registerFactory(H3KStorageContainers.GOLD_CHEST.get(), H3KChestType.GOLD::createScreen);
        ScreenManager.registerFactory(H3KStorageContainers.DIAMOND_CHEST.get(), H3KChestType.DIAMOND::createScreen);
        ScreenManager.registerFactory(H3KStorageContainers.NETHERITE_CHEST.get(), H3KChestType.NETHERITE::createScreen);

    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
    }

    private void processIMC(final InterModProcessEvent event) {
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
    }
}
