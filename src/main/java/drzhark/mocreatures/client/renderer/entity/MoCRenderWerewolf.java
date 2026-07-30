/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.proxy.MoCProxyClient;
import drzhark.mocreatures.client.model.MoCModelWerehuman;
import drzhark.mocreatures.client.model.MoCModelWerewolf;
import drzhark.mocreatures.entity.hostile.MoCEntityWerewolf;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MoCRenderWerewolf extends RenderLiving<MoCEntityWerewolf> {
    private final MoCModelWerewolf tempWerewolf;

    public MoCRenderWerewolf(MoCModelWerehuman model, ModelBase modelBase, float f) {
        super(MoCProxyClient.mc.getRenderManager(), modelBase, f);
        this.addLayer(new LayerMoCWereHuman(this));
        this.tempWerewolf = (MoCModelWerewolf) modelBase;
    }

    private ResourceLocation getWerewolfTexture(boolean lowRes, String texturePath) {
        String fullPath = "textures/entity/werewolf/" + texturePath;
        return new ResourceLocation(MoCConstants.MOD_ID, fullPath);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityWerewolf entity) {
        boolean lowRes = MoCreatures.proxy.lowResolutionTextures;
        String tempTexture;

        if (entity.getIsHumanForm()) {
            return getWerewolfTexture(false, "wereblank.png");
        }

        switch (entity.getType()) {
            case 1:
                tempTexture = "werewolf_black.png";
                break;
            case 3:
                tempTexture = "werewolf_white.png";
                break;
            case 4:
                if (!MoCreatures.proxy.getAnimateTextures()) {
                    tempTexture = "werewolf_fire.png";
                    break;
                }
                tempTexture = "werewolf_fire_animated.png";
                break;
            default:
                tempTexture = "werewolf_brown.png";
                break;
        }

        return getWerewolfTexture(lowRes, tempTexture);
    }

    @Override
    public void doRender(MoCEntityWerewolf entity, double d, double d1, double d2, float f, float f1) {
        this.tempWerewolf.hunched = entity.getIsHunched();
        super.doRender(entity, d, d1, d2, f, f1);

    }

    @Override
    protected void renderModel(MoCEntityWerewolf entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        boolean isAnimated = entity.getType() == 4 && !entity.getIsHumanForm() && MoCreatures.proxy.getAnimateTextures();
        int frameCount = 3;
        int ticksPerFrame = 2;

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

    private class LayerMoCWereHuman implements LayerRenderer<MoCEntityWerewolf> {

        private final MoCRenderWerewolf mocRenderer;
        private final MoCModelWerehuman mocModel = new MoCModelWerehuman();

        public LayerMoCWereHuman(MoCRenderWerewolf render) {
            this.mocRenderer = render;
        }

        public void doRenderLayer(MoCEntityWerewolf entity, float f, float f1, float f2, float f3, float f4, float f5, float f6) {
            int myType = entity.getType();

            if (!entity.getIsHumanForm()) {
                bindTexture(getWerewolfTexture(false, "wereblank.png"));
            } else {
                switch (myType) {

                    case 1:
                        bindTexture(getWerewolfTexture(false, "werehuman_dude.png"));
                        break;
                    case 2:
                        bindTexture(getWerewolfTexture(false, "werehuman_classic.png"));
                        break;
                    case 4:
                        bindTexture(getWerewolfTexture(false, "werehuman_woman.png"));
                        break;
                    default:
                        bindTexture(getWerewolfTexture(false, "werehuman_oldie.png"));
                }
            }

            this.mocModel.setModelAttributes(this.mocRenderer.getMainModel());
            this.mocModel.setLivingAnimations(entity, f, f1, f2);
            this.mocModel.render(entity, f, f1, f3, f4, f5, f6);
        }

        @Override
        public boolean shouldCombineTextures() {
            return true;
        }
    }
}
