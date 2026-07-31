/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.model.MoCModelHorse;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MoCRenderHorse extends MoCRenderMoC<MoCEntityHorse> {
    public MoCRenderHorse(MoCModelHorse model) {
        super(model, 0.5F);
    }

    private ResourceLocation getHorseTexture(boolean lowRes, String texturePath) {
        String fullPath = lowRes ? "textures/entity/horse/16x/" + texturePath : "textures/entity/horse/" + texturePath;
        return new ResourceLocation(MoCConstants.MOD_ID, fullPath);
    }

    // TODO: Document all horse types then document them here like what was done in MoCRenderHorseMob
    @Override
    protected ResourceLocation getEntityTexture(MoCEntityHorse entity) {
        boolean lowRes = MoCreatures.proxy.lowResolutionTextures;

        // Essence transformation
        if (entity.transformCounter != 0 && entity.transformType != 0) {
            String newText;
            switch (entity.transformType) {
                case 24:
                    newText = (entity.world != null && MoCreatures.proxy.getAnimateTextures() && !MoCreatures.proxy.lowResolutionTextures) ? "horse_undead_unicorn_animated_0.png" : "horse_undead_unicorn_0.png";
                    break;
                case 25:
                    newText = (MoCreatures.proxy.getAnimateTextures() && !MoCreatures.proxy.lowResolutionTextures) ? "horse_undead_pegasus_animated_0.png" : "horse_undead_pegasus_0.png";
                    break;
                case 32:
                    newText = "horsebat.png";
                    break;
                case 36:
                    newText = "horseunicorn.png";
                    break;
                case 38:
                    newText = (MoCreatures.proxy.getAnimateTextures() && !MoCreatures.proxy.lowResolutionTextures) ? "horse_nightmare_animated.png" : "horse_nightmare.png";
                    break;
                case 39:
                    newText = "horsepegasus.png";
                    break;
                case 40:
                    newText = "horsedarkpegasus.png";
                    break;
                case 48:
                    newText = "horsefairyyellow.png";
                    break;
                case 49:
                    newText = "horsefairypurple.png";
                    break;
                case 50:
                    newText = "horsefairywhite.png";
                    break;
                case 51:
                    newText = "horsefairyblue.png";
                    break;
                case 52:
                    newText = "horsefairypink.png";
                    break;
                case 53:
                    newText = "horsefairylightgreen.png";
                    break;
                case 54:
                    newText = "horsefairyblack.png";
                    break;
                case 55:
                    newText = "horsefairyred.png";
                    break;
                case 56:
                    newText = "horsefairydarkblue.png";
                    break;
                case 57:
                    newText = "horsefairycyan.png";
                    break;
                case 58:
                    newText = "horsefairygreen.png";
                    break;
                case 59:
                    newText = "horsefairyorange.png";
                    break;
                default:
                    newText = (MoCreatures.proxy.getAnimateTextures() && !MoCreatures.proxy.lowResolutionTextures) ? "horse_undead_animated_0.png" : "horse_undead_0.png";
                    break;
            }

            // Flashing effect during transformation
            if (entity.transformCounter > 75 && entity.transformCounter % 4 == 0) {
                return getHorseTexture(lowRes, newText);
            }
        }

        String tempTexture;
        switch (entity.getType()) {
            case 1:
                tempTexture = "horsewhite.png";
                break;
            case 2:
                tempTexture = "horsecreamy.png";
                break;
            case 3:
                tempTexture = "horsebrown.png";
                break;
            case 4:
                tempTexture = "horsedarkbrown.png";
                break;
            case 5:
                tempTexture = "horseblack.png";
                break;
            case 6:
                tempTexture = "horsebrightcreamy.png";
                break;
            case 7:
                tempTexture = "horsespeckled.png";
                break;
            case 8:
                tempTexture = "horsepalebrown.png";
                break;
            case 9:
                tempTexture = "horsegrey.png";
                break;
            case 11:
                tempTexture = "horsepinto.png";
                break;
            case 12:
                tempTexture = "horsebrightpinto.png";
                break;
            case 13:
                tempTexture = "horsepalespeckles.png";
                break;
            case 16:
                tempTexture = "horsespotted.png";
                break;
            case 17:
                tempTexture = "horsecow.png";
                break;
            case 21:
                tempTexture = "horseghost.png";
                break;
            case 22:
                tempTexture = "horseghostb.png";
                break;
            case 23:
            case 24:
            case 25: {
                String baseTex = "horse_undead";
                if (entity.getType() == 24) {
                    baseTex = "horse_undead_unicorn";
                } else if (entity.getType() == 25) {
                    baseTex = "horse_undead_pegasus";
                }

                int decayStage = entity.getAge() / 100;
                if (decayStage > 3) decayStage = 3;
                if (decayStage < 0) decayStage = 0;

                String resolvedTex;
                if (lowRes) {
                    resolvedTex = baseTex + "_" + decayStage + ".png";
                } else {
                    resolvedTex = (MoCreatures.proxy.getAnimateTextures() && entity.world != null) ? baseTex + "_animated_" + decayStage + ".png" : baseTex + "_" + decayStage + ".png";
                }

                return getHorseTexture(lowRes, resolvedTex);
            }
            case 26:
                tempTexture = "horseskeleton.png";
                break;
            case 27:
                tempTexture = "horseunicornskeleton.png";
                break;
            case 28:
                tempTexture = "horsepegasusskeleton.png";
                break;
            case 32:
                tempTexture = "horsebat.png";
                break;
            case 36:
                tempTexture = "horseunicorn.png";
                break;
            case 38:
                if (!MoCreatures.proxy.getAnimateTextures() || entity.world == null) {
                    String nmTex = "horse_nightmare.png";
                    return getHorseTexture(lowRes, nmTex);
                }
                String nmAnimTex = "horse_nightmare_animated.png";
                return getHorseTexture(lowRes, nmAnimTex);
            case 39:
                tempTexture = "horsepegasus.png";
                break;
            case 40:
                tempTexture = "horsedarkpegasus.png";
                break;
            /*
             * case 44: tempTexture = "horsefairydarkblue.png"; break; case 45:
             * tempTexture = "horsefairydarkblue.png"; break; case 46:
             * tempTexture = "horsefairydarkblue.png"; break; case 47:
             * tempTexture = "horsefairydarkblue.png"; break;
             */
            case 48:
                tempTexture = "horsefairyyellow.png";
                break;
            case 49:
                tempTexture = "horsefairypurple.png";
                break;
            case 50:
                tempTexture = "horsefairywhite.png";
                break;
            case 51:
                tempTexture = "horsefairyblue.png";
                break;
            case 52:
                tempTexture = "horsefairypink.png";
                break;
            case 53:
                tempTexture = "horsefairylightgreen.png";
                break;
            case 54:
                tempTexture = "horsefairyblack.png";
                break;
            case 55:
                tempTexture = "horsefairyred.png";
                break;
            case 56:
                tempTexture = "horsefairydarkblue.png";
                break;
            case 57:
                tempTexture = "horsefairycyan.png";
                break;
            case 58:
                tempTexture = "horsefairygreen.png";
                break;
            case 59:
                tempTexture = "horsefairyorange.png";
                break;
            case 60:
                tempTexture = "horsezebra.png";
                break;
            case 61:
                tempTexture = "horsezorse.png";
                break;
            case 65:
                tempTexture = "horsedonkey.png";
                break;
            case 66:
                tempTexture = "horsemule.png";
                break;
            case 67:
                tempTexture = "horsezonky.png";
                break;
            default:
                tempTexture = "horsebug.png";
        }

        if ((entity.isArmored() || entity.isMagicHorse()) && entity.getArmorType() > 0) {
            String armorTex = "";
            if (entity.getArmorType() == 1) armorTex = "metal.png";
            if (entity.getArmorType() == 2) armorTex = "gold.png";
            if (entity.getArmorType() == 3) armorTex = "diamond.png";
            if (entity.getArmorType() == 4) armorTex = "crystaline.png";
            String combinedArmor = tempTexture.replace(".png", armorTex);
            return getHorseTexture(lowRes, combinedArmor);
        }

        return getHorseTexture(lowRes, tempTexture);
    }

    protected void adjustHeight(MoCEntityHorse entity, float FHeight) {
        GlStateManager.translate(0.0F, FHeight, 0.0F);
    }

    @Override
    protected void preRenderCallback(MoCEntityHorse entity, float f) {
        if (!entity.getIsAdult() || entity.getType() > 64) {
            stretch(entity);
        }
        if (entity.getIsGhost()) {
            adjustHeight(entity, -0.3F + (entity.tFloat() / 5F));
        }
        super.preRenderCallback(entity, f);
    }

    protected void stretch(MoCEntityHorse entity) {
        float sizeFactor = entity.getAge() * 0.01F;
        if (entity.getIsAdult()) {
            sizeFactor = 1.0F;
        }
        if (entity.getType() > 64) { // donkey
            sizeFactor *= 0.9F;
        }
        GlStateManager.scale(sizeFactor, sizeFactor, sizeFactor);
    }

    @Override
    protected void renderModel(MoCEntityHorse entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        boolean isAnimated = entity.world != null && MoCreatures.proxy.getAnimateTextures();
        boolean isLowResolution = MoCreatures.proxy.lowResolutionTextures;
        int frameCount = 1;
        int ticksPerFrame = 2;

        if (isAnimated) {
            if (entity.isNightmare()) {
                frameCount = 5;
            } else if (entity.isUndead() && entity.getType() < 26 && !isLowResolution) {
                if (entity.getType() == 24) {
                    frameCount = 6; // Undead Unicorn has 6 frames
                    ticksPerFrame = 4;
                } else {
                    frameCount = 7; // Other undead horses have 7 frames
                    ticksPerFrame = 4;
                }
            }
        }

        if (isAnimated && frameCount > 1) {
            GlStateManager.matrixMode(GL11.GL_TEXTURE);
            GlStateManager.pushMatrix();
            GlStateManager.loadIdentity();
            int currentFrame = ((int) ageInTicks / ticksPerFrame) % frameCount;
            GlStateManager.translate(0.0F, (float) currentFrame / frameCount, 0.0F);
            GlStateManager.scale(1.0F, 1.0F / frameCount, 1.0F);
            GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        }

        super.renderModel(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);

        if (isAnimated && frameCount > 1) {
            GlStateManager.matrixMode(GL11.GL_TEXTURE);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        }
    }
}
