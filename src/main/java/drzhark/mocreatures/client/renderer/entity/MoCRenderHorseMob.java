/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
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

    public MoCRenderHorseMob(MoCModelHorseMob model) {
        super(MoCProxyClient.mc.getRenderManager(), model, 0.5F);

    }

    protected void adjustHeight(MoCEntityHorseMob entity, float FHeight) {
        GlStateManager.translate(0.0F, FHeight, 0.0F);
    }

    private ResourceLocation getHorseTexture(boolean lowRes, String texturePath) {
        String fullPath = lowRes ? "textures/entity/horse/16x/" + texturePath : "textures/entity/horse/" + texturePath;
        return new ResourceLocation(MoCConstants.MOD_ID, fullPath);
    }

    /**
     * 23 Undead
     * 24 Undead Unicorn (Unused)
     * 25 Undead Pegasus (Unused)
     * 26 Skeleton
     * 27 Skeleton Unicorn (Unused)
     * 28 Skeleton Pegasus (Unused)
     * 30 Bug Horse (Unused)
     * 32 Bat Horse
     * 38 Nightmare (Fire)
     */
    @Override
    protected ResourceLocation getEntityTexture(MoCEntityHorseMob entity) {
        boolean lowRes = MoCreatures.proxy.lowResolutionTextures;

        switch (entity.getType()) {
            case 23: // Undead Horse
                if ((!MoCreatures.proxy.getAnimateTextures() || lowRes) || entity.world == null) {
                    return getHorseTexture(lowRes, "horse_undead_0.png");
                }
                return getHorseTexture(lowRes, "horse_undead_animated_0.png");

            case 26:
                return getHorseTexture(lowRes, "horseskeleton.png");

            case 32:
                return getHorseTexture(lowRes, "horsebat.png");

            case 38:
                if (!MoCreatures.proxy.getAnimateTextures() || entity.world == null) {
                    return getHorseTexture(lowRes, "horse_nightmare.png");
                }
                return getHorseTexture(lowRes, "horse_nightmare_animated.png");

            default:
                return getHorseTexture(lowRes, "horse_undead_0.png");
        }
    }

    @Override
    protected void renderModel(MoCEntityHorseMob entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        boolean isAnimated = entity.world != null && MoCreatures.proxy.getAnimateTextures();
        boolean isLowResolution = MoCreatures.proxy.lowResolutionTextures;
        int frameCount = 1;
        int ticksPerFrame = 2;

        if (isAnimated) {
            if (entity.getType() == 23 && !isLowResolution) {
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
