/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.client.model.MoCModelBigCat;
import drzhark.mocreatures.entity.hunter.MoCEntityLeoger;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderLeoger extends MoCRenderMoC<MoCEntityLeoger> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/big_cat/big_cat_leoger.png");

    public MoCRenderLeoger() {
        super(new MoCModelBigCat(), 0.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityLeoger entity) {
        return TEXTURE;
    }
}
