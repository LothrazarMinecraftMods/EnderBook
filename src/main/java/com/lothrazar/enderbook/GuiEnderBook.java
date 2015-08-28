package com.lothrazar.enderbook;

import java.util.ArrayList;
import java.util.Arrays;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
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
	public static int buttonIdNew;
	GuiButton buttonNew;
	@Override
	public void initGui()
	{
		//great tips here http://www.minecraftforge.net/forum/index.php?topic=29945.0
	   // Keyboard.enableRepeatEvents(true);
		ItemStack book = entityPlayer.getHeldItem();
		if(book.hasTagCompound() == false){book.setTagCompound(new NBTTagCompound());}

		int buttonID = 0, w = 52,h = 20 ,x,y = 40, ypad = 5;
		buttonIdNew = buttonID;
		
		 ArrayList<BookLocation> list = ItemEnderBook.getLocations(book);
		 
		 buttonNew = new GuiButtonBook(buttonIdNew, this.width/2,20,w,h,StatCollector.translateToLocal("gui.enderbook.new"));
		//one button to create new waypoints. all the other ones just use a waypoint
		buttonList.add(buttonNew);
// on new clicked, we want the server to run ItemEnderBook.saveCurrentLocation
		
		GuiButtonBook b;
		BookLocation loc;
		for(int i = 0; i < list.size(); i++)
		{
			loc = list.get(i);
	 
 
			x = (this.width - 350) / 2;
			y += h + ypad;
			b = new GuiButtonBook(loc.id, x,y,w,h,StatCollector.translateToLocal("gui.enderbook.go"));//+" "+loc.id
			b.setTooltip(list.get(i).toDisplay());
		
			b.enabled = (loc.dimension == this.entityPlayer.dimension);
		
			buttonList.add(b);
		}
	}
 
	@Override
	public void drawScreen(int x, int y, float par3)
	{
		drawDefaultBackground();
		drawCenteredString(fontRendererObj, StatCollector.translateToLocal("gui.enderbook.title"), width / 2, 6, 16777215);

		super.drawScreen(x, y, par3);
		
		//http://www.minecraftforge.net/forum/index.php?topic=18043.0
		for (int i = 0; i < buttonList.size(); i++) 
		{
			if (buttonList.get(i) instanceof GuiButtonBook) 
			{
				GuiButtonBook btn = (GuiButtonBook) buttonList.get(i);
				if (btn.func_146115_a() && btn.getTooltip() != null) 
				{
					//it takes a list, one on each line. but we use single line tooltips
					drawHoveringText(Arrays.asList(new String[]{ btn.getTooltip()}), x, y, fontRendererObj);
				}
			}
		}
	}
	@Override
	protected void actionPerformed(GuiButton btn)
	{
		if(btn.id == buttonIdNew)
		{
			
			ModEnderBook.network.sendToServer(new PacketNewButton());
		}
		else
			ModEnderBook.network.sendToServer(new PacketWarpButton(btn.id));
		
		
		this.entityPlayer.closeScreen();
		
	}
	@Override
	public boolean doesGuiPauseGame()
	{
		return false;
	}
}
