package com.androsa.nifty.compat;

import com.androsa.nifty.NiftyBlock;
import com.androsa.nifty.util.ModelUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class NiftyTFFieryStairs extends NiftyTFStairs {

    public NiftyTFFieryStairs(IBlockState state) {
        super(state, NiftyBlock.FIERY);

        useNeighborBrightness = false;
    }

    @Override
    @Deprecated
    @SideOnly(Side.CLIENT)
    public int getPackedLightmapCoords(IBlockState state, IBlockAccess source, BlockPos pos) {
        return 15728880;
    }

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        if ((!entityIn.isImmuneToFire())
                && entityIn instanceof EntityLivingBase
                && (!EnchantmentHelper.hasFrostWalkerEnchantment((EntityLivingBase) entityIn))) {
            entityIn.attackEntityFrom(DamageSource.HOT_FLOOR, 1.0F);
        }
        super.onEntityWalk(worldIn, pos, entityIn);
    }

    @Override
    public boolean isFireSource(World world, BlockPos pos, EnumFacing face) {
        return true;
    }

    @Override
    @Deprecated
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        IBlockState state = blockAccess.getBlockState(pos.offset(side));
        return state.getBlock() != this;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModel() {
        ModelUtil.registerToState(this, 0, getDefaultState().withProperty(FACING, EnumFacing.EAST));
        ModelResourceLocation mrl = new ModelResourceLocation(this.getRegistryName(), "inventory");
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, mrl);
    }
}