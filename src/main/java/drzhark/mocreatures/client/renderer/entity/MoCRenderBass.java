/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.model.MoCModelMediumFish;
import drzhark.mocreatures.entity.aquatic.MoCEntityBass;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderBass extends MoCRenderMoC<MoCEntityBass> {
    private static final ResourceLocation TEXTURE = MoCreatures.proxy.lowResolutionTextures
            ? new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/medium_fish/16x/medium_fish_bass.png")
            : new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/medium_fish/medium_fish_bass.png");

    public MoCRenderBass() {
        super(new MoCModelMediumFish(), 0.2F);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityBass entity) {
        return TEXTURE;
    }
}
