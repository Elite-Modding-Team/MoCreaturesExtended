package drzhark.mocreatures.compat.patchouli;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.init.MoCItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.ItemHandlerHelper;

import vazkii.patchouli.api.PatchouliAPI;

@SuppressWarnings("unused")
public class PatchouliIntegration {
    private static PatchouliAPI.IPatchouliAPI api = null;

    public static void giveBookToPlayer(EntityPlayer player) {
        ItemStack bookStack = getAPI().getBookStack(new ResourceLocation(MoCConstants.MOD_ID, "creaturepedia").toString());
        ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(MoCItems.creaturepedia));
    }

    private static PatchouliAPI.IPatchouliAPI getAPI() {
        if (api == null) {
            api = PatchouliAPI.instance;
            if (api.isStub()) {
                //MoCConstants.LOGGER.warn("Failed to intercept Patchouli API. Issues may occur!");
            }
        }
        return api;
    }
}