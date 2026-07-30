/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.proxy.MoCProxyClient;
import drzhark.mocreatures.client.model.MoCModelCrocodile;
import drzhark.mocreatures.entity.hunter.MoCEntityCrocodile;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderCrocodile extends RenderLiving<MoCEntityCrocodile> {
    private static final ResourceLocation TEXTURE = MoCreatures.proxy.lowResolutionTextures
            ? new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/crocodile/16x/crocodile.png")
            : new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/crocodile/crocodile.png");

    public MoCModelCrocodile croc;

    public MoCRenderCrocodile(MoCModelCrocodile model, float f) {
        super(MoCProxyClient.mc.getRenderManager(), model, f);
        this.croc = model;
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityCrocodile entity) {
        return TEXTURE;
    }

    @Override
    public void doRender(MoCEntityCrocodile entity, double d, double d1, double d2, float f, float f1) {
        super.doRender(entity, d, d1, d2, f, f1);
    }

    @Override
    protected void preRenderCallback(MoCEntityCrocodile entity, float f) {
        this.croc.biteProgress = entity.biteProgress;
        this.croc.swimming = entity.isSwimming();
        this.croc.resting = entity.getIsSitting();
        if (entity.isSpinning()) {
            spinCroc(entity, (EntityLiving) entity.getRidingEntity());
        }
        stretch(entity);
        if (entity.getIsSitting()) {
            if (!entity.isInsideOfMaterial(Material.WATER)) {
                adjustHeight(entity, 0.2F);
            } else {
                //adjustHeight(entitycrocodile, 0.1F);
            }

        }
        // if(!entitycrocodile.getIsAdult()) { }
    }

    protected void rotateAnimal(MoCEntityCrocodile entitycrocodile) {

        //float f = entitycrocodile.swingProgress *10F *entitycrocodile.getFlipDirection();
        //float f2 = entitycrocodile.swingProgress /30 *entitycrocodile.getFlipDirection();
        //GlStateManager.rotate(180F + f, 0.0F, 0.0F, -1.0F);
        //GlStateManager.translate(0.0F-f2, 0.5F, 0.0F);
    }

    protected void adjustHeight(MoCEntityCrocodile entitycrocodile, float FHeight) {
        GlStateManager.translate(0.0F, FHeight, 0.0F);
    }

    protected void spinCroc(MoCEntityCrocodile entity, EntityLiving prey) {
        int intSpin = entity.spinInt;
        int direction = 1;
        if (intSpin > 40) {
            intSpin -= 40;
            direction = -1;
        }
        int intEndSpin = intSpin;
        if (intSpin >= 20) {
            intEndSpin = (20 - (intSpin - 20));
        }
        if (intEndSpin == 0) {
            intEndSpin = 1;
        }
        float f3 = (((intEndSpin) - 1.0F) / 20F) * 1.6F;
        f3 = MathHelper.sqrt(f3);
        if (f3 > 1.0F) {
            f3 = 1.0F;
        }
        f3 *= direction;
        GlStateManager.rotate(f3 * 90F, 0.0F, 0.0F, 1.0F);

        if (prey != null) {
            prey.deathTime = intEndSpin;
        }
    }

    protected void stretch(MoCEntityCrocodile entity) {
        // float f = 1.3F;
        float f = entity.getAge() * 0.01F;
        // if(!entitycrocodile.getIsAdult()) { f = entitycrocodile.age; }
        GlStateManager.scale(f, f, f);
    }
}
