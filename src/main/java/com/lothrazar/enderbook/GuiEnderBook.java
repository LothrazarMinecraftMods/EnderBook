package com.lothrazar.enderbook;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiEnderBook  extends GuiScreen
{
	private final EntityPlayer entityPlayer;

	//public final ResourceLocation texture = new ResourceLocation(ModSamsContent.MODID, "textures/enderbook/textures/gui/book_ender.png" );
	
	public GuiEnderBook(EntityPlayer entityPlayer)
	{
		this.entityPlayer = entityPlayer;
	}
	int buttonCount = 10;
	@Override
	public void initGui()
	{
		ItemStack book = entityPlayer.getHeldItem();
		if(book.hasTagCompound() == false){book.setTagCompound(new NBTTagCompound());}

		int buttonID = 1, w = 64,h = 16 ,x,y;

		for(int i = 0; i < buttonCount; i++)
		{
			//TODO: loop over current waypoints and display
			//buttonList.add(new GuiButton(buttonID++, (width - 400) / 2 + (buttonID % 6) * 60, 20 + 30 * (buttonID / 6), 64, 16,"test"));
			
			x = (width - 400) / 2 + (buttonID % 6) * 60;
			y = 20;// + 30 * (buttonID / 6);
			 
			
			buttonList.add(new GuiButton(buttonID++, (width - 400) / 2 + (buttonID % 6) * 60, 20 + 30 * (buttonID / 6), 64, 16,"test"));
			
			buttonID++;
		}
	}
 
	@Override
	public void drawScreen(int par1, int par2, float par3)
	{
		drawDefaultBackground();
		drawCenteredString(fontRendererObj, "test string", width / 2, 6, 16777215);
		super.drawScreen(par1, par2, par3);
	}
	@Override
	protected void actionPerformed(GuiButton btn)
	{
		System.out.println("button clicked "+btn.id);
	}
	@Override
	public boolean doesGuiPauseGame()
	{
		return true;//setting to false, works but hen the game crashes when player opens regular inventor ywhile this is open..?
	}
}
