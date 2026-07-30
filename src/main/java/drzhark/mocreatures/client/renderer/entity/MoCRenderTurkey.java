/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.model.MoCModelTurkey;
import drzhark.mocreatures.entity.hostile.MoCEntityScorpion;
import drzhark.mocreatures.entity.passive.MoCEntityTurkey;
import net.minecraft.client.model.ModelBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderTurkey extends MoCRenderMoC<MoCEntityTurkey> {
    public MoCRenderTurkey() {
        super(new MoCModelTurkey(), 0.4F);
    }

    private ResourceLocation getTurkeyTexture(boolean lowRes, String texturePath) {
        String fullPath = "textures/entity/turkey/" + texturePath;
        return new ResourceLocation(MoCConstants.MOD_ID, fullPath);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityTurkey entity) {
        boolean lowRes = MoCreatures.proxy.lowResolutionTextures;
        String tempTexture;

        if (entity.getType() == 1 && !entity.isChild()) {
            tempTexture = "turkey_male.png";
        } else {
            tempTexture = "turkey_female.png";
        }

        return getTurkeyTexture(lowRes, tempTexture);
    }
}
