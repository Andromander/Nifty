package com.androsa.ornamental.data.provider;

import com.androsa.ornamental.builder.OrnamentBuilder;
import com.androsa.ornamental.blocks.*;
import com.androsa.ornamental.data.conditions.ConfigCondition;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.common.data.ForgeRecipeProvider;

import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class OrnamentalRecipeProvider extends ForgeRecipeProvider implements IConditionBuilder {

    private final String modID;

    public OrnamentalRecipeProvider(DataGenerator generator, String modid) {
        super(generator);
        this.modID = modid;
    }

    private ResourceLocation loc(String name) {
        return new ResourceLocation(modID, name);
    }

    public void stairs(Consumer<IFinishedRecipe> consumer, Supplier<? extends OrnamentStairs> result, Block ingredient) {
        OrnamentBuilder builder = result.get().getBuilder();

        ShapedRecipeBuilder recipe = ShapedRecipeBuilder.shaped(result.get(), 8)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .define('#', ingredient)
                .unlockedBy("has_" + builder.name, has(ingredient));

        if (builder.hasConfig) {
            ConditionalRecipe.builder()
                    .addCondition(new ConfigCondition(builder.booleanValue.get().getPath().get(0)))
                    .addRecipe(recipe::save)
                    .build(consumer, loc(builder.name + "_stairs"));
        } else {
            recipe.save(consumer, loc(builder.name + "_stairs"));
        }
    }

    public void slab(Consumer<IFinishedRecipe> consumer, Supplier<? extends OrnamentSlab> result, Block ingredient) {
        OrnamentBuilder builder = result.get().getBuilder();

        ShapedRecipeBuilder recipe = ShapedRecipeBuilder.shaped(result.get(), 6)
                .pattern("###")
                .define('#', ingredient)
                .unlockedBy("has_" + builder.name, has(ingredient));

        if (builder.hasConfig) {
            ConditionalRecipe.builder()
                    .addCondition(new ConfigCondition(builder.booleanValue.get().getPath().get(0)))
                    .addRecipe(recipe::save)
                    .build(consumer, loc(builder.name + "_slab"));
        } else {
            recipe.save(consumer, loc(builder.name + "_slab"));
        }
    }

    public void slabOverride(Consumer<IFinishedRecipe> consumer, Supplier<? extends OrnamentSlab> result, Block ingredient) {
		OrnamentBuilder builder = result.get().getBuilder();

		ShapedRecipeBuilder recipe = ShapedRecipeBuilder.shaped(result.get(), 6)
				.pattern(" / ")
				.pattern("###")
				.define('/', ItemTags.SLABS)
				.define('#', ingredient)
				.unlockedBy("has_" + builder.name, has(ingredient));

		if (builder.hasConfig) {
			ConditionalRecipe.builder()
					.addCondition(new ConfigCondition(builder.booleanValue.get().getPath().get(0)))
					.addRecipe(recipe::save)
					.build(consumer, loc(builder.name + "_slab"));
		} else {
			recipe.save(consumer, loc(builder.name + "_slab"));
		}
	}

    public void fence(Consumer<IFinishedRecipe> consumer, Supplier<? extends OrnamentFence> result, Block bigItem, Supplier<? extends SlabBlock> smallItem) {
        fence(consumer, result, bigItem, smallItem.get());
    }

    public void fence(Consumer<IFinishedRecipe> consumer, Supplier<? extends OrnamentFence> result, Block bigItem, IItemProvider smallItem) {
        OrnamentBuilder builder = result.get().getBuilder();

        ShapedRecipeBuilder recipe = ShapedRecipeBuilder.shaped(result.get(), 3)
                .pattern("#/#")
                .pattern("#/#")
                .define('#', bigItem)
                .define('/', smallItem)
                .unlockedBy("has_" + builder.name, has(bigItem));

        if (builder.hasConfig) {
            ConditionalRecipe.builder()
                    .addCondition(new ConfigCondition(builder.booleanValue.get().getPath().get(0)))
                    .addRecipe(recipe::save)
                    .build(consumer, loc(builder.name + "_fence"));
        } else {
            recipe.save(consumer, loc(builder.name + "_fence"));
        }
    }

    public void trapdoor(Consumer<IFinishedRecipe> consumer, Supplier<? extends OrnamentTrapDoor> result, Supplier<? extends OrnamentSlab> ingredient) {
        trapdoor(consumer, result, ingredient.get());
    }

    public void trapdoor(Consumer<IFinishedRecipe> consumer, Supplier<? extends OrnamentTrapDoor> result, IItemProvider ingredient) {
        OrnamentBuilder builder = result.get().getBuilder();

        ShapedRecipeBuilder recipe = ShapedRecipeBuilder.shaped(result.get())
                .pattern("##")
                .pattern("##")
                .define('#', ingredient)
                .unlockedBy("has_" + builder.name, has(ingredient));

        if (builder.hasConfig) {
            ConditionalRecipe.builder()
                    .addCondition(new ConfigCondition(builder.booleanValue.get().getPath().get(0)))
                    .addRecipe(recipe::save)
                    .build(consumer, loc(builder.name + "_trapdoor"));
        } else {
            recipe.save(consumer, loc(builder.name + "_trapdoor"));
        }
    }

    public void trapdoorWide(Consumer<IFinishedRecipe> consumer, Supplier<? extends OrnamentTrapDoor> result, Supplier<? extends OrnamentSlab> ingredient) {
        trapdoorWide(consumer, result, ingredient.get());
    }

    public void trapdoorWide(Consumer<IFinishedRecipe> consumer, Supplier<? extends OrnamentTrapDoor> result, IItemProvider ingredient) {
        OrnamentBuilder builder = result.get().getBuilder();

        ShapedRecipeBuilder recipe = ShapedRecipeBuilder.shaped(result.get())
                .pattern("###")
                .pattern("###")
                .define('#', ingredient)
                .unlockedBy("has_" + builder.name, has(ingredient));

        if (builder.hasConfig) {
            ConditionalRecipe.builder()
                    .addCondition(new ConfigCondition(builder.booleanValue.get().getPath().get(0)))
                    .addRecipe(recipe::save)
                    .build(consumer, loc(builder.name + "_trapdoor"));
        } else {
            recipe.save(consumer, loc(builder.name + "_trapdoor"));
        }
    }

    public void fencegate(Consumer<IFinishedRecipe> consumer, Supplier<? extends OrnamentFenceGate> result, Block bigItem, Supplier<? extends OrnamentSlab> smallItem) {
        fencegate(consumer, result, bigItem, smallItem.get());
    }

    public void fencegate(Consumer<IFinishedRecipe> consumer, Supplier<? extends OrnamentFenceGate> result, Block bigItem, IItemProvider smallItem) {
        OrnamentBuilder builder = result.get().getBuilder();

        ShapedRecipeBuilder recipe = ShapedRecipeBuilder.shaped(result.get())
                .pattern("/#/")
                .pattern("/#/")
                .define('#', bigItem)
                .define('/', smallItem)
                .unlockedBy("has_" + builder.name, has(bigItem));

        if (builder.hasConfig) {
            ConditionalRecipe.builder()
                    .addCondition(new ConfigCondition(builder.booleanValue.get().getPath().get(0)))
                    .addRecipe(recipe::save)
                    .build(consumer, loc(builder.name + "_fence_gate"));
        } else {
            recipe.save(consumer, loc(builder.name + "_fence_gate"));
        }
    }

    public void door(Consumer<IFinishedRecipe> consumer, Supplier<? extends OrnamentDoor> result, Supplier<? extends OrnamentSlab> ingredient) {
        door(consumer, result, ingredient.get());
    }

    public void door(Consumer<IFinishedRecipe> consumer, Supplier<? extends OrnamentDoor> result, IItemProvider ingredient) {
        OrnamentBuilder builder = result.get().getBuilder();

        ShapedRecipeBuilder recipe = ShapedRecipeBuilder.shaped(result.get())
                .pattern("##")
                .pattern("##")
                .pattern("##")
                .define('#', ingredient)
                .unlockedBy("has_" + builder.name, has(ingredient));

        if (builder.hasConfig) {
            ConditionalRecipe.builder()
                    .addCondition(new ConfigCondition(builder.booleanValue.get().getPath().get(0)))
                    .addRecipe(recipe::save)
                    .build(consumer, loc(builder.name + "_door"));
        } else {
            recipe.save(consumer, loc(builder.name + "_door"));
        }
    }

    public void pole(Consumer<IFinishedRecipe> consumer, Supplier<? extends OrnamentPole> result, Supplier<? extends OrnamentSlab> ingredient) {
        pole(consumer, result, ingredient.get());
    }

    public void pole(Consumer<IFinishedRecipe> consumer, Supplier<? extends OrnamentPole> result, IItemProvider ingredient) {
        OrnamentBuilder builder = result.get().getBuilder();

        ShapedRecipeBuilder recipe = ShapedRecipeBuilder.shaped(result.get(), 6)
                .pattern("#")
                .pattern("#")
                .pattern("#")
                .define('#', ingredient)
                .unlockedBy("has_" + builder.name, has(ingredient));

        if (builder.hasConfig) {
            ConditionalRecipe.builder()
                    .addCondition(new ConfigCondition(builder.booleanValue.get().getPath().get(0)))
                    .addRecipe(recipe::save)
                    .build(consumer, loc(builder.name + "_pole"));
        } else {
            recipe.save(consumer, loc(builder.name + "_pole"));
        }
    }

    public void beam(Consumer<IFinishedRecipe> consumer, Supplier<? extends OrnamentBeam> result, Supplier<? extends OrnamentSlab> ingredient) {
        beam(consumer, result, ingredient.get());
    }

    public void beam(Consumer<IFinishedRecipe> consumer, Supplier<? extends OrnamentBeam> result, IItemProvider ingredient) {
        OrnamentBuilder builder = result.get().getBuilder();

        ShapedRecipeBuilder recipe = ShapedRecipeBuilder.shaped(result.get(), 6)
                .pattern("###")
                .define('#', ingredient)
                .unlockedBy("has_" + builder.name, has(ingredient));

        if (builder.hasConfig) {
            ConditionalRecipe.builder()
                    .addCondition(new ConfigCondition(builder.booleanValue.get().getPath().get(0)))
                    .addRecipe(recipe::save)
                    .build(consumer, loc(builder.name + "_beam"));
        } else {
            recipe.save(consumer, loc(builder.name + "_beam"));
        }
    }

    public void convertPoleBeam(Consumer<IFinishedRecipe> consumer, Supplier<? extends OrnamentPole> pole, Supplier<? extends OrnamentBeam> beam) {
        OrnamentBuilder polebuilder = pole.get().getBuilder();
        OrnamentBuilder beambuilder = beam.get().getBuilder();

        ShapelessRecipeBuilder polerecipe = ShapelessRecipeBuilder.shapeless(beam.get())
                .requires(pole.get())
                .unlockedBy("has_" + polebuilder.name, has(pole.get()));
        ShapelessRecipeBuilder beamrecipe = ShapelessRecipeBuilder.shapeless(pole.get())
                .requires(beam.get())
                .unlockedBy("has_" + beambuilder.name, has(beam.get()));

        if (polebuilder.hasConfig) {
            ConditionalRecipe.builder()
                    .addCondition(new ConfigCondition(polebuilder.booleanValue.get().getPath().get(0)))
                    .addRecipe(polerecipe::save)
                    .build(consumer, loc(polebuilder.name + "_pole_to_beam"));
        } else {
            polerecipe.save(consumer, loc(polebuilder.name + "_pole_to_beam"));
        }

        if (beambuilder.hasConfig) {
            ConditionalRecipe.builder()
                    .addCondition(new ConfigCondition(beambuilder.booleanValue.get().getPath().get(0)))
                    .addRecipe(beamrecipe::save)
                    .build(consumer, loc(beambuilder.name + "_beam_to_pole"));
        } else {
            beamrecipe.save(consumer, loc(beambuilder.name + "_beam_to_pole"));
        }
    }

    public void wall(Consumer<IFinishedRecipe> consumer, Supplier<? extends OrnamentWall> result, IItemProvider ingredient) {
        OrnamentBuilder builder = result.get().getBuilder();

        ShapedRecipeBuilder recipe = ShapedRecipeBuilder.shaped(result.get())
                .pattern("###")
                .pattern("###")
                .define('#', ingredient)
                .unlockedBy("has_" + builder.name, has(ingredient));

        if (builder.hasConfig) {
            ConditionalRecipe.builder()
                    .addCondition(new ConfigCondition(builder.booleanValue.get().getPath().get(0)))
                    .addRecipe(recipe::save)
                    .build(consumer, loc(builder.name + "_wall"));
        } else {
            recipe.save(consumer, loc(builder.name + "_wall"));
        }
    }
}
