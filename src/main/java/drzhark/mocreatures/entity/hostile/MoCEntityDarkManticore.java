/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.hostile;

import drzhark.mocreatures.init.MoCLootTables;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoCEntityDarkManticore extends MoCEntityManticore {
    public MoCEntityDarkManticore(World world) {
        super(world);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(35.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.5D);
    }

    @Override
    protected void applyEnchantments(EntityLivingBase entityLivingBaseIn, Entity entityIn) {
        if (!getIsPoisoning() && this.rand.nextInt(5) == 0 && entityIn instanceof EntityLivingBase) {
            setPoisoning(true);
            ((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 15 * 20, 0)); // 15 seconds
            ((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 15 * 20, 0));
        } else {
            openMouth();
        }
        super.applyEnchantments(entityLivingBaseIn, entityIn);
    }

    @Override
    public boolean getCanSpawnHere() {
        return super.getCanSpawnHere() && !this.world.canSeeSky(new BlockPos(this)) && (this.posY < 50.0D);
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return MoCLootTables.DARK_MANTICORE;
    }
}
