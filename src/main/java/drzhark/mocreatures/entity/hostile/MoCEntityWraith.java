/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.hostile;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityMob;
import drzhark.mocreatures.init.MoCLootTables;
import drzhark.mocreatures.init.MoCSoundEvents;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageAnimation;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
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

public class MoCEntityWraith extends MoCEntityMob//MoCEntityFlyerMob
{
    public int attackCounter;

    public MoCEntityWraith(World world) {
        super(world);
        this.collidedVertically = false;
        setSize(0.6F, 1.75F);
        experienceValue = 5;
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, true));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntityIronGolem.class, true));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    }

    @Override
    public void selectType() {
        if (getType() == 0) {
            int i = this.rand.nextInt(100);
            if (i < 5 && MoCreatures.proxy.easterEggs) {
                setType(2);
                setCustomNameTag("Scratch");
            } else {
                setType(1);
            }
        }
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCreatures.proxy.legacyWraithSounds ? MoCSoundEvents.ENTITY_WRAITH_DEATH_LEGACY : MoCSoundEvents.ENTITY_WRAITH_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCreatures.proxy.legacyWraithSounds ? MoCSoundEvents.ENTITY_WRAITH_HURT_LEGACY : MoCSoundEvents.ENTITY_WRAITH_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return MoCreatures.proxy.legacyWraithSounds ? MoCSoundEvents.ENTITY_WRAITH_AMBIENT_LEGACY : MoCSoundEvents.ENTITY_WRAITH_AMBIENT;
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return MoCLootTables.WRAITH;
    }

    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEAD;
    }

    @Override
    public boolean isFlyer() {
        return true;
    }

    @Override
    public boolean canBePushed() {
        return false;
    }

    @Override
    protected void collideWithEntity(Entity par1Entity) {
    }

    @Override
    public void fall(float f, float f1) {
    }

    protected void updateFallState(double y, boolean onGroundIn, Block blockIn, BlockPos pos) {
    }

    public int maxFlyingHeight() {
        return 10;
    }

    public int minFlyingHeight() {
        return 3;
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        startArmSwingAttack();
        return super.attackEntityAsMob(entityIn);
    }

    /**
     * Starts attack counters and synchronizes animations with clients
     */
    private void startArmSwingAttack() {
        if (!this.world.isRemote) {
            this.attackCounter = 1;
            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 1), new TargetPoint(this.world.provider.getDimensionType().getId(), this.posX, this.posY, this.posZ, 64));
        }
    }

    @Override
    public void onLivingUpdate() {
        if (this.attackCounter > 0) {
            this.attackCounter += 2;
            if (this.attackCounter > 10) this.attackCounter = 0;
        }

        super.onLivingUpdate();
    }

    @Override
    public void performAnimation(int animationType) {
        if (animationType == 1) {
            this.attackCounter = 1;
        }

    }

    @Override
    protected boolean isHarmedByDaylight() {
        return true;
    }

    public float getEyeHeight() {
        return this.height * 0.84F;
    }
}
