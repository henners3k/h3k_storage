package com.github.henners3k.h3k_storage.type;

import com.github.henners3k.h3k_storage.mod.Constants;
import net.minecraft.util.ResourceLocation;

public enum H3KChestType {

    // TODO Textures

    IRON(6, 9, new ResourceLocation(Constants.MOD_ID, "textures/gui/container/chest/iron_chest.png"), 176, 222, 256, 256),
    GOLD(9, 9, new ResourceLocation(Constants.MOD_ID, "textures/gui/container/chest/gold_chest.png"), 176, 275, 512, 512),
    DIAMOND(6, 18, new ResourceLocation(Constants.MOD_ID, "textures/gui/container/chest/diamond_chest.png"), 338, 222, 512, 512),
    NETHERITE(9, 18, new ResourceLocation(Constants.MOD_ID, "textures/gui/container/chest/netherite_chest.pgn"), 338, 276, 512, 512);

    private final int rows, columns, sizeX, sizeY, textureWidth, textureHeight;
    private final ResourceLocation texture;

    H3KChestType(int rows, int columns, ResourceLocation texture, int sizeX, int sizeY, int textureWidth, int textureHeight) {
        this.rows = rows;
        this.columns = columns;
        this.texture = texture;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
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
}
