package com.androsa.ornamental.data.provider;

import com.androsa.ornamental.OrnamentalMod;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ItemModelProvider;

import java.util.function.Supplier;

public abstract class OrnamentalItemModelProvider extends ItemModelProvider {

    public OrnamentalItemModelProvider(DataGenerator generator, ExistingFileHelper helper) {
        super(generator, OrnamentalMod.MODID, helper);
    }

    public String blockName(Supplier<? extends Block> block) {
        return block.get().asItem().getRegistryName().getPath();
    }

    public void blockItem(Supplier<? extends Block> block) {
        withExistingParent(blockName(block), modLoc("block/" + blockName(block)));
    }

    public void blockItemModel(Supplier<? extends Block> block) {
        withExistingParent(blockName(block), mcLoc("item/generated"))
                .texture("layer0", modLoc("item/" + blockName(block)));
    }

    public void blockItemModel(Supplier<? extends Block> block, String name) {
        withExistingParent(blockName(block), mcLoc("item/generated"))
                .texture("layer0", modLoc("item/" + name));
    }

    public void blockItemFence(Supplier<? extends Block> block, String name) {
        withExistingParent(blockName(block), mcLoc("block/fence_inventory"))
                .texture("texture", mcLoc("block/" + name));
    }

    public void blockItemFenceColumn(Supplier<? extends Block> block, String side, String top) {
        withExistingParent(blockName(block), modLoc("block/util/fence_inventory_column"))
                .texture("end", mcLoc("block/" + top))
                .texture("side", mcLoc("block/" + side));
    }

    public void blockItemFenceMissing(Supplier<? extends Block> block) {
        withExistingParent(blockName(block), mcLoc("block/fence_inventory"))
                .texture("texture", modLoc("block/missingno"));
    }

    public void blockItemTrapdoor(Supplier<? extends Block> block) {
        withExistingParent(blockName(block), modLoc("block/" + blockName(block) + "_bottom"));
    }
}