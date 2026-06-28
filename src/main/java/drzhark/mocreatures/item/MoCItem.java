/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.item;

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

import javax.annotation.Nullable;
import java.util.List;

public class MoCItem extends Item {
    protected String tooltip = null;

    public MoCItem() {
        super();
        this.setCreativeTab(MoCreatures.tabMoC);
    }

    public MoCItem(String name, boolean hasTooltip) {
        super();
        this.setCreativeTab(MoCreatures.tabMoC);

        if (hasTooltip) {
            this.tooltip = "info." + MoCConstants.MOD_ID + "." + name;
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
        if (tooltip != null) {
            list.add(TextFormatting.WHITE + I18n.format(tooltip));
        }
    }
}
