/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.proxy.MoCProxyClient;
import drzhark.mocreatures.client.model.MoCModelHorseMob;
import drzhark.mocreatures.entity.hostile.MoCEntityHorseMob;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MoCRenderHorseMob extends RenderLiving<MoCEntityHorseMob> {

    public MoCRenderHorseMob(MoCModelHorseMob modelbase) {
        super(MoCProxyClient.mc.getRenderManager(), modelbase, 0.5F);

    }

    protected void adjustHeight(MoCEntityHorseMob entityhorsemob, float FHeight) {
        GlStateManager.translate(0.0F, FHeight, 0.0F);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityHorseMob entityhorsemob) {
        return entityhorsemob.getTexture();
    }

    @Override
    protected void renderModel(MoCEntityHorseMob entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        boolean isAnimated = MoCreatures.proxy.getAnimateTextures();
        int frameCount = 1;
        int ticksPerFrame = 2;

        if (isAnimated) {
            if (entity.getType() == 23) {
                frameCount = 7;
                ticksPerFrame = 4;
            } else if (entity.getType() == 38) {
                frameCount = 5;
            }
        }

        if (isAnimated && frameCount > 1) {
            GlStateManager.matrixMode(GL11.GL_TEXTURE);
            GlStateManager.pushMatrix();
            GlStateManager.loadIdentity();
            int currentFrame = ((int) ageInTicks / ticksPerFrame) % frameCount;
            GlStateManager.translate(0.0F, (float) currentFrame / frameCount, 0.0F);
            GlStateManager.scale(1.0F, 1.0F / frameCount, 1.0F);
            GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        }

        super.renderModel(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);

        if (isAnimated && frameCount > 1) {
            GlStateManager.matrixMode(GL11.GL_TEXTURE);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        }
    }
}
