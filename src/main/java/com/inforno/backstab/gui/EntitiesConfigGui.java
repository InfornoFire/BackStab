package com.inforno.backstab.gui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import com.inforno.backstab.config.Config;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

public class EntitiesConfigGui extends GuiScreen {
	
	private GuiButton done, add, remove;
	private GuiTextField input;
	private GuiScroll list;
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		input.drawTextBox();
		drawString(fontRenderer, Config.entitiesString, width/2 - 200, height/2 - 115, 0xFFFFFF);
		list.drawScreen(mouseX, mouseY, partialTicks);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void initGui() {
		buttonList.clear();
		list = new GuiScroll(this, Config.entities, width/2 + 30, height/2, height/2 - 50, height/2 + 50, width/2 - 200, 20, width/2 - 190);
		input = new GuiTextField(0, fontRenderer, width/2 - 200, height/2 - 100, 200, 20);
		input.setMaxStringLength(1000);
		buttonList.add(done = new GuiButton(-1, width/2 - 100, height - 33, 200, 20, "Done"));
		buttonList.add(add = new GuiButton(0, width/2 + 5, height/2 - 100, 60, 20, "Add Entity"));
		buttonList.add(remove = new GuiButton(1, width/2 + 70, height/2 - 100, 75, 20, "Remove Entity"));
		super.initGui();
	}
	
	@Override
	public void actionPerformed(GuiButton button) throws IOException {
		switch(button.id) {
		case -1:
			mc.displayGuiScreen(null);
			break;
		case 0:
			if (!input.getText().isEmpty()) {
				Config.entities.add(input.getText());
				list.updateList(Config.entities);
				initGui();
			}
			break;
		case 1:
			Config.entities.remove(list.getIndex());
			list.updateList(Config.entities);
			initGui();
			break;
		}
		Config.syncFromFields();
		super.actionPerformed(button);
	}
	
	@Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        input.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
    	input.textboxKeyTyped(typedChar, keyCode);
    	if (Keyboard.isKeyDown(Keyboard.KEY_RETURN)) {
	    	input.setFocused(false);
	    }
        super.keyTyped(typedChar, keyCode);
    }
}
