/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.model.MoCModelWraith;
import drzhark.mocreatures.proxy.MoCProxyClient;
import drzhark.mocreatures.entity.hostile.MoCEntityWraith;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderWraith extends RenderLiving<MoCEntityWraith> {
    public MoCRenderWraith() {
        super(MoCProxyClient.mc.getRenderManager(), new MoCModelWraith(), 0.5F);
    }

    private ResourceLocation getWraithTexture(boolean lowRes, String texturePath) {
        String fullPath = "textures/entity/wraith/" + texturePath;
        return new ResourceLocation(MoCConstants.MOD_ID, fullPath);
    }

    @Override
    public void doRender(MoCEntityWraith entity, double d, double d1, double d2, float f, float f1) {
        boolean flag = entity.isGlowing();
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        if (!flag) {
            float transparency = 0.6F;
            GlStateManager.blendFunc(770, 771);
            GlStateManager.color(0.8F, 0.8F, 0.8F, transparency);
        } else {
            GlStateManager.blendFunc(770, 1);
        }
        super.doRender(entity, d, d1, d2, f, f1);
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityWraith entity) {
        boolean lowRes = MoCreatures.proxy.lowResolutionTextures;
        boolean isAlpha = MoCreatures.proxy.alphaWraithEyes;
        String tempTexture;

        switch (entity.getType()) {
            case 2:
                tempTexture = "wraith_scratch.png";
                break;
            default:
                tempTexture = isAlpha ? "alpha/wraith_alpha.png" : "wraith.png";
                break;
        }

        return getWraithTexture(lowRes, tempTexture);
    }
}
