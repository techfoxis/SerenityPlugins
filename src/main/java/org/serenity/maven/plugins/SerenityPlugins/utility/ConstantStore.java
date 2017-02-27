package org.serenity.maven.plugins.SerenityPlugins.utility;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.serenity.maven.plugins.SerenityPlugins.command.SerenityCommand;

public class ConstantStore {

	// TODO Put skulls into cache
	public static final String FIREWORK_SHOW_HEAD = "/give @p skull 1 3 {display:{Name:\"Fireworks Show\"},SkullOwner:{Id:\"4871fc40-b2c7-431d-9eb8-b54cd666dca7\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzg0MGI4N2Q1MjI3MWQyYTc1NWRlZGM4Mjg3N2UwZWQzZGY2N2RjYzQyZWE0NzllYzE0NjE3NmIwMjc3OWE1In19fQ==\"}]}}}";
	public static final String SURVIVE_AND_THRIVE_HEAD = "/give @p skull 1 3 {display:{Name:\"Survive And Thrive II\"},SkullOwner:{Id:\"a7319ab0-26a7-4992-8aa8-27abb86937f1\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTA5ZDM4Y2ExNGRkMjRkNWU1ODYyMjkyOWM0MmM3YTAyMzU0ODYxNmQ2NTA2YWY5ZGNiMzFlNGE1M2IyOTMzIn19fQ==\"}]}}}";
	public static final Long FW_COOLDOWN_TIME = 480000L;
	public static final int MAX_DISTANCE = 700;
	
	public static final List<DyeColor> ALL_DYES = new ArrayList<DyeColor>() {
		{
			add(DyeColor.RED);
			add(DyeColor.ORANGE);
			add(DyeColor.YELLOW);
			add(DyeColor.LIME);
			add(DyeColor.LIGHT_BLUE);
			add(DyeColor.CYAN);
			add(DyeColor.BLUE);
			add(DyeColor.PURPLE);
			add(DyeColor.MAGENTA);
			add(DyeColor.PINK);
			add(DyeColor.GREEN);
			add(DyeColor.BLACK);
			add(DyeColor.BROWN);
			add(DyeColor.GRAY);
			add(DyeColor.SILVER);
			add(DyeColor.WHITE);
		}
	};
	
	public static final List<Color> ALL_COLORS = new ArrayList<Color>() {
		{
			add(Color.AQUA);
			add(Color.BLACK);
			add(Color.BLUE);
			add(Color.FUCHSIA);
			add(Color.GRAY);
			add(Color.GREEN);
			add(Color.LIME);
			add(Color.MAROON);
			add(Color.NAVY);
			add(Color.OLIVE);
			add(Color.ORANGE);
			add(Color.PURPLE);
			add(Color.RED);
			add(Color.SILVER);
			add(Color.TEAL);
			add(Color.WHITE);
			add(Color.YELLOW);
		}
	};
	
	public static final List<Integer> ALL_RECORD_IDS = new ArrayList<Integer>() {
		{
			add(Material.RECORD_10.getId());
			add(Material.RECORD_11.getId());
			add(Material.RECORD_12.getId());
			add(Material.RECORD_3.getId());
			add(Material.RECORD_4.getId());
			add(Material.RECORD_5.getId());
			add(Material.RECORD_6.getId());
			add(Material.RECORD_8.getId());
			add(Material.RECORD_9.getId());
		}
	};
	
	public static final List<ChatColor> ALL_CHAT_COLORS = new ArrayList<ChatColor>() {
		{
			add(ChatColor.DARK_RED);
			add(ChatColor.RED);
			add(ChatColor.GOLD);
			add(ChatColor.YELLOW);
			add(ChatColor.GREEN);
			add(ChatColor.DARK_GREEN);
			add(ChatColor.AQUA);
			add(ChatColor.DARK_AQUA);
			add(ChatColor.DARK_BLUE);
			add(ChatColor.BLUE);
			add(ChatColor.DARK_PURPLE);
			add(ChatColor.LIGHT_PURPLE);
			add(ChatColor.BLACK);
			add(ChatColor.DARK_GRAY);
			add(ChatColor.GRAY);
			add(ChatColor.WHITE);
		}
	};
	
	// TODO Abstract the constructors into their relative classes
	public static final List<SerenityCommand> ALL_COMMANDS = new ArrayList<SerenityCommand>() {
		{
			add(new SerenityCommand("as", "Armor stand manipulation",
					"Brings up an interface to manipulate armor stands", "/as", 1440, true));
			add(new SerenityCommand("afk", "Sets you AFK",
					"See other AFK players in the TAB-list.\n" + "While you are AFK, your vote for day is automatic",
					"/afk", 0, false));
			add(new SerenityCommand("coords", "Broadcasts your coordinates",
					"This will broadcast your coordinates to the server.\n" + "It's faster than manually typing them.",
					"/coords", 0, false));
			add(new SerenityCommand("deposit", "Store your heads in the database",
					"Put the heads you're holding into the Serenity database.  \nYou will need to visit \nhttps://serenity-mc.org/heads to withdraw later",
					"/deposit", 0, false));
			add(new SerenityCommand("gettime", "Tells you the time",
					"It will also tell you the server time! (Eastern US time in real life)", "/gettime", 0, false));
			add(new SerenityCommand("ignore", "Ignore another player's chats",
					"Type this to ignore a player.\n" + "To undo, type the command again.  It's a toggle.",
					"/ignore <PlayerName>", 0, false));
			add(new SerenityCommand("lag", "See if the server is lagging",
					"This will return the server's Ticks Per Second (TPS).\n"
							+ "A minecraft server should be running at or close to 20 TPS.\nIt will also calculate your ping.\n"
							+ "Ping is the time it takes the network to go between your computer and the server.\n"
							+ "A ping above 200ms might seem like lag!",
					"/lag", 0, false));
			add(new SerenityCommand("links", "Server web-links", "Some helpful links to some of our webpages", "/links",
					0, false));
			add(new SerenityCommand("mailto", "Mail your items to someone's mailbox",
					"Make sure you have a mailbox setup first.\n" + "A mailbox is a chest on top of a fencepost.",
					"/mailto <MailboxName>", 0, false));
			add(new SerenityCommand("map", "A link to the online map at your location",
					"This map updates every 30 minutes.\n"
							+ "To add a marker, type [Point] or [Home] on the first line of a sign\n"
							+ "followed by any other text you like!",
					"/map", 0, false));
			add(new SerenityCommand("move", "Move to another server",
					"/move creative to get to creative.\n" + "/move event to go to the event server.\n"
							+ "These servers are totally seperate from Survival, however, \n"
							+ "you will be able to chat between Creative and Survival",
					"/move <ServerName>", 360, false));
			add(new SerenityCommand("msg", "Privately message another player",
					"If he or she is offline, an offline message will be sent.\n"
							+ "To read offline messages, type /msg read and click each message",
					"/msg <PlayerName>", 0, false));
			add(new SerenityCommand("mytime", "See how much time you've spent here",
					"12 hours for colored chat, 24 to edit spawn,\n" + "and 48 to manipulate armor stands", "/mytime",
					0, false));
			add(new SerenityCommand("password", "Set your password for https://serenity-mc.org/",
					"Generates a unique link for you to set or reset your\nhttp://serenity-mc.org/ password.\nUse this website to keep up to date with the community \nand manage your messages",
					"/password", 0, false));
			add(new SerenityCommand("portal", "Calculates a nether portal location",
					"Divides or multiplies by 8 depending on your world.\n"
							+ "Helpful for syncing up nether/overworld portals",
					"/portal", 0, false));
			add(new SerenityCommand("protect", "Claim commands",
					"/protect claim to claim\n/protect unclaim to unclaim\n/protect trust <Player> to trust a player\n/protect untrust <Player> to untrust\n/protect trustlist to see all trusted players\n/protect list to see all claims",
					"/protect", 60, false));
			add(new SerenityCommand("setchatcolor", "Set your chat color",
					"Type just /setchatcolor to see a list of colors", "/setchatcolor <color>", 720, false));
			add(new SerenityCommand("setcompass", "Set your compass to point somewhere",
					"Type this command to see all options", "/setcompass", 720, false));
			add(new SerenityCommand("status", "Read or update your status",
					"Your status will also appear on the Serenity Players web page!", "/status <optional_status>", 0,
					false));
			add(new SerenityCommand("text", "Send a text message to the owner's cell phone",
					"The owner's phone will vibrate with your message.\nUse this if no staff is available."
							+ "\n/msg [Server] to send a non-urgent message",
					"/text <message>", 720, false));
			
			// TODO Add a better method for enabling voting
			/*
			 * add(new SerenityCommand("vote", "Vote for something",
			 * "Sometimes we have votes for stuff..", "/vote", 720, true));
			 */
		}
	};
	
	// TODO Put skulls into Cache
	public static final List<String> ALL_HOLIDAY_SKULLS = new ArrayList<String>() {

		{
			add("skull 1 3 {SkullOwner:{Name:%s,Id:\"6616a590-e326-442d-8806-b313fca1b5d5\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWM2Mjc0YzIyZDcyNmZjMTIwY2UyNTczNjAzMGNjOGFmMjM4YjQ0YmNiZjU2NjU1MjA3OTUzYzQxNDQyMmYifX19\"}]}}}");
			add("skull 1 3 {SkullOwner:{Name:%s,Id:\"91df0725-c04d-4776-b9e7-01dad0ea6d5f\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmM4NjUyYmZkYjdhZGRlMTI4ZTdlYWNjNTBkMTZlYjlmNDg3YTMyMDliMzA0ZGUzYjk2OTdjZWJmMTMzMjNiIn19fQ==\"}]}}}");
			add("skull 1 3 {SkullOwner:{Name:%s,Id:\"f5e1766b-91d7-42d5-9d0f-78d31f82b366\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDdlNTVmY2M4MDlhMmFjMTg2MWRhMmE2N2Y3ZjMxYmQ3MjM3ODg3ZDE2MmVjYTFlZGE1MjZhNzUxMmE2NDkxMCJ9fX0=\"}]}}}");
			add("skull 1 3 {SkullOwner:{Name:%s,Id:\"fd711859-613c-4a98-899f-2068082f4bd1\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2ZjZDFjODJlMmZiM2ZhMzY4Y2ZhOWE1MDZhYjZjOTg2NDc1OTVkMjE1ZDY0NzFhZDQ3Y2NlMjk2ODVhZiJ9fX0=\"}]}}}");
			add("skull 1 3 {SkullOwner:{Name:%s,Id:\"ee53f881-8b67-4604-bfb9-a43a6966eae2\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWM3MTJiMTk3MWM1ZjQyZWVmZjgwNTUxMTc5MjIwYzA4YjgyMTNlYWNiZTZiYzE5ZDIzOGMxM2Y4NmUyYzAifX19\"}]}}}");
			add("skull 1 3 {SkullOwner:{Name:%s,Id:\"e174cc81-8646-4c48-afeb-d5fac7d24e16\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjU3YjJlZTY1NmQ3Yjg2NWMzZmFkZDViMTQyOGMzNThkNDc2M2Y0MTc4YWM1OTlkNjA0ODY5YTE5ZDcifX19\"}]}}}");
			add("skull 1 3 {SkullOwner:{Name:%s,Id:\"b420e45b-65eb-4d93-8bd1-1c0253d4388e\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDNlMTY1ODU3YzRjMmI3ZDZkOGQ3MGMxMDhjYzZkNDY0ZjdmMzBlMDRkOTE0NzMxYjU5ZTMxZDQyNWEyMSJ9fX0=\"}]}}}");
			add("skull 1 3 {SkullOwner:{Name:%s,Id:\"ce936179-5016-4926-8889-2c1705ef70d7\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmYyZDE4OTVmZmY0YjFiYjkxMTZjOGE5ZTIyOTU5N2Y2OWYzZWVlODgxMjI3NzZlNWY5NzMzNTdlNmIifX19\"}]}}}");
			add("skull 1 3 {SkullOwner:{Name:%s,Id:\"41ac016b-472a-4b12-88c8-dd964e3f42aa\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTg3NDU4OTg0YThhYWU1NzAxM2QxYjllOGRmYTRjM2I0ZDI1ZTQyNjE4MjMzOTI0Zjc0NDc0MTM0ZjYyYSJ9fX0=\"}]}}}");
			add("skull 1 3 {SkullOwner:{Name:%s,Id:\"97f38226-bc1b-40d8-92b9-cebbfd37d63c\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGFjYjNjMWUxYjM0Zjg3MzRhZWRmYWJkMWUxZjVlMGIyODBiZWY5MjRmYjhiYmYzZTY5MmQyNTM4MjY2ZjQifX19\"}]}}}");
			add("skull 1 3 {SkullOwner:{Name:%s,Id:\"13e135fb-348f-4c6a-99e3-e6e3aa385e73\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTI4ZTY5MmQ4NmUyMjQ0OTc5MTVhMzk1ODNkYmUzOGVkZmZkMzljYmJhNDU3Y2M5NWE3YWMzZWEyNWQ0NDUifX19\"}]}}}");
			add("skull 1 3 {SkullOwner:{Name:%s,Id:\"54a219b4-9a5f-45ed-80ff-8766d501588b\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODRlMWM0MmYxMTM4M2I5ZGM4ZTY3ZjI4NDZmYTMxMWIxNjMyMGYyYzJlYzdlMTc1NTM4ZGJmZjFkZDk0YmI3In19fQ==\"}]}}}");
			add("skull 1 3 {SkullOwner:{Name:%s,Id:\"3c62af53-c3f2-4df8-8cdd-beaf88b98030\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzg1MzZhNDYxNjg0ZmM3YTYzYjU0M2M1ZGYyMzQ4Y2Q5NjhiZjU1ODM1OTFiMWJiY2M1ZjBkYjgzMTY2ZGM3In19fQ==\"}]}}}");
			add("skull 1 3 {SkullOwner:{Name:%s,Id:\"dd7514ca-c10f-4389-9c14-78d2feae91b6\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjczYTIxMTQxMzZiOGVlNDkyNmNhYTUxNzg1NDE0MDM2YTJiNzZlNGYxNjY4Y2I4OWQ5OTcxNmM0MjEifX19\"}]}}}");
			add("skull 1 3 {SkullOwner:{Name:%s,Id:\"50f36e51-ea4d-434d-adc9-ca7666546861\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2E3NTMyOGViYTdjYjVhMDUyMzI2ZjU5ZGRhZjY3YTFjZWJkNGU1NWJiNjgwMWMwM2MzMTQyZTI4ZTEifX19\"}]}}}");
			add("skull 1 3 {SkullOwner:{Name:%s,Id:\"7def2717-e74c-4e70-870c-9155c1caffca\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWQ5N2Y0ZjQ0ZTc5NmY3OWNhNDMwOTdmYWE3YjRmZTkxYzQ0NWM3NmU1YzI2YTVhZDc5NGY1ZTQ3OTgzNyJ9fX0=\"}]}}}");
			add("skull 1 3 {SkullOwner:{Name:%s,Id:\"e45cf551-aed1-4c70-8681-4821ff3267b7\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTlmMDc0MzU3NmJiYTRhMjYyMjQ4MDU0ODk3MGI3MjE1NDNkMmM0NTc5NTVlOGRkNWM0ZjlkZGI2YTU2Yjk1YyJ9fX0=\"}]}}}");
			add("skull 1 3 {SkullOwner:{Name:%s,Id:\"d08a119e-2aeb-48be-b14c-b3aa6b73af60\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWMzODIxZDRmNjFiMTdmODJmMGQ3YThlNTMxMjYwOGZmNTBlZGUyOWIxYjRkYzg5ODQ3YmU5NDI3ZDM2In19fQ==\"}]}}}");
			add("skull 1 3 {SkullOwner:{Name:%s,Id:\"c04fdca4-1221-49b2-b29d-f6622478dbbc\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTBjNzVhMDViMzQ0ZWEwNDM4NjM5NzRjMTgwYmE4MTdhZWE2ODY3OGNiZWE1ZTRiYTM5NWY3NGQ0ODAzZDFkIn19fQ==\"}]}}}");
			add("skull 1 3 {SkullOwner:{Name:%s,Id:\"590fae25-9d3c-4650-97f4-8657336e4a45\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzU0MTlmY2U1MDZhNDk1MzQzYTFkMzY4YTcxZDIyNDEzZjA4YzZkNjdjYjk1MWQ2NTZjZDAzZjgwYjRkM2QzIn19fQ==\"}]}}}");
			add("skull 1 3 {SkullOwner:{Name:%s,Id:\"ab43bbca-b466-4e37-a633-c6877b78e000\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWUzYThmZDA4NTI5Nzc0NDRkOWZkNzc5N2NhYzA3YjhkMzk0OGFkZGM0M2YwYmI1Y2UyNWFlNzJkOTVkYyJ9fX0=\"}]}}}");
			add("skull 1 3 {SkullOwner:{Name:%s,Id:\"18998be6-3b5f-46f1-8cdd-89458b6ec578\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTNlNThlYTdmMzExM2NhZWNkMmIzYTZmMjdhZjUzYjljYzljZmVkN2IwNDNiYTMzNGI1MTY4ZjEzOTFkOSJ9fX0=\"}]}}}");
			add("skull 1 3 {SkullOwner:{Name:%s,Id:\"736a2a57-7ca6-487d-a059-274865673e92\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWU2ZTQ1NjI5OWQyYWE1YjQ1ZjE0ODg1ZGEyMzY4YThhOTMzZTY5ZTU1YjUzZmU0YmJjODFmMWI0NjBmZDljIn19fQ==\"}]}}}");
			add("skull 1 3 {SkullOwner:{Name:%s,Id:\"e4fc9813-b4f8-4787-a4c7-893544ff2af9\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTdjMDVhNmE3OWRmYjAyNWU4ZjAyYjljZDQ3OTRiN2JiZTIyMWNjMWIyZjJlYWQyMmQyNDY2NzEzNjE3YmMifX19\"}]}}}");
			add("skull 1 3 {SkullOwner:{Name:%s,Id:\"f02c52f5-ae16-40a9-8040-7e45546d94d7\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmVhMTc3ZjFiNzRjYTU3YTFjY2U5MzhlOGQ5OTRiYzFmNjM3ZTVmNjljODJlZmYyOTYxMmExM2JhOGIyZGQ3In19fQ==\"}]}}}");
			add("skull 1 3 {SkullOwner:{Name:%s,Id:\"7380bd04-507e-47de-8f9e-acbbd0281e33\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTI2ZTM0NjI4N2EyMWRiZmNhNWI1OGMxNDJkOGQ1NzEyYmRjODRmNWI3NWQ0MzE0ZWQyYTgzYjIyMmVmZmEifX19\"}]}}}");
			add("skull 1 3 {SkullOwner:{Name:%s,Id:\"d49c2625-cb64-4728-9acd-860bc0ef4272\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjI0MDcwYzliNjY1OWVkMjViMmNhMTI2OTE1ZjRkODgyMGZhZmNlNDMyNGVkOWE4ZjRiOGE1MDYzNDUzMDdmIn19fQ==\"}]}}}");
			add("skull 1 3 {SkullOwner:{Name:%s,Id:\"91505014-a335-4fe5-94c5-162d685ab3d2\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDhjNTNiY2U4YWU1OGRjNjkyNDkzNDgxOTA5YjcwZTExYWI3ZTk0MjJkOWQ4NzYzNTEyM2QwNzZjNzEzM2UifX19\"}]}}}");
			add("skull 1 3 {SkullOwner:{Name:%s,Id:\"86b1b5d8-b783-4a6f-a433-177c69e8d8b7\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODg2NjJiYTA3MDhlOGQ2MGQ1NjM2NWVjMmJjMDBmZjE3OTJmMTY2MzRmYzg0NWE4NDNhODRkZTA4MWVhNGYifX19\"}]}}}");
			add("skull 1 3 {SkullOwner:{Name:%s,Id:\"7f292b5e-8a1f-4c63-a371-9181631fcd85\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjExYWIzYTExMzJjOWQxZWY4MzVlYTgxZDk3MmVkOWI1Y2Q4ZGRmZjBhMDdjNTVhNzQ5YmNmY2Y4ZGY1In19fQ==\"}]}}}");
			add("skull 1 3 {SkullOwner:{Name:%s,Id:\"14cb891f-35b9-43ed-aa29-86a5aa77cba0\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjdlMTFkYjhmMzkyNzI3YTFjNDU5ZWI1OTFkZDhhMGFhMzg2MWMyNzQ4N2UxMzg0YmIyMmY5MTU3M2JlNyJ9fX0=\"}]}}}");
			add("skull 1 3 {SkullOwner:{Name:%s,Id:\"eb2e049e-5563-4df7-be8a-1c89123a79a1\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWU0ODYxNWRmNmI3ZGRmM2FkNDk1MDQxODc2ZDkxNjliZGM5ODNhM2ZhNjlhMmFjYTEwN2U4ZjI1MWY3Njg3In19fQ==\"}]}}}");
			add("skull 1 3 {SkullOwner:{Name:%s,Id:\"674af43c-ecd1-416c-b4ef-9f182eaeafc6\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODg0ZTkyNDg3YzY3NDk5OTViNzk3MzdiOGE5ZWI0YzQzOTU0Nzk3YTZkZDZjZDliNGVmY2UxN2NmNDc1ODQ2In19fQ==\"}]}}}");
			add("skull 1 3 {SkullOwner:{Name:%s,Id:\"11aca51c-d1f6-4493-b8c0-bfbc74365dda\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmVhZWQyMzI1ZDc5NTIyMTIwZjQyMmExZGU3Mjc1NmFmNDRjYWY3NWM3NWRjMjYzM2U1NjczMjM4NDdlZCJ9fX0=\"}]}}}");
			add("skull 1 3 {SkullOwner:{Name:%s,Id:\"da2407bc-43b4-4bbd-bd19-020258f61009\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjY4MTI5NjIzZmZkZTIyY2JjNTM0OTE1YWUzOGE1ZDYwZjMyZGFlZGJjNGZkNWQyYzMxMGZlYTUzN2VkIn19fQ==\"}]}}}");
			add("skull 1 3 {SkullOwner:{Name:%s,Id:\"4cf3986b-2ec9-41cf-a976-01335a6266c3\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWJkNDZiMzhiMjFiMzQyY2FmOTE3YWQ5Y2E0MmFmYjY4Mzg4YTU1OTFiY2M5YWRlZDFlOGUzNDZlMTg4OTAifX19\"}]}}}");
			add("skull 1 3 {SkullOwner:{Name:%s,Id:\"5ddb61e0-4e65-4097-8026-489261d7d278\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDQ5MjdjZTViYTIyYWQxZTc1N2Q2YTMzM2UyNzViMzZkYTFhODQzNmZjZWYwNzczNDBhYjUzZTNmYiJ9fX0=\"}]}}}");
			add("skull 1 3 {SkullOwner:{Name:%s,Id:\"973f5f11-aaca-4794-9863-81df5b76995b\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2ZiYmY5ZTRlZmQwMTkzYzUyNjQ3MTliYTQ3OGI5MTdhMjYwNzE0NmY2ZWE4MGNiNWZlMDkzYzNiOTlhNGMifX19\"}]}}}");
			add("skull 1 3 {SkullOwner:{Name:%s,Id:\"41a9ffd8-dcd6-44a0-b4f3-a7cf38cf1528\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmQ2MWNjYmZkY2RmODk0MWFkYWY3NmM2YzBlMDE4MmQyYzhiYmI1ZGMxOGYzNzQ4OTU2NTJiYzY2MWI2ZWQifX19\"}]}}}");
			add("skull 1 3 {SkullOwner:{Name:%s,Id:\"32cc3adf-2e6c-375e-3aef-9d62d391b587\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmIyMTYxN2QyNzU1YmMyMGY4ZjdlMzg4ZjQ5ZTQ4NTgyNzQ1ZmVjMTZiYjE0Yzc3NmY3MTE4Zjk4YzU1ZTgifX19\"}]}}}");
		}
	};
	
}
