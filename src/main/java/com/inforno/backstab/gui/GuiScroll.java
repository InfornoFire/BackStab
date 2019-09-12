package com.inforno.backstab.gui;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraftforge.fml.client.GuiScrollingList;

public class GuiScroll extends GuiScrollingList {

	private GuiScreen parent;
	private int size, index, location;
	private List list;
	
	public GuiScroll(GuiScreen parent, List<String> list, int width, int height, int top, int bottom, int left, int entryHeight, int location) {
		super(Minecraft.getMinecraft(), width, height, top, bottom, left, entryHeight, parent.width, parent.height);
		this.parent = parent;
		updateList(list);
		size = list.size();
		this.location = location;
	}
	
	public void updateList(List<String> list) {
		this.list = list;
	}

	@Override
	protected int getSize() {
		return this.size;
	}

	@Override
	protected void elementClicked(int index, boolean doubleClick) {
		this.index = index;
	}

	@Override
	protected boolean isSelected(int index) {
		return this.index == index;
	}
	
	public int getIndex() {
		return index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	protected void drawBackground() {
		
	}

	@Override
	protected void drawSlot(int slotIdx, int entryRight, int slotTop, int slotBuffer, Tessellator tess) {
		parent.drawString(Minecraft.getMinecraft().fontRenderer, (String) list.get(slotIdx), location, slotTop + 5, 0xFFFFFF);
	}
}
