package com.github.henners3k.h3k_storage.mod;

import com.github.henners3k.h3k_storage.gui.chest.H3KChestContainer;
import com.github.henners3k.h3k_storage.type.H3KChestType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class H3KStorageContainers {
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Constants.MOD_ID);

    // Chests
    public static final RegistryObject<ContainerType<H3KChestContainer>> IRON_CHEST =
            CONTAINERS.register(Constants.IRON_CHEST, () -> IForgeContainerType.create(H3KChestType.IRON::createContainer));
    public static final RegistryObject<ContainerType<H3KChestContainer>> GOLD_CHEST =
            CONTAINERS.register(Constants.GOLD_CHEST, () -> IForgeContainerType.create(H3KChestType.GOLD::createContainer));
    public static final RegistryObject<ContainerType<H3KChestContainer>> DIAMOND_CHEST =
            CONTAINERS.register(Constants.DIAMOND_CHEST, () -> IForgeContainerType.create(H3KChestType.DIAMOND::createContainer));
    public static final RegistryObject<ContainerType<H3KChestContainer>> NETHERITE_CHEST =
            CONTAINERS.register(Constants.NETHERITE_CHEST, () -> IForgeContainerType.create(H3KChestType.NETHERITE::createContainer));
}