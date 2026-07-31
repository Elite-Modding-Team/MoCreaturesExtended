/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.client.model.MoCModelCrab;
import drzhark.mocreatures.entity.ambient.MoCEntityCrab;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderCrab extends MoCRenderMoC<MoCEntityCrab> {
    private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/crab/crab_red.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/crab/crab_blue.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/crab/crab_spotted.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/crab/crab_green.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/crab/crab_russet.png")
    };

    public MoCRenderCrab() {
        super(new MoCModelCrab(), 0.2F);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityCrab entity) {
        int type = entity.getType();
        if (type < 0 || type >= TEXTURES.length) {
            type = 0;
        }
        return TEXTURES[type];
    }
}
