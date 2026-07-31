/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.ambient;

import drzhark.mocreatures.entity.MoCEntityAmbient;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.init.MoCLootTables;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoCEntityCricket extends MoCEntityAmbient {

    private int jumpCounter;
    
    public MoCEntityCricket(World world) {
        super(world);
        setSize(0.4F, 0.3F);
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(1, new EntityAIWanderMoC2(this, 1.2D));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(4.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(1.0D);
    }

    @Override
    public void selectType() {
        this.setType(this.rand.nextInt(2));
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (this.isInWater()) {
            this.motionY *= 0.6D;
        }

        if (!this.world.isRemote) {
            if (this.jumpCounter > 0 && ++this.jumpCounter > 30) {
                this.jumpCounter = 0;
            }
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        if (!world.isDaytime()) {
            return world.rand.nextDouble() <= 0.1D ? MoCSoundEvents.ENTITY_CRICKET_AMBIENT : null;
        } else {
            return world.rand.nextDouble() <= 0.1D ? MoCSoundEvents.ENTITY_CRICKET_CHIRP : null;
        }
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_CRICKET_HURT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCSoundEvents.ENTITY_CRICKET_HURT;
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return MoCLootTables.CRICKET;
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
}
