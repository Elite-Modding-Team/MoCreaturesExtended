/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.client.model.MoCModelManticore;
import drzhark.mocreatures.entity.hostile.MoCEntityFrostManticore;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderFrostManticore extends MoCRenderMoC<MoCEntityFrostManticore> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/manticore/manticore_frost.png");

    public MoCRenderFrostManticore() {
        super(new MoCModelManticore(), 0.7F);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityFrostManticore entity) {
        return TEXTURE;
    }
}
