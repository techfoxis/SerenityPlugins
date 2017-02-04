package org.serenity.maven.plugins.SerenityPlugins;

import java.util.GregorianCalendar;
import java.util.UUID;

public class PlayerStatus implements Comparable<PlayerStatus> {
	private String playerStatus;
	private GregorianCalendar time;
	private UUID uuid;
	
	public PlayerStatus(String status, GregorianCalendar time, UUID uuid){
		this.playerStatus = status;
		this.time = time;
		this.uuid = uuid;
	}
	
	public String getStatus(){
		return playerStatus;
	}
	
	public void setStatus(String status){
		this.playerStatus = status;
	}
	
	public GregorianCalendar getTime(){
		return time;
	}
	
	public void setTime(GregorianCalendar gc){
		time = gc;
	}
	
	
	public String toString(){
		GregorianCalendar currTime = new GregorianCalendar();
		Long difference = currTime.getTimeInMillis() - time.getTimeInMillis();
		String s = "";
		if(difference < 1000)
		s += " §7§o(" + difference + " milliseconds ago): §r\n" + playerStatus + "\n";
		else if(difference < 60000)
		s += " §7§o(" + difference/1000 + " seconds ago): §r\n" + playerStatus + "\n";
		else if(difference < 14400000)
		s += " §7§o(" + difference/60000 + " minutes ago): §r\n" + playerStatus + "\n";
		else 
		s += " §7§o(" + difference/3600000 + " hours ago): §r\n" + playerStatus + "\n";
		return s;
	}

	@Override
	public int compareTo(PlayerStatus o) {
		if(this.getTime().getTimeInMillis() < o.getTime().getTimeInMillis()){
			return -1;
		}
		else
			return 1;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
	
}

