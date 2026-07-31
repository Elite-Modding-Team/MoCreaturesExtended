/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.aquatic;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.entity.tameable.MoCEntityTameableAquatic;
import drzhark.mocreatures.init.MoCLootTables;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoCEntityJellyFish extends MoCEntityTameableAquatic {

    private static final DataParameter<Boolean> GLOWS = EntityDataManager.createKey(MoCEntityJellyFish.class, DataSerializers.BOOLEAN);
    private int poisoncounter;

    public MoCEntityJellyFish(World world) {
        super(world);
        setSize(0.45F, 0.575F);
        // TODO: Make hitboxes adjust depending on size
        //setAge(50 + (this.rand.nextInt(50)));
        setAge(100);
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(5, new EntityAIWanderMoC2(this, 0.5D, 120));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(6.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.15D);
    }

    @Override
    public void selectType() {
        this.setType(this.rand.nextInt(12));
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(GLOWS, Boolean.FALSE);
    }

    public boolean isGlowing() {
        return (this.dataManager.get(GLOWS));
    }

    public void setGlowing(boolean flag) {
        this.dataManager.set(GLOWS, flag);
    }

    @Override
    public float getAIMoveSpeed() {
        return 0.02F;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!this.world.isRemote) {

            if (this.rand.nextInt(200) == 0) {
                setGlowing(!this.world.isDaytime());
            }

            if (!getIsTamed() && ++this.poisoncounter > 250 && (this.shouldAttackPlayers()) && this.rand.nextInt(30) == 0) {
                if (MoCTools.findNearPlayerAndPoison(this, true)) {
                    this.poisoncounter = 0;
                }
            }
        }
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SLIME_ATTACK;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_SLIME_ATTACK;
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return MoCLootTables.JELLYFISH;
    }

    @Override
    public float pitchRotationOffset() {
        if (!this.isInWater()) {
            return 90F;
        }
        return 0F;
    }

    @Override
    public int nameYOffset() {
        return (int) (getAge() * -1 / 2.3);
    }

    @Override
    public float getSizeFactor() {
        float myMoveSpeed = MoCTools.getMyMovementSpeed(this);
        float pulseSpeed = 0.08F;
        if (myMoveSpeed > 0F)
            pulseSpeed = 0.5F;
        float pulseSize = MathHelper.cos(this.ticksExisted * pulseSpeed) * 0.2F;
        return getAge() * 0.01F + (pulseSize / 5);
    }

    @Override
    protected boolean canBeTrappedInNet() {
        return true;
    }

    public float getEyeHeight() {
        return this.height * 0.85F;
    }
}
