/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.client.model.MoCModelBear;
import drzhark.mocreatures.entity.hunter.MoCEntityBear;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderBear extends MoCRenderMoC<MoCEntityBear> {

    public MoCRenderBear() {
        super(new MoCModelBear(), 0.7F);
    }

    @Override
    protected void preRenderCallback(MoCEntityBear entity, float f) {
        stretch(entity);
        super.preRenderCallback(entity, f);

    }

    protected void stretch(MoCEntityBear entity) {
        float sizeFactor = entity.getAge() * 0.01F;
        if (entity.getIsAdult()) {
            sizeFactor = 1.0F;
        }
        sizeFactor *= entity.getBearSize();
        GlStateManager.scale(sizeFactor, sizeFactor, sizeFactor);
    }
}
