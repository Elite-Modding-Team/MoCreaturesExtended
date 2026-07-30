/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.model.MoCModelBigCat;
import drzhark.mocreatures.entity.hunter.MoCEntityTiger;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderTiger extends MoCRenderMoC<MoCEntityTiger> {
    public MoCRenderTiger() {
        super(new MoCModelBigCat(), 0.5F);
    }

    private ResourceLocation getTigerTexture(boolean lowRes, String texturePath) {
        String fullPath = "textures/entity/big_cat/" + texturePath;
        return new ResourceLocation(MoCConstants.MOD_ID, fullPath);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityTiger entity) {
        boolean lowRes = MoCreatures.proxy.lowResolutionTextures;
        String tempTexture;

        switch (entity.getType()) {
            case 2: // White Tiger
            case 3: // Winged White Tiger
                tempTexture = "big_cat_white_tiger.png";
                break;
            default: // Orange Tiger
                tempTexture = "big_cat_tiger.png";
                break;
        }

        return getTigerTexture(lowRes, tempTexture);
    }
}
