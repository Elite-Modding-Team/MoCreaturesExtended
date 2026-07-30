/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.model.MoCModelElephant;
import drzhark.mocreatures.client.model.MoCModelFox;
import drzhark.mocreatures.client.model.MoCModelJellyFish;
import drzhark.mocreatures.entity.aquatic.MoCEntityJellyFish;
import drzhark.mocreatures.entity.hunter.MoCEntityFox;
import drzhark.mocreatures.entity.neutral.MoCEntityElephant;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderElephant extends MoCRenderMoC<MoCEntityElephant> {
    private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/elephant/elephant_african.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/elephant/elephant_asian.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/elephant/mammoth_woolly.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/elephant/mammoth_songhua.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/elephant/elephant_asian_decorated.png")
    };

    private static final ResourceLocation[] TEXTURES_LOW = new ResourceLocation[] {
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/elephant/16x/elephant_african.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/elephant/16x/elephant_asian.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/elephant/16x/mammoth_woolly.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/elephant/16x/mammoth_songhua.png"),
            new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/elephant/16x/elephant_asian_decorated.png")
    };

    public MoCRenderElephant() {
        super(new MoCModelElephant(), 0.7F);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityElephant entity) {
        int type = entity.getType();
        if (MoCreatures.proxy.lowResolutionTextures) {
            return TEXTURES_LOW[type - 1];
        }
        return TEXTURES[type - 1];
    }
}
