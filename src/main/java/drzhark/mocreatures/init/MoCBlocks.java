/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.init;

import com.google.common.base.Preconditions;
import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.block.*;
import drzhark.mocreatures.block.MoCBlockSapling.EnumWoodType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSlab;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;

@SuppressWarnings("deprecation")
@Mod.EventBusSubscriber(modid = MoCConstants.MOD_ID)
@GameRegistry.ObjectHolder(MoCConstants.MOD_ID)
public class MoCBlocks {
    @GameRegistry.ObjectHolder("ancient_ore")
    public static MoCBlockOre ancientOre;
    @GameRegistry.ObjectHolder("ancient_silver_block")
    public static Block ancientSilverBlock;
    @GameRegistry.ObjectHolder("carved_silver_sandstone")
    public static Block carvedSilverSandstone;
    @GameRegistry.ObjectHolder("cobbled_wyvstone")
    public static Block cobbledWyvstone;
    @GameRegistry.ObjectHolder("cobbled_wyvstone_slab")
    public static MoCBlockSlab.Half cobbledWyvstoneSlab;
    @GameRegistry.ObjectHolder("cobbled_wyvstone_slab_double")
    public static MoCBlockSlab.Double cobbledWyvstoneSlabDouble;
    @GameRegistry.ObjectHolder("cobbled_wyvstone_stairs")
    public static MoCBlockStairs cobbledWyvstoneStairs;
    @GameRegistry.ObjectHolder("cobbled_wyvstone_wall")
    public static MoCBlockWall cobbledWyvstoneWall;
    @GameRegistry.ObjectHolder("cobbled_deep_wyvstone")
    public static Block cobbledDeepWyvstone;
    @GameRegistry.ObjectHolder("cobbled_deep_wyvstone_slab")
    public static MoCBlockSlab.Half cobbledDeepWyvstoneSlab;
    @GameRegistry.ObjectHolder("cobbled_deep_wyvstone_slab_double")
    public static MoCBlockSlab.Double cobbledDeepWyvstoneSlabDouble;
    @GameRegistry.ObjectHolder("cobbled_deep_wyvstone_stairs")
    public static MoCBlockStairs cobbledDeepWyvstoneStairs;
    @GameRegistry.ObjectHolder("cobbled_deep_wyvstone_wall")
    public static MoCBlockWall cobbledDeepWyvstoneWall;
    @GameRegistry.ObjectHolder("deep_wyvstone")
    public static Block deepWyvstone;
    @GameRegistry.ObjectHolder("deep_wyvwstone_button")
    public static MoCBlockButtonStone deepWyvstoneButton;
    @GameRegistry.ObjectHolder("deep_wyvstone_pressure_plate")
    public static MoCBlockPressurePlateStone deepWyvstonePressurePlate;
    @GameRegistry.ObjectHolder("deep_wyvstone_slab")
    public static MoCBlockSlab.Half deepWyvstoneSlab;
    @GameRegistry.ObjectHolder("deep_wyvstone_slab_double")
    public static MoCBlockSlab.Double deepWyvstoneSlabDouble;
    @GameRegistry.ObjectHolder("deep_wyvstone_stairs")
    public static MoCBlockStairs deepWyvstoneStairs;
    @GameRegistry.ObjectHolder("deep_wyvstone_wall")
    public static MoCBlockWall deepWyvstoneWall;
    @GameRegistry.ObjectHolder("fine_silver_block")
    public static Block fineSilverBlock;
    @GameRegistry.ObjectHolder("firestone")
    public static Block firestone;
    @GameRegistry.ObjectHolder("gleaming_glass")
    public static Block gleamingGlass;
    @GameRegistry.ObjectHolder("gleaming_glass_pane")
    public static Block gleamingGlassPane;
    @GameRegistry.ObjectHolder("mossy_cobbled_wyvstone")
    public static Block mossyCobbledWyvstone;
    @GameRegistry.ObjectHolder("mossy_cobbled_wyvstone_slab")
    public static MoCBlockSlab.Half mossyCobbledWyvstoneSlab;
    @GameRegistry.ObjectHolder("mossy_cobbled_wyvstone_slab_double")
    public static MoCBlockSlab.Double mossyCobbledWyvstoneSlabDouble;
    @GameRegistry.ObjectHolder("mossy_cobbled_wyvstone_stairs")
    public static MoCBlockStairs mossyCobbledWyvstoneStairs;
    @GameRegistry.ObjectHolder("mossy_cobbled_wyvstone_wall")
    public static MoCBlockWall mossyCobbledWyvstoneWall;
    @GameRegistry.ObjectHolder("mossy_cobbled_deep_wyvstone")
    public static Block mossyCobbledDeepWyvstone;
    @GameRegistry.ObjectHolder("mossy_cobbled_deep_wyvstone_slab")
    public static MoCBlockSlab.Half mossyCobbledDeepWyvstoneSlab;
    @GameRegistry.ObjectHolder("mossy_cobbled_deep_wyvstone_slab_double")
    public static MoCBlockSlab.Double mossyCobbledDeepWyvstoneSlabDouble;
    @GameRegistry.ObjectHolder("mossy_cobbled_deep_wyvstone_stairs")
    public static MoCBlockStairs mossyCobbledDeepWyvstoneStairs;
    @GameRegistry.ObjectHolder("mossy_cobbled_deep_wyvstone_wall")
    public static MoCBlockWall mossyCobbledDeepWyvstoneWall;
    @GameRegistry.ObjectHolder("silver_sand")
    public static Block silverSand;
    @GameRegistry.ObjectHolder("silver_sandstone")
    public static Block silverSandstone;
    @GameRegistry.ObjectHolder("silver_sandstone_slab")
    public static MoCBlockSlab.Half silverSandstoneSlab;
    @GameRegistry.ObjectHolder("silver_sandstone_slab_double")
    public static MoCBlockSlab.Double silverSandstoneSlabDouble;
    @GameRegistry.ObjectHolder("silver_sandstone_stairs")
    public static MoCBlockStairs silverSandstoneStairs;
    @GameRegistry.ObjectHolder("silver_sandstone_wall")
    public static MoCBlockWall silverSandstoneWall;
    @GameRegistry.ObjectHolder("smooth_silver_sandstone")
    public static Block smoothSilverSandstone;
    @GameRegistry.ObjectHolder("tall_wyvgrass")
    public static Block tallWyvgrass;
    @GameRegistry.ObjectHolder("wyvern_diamond_ore")
    public static MoCBlockOre wyvernDiamondOre;
    @GameRegistry.ObjectHolder("wyvern_emerald_ore")
    public static MoCBlockOre wyvernEmeraldOre;
    @GameRegistry.ObjectHolder("wyvern_gold_ore")
    public static MoCBlockOre wyvernGoldOre;
    @GameRegistry.ObjectHolder("wyvern_iron_ore")
    public static MoCBlockOre wyvernIronOre;
    @GameRegistry.ObjectHolder("wyvern_lapis_ore")
    public static MoCBlockOre wyvernLapisOre;
    @GameRegistry.ObjectHolder("wyvern_nest_block")
    public static MoCBlockNest wyvernNestBlock;
    @GameRegistry.ObjectHolder("wyvstone")
    public static Block wyvstone;
    @GameRegistry.ObjectHolder("wyvwstone_button")
    public static MoCBlockButtonStone wyvwstoneButton;
    @GameRegistry.ObjectHolder("wyvstone_pressure_plate")
    public static MoCBlockPressurePlateStone wyvstonePressurePlate;
    @GameRegistry.ObjectHolder("wyvstone_slab")
    public static MoCBlockSlab.Half wyvstoneSlab;
    @GameRegistry.ObjectHolder("wyvstone_slab_double")
    public static MoCBlockSlab.Double wyvstoneSlabDouble;
    @GameRegistry.ObjectHolder("wyvstone_stairs")
    public static MoCBlockStairs wyvstoneStairs;
    @GameRegistry.ObjectHolder("wyvstone_wall")
    public static MoCBlockWall wyvstoneWall;
    @GameRegistry.ObjectHolder("wyvgrass")
    public static Block wyvgrass;
    @GameRegistry.ObjectHolder("wyvdirt")
    public static Block wyvdirt;
    @GameRegistry.ObjectHolder("wyvwood_button")
    public static MoCBlockButtonWood wyvwoodButton;
    @GameRegistry.ObjectHolder("wyvwood_door")
    public static MoCBlockDoorWood wyvwoodDoor;
    @GameRegistry.ObjectHolder("wyvwood_fence")
    public static MoCBlockFenceWood wyvwoodFence;
    @GameRegistry.ObjectHolder("wyvwood_fence_gate")
    public static MoCBlockFenceGateWood wyvwoodFenceGate;
    @GameRegistry.ObjectHolder("wyvwood_leaves")
    public static Block wyvwoodLeaves;
    @GameRegistry.ObjectHolder("wyvwood_log")
    public static Block wyvwoodLog;
    @GameRegistry.ObjectHolder("wyvwood_planks")
    public static Block wyvwoodPlanks;
    @GameRegistry.ObjectHolder("wyvwood_sapling")
    public static Block wyvwoodSapling;
    @GameRegistry.ObjectHolder("wyvwood_slab")
    public static MoCBlockSlab.Half wyvwoodSlab;
    @GameRegistry.ObjectHolder("wyvwood_slab_double")
    public static MoCBlockSlab.Double wyvwoodSlabDouble;
    @GameRegistry.ObjectHolder("wyvwood_stairs")
    public static MoCBlockStairs wyvwoodPlanksStairs;
    @GameRegistry.ObjectHolder("wyvwood_trapdoor")
    public static MoCBlockTrapdoorWood wyvwoodTrapdoor;

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(
                setup(new MoCBlockButtonStone(), "deep_wyvstone_button"),
                setup(new MoCBlockButtonStone(), "wyvstone_button"),
                setup(new MoCBlockButtonWood(), "wyvwood_button"),
                setup(new MoCBlockDirt(MapColor.DIRT), "wyvdirt").setHardness(0.6F),
                setup(new MoCBlockDoorWood(MapColor.DIAMOND), "wyvwood_door"),
                setup(new MoCBlockFenceGateWood(MapColor.DIAMOND, true), "wyvwood_fence_gate"),
                setup(new MoCBlockFenceWood(MapColor.DIAMOND, true), "wyvwood_fence"),
                setup(new MoCBlockFirestone(MapColor.ADOBE), "firestone").setHardness(3.0F).setLightLevel(0.5F),
                setup(new MoCBlockGlass(true), "gleaming_glass").setHardness(0.4F),
                setup(new MoCBlockGrass(MapColor.BLUE_STAINED_HARDENED_CLAY), "wyvgrass").setHardness(0.7F),
                setup(new MoCBlockLeaf(MapColor.DIAMOND, true, 100), "wyvwood_leaves").setHardness(0.2F).setLightOpacity(1),
                setup(new MoCBlockLog(MapColor.CYAN_STAINED_HARDENED_CLAY, true), "wyvwood_log").setHardness(2.0F),
                setup(new MoCBlockMetal(MapColor.IRON), "ancient_silver_block").setHardness(3.0F).setResistance(10.0F),
                setup(new MoCBlockMetal(MapColor.CLAY), "fine_silver_block").setHardness(3.0F).setResistance(10.0F),
                setup(new MoCBlockNest(), "wyvern_nest_block").setHardness(0.5F),
                setup(new MoCBlockOre(MapColor.STONE), "ancient_ore").setHardness(3.0F).setResistance(5.0F),
                setup(new MoCBlockOre(MapColor.STONE), "wyvern_diamond_ore").setHardness(4.5F).setResistance(5.0F),
                setup(new MoCBlockOre(MapColor.STONE), "wyvern_emerald_ore").setHardness(4.5F).setResistance(5.0F),
                setup(new MoCBlockOre(MapColor.STONE), "wyvern_gold_ore").setHardness(3.0F).setResistance(5.0F),
                setup(new MoCBlockOre(MapColor.STONE), "wyvern_iron_ore").setHardness(3.0F).setResistance(5.0F),
                setup(new MoCBlockOre(MapColor.STONE), "wyvern_lapis_ore").setHardness(1.5F).setResistance(5.0F),
                setup(new MoCBlockPane(Material.GLASS, SoundType.GLASS, false, true), "gleaming_glass_pane").setHardness(0.4F),
                setup(new MoCBlockPlanks(MapColor.DIAMOND, true), "wyvwood_planks").setHardness(2.0F).setResistance(5.0F),
                setup(new MoCBlockPressurePlateStone(MapColor.STONE), "deep_wyvstone_pressure_plate"),
                setup(new MoCBlockPressurePlateStone(MapColor.STONE), "wyvstone_pressure_plate"),
                setup(new MoCBlockPressurePlateWood(MapColor.DIAMOND), "wyvwood_pressure_plate"),
                setup(new MoCBlockRock(MapColor.CLAY), "carved_silver_sandstone").setHardness(1.2F),
                setup(new MoCBlockRock(MapColor.CLAY), "silver_sandstone").setHardness(1.2F),
                setup(new MoCBlockRock(MapColor.CLAY), "smooth_silver_sandstone").setHardness(1.2F),
                setup(new MoCBlockRock(MapColor.STONE), "cobbled_deep_wyvstone").setHardness(3.5F).setResistance(10.0F),
                setup(new MoCBlockRock(MapColor.STONE), "cobbled_wyvstone").setHardness(2.0F).setResistance(10.0F),
                setup(new MoCBlockRock(MapColor.STONE), "deep_wyvstone").setHardness(3.0F).setResistance(10.0F),
                setup(new MoCBlockRock(MapColor.STONE), "mossy_cobbled_deep_wyvstone").setHardness(3.5F).setResistance(10.0F),
                setup(new MoCBlockRock(MapColor.STONE), "mossy_cobbled_wyvstone").setHardness(2.0F).setResistance(10.0F),
                setup(new MoCBlockRock(MapColor.STONE), "wyvstone").setHardness(1.5F).setResistance(10.0F),
                setup(new MoCBlockSand(MapColor.CLAY), "silver_sand").setHardness(0.6F),
                setup(new MoCBlockSapling(EnumWoodType.WYVWOOD, MapColor.FOLIAGE, true), "wyvwood_sapling").setHardness(0.0F),
                setup(new MoCBlockSlab.Double(Material.ROCK, MapColor.CLAY, false), "silver_sandstone_slab_double").setHardness(1.2F),
                setup(new MoCBlockSlab.Double(Material.ROCK, MapColor.STONE, false), "cobbled_deep_wyvstone_slab_double").setHardness(3.5F).setResistance(10.0F),
                setup(new MoCBlockSlab.Double(Material.ROCK, MapColor.STONE, false), "cobbled_wyvstone_slab_double").setHardness(2.0F).setResistance(10.0F),
                setup(new MoCBlockSlab.Double(Material.ROCK, MapColor.STONE, false), "deep_wyvstone_slab_double").setHardness(3.0F).setResistance(10.0F),
                setup(new MoCBlockSlab.Double(Material.ROCK, MapColor.STONE, false), "mossy_cobbled_deep_wyvstone_slab_double").setHardness(3.5F).setResistance(10.0F),
                setup(new MoCBlockSlab.Double(Material.ROCK, MapColor.STONE, false), "mossy_cobbled_wyvstone_slab_double").setHardness(2.0F).setResistance(10.0F),
                setup(new MoCBlockSlab.Double(Material.ROCK, MapColor.STONE, false), "wyvstone_slab_double").setHardness(1.5F).setResistance(10.0F),
                setup(new MoCBlockSlab.Double(Material.WOOD, MapColor.DIAMOND, true), "wyvwood_slab_double").setHardness(2.0F).setResistance(5.0F),
                setup(new MoCBlockSlab.Half(Material.ROCK, MapColor.CLAY, false), "silver_sandstone_slab").setHardness(1.2F),
                setup(new MoCBlockSlab.Half(Material.ROCK, MapColor.STONE, false), "cobbled_deep_wyvstone_slab").setHardness(3.5F).setResistance(10.0F),
                setup(new MoCBlockSlab.Half(Material.ROCK, MapColor.STONE, false), "cobbled_wyvstone_slab").setHardness(2.0F).setResistance(10.0F),
                setup(new MoCBlockSlab.Half(Material.ROCK, MapColor.STONE, false), "deep_wyvstone_slab").setHardness(3.0F).setResistance(10.0F),
                setup(new MoCBlockSlab.Half(Material.ROCK, MapColor.STONE, false), "mossy_cobbled_deep_wyvstone_slab").setHardness(3.5F).setResistance(10.0F),
                setup(new MoCBlockSlab.Half(Material.ROCK, MapColor.STONE, false), "mossy_cobbled_wyvstone_slab").setHardness(2.0F).setResistance(10.0F),
                setup(new MoCBlockSlab.Half(Material.ROCK, MapColor.STONE, false), "wyvstone_slab").setHardness(1.5F).setResistance(10.0F),
                setup(new MoCBlockSlab.Half(Material.WOOD, MapColor.DIAMOND, true), "wyvwood_slab").setHardness(2.0F).setResistance(5.0F),
                setup(new MoCBlockStairs(new MoCBlockPlanks(MapColor.DIAMOND, true).getDefaultState(), true), "wyvwood_stairs").setHardness(2.0F).setResistance(5.0F),
                setup(new MoCBlockStairs(new MoCBlockRock(MapColor.CLAY).getDefaultState(), false), "silver_sandstone_stairs").setHardness(1.2F),
                setup(new MoCBlockStairs(new MoCBlockRock(MapColor.STONE).getDefaultState(), false), "cobbled_deep_wyvstone_stairs").setHardness(3.5F).setResistance(10.0F),
                setup(new MoCBlockStairs(new MoCBlockRock(MapColor.STONE).getDefaultState(), false), "cobbled_wyvstone_stairs").setHardness(2.0F).setResistance(10.0F),
                setup(new MoCBlockStairs(new MoCBlockRock(MapColor.STONE).getDefaultState(), false), "deep_wyvstone_stairs").setHardness(3.0F).setResistance(10.0F),
                setup(new MoCBlockStairs(new MoCBlockRock(MapColor.STONE).getDefaultState(), false), "mossy_cobbled_deep_wyvstone_stairs").setHardness(3.5F).setResistance(10.0F),
                setup(new MoCBlockStairs(new MoCBlockRock(MapColor.STONE).getDefaultState(), false), "mossy_cobbled_wyvstone_stairs").setHardness(2.0F).setResistance(10.0F),
                setup(new MoCBlockStairs(new MoCBlockRock(MapColor.STONE).getDefaultState(), false), "wyvstone_stairs").setHardness(1.5F).setResistance(10.0F),
                setup(new MoCBlockTallGrass(MapColor.LIGHT_BLUE_STAINED_HARDENED_CLAY, false), "tall_wyvgrass").setHardness(0.0F),
                setup(new MoCBlockTrapdoorWood(MapColor.DIAMOND), "wyvwood_trapdoor"),
                setup(new MoCBlockWall(new MoCBlockRock(MapColor.CLAY), false), "silver_sandstone_wall").setHardness(1.2F),
                setup(new MoCBlockWall(new MoCBlockRock(MapColor.STONE), false), "cobbled_deep_wyvstone_wall").setHardness(3.5F).setResistance(10.0F),
                setup(new MoCBlockWall(new MoCBlockRock(MapColor.STONE), false), "cobbled_wyvstone_wall").setHardness(2.0F).setResistance(10.0F),
                setup(new MoCBlockWall(new MoCBlockRock(MapColor.STONE), false), "deep_wyvstone_wall").setHardness(3.0F).setResistance(10.0F),
                setup(new MoCBlockWall(new MoCBlockRock(MapColor.STONE), false), "mossy_cobbled_deep_wyvstone_wall").setHardness(3.5F).setResistance(10.0F),
                setup(new MoCBlockWall(new MoCBlockRock(MapColor.STONE), false), "mossy_cobbled_wyvstone_wall").setHardness(2.0F).setResistance(10.0F),
                setup(new MoCBlockWall(new MoCBlockRock(MapColor.STONE), false), "wyvstone_wall").setHardness(1.5F).setResistance(10.0F)
        );
    }

    @SubscribeEvent
    public static void registerItemBlocks(RegistryEvent.Register<Item> event) {
        final IForgeRegistry<Item> registry = event.getRegistry();
        ForgeRegistries.BLOCKS.getValues().stream()
                .filter(block -> block.getRegistryName().getNamespace().equals(MoCConstants.MOD_ID))
                .filter(block -> !(block instanceof BlockDoor)) // Doors should not have an item block registered
                .filter(block -> !(block instanceof BlockSlab)) // Slabs should not have an item block registered
                .forEach(block -> registry.register(setup(new ItemBlock(block), block.getRegistryName())));

        registry.register(setup(new ItemSlab(silverSandstoneSlab, silverSandstoneSlab, silverSandstoneSlabDouble), silverSandstoneSlab.getRegistryName()));
        registry.register(setup(new ItemSlab(cobbledDeepWyvstoneSlab, cobbledDeepWyvstoneSlab, cobbledDeepWyvstoneSlabDouble), cobbledDeepWyvstoneSlab.getRegistryName()));
        registry.register(setup(new ItemSlab(cobbledWyvstoneSlab, cobbledWyvstoneSlab, cobbledWyvstoneSlabDouble), cobbledWyvstoneSlab.getRegistryName()));
        registry.register(setup(new ItemSlab(deepWyvstoneSlab, deepWyvstoneSlab, deepWyvstoneSlabDouble), deepWyvstoneSlab.getRegistryName()));
        registry.register(setup(new ItemSlab(mossyCobbledDeepWyvstoneSlab, mossyCobbledDeepWyvstoneSlab, mossyCobbledDeepWyvstoneSlabDouble), mossyCobbledDeepWyvstoneSlab.getRegistryName()));
        registry.register(setup(new ItemSlab(mossyCobbledWyvstoneSlab, mossyCobbledWyvstoneSlab, mossyCobbledWyvstoneSlabDouble), mossyCobbledWyvstoneSlab.getRegistryName()));
        registry.register(setup(new ItemSlab(wyvstoneSlab, wyvstoneSlab, wyvstoneSlabDouble), wyvstoneSlab.getRegistryName()));
        registry.register(setup(new ItemSlab(wyvwoodSlab, wyvwoodSlab, wyvwoodSlabDouble), wyvwoodSlab.getRegistryName()));
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        for (Item item : ForgeRegistries.ITEMS.getValues()) {
            if (item.getRegistryName().getNamespace().equals(MoCConstants.MOD_ID)) {
                ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "normal"));
            }
        }

        if (Item.getItemFromBlock(tallWyvgrass) != null) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(tallWyvgrass), 0, new ModelResourceLocation(tallWyvgrass.getRegistryName(), "inventory")
            );
        }

        // All doors, fence gates, slabs, and walls go here
        ModelLoader.setCustomStateMapper(wyvwoodDoor, (new StateMap.Builder()).ignore(MoCBlockDoorWood.POWERED).build());

        ModelLoader.setCustomStateMapper(wyvwoodFenceGate, (new StateMap.Builder()).ignore(MoCBlockFenceGateWood.POWERED).build());

        ModelLoader.setCustomStateMapper(cobbledWyvstoneWall, (new StateMap.Builder()).ignore(MoCBlockWall.VARIANT).build());
        ModelLoader.setCustomStateMapper(cobbledDeepWyvstoneWall, (new StateMap.Builder()).ignore(MoCBlockWall.VARIANT).build());
        ModelLoader.setCustomStateMapper(deepWyvstoneWall, (new StateMap.Builder()).ignore(MoCBlockWall.VARIANT).build());
        ModelLoader.setCustomStateMapper(mossyCobbledWyvstoneWall, (new StateMap.Builder()).ignore(MoCBlockWall.VARIANT).build());
        ModelLoader.setCustomStateMapper(mossyCobbledDeepWyvstoneWall, (new StateMap.Builder()).ignore(MoCBlockWall.VARIANT).build());
        ModelLoader.setCustomStateMapper(silverSandstoneWall, (new StateMap.Builder()).ignore(MoCBlockWall.VARIANT).build());
        ModelLoader.setCustomStateMapper(wyvstoneWall, (new StateMap.Builder()).ignore(MoCBlockWall.VARIANT).build());
    }

    @Nonnull
    public static <T extends IForgeRegistryEntry<T>> T setup(T entry, String name) {
        return setup(entry, new ResourceLocation(MoCConstants.MOD_ID, name));
    }

    @Nonnull
    public static <T extends IForgeRegistryEntry<T>> T setup(T entry, ResourceLocation registryName) {
        Preconditions.checkNotNull(entry, "Entry to setup must not be null!");
        Preconditions.checkNotNull(registryName, "Registry name to assign must not be null!");
        entry.setRegistryName(registryName);
        if (entry instanceof Block) {
            ((Block) entry).setTranslationKey(registryName.getNamespace() + "." + registryName.getPath()).setCreativeTab(MoCreatures.tabMoC);
        }
        if (entry instanceof Item) {
            ((Item) entry).setTranslationKey(registryName.getNamespace() + "." + registryName.getPath()).setCreativeTab(MoCreatures.tabMoC);
        }
        return entry;
    }
}
