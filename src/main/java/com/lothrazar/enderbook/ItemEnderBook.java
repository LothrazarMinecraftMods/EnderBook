package com.lothrazar.enderbook;

import java.util.ArrayList;
import java.util.List; 

import com.google.common.collect.Sets;   

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;

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
 
		 for(int i = 0; i <= ModEnderBook.config.maximumSaved; i++)
		 {
		 	 KEY = KEY_LOC + "_" + i;

			 String csv = itemStack.stackTagCompound.getString(KEY);
	
			 if(csv == null || csv.isEmpty()) {continue;} 
		 
			 list.add(new BookLocation(csv));
		 } 
		 
		 return list;
	}
	
	public static int getEmptySlot(ItemStack itemStack)
	{
		int empty = itemStack.stackTagCompound.getInteger(KEY_LARGEST);
	
		if(empty == 0) {empty = 1;}//first index is 1 not zero
		
		itemStack.stackTagCompound.setInteger(KEY_LARGEST,empty+1);//save the next empty one
		return empty;
	}

	public static void deleteWaypoint(EntityPlayer player, int slot) 
	{
		player.getHeldItem().stackTagCompound.setString(KEY_LOC + "_" + slot, null);		
	}
	
	public static void saveCurrentLocation(EntityPlayer player,String name) 
	{ 
		if (player.getHeldItem().stackTagCompound == null) {player.getHeldItem().stackTagCompound = new NBTTagCompound();}
	
		int id = getEmptySlot(player.getHeldItem());//int slot = entityPlayer.inventory.currentItem + 1;
    	BookLocation loc = new BookLocation(id,player  ,name);
    	  
		player.getHeldItem().stackTagCompound.setString(KEY_LOC + "_" + id, loc.toCSV());		
	} 
	
	private static BookLocation getLocation(ItemStack stack, int slot)
	{
		String csv = stack.stackTagCompound.getString(ItemEnderBook.KEY_LOC + "_" + slot);
		
		if(csv == null || csv.isEmpty()) 
		{
			//Relay.addChatMessage(event.entityPlayer, "No location saved at "+KEY);
			return null;
		}
		
		return new BookLocation(csv);
	}
	
	public static void teleport(EntityPlayer player,int slot)// ItemStack enderBookInstance 
	{ 
		//int slot = entityPlayer.inventory.currentItem+1;
		System.out.println("tp to "+slot);
    
    	ItemStack stack = player.getHeldItem();
		String csv = stack.stackTagCompound.getString(ItemEnderBook.KEY_LOC + "_" + slot);
		
		if(csv == null || csv.isEmpty()) 
		{
			System.out.println(" csv.isEmpty");
			//Relay.addChatMessage(event.entityPlayer, "No location saved at "+KEY);
			return;
		}
		
		BookLocation loc = getLocation(player.getHeldItem(),slot);
		if(player.dimension != loc.dimension)// TODO: Reference dim nums
		{
			System.out.println("Mismatched dimensions");
			//player.addChatMessage(new ChatTranslationComponent("Mismatched dimensions"));
			//Chat.addMessage(event.entityPlayer, "Only useable in the overworld");
			return;
		}
	 
	    player.setPositionAndUpdate(loc.X,loc.Y,loc.Z); 

	    
	    //TODO: a config entry so it takes durability?
		//player.getCurrentEquippedItem().damageItem(1, player);
	}
	 
	public static void initEnderbook()
	{
		itemEnderBook = new ItemEnderBook();

		String name = "book_ender";
		itemEnderBook.setUnlocalizedName(name).setTextureName(ModEnderBook.TEXTURE_LOCATION + name);
		GameRegistry.registerItem(itemEnderBook,name);
		

		if(ModEnderBook.config.craftNetherStar)
			GameRegistry.addRecipe(new ItemStack(itemEnderBook), 
				"ene", 
				"ebe",
				"eee", 
				'e', Items.ender_pearl, 
				'b', Items.book,
				'n', Items.nether_star //TODO : a config entry that decides if it uses a star or not
				);
		else
			GameRegistry.addRecipe(new ItemStack(itemEnderBook), 
				"eee", 
				"ebe",
				"eee", 
				'e', Items.ender_pearl, 
				'b', Items.book
				);

	}

}
 