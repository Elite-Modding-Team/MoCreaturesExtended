/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.model.MoCModelElephant;
import drzhark.mocreatures.client.model.MoCModelFox;
import drzhark.mocreatures.client.model.MoCModelJellyFish;
import drzhark.mocreatures.entity.aquatic.MoCEntityJellyFish;
import drzhark.mocreatures.entity.hunter.MoCEntityFox;
import drzhark.mocreatures.entity.neutral.MoCEntityElephant;
import drzhark.mocreatures.entity.neutral.MoCEntityOstrich;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderElephant extends MoCRenderMoC<MoCEntityElephant> {
    public MoCRenderElephant() {
        super(new MoCModelElephant(), 0.7F);
    }

    private ResourceLocation getElephantTexture(boolean lowRes, String texturePath) {
        String fullPath = lowRes ? "textures/entity/elephant/16x/" + texturePath : "textures/entity/elephant/" + texturePath;
        return new ResourceLocation(MoCConstants.MOD_ID, fullPath);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityElephant entity) {
        boolean lowRes = MoCreatures.proxy.lowResolutionTextures;
        String tempTexture;
        switch (entity.getType()) {
            case 1:
                tempTexture = "elephant_african.png";
                break;
            case 2:
                tempTexture = "elephant_asian.png";
                break;
            case 4:
                tempTexture = "mammoth_songhua.png";
                break;
            case 5:
                tempTexture = "elephant_asian_decorated.png";
                break;
            default:
                tempTexture = "mammoth_woolly.png";
                break;
        }

        return getElephantTexture(lowRes, tempTexture);
    }
}
