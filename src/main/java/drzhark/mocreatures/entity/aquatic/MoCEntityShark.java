/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.aquatic;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.ai.EntityAIHuntAquatic;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.entity.passive.*;
import drzhark.mocreatures.entity.tameable.MoCEntityTameableAquatic;
import drzhark.mocreatures.init.MoCLootTables;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class MoCEntityShark extends MoCEntityTameableAquatic {

    public MoCEntityShark(World world) {
        super(world);
        this.texture = "shark.png";
        setSize(1.65F, 0.9F);
        // TODO: Make hitboxes adjust depending on size
        //setAge(60 + this.rand.nextInt(100));
        setAge(160);
        experienceValue = 5;
    }

    @Override
    protected void initEntityAI() {
        List<Class<? extends EntityLivingBase>> hunterTargets = new ArrayList<>();
        hunterTargets.add(EntityPlayer.class);
        hunterTargets.add(EntitySquid.class);
        hunterTargets.add(MoCEntityDolphin.class);
        hunterTargets.add(MoCEntityFishy.class);
        hunterTargets.add(MoCEntityMantaRay.class);
        hunterTargets.add(MoCEntityMediumFish.class);
        hunterTargets.add(MoCEntitySmallFish.class);
        hunterTargets.add(MoCEntityStingRay.class);

        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(5, new EntityAIWanderMoC2(this, 1.0D, 30));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAIHuntAquatic(this, hunterTargets, false));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.55D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);
    }

    @Override
    public ResourceLocation getTexture() {
        if (MoCreatures.proxy.legacyBigCatModels)
            return MoCreatures.proxy.getModelTexture("shark_legacy.png");
        return MoCreatures.proxy.getModelTexture("shark.png");
    }

    @Override
    protected void entityInit() {
        super.entityInit();
    }

    @Override
    protected int getExperiencePoints(EntityPlayer player) {
        return experienceValue;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (super.attackEntityFrom(damagesource, i) && (this.world.getDifficulty().getId() > 0)) {
            Entity entity = damagesource.getTrueSource();
            if (entity != null && this.isRidingOrBeingRiddenBy(entity)) {
                return true;
            }
            if (entity != this && entity instanceof EntityLivingBase) {
                setAttackTarget((EntityLivingBase) entity);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        if (!getIsAdult()) {
            return null;
        }

        return MoCLootTables.SHARK;
    }

    @Override
    public boolean isReadyToHunt() {
        return getIsAdult() && isInWater();
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!this.world.isRemote) {
            if (getAge() >= 160) {
                setAdult(true);
            }
            if (!getIsAdult() && (this.rand.nextInt(50) == 0)) {
                setAge(getAge() + 1);
            }
        }
    }

    @Override
    public void setDead() {
        // Server check required to prevent tamed entities from being duplicated on client-side
        if (!this.world.isRemote && (getIsTamed()) && (getHealth() > 0)) {
            return;
        }
        super.setDead();
    }

    public boolean isMyHealFood(Item item1) {
        return false;
    }

    @Override
    protected boolean usesNewAI() {
        return true;
    }

    @Override
    public float getAIMoveSpeed() {
        return 0.12F;
    }

    @Override
    public boolean isMovementCeased() {
        return !isInWater();
    }

    @Override
    protected double minDivingDepth() {
        return 1D;
    }

    @Override
    protected double maxDivingDepth() {
        return 6.0D;
    }

    @Override
    public int getMaxAge() {
        return 200;
    }

    @Override
    public boolean isNotScared() {
        return true;
    }

    public float getEyeHeight() {
        return this.height * 0.61F;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_FISH_FLOP;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_GUARDIAN_HURT_LAND;
    }

    @Override
    protected SoundEvent getSwimSound() {
        return MoCSoundEvents.ENTITY_FISH_SWIM;
    }
}
