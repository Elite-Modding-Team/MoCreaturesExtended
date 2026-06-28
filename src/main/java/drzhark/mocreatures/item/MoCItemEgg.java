/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.item;

import drzhark.mocreatures.entity.item.MoCEntityEgg;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/*
 * ID LIST:
 * [0] Blue Fishy
 * [1] Regal Blue Fishy
 * [2] Orange White Stripe Fishy
 * [3] Light Blue Fishy
 * [4] Green Yellow Fishy
 * [5] Green Fishy
 * [6] Purple Fishy
 * [7] Yellow Fishy
 * [8] Orange Blue Stripe Fishy
 * [9] Black White Fishy
 * [10] Red Fishy
 * [11] Shark
 * [21] Dark Snake
 * [22] Spotted Snake
 * [23] Orange Snake
 * [24] Green Snake
 * [25] Coral Snake
 * [26] Cobra
 * [27] Rattlesnake
 * [28] Python
 * [30] Ostrich
 * [31] Stolen Ostrich
 * [33] Komodo Dragon
 * [41] Earth Scorpion
 * [42] Dark Scorpion
 * [43] Fire Scorpion
 * [44] Frost Scorpion
 * [45] Undead Scorpion
 * [50] Jungle Wyvern (Green)
 * [51] Swamp Wyvern (Lime)
 * [52] Savanna Wyvern (Russet)
 * [53] Sand Wyvern (Yellow)
 * [54] Mother Wyvern (Red)
 * [55] Undead Wyvern
 * [56] Light Wyvern
 * [57] Dark Wyvern
 * [58] Arctic Wyvern (Light Blue)
 * [59] Cave Wyvern (Gray)
 * [60] Mountain Wyvern (Brown)
 * [61] Sea Wyvern (Turquoise)
 * [62] Fire Manticore
 * [63] Dark Manticore
 * [64] Frost Manticore
 * [65] Toxic Manticore
 * [66] Manticore (Plain)
 * [70] Salmon
 * [71] Cod
 * [72] Bass
 * [80] Anchovy
 * [81] Angelfish
 * [82] Anglerfish
 * [83] Clownfish
 * [84] Goldfish
 * [85] Hippo Tang
 * [86] Mandarinfish
 * [90] Piranha
 */

public class MoCItemEgg extends MoCItem {

    public MoCItemEgg() {
    	super();
        this.maxStackSize = 16;
        setHasSubtypes(true);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        final ItemStack stack = player.getHeldItem(hand);
        if (!player.capabilities.isCreativeMode) stack.shrink(1);
        if (!world.isRemote && player.onGround) {
            int i = stack.getItemDamage();
            if (i == 30) {
                i = 31; // For ostrich eggs. Placed eggs become stolen eggs.
            }
            MoCEntityEgg entityegg = new MoCEntityEgg(world, i);
            entityegg.setPosition(player.posX, player.posY, player.posZ);
            player.world.spawnEntity(entityegg);
            entityegg.motionY += world.rand.nextFloat() * 0.05F;
            entityegg.motionX += (world.rand.nextFloat() - world.rand.nextFloat()) * 0.3F;
            entityegg.motionZ += (world.rand.nextFloat() - world.rand.nextFloat()) * 0.3F;
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (!this.isInCreativeTab(tab)) {
            return;
        }

        for (int i = 0; i < 11; i++) { // Fishies
            items.add(new ItemStack(this, 1, i));
        }        

        items.add(new ItemStack(this, 1, 11)); // Shark
        
        for (int i = 21; i < 29; i++) { // Snakes
            items.add(new ItemStack(this, 1, i));
        }
        
        items.add(new ItemStack(this, 1, 30)); // Ostrich
        items.add(new ItemStack(this, 1, 31)); // Ostrich (Stolen)
        items.add(new ItemStack(this, 1, 33)); // Komodo Dragon
        
        for (int i = 41; i < 46; i++) { // Scorpions
            items.add(new ItemStack(this, 1, i));
        }
        
        for (int i = 50; i < 62; i++) { // Wyverns
            items.add(new ItemStack(this, 1, i));
        }
        
        for (int i = 62; i < 67; i++) { // Manticores
            items.add(new ItemStack(this, 1, i));
        }
        
        for (int i = 70; i < 73; i++) { // Medium Fish
            items.add(new ItemStack(this, 1, i));
        }
        
        for (int i = 80; i < 87; i++) { // Small Fish
            items.add(new ItemStack(this, 1, i));
        }
        
        items.add(new ItemStack(this, 1, 90)); // Piranha
    }

    @Override
    public String getTranslationKey(ItemStack itemstack) {
        return getTranslationKey() + "." + itemstack.getItemDamage();
    }
}
