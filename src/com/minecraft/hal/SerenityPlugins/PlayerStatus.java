package com.minecraft.hal.SerenityPlugins;

import java.util.GregorianCalendar;

public class PlayerStatus implements Comparable<PlayerStatus> {
	public String playerName;
	public String playerStatus;
	public GregorianCalendar time;
	
	public PlayerStatus(String name, String status, GregorianCalendar time){
		this.playerName = name;
		this.playerStatus = status;
		this.time = time;
	}
	
	public String getName(){
		return playerName;
	}
	
	public String getStatus(){
		return playerStatus;
	}
	
	public GregorianCalendar getTime(){
		return time;
	}
	
	public String toString(){
		GregorianCalendar currTime = new GregorianCalendar();
		Long difference = currTime.getTimeInMillis() - time.getTimeInMillis();
		String s = "";
		if(difference < 1000)
		s += "§3" + playerName + " §7§o(" + difference + " milliseconds ago): §r\n" + playerStatus + "\n";
		else if(difference < 60000)
		s += "§3" + playerName + " §7§o(" + difference/1000 + " seconds ago): §r\n" + playerStatus + "\n";
		else if(difference < 14400000)
		s += "§3" + playerName + " §7§o(" + difference/60000 + " minutes ago): §r\n" + playerStatus + "\n";
		else 
		s += "§3" +playerName + " §7§o(" + difference/3600000 + " hours ago): §r\n" + playerStatus + "\n";
		return s;
	}

	@Override
	public int compareTo(PlayerStatus o) {
		if(this.getName().equals(o.getName())){
			return 0;
		}
		if(this.getTime().getTimeInMillis() < o.getTime().getTimeInMillis()){
			return -1;
		}
		else
			return 1;
	}
	
}

