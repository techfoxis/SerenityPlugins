package com.minecraft.hal.SerenityPlugins;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public class Ignoring {
	public ArrayList<String> ignoreList;
	public Player player;
	
	public Ignoring(Player player){
		this.player = player;
		ignoreList = new ArrayList<String>();
	}
}
