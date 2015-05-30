package com.minecraft.hal.SerenityPlugins;

public class LastLogin implements Comparable<LastLogin>{
	
	public String name;
	public long timeInMilliseconds;
	
	public LastLogin(String name, long timeInMilliseconds){
		this.name = name;
		this.timeInMilliseconds = timeInMilliseconds;
	}

	@Override
	public int compareTo(LastLogin arg0) {
		if(this.timeInMilliseconds > arg0.timeInMilliseconds){
			return 1;
		}
		if(this.timeInMilliseconds < arg0.timeInMilliseconds){
			return -1;
		}
		return 0;
	}
}
