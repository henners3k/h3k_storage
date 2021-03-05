package com.github.henners3k.h3k_storage.type;

import com.github.henners3k.h3k_storage.gui.chest.H3KChestContainer;
import com.github.henners3k.h3k_storage.gui.chest.H3KChestScreen;
import com.github.henners3k.h3k_storage.mod.Constants;
import com.github.henners3k.h3k_storage.mod.H3KStorageContainers;
import com.github.henners3k.h3k_storage.mod.H3KStorageTiles;
import com.github.henners3k.h3k_storage.tile.H3KChestTile;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import java.util.function.Supplier;

public enum H3KChestType {

    IRON(6, 9, new ResourceLocation(Constants.MOD_ID, "textures/gui/container/chest/iron_chest.png"), 176, 222, 256, 256, H3KStorageTiles.IRON_CHEST, H3KStorageContainers.IRON_CHEST),
    GOLD(9, 9, new ResourceLocation(Constants.MOD_ID, "textures/gui/container/chest/gold_chest.png"), 176, 275, 512, 512, H3KStorageTiles.GOLD_CHEST, H3KStorageContainers.GOLD_CHEST),
    DIAMOND(6, 18, new ResourceLocation(Constants.MOD_ID, "textures/gui/container/chest/diamond_chest.png"), 338, 222, 512, 512, H3KStorageTiles.DIAMOND_CHEST, H3KStorageContainers.DIAMOND_CHEST),
    NETHERITE(9, 18, new ResourceLocation(Constants.MOD_ID, "textures/gui/container/chest/netherite_chest.png"), 338, 276, 512, 512, H3KStorageTiles.NETHERITE_CHEST, H3KStorageContainers.NETHERITE_CHEST);

    private final int rows, columns, sizeX, sizeY, textureWidth, textureHeight;
    private final ResourceLocation texture;
    private final Supplier<TileEntityType<H3KChestTile>> tileType;
    private final Supplier<ContainerType<H3KChestContainer>> containerType;

    H3KChestType(int rows, int columns, ResourceLocation texture, int sizeX, int sizeY, int textureWidth, int textureHeight, Supplier<TileEntityType<H3KChestTile>> tileType, Supplier<ContainerType<H3KChestContainer>> containerType) {
        this.rows = rows;
        this.columns = columns;
        this.texture = texture;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        this.tileType = tileType;
        this.containerType = containerType;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public int getTextureWidth() {
        return textureWidth;
    }

    public int getTextureHeight() {
        return textureHeight;
    }

    public ResourceLocation getTexture() {
        return texture;
    }

    public int getSize() {
        return getColumns() * getRows();
    }

    public H3KChestTile createTile() {
        return new H3KChestTile(this);
    }

    public TileEntityType<H3KChestTile> getTileType() {
        return tileType.get();
    }

    public H3KChestContainer createContainer(int windowId, IInventory inventory, PlayerInventory playerInventory) {
        return new H3KChestContainer(this, windowId, inventory, playerInventory);
    }

    public H3KChestContainer createContainer(int windowId, PlayerInventory inv, PacketBuffer data) {
        return createContainer(windowId, new Inventory(getSize()), inv);
    }

    public ContainerType<H3KChestContainer> getContainerType() {
        return containerType.get();
    }

    public H3KChestScreen createScreen(H3KChestContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        return new H3KChestScreen(this, screenContainer, inv, titleIn);
    }
}
