package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCreatures;
import net.minecraft.block.Block;
import net.minecraft.item.ItemDoor;

public class MoCItemDoor extends ItemDoor {
    public MoCItemDoor(Block block) {
        super(block);
        this.setCreativeTab(MoCreatures.tabMoC);
    }
}
