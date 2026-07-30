/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity.legacy;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.client.model.legacy.MoCLegacyModelBigCat1;
import drzhark.mocreatures.client.model.legacy.MoCLegacyModelBigCat2;
import drzhark.mocreatures.entity.hunter.MoCEntityBigCat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCLegacyRenderLiard extends MoCLegacyRenderBigCat {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/big_cat/legacy/big_cat_liard_legacy.png");

    public MoCLegacyRenderLiard() {
        super(new MoCLegacyModelBigCat2(), new MoCLegacyModelBigCat1(), 0.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityBigCat entity) {
        return TEXTURE;
    }
}
