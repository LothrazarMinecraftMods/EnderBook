package com.lothrazar.enderbook;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiEnderBook  extends GuiScreen
{
	private final EntityPlayer entityPlayer;

	public GuiEnderBook(EntityPlayer entityPlayer)
	{
		this.entityPlayer = entityPlayer;
	}
	// public final ResourceLocation texture = new ResourceLocation(.modid, "textures/enderbook/textures/gui" );
	@Override
	public void initGui()
	{
		ItemStack book = entityPlayer.getHeldItem();
		if(book.hasTagCompound() == false){book.setTagCompound(new NBTTagCompound());}
			
		System.out.println("gui ender success");
		int buttonID = 0;
		buttonID++;
		//TODO: loop over current waypoints and display
		buttonList.add(new GuiButton(buttonID++, (width - 400) / 2 + (buttonID % 6) * 60, 20 + 30 * (buttonID / 6), 64, 16,"test"));
		
		
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
		return false;
	}
}
