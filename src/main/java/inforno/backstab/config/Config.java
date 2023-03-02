package inforno.backstab.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import inforno.backstab.Backstab;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.*;

public class Config {

	public static final String GENERAL = "general";
	public static DoubleValue specBonus;
	public static DoubleValue specMultiplier;
	public static DoubleValue specDegrees;
	public static BooleanValue specSneaking;
	public static BooleanValue specBypassArmor;
	
	public static final String ITEMS = "items";
	public static ConfigValue<List<? extends String>> specItems;
	public static BooleanValue specItemsAll;
	
	public static final String ENTITIES = "entities";
	public static ConfigValue<List<? extends String>> specEntities;
	public static BooleanValue specPlayers;
	
	public static final String SOUNDS = "sounds";
	public static ConfigValue<String> specSound;
	public static DoubleValue specVolume;
	public static DoubleValue specPitch;
	
	public static String bonusString = "Adds bonus damage on backstab with a fully charged attack [Default: 0.0]";
	public static String multiplierString = "Changes the damage multiplier [Default: 1.5]";
	public static String degreeString = "Changes the degree needed to backstab [Default: 45]";
	public static String sneakingString = "Requires sneaking to backstab? [Default: false]";
	public static String bypassArmorString = "Backstab damage bypasses armor [Default: false]";
	public static String itemsString = "What items CAN backstab? [Format: modid:itemid,(override multiplier),(override bonus)], if override value is negative or missing, the config multiplier and bonus will be used instead.";
	public static String itemsString2 = "Note this is separate from the tags system where items or item tags can be added to backstab:backstab which will only use default values (whitelist overrides tags)";
	public static String itemsAllString = "Overrides items, will enable everything (including fists) to backstab [Default: false]";
	public static String entitiesString = "What mobs CANNOT be backstabbed? [Format: modid:entityid], [Default: minecraft:ender_dragon] due to messy interactions";
	public static String playersString = "Can players be backstabbed by other players?";
	public static String soundString = "Sound played when the player backstab [Format: Resource Location or /playsound (sound) format, Default: 'minecraft:entity.arrow.hit_player']";
	public static String volumeString = "Changes the volume of the backstab sound [Default: 0.75]";
	public static String pitchString = "Changes the pitch of the backstab sound [Default: 0.4]";

	public Config(ForgeConfigSpec.Builder builder) {
		builder.push(GENERAL);
		specBonus = builder
				.comment(bonusString)
				.translation(Backstab.MODID + ".config.bonus")
				.defineInRange("bonus", 0.0, 0.0, 1000.0);
		specMultiplier = builder
				.comment(multiplierString)
				.translation(Backstab.MODID + ".config.multiplier")
				.defineInRange("multiplier", 1.5, 0.0, 1000.0);
		specDegrees = builder
				.comment(degreeString)
				.translation(Backstab.MODID + ".config.degree")
				.defineInRange("degree", 45.0, 0.0, 360.0);
		specSneaking = builder
				.comment(sneakingString)
				.translation(Backstab.MODID + ".config.sneaking")
				.define("sneaking", false);
		specBypassArmor = builder
				.comment(bypassArmorString)
				.translation(Backstab.MODID + ".config.armorbypass")
				.define("bypassArmor", false);
		builder.pop();
		
		builder.push(ITEMS);
		specItems = builder
				.comment(itemsString)
				.comment(itemsString2)
				.translation(Backstab.MODID + ".config.items")
				.defineList("items", Arrays.asList(new String[]{"backstab:wood_dagger,1.5", "backstab:stone_dagger,1.7", "backstab:iron_dagger,1.8", "backstab:gold_dagger,1.5", "backstab:diamond_dagger,1.8", "backstab:netherite_dagger,1.8,1.0"}), string -> string instanceof String);
		specItemsAll = builder
				.comment(itemsAllString)
				.translation(Backstab.MODID + ".config.itemsall")
				.define("itemsall", false);
		builder.pop();
		
		builder.push(ENTITIES);
		specEntities = builder
				.comment(entitiesString)
				.translation(Backstab.MODID + ".config.entities_blacklist")
				.defineList("entities_blacklist", Arrays.asList(new String[]{"minecraft:ender_dragon"}), string -> string instanceof String);
		specPlayers = builder
				.comment(playersString)
				.translation(Backstab.MODID + ".config.players")
				.define("players", true);
		builder.pop();
		
		builder.push(SOUNDS);
		specSound = builder
				.comment(soundString)
				.translation(Backstab.MODID + ".config.sound")
				.define("sound", "minecraft:entity.arrow.hit_player");
		specVolume = builder
				.comment(volumeString)
				.translation(Backstab.MODID + ".config.volume")
				.defineInRange("volume", 0.75, 0.0, 100.0);
		specPitch = builder
				.comment(pitchString)
				.translation(Backstab.MODID + ".config.pitch")
				.defineInRange("pitch", 0.4, 0.0, 2.0);
		builder.pop();
	}
	
	public static double backstabBonus, backstabMultiplier, backstabDegrees;
	public static boolean backstabSneaking, backstabBypassArmor;
	public static boolean backstabItemsAll;
	public static boolean backstabPlayers;
	public static String backstabSound;
	public static double backstabVolume;
	public static double backstabPitch;
	
	public static List<String> items;
	public static HashMap<String, Double[]> itemsData;
	public static List<String> entities;
	
	public static void bakeConfig() { 
		backstabBonus = specBonus.get();
		backstabMultiplier = specMultiplier.get();
		backstabDegrees = specDegrees.get();
		backstabSneaking = specSneaking.get();
		backstabBypassArmor = specBypassArmor.get();
		backstabItemsAll = specItemsAll.get();
		backstabPlayers = specPlayers.get();
		backstabSound = specSound.get();
		backstabVolume = specVolume.get();
		backstabPitch = specPitch.get();

		items = (List<String>) specItems.get();
		itemsData = new HashMap<>();
		for (String item : items) {
			String[] arr = item.split(",");
			if (arr.length < 1) continue;
			Double mult = arr.length > 1 ? Double.parseDouble(arr[1]) : backstabMultiplier;
			Double bonus = arr.length > 2 ? Double.parseDouble(arr[2]) : backstabBonus;
			mult = mult < 0.0 ? backstabMultiplier : mult;
			bonus = bonus < 0.0 ? backstabBonus : bonus;
			itemsData.put(arr[0], new Double[]{mult, bonus});
		}

		entities = (List<String>) specEntities.get();
	}
}
