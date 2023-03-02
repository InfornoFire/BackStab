package inforno.backstab.items;

import inforno.backstab.Backstab;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BackstabItems {
	
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Backstab.MODID);
	
	public static final RegistryObject<DaggerItem> wooddagger = ITEMS.register("wood_dagger", () -> new DaggerItem(Tiers.WOOD, 2, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
	public static final RegistryObject<DaggerItem> stonedagger = ITEMS.register("stone_dagger", () -> new DaggerItem(Tiers.STONE, 3, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
	public static final RegistryObject<DaggerItem> irondagger = ITEMS.register("iron_dagger", () -> new DaggerItem(Tiers.IRON, 4, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
	public static final RegistryObject<DaggerItem> golddagger = ITEMS.register("gold_dagger", () -> new DaggerItem(Tiers.GOLD, 2, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
	public static final RegistryObject<DaggerItem> diamonddagger = ITEMS.register("diamond_dagger", () -> new DaggerItem(Tiers.DIAMOND, 5, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
	public static final RegistryObject<DaggerItem> netheritedagger = ITEMS.register("netherite_dagger", () -> new DaggerItem(Tiers.NETHERITE, 6, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

	public class Tags {
		public static final TagKey<Item> BACKSTAB = ItemTags.create(new ResourceLocation(Backstab.MODID, "backstab"));
	}
}