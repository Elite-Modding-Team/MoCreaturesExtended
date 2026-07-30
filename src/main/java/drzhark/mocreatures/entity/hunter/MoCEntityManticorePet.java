/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.hunter;

import drzhark.mocreatures.init.MoCItems;
import drzhark.mocreatures.init.MoCLootTables;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;

// TODO: Make it comaptible with essences
public class MoCEntityManticorePet extends MoCEntityBigCat {

    public MoCEntityManticorePet(World world) {
        super(world);
        this.chestName = "ManticoreChest";
    }

    // TODO: Varied stats depending on type
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(7.0D);
    }

    @Override
    public void selectType() {

        if (getType() == 0) {
            setType(this.rand.nextInt(5) + 1);
        }
        super.selectType();
    }

    @Override
    public boolean hasMane() {
        return true;
    }

    @Override
    public boolean isFlyer() {
        return true;
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        final Boolean tameResult = this.processTameInteract(player, hand);
        if (tameResult != null) {
            return tameResult;
        }

        final ItemStack stack = player.getHeldItem(hand);
        if (!stack.isEmpty() && getIsTamed() && (stack.getItem() == MoCItems.whip)) {
            setSitting(!getIsSitting());
            setIsJumping(false);
            getNavigator().clearPath();
            setAttackTarget(null);
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

    @Override
    public boolean compatibleMate(Entity mate) {
        return false;
    }

    @Override
    public boolean readytoBreed() {
        return false;
    }

    @Override
    public int getMaxAge() {
        return 130;
    }

    @Override
    public boolean getHasStinger() {
        return true;
    }

    @Override
    public boolean hasSaberTeeth() {
        return true;
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        if (!getIsAdult()) {
            return null;
        }

        switch (getType()) {
            case 2:
                return MoCLootTables.DARK_MANTICORE;
            case 3:
                return MoCLootTables.FROST_MANTICORE;
            case 4:
                return MoCLootTables.TOXIC_MANTICORE;
            case 5:
                return MoCLootTables.PLAIN_MANTICORE;
            default:
                return MoCLootTables.FIRE_MANTICORE;
        }
    }
}
