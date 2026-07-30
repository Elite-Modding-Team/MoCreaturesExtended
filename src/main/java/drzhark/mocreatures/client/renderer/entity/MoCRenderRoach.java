/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.client.model.MoCModelRoach;
import drzhark.mocreatures.entity.ambient.MoCEntityRoach;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderRoach extends MoCRenderInsect<MoCEntityRoach> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/roach/roach.png");

    public MoCRenderRoach() {
        super(new MoCModelRoach());
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityRoach entity) {
        return TEXTURE;
    }
}
