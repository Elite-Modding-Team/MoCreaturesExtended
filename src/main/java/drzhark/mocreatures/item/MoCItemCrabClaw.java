/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.item;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.google.common.collect.Multimap;

import drzhark.mocreatures.init.MoCItems;

import java.util.UUID;

@SuppressWarnings("deprecation")
public class MoCItemCrabClaw extends MoCItem {
	
    protected static final UUID REACH_DISTANCE_MODIFIER = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
    protected static final UUID TOUGHNESS_MODIFIER = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
    public final int armor;
    public final int durability;
    public final int enchantability;
    public final float reach;
    public final float toughness;
    
    public MoCItemCrabClaw(String name, int durability, int enchantability, float toughness, int armor, float reach) {
        super();
        this.setMaxDamage(durability);
        this.maxStackSize = 1;
        this.armor = armor;
        this.durability = durability;
        this.enchantability = enchantability;
        this.reach = reach;
        this.toughness = toughness;
    }
    
    // TODO: Damage claw while placing/destroying blocks or hitting mobs
    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
    	Item itemOffhand = attacker.getHeldItemOffhand().getItem();
    	
        if (itemOffhand instanceof MoCItemCrabClaw) {
        	stack.damageItem(1, attacker);
        }
        
        return true;
    }
    
    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
    	Item itemOffhand = entityLiving.getHeldItemOffhand().getItem();
    	
        if (!worldIn.isRemote && state.getBlockHardness(worldIn, pos) != 0.0D && itemOffhand instanceof MoCItemCrabClaw) {
            stack.damageItem(1, entityLiving);
        }

        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public boolean isFull3D() {
        return true;
    }
    
    @Override
    public int getItemEnchantability() {
        return enchantability;
    }
    
    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
    	return repair.getItem() == MoCItems.animalHide ? true : super.getIsRepairable(toRepair, repair);
    }
    
    @Override
	public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

        if (equipmentSlot == EntityEquipmentSlot.OFFHAND) {
        	multimap.put(SharedMonsterAttributes.ARMOR.getName(), new AttributeModifier(TOUGHNESS_MODIFIER, "Crab claw armor", armor, 0));
        	multimap.put(EntityPlayer.REACH_DISTANCE.getName(), new AttributeModifier(REACH_DISTANCE_MODIFIER, "Crab claw reach", reach, 0));
        	multimap.put(SharedMonsterAttributes.ARMOR_TOUGHNESS.getName(), new AttributeModifier(TOUGHNESS_MODIFIER, "Crab claw toughness", toughness, 0));
        }

        return multimap;
    }
}
