/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.entity.ambient.MoCEntityButterfly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderButterfly extends MoCRenderInsect<MoCEntityButterfly> {
    private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/butterfly/butterfly_pieris_rapae.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/butterfly/butterfly_agalais_urticae.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/butterfly/butterfly_argyreus_hyperbius.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/butterfly/butterfly_athyma_nefte.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/butterfly/butterfly_catopsilia_pomona.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/butterfly/butterfly_morpho_peleides.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/butterfly/butterfly_vanessa_atalanta.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/butterfly/moth_camptogramma_bilineata.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/butterfly/moth_idia_aemula.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/butterfly/moth_thyatira_batis.png")
    };

    public MoCRenderButterfly(ModelBase model) {
        super(model);

    }

    @Override
    protected void preRenderCallback(MoCEntityButterfly entity, float par2) {
        if (entity.isOnAir() || !entity.onGround) {
            adjustHeight(entity, entity.tFloat());
        }
        if (entity.climbing()) {
            rotateAnimal(entity);
        }
        stretch(entity);
    }

    protected void adjustHeight(MoCEntityButterfly entity, float FHeight) {
        GlStateManager.translate(0.0F, FHeight, 0.0F);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityButterfly entity) {
        int type = entity.getType();
        if (type < 0 || type >= TEXTURES.length) {
            type = 0;
        }
        return TEXTURES[type];
    }
}
