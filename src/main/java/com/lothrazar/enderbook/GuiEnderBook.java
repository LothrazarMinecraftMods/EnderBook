package com.lothrazar.enderbook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.lwjgl.input.Keyboard;




import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;//http://www.minecraftforge.net/forum/index.php?topic=22378.0
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
	final int btnsPerColumn = 7;
	//public final ResourceLocation texture = new ResourceLocation(ModSamsContent.MODID, "textures/enderbook/textures/gui/book_ender.png" );
	
	public GuiEnderBook(EntityPlayer entityPlayer)
	{
		this.entityPlayer = entityPlayer;
	}
	int buttonCount = 10;
	public static int buttonIdNew;
	GuiButton buttonNew;
	GuiTextField txtNew;
	@Override
	public void initGui()
	{
		//great tips here http://www.minecraftforge.net/forum/index.php?topic=29945.0

		ItemStack book = entityPlayer.getHeldItem();
		if(book.hasTagCompound() == false){book.setTagCompound(new NBTTagCompound());}

		int buttonID = 0, w = 70,h = 20 , ypad = 5;
		buttonIdNew = buttonID;
		
		ArrayList<BookLocation> list = ItemEnderBook.getLocations(book);
		
		buttonNew = new GuiButtonBook(buttonIdNew, 
				this.width/2 - w,//x
				20,//y
				w,h,
				StatCollector.translateToLocal("gui.enderbook.new"));

		buttonList.add(buttonNew);
	//System.out.println("Currentsize= "+ItemEnderBook.getLocations(entityPlayer.getHeldItem()).size());
		if(entityPlayer.getHeldItem() != null && 
				ItemEnderBook.getLocations(entityPlayer.getHeldItem()).size() >= ModEnderBook.config.maximumSaved)
		{
			buttonNew.enabled = false;//also a tooltip?
		}

		
		txtNew = new GuiTextField(this.fontRendererObj,
				buttonNew.xPosition + buttonNew.width + 8,
				buttonNew.yPosition, 
				w,h);
		
		txtNew.setMaxStringLength(10);
		//default to the current biome
		txtNew.setText(entityPlayer.worldObj.getBiomeGenForCoords((int)entityPlayer.posX, (int)entityPlayer.posY).biomeName);
		txtNew.setFocused(true);
		
		GuiButtonBook b;
		BookLocation loc;
		String buttonText;
		int yStart = 45;
		int xStart = (this.width/10);
		int x = xStart;
		int y = yStart;
		for(int i = 0; i < list.size(); i++)
		{
			loc = list.get(i);
			buttonText = (loc.display == null) ? StatCollector.translateToLocal("gui.enderbook.go") : loc.display;
			
			
			if(i % btnsPerColumn == 0)  //do we start a new row?
			{ 
				x += w + 10;
				y = yStart;
			}
			else 
			{
				y += h + ypad;
			}
			
			b = new GuiButtonBook(loc.id, x,y,w,h,buttonText);//+" "+loc.id
			b.setTooltip(list.get(i).coordsDisplay());
		
			b.enabled = (loc.dimension == this.entityPlayer.dimension);
		
			buttonList.add(b);
		}
	}
 
	@Override
	public void drawScreen(int x, int y, float par3)
	{
		drawDefaultBackground();
		drawCenteredString(fontRendererObj, StatCollector.translateToLocal("gui.enderbook.title"), width / 2, 6, 16777215);
		
		//TODO::: http://www.minecraftforge.net/forum/index.php?topic=22378.0
		txtNew.drawTextBox();
		
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
			ModEnderBook.network.sendToServer(new PacketNewButton(txtNew.getText()));
		}
		else
		{
			ModEnderBook.network.sendToServer(new PacketWarpButton(btn.id));
			
			//TODO: particles/sounds/etc., maybe verify dimensins>?
			Random rand = this.entityPlayer.worldObj.rand;
			
			this.entityPlayer.worldObj.spawnParticle("portal", entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ 
					, entityPlayer.posX+rand.nextDouble() * 0.6D 
					, entityPlayer.posY+rand.nextDouble() * 0.6D 
					, entityPlayer.posZ+rand.nextDouble() * 0.6D  );
			
			//RenderGlobal.s
			//todo: also at future loc?
		}
		
		this.entityPlayer.closeScreen();
	}
	
	@Override
	public boolean doesGuiPauseGame()
	{
		return ModEnderBook.config.doesPauseGame;
	}
	
	//http://www.minecraftforge.net/forum/index.php?topic=22378.0
	//below is all the stuff that makes the text box NOT broken
	@Override
	public void updateScreen()
    {
        super.updateScreen();
        txtNew.updateCursorCounter();
    }
	@Override
	protected void keyTyped(char par1, int par2)
    {
        super.keyTyped(par1, par2);
        txtNew.textboxKeyTyped(par1, par2);
    }
	@Override
	protected void mouseClicked(int x, int y, int btn) 
	{
        super.mouseClicked(x, y, btn);
        txtNew.mouseClicked(x, y, btn);
    }
	//ok end of textbox fixing stuff
}
