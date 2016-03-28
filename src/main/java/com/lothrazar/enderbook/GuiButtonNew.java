package com.lothrazar.enderbook;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.text.translation.I18n;

public class GuiButtonNew extends GuiButton
{
	private int bookSlot;
	public int getSlot()	
	{
		return bookSlot;
	}
	public GuiButtonNew(int id, int x, int y,	int w, int h,  int slot) 
	{
		super(id, x, y, w, h, 
				I18n.translateToLocal("gui.enderbook.new"));
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
