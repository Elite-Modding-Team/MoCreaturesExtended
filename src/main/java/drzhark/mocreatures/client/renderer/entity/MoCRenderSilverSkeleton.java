/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.client.model.MoCModelSilverSkeleton;
import drzhark.mocreatures.entity.hostile.MoCEntitySilverSkeleton;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderSilverSkeleton extends MoCRenderMoC<MoCEntitySilverSkeleton> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/silver_skeleton/silver_skeleton.png");

    public MoCRenderSilverSkeleton() {
        super(new MoCModelSilverSkeleton(), 0.6F);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntitySilverSkeleton entity) {
        return TEXTURE;
    }
}
