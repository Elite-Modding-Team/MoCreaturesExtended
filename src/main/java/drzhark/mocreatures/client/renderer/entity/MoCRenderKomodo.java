/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.model.MoCModelKomodo;
import drzhark.mocreatures.client.model.MoCModelRaccoon;
import drzhark.mocreatures.entity.hunter.MoCEntityKomodo;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderKomodo extends MoCRenderMoC<MoCEntityKomodo> {
    private static final ResourceLocation TEXTURE = MoCreatures.proxy.lowResolutionTextures
            ? new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/komodo_dragon/16x/komodo_dragon.png")
            : new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/komodo_dragon/komodo_dragon.png");

    public MoCRenderKomodo() {
        super(new MoCModelKomodo(), 0.3F);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityKomodo entity) {
        return TEXTURE;
    }
}
