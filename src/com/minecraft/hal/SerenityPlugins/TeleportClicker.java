package com.minecraft.hal.SerenityPlugins;

public class TeleportClicker {

	public String name;
	public Long lastClick;
	
	public TeleportClicker(String name, long lastClick){
		this.name= name;
		this.lastClick = lastClick;
	}
	
	public Long getLastClick(){
		return lastClick;
	}
	
	public String getName(){
		return name;
	}
}
