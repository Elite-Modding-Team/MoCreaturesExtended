/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.model.MoCModelWyvern;
import drzhark.mocreatures.entity.neutral.MoCEntityWyvern;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderWyvern extends MoCRenderMoC<MoCEntityWyvern> {
    public MoCRenderWyvern() {
        super(new MoCModelWyvern(), 0.5F);
    }

    private ResourceLocation getWyvernTexture(boolean lowRes, String texturePath) {
        String fullPath = lowRes ? "textures/entity/wyvern/16x/" + texturePath : "textures/entity/wyvern/" + texturePath;
        return new ResourceLocation(MoCConstants.MOD_ID, fullPath);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityWyvern entity) {
        boolean lowRes = MoCreatures.proxy.lowResolutionTextures;

        // Essence transformation
        if (entity.transformCounter != 0 && entity.transformType > 5) {
            String newText;
            switch (entity.transformType) {
                case 6:
                    newText = "wyvern_mother_undead.png";
                    break;
                case 7:
                    newText = "wyvern_mother_light.png";
                    break;
                case 8:
                default:
                    newText = "wyvern_mother_dark.png";
                    break;
            }

            // Flashing effect during transformation
            if ((entity.transformCounter % 5) == 0) {
                return getWyvernTexture(lowRes, newText);
            }
            if (entity.transformCounter > 50 && (entity.transformCounter % 3) == 0) {
                return getWyvernTexture(lowRes, newText);
            }
            if (entity.transformCounter > 75 && (entity.transformCounter % 4) == 0) {
                return getWyvernTexture(lowRes, newText);
            }
        }

        /**
         * 1-4 regular wyverns
         * 5 mother wyvern
         * 6 undead
         * 7 light
         * 8 darkness
         * 9-12 extra wyverns
         */
        String tempTexture;
        switch (entity.getType()) {
            case 1:
                tempTexture = "wyvern_jungle.png";
                break;
            case 2:
                tempTexture = "wyvern_swamp.png";
                break;
            case 3:
                tempTexture = "wyvern_sand.png";
                break;
            case 5:
                tempTexture = "wyvern_mother.png";
                break;
            case 6:
                tempTexture = "wyvern_mother_undead.png";
                break;
            case 7:
                tempTexture = "wyvern_mother_light.png";
                break;
            case 8:
                tempTexture = "wyvern_mother_dark.png";
                break;
            case 9:
                tempTexture = "wyvern_arctic.png";
                break;
            case 10:
                tempTexture = "wyvern_cave.png";
                break;
            case 11:
                tempTexture = "wyvern_mountain.png";
                break;
            case 12:
                tempTexture = "wyvern_sea.png";
                break;
            default:
                tempTexture = "wyvern_sun.png";
                break;
        }

        return getWyvernTexture(lowRes, tempTexture);
    }
}
