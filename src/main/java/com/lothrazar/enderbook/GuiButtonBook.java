package com.lothrazar.enderbook;

import net.minecraft.client.gui.GuiButton;

public class GuiButtonBook extends GuiButton
{

	public GuiButtonBook(int id, int x, int y,	int w, int h, String txt) 
	{
		super(id, x, y, w, h, txt);
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
