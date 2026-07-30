/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.client.model.MoCModelGolem;
import drzhark.mocreatures.entity.hostile.MoCEntityGolem;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderGolem extends MoCRenderMoC<MoCEntityGolem> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/block_golem/block_golem.png");

    public MoCRenderGolem(ModelBase model, float f) {
        super(model, f);
        this.addLayer(new LayerMoCGolem(this));
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityGolem entity) {
        return TEXTURE;
    }

    /**
     * Used for the power texture used on the golem
     */
    public ResourceLocation getEffectTexture(MoCEntityGolem entity) {
        switch (entity.getGolemState()) {
            case 1:
                return new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/block_golem/golem_effect_red.png");
            case 2:
                return new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/block_golem/golem_effect_yellow.png");
            case 3:
                return new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/block_golem/golem_effect_orange.png");
            case 4:
                return new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/block_golem/golem_effect_blue.png");
            default:
                return null;
        }
    }

    private class LayerMoCGolem implements LayerRenderer<MoCEntityGolem> {
        private final MoCRenderGolem mocRenderer;
        private final MoCModelGolem mocModel = new MoCModelGolem();

        public LayerMoCGolem(MoCRenderGolem render) {
            this.mocRenderer = render;
        }

        public void doRenderLayer(MoCEntityGolem entity, float f, float f1, float f2, float f3, float f4, float f5, float f6) {
            ResourceLocation effectTexture = getEffectTexture(entity);
            if (effectTexture != null) {
                GlStateManager.depthMask(false);
                float var4 = entity.ticksExisted + f1;
                bindTexture(effectTexture);
                GlStateManager.matrixMode(5890);
                GlStateManager.loadIdentity();
                float var5 = var4 * 0.01F;
                float var6 = var4 * 0.01F;
                GlStateManager.translate(var5, var6, 0.0F);
                GlStateManager.matrixMode(5888);
                GlStateManager.enableBlend();
                float var7 = 0.5F;
                GlStateManager.color(var7, var7, var7, 1.0F);
                GlStateManager.disableLighting();
                GlStateManager.blendFunc(1, 1);
                this.mocModel.setModelAttributes(this.mocRenderer.getMainModel());
                this.mocModel.setLivingAnimations(entity, f, f1, f2);
                this.mocModel.render(entity, f, f1, f3, f4, f5, f6);
                GlStateManager.matrixMode(5890);
                GlStateManager.loadIdentity();
                GlStateManager.matrixMode(5888);
                GlStateManager.enableLighting();
                GlStateManager.disableBlend();
                GlStateManager.depthMask(true);
            }
        }

        @Override
        public boolean shouldCombineTextures() {
            return true;
        }
    }
}
