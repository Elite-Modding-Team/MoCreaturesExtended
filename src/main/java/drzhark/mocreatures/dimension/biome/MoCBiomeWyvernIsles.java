/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.dimension.biome;

import drzhark.mocreatures.dimension.worldgen.MoCWorldGenBigTree;
import drzhark.mocreatures.dimension.worldgen.MoCWorldGenWyvernGrass;
import drzhark.mocreatures.dimension.worldgen.MoCWorldGenWyvernNest;
import drzhark.mocreatures.entity.ambient.MoCEntityDragonfly;
import drzhark.mocreatures.entity.hunter.MoCEntitySnake;
import drzhark.mocreatures.entity.neutral.MoCEntityWyvern;
import drzhark.mocreatures.entity.passive.MoCEntityBunny;
import drzhark.mocreatures.init.MoCBlocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenShrub;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Random;

public class MoCBiomeWyvernIsles extends Biome {
    public MoCBiomeWyvernIsles(Biome.BiomeProperties biomeProperties) {
        super(biomeProperties);
        this.spawnableCreatureList.clear();
        this.spawnableMonsterList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        this.modSpawnableLists.clear();
        this.spawnableCreatureList.add(new SpawnListEntry(MoCEntityBunny.class, 6, 2, 4));
        this.spawnableCreatureList.add(new SpawnListEntry(MoCEntityDragonfly.class, 6, 2, 4));
        this.spawnableCreatureList.add(new SpawnListEntry(MoCEntitySnake.class, 6, 1, 3));
        this.spawnableCreatureList.add(new SpawnListEntry(MoCEntityWyvern.class, 12, 2, 3));
        this.topBlock = MoCBlocks.wyvgrass.getDefaultState();
        this.fillerBlock = MoCBlocks.wyvdirt.getDefaultState();
        this.decorator = new MoCBiomeWyvernIslesDecorator();
    }

    @Override
    public WorldGenAbstractTree getRandomTreeFeature(Random rand) {
        if (rand.nextInt(10) == 0) {
            return new MoCWorldGenBigTree(false, MoCBlocks.wyvwoodLog.getDefaultState(), MoCBlocks.wyvwoodLeaves.getDefaultState(), 2, 30, 10);
        } else {
            return new WorldGenShrub(MoCBlocks.wyvwoodLog.getDefaultState(), MoCBlocks.wyvwoodLeaves.getDefaultState());
        }
    }

    @Override
    public WorldGenerator getRandomWorldGenForGrass(@Nonnull Random rand) {
        return new MoCWorldGenWyvernGrass(MoCBlocks.tallWyvgrass.getDefaultState());
    }

    @Override
    public void decorate(@Nonnull World world, @Nonnull Random random, @Nonnull BlockPos pos) {
        super.decorate(world, random, pos);

        if (FMLLaunchHandler.isDeobfuscatedEnvironment() && random.nextInt(100) == 0) {
            int i = random.nextInt(16) + 8;
            int j = random.nextInt(16) + 8;
            BlockPos blockpos = world.getHeight(pos.add(i, 0, j));
            if (world.getBlockState(blockpos.down()).getMaterial() != Material.AIR) {
                (new MoCWorldGenWyvernNest()).generate(world, random, blockpos);
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getModdedBiomeFoliageColor(final int original) {
        return 0x4F4569;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getModdedBiomeGrassColor(final int original) {
        return 0x4F4569;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getSkyColorByTemp(final float currentTemperature) {
        return 0x8C95FF;
    }
}
