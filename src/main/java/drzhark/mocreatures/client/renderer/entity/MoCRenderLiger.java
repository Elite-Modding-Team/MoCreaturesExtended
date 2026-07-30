/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.client.model.MoCModelBigCat;
import drzhark.mocreatures.entity.hunter.MoCEntityLiger;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderLiger extends MoCRenderMoC<MoCEntityLiger> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/big_cat/big_cat_liard.png");

    public MoCRenderLiger() {
        super(new MoCModelBigCat(), 0.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityLiger entity) {
        return TEXTURE;
    }
}
