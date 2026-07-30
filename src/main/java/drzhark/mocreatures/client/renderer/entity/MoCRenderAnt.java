/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.client.model.MoCModelAnt;
import drzhark.mocreatures.entity.ambient.MoCEntityAnt;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderAnt extends MoCRenderMoC<MoCEntityAnt> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/ant/ant.png");

    public MoCRenderAnt() {
        super(new MoCModelAnt(), 0.0F);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityAnt entity) {
        return TEXTURE;
    }
}
