/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.aquatic;

import drzhark.mocreatures.init.MoCLootTables;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoCEntityManderin extends MoCEntitySmallFish {
    public MoCEntityManderin(World world) {
        super(world);
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return MoCLootTables.MANDARINFISH;
    }
}
