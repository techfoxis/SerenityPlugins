package com.minecraft.hal.SerenityPlugins;

import java.util.GregorianCalendar;

import org.bukkit.Location;

public class FireWorkShow {
	public String name;
	public Location location;
	public GregorianCalendar lastShow;
	public boolean isActive;
	
	public FireWorkShow(String name, Location l) {
		this.name = name;
		this.location = l;
		lastShow = new GregorianCalendar(2000, 1, 1, 0, 0, 0);
		isActive = false;
		}
	
	public FireWorkShow() {
		this.name = "I haven't been initialized";
		this.location = null;
	}
	
	public Location getLocation() {
		return location;
	}

	public String getName() {
		return name;
	}
	
	public GregorianCalendar getLastShow(){
		return lastShow;
	}
	
	public void setLastShow(GregorianCalendar time){
		lastShow = time;
	}
		
	public boolean equals(FireWorkShow fws){
		if(this.name.equals(fws.name)){
			if(this.location.equals(fws.location)){
				return true;
			}
		}
		return false;
	}
	
	public void setLocation(Location l){
		this.location = l;
	}
	
	public void setName(String s){
		this.name = s;
	}
	
	public void setActive(boolean active){
		this.isActive = active;
	}
	
	public boolean isActive(){
		return isActive;
	}
	
	
}
