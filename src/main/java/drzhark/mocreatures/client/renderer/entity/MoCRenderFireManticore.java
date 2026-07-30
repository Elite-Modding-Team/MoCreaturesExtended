/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.client.model.MoCModelManticore;
import drzhark.mocreatures.entity.hostile.MoCEntityFireManticore;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderFireManticore extends MoCRenderMoC<MoCEntityFireManticore> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/manticore/manticore_fire.png");

    public MoCRenderFireManticore() {
        super(new MoCModelManticore(), 0.7F);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityFireManticore entity) {
        return TEXTURE;
    }
}
