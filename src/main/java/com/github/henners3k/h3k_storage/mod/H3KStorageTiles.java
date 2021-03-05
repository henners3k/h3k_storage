package com.github.henners3k.h3k_storage.mod;

import com.github.henners3k.h3k_storage.tile.H3KChestTile;
import com.github.henners3k.h3k_storage.type.H3KChestType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@SuppressWarnings("ConstantConditions")
public final class H3KStorageTiles {
    public static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, H3KStorageStrings.MOD_ID);

    // Chests
    public static final RegistryObject<TileEntityType<H3KChestTile>> IRON_CHEST = TILES.register(H3KStorageStrings.IRON_CHEST,
            () -> TileEntityType.Builder.create(H3KChestType.IRON::createTile, H3KStorageBlocks.IRON_CHEST.get()).build(null)
    );
    public static final RegistryObject<TileEntityType<H3KChestTile>> GOLD_CHEST = TILES.register(H3KStorageStrings.GOLD_CHEST,
            () -> TileEntityType.Builder.create(H3KChestType.GOLD::createTile, H3KStorageBlocks.GOLD_CHEST.get()).build(null)
    );
    public static final RegistryObject<TileEntityType<H3KChestTile>> DIAMOND_CHEST = TILES.register(H3KStorageStrings.DIAMOND_CHEST,
            () -> TileEntityType.Builder.create(H3KChestType.DIAMOND::createTile, H3KStorageBlocks.DIAMOND_CHEST.get()).build(null)
    );
    public static final RegistryObject<TileEntityType<H3KChestTile>> NETHERITE_CHEST = TILES.register(H3KStorageStrings.NETHERITE_CHEST,
            () -> TileEntityType.Builder.create(H3KChestType.NETHERITE::createTile, H3KStorageBlocks.NETHERITE_CHEST.get()).build(null)
    );
    

}
