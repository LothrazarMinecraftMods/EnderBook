package com.lothrazar.enderbook;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
 
public class ClientProxy extends CommonProxy
{
	@Override
	public void registerRenderers()
	{

        ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();

        String name = ModEnderBook.TEXTURE_LOCATION + "book_ender";

   		mesher.register(ItemEnderBook.itemEnderBook, 0, new ModelResourceLocation( name , "inventory"));	 
        
	}
}
