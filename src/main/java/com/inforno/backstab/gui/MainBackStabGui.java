package com.inforno.backstab.gui;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class MainBackStabGui extends GuiScreen {
	
	private GuiButton done, config;
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void initGui() {
		buttonList.clear();
		buttonList.add(done = new GuiButton(-1, width/2 - 100, height - 33, 200, 20, "Done"));
		buttonList.add(config = new GuiButton(0, width/2 - 100, height/2, 200, 20, "Configurations"));
		super.initGui();
	}
	
	@Override
	public void actionPerformed(GuiButton button) throws IOException {
		switch(button.id) {
		case -1:
			mc.displayGuiScreen(null);
			break;
		case 0:
			mc.displayGuiScreen(new ConfigGui());
			break;
		}
		super.actionPerformed(button);
	}
}
