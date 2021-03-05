package com.github.henners3k.h3k_storage.mod;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@SuppressWarnings("unused")
public final class H3KStorageItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MOD_ID);

    // Chests
    public static final RegistryObject<Item> IRON_CHEST =
            ITEMS.register(Constants.IRON_CHEST, () -> new BlockItem(H3KStorageBlocks.IRON_CHEST.get(), new Item.Properties()));
    public static final RegistryObject<Item> GOLD_CHEST =
            ITEMS.register(Constants.GOLD_CHEST, () -> new BlockItem(H3KStorageBlocks.GOLD_CHEST.get(), new Item.Properties()));
    public static final RegistryObject<Item> DIAMOND_CHEST =
            ITEMS.register(Constants.DIAMOND_CHEST, () -> new BlockItem(H3KStorageBlocks.DIAMOND_CHEST.get(), new Item.Properties()));
    public static final RegistryObject<Item> NETHERITE_CHEST =
            ITEMS.register(Constants.NETHERITE_CHEST, () -> new BlockItem(H3KStorageBlocks.NETHERITE_CHEST.get(), new Item.Properties()));
}