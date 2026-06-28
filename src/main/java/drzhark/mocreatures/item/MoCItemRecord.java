/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import net.minecraft.item.ItemRecord;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class MoCItemRecord extends ItemRecord {

    public static ResourceLocation RECORD_SHUFFLE_RESOURCE = new ResourceLocation(MoCConstants.MOD_ID, "music_disc.shuffling");

    public MoCItemRecord(String name, SoundEvent soundEvent) {
        super(name, soundEvent);
        this.setCreativeTab(MoCreatures.tabMoC);
    }
}
