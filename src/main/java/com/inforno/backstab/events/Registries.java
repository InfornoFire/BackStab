package com.inforno.backstab.events;

import com.inforno.backstab.config.Config;
import com.inforno.backstab.items.Items;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class Registries {
	
	@SubscribeEvent
	public static void ItemRegister(RegistryEvent.Register<Item> event) {
		if (Config.backstabItemsEnabled) {
			event.getRegistry().registerAll(Items.items.toArray(new Item[0]));
		}
	}
}