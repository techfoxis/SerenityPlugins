package com.minecraft.hal.SerenityPlugins;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;

public class ProtectedArea {

	public String owner;
	public Location location1;
	public Location location2;
	public ArrayList<String> trustedPlayers;

	public ProtectedArea(Location location1, Location location2, String owner) {
		this.location1 = location1;
		this.location2 = location2;
		this.owner = owner;
		trustedPlayers = new ArrayList<String>();
	}

	public ProtectedArea() {
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

	public boolean hasPermission(String name) {
		if (name.equalsIgnoreCase(owner)) {
			return true;
		}
		for (String names : trustedPlayers) {
			if (names.equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}

	public String trustList() {
		String s = " - " + this.owner;
		for (int i = 0; i < trustedPlayers.size(); i++) {
			s += "\n - " + trustedPlayers.get(i);
		}
		return s;
	}

	public String getId() {
		int minX = Math.min((int) location1.getX(), (int) location2.getX());
		int maxX = Math.max((int) location1.getX(), (int) location2.getX());

		int minZ = Math.min((int) location1.getZ(), (int) location2.getZ());
		int maxZ = Math.max((int) location1.getZ(), (int) location2.getZ());

		return location1.getWorld().getName() + minX + "_" + minZ + "_" + maxX
				+ "_" + maxZ;
	}

	public boolean equals(Location location) {
		if (checkXBetween(location.getX()) && checkZBetween(location.getZ())) {
			if (location.getWorld().equals(location1.getWorld())) {
				return true;
			}
		}
		/*
		 * if ((this.location1.getX() - length) <= location.getX() &&
		 * location.getX() <= (this.location1.getX() + length)) { if
		 * ((this.location.getZ() - length) <= location.getZ() &&
		 * location.getZ() <= (this.location.getZ() + length)) { if
		 * (this.location.getWorld().equals(location.getWorld())) { return true;
		 * } } } return false;
		 */
		return false;
	}

	private boolean checkZBetween(Double z) {
		if (location1.getZ() < location2.getZ()) {
			if (location1.getZ() <= z && z <= location2.getZ()) {
				return true;
			}
		} else {
			if (location2.getZ() <= z && z <= location1.getZ()) {
				return true;
			}
		}
		return false;
	}

	private boolean checkXBetween(Double x) {
		if (location1.getX() < location2.getX()) {
			if (location1.getX() <= x && x <= location2.getX()) {
				return true;
			}
		} else {
			if (location2.getX() <= x && x <= location1.getX()) {
				return true;
			}
		}
		return false;
	}

	public void highlightArea() {
		int x = Math.min((int) location1.getX(), (int) location2.getX());
		int z = Math.min((int) location1.getZ(), (int) location2.getZ());
		int xMax = Math.max((int) location1.getX(), (int) location2.getX());
		int zMax = Math.max((int) location1.getZ(), (int) location2.getZ());

		World w = location1.getWorld();
		for (int i = x; i < x + xDiff(); i++) {
			Location l = new Location(w, i + .5, 1, z + .5);
			l.setY(w.getHighestBlockYAt(l));
			ParticleEffect.FIREWORKS_SPARK.display((float) 1, (float) 1,
					(float) .005, .0001F, 5, l, 50);
			l = new Location(w, i + .5, 1, zMax + .5);
			l.setY(w.getHighestBlockYAt(l));

			ParticleEffect.FIREWORKS_SPARK.display((float) 1, (float) 1,
					(float) .005, .0001F, 5, l, 50);
		}

		for (int i = z; i < z + zDiff(); i++) {
			Location l = new Location(w, x + .5, 1, i + .5);
			l.setY(w.getHighestBlockYAt(l));
			ParticleEffect.FIREWORKS_SPARK.display((float) .005, (float) 1,
					(float) 1, .0001F, 5, l, 50);

			l = new Location(w, xMax + .5, 1, i + .5);
			l.setY(w.getHighestBlockYAt(l));
			ParticleEffect.FIREWORKS_SPARK.display((float) .005, (float) 1,
					(float) 1, .0001F, 5, l, 50);
		}
	}
	
	public void highlightAreaKayla() {
		int x = Math.min((int) location1.getX(), (int) location2.getX());
		int z = Math.min((int) location1.getZ(), (int) location2.getZ());
		int xMax = Math.max((int) location1.getX(), (int) location2.getX());
		int zMax = Math.max((int) location1.getZ(), (int) location2.getZ());

		World w = location1.getWorld();
		for (int i = x; i < x + xDiff(); i++) {
			Location l = new Location(w, i + .5, 1, z + .5);
			l.setY(w.getHighestBlockYAt(l));
			ParticleEffect.SPELL_MOB.display((float) 1, (float) 1,
					(float) .005, 1, 5, l, 50);
			l = new Location(w, i + .5, 1, zMax + .5);
			l.setY(w.getHighestBlockYAt(l));

			ParticleEffect.SPELL_MOB.display((float) 1, (float) 1,
					(float) .005, 1, 5, l, 50);
		}

		for (int i = z; i < z + zDiff(); i++) {
			Location l = new Location(w, x + .5, 1, i + .5);
			l.setY(w.getHighestBlockYAt(l));
			ParticleEffect.SPELL_MOB.display((float) .005, (float) 1,
					(float) 1, 1, 5, l, 50);

			l = new Location(w, xMax + .5, 1, i + .5);
			l.setY(w.getHighestBlockYAt(l));
			ParticleEffect.SPELL_MOB.display((float) .005, (float) 1,
					(float) 1, 1, 5, l, 50);
		}
	}

	public boolean intersects(ProtectedArea pa) {
		World w = location1.getWorld();
		if (pa.location1.getWorld().equals(location1.getWorld())) {
			int x = Math.min((int) location1.getX(), (int) location2.getX());
			int z = Math.min((int) location1.getZ(), (int) location2.getZ());
			int xDiff = xDiff();
			int zDiff = zDiff();

			for (int i = x; i < x + xDiff; i++) {
				for (int j = z; j < z + zDiff; j++) {
					Location l = new Location(w, i, 1, j);
					if (pa.equals(l)) {
						return true;
					}
				}
			}
		}

		return false;

	}

	public int area() {
		return xDiff() * zDiff();
	}

	public int xDiff() {
		return Math.abs((int) (location1.getX() - location2.getX())) + 1;
	}

	public int zDiff() {
		return Math.abs((int) (location1.getZ() - location2.getZ())) + 1;
	}
}
