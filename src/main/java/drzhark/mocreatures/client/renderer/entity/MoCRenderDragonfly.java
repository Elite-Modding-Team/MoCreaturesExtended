/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.client.model.MoCModelDragonfly;
import drzhark.mocreatures.client.model.MoCModelSnail;
import drzhark.mocreatures.entity.ambient.MoCEntityDragonfly;
import drzhark.mocreatures.entity.ambient.MoCEntitySnail;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderDragonfly extends MoCRenderMoC<MoCEntityDragonfly> {
    private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/dragonfly/dragonfly_blue.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/dragonfly/dragonfly_green.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/dragonfly/dragonfly_cyan.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/dragonfly/dragonfly_red.png")
    };

    public MoCRenderDragonfly() {
        super(new MoCModelDragonfly(), 0.0F);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityDragonfly entity) {
        int type = entity.getType();
        return TEXTURES[type - 1];
    }
}
