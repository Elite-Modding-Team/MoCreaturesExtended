/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.client.model.MoCModelSmallFish;
import drzhark.mocreatures.entity.aquatic.MoCEntityAngler;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderAnglerfish extends MoCRenderMoC<MoCEntityAngler> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/small_fish/small_fish_anglerfish.png");

    public MoCRenderAnglerfish() {
        super(new MoCModelSmallFish(), 0.1F);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityAngler entity) {
        return TEXTURE;
    }
}
