/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.hunter;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.ai.EntityAIFleeFromPlayer;
import drzhark.mocreatures.entity.ai.EntityAIHunt;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.entity.ambient.MoCEntityCrab;
import drzhark.mocreatures.entity.aquatic.MoCEntityFishy;
import drzhark.mocreatures.entity.aquatic.MoCEntityMediumFish;
import drzhark.mocreatures.entity.aquatic.MoCEntitySmallFish;
import drzhark.mocreatures.entity.hostile.MoCEntityRat;
import drzhark.mocreatures.entity.neutral.MoCEntityOstrich;
import drzhark.mocreatures.entity.passive.*;
import drzhark.mocreatures.entity.tameable.MoCEntityTameableAnimal;
import drzhark.mocreatures.init.MoCLootTables;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityParrot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class MoCEntityCrocodile extends MoCEntityTameableAnimal {

    private static final DataParameter<Boolean> IS_RESTING = EntityDataManager.createKey(MoCEntityCrocodile.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> EATING_PREY = EntityDataManager.createKey(MoCEntityCrocodile.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> IS_BITING = EntityDataManager.createKey(MoCEntityCrocodile.class, DataSerializers.BOOLEAN);
    public float biteProgress;
    public float spin;
    public int spinInt;
    private boolean waterbound;

    public MoCEntityCrocodile(World world) {
        super(world);
        this.texture = MoCreatures.proxy.lowResolutionTextures ? "16x/crocodile.png" : "crocodile.png";
        setSize(0.9F, 0.5F);
        setAdult(true);
        // TODO: Make hitboxes adjust depending on size
        //setAge(50 + this.rand.nextInt(50));
        setAge(80);
        setTamed(false);
        experienceValue = 5;
    }

    @Override
    protected void initEntityAI() {
        List<Class<? extends EntityLivingBase>> hunterTargets = new ArrayList<>();
        hunterTargets.add(EntityParrot.class);
        hunterTargets.add(EntityPlayer.class);
        hunterTargets.add(MoCEntityBird.class);
        hunterTargets.add(MoCEntityCrab.class);
        hunterTargets.add(MoCEntityFilchLizard.class);
        hunterTargets.add(MoCEntityFishy.class);
        hunterTargets.add(MoCEntityMouse.class);
        hunterTargets.add(MoCEntityRat.class);
        hunterTargets.add(MoCEntitySnake.class);
        hunterTargets.add(MoCEntityTurtle.class);
        hunterTargets.add(MoCEntityMediumFish.class);
        hunterTargets.add(MoCEntityOstrich.class);
        hunterTargets.add(MoCEntitySmallFish.class);
        hunterTargets.add(MoCEntityTurkey.class);

        this.tasks.addTask(3, new EntityAIFleeFromPlayer(this, 0.8D, 4D));
        this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.0D, true));
        this.tasks.addTask(7, new EntityAIWanderMoC2(this, 0.9D));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAIHunt<>(this, hunterTargets, true));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(25.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(6.0D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(IS_RESTING, Boolean.FALSE);
        this.dataManager.register(EATING_PREY, Boolean.FALSE);
        this.dataManager.register(IS_BITING, Boolean.FALSE);
    }

    public boolean getIsBiting() {
        return this.dataManager.get(IS_BITING);
    }

    public boolean getIsSitting() {
        return this.dataManager.get(IS_RESTING);
    }

    public void setIsSitting(boolean flag) {
        this.dataManager.set(IS_RESTING, flag);
    }

    public boolean getHasCaughtPrey() {
        return this.dataManager.get(EATING_PREY);
    }

    public void setHasCaughtPrey(boolean flag) {
        this.dataManager.set(EATING_PREY, flag);
    }

    public void setBiting(boolean flag) {
        this.dataManager.set(IS_BITING, flag);
    }

    @Override
    protected int getExperiencePoints(EntityPlayer player) {
        return experienceValue;
    }

    @Override
    protected void jump() {

        if (isSwimming()) {
            if (getHasCaughtPrey()) {
                return;
            }

            this.motionY = 0.3D;
            this.isAirBorne = true;

        } else if (this.getAttackTarget() != null || getHasCaughtPrey()) {
            super.jump();
        }
    }

    @Override
    public boolean isMovementCeased() {
        return getIsSitting();
    }

    @Override
    public void onLivingUpdate() {
        if (getIsSitting()) {
            this.rotationPitch = -5F;
            if (!isSwimming() && this.biteProgress < 0.3F && this.rand.nextInt(5) == 0) {
                this.biteProgress += 0.005F;
            }
            if (this.getAttackTarget() != null) {
                setIsSitting(false);
            }
            if (!this.world.isRemote && this.getAttackTarget() != null || isSwimming() || getHasCaughtPrey() || this.rand.nextInt(500) == 0)// isInsideOfMaterial(Material.WATER)
            {
                setIsSitting(false);
                this.biteProgress = 0;
            }

        } else {
            if (!this.world.isRemote && (this.rand.nextInt(500) == 0) && this.getAttackTarget() == null && !getHasCaughtPrey() && !isSwimming()) {
                setIsSitting(true);
                this.getNavigator().clearPath();
            }

        }

        if (this.rand.nextInt(500) == 0 && !getHasCaughtPrey() && !getIsSitting()) {
            crocBite();
        }

        //TODO replace with move to water AI
        if (!this.world.isRemote && this.rand.nextInt(500) == 0 && !this.waterbound && !getIsSitting() && !isSwimming()) {
            MoCTools.moveToWater(this);
        }

        if (this.waterbound) {
            if (!isInsideOfMaterial(Material.WATER)) {
                MoCTools.moveToWater(this);
            } else {
                this.waterbound = false;
            }
        }

        if (getHasCaughtPrey()) {
            if (this.isBeingRidden()) {
                setAttackTarget(null);

                this.biteProgress = 0.4F;
                setIsSitting(false);

                if (!isInsideOfMaterial(Material.WATER)) {
                    this.waterbound = true;
                    if (this.getRidingEntity() instanceof EntityLiving && ((EntityLivingBase) this.getRidingEntity()).getHealth() > 0) {
                        ((EntityLivingBase) this.getRidingEntity()).deathTime = 0;
                    }

                    if (!this.world.isRemote && this.rand.nextInt(50) == 0) {
                        this.getRidingEntity().attackEntityFrom(DamageSource.causeMobDamage(this), 2);
                    }
                }
            } else {
                setHasCaughtPrey(false);
                this.biteProgress = 0F;
                this.waterbound = false;
            }

            if (isSpinning()) {
                this.spinInt += 3;
                if ((this.spinInt % 20) == 0) {
                    MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_CROCODILE_ROLL);
                }
                if (this.spinInt > 80) {
                    this.spinInt = 0;
                    this.getRidingEntity().attackEntityFrom(DamageSource.causeMobDamage(this), 4); //TODO ADJUST
                }

                //TODO 4FIX
                //the following if to be removed from SMP
                //if (!this.world.isRemote && this.isBeingRidden() && this.getRidingEntity() instanceof EntityPlayer) {
                //MoCreatures.mc.gameSettings.thirdPersonView = 1;
                //}
            }
        }

        super.onLivingUpdate();
    }

    @Override
    public boolean isNotScared() {
        return true;
    }

    public void crocBite() {
        if (!getIsBiting()) {
            setBiting(true);
            this.biteProgress = 0.0F;
        }
    }

    @Override
    public void onUpdate() {
        if (getIsBiting() && !getHasCaughtPrey())// && biteProgress <0.3)
        {
            this.biteProgress += 0.1F;
            if (this.biteProgress == 0.4F) {
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_CROCODILE_ATTACK);
            }
            if (this.biteProgress > 0.6F) {

                setBiting(false);
                this.biteProgress = 0.0F;
            }
        }

        super.onUpdate();
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        if (getHasCaughtPrey()) {
            return false;
        }

        //TODO FIX!!!!
        /*if (!this.world.isRemote && entityIn.getRidingEntity() == null && this.rand.nextInt(3) == 0) {
            entityIn.mountEntity(this);
            this.setHasCaughtPrey(true);
            return false;
        } */

        crocBite();
        setHasCaughtPrey(false);
        return super.attackEntityAsMob(entityIn);
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (this.isBeingRidden()) {

            Entity entity = damagesource.getTrueSource();
            if (entity != null && this.getRidingEntity() == entity) {
                if (this.rand.nextInt(2) != 0) {
                    return false;
                } else {
                    unMount();
                }
            }

        }
        if (super.attackEntityFrom(damagesource, i)) {
            Entity entity = damagesource.getTrueSource();

            if (this.isBeingRidden() && this.getRidingEntity() == entity) {
                if ((entity != this) && entity instanceof EntityLivingBase && super.shouldAttackPlayers()) {
                    setAttackTarget((EntityLivingBase) entity);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean canAttackTarget(EntityLivingBase entity) {
        return !(entity instanceof MoCEntityCrocodile);
    }

    @Override
    public void updatePassenger(Entity passenger) {
        if (!this.isBeingRidden()) {
            return;
        }
        int direction;

        double dist = getAge() * 0.01F + passenger.width - 0.4D;
        double newPosX = this.posX - (dist * Math.cos((MoCTools.realAngle(this.rotationYaw - 90F)) / 57.29578F));
        double newPosZ = this.posZ - (dist * Math.sin((MoCTools.realAngle(this.rotationYaw - 90F)) / 57.29578F));
        passenger.setPosition(newPosX, this.posY + getMountedYOffset() + passenger.getYOffset(), newPosZ);

        if (this.spinInt > 40) {
            direction = -1;
        } else {
            direction = 1;
        }

        ((EntityLivingBase) passenger).renderYawOffset = this.rotationYaw * direction;
        ((EntityLivingBase) passenger).prevRenderYawOffset = this.rotationYaw * direction;
    }

    @Override
    public double getMountedYOffset() {
        return this.height * 0.35D;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_CROCODILE_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCSoundEvents.ENTITY_CROCODILE_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        if (getIsSitting()) {
            return MoCSoundEvents.ENTITY_CROCODILE_REST;
        }
        return MoCSoundEvents.ENTITY_CROCODILE_AMBIENT;
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        if (!getIsAdult()) {
            return null;
        }

        return MoCLootTables.CROCODILE;
    }

    public boolean isSpinning() {
        return getHasCaughtPrey() && (this.isBeingRidden()) && (this.isSwimming());
    }

    @Override
    public void onDeath(DamageSource damagesource) {

        unMount();
        MoCTools.checkForTwistedEntities(this.world);
        super.onDeath(damagesource);
    }

    public void unMount() {

        if (this.isBeingRidden()) {
            if (this.getRidingEntity() instanceof EntityLiving && ((EntityLivingBase) this.getRidingEntity()).getHealth() > 0) {
                ((EntityLivingBase) this.getRidingEntity()).deathTime = 0;
            }

            this.dismountRidingEntity();
            setHasCaughtPrey(false);
        }
    }

    @Override
    public int getTalkInterval() {
        return 400;
    }

    @Override
    public boolean isAmphibian() {
        return true;
    }

    @Override
    public boolean isSwimming() {
        return this.isInWater();
    }

    @Override
    public boolean isReadyToHunt() {
        return this.isNotScared() && !this.isMovementCeased() && !this.isBeingRidden() && !this.getHasCaughtPrey();
    }

    public float getEyeHeight() {
        return !this.isMovementCeased() ? this.height * 0.7F : this.height * 0.39F;
    }
}
