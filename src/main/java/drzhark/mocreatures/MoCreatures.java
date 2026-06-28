/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures;

import com.mojang.authlib.GameProfile;
import drzhark.mocreatures.client.MoCKeyHandler;
import drzhark.mocreatures.compat.CompatHandler;
import drzhark.mocreatures.compat.datafixes.BlockIDFixer;
import drzhark.mocreatures.compat.datafixes.EntityIDFixer;
import drzhark.mocreatures.compat.datafixes.ItemIDFixer;
import drzhark.mocreatures.compat.tinkers.TinkersConstructIntegration;
import drzhark.mocreatures.dimension.MoCWorldProviderWyvernSkylands;
import drzhark.mocreatures.entity.MoCEntityData;
import drzhark.mocreatures.entity.tameable.MoCPetMapData;
import drzhark.mocreatures.event.MoCEventHooks;
import drzhark.mocreatures.event.MoCEventHooksClient;
import drzhark.mocreatures.event.MoCEventHooksTerrain;
import drzhark.mocreatures.init.MoCCreativeTabs;
import drzhark.mocreatures.init.MoCEntities;
import drzhark.mocreatures.init.MoCRecipes;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.command.CommandMoCPets;
import drzhark.mocreatures.network.command.CommandMoCSpawn;
import drzhark.mocreatures.network.command.CommandMoCTP;
import drzhark.mocreatures.network.command.CommandMoCreatures;
import drzhark.mocreatures.proxy.MoCProxy;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ModFixs;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;

@Mod(modid = MoCConstants.MOD_ID, name = MoCConstants.MOD_NAME, version = MoCConstants.MOD_VERSION, acceptableRemoteVersions = MoCConstants.MOD_ACCEPTED_VERSIONS, dependencies = MoCConstants.MOD_DEPENDENCIES)
public class MoCreatures {

    public static final Logger LOGGER = LogManager.getLogger(MoCConstants.MOD_ID);
    public static final CreativeTabs tabMoC = new MoCCreativeTabs(CreativeTabs.CREATIVE_TAB_ARRAY.length, "MoCreaturesTab");
    public static final String MOC_LOGO = TextFormatting.WHITE + "[" + TextFormatting.AQUA + MoCConstants.MOD_NAME + TextFormatting.WHITE + "]";
    @Instance(MoCConstants.MOD_ID)
    public static MoCreatures instance;
    @SidedProxy(clientSide = "drzhark.mocreatures.proxy.MoCProxyClient", serverSide = "drzhark.mocreatures.proxy.MoCProxy")
    public static MoCProxy proxy;
    public static GameProfile MOCFAKEPLAYER = new GameProfile(UUID.fromString("6E379B45-1111-2222-3333-2FE1A88BCD66"), "[MoCreatures]");
    public static DimensionType WYVERN_SKYLANDS;
    public static int wyvernSkylandsDimensionID;
    public static Object2ObjectLinkedOpenHashMap<String, MoCEntityData> mocEntityMap = new Object2ObjectLinkedOpenHashMap<>();
    public static Object2ObjectOpenHashMap<Class<? extends EntityLiving>, MoCEntityData> entityMap = new Object2ObjectOpenHashMap<>();
    public static Int2ObjectOpenHashMap<Class<? extends EntityLiving>> instaSpawnerMap = new Int2ObjectOpenHashMap<>();
    public MoCPetMapData mapData;

    public static boolean isServer() {
        return (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER);
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MoCMessageHandler.init();
        MinecraftForge.EVENT_BUS.register(new MoCEventHooks());
        MinecraftForge.TERRAIN_GEN_BUS.register(new MoCEventHooksTerrain());
        proxy.configInit(event);
        if (!isServer()) {
            MinecraftForge.EVENT_BUS.register(new MoCEventHooksClient());
            MinecraftForge.EVENT_BUS.register(new MoCKeyHandler());
            if (Loader.isModLoaded("tconstruct")) {
                MinecraftForge.EVENT_BUS.register(new TinkersConstructIntegration());
            }
        }
        MoCEntities.registerEntities();
        CompatHandler.preInit();
    }

    @EventHandler
    public void load(FMLInitializationEvent event) {
        wyvernSkylandsDimensionID = proxy.wyvernDimension;
        proxy.mocSettingsConfig.save();
        proxy.registerRenderers();
        proxy.registerRenderInformation();
        WYVERN_SKYLANDS = DimensionType.register("Wyvern Skylands", "_wyvern_skylands", wyvernSkylandsDimensionID, MoCWorldProviderWyvernSkylands.class, false);
        DimensionManager.registerDimension(wyvernSkylandsDimensionID, WYVERN_SKYLANDS);
        MoCEventHooksTerrain.addBiomeTypes();
        MoCEntities.registerSpawns();
        MoCEventHooksTerrain.buildWorldGenSpawnLists();
        MoCRecipes.RegistrationHandler.registerOreDictionaries();
        CompatHandler.init();
        ModFixs modFixer = FMLCommonHandler.instance().getDataFixer().init(MoCConstants.MOD_ID, MoCConstants.DATAFIXER_VERSION);
        modFixer.registerFix(FixTypes.BLOCK_ENTITY, new BlockIDFixer());
        modFixer.registerFix(FixTypes.ENTITY, new EntityIDFixer());
        modFixer.registerFix(FixTypes.ITEM_INSTANCE, new ItemIDFixer());
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        CompatHandler.postInit();
    }

    @EventHandler
    public void loadComplete(FMLLoadCompleteEvent event) {
        if (MoCreatures.proxy.debug) {
            for (Biome biome : ForgeRegistries.BIOMES.getValuesCollection()) {
                for (Biome.SpawnListEntry entry : biome.getSpawnableList(EnumCreatureType.CREATURE)) {
                    LOGGER.info("Creature is spawnable in biome " + biome.biomeName + ": " + entry.entityClass);
                }
                for (Biome.SpawnListEntry entry : biome.getSpawnableList(EnumCreatureType.WATER_CREATURE)) {
                    LOGGER.info("Water Creature is spawnable in biome " + biome.biomeName + ": " + entry.entityClass);
                }
                for (Biome.SpawnListEntry entry : biome.getSpawnableList(EnumCreatureType.MONSTER)) {
                    LOGGER.info("Monster is spawnable in biome " + biome.biomeName + ": " + entry.entityClass);
                }
                for (Biome.SpawnListEntry entry : biome.getSpawnableList(EnumCreatureType.AMBIENT)) {
                    LOGGER.info("Ambient is spawnable in biome " + biome.biomeName + ": " + entry.entityClass);
                }
            }
        }
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandMoCreatures());
        event.registerServerCommand(new CommandMoCTP());
        event.registerServerCommand(new CommandMoCPets());
        if (isServer()) {
            if (FMLCommonHandler.instance().getMinecraftServerInstance().isDedicatedServer()) {
                event.registerServerCommand(new CommandMoCSpawn());
            }
        }
    }
}
