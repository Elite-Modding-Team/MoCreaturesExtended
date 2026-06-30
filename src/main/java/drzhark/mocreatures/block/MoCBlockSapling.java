/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.block;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.dimension.worldgen.MoCWorldGenBigTree;
import drzhark.mocreatures.init.MoCBlocks;
import net.minecraft.block.*;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.TerrainGen;

import java.util.Random;

@SuppressWarnings("deprecation")
public class MoCBlockSapling extends BlockBush implements IGrowable {
    public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 1);
    protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.1D, 0.0D, 0.1D, 0.9D, 0.8D, 0.9D);
    public boolean flammable;
    private EnumWoodType woodType;

    public MoCBlockSapling(EnumWoodType woodType, MapColor mapColor, boolean flammable) {
        super(Material.PLANTS, mapColor);
        this.flammable = flammable;
        this.setCreativeTab(MoCreatures.tabMoC);
        this.setSoundType(SoundType.PLANT);
        this.setDefaultState(this.blockState.getBaseState().withProperty(STAGE, 0));
        this.woodType = woodType;
    }

    public MoCBlockSapling(Material material, MapColor mapColor) {
        super(material, mapColor);
    }

    public boolean isFlammable() {
        return flammable;
    }

    @Override
    public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
        if (isFlammable()) {
            return Blocks.SAPLING.getFlammability(world, pos, face);
        } else {
            return 0;
        }
    }

    @Override
    public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {
        if (isFlammable()) {
            return Blocks.SAPLING.getFireSpreadSpeed(world, pos, face);
        } else {
            return 0;
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return AABB;
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        if (!world.isRemote) {
            super.updateTick(world, pos, state, rand);

            if (!world.isAreaLoaded(pos, 1)) return;
            if (world.getLightFromNeighbors(pos.up()) >= 9 && rand.nextInt(7) == 0) {
                this.grow(world, pos, state, rand);
            }
        }
    }

    public void grow(World world, BlockPos pos, IBlockState state, Random rand) {
        if (state.getValue(STAGE) == 0) {
            world.setBlockState(pos, state.cycleProperty(STAGE), 4);
        } else {
            this.generateTree(world, pos, state, rand);
        }
    }

    public void generateTree(World world, BlockPos pos, IBlockState state, Random rand) {
        if (!TerrainGen.saplingGrowTree(world, rand, pos)) return;
        WorldGenerator generator = null;
        int i = 0;
        int j = 0;
        boolean flag = false;

        switch (this.woodType) {
            case WYVWOOD:
                check:
                for (i = 0; i >= -1; --i) {
                    for (j = 0; j >= -1; --j) {
                        if (this.isTwoByTwoOfType(world, pos, i, j, EnumWoodType.WYVWOOD)) {
                            generator = new MoCWorldGenBigTree(false, MoCBlocks.wyvwoodLog.getDefaultState(), MoCBlocks.wyvwoodLeaves.getStateFromMeta(0), 2, 30, 10);
                            flag = true;
                            break check;
                        }
                    }
                }

                if (!flag) {
                    return;
                }
                break;

            default:
                break;
        }

        IBlockState air = Blocks.AIR.getDefaultState();

        if (flag) {
            world.setBlockState(pos.add(i, 0, j), air, 3);
            world.setBlockState(pos.add(i + 1, 0, j), air, 3);
            world.setBlockState(pos.add(i, 0, j + 1), air, 3);
            world.setBlockState(pos.add(i + 1, 0, j + 1), air, 3);
        } else {
            world.setBlockState(pos, air, 3);
        }

        if (generator == null || !generator.generate(world, rand, pos.add(i, 0, j))) {
            if (flag) {
                world.setBlockState(pos.add(i, 0, j), state, 3);
                world.setBlockState(pos.add(i + 1, 0, j), state, 3);
                world.setBlockState(pos.add(i, 0, j + 1), state, 3);
                world.setBlockState(pos.add(i + 1, 0, j + 1), state, 3);
            } else {
                world.setBlockState(pos, state, 3);
            }
        }
    }

    // Trees that utilize 2x2 Saplings
    private boolean isTwoByTwoOfType(World world, BlockPos pos, int xOffset, int zOffset, EnumWoodType type) {
        return this.isTypeAt(world, pos.add(xOffset, 0, zOffset), type) && this.isTypeAt(world, pos.add(xOffset + 1, 0, zOffset), type) && this.isTypeAt(world, pos.add(xOffset, 0, zOffset + 1), type) && this.isTypeAt(world, pos.add(xOffset + 1, 0, zOffset + 1), type);
    }

    public EnumWoodType getWoodType() {
        return woodType;
    }

    public boolean isTypeAt(final World world, final BlockPos pos, final EnumWoodType type) {
        final IBlockState state = world.getBlockState(pos);

        if (state.getBlock() instanceof MoCBlockSapling) {
            return ((MoCBlockSapling) state.getBlock()).getWoodType() == type;
        } else {
            return false;
        }
    }

    public boolean canGrow(World world, BlockPos pos, IBlockState state, boolean isClient) {
        return true;
    }

    public boolean canUseBonemeal(World world, Random rand, BlockPos pos, IBlockState state) {
        return world.rand.nextFloat() < 0.45D;
    }

    public void grow(World world, Random rand, BlockPos pos, IBlockState state) {
        this.grow(world, pos, state, rand);
    }

    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(STAGE, meta);
    }

    public int getMetaFromState(IBlockState state) {
        return state.getValue(STAGE);
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, STAGE);
    }

    @Override
    public boolean canBlockStay(World world, BlockPos pos, IBlockState state) {
        Block soil = world.getBlockState(pos.down()).getBlock();
        return soil instanceof MoCBlockGrass || soil instanceof MoCBlockDirt || soil instanceof BlockGrass || soil instanceof BlockDirt || soil instanceof BlockFarmland;
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        IBlockState soil = world.getBlockState(pos.down());
        Block tempblock = soil.getBlock();
        if (tempblock instanceof MoCBlockDirt || tempblock instanceof MoCBlockGrass) {
            return true;
        }
        return super.canPlaceBlockAt(world, pos) && soil.getBlock().canSustainPlant(soil, world, pos.down(), EnumFacing.UP, this);
    }

    public enum EnumWoodType implements IStringSerializable {
        WYVWOOD(0, "wyvwood");

        private final String name;
        private final int meta;

        EnumWoodType(int meta, String name) {
            this.meta = meta;
            this.name = name;
        }

        public static EnumWoodType byMeta(int meta) {
            for (EnumWoodType woodType : EnumWoodType.values()) {
                if (woodType.meta == meta) {
                    return woodType;
                }
            }
            return EnumWoodType.WYVWOOD;
        }

        public int getMetadata() {
            return this.meta;
        }

        public String toString() {
            return this.name;
        }

        public String getName() {
            return this.name;
        }
    }
}
