/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.client.model.MoCModelScorpion;
import drzhark.mocreatures.entity.hostile.MoCEntityScorpion;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderScorpion extends MoCRenderMoC<MoCEntityScorpion> {
    public MoCRenderScorpion() {
        super(new MoCModelScorpion(), 0.4F);
    }

    @Override
    public void doRender(MoCEntityScorpion entity, double d, double d1, double d2, float f, float f1) {
        super.doRender(entity, d, d1, d2, f, f1);
    }

    @Override
    protected float getDeathMaxRotation(MoCEntityScorpion entity) {
        return 180.0F;
    }

    @Override
    protected void preRenderCallback(MoCEntityScorpion entity, float f) {
        /* TODO: Fix rider rotation
        if (entity.isOnLadder()) {
            rotateAnimal(entity);
        }
        */

        if (!entity.getIsAdult()) {
            stretch(entity);
        } else {
            adjustHeight(entity);
        }
    }

    protected void adjustHeight(MoCEntityScorpion entity) {
        GlStateManager.translate(0.0F, -0.1F, 0.0F);
    }

    protected void rotateAnimal(MoCEntityScorpion entity) {
        GlStateManager.rotate(90.0F, -1.0F, 0.0F, 0.0F);
        GlStateManager.translate(0.0F, 1.0F, 0.0F);
    }

    protected void stretch(MoCEntityScorpion entity) {

        float f = 1.1F;
        if (!entity.getIsAdult()) {
            f = entity.getAge() * 0.01F;
        }
        GlStateManager.scale(f, f, f);
    }
}
