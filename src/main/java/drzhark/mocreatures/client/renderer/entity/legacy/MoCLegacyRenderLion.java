/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity.legacy;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.model.legacy.MoCLegacyModelBigCat1;
import drzhark.mocreatures.client.model.legacy.MoCLegacyModelBigCat2;
import drzhark.mocreatures.entity.hunter.MoCEntityBigCat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCLegacyRenderLion extends MoCLegacyRenderBigCat {
    public MoCLegacyRenderLion() {
        super(new MoCLegacyModelBigCat2(), new MoCLegacyModelBigCat1(), 0.5F);
    }

    private ResourceLocation getLionTexture(boolean lowRes, String texturePath) {
        String fullPath = "textures/entity/big_cat/legacy/" + texturePath;
        return new ResourceLocation(MoCConstants.MOD_ID, fullPath);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityBigCat entity) {
        boolean lowRes = MoCreatures.proxy.lowResolutionTextures;
        String tempTexture;

        switch (entity.getType()) {
            case 6:
            case 7:
            case 8:
                tempTexture = "big_cat_white_lion_legacy.png";
                break;
            default:
                tempTexture = "big_cat_lion_legacy.png";
                break;
        }

        return getLionTexture(lowRes, tempTexture);
    }
}
