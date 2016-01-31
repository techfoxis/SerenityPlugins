package com.minecraft.hal.SerenityPlugins;

public class SerenityCommand {

	public String command;
	public String description;
	public String longDescription;
	public String example;
	public int timeRequired;
	public boolean secret;
	
	public SerenityCommand(String cmd, String desc, String longDesc, String example, int timeRequired, boolean secret){
		command = cmd;
		description = desc;
		this.longDescription = longDesc;
		this.example = example;
		this.timeRequired = timeRequired;
		this.secret = secret;
	}
}

