/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.client.model.MoCModelBigCat;
import drzhark.mocreatures.entity.hunter.MoCEntityPanthger;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderPanthger extends MoCRenderMoC<MoCEntityPanthger> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/big_cat/big_cat_panthger.png");

    public MoCRenderPanthger() {
        super(new MoCModelBigCat(), 0.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityPanthger entity) {
        return TEXTURE;
    }
}
