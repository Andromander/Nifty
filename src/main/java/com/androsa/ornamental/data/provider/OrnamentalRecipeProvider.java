package com.androsa.ornamental.data.provider;

import com.androsa.ornamental.builder.OrnamentBuilder;
import com.androsa.ornamental.OrnamentalMod;
import com.androsa.ornamental.blocks.*;
import com.androsa.ornamental.data.conditions.ConfigCondition;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.common.data.ForgeRecipeProvider;

import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class OrnamentalRecipeProvider extends ForgeRecipeProvider implements IConditionBuilder {

    public OrnamentalRecipeProvider(DataGenerator generator) {
        super(generator);
    }

    private ResourceLocation loc(String name) {
        return new ResourceLocation(OrnamentalMod.MODID, name);
    }

    public void stairs(Consumer<IFinishedRecipe> consumer, Supplier<? extends OrnamentStairs> result, Block ingredient) {
        OrnamentBuilder builder = result.get().getBuilder();
        ConditionalRecipe.builder()
                .addCondition(new ConfigCondition(builder.booleanValue.get().getPath().get(0)))
                .addRecipe(ShapedRecipeBuilder.shapedRecipe(result.get(), 8)
                        .patternLine("#  ")
                        .patternLine("## ")
                        .patternLine("###")
                        .key('#', ingredient)
                        .addCriterion("has_" + builder.name, hasItem(ingredient))
                        ::build)
                .build(consumer, loc(builder.name + "_stairs"));
    }

    public void slab(Consumer<IFinishedRecipe> consumer, Supplier<? extends OrnamentSlab> result, Block ingredient) {
        OrnamentBuilder builder = result.get().getBuilder();
        ConditionalRecipe.builder()
                .addCondition(new ConfigCondition(builder.booleanValue.get().getPath().get(0)))
                .addRecipe(ShapedRecipeBuilder.shapedRecipe(result.get(), 6)
                        .patternLine("###")
                        .key('#', ingredient)
                        .addCriterion("has_" + builder.name, hasItem(ingredient))
                        ::build)
                .build(consumer, loc(builder.name + "_slab"));
    }

    public void fence(Consumer<IFinishedRecipe> consumer, Supplier<? extends OrnamentFence> result, Block bigItem, Supplier<? extends SlabBlock> smallItem) {
        fence(consumer, result, bigItem, smallItem.get());
    }

    public void fence(Consumer<IFinishedRecipe> consumer, Supplier<? extends OrnamentFence> result, Block bigItem, IItemProvider smallItem) {
        OrnamentBuilder builder = result.get().getBuilder();
        ConditionalRecipe.builder()
                .addCondition(new ConfigCondition(builder.booleanValue.get().getPath().get(0)))
                .addRecipe(ShapedRecipeBuilder.shapedRecipe(result.get(), 3)
                        .patternLine("#/#")
                        .patternLine("#/#")
                        .key('#', bigItem)
                        .key('/', smallItem)
                        .addCriterion("has_" + builder.name, hasItem(bigItem))
                        ::build)
                .build(consumer, loc(builder.name + "_fence"));
    }

    public void trapdoor(Consumer<IFinishedRecipe> consumer, Supplier<? extends OrnamentTrapDoor> result, Supplier<? extends OrnamentSlab> ingredient) {
        trapdoor(consumer, result, ingredient.get());
    }

    public void trapdoor(Consumer<IFinishedRecipe> consumer, Supplier<? extends OrnamentTrapDoor> result, IItemProvider ingredient) {
        OrnamentBuilder builder = result.get().getBuilder();
        ConditionalRecipe.builder()
                .addCondition(new ConfigCondition(builder.booleanValue.get().getPath().get(0)))
                .addRecipe(ShapedRecipeBuilder.shapedRecipe(result.get())
                        .patternLine("##")
                        .patternLine("##")
                        .key('#', ingredient)
                        .addCriterion("has_" + builder.name, hasItem(ingredient))
                        ::build)
                .build(consumer, loc(builder.name + "_trapdoor"));
    }

    public void trapdoorWide(Consumer<IFinishedRecipe> consumer, Supplier<? extends OrnamentTrapDoor> result, Supplier<? extends OrnamentSlab> ingredient) {
        trapdoorWide(consumer, result, ingredient.get());
    }

    public void trapdoorWide(Consumer<IFinishedRecipe> consumer, Supplier<? extends OrnamentTrapDoor> result, IItemProvider ingredient) {
        OrnamentBuilder builder = result.get().getBuilder();
        ConditionalRecipe.builder()
                .addCondition(new ConfigCondition(builder.booleanValue.get().getPath().get(0)))
                .addRecipe(ShapedRecipeBuilder.shapedRecipe(result.get())
                        .patternLine("###")
                        .patternLine("###")
                        .key('#', ingredient)
                        .addCriterion("has_" + builder.name, hasItem(ingredient))
                        ::build)
                .build(consumer, loc(builder.name + "_trapdoor"));
    }

    public void fencegate(Consumer<IFinishedRecipe> consumer, Supplier<? extends OrnamentFenceGate> result, Block bigItem, Supplier<? extends OrnamentSlab> smallItem) {
        fencegate(consumer, result, bigItem, smallItem.get());
    }

    public void fencegate(Consumer<IFinishedRecipe> consumer, Supplier<? extends OrnamentFenceGate> result, Block bigItem, IItemProvider smallItem) {
        OrnamentBuilder builder = result.get().getBuilder();
        ConditionalRecipe.builder()
                .addCondition(new ConfigCondition(builder.booleanValue.get().getPath().get(0)))
                .addRecipe(ShapedRecipeBuilder.shapedRecipe(result.get())
                        .patternLine("/#/")
                        .patternLine("/#/")
                        .key('#', bigItem)
                        .key('/', smallItem)
                        .addCriterion("has_" + builder.name, hasItem(bigItem))
                        ::build)
                .build(consumer, loc(builder.name + "_fence_gate"));
    }

    public void door(Consumer<IFinishedRecipe> consumer, Supplier<? extends OrnamentDoor> result, Supplier<? extends OrnamentSlab> ingredient) {
        door(consumer, result, ingredient.get());
    }

    public void door(Consumer<IFinishedRecipe> consumer, Supplier<? extends OrnamentDoor> result, IItemProvider ingredient) {
        OrnamentBuilder builder = result.get().getBuilder();
        ConditionalRecipe.builder()
                .addCondition(new ConfigCondition(builder.booleanValue.get().getPath().get(0)))
                .addRecipe(ShapedRecipeBuilder.shapedRecipe(result.get())
                        .patternLine("##")
                        .patternLine("##")
                        .patternLine("##")
                        .key('#', ingredient)
                        .addCriterion("has_" + builder.name, hasItem(ingredient))
                        ::build)
                .build(consumer, loc(builder.name + "_door"));
    }
}