/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.client.model.MoCModelEnt;
import drzhark.mocreatures.entity.neutral.MoCEntityEnt;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderEnt extends MoCRenderMoC<MoCEntityEnt> {
    private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/ent/ent_oak.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/ent/ent_birch.png")
    };

    public MoCRenderEnt() {
        super(new MoCModelEnt(), 0.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityEnt entity) {
        int type = entity.getType();
        return TEXTURES[type - 1];
    }
}
