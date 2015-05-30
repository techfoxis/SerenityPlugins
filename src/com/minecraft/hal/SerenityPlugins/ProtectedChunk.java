package com.minecraft.hal.SerenityPlugins;

import java.util.ArrayList;

import org.bukkit.Chunk;

public class ProtectedChunk {

	public Chunk chunk;
	public String owner;
	public ArrayList<String> trustedPlayers;
	
	public ProtectedChunk(Chunk chunk, String owner) {
		this.chunk = chunk;
		this.owner = owner;
		trustedPlayers = new ArrayList<String>();
	}

	public void addTrust(String name) {
		if (!trustedPlayers.contains(name)) {
			trustedPlayers.add(name);
		}
	}

	public boolean unTrust(String name) {
		for (int i = 0; i < trustedPlayers.size(); i++) {
			if (trustedPlayers.get(i).equals(name)) {
				trustedPlayers.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public boolean hasPermission(String name){
		if(name.equalsIgnoreCase(owner)){return true;}
		for(String names: trustedPlayers){
			if(names.equalsIgnoreCase(name)){
				return true;
			}
		}
		return false;
	}
	
	public String trustList(){
		String s = " - " + this.owner;
		for(int i = 0; i < trustedPlayers.size(); i++){
			s += "\n - " + trustedPlayers.get(i);
		}
		return s;
	}
	
	public String getId(){
		return chunk.getWorld().getName() + chunk.getX() + "_" + chunk.getZ();
	}
	
	public boolean equals(Chunk chunk){
		if(this.chunk.getX() == chunk.getX() && this.chunk.getZ() == chunk.getZ() && this.chunk.getWorld().equals(chunk.getWorld())){
			return true;
		}
		return false;
	}
	
	
}
