/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.model.MoCModelHorse;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MoCRenderHorse extends MoCRenderMoC<MoCEntityHorse> {

    public MoCRenderHorse(MoCModelHorse modelbase) {
        super(modelbase, 0.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityHorse entityhorse) {
        return entityhorse.getTexture();
    }

    protected void adjustHeight(MoCEntityHorse entityhorse, float FHeight) {
        GlStateManager.translate(0.0F, FHeight, 0.0F);
    }

    @Override
    protected void preRenderCallback(MoCEntityHorse entityhorse, float f) {
        if (!entityhorse.getIsAdult() || entityhorse.getType() > 64) {
            stretch(entityhorse);
        }
        if (entityhorse.getIsGhost()) {
            adjustHeight(entityhorse, -0.3F + (entityhorse.tFloat() / 5F));
        }
        super.preRenderCallback(entityhorse, f);
    }

    protected void stretch(MoCEntityHorse entityhorse) {
        float sizeFactor = entityhorse.getAge() * 0.01F;
        if (entityhorse.getIsAdult()) {
            sizeFactor = 1.0F;
        }
        if (entityhorse.getType() > 64) { // donkey
            sizeFactor *= 0.9F;
        }
        GlStateManager.scale(sizeFactor, sizeFactor, sizeFactor);
    }

    @Override
    protected void renderModel(MoCEntityHorse entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        boolean isAnimated = MoCreatures.proxy.getAnimateTextures();
        int frameCount = 1;
        int ticksPerFrame = 2;

        if (isAnimated) {
            if (entity.isNightmare()) {
                frameCount = 5;
            } else if (entity.isUndead() && entity.getType() < 26) {
                if (entity.getType() == 24) {
                    frameCount = 6; // Undead Unicorn has 6 frames
                    ticksPerFrame = 4;
                } else {
                    frameCount = 7; // Other undead horses have 7 frames
                    ticksPerFrame = 4;
                }
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
