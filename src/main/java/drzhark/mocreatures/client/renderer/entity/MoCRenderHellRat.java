/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.hostile.MoCEntityHellRat;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;

@SideOnly(Side.CLIENT)
public class MoCRenderHellRat extends MoCRenderRat<MoCEntityHellRat> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/rat/hell_rat.png");
    private static final ResourceLocation TEXTURE_ANIMATED = new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/rat/hell_rat_animated.png");

    public MoCRenderHellRat(ModelBase model, float f) {
        super(model, f);
    }

    @Override
    protected void stretch(MoCEntityHellRat entity) {
        float f = 1.3F;
        GlStateManager.scale(f, f, f);
    }

    @Override
    protected void renderModel(MoCEntityHellRat entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        boolean isAnimated = entity.world != null && MoCreatures.proxy.getAnimateTextures();
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
    protected ResourceLocation getEntityTexture(@Nonnull MoCEntityHellRat entity) {
        if (entity.world == null || !MoCreatures.proxy.getAnimateTextures()) {
            return TEXTURE;
        }
        return TEXTURE_ANIMATED;
    }
}
