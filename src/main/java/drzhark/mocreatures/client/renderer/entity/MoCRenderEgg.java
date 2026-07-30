/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.proxy.MoCProxyClient;
import drzhark.mocreatures.entity.item.MoCEntityEgg;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderEgg extends RenderLiving<MoCEntityEgg> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MoCConstants.MOD_ID, "textures/models/egg.png");

    public MoCRenderEgg(ModelBase model, float f) {
        super(MoCProxyClient.mc.getRenderManager(), model, f);
    }

    @Override
    protected void preRenderCallback(MoCEntityEgg entity, float f) {
        stretch(entity);
        super.preRenderCallback(entity, f);

    }

    protected void stretch(MoCEntityEgg entity) {
        float f = entity.getSize() * 0.01F;
        GlStateManager.scale(f, f, f);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityEgg entity) {
        return TEXTURE;
    }
}
