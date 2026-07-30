/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.model.MoCModelManticorePet;
import drzhark.mocreatures.entity.hunter.MoCEntityManticorePet;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

// TODO: Make it comaptible with essences
@SideOnly(Side.CLIENT)
public class MoCRenderPetManticore extends MoCRenderMoC<MoCEntityManticorePet> {
    public MoCRenderPetManticore() {
        super(new MoCModelManticorePet(), 0.7F);
    }

    private ResourceLocation getManticoreTexture(boolean lowRes, String texturePath) {
        String fullPath = "textures/entity/manticore/" + texturePath;
        return new ResourceLocation(MoCConstants.MOD_ID, fullPath);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityManticorePet entity) {
        boolean lowRes = MoCreatures.proxy.lowResolutionTextures;

        // Essence transformation
        /*if (entity.transformCounter != 0 && entity.transformType != 0) {
            String newText;
            switch (entity.transformType) {
                case 2: // Dark Manticore
                    newText = "manticore_dark.png";
                    break;
                case 3: // Frost Manticore
                    newText = "manticore_frost.png";
                    break;
                case 4: // Toxic Manticore
                    newText = "manticore_toxic.png";
                    break;
                case 5: // Plain Manticore
                    newText = "manticore_plain.png";
                    break;
                default: // Fire Manticore
                    newText = "manticore_fire.png";
                    break;
            }

            // Flashing effect during transformation
            if (entity.transformCounter > 60 && (entity.transformCounter % 3) == 0) {
                return getScorpionTexture(lowRes, newText);
            }
        }*/

        String tempTexture;
        switch (entity.getType()) {
            case 2:
                tempTexture = "manticore_dark.png";
                break;
            case 3:
                tempTexture = "manticore_frost.png";
                break;
            case 4:
                tempTexture = "manticore_toxic.png";
                break;
            case 5:
                tempTexture = "manticore_plain.png";
                break;
            default:
                tempTexture = "manticore_fire.png";
                break;
        }

        return getManticoreTexture(lowRes, tempTexture);
    }
}
