/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoCItemTurtleSoup extends MoCItemFood {

    public MoCItemTurtleSoup(int j) {
        super(j);
        this.maxStackSize = 1;
    }

    public MoCItemTurtleSoup(int j, float f, boolean flag) {
        super(j, f, flag);
        this.maxStackSize = 1;
    }

    @Override
    @Nullable
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
        super.onItemUseFinish(stack, worldIn, entityLiving);
        return new ItemStack(Items.BOWL);
    }
}
