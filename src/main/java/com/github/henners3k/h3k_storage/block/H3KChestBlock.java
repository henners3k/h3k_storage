package com.github.henners3k.h3k_storage.block;

import com.github.henners3k.h3k_storage.inventory.H3KChestInventory;
import com.github.henners3k.h3k_storage.tile.H3KChestTile;
import com.github.henners3k.h3k_storage.type.H3KChestType;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.piglin.PiglinTasks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;

@SuppressWarnings("deprecation")
public class H3KChestBlock extends ContainerBlock implements IWaterLoggable {

    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    private static final VoxelShape BODY_SHAPE = Block.makeCuboidShape(1, 0, 1, 15, 14, 15);
    private static final HashMap<Direction, VoxelShape> LATCH_SHAPES = new HashMap<Direction, VoxelShape>() {{
        put(Direction.NORTH, Block.makeCuboidShape(7, 7, 0, 9, 11, 1));
        put(Direction.EAST, Block.makeCuboidShape(15, 7, 7, 16, 11, 9));
        put(Direction.SOUTH, Block.makeCuboidShape(7, 7, 15, 9, 11, 16));
        put(Direction.WEST, Block.makeCuboidShape(0, 7, 7, 1, 11, 9));
    }};

    private final H3KChestType type;

    public H3KChestBlock(H3KChestType type, Properties builder) {
        super(builder);
        this.type = type;
    }


    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new H3KChestTile(type);
    }


    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    @Nonnull
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.or(BODY_SHAPE, BODY_SHAPE, LATCH_SHAPES.get(state.get(FACING)));

    }

    @Override
    @Nonnull
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.isRemote)
            return ActionResultType.SUCCESS;

        INamedContainerProvider iNamedContainerProvider = this.getContainer(state, worldIn, pos);
        if (iNamedContainerProvider != null) {

            player.openContainer(iNamedContainerProvider);
            player.addStat(Stats.CUSTOM.get(Stats.OPEN_CHEST));
            PiglinTasks.func_234478_a_(player, true);
        }

        return ActionResultType.CONSUME;
    }


    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        Direction direction = context.getPlacementHorizontalFacing().getOpposite();
        FluidState fluidstate = context.getWorld().getFluidState(context.getPos());

        return super.getStateForPlacement(context).with(FACING, direction).with(WATERLOGGED, fluidstate.getFluid() == Fluids.WATER);
    }

    @Override
    @Nonnull
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    @Override
    @Nonnull
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }

    @Override
    @Nonnull
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.get(WATERLOGGED))
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));

        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    @Nonnull
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (!stack.hasDisplayName()) return;

        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (tileEntity instanceof H3KChestTile)
            ((H3KChestTile) tileEntity).setCustomName(stack.getDisplayName());
    }

    @Override
    public boolean hasComparatorInputOverride(BlockState state) {
        return true;
    }

    private H3KChestInventory getInventory(BlockState blockState, World worldIn, BlockPos pos) {
        TileEntity tileEntity = worldIn.getTileEntity(pos);

        if (!(tileEntity instanceof H3KChestTile))
            return null;

        return ((H3KChestTile) tileEntity).getInventory();
    }

    @Override
    public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos) {
        return Container.calcRedstoneFromInventory(this.getInventory(blockState, worldIn, pos));
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos blockPos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity tile = worldIn.getTileEntity(blockPos);
            if (tile instanceof H3KChestTile) {
                ((H3KChestTile) tile).dropAllContents(worldIn, blockPos);
            }
            worldIn.updateComparatorOutputLevel(blockPos, this);
            super.onReplaced(state, worldIn, blockPos, newState, isMoving);
        }
    }
}
