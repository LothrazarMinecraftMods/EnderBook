package com.lothrazar.enderbook;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
 
//TODO: fix // ,guiFactory = "com.lothrazar.samspowerups.gui.ConfigGuiFactory"
@Mod(modid = ModSamsContent.MODID,  useMetadata = true) 
public class ModSamsContent
{
	@Instance(value = ModSamsContent.MODID)
	public static ModSamsContent instance;
//	public static Logger logger;
	public final static String MODID = "enderbook";

	public static Configuration config;
 
	@EventHandler
	public void onPreInit(FMLPreInitializationEvent event)
	{ 
		//logger = event.getModLog();
		
		config = new Configuration(event.getSuggestedConfigurationFile());
 

		MinecraftForge.EVENT_BUS.register(instance);
		FMLCommonHandler.instance().bus().register(instance);
  
	}

	@EventHandler
	public void onInit(FMLInitializationEvent event)
	{     
		 ItemEnderBook.initEnderbook();
	}
 
	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		ItemStack itemStack = event.entityPlayer.getCurrentEquippedItem();

		if (itemStack == null || itemStack.getItem() == null ) { return; }

		if (event.action.LEFT_CLICK_BLOCK == event.action)
		{ 
			if (itemStack.getItem() == ItemEnderBook.itemEnderBook)
			{
				ItemEnderBook.teleport(event.entityPlayer, itemStack);
			} 
		} 
		else
		{
			if (itemStack.getItem() == ItemEnderBook.itemEnderBook)
			{
				ItemEnderBook.itemEnderBook.saveCurrentLocation( event.entityPlayer, itemStack);
			}
		}
	}
 
	 public static String TEXTURE_LOCATION = MODID+":";
 
	 public static void registerItemHelper(Item s, String name)
	 {
		 s.setUnlocalizedName(name).setTextureName(TEXTURE_LOCATION + name);
		 GameRegistry.registerItem(s, name);
	 }
	 
}
