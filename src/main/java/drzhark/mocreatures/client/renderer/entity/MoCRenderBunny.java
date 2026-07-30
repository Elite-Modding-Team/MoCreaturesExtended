/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.passive.MoCEntityBunny;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderBunny extends MoCRenderMoC<MoCEntityBunny> {
    private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/bunny/bunny_golden.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/bunny/bunny_beige.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/bunny/bunny_white.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/bunny/bunny_black.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/bunny/bunny_spotted.png")
    };

    private static final ResourceLocation[] TEXTURES_LOW = new ResourceLocation[] {
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/bunny/legacy/bunny_golden.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/bunny/legacy/bunny_beige.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/bunny/legacy/bunny_white.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/bunny/legacy/bunny_black.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/bunny/legacy/bunny_spotted.png")
    };

    public MoCRenderBunny(ModelBase model, float f) {
        super(model, f);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityBunny entity) {
        int type = entity.getType();
        if (MoCreatures.proxy.legacyBunnyTextures) {
            return TEXTURES_LOW[type - 1];
        }
        return TEXTURES[type - 1];
    }

    @Override
    protected float handleRotationFloat(MoCEntityBunny entity, float f) {
        if (!entity.getIsAdult()) {
            stretch(entity);
        }
        return entity.ticksExisted + f;
    }

    @Override
    protected void preRenderCallback(MoCEntityBunny entity, float f) {
        rotBunny(entity);
        adjustOffsets(entity.getAdjustedXOffset(), entity.getAdjustedYOffset(), entity.getAdjustedZOffset());
    }

    protected void rotBunny(MoCEntityBunny entity) {
        if (!entity.onGround && (entity.getRidingEntity() == null)) {
            if (entity.motionY > 0.5D) {
                GlStateManager.rotate(35F, -1F, 0.0F, 0.0F);
            } else if (entity.motionY < -0.5D) {
                GlStateManager.rotate(-35F, -1F, 0.0F, 0.0F);
            } else {
                GlStateManager.rotate((float) (entity.motionY * 70D), -1F, 0.0F, 0.0F);
            }
        }
    }

    protected void stretch(MoCEntityBunny entity) {
        float f = entity.getAge() * 0.01F;
        GlStateManager.scale(f, f, f);
    }
}
