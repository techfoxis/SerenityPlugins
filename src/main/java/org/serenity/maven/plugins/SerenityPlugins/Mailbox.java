package org.serenity.maven.plugins.SerenityPlugins;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.block.Chest;

public class Mailbox {
	public UUID uuid;
	public Location location;
	public boolean isPublic;
	public String name;

	public Mailbox(UUID name, String actualName, Location l) {
		this.uuid = name;
		this.name = actualName;
		this.location = l;
	}

	public Mailbox() {
		this.uuid = null;
		this.name = null;
		this.location = null;
		this.isPublic = false;
	}

	public String toString() {
		String s = this.uuid + "'s mailbox is in: "
				+ this.location.getWorld().getName() + "\nAt:" + "\nX: "
				+ this.location.getX() + "\nY " + this.location.getY() + "\nZ "
				+ this.location.getZ() + this.name + this.isPublic;
		
		return s;
	}

	public boolean equals(Mailbox mb) {
		if (this.uuid.equals(mb.uuid)) {
			if (this.location.equals(mb.location)) {
				if (this.uuid != null) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean hasMail() {
		return ((Chest) this.location.getBlock().getState()).getInventory()
				.getContents()[0] != null;
	}
}
