/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.aquatic;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.init.MoCLootTables;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoCEntityCod extends MoCEntityMediumFish {

    public MoCEntityCod(World world) {
        super(world);
        this.setType(2);
    }

    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.proxy.lowResolutionTextures ? MoCreatures.proxy.getModelTexture("16x/mediumfish_cod.png") : MoCreatures.proxy.getModelTexture("mediumfish_cod.png");
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return MoCLootTables.COD;
    }
}
