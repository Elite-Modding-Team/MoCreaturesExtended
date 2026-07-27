/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.hostile.MoCEntityHellRat;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MoCRenderHellRat extends MoCRenderRat<MoCEntityHellRat> {

    public MoCRenderHellRat(ModelBase modelbase, float f) {
        super(modelbase, f);
    }

    @Override
    protected void stretch(MoCEntityHellRat entityhellrat) {
        float f = 1.3F;
        GlStateManager.scale(f, f, f);
    }

    @Override
    protected void renderModel(MoCEntityHellRat entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        boolean isAnimated = entity.getType() == 4 && MoCreatures.proxy.getAnimateTextures();
        int frameCount = 2;
        int ticksPerFrame = 3;

        if (isAnimated) {
            GlStateManager.matrixMode(GL11.GL_TEXTURE);
            GlStateManager.pushMatrix();
            GlStateManager.loadIdentity();
            int currentFrame = ((int) ageInTicks / ticksPerFrame) % frameCount;
            GlStateManager.translate(0.0F, (float) currentFrame / frameCount, 0.0F);
            GlStateManager.scale(1.0F, 1.0F / frameCount, 1.0F);
            GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        }

        super.renderModel(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);

        if (isAnimated) {
            GlStateManager.matrixMode(GL11.GL_TEXTURE);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        }
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityHellRat entityhellrat) {
        return entityhellrat.getTexture();
    }
}
