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
		 
		 buttonNew = new GuiButton(buttonIdNew, this.width/2,20,w,h,StatCollector.translateToLocal("gui.enderbook.new"));
		//one button to create new waypoints. all the other ones just use a waypoint
		buttonList.add(buttonNew);
// on new clicked, we want the server to run ItemEnderBook.saveCurrentLocation
		
		//System.out.println("init with this many "+list.size());
		for(int i = 0; i < list.size(); i++)
		{
			x = (this.width - 400) / 2 - 2;
			buttonID = list.get(i).id;
			System.out.println("new button with id "+buttonID);
		//	buttonID++;
			y += h + ypad;
			//TODO: coordinates on the button?
			
			//buttonID+ " "+
			buttonList.add(new GuiButton(buttonID, x,y,w,h
					,StatCollector.translateToLocal("gui.enderbook.go")+" "+list.get(i).toDisplay()));

			//buttonID++;
			
			//drawHoveringText
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
			if (buttonList.get(i) instanceof GuiButton) 
			{
				GuiButton btn = (GuiButton) buttonList.get(i);
				if (btn.func_146115_a()) 
				{
					String[] desc = { "test"+btn.id};
			 
					drawHoveringText(Arrays.asList(desc), x, y, fontRendererObj);
				}
			}
		}
	    /**
	     * Returns 0 if the button is disabled, 1 if the mouse is NOT hovering over this button and 2 if it IS hovering over
	     * this button.
	    
	  
		if(this.buttonNew.getHoverState(this.buttonNew.enabled) == 2)
		{
			ArrayList<String> tooltips = new ArrayList<String>();
			tooltips.add("test");
			
			//hoverhttp://www.minecraftforge.net/forum/index.php?topic=29228.0
			
			this.func_146283_a(tooltips, buttonNew.xPosition, buttonNew.yPosition);
		}
		*/
		
		
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
