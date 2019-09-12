package com.inforno.backstab.config;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.inforno.backstab.BackStab;
import com.inforno.backstab.Reference;

import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Config {
	
	public static Configuration config;
	
	public static final String GENERAL = "general";
	public static double backstabMultiplier, backstabDegrees;
	public static boolean backstabSneaking;
	
	public static final String ITEMS = "items";
	public static String[] backstabItems = {};
	public static boolean backstabItemsAll, backstabItemsEnabled;
	
	public static final String ENTITIES = "entities";
	public static String[] backstabEntities = {};
	public static boolean backstabPlayers;
	
	public static final String SOUNDS = "sounds";
	public static String backstabSound;
	public static double backstabPitch;
	public static double backstabVolume;
	
	public static String multiplierString = "Changes the damage multiplier [Default: 5, Range: 0.0-1000.0]";
	public static String degreeString = "Changes the degree needed to backstab [Default: 45, Range: 0.0-360.0]";
	public static String sneakingString = "Requires sneaking to backstab? [Default: false]";
	public static String itemsString = "What items CAN backstab? [Format: modid:itemid]";
	public static String itemsAllString = "Overrides items, will enable everything (including fists) to backstab [Default: false]";
	public static String itemsEnabledString = "Should the daggers be enabled? Warning: Only change before you make a world [Default: true]";
	public static String entitiesString = "What mobs CANNOT be backstab? [Format: modid:entityid]";
	public static String playersString = "Can players be backstabbed by other players?";
	public static String soundsString = "Sound played when the player backstabs [Format: Resource Location or /playsound (sound) format, Default: 'block.anvil.place']";
	public static String pitchString = "Changes the pitch of the backstab sound [Default: 0.4, Range: 0.0-100.0]";
	public static String volumeString = "Changes the volume of the backstab sound [Default: 1.0, Range: 0.0-2.0]";
	
	private static String[] defaultitems = {"backstab:wood_dagger", "backstab:stone_dagger", "backstab:iron_dagger", "backstab:gold_dagger", "backstab:diamond_dagger"};
	private static String[] defaultentities = {};
	
	public static List<String> items;
	public static List<String> entities;
	
	public static void preinit() {
		File configFile = new File(Loader.instance().getConfigDir(), "backstab.cfg");
		config = new Configuration(configFile);
		syncFromFiles();
	}
	
	public static void clientPreInit() {
		MinecraftForge.EVENT_BUS.register(new ConfigEventHandler());
	 }
	
	public static Configuration getConfig() {
		return config;
	}
	 
	public static void syncFromFiles() {
		syncConfig(true, true);
	}
	 
	public static void syncFromGui() {
		syncConfig(false, true);
	}
	 
	public static void syncFromFields() {
		syncConfig(false, false);
	}
	 
	private static void syncConfig(boolean loadFromConfigFile, boolean readFieldsFromConfig) {
		if (loadFromConfigFile) {
			config.load(); 
		}
		
		Property propertyBackStabMultiplier = config.get(GENERAL, "multiplier", 5.0, multiplierString, 0.0, 1000.0);
		Property propertyBackStabDegrees = config.get(GENERAL, "degree", 45.0, degreeString, 0.0, 360.0);
		Property propertyBackStabSneaking = config.get(GENERAL, "sneaking", false, sneakingString);	
		Property propertyBackStabItems = config.get(ITEMS, "items", defaultitems, itemsString);
		Property propertyBackStabItemsAll = config.get(ITEMS, "itemsall", false, itemsAllString);;
		Property propertyBackStabItemsEnabled = config.get(ITEMS, "itemenabled", true, itemsEnabledString);
		Property propertyBackStabEntities = config.get(ENTITIES, "entities_blacklist", defaultentities, entitiesString);
		Property propertyBackStabPlayers = config.get(ENTITIES, "players", true, playersString);
		Property propertyBackStabSound = config.get(SOUNDS, "sound", "block.anvil.place", soundsString);
		Property propertyBackStabPitch = config.get(SOUNDS, "pitch", 0.4, pitchString, 0.0, 100.0);
		Property propertyBackStabVolume = config.get(SOUNDS, "volume", 1.0, volumeString, 0.0, 2.0);
		
		List<String> propertyOrderGeneral = new ArrayList<String>();
		propertyOrderGeneral.add(propertyBackStabMultiplier.getName());
		propertyOrderGeneral.add(propertyBackStabDegrees.getName());
		propertyOrderGeneral.add(propertyBackStabSneaking.getName());
		config.setCategoryPropertyOrder(GENERAL, propertyOrderGeneral);
		
		List<String> propertyOrderItems = new ArrayList<String>();
		propertyOrderItems.add(propertyBackStabItems.getName());
		propertyOrderItems.add(propertyBackStabItemsAll.getName());
		propertyOrderItems.add(propertyBackStabItemsEnabled.getName());
		config.setCategoryPropertyOrder(ITEMS, propertyOrderItems);
		
		List<String> propertyOrderEntities = new ArrayList<String>();
		propertyOrderEntities.add(propertyBackStabEntities.getName());
		propertyOrderEntities.add(propertyBackStabPlayers.getName());
		config.setCategoryPropertyOrder(ENTITIES, propertyOrderEntities);
		
		List<String> propertyOrderSounds = new ArrayList<String>();
		propertyOrderSounds.add(propertyBackStabSound.getName());
		propertyOrderSounds.add(propertyBackStabPitch.getName());
		propertyOrderSounds.add(propertyBackStabVolume.getName());
		config.setCategoryPropertyOrder(SOUNDS, propertyOrderSounds);
		
		if (readFieldsFromConfig) {
			backstabMultiplier = propertyBackStabMultiplier.getDouble();
			backstabDegrees = propertyBackStabDegrees.getDouble();
			backstabSneaking = propertyBackStabSneaking.getBoolean();
			backstabItems = propertyBackStabItems.getStringList();
			backstabItemsAll = propertyBackStabItemsAll.getBoolean();
			backstabItemsEnabled = propertyBackStabItemsEnabled.getBoolean();
			backstabPlayers = propertyBackStabPlayers.getBoolean();
			backstabSound = propertyBackStabSound.getString();
			backstabPitch = propertyBackStabPitch.getDouble();
			backstabVolume = propertyBackStabVolume.getDouble();
			items = new ArrayList<String>(Arrays.asList(backstabItems));
			entities = new ArrayList<String>(Arrays.asList(backstabEntities));
		}
		
		backstabItems = items.toArray(new String[items.size()]);
		backstabEntities = entities.toArray(new String[entities.size()]);
		
		propertyBackStabMultiplier.set(backstabMultiplier);
		propertyBackStabDegrees.set(backstabDegrees);
		propertyBackStabSneaking.set(backstabSneaking);
		propertyBackStabItems.set(backstabItems);
		propertyBackStabItemsAll.set(backstabItemsAll);
		propertyBackStabItemsEnabled.set(backstabItemsEnabled);
		propertyBackStabEntities.set(backstabEntities);
		propertyBackStabPlayers.set(backstabPlayers);
		propertyBackStabSound.set(backstabSound);
		propertyBackStabPitch.set(backstabPitch);
		propertyBackStabVolume.set(backstabVolume);
		
		if (config.hasChanged()) {
			config.save();
		}
	}
	 
	public static class ConfigEventHandler {
		@SubscribeEvent(priority = EventPriority.NORMAL)
		public void onEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
			if(event.getModID().equals(Reference.MODID)) {
				if (event.getConfigID().equals(GENERAL) || event.getConfigID().equals(ITEMS) || event.getConfigID().equals(ENTITIES) || event.getConfigID().equals(SOUNDS)) {
					syncFromGui();
				}
			}
		}
	}
}
