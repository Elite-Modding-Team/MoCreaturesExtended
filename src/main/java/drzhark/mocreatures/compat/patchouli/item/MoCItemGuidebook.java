package drzhark.mocreatures.compat.patchouli.item;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.item.MoCItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;
import vazkii.patchouli.api.PatchouliAPI;
import vazkii.patchouli.common.base.PatchouliSounds;

import javax.annotation.Nonnull;

public class MoCItemGuidebook extends MoCItem {
    private static final ResourceLocation book = new ResourceLocation(MoCConstants.MOD_ID, "creaturepedia");

    public static boolean isOpen() {
        return book.equals(PatchouliAPI.instance.getOpenBookGui());
    }

    public MoCItemGuidebook() {
        super();
        setMaxStackSize(1);
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);

        if (player instanceof EntityPlayerMP) {
            PatchouliAPI.instance.openBookGUI((EntityPlayerMP) player, book);
            world.playSound(null, player.posX, player.posY, player.posZ, PatchouliSounds.book_open, SoundCategory.PLAYERS, 0.8F, 0.7F + player.getRNG().nextFloat() * 0.4F);
        }

        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }
}
