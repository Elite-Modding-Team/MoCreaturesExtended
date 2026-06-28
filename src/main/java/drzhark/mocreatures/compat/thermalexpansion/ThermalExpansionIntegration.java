/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.compat.thermalexpansion;

import cofh.core.util.helpers.ItemHelper;
import cofh.thermalexpansion.util.managers.device.FactorizerManager;
import cofh.thermalexpansion.util.managers.device.TapperManager;
import cofh.thermalexpansion.util.managers.machine.CrucibleManager;
import cofh.thermalexpansion.util.managers.machine.InsolatorManager;
import cofh.thermalexpansion.util.managers.machine.PulverizerManager;
import cofh.thermalexpansion.util.managers.machine.SawmillManager;
import cofh.thermalexpansion.util.managers.machine.SmelterManager;
import cofh.thermalfoundation.init.TFFluids;
import cofh.thermalfoundation.item.ItemFertilizer;
import cofh.thermalfoundation.item.ItemMaterial;
import drzhark.mocreatures.init.MoCBlocks;
import drzhark.mocreatures.init.MoCItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

// Courtesy of SokyranTheDragon for a good portion of recipes
public class ThermalExpansionIntegration {

    public static void addRecipes() {
        // Factorizer
        FactorizerManager.addDefaultRecipe(new ItemStack(MoCItems.ancientSilverIngot), new ItemStack(MoCBlocks.ancientSilverBlock), 9);
        FactorizerManager.addDefaultRecipe(new ItemStack(MoCItems.ancientSilverNugget), new ItemStack(MoCItems.ancientSilverIngot), 9);
        FactorizerManager.addDefaultRecipe(new ItemStack(MoCBlocks.silverSand), new ItemStack(MoCBlocks.silverSandstone), 4);
        FactorizerManager.addDefaultRecipe(new ItemStack(MoCItems.firestoneChunk), new ItemStack(MoCBlocks.firestone), 4);

        // Induction Smelter
        int energy = 6000;
        SmelterManager.addRecycleRecipe(energy, new ItemStack(MoCItems.tusksIron), new ItemStack(Items.IRON_INGOT), 2);
        SmelterManager.addRecycleRecipe(energy, new ItemStack(MoCItems.silversword), new ItemStack(MoCItems.ancientSilverIngot), 1);
        SmelterManager.addRecycleRecipe(energy, new ItemStack(MoCItems.silveraxe), new ItemStack(MoCItems.ancientSilverIngot), 1);
        SmelterManager.addRecycleRecipe(energy, new ItemStack(MoCItems.silverMattock), new ItemStack(MoCItems.ancientSilverIngot), 2);
        SmelterManager.addRecycleRecipe(energy, new ItemStack(MoCItems.bo), new ItemStack(MoCItems.ancientSilverIngot), 1);
        SmelterManager.addRecycleRecipe(energy, new ItemStack(MoCItems.katana), new ItemStack(MoCItems.ancientSilverIngot), 1);
        SmelterManager.addRecycleRecipe(energy, new ItemStack(MoCItems.nunchaku), new ItemStack(MoCItems.ancientSilverIngot), 1);
        SmelterManager.addRecycleRecipe(energy, new ItemStack(MoCItems.sai), new ItemStack(MoCItems.ancientSilverIngot), 1);
        SmelterManager.addRecycleRecipe(energy, new ItemStack(MoCItems.silverBow), new ItemStack(MoCItems.ancientSilverIngot), 1);

        // Pulverizer
        energy = 3000;
        PulverizerManager.addRecycleRecipe(energy, new ItemStack(MoCItems.sharkteeth), new ItemStack(Items.DYE, 2, 15), 2);
        PulverizerManager.addRecycleRecipe(energy, new ItemStack(MoCItems.bigcatclaw), new ItemStack(Items.DYE, 2, 15), 2);
        PulverizerManager.addRecycleRecipe(energy, new ItemStack(MoCItems.sharksword), new ItemStack(MoCItems.sharkteeth), 1);
        PulverizerManager.addRecycleRecipe(energy, new ItemStack(MoCItems.sharkaxe), new ItemStack(MoCItems.sharkteeth), 1);
        PulverizerManager.addRecycleRecipe(energy, new ItemStack(MoCItems.sharkMattock), new ItemStack(MoCItems.sharkteeth), 2);
        PulverizerManager.addRecycleRecipe(energy, new ItemStack(MoCItems.woolball), new ItemStack(Items.STRING), 4);
        PulverizerManager.addRecycleRecipe(energy, new ItemStack(MoCItems.sugarlump), new ItemStack(Items.SUGAR), 4);
        PulverizerManager.addRecycleRecipe(energy, new ItemStack(MoCItems.tusksWood), ItemMaterial.dustWood, 10);
        PulverizerManager.addRecycleRecipe(energy, new ItemStack(MoCItems.tusksDiamond), new ItemStack(Items.DIAMOND), 2);
        PulverizerManager.addRecycleRecipe(energy, new ItemStack(MoCItems.horsearmorcrystal), new ItemStack(Items.DIAMOND), 2);
        PulverizerManager.addRecycleRecipe(energy, new ItemStack(MoCItems.sugarlump), new ItemStack(Items.SUGAR), 4);
        PulverizerManager.addRecycleRecipe(2000, new ItemStack(MoCItems.ancientSilverScrap), ItemMaterial.dustSilver, 1);
        PulverizerManager.addRecipe(energy, new ItemStack(MoCItems.medallion), new ItemStack(Items.LEATHER), new ItemStack(Items.DYE, 1, 4), 25);
        PulverizerManager.addRecycleRecipe(energy, new ItemStack(MoCItems.helmetSilver), new ItemStack(MoCItems.ancientSilverIngot), 2);
        PulverizerManager.addRecycleRecipe(energy, new ItemStack(MoCItems.chestSilver), new ItemStack(MoCItems.ancientSilverIngot), 4);
        PulverizerManager.addRecycleRecipe(energy, new ItemStack(MoCItems.legsSilver), new ItemStack(MoCItems.ancientSilverIngot), 3);
        PulverizerManager.addRecycleRecipe(energy, new ItemStack(MoCItems.bootsSilver), new ItemStack(MoCItems.ancientSilverIngot), 2);
        PulverizerManager.addRecipe(energy, new ItemStack(MoCBlocks.silverSandstone), new ItemStack(MoCBlocks.silverSand, 2), ItemMaterial.dustNiter, 40);
        PulverizerManager.addRecipe(energy, new ItemStack(MoCBlocks.carvedSilverSandstone), new ItemStack(MoCBlocks.silverSand, 2), ItemMaterial.dustNiter, 40);
        PulverizerManager.addRecipe(energy, new ItemStack(MoCBlocks.smoothSilverSandstone), new ItemStack(MoCBlocks.silverSand, 2), ItemMaterial.dustNiter, 40);
        PulverizerManager.addRecipe(4000, new ItemStack(MoCBlocks.ancientOre), new ItemStack(Items.DYE, 6, 15), new ItemStack(MoCItems.ancientSilverScrap), 20);
        PulverizerManager.addRecycleRecipe(1000, new ItemStack(MoCBlocks.wyvstone), new ItemStack(MoCBlocks.cobbledWyvstone), 1);
        PulverizerManager.addRecycleRecipe(1000, new ItemStack(MoCBlocks.deepWyvstone), new ItemStack(MoCBlocks.cobbledDeepWyvstone), 1);
        PulverizerManager.addRecycleRecipe(4000, new ItemStack(MoCBlocks.gleamingGlass), new ItemStack(MoCBlocks.silverSand), 1);
        PulverizerManager.addRecipe(4000, new ItemStack(MoCBlocks.cobbledWyvstone), new ItemStack(Blocks.GRAVEL), new ItemStack(MoCBlocks.silverSand), 15);
        PulverizerManager.addRecipe(4000, new ItemStack(MoCBlocks.cobbledDeepWyvstone), new ItemStack(Blocks.GRAVEL), new ItemStack(MoCBlocks.silverSand), 15);
        PulverizerManager.addRecycleRecipe(4000, new ItemStack(MoCBlocks.firestone), new ItemStack(MoCItems.firestoneChunk), 4);
        // Chitin Armor
        PulverizerManager.addRecycleRecipe(energy, new ItemStack(MoCItems.scorpHelmetDirt), new ItemStack(MoCItems.chitin), 2);
        PulverizerManager.addRecycleRecipe(energy, new ItemStack(MoCItems.scorpPlateDirt), new ItemStack(MoCItems.chitin), 4);
        PulverizerManager.addRecycleRecipe(energy, new ItemStack(MoCItems.scorpLegsDirt), new ItemStack(MoCItems.chitin), 3);
        PulverizerManager.addRecycleRecipe(energy, new ItemStack(MoCItems.scorpBootsDirt), new ItemStack(MoCItems.chitin), 2);
        PulverizerManager.addRecycleRecipe(energy, new ItemStack(MoCItems.scorpHelmetCave), new ItemStack(MoCItems.chitinCave), 2);
        PulverizerManager.addRecycleRecipe(energy, new ItemStack(MoCItems.scorpPlateCave), new ItemStack(MoCItems.chitinCave), 4);
        PulverizerManager.addRecycleRecipe(energy, new ItemStack(MoCItems.scorpLegsCave), new ItemStack(MoCItems.chitinCave), 3);
        PulverizerManager.addRecycleRecipe(energy, new ItemStack(MoCItems.scorpBootsCave), new ItemStack(MoCItems.chitinCave), 2);
        PulverizerManager.addRecycleRecipe(energy, new ItemStack(MoCItems.scorpHelmetFrost), new ItemStack(MoCItems.chitinFrost), 2);
        PulverizerManager.addRecycleRecipe(energy, new ItemStack(MoCItems.scorpPlateFrost), new ItemStack(MoCItems.chitinFrost), 4);
        PulverizerManager.addRecycleRecipe(energy, new ItemStack(MoCItems.scorpLegsFrost), new ItemStack(MoCItems.chitinFrost), 3);
        PulverizerManager.addRecycleRecipe(energy, new ItemStack(MoCItems.scorpBootsFrost), new ItemStack(MoCItems.chitinFrost), 2);
        PulverizerManager.addRecycleRecipe(energy, new ItemStack(MoCItems.scorpHelmetNether), new ItemStack(MoCItems.chitinNether), 2);
        PulverizerManager.addRecycleRecipe(energy, new ItemStack(MoCItems.scorpPlateNether), new ItemStack(MoCItems.chitinNether), 4);
        PulverizerManager.addRecycleRecipe(energy, new ItemStack(MoCItems.scorpLegsNether), new ItemStack(MoCItems.chitinNether), 3);
        PulverizerManager.addRecycleRecipe(energy, new ItemStack(MoCItems.scorpBootsNether), new ItemStack(MoCItems.chitinNether), 2);
        PulverizerManager.addRecycleRecipe(energy, new ItemStack(MoCItems.scorpHelmetUndead), new ItemStack(MoCItems.chitinUndead), 2);
        PulverizerManager.addRecycleRecipe(energy, new ItemStack(MoCItems.scorpPlateUndead), new ItemStack(MoCItems.chitinUndead), 4);
        PulverizerManager.addRecycleRecipe(energy, new ItemStack(MoCItems.scorpLegsUndead), new ItemStack(MoCItems.chitinUndead), 3);
        PulverizerManager.addRecycleRecipe(energy, new ItemStack(MoCItems.scorpBootsUndead), new ItemStack(MoCItems.chitinUndead), 2);
        // Scorpion Tools
        PulverizerManager.addRecipe(energy, new ItemStack(MoCItems.scorpStingCave), new ItemStack(MoCItems.chitinCave), new ItemStack(MoCItems.chitinCave), 50);
        PulverizerManager.addRecipe(energy, new ItemStack(MoCItems.scorpSwordCave), new ItemStack(MoCItems.chitinCave), new ItemStack(MoCItems.scorpStingCave), 50);
        PulverizerManager.addRecipe(energy, new ItemStack(MoCItems.scorpAxeCave), new ItemStack(MoCItems.chitinCave), new ItemStack(MoCItems.scorpStingCave), 50);
        PulverizerManager.addRecipe(energy, new ItemStack(MoCItems.scorpMattockCave), new ItemStack(MoCItems.chitinCave, 2), new ItemStack(MoCItems.scorpStingCave), 50);
        PulverizerManager.addRecipe(energy, new ItemStack(MoCItems.scorpStingDirt), new ItemStack(MoCItems.chitin), new ItemStack(MoCItems.chitin), 50);
        PulverizerManager.addRecipe(energy, new ItemStack(MoCItems.scorpSwordDirt), new ItemStack(MoCItems.chitin), new ItemStack(MoCItems.scorpStingDirt), 50);
        PulverizerManager.addRecipe(energy, new ItemStack(MoCItems.scorpAxeDirt), new ItemStack(MoCItems.chitin), new ItemStack(MoCItems.scorpStingDirt), 50);
        PulverizerManager.addRecipe(energy, new ItemStack(MoCItems.scorpMattockDirt), new ItemStack(MoCItems.chitin, 2), new ItemStack(MoCItems.scorpStingDirt), 50);
        PulverizerManager.addRecipe(energy, new ItemStack(MoCItems.scorpStingFrost), new ItemStack(MoCItems.chitinFrost), new ItemStack(MoCItems.chitinFrost), 50);
        PulverizerManager.addRecipe(energy, new ItemStack(MoCItems.scorpSwordFrost), new ItemStack(MoCItems.chitinFrost), new ItemStack(MoCItems.scorpStingFrost), 50);
        PulverizerManager.addRecipe(energy, new ItemStack(MoCItems.scorpAxeFrost), new ItemStack(MoCItems.chitinFrost), new ItemStack(MoCItems.scorpStingFrost), 50);
        PulverizerManager.addRecipe(energy, new ItemStack(MoCItems.scorpMattockFrost), new ItemStack(MoCItems.chitinFrost, 2), new ItemStack(MoCItems.scorpStingFrost), 50);
        PulverizerManager.addRecipe(energy, new ItemStack(MoCItems.scorpStingNether), new ItemStack(MoCItems.chitinNether), new ItemStack(MoCItems.chitinNether), 50);
        PulverizerManager.addRecipe(energy, new ItemStack(MoCItems.scorpSwordNether), new ItemStack(MoCItems.chitinNether), new ItemStack(MoCItems.scorpStingNether), 50);
        PulverizerManager.addRecipe(energy, new ItemStack(MoCItems.scorpAxeNether), new ItemStack(MoCItems.chitinNether), new ItemStack(MoCItems.scorpStingNether), 50);
        PulverizerManager.addRecipe(energy, new ItemStack(MoCItems.scorpMattockNether), new ItemStack(MoCItems.chitinNether, 2), new ItemStack(MoCItems.scorpStingNether), 50);
        PulverizerManager.addRecipe(energy, new ItemStack(MoCItems.scorpStingUndead), new ItemStack(MoCItems.chitinUndead), new ItemStack(MoCItems.chitinUndead), 50);
        PulverizerManager.addRecipe(energy, new ItemStack(MoCItems.scorpSwordUndead), new ItemStack(MoCItems.chitinUndead), new ItemStack(MoCItems.scorpStingUndead), 50);
        PulverizerManager.addRecipe(energy, new ItemStack(MoCItems.scorpAxeUndead), new ItemStack(MoCItems.chitinUndead), new ItemStack(MoCItems.scorpStingUndead), 50);
        PulverizerManager.addRecipe(energy, new ItemStack(MoCItems.scorpMattockUndead), new ItemStack(MoCItems.chitinUndead, 2), new ItemStack(MoCItems.scorpStingUndead), 50);

        // Sawmill
        energy = 1500;
        SawmillManager.addRecipe(1000, new ItemStack(MoCBlocks.wyvwoodLog), new ItemStack(MoCBlocks.wyvwoodPlanks, 6, 0), ItemMaterial.dustWood, 100);
        TapperManager.addStandardMapping(new ItemStack(MoCBlocks.wyvwoodLog), new FluidStack(TFFluids.fluidResin, 100));
        SawmillManager.addRecipe(energy, new ItemStack(MoCItems.whip), new ItemStack(Items.LEATHER, 2), new ItemStack(MoCItems.bigcatclaw), 50);
        SawmillManager.addRecipe(energy, new ItemStack(MoCItems.reptileWhip), new ItemStack(MoCItems.hideCroc, 2), new ItemStack(MoCItems.bigcatclaw), 50);
        SawmillManager.addRecipe(energy, new ItemStack(MoCItems.elephantChest), new ItemStack(Blocks.CHEST), new ItemStack(MoCItems.animalHide), 50);
        SawmillManager.addRecipe(energy, new ItemStack(MoCItems.elephantGarment), new ItemStack(MoCItems.medallion), new ItemStack(Items.STRING, 12), 100);
        SawmillManager.addRecipe(energy, new ItemStack(MoCItems.elephantHarness), new ItemStack(MoCItems.animalHide, 2), new ItemStack(Items.STRING, 8), 100);
        SawmillManager.addRecipe(energy, new ItemStack(MoCItems.elephantHowdah), new ItemStack(Items.STRING, 12));
        SawmillManager.addRecipe(energy, new ItemStack(MoCItems.mammothPlatform), ItemHelper.cloneStack(ItemMaterial.dustWood, 16), new ItemStack(Items.LEAD, 1), 150);
        SawmillManager.addRecipe(energy, new ItemStack(MoCItems.litterbox), new ItemStack(Blocks.PLANKS, 2), new ItemStack(Blocks.SAND), 25);
        // Kitty Beds
        for (int i = 0; i < 16; i++) {
            String colorName = EnumDyeColor.byMetadata(i).getTranslationKey().toLowerCase();
            if (colorName.equalsIgnoreCase("lightblue")) {
                colorName = "light_blue";
            }

            ResourceLocation loc = new ResourceLocation(drzhark.mocreatures.MoCConstants.MOD_ID, "kittybed_" + colorName);
            Item bedItem = ForgeRegistries.ITEMS.getValue(loc);

            if (bedItem != null) {
                SawmillManager.addRecipe(energy, new ItemStack(bedItem), new ItemStack(Blocks.PLANKS, 1), new ItemStack(Blocks.WOOL, 1, i));
            }
        }
        // Reptile Hide
        SawmillManager.addRecycleRecipe(energy, new ItemStack(MoCItems.helmetCroc), new ItemStack(MoCItems.hideCroc), 2);
        SawmillManager.addRecycleRecipe(energy, new ItemStack(MoCItems.plateCroc), new ItemStack(MoCItems.hideCroc), 4);
        SawmillManager.addRecycleRecipe(energy, new ItemStack(MoCItems.legsCroc), new ItemStack(MoCItems.hideCroc), 3);
        SawmillManager.addRecycleRecipe(energy, new ItemStack(MoCItems.bootsCroc), new ItemStack(MoCItems.hideCroc), 2);
        // Fur
        SawmillManager.addRecycleRecipe(energy, new ItemStack(MoCItems.helmetFur), new ItemStack(MoCItems.fur), 2);
        SawmillManager.addRecycleRecipe(energy, new ItemStack(MoCItems.chestFur), new ItemStack(MoCItems.fur), 4);
        SawmillManager.addRecycleRecipe(energy, new ItemStack(MoCItems.legsFur), new ItemStack(MoCItems.fur), 3);
        SawmillManager.addRecycleRecipe(energy, new ItemStack(MoCItems.bootsFur), new ItemStack(MoCItems.fur), 2);
        // Hide
        SawmillManager.addRecycleRecipe(energy, new ItemStack(MoCItems.helmetHide), new ItemStack(MoCItems.animalHide), 2);
        SawmillManager.addRecycleRecipe(energy, new ItemStack(MoCItems.chestHide), new ItemStack(MoCItems.animalHide), 4);
        SawmillManager.addRecycleRecipe(energy, new ItemStack(MoCItems.legsHide), new ItemStack(MoCItems.animalHide), 3);
        SawmillManager.addRecycleRecipe(energy, new ItemStack(MoCItems.bootsHide), new ItemStack(MoCItems.animalHide), 2);

        // Magma Crucible
        energy = 300000;
        CrucibleManager.addRecipe(250000, new ItemStack(MoCBlocks.cobbledWyvstone), new FluidStack(FluidRegistry.LAVA, 1000));
        CrucibleManager.addRecipe(250000, new ItemStack(MoCBlocks.wyvstone), new FluidStack(FluidRegistry.LAVA, 1000));
        CrucibleManager.addRecipe(200000, new ItemStack(MoCBlocks.deepWyvstone), new FluidStack(FluidRegistry.LAVA, 1000));
        CrucibleManager.addRecipe(200000, new ItemStack(MoCBlocks.cobbledDeepWyvstone), new FluidStack(FluidRegistry.LAVA, 1000));
        CrucibleManager.addRecipe(80000, new ItemStack(MoCBlocks.firestone), new FluidStack(FluidRegistry.LAVA, 1000));
        CrucibleManager.addRecipe(20000, new ItemStack(MoCItems.firestoneChunk), new FluidStack(FluidRegistry.LAVA, 250));

        // Phytogenic Insolator
        InsolatorManager.addRecipe(4800, ItemFertilizer.fertilizerBasic, new ItemStack(MoCBlocks.tallWyvgrass), new ItemStack(MoCBlocks.tallWyvgrass, 3));
        InsolatorManager.addRecipe(7200, ItemFertilizer.fertilizerRich, new ItemStack(MoCBlocks.tallWyvgrass), new ItemStack(MoCBlocks.tallWyvgrass, 6));
        InsolatorManager.addRecipe(9600, ItemFertilizer.fertilizerFlux, new ItemStack(MoCBlocks.tallWyvgrass), new ItemStack(MoCBlocks.tallWyvgrass, 9));
    }
}
