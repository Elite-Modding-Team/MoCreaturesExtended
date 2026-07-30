/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.hostile;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityMob;
import drzhark.mocreatures.entity.item.MoCEntityThrowableRock;
import drzhark.mocreatures.init.MoCLootTables;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoCEntityMiniGolem extends MoCEntityMob {
    private static final DataParameter<Boolean> ANGRY = EntityDataManager.createKey(MoCEntityMiniGolem.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> HAS_ROCK = EntityDataManager.createKey(MoCEntityMiniGolem.class, DataSerializers.BOOLEAN);
    public int tCounter;
    public MoCEntityThrowableRock tempRock;

    public MoCEntityMiniGolem(World world) {
        super(world);
        setSize(0.9F, 1.2F);
        experienceValue = 5;
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new MoCEntityMiniGolem.AIGolemAttack(this, 1.0D, true));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new MoCEntityMiniGolem.AIGolemTarget<>(this, EntityPlayer.class, true));
        this.targetTasks.addTask(3, new MoCEntityMiniGolem.AIGolemTarget<>(this, EntityIronGolem.class, true));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(6.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(ANGRY, Boolean.FALSE);
        this.dataManager.register(HAS_ROCK, Boolean.FALSE);
    }

    public boolean getIsAngry() {
        return this.dataManager.get(ANGRY);
    }

    public void setIsAngry(boolean flag) {
        this.dataManager.set(ANGRY, flag);
    }

    public boolean getHasRock() {
        return this.dataManager.get(HAS_ROCK);
    }

    public void setHasRock(boolean flag) {
        this.dataManager.set(HAS_ROCK, flag);
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (!this.world.isRemote) {
            setIsAngry(getAttackTarget() != null);

            if (getIsAngry() && getAttackTarget() != null && !getHasRock() && this.rand.nextInt(30) == 0) {
                acquireTRock();
            }

            if (getHasRock()) {
                getNavigator().clearPath();
                attackWithTRock();
            }
        }
    }

    @Override
    public void onDeath(DamageSource cause) {
        if (getHasRock() && this.tempRock != null) this.tempRock.transformToItem();
        super.onDeath(cause);
    }

    protected void acquireTRock() {
        IBlockState tRockState = MoCTools.destroyRandomBlockWithIBlockState(this, 3D);
        if (tRockState == null) {
            this.tCounter = 1;
            setHasRock(false);
            return;
        }

        //creates a dummy TRock on top of it
        MoCEntityThrowableRock tRock = new MoCEntityThrowableRock(this.world, this, this.posX, this.posY + 1.5D, this.posZ);
        this.world.spawnEntity(tRock);
        tRock.setState(tRockState);
        tRock.setBehavior(1);
        this.tempRock = tRock;
        setHasRock(true);
    }

    /**
     *
     */
    protected void attackWithTRock() {
        this.tCounter++;

        if (this.tCounter < 50) {
            //maintains position of TRock above head
            this.tempRock.posX = this.posX;
            this.tempRock.posY = (this.posY + 1.0D);
            this.tempRock.posZ = this.posZ;
        }

        if (this.tCounter >= 50) {
            //throws a newly spawned TRock and destroys the held TRock
            if (this.getAttackTarget() != null && this.getDistance(this.getAttackTarget()) < 48F) {
                MoCTools.throwStone(this, this.getAttackTarget(), this.tempRock.getState(), 10D, 0.25D);
            } else {
                this.tempRock.transformToItem();
            }

            this.tempRock.setDead();
            setHasRock(false);
            this.tCounter = 0;
        }
    }

    /**
     * Stretches the model to that size
     */
    @Override
    public float getSizeFactor() {
        return 1.0F;
    }

    /**
     * Plays step sound at given x, y, z for the entity
     */  
    @Override
    protected void playStepSound(BlockPos p_180429_1_, Block p_180429_2_) {
        if (MoCreatures.proxy.legacyMiniGolemSounds) {
            this.playSound(MoCSoundEvents.ENTITY_GENERIC_STOMP, 1.0F, 1.0F);
        } else {
            this.playSound(SoundEvents.ENTITY_IRONGOLEM_STEP, 1.0F, 1.0F);
        }
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCreatures.proxy.legacyMiniGolemSounds ? MoCSoundEvents.ENTITY_BIG_GOLEM_HURT_LEGACY : MoCSoundEvents.ENTITY_MINI_GOLEM_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCreatures.proxy.legacyMiniGolemSounds ? MoCSoundEvents.ENTITY_BIG_GOLEM_HURT_LEGACY : MoCSoundEvents.ENTITY_MINI_GOLEM_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return MoCreatures.proxy.legacyMiniGolemSounds ? MoCSoundEvents.ENTITY_BIG_GOLEM_AMBIENT : null;
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return MoCLootTables.MINI_GOLEM;
    }

    public float getEyeHeight() {
        return this.height * 0.92F;
    }

    static class AIGolemAttack extends EntityAIAttackMelee {
        public AIGolemAttack(MoCEntityMiniGolem golem, double speed, boolean useLongMemory) {
            super(golem, speed, useLongMemory);
        }

        @Override
        public boolean shouldContinueExecuting() {
            float f = this.attacker.getBrightness();

            if (f >= 0.5F && this.attacker.getRNG().nextInt(100) == 0) {
                this.attacker.setAttackTarget(null);
                return false;
            } else {
                return super.shouldContinueExecuting();
            }
        }
    }

    static class AIGolemTarget<T extends EntityLivingBase> extends EntityAINearestAttackableTarget<T> {
        public AIGolemTarget(MoCEntityMiniGolem golem, Class<T> classTarget, boolean checkSight) {
            super(golem, classTarget, checkSight);
        }

        @Override
        public boolean shouldExecute() {
            float f = this.taskOwner.getBrightness();
            return f < 0.5F && super.shouldExecute();
        }
    }
}
