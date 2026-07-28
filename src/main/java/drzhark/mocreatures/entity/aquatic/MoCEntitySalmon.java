/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.aquatic;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.init.MoCLootTables;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoCEntitySalmon extends MoCEntityMediumFish {

    public MoCEntitySalmon(World world) {
        super(world);
        this.setType(1);
    }

    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.proxy.lowResolutionTextures ? MoCreatures.proxy.getModelTexture("16x/mediumfish_salmon.png") : MoCreatures.proxy.getModelTexture("mediumfish_salmon.png");
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return MoCLootTables.SALMON;
    }
}
