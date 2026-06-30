/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.dimension.worldgen;

import com.google.common.collect.Lists;
import drzhark.mocreatures.init.MoCBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

// Based off the large vanilla oak trees
public class MoCWorldGenBigTree extends WorldGenAbstractTree {
    private Random rand;
    private World world;
    private BlockPos basePos = BlockPos.ORIGIN;

    int heightLimit = 20;
    int height;
    double heightAttenuation = 0.618D;
    double branchSlope = 0.381D;
    double scaleWidth = 1.0D;
    double leafDensity = 1.0D;
    int trunkSize = 1;
    int heightLimitLimit = 12;
    int leafDistanceLimit = 4;

    private List<FoliageCoordinates> foliageCoords;
    private IBlockState iBlockStateLog;
    private IBlockState iBlockStateLeaf;

    public MoCWorldGenBigTree(boolean notify) {
        super(notify);
    }

    public MoCWorldGenBigTree(boolean notify, IBlockState logState, IBlockState leafState, int trunkSize, int heightLimit, int leafDist) {
        super(notify);
        this.iBlockStateLog = logState;
        this.iBlockStateLeaf = leafState;
        this.trunkSize = trunkSize;
        this.heightLimitLimit = heightLimit;
        this.leafDistanceLimit = leafDist;
    }

    void generateLeafNodeList() {
        this.height = (int) ((double) this.heightLimit * this.heightAttenuation);

        if (this.height >= this.heightLimit) {
            this.height = this.heightLimit - 1;
        }

        int i = (int) (1.382D + Math.pow(this.leafDensity * (double) this.heightLimit / 13.0D, 2.0D));
        if (i < 1) i = 1;

        int j = this.basePos.getY() + this.height;
        int k = this.heightLimit - this.leafDistanceLimit;
        this.foliageCoords = Lists.newArrayList();
        this.foliageCoords.add(new FoliageCoordinates(this.basePos.up(k), j));

        for (; k >= 0; --k) {
            float f = this.layerSize(k);
            if (f >= 0.0F) {
                for (int l = 0; l < i; ++l) {
                    double d0 = this.scaleWidth * (double) f * ((double) this.rand.nextFloat() + 0.328D);
                    double d1 = (double) (this.rand.nextFloat() * 2.0F) * Math.PI;
                    double d2 = d0 * Math.sin(d1) + 0.5D;
                    double d3 = d0 * Math.cos(d1) + 0.5D;
                    BlockPos blockpos = this.basePos.add(d2, (double) (k - 1), d3);
                    BlockPos blockpos1 = blockpos.up(this.leafDistanceLimit);

                    if (this.checkBlockLine(blockpos, blockpos1) == -1) {
                        int i1 = this.basePos.getX() - blockpos.getX();
                        int j1 = this.basePos.getZ() - blockpos.getZ();
                        double d4 = (double) blockpos.getY() - Math.sqrt((double) (i1 * i1 + j1 * j1)) * this.branchSlope;
                        int k1 = d4 > (double) j ? j : (int) d4;
                        BlockPos blockpos2 = new BlockPos(this.basePos.getX(), k1, this.basePos.getZ());

                        if (this.checkBlockLine(blockpos2, blockpos) == -1) {
                            this.foliageCoords.add(new FoliageCoordinates(blockpos, blockpos2.getY()));
                        }
                    }
                }
            }
        }
    }

    void crossSection(BlockPos pos, float size) {
        int i = (int) ((double) size + 0.618D);

        for (int j = -i; j <= i; ++j) {
            for (int k = -i; k <= i; ++k) {
                if (Math.pow((double) Math.abs(j) + 0.5D, 2.0D) + Math.pow((double) Math.abs(k) + 0.5D, 2.0D) <= (double) (size * size)) {
                    BlockPos blockpos = pos.add(j, 0, k);

                    if (this.world.isBlockLoaded(blockpos)) {
                        IBlockState state = this.world.getBlockState(blockpos);

                        if (state.getBlock().isAir(state, world, blockpos) || state.getBlock().isLeaves(state, world, blockpos)) {
                            this.world.setBlockState(blockpos, this.iBlockStateLeaf, 2);
                        }
                    }
                }
            }
        }
    }

    float layerSize(int y) {
        if ((float) y < (float) this.heightLimit * 0.3F) {
            return -1.0F;
        } else {
            float f = (float) this.heightLimit / 2.0F;
            float f1 = f - (float) y;
            float f2 = MathHelper.sqrt(f * f - f1 * f1);

            if (f1 == 0.0F) {
                f2 = f;
            } else if (Math.abs(f1) >= f) {
                return 0.0F;
            }

            return f2 * 0.5F;
        }
    }

    float leafSize(int y) {
        if (y >= 0 && y < this.leafDistanceLimit) {
            return y != 0 && y != this.leafDistanceLimit - 1 ? 3.0F : 2.0F;
        } else {
            return -1.0F;
        }
    }

    void generateLeafNode(BlockPos pos) {
        int startY = pos.getY();
        int endY = startY + this.leafDistanceLimit;

        for (int currentY = startY; currentY < endY; ++currentY) {
            float size = this.leafSize(currentY - startY);
            this.crossSection(new BlockPos(pos.getX(), currentY, pos.getZ()), size);

            if (currentY < (endY - 1)) {
                BlockPos logPos = new BlockPos(pos.getX(), currentY, pos.getZ());
                this.world.setBlockState(logPos, this.iBlockStateLog, 2);
            }
        }
    }

    void limb(BlockPos posOne, BlockPos posTwo) {
        BlockPos difference = posTwo.add(-posOne.getX(), -posOne.getY(), -posOne.getZ());
        int steps = this.getGreatestDistance(difference);
        float f = (float) difference.getX() / (float) steps;
        float f1 = (float) difference.getY() / (float) steps;
        float f2 = (float) difference.getZ() / (float) steps;

        for (int j = 0; j <= steps; ++j) {
            BlockPos currentPos = posOne.add(0.5F + (float) j * f, 0.5F + (float) j * f1, 0.5F + (float) j * f2);

            if (this.world.isBlockLoaded(currentPos)) {
                BlockLog.EnumAxis axis = this.getLogAxis(posOne, currentPos);

                IBlockState stateWithAxis = this.iBlockStateLog;
                if (stateWithAxis.getPropertyKeys().contains(BlockLog.LOG_AXIS)) {
                    stateWithAxis = stateWithAxis.withProperty(BlockLog.LOG_AXIS, axis);
                }
                this.world.setBlockState(currentPos, stateWithAxis, 2);
            }
        }
    }

    private int getGreatestDistance(BlockPos pos) {
        int i = MathHelper.abs(pos.getX());
        int j = MathHelper.abs(pos.getY());
        int k = MathHelper.abs(pos.getZ());
        return k > i && k > j ? k : (j > i ? j : i);
    }

    private BlockLog.EnumAxis getLogAxis(BlockPos start, BlockPos end) {
        BlockLog.EnumAxis axis = BlockLog.EnumAxis.Y;
        int diffX = Math.abs(end.getX() - start.getX());
        int diffZ = Math.abs(end.getZ() - start.getZ());
        int maxDiff = Math.max(diffX, diffZ);

        if (maxDiff > 0) {
            if (diffX == maxDiff) {
                axis = BlockLog.EnumAxis.X;
            } else if (diffZ == maxDiff) {
                axis = BlockLog.EnumAxis.Z;
            }
        }
        return axis;
    }

    void generateLeaves() {
        for (FoliageCoordinates coords : this.foliageCoords) {
            this.generateLeafNode(coords);
        }
    }

    boolean leafNodeNeedsBase(int yOffset) {
        return (double) yOffset >= (double) this.heightLimit * 0.2D;
    }

    void generateTrunk() {
        BlockPos topTrunk = this.basePos.up(this.height);
        this.limb(this.basePos, topTrunk);

        if (this.trunkSize == 2) {
            this.limb(this.basePos.east(), topTrunk.east());
            this.limb(this.basePos.east().south(), topTrunk.east().south());
            this.limb(this.basePos.south(), topTrunk.south());
        }
    }

    void generateLeafNodeBases() {
        for (FoliageCoordinates coords : this.foliageCoords) {
            int branchBaseY = coords.getBranchBase();
            BlockPos basePosAtBranch = new BlockPos(this.basePos.getX(), branchBaseY, this.basePos.getZ());

            if (!basePosAtBranch.equals(coords) && this.leafNodeNeedsBase(branchBaseY - this.basePos.getY())) {
                this.limb(basePosAtBranch, coords);
            }
        }
    }

    int checkBlockLine(BlockPos posOne, BlockPos posTwo) {
        BlockPos difference = posTwo.add(-posOne.getX(), -posOne.getY(), -posOne.getZ());
        int maxDist = this.getGreatestDistance(difference);
        float f = (float) difference.getX() / (float) maxDist;
        float f1 = (float) difference.getY() / (float) maxDist;
        float f2 = (float) difference.getZ() / (float) maxDist;

        if (maxDist == 0) {
            return -1;
        } else {
            for (int step = 0; step <= maxDist; ++step) {
                BlockPos checkingPos = posOne.add(0.5F + (float) step * f, 0.5F + (float) step * f1, 0.5F + (float) step * f2);
                if (!this.isReplaceable(world, checkingPos)) {
                    return step;
                }
            }
            return -1;
        }
    }

    private boolean validTreeLocation() {
        BlockPos down = this.basePos.down();
        IBlockState state = this.world.getBlockState(down);

        Block block = state.getBlock();
        boolean isSoil = (block == MoCBlocks.wyvdirt || block == MoCBlocks.wyvgrass || block == Blocks.DIRT || block == Blocks.GRASS);

        if (!isSoil) {
            return false;
        } else {
            int clearance = this.checkBlockLine(this.basePos, this.basePos.up(this.heightLimit - 1));
            if (clearance == -1) {
                return true;
            } else if (clearance < 6) {
                return false;
            } else {
                this.heightLimit = clearance;
                return true;
            }
        }
    }

    @Override
    public boolean generate(@Nonnull World worldIn, Random rand, @Nonnull BlockPos position) {
        this.world = worldIn;
        this.basePos = position;
        this.rand = new Random(rand.nextLong());

        if (this.heightLimit == 0) {
            this.heightLimit = 5 + this.rand.nextInt(this.heightLimitLimit);
        }

        if (!this.validTreeLocation()) {
            this.world = null;
            return false;
        } else {
            this.generateLeafNodeList();
            this.generateLeaves();
            this.generateTrunk();
            this.generateLeafNodeBases();
            this.world = null;
            return true;
        }
    }

    static class FoliageCoordinates extends BlockPos {
        private final int branchBase;

        public FoliageCoordinates(BlockPos pos, int branchBaseY) {
            super(pos.getX(), pos.getY(), pos.getZ());
            this.branchBase = branchBaseY;
        }

        public int getBranchBase() {
            return this.branchBase;
        }
    }
}