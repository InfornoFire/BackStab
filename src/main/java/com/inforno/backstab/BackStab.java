package com.inforno.backstab;

import java.util.List;

import com.inforno.backstab.config.Config;
import com.inforno.backstab.events.Events;
import com.inforno.backstab.items.Items;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(name = Reference.NAME, 
	version = Reference.VERSION,
	modid = Reference.MODID, 
	guiFactory = Reference.GUIFACTORY)
public class BackStab {
	
	@Mod.Instance(Reference.MODID)
	public static BackStab instance;
	
	public static boolean isValidItem(Item item) {
		return Config.backstabItemsAll ? true : containsNoCase(Config.items, item.getRegistryName().toString());
	}
	
	public static boolean isValidEntity(EntityLivingBase entity) {
		return (entity instanceof EntityPlayer) ? Config.backstabPlayers : !containsNoCase(Config.entities, EntityList.getKey(entity.getClass()).toString());
	}
	
	private static boolean containsNoCase(List<String> list, String s) {
        for (int i = 0; i < list.toArray().length; i++) {
            if (s.equalsIgnoreCase((String) list.toArray()[i]))  {
            	return true;
            }
        }
        return false;
	}
	
	@EventHandler
	public void preinit(FMLPreInitializationEvent e) {
		Items.preinit();
		Config.preinit();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent e) {
		MinecraftForge.EVENT_BUS.register(new Events());
	}
	
	@EventHandler
	@SideOnly(Side.CLIENT)
	public void clientInit(FMLInitializationEvent e) {
		Items.registerRenders();
		Config.clientPreInit();
	}
}
