/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

import com.google.common.collect.Sets;

import java.util.List;
import java.util.Set;

public class MoCItemMattock extends ItemPickaxe {
    private int specialWeaponType = 0;

    public MoCItemMattock(Item.ToolMaterial material, float damage, float speed) {
        this(0, material, damage, speed);
    }

    public MoCItemMattock(int meta, Item.ToolMaterial material, float damage, float speed) {
        super(material);
        this.setCreativeTab(MoCreatures.tabMoC);
        this.attackSpeed = -4.0F + speed;
    }

    public MoCItemMattock(Item.ToolMaterial material, float damage, float speed, int damageType) {
        this(material, damage, speed);
        this.specialWeaponType = damageType;
    }

    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack itemstack = player.getHeldItem(hand);

        IBlockState iblockstate = worldIn.getBlockState(pos);
        Block block = iblockstate.getBlock();

        if (facing != EnumFacing.DOWN && worldIn.isAirBlock(pos.up())) {
            if (!player.isSneaking() && block == Blocks.FARMLAND || player.isSneaking() && block == Blocks.GRASS_PATH) {
                this.setBlock(itemstack, player, worldIn, pos, Blocks.DIRT.getDefaultState());
                return EnumActionResult.SUCCESS;
            }
        }

        if (!player.isSneaking())
            return Items.IRON_SHOVEL.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
        return Items.IRON_HOE.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }

    protected void setBlock(ItemStack stack, EntityPlayer player, World worldIn, BlockPos pos, IBlockState state) {
        worldIn.playSound(player, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);

        if (!worldIn.isRemote) {
            worldIn.setBlockState(pos, state, 11);
            stack.damageItem(1, player);
        }
    }

    @Override
    public Set<String> getToolClasses(ItemStack stack) {
        return Sets.newHashSet("pickaxe", "shovel");
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        if (MoCreatures.proxy.weaponEffects) {
            int timer = 10; // In seconds
            switch (this.specialWeaponType) {
                case 1: // Poison 2
                    target.addPotionEffect(new PotionEffect(MobEffects.POISON, timer * 20, 1));
                    break;
                case 2: // Slowness
                    target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, timer * 20, 0));
                    break;
                case 3: // Fire
                    target.setFire(timer);
                    break;
                case 4: // Weakness (Nausea for players)
                    target.addPotionEffect(new PotionEffect(target instanceof EntityPlayer ? MobEffects.NAUSEA : MobEffects.WEAKNESS, timer * 20, 0));
                    break;
                case 5: // Wither (Blindness for players)
                    target.addPotionEffect(new PotionEffect(target instanceof EntityPlayer ? MobEffects.BLINDNESS : MobEffects.WITHER, timer * 20, 0));
                    break;
                default:
                    break;
            }
        }

        stack.damageItem(1, attacker);
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (MoCreatures.proxy.weaponEffects) {
            switch (this.specialWeaponType) {
                case 1: // Poison 2
                    tooltip.add(TextFormatting.BLUE + I18n.format("info." + MoCConstants.MOD_ID + ".sting_weapon_dirt", 10));
                    break;
                case 2: // Slowness
                    tooltip.add(TextFormatting.BLUE + I18n.format("info." + MoCConstants.MOD_ID + ".sting_weapon_frost", 10));
                    break;
                case 3: // Fire
                    tooltip.add(TextFormatting.BLUE + I18n.format("info." + MoCConstants.MOD_ID + ".sting_weapon_fire", 10));
                    break;
                case 4: // Weakness (Nausea for players)
                    tooltip.add(TextFormatting.BLUE + I18n.format("info." + MoCConstants.MOD_ID + ".sting_weapon_cave", 10));
                    break;
                case 5: // Wither (Blindness for players)
                    tooltip.add(TextFormatting.BLUE + I18n.format("info." + MoCConstants.MOD_ID + ".sting_weapon_undead", 10));
                    break;
                default:
                    break;
            }
        }
    }
}
