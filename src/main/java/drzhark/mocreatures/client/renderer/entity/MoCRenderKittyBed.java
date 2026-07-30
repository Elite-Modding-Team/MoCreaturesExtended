/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.proxy.MoCProxyClient;
import drzhark.mocreatures.client.model.MoCModelKittyBed;
import drzhark.mocreatures.client.model.MoCModelKittyBed2;
import drzhark.mocreatures.entity.item.MoCEntityKittyBed;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings("unused")
@SideOnly(Side.CLIENT)
public class MoCRenderKittyBed extends RenderLiving<MoCEntityKittyBed> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MoCConstants.MOD_ID, "textures/models/kitty_bed.png");

    public static float[][] fleeceColorTable = {{1.0F, 1.0F, 1.0F}, {0.95F, 0.7F, 0.2F}, {0.9F, 0.5F, 0.85F}, {0.6F, 0.7F, 0.95F},
            {0.9F, 0.9F, 0.2F}, {0.5F, 0.8F, 0.1F}, {0.95F, 0.7F, 0.8F}, {0.3F, 0.3F, 0.3F}, {0.6F, 0.6F, 0.6F}, {0.3F, 0.6F, 0.7F},
            {0.7F, 0.4F, 0.9F}, {0.2F, 0.4F, 0.8F}, {0.5F, 0.4F, 0.3F}, {0.4F, 0.5F, 0.2F}, {0.8F, 0.3F, 0.3F}, {0.1F, 0.1F, 0.1F}};
    public MoCModelKittyBed kittybed;
    private int mycolor;

    public MoCRenderKittyBed(MoCModelKittyBed model, MoCModelKittyBed2 model2, float f) {
        super(MoCProxyClient.mc.getRenderManager(), model, f);
        this.kittybed = model;
        this.addLayer(new LayerMoCKittyBed(this));
    }

    @Override
    protected void preRenderCallback(MoCEntityKittyBed entity, float f) {
        this.mycolor = entity.getSheetColor();
        this.kittybed.hasMilk = entity.getHasMilk();
        this.kittybed.hasFood = entity.getHasFood();
        this.kittybed.pickedUp = entity.getPickedUp();
        this.kittybed.milklevel = entity.milkLevel;
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityKittyBed entity) {
        return TEXTURE;
    }

    private static class LayerMoCKittyBed implements LayerRenderer<MoCEntityKittyBed> {

        private final MoCRenderKittyBed mocRenderer;
        private final MoCModelKittyBed2 mocModel = new MoCModelKittyBed2();

        public LayerMoCKittyBed(MoCRenderKittyBed render) {
            this.mocRenderer = render;
        }

        public void doRenderLayer(MoCEntityKittyBed entity, float f, float f1, float f2, float f3, float f4, float f5, float f6) {
            float f8 = 0.35F;
            int j = this.mocRenderer.mycolor;
            GlStateManager.color(f8 * fleeceColorTable[j][0], f8 * fleeceColorTable[j][1], f8 * fleeceColorTable[j][2]);
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
