/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.model.MoCModelMediumFish;
import drzhark.mocreatures.entity.aquatic.MoCEntityCod;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderCod extends MoCRenderMoC<MoCEntityCod> {
    private static final ResourceLocation TEXTURE = MoCreatures.proxy.lowResolutionTextures
            ? new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/medium_fish/16x/medium_fish_cod.png")
            : new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/medium_fish/medium_fish_cod.png");

    public MoCRenderCod() {
        super(new MoCModelMediumFish(), 0.2F);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityCod entity) {
        return TEXTURE;
    }
}
