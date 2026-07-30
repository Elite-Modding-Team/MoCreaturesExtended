/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.client.model.MoCModelFly;
import drzhark.mocreatures.entity.ambient.MoCEntityFly;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderFly extends MoCRenderInsect<MoCEntityFly> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/fly/fly.png");

    public MoCRenderFly() {
        super(new MoCModelFly());
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityFly entity) {
        return TEXTURE;
    }
}
