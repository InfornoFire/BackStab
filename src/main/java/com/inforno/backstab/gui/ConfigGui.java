package com.inforno.backstab.gui;

import java.io.IOException;
import java.text.DecimalFormat;

import com.inforno.backstab.config.Config;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.config.GuiSlider;

public class ConfigGui extends GuiScreen {
	
	private GuiButton done, sneaking, itemsall, items, entities, sounds;
	private GuiSlider multiplier, degree;
	private String configTrue = ":" + TextFormatting.GREEN + " True";
	private String configFalse = ":" + TextFormatting.RED + " False";
	private DecimalFormat format = new DecimalFormat("#.0");
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	public void drawInformation(String line, int mouseX, int mouseY, int startX, int startY, int width, int height) {
        if (mouseX >= startX && mouseX <= startX + width && mouseY >= startY && mouseY <= startY + height) {
            drawHoveringText(line, mouseX, mouseY);
        }
    }
	
	@Override
	public void initGui() {
		buttonList.clear();
		buttonList.add(done = new GuiButton(-1, width/2 - 100, height - 33, 200, 20, "Done"));
		buttonList.add(sneaking = new GuiButton(0, width/2 - 100, height/2 - 60, 200, 20, "Requires Sneaking" 
				+ (Config.backstabSneaking ? configTrue : configFalse)));
		buttonList.add(sneaking = new GuiButton(1, width/2 - 100, height/2 - 30, 200, 20, "Backstabbing Players" 
				+ (Config.backstabPlayers ? configTrue : configFalse)));
		buttonList.add(itemsall = new GuiButton(2, width/2 - 100, height/2, 200, 20, "All Items Override" 
				+ (Config.backstabItemsAll ? configTrue : configFalse)));
		buttonList.add(items = new GuiButton(3, width/2 - 100, height/2 + 30, 95, 20, "Items"));
		buttonList.add(entities = new GuiButton(4, width/2 + 5, height/2 + 30, 95, 20, "Entities"));
		buttonList.add(sounds = new GuiButton(5, width/2 - 100, height/2 + 60, 200, 20, "Sounds"));
		buttonList.add(multiplier = new GuiSlider(6, width/2 - 100, height/2 - 120, 200, 20, "Multplier Damage: ", "", 0.0, 50.0, Config.backstabMultiplier, true, true));
		buttonList.add(degree = new GuiSlider(7, width/2 - 100, height/2 - 90, 200, 20, "Degree: ", "", 0.0, 360.0, Config.backstabDegrees, true, true));
		super.initGui();
	}
	
	@Override
	public void updateScreen() {
		multiplier.setValue(Double.parseDouble(format.format(multiplier.getValue())));
		degree.setValue(Double.parseDouble(format.format(degree.getValue())));
		Config.backstabMultiplier = multiplier.getValue();
		Config.backstabDegrees = degree.getValue();
		Config.syncFromFields();
	}
	
	@Override
	public void actionPerformed(GuiButton button) throws IOException {
		switch(button.id) {
		case -1:
			mc.displayGuiScreen(null);
			break;
		case 0:
			Config.backstabSneaking = !Config.backstabSneaking ? true : false;
			initGui();
			break;
		case 1:
			Config.backstabPlayers = !Config.backstabPlayers ? true : false;
			initGui();
			break;
		case 2:
			Config.backstabItemsAll = !Config.backstabItemsAll ? true : false;
			initGui();
			break;
		case 3:
			mc.displayGuiScreen(new ItemsConfigGui());
			break;
		case 4:
			mc.displayGuiScreen(new EntitiesConfigGui());
			break;
		case 5:
			mc.displayGuiScreen(new SoundsConfigGui());
			break;
		case 6:
			multiplier.updateSlider();
			break;
		case 7:
			degree.updateSlider();
			break;
		}
		Config.syncFromFields();
		super.actionPerformed(button);
	}
}
