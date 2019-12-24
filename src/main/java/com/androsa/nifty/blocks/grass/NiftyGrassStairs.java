package com.androsa.nifty.blocks.grass;

import com.androsa.nifty.ModBlocks;
import com.androsa.nifty.NiftyBlock;
import com.androsa.nifty.blocks.NiftyStairs;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class NiftyGrassStairs extends NiftyStairs {

    public NiftyGrassStairs() {
        super(NiftyBlock.GRASS, false);
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn, Hand hand, BlockRayTraceResult result) {
        ItemStack itemstack = playerIn.getHeldItem(hand);

        if (!itemstack.isEmpty()) {
            if (itemstack.getItem() instanceof HoeItem) {
                BlockState blockstate = worldIn.getBlockState(pos);
                worldIn.setBlockState(pos, ModBlocks.dirt_stairs.get().getDefaultState().with(FACING, blockstate.get(FACING)).with(SHAPE, blockstate.get(SHAPE)).with(HALF, blockstate.get(HALF)).with(WATERLOGGED, blockstate.get(WATERLOGGED)), 3);
                worldIn.playSound(null, pos, SoundEvents.BLOCK_GRAVEL_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
                itemstack.damageItem(1, playerIn, (user) -> user.sendBreakAnimation(hand));
                return true;
            } else if (itemstack.getItem() instanceof ShovelItem) {
                BlockState blockstate = worldIn.getBlockState(pos);
                worldIn.setBlockState(pos, ModBlocks.path_stairs.get().getDefaultState().with(FACING, blockstate.get(FACING)).with(SHAPE, blockstate.get(SHAPE)).with(HALF, blockstate.get(HALF)).with(WATERLOGGED, blockstate.get(WATERLOGGED)), 3);
                worldIn.playSound(null, pos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
                itemstack.damageItem(1, playerIn, (user) -> user.sendBreakAnimation(hand));
                return true;
            }
        }
        return super.onBlockActivated(state, worldIn, pos, playerIn, hand, result);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }
}