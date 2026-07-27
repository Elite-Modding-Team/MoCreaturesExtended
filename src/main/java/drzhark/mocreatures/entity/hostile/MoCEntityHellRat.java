/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.hostile;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.init.MoCLootTables;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoCEntityHellRat extends MoCEntityRat {
    public MoCEntityHellRat(World world) {
        super(world);
        setSize(0.88F, 0.755F);
        this.isImmuneToFire = true;
        experienceValue = 7;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.325D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.5D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(7.0D);
    }

    @Override
    public void selectType() {
        setType(4);
    }

    @Override
    public ResourceLocation getTexture() {
        if (!MoCreatures.proxy.getAnimateTextures()) {
            return MoCreatures.proxy.getModelTexture("hell_rat.png");
        }
        return MoCreatures.proxy.getModelTexture("hell_rat_animated.png");
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_HELL_RAT_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCSoundEvents.ENTITY_HELL_RAT_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return MoCSoundEvents.ENTITY_HELL_RAT_AMBIENT;
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable() {
        return MoCLootTables.HELL_RAT;
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        boolean flag = super.attackEntityAsMob(entityIn);

        if (flag && entityIn instanceof EntityLivingBase) {
            entityIn.setFire(5);
        }

        return flag;
    }

    @Override
    public void onLivingUpdate() {
        if (this.world.isRemote) {
            for (int i = 0; i < 2; ++i) {
                this.world.spawnParticle(EnumParticleTypes.FLAME, this.posX + (this.rand.nextDouble() - 0.5D) * (double) this.width, this.posY + this.rand.nextDouble() * (double) this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double) this.width, 0.0D, 0.0D, 0.0D);
            }
        }
        super.onLivingUpdate();
    }

    public float getEyeHeight() {
        return this.height * 0.485F;
    }
}
