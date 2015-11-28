package com.lothrazar.enderbook;

import java.util.ArrayList; 
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.WorldServer;

public class ItemEnderBook extends Item
{ 
	public static String KEY_LOC = "location"; 
	public static String KEY_LARGEST = "loc_largest"; 
	public static ItemEnderBook itemEnderBook; 
	
	public ItemEnderBook( )
	{  
		super(); 
		this.setMaxStackSize(1);
    	setCreativeTab(CreativeTabs.tabTransport) ; 
	}
	
	public static ArrayList<BookLocation> getLocations(ItemStack itemStack)
	{
		 ArrayList<BookLocation> list = new  ArrayList<BookLocation>();
		 
		 String KEY; 
		 int end = getLargestSlot(itemStack);
		 for(int i = 0; i <= end; i++)
		 {
		 	 KEY = KEY_LOC + "_" + i;

			 String csv = itemStack.getTagCompound().getString(KEY);
	
			 if(csv == null || csv.isEmpty()) {continue;} 
		
			 list.add(new BookLocation(csv));
		 } 
		 
		 return list;
	}

	public static int getLargestSlot(ItemStack itemStack)
	{
		return itemStack.getTagCompound().getInteger(KEY_LARGEST);
	}
	public static int getEmptySlotAndIncrement(ItemStack itemStack)
	{
		int empty = itemStack.getTagCompound().getInteger(KEY_LARGEST);
	
		if(empty == 0) {empty = 1;}//first index is 1 not zero
		
		itemStack.getTagCompound().setInteger(KEY_LARGEST,empty+1);//save the next empty one
		return empty;
	}

	public static void deleteWaypoint(EntityPlayer player, int slot) 
	{	
		player.getHeldItem().getTagCompound().removeTag(KEY_LOC + "_" + slot);
	}
	
	public static void saveCurrentLocation(EntityPlayer player,ItemStack book, String name) 
	{ 
		if (book.getTagCompound() == null) {book.setTagCompound(new NBTTagCompound());}
	
		int id = getEmptySlotAndIncrement(book);//int slot = entityPlayer.inventory.currentItem + 1;
    	
		BookLocation loc = new BookLocation(id,player  ,name);
    	  
		book.getTagCompound().setString(KEY_LOC + "_" + id, loc.toCSV());		
	} 
	
	private static BookLocation getLocation(ItemStack stack, int slot)
	{
		String csv = stack.getTagCompound().getString(ItemEnderBook.KEY_LOC + "_" + slot);
		
		if(csv == null || csv.isEmpty()) 
		{
			//Relay.addChatMessage(event.entityPlayer, "No location saved at "+KEY);
			return null;
		}
		
		return new BookLocation(csv);
	}
	
	public static void teleport(EntityPlayer player,int slot)// ItemStack enderBookInstance 
	{  
    	ItemStack stack = player.getHeldItem();
		String csv = stack.getTagCompound().getString(ItemEnderBook.KEY_LOC + "_" + slot);
		
		if(csv == null || csv.isEmpty()) 
		{ 
			return;
		}
		
		BookLocation loc = getLocation(player.getHeldItem(),slot);
		if(player.dimension != loc.dimension)
		{ 
			return;
		}
		
		
		BlockPos dest = new BlockPos(loc.X,loc.Y,loc.Z);
		
		float f = 0.5F;//center the player on the block. also moving up so not stuck in floor
		//SHOULD be that length is only zero on save&quit. but just being safe
		if(MinecraftServer.getServer().worldServers.length > 0)
		{
			WorldServer s = MinecraftServer.getServer().worldServers[0];
			s.theChunkProviderServer.chunkLoadOverride = true;
			//s.theChunkProviderServer.unloadQueuedChunks();
			//s.theChunkProviderServer.unloadAllChunks();//!!NOTHING ELSE WORKED EITHERT!!
			s.theChunkProviderServer.loadChunk(dest.getX(),dest.getZ()); 
		}
		player.worldObj.markBlockForUpdate(new BlockPos(loc.X,loc.Y,loc.Z)); 
		player.worldObj.markBlockForUpdate(new BlockPos(loc.X,loc.Y-1,loc.Z)); 
		
	    //player.setPositionAndUpdate(loc.X-f,loc.Y + 0.9,loc.Z-f); 
	    
	    //MAYBE:
	    player.setPositionAndRotation(loc.X-f,loc.Y + 0.9,loc.Z-f,player.cameraYaw,player.cameraPitch); 

	    //MADE NO DIFFERENCEEEEEEEEEEEEEEEEEE
	   // String command = "/tp @p "+ dest.getX() +  " "+dest.getY()+" "+dest.getZ();
	    //MinecraftServer.getServer().getCommandManager().executeCommand(player, command);


		
		player.worldObj.markBlockForUpdate(new BlockPos(loc.X,loc.Y,loc.Z)); 
		player.worldObj.markBlockForUpdate(new BlockPos(loc.X,loc.Y,loc.Z)); 

	    //just in case nothing else works
//GRAVEYARD BELOW!!!
	  //LEAVING FAILED ATTEMPTS IN COMMENTS!!!
		/*
		Ticket ticket = ForgeChunkManager.requestTicket(ModEnderBook.instance, player.worldObj, ForgeChunkManager.Type.NORMAL);
		//load the chunk - could be far away
		if(ticket != null)
		ForgeChunkManager.forceChunk(ticket,  
				new ChunkCoordIntPair((int)loc.X,(int)loc.Z));
CommandTeleport x;
*/
	   // player.setPositionAndRotation(loc.X-f,loc.Y+f,loc.Z-f, player.cameraYaw, player.cameraPitch);
	    //yaw and pitch???
	     

		//player.worldObj.markBlockForUpdate(new BlockPos(loc.X,loc.Y,loc.Z)); 
		//player.worldObj.markBlockForUpdate(new BlockPos(loc.X,loc.Y-1,loc.Z)); 
		//player.worldObj.markBlockForUpdate(new BlockPos(loc.X,loc.Y-2,loc.Z)); 
		//player.worldObj.markBlockForUpdate(new BlockPos(loc.X,loc.Y-3,loc.Z)); 
		
     //   EnumSet enumset = EnumSet.noneOf(S08PacketPlayerPosLook.EnumFlags.class);
	//	((EntityPlayerMP)player).playerNetServerHandler.setPlayerLocation(loc.X-f,loc.Y+f,loc.Z-f, player.cameraYaw, player.cameraPitch, enumset);
		 
		//player.worldObj.getChunkProvider().provideChunk(dest);

	    
	    //TODO: maybe 	a config entry so it takes durability?
		//player.getCurrentEquippedItem().damageItem(1, player);
	    
	}
	 
	public static void initEnderbook()
	{
		itemEnderBook = new ItemEnderBook();

		String name = "book_ender";
		itemEnderBook.setUnlocalizedName(name);//.setTextureName(ModEnderBook.TEXTURE_LOCATION + name);
		GameRegistry.registerItem(itemEnderBook,name);
		

		if(ConfigSettings.craftNetherStar)
			GameRegistry.addRecipe(new ItemStack(itemEnderBook), 
				"ene", 
				"ebe",
				"eee", 
				'e', Items.ender_pearl, 
				'b', Items.book,
				'n', Items.nether_star  
				);
		else
			GameRegistry.addRecipe(new ItemStack(itemEnderBook), 
				"eee", 
				"ebe",
				"eee", 
				'e', Items.ender_pearl, 
				'b', Items.book
				);

		//if you want to clean out the book and start over
		GameRegistry.addShapelessRecipe(new ItemStack(itemEnderBook), new ItemStack(itemEnderBook));
	}

}
 