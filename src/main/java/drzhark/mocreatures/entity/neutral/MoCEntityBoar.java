/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.neutral;

import com.google.common.collect.Sets;
import drzhark.mocreatures.entity.MoCEntityAnimal;
import drzhark.mocreatures.entity.ai.EntityAIFleeFromPlayer;
import drzhark.mocreatures.entity.ai.EntityAIFollowAdult;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.init.MoCLootTables;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Set;

public class MoCEntityBoar extends MoCEntityAnimal {
    private static final Set<Item> BREEDING_ITEMS = Sets.newHashSet(new ItemStack(Blocks.BROWN_MUSHROOM).getItem(),
            new ItemStack(Blocks.RED_MUSHROOM).getItem());

    public MoCEntityBoar(World world) {
        super(world);
        setSize(0.9F, 0.9F);
        setAdult(true);
        setAge(60);
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(3, new EntityAITempt(this, 1.1D, false, BREEDING_ITEMS));
        this.tasks.addTask(4, new EntityAIFleeFromPlayer(this, 1.0D, 4D));
        this.tasks.addTask(5, new EntityAIAttackMelee(this, 1.2D, false));
        this.tasks.addTask(6, new EntityAIFollowAdult(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWanderMoC2(this, 1.0D));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.5D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
    }

    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData entityLivingData) {
        if (this.rand.nextInt(4) == 0) {
            this.setGrowingAge(-24000);
            this.setAdult(false);
        }
        return super.onInitialSpawn(difficulty, entityLivingData);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return !stack.isEmpty() && BREEDING_ITEMS.contains(stack.getItem());
    }

    @Override
    public EntityAgeable createChild(EntityAgeable entity) {
        MoCEntityBoar baby = new MoCEntityBoar(entity.world);
        baby.setGrowingAge(-24000);
        baby.setAdult(false);
        return baby;
    }

    @Override
    public boolean canMateWith(EntityAnimal otherAnimal) {
        if (otherAnimal == this) {
            return false;
        } else if (otherAnimal.getClass() != this.getClass()) {
            return false;
        } else {
            return this.isInLove() && otherAnimal.isInLove();
        }
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);

        if (!stack.isEmpty() && this.isBreedingItem(stack)) {
            if (this.isChild()) {
                this.consumeItemFromStack(player, stack);
                this.ageUp((int) ((float) (-this.getGrowingAge() / 20) * 0.1F), true);
                //MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_EAT);
                return true;
            }

            if (this.getGrowingAge() == 0 && this.inLove <= 0) {
                this.consumeItemFromStack(player, stack);
                this.setInLove(player);
                this.setAttackTarget(null);
                //MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_EAT);
                return true;
            }
        }

        return super.processInteract(player, hand);
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (super.attackEntityFrom(damagesource, i)) {
            Entity entity = damagesource.getTrueSource();
            if (entity != null && this.isRidingOrBeingRiddenBy(entity)) {
                return true;
            }
            if (entity != this && entity instanceof EntityLivingBase && super.shouldAttackPlayers() && getIsAdult()) {
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
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_PIG_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_PIG_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_PIG_DEATH;
    }

    // TODO: Add unique sound event
    @Override
    protected void playStepSound(BlockPos pos, Block blockIn) {
        this.playSound(SoundEvents.ENTITY_PIG_STEP, 0.15F, 1.0F);
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        if (!getIsAdult()) {
            return null;
        }

        return MoCLootTables.BOAR;
    }

    @Override
    public boolean canAttackTarget(EntityLivingBase entity) {
        return !(entity instanceof MoCEntityBoar) && super.canAttackTarget(entity);
    }

    @Override
    public boolean isReadyToHunt() {
        return this.getIsAdult() && !this.isMovementCeased();
    }

    @Override
    public float getSizeFactor() {
        if (getIsAdult()) {
            return 1F;
        }
        return getAge() * 0.01F;
    }

    public float getEyeHeight() {
        return this.height * 0.75F;
    }
}
