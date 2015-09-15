package com.lothrazar.enderbook;

import net.minecraft.entity.player.EntityPlayer;
 
public class BookLocation
{
	public double X;
	public double Y;
	public double Z;
	public int id;
	public int dimension; 
	public String display; 
 
	public BookLocation(int idx,EntityPlayer p,String d)
	{
		X = p.posX;
		Y = p.posY;
		Z = p.posZ;
		id = idx;
		dimension = p.dimension;
		display = d;
	}
	
	public BookLocation(String csv)
	{
		String[] pts = csv.split(",");
		id = Integer.parseInt(pts[0]);
		X = Double.parseDouble(pts[1]);
		Y = Double.parseDouble(pts[2]);
		Z = Double.parseDouble(pts[3]);
		dimension = Integer.parseInt(pts[4]); 
		if(pts.length > 5)
			display = pts[5]; 
	}
	
	public String toCSV()
	{
		return id+","+X+","+Y+","+Z + ","+dimension+ ","+display;	 
	}
	
	public String coordsDisplay()
	{
		return Math.round(X)+", "+Math.round(Y)+", "+Math.round(Z);	// + showName	
	} 
}
