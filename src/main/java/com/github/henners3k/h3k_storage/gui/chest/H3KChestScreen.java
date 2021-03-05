package com.github.henners3k.h3k_storage.gui.chest;

import com.github.henners3k.h3k_storage.type.H3KChestType;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import org.lwjgl.opengl.GL11;

public class H3KChestScreen extends ContainerScreen<H3KChestContainer> implements IHasContainer<H3KChestContainer> {

    private final H3KChestType type;

    public H3KChestScreen(H3KChestType type, H3KChestContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.type = type;

        this.xSize = type.getSizeX();
        this.ySize = type.getSizeY();
        this.playerInventoryTitleX = 8 + (18 * (type.getColumns() - 9)) / 2;
        this.playerInventoryTitleY = ySize - 94;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        GL11.glColor4f(1F, 1F, 1F, 1F);
        this.minecraft.getTextureManager().bindTexture(type.getTexture());

        int x1 = (this.width - this.xSize) / 2;
        int y1 = (this.height - this.ySize) / 2;

        blit(matrixStack, x1, y1, 0, 0, this.xSize, this.ySize, type.getTextureWidth(), type.getTextureHeight());
    }
}
