/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.model.MoCModelBigCat;
import drzhark.mocreatures.entity.hunter.MoCEntityLion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderLion extends MoCRenderMoC<MoCEntityLion> {
    public MoCRenderLion() {
        super(new MoCModelBigCat(), 0.5F);
    }

    private ResourceLocation getLionTexture(boolean lowRes, String texturePath) {
        String fullPath = "textures/entity/big_cat/" + texturePath;
        return new ResourceLocation(MoCConstants.MOD_ID, fullPath);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityLion entity) {
        boolean lowRes = MoCreatures.proxy.lowResolutionTextures;
        String tempTexture;

        switch (entity.getType()) {
            case 2:
            case 3:
                tempTexture = "big_cat_lion_male.png";
                break;
            case 6:
            case 7:
            case 8:
                tempTexture = "big_cat_white_lion.png";
                break;
            default:
                tempTexture = "big_cat_lion_female.png";
                break;
        }

        return getLionTexture(lowRes, tempTexture);
    }
}
