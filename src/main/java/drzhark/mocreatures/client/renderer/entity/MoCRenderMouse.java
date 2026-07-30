/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.entity.passive.MoCEntityMouse;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderMouse extends MoCRenderMoC<MoCEntityMouse> {
    private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/mouse/mouse_beige.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/mouse/mouse_brown.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/mouse/mouse_white.png")
    };

    public MoCRenderMouse(ModelBase model, float f) {
        super(model, f);
    }

    @Override
    public void doRender(MoCEntityMouse entity, double d, double d1, double d2, float f, float f1) {
        super.doRender(entity, d, d1, d2, f, f1);
    }

    @Override
    protected float handleRotationFloat(MoCEntityMouse entity, float f) {
        stretch(entity);
        return entity.ticksExisted + f;
    }

    @Override
    protected void preRenderCallback(MoCEntityMouse entity, float f) {
        // When mice are picked up
        if (entity.upsideDown()) {
            upsideDown(entity);
        }

        if (entity.isOnLadder()) {
            rotateAnimal(entity);
        }
    }

    protected void rotateAnimal(MoCEntityMouse entity) {
        GlStateManager.rotate(90.0F, -1.0F, 0.0F, 0.0F);
        GlStateManager.translate(0.0F, 0.4F, 0.0F);
    }

    protected void stretch(MoCEntityMouse entity) {
        float f = 0.6F;
        GlStateManager.scale(f, f, f);
    }

    protected void upsideDown(MoCEntityMouse entity) {
        GlStateManager.rotate(-90.0F, -1.0F, 0.0F, 0.0F);
        GlStateManager.translate(-0.55F, 0.0F, 0.0F);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityMouse entity) {
        int type = entity.getType();
        return TEXTURES[type - 1];
    }
}
