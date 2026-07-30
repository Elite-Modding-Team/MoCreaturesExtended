/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.aquatic;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.init.MoCLootTables;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoCEntityAnchovy extends MoCEntitySmallFish {
    private static final ResourceLocation TEXTURE = MoCreatures.proxy.getModelTexture("smallfish_anchovy.png");

    public MoCEntityAnchovy(World world) {
        super(world);
        this.setType(1);
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return MoCLootTables.ANCHOVY;
    }
}
