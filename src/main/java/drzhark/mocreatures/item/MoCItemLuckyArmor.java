/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.item;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;

import com.google.common.collect.Multimap;

public class MoCItemLuckyArmor extends MoCItemArmor {
    public AttributeModifier luck;

    public MoCItemLuckyArmor(float luck, ItemArmor.ArmorMaterial materialIn, int renderIndex, EntityEquipmentSlot equipmentSlotIn) {
        super(materialIn, renderIndex, equipmentSlotIn);
        this.luck = new AttributeModifier("F34BB326-D435-4B63-8254-0B6CB57A8E6F", (double) luck, 0);
    }

    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

        if (equipmentSlot == this.armorType) {
            multimap.put(SharedMonsterAttributes.LUCK.getName(), this.luck);
        }

        return multimap;
    }
}
