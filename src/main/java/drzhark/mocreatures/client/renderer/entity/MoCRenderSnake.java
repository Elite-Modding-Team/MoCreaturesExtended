/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.hunter.MoCEntitySnake;
import net.minecraft.block.material.Material;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderSnake extends MoCRenderMoC<MoCEntitySnake> {
    private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/snake/snake_wolf.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/snake/snake_orange.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/snake/snake_green_bright.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/snake/snake_coral.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/snake/snake_cobra.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/snake/snake_rattle.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/snake/snake_python.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/snake/snake_green_dark.png")
    };

    private static final ResourceLocation[] TEXTURES_LOW = new ResourceLocation[] {
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/snake/16x/snake_wolf.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/snake/16x/snake_orange.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/snake/16x/snake_green_bright.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/snake/16x/snake_coral.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/snake/16x/snake_cobra.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/snake/16x/snake_rattle.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/snake/16x/snake_python.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/snake/16x/snake_green_dark.png")
    };

    public MoCRenderSnake(ModelBase model, float f) {
        super(model, 0.0F);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntitySnake entity) {
        int type = entity.getType();
        if (MoCreatures.proxy.lowResolutionTextures) {
            return TEXTURES_LOW[type - 1];
        }
        return TEXTURES[type - 1];
    }

    protected void adjustHeight(MoCEntitySnake entity, float FHeight) {
        GlStateManager.translate(0.0F, FHeight, 0.0F);
    }

    @Override
    protected void preRenderCallback(MoCEntitySnake entity, float f) {
        stretch(entity);

        /*
         * if(mod_mocreatures.mc.isMultiplayerWorld() &&
         * (entity.pickedUp())) { GlStateManager.translate(0.0F, 1.4F, 0.0F); }
         */

        if (entity.pickedUp())// && entity.getSizeF() < 0.6F)
        {
            float xOff = (entity.getSizeF() - 1.0F);
            if (xOff > 0.0F) {
                xOff = 0.0F;
            }
            if (entity.world.isRemote) {
                GlStateManager.translate(xOff, 0.0F, 0F);
            } else {
                GlStateManager.translate(xOff, 0F, 0.0F);
                //-0.5 puts it in the right shoulder
            }
            /*
             * //if(small) //works for small snakes GlStateManager.rotate(20F, 1F, 0F,
             * 0F); if(mod_mocreatures.mc.isMultiplayerWorld()) {
             * GlStateManager.translate(-0.5F, 1.4F, 0F); } else {
             * GlStateManager.translate(0.7F, 0F, 1.2F); }
             */
        }

        if (entity.isInsideOfMaterial(Material.WATER)) {
            adjustHeight(entity, -0.25F);
        }

        super.preRenderCallback(entity, f);
    }

    protected void stretch(MoCEntitySnake entity) {
        float f = entity.getSizeF();
        GlStateManager.scale(f, f, f);
    }

    /*
     * @Override protected void preRenderCallback(EntityLiving entityliving,
     * float f) { MoCEntitySnake entity = (MoCEntitySnake) entityliving;
     * //tempSnake.textPos = entity.type - 1; if (entity.type <4) {
     * tempSnake.textPos = 0; }else { tempSnake.textPos = 1; }
     * super.preRenderCallback(entityliving, f); } private MoCModelSnake
     * tempSnake;
     */
}
