/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.init.MoCItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class MoCItemFood extends ItemFood {
    public int itemUseDuration;
    public boolean alwaysEdible;

    public MoCItemFood(int amount) {
        super(amount, 0.6F, false);
        this.setCreativeTab(MoCreatures.tabMoC);
    }

    public MoCItemFood(int amount, float saturation, boolean isWolfFood) {
        super(amount, saturation, isWolfFood);
        this.setCreativeTab(MoCreatures.tabMoC);
    }

    public MoCItemFood(int amount, float saturation, boolean isWolfFood, int eatingSpeed) {
        super(amount, saturation, isWolfFood);
        this.setCreativeTab(MoCreatures.tabMoC);
        itemUseDuration = eatingSpeed; // 32 by default
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        if (itemUseDuration == 0) {
            return 32;
        }

        return itemUseDuration;
    }

    @Override
    protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
        if (this == MoCItems.mysticPear) {
            player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 10 * 20, 1));
            player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 10 * 20, 1));
        }
        
        if (this == MoCItems.sugarlump) {
            player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 4 * 20, 0));
        }

        super.onFoodEaten(stack, world, player);
    }

    public MoCItemFood setAlwaysEdible() {
        this.alwaysEdible = true;
        return this;
    }
}
