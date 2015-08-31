package com.lothrazar.enderbook;

import net.minecraft.client.gui.GuiButton;

public class GuiButtonBook extends GuiButton
{
	private int bookSlot;
	public int getSlot()	
	{
		return bookSlot;
	}
	public GuiButtonBook(int id, int x, int y,	int w, int h, String txt, int slot) 
	{
		super(id, x, y, w, h, txt);
		bookSlot = slot;
	}
	private String tooltip = null;
	public void setTooltip(String s)
	{
		tooltip = s;
	}
	public String getTooltip()
	{
		return tooltip;
	}
}
