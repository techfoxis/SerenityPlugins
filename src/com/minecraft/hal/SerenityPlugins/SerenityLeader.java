package com.minecraft.hal.SerenityPlugins;

public class SerenityLeader {
	private int online;
	private Long lifeInTicks;
	private int diamondsFound;
	private int monstersKilled;
	private int villagerTrades;
	private int animalsBred;
	private int deaths;

	public SerenityLeader() {
		online = 0;
		lifeInTicks = 0L;
		diamondsFound = 0;
		monstersKilled = 0;
		villagerTrades = 0;
		animalsBred = 0;
		deaths = 0;
	}

	public int getOnline() {
		return online;
	}

	public void setOnline(int online) {
		this.online = online;
	}

	public Long getLifeInTicks() {
		return lifeInTicks;
	}

	public void setLifeInTicks(Long lifeInTicks) {
		this.lifeInTicks = lifeInTicks;
	}

	public int getDiamondsFound() {
		return diamondsFound;
	}

	public void setDiamondsFound(int diamondsFound) {
		this.diamondsFound = diamondsFound;
	}

	public int getMonstersKilled() {
		return monstersKilled;
	}

	public void setMonstersKilled(int monstersKilled) {
		this.monstersKilled = monstersKilled;
	}

	public int getVillagerTrades() {
		return villagerTrades;
	}

	public void setVillagerTrades(int villagerTrades) {
		this.villagerTrades = villagerTrades;
	}

	public int getAnimalsBred() {
		return animalsBred;
	}

	public void setAnimalsBred(int animalsBred) {
		this.animalsBred = animalsBred;
	}

	public int getDeaths() {
		return deaths;
	}

	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}

}
