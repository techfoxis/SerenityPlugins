package com.minecraft.hal.SerenityPlugins;

import org.bukkit.entity.Player;

public class AfkPlayer {
	
	public Player player;
	public Long lastAfk;
	
	public AfkPlayer(Player player, Long lastAfk){
		this.player = player;
		this.lastAfk = lastAfk;
	}
}
