package com.minecraft.hal.SerenityPlugins;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import com.avaje.ebeaninternal.server.subclass.GetterSetterMethods;

public class SerenityPlayer {
	private UUID uuid;
	private String name;
	private String IP;
	private int minutes;
	private Long firstPlayed;
	private Long lastPlayed;
	private String chatColor;
	private int localeId;
	private List<UUID> friends;
	private boolean isOp;
	private boolean isDirty;
	private boolean isWhitelisted;
	private boolean isAFK;
	private boolean isMuted;
	private boolean isCelebrating;
	private boolean isLocalChatting;
	private boolean isOnline;
	private boolean banned;
	private int afkTime;
	private int playerVectorHash;
	private List<OfflineMessage> offlineMessages;
	private Long lastText;
	private String lastToSendMessage;
	private Long lastRandomTP;
	private SerenityLeader serenityLeader;

	@Override
	public int hashCode() {
		return uuid.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof SerenityPlayer) {
			SerenityPlayer s = (SerenityPlayer) o;
			return this.uuid.equals(s.uuid);
		}
		return false;
	}
	
	public SerenityPlayer(){
		offlineMessages = new ArrayList<OfflineMessage>();
		serenityLeader = new SerenityLeader();
	}

	public String getUpdateString() {
		String sql = "UPDATE Player Set " + "Name='" + getName() + "',IP='"
				+ getIP() + "',Time=" + getMinutes() + ",FirstPlayed="
				+ getFirstPlayed() + ",LastPlayed=" + getLastPlayed()
				+ ",Color='" + getChatColor() + "',Locale='" + getLocaleId()
				+ "',Op=" + (isOp() ? 1 : 0) + ",Whitelisted="
				+ (isWhitelisted() ? 1 : 0) + ",Muted=" + (isMuted() ? 1 : 0)
				+ ",LocalChat=" + (isLocalChatting() ? 1 : 0) + ",Online="
				+ (isOnline() ? 1 : 0) + ",Banned=" + (isBanned() ? 1 : 0)
				+ " WHERE UUID='" + getUUID().toString() + "';";
		return sql;
	}

	public String getLeaderboardUpdate(){
		return "INSERT INTO Leaderboard Values (" 
				+ System.currentTimeMillis()
				+ ",'" + getUUID().toString()
				+ "'," + getSerenityLeader().getOnline()
				+ "," + getSerenityLeader().getLifeInTicks()
				+ "," + getSerenityLeader().getDiamondsFound()
				+ "," + getSerenityLeader().getMonstersKilled()
				+ "," + getSerenityLeader().getVillagerTrades()
				+ "," + getSerenityLeader().getAnimalsBred() + ");";
	}

	public UUID getUUID() {
		return uuid;
	}

	public void setUUID(UUID uuid) {
		this.uuid = uuid;
		this.setDirty(true);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		this.setDirty(true);
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
		this.setDirty(true);
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
		this.setDirty(true);
	}

	public Long getFirstPlayed() {
		return firstPlayed;
	}

	public void setFirstPlayed(Long firstPlayed) {
		this.firstPlayed = firstPlayed;
		this.setDirty(true);
	}

	public Long getLastPlayed() {
		return lastPlayed;
	}

	public void setLastPlayed(Long lastPlayed) {
		this.lastPlayed = lastPlayed;
		this.setDirty(true);
	}

	public String getChatColor() {
		return chatColor;
	}

	public void setChatColor(String chatColor) {
		this.chatColor = chatColor;
		this.setDirty(true);
	}

	public int getLocaleId() {
		return localeId;
	}

	public void setLocaleId(int localeId) {
		this.localeId = localeId;
		this.setDirty(true);
	}

	public List<UUID> getFriends() {
		return friends;
	}

	public void setFriends(List<UUID> friends) {
		this.friends = friends;
		this.setDirty(true);
	}

	public boolean isOp() {
		return isOp;
	}

	public void setOp(boolean isOp) {
		this.isOp = isOp;
		this.setDirty(true);
	}

	public boolean isDirty() {
		return isDirty;
	}

	public void setDirty(boolean isDirty) {
		this.isDirty = isDirty;
	}

	public boolean isWhitelisted() {
		return isWhitelisted;
	}

	public void setWhitelisted(boolean isWhitelisted) {
		this.isWhitelisted = isWhitelisted;
		this.setDirty(true);
	}

	public boolean isAFK() {
		return isAFK;
	}

	public void setAFK(boolean isAFK) {
		this.isAFK = isAFK;
	}

	public boolean isMuted() {
		return isMuted;
	}

	public void setMuted(boolean isMuted) {
		this.isMuted = isMuted;
		this.setDirty(true);
	}

	public boolean isCelebrating() {
		return isCelebrating;
	}

	public void setCelebrating(boolean isCelebrating) {
		this.isCelebrating = isCelebrating;
	}

	public boolean isLocalChatting() {
		return isLocalChatting;
	}

	public void setLocalChatting(boolean isLocalChatting) {
		this.isLocalChatting = isLocalChatting;
		this.setDirty(true);
	}

	public boolean isOnline() {
		return isOnline;
	}

	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
		this.setDirty(true);
	}

	public boolean isBanned() {
		return banned;
	}

	public void setBanned(boolean banned) {
		this.banned = banned;
		this.setDirty(true);
	}

	public int getAfkTime() {
		return afkTime;
	}

	public void setAfkTime(int afkTime) {
		this.afkTime = afkTime;
	}

	public int getPlayerVectorHash() {
		return playerVectorHash;
	}

	public void setPlayerVectorHash(int hash) {
		this.playerVectorHash = hash;
	}

	public List<OfflineMessage> getOfflineMessages() {
		return offlineMessages;
	}

	public void setOfflineMessages(List<OfflineMessage> offlineMessages) {
		this.offlineMessages = offlineMessages;
	}

	public Long getLastText() {
		return lastText;
	}

	public void setLastText(Long lastText) {
		this.lastText = lastText;
	}

	public String getLastToSendMessage() {
		return lastToSendMessage;
	}

	public void setLastToSendMessage(String lastToSendMessage) {
		this.lastToSendMessage = lastToSendMessage;
	}

	public Long getLastRandomTP() {
		return lastRandomTP;
	}

	public void setLastRandomTP(Long lastRandomTP) {
		this.lastRandomTP = lastRandomTP;
	}
	
	public SerenityLeader getSerenityLeader(){
		return this.serenityLeader;
	}
	
	public void clearSerenityLeader(){
		this.serenityLeader = new SerenityLeader();
	}
	
}
