/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.aquatic;

import drzhark.mocreatures.entity.ai.EntityAIFleeFromEntityMoC;
import drzhark.mocreatures.entity.ai.EntityAIPanicMoC;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.entity.tameable.MoCEntityTameableAquatic;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MoCEntitySmallFish extends MoCEntityTameableAquatic {

    public static final String[] fishNames = {"Anchovy", "Angelfish", "Anglerfish", "Clownfish", "Goldfish", "Hippo Tang", "Mandarinfish"};

    public MoCEntitySmallFish(World world) {
        super(world);
        setSize(0.5f, 0.3f);
        // TODO: Make hitboxes adjust depending on size
        //setAge(70 + this.rand.nextInt(30));
        setAge(100);
    }

    public static MoCEntitySmallFish createEntity(World world, int type) {
        if (type == 1) {
            return new MoCEntityAnchovy(world);
        }
        if (type == 2) {
            return new MoCEntityAngelFish(world);
        }
        if (type == 3) {
            return new MoCEntityAngler(world);
        }
        if (type == 4) {
            return new MoCEntityClownFish(world);
        }
        if (type == 5) {
            return new MoCEntityGoldFish(world);
        }
        if (type == 6) {
            return new MoCEntityHippoTang(world);
        }
        if (type == 7) {
            return new MoCEntityManderin(world);
        }

        return new MoCEntityClownFish(world);
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(1, new EntityAIPanicMoC(this, 1.3D));
        this.tasks.addTask(2, new EntityAIFleeFromEntityMoC(this, entity -> (entity.height > 0.3F || entity.width > 0.3F), 2.0F, 0.6D, 1.5D));
        this.tasks.addTask(5, new EntityAIWanderMoC2(this, 1.0D, 80));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(4.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
    }

    @Override
    protected boolean canBeTrappedInNet() {
        return true;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (!this.world.isRemote) {

            if (getIsTamed() && this.rand.nextInt(100) == 0 && getHealth() < getMaxHealth()) {
                this.setHealth(getMaxHealth());
            }
        }
        if (!this.isInWater()) {
            this.prevRenderYawOffset = this.renderYawOffset = this.rotationYaw = this.prevRotationYaw;
            this.rotationPitch = this.prevRotationPitch;
        }
    }

    @Override
    public float getSizeFactor() {
        return getAge() * 0.01F;
    }

    @Override
    public float getAdjustedYOffset() {
        if (!this.isInWater()) {
            return 0.5F;
        }
        return 0.3F;
    }

    @Override
    protected boolean isFisheable() {
        return !getIsTamed();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public float yawRotationOffset() {
        if (!this.isInWater()) {
            return 90F;
        }
        return 90F + super.yawRotationOffset();
    }

    @Override
    public float rollRotationOffset() {
        if (!this.isInWater()) {
            return -90F;
        }
        return 0F;
    }

    @Override
    public int nameYOffset() {
        return -25;
    }

    @Override
    protected boolean usesNewAI() {
        return true;
    }

    @Override
    public float getAIMoveSpeed() {
        return 0.10F;
    }

    @Override
    public boolean isMovementCeased() {
        return !isInWater();
    }

    @Override
    protected double maxDivingDepth() {
        return 2.0D;
    }

    @Override
    public int getMaxAge() {
        return 120;
    }

    @Override
    public boolean isNotScared() {
        return getIsTamed();
    }

    @Override
    public float getAdjustedZOffset() {
        if (!isInWater()) {
            return 0.1F;
        }
        return 0F;
    }

    public float getEyeHeight() {
        return this.height * 0.45F;
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
