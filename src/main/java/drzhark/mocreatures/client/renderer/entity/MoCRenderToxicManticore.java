/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.client.model.MoCModelManticore;
import drzhark.mocreatures.entity.hostile.MoCEntityToxicManticore;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderToxicManticore extends MoCRenderMoC<MoCEntityToxicManticore> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/manticore/manticore_toxic.png");

    public MoCRenderToxicManticore() {
        super(new MoCModelManticore(), 0.7F);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityToxicManticore entity) {
        return TEXTURE;
    }
}
