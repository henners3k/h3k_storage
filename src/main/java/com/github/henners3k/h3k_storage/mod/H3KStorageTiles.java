package com.github.henners3k.h3k_storage.mod;

import com.github.henners3k.h3k_storage.tile.H3KChestTile;
import com.github.henners3k.h3k_storage.type.H3KChestType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class H3KStorageTiles {
    public static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Constants.MOD_ID);

    public static final RegistryObject<TileEntityType<H3KChestTile>> IRON_CHEST = TILES.register(Constants.IRON_CHEST,
            () -> TileEntityType.Builder.create(H3KChestType.IRON::createTile, H3KStorageBlocks.IRON_CHEST.get()).build(null)
    );
    
}
