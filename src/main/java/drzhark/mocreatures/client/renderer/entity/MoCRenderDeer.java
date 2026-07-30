/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.client.model.MoCModelDeer;
import drzhark.mocreatures.entity.passive.MoCEntityDeer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderDeer extends MoCRenderMoC<MoCEntityDeer> {
    private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/deer/deer_stag.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/deer/deer_doe.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/deer/deer_fawn.png")
    };

    public MoCRenderDeer() {
        super(new MoCModelDeer(), 0.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityDeer entity) {
        int type = entity.getType();
        return TEXTURES[type - 1];
    }
}
