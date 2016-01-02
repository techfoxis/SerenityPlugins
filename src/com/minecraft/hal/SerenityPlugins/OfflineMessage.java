package com.minecraft.hal.SerenityPlugins;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class OfflineMessage implements Comparable {
	private int ID;
	private String message;
	private UUID from;
	private UUID to;
	private Long time; 
	private boolean read;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public UUID getFrom() {
		return from;
	}
	public void setFrom(UUID from) {
		this.from = from;
	}
	public UUID getTo() {
		return to;
	}
	public void setTo(UUID to) {
		this.to = to;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public boolean isRead() {
		return read;
	}
	public void setRead(boolean read) {
		this.read = read;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	
	public String constructString(String color, String name){
		int previewLength = 20;
		String firstfewChars = "";
		if(message.length() < previewLength){
			firstfewChars = message;
		}else{
			firstfewChars = message.substring(0, previewLength) + "...";
		}
		
		firstfewChars = firstfewChars.replace("\"", "");
		
		
		
		String ret = "";
		if(!isRead()){
			ret = FancyText.GenerateFancyText("§a§onew §r" + color + name + " §7(" + getTimeAgo() + ") §r" + firstfewChars, FancyText.RUN_COMMAND, "/msg ~ " + getID(), FancyText.SHOW_TEXT, "Click to read");
		}else{
			ret = "[";
			ret += FancyText.GenerateFancyText("§4x", FancyText.RUN_COMMAND, "/msg ^ " + getID(), FancyText.SHOW_TEXT, "Click to delete");
			ret += ",";
			ret += FancyText.GenerateFancyText("§r " + color + name + " §7(" + getTimeAgo() + ") §r" + firstfewChars, FancyText.RUN_COMMAND, "/msg ~ " + getID(), FancyText.SHOW_TEXT, "Click to read");
			ret+= "]";
		}
		return ret;
	}
	
	private String getTimeAgo(){
		Long timeDiff = System.currentTimeMillis() - this.getTime();
		Long mins = TimeUnit.MILLISECONDS.toMinutes(timeDiff);
		Long hours = TimeUnit.MILLISECONDS.toHours(timeDiff);
		Long days = TimeUnit.MILLISECONDS.toDays(timeDiff);
		
		if(mins < 100){
			return mins + " minutes ago";
		}
		if(hours < 49){
			return hours + " hours ago";
		}
		return days + " days ago";
	}
	@Override
	public int compareTo(Object arg0) {
		if(arg0 instanceof OfflineMessage)
		return (this.getTime() < ((OfflineMessage) arg0).getTime() ? 0:1);
		return 0;

	}
	
	
	

}
