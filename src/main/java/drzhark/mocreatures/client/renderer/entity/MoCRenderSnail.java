/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.client.model.MoCModelSnail;
import drzhark.mocreatures.entity.ambient.MoCEntitySnail;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderSnail extends MoCRenderMoC<MoCEntitySnail> {
    private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/snail/snail_brown.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/snail/snail_green.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/snail/snail_yellow.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/snail/snail_red.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/snail/slug_golden.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/snail/slug_black.png")
    };

    public MoCRenderSnail() {
        super(new MoCModelSnail(), 0.0F);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntitySnail entity) {
        int type = entity.getType();
        if (type < 0 || type >= TEXTURES.length) {
            type = 0;
        }
        return TEXTURES[type];
    }
}
