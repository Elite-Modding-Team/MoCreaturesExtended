/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.model.MoCModelBoar;
import drzhark.mocreatures.client.model.MoCModelJellyFish;
import drzhark.mocreatures.entity.aquatic.MoCEntityJellyFish;
import drzhark.mocreatures.entity.hunter.MoCEntityFox;
import drzhark.mocreatures.entity.neutral.MoCEntityBoar;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderBoar extends MoCRenderMoC<MoCEntityBoar> {
    public MoCRenderBoar() {
        super(new MoCModelBoar(), 0.5F);
    }

    private ResourceLocation getBoarTexture(boolean lowRes, String texturePath) {
        String fullPath = "textures/entity/boar/" + texturePath;
        return new ResourceLocation(MoCConstants.MOD_ID, fullPath);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityBoar entity) {
        boolean lowRes = MoCreatures.proxy.lowResolutionTextures;
        String tempTexture = "boar.png";

        if (!entity.getIsAdult()) {
            tempTexture = "boar_baby.png";
        }

        return getBoarTexture(lowRes, tempTexture);
    }
}
