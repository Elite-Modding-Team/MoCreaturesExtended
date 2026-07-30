/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.client.model.MoCModelSmallFish;
import drzhark.mocreatures.entity.aquatic.MoCEntityPiranha;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderPiranha extends MoCRenderMoC<MoCEntityPiranha> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/small_fish/small_fish_piranha.png");

    public MoCRenderPiranha() {
        super(new MoCModelSmallFish(), 0.1F);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityPiranha entity) {
        return TEXTURE;
    }
}
