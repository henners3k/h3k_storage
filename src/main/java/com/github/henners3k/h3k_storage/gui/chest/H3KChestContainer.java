package com.github.henners3k.h3k_storage.gui.chest;

import com.github.henners3k.h3k_storage.type.H3KChestType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class H3KChestContainer extends Container {

    private static final int OFFSET_X = 8;
    private static final int OFFSET_Y = 18;
    private static final int PLAYER_INV_GAP_Y = 14;
    private static final int HOT_BAR_GAP_Y = 4;
    private static final int SLOT_SIZE = 18;

    private final H3KChestType type;
    private final IInventory chestInventory;

    public H3KChestContainer(H3KChestType type, int windowId, IInventory chestInventory, PlayerInventory playerInventory) {
        super(type.getContainerType(), windowId);
        this.type = type;
        this.chestInventory = chestInventory;

        // Chest Slots
        for (int row = 0; row < type.getRows(); row++)
            for (int col = 0; col < type.getColumns(); col++) {
                int i = row * type.getColumns() + col;

                int x = OFFSET_X + col * SLOT_SIZE;
                int y = OFFSET_Y + row * SLOT_SIZE;

                addSlot(new Slot(chestInventory, i, x, y));
            }

        // Player Inventory
        int playerInvOffsetX = OFFSET_X + (SLOT_SIZE * (type.getColumns() - 9)) / 2; // Player Inventory is centered
        int playerInvStartY = OFFSET_Y + type.getRows() * SLOT_SIZE + PLAYER_INV_GAP_Y;

        for (int row = 0; row < 3; row++)
            for (int col = 0; col < 9; col++) {
                int i = (row + 1) * 9 + col; // First 9 are Hot Bar

                int x = playerInvOffsetX + col * SLOT_SIZE;
                int y = playerInvStartY + row * SLOT_SIZE;

                addSlot(new Slot(playerInventory, i, x, y));
            }

        // Hot Bar
        int hotBarY = playerInvStartY + SLOT_SIZE * 3 + HOT_BAR_GAP_Y;

        for (int i = 0; i < 9; i++) {
            int x = playerInvOffsetX + i * SLOT_SIZE;
            addSlot(new Slot(playerInventory, i, x, hotBarY));
        }
    }

    @Override
    public boolean canInteractWith(@Nonnull PlayerEntity playerIn) {
        return chestInventory.isUsableByPlayer(playerIn);
    }

    @Nonnull
    @Override
    public ItemStack transferStackInSlot(@Nonnull PlayerEntity playerIn, int index) {
        Slot sourceSlot = inventorySlots.get(index);
        if (sourceSlot == null || !sourceSlot.getHasStack()) return ItemStack.EMPTY;
        ItemStack sourceStack = sourceSlot.getStack();
        ItemStack copyOfSourceStack = sourceStack.copy();

        int chestSlotCount = type.getRows() * type.getColumns();

        if (index >= chestSlotCount && index < chestSlotCount + 36) {
            if (!mergeItemStack(sourceStack, 0, chestSlotCount, false)) {
                return ItemStack.EMPTY;
            }
        } else if (index < chestSlotCount) {
            if (!mergeItemStack(sourceStack, chestSlotCount, chestSlotCount + 36, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            return ItemStack.EMPTY;
        }

        if (sourceStack.getCount() == 0) {
            sourceSlot.putStack(ItemStack.EMPTY);
        } else {
            sourceSlot.onSlotChanged();
        }

        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }
}
