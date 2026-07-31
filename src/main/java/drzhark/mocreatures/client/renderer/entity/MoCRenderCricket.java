/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.entity.ambient.MoCEntityCricket;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderCricket extends MoCRenderMoC<MoCEntityCricket> {
    private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/cricket/cricket_light_brown.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/cricket/cricket_brown.png")
    };

    public MoCRenderCricket(ModelBase model) {
        super(model, 0.0F);
    }

    @Override
    protected void preRenderCallback(MoCEntityCricket entity, float par2) {
        rotateCricket(entity);
    }

    protected void rotateCricket(MoCEntityCricket entity) {
        if (!entity.onGround) {
            if (entity.motionY > 0.5D) {
                GlStateManager.rotate(35F, -1F, 0.0F, 0.0F);
            } else if (entity.motionY < -0.5D) {
                GlStateManager.rotate(-35F, -1F, 0.0F, 0.0F);
            } else {
                GlStateManager.rotate((float) (entity.motionY * 70D), -1F, 0.0F, 0.0F);
            }
        }
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityCricket entity) {
        int type = entity.getType();
        if (type < 0 || type >= TEXTURES.length) {
            type = 0;
        }
        return TEXTURES[type];
    }
}
