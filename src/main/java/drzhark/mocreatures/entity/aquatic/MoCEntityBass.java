/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.aquatic;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.init.MoCLootTables;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoCEntityBass extends MoCEntityMediumFish {

    public MoCEntityBass(World world) {
        super(world);
        this.setType(3);
    }

    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.proxy.lowResolutionTextures ? MoCreatures.proxy.getModelTexture("16x/mediumfish_bass.png") : MoCreatures.proxy.getModelTexture("mediumfish_bass.png");
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return MoCLootTables.BASS;
    }
}
