/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.client.model.MoCModelBigCat;
import drzhark.mocreatures.entity.hunter.MoCEntityLiard;
import drzhark.mocreatures.entity.hunter.MoCEntityLither;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderLither extends MoCRenderMoC<MoCEntityLither> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/big_cat/big_cat_lither.png");

    public MoCRenderLither() {
        super(new MoCModelBigCat(), 0.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityLither entity) {
        return TEXTURE;
    }
}
