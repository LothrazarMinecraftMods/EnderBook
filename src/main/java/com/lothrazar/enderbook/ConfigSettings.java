package com.lothrazar.enderbook;

import net.minecraftforge.common.config.Configuration;

public class ConfigSettings 
{
	public static boolean doesPauseGame;
	public static boolean craftNetherStar;
	public static boolean showCoordTooltips;
	public static int maximumSaved;
	public static int btnsPerColumn;
	public static int expCostPerTeleport;
	public static Configuration config;
	public static String category_public;
	public static void load(Configuration c)
	{
		config = c;
		config.load();
		
		syncConfig();
	}
	public static void syncConfig()
	{
		String category = Configuration.CATEGORY_GENERAL;
		category_public = category;//cat for ingame gui
		doesPauseGame = config.getBoolean("pause_game_sp", category, false, "The Ender Book GUI will pause the game (single player)");
		
		craftNetherStar = config.getBoolean("needs_nether_star", category, true, "The Ender Book requires a nether star to craft.");
		
		showCoordTooltips = config.getBoolean("show_coordinates_tooltip", category, true, "Waypoint buttons will show the exact coordinates in a hover tooltip.");

		maximumSaved = config.getInt("max_saved", category, 16, 1, 999, "How many waypoints the book can store.");
		
		btnsPerColumn = config.getInt("show_per_column", category, 8, 1, 50, "Number of waypoints per column.  Change this if they are going off the screen for your chosen GUI Scale.");
		
		expCostPerTeleport = config.getInt("exp_per_teleport", category, 10, 0, 9999, "How many experience points are drained from the player on each teleport.  Set to zero for free teleports to your waypoints.");
		
		
		if(config.hasChanged()){config.save();}
	}
	
}
