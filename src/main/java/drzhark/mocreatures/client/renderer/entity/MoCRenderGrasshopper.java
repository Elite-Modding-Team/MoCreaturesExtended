/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.entity.ambient.MoCEntityGrasshopper;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderGrasshopper extends MoCRenderMoC<MoCEntityGrasshopper> {
    private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/grasshopper/grasshopper_bright_green.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/grasshopper/grasshopper_olive_green.png")
    };

    public MoCRenderGrasshopper(ModelBase model) {
        super(model, 0.0F);
    }

    @Override
    protected void preRenderCallback(MoCEntityGrasshopper entity, float par2) {
        rotateGrasshopper(entity);
    }

    protected void rotateGrasshopper(MoCEntityGrasshopper entity) {
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
    protected ResourceLocation getEntityTexture(MoCEntityGrasshopper entity) {
        int type = entity.getType();
        if (type < 1 || type >= TEXTURES.length) {
            type = 1;
        }
        return TEXTURES[type];
    }
}
