/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.client.model.MoCModelDuck;
import drzhark.mocreatures.entity.passive.MoCEntityDuck;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderDuck extends MoCRenderMoC<MoCEntityDuck> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/duck/duck.png");

    public MoCRenderDuck() {
        super(new MoCModelDuck(), 0.3F);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityDuck entity) {
        return TEXTURE;
    }
}
