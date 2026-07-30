/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.proxy.MoCProxyClient;
import drzhark.mocreatures.entity.aquatic.MoCEntityFishy;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderFishy extends RenderLiving<MoCEntityFishy> {
    private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/fishy/fishy_blue.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/fishy/fishy_orange.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/fishy/fishy_light_blue.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/fishy/fishy_lime.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/fishy/fishy_green.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/fishy/fishy_purple.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/fishy/fishy_yellow.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/fishy/fishy_cyan.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/fishy/fishy_striped.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/fishy/fishy_red.png")
    };

    public MoCRenderFishy(ModelBase model, float f) {
        super(MoCProxyClient.mc.getRenderManager(), model, f);
    }

    @Override
    public void doRender(MoCEntityFishy entity, double d, double d1, double d2, float f, float f1) {
        if (entity.getType() == 0) { // && !MoCreatures.mc.isMultiplayerWorld())
            entity.selectType();
        }
        super.doRender(entity, d, d1, d2, f, f1);
    }

    @Override
    protected void preRenderCallback(MoCEntityFishy entity, float f) {
        GlStateManager.translate(0.0F, 0.3F, 0.0F);
    }

    @Override
    protected float handleRotationFloat(MoCEntityFishy entity, float f) {
        if (!entity.getIsAdult()) {
            stretch(entity);
        }
        return entity.ticksExisted + f;
    }

    protected void stretch(MoCEntityFishy entity) {
        GlStateManager.scale(entity.getAge() * 0.01F, entity.getAge() * 0.01F, entity.getAge() * 0.01F);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityFishy entity) {
        int type = entity.getType();
        return TEXTURES[type - 1];
    }
}
