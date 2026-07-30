/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.hostile.MoCEntityScorpion;
import drzhark.mocreatures.entity.hostile.MoCEntityWraith;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderFlameWraith extends MoCRenderWraith {
    private static final ResourceLocation TEXTURE = MoCreatures.proxy.alphaWraithEyes
            ? new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/wraith/alpha/wraith_flame_alpha.png")
            : new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/wraith/wraith_flame.png");

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityWraith entity) {
        return TEXTURE;
    }
}
