/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.ambient;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.entity.ai.EntityAIFollowOwnerPlayer;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.entity.tameable.MoCEntityTameableAnimal;
import drzhark.mocreatures.init.MoCLootTables;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class MoCEntityCrab extends MoCEntityTameableAnimal {

    public MoCEntityCrab(World world) {
        super(world);
        setSize(0.45F, 0.3F);
        setAge(50 + this.rand.nextInt(50));
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(1, new EntityAIFollowOwnerPlayer(this, 0.8D, 6F, 5F));
        this.tasks.addTask(4, new EntityAIWanderMoC2(this, 1.0D));
        this.tasks.addTask(5, new EntityAIAttackMelee(this, 1.0D, true));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(6.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.5D);
    }

    @Override
    public void selectType() {
        this.setType(this.rand.nextInt(5));
    }

    @Override
    protected int getExperiencePoints(EntityPlayer player) {
        return 1 + this.world.rand.nextInt(3);
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return MoCLootTables.CRAB;
    }

    @Override
    public boolean isCreatureType(EnumCreatureType type, boolean forSpawnCount) {
        return type == EnumCreatureType.AMBIENT;
    }

    @Override
    public boolean isOnLadder() {
        return this.collidedHorizontally;
    }

    public boolean climbing() {
        return !this.onGround && isOnLadder();
    }

    @Override
    public void jump() {
    }

    @Override
    protected void collideWithEntity(Entity entity) {
        if (entity instanceof EntityPlayer && this.getAttackTarget() == null && !(entity.world.getDifficulty() == EnumDifficulty.PEACEFUL)) {
            entity.attackEntityFrom(DamageSource.causeMobDamage(this), 1.5F);
        }

        super.collideWithEntity(entity);
    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        this.playSound(MoCSoundEvents.ENTITY_GENERIC_SMACK, 1.0F, 2.0F);
        return super.attackEntityAsMob(entity);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public float getSizeFactor() {
        return 0.7F * getAge() * 0.01F;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public boolean isFleeing() {
        return MoCTools.getMyMovementSpeed(this) > 0.09F;
    }

    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.ARTHROPOD;
    }

    @Override
    protected boolean canBeTrappedInNet() {
        return true;
    }

    @Override
    public int nameYOffset() {
        return -20;
    }

    @Override
    public boolean isReadyToFollowOwnerPlayer() {
        return !this.isMovementCeased();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return null;
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 4;
    }
}
