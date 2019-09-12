package com.inforno.backstab.items;

import java.util.ArrayList;
import java.util.List;

import com.inforno.backstab.BackStab;
import com.inforno.backstab.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;import net.minecraftforge.fml.relauncher.Side;

public class Items {
	
	public static final List<Item> items = new ArrayList<Item>();
	
	public static Item wooddagger;
	public static Item stonedagger;
	public static Item irondagger;
	public static Item golddagger;
	public static Item diamonddagger;
	
	public static final void preinit() {
		wooddagger = registerItem(new ItemDagger(Item.ToolMaterial.WOOD), "wood_dagger");
		stonedagger = registerItem(new ItemDagger(Item.ToolMaterial.STONE), "stone_dagger");
		irondagger = registerItem(new ItemDagger(Item.ToolMaterial.IRON), "iron_dagger");
		golddagger = registerItem(new ItemDagger(Item.ToolMaterial.GOLD), "gold_dagger");
		diamonddagger = registerItem(new ItemDagger(Item.ToolMaterial.DIAMOND), "diamond_dagger");
	}
	
	public static final void registerRenders() {
		for(Item i : items) {
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(i, 0, 
					new ModelResourceLocation(Reference.MODID + ":" + i.getRegistryName().getResourcePath(), "inventory"));
		}
	}
	
	public static final Item registerItem(Item i, String n) {
		ResourceLocation r = new ResourceLocation(Reference.MODID, n);
		i.setUnlocalizedName(n);
		i.setRegistryName(r);
		items.add(i);
		return i;
	}
}
