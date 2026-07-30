/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.neutral.MoCEntityOstrich;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderOstrich extends MoCRenderMoC<MoCEntityOstrich> {
    public MoCRenderOstrich(ModelBase model, float f) {
        super(model, 0.5F);
    }

    private ResourceLocation getOstrichTexture(boolean lowRes, String texturePath) {
        String fullPath = "textures/entity/ostrich/" + texturePath;
        return new ResourceLocation(MoCConstants.MOD_ID, fullPath);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityOstrich entity) {
        boolean lowRes = MoCreatures.proxy.lowResolutionTextures;

        // Essence transformation
        if (entity.transformCounter != 0 && entity.transformType > 4) {
            String newText;
            switch (entity.transformType) {
                case 5:
                    newText = "ostrich_fire.png";
                    break;
                case 6:
                    newText = "ostrich_dark.png";
                    break;
                case 7:
                    newText = "ostrich_undead.png";
                    break;
                case 8:
                    newText = "ostrich_light.png";
                    break;
                default:
                    newText = "ostrich_male.png";
                    break;
            }

            // Flashing effect during transformation
            if ((entity.transformCounter % 5) == 0) {
                return getOstrichTexture(lowRes, newText);
            }
            if (entity.transformCounter > 50 && (entity.transformCounter % 3) == 0) {
                return getOstrichTexture(lowRes, newText);
            }
            if (entity.transformCounter > 75 && (entity.transformCounter % 4) == 0) {
                return getOstrichTexture(lowRes, newText);
            }
        }

        String tempTexture;
        switch (entity.getType()) {
            case 1:
                tempTexture = "ostrich_baby.png";
                break;
            case 2:
                tempTexture = "ostrich_female.png";
                break;
            case 4:
                tempTexture = "ostrich_white.png";
                break;
            case 5:
                tempTexture = "ostrich_fire.png";
                break;
            case 6:
                tempTexture = "ostrich_dark.png";
                break;
            case 7:
                tempTexture = "ostrich_undead.png";
                break;
            case 8:
                tempTexture = "ostrich_light.png";
                break;
            default:
                tempTexture = "ostrich_male.png";
                break;
        }

        return getOstrichTexture(lowRes, tempTexture);
    }

    protected void adjustHeight(MoCEntityOstrich entity, float FHeight) {
        GlStateManager.translate(0.0F, FHeight, 0.0F);
    }

    @Override
    protected void preRenderCallback(MoCEntityOstrich entity, float f) {
        if (entity.getType() == 1) {
            stretch(entity);
        }

        super.preRenderCallback(entity, f);

    }

    protected void stretch(MoCEntityOstrich entity) {

        float f = entity.getAge() * 0.01F;
        GlStateManager.scale(f, f, f);
    }
}
