/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.hostile.MoCEntityScorpion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderEarthScorpion extends MoCRenderScorpion {
    private static final ResourceLocation TEXTURE = MoCreatures.proxy.lowResolutionTextures
            ? new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/scorpion/16x/scorpion_dirt.png")
            : new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/scorpion/scorpion_dirt.png");

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityScorpion entity) {
        return TEXTURE;
    }
}
