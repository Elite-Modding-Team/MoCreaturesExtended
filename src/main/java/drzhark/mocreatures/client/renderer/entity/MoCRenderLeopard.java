/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.model.MoCModelBigCat;
import drzhark.mocreatures.entity.hunter.MoCEntityLeopard;
import drzhark.mocreatures.entity.hunter.MoCEntityLion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderLeopard extends MoCRenderMoC<MoCEntityLeopard> {
    public MoCRenderLeopard() {
        super(new MoCModelBigCat(), 0.5F);
    }

    private ResourceLocation getLeopardTexture(boolean lowRes, String texturePath) {
        String fullPath = "textures/entity/big_cat/" + texturePath;
        return new ResourceLocation(MoCConstants.MOD_ID, fullPath);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityLeopard entity) {
        boolean lowRes = MoCreatures.proxy.lowResolutionTextures;
        String tempTexture;

        switch (entity.getType()) {
            case 2:
            case 3:
                tempTexture = "big_cat_snow_leopard.png";
                break;
            default:
                tempTexture = "big_cat_leopard.png";
                break;
        }

        return getLeopardTexture(lowRes, tempTexture);
    }
}
