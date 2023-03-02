package inforno.backstab.command;

import inforno.backstab.Backstab;
import inforno.backstab.config.Config;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.*;

import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.*;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.network.chat.Component;
import net.minecraftforge.registries.ForgeRegistries;

public class Command {
	
	@SuppressWarnings("unused")
	public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext buildContext) {
		dispatcher.register(Commands.literal(Backstab.MODID).requires(context -> context.hasPermission(context.getServer().getOperatorUserPermissionLevel())).then(
			Commands.literal("config").then(
					Commands.literal("bonus_damage").then(
							Commands.argument("damage", DoubleArgumentType.doubleArg(0.0, 1000.0))
							.executes(context -> {
								Config.specBonus.set(DoubleArgumentType.getDouble(context, "damage"));
								Config.bakeConfig();
								context.getSource().sendSuccess(Component.translatable("Bonus damage on backstab with fully charged attack is set to " + Config.backstabBonus), true);
								return 0;
							})
					).executes(context -> {
						context.getSource().sendSuccess(Component.translatable("Bonus damage on backstab with fully charged attack is currently " + Config.backstabBonus), false);
						return 0;
					})
			).then(
					Commands.literal("damage_multiplier").then(
							Commands.argument("multiplier", DoubleArgumentType.doubleArg(0.0, 1000.0))
							.executes(context -> {
								Config.specMultiplier.set(DoubleArgumentType.getDouble(context, "multiplier"));
								Config.bakeConfig();
								context.getSource().sendSuccess(Component.translatable("Damage multiplier on backstab is set to " + Config.backstabMultiplier), true);
								return 0;
							})
					).executes(context -> {
						context.getSource().sendSuccess(Component.translatable("Damage multiplier on backstab is currently " + Config.backstabMultiplier), false);
						return 0;
					})
			).then(
					Commands.literal("degrees").then(
							Commands.argument("degrees", DoubleArgumentType.doubleArg(0.0, 360.0))
							.executes(context -> {
								Config.specDegrees.set(DoubleArgumentType.getDouble(context, "degrees"));
								Config.bakeConfig();
								context.getSource().sendSuccess(Component.translatable("Degrees to backstab is set to " + Config.backstabDegrees), true);
								return 0;
							})
					).executes(context -> {
						context.getSource().sendSuccess(Component.translatable("Degrees to backstab is currently " + Config.backstabDegrees), false);
						return 0;
					})
			).then(
					Commands.literal("requires_sneaking").then(
							Commands.argument("true/false", BoolArgumentType.bool())
							.executes(context -> {
								Config.specSneaking.set(BoolArgumentType.getBool(context, "true/false"));
								Config.bakeConfig();
								context.getSource().sendSuccess(Component.translatable("Sneaking required to backstab? " + Config.backstabSneaking), true);
								return 0;
							})
					).executes(context -> {
						context.getSource().sendSuccess(Component.translatable("Sneaking required backstab? " + Config.backstabSneaking), false);
						return 0;
					})
			).then(
					Commands.literal("bypass_armor").then(
							Commands.argument("true/false", BoolArgumentType.bool())
							.executes(context -> {
								Config.specBypassArmor.set(BoolArgumentType.getBool(context, "true/false"));
								Config.bakeConfig();
								context.getSource().sendSuccess(Component.translatable("Backstab damage bypass armor? " + Config.backstabBypassArmor), true);
								return 0;
							})
					).executes(context -> {
						context.getSource().sendSuccess(Component.translatable("Backstab damage bypass armor? " + Config.backstabSneaking), false);
						return 0;
					})
			).then(
					Commands.literal("items_whitelist").then(
						Commands.literal("add").then(
								Commands.argument("item_id", ItemArgument.item(buildContext))
								.executes(context -> {
									String id = ForgeRegistries.ITEMS.getKey(ItemArgument.getItem(context, "item_id").getItem()).toString();
									if (!Config.itemsData.containsKey(id)) {
										Config.items.add(id);
										Config.specItems.set(Config.items);
										context.getSource().sendSuccess(Component.translatable("ItemID " + id + " can now backstab"), true);
									} else {
										context.getSource().sendSuccess(Component.translatable("ItemID " + id + " can already backstab"), true);
									}
									return 0;
								}).then(Commands.argument("multiplier", DoubleArgumentType.doubleArg(0.0, 1000.0))
									.executes(context -> {
										String id = ForgeRegistries.ITEMS.getKey(ItemArgument.getItem(context, "item_id").getItem()).toString();
										if (Config.itemsData.containsKey(id)) {
											Config.items.removeIf(item -> item.startsWith(id));
											context.getSource().sendSuccess(Component.translatable("Replacing ItemID " + id + "'s data"), true);
										}
										Config.items.add(id + "," + DoubleArgumentType.getDouble(context, "multiplier"));
										Config.specItems.set(Config.items);
										Config.bakeConfig();
										context.getSource().sendSuccess(Component.translatable("ItemID " + id + " can now backstab"), true);
										return 0;
								}).then(Commands.argument("bonus", DoubleArgumentType.doubleArg(0.0, 1000.0))
									.executes(context -> {
										String id = ForgeRegistries.ITEMS.getKey(ItemArgument.getItem(context, "item_id").getItem()).toString();
										if (Config.itemsData.containsKey(id)) {
											Config.items.removeIf(item -> item.startsWith(id));
											context.getSource().sendSuccess(Component.translatable("Replacing ItemID " + id + "'s data"), true);
										}
										Config.items.add(id + "," + DoubleArgumentType.getDouble(context, "multiplier") + "," + DoubleArgumentType.getDouble(context, "bonus"));
										Config.specItems.set(Config.items);
										Config.bakeConfig();
										context.getSource().sendSuccess(Component.translatable("ItemID " + id + " can now backstab"), true);
										return 0;
									})
								))
						)).then(Commands.literal("remove").then(
										Commands.argument("item_id", ItemArgument.item(buildContext))
										.executes(context -> {
											String id = ForgeRegistries.ITEMS.getKey(ItemArgument.getItem(context, "item_id").getItem()).toString();
											if (Config.items.removeIf(item -> item.startsWith(id))) {
												Config.specItems.set(Config.items);
												context.getSource().sendSuccess(Component.translatable("ItemID " + id + " can no longer backstab"), true);
												return 0;
											}
											context.getSource().sendSuccess(Component.translatable("ItemID " + id + " was not found in list"), false);
											return 0;
										})
								)
						).executes(context -> {
							context.getSource().sendSuccess(Component.translatable("List of items that can backstab: " + Config.items.toString()), false);
							return 0;
						})
			).then(
					Commands.literal("all_items").then(
							Commands.argument("true/false", BoolArgumentType.bool())
							.executes(context -> {
								Config.specItemsAll.set(BoolArgumentType.getBool(context, "true/false"));
								Config.bakeConfig();
								context.getSource().sendSuccess(Component.translatable("All items can backstab? " + Config.backstabItemsAll), true);
								return 0;
							})
					).executes(context -> {
						context.getSource().sendSuccess(Component.translatable("All items can backstab? " + Config.backstabItemsAll), false);
						return 0;
					})
			).then(
					Commands.literal("players").then(
							Commands.argument("true/false", BoolArgumentType.bool())
							.executes(context -> {
								Config.specPlayers.set(BoolArgumentType.getBool(context, "true/false"));
								Config.bakeConfig();
								context.getSource().sendSuccess(Component.translatable("Can backstab Players? " + Config.backstabItemsAll), true);
								return 0;
							})
					).executes(context -> {
						context.getSource().sendSuccess(Component.translatable("Can backstab Players? " + Config.backstabItemsAll), false);
						return 0;
					})
			).then(
					Commands.literal("entities_blacklist").then(
							Commands.literal("add").then(
									Commands.argument("entity_id", EntitySummonArgument.id()).suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
									.executes(context -> {
										String id = EntitySummonArgument.getSummonableEntity(context, "entity_id").toString();
										if (!Config.entities.contains(id)) {
											Config.entities.add(id);
											Config.specEntities.set(Config.entities);
											context.getSource().sendSuccess(Component.translatable("EntityID " + id + " can no longer be backstabed"), true);
										} else {
											context.getSource().sendSuccess(Component.translatable("EntityID " + id + " is already blacklisted"), true);
										}
										return 0;
									})
							)
					).then(
							Commands.literal("remove").then(
									Commands.argument("entity_id", EntitySummonArgument.id())
									.executes(context -> {
										String id = EntitySummonArgument.getSummonableEntity(context, "entity_id").toString();
										if (Config.entities.remove(id)) {
											Config.specEntities.set(Config.entities);
											context.getSource().sendSuccess(Component.translatable("EntityID " + id + " can now be backstabed"), true);
											return 0;
										}
										context.getSource().sendSuccess(Component.translatable("EntityID " + id + " was not found in list"), false);
										return 0;
									})
							)
					).executes(context -> {
						context.getSource().sendSuccess(Component.translatable("List of entities that cannot be backstabed: " + Config.entities.toString()), false);
						return 0;
					})
			).then(
					Commands.literal("sound").then(
							Commands.argument("sound_id", ResourceLocationArgument.id()).suggests(SuggestionProviders.AVAILABLE_SOUNDS)
							.executes(context -> {
								Config.specSound.set(ResourceLocationArgument.getId(context, "sound_id").toString());
								Config.bakeConfig();
								context.getSource().sendSuccess(Component.translatable("SoundID " + ResourceLocationArgument.getId(context, "sound_id") + " is now played when someone backstabs"), true);
								return 0;
							})
					).executes(context -> {
						context.getSource().sendSuccess(Component.translatable("SoundID played currently when backstabbing: " + Config.backstabSound), false);
						return 0;
					})
			).then(
					Commands.literal("sound_pitch").then(
							Commands.argument("pitch", DoubleArgumentType.doubleArg(0.0, 100.0))
							.executes(context -> {
								Config.specPitch.set(DoubleArgumentType.getDouble(context, "pitch"));
								Config.bakeConfig();
								context.getSource().sendSuccess(Component.translatable("Pitch of backstab sound is set to " + Config.backstabPitch), true);
								return 0;
							})
					).executes(context -> {
						context.getSource().sendSuccess(Component.translatable("Pitch of backstabs sound is currently " + Config.backstabPitch), false);
						return 0;
					})
			).then(
					Commands.literal("sound_volume").then(
							Commands.argument("volume", DoubleArgumentType.doubleArg(0.0, 2.0))
							.executes(context -> {
								Config.specVolume.set(DoubleArgumentType.getDouble(context, "volume"));
								Config.bakeConfig();
								context.getSource().sendSuccess(Component.translatable("Volume of backstab sound is set to " + Config.backstabVolume), true);
								return 0;
							})
					).executes(context -> {
						context.getSource().sendSuccess(Component.translatable("Volume of backstabs sound is currently " + Config.backstabVolume), false);
						return 0;
					})
			)
		));
	}
}
