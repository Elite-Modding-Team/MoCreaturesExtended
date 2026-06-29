/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.hunter;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.entity.ai.*;
import drzhark.mocreatures.entity.inventory.MoCAnimalChest;
import drzhark.mocreatures.entity.tameable.MoCEntityTameableAnimal;
import drzhark.mocreatures.init.MoCItems;
import drzhark.mocreatures.init.MoCSoundEvents;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageAnimation;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSaddle;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class MoCEntityBear extends MoCEntityTameableAnimal {

    private static final DataParameter<Integer> BEAR_STATE = EntityDataManager.createKey(MoCEntityBear.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> RIDEABLE = EntityDataManager.createKey(MoCEntityBear.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> CHESTED = EntityDataManager.createKey(MoCEntityBear.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> GHOST = EntityDataManager.createKey(MoCEntityBear.class, DataSerializers.BOOLEAN);
    public int mouthCounter;
    public MoCAnimalChest localchest;
    public ItemStack localstack;
    private int attackCounter;
    private int standingCounter;

    public MoCEntityBear(World world) {
        super(world);
        setSize(1.2F, 1.5F);
        setAge(55);
        setAdult(this.rand.nextInt(4) != 0);
        stepHeight = 1.0F;
        experienceValue = 5;
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIPanicMoC(this, 1.0D));
        this.tasks.addTask(3, new EntityAIFollowOwnerPlayer(this, 1D, 2F, 10F));
        this.tasks.addTask(4, new EntityAIFollowAdult(this, 1.0D));
        this.tasks.addTask(5, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(6, new EntityAIWanderMoC2(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        //this.targetTasks.addTask(2, new EntityAIHunt<>(this, EntityAnimal.class, false));
        this.targetTasks.addTask(3, new EntityAIHunt<>(this, EntityPlayer.class, false));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(20.0D);
    }

    /**
     * Initializes datawatchers for entity. Each datawatcher is used to sync
     * server data to client.
     */
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(BEAR_STATE, 0);
        this.dataManager.register(RIDEABLE, Boolean.FALSE);
        this.dataManager.register(CHESTED, Boolean.FALSE);
        this.dataManager.register(GHOST, Boolean.FALSE);
    }

    /**
     * 0 - bear is on fours (IDLE)
     * 1 - standing (IDLE)
     * 2 - sitting (IDLE)
     * 3 - sitting (FORCED via WHIP)
     */
    public int getBearState() {
        return this.dataManager.get(BEAR_STATE);
    }

    public void setBearState(int i) {
        this.dataManager.set(BEAR_STATE, i);
    }

    @Override
    public boolean getIsSitting() {
        return this.isMovementCeased();
    }
    @Override
    public boolean getIsRideable() {
        return this.dataManager.get(RIDEABLE);
    }

    public boolean getIsChested() {
        return this.dataManager.get(CHESTED);
    }

    public void setIsChested(boolean flag) {
        this.dataManager.set(CHESTED, flag);
    }

    public boolean getIsGhost() {
        return this.dataManager.get(GHOST);
    }

    public void setIsGhost(boolean flag) {
        this.dataManager.set(GHOST, flag);
    }

    public void setRideable(boolean flag) {
        this.dataManager.set(RIDEABLE, flag);
    }

    @Override
    public void selectType() {
        if (getIsAdult()) {
            setAge(getMaxAge());
        }
    }

    /**
     * Returns the factor size for the bear, polars are bigger and pandas
     * smaller
     */
    public float getBearSize() {
        return 1.0F;
    }

    @Override
    protected int getExperiencePoints(EntityPlayer player) {
        return experienceValue;
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        startAttack();
        return super.attackEntityAsMob(entityIn);
    }

    /**
     * Checks if entity is sitting (IDLE or FORCED via WHIP).
     */
    @Override
    public boolean isMovementCeased() {
        return getBearState() == 2 || getBearState() == 3;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (super.attackEntityFrom(damagesource, i)) {
            Entity entity = damagesource.getTrueSource();
            if (entity != null && this.isRidingOrBeingRiddenBy(entity)) {
                return true;
            }
            if (entity != this && entity instanceof EntityLivingBase && super.shouldAttackPlayers()) {
                setAttackTarget((EntityLivingBase) entity);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isNotScared() {
        return getIsAdult();
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.mouthCounter > 0 && ++this.mouthCounter > 20) {
            this.mouthCounter = 0;
        }
        if (this.attackCounter > 0 && ++this.attackCounter > 9) {
            this.attackCounter = 0;
        }
        if (!this.world.isRemote && getBearState() != 3 && !getIsAdult() && getAge() < 80 && (this.rand.nextInt(300) == 0)) {
            setBearState(2); // randomly perform an idle sit
        }
        /*
         * Sitting bears will resume on fours stance every now and then, if not sat via WHIP
         */
        if (!this.world.isRemote && getBearState() == 2 && this.rand.nextInt(800) == 0) {
            setBearState(0);
        }
        if (!this.world.isRemote && getBearState() == 2 && !this.getNavigator().noPath()) {
            setBearState(0);
        }
        if (!this.world.isRemote && this.standingCounter > 0 && ++this.standingCounter > 100 && getBearState() != 3) {
            this.standingCounter = 0;
            setBearState(0);
        }
        /*
         * Standing if close to a vulnerable player
         */
        if (!this.world.isRemote && !getIsTamed() && getIsStanding()
                && !this.isMovementCeased() && getIsAdult() && (this.rand.nextInt(200) == 0) && shouldAttackPlayers()) {
            EntityPlayer entityplayer1 = this.world.getClosestPlayerToEntity(this, 4D);
            if ((entityplayer1 != null && this.canEntityBeSeen(entityplayer1) && !entityplayer1.capabilities.disableDamage)) {
                this.setStand();
                setBearState(1);
            }
        }
        //TODO move to AI
        if (!this.world.isRemote && getType() == 3 && (this.deathTime == 0) && !this.isMovementCeased()) {
            EntityItem entityitem = getClosestItem(this, 12D, Items.REEDS, Items.SUGAR);
            if (entityitem != null) {

                float f = entityitem.getDistance(this);
                if (f > 2.0F) {
                    setPathToEntity(entityitem, f);
                }
                if (f < 2.0F && this.deathTime == 0) {
                    entityitem.setDead();
                    MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_EAT);
                    this.setHealth(getMaxHealth());
                }

            }
        }
    }

    @Override
    public boolean canAttackTarget(EntityLivingBase entity) {
        return !(entity instanceof MoCEntityBear) && entity.height <= 1D && entity.width <= 1D;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_BEAR_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        openMouth();
        return MoCSoundEvents.ENTITY_BEAR_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        openMouth();
        return MoCSoundEvents.ENTITY_BEAR_AMBIENT;
    }
    
    // TODO: Add unique sound event
    @Override
    protected void playStepSound(BlockPos pos, Block blockIn) {
        this.playSound(SoundEvents.ENTITY_POLAR_BEAR_STEP, 0.15F, 1.0F);
    }

    private void openMouth() {
        this.mouthCounter = 1;
    }

    public float getAttackSwing() {
        if (attackCounter == 0)
            return 0;
        return 1.5F + ((attackCounter / 10F) - 10F) * 5F;
    }

    private void startAttack() {
        if (!this.world.isRemote && this.attackCounter == 0 && getBearState() == 1) {
            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 0),
                    new TargetPoint(this.world.provider.getDimensionType().getId(), this.posX, this.posY, this.posZ, 64));
            this.attackCounter = 1;
        }
    }

    @Override
    public void performAnimation(int i) {
        this.attackCounter = 1;
    }

    protected void eatingAnimal() {
        openMouth();
        MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_EAT);
    }

    @Override
    public double getCustomSpeed() {
        if (this.isMovementCeased()) {
            return 0D;
        }
        return super.getCustomSpeed();
    }

    @Override
    public boolean isReadyToHunt() {
        return this.getIsAdult() && !this.isMovementCeased();
    }

    @Override
    public boolean isReadyToFollowOwnerPlayer() { return !this.isMovementCeased(); }

    public boolean getIsStanding() {
        return this.standingCounter != 0;
    }

    public void setStand() {
        this.standingCounter = 1;
    }

    public void processBearWhipped() {
        if (!this.isMovementCeased()) {
            // Set to "SITTING via WHIP"
            setBearState(3);
        } else {
            // Set to "on all fours"
            setBearState(0);
        }
        setIsJumping(false);
        getNavigator().clearPath();
        setAttackTarget(null);
    }
    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        final ItemStack stack = player.getHeldItem(hand);
        if (!stack.isEmpty() && getIsTamed() && (stack.getItem() == MoCItems.whip)) {
            this.processBearWhipped();
            return true;
        }
        final Boolean tameResult = this.processTameInteract(player, hand);
        if (tameResult != null) {
            return tameResult;
        }

        if (!stack.isEmpty() && getIsTamed() && !getIsRideable() && getIsAdult()
                && (stack.getItem() instanceof ItemSaddle)) {
            if (!player.capabilities.isCreativeMode) stack.shrink(1);
            setRideable(true);
            return true;
        }
        if (!stack.isEmpty() && getIsTamed() && (MoCTools.isItemEdibleforCarnivores(stack.getItem()))) {
            if (!player.capabilities.isCreativeMode) stack.shrink(1);
            this.setHealth(getMaxHealth());
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_EAT);
            setIsHunting(false);
            setHasEaten(true);
            return true;
        }
        if (!stack.isEmpty() && getIsTamed() && getIsAdult() && !getIsChested() && (stack.getItem() == Item.getItemFromBlock(Blocks.CHEST))) {
            if (!player.capabilities.isCreativeMode) stack.shrink(1);
            setIsChested(true);
            MoCTools.playCustomSound(this, SoundEvents.ENTITY_CHICKEN_EGG);
            return true;
        }
        if (getIsChested() && player.isSneaking()) {
            if (this.localchest == null) {
                this.localchest = new MoCAnimalChest("BigBearChest", 18);
            }
            if (!this.world.isRemote) {
                player.displayGUIChest(this.localchest);
            }
            return true;
        }

        return super.processInteract(player, hand);
    }

    @Override
    public double getMountedYOffset() {
        double Yfactor = ((0.086D * this.getAge()) - 2.5D) / 10D;
        return this.height * Yfactor;
    }

    @Override
    public int nameYOffset() {
        return (int) (((0.445D * this.getAge()) + 15D) * -1);
    }

    @Override
    public boolean rideableEntity() {
        return true;
    }

    @Override
    public float getSizeFactor() {
        return getAge() * 0.01F;
    }

    @Override
    public void updatePassenger(Entity passenger) {
        double dist = getSizeFactor() * (0.1D);
        double newPosX = this.posX + (dist * Math.sin(this.renderYawOffset / 57.29578F));
        double newPosZ = this.posZ - (dist * Math.cos(this.renderYawOffset / 57.29578F));
        passenger.setPosition(newPosX, this.posY + getMountedYOffset() + passenger.getYOffset(), newPosZ);
    }
    
    /*@Override
    public int nameYOffset() {
        if (getIsAdult()) {
            return (-55);
        }
        return (100 / getAge()) * (-40);
    }*/

    @Override
    public void dropMyStuff() {
        if (!this.world.isRemote) {
            dropArmor();
            MoCTools.dropSaddle(this, this.world);

            if (getIsChested()) {
                MoCTools.dropInventory(this, this.localchest);
                MoCTools.dropCustomItem(this, this.world, new ItemStack(Blocks.CHEST, 1));
                setIsChested(false);
            }
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setBoolean("Saddle", getIsRideable());
        nbttagcompound.setBoolean("Chested", getIsChested());
        nbttagcompound.setBoolean("Ghost", getIsGhost());
        nbttagcompound.setInteger("BearState", getBearState());
        if (getIsChested() && this.localchest != null) {
            NBTTagList nbttaglist = new NBTTagList();
            for (int i = 0; i < this.localchest.getSizeInventory(); i++) {
                // grab the current item stack
                this.localstack = this.localchest.getStackInSlot(i);
                if (!this.localstack.isEmpty()) {
                    NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                    nbttagcompound1.setByte("Slot", (byte) i);
                    this.localstack.writeToNBT(nbttagcompound1);
                    nbttaglist.appendTag(nbttagcompound1);
                }
            }
            nbttagcompound.setTag("Items", nbttaglist);
        }

    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
        super.readEntityFromNBT(nbttagcompound);
        setRideable(nbttagcompound.getBoolean("Saddle"));
        setIsChested(nbttagcompound.getBoolean("Chested"));
        setIsGhost(nbttagcompound.getBoolean("Ghost"));
        setBearState(nbttagcompound.getInteger("BearState"));
        if (getIsChested()) {
            NBTTagList nbttaglist = nbttagcompound.getTagList("Items", 10);
            this.localchest = new MoCAnimalChest("BigBearChest", 18);
            for (int i = 0; i < nbttaglist.tagCount(); i++) {
                NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
                int j = nbttagcompound1.getByte("Slot") & 0xff;
                if (j < this.localchest.getSizeInventory()) {
                    this.localchest.setInventorySlotContents(j, new ItemStack(nbttagcompound1));
                }
            }
        }
    }
}
