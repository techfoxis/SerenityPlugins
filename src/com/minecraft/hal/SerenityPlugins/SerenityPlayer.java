package com.minecraft.hal.SerenityPlugins;

import java.util.List;
import java.util.UUID;

public class SerenityPlayer {
	public UUID uuid;
	public String name;
	public String IP;
	public int minutes;
	public Long firstPlayed;
	public Long lastPlayed;
	public String chatColor;
	public int localeId;
	public List<UUID> friends;
	public boolean isOp;
	public boolean isDirty;

	public void setMinutes(int minutes) {
		this.minutes = minutes;
		isDirty = true;
	}

	public void setFirstPlayed(Long time) {
		this.firstPlayed = time;
		isDirty = true;
	}

	public void setLastPlayed(Long time) {
		this.lastPlayed = time;
		isDirty = true;
	}

	public void setName(String name) {
		this.name = name;
		isDirty = true;
	}

	public void setUUID(UUID uid) {
		this.uuid = uid;
		isDirty = true;
	}

	public void setIP(String IP) {
		this.IP = IP;
		isDirty = true;
	}

	public void setColor(String color) {
		this.chatColor = color;
		isDirty = true;
	}

	public void setOp(boolean op) {
		this.isOp = op;
		isDirty = true;
	}
	
	public void setLocale(int locale){
		this.localeId = locale;
		isDirty = true;
	}

	public void setDirty(boolean dirty) {
		this.isDirty = dirty;
	}
}
