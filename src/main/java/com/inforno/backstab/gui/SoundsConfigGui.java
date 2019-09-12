package com.inforno.backstab.gui;

import java.io.IOException;
import java.text.DecimalFormat;

import org.apache.commons.lang3.math.NumberUtils;
import org.lwjgl.input.Keyboard;

import com.inforno.backstab.config.Config;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraftforge.fml.client.config.GuiSlider;

public class SoundsConfigGui extends GuiScreen {
	
	private GuiButton done;
	private GuiTextField sound;
	private GuiSlider pitch, volume;
	private String soundString = "Sound:";
	private DecimalFormat format = new DecimalFormat("#.0");
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		sound.drawTextBox();
		drawString(fontRenderer, soundString, width/2 - 100, height/2 - 25, 0xFFFFFF);
		drawInformation(Config.soundsString, mouseX, mouseY, width/2 - 100, height/2 - 25, fontRenderer.getStringWidth(soundString), 10);
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
		sound = new GuiTextField(0, fontRenderer, width/2 - 43, height/2 - 30, 140, 20);
		sound.setMaxStringLength(1000);
		sound.setText(Config.backstabSound);
		buttonList.add(done = new GuiButton(-1, width/2 - 100, height - 33, 200, 20, "Done"));
		buttonList.add(pitch = new GuiSlider(0, width/2 - 100, height/2, 200, 20, "Pitch: ", "", 0.0, 5.0, Config.backstabPitch, true, true));
		buttonList.add(volume = new GuiSlider(1, width/2 - 100, height/2 + 30, 200, 20, "Volume: ", "", 0.0, 2.0, Config.backstabVolume, true, true));
		super.initGui();
	}
	
	@Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        sound.mouseClicked(mouseX, mouseY, mouseButton);
    	updateTextBoxes(sound);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
    	sound.textboxKeyTyped(typedChar, keyCode);
    	updateTextBoxes(sound);
        super.keyTyped(typedChar, keyCode);
    }
    
    @Override
	public void actionPerformed(GuiButton button) throws IOException {
		switch(button.id) {
		case -1:
			mc.displayGuiScreen(null);
			break;
		case 0:
			pitch.updateSlider();
			break;
		case 1:
			volume.updateSlider();
			break;
		}
		super.actionPerformed(button);
	}
    
    @Override
    public void updateScreen() {
    	pitch.setValue(Double.parseDouble(format.format(pitch.getValue())));
    	volume.setValue(Double.parseDouble(format.format(volume.getValue())));
    	Config.backstabPitch = Double.parseDouble(format.format(pitch.getValue()));
    	Config.backstabVolume = Double.parseDouble(format.format(volume.getValue()));
    }
    
    @Override
    public void onGuiClosed() {
    	sound.setFocused(false);
    	updateTextBoxes(sound);
    }
    
    public void updateTextBoxes(GuiTextField textField) {
    	if (Keyboard.isKeyDown(Keyboard.KEY_RETURN)) {
	    	textField.setFocused(false);
	    }
        if (!textField.getText().isEmpty() && !textField.isFocused()) {
        	switch (textField.getId()) {
        	case 0:
        		Config.backstabSound = textField.getText();
        		break;
        	}
            Config.syncFromFields();
        }
    }
}