/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.hunter;

import drzhark.mocreatures.entity.tameable.IMoCTameable;
import drzhark.mocreatures.init.MoCItems;
import drzhark.mocreatures.init.MoCLootTables;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoCEntityTiger extends MoCEntityBigCat {

    public MoCEntityTiger(World world) {
        super(world);
        setSize(1.25F, 1.275F);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    }

    @Override
    public void selectType() {
        if (getType() == 0) {
            if (this.rand.nextInt(20) == 0) {
                setType(2);
            } else {
                setType(1);
            }
        }
        super.selectType();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(calculateMaxHealth());
        this.setHealth(getMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(calculateAttackDmg());
    }

    @Override
    public boolean isFlyer() {
        return this.getType() == 3;
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        final Boolean tameResult = this.processTameInteract(player, hand);
        if (tameResult != null) {
            return tameResult;
        }

        final ItemStack stack = player.getHeldItem(hand);
        if (!stack.isEmpty() && getIsTamed() && getType() == 2 && (stack.getItem() == MoCItems.essencelight)) {
            if (!player.capabilities.isCreativeMode) stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, new ItemStack(Items.GLASS_BOTTLE));
            } else {
                player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
            }
            setType(3);
            return true;
        }
        if (this.getIsRideable() && this.getIsAdult() && (!this.getIsChested() || !player.isSneaking()) && !this.isBeingRidden()) {
            if (!this.world.isRemote && player.startRiding(this)) {
                player.rotationYaw = this.rotationYaw;
                player.rotationPitch = this.rotationPitch;
                setSitting(false);
            }

            return true;
        }

        return super.processInteract(player, hand);
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        if (!getIsAdult()) {
            return null;
        }

        return MoCLootTables.TIGER;
    }

    @Override
    public String getOffspringClazz(IMoCTameable mate) {
        if (mate instanceof MoCEntityLion && mate.getType() == 2) {
            return "Liger";
        }
        if (mate instanceof MoCEntityPanther && mate.getType() == 1) {
            return "Panthger";
        }
        if (mate instanceof MoCEntityLeopard && mate.getType() == 1) {
            return "Leoger";
        }
        return "Tiger";
    }

    @Override
    public int getOffspringTypeInt(IMoCTameable mate) {
        if (mate instanceof MoCEntityLion && mate.getType() == 2) {
            return 1; // Liger
        }
        if (mate instanceof MoCEntityLeopard && mate.getType() == 1) {
            return 1; // Leoger
        }
        if (mate instanceof MoCEntityPanther && mate.getType() == 1) {
            return 1; // Panthger
        }
        return this.getType();
    }

    @Override
    public boolean compatibleMate(Entity mate) {
        return (mate instanceof MoCEntityTiger && ((MoCEntityTiger) mate).getType() < 3)
                || (mate instanceof MoCEntityLion && ((MoCEntityLion) mate).getType() == 2)
                || (mate instanceof MoCEntityLeopard && ((MoCEntityLeopard) mate).getType() == 1)
                || (mate instanceof MoCEntityPanther && ((MoCEntityPanther) mate).getType() == 1);
    }

    @Override
    public boolean readytoBreed() {
        return this.getType() < 3 && super.readytoBreed();
    }

    public double calculateMaxHealth() {
        // White Tiger
        if (this.getType() == 2 || this.getType() == 3) {
            return 40.0D;
        }
        // Orange Tiger
        else {
            return 35.0D;
        }
    }

    public double calculateAttackDmg() {
        // White Tiger
        if (this.getType() == 2 || this.getType() == 3) {
            return 7.5D;
        }
        // Orange Tiger
        return 7.0D;
    }

    @Override
    public int getMaxAge() {
        // White Tiger
        if (this.getType() == 2 || this.getType() == 3) {
            return 130;
        }
        // Orange Tiger
        return 120;
    }

    @Override
    public boolean canAttackTarget(EntityLivingBase entity) {
        if (!this.getIsAdult() && (this.getAge() < this.getMaxAge() * 0.8)) {
            return false;
        }
        if (entity instanceof MoCEntityTiger) {
            return false;
        }
        return entity.height < 2F && entity.width < 2F;
    }

    public float getEyeHeight() {
        return this.height * 0.92F;
    }
}
