/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.aquatic;

import drzhark.mocreatures.init.MoCLootTables;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoCEntitySalmon extends MoCEntityMediumFish {
    public MoCEntitySalmon(World world) {
        super(world);
        this.setType(1);
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return MoCLootTables.SALMON;
    }
}
