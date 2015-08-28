package com.lothrazar.enderbook;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
 
public class BookLocation
{
	public double X;
	public double Y;
	public double Z;
	public int id;
	public int dimension; 
 
	public BookLocation(int idx,EntityPlayer p)
	{
		X = p.posX;
		Y = p.posY;
		Z = p.posZ;
		id = idx;
		dimension = p.dimension;
	}
	
	public BookLocation(String csv)
	{
		String[] pts = csv.split(",");
		id = Integer.parseInt(pts[0]);
		X = Double.parseDouble(pts[1]);
		Y = Double.parseDouble(pts[2]);
		Z = Double.parseDouble(pts[3]);
		dimension = Integer.parseInt(pts[4]); 
	}
	
	public String toCSV()
	{
		return id+","+X+","+Y+","+Z + ","+dimension;	// + ","+name	
	}
	
	public String toDisplay()
	{
		return "["+id + "] "+Math.round(X)+", "+Math.round(Y)+", "+Math.round(Z);	// + showName	
	} 
}
