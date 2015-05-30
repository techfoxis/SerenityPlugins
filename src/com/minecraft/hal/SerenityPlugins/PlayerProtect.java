package com.minecraft.hal.SerenityPlugins;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PlayerProtect {
	
	public Player player;
	public int length =0;
	public Location location1 = null;
	public Location location2 = null;
	
	public PlayerProtect(Player player, int length){
		this.player = player;
		this.length = length;
	}
	
	public boolean isCenter(){
		return length!=0;
	}
	
	public void setLoc1(Location l){
		location1 = l;
	}
	
	public void setLoc2(Location l){
		location2 = l;
	}
}
