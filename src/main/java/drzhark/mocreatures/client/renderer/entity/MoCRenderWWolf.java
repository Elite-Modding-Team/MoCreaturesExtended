/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.proxy.MoCProxyClient;
import drzhark.mocreatures.entity.hostile.MoCEntityWWolf;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderWWolf extends RenderLiving<MoCEntityWWolf> {
    private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/wild_wolf/wild_wolf_classic.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/wild_wolf/wild_wolf_black.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/wild_wolf/wild_wolf_timber.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/wild_wolf/wild_wolf_dark.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/wild_wolf/wild_wolf_bright.png")
    };

    public MoCRenderWWolf(ModelBase model, float f) {
        super(MoCProxyClient.mc.getRenderManager(), model, f);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityWWolf entity) {
        int type = entity.getType();
        return TEXTURES[type - 1];
    }
}
