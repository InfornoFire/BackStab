package inforno.backstab;

import javax.annotation.Nullable;

import inforno.backstab.config.Config;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.item.ItemStack;

public class BackstabDamageSource extends EntityDamageSource {

	public static final DamageSource BACKSTAB = new DamageSource("backstab");

	private boolean backstabDamage;
	
	public BackstabDamageSource(@Nullable Entity damageSourceEntityIn) {
		super("backstab", damageSourceEntityIn);
	}

	// Silent kills in future
	@Override
	public Component getLocalizedDeathMessage(LivingEntity entity) {
		ItemStack itemstack = this.entity instanceof LivingEntity ? ((LivingEntity)this.entity).getMainHandItem() : ItemStack.EMPTY;
		String s = "death.attack." + this.msgId;
		return !itemstack.isEmpty() && itemstack.hasCustomHoverName() ? Component.translatable(s + ".item", entity.getDisplayName(), this.entity.getDisplayName(), itemstack.getDisplayName()) : Component.translatable(s, entity.getDisplayName(), this.entity.getDisplayName());
	}
	
	public DamageSource setBackstab() {
	      this.backstabDamage = true;
	      return this;
	}

	public static DamageSource causeBackstabDamage(Player player) {
		if (Config.backstabBypassArmor) {
			return new BackstabDamageSource(player).bypassArmor();
		}
		return new BackstabDamageSource(player);
	}
}
