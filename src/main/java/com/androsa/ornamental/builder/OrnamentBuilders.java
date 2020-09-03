package com.androsa.ornamental.builder;

import com.androsa.ornamental.OrnamentalConfig;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.ToolType;

public class OrnamentBuilders {

    public static final OrnamentBuilder IRON = new OrnamentBuilder("iron")
            .properties(Material.IRON)
            .sound(SoundType.METAL)
            .hardnessAndResistance(5.0F, 10.0F)
            .tool(ToolType.PICKAXE, 1, true)
            .config(() -> OrnamentalConfig.showIronBlocks);

    public static final OrnamentBuilder GOLD = new OrnamentBuilder("gold")
            .properties(Material.IRON, MaterialColor.GOLD)
            .sound(SoundType.METAL)
            .hardnessAndResistance(5.0F, 10.0F)
            .tool(ToolType.PICKAXE, 2, true)
            .config(() -> OrnamentalConfig.showGoldBlocks);

    public static final OrnamentBuilder DIAMOND = new OrnamentBuilder("diamond")
            .properties(Material.IRON, MaterialColor.DIAMOND)
            .sound(SoundType.METAL)
            .hardnessAndResistance(5.0F, 10.0F)
            .tool(ToolType.PICKAXE, 2, true)
            .config(() -> OrnamentalConfig.showDiamondBlocks);

    public static final OrnamentBuilder EMERALD = new OrnamentBuilder("emerald")
            .properties(Material.IRON, MaterialColor.EMERALD)
            .sound(SoundType.METAL)
            .hardnessAndResistance(5.0F, 10.0F)
            .tool(ToolType.PICKAXE, 2, true)
            .config(() -> OrnamentalConfig.showEmeraldBlocks);

    public static final OrnamentBuilder LAPIS = new OrnamentBuilder("lapis")
            .properties(Material.IRON, MaterialColor.LAPIS)
            .hardnessAndResistance(5.0F, 10.0F)
            .tool(ToolType.PICKAXE, 1, true)
            .config(() -> OrnamentalConfig.showLapisBlocks);

    public static final OrnamentBuilder OBSIDIAN = new OrnamentBuilder("obsidian")
            .properties(Material.ROCK, MaterialColor.BLACK)
            .hardnessAndResistance(50.0F, 2000.0F)
            .tool(ToolType.PICKAXE, 3, true)
            .config(() -> OrnamentalConfig.showObsidianBlocks);

    public static final OrnamentBuilder COAL = new OrnamentBuilder("coal")
            .properties(Material.ROCK, MaterialColor.BLACK)
            .hardnessAndResistance(5.0F, 10.0F)
            .tool(ToolType.PICKAXE, true)
            .burnTime(10500, 5250, 4000, 8000, 12000, 5250)
            .canOpen()
            .config(() -> OrnamentalConfig.showCoalBlocks);

    public static final OrnamentBuilder REDSTONE = new OrnamentBuilder("redstone")
            .properties(Material.IRON, MaterialColor.TNT)
            .sound(SoundType.METAL)
            .hardnessAndResistance(5.0F, 10.0F)
            .tool(ToolType.PICKAXE, true)
            .hasPower()
            .config(() -> OrnamentalConfig.showRedstoneBlocks);

    public static final OrnamentBuilder MISSINGNO = new OrnamentBuilder("missingno")
            .properties(Material.IRON, MaterialColor.MAGENTA)
            .sound(SoundType.METAL)
            .hardnessAndResistance(5.0F, 10.0F)
            .tool(ToolType.PICKAXE, 2, true)
            .config(() -> OrnamentalConfig.showMissingnoBlocks);

    public static final OrnamentBuilder CLAY = new OrnamentBuilder("clay")
            .properties(Material.CLAY)
            .sound(SoundType.GROUND)
            .hardnessAndResistance(0.6F)
            .tool(ToolType.SHOVEL, false)
            .canOpen()
            .config(() -> OrnamentalConfig.showClayBlocks);

    public static final OrnamentBuilder DIRT = new OrnamentBuilder("dirt")
            .properties(Material.EARTH)
            .sound(SoundType.GROUND)
            .hardness(0.5F)
            .tool(ToolType.SHOVEL, false)
            .canOpen()
            .boneMealToGrass()
            .config(() -> OrnamentalConfig.showDirtBlocks);

    public static final OrnamentBuilder GRASS = new OrnamentBuilder("grass")
            .properties(Material.ORGANIC)
            .sound(SoundType.PLANT)
            .hardness(0.6F)
            .tool(ToolType.SHOVEL, false)
            .canOpen()
            .hoeToDirt()
            .shovelToPath()
            .config(() -> OrnamentalConfig.showGrassBlocks);

    public static final OrnamentBuilder HAY = new OrnamentBuilder("hay")
            .properties(Material.ORGANIC, MaterialColor.YELLOW)
            .sound(SoundType.PLANT)
            .hardness(0.5F)
            .fall(0.2F)
            .canOpen()
            .config(() -> OrnamentalConfig.showHayBlocks);

    public static final OrnamentBuilder PATH = new OrnamentBuilder("grass_path")
            .properties(Material.EARTH)
            .sound(SoundType.PLANT)
            .hardness(0.6F)
            .tool(ToolType.SHOVEL, false)
            .canOpen()
            .hoeToGrass()
            .usePathShapes()
            .config(() -> OrnamentalConfig.showPathBlocks);

    public static final OrnamentBuilder BRICK = new OrnamentBuilder("brick")
            .properties(Material.ROCK, MaterialColor.RED)
            .hardnessAndResistance(2.0F, 6.0F)
            .tool(ToolType.PICKAXE, true)
            .config(() -> OrnamentalConfig.showBrickBlocks);

    public static final OrnamentBuilder QUARTZ = new OrnamentBuilder("quartz")
            .properties(Material.ROCK, MaterialColor.QUARTZ)
            .hardnessAndResistance(0.8F)
            .tool(ToolType.PICKAXE, true)
            .config(() -> OrnamentalConfig.showQuartzBlocks);

    public static final OrnamentBuilder BONE = new OrnamentBuilder("bone")
            .properties(Material.ROCK, MaterialColor.SAND)
            .hardnessAndResistance(2.0F)
            .tool(ToolType.PICKAXE, true)
            .canOpen()
            .config(() -> OrnamentalConfig.showBoneBlocks);

    public static final OrnamentBuilder NETHER_BRICK = new OrnamentBuilder("nether_brick")
            .properties(Material.ROCK, MaterialColor.NETHERRACK)
            .hardnessAndResistance(2.0F, 6.0F)
            .tool(ToolType.PICKAXE, true)
            .config(() -> OrnamentalConfig.showNetherBrickBlocks);

    public static final OrnamentBuilder RED_NETHER_BRICK = new OrnamentBuilder("red_nether_brick")
            .properties(Material.ROCK, MaterialColor.NETHERRACK)
            .hardnessAndResistance(2.0F, 6.0F)
            .tool(ToolType.PICKAXE, true)
            .config(() -> OrnamentalConfig.showRedNetherBrickBlocks);

    public static final OrnamentBuilder SNOW = new OrnamentBuilder("snow")
            .properties(Material.SNOW_BLOCK)
            .sound(SoundType.SNOW)
            .hardnessAndResistance(0.1F)
            .tool(ToolType.SHOVEL, true)
            .canOpen()
            .config(() -> OrnamentalConfig.showSnowBlocks);

    public static final OrnamentBuilder ICE = new OrnamentBuilder("ice")
            .properties(Material.ICE)
            .sound(SoundType.GLASS)
            .hardnessAndResistance(0.5F)
            .tool(ToolType.PICKAXE, false)
            .slip(0.98F)
            .canOpen()
            .ticks()
            .canMelt(Blocks.WATER, true)
            .notSolid()
            .doBreakableBlockCull()
            .pushReactOverride(PushReaction.NORMAL)
            .setCanEntitySpawn((state, reader, pos, type) -> type == EntityType.POLAR_BEAR && state.isSolidSide(reader, pos, Direction.UP))
            .config(() -> OrnamentalConfig.showIceBlocks);

    public static final OrnamentBuilder PACKED_ICE = new OrnamentBuilder("packed_ice")
            .properties(Material.PACKED_ICE)
            .sound(SoundType.GLASS)
            .hardnessAndResistance(0.5F)
            .tool(ToolType.PICKAXE, false)
            .slip(0.98F)
            .canOpen()
            .config(() -> OrnamentalConfig.showPackedIceBlocks);

    public static final OrnamentBuilder BLUE_ICE = new OrnamentBuilder("blue_ice")
            .properties(Material.PACKED_ICE)
            .sound(SoundType.GLASS)
            .hardnessAndResistance(2.8F)
            .tool(ToolType.PICKAXE, false)
            .slip(0.989F)
            .canOpen()
            .config(() -> OrnamentalConfig.showBlueIceBlocks);

    public static final OrnamentBuilder NETHERITE = new OrnamentBuilder("netherite")
            .properties(Material.IRON, MaterialColor.BLACK)
            .sound(SoundType.NETHERITE)
            .hardnessAndResistance(50.0F, 1200.0F)
            .tool(ToolType.PICKAXE, 3, true)
            .isFireproof()
            .config(() -> OrnamentalConfig.showNetheriteBlocks);
}
