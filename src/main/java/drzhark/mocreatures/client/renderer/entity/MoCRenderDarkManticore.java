/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.client.model.MoCModelManticore;
import drzhark.mocreatures.client.model.MoCModelRay;
import drzhark.mocreatures.entity.aquatic.MoCEntityStingRay;
import drzhark.mocreatures.entity.hostile.MoCEntityDarkManticore;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderDarkManticore extends MoCRenderMoC<MoCEntityDarkManticore> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/manticore/manticore_dark.png");

    public MoCRenderDarkManticore() {
        super(new MoCModelManticore(), 0.7F);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityDarkManticore entity) {
        return TEXTURE;
    }
}
