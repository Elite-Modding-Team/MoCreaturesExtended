/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.neutral;

import com.google.common.collect.Sets;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.ai.EntityAIFollowAdult;
import drzhark.mocreatures.entity.ai.EntityAIMateMoC;
import drzhark.mocreatures.entity.ai.EntityAIPanicMoC;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.entity.tameable.MoCEntityTameableAnimal;
import drzhark.mocreatures.init.MoCLootTables;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Set;

public class MoCEntityGoat extends MoCEntityTameableAnimal {
    private static final DataParameter<Boolean> IS_CHARGING = EntityDataManager.createKey(MoCEntityGoat.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> IS_UPSET = EntityDataManager.createKey(MoCEntityGoat.class, DataSerializers.BOOLEAN);

    private static final Set<Item> BREEDING_ITEMS = Sets.newHashSet(Items.WHEAT);

    public int movecount;
    private boolean hungry;
    private boolean swingLeg;
    private boolean swingEar;
    private boolean swingTail;
    private boolean bleat;
    private boolean eating;
    private int bleatcount;
    private int attacking;
    private int chargecount;
    private int tailcount; // 90 to -45
    private int earcount; // 20 to 40 default = 30
    private int eatcount;

    public MoCEntityGoat(World world) {
        super(world);
        setSize(0.8F, 0.9F);
        setAdult(true);
        setAge(70);
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIMateMoC(this, 1.0D));
        this.tasks.addTask(3, new EntityAITempt(this, 1.0D, false, BREEDING_ITEMS));
        this.tasks.addTask(4, new EntityAIPanicMoC(this, 1.0D));
        this.tasks.addTask(5, new EntityAIFollowAdult(this, 1.0D));
        this.tasks.addTask(6, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(7, new EntityAIWanderMoC2(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(1.0D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.5D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(IS_CHARGING, Boolean.FALSE);
        this.dataManager.register(IS_UPSET, Boolean.FALSE);
    }

    public boolean getUpset() {
        return this.dataManager.get(IS_UPSET);
    }

    public void setUpset(boolean flag) {
        this.dataManager.set(IS_UPSET, flag);
    }

    public boolean getCharging() {
        return this.dataManager.get(IS_CHARGING);
    }

    public void setCharging(boolean flag) {
        this.dataManager.set(IS_CHARGING, flag);
    }

    @Override
    public void selectType() {
        /*
         * type 1 = baby type 2 = female type 3 = female 2 type 4 = female 3
         * type 5 = male 1 type 6 = male 2 type 7 = male 3
         */
        if (getType() == 0) {
            int i = this.rand.nextInt(100);
            if (i <= 15) {
                setType(1);
                setAge(50);
            } else if (i <= 30) {
                setType(2);
                setAge(70);
            } else if (i <= 45) {
                setType(3);
                setAge(70);
            } else if (i <= 60) {
                setType(4);
                setAge(70);
            } else if (i <= 75) {
                setType(5);
                setAge(90);
            } else if (i <= 90) {
                setType(6);
                setAge(90);
            } else {
                setType(7);
                setAge(90);
            }
        }
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return !stack.isEmpty() && BREEDING_ITEMS.contains(stack.getItem());
    }

    @Override
    public EntityAgeable createChild(EntityAgeable entity) {
        MoCEntityGoat baby = new MoCEntityGoat(entity.world);
        baby.setGrowingAge(-24000);
        baby.setAdult(false);
        baby.setType(1);
        baby.setAge(50);
        return baby;
    }

    @Override
    public boolean canMateWith(EntityAnimal otherAnimal) {
        if (otherAnimal == this) return false;
        if (otherAnimal.getClass() != this.getClass()) return false;

        MoCEntityGoat otherGoat = (MoCEntityGoat) otherAnimal;
        if (!this.isInLove() || !otherGoat.isInLove()) return false;

        // Goats have different genders based on type, we'll need to use the correct ones
        boolean thisIsFemale = this.getType() >= 2 && this.getType() <= 4;
        boolean thisIsMale = this.getType() >= 5 && this.getType() <= 7;
        boolean otherIsFemale = otherGoat.getType() >= 2 && otherGoat.getType() <= 4;
        boolean otherIsMale = otherGoat.getType() >= 5 && otherGoat.getType() <= 7;
        return (thisIsFemale && otherIsMale) || (thisIsMale && otherIsFemale);
    }

    @Override
    public ResourceLocation getTexture() {
        switch (getType()) {
            case 2:
                return MoCreatures.proxy.getModelTexture("goat_brown_light.png");
            case 3:
                return MoCreatures.proxy.getModelTexture("goat_brown_spotted.png");
            case 4:
                return MoCreatures.proxy.getModelTexture("goat_gray_spotted.png");
            case 5:
                return MoCreatures.proxy.getModelTexture("goat_gray.png");
            case 6:
                return MoCreatures.proxy.getModelTexture("goat_brown.png");
            default:
                return MoCreatures.proxy.getModelTexture("goat_white.png");
        }
    }

    public void calm() {
        setAttackTarget(null);
        setUpset(false);
        setCharging(false);
        this.attacking = 0;
        this.chargecount = 0;
    }

    @Override
    protected void jump() {
        if (getType() == 1) {
            this.motionY = 0.41D;
        } else if (getType() < 5) {
            this.motionY = 0.45D;
        } else {
            this.motionY = 0.5D;
        }

        if (isPotionActive(MobEffects.JUMP_BOOST)) {
            this.motionY += (getActivePotionEffect(MobEffects.JUMP_BOOST).getAmplifier() + 1) * 0.1F;
        }
        if (isSprinting()) {
            float f = this.rotationYaw * 0.01745329F;
            this.motionX -= MathHelper.sin(f) * 0.2F;
            this.motionZ += MathHelper.cos(f) * 0.2F;
        }
        this.isAirBorne = true;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.world.isRemote) {
            if (this.rand.nextInt(100) == 0) setSwingEar(true);
            if (this.rand.nextInt(80) == 0) setSwingTail(true);
            if (this.rand.nextInt(50) == 0) setEating(true);
        }

        if (getBleating()) {
            this.bleatcount++;
            if (this.bleatcount > 15) {
                this.bleatcount = 0;
                setBleating(false);
            }
        }

        if ((this.hungry) && (this.rand.nextInt(20) == 0)) {
            this.hungry = false;
        }

        if (!this.world.isRemote && (getAge() < 90 || getType() > 4 && getAge() < 100) && this.rand.nextInt(500) == 0) {
            setAge(getAge() + 1);
            if (getType() == 1 && getAge() > 70) {
                int i = this.rand.nextInt(6) + 2;
                setType(i);
                setAdult(true);
            }
        }

        // Interrupt charging behavior if a player nearby is actively tempting them with food
        EntityPlayer temptingPlayer = this.world.getClosestPlayerToEntity(this, 10.0D);
        if (temptingPlayer != null && (this.isBreedingItem(temptingPlayer.getHeldItemMainhand()) || this.isBreedingItem(temptingPlayer.getHeldItemOffhand()))) {
            if (getUpset() || getCharging()) {
                this.calm();
            }
        }

        if (getUpset()) {
            this.attacking += (this.rand.nextInt(4)) + 2;
            if (this.attacking > 75) this.attacking = 75;

            if (this.rand.nextInt(200) == 0 || getAttackTarget() == null) {
                calm();
            }

            if (!getCharging() && this.rand.nextInt(35) == 0) {
                swingLeg();
            }

            if (!getCharging()) {
                this.getNavigator().clearPath();
            }

            if (getAttackTarget() != null) {
                faceEntity(getAttackTarget(), 10F, 10F);
                if (this.rand.nextInt(80) == 0) {
                    setCharging(true);
                }
            }
        }

        if (getCharging()) {
            this.chargecount++;
            if (this.chargecount > 120) this.chargecount = 0;
            if (getAttackTarget() == null) calm();
        }

        if (!getUpset() && !getCharging()) {
            EntityPlayer entityplayer1 = this.world.getClosestPlayerToEntity(this, 24D);
            if (entityplayer1 != null) { // Behaviour that happens only close to player :)
                // Is there food around? Only check with a player near
                EntityItem entityitem = getClosestEntityItem(this, 10D);
                if (entityitem != null) {
                    float f = entityitem.getDistance(this);
                    if (f > 2.0F) {
                        int i = MathHelper.floor(entityitem.posX);
                        int j = MathHelper.floor(entityitem.posY);
                        int k = MathHelper.floor(entityitem.posZ);
                        faceLocation(i, j, k, 30F);
                        setPathToEntity(entityitem, f);
                        return;
                    }
                    if (f < 2.0F && this.deathTime == 0 && this.rand.nextInt(50) == 0) {
                        MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GOAT_EAT);
                        setEating(true);
                        entityitem.setDead();
                        return;
                    }
                }

                // Find other goat to play!
                if (getType() > 4 && this.rand.nextInt(200) == 0 && !this.isInLove()) {
                    MoCEntityGoat entitytarget = (MoCEntityGoat) getClosestEntityLiving(this, 14D);
                    if (entitytarget != null && entitytarget.getType() > 4 && !entitytarget.isInLove()) {
                        setUpset(true);
                        setAttackTarget(entitytarget);
                        entitytarget.setUpset(true);
                        entitytarget.setAttackTarget(this);
                    }
                }
            } // End of close to player behavior
        } // End of !upset !charging
    }

    @Override
    public boolean isMyFavoriteFood(ItemStack stack) {
        return !stack.isEmpty() && MoCTools.isItemEdible(stack.getItem());
    }

    @Override
    public int getTalkInterval() {
        if (this.hungry) {
            return 80;
        }

        return 200;
    }

    @Override
    public boolean entitiesToIgnore(Entity entity) {
        return ((!(entity instanceof MoCEntityGoat)) || ((((MoCEntityGoat) entity).getType() < 5)));
    }

    @Override
    public boolean isMovementCeased() {
        return getUpset() && !getCharging();
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        this.attacking = 30;
        if (entityIn instanceof MoCEntityGoat) {
            MoCTools.bigSmack(this, entityIn, 0.4F);
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_SMACK);
            if (this.rand.nextInt(3) == 0) {
                calm();
                ((MoCEntityGoat) entityIn).calm();
            }
            return false;
        }
        MoCTools.bigSmack(this, entityIn, 0.8F);
        if (this.rand.nextInt(3) == 0) {
            calm();
        }
        return super.attackEntityAsMob(entityIn);
    }

    @Override
    public boolean isNotScared() {
        return getType() > 4;
    }

    private void swingLeg() {
        if (!getSwingLeg()) {
            setSwingLeg(true);
            this.movecount = 0;
        }
    }

    public boolean getSwingLeg() {
        return this.swingLeg;
    }

    public void setSwingLeg(boolean flag) {
        this.swingLeg = flag;
    }

    public boolean getSwingEar() {
        return this.swingEar;
    }

    public void setSwingEar(boolean flag) {
        this.swingEar = flag;
    }

    public boolean getSwingTail() {
        return this.swingTail;
    }

    public void setSwingTail(boolean flag) {
        this.swingTail = flag;
    }

    public boolean getEating() {
        return this.eating;
    }

    public void setEating(boolean flag) {
        this.eating = flag;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (super.attackEntityFrom(damagesource, i)) {
            Entity entity = damagesource.getTrueSource();

            if (entity != this && entity instanceof EntityLivingBase && super.shouldAttackPlayers() && getType() > 4) {
                setAttackTarget((EntityLivingBase) entity);
                setUpset(true);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onUpdate() {

        if (getSwingLeg()) {
            this.movecount += 5;
            if (this.movecount == 30) {
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GOAT_DIG);
            }

            if (this.movecount > 100) {
                setSwingLeg(false);
                this.movecount = 0;
            }
        }

        if (getSwingEar()) {
            this.earcount += 5;
            if (this.earcount > 40) {
                setSwingEar(false);
                this.earcount = 0;
            }
        }

        if (getSwingTail()) {
            this.tailcount += 15;
            if (this.tailcount > 135) {
                setSwingTail(false);
                this.tailcount = 0;
            }
        }

        if (getEating()) {
            this.eatcount += 1;
            if (this.eatcount == 2) {
                EntityPlayer entityplayer1 = this.world.getClosestPlayerToEntity(this, 3D);
                if (entityplayer1 != null) {
                    MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GOAT_EAT);
                }
            }
            if (this.eatcount > 25) {
                setEating(false);
                this.eatcount = 0;
            }
        }

        super.onUpdate();
    }

    public int legMovement() {
        if (!getSwingLeg()) {
            return 0;
        }

        if (this.movecount < 21) {
            return this.movecount * -1;
        }
        if (this.movecount < 70) {
            return this.movecount - 40;
        }
        return -this.movecount + 100;
    }

    public int earMovement() {
        // 20 to 40 default = 30
        if (!getSwingEar()) {
            return 0;
        }
        if (this.earcount < 11) {
            return this.earcount + 30;
        }
        if (this.earcount < 31) {
            return -this.earcount + 50;
        }
        return this.earcount - 10;
    }

    public int tailMovement() {
        // 90 to -45
        if (!getSwingTail()) {
            return 90;
        }

        return this.tailcount - 45;
    }

    public int mouthMovement() {
        if (!getEating()) {
            return 0;
        }
        if (this.eatcount < 6) {
            return this.eatcount;
        }
        if (this.eatcount < 16) {
            return -this.eatcount + 10;
        }
        return this.eatcount - 20;
    }

    @Override
    public void fall(float f, float f1) {
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        final ItemStack stack = player.getHeldItem(hand);

        if (!stack.isEmpty() && this.isBreedingItem(stack)) {
            if (this.isChild()) {
                if (!player.capabilities.isCreativeMode) stack.shrink(1);
                this.ageUp((int) ((float) (-this.getGrowingAge() / 20) * 0.1F), true);
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GOAT_EAT);
                return true;
            }

            if (this.getGrowingAge() == 0 && this.inLove <= 0) {
                if (!player.capabilities.isCreativeMode) stack.shrink(1);
                this.setInLove(player);
                calm(); // Stop fighting behaviors when given tempt items
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GOAT_EAT);
                return true;
            }
        }

        final Boolean tameResult = this.processTameInteract(player, hand);
        if (tameResult != null) {
            return tameResult;
        }

        if (!stack.isEmpty() && stack.getItem() == Items.BUCKET) {
            if (getType() > 4) {
                setUpset(true);
                setAttackTarget(player);
                return false;
            }
            if (getType() == 1) {
                return false;
            }

            if (!player.capabilities.isCreativeMode) stack.shrink(1);
            player.addItemStackToInventory(new ItemStack(Items.MILK_BUCKET));
            return true;
        }

        return super.processInteract(player, hand);
    }

    public boolean getBleating() {
        return this.bleat && (getAttacking() == 0);
    }

    public void setBleating(boolean flag) {
        this.bleat = flag;
    }

    public int getAttacking() {
        return this.attacking;
    }

    public void setAttacking(int flag) {
        this.attacking = flag;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCSoundEvents.ENTITY_GOAT_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        setBleating(true);
        if (getType() == 1) {
            return MoCSoundEvents.ENTITY_GOAT_AMBIENT_BABY;
        }
        if (getType() > 2 && getType() < 5) {
            return MoCSoundEvents.ENTITY_GOAT_AMBIENT_FEMALE;
        }

        return MoCSoundEvents.ENTITY_GOAT_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_GOAT_DEATH;
    }
    
    // TODO: Add unique step sound
    @Override
    protected void playStepSound(BlockPos pos, Block block) {
        this.playSound(SoundEvents.ENTITY_SHEEP_STEP, 0.15F, 1.0F);
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        if (!getIsAdult()) {
            return null;
        }

        return MoCLootTables.GOAT;
    }

    @Override
    public int getMaxAge() {
        return 50; //so the update is not handled on MoCEntityAnimal
    }

    @Override
    public float getAIMoveSpeed() {
        return 0.15F;
    }

    public float getEyeHeight() {
        return this.height * 0.945F;
    }
}
