/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.client.model.MoCModelMiniGolem;
import drzhark.mocreatures.entity.hostile.MoCEntityMiniGolem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderMiniBlockGolem extends MoCRenderMoC<MoCEntityMiniGolem> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/mini_block_golem/mini_block_golem.png");

    public MoCRenderMiniBlockGolem() {
        super(new MoCModelMiniGolem(), 0.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityMiniGolem entity) {
        return TEXTURE;
    }
}
