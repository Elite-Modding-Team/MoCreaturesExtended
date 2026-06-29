/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.model;

import drzhark.mocreatures.entity.hostile.MoCEntityWraith;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCModelWraith extends ModelBiped {
    private int attackCounter;

    public MoCModelWraith() {
        textureWidth = 64;
        textureHeight = 40;
        this.bipedHead = new ModelRenderer(this, 0, 0);
        this.bipedHead.addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8, 0.0F);
        this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bipedBody = new ModelRenderer(this, 16, 16);
        this.bipedBody.addBox(-4.0F, 4.0F, -2.0F, 8, 20, 4, 0.0F);
        this.bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bipedRightArm = new ModelRenderer(this, 40, 16);
        this.bipedRightArm.addBox(-2.75F, -2.0F, 2.0F, 4, 12, 4, 0.0F);
        this.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
        this.bipedLeftArm = new ModelRenderer(this, 40, 16);
        this.bipedLeftArm.mirror = true;
        this.bipedLeftArm.addBox(-1.25F, -2.0F, 2.0F, 4, 12, 4, 0.0F);
        this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
    }

    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
        GlStateManager.pushMatrix();
        if (this.isChild) {
            GlStateManager.scale(0.75F, 0.75F, 0.75F);
            GlStateManager.translate(0.0F, 16.0F * scale, 0.0F);
            this.bipedHead.render(scale);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
            GlStateManager.translate(0.0F, 24.0F * scale, 0.0F);
        } else {
            if (entityIn.isSneaking()) {
                GlStateManager.translate(0.0F, 0.2F, 0.0F);
            }
            this.bipedHead.render(scale);
        }
        this.bipedBody.render(scale);
        this.bipedRightArm.render(scale);
        this.bipedLeftArm.render(scale);
        this.bipedRightLeg.render(scale);
        this.bipedLeftLeg.render(scale);
        GlStateManager.popMatrix();
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity par7Entity) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, par7Entity);
        if (par7Entity instanceof MoCEntityWraith) {
            this.attackCounter = ((MoCEntityWraith) par7Entity).attackCounter;
        }
        float f6 = MathHelper.sin(this.swingProgress * 3.141593F);
        float f7 = MathHelper.sin((1.0F - ((1.0F - this.swingProgress) * (1.0F - this.swingProgress))) * 3.141593F);
        this.bipedRightArm.rotateAngleZ = 0.0F;
        this.bipedLeftArm.rotateAngleZ = 0.0F;
        this.bipedRightArm.rotateAngleY = -(0.1F - (f6 * 0.6F));
        this.bipedLeftArm.rotateAngleY = 0.1F - (f6 * 0.6F);
        if (this.attackCounter != 0) {
            float armMov = (MathHelper.cos((attackCounter) * 0.12F) * 4F);
            this.bipedRightArm.rotateAngleX = -armMov;
            this.bipedLeftArm.rotateAngleX = -armMov;
        } else {
            this.bipedRightArm.rotateAngleX = -1.570796F;
            this.bipedLeftArm.rotateAngleX = -1.570796F;
            this.bipedRightArm.rotateAngleX -= (f6 * 1.2F) - (f7 * 0.4F);
            this.bipedLeftArm.rotateAngleX -= (f6 * 1.2F) - (f7 * 0.4F);
            this.bipedRightArm.rotateAngleX += MathHelper.sin(f2 * 0.067F) * 0.05F;
            this.bipedLeftArm.rotateAngleX -= MathHelper.sin(f2 * 0.067F) * 0.05F;
        }
        this.bipedRightArm.rotateAngleZ += (MathHelper.cos(f2 * 0.09F) * 0.05F) + 0.05F;
        this.bipedLeftArm.rotateAngleZ -= (MathHelper.cos(f2 * 0.09F) * 0.05F) + 0.05F;
    }
}
