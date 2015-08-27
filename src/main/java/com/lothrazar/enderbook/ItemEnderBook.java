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

public class ItemEnderBook extends ItemTool
{ 
	public static String KEY_LOC = "location"; 
	public static String KEY_LARGEST = "loc_largest"; 
	public static ItemEnderBook itemEnderBook;
	public static final int MAX_SAVED = 9;
	private static int DURABILITY = 50;
	
	public ItemEnderBook( )
	{  
		super(1.0F,Item.ToolMaterial.WOOD, Sets.newHashSet()); 
    	this.setMaxDamage(DURABILITY);
		this.setMaxStackSize(1);
    	setCreativeTab(CreativeTabs.tabTransport) ; 
	}
	
	
	public static ArrayList<Location> getLocations(ItemStack itemStack)
	{
		 ArrayList<Location> list = new  ArrayList<Location>();
		 
		 String KEY; 
 
		 for(int i = 0; i <= MAX_SAVED; i++)
		 {

		 	 KEY = KEY_LOC + "_" + i;

			 String csv = itemStack.stackTagCompound.getString(KEY);
	
			 if(csv == null || csv.isEmpty()) {continue;} 
		 
			 list.add(new Location(csv));
 
		 } 
		 
		 return list;
	}
	public static int getEmptySlot(ItemStack itemStack)
	{
		int empty =  itemStack.stackTagCompound.getInteger(KEY_LARGEST);
		itemStack.stackTagCompound.setInteger(KEY_LARGEST,empty+1);
		return empty;
	}
	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4) 
	{ 
	     if (itemStack.stackTagCompound == null) 
	     { 
        	 list.add("Right Click while sneaking to set location" );
	    	 return;
	     }
	     
	     ItemStack held = player.getCurrentEquippedItem();

		 int slot = player.inventory.currentItem + 1;
			
	     String KEY;
	     Location loc;
	     String display;
	     for(int i = 1; i <= 9; i++)
	     {

	     	 KEY = KEY_LOC + "_" + i;

	 		String csv = itemStack.stackTagCompound.getString(KEY);

			if(csv == null || csv.isEmpty()) {continue;} 
			loc = new Location(csv);
 
			if(slot == i && held != null && held.equals(itemStack))
				display = EnumChatFormatting.GRAY+ "["+ EnumChatFormatting.RED + i +EnumChatFormatting.GRAY+ "] " ;
			else
				display = EnumChatFormatting.GRAY+ "["+ i + "] " ;
			
				 
	    	 list.add(display+EnumChatFormatting.DARK_GREEN + loc.toDisplayNoCoords());
	     } 
	 }

	public static void saveCurrentLocation(EntityPlayer entityPlayer) 
	{ 
		if (entityPlayer.getHeldItem().stackTagCompound == null) {entityPlayer.getHeldItem().stackTagCompound = new NBTTagCompound();}
	
		
		int id = getEmptySlot(entityPlayer.getHeldItem());//int slot = entityPlayer.inventory.currentItem + 1;
    	Location loc = new Location(id
    			,entityPlayer.posX
    			,entityPlayer.posY
    			,entityPlayer.posZ
    			,entityPlayer.dimension 
    			,""//,biome.biomeName
    			);
    	
    	String KEY = ItemEnderBook.KEY_LOC + "_" + id;

		System.out.println("new loc "+id);
		
		entityPlayer.getHeldItem().stackTagCompound.setString(KEY, loc.toCSV());		
	} 
	
	public static void teleport(EntityPlayer entityPlayer, ItemStack enderBookInstance) 
	{ 
		int slot = entityPlayer.inventory.currentItem+1;
    	String KEY = ItemEnderBook.KEY_LOC + "_" + slot;
    	
		String csv = enderBookInstance.stackTagCompound.getString(KEY);
		
		if(csv == null || csv.isEmpty()) 
		{
			//Relay.addChatMessage(event.entityPlayer, "No location saved at "+KEY);
			return;
		}
		
		Location loc = new Location(csv);
		
		if(entityPlayer.dimension != 0)// TODO: Reference dim nums
		{
			//Chat.addMessage(event.entityPlayer, "Only useable in the overworld");
			return;
		}
	
		if(loc.dimension == 1) // TODO: Reference dim nums
		{
			entityPlayer.setFire(4);
		} 
		else if(loc.dimension == -1)// TODO: Reference dim nums
		{
			entityPlayer.heal(-15);
		}
  
	    entityPlayer.setPositionAndUpdate(loc.X,loc.Y,loc.Z); 

		entityPlayer.getCurrentEquippedItem().damageItem(1, entityPlayer);
	}
	 
	public static void initEnderbook()
	{
		itemEnderBook = new ItemEnderBook();

		ModEnderBook.registerItemHelper(itemEnderBook, "book_ender");

		GameRegistry.addRecipe(new ItemStack(itemEnderBook), "eee", "ebe",
				"eee", 'e', Items.ender_pearl, 'b', Items.book);
		GameRegistry.addSmelting(itemEnderBook, new ItemStack(
				Items.ender_pearl, 8), 0);
	}
}
 