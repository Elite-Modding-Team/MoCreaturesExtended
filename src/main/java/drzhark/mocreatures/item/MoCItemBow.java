/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCreatures;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.stats.StatList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class MoCItemBow extends ItemBow {
    public float damageMult;
    public float velocityMult;
    public float inaccuracy;
    public float drawTimeMult;
    public Ingredient repairMaterial;

    public MoCItemBow(int durability, float damageMult, float velocityMult, float drawTimeMult, float inaccuracy, Ingredient repairMaterial) {
        this.setCreativeTab(MoCreatures.tabMoC);
        this.maxStackSize = 1;
        this.setMaxDamage(durability);
        this.damageMult = damageMult;
        this.velocityMult = velocityMult;
        this.drawTimeMult = drawTimeMult;
        this.inaccuracy = inaccuracy;
        this.repairMaterial = repairMaterial;
        this.addPropertyOverride(new ResourceLocation("pull"), (ItemStack bow, World world, EntityLivingBase entity) -> {
            if (entity == null) {
            	return 0.0F;
            }

            float drawTime = 20.0F * drawTimeMult;

            return (this.getMaxItemUseDuration(bow) - entity.getItemInUseCount()) / drawTime;
        });
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack itemStack, World world, EntityLivingBase entityLiving, int timeInUse) {
        if (entityLiving instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entityLiving;
            boolean isInfinityEnchant = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, itemStack) > 0;
            ItemStack stack = this.findAmmo(player);

            float chargeDivider = 1 * drawTimeMult;

            int charge = (int) ((this.getMaxItemUseDuration(itemStack) - timeInUse) / chargeDivider);
            charge = ForgeEventFactory.onArrowLoose(itemStack, world, player, charge, !stack.isEmpty() || isInfinityEnchant);
            if (charge < 0) return;

            if ((!stack.isEmpty() || isInfinityEnchant)) {
                if (stack.isEmpty()) {
                	stack = new ItemStack(Items.ARROW);
                }

                float arrowVelocity = getArrowVelocity(charge);

                if ((double) arrowVelocity >= 0.1D) {
                    boolean arrowInfinite = player.capabilities.isCreativeMode || (stack.getItem() instanceof ItemArrow && ((ItemArrow) stack.getItem()).isInfinite(stack, itemStack, player));

                    if (!world.isRemote) {
                        ItemArrow itemArrow = (ItemArrow) (stack.getItem() instanceof ItemArrow ? stack.getItem() : Items.ARROW);
                        EntityArrow entityArrow = itemArrow.createArrow(world, stack, player);
                        entityArrow = this.customizeArrow(entityArrow);
                        entityArrow.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, (arrowVelocity * 3.0F) * velocityMult, inaccuracy);

                        if (arrowVelocity == 1.0F) {
                            entityArrow.setIsCritical(true);
                        }

                        entityArrow.setDamage(entityArrow.getDamage() * damageMult);

                        int power = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, itemStack);

                        if (power > 0) {
                            entityArrow.setDamage(entityArrow.getDamage() + (double) power * 0.5D + 0.5D);
                        }

                        int punch = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, itemStack);

                        if (punch > 0) {
                            entityArrow.setKnockbackStrength(punch);
                        }

                        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, itemStack) > 0) {
                            entityArrow.setFire(100);
                        }

                        itemStack.damageItem(1, player);

                        if (arrowInfinite || player.capabilities.isCreativeMode && (stack.getItem() == Items.SPECTRAL_ARROW || stack.getItem() == Items.TIPPED_ARROW)) {
                            entityArrow.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
                        }

                        world.spawnEntity(entityArrow);
                    }

                    world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + arrowVelocity * 0.5F);

                    if (!arrowInfinite && !player.capabilities.isCreativeMode) {
                    	stack.shrink(1);

                        if (stack.isEmpty()) {
                            player.inventory.deleteStack(stack);
                        }
                    }

                    player.addStat(StatList.getObjectUseStats(this));
                }
            }
        }
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return repairMaterial.test(repair) || super.getIsRepairable(toRepair, repair);
    }
}
