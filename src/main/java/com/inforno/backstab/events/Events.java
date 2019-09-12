package com.inforno.backstab.events;

import java.util.List;
import java.util.Arrays;

import com.inforno.backstab.BackStab;
import com.inforno.backstab.config.Config;
import com.inforno.backstab.items.Items;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;

public class Events {
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onLivingHurtEvent(LivingHurtEvent event) {
		if (event.getSource().getTrueSource() != null && event.getSource().getTrueSource() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
			if (!player.world.isRemote 
			&& BackStab.isValidItem(player.getHeldItemMainhand().getItem())
			&& BackStab.isValidEntity(event.getEntityLiving())) {
				if (!Config.backstabSneaking || player.isSneaking()) {
					double targetYaw = event.getEntityLiving().getRotationYawHead() % 360;
					//Unfortunately cannot be from ImmediateSource due to how weird arrows are with direction
					double attackerYaw = event.getSource().getTrueSource().getRotationYawHead() % 360;
					if (attackerYaw < 0) attackerYaw += 360;
			        if (targetYaw < 0) targetYaw += 360;
					if(Math.abs(targetYaw - attackerYaw) < Config.backstabDegrees || 360 - Math.abs(targetYaw - attackerYaw) < Config.backstabDegrees) {
						event.setAmount((float) (event.getAmount() * Config.backstabMultiplier));
						player.world.playSound(null, player.posX, player.posY, player.posZ, getRegisteredSoundEvent(Config.backstabSound), SoundCategory.PLAYERS, (float) Config.backstabPitch, (float) Config.backstabVolume);
					}
				}
			}
		}
    }
	
	//Pulled from MinecraftForge's SoundEvents#getRegisteredSoundEvent
    private static SoundEvent getRegisteredSoundEvent(String id) {
        SoundEvent soundevent = SoundEvent.REGISTRY.getObject(new ResourceLocation(id));
        if (soundevent == null) {
            throw new IllegalStateException("Invalid Sound requested: " + id);
        } else {
            return soundevent;
        }
    }
}
