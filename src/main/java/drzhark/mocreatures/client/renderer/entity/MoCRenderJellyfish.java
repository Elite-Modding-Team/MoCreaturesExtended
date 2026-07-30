/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.model.MoCModelJellyFish;
import drzhark.mocreatures.client.model.MoCModelTurkey;
import drzhark.mocreatures.entity.aquatic.MoCEntityJellyFish;
import drzhark.mocreatures.entity.passive.MoCEntityTurkey;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderJellyfish extends MoCRenderMoC<MoCEntityJellyFish> {
    private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/jellyfish/jellyfish_orange_dark.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/jellyfish/jellyfish_purple_gray.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/jellyfish/jellyfish_blue_dark.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/jellyfish/jellyfish_green.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/jellyfish/jellyfish_orange_red.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/jellyfish/jellyfish_orange_yellow.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/jellyfish/jellyfish_blue_speckled.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/jellyfish/jellyfish_white.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/jellyfish/jellyfish_purple.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/jellyfish/jellyfish_orange_light.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/jellyfish/jellyfish_red.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/jellyfish/jellyfish_blue_light.png")
    };

    public MoCRenderJellyfish() {
        super(new MoCModelJellyFish(), 0.1F);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityJellyFish entity) {
        int type = entity.getType();
        return TEXTURES[type - 1];
    }
}
