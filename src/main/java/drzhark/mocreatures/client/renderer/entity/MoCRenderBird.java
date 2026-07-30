/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.passive.MoCEntityBird;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderBird extends MoCRenderMoC<MoCEntityBird> {
    private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/bird/bird_white.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/bird/bird_black.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/bird/bird_green.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/bird/bird_yellow.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/bird/bird_red.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/bird/bird_blue.png")
    };

    private static final ResourceLocation[] TEXTURES_LOW = new ResourceLocation[] {
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/bird/16x/bird_white.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/bird/16x/bird_black.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/bird/16x/bird_green.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/bird/16x/bird_yellow.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/bird/16x/bird_red.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/bird/16x/bird_blue.png")
    };

    public MoCRenderBird(ModelBase model, float f) {
        super(model, f);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityBird entity) {
        int type = entity.getType();
        if (type < 1 || type >= TEXTURES.length) {
            type = 1;
        }
        if (MoCreatures.proxy.lowResolutionTextures) {
            return TEXTURES_LOW[type];
        }
        return TEXTURES[type];
    }

    @Override
    public void doRender(MoCEntityBird entity, double d, double d1, double d2, float f, float f1) {
        super.doRender(entity, d, d1, d2, f, f1);
    }

    @Override
    protected float handleRotationFloat(MoCEntityBird entity, float f) {
        float f1 = entity.winge + ((entity.wingb - entity.winge) * f);
        float f2 = entity.wingd + ((entity.wingc - entity.wingd) * f);
        return (MathHelper.sin(f1) + 1.0F) * f2;
    }

    @Override
    protected void preRenderCallback(MoCEntityBird entity, float f) {
        if (!entity.world.isRemote && (entity.getRidingEntity() != null)) {
            GlStateManager.translate(0.0F, 1.3F, 0.0F);
        }
    }
}
