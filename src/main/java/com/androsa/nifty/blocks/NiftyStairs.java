package com.androsa.nifty.blocks;

import com.androsa.nifty.ModBlocks;
import com.androsa.nifty.NiftyBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShovelItem;
import net.minecraft.state.properties.Half;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class NiftyStairs extends StairsBlock {

    protected static final VoxelShape PATH_AABB_SLAB_TOP = NiftySlab.PATH_TOP_SHAPE;
    protected static final VoxelShape PATH_AABB_SLAB_BOTTOM = NiftySlab.PATH_BOTTOM_SHAPE;
    protected static final VoxelShape PATH_NWD_CORNER = Block.makeCuboidShape(0.0D, 1.0D, 0.0D, 8.0D, 8.0D, 8.0D);
    protected static final VoxelShape PATH_SWD_CORNER = Block.makeCuboidShape(0.0D, 1.0D, 8.0D, 8.0D, 8.0D, 16.0D);
    protected static final VoxelShape PATH_NWU_CORNER = Block.makeCuboidShape(0.0D, 7.0D, 0.0D, 8.0D, 15.0D, 8.0D);
    protected static final VoxelShape PATH_SWU_CORNER = Block.makeCuboidShape(0.0D, 7.0D, 8.0D, 8.0D, 15.0D, 16.0D);
    protected static final VoxelShape PATH_NED_CORNER = Block.makeCuboidShape(8.0D, 1.0D, 0.0D, 16.0D, 8.0D, 8.0D);
    protected static final VoxelShape PATH_SED_CORNER = Block.makeCuboidShape(8.0D, 1.0D, 8.0D, 16.0D, 8.0D, 16.0D);
    protected static final VoxelShape PATH_NEU_CORNER = Block.makeCuboidShape(8.0D, 7.0D, 0.0D, 16.0D, 15.0D, 8.0D);
    protected static final VoxelShape PATH_SEU_CORNER = Block.makeCuboidShape(8.0D, 7.0D, 8.0D, 16.0D, 15.0D, 16.0D);
    protected static final VoxelShape[] PATH_SLAB_TOP_SHAPES = makeShapes(PATH_AABB_SLAB_TOP, PATH_NWD_CORNER, PATH_NED_CORNER, PATH_SWD_CORNER, PATH_SED_CORNER);
    protected static final VoxelShape[] PATH_SLAB_BOTTOM_SHAPES = makeShapes(PATH_AABB_SLAB_BOTTOM, PATH_NWU_CORNER, PATH_NEU_CORNER, PATH_SWU_CORNER, PATH_SEU_CORNER);
    private static final int[] metaInt = new int[]{12, 5, 3, 10, 14, 13, 7, 11, 13, 7, 11, 14, 8, 4, 1, 2, 4, 1, 2, 8};

    private float fallDamage;
    private NiftyBlock blockType;

    public NiftyStairs(Properties props, NiftyBlock block) {
        super(() -> new Block(Properties.create(block.material, block.color)).getDefaultState(), props.hardnessAndResistance(block.hardness, block.resistance).sound(block.sound).harvestTool(block.tool).harvestLevel(block.level));
        if (block == NiftyBlock.ICE) props.nonOpaque();

        this.fallDamage = block.multiplier;
        this.blockType = block;
    }

    private static VoxelShape[] makeShapes(VoxelShape slabShape, VoxelShape nwCorner, VoxelShape neCorner, VoxelShape swCorner, VoxelShape seCorner) {
        return IntStream.range(0, 16).mapToObj((meta) -> combineShapes(meta, slabShape, nwCorner, neCorner, swCorner, seCorner)).toArray(VoxelShape[]::new);
    }

    private static VoxelShape combineShapes(int bitfield, VoxelShape slabShape, VoxelShape nwCorner, VoxelShape neCorner, VoxelShape swCorner, VoxelShape seCorner) {
        VoxelShape voxelshape = slabShape;
        if ((bitfield & 1) != 0) {
            voxelshape = VoxelShapes.or(slabShape, nwCorner);
        }

        if ((bitfield & 2) != 0) {
            voxelshape = VoxelShapes.or(voxelshape, neCorner);
        }

        if ((bitfield & 4) != 0) {
            voxelshape = VoxelShapes.or(voxelshape, swCorner);
        }

        if ((bitfield & 8) != 0) {
            voxelshape = VoxelShapes.or(voxelshape, seCorner);
        }

        return voxelshape;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        if (blockType == NiftyBlock.PATH)
            return (state.get(HALF) == Half.TOP ? PATH_SLAB_TOP_SHAPES : PATH_SLAB_BOTTOM_SHAPES)[metaInt[this.getShapeMeta(state)]];
        return super.getShape(state, worldIn, pos, context);
    }

    private int getShapeMeta(BlockState state) {
        return state.get(SHAPE).ordinal() * 4 + state.get(FACING).getHorizontalIndex();
    }

    @Override
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
        entityIn.handleFallDamage(fallDistance, fallDamage);
    }

    @Override
    public float getSlipperiness(BlockState state, IWorldReader world, BlockPos pos, Entity entity) {
        switch (blockType) {
            case ICE:
            case PACKED_ICE:
                return 0.98F;
            case BLUE_ICE:
                return 0.989F;
            default:
                return super.getSlipperiness(state, world, pos, entity);
        }
    }

    @Override
    public boolean ticksRandomly(BlockState state) {
        return blockType == NiftyBlock.ICE;
    }

    @Override
    @Deprecated
    public boolean canProvidePower(BlockState state) {
        return blockType == NiftyBlock.REDSTONE;
    }

    @Override
    @Deprecated
    public int getWeakPower(BlockState blockState, IBlockReader blockReader, BlockPos pos, Direction side) {
        return blockType == NiftyBlock.REDSTONE ? 11 : 0;
    }

    @Override
    public ActionResultType onUse(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
        ItemStack itemstack = player.getHeldItem(hand);

        switch (blockType) {
            case DIRT:
                if (!itemstack.isEmpty() && itemstack.getItem() == Items.BONE_MEAL) {
                    this.setBlock(worldIn, pos, ModBlocks.grass_stairs);
                    worldIn.playSound(null, pos, SoundEvents.BLOCK_GRASS_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);

                    if (!player.abilities.isCreativeMode) {
                        itemstack.shrink(1);
                    }
                    return ActionResultType.SUCCESS;
                }
                return super.onUse(state, worldIn, pos, player, hand, result);

            case GRASS:
                if (!itemstack.isEmpty()) {
                    if (itemstack.getItem() instanceof HoeItem) {
                        this.setBlock(worldIn, pos, ModBlocks.dirt_stairs);
                        worldIn.playSound(null, pos, SoundEvents.BLOCK_GRAVEL_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
                        itemstack.damageItem(1, player, (user) -> user.sendBreakAnimation(hand));
                        return ActionResultType.SUCCESS;
                    } else if (itemstack.getItem() instanceof ShovelItem) {
                        this.setBlock(worldIn, pos, ModBlocks.path_stairs);
                        worldIn.playSound(null, pos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
                        itemstack.damageItem(1, player, (user) -> user.sendBreakAnimation(hand));
                        return ActionResultType.SUCCESS;
                    }
                }
                return super.onUse(state, worldIn, pos, player, hand, result);

            case PATH:
                if (!itemstack.isEmpty() && itemstack.getItem() instanceof HoeItem) {
                    this.setBlock(worldIn, pos, ModBlocks.grass_stairs);
                    worldIn.playSound(null, pos, SoundEvents.BLOCK_GRASS_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    itemstack.damageItem(1, player, (user) -> user.sendBreakAnimation(hand));
                    return ActionResultType.SUCCESS;
                }
                return super.onUse(state, worldIn, pos, player, hand, result);

            default:
                return super.onUse(state, worldIn, pos, player, hand, result);
        }
    }

    private void setBlock(World world, BlockPos pos, Supplier<? extends Block> block) {
        BlockState state = world.getBlockState(pos);
        world.setBlockState(pos, block.get().getDefaultState()
                .with(FACING, state.get(FACING))
                .with(SHAPE, state.get(SHAPE))
                .with(HALF, state.get(HALF))
                .with(WATERLOGGED, state.get(WATERLOGGED)));
    }

    @Override
    public void onBlockHarvested(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (blockType.booleanValue.get().get()) {
            super.onBlockHarvested(world, pos, state, player);
        }
    }

    @Override
    public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, TileEntity te, ItemStack stack) {
        super.harvestBlock(worldIn, player, pos, state, te, stack);
        if (blockType == NiftyBlock.ICE) {
            if (EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack) == 0) {
                if (worldIn.dimension.doesWaterVaporize()) {
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
    public void scheduledTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        super.scheduledTick(state, worldIn, pos, random);
        if (blockType == NiftyBlock.ICE) {
            if (worldIn.getLightLevel(LightType.BLOCK, pos) > 11 - state.getOpacity(worldIn, pos)) {
                this.turnIntoWater(worldIn, pos);
            }
        }
    }

    private void turnIntoWater(World world, BlockPos pos) {
        if (world.dimension.doesWaterVaporize()) {
            world.removeBlock(pos, false);
        } else {
            world.setBlockState(pos, Blocks.WATER.getDefaultState());
            world.neighborChanged(pos, Blocks.WATER, pos);
        }
    }

    @Override
    @Deprecated
    public PushReaction getPushReaction(BlockState state) {
        if (blockType == NiftyBlock.ICE)
            return PushReaction.NORMAL;
        return super.getPushReaction(state);
    }

    @Override
    @Deprecated
    public boolean canEntitySpawn(BlockState state, IBlockReader worldIn, BlockPos pos, EntityType<?> type) {
        if (blockType == NiftyBlock.ICE) {
            return type == EntityType.POLAR_BEAR;
        }
        return super.canEntitySpawn(state, worldIn, pos, type);
    }
}
