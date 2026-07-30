/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.aquatic;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.init.MoCLootTables;
import drzhark.mocreatures.init.MoCSoundEvents;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageAnimation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

import javax.annotation.Nullable;

public class MoCEntityStingRay extends MoCEntityRay {
    private int poisoncounter;
    private int tailCounter;

    public MoCEntityStingRay(World world) {
        super(world);
        setSize(0.7F, 0.3F);
        // TODO: Make hitboxes adjust depending on size
        //setAge(50 + (this.rand.nextInt(40)));
        setAge(90);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return MoCLootTables.STINGRAY;
    }

    @Override
    public boolean isPoisoning() {
        return this.tailCounter != 0;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!this.world.isRemote) {
            if (!getIsTamed() && ++this.poisoncounter > 250 && (this.world.getDifficulty().getId() > 0) && this.rand.nextInt(30) == 0) {
                if (MoCTools.findNearPlayerAndPoison(this, true)) {
                    MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 1),
                            new TargetPoint(this.world.provider.getDimensionType().getId(), this.posX, this.posY, this.posZ, 64));
                    this.poisoncounter = 0;
                }
            }
        } else //client stuff
        {
            if (this.tailCounter > 0 && ++this.tailCounter > 50) {
                this.tailCounter = 0;
            }
        }
    }

    @Override
    public void performAnimation(int animationType) {
        if (animationType == 1) //attacking with tail
        {
            this.tailCounter = 1;
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (super.attackEntityFrom(damagesource, i)) {
            if ((this.world.getDifficulty().getId() == 0)) {
                return true;
            }
            Entity entity = damagesource.getTrueSource();
            if (entity instanceof EntityLivingBase) {
                if (entity != this) {
                    setAttackTarget((EntityLivingBase) entity);
                }
                return true;
            }
        }
        return false;
    }

    public float getEyeHeight() {
        return this.height * 0.86F;
    }
    
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_FISH_DEATH_VICIOUS;
    }
}
