/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.model.MoCModelPetScorpion;
import drzhark.mocreatures.client.model.MoCModelScorpion;
import drzhark.mocreatures.entity.hunter.MoCEntityPetScorpion;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderPetScorpion extends MoCRenderMoC<MoCEntityPetScorpion> {

    public MoCRenderPetScorpion(MoCModelPetScorpion model, float f) {
        super(model, f);
    }

    private ResourceLocation getScorpionTexture(boolean lowRes, String texturePath) {
        String fullPath = lowRes ? "textures/entity/scorpion/16x/" + texturePath : "textures/entity/scorpion/" + texturePath;
        return new ResourceLocation(MoCConstants.MOD_ID, fullPath);
    }

    @Override
    public void doRender(MoCEntityPetScorpion entity, double d, double d1, double d2, float f, float f1) {
        super.doRender(entity, d, d1, d2, f, f1);
    }

    @Override
    protected float getDeathMaxRotation(MoCEntityPetScorpion entity) {
        return 180.0F;
    }

    @Override
    protected void preRenderCallback(MoCEntityPetScorpion entity, float f) {
        /* TODO: Fix rider rotation
        if (entityscorpion.isOnLadder()) {
            rotateAnimal(entity);
        }
        */

        if (entity.getIsSitting()) {
            float factorY = 0.4F * (entity.getAge() / 100.0F);
            GlStateManager.translate(0.0F, factorY, 0.0F);
        }

        if (!entity.getIsAdult()) {
            stretch(entity);
            if (entity.getRidingEntity() != null) {
                upsideDown(entity);
            }
        } else {
            adjustHeight(entity);
        }
    }

    protected void upsideDown(MoCEntityPetScorpion entity) {
        GlStateManager.rotate(-90.0F, -1.0F, 0.0F, 0.0F);
        GlStateManager.translate(-1.5F, -0.5F, -2.5F);
    }

    protected void adjustHeight(MoCEntityPetScorpion entity) {
        GlStateManager.translate(0.0F, -0.1F, 0.0F);
    }

    protected void rotateAnimal(MoCEntityPetScorpion entity) {
        GlStateManager.rotate(90.0F, -1.0F, 0.0F, 0.0F);
        GlStateManager.translate(0.0F, 1.0F, 0.0F);
    }

    protected void stretch(MoCEntityPetScorpion entity) {

        float f = 1.1F;
        if (!entity.getIsAdult()) {
            f = entity.getAge() * 0.01F;
        }
        GlStateManager.scale(f, f, f);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityPetScorpion entity) {
        boolean lowRes = MoCreatures.proxy.lowResolutionTextures;
        boolean saddle = entity.getIsRideable();

        // Essence transformation
        if (entity.transformCounter != 0 && entity.transformType != 0) {
            String newText;
            switch (entity.transformType) {
                case 2: // Cave Scorpion
                    newText = saddle ? "scorpion_cave_saddled.png" : "scorpion_cave.png";
                    break;
                case 3: // Fire Scorpion
                    newText = saddle ? "scorpion_fire_saddled.png" : "scorpion_fire.png";
                    break;
                case 4: // Frost Scorpion
                    newText = saddle ? "scorpion_frost_saddled.png" : "scorpion_frost.png";
                    break;
                case 5: // Undead Scorpion
                    newText = saddle ? "scorpion_undead_saddled.png" : "scorpion_undead.png";
                    break;
                default:
                    newText = saddle ? "scorpion_undead_saddled.png" : "scorpion_undead.png";
                    break;
            }

            // Flashing effect during transformation
            if (entity.transformCounter > 60 && (entity.transformCounter % 3) == 0) {
                return getScorpionTexture(lowRes, newText);
            }
        }

        String tempTexture;
        switch (entity.getType()) {
            case 1:
                tempTexture = saddle ? "scorpion_dirt_saddled.png" : "scorpion_dirt.png";
                break;
            case 2:
                tempTexture = saddle ? "scorpion_cave_saddled.png" : "scorpion_cave.png";
                break;
            case 3:
                tempTexture = saddle ? "scorpion_fire_saddled.png" : "scorpion_fire.png";
                break;
            case 4:
                tempTexture = saddle ? "scorpion_frost_saddled.png" : "scorpion_frost.png";
                break;
            case 5:
                tempTexture = saddle ? "scorpion_undead_saddled.png" : "scorpion_undead.png";
                break;
            default:
                tempTexture = "scorpion_dirt.png";
                break;
        }

        return getScorpionTexture(lowRes, tempTexture);
    }
}
