package inforno.backstab;

import inforno.backstab.config.Config;
import inforno.backstab.config.ConfigHelper;
import inforno.backstab.events.Events;
import inforno.backstab.items.BackstabItems;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Backstab.MODID)
public class Backstab {
	
	public static final String MODID = "backstab";
	public static final Logger LOGGER = LogManager.getLogger(MODID);
	
	public Backstab() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigHelper.CONFIG_SPEC);
		BackstabItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
		MinecraftForge.EVENT_BUS.register(new Events());
	}
    
	public static boolean isValidItem(ItemStack stack) {
		return Config.backstabItemsAll || Config.itemsData.containsKey(ForgeRegistries.ITEMS.getKey(stack.getItem()).toString()) || stack.is(BackstabItems.Tags.BACKSTAB);
	}
	
	public static boolean isValidEntity(LivingEntity entity) {
		return (entity instanceof Player) ? Config.backstabPlayers : !Config.entities.contains(entity.getEncodeId());
	}
}
