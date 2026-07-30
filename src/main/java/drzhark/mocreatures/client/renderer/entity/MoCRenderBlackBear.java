/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.hunter.MoCEntityBear;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderBlackBear extends MoCRenderBear {
    private static final ResourceLocation TEXTURE = MoCreatures.proxy.lowResolutionTextures
            ? new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/bear/16x/bear_black.png")
            : new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/bear/bear_black.png");

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityBear entity) {
        return TEXTURE;
    }
}
