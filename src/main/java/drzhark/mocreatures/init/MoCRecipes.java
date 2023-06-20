/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.init;

import drzhark.mocreatures.MoCConstants;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.oredict.OreDictionary;

@ObjectHolder(MoCConstants.MOD_ID)
public class MoCRecipes {

    @Mod.EventBusSubscriber(modid = MoCConstants.MOD_ID)
    public static class RegistrationHandler {
        @SubscribeEvent
        public static void registerRecipes(final RegistryEvent.Register<IRecipe> event) {

            GameRegistry.addSmelting(new ItemStack(MoCBlocks.mocLog, 1, 0), new ItemStack(Items.COAL, 1, 1), 0.15F);
            GameRegistry.addSmelting(new ItemStack(MoCBlocks.mocLog, 1, 1), new ItemStack(Items.COAL, 1, 1), 0.15F);

            GameRegistry.addSmelting(MoCItems.crabraw, new ItemStack(MoCItems.crabcooked, 1), 0.35F);
            GameRegistry.addSmelting(MoCItems.ratRaw, new ItemStack(MoCItems.ratCooked, 1), 0.35F);
            GameRegistry.addSmelting(MoCItems.mocegg, new ItemStack(MoCItems.omelet, 1), 0.35F);
            GameRegistry.addSmelting(MoCItems.ostrichraw, new ItemStack(MoCItems.ostrichcooked, 1), 0.35F);
            GameRegistry.addSmelting(MoCItems.rawTurkey, new ItemStack(MoCItems.cookedTurkey, 1), 0.35F);
            GameRegistry.addSmelting(MoCItems.turtleraw, new ItemStack(MoCItems.turtlecooked, 1), 0.35F);

            GameRegistry.addSmelting(Items.EGG, new ItemStack(MoCItems.omelet, 1), 0.35F);

            OreDictionary.registerOre("dirt", new ItemStack(MoCBlocks.mocDirt, 1, 0));
            OreDictionary.registerOre("dirt", new ItemStack(MoCBlocks.mocDirt, 1, 1));
            OreDictionary.registerOre("grass", new ItemStack(MoCBlocks.mocGrass, 1, 0));
            OreDictionary.registerOre("grass", new ItemStack(MoCBlocks.mocGrass, 1, 1));
            OreDictionary.registerOre("logWood", new ItemStack(MoCBlocks.mocLog, 1, 0));
            OreDictionary.registerOre("logWood", new ItemStack(MoCBlocks.mocLog, 1, 1));
            OreDictionary.registerOre("plankWood", new ItemStack(MoCBlocks.mocPlank, 1, 0));
            OreDictionary.registerOre("plankWood", new ItemStack(MoCBlocks.mocPlank, 1, 1));
            OreDictionary.registerOre("stone", new ItemStack(MoCBlocks.mocStone, 1, 0));
            OreDictionary.registerOre("stone", new ItemStack(MoCBlocks.mocStone, 1, 1));
            OreDictionary.registerOre("treeLeaves", new ItemStack(MoCBlocks.mocLeaf, 1, 0));
            OreDictionary.registerOre("treeLeaves", new ItemStack(MoCBlocks.mocLeaf, 1, 1));
            
            OreDictionary.registerOre("egg", new ItemStack(MoCItems.mocegg, 1, OreDictionary.WILDCARD_VALUE));
            OreDictionary.registerOre("record", MoCItems.recordshuffle);
            
            // Pam's HarvestCraft OreDictionaries
            OreDictionary.registerOre("foodCrabcooked", MoCItems.crabcooked);
            OreDictionary.registerOre("foodCrabraw", MoCItems.crabraw);
            OreDictionary.registerOre("foodOmelet", MoCItems.omelet);
            OreDictionary.registerOre("foodOstrichcooked", MoCItems.ostrichcooked);
            OreDictionary.registerOre("foodOstrichraw", MoCItems.ostrichraw);
            OreDictionary.registerOre("foodRatburger", MoCItems.ratBurger);
            OreDictionary.registerOre("foodRatcooked", MoCItems.ratCooked);
            OreDictionary.registerOre("foodRatraw", MoCItems.ratRaw);
            OreDictionary.registerOre("foodTurkeycooked", MoCItems.cookedTurkey);
            OreDictionary.registerOre("listAllegg", new ItemStack(MoCItems.mocegg, 1, OreDictionary.WILDCARD_VALUE));
            OreDictionary.registerOre("listAllmeatcooked", MoCItems.crabcooked);
            OreDictionary.registerOre("listAllmeatcooked", MoCItems.ostrichcooked);
            OreDictionary.registerOre("listAllmeatcooked", MoCItems.ratCooked);
            OreDictionary.registerOre("listAllmeatcooked", MoCItems.cookedTurkey);
            OreDictionary.registerOre("listAllmeatcooked", MoCItems.turtlecooked);
            OreDictionary.registerOre("listAllmeatraw", MoCItems.crabraw);
            OreDictionary.registerOre("listAllmeatraw", MoCItems.ostrichraw);
            OreDictionary.registerOre("listAllmeatraw", MoCItems.ratRaw);
            OreDictionary.registerOre("listAllmeatraw", MoCItems.rawTurkey);
            OreDictionary.registerOre("listAllmeatraw", MoCItems.turtleraw);
            OreDictionary.registerOre("listAllturkeycooked", MoCItems.cookedTurkey);
            OreDictionary.registerOre("listAllturkeyraw", MoCItems.rawTurkey);
            OreDictionary.registerOre("foodTurkeyraw", MoCItems.rawTurkey);
            OreDictionary.registerOre("foodTurtlecooked", MoCItems.turtlecooked);
            OreDictionary.registerOre("foodTurtleraw", MoCItems.turtleraw);
            OreDictionary.registerOre("foodTurtlesoup", MoCItems.turtlesoup);
        }
    }
}
