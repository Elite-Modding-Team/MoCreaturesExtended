/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.model.MoCModelKomodo;
import drzhark.mocreatures.client.model.MoCModelMaggot;
import drzhark.mocreatures.entity.ambient.MoCEntityMaggot;
import drzhark.mocreatures.entity.hunter.MoCEntityKomodo;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderMaggot extends MoCRenderMoC<MoCEntityMaggot> {
    private static final ResourceLocation TEXTURE = MoCreatures.proxy.lowResolutionTextures
            ? new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/maggot/16x/maggot.png")
            : new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/maggot/maggot.png");

    public MoCRenderMaggot() {
        super(new MoCModelMaggot(), 0.0F);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityMaggot entity) {
        return TEXTURE;
    }
}
