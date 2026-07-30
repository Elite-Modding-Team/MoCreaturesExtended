/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.model.MoCModelRaccoon;
import drzhark.mocreatures.entity.hunter.MoCEntityRaccoon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderRaccoon extends MoCRenderMoC<MoCEntityRaccoon> {
    private static final ResourceLocation TEXTURE = MoCreatures.proxy.lowResolutionTextures
            ? new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/raccoon/16x/raccoon.png")
            : new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/raccoon/raccoon.png");

    public MoCRenderRaccoon() {
        super(new MoCModelRaccoon(), 0.4F);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityRaccoon entity) {
        return TEXTURE;
    }
}
