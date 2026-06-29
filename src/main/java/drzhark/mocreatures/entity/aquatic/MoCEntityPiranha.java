/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.aquatic;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.ai.EntityAIHuntAquatic;
import drzhark.mocreatures.init.MoCLootTables;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class MoCEntityPiranha extends MoCEntitySmallFish {
    public MoCEntityPiranha(World world) {
        super(world);
        experienceValue = 3;
    }

    @Override
    protected void initEntityAI() {
        List<Class<? extends EntityLivingBase>> hunterTargets = new ArrayList<>();
        hunterTargets.add(MoCEntityAnchovy.class);
        hunterTargets.add(MoCEntityAngelFish.class);
        hunterTargets.add(MoCEntityClownFish.class);
        hunterTargets.add(MoCEntityGoldFish.class);
        hunterTargets.add(MoCEntityFishy.class);
        hunterTargets.add(MoCEntityHippoTang.class);
        hunterTargets.add(MoCEntityManderin.class);

        this.tasks.addTask(3, new EntityAIAttackMelee(this, 1.0D, false));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAIHuntAquatic(this, hunterTargets, false));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(5.0D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.5D);
    }

    @Override
    public void selectType() {
        setType(1);
    }

    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.proxy.getModelTexture("smallfish_piranha.png");
    }

    @Override
    protected int getExperiencePoints(EntityPlayer player) {
        return experienceValue;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (super.attackEntityFrom(damagesource, i) && (this.world.getDifficulty().getId() > 0)) {
            Entity entity = damagesource.getTrueSource();
            if (entity instanceof EntityLivingBase) {
                if (this.isRidingOrBeingRiddenBy(entity)) {
                    return true;
                }
                if (entity != this) {
                    this.setAttackTarget((EntityLivingBase) entity);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isReadyToHunt() {
        return isInWater();
    }

    @Override
    public boolean isNotScared() {
        return true;
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return MoCLootTables.PIRANHA;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_FISH_DEATH_VICIOUS;
    }
}
