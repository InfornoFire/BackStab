package inforno.backstab.events;

import inforno.backstab.Backstab;
import inforno.backstab.BackstabDamageSource;
import inforno.backstab.command.Command;
import inforno.backstab.config.Config;

import inforno.backstab.items.DaggerItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class Events {

	private boolean chargedAttack;
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
    public void onLivingHurtEvent(LivingHurtEvent event) {
		// Allow mobs backstab in future
		if (event.getSource().getEntity() != null && event.getSource().getEntity() instanceof Player) {
			Player player = (Player) event.getSource().getEntity();
			if (!player.level.isClientSide
			&& (event.getSource().msgId.equals("player") || event.getSource().msgId.equals("mob"))
			&& (!Config.backstabSneaking || player.isShiftKeyDown())
			&& Backstab.isValidItem(player.getMainHandItem())
			&& Backstab.isValidEntity(event.getEntity())) {
				double targetYaw = event.getEntity().getYHeadRot() % 360;
				//Unfortunately cannot be from ImmediateSource due to how weird arrows are with direction
				double attackerYaw = event.getSource().getEntity().getYHeadRot() % 360;
				if (attackerYaw < 0) attackerYaw += 360;
		        if (targetYaw < 0) targetYaw += 360;
				if(Math.abs(targetYaw - attackerYaw) < Config.backstabDegrees || 360 - Math.abs(targetYaw - attackerYaw) < Config.backstabDegrees) {
					float oldAmount = event.getAmount();
					Double[] data = Config.itemsData.get(ForgeRegistries.ITEMS.getKey(player.getMainHandItem().getItem()).toString());
					if (data == null) data = new Double[]{Config.backstabMultiplier, Config.backstabBonus};
					if (chargedAttack) {
						event.setAmount((float) (event.getAmount() + data[1]));
						chargedAttack = false;
					}
					event.setAmount((float) (event.getAmount() * data[0]));
					event.setCanceled(true);
					// Add oldAmount because cancelling subtracts previous
					event.getEntity().hurt(BackstabDamageSource.causeBackstabDamage(player).bypassArmor(), event.getAmount() + oldAmount);
					player.playNotifySound(getRegisteredSoundEvent(Config.backstabSound), SoundSource.PLAYERS, (float) Config.backstabVolume, (float) Config.backstabPitch);
				}
			}
		}
    }

	// Very hacky, needs to be redone as a mixin in the future
	@SubscribeEvent
	public void onAttackEntity(final AttackEntityEvent event) {
		if (!event.getEntity().level.isClientSide) {
			chargedAttack = event.getEntity().getAttackStrengthScale(0) == 1.0F ? true : false;
			//Inspired by djmanly's Simple Sweep Mod: https://www.curseforge.com/minecraft/mc-mods/simple-sweep
			if (event.getEntity().getMainHandItem().getItem() instanceof DaggerItem && event.getEntity().isOnGround()) {
				event.getEntity().setOnGround(false);
			}
		}
	}
	
	@SubscribeEvent
    public void onCommandsRegister(RegisterCommandsEvent event) {
        Command.register(event.getDispatcher(), event.getBuildContext());
    }
	
	// Pulled from MinecraftForge's SoundEvents#getRegisteredSoundEvent
	// Modified from Forge 1.12.2
    private static SoundEvent getRegisteredSoundEvent(String id) {
        SoundEvent soundevent = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(id));
        if (soundevent == null) {
            throw new IllegalStateException("Invalid Sound requested: " + id);
        } else {
            return soundevent;
        }
    }
}
