package com.inforno.backstab.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.inforno.backstab.BackStab;
import com.inforno.backstab.Reference;
import com.inforno.backstab.gui.MainBackStabGui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.DummyConfigElement.DummyCategoryElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.GuiConfigEntries;
import net.minecraftforge.fml.client.config.GuiConfigEntries.CategoryEntry;
import net.minecraftforge.fml.client.config.IConfigElement;

public class ConfigGuiFactory implements IModGuiFactory{

	@Override
	public GuiScreen createConfigGui(GuiScreen parentScreen) {
		return new MainBackStabGui();
	}

	@Override
	public boolean hasConfigGui() {	
		return true;
	}

	@Override
	public void initialize(Minecraft minecraftInstance) {
		
	}

	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
		return null;
	}
}
