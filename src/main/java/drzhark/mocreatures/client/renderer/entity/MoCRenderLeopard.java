/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.client.model.MoCModelBigCat;
import drzhark.mocreatures.entity.hunter.MoCEntityLeopard;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderLeopard extends MoCRenderMoC<MoCEntityLeopard> {
    private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/big_cat/big_cat_leopard.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/big_cat/big_cat_snow_leopard.png")
    };

    public MoCRenderLeopard() {
        super(new MoCModelBigCat(), 0.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityLeopard entity) {
        int type = entity.getType();
        return TEXTURES[type - 1];
    }
}
