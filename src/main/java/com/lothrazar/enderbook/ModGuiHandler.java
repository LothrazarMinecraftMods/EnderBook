package com.lothrazar.enderbook;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class ModGuiHandler implements IGuiHandler
{

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world,	int x, int y, int z) 
	{

		//if(id == ModSamsContent.guiIndex)return new ContainerEnderBook();
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world,	int x, int y, int z) 
	{
		System.out.println(id + " == "+ ModSamsContent.guiIndex);

		if(id == ModSamsContent.guiIndex) return new GuiEnderBook(player);
		
		return null;
	}
}