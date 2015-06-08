package com.minecraft.hal.SerenityPlugins;

import org.bukkit.Location;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;

public class Mailbox {
	public String name;
	public Location location;
	public Inventory inv;
	
	public Mailbox(String name, Location l) {
		this.name = name;
		this.location = l;
		this.inv = ((Chest) this.getLocation().getBlock().getState()).getInventory();
	}
	
	public Mailbox() {
		this.name = "I haven't been initialized";
		this.location = null;
	}
	
	public Location getLocation() {
		return location;
	}

	public String getName() {
		return name;
	}

	public String toString() {
		String s = this.name + "'s mailbox is in: "
				+ this.location.getWorld().getName() + "\nAt:" + "\nX: "
				+ this.location.getX() + "\nY " + this.location.getY() + "\nZ "
				+ this.location.getZ();
		return s;
	}
	
	public boolean equals(Mailbox mb){
		if(this.name.equals(mb.name)){
			if(this.location.equals(mb.location)){
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
	
	public boolean hasMail(){
		return inv.getContents()[0] != null;
	}
	
	
}
