/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.client.model.MoCModelRay;
import drzhark.mocreatures.entity.aquatic.MoCEntityStingRay;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderStingRay extends MoCRenderMoC<MoCEntityStingRay> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/ray/ray_sting.png");

    public MoCRenderStingRay() {
        super(new MoCModelRay(), 0.4F);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityStingRay entity) {
        return TEXTURE;
    }
}
