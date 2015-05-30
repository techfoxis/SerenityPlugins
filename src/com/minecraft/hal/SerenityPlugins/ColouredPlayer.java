package com.minecraft.hal.SerenityPlugins;

public class ColouredPlayer {
	public String player;
	public String color;
	
	public ColouredPlayer(String player, String color){
		this.player = player;
		this.color = color;
	}
	
	public String getColor(){
		return color;
	}
	
	public String getPlayer(){
		return player;
	}
}
