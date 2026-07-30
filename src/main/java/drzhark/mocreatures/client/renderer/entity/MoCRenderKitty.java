/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.model.MoCModelKitty;
import drzhark.mocreatures.entity.neutral.MoCEntityKitty;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderKitty extends MoCRenderMoC<MoCEntityKitty> {
    private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/kitty/kitty_cream.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/kitty/kitty_gray.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/kitty/kitty_black.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/kitty/kitty_calico.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/kitty/kitty_tuxedo.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/kitty/kitty_white_black.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/kitty/kitty_white.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/kitty/kitty_orange_tabby.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/kitty/kitty_cream_dark.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/kitty/kitty_gray_tabby.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/kitty/kitty_yellow_tabby.png")
    };

    public MoCModelKitty kitty;

    public MoCRenderKitty(ModelBase model, float f) {
        super(model, f);
        this.kitty = (MoCModelKitty) model;
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityKitty entity) {
        int type = entity.getType();
        if (type < 1 || type >= TEXTURES.length) {
            type = 1;
        }
        return TEXTURES[type];
    }

    @Override
    public void doRender(MoCEntityKitty entity, double d, double d1, double d2, float f, float f1) {
        super.doRender(entity, d, d1, d2, f, f1);
        boolean displayPetIcons = MoCreatures.proxy.getDisplayPetIcons();
        if (entity.getIsTamed()) {
            float f2 = 1.6F;
            float f3 = 0.01666667F * f2;
            float f4 = entity.getDistance(this.renderManager.renderViewEntity);
            if (f4 < 12F) {
                float f5 = 0.2F;
                if (this.kitty.isSitting) {
                    f5 = 0.4F;
                }

                GlStateManager.pushMatrix();
                GlStateManager.translate((float) d + 0.0F, (float) d1 - f5, (float) d2);
                GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
                GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
                GlStateManager.scale(-f3, -f3, f3);
                GlStateManager.disableLighting();
                Tessellator tessellator = Tessellator.getInstance();

                if (displayPetIcons && entity.getShowEmoteIcon()) {
                    this.bindTexture(entity.getEmoteIcon());
                    int i = -90;
                    int k = 32;
                    int l = (k / 2) * -1;
                    float f9 = 0.0F;
                    float f11 = 1.0F / k;
                    float f12 = 1.0F / k;
                    tessellator.getBuffer().begin(7, DefaultVertexFormats.POSITION_TEX);
                    tessellator.getBuffer().pos(l, i + k, f9).tex(0.0D, k * f12).endVertex();
                    tessellator.getBuffer().pos(l + k, i + k, f9).tex(k * f11, k * f12).endVertex();
                    tessellator.getBuffer().pos(l + k, i, f9).tex(k * f11, 0.0D).endVertex();
                    tessellator.getBuffer().pos(l, i, f9).tex(0.0D, 0.0D).endVertex();
                    tessellator.draw();
                }

                GlStateManager.enableLighting();
                GlStateManager.popMatrix();
            }
        }
    }

    @Override
    protected float handleRotationFloat(MoCEntityKitty entity, float f) {
        if (!entity.getIsAdult()) {
            stretch(entity);
        }
        return entity.ticksExisted + f;
    }

    protected void onMaBack(MoCEntityKitty entity) {
        GlStateManager.rotate(90F, 0.0F, 0.0F, -1F);
        if (!entity.world.isRemote && (entity.getRidingEntity() != null)) {
            GlStateManager.translate(-1.5F, 0.2F, -0.2F);
        } else {
            GlStateManager.translate(0.1F, 0.2F, -0.2F);
        }

    }

    protected void onTheSide(MoCEntityKitty entity) {
        GlStateManager.rotate(90F, 0.0F, 0.0F, -1F);
        GlStateManager.translate(0.2F, 0.0F, -0.2F);
    }

    @Override
    protected void preRenderCallback(MoCEntityKitty entity, float f) {
        this.kitty.isSitting = entity.getIsSitting();
        this.kitty.isSwinging = entity.getIsSwinging();
        this.kitty.swingProgress = entity.swingProgress;
        this.kitty.kittystate = entity.getKittyState();
        if (entity.getKittyState() == 20) {
            onTheSide(entity);
        }
        if (entity.climbingTree()) {
            rotateAnimal(entity);
        }
        if (entity.upsideDown()) {
            upsideDown(entity);
        }
        if (entity.onMaBack()) {
            onMaBack(entity);
        }
    }

    protected void rotateAnimal(MoCEntityKitty entity) {
        GlStateManager.rotate(90F, -1.0F, 0.0F, 0.0F);
        GlStateManager.translate(0.0F, 0.5F, 0.0F);
    }

    protected void stretch(MoCEntityKitty entity) {
        GlStateManager.scale(entity.getAge() * 0.01F, entity.getAge() * 0.01F, entity.getAge() * 0.01F);
    }

    protected void upsideDown(MoCEntityKitty entity) {
        GlStateManager.rotate(180F, 0.0F, 0.0F, -1F);
        GlStateManager.translate(-0.35F, 0F, -0.55F);
    }
}
