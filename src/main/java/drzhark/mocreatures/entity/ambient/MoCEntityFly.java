/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.ambient;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.entity.MoCEntityInsect;
import drzhark.mocreatures.init.MoCLootTables;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoCEntityFly extends MoCEntityInsect {
    private int soundCount;// = 50;

    public MoCEntityFly(World world) {
        super(world);
    }

    @Override
    public boolean isAttractedToLight() {
        return true;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (!this.world.isRemote) {
            if (getIsFlying() && --this.soundCount == -1) {
                EntityPlayer ep = this.world.getClosestPlayerToEntity(this, 5D);
                if (ep != null) {
                    MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_FLY_AMBIENT);
                    this.soundCount = 55;
                }
            }
        }
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_FLY_HURT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCSoundEvents.ENTITY_FLY_HURT;
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return MoCLootTables.FLY;
    }

    @Override
    public boolean isMyFavoriteFood(ItemStack stack) {
        return !stack.isEmpty() && stack.getItem() == Items.ROTTEN_FLESH;
    }

    @Override
    public boolean isFlyer() {
        return true;
    }

    @Override
    public float getAIMoveSpeed() {
        if (getIsFlying()) {
            return 0.2F;
        }
        return 0.12F;
    }
    
    @Override
    public int getMaxSpawnedInChunk() {
        return 4;
    }
}
