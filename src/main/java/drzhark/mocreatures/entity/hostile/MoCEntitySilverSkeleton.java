/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.hostile;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityMob;
import drzhark.mocreatures.init.MoCLootTables;
import drzhark.mocreatures.init.MoCSoundEvents;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageAnimation;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

import javax.annotation.Nullable;

public class MoCEntitySilverSkeleton extends MoCEntityMob {
    private static final ResourceLocation TEXTURE = MoCreatures.proxy.getModelTexture("silver_skeleton.png");

    public int attackCounterLeft;
    public int attackCounterRight;

    public MoCEntitySilverSkeleton(World world) {
        super(world);
        setSize(0.6F, 2.125F);
        experienceValue = 5 + this.world.rand.nextInt(4);
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new MoCEntitySilverSkeleton.AISkeletonAttack(this, 1.0D, false));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new MoCEntitySilverSkeleton.AISkeletonTarget<>(this, EntityPlayer.class, false));
        this.targetTasks.addTask(3, new MoCEntitySilverSkeleton.AISkeletonTarget<>(this, EntityIronGolem.class, true));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(35.0D);
        getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(14.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(7.0D);
    }

    @Override
    public void onLivingUpdate() {
        if (!this.world.isRemote) {
            setSprinting(this.getAttackTarget() != null);
        }

        if (this.attackCounterLeft > 0 && ++this.attackCounterLeft > 10) {
            this.attackCounterLeft = 0;
        }

        if (this.attackCounterRight > 0 && ++this.attackCounterRight > 10) {
            this.attackCounterRight = 0;
        }

        super.onLivingUpdate();
    }

    @Override
    public void performAnimation(int animationType) {

        if (animationType == 1) //left arm
        {
            this.attackCounterLeft = 1;
        }
        if (animationType == 2) //right arm
        {
            this.attackCounterRight = 1;
        }
    }

    /**
     * Starts attack counters and synchronizes animations with clients
     */
    private void startAttackAnimation() {
        if (!this.world.isRemote) {
            boolean leftArmW = this.rand.nextInt(2) == 0;

            if (leftArmW) {
                this.attackCounterLeft = 1;
                MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 1), new TargetPoint(this.world.provider.getDimensionType().getId(), this.posX, this.posY, this.posZ, 64));
            } else {
                this.attackCounterRight = 1;
                MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 2), new TargetPoint(this.world.provider.getDimensionType().getId(), this.posX, this.posY, this.posZ, 64));
            }
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
    	MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_SILVER_SKELETON_ATTACK);
        startAttackAnimation();
        return super.attackEntityAsMob(entityIn);
    }

    @Override
    public float getAIMoveSpeed() {
        if (isSprinting()) {
            return 0.425F;
        }
        
        return 0.25F;
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_SILVER_SKELETON_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCSoundEvents.ENTITY_SILVER_SKELETON_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return MoCSoundEvents.ENTITY_SILVER_SKELETON_AMBIENT;
    }

    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEAD;
    }

    @Override
    protected void playStepSound(BlockPos pos, Block block) {
        this.playSound(MoCSoundEvents.ENTITY_SILVER_SKELETON_STEP, 0.3F, 1.0F);
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return MoCLootTables.SILVER_SKELETON;
    }

    public float getEyeHeight() {
        return this.height * 0.905F;
    }
    
    static class AISkeletonAttack extends EntityAIAttackMelee {
        public AISkeletonAttack(MoCEntitySilverSkeleton skeleton, double speed, boolean useLongMemory) {
            super(skeleton, speed, useLongMemory);
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

    static class AISkeletonTarget<T extends EntityLivingBase> extends EntityAINearestAttackableTarget<T> {
        public AISkeletonTarget(MoCEntitySilverSkeleton skeleton, Class<T> classTarget, boolean checkSight) {
            super(skeleton, classTarget, checkSight);
        }

        @Override
        public boolean shouldExecute() {
            float f = this.taskOwner.getBrightness();
            return f < 0.5F && super.shouldExecute();
        }
    }
}
