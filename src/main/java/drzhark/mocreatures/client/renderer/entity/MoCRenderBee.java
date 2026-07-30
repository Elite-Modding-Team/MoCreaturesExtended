/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.client.model.MoCModelBee;
import drzhark.mocreatures.entity.ambient.MoCEntityBee;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderBee extends MoCRenderInsect<MoCEntityBee> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/bee/bee.png");

    public MoCRenderBee() {
        super(new MoCModelBee());
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityBee entity) {
        return TEXTURE;
    }
}
