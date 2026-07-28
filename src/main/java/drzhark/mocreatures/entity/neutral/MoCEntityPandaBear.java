/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.neutral;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.hunter.MoCEntityBear;
import drzhark.mocreatures.entity.tameable.IMoCTameable;
import drzhark.mocreatures.init.MoCItems;
import drzhark.mocreatures.init.MoCLootTables;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoCEntityPandaBear extends MoCEntityBear {

    public MoCEntityPandaBear(World world) {
        super(world);
        setSize(0.8F, 1.05F);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    }

    @Override
    public void selectType() {
        if (getType() == 0) {
            setType(1);
        }
        super.selectType();
    }

    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.proxy.lowResolutionTextures ? MoCreatures.proxy.getModelTexture("16x/bear_panda.png") : MoCreatures.proxy.getModelTexture("bear_panda.png");
    }

    @Override
    public float getBearSize() {
        return 0.8F;
    }

    @Override
    public int getMaxAge() {
        return 80;
    }

    @Override
    public boolean isReadyToHunt() {
        return false;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        return super.attackEntityFrom(damagesource, i);
    }

    @Override
    public boolean shouldAttackPlayers() {
        return false;
    }

    @Override
    public boolean isMyFavoriteFood(ItemStack stack) {
        return this.getType() == 3 && !stack.isEmpty() && stack.getItem() == Items.REEDS;
    }

    @Override
    public boolean isMyHealFood(ItemStack stack) {
        return this.getType() == 3 && !stack.isEmpty() && stack.getItem() == Items.REEDS;
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        final Boolean tameResult = this.processTameInteract(player, hand);
        if (tameResult != null) {
            return tameResult;
        }

        final ItemStack stack = player.getHeldItem(hand);
        if (!stack.isEmpty() && (stack.getItem() == MoCItems.sugarlump || stack.getItem() == Items.REEDS)) {
            if (!player.capabilities.isCreativeMode) stack.shrink(1);

            if (!this.world.isRemote) {
                MoCTools.tameWithName(player, this);
            }

            this.setHealth(getMaxHealth());
            eatingAnimal();
            if (!this.world.isRemote && !getIsAdult() && (getAge() < 100)) {
                setAge(getAge() + 1);
            }

            return true;
        }
        if (!stack.isEmpty() && getIsTamed() && (stack.getItem() == MoCItems.whip)) {
            this.processBearWhipped();
            return true;
        }
        if (this.getIsRideable() && this.getIsAdult() && (!this.getIsChested() || !player.isSneaking()) && !this.isBeingRidden()) {
            if (!this.world.isRemote && player.startRiding(this)) {
                player.rotationYaw = this.rotationYaw;
                player.rotationPitch = this.rotationPitch;
                setBearState(0);
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

        return MoCLootTables.PANDA_BEAR;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        /*
         * panda bears and cubs will sit down sometimes
         */
        if (!this.world.isRemote && getBearState() != 3 && !getIsTamed() && this.rand.nextInt(300) == 0) {
            setBearState(2);
        }
    }

    @Override
    public String getOffspringClazz(IMoCTameable mate) {
        return "PandaBear";
    }

    @Override
    public int getOffspringTypeInt(IMoCTameable mate) {
        return 1;
    }

    @Override
    public boolean compatibleMate(Entity mate) {
        return mate instanceof MoCEntityPandaBear;
    }

    // TODO: Change depending on whether it's sitting or not
    public float getEyeHeight() {
        return this.height * 0.76F;
    }
}
