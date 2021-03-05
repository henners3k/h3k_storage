package com.github.henners3k.h3k_storage.mod;

import com.github.henners3k.h3k_storage.block.H3KChestBlock;
import com.github.henners3k.h3k_storage.type.H3KChestType;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@SuppressWarnings("unused")
public final class H3KStorageBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Constants.MOD_ID);

    // Chests
    public static final RegistryObject<Block> IRON_CHEST = BLOCKS.register(Constants.IRON_CHEST,
            () -> new H3KChestBlock(H3KChestType.IRON, AbstractBlock.Properties.create(Material.IRON, MaterialColor.IRON).hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL))
    );
    public static final RegistryObject<Block> GOLD_CHEST = BLOCKS.register(Constants.GOLD_CHEST,
            () -> new H3KChestBlock(H3KChestType.GOLD, AbstractBlock.Properties.create(Material.IRON, MaterialColor.GOLD).hardnessAndResistance(3.0F, 6.0F).sound(SoundType.METAL))
    );
    public static final RegistryObject<Block> DIAMOND_CHEST = BLOCKS.register(Constants.DIAMOND_CHEST,
            () -> new H3KChestBlock(H3KChestType.DIAMOND, AbstractBlock.Properties.create(Material.IRON, MaterialColor.DIAMOND).hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL))
    );
    public static final RegistryObject<Block> NETHERITE_CHEST = BLOCKS.register(Constants.NETHERITE_CHEST,
            () -> new H3KChestBlock(H3KChestType.NETHERITE, AbstractBlock.Properties.create(Material.IRON, MaterialColor.BLACK).setRequiresTool().hardnessAndResistance(50.0F, 1200.0F).sound(SoundType.NETHERITE))
    );


}
