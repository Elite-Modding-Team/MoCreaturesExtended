/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.ambient;

import drzhark.mocreatures.entity.MoCEntityInsect;
import drzhark.mocreatures.init.MoCLootTables;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoCEntityButterfly extends MoCEntityInsect {

    private int fCounter;

    public MoCEntityButterfly(World world) {
        super(world);
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
    }

    @Override
    public void selectType() {
        if (getType() == 0) {
            setType(this.rand.nextInt(10) + 1);
        }
    }

    public float tFloat() {
        if (!getIsFlying()) {
            return 0F;
        }
        if (++this.fCounter > 1000) {
            this.fCounter = 0;
        }

        return MathHelper.cos((this.fCounter * 0.1F)) * 0.2F;
    }

    @Override
    public float getSizeFactor() {
        if (getType() < 8) {
            return 0.7F;
        }
        return 1.0F;
    }

    @Override
    public boolean isMyFavoriteFood(ItemStack stack) {
        return !stack.isEmpty() && (stack.getItem() == Item.getItemFromBlock(Blocks.RED_FLOWER) || stack.getItem() == Item.getItemFromBlock(Blocks.YELLOW_FLOWER));
    }

    @Override
    public boolean isAttractedToLight() {
        return getType() > 7;
    }

    @Override
    public boolean isFlyer() {
        return true;
    }

    @Override
    public float getAIMoveSpeed() {
        if (getIsFlying()) {
            return 0.13F;
        }
        return 0.10F;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return null;
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return MoCLootTables.BUTTERFLY;
    }

    @Override
    public float getEyeHeight() {
        return 0.1F;
    }
    
    @Override
    public int getMaxSpawnedInChunk() {
        return 4;
    }
}
