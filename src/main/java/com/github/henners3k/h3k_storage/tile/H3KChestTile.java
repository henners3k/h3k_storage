package com.github.henners3k.h3k_storage.tile;

import com.github.henners3k.h3k_storage.type.H3KChestType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class H3KChestTile extends TileEntity implements INamedContainerProvider, IInventory {
    private final H3KChestType type;
    private final NonNullList<ItemStack> contents;
    private ITextComponent customName;

    public H3KChestTile(H3KChestType type) {
        super(type.getTileType());
        this.type = type;
        this.contents = NonNullList.withSize(type.getSize(), ItemStack.EMPTY);
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        ItemStackHelper.loadAllItems(nbt, contents);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        ItemStackHelper.saveAllItems(compound, contents);
        return compound;
    }

    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT nbt = new CompoundNBT();
        write(nbt);
        return nbt;
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        read(state, tag);
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(pos, 42, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        BlockState state = world.getBlockState(getPos());
        handleUpdateTag(state, pkt.getNbtCompound());
    }

    public void dropAllContents(World world, BlockPos pos) {
        InventoryHelper.dropInventoryItems(world, pos, this);
    }

    @Override
    public ITextComponent getDisplayName() {
        return customName != null ? customName : getBlockState().getBlock().getTranslatedName();
    }

    public void setCustomName(ITextComponent customName) {
        this.customName = customName;
    }

    @Nullable
    @Override
    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return type.createContainer(windowId, this, playerInventory);
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
        return contents.get(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        ItemStack itemStack = ItemStackHelper.getAndSplit(contents, index, count);
        if (!itemStack.isEmpty())
            this.markDirty();
        return itemStack;
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(contents, index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        contents.set(index, stack);
        if (stack.getCount() > this.getInventoryStackLimit()) {
            stack.setCount(this.getInventoryStackLimit());
        }
        this.markDirty();
    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
        if (this.world.getTileEntity(this.pos) != this) {
            return false;
        } else {
            return !(player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) > 64.0D);
        }
    }

    @Override
    public void clear() {
        this.contents.clear();
    }
}
