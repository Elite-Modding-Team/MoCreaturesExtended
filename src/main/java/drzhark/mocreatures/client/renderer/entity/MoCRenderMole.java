/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.client.model.MoCModelMole;
import drzhark.mocreatures.entity.passive.MoCEntityMole;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderMole extends MoCRenderMoC<MoCEntityMole> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/mole/mole.png");

    public MoCRenderMole() {
        super(new MoCModelMole(), 0.0F);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityMole entity) {
        return TEXTURE;
    }
}
