package com.github.henners3k.h3k_storage.inventory;

import com.github.henners3k.h3k_storage.type.H3KChestType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.items.ItemStackHandler;

public class H3KChestInventory implements IInventory {
    private final H3KChestType type;
    private final ItemStackHandler contents;

    public H3KChestInventory(H3KChestType type) {
        this.type = type;
        this.contents = new ItemStackHandler(type.getSize());
    }

    @Override
    public int getSizeInventory() {
        return type.getSize();
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < getSizeInventory(); i++) {
            ItemStack itemStack = getStackInSlot(i);
            if (!itemStack.isEmpty())
                return false;
        }
        return true;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return contents.getStackInSlot(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return contents.extractItem(index, count, false);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        int amount = contents.getSlotLimit(index);
        return contents.extractItem(index, amount, false);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        contents.setStackInSlot(index, stack);
    }

    @Override
    public void markDirty() {

    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
        return true;
    }

    @Override
    public void clear() {
        for (int i = 0; i < getSizeInventory(); i++)
            setInventorySlotContents(i, ItemStack.EMPTY);
    }

    public CompoundNBT serializeNBT() {
        return contents.serializeNBT();
    }

    public void deserializeNBT(CompoundNBT nbt) {
        contents.deserializeNBT(nbt);
    }
}
