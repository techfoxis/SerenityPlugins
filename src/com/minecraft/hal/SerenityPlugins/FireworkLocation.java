package com.minecraft.hal.SerenityPlugins;

import org.bukkit.Location;

public class FireworkLocation implements Comparable<FireworkLocation>{
	
	public Location l;
	
	public FireworkLocation(Location l){
		this.l = l;
	}
	
	public Location getLocation(){
		return l;
	}

	@Override
	public int compareTo(FireworkLocation fl) {

		if(this.l.getX() < fl.getLocation().getX()){
			return -1;
		}
		else if(this.l.getX() > fl.getLocation().getX()){
			return 1;
		}
		
		return 0;
	}

}
