/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.item;

import java.util.List;

import javax.annotation.Nullable;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.init.MoCItems;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MoCItem extends Item {
    protected String Tooltip = null;

    public MoCItem() {
    }

    public MoCItem(String name) {
        this(name, 0, false);
    }

    public MoCItem(String name, int meta, boolean hasTooltip) {
        this.setCreativeTab(MoCreatures.tabMoC);
        this.setRegistryName(MoCConstants.MOD_ID, name);
        this.setTranslationKey(name);

        if (hasTooltip) {
            this.Tooltip = "info." + MoCConstants.MOD_ID + "." + name;
        }
    }

    public EnumRarity getRarity(ItemStack stack) {
        if (stack.getItem() == MoCItems.horsearmorcrystal) {
            return EnumRarity.RARE;
        }

        return EnumRarity.COMMON;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flag) {
        if (Tooltip != null) {
            list.add(TextFormatting.WHITE + I18n.format(Tooltip));
        }
    }
}
