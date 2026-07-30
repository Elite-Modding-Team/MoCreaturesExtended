/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.client.model.MoCModelRay;
import drzhark.mocreatures.entity.aquatic.MoCEntityMantaRay;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderMantaRay extends MoCRenderMoC<MoCEntityMantaRay> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/ray/ray_manta.png");

    public MoCRenderMantaRay() {
        super(new MoCModelRay(), 0.4F);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityMantaRay entity) {
        return TEXTURE;
    }
}
