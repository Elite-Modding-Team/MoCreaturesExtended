/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.client.model.MoCModelOgre;
import drzhark.mocreatures.entity.hostile.MoCEntityCaveOgre;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderCaveOgre extends MoCRenderMoC<MoCEntityCaveOgre> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/ogre/ogre_cave.png");

    public MoCRenderCaveOgre() {
        super(new MoCModelOgre(), 0.6F);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityCaveOgre entity) {
        return TEXTURE;
    }
}
