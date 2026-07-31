/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.proxy.MoCProxyClient;
import drzhark.mocreatures.entity.hostile.MoCEntityRat;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderRat<T extends MoCEntityRat> extends RenderLiving<T> {
    private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/rat/rat_brown.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/rat/rat_black.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/rat/rat_white.png")
    };

    public MoCRenderRat(ModelBase model, float f) {
        super(MoCProxyClient.mc.getRenderManager(), model, f);
    }

    @Override
    public void doRender(T entity, double d, double d1, double d2, float f, float f1) {
        super.doRender(entity, d, d1, d2, f, f1);
    }

    @Override
    protected float handleRotationFloat(T entity, float f) {
        stretch(entity);
        return entity.ticksExisted + f;
    }

    @Override
    protected void preRenderCallback(T entity, float f) {
        if (entity.isOnLadder()) {
            rotateAnimal(entity);
        }
    }

    protected void rotateAnimal(T entity) {
        GlStateManager.rotate(90.0F, -1.0F, 0.0F, 0.0F);
        GlStateManager.translate(0.0F, 0.4F, 0.0F);
    }

    protected void stretch(T entity) {
        float f = 0.8F;
        GlStateManager.scale(f, f, f);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityRat entity) {
        int type = entity.getType();
        if (type < 0 || type >= TEXTURES.length) {
            type = 0;
        }
        return TEXTURES[type];
    }
}
