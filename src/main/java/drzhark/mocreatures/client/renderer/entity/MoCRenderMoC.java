/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.proxy.MoCProxyClient;
import drzhark.mocreatures.entity.IMoCEntity;
import drzhark.mocreatures.entity.MoCEntityAmbient;
import drzhark.mocreatures.entity.ambient.MoCEntityCrab;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderMoC<T extends EntityLiving> extends RenderLiving<T> {

    private float prevPitch;
    private float prevRoll;
    private float prevYaw;

    public MoCRenderMoC(ModelBase modelbase, float f) {
        super(MoCProxyClient.mc.getRenderManager(), modelbase, f);
    }

    @Override
    public void doRender(T entity, double d, double d1, double d2, float f, float f1) {
        doRenderMoC(entity, d, d1, d2, f, f1);
    }

    @Override
    protected float getDeathMaxRotation(EntityLiving entity) {
        if (entity instanceof MoCEntityAmbient || entity instanceof MoCEntityCrab) {
            return 180.0F;
        }

        return 90.0F;
    }

    public void doRenderMoC(T entity, double d, double d1, double d2, float f, float f1) {
        super.doRender(entity, d, d1, d2, f, f1);
        IMoCEntity entityMoC = (IMoCEntity) entity;
        boolean flag = MoCreatures.proxy.getDisplayPetName() && !(entityMoC.getPetName().isEmpty());
        boolean flag1 = MoCreatures.proxy.getDisplayPetHealth();
        if (entityMoC.shouldRenderNameAndHealth()) {
            float f2 = 1.6F;
            float f3 = 0.01666667F * f2;
            float f5 = ((Entity) entityMoC).getDistance(this.renderManager.renderViewEntity);
            if (f5 < 16F) {
                String s = "";
                s = s + entityMoC.getPetName();
                float f7 = 0.1F;
                FontRenderer fontrenderer = getFontRendererFromRenderManager();
                GlStateManager.pushMatrix();
                GlStateManager.translate((float) d + 0.0F, (float) d1 + f7, (float) d2);
                GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
                GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
                GlStateManager.scale(-f3, -f3, f3);
                GlStateManager.disableLighting();
                Tessellator tessellator1 = Tessellator.getInstance();
                int yOff = entityMoC.nameYOffset();
                if (flag1) {
                    GlStateManager.disableTexture2D();
                    if (!flag) {
                        yOff += 8;
                    }
                    tessellator1.getBuffer().begin(7, DefaultVertexFormats.POSITION_COLOR);
                    // might break SSP
                    float f8 = ((EntityLiving) entityMoC).getHealth();
                    float f9 = ((EntityLiving) entityMoC).getMaxHealth();
                    float f10 = f8 / f9;
                    float f11 = 40F * f10;
                    tessellator1.getBuffer().pos(-20F + f11, -10 + yOff, 0.0D).color(0.7F, 0.0F, 0.0F, 1.0F).endVertex();
                    tessellator1.getBuffer().pos(-20F + f11, -6 + yOff, 0.0D).color(0.7F, 0.0F, 0.0F, 1.0F).endVertex();
                    tessellator1.getBuffer().pos(20D, -6 + yOff, 0.0D).color(0.7F, 0.0F, 0.0F, 1.0F).endVertex();
                    tessellator1.getBuffer().pos(20D, -10 + yOff, 0.0D).color(0.7F, 0.0F, 0.0F, 1.0F).endVertex();
                    tessellator1.getBuffer().pos(-20D, -10 + yOff, 0.0D).color(0.0F, 0.7F, 0.0F, 1.0F).endVertex();
                    tessellator1.getBuffer().pos(-20D, -6 + yOff, 0.0D).color(0.0F, 0.7F, 0.0F, 1.0F).endVertex();
                    tessellator1.getBuffer().pos(f11 - 20F, -6 + yOff, 0.0D).color(0.0F, 0.7F, 0.0F, 1.0F).endVertex();
                    tessellator1.getBuffer().pos(f11 - 20F, -10 + yOff, 0.0D).color(0.0F, 0.7F, 0.0F, 1.0F).endVertex();
                    tessellator1.draw();
                    GlStateManager.enableTexture2D();
                }
                if (flag) {
                    GlStateManager.depthMask(false);
                    GlStateManager.disableDepth();
                    GlStateManager.enableBlend();
                    GlStateManager.blendFunc(770, 771);
                    GlStateManager.disableTexture2D();
                    tessellator1.getBuffer().begin(7, DefaultVertexFormats.POSITION_COLOR);
                    int i = fontrenderer.getStringWidth(s) / 2;
                    tessellator1.getBuffer().pos(-i - 1, -1 + yOff, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                    tessellator1.getBuffer().pos(-i - 1, 8 + yOff, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                    tessellator1.getBuffer().pos(i + 1, 8 + yOff, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                    tessellator1.getBuffer().pos(i + 1, -1 + yOff, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                    tessellator1.draw();
                    GlStateManager.enableTexture2D();
                    fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, yOff, 0x20ffffff);
                    GlStateManager.enableDepth();
                    GlStateManager.depthMask(true);
                    fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, yOff, -1);
                    GlStateManager.disableBlend();
                    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                }
                GlStateManager.enableLighting();
                GlStateManager.popMatrix();
            }
        }

    }

    protected void stretch(IMoCEntity mocreature) {
        float f = mocreature.getSizeFactor();
        if (f != 0) {
            GlStateManager.scale(f, f, f);
        }
    }

    @Override
    protected void preRenderCallback(T entityliving, float f) {
        IMoCEntity mocreature = (IMoCEntity) entityliving;
        super.preRenderCallback(entityliving, f);
        // Interpolation factor for smoother animations
        float interpolationFactor = 0.05F;
        // Interpolate pitch, roll, and yaw
        float interpolatedPitch = prevPitch + (mocreature.pitchRotationOffset() - prevPitch) * interpolationFactor;
        float interpolatedRoll = prevRoll + (mocreature.rollRotationOffset() - prevRoll) * interpolationFactor;
        float interpolatedYaw = prevYaw + (mocreature.yawRotationOffset() - prevYaw) * interpolationFactor;
        // Apply the interpolated transformations
        if (interpolatedPitch != 0) {
            GlStateManager.rotate(interpolatedPitch, -1.0F, 0.0F, 0.0F);
        }
        if (interpolatedRoll != 0) {
            GlStateManager.rotate(interpolatedRoll, 0F, 0F, -1.0F);
        }
        if (interpolatedYaw != 0) {
            GlStateManager.rotate(interpolatedYaw, 0.0F, -1.0F, 0.0F);
        }
        // Save the current values for the next frame's interpolation
        prevPitch = interpolatedPitch;
        prevRoll = interpolatedRoll;
        prevYaw = interpolatedYaw;
        adjustPitch(mocreature);
        adjustRoll(mocreature);
        adjustYaw(mocreature);
        stretch(mocreature);
    }

    /**
     * Tilts the creature to the front / back
     */
    protected void adjustPitch(IMoCEntity mocreature) {
        float f = mocreature.pitchRotationOffset();

        if (f != 0) {
            GlStateManager.rotate(f, -1F, 0.0F, 0.0F);
        }
    }

    /**
     * Rolls creature
     */
    protected void adjustRoll(IMoCEntity mocreature) {
        float f = mocreature.rollRotationOffset();

        if (f != 0) {
            GlStateManager.rotate(f, 0F, 0F, -1F);
        }
    }

    protected void adjustYaw(IMoCEntity mocreature) {
        float f = mocreature.yawRotationOffset();
        if (f != 0) {
            GlStateManager.rotate(f, 0.0F, -1.0F, 0.0F);
        }
    }

    /**
     * translates the model
     */
    protected void adjustOffsets(float xOffset, float yOffset, float zOffset) {
        GlStateManager.translate(xOffset, yOffset, zOffset);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityLiving entity) {
        return null;
    }
}
