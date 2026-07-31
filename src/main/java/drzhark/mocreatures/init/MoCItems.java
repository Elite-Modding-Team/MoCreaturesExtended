/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.init;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.compat.patchouli.item.MoCItemGuidebook;
import drzhark.mocreatures.item.*;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;

import com.google.common.base.Preconditions;

@SuppressWarnings("deprecation")
@Mod.EventBusSubscriber(modid = MoCConstants.MOD_ID)
@GameRegistry.ObjectHolder(MoCConstants.MOD_ID)
public class MoCItems {
    @GameRegistry.ObjectHolder("amuletbone")
    public static MoCItemHorseAmulet amuletbone;
    @GameRegistry.ObjectHolder("amuletbonefull")
    public static MoCItemHorseAmulet amuletbonefull;
    @GameRegistry.ObjectHolder("amuletfairy")
    public static MoCItemHorseAmulet amuletfairy;
    @GameRegistry.ObjectHolder("amuletfairyfull")
    public static MoCItemHorseAmulet amuletfairyfull;
    @GameRegistry.ObjectHolder("amuletghost")
    public static MoCItemHorseAmulet amuletghost;
    @GameRegistry.ObjectHolder("amuletghostfull")
    public static MoCItemHorseAmulet amuletghostfull;
    @GameRegistry.ObjectHolder("amuletpegasus")
    public static MoCItemHorseAmulet amuletpegasus;
    @GameRegistry.ObjectHolder("amuletpegasusfull")
    public static MoCItemHorseAmulet amuletpegasusfull;
    @GameRegistry.ObjectHolder("silveraxe")
    public static MoCItemAxe silveraxe;
    @GameRegistry.ObjectHolder("ancient_silver_boots")
    public static MoCItemArmor bootsSilver;
    @GameRegistry.ObjectHolder("ancient_silver_bow")
    public static MoCItemBow silverBow;
    @GameRegistry.ObjectHolder("ancient_silver_chestplate")
    public static MoCItemArmor chestSilver;
    @GameRegistry.ObjectHolder("ancient_silver_helmet")
    public static MoCItemArmor helmetSilver;
    @GameRegistry.ObjectHolder("ancientsilveringot")
    public static MoCItem ancientSilverIngot;
    @GameRegistry.ObjectHolder("ancient_silver_mattock")
    public static MoCItemMattock silverMattock;
    @GameRegistry.ObjectHolder("ancientsilvernugget")
    public static MoCItem ancientSilverNugget;
    @GameRegistry.ObjectHolder("ancient_silver_leggings")
    public static MoCItemArmor legsSilver;
    @GameRegistry.ObjectHolder("ancientsilverscrap")
    public static MoCItem ancientSilverScrap;
    @GameRegistry.ObjectHolder("silversword")
    public static MoCItemSword silversword;
    @GameRegistry.ObjectHolder("bigcatclaw")
    public static MoCItem bigcatclaw;
    @GameRegistry.ObjectHolder("bo")
    public static MoCItemSword bo;
    @GameRegistry.ObjectHolder("chitin")
    public static MoCItem chitin;
    @GameRegistry.ObjectHolder("chitinblack")
    public static MoCItem chitinCave;
    @GameRegistry.ObjectHolder("chitinfrost")
    public static MoCItem chitinFrost;
    @GameRegistry.ObjectHolder("chitinnether")
    public static MoCItem chitinNether;
    @GameRegistry.ObjectHolder("chitinundead")
    public static MoCItem chitinUndead;
    /*@GameRegistry.ObjectHolder("creaturepedia")
    public static MoCItemGuidebook creaturepedia;*/
    @GameRegistry.ObjectHolder("crabcooked")
    public static MoCItemFood crabcooked;
    @GameRegistry.ObjectHolder("duckcooked")
    public static MoCItemFood duckCooked;
    @GameRegistry.ObjectHolder("ostrichcooked")
    public static MoCItemFood ostrichcooked;
    @GameRegistry.ObjectHolder("ratcooked")
    public static MoCItemFood ratCooked;
    @GameRegistry.ObjectHolder("turkeycooked")
    public static MoCItemFood cookedTurkey;
    @GameRegistry.ObjectHolder("turtlecooked")
    public static MoCItemFood turtlecooked;
    @GameRegistry.ObjectHolder("venisoncooked")
    public static MoCItemFood venisonCooked;
    @GameRegistry.ObjectHolder("elephantchest")
    public static MoCItem elephantChest;
    @GameRegistry.ObjectHolder("elementgarment")
    public static MoCItem elephantGarment;
    @GameRegistry.ObjectHolder("elementharness")
    public static MoCItem elephantHarness;
    @GameRegistry.ObjectHolder("elementhowdah")
    public static MoCItem elephantHowdah;
    @GameRegistry.ObjectHolder("essencedarkness")
    public static MoCItem essencedarkness;
    @GameRegistry.ObjectHolder("essence_eternal")
    public static MoCItem essenceEternal;
    @GameRegistry.ObjectHolder("essencefire")
    public static MoCItem essencefire;
    @GameRegistry.ObjectHolder("essence_ice")
    public static MoCItem essenceIce;
    @GameRegistry.ObjectHolder("essencelight")
    public static MoCItem essencelight;
    @GameRegistry.ObjectHolder("essenceundead")
    public static MoCItem essenceundead;
    @GameRegistry.ObjectHolder("fine_silver_ingot")
    public static MoCItem fineSilverIngot;
    @GameRegistry.ObjectHolder("fine_silver_nugget")
    public static MoCItem fineSilverNugget;
    @GameRegistry.ObjectHolder("firestonechunk")
    public static MoCItem firestoneChunk;
    @GameRegistry.ObjectHolder("fishnet")
    public static MoCItemPetAmulet fishnet;
    @GameRegistry.ObjectHolder("fishnetfull")
    public static MoCItemPetAmulet fishnetfull;
    @GameRegistry.ObjectHolder("fur")
    public static MoCItem fur;
    @GameRegistry.ObjectHolder("furboots")
    public static MoCItemLuckyArmor bootsFur;
    @GameRegistry.ObjectHolder("furchest")
    public static MoCItemLuckyArmor chestFur;
    @GameRegistry.ObjectHolder("furhelmet")
    public static MoCItemLuckyArmor helmetFur;
    @GameRegistry.ObjectHolder("furlegs")
    public static MoCItemLuckyArmor legsFur;
    @GameRegistry.ObjectHolder("heartdarkness")
    public static MoCItem heartdarkness;
    @GameRegistry.ObjectHolder("heart_eternal")
    public static MoCItem heartEternal;
    @GameRegistry.ObjectHolder("heartfire")
    public static MoCItem heartfire;
    @GameRegistry.ObjectHolder("heart_ice")
    public static MoCItem heartIce;
    @GameRegistry.ObjectHolder("heartundead")
    public static MoCItem heartundead;
    @GameRegistry.ObjectHolder("hide")
    public static MoCItem animalHide;
    @GameRegistry.ObjectHolder("hideboots")
    public static MoCItemArmor bootsHide;
    @GameRegistry.ObjectHolder("hidechest")
    public static MoCItemArmor chestHide;
    @GameRegistry.ObjectHolder("hidehelmet")
    public static MoCItemArmor helmetHide;
    @GameRegistry.ObjectHolder("hidelegs")
    public static MoCItemArmor legsHide;
    @GameRegistry.ObjectHolder("horsearmorcrystal")
    public static MoCItem horsearmorcrystal;
    @GameRegistry.ObjectHolder("katana")
    public static MoCItemSword katana;
    @GameRegistry.ObjectHolder("kittybed")
    public static MoCItemKittyBed kittybed;
    @GameRegistry.ObjectHolder("kittylitter")
    public static MoCItemLitterBox litterbox;
    @GameRegistry.ObjectHolder("mammothplatform")
    public static MoCItem mammothPlatform;
    @GameRegistry.ObjectHolder("medallion")
    public static MoCItem medallion;
    @GameRegistry.ObjectHolder("mocegg")
    public static MoCItemEgg mocegg;
    @GameRegistry.ObjectHolder("mysticpear")
    public static MoCItemFood mysticPear;
    @GameRegistry.ObjectHolder("nunchaku")
    public static MoCItemSword nunchaku;
    @GameRegistry.ObjectHolder("omelet")
    public static MoCItemFood omelet;
    @GameRegistry.ObjectHolder("petamulet")
    public static MoCItemPetAmulet petamulet;
    @GameRegistry.ObjectHolder("petamuletfull")
    public static MoCItemPetAmulet petamuletfull;
    @GameRegistry.ObjectHolder("petfood")
    public static MoCItem petfood;
    @GameRegistry.ObjectHolder("ratburger")
    public static MoCItemFood ratBurger;
    @GameRegistry.ObjectHolder("recordshuffle")
    public static MoCItemRecord recordshuffle;
    @GameRegistry.ObjectHolder("crabraw")
    public static MoCItemFood crabraw;
    @GameRegistry.ObjectHolder("duckraw")
    public static MoCItemFood duckRaw;
    @GameRegistry.ObjectHolder("ostrichraw")
    public static MoCItemFood ostrichraw;
    @GameRegistry.ObjectHolder("ratraw")
    public static MoCItemFood ratRaw;
    @GameRegistry.ObjectHolder("turkeyraw")
    public static MoCItemFood rawTurkey;
    @GameRegistry.ObjectHolder("turtleraw")
    public static MoCItemFood turtleraw;
    @GameRegistry.ObjectHolder("venisonraw")
    public static MoCItemFood venisonRaw;
    @GameRegistry.ObjectHolder("reptileboots")
    public static MoCItemArmor bootsCroc;
    @GameRegistry.ObjectHolder("reptilehelmet")
    public static MoCItemArmor helmetCroc;
    @GameRegistry.ObjectHolder("reptilehide")
    public static MoCItem hideCroc;
    @GameRegistry.ObjectHolder("reptilelegs")
    public static MoCItemArmor legsCroc;
    @GameRegistry.ObjectHolder("reptileplate")
    public static MoCItemArmor plateCroc;
    @GameRegistry.ObjectHolder("reptile_whip")
    public static MoCItemWhip reptileWhip;
    @GameRegistry.ObjectHolder("sai")
    public static MoCItemSword sai;
    @GameRegistry.ObjectHolder("scrolloffreedom")
    public static MoCItem scrollOfFreedom;
    @GameRegistry.ObjectHolder("scrollofsale")
    public static MoCItem scrollOfSale;
    @GameRegistry.ObjectHolder("scrollofrenaming")
    public static MoCItem scrollOfRenaming;
    @GameRegistry.ObjectHolder("scorpaxecave")
    public static MoCItemAxe scorpAxeCave;
    @GameRegistry.ObjectHolder("scorpbootscave")
    public static MoCItemArmor scorpBootsCave;
    @GameRegistry.ObjectHolder("scorphelmetcave")
    public static MoCItemArmor scorpHelmetCave;
    @GameRegistry.ObjectHolder("scorplegscave")
    public static MoCItemArmor scorpLegsCave;
    @GameRegistry.ObjectHolder("dark_scorpion_mattock")
    public static MoCItemMattock scorpMattockCave;
    @GameRegistry.ObjectHolder("scorpplatecave")
    public static MoCItemArmor scorpPlateCave;
    @GameRegistry.ObjectHolder("scorpstingcave")
    public static MoCItemWeapon scorpStingCave;
    @GameRegistry.ObjectHolder("scorpswordcave")
    public static MoCItemSword scorpSwordCave;
    @GameRegistry.ObjectHolder("scorpaxedirt")
    public static MoCItemAxe scorpAxeDirt;
    @GameRegistry.ObjectHolder("scorpbootsdirt")
    public static MoCItemArmor scorpBootsDirt;
    @GameRegistry.ObjectHolder("scorphelmetdirt")
    public static MoCItemArmor scorpHelmetDirt;
    @GameRegistry.ObjectHolder("scorplegsdirt")
    public static MoCItemArmor scorpLegsDirt;
    @GameRegistry.ObjectHolder("earth_scorpion_mattock")
    public static MoCItemMattock scorpMattockDirt;
    @GameRegistry.ObjectHolder("scorpplatedirt")
    public static MoCItemArmor scorpPlateDirt;
    @GameRegistry.ObjectHolder("scorpstingdirt")
    public static MoCItemWeapon scorpStingDirt;
    @GameRegistry.ObjectHolder("scorpsworddirt")
    public static MoCItemSword scorpSwordDirt;
    @GameRegistry.ObjectHolder("scorpaxenether")
    public static MoCItemAxe scorpAxeNether;
    @GameRegistry.ObjectHolder("scorpbootsnether")
    public static MoCItemArmor scorpBootsNether;
    @GameRegistry.ObjectHolder("scorphelmetnether")
    public static MoCItemArmor scorpHelmetNether;
    @GameRegistry.ObjectHolder("scorplegsnether")
    public static MoCItemArmor scorpLegsNether;
    @GameRegistry.ObjectHolder("fire_scorpion_mattock")
    public static MoCItemMattock scorpMattockNether;
    @GameRegistry.ObjectHolder("scorpplatenether")
    public static MoCItemArmor scorpPlateNether;
    @GameRegistry.ObjectHolder("scorpstingnether")
    public static MoCItemWeapon scorpStingNether;
    @GameRegistry.ObjectHolder("scorpswordnether")
    public static MoCItemSword scorpSwordNether;
    @GameRegistry.ObjectHolder("scorpaxefrost")
    public static MoCItemAxe scorpAxeFrost;
    @GameRegistry.ObjectHolder("scorpbootsfrost")
    public static MoCItemArmor scorpBootsFrost;
    @GameRegistry.ObjectHolder("scorphelmetfrost")
    public static MoCItemArmor scorpHelmetFrost;
    @GameRegistry.ObjectHolder("scorplegsfrost")
    public static MoCItemArmor scorpLegsFrost;
    @GameRegistry.ObjectHolder("frost_scorpion_mattock")
    public static MoCItemMattock scorpMattockFrost;
    @GameRegistry.ObjectHolder("scorpplatefrost")
    public static MoCItemArmor scorpPlateFrost;
    @GameRegistry.ObjectHolder("scorpstingfrost")
    public static MoCItemWeapon scorpStingFrost;
    @GameRegistry.ObjectHolder("scorpswordfrost")
    public static MoCItemSword scorpSwordFrost;
    @GameRegistry.ObjectHolder("scorpaxeundead")
    public static MoCItemAxe scorpAxeUndead;
    @GameRegistry.ObjectHolder("scorpbootsundead")
    public static MoCItemArmor scorpBootsUndead;
    @GameRegistry.ObjectHolder("scorphelmetundead")
    public static MoCItemArmor scorpHelmetUndead;
    @GameRegistry.ObjectHolder("scorplegsundead")
    public static MoCItemArmor scorpLegsUndead;
    @GameRegistry.ObjectHolder("undead_scorpion_mattock")
    public static MoCItemMattock scorpMattockUndead;
    @GameRegistry.ObjectHolder("scorpplateundead")
    public static MoCItemArmor scorpPlateUndead;
    @GameRegistry.ObjectHolder("scorpstingundead")
    public static MoCItemWeapon scorpStingUndead;
    @GameRegistry.ObjectHolder("scorpswordundead")
    public static MoCItemSword scorpSwordUndead;
    @GameRegistry.ObjectHolder("sharkaxe")
    public static MoCItemAxe sharkaxe;
    @GameRegistry.ObjectHolder("shark_mattock")
    public static MoCItemMattock sharkMattock;
    @GameRegistry.ObjectHolder("sharksword")
    public static MoCItemSword sharksword;
    @GameRegistry.ObjectHolder("sharkteeth")
    public static MoCItem sharkteeth;
    @GameRegistry.ObjectHolder("staffportal")
    public static ItemStaffPortal staffPortal;
    @GameRegistry.ObjectHolder("sugarlump")
    public static MoCItemFood sugarlump;
    @GameRegistry.ObjectHolder("turtlesoup")
    public static MoCItemTurtleSoup turtlesoup;
    @GameRegistry.ObjectHolder("tusksdiamond")
    public static MoCItem tusksDiamond;
    @GameRegistry.ObjectHolder("tusksiron")
    public static MoCItem tusksIron;
    @GameRegistry.ObjectHolder("tuskswood")
    public static MoCItem tusksWood;
    @GameRegistry.ObjectHolder("unicornhorn")
    public static MoCItem unicornhorn;
    @GameRegistry.ObjectHolder("whip")
    public static MoCItemWhip whip;
    @GameRegistry.ObjectHolder("woolball")
    public static MoCItem woolball;
    @GameRegistry.ObjectHolder("wyvwood_door")
    public static MoCItemDoor wyvwoodDoor;

    @GameRegistry.ObjectHolder("kittybed_white")
    public static MoCItemKittyBed kittybed_white;
    @GameRegistry.ObjectHolder("kittybed_orange")
    public static MoCItemKittyBed kittybed_orange;
    @GameRegistry.ObjectHolder("kittybed_magenta")
    public static MoCItemKittyBed kittybed_magenta;
    @GameRegistry.ObjectHolder("kittybed_light_blue")
    public static MoCItemKittyBed kittybed_light_blue;
    @GameRegistry.ObjectHolder("kittybed_yellow")
    public static MoCItemKittyBed kittybed_yellow;
    @GameRegistry.ObjectHolder("kittybed_lime")
    public static MoCItemKittyBed kittybed_lime;
    @GameRegistry.ObjectHolder("kittybed_pink")
    public static MoCItemKittyBed kittybed_pink;
    @GameRegistry.ObjectHolder("kittybed_gray")
    public static MoCItemKittyBed kittybed_gray;
    @GameRegistry.ObjectHolder("kittybed_silver")
    public static MoCItemKittyBed kittybed_silver;
    @GameRegistry.ObjectHolder("kittybed_cyan")
    public static MoCItemKittyBed kittybed_cyan;
    @GameRegistry.ObjectHolder("kittybed_purple")
    public static MoCItemKittyBed kittybed_purple;
    @GameRegistry.ObjectHolder("kittybed_blue")
    public static MoCItemKittyBed kittybed_blue;
    @GameRegistry.ObjectHolder("kittybed_brown")
    public static MoCItemKittyBed kittybed_brown;
    @GameRegistry.ObjectHolder("kittybed_green")
    public static MoCItemKittyBed kittybed_green;
    @GameRegistry.ObjectHolder("kittybed_red")
    public static MoCItemKittyBed kittybed_red;
    @GameRegistry.ObjectHolder("kittybed_black")
    public static MoCItemKittyBed kittybed_black;

    public static ArmorMaterial crocARMOR = EnumHelper.addArmorMaterial("crocARMOR", "crocARMOR", 10, new int[]{1, 3, 4, 1}, 17, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 1.0F).setRepairItem(new ItemStack(hideCroc));
    public static ArmorMaterial furARMOR = EnumHelper.addArmorMaterial("furARMOR", "furARMOR", 4, new int[]{1, 2, 2, 1}, 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F).setRepairItem(new ItemStack(fur));
    public static ArmorMaterial hideARMOR = EnumHelper.addArmorMaterial("hideARMOR", "hideARMOR", 8, new int[]{1, 3, 3, 1}, 18, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F).setRepairItem(new ItemStack(animalHide));
    public static ArmorMaterial scorpcARMOR = EnumHelper.addArmorMaterial("scorpcARMOR", "scorpcARMOR", 18, new int[]{2, 6, 7, 2}, 16, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 2.0F).setRepairItem(new ItemStack(chitinCave));
    public static ArmorMaterial scorpdARMOR = EnumHelper.addArmorMaterial("scorpdARMOR", "scorpdARMOR", 18, new int[]{2, 6, 7, 2}, 16, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 2.0F).setRepairItem(new ItemStack(chitin));
    public static ArmorMaterial scorpfARMOR = EnumHelper.addArmorMaterial("scorpfARMOR", "scorpfARMOR", 18, new int[]{2, 6, 7, 2}, 16, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 2.0F).setRepairItem(new ItemStack(chitinFrost));
    public static ArmorMaterial scorpnARMOR = EnumHelper.addArmorMaterial("scorpnARMOR", "scorpnARMOR", 18, new int[]{2, 6, 7, 2}, 16, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 2.0F).setRepairItem(new ItemStack(chitinNether));
    public static ArmorMaterial scorpuARMOR = EnumHelper.addArmorMaterial("scorpuARMOR", "scorpuARMOR", 18, new int[]{2, 6, 7, 2}, 16, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 2.0F).setRepairItem(new ItemStack(chitinUndead));
    public static ArmorMaterial silverARMOR = EnumHelper.addArmorMaterial("silverARMOR", "silverARMOR", 15, new int[]{2, 6, 5, 2}, 22, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 1.5F);

    public static ToolMaterial NINJA = EnumHelper.addToolMaterial("NINJA", 3, 501, 10.0F, 3.5F, 20).setRepairItem(new ItemStack(ancientSilverIngot));
    public static ToolMaterial REPTILEWHIP = EnumHelper.addToolMaterial("REPTILEWHIP", 0, 224, 3.0F, 4.0F, 17).setRepairItem(new ItemStack(hideCroc));
    public static ToolMaterial SCORPC = EnumHelper.addToolMaterial("SCORPC", 3, 371, 7.5F, 2.5F, 16).setRepairItem(new ItemStack(chitinCave));
    public static ToolMaterial SCORPD = EnumHelper.addToolMaterial("SCORPD", 3, 371, 7.5F, 2.5F, 16).setRepairItem(new ItemStack(chitin));
    public static ToolMaterial SCORPF = EnumHelper.addToolMaterial("SCORPF", 3, 371, 7.5F, 2.5F, 16).setRepairItem(new ItemStack(chitinFrost));
    public static ToolMaterial SCORPN = EnumHelper.addToolMaterial("SCORPN", 3, 371, 7.5F, 2.5F, 16).setRepairItem(new ItemStack(chitinNether));
    public static ToolMaterial SCORPU = EnumHelper.addToolMaterial("SCORPU", 3, 371, 7.5F, 2.5F, 16).setRepairItem(new ItemStack(chitinUndead));
    public static ToolMaterial SHARK = EnumHelper.addToolMaterial("SHARK", 1, 161, 7.0F, 2.5F, 15).setRepairItem(new ItemStack(sharkteeth));
    public static ToolMaterial SILVER = EnumHelper.addToolMaterial("SILVER", 3, 404, 9.5F, 3.0F, 19).setRepairItem(new ItemStack(ancientSilverIngot));
    public static ToolMaterial STING = EnumHelper.addToolMaterial("STING", 0, 8, 6.0F, 0.0F, 5);
    public static ToolMaterial WHIP = EnumHelper.addToolMaterial("WHIP", 0, 184, 2.0F, 3.0F, 15).setRepairItem(new ItemStack(Items.LEATHER));

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void registerRenders(final ModelRegistryEvent modelRegistryEvent) {
        ModelLoader.setCustomModelResourceLocation(silverBow, 0, new ModelResourceLocation(silverBow.delegate.name(), "inventory"));

        for (int i = 0; i < 16; i++) {
            String colorName = EnumDyeColor.byMetadata(i).getTranslationKey().toLowerCase();
            if (colorName.equalsIgnoreCase("lightblue")) {
                colorName = "light_blue";
            }

            ResourceLocation loc = new ResourceLocation(MoCConstants.MOD_ID, "kittybed_" + colorName);
            Item bedItem = ForgeRegistries.ITEMS.getValue(loc);

            if (bedItem != null) {
                ModelLoader.setCustomModelResourceLocation(bedItem, 0, new ModelResourceLocation(loc, "inventory"));
            }
        }

        if (mocegg != null) {
            for (int i = 0; i < 91; i++) {
                ModelLoader.setCustomModelResourceLocation(mocegg, i, new ModelResourceLocation(mocegg.delegate.name(), "inventory"));
            }
        }
    }

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event) {
        final IForgeRegistry<Item> registry = event.getRegistry();

        // ITEMS
        // TODO: Work in progress, disabled for now!
        /*if (Loader.isModLoaded("patchouli")) {
            registry.registerAll(
                    setup(new MoCItemGuidebook(), "creaturepedia")
            );
        }*/

        registry.registerAll
                (
                        setup(new MoCItem(), "sharkteeth"),
                        setup(new MoCItemFood(1, 0.1F, false, 12).setPotionEffect(new PotionEffect(MobEffects.NAUSEA, 4 * 20, 0), 0.15F), "sugarlump"),
                        setup(new MoCItemEgg(), "mocegg"),
                        setup(new MoCItem(), "bigcatclaw"),
                        setup(new MoCItem(), "medallion"),
                        setup(new MoCItemLitterBox(), "kittylitter"),
                        setup(new MoCItem(), "woolball"),
                        setup(new MoCItem(), "petfood"),
                        setup(new MoCItem(), "reptilehide"),
                        setup(new MoCItemArmor(crocARMOR, 4, EntityEquipmentSlot.HEAD), "reptilehelmet"),
                        setup(new MoCItemArmor(crocARMOR, 4, EntityEquipmentSlot.CHEST), "reptileplate"),
                        setup(new MoCItemArmor(crocARMOR, 4, EntityEquipmentSlot.LEGS), "reptilelegs"),
                        setup(new MoCItemArmor(crocARMOR, 4, EntityEquipmentSlot.FEET), "reptileboots"),
                        setup(new MoCItem(), "fur"),
                        setup(new MoCItemFood(3, 0.5F, false), "omelet"),
                        setup(new MoCItemFood(2, 0.2F, true), "turtleraw"),
                        setup(new MoCItemFood(6, 0.7F, true), "turtlecooked"),
                        setup(new MoCItemTurtleSoup(8, 0.8F, false), "turtlesoup"),
                        setup(new ItemStaffPortal(), "staffportal"),

                        setup(new MoCItem(), "ancientsilverscrap"),
                        setup(new MoCItem(), "ancientsilveringot"),
                        setup(new MoCItem(), "ancientsilvernugget"),
                        setup(new MoCItemArmor(silverARMOR, 4, EntityEquipmentSlot.HEAD), "ancient_silver_helmet"),
                        setup(new MoCItemArmor(silverARMOR, 4, EntityEquipmentSlot.CHEST), "ancient_silver_chestplate"),
                        setup(new MoCItemArmor(silverARMOR, 4, EntityEquipmentSlot.LEGS), "ancient_silver_leggings"),
                        setup(new MoCItemArmor(silverARMOR, 4, EntityEquipmentSlot.FEET), "ancient_silver_boots"),
                        setup(new MoCItem(), "firestonechunk"),

                        setup(new MoCItemWhip(WHIP, 1.9F), "whip"),
                        setup(new MoCItemWhip(REPTILEWHIP, 1.9F), "reptile_whip"),
                        setup(new MoCItemSword(NINJA), "nunchaku"),
                        setup(new MoCItemSword(NINJA), "sai"),
                        setup(new MoCItemSword(NINJA), "bo"),
                        setup(new MoCItemSword(NINJA), "katana"),
                        setup(new MoCItemSword(SHARK), "sharksword"),
                        setup(new MoCItemMattock(SHARK, 4.5F, 1.2F), "shark_mattock"),
                        setup(new MoCItemAxe(SHARK, 9.5F, 1.0F), "sharkaxe"),
                        setup(new MoCItemSword(SILVER), "silversword"),
                        setup(new MoCItemMattock(SILVER, 6.0F, 1.3F), "ancient_silver_mattock"),
                        setup(new MoCItemAxe(SILVER, 10.0F, 1.1F), "silveraxe"),
                        setup(new MoCItemBow(720, 1.2F, 1.2F, 0.8F, 0.8F, Ingredient.fromStacks(new ItemStack(ancientSilverIngot))), "ancient_silver_bow"),

                        setup(new MoCItem(), "essencedarkness"),
                        setup(new MoCItem(), "essence_eternal"),
                        setup(new MoCItem(), "essencefire"),
                        setup(new MoCItem(), "essence_ice"),
                        setup(new MoCItem(), "essenceundead"),
                        setup(new MoCItem(), "essencelight"),

                        setup(new MoCItemHorseAmulet(), "amuletbone"),
                        setup(new MoCItemHorseAmulet(), "amuletbonefull"),
                        setup(new MoCItemHorseAmulet(), "amuletghost"),
                        setup(new MoCItemHorseAmulet(), "amuletghostfull"),
                        setup(new MoCItemHorseAmulet(), "amuletfairy"),
                        setup(new MoCItemHorseAmulet(), "amuletfairyfull"),
                        setup(new MoCItemHorseAmulet(), "amuletpegasus"),
                        setup(new MoCItemHorseAmulet(), "amuletpegasusfull"),
                        setup(new MoCItemPetAmulet(), "fishnet"),
                        setup(new MoCItemPetAmulet(), "fishnetfull"),
                        setup(new MoCItemPetAmulet(), "petamulet"),
                        setup(new MoCItemPetAmulet(), "petamuletfull"),

                        setup(new MoCItemLuckyArmor(0.5F, furARMOR, 4, EntityEquipmentSlot.HEAD), "furhelmet"),
                        setup(new MoCItemLuckyArmor(0.5F, furARMOR, 4, EntityEquipmentSlot.CHEST), "furchest"),
                        setup(new MoCItemLuckyArmor(0.5F, furARMOR, 4, EntityEquipmentSlot.LEGS), "furlegs"),
                        setup(new MoCItemLuckyArmor(0.5F, furARMOR, 4, EntityEquipmentSlot.FEET), "furboots"),

                        setup(new MoCItem(), "heartdarkness"),
                        setup(new MoCItem(), "heart_eternal"),
                        setup(new MoCItem(), "heartfire"),
                        setup(new MoCItem(), "heart_ice"),
                        setup(new MoCItem(), "heartundead"),
                        setup(new MoCItem(), "unicornhorn"),
                        setup(new MoCItemFood(3, 0.4F, true).setPotionEffect(new PotionEffect(MobEffects.HUNGER, 30 * 20, 0), 0.8F), "ostrichraw"),
                        setup(new MoCItemFood(7, 0.8F, true), "ostrichcooked"),
                        setup(new MoCItem("horsearmorcrystal", true).setMaxStackSize(1), "horsearmorcrystal"),
                        setup(new MoCItemFood(4, 0.8F, false, 16).setAlwaysEdible(), "mysticpear"),
                        setup(new MoCItemRecord("recordshuffle", MoCSoundEvents.MUSIC_DISC_SHUFFLING), "recordshuffle"),

                        setup(new MoCItemDoor(MoCBlocks.wyvwoodDoor), "wyvwood_door"),

                        setup(new MoCItem(), "hide"),
                        setup(new MoCItemArmor(hideARMOR, 4, EntityEquipmentSlot.HEAD), "hidehelmet"),
                        setup(new MoCItemArmor(hideARMOR, 4, EntityEquipmentSlot.CHEST), "hidechest"),
                        setup(new MoCItemArmor(hideARMOR, 4, EntityEquipmentSlot.LEGS), "hidelegs"),
                        setup(new MoCItemArmor(hideARMOR, 4, EntityEquipmentSlot.FEET), "hideboots"),
                        setup(new MoCItemFood(3, 0.4F, true).setPotionEffect(new PotionEffect(MobEffects.HUNGER, 30 * 20, 0), 0.8F), "turkeyraw"),
                        setup(new MoCItemFood(7, 0.8F, true), "turkeycooked"),
                        setup(new MoCItemFood(2, 0.4F, true).setPotionEffect(new PotionEffect(MobEffects.HUNGER, 30 * 20, 0), 0.8F), "duckraw"),
                        setup(new MoCItemFood(6, 0.7F, true), "duckcooked"),
                        setup(new MoCItemFood(2, 0.1F, true).setPotionEffect(new PotionEffect(MobEffects.HUNGER, 30 * 20, 0), 0.8F), "ratraw"),
                        setup(new MoCItemFood(4, 0.5F, true), "ratcooked"),
                        setup(new MoCItemFood(9, 0.5F, false), "ratburger"),
                        setup(new MoCItemFood(3, 0.4F, true).setPotionEffect(new PotionEffect(MobEffects.HUNGER, 30 * 20, 0), 0.8F), "venisonraw"),
                        setup(new MoCItemFood(8, 0.9F, true), "venisoncooked"),

                        setup(new MoCItem(), "chitinblack"),
                        setup(new MoCItem(), "chitin"),
                        setup(new MoCItem(), "chitinnether"),
                        setup(new MoCItem(), "chitinfrost"),
                        setup(new MoCItem(), "chitinundead"),

                        setup(new MoCItemSword(SCORPC, 4), "scorpswordcave"),
                        setup(new MoCItemMattock(SCORPC, 4.5F, 1.2F, 4), "dark_scorpion_mattock"),
                        setup(new MoCItemAxe(SCORPC, 9.5F, 1.0F, 4), "scorpaxecave"),
                        setup(new MoCItemSword(SCORPD, 1), "scorpsworddirt"),
                        setup(new MoCItemMattock(SCORPD, 4.5F, 1.2F, 1), "earth_scorpion_mattock"),
                        setup(new MoCItemAxe(SCORPD, 9.5F, 1.0F, 1), "scorpaxedirt"),
                        setup(new MoCItemSword(SCORPN, 3), "scorpswordnether"),
                        setup(new MoCItemMattock(SCORPN, 4.5F, 1.2F, 3), "fire_scorpion_mattock"),
                        setup(new MoCItemAxe(SCORPN, 9.5F, 1.0F, 3), "scorpaxenether"),
                        setup(new MoCItemSword(SCORPF, 2), "scorpswordfrost"),
                        setup(new MoCItemMattock(SCORPF, 4.5F, 1.2F, 2), "frost_scorpion_mattock"),
                        setup(new MoCItemAxe(SCORPF, 9.5F, 1.0F, 2), "scorpaxefrost"),
                        setup(new MoCItemSword(SCORPU, 5), "scorpswordundead"),
                        setup(new MoCItemMattock(SCORPU, 4.5F, 1.2F, 5), "undead_scorpion_mattock"),
                        setup(new MoCItemAxe(SCORPU, 9.5F, 1.0F, 5), "scorpaxeundead"),

                        setup(new MoCItemArmor(scorpcARMOR, 4, EntityEquipmentSlot.HEAD), "scorphelmetcave"),
                        setup(new MoCItemArmor(scorpcARMOR, 4, EntityEquipmentSlot.CHEST), "scorpplatecave"),
                        setup(new MoCItemArmor(scorpcARMOR, 4, EntityEquipmentSlot.LEGS), "scorplegscave"),
                        setup(new MoCItemArmor(scorpcARMOR, 4, EntityEquipmentSlot.FEET), "scorpbootscave"),
                        setup(new MoCItemArmor(scorpdARMOR, 4, EntityEquipmentSlot.HEAD), "scorphelmetdirt"),
                        setup(new MoCItemArmor(scorpdARMOR, 4, EntityEquipmentSlot.CHEST), "scorpplatedirt"),
                        setup(new MoCItemArmor(scorpdARMOR, 4, EntityEquipmentSlot.LEGS), "scorplegsdirt"),
                        setup(new MoCItemArmor(scorpdARMOR, 4, EntityEquipmentSlot.FEET), "scorpbootsdirt"),
                        setup(new MoCItemArmor(scorpnARMOR, 4, EntityEquipmentSlot.HEAD), "scorphelmetnether"),
                        setup(new MoCItemArmor(scorpnARMOR, 4, EntityEquipmentSlot.CHEST), "scorpplatenether"),
                        setup(new MoCItemArmor(scorpnARMOR, 4, EntityEquipmentSlot.LEGS), "scorplegsnether"),
                        setup(new MoCItemArmor(scorpnARMOR, 4, EntityEquipmentSlot.FEET), "scorpbootsnether"),
                        setup(new MoCItemArmor(scorpfARMOR, 4, EntityEquipmentSlot.HEAD), "scorphelmetfrost"),
                        setup(new MoCItemArmor(scorpfARMOR, 4, EntityEquipmentSlot.CHEST), "scorpplatefrost"),
                        setup(new MoCItemArmor(scorpfARMOR, 4, EntityEquipmentSlot.LEGS), "scorplegsfrost"),
                        setup(new MoCItemArmor(scorpfARMOR, 4, EntityEquipmentSlot.FEET), "scorpbootsfrost"),
                        setup(new MoCItemArmor(scorpuARMOR, 4, EntityEquipmentSlot.HEAD), "scorphelmetundead"),
                        setup(new MoCItemArmor(scorpuARMOR, 4, EntityEquipmentSlot.CHEST), "scorpplateundead"),
                        setup(new MoCItemArmor(scorpuARMOR, 4, EntityEquipmentSlot.LEGS), "scorplegsundead"),
                        setup(new MoCItemArmor(scorpuARMOR, 4, EntityEquipmentSlot.FEET), "scorpbootsundead"),

                        setup(new MoCItemWeapon(STING, 4), "scorpstingcave"),
                        setup(new MoCItemWeapon(STING, 1), "scorpstingdirt"),
                        setup(new MoCItemWeapon(STING, 3), "scorpstingnether"),
                        setup(new MoCItemWeapon(STING, 2), "scorpstingfrost"),
                        setup(new MoCItemWeapon(STING, 5), "scorpstingundead"),

                        setup(new MoCItem(), "tuskswood"),
                        setup(new MoCItem(), "tusksiron"),
                        setup(new MoCItem(), "tusksdiamond"),
                        setup(new MoCItem(), "elephantchest"),
                        setup(new MoCItem(), "elephantgarment"),
                        setup(new MoCItem(), "elephantharness"),
                        setup(new MoCItem(), "elephanthowdah"),
                        setup(new MoCItem(), "mammothplatform"),

                        setup(new MoCItem("scrolloffreedom", true), "scrolloffreedom"),
                        setup(new MoCItem("scrollofrenaming", true), "scrollofrenaming"),
                        setup(new MoCItem("scrollofsale", true), "scrollofsale"),
                        setup(new MoCItemFood(2, 0.1F, true).setPotionEffect(new PotionEffect(MobEffects.HUNGER, 30 * 20, 0), 0.8F), "crabraw"),
                        setup(new MoCItemFood(4, 0.6F, true), "crabcooked")
                );

        if (MoCreatures.proxy.enableFineSilver) {
            registry.registerAll(
                    setup(new MoCItem(), "fine_silver_ingot"),
                    setup(new MoCItem(), "fine_silver_nugget")
            );
        }

        for (int i = 0; i < 16; i++) {
            String colorName = EnumDyeColor.byMetadata(i).getTranslationKey().toLowerCase();
            if (colorName.equalsIgnoreCase("lightblue")) {
                colorName = "light_blue";
            }

            String registryName = "kittybed_" + colorName;
            registry.register(setup(new MoCItemKittyBed(registryName, i), registryName));
        }
    }

    @Nonnull
    public static <T extends IForgeRegistryEntry<T>> T setup(T entry, String name) {
        return setup(entry, new ResourceLocation(MoCConstants.MOD_ID, name));
    }

    @Nonnull
    public static <T extends IForgeRegistryEntry<T>> T setup(T entry, ResourceLocation registryName) {
        Preconditions.checkNotNull(entry, "Entry to setup must not be null!");
        Preconditions.checkNotNull(registryName, "Registry name to assign must not be null!");
        entry.setRegistryName(registryName);
        if (entry instanceof Block) {
            ((Block) entry).setTranslationKey(registryName.getNamespace() + "." + registryName.getPath()).setCreativeTab(MoCreatures.tabMoC);
        }
        if (entry instanceof Item) {
            ((Item) entry).setTranslationKey(registryName.getNamespace() + "." + registryName.getPath()).setCreativeTab(MoCreatures.tabMoC);
        }
        return entry;
    }
}