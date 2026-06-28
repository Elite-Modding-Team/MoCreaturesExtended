/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.init.MoCItems;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class MoCItemArmor extends ItemArmor {
    public MoCItemArmor(ItemArmor.ArmorMaterial materialIn, int renderIndex, EntityEquipmentSlot equipmentSlotIn) {
        super(materialIn, renderIndex, equipmentSlotIn);
        this.setCreativeTab(MoCreatures.tabMoC);
    }

    @Override
    public String getArmorTexture(ItemStack itemstack, Entity entity, EntityEquipmentSlot slot, String type) {
        String tempArmorTexture = "croc_1.png";

        if ((itemstack.getItem() == MoCItems.helmetCroc) || (itemstack.getItem() == MoCItems.plateCroc) || (itemstack.getItem() == MoCItems.bootsCroc)) {
            tempArmorTexture = "croc_1.png";
        }

        if (itemstack.getItem() == MoCItems.legsCroc) {
            tempArmorTexture = "croc_2.png";
        }

        if ((itemstack.getItem() == MoCItems.helmetFur) || (itemstack.getItem() == MoCItems.chestFur) || (itemstack.getItem() == MoCItems.bootsFur)) {
            tempArmorTexture = "fur_1.png";
        }

        if (itemstack.getItem() == MoCItems.legsFur) {
            tempArmorTexture = "fur_2.png";
        }

        if ((itemstack.getItem() == MoCItems.helmetHide) || (itemstack.getItem() == MoCItems.chestHide) || (itemstack.getItem() == MoCItems.bootsHide)) {
            tempArmorTexture = "hide_1.png";
        }

        if (itemstack.getItem() == MoCItems.legsHide) {
            tempArmorTexture = "hide_2.png";
        }

        if ((itemstack.getItem() == MoCItems.scorpHelmetDirt) || (itemstack.getItem() == MoCItems.scorpPlateDirt) || (itemstack.getItem() == MoCItems.scorpBootsDirt)) {
            tempArmorTexture = "scorpd_1.png";
        }

        if (itemstack.getItem() == MoCItems.scorpLegsDirt) {
            tempArmorTexture = "scorpd_2.png";
        }

        if ((itemstack.getItem() == MoCItems.scorpHelmetFrost) || (itemstack.getItem() == MoCItems.scorpPlateFrost) || (itemstack.getItem() == MoCItems.scorpBootsFrost)) {
            tempArmorTexture = "scorpf_1.png";
        }

        if (itemstack.getItem() == MoCItems.scorpLegsFrost) {
            tempArmorTexture = "scorpf_2.png";
        }

        if ((itemstack.getItem() == MoCItems.scorpHelmetCave) || (itemstack.getItem() == MoCItems.scorpPlateCave) || (itemstack.getItem() == MoCItems.scorpBootsCave)) {
            tempArmorTexture = "scorpc_1.png";
        }

        if (itemstack.getItem() == MoCItems.scorpLegsCave) {
            tempArmorTexture = "scorpc_2.png";
        }

        if ((itemstack.getItem() == MoCItems.scorpHelmetNether) || (itemstack.getItem() == MoCItems.scorpPlateNether) || (itemstack.getItem() == MoCItems.scorpBootsNether)) {
            tempArmorTexture = "scorpn_1.png";
        }

        if (itemstack.getItem() == MoCItems.scorpLegsNether) {
            tempArmorTexture = "scorpn_2.png";
        }

        if ((itemstack.getItem() == MoCItems.scorpHelmetUndead) || (itemstack.getItem() == MoCItems.scorpPlateUndead) || (itemstack.getItem() == MoCItems.scorpBootsUndead)) {
            tempArmorTexture = "scorpu_1.png";
        }

        if (itemstack.getItem() == MoCItems.scorpLegsUndead) {
            tempArmorTexture = "scorpu_2.png";
        }

        if ((itemstack.getItem() == MoCItems.helmetSilver) || (itemstack.getItem() == MoCItems.chestSilver) || (itemstack.getItem() == MoCItems.bootsSilver)) {
            tempArmorTexture = "silver_1.png";
        }

        if (itemstack.getItem() == MoCItems.legsSilver) {
            tempArmorTexture = "silver_2.png";
        }

        return MoCreatures.proxy.getArmorTexture(tempArmorTexture).toString();
    }

    /**
     * Called to tick armor in the armor slot. Override to do something
     */
    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        if (!MoCreatures.proxy.armorSetEffects) return;

        Item boots = player.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem(); // Boots
        Item legs = player.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem(); // Leggings
        Item plate = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem(); // Chestplate
        Item helmet = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem(); // Helmet

        // Dark Scorpion Armor Set Effect - Night Vision
        if (boots == MoCItems.scorpBootsCave && legs == MoCItems.scorpLegsCave && plate == MoCItems.scorpPlateCave && helmet == MoCItems.scorpHelmetCave) {
            player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 2, 0, true, false));
            return;
        }

        // Fire Scorpion Armor Set Effect - Fire Resistance
        if (boots == MoCItems.scorpBootsNether && legs == MoCItems.scorpLegsNether && plate == MoCItems.scorpPlateNether && helmet == MoCItems.scorpHelmetNether) {
            player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 2, 0, true, false));
            return;
        }

        // Frost Scorpion Armor Set Effect - Resistance
        if (boots == MoCItems.scorpBootsFrost && legs == MoCItems.scorpLegsFrost && plate == MoCItems.scorpPlateFrost && helmet == MoCItems.scorpHelmetFrost) {
            player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 2, 0, true, false));
            return;
        }

        // Earth Scorpion Armor Set Effect - Speed II
        if (boots == MoCItems.scorpBootsDirt && legs == MoCItems.scorpLegsDirt && plate == MoCItems.scorpPlateDirt && helmet == MoCItems.scorpHelmetDirt) {
            player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 2, 1, true, false));
        }

        // Undead Scorpion Armor Set Effect - Strength
        if (boots == MoCItems.scorpBootsUndead && legs == MoCItems.scorpLegsUndead && plate == MoCItems.scorpPlateUndead && helmet == MoCItems.scorpHelmetUndead) {
            player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 2, 0, true, false));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if ((this == MoCItems.scorpHelmetDirt) || (this == MoCItems.scorpPlateDirt) || (this == MoCItems.scorpLegsDirt) || (this == MoCItems.scorpBootsDirt)) {
            tooltip.add(new TextComponentTranslation("info.mocreatures.setbonus").setStyle(new Style().setColor(TextFormatting.GRAY)).getFormattedText());
            tooltip.add(" " + new TextComponentTranslation("info.mocreatures.setbonusscorp1").setStyle(new Style().setColor(TextFormatting.BLUE)).getFormattedText());
        }

        if ((this == MoCItems.scorpHelmetFrost) || (this == MoCItems.scorpPlateFrost) || (this == MoCItems.scorpLegsFrost) || (this == MoCItems.scorpBootsFrost)) {
            tooltip.add(new TextComponentTranslation("info.mocreatures.setbonus").setStyle(new Style().setColor(TextFormatting.GRAY)).getFormattedText());
            tooltip.add(" " + new TextComponentTranslation("info.mocreatures.setbonusscorp2").setStyle(new Style().setColor(TextFormatting.BLUE)).getFormattedText());
        }

        if ((this == MoCItems.scorpHelmetNether) || (this == MoCItems.scorpPlateNether) || (this == MoCItems.scorpLegsNether) || (this == MoCItems.scorpBootsNether)) {
            tooltip.add(new TextComponentTranslation("info.mocreatures.setbonus").setStyle(new Style().setColor(TextFormatting.GRAY)).getFormattedText());
            tooltip.add(" " + new TextComponentTranslation("info.mocreatures.setbonusscorp3").setStyle(new Style().setColor(TextFormatting.BLUE)).getFormattedText());
        }

        if ((this == MoCItems.scorpHelmetCave) || (this == MoCItems.scorpPlateCave) || (this == MoCItems.scorpLegsCave) || (this == MoCItems.scorpBootsCave)) {
            tooltip.add(new TextComponentTranslation("info.mocreatures.setbonus").setStyle(new Style().setColor(TextFormatting.GRAY)).getFormattedText());
            tooltip.add(" " + new TextComponentTranslation("info.mocreatures.setbonusscorp4").setStyle(new Style().setColor(TextFormatting.BLUE)).getFormattedText());
        }

        if ((this == MoCItems.scorpHelmetUndead) || (this == MoCItems.scorpPlateUndead) || (this == MoCItems.scorpLegsUndead) || (this == MoCItems.scorpBootsUndead)) {
            tooltip.add(new TextComponentTranslation("info.mocreatures.setbonus").setStyle(new Style().setColor(TextFormatting.GRAY)).getFormattedText());
            tooltip.add(" " + new TextComponentTranslation("info.mocreatures.setbonusscorp5").setStyle(new Style().setColor(TextFormatting.BLUE)).getFormattedText());
        }
    }
}
