/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.model.MoCModelTurtle;
import drzhark.mocreatures.entity.passive.MoCEntityTurtle;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderTurtle extends MoCRenderMoC<MoCEntityTurtle> {
    public MoCModelTurtle turtly;

    public MoCRenderTurtle(MoCModelTurtle model, float f) {
        super(model, f);
        this.turtly = model;
    }

    private ResourceLocation getTurtleTexture(boolean lowRes, String texturePath) {
        String fullPath = "textures/entity/turtle/" + texturePath;
        return new ResourceLocation(MoCConstants.MOD_ID, fullPath);
    }

    @Override
    protected void preRenderCallback(MoCEntityTurtle entity, float f) {
        this.turtly.upsidedown = entity.getIsUpsideDown();
        this.turtly.swingProgress = entity.swingProgress;
        this.turtly.isHiding = entity.getIsHiding();

        if (!entity.world.isRemote && (entity.getRidingEntity() != null)) {

            GlStateManager.translate(0.0F, 1.3F, 0.0F);

        }
        if (entity.getIsHiding()) {
            adjustHeight(entity, 0.15F * entity.getAge() * 0.01F);
        } else if (!entity.getIsHiding() && !entity.getIsUpsideDown() && !entity.isInsideOfMaterial(Material.WATER)) {
            adjustHeight(entity, 0.05F * entity.getAge() * 0.01F);
        }
        if (entity.getIsUpsideDown()) {
            rotateAnimal(entity);
        }

        stretch(entity);

    }

    protected void rotateAnimal(MoCEntityTurtle entity) {
        //GlStateManager.rotate(180F, -1F, 0.0F, 0.0F); //head up 180
        //GlStateManager.rotate(180F, 0.0F, -1.0F, 0.0F); //head around 180

        float f = entity.swingProgress * 10F * entity.getFlipDirection();
        float f2 = entity.swingProgress / 30 * entity.getFlipDirection();
        GlStateManager.rotate(180F + f, 0.0F, 0.0F, -1.0F);
        GlStateManager.translate(0.0F - f2, 0.5F * entity.getAge() * 0.01F, 0.0F);
    }

    protected void adjustHeight(MoCEntityTurtle entity, float height) {
        GlStateManager.translate(0.0F, height, 0.0F);
    }

    protected void stretch(MoCEntityTurtle entity) {
        float f = entity.getAge() * 0.01F;
        GlStateManager.scale(f, f, f);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityTurtle entity) {
        boolean lowRes = MoCreatures.proxy.lowResolutionTextures;
        String tempTexture = "turtle.png";

        if (MoCreatures.proxy.easterEggs) {
            String petName = entity.getPetName();
            if ((petName == null || petName.isEmpty()) && entity.hasCustomName()) {
                petName = entity.getCustomNameTag();
            }

            if (petName != null && !petName.isEmpty()) {
                switch (petName.toLowerCase()) {
                    case "donatello":
                        tempTexture = "turtle_donatello.png";
                        break;
                    case "leonardo":
                        tempTexture = "turtle_leonardo.png";
                        break;
                    case "raphael":
                        tempTexture = "turtle_raphael.png";
                        break;
                    case "michelangelo":
                        tempTexture = "turtle_michelangelo.png";
                        break;
                    default:
                        tempTexture = "turtle.png";
                        break;
                }
            }
        }

        return getTurtleTexture(lowRes, tempTexture);
    }
}
