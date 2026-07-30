/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.ambient;

//import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.entity.MoCEntityInsect;
import drzhark.mocreatures.init.MoCLootTables;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.entity.SharedMonsterAttributes;
//import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoCEntityGrasshopper extends MoCEntityInsect {

    private int jumpCounter;
    //private int soundCounter;

    public MoCEntityGrasshopper(World world) {
        super(world);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(1.0D);
    }

    @Override
    public void selectType() {
        if (getType() == 0) {
            int i = this.rand.nextInt(100);
            if (i <= 50) {
                setType(1);
            } else {
                setType(2);
            }
        }
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!this.world.isRemote) {
            /*if (getIsFlying() || !this.onGround) {
                EntityPlayer ep = this.world.getClosestPlayerToEntity(this, 5D);
                if (ep != null && --this.soundCounter == -1) {
                    MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GRASSHOPPER_FLY);
                    this.soundCounter = 10;
                }
            }*/

            if (this.jumpCounter > 0 && ++this.jumpCounter > 30) {
                this.jumpCounter = 0;
            }
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        if (world.isDaytime()) {
            // TODO: Add grasshopper daytime ambient sound
            return world.rand.nextDouble() <= 0.1D ? MoCSoundEvents.ENTITY_GRASSHOPPER_CHIRP : null;
        } else {
            return world.rand.nextDouble() <= 0.1D ? MoCSoundEvents.ENTITY_GRASSHOPPER_CHIRP : null;
        }
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_GRASSHOPPER_HURT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCSoundEvents.ENTITY_GRASSHOPPER_HURT;
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return MoCLootTables.GRASSHOPPER;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!this.world.isRemote) {
            if (onGround && ((motionX > 0.05D) || (motionZ > 0.05D) || (motionX < -0.05D) || (motionZ < -0.05D)))
                if (this.jumpCounter == 0) {
                    this.motionY = 0.45D;
                    this.motionX *= 5D;
                    this.motionZ *= 5D;
                    this.jumpCounter = 1;
                }
        }
    }

    @Override
    public boolean isFlyer() {
        return true;
    }

    @Override
    public float getAIMoveSpeed() {
        if (getIsFlying()) {
            return 0.12F;
        }
        return 0.15F;
    }

    @Override
    public float getEyeHeight() {
        return 0.15F;
    }
    
    @Override
    public int getMaxSpawnedInChunk() {
        return 4;
    }
}
