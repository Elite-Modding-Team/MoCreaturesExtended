/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.model.MoCModelFox;
import drzhark.mocreatures.entity.hunter.MoCEntityFox;
import drzhark.mocreatures.entity.passive.MoCEntityTurkey;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderFox extends MoCRenderMoC<MoCEntityFox> {
    public MoCRenderFox() {
        super(new MoCModelFox(), 0.5F);
    }

    private ResourceLocation getFoxTexture(boolean lowRes, String texturePath) {
        String fullPath = lowRes ? "textures/entity/fox/16x/" + texturePath : "textures/entity/fox/" + texturePath;
        return new ResourceLocation(MoCConstants.MOD_ID, fullPath);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityFox entity) {
        boolean lowRes = MoCreatures.proxy.lowResolutionTextures;
        String tempTexture = "fox.png";

        if (!entity.getIsAdult() && entity.getType() != 2) {
            tempTexture = "fox_cub.png";
        }
        if (entity.getType() == 2) {
            tempTexture = "fox_snow.png";
        }
        return getFoxTexture(lowRes, tempTexture);
    }
}
