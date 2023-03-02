package inforno.backstab.config;

import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.apache.commons.lang3.tuple.Pair;

import inforno.backstab.Backstab;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Backstab.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ConfigHelper {
	
	public static final Config CONFIG;
	public static final ForgeConfigSpec CONFIG_SPEC;

	static {
		final Pair<Config, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Config::new);
		CONFIG = specPair.getLeft();
		CONFIG_SPEC = specPair.getRight();
	}
	
	@SubscribeEvent
	public static void onModConfigEvent(final ModConfigEvent configEvent) {
		Config.bakeConfig();
	}
}
