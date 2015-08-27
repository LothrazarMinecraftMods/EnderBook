package com.lothrazar.enderbook;

import java.util.ArrayList;

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
	@Override
	public void initGui()
	{
		//great tips here http://www.minecraftforge.net/forum/index.php?topic=29945.0
	   // Keyboard.enableRepeatEvents(true);
		ItemStack book = entityPlayer.getHeldItem();
		if(book.hasTagCompound() == false){book.setTagCompound(new NBTTagCompound());}

		int buttonID = 0, w = 52,h = 20 ,x,y;
		buttonIdNew = buttonID;
		
		 ArrayList<Location> list = ItemEnderBook.getLocations(book);
		 
		//one button to create new waypoints. all the other ones just use a waypoint
		buttonList.add(new GuiButton(buttonIdNew, this.width/2,20,w,h,StatCollector.translateToLocal("gui.enderbook.new")));
// on new clicked, we want the server to run ItemEnderBook.saveCurrentLocation
		
		System.out.println("init with this many "+list.size());
		for(int i = 0; i < list.size(); i++)
		{
			x = (this.width - 400) / 2 - 2;
			buttonID++ ;
			y = 40 + 30 * (buttonID);
			
			buttonList.add(new GuiButton(buttonID++, x,y,w,h,StatCollector.translateToLocal("gui.enderbook.go")));

			buttonID++;
		}
	}
 
	@Override
	public void drawScreen(int par1, int par2, float par3)
	{
		drawDefaultBackground();
		drawCenteredString(fontRendererObj, StatCollector.translateToLocal("gui.enderbook.title"), width / 2, 6, 16777215);
		super.drawScreen(par1, par2, par3);
	}
	@Override
	protected void actionPerformed(GuiButton btn)
	{
		if(btn.id == buttonIdNew)
			ModEnderBook.network.sendToServer(new PacketNewButton());
		else
			ModEnderBook.network.sendToServer(new PacketWarpButton());
		
	}
	@Override
	public boolean doesGuiPauseGame()
	{
		return false;
	}
}
