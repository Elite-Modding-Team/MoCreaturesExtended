/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityAnimal;
import drzhark.mocreatures.entity.hunter.MoCEntityBigCat;
import drzhark.mocreatures.entity.hunter.MoCEntityPetScorpion;
import drzhark.mocreatures.entity.neutral.MoCEntityElephant;
import drzhark.mocreatures.entity.neutral.MoCEntityKitty;
import drzhark.mocreatures.entity.neutral.MoCEntityOstrich;
import drzhark.mocreatures.entity.neutral.MoCEntityWyvern;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class MoCItemWhip extends MoCItemSword {
    private float AttackSpeed;

    public MoCItemWhip(Item.ToolMaterial material, float attackSpeedIn) {
        super(0, material);
        this.maxStackSize = 1;
        this.AttackSpeed = attackSpeedIn;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        final ItemStack stack = player.getHeldItem(hand);
        Block block = worldIn.getBlockState(pos).getBlock();
        Block block1 = worldIn.getBlockState(pos.up()).getBlock();
        if (side != EnumFacing.DOWN && (block1 == Blocks.AIR) && (block != Blocks.AIR) && (block != Blocks.STANDING_SIGN)) {
            whipFX(worldIn, pos);
            worldIn.playSound(player, pos, MoCSoundEvents.ENTITY_GENERIC_WHIP, SoundCategory.PLAYERS, 0.5F, 0.4F / ((itemRand.nextFloat() * 0.4F) + 0.8F));
            player.getCooldownTracker().setCooldown(this, 20);
            stack.damageItem(2, player);
            List<Entity> list = worldIn.getEntitiesWithinAABBExcludingEntity(player, player.getEntityBoundingBox().grow(12D));
            for (Entity entity : list) {
                if (entity instanceof MoCEntityAnimal) {
                    MoCEntityAnimal animal = (MoCEntityAnimal) entity;
                    if (MoCreatures.proxy.enableOwnership && animal.getOwnerId() != null && !player.getUniqueID().equals(animal.getOwnerId()) && !MoCTools.isThisPlayerAnOP(player)) {
                        continue;
                    }
                }

                if (entity instanceof MoCEntityBigCat) {
                    MoCEntityBigCat entitybigcat = (MoCEntityBigCat) entity;
                    if (entitybigcat.getIsTamed()) {
                        entitybigcat.setSitting(!entitybigcat.getIsSitting());
                        entitybigcat.setIsJumping(false);
                        entitybigcat.getNavigator().clearPath();
                        entitybigcat.setAttackTarget(null);
                    } else if ((worldIn.getDifficulty().getId() > 0) && entitybigcat.getIsAdult()) {
                        entitybigcat.setAttackTarget(player);
                    }
                }

                if (entity instanceof MoCEntityHorse) {
                    MoCEntityHorse entityhorse = (MoCEntityHorse) entity;
                    if (entityhorse.getIsTamed()) {
                        if (entityhorse.getRidingEntity() == null) {
                            entityhorse.setSitting(!entityhorse.getIsSitting());
                            entityhorse.setIsJumping(false);
                            entityhorse.getNavigator().clearPath();
                            entityhorse.setAttackTarget(null);
                        } else if (entityhorse.isNightmare()) {
                            entityhorse.setNightmareInt(100);
                        } else if (entityhorse.sprintCounter == 0) {
                            entityhorse.sprintCounter = 1;
                        }
                    }
                }

                if ((entity instanceof MoCEntityKitty)) {
                    MoCEntityKitty entitykitty = (MoCEntityKitty) entity;
                    if ((entitykitty.getKittyState() > 2) && entitykitty.whipable()) {
                        entitykitty.setSitting(!entitykitty.getIsSitting());
                        entitykitty.setIsJumping(false);
                        entitykitty.getNavigator().clearPath();
                        entitykitty.setAttackTarget(null);
                    }
                }

                if ((entity instanceof MoCEntityWyvern)) {
                    MoCEntityWyvern entitywyvern = (MoCEntityWyvern) entity;
                    if (entitywyvern.getIsTamed() && entitywyvern.getRidingEntity() == null && !entitywyvern.isOnAir()) {
                        entitywyvern.setSitting(!entitywyvern.getIsSitting());
                        entitywyvern.setIsJumping(false);
                        entitywyvern.getNavigator().clearPath();
                        entitywyvern.setAttackTarget(null);
                    }
                }

                if ((entity instanceof MoCEntityPetScorpion)) {
                    MoCEntityPetScorpion petscorpion = (MoCEntityPetScorpion) entity;
                    if (petscorpion.getIsTamed() && petscorpion.getRidingEntity() == null) {
                        petscorpion.setSitting(!petscorpion.getIsSitting());
                        petscorpion.setIsJumping(false);
                        petscorpion.getNavigator().clearPath();
                        petscorpion.setAttackTarget(null);
                    }
                }

                if (entity instanceof MoCEntityOstrich) {
                    MoCEntityOstrich entityostrich = (MoCEntityOstrich) entity;

                    //makes ridden ostrich sprint
                    if (entityostrich.isBeingRidden() && entityostrich.sprintCounter == 0) {
                        entityostrich.sprintCounter = 1;
                    }

                    //toggles hiding of tamed ostriches
                    if (entityostrich.getIsTamed() && entityostrich.getRidingEntity() == null) {
                        entityostrich.setHiding(!entityostrich.getHiding());
                        entityostrich.setIsJumping(false);
                        entityostrich.getNavigator().clearPath();
                        entityostrich.setAttackTarget(null);
                    }
                }

                if (entity instanceof MoCEntityElephant) {
                    MoCEntityElephant entityelephant = (MoCEntityElephant) entity;

                    //makes elephants charge
                    if (entityelephant.isBeingRidden() && entityelephant.sprintCounter == 0) {
                        entityelephant.sprintCounter = 1;
                    }
                }
            }
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.FAIL;
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        target.playSound(MoCSoundEvents.ENTITY_GENERIC_WHIP, 0.5F, 2.0F / ((itemRand.nextFloat() * 0.4F) + 0.8F));
        return super.hitEntity(stack, target, attacker);
    }

    public void whipFX(World world, BlockPos pos) {
        double d = pos.getX() + 0.5F;
        double d1 = pos.getY() + 1.0F;
        double d2 = pos.getZ() + 0.5F;
        double d3 = 0.2199999988079071D;
        double d4 = 0.27000001072883606D;
        world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d - d4, d1 + d3, d2, 0.0D, 0.0D, 0.0D);
        world.spawnParticle(EnumParticleTypes.FLAME, d - d4, d1 + d3, d2, 0.0D, 0.0D, 0.0D);
        world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d + d4, d1 + d3, d2, 0.0D, 0.0D, 0.0D);
        world.spawnParticle(EnumParticleTypes.FLAME, d + d4, d1 + d3, d2, 0.0D, 0.0D, 0.0D);
        world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d, d1 + d3, d2 - d4, 0.0D, 0.0D, 0.0D);
        world.spawnParticle(EnumParticleTypes.FLAME, d, d1 + d3, d2 - d4, 0.0D, 0.0D, 0.0D);
        world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d, d1 + d3, d2 + d4, 0.0D, 0.0D, 0.0D);
        world.spawnParticle(EnumParticleTypes.FLAME, d, d1 + d3, d2 + d4, 0.0D, 0.0D, 0.0D);
        world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d, d1, d2, 0.0D, 0.0D, 0.0D);
        world.spawnParticle(EnumParticleTypes.FLAME, d, d1, d2, 0.0D, 0.0D, 0.0D);
    }

    /**
     * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
     */
    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
        Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Damage modifier", (double) this.getAttackDamage(), 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Speed modifier", (double) this.AttackSpeed - 4.0D, 0));
        }

        return multimap;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(TextFormatting.BLUE + I18n.format("info." + MoCConstants.MOD_ID + ".whip"));
    }
}
