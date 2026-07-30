/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.model.MoCModelMediumFish;
import drzhark.mocreatures.entity.aquatic.MoCEntitySalmon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderSalmon extends MoCRenderMoC<MoCEntitySalmon> {
    private static final ResourceLocation TEXTURE = MoCreatures.proxy.lowResolutionTextures
            ? new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/medium_fish/16x/medium_fish_salmon.png")
            : new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/medium_fish/medium_fish_salmon.png");

    public MoCRenderSalmon() {
        super(new MoCModelMediumFish(), 0.2F);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntitySalmon entity) {
        return TEXTURE;
    }
}
