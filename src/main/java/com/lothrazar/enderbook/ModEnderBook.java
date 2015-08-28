package com.lothrazar.enderbook;
 
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
 
//TODO: fix // ,guiFactory = "com.lothrazar.samspowerups.gui.ConfigGuiFactory"
@Mod(modid = ModEnderBook.MODID,  useMetadata = true) 
public class ModEnderBook
{
	@Instance(value = ModEnderBook.MODID)
	public static ModEnderBook instance;
	@SidedProxy(clientSide = "com.lothrazar.enderbook.ClientProxy", serverSide = "com.lothrazar.enderbook.CommonProxy")
	public static CommonProxy proxy;
	//public static Logger logger;
	public static ConfigSettings config;
	public final static String MODID = "enderbook";
	public static String TEXTURE_LOCATION = MODID+":";
	public static final SimpleNetworkWrapper network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
	 

	public static int guiIndex = 52;
 
	@EventHandler
	public void onPreInit(FMLPreInitializationEvent event)
	{ 
		//logger = event.getModLog();
		
		config = new ConfigSettings(new Configuration(event.getSuggestedConfigurationFile()));
 
		MinecraftForge.EVENT_BUS.register(instance);
		FMLCommonHandler.instance().bus().register(instance);
	}
	
	 
	@EventHandler
	public void onInit(FMLInitializationEvent event)
	{     
		 ItemEnderBook.initEnderbook();

		//http://www.minecraftforge.net/forum/index.php?topic=7427.0

		 proxy.registerRenderers();
	//	 NetworkRegistry.INSTANCE.registerGuiHandler(this, new ModGuiHandler());
		 
		 
		 //register network packets
		 int packetID = 0;
		 network.registerMessage(PacketWarpButton.class, PacketWarpButton.class, packetID++, Side.SERVER);
		 network.registerMessage(PacketNewButton.class, PacketNewButton.class, packetID++, Side.SERVER);
	}
 
	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		
		ItemStack itemStack = event.entityPlayer.getCurrentEquippedItem();
		if (itemStack == null || itemStack.getItem() == null ) { return; }
		
		if (itemStack.getItem() == ItemEnderBook.itemEnderBook && event.action.RIGHT_CLICK_AIR == event.action)
		{ 
			EntityPlayer player = event.entityPlayer;
			//World world = event.world;
			Minecraft.getMinecraft().displayGuiScreen(new GuiEnderBook(player));
			//player.openGui(instance, guiIndex, world, (int) player.posX, (int) player.posY, (int) player.posZ);
		}
/*
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
		}*/
	}
 
	public static void registerItemHelper(Item s, String name)
	{
		s.setUnlocalizedName(name).setTextureName(TEXTURE_LOCATION + name);
		GameRegistry.registerItem(s, name);
	}
}
