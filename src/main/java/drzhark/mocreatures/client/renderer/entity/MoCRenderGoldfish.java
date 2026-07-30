/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.client.model.MoCModelSmallFish;
import drzhark.mocreatures.entity.aquatic.MoCEntityGoldFish;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderGoldfish extends MoCRenderMoC<MoCEntityGoldFish> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/small_fish/small_fish_goldfish.png");

    public MoCRenderGoldfish() {
        super(new MoCModelSmallFish(), 0.1F);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityGoldFish entity) {
        return TEXTURE;
    }
}
