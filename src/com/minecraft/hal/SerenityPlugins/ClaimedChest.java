package com.minecraft.hal.SerenityPlugins;

import org.bukkit.block.Chest;

public class ClaimedChest {
	public Chest chest;
	public String owner;
	public String id;
	
	public ClaimedChest(Chest chest, String player){
		this.chest = chest;
		this.owner = player;
	}
	
	public Boolean equals(ClaimedChest cc){
		if(this.chest.equals(cc.chest)){
			if(this.owner.equals(cc.owner))
				return true;
		}
		return false;
	}
	
	public void setId(String i){
		id = i;
	}
}
