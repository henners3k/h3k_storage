package com.github.henners3k.h3k_storage.tile;

import com.github.henners3k.h3k_storage.inventory.H3KChestInventory;
import com.github.henners3k.h3k_storage.mod.Constants;
import com.github.henners3k.h3k_storage.type.H3KChestType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class H3KChestTile extends TileEntity implements INamedContainerProvider {
    private final H3KChestType type;
    private final H3KChestInventory inventory;
    private ITextComponent customName;

    public H3KChestTile(H3KChestType type) {
        super(type.getTileType());
        this.type = type;
        this.inventory = new H3KChestInventory(type);
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        CompoundNBT inventoryNBT = nbt.getCompound(Constants.INVENTORY_TAG);
        inventory.deserializeNBT(inventoryNBT);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        CompoundNBT inventoryNBT = inventory.serializeNBT();
        compound.put(Constants.INVENTORY_TAG, inventoryNBT);
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
        InventoryHelper.dropInventoryItems(world, pos, inventory);
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
        return type.createContainer(windowId, inventory, playerInventory);
    }

    public H3KChestInventory getInventory() {
        return inventory;
    }
}
