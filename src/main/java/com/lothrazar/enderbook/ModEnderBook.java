package com.lothrazar.enderbook;
 
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
 
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
		 
		 //register network packets
		 int packetID = 0;
		 network.registerMessage(PacketWarpButton.class, PacketWarpButton.class, packetID++, Side.SERVER);
		 network.registerMessage(PacketNewButton.class, PacketNewButton.class, packetID++, Side.SERVER);
		 network.registerMessage(PacketDeleteButton.class, PacketDeleteButton.class, packetID++, Side.SERVER);
	}
 
	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		ItemStack itemStack = event.entityPlayer.getCurrentEquippedItem();
		if (itemStack == null || itemStack.getItem() == null ) { return; }
		
		if (itemStack.getItem() == ItemEnderBook.itemEnderBook && event.action.RIGHT_CLICK_AIR == event.action)
		{ 
			EntityPlayer player = event.entityPlayer;

			Minecraft.getMinecraft().displayGuiScreen(new GuiEnderBook(player));
			
			//if it had a container, we would open it this way:
			//player.openGui(instance, guiIndex, world, (int) player.posX, (int) player.posY, (int) player.posZ);
		}
	}
}
