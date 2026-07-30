/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.hostile;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityMob;
import drzhark.mocreatures.init.MoCItems;
import drzhark.mocreatures.init.MoCLootTables;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoCEntityWerewolf extends MoCEntityMob {
    private static final DataParameter<Boolean> IS_HUMAN = EntityDataManager.createKey(MoCEntityWerewolf.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> IS_HUNCHED = EntityDataManager.createKey(MoCEntityWerewolf.class, DataSerializers.BOOLEAN);
    private boolean transforming;
    private int tcounter;

    public MoCEntityWerewolf(World world) {
        super(world);
        // TODO: Change hitbox depending on form
        setSize(0.7F, 2.0F);
        this.transforming = false;
        this.tcounter = 0;
        setHumanForm(true);
        experienceValue = 10;
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, false));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntityIronGolem.class, true));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(7.5D);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(IS_HUMAN, Boolean.FALSE);
        this.dataManager.register(IS_HUNCHED, Boolean.FALSE);
    }

    @Override
    public void setHealth(float par1) {
        if (this.getIsHumanForm() && par1 > 15F) {
            par1 = 15F;
        }
        super.setHealth(par1);
    }

    @Override
    public void selectType() {
        if (getType() == 0) {
            int k = this.rand.nextInt(100);
            if (k <= 28) {
                setType(1);
            } else if (k <= 56) {
                setType(2);
            } else if (k <= 85) {
                setType(3);
            } else {
                setType(4);
                this.isImmuneToFire = true;
            }
        }
    }

    public boolean getIsHumanForm() {
        return this.dataManager.get(IS_HUMAN);
    }

    public void setHumanForm(boolean flag) {
        this.dataManager.set(IS_HUMAN, flag);
    }

    public boolean getIsHunched() {
        return this.dataManager.get(IS_HUNCHED);
    }

    public void setHunched(boolean flag) {
        this.dataManager.set(IS_HUNCHED, flag);
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        if (getIsHumanForm()) {
            setAttackTarget(null);
            return false;
        }
        if (this.getType() == 4 && entityIn instanceof EntityLivingBase) {
            entityIn.setFire(10);
        }
        return super.attackEntityAsMob(entityIn);
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        Entity entity = damagesource.getTrueSource();
        if (!getIsHumanForm() && (entity instanceof EntityPlayer)) {
            EntityPlayer entityplayer = (EntityPlayer) entity;
            ItemStack stack = entityplayer.getHeldItemMainhand();
            if (!stack.isEmpty()) {
                if (stack.getItem() == MoCItems.silversword || stack.getItem() == MoCItems.silveraxe || stack.getItem() == MoCItems.silverMattock) {
                    i = 10.0F;
                } else if (stack.getItem() instanceof ItemSword) {
                    String swordMaterial = ((ItemSword) stack.getItem()).getToolMaterialName();
                    String swordName = stack.getItem().getTranslationKey();
                    if (swordMaterial.toLowerCase().contains("silver") || swordName.toLowerCase().contains("silver")) {
                        i = ((ItemSword) stack.getItem()).getAttackDamage() * 3F;
                    } else {
                        i = ((ItemSword) stack.getItem()).getAttackDamage() * 0.5F;
                    }
                } else if (stack.getItem().getTranslationKey().toLowerCase().contains("silver")) {
                    i = 6.0F;
                } else if (stack.getItem() == MoCItems.bo || stack.getItem() == MoCItems.katana || stack.getItem() == MoCItems.nunchaku || stack.getItem() == MoCItems.sai){
                	i = 7.0F;
                } else {
                    i = Math.min(i * 0.5F, 4F);
                }
            }
        }
        return super.attackEntityFrom(damagesource, i);
    }

    @Override
    public boolean shouldAttackPlayers() {
        return !getIsHumanForm() && super.shouldAttackPlayers();
    }

    @Override
    protected SoundEvent getDeathSound() {
        if (getIsHumanForm()) {
            return MoCreatures.proxy.legacyWerehumanSounds ? MoCSoundEvents.ENTITY_WEREHUMAN_DEATH_LEGACY : SoundEvents.ENTITY_GENERIC_HURT;
        } else {
            return MoCSoundEvents.ENTITY_WEREWOLF_DEATH;
        }
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        if (getIsHumanForm()) {
            if (!this.transforming)
                return MoCreatures.proxy.legacyWerehumanSounds ? MoCSoundEvents.ENTITY_WEREHUMAN_HURT_LEGACY : SoundEvents.ENTITY_GENERIC_HURT;
            return null;
        } else {
            return MoCSoundEvents.ENTITY_WEREWOLF_HURT;
        }
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        if (getIsHumanForm()) {
            return null;
        } else {
            return MoCSoundEvents.ENTITY_WEREWOLF_AMBIENT;
        }
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        if (getIsHumanForm()) {
            return MoCLootTables.WEREHUMAN;
        }

        return MoCLootTables.WEREWOLF;
    }

    public boolean IsNight() {
        return !this.world.isDaytime();
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!this.world.isRemote) {
            if (((IsNight() && getIsHumanForm()) || (!IsNight() && !getIsHumanForm())) && (this.rand.nextInt(250) == 0)) {
                this.transforming = true;
            }
            if (getIsHumanForm() && (this.getAttackTarget() != null)) {
                setAttackTarget(null);
            }
            if (this.getAttackTarget() != null && !getIsHumanForm()) {
                boolean hunch = (this.getDistanceSq(this.getAttackTarget()) > 12D);
                setHunched(hunch);
            }

            if (this.transforming && (this.rand.nextInt(3) == 0)) {
                this.tcounter++;
                if ((this.tcounter % 2) == 0) {
                    this.posX += 0.3D;
                    this.posY += (double) this.tcounter / 30;
                    attackEntityFrom(DamageSource.causeMobDamage(this), 1);
                }
                if ((this.tcounter % 2) != 0) {
                    this.posX -= 0.3D;
                }
                if (this.tcounter == 10) {
                    MoCTools.playCustomSound(this, MoCreatures.proxy.legacyWerehumanSounds ? MoCSoundEvents.ENTITY_WEREWOLF_TRANSFORM_LEGACY : MoCSoundEvents.ENTITY_WEREWOLF_TRANSFORM);
                }
                if (this.tcounter > 30) {
                    Transform();
                    this.tcounter = 0;
                    this.transforming = false;
                }
            }
            //so entity doesn't despawn that often
            if (this.rand.nextInt(300) == 0) {
                this.idleTime -= 100 * this.world.getDifficulty().getId();
                if (this.idleTime < 0) {
                    this.idleTime = 0;
                }
            }
        }
    }

    @Override
    public boolean getCanSpawnHere() {
        return super.getCanSpawnHere() && this.world.canSeeSky(new BlockPos(this));
    }

    private void Transform() {
        if (this.deathTime > 0) {
            return;
        }
        int i = MathHelper.floor(this.posX);
        int j = MathHelper.floor(getEntityBoundingBox().minY) + 1;
        int k = MathHelper.floor(this.posZ);
        float f = 0.1F;
        for (int l = 0; l < 30; l++) {
            double d = i + this.world.rand.nextFloat();
            double d1 = j + this.world.rand.nextFloat();
            double d2 = k + this.world.rand.nextFloat();
            double d3 = d - i;
            double d4 = d1 - j;
            double d5 = d2 - k;
            double d6 = MathHelper.sqrt((d3 * d3) + (d4 * d4) + (d5 * d5));
            d3 /= d6;
            d4 /= d6;
            d5 /= d6;
            double d7 = 0.5D / ((d6 / f) + 0.1D);
            d7 *= (this.world.rand.nextFloat() * this.world.rand.nextFloat()) + 0.3F;
            d3 *= d7;
            d4 *= d7;
            d5 *= d7;
            this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (d + (i * 1.0D)) / 2D, (d1 + (j * 1.0D)) / 2D, (d2 + (k * 1.0D)) / 2D, d3, d4, d5);
        }

        if (getIsHumanForm()) {
            setHumanForm(false);
            this.setHealth(40);
            setSize(0.6F, 2.125F);
            this.transforming = false;
            this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
        } else {
            setHumanForm(true);
            this.setHealth(15);
            setSize(0.6F, 2.125F);
            this.transforming = false;
            this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
        super.readEntityFromNBT(nbttagcompound);
        setHumanForm(nbttagcompound.getBoolean("HumanForm"));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setBoolean("HumanForm", getIsHumanForm());
    }

    @Override
    public float getAIMoveSpeed() {
        if (getIsHumanForm()) {
            return 0.1F;
        }
        if (getIsHunched()) {
            return 0.35F;
        }
        return 0.2F;
    }

    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
        if (getType() == 4) {
            this.isImmuneToFire = true;
        }
        return super.onInitialSpawn(difficulty, livingdata);
    }

    public float getEyeHeight() {
        return getIsHumanForm() ? this.height * 0.885F : this.height;
    }
}
