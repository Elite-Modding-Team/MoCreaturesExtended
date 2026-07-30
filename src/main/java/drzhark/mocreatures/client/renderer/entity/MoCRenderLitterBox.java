/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.proxy.MoCProxyClient;
import drzhark.mocreatures.client.model.MoCModelLitterBox;
import drzhark.mocreatures.entity.item.MoCEntityLitterBox;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderLitterBox extends RenderLiving<MoCEntityLitterBox> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MoCConstants.MOD_ID, "textures/models/litter_box.png");

    public MoCModelLitterBox litterbox;

    public MoCRenderLitterBox(MoCModelLitterBox model, float f) {
        super(MoCProxyClient.mc.getRenderManager(), model, f);
        this.litterbox = model;
    }

    @Override
    protected void preRenderCallback(MoCEntityLitterBox entity, float f) {
        this.litterbox.usedlitter = entity.getUsedLitter();
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityLitterBox entity) {
        return TEXTURE;
    }
}
