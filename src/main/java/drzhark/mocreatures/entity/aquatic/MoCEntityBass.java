/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.aquatic;

import drzhark.mocreatures.init.MoCLootTables;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoCEntityBass extends MoCEntityMediumFish {
    public MoCEntityBass(World world) {
        super(world);
        this.setType(3);
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return MoCLootTables.BASS;
    }
}
