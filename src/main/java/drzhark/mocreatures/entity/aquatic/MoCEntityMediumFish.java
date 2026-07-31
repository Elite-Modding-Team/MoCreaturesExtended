/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.aquatic;

import drzhark.mocreatures.entity.ai.EntityAIFleeFromEntityMoC;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.entity.tameable.MoCEntityTameableAquatic;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.block.material.Material;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MoCEntityMediumFish extends MoCEntityTameableAquatic {

    public static final String[] fishNames = {"Salmon", "Cod", "Bass"};

    public MoCEntityMediumFish(World world) {
        super(world);
        setSize(0.7f, 0.45f);
        // TODO: Make hitboxes adjust depending on size
        //setAge(30 + this.rand.nextInt(70));
        setAge(100);
    }

    public static MoCEntityMediumFish createEntity(World world, int type) {
        if (type == 1) {
            return new MoCEntitySalmon(world);
        }
        if (type == 2) {
            return new MoCEntityCod(world);
        }
        if (type == 3) {
            return new MoCEntityBass(world);
        }

        return new MoCEntitySalmon(world);
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(3, new EntityAIFleeFromEntityMoC(this, entity -> (entity.height > 0.6F && entity.width > 0.3F), 2.0F, 0.6D, 1.5D));
        this.tasks.addTask(5, new EntityAIWanderMoC2(this, 1.0D, 50));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(7.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (!this.world.isRemote) {
            if (getIsTamed() && this.rand.nextInt(100) == 0 && getHealth() < getMaxHealth()) {
                this.setHealth(getMaxHealth());
            }
        }
        if (!this.isInsideOfMaterial(Material.WATER)) {
            this.prevRenderYawOffset = this.renderYawOffset = this.rotationYaw = this.prevRotationYaw;
            this.rotationPitch = this.prevRotationPitch;
        }
    }

    @Override
    public float getSizeFactor() {
        return getAge() * 0.0081F;
    }

    @Override
    public float getAdjustedYOffset() {
        if (!this.isInsideOfMaterial(Material.WATER)) {
            return 1F;
        }
        return 0.5F;
    }

    @Override
    protected boolean isFisheable() {
        return !getIsTamed();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public float yawRotationOffset() {
        if (!this.isInsideOfMaterial(Material.WATER)) {
            return 90F;
        }
        return 90F + super.yawRotationOffset();
    }

    @Override
    public float rollRotationOffset() {
        if (!isInWater() && this.onGround) {
            return -90F;
        }
        return 0F;
    }

    @Override
    public int nameYOffset() {
        return -30;
    }

    @Override
    public float getAdjustedZOffset() {
        if (!isInWater()) {
            return 0.2F;
        }
        return 0F;
    }

    @Override
    protected boolean canBeTrappedInNet() {
        return true;
    }

    @Override
    protected boolean usesNewAI() {
        return true;
    }

    @Override
    public float getAIMoveSpeed() {
        return 0.15F;
    }

    @Override
    public boolean isMovementCeased() {
        return !isInWater();
    }

    @Override
    protected double minDivingDepth() {
        return 0.5D;
    }

    @Override
    protected double maxDivingDepth() {
        return 4.0D;
    }

    @Override
    public int getMaxAge() {
        return 120;
    }

    @Override
    public boolean isNotScared() {
        return getIsTamed();
    }

    public float getEyeHeight() {
        return this.height * 0.775F;
    }
    
    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_FISH_FLOP;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCSoundEvents.ENTITY_FISH_HURT;
    }

    @Override
    protected SoundEvent getSwimSound() {
        return MoCSoundEvents.ENTITY_FISH_SWIM;
    }
}
