/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityAnimal;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.init.MoCLootTables;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoCEntityDuck extends MoCEntityAnimal {

    // TODO: Figure out what these values are supposed to be named
    public float field_70886_e = 0.0F;
    public float destPos = 0.0F;
    public float field_70884_g;
    public float field_70888_h;
    public float field_70889_i = 1.0F;

    public MoCEntityDuck(World world) {
        super(world);
        this.texture = "duck.png";
        setSize(0.4F, 0.7F);
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.4D));
        this.tasks.addTask(5, new EntityAIWanderMoC2(this, 1.0D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(4.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    }

    // TODO: Add proper death sound event
    @Override
    protected SoundEvent getDeathSound() {
        return MoCreatures.proxy.legacyDuckSounds ? MoCSoundEvents.ENTITY_DUCK_HURT_LEGACY : MoCSoundEvents.ENTITY_DUCK_HURT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCreatures.proxy.legacyDuckSounds ? MoCSoundEvents.ENTITY_DUCK_HURT_LEGACY : MoCSoundEvents.ENTITY_DUCK_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return MoCreatures.proxy.legacyDuckSounds ? MoCSoundEvents.ENTITY_DUCK_AMBIENT_LEGACY : MoCSoundEvents.ENTITY_DUCK_AMBIENT;
    }
    
    // TODO: Add unique step sound
    @Override
    protected void playStepSound(BlockPos pos, Block blockIn) {
        this.playSound(MoCSoundEvents.ENTITY_DUCK_STEP, 0.15F, 1.0F);
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return MoCLootTables.DUCK;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        this.field_70888_h = this.field_70886_e;
        this.field_70884_g = this.destPos;
        this.destPos = (float) (this.destPos + (this.onGround ? -1 : 4) * 0.3D);

        if (this.destPos < 0.0F) {
            this.destPos = 0.0F;
        }

        if (this.destPos > 1.0F) {
            this.destPos = 1.0F;
        }

        if (!this.onGround && this.field_70889_i < 1.0F) {
            this.field_70889_i = 1.0F;
        }

        this.field_70889_i = (float) (this.field_70889_i * 0.9D);

        if (!this.onGround && this.motionY < 0.0D) {
            this.motionY *= 0.6D;
        }

        this.field_70886_e += this.field_70889_i * 2.0F;
    }

    @Override
    public void fall(float f, float f1) {
    }

    public float getEyeHeight() {
        return this.height * 0.945F;
    }
}
