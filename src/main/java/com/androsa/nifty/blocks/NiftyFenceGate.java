package com.androsa.nifty.blocks;

import com.androsa.nifty.registry.ModBlocks;
import com.androsa.nifty.builder.NiftyBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShovelItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Random;
import java.util.function.Supplier;

public class NiftyFenceGate extends FenceGateBlock implements INiftyBlock {

    protected static final VoxelShape PATH_HITBOX_ZAXIS = Block.makeCuboidShape(0.0D, 0.0D, 6.0D, 16.0D, 15.0D, 10.0D);
    protected static final VoxelShape PATH_HITBOX_XAXIS = Block.makeCuboidShape(6.0D, 0.0D, 0.0D, 10.0D, 15.0D, 16.0D);
    protected static final VoxelShape PATH_HITBOX_ZAXIS_INWALL = Block.makeCuboidShape(0.0D, 0.0D, 6.0D, 16.0D, 12.0D, 10.0D);
    protected static final VoxelShape PATH_HITBOX_XAXIS_INWALL = Block.makeCuboidShape(6.0D, 0.0D, 0.0D, 10.0D, 12.0D, 16.0D);
    protected static final VoxelShape PATH_COLLISION_BOX_ZAXIS = Block.makeCuboidShape(0.0D, 0.0D, 6.0D, 16.0D, 23.0D, 10.0D);
    protected static final VoxelShape PATH_COLLISION_BOX_XAXIS = Block.makeCuboidShape(6.0D, 0.0D, 0.0D, 10.0D, 23.0D, 16.0D);
    protected static final VoxelShape PATH_RENDER_BOX_Z = VoxelShapes.or(Block.makeCuboidShape(0.0D, 4.0D, 7.0D, 2.0D, 15.0D, 9.0D), Block.makeCuboidShape(14.0D, 4.0D, 7.0D, 16.0D, 15.0D, 9.0D));
    protected static final VoxelShape PATH_RENDER_BOX_X = VoxelShapes.or(Block.makeCuboidShape(7.0D, 4.0D, 0.0D, 9.0D, 15.0D, 2.0D), Block.makeCuboidShape(7.0D, 4.0D, 14.0D, 9.0D, 15.0D, 16.0D));
    protected static final VoxelShape PATH_RENDER_BOX_INWALL_Z = VoxelShapes.or(Block.makeCuboidShape(0.0D, 1.0D, 7.0D, 2.0D, 12.0D, 9.0D), Block.makeCuboidShape(14.0D, 1.0D, 7.0D, 16.0D, 12.0D, 9.0D));
    protected static final VoxelShape PATH_RENDER_BOX_INWALL_X = VoxelShapes.or(Block.makeCuboidShape(7.0D, 1.0D, 0.0D, 9.0D, 12.0D, 2.0D), Block.makeCuboidShape(7.0D, 1.0D, 14.0D, 9.0D, 12.0D, 16.0D));

    private final NiftyBuilder builder;

    public NiftyFenceGate(Properties props, NiftyBuilder builder) {
        super(props);
        this.builder = builder;
    }

    @Override
    public NiftyBuilder getBuilder() {
        return builder;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        if (builder.isPath) {
            if (state.get(IN_WALL)) {
                return state.get(HORIZONTAL_FACING).getAxis() == Direction.Axis.X ? PATH_HITBOX_XAXIS_INWALL : PATH_HITBOX_ZAXIS_INWALL;
            } else {
                return state.get(HORIZONTAL_FACING).getAxis() == Direction.Axis.X ? PATH_HITBOX_XAXIS : PATH_HITBOX_ZAXIS;
            }
        }
        return super.getShape(state, worldIn, pos, context);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        if (builder.isPath) {
            if (state.get(OPEN)) {
                return VoxelShapes.empty();
            } else {
                return state.get(HORIZONTAL_FACING).getAxis() == Direction.Axis.Z ? PATH_COLLISION_BOX_ZAXIS : PATH_COLLISION_BOX_XAXIS;
            }
        }
        return super.getCollisionShape(state, worldIn, pos, context);
    }

    @Override
    public VoxelShape getRenderShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        if (builder.isPath) {
            if (state.get(IN_WALL)) {
                return state.get(HORIZONTAL_FACING).getAxis() == Direction.Axis.X ? PATH_RENDER_BOX_INWALL_X : PATH_RENDER_BOX_INWALL_Z;
            } else {
                return state.get(HORIZONTAL_FACING).getAxis() == Direction.Axis.X ? PATH_RENDER_BOX_X : PATH_RENDER_BOX_Z;
            }
        }
        return super.getRenderShape(state, worldIn, pos);
    }

    @Override
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
        entityIn.onLivingFall(fallDistance, builder.fallMultiplier);
    }

    @Override
    @Deprecated
    public boolean canProvidePower(BlockState state) {
        return builder.hasPower;
    }

    @Override
    @Deprecated
    public int getWeakPower(BlockState blockState, IBlockReader blockReader, BlockPos pos, Direction side) {
        return builder.hasPower ? 3 : 0;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
        ItemStack itemstack = player.getHeldItem(hand);

        if (builder.isDirt) {
            if (!itemstack.isEmpty() && itemstack.getItem() == Items.BONE_MEAL) {
                this.setBlock(worldIn, pos, ModBlocks.grass_fence_gate);
                worldIn.playSound(null, pos, SoundEvents.BLOCK_GRASS_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);

                if (!player.abilities.isCreativeMode) {
                    itemstack.shrink(1);
                }
                return ActionResultType.SUCCESS;
            }
        }

        if (builder.isGrass) {
            if (itemstack.getItem() instanceof HoeItem) {
                this.setBlock(worldIn, pos, ModBlocks.dirt_fence_gate);
                worldIn.playSound(null, pos, SoundEvents.BLOCK_GRAVEL_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
                itemstack.damageItem(1, player, (user) -> user.sendBreakAnimation(hand));
                return ActionResultType.SUCCESS;
            } else if (itemstack.getItem() instanceof ShovelItem) {
                this.setBlock(worldIn, pos, ModBlocks.path_fence_gate);
                worldIn.playSound(null, pos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
                itemstack.damageItem(1, player, (user) -> user.sendBreakAnimation(hand));
            }
        }

        if (builder.isPath) {
            if (!itemstack.isEmpty() && itemstack.getItem() instanceof HoeItem) {
                this.setBlock(worldIn, pos, ModBlocks.grass_fence_gate);
                worldIn.playSound(null, pos, SoundEvents.BLOCK_GRASS_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
                itemstack.damageItem(1, player, (user) -> user.sendBreakAnimation(hand));
                return ActionResultType.SUCCESS;
            }
        }

        return this.performNormally(state, worldIn, pos, player);
    }

    private ActionResultType performNormally(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        if (!builder.hasPower) {
            return ActionResultType.PASS;
        } else {
            if (state.get(OPEN)) {
                state = state.with(OPEN, Boolean.FALSE);
                worldIn.setBlockState(pos, state, 10);
            } else {
                Direction enumfacing = player.getHorizontalFacing();
                if (state.get(HORIZONTAL_FACING) == enumfacing.getOpposite()) {
                    state = state.with(HORIZONTAL_FACING, enumfacing);
                }

                state = state.with(OPEN, Boolean.TRUE);
                worldIn.setBlockState(pos, state, 10);
            }

            worldIn.playEvent(player, state.get(OPEN) ? 1008 : 1014, pos, 0);
            return ActionResultType.SUCCESS;
        }
    }

    private void setBlock(World world, BlockPos pos, Supplier<? extends Block> block) {
        BlockState state = world.getBlockState(pos);
        world.setBlockState(pos, block.get().getDefaultState()
                .with(HORIZONTAL_FACING, state.get(HORIZONTAL_FACING))
                .with(OPEN, state.get(OPEN))
                .with(POWERED, state.get(POWERED))
                .with(IN_WALL, state.get(IN_WALL)));
    }

    @Override
    public void onBlockHarvested(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (builder.hasConfig) {
            ForgeConfigSpec.BooleanValue val = builder.booleanValue.get();

            if (val == null) {
                throw new NullPointerException(builder.name + " expected a config value but found null.");
            } else {
                if (val.get()) {
                    super.onBlockHarvested(world, pos, state, player);
                }
            }
        }
    }

    @Override
    public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, TileEntity te, ItemStack stack) {
        super.harvestBlock(worldIn, player, pos, state, te, stack);
        if (builder.isIce) {
            if (EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack) == 0) {
                if (worldIn.func_230315_m_().func_236040_e_()) { //doesWaterVaporize
                    worldIn.removeBlock(pos, false);
                    return;
                }

                Material material = worldIn.getBlockState(pos.down()).getMaterial();
                if (material.blocksMovement() || material.isLiquid()) {
                    worldIn.setBlockState(pos, Blocks.WATER.getDefaultState());
                }
            }
        }
    }

    @Override
    @Deprecated
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        super.randomTick(state, worldIn, pos, random);
        if (builder.isIce) {
            if (worldIn.getLightFor(LightType.BLOCK, pos) > 11 - state.getOpacity(worldIn, pos)) {
                this.turnIntoWater(worldIn, pos);
            }
        }
    }

    protected void turnIntoWater(World world, BlockPos pos) {
        if (world.func_230315_m_().func_236040_e_()) { //doesWaterVaporize
            world.removeBlock(pos, false);
        } else {
            world.setBlockState(pos, Blocks.WATER.getDefaultState());
            world.neighborChanged(pos, Blocks.WATER, pos);
        }
    }

    @Override
    @Deprecated
    public PushReaction getPushReaction(BlockState state) {
        return builder.isIce ? PushReaction.NORMAL : super.getPushReaction(state);
    }
}
