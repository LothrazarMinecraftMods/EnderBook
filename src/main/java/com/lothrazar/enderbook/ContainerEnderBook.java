package com.lothrazar.enderbook;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerEnderBook extends Container
{

	@Override
	public boolean canInteractWith(EntityPlayer p) 
	{

		return true;
	}

}
