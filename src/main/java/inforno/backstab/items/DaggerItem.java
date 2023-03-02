package inforno.backstab.items;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.SwordItem;

import net.minecraft.world.item.Item.Properties;

public class DaggerItem extends SwordItem {
	
	public DaggerItem(Tier tier, int damage, Properties properties) {
		super(tier, 0, -1F, properties);
	}
}
