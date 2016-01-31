package com.minecraft.hal.SerenityPlugins;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.message.Message;
import org.bukkit.Art;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Effect;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.Statistic;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Hanging;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Horse.Style;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.entity.Rabbit;
import org.bukkit.entity.Sheep;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.material.Dispenser;
import org.bukkit.material.Wool;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.Vector;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public final class SerenityPlugins extends JavaPlugin implements Listener,
		CommandExecutor, PluginMessageListener {

	public SerenityPlugins global = this;
	public ConfigAccessor fireworksCfg;
	public ConfigAccessor protectedAreasCfg;
	public ConfigAccessor podrickCfg;
	public ConfigAccessor stringsCfg;
	public ConfigAccessor emailCfg;
	public ConfigAccessor bookCfg;
	public ConfigAccessor linksCfg;
	public ConfigAccessor daleCfg;
	public ConfigAccessor voteCfg;
	public ConfigAccessor motdsCfg;
	public boolean opParticles;
	public boolean opParticlesDeb;

	public SimpleDateFormat psdf = new SimpleDateFormat("MM/dd");
	public SimpleDateFormat sdtf = new SimpleDateFormat(
			"EEE, MMM d, yyyy hh:mm:ss a z");

	public List<Mailbox> mailBoxes;
	public List<Player> preppedToUnProtectChunk;
	public List<PlayerProtect> preppedToProtectArea;
	public Set<PlayerStatus> playerStatuses;
	public List<FireWorkShow> fireworkShowLocations;
	public String mainMotd = "";
	public HashMap<UUID, SerenityPlayer> serenityPlayers;
	public List<String> playersInCreative;
	public Long lastCreativeList;

	public short specEff = 0;

	public static final List<DyeColor> allDyes = new ArrayList<DyeColor>() {
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

	public static final List<SerenityCommand> allCommands = new ArrayList<SerenityCommand>() {
		{
			add(new SerenityCommand("as", "Armor stand manipulation",
					"Brings up an interface to manipulate armor stands", "/as",
					2880, true));
			add(new SerenityCommand(
					"afk",
					"Sets you AFK",
					"See other AFK players in the TAB-list.\n"
							+ "While you are AFK, your vote for day is automatic",
					"/afk", 0, false));
			add(new SerenityCommand("coords", "Broadcasts your coordinates",
					"This will broadcast your coordinates to the server.\n"
							+ "It's faster than manually typing them.",
					"/coords", 0, false));
			add(new SerenityCommand(
					"gettime",
					"Tells you the time",
					"It will also tell you the server time! (Eastern US time in real life)",
					"/gettime", 0, false));
			add(new SerenityCommand(
					"ignore",
					"Ignore another player's chats",
					"Type this to ignore a player.\n"
							+ "To undo, type the command again.  It's a toggle.",
					"/ignore <PlayerName>", 0, false));
			add(new SerenityCommand(
					"lag",
					"See if the server is lagging",
					"This will return the server's Ticks Per Second (TPS).\n"
							+ "A minecraft server should be running at or close to 20 TPS.\nIt will also calculate your ping.\n"
							+ "Ping is the time it takes the network to go between your computer and the server.\n"
							+ "A ping above 200ms might seem like lag!",
					"/lag", 0, false));
			add(new SerenityCommand("links", "Server web-links",
					"Some helpful links to some of our webpages", "/links", 0,
					false));
			add(new SerenityCommand("mailto",
					"Mail your items to someone's mailbox",
					"Make sure you have a mailbox setup first.\n"
							+ "A mailbox is a chest on top of a fencepost.",
					"/mailto <MailboxName>", 0, false));
			add(new SerenityCommand(
					"map",
					"A link to the online map at your location",
					"This map updates every 30 minutes.\n"
							+ "To add a marker, type [Point] or [Home] on the first line of a sign\n"
							+ "followed by any other text you like!", "/map",
					0, false));
			add(new SerenityCommand(
					"move",
					"Move to another server",
					"/move creative to get to creative.\n"
							+ "/move event to go to the event server.\n"
							+ "These servers are totally seperate from Survival, however, \n"
							+ "you will be able to chat between Creative and Survival",
					"/move <ServerName>", 0, false));
			add(new SerenityCommand(
					"msg",
					"Privately message another player",
					"If he or she is offline, an offline message will be sent.\n"
							+ "To read offline messages, type /msg read and click each message",
					"/msg <PlayerName>", 0, false));
			add(new SerenityCommand("mytime",
					"See how much time you've spent here",
					"12 hours for colored chat, 24 to edit spawn,\n"
							+ "and 48 to manipulate armor stands", "/mytime",
					0, false));
			add(new SerenityCommand(
					"portal",
					"Calculates a nether portal location",
					"Divides or multiplies by 8 depending on your world.\n"
							+ "Helpful for syncing up nether/overworld portals",
					"/portal", 0, false));
			add(new SerenityCommand(
					"protect",
					"Claim commands",
					"/protect claim to claim\n/protect unclaim to unclaim\n/protect trust <Player> to trust a player\n/protect untrust <Player> to untrust\n/protect trustlist to see all trusted players\n/protect list to see all claims",
					"/protect", 60, false));
			add(new SerenityCommand("setchatcolor", "Set your chat color",
					"Type just /setchatcolor to see a list of colors",
					"/setchatcolor <color>", 720, false));
			add(new SerenityCommand("setcompass",
					"Set your compass to point somewhere",
					"Type this command to see all options", "/setcompass", 720,
					false));
			add(new SerenityCommand(
					"status",
					"Read or update your status",
					"Your status will also appear on the Serenity Players web page!",
					"/status <optional_status>", 0, false));
			add(new SerenityCommand(
					"text",
					"Send a text message to the owner's cell phone",
					"The owner's phone will vibrate with your message.\nUse this if no staff is available."
							+ "\n/msg [Server] to send a non-urgent message",
					"/text <message>", 720, false));
			/*
			 * add(new SerenityCommand("vote", "Vote for something",
			 * "Sometimes we have votes for stuff..", "/vote", 720, true));
			 */
		}
	};

	public static final List<Color> allColors = new ArrayList<Color>() {
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

	public static final List<Integer> allRecordIds = new ArrayList<Integer>() {
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

	public static final List<ChatColor> allChatColors = new ArrayList<ChatColor>() {
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

	public static final List<String> motdGreetings = new ArrayList<String>() {
		{
			add("Hello %s");
			add("Hey %s!");
			add("Welcome back %s");
			add("Glad to see you %s");
			add("How are you, %s ?");
			add("Howdy, %s");
			add("Welcome, %s");
			add("%s is here!");
			add("%s!!!!");
			add("OMG it's %s!");
			add("%s is cool");
		}
	};

	public List<String> allMotds = new ArrayList<String>();

	public List<Ignoring> ignorers;
	public List<Long> lags;
	public List<ProtectedArea> areas;
	public boolean votingForDay;
	public final int MAX_DISTANCE = 700;
	public Random rand = new Random();
	public Location TELEPORTDESTINATIONFORSOMETHING;
	public Location BLUEBLOCKSINFINALDUNGEON;
	public Location GREENBLOCKSINFINALDUNGEON;
	public Location REDBLOCKSINFINALDUNGEON;
	public Location CLEARBLOCKSINFINALDUNGEON;
	public Location CENTERBLOCKSINFINALDUNGEON;
	public Location WATERROOMMINESHAFT;
	public Location WATERROOMACTIVATE;
	public boolean aBattleIsRaging = false;
	public Location DOORWAYTOFINALDUNGEON;

	public int finalDungeonKillCount;

	public List<String> SOMEONEGREETINGPREFIX;

	public List<String> SOMEONETEXT1;

	public HashMap<String, Long> racers;
	public HashMap<String, String> englishStrings;

	public enum stringKeys {
		PORTBADPORTAL, TIMEONEHOUR, TIMETHREEHOUR, TIMETWELVEHOUR, PORTENDPORTALWARNING, PORTEARLYPORTALATTEMPT, MAILHASMAIL, PROTISSTUCK, CHATTRIEDTOCHATWITHGLOBALCHATDISABLED, JOINPLAYERFIRSTLOGIN, MAILTRIEDTOEXPANDMAILBOX, MAILCREATEDAMAILBOX, MAILALREADYHASAMAILBOX, MAILOPENEDAPUBLICMAILBOX, MAILINTERRACTEDWITHAMAILBOX, MAILDESTROYEDMAILBOX, MAILTRIEDTOBREAKPUBLICMAILBOX, BEDSOMEONEENTEREDABED, PROTOWNBLOCK, PROTDOESNOTOWNBUTHASPERMISSION, PROTDOESNOTOWNBLOCK, PROTNOBODYOWNSBLOCK, RANDOMTPWAIT, RANDOMTPTOOFAR, RANDOMTPWRONGWORLD, CHATHELP, PROTALREADYPREPPED, PROTPREPPEDTOPROTECT, PROTAREABADARG, PROTAREATOOSMALL, PROTAREATOOBIG, PROTAREAREADY, PROTUNCLAIMPREPPED, PROTUNCLAIMSTILLPREPPED, PROTNOAREAS, PROTTRUSTLIST, PROTAREALIST, PROTEXTENDEDLISTDATA, PROTSTUCKABUSEATTEMPT, PROTSTUCKABUSEATTEMPTNOTEVENPROT, PROTTIMEBADARG, PROTADDEDTRUST, PROTTRUSTISSUES, PROTCANTFINDTRUST, PROTUNTRUSTED, PROTHELP, PROTTOOFAR, PROTALREADYOWN, PROTAREACLAIMED, PROTFIRSTCORNER, PROTINTERSECT, PROTPREMATURE, PROTNOTYET, PROTSUCCESS, PROTNOTOVERWORLD, PROTTOOFARAWAYTOUNCLAIM, PROTUNCLAIMSUCCESS, PROTUNCLAIMDOESNOTOWN, PROTUNCLAIMNOBODYOWNS, CHATCANSEEGLOBAL, CHATCANTSEEGLOBAL, STATUSSUCCESS, PORTALOVERWORLD, PORTALNETHER, LASTSEENNOTFOUND, BEDVOTING, BEDOKNOTVOTING, AFKALREADYAFK, GETTIMETICKS, MAILDOESNTHAVEMAILBOX, MAILCOULDNOTFINDBOX, MAILTOSELF, MAILEMPTY, MAILFULL, MAILNOTENOUGHSPACE, MAILSUCCESS, COMPASSHELP, COMPASSSPAWN, COMPASSBED, COMPASSBEDDESTROYED, COMPASSMAILBOX, COMPASSNOMAILBOX, COMPASSHERE, COMPASSCUST, CHATCOLORINVALID, CHATCOLOREARLY, CHATPUBLICNOW, CHATLOCALNOW, LAGWAIT, IGNORECOULDNOTFIND, IGNORENOLONGERIGNORING, IGNORESUCCESS, NOPERMS, FIREWORKSCREATE, FIREWORKSEXISTS, FIREWORKSEARLYBREAKATTEMPT, FIREWORKNONOWNERBREAK, FIREWORKBREAKSUCCESS
	}

	public boolean scoreboardIsDisplayed = false;

	public boolean isAParty = false;
	public short partyMode = -1;
	public short partyOffset = 0;

	public Secrets Secret;
	public List<Material> possiblePartyArmors;

	public HashMap<String, String> rewardHeads;

	public static List<String> allHolidaySkulls = new ArrayList<String>() {
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

	public boolean pluginReady = false;
	private boolean showingSql;
	private long lastEventList;
	private ArrayList<String> playersInEvent;

	// public String[] betters;
	// public String[] horses;

	@Override
	public void onEnable() {
		sdtf.setTimeZone(TimeZone.getTimeZone("UTC"));
		lastCreativeList = System.currentTimeMillis();
		playersInCreative = new ArrayList<String>();
		lastEventList = System.currentTimeMillis();
		playersInEvent = new ArrayList<String>();

		ignoreArmor();
		getRewardHeads();

		stopTheParty();
		getServer().getPluginManager().registerEvents(this, this);
		this.getServer().getMessenger()
				.registerOutgoingPluginChannel(this, "BungeeCord");
		this.getServer().getMessenger()
				.registerIncomingPluginChannel(this, "BungeeCord", this);
		serenityPlayers = new HashMap<UUID, SerenityPlayer>();

		createTables();
		loadSerenityPlayersFromDatabase();
		setOnlinePlayersOnline();
		loadSerenityMailboxesFromDatabase();
		loadMessagesFromDatabase();
		loadStatusesFromDatabase();

		Secret = new Secrets();

		SOMEONEGREETINGPREFIX = new ArrayList<String>() {
			{
				add(Secret.someoneGreeting1);
				add(Secret.someoneGreeting2);
				add(Secret.someoneGreeting3);
				add(Secret.someoneGreeting4);
				add(Secret.someoneGreeting5);
			}
		};

		SOMEONETEXT1 = new ArrayList<String>() {
			{
				add(Secret.someoneFirstText1);
				add(Secret.someoneFirstText2);
				add(Secret.someoneFirstText3);
			}

		};

		possiblePartyArmors = new ArrayList<Material>() {
			{
				add(Material.APPLE);
				add(Material.ARROW);
				add(Material.BAKED_POTATO);
				add(Material.BED);
				add(Material.BOAT);
				add(Material.BREAD);
				add(Material.CACTUS);
				add(Material.CARROT_ITEM);
				add(Material.COOKIE);
				add(Material.DIRT);
				add(Material.EGG);
				add(Material.FEATHER);
				add(Material.GRAVEL);
				add(Material.POTATO_ITEM);
				add(Material.PUMPKIN);
				add(Material.PUMPKIN_SEEDS);
				add(Material.SOUL_SAND);
				add(Material.YELLOW_FLOWER);
				add(Material.RED_ROSE);
				add(Material.BONE);
				add(Material.CARPET);
				add(Material.STONE_HOE);
				add(Material.STONE_AXE);
				add(Material.STONE_PICKAXE);
				add(Material.STONE_SWORD);
				add(Material.STONE_SPADE);
				add(Material.LADDER);
				add(Material.COBBLE_WALL);
				add(Material.GLASS_BOTTLE);
				add(Material.MELON_SEEDS);
				add(Material.FLINT);
				add(Material.SIGN);
				add(Material.SNOW_BLOCK);
				add(Material.LEVER);
				add(Material.STONE_BUTTON);
				add(Material.WOOD_BUTTON);
			}
		};

		// betters = new String[3];
		// horses = new String[3];
		TELEPORTDESTINATIONFORSOMETHING = Secret.TELEPORTDESTINATIONFORSOMETHING;
		GREENBLOCKSINFINALDUNGEON = Secret.GREENBLOCKSINFINALDUNGEON;
		BLUEBLOCKSINFINALDUNGEON = Secret.BLUEBLOCKSINFINALDUNGEON;
		REDBLOCKSINFINALDUNGEON = Secret.REDBLOCKSINFINALDUNGEON;
		CLEARBLOCKSINFINALDUNGEON = Secret.CLEARBLOCKSINFINALDUNGEON;
		CENTERBLOCKSINFINALDUNGEON = Secret.CENTERBLOCKSINFINALDUNGEON;
		WATERROOMMINESHAFT = Secret.WATERROOMMINESHAFT;
		WATERROOMACTIVATE = Secret.WATERROOMACTIVATE;
		DOORWAYTOFINALDUNGEON = Secret.DOORWAYTOFINALDUNGEON;

		racers = new HashMap<String, Long>();

		// mailboxCfg = new ConfigAccessor(this, "mailboxes.yml");
		// statusCfg = new ConfigAccessor(this, "status.yml");
		fireworksCfg = new ConfigAccessor(this, "fireworks.yml");
		protectedAreasCfg = new ConfigAccessor(this, "protectedareas.yml");
		// chatcolorCfg = new ConfigAccessor(this, "chatcolor.yml");
		podrickCfg = new ConfigAccessor(this, "podrick.yml");
		stringsCfg = new ConfigAccessor(this, "strings.yml");
		// teamsCfg = new ConfigAccessor(this, "teams.yml");
		emailCfg = new ConfigAccessor(this, "email.yml");
		bookCfg = new ConfigAccessor(this, "book.yml");
		linksCfg = new ConfigAccessor(this, "links.yml");
		daleCfg = new ConfigAccessor(this, "dale.yml");
		// leaderboardCfg = new ConfigAccessor(this, "leaderboard.yml");
		// diamondFoundCfg = new ConfigAccessor(this, "diamonds.yml");
		voteCfg = new ConfigAccessor(this, "vote.yml");
		// monsterCfg = new ConfigAccessor(this, "monster.yml");
		motdsCfg = new ConfigAccessor(this, "motds.yml");

		preppedToProtectArea = new ArrayList<PlayerProtect>();
		preppedToUnProtectChunk = new ArrayList<Player>();

		fireworkShowLocations = new ArrayList<FireWorkShow>();
		ignorers = new ArrayList<Ignoring>();
		lags = new ArrayList<Long>();
		areas = new ArrayList<ProtectedArea>();
		votingForDay = false;
		ItemStack stick = Secret.SECRETITEMSTACK1;
		ItemMeta item = stick.getItemMeta();
		item.setDisplayName(Secret.SECRETITEM1NAME);
		item.addEnchant(Enchantment.LURE, -500, true);
		stick.setItemMeta(item);

		ShapedRecipe secretitem2 = new ShapedRecipe(new ItemStack(stick));
		secretitem2.shape("ABA", "BAB", "ABA");
		secretitem2.setIngredient('A', Secret.SECRETITEM1MAT1);
		secretitem2.setIngredient('B', Secret.SECRETITEM1MAT2);
		this.getServer().addRecipe(secretitem2);

		ItemStack secretItemSpookyOne = new ItemStack(Secret.SECRETITEM2RESULT,
				1, (short) 1);
		ItemMeta im = secretItemSpookyOne.getItemMeta();
		im.setDisplayName(Secret.SECRETITEM2NAME);
		secretItemSpookyOne.setItemMeta(im);

		ShapedRecipe spookyFruitRecipe = new ShapedRecipe(new ItemStack(
				secretItemSpookyOne));
		spookyFruitRecipe.shape("AAA", "ABA", "AAA");
		spookyFruitRecipe.setIngredient('A', Secret.SECRETITEM2MAT);
		spookyFruitRecipe.setIngredient('B', secretItemSpookyOne.getType());
		this.getServer().addRecipe(spookyFruitRecipe);

		ItemStack pHelm = new ItemStack(Material.LEATHER_HELMET);
		ItemMeta pHMeta = pHelm.getItemMeta();
		pHMeta.setDisplayName("§dParty Armor");
		pHelm.setItemMeta(pHMeta);

		ItemStack pChest = new ItemStack(Material.LEATHER_CHESTPLATE);
		ItemMeta pCMeta = pChest.getItemMeta();
		pCMeta.setDisplayName("§dParty Armor");
		pChest.setItemMeta(pCMeta);

		ItemStack pLegs = new ItemStack(Material.LEATHER_LEGGINGS);
		ItemMeta pLMeta = pLegs.getItemMeta();
		pLMeta.setDisplayName("§dParty Armor");
		pLegs.setItemMeta(pLMeta);

		ItemStack pBoots = new ItemStack(Material.LEATHER_BOOTS);
		ItemMeta pBMeta = pBoots.getItemMeta();
		pBMeta.setDisplayName("§dParty Armor");
		pBoots.setItemMeta(pBMeta);

		for (Material m : possiblePartyArmors) {
			ShapedRecipe helmRecipe = new ShapedRecipe(new ItemStack(pHelm));
			helmRecipe.shape("AAA", "A A", "   ");
			helmRecipe.setIngredient('A', m);
			this.getServer().addRecipe(helmRecipe);

			ShapedRecipe chestRecipe = new ShapedRecipe(new ItemStack(pChest));
			chestRecipe.shape("A A", "AAA", "AAA");
			chestRecipe.setIngredient('A', m);
			this.getServer().addRecipe(chestRecipe);

			ShapedRecipe legRecipe = new ShapedRecipe(new ItemStack(pLegs));
			legRecipe.shape("AAA", "A A", "A A");
			legRecipe.setIngredient('A', m);
			this.getServer().addRecipe(legRecipe);

			ShapedRecipe bootRecipe = new ShapedRecipe(new ItemStack(pBoots));
			bootRecipe.shape("   ", "A A", "A A");
			bootRecipe.setIngredient('A', m);
			this.getServer().addRecipe(bootRecipe);

		}

		getLogger().info("Added " + playerStatuses.size() + " statuses");

		ConfigurationSection fireworksShowsFromConfig = fireworksCfg
				.getConfig().getConfigurationSection("Fireworks");
		try {
			for (String key : fireworksShowsFromConfig.getKeys(false)) {

				ArrayList<String> coords = new ArrayList<String>();
				for (Object object : fireworksShowsFromConfig.getList(key)) {
					coords.add(object.toString());
				}

				int x = Integer.parseInt(coords.get(1));
				int y = Integer.parseInt(coords.get(2));
				int z = Integer.parseInt(coords.get(3));
				String s = coords.get(0);
				Location l = new Location(Bukkit.getWorld(s), x, y, z);
				FireWorkShow fws = new FireWorkShow(key, l);
				fws.setActive(false);
				fws.getLocation().getBlock().setType(Material.GOLD_BLOCK);
				fws.getLocation().getBlock().getState().update();
				fireworkShowLocations.add(fws);

			}
		} catch (Exception e) {
			getLogger().info(
					"Exception thrown while trying to add fireworks blocks");
		}

		ConfigurationSection protectedChunksFromConfig = protectedAreasCfg
				.getConfig().getConfigurationSection("ProtectedAreas");

		try {
			for (String key : protectedChunksFromConfig.getKeys(false)) {
				ConfigurationSection namedChunksFromConfig = protectedAreasCfg
						.getConfig().getConfigurationSection(
								"ProtectedAreas." + key);
				for (String subkey : namedChunksFromConfig.getKeys(true)) {
					ArrayList<String> coords = new ArrayList<String>();

					if (!subkey.equals("Trusts")) {
						for (Object subSubkey : namedChunksFromConfig
								.getList(subkey)) {
							coords.add(subSubkey.toString());
						}

						Location l = new Location(
								Bukkit.getWorld(coords.get(0)),
								(double) Integer.parseInt(coords.get(1)),
								(double) 1.0, (double) Integer.parseInt(coords
										.get(2)));

						Location l2 = new Location(Bukkit.getWorld(coords
								.get(0)), (double) Integer.parseInt(coords
								.get(3)), (double) 1.0,
								(double) Integer.parseInt(coords.get(4)));

						ProtectedArea pa = new ProtectedArea(l, l2, Bukkit
								.getOfflinePlayer(key).getName());

						areas.add(pa);
					}
				}
			}
		} catch (Exception e) {
			getLogger().info(
					"Exception thrown while trying to add protected areas\n"
							+ e.toString());
		}

		getLogger().info("Added " + areas.size() + " protected areas");

		try {
			for (String key : protectedChunksFromConfig.getKeys(false)) {
				ConfigurationSection namedChunksFromConfig = protectedAreasCfg
						.getConfig().getConfigurationSection(
								"ProtectedAreas." + key);
				for (String subkey : namedChunksFromConfig.getKeys(true)) {
					ArrayList<String> coords = new ArrayList<String>();
					if (subkey.equals("Trusts")) {
						for (Object subSubkey : namedChunksFromConfig
								.getList(subkey)) {
							coords.add(subSubkey.toString());
						}

						for (ProtectedArea pa : areas) {
							if (pa.owner.equals(key)) {
								for (String name : coords) {
									pa.trustedPlayers.add(name);
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			getLogger()
					.info("Exception thrown while trying to add protected areas trust");
		}

		englishStrings = new HashMap<String, String>();

		try {
			ConfigurationSection englishStringsFromConfig = stringsCfg
					.getConfig().getConfigurationSection("Language.English");
			for (String subkey : englishStringsFromConfig.getKeys(true)) {
				String translation = englishStringsFromConfig.getString(subkey);
				translation = translation.replace('@', '§');
				translation = translation.replace('^', '\n');
				englishStrings.put(subkey, translation);
			}
		} catch (Exception e) {
			getLogger().info(
					"Exception thrown while trying to english translations");
		}

		try {
			ConfigurationSection motds = motdsCfg.getConfig()
					.getConfigurationSection("MOTDS");
			for (String s : motds.getStringList("MOTD")) {
				allMotds.add(s);
			}
		} catch (Exception e) {
			getLogger().info("Exception thrown while trying to add MOTDs");
		}

		try {
			ConfigurationSection motds = motdsCfg.getConfig()
					.getConfigurationSection("Main");
			for (String s : motds.getStringList("MOTD")) {
				mainMotd = s;
			}
		} catch (Exception e) {
			getLogger().info("Exception thrown while trying to add MOTD main");
		}

		for (Player p : Bukkit.getOnlinePlayers()) {
			SerenityPlayer sp = serenityPlayers.get(p.getUniqueId());
			sp.setOnline(true);
		}

		runEverySecond();
		runEveryMinute();
		runsaveDirtySerenityPlayersScheduler();
		runAFKTest();
		runUnAFKTest();
		runTeleportTask();
		runMailboxHearts();
		runEntityCountWatchdog();

		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {

			@Override
			public void run() {
				setListNames();
			}
		}, 10L);

		pluginReady = true;
		getLogger().info("Serenity Plugins enabled");
	}

	private void ignoreArmor() {
		org.apache.logging.log4j.core.Logger coreLogger = (org.apache.logging.log4j.core.Logger) LogManager
				.getRootLogger();
		coreLogger.addFilter(new Filter() {
			@Override
			public Result filter(LogEvent arg0) {
				if (arg0.getMessage().toString().toLowerCase()
						.contains("command: /as")) {
					return Result.DENY;
				}
				return null;
			}

			@Override
			public Result filter(Logger arg0, Level arg1, Marker arg2,
					String arg3, Object... arg4) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Result filter(Logger arg0, Level arg1, Marker arg2,
					Object arg3, Throwable arg4) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Result filter(Logger arg0, Level arg1, Marker arg2,
					Message arg3, Throwable arg4) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Result getOnMatch() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Result getOnMismatch() {
				// TODO Auto-generated method stub
				return null;
			}
		});

	}

	private void getRewardHeads() {
		rewardHeads = new HashMap<String, String>();

		try {
			Long now = System.currentTimeMillis();
			Connection conn = getConnection();
			Statement st = conn.createStatement();

			ResultSet rs = st.executeQuery("Select * FROM Heads");
			while (rs.next()) {
				String raw = rs.getString("Head");
				String name = raw.substring(raw.indexOf('"') + 1,
						raw.indexOf('}') - 1);
				String actualCommand = raw.replace("/give @p", "give %s");
				actualCommand = actualCommand.replaceFirst("\\{.*?\\},", "\\{");
				actualCommand = actualCommand.replaceFirst("SkullOwner:\\{",
						"SkullOwner:\\{Name:\"" + name + "\",");

				rewardHeads.put(actualCommand, name);

			}
			if (conn != null)
				conn.close();
			getLogger().info(
					"Time to get Status: " + (System.currentTimeMillis() - now)
							+ "ms");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void setOnlinePlayersOnline() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			serenityPlayers.get(p.getUniqueId()).setOnline(true);
		}
	}

	private void runEveryMinute() {
		BukkitScheduler scheduler = Bukkit.getScheduler();
		scheduler.runTaskTimer(this, new Runnable() {
			@Override
			public void run() {
				if (Bukkit.getOnlinePlayers().size() > 0) {
					addAMinuteToEachPlayer();
				}
			}
		}, 0L, 1200L);
	}

	private void runAFKTest() {
		BukkitScheduler scheduler = Bukkit.getScheduler();
		scheduler.runTaskTimer(this, new Runnable() {
			@Override
			public void run() {
				if (Bukkit.getOnlinePlayers().size() > 0) {
					afkTest();
				}
			}
		}, 0L, 600L);
	}

	private void runUnAFKTest() {
		BukkitScheduler scheduler = Bukkit.getScheduler();
		scheduler.runTaskTimer(this, new Runnable() {
			@Override
			public void run() {
				if (Bukkit.getOnlinePlayers().size() > 0) {
					for (SerenityPlayer sp : getOnlineSerenityPlayers()) {
						if (sp.isAFK()) {
							Player p = Bukkit.getPlayer(sp.getUUID());
							Vector bV = p.getLocation().getDirection();
							if (sp.getPlayerVectorHash() != bV.hashCode()) {
								sp.setPlayerVectorHash(bV.hashCode());
								unAfk(sp);
							}
						}
					}
				}
			}
		}, 0L, 100L);
	}

	private void runTeleportTask() {
		BukkitScheduler scheduler = Bukkit.getScheduler();
		scheduler.runTaskTimer(this, new Runnable() {
			@Override
			public void run() {
				if (Bukkit.getOnlinePlayers().size() > 0) {
					if (aBattleIsRaging)
						teleportSomeone();
				}
			}
		}, 0L, 50L);
	}

	private void runsaveDirtySerenityPlayersScheduler() {
		BukkitScheduler scheduler = Bukkit.getScheduler();
		scheduler.runTaskTimer(this, new Runnable() {
			@Override
			public void run() {
				saveDirtySerenityPlayers();
			}
		}, 0L, 6000L);
	}

	private void runMailboxHearts() {
		BukkitScheduler scheduler = Bukkit.getScheduler();
		scheduler.runTaskTimer(this, new Runnable() {
			@Override
			public void run() {
				if (Bukkit.getOnlinePlayers().size() > 0) {
					fireOnMailBoxes();
					updateFireworksBlocks();
				}
			}
		}, 0L, 40L);
	}

	private void runEntityCountWatchdog() {
		BukkitScheduler scheduler = Bukkit.getScheduler();
		scheduler.runTaskTimer(this, new Runnable() {
			@Override
			public void run() {
				if (Bukkit.getOnlinePlayers().size() > 0) {
					// int currentDay = new Date().getDay();
					if (getServer().getOnlinePlayers().size() != 0) {
						// addScores(currentDay);
						checkAndClearAllChunks();
						unloadChunks();
					}
				}
			}
		}, 0L, 12000L);
	}

	private void runEverySecond() {
		BukkitScheduler scheduler = Bukkit.getScheduler();
		scheduler.runTaskTimer(this, new Runnable() {
			@Override
			public void run() {
				Long now = System.currentTimeMillis();
				if (lags.size() > 9) {
					lags.remove(0);
				}
				lags.add(now);
				if (Bukkit.getOnlinePlayers().size() > 0) {
					sendPlayerListToBungee();

					if (System.currentTimeMillis() - lastCreativeList > 1500) {
						playersInCreative = new ArrayList<String>();
					}
					if (System.currentTimeMillis() - lastEventList > 1500) {
						playersInEvent = new ArrayList<String>();
					}
					if (System.currentTimeMillis() - lastEventList > 1500) {
						playersInEvent = new ArrayList<String>();
					}
					for (Player p : Bukkit.getOnlinePlayers()) {
						sendPlayerList(p, "§aSurvival:", "§dCreative:",
								playersInCreative, "§4Event (no chat):",
								playersInEvent);
					}

					PartyLeather();
					for (SerenityPlayer sp : getOnlineSerenityPlayers()) {
						if (sp.isCelebrating())
							celebrate(sp);
					}

					if (opParticles) {
						for (Player p : Bukkit.getOnlinePlayers()) {
							if (p.isOp()) {
								doHalSpook(p);
							}
						}
					}

					highlightSticks();
					if (getTickrate() < 16) {
						Bukkit.getLogger().info("§cTickrate: " + getTickrate());
					}
					checkParty();
				}
			}

		}, 0L, 20L);
	}

	private void loadStatusesFromDatabase() {
		playerStatuses = new TreeSet<PlayerStatus>();
		try {
			Long now = System.currentTimeMillis();
			Connection conn = getConnection();
			Statement st = conn.createStatement();

			ResultSet rs = st.executeQuery("Select * FROM Status");
			while (rs.next()) {
				GregorianCalendar gc = new GregorianCalendar();
				gc.setTimeInMillis(rs.getLong("Time"));
				PlayerStatus ps = new PlayerStatus(rs.getString("Status")
						.replace('`', '\''), gc, UUID.fromString(rs
						.getString("UUID")));

				ps.setStatus(ps.getStatus().replace('|', '\"'));
				playerStatuses.add(ps);
			}
			if (conn != null)
				conn.close();
			getLogger().info(
					"Time to get Status: " + (System.currentTimeMillis() - now)
							+ "ms");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void loadMessagesFromDatabase() {
		try {
			Long now = System.currentTimeMillis();
			Connection conn = getConnection();
			Statement st = conn.createStatement();

			ResultSet rs = st.executeQuery("Select * FROM Messages");
			while (rs.next()) {
				OfflineMessage om = new OfflineMessage();
				om.setFrom(UUID.fromString(rs.getString("FromUUID")));
				om.setTo(UUID.fromString(rs.getString("ToUUID")));
				om.setMessage(rs.getString("Message"));
				om.setRead(rs.getBoolean("ReadStatus"));
				om.setTime(rs.getLong("Time"));
				om.setID(rs.getInt("M_ID"));
				serenityPlayers.get(om.getTo()).getOfflineMessages().add(om);
			}
			if (conn != null)
				conn.close();
			getLogger().info(
					"Time to get Messages: "
							+ (System.currentTimeMillis() - now) + "ms");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void loadSerenityPlayersFromDatabase() {

		try {
			Long now = System.currentTimeMillis();
			Connection conn = getConnection();
			Statement st = conn.createStatement();

			ResultSet rs = st.executeQuery("Select * FROM Player");
			while (rs.next()) {
				SerenityPlayer sp = new SerenityPlayer();
				sp.setChatColor(rs.getString("Color"));
				sp.setUUID(UUID.fromString(rs.getString("UUID")));
				sp.setName(rs.getString("Name"));
				sp.setIP(rs.getString("IP"));
				sp.setMinutes(rs.getInt("Time"));
				sp.setFirstPlayed(rs.getLong("FirstPlayed"));
				sp.setLastPlayed(rs.getLong("LastPlayed"));
				sp.setLocaleId(rs.getInt("Locale"));
				sp.setOp(rs.getBoolean("Op"));
				sp.setWhitelisted(rs.getBoolean("Whitelisted"));
				sp.setMuted(rs.getBoolean("Muted"));
				sp.setLocalChatting(rs.getBoolean("LocalChat"));
				sp.setOnline(false);
				sp.setDirty(false);
				serenityPlayers.put(sp.getUUID(), sp);
			}
			if (conn != null)
				conn.close();
			getLogger().info(
					"Time to get SerenityPlayer cache: "
							+ (System.currentTimeMillis() - now) + "ms");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void loadSerenityMailboxesFromDatabase() {

		mailBoxes = new ArrayList<Mailbox>();
		try {
			Long now = System.currentTimeMillis();
			Connection conn = getConnection();
			Statement st = conn.createStatement();

			ResultSet rs = st.executeQuery("Select * FROM Mailbox");
			while (rs.next()) {
				Mailbox mb = new Mailbox();
				mb.uuid = UUID.fromString(rs.getString("Owner"));
				String world = rs.getString("World");
				int x = rs.getInt("X");
				int y = rs.getInt("Y");
				int z = rs.getInt("Z");
				mb.isPublic = rs.getBoolean("Public");
				if (!mb.isPublic) {
					mb.name = serenityPlayers.get(mb.uuid).getName();
				} else {
					mb.name = rs.getString("Name");
				}
				mb.location = new Location(Bukkit.getWorld(world), (double) x,
						(double) y, (double) z);
				mailBoxes.add(mb);
			}
			if (conn != null)
				conn.close();
			getLogger().info(
					"Time to get Mailboxes loaded: "
							+ (System.currentTimeMillis() - now) + "ms");
			return;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}

	/*
	 * private void populateMailboxes() {
	 * Bukkit.getScheduler().runTaskLaterAsynchronously(this, new Runnable() {
	 * 
	 * @Override public void run() { try { Connection conn = getConnection();
	 * Statement st = conn.createStatement();
	 * 
	 * ConfigurationSection mailBoxesFromConfig = mailboxCfg
	 * .getConfig().getConfigurationSection("Mailboxes");
	 * 
	 * for (String key : mailBoxesFromConfig.getKeys(false)) {
	 * 
	 * ArrayList<String> coords = new ArrayList<String>(); for (Object object :
	 * mailBoxesFromConfig.getList(key)) { coords.add(object.toString()); }
	 * 
	 * int x = Integer.parseInt(coords.get(1)); int y =
	 * Integer.parseInt(coords.get(2)); int z = Integer.parseInt(coords.get(3));
	 * String s = coords.get(0); Location l = new Location(Bukkit.getWorld(s),
	 * x, y, z); Mailbox mb = new Mailbox(); String origName = key; mb.location
	 * = l; mb.isPublic = false;
	 * 
	 * UUID insName = Bukkit.getOfflinePlayer(key) .getUniqueId();
	 * 
	 * if (uUIDisCached(insName)) { String sql =
	 * "INSERT INTO Mailbox (Owner, World, X, Y, Z, Public, Name) VALUES ('" +
	 * insName + "','" + mb.location.getWorld().getName() + "','" +
	 * mb.location.getBlockX() + "','" + mb.location.getBlockY() + "','" +
	 * mb.location.getBlockZ() + "','" + ((mb.isPublic) ? 1 : 0) + "','" + key +
	 * "');"; st.executeUpdate(sql); } } loadSerenityMailboxesFromDatabase(); if
	 * (conn != null) conn.close(); } catch (SQLException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } } }, 25L); }
	 */

	protected boolean uUIDisCached(UUID insName) {
		return serenityPlayers.get(insName) == null;
	}

	private void createTables() {
		try {
			Connection conn = getConnection();
			Statement st = conn.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS Player ("
					+ "UUID VARCHAR(40) NOT NULL PRIMARY KEY , "
					+ "Name VARCHAR(20), IP VARCHAR(15), " + "Time INT, "
					+ "FirstPlayed Long, " + "LastPlayed Long, "
					+ "Color VARCHAR(4), " + "Locale INT, " + "Op TINYINT(1), "
					+ "Whitelisted TINYINT(1), " + "Muted TINYINT(1), "
					+ "LocalChat TINYINT(1), " + "Online TINYINT(1), "
					+ " Banned TINYINT(1)" + ")";
			st.executeUpdate(sql);

			sql = "CREATE TABLE IF NOT EXISTS Mailbox (M_ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
					+ "Owner VARCHAR(40),"
					+ "FOREIGN KEY (Owner) REFERENCES Player(UUID), World VARCHAR(20), "
					+ " X INT, Y INT, Z INT, Public TINYINT(0), Name VARCHAR(20))";
			st.executeUpdate(sql);

			sql = "CREATE TABLE IF NOT EXISTS Messages ("
					+ "M_ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
					+ "FromUUID VARCHAR(40), "
					+ "FOREIGN KEY (FromUUID) REFERENCES Player(UUID), "
					+ "ToUUID VARCHAR(40), "
					+ "FOREIGN KEY (ToUUID) REFERENCES Player(UUID), "
					+ "Message VARCHAR(500), " + "ReadStatus TINYINT(1), "
					+ "Time Long);";
			st.executeUpdate(sql);

			sql = "CREATE TABLE IF NOT EXISTS Leaderboard (" + "Time Long, "
					+ "UUID VARCHAR(40), "
					+ "FOREIGN KEY (UUID) REFERENCES Player(UUID), "
					+ "Online INT, " + "Life Long, " + "Diamonds INT, "
					+ "Monsters INT," + "Villagers INT," + "Animals INT,"
					+ "Deaths INT);";
			st.executeUpdate(sql);

			sql = "CREATE TABLE IF NOT EXISTS Status (" + "UUID VARCHAR(40), "
					+ "FOREIGN KEY (UUID) REFERENCES Player(UUID), "
					+ "Time Long," + "Status VARCHAR(300));";
			st.executeUpdate(sql);

			sql = "CREATE TABLE IF NOT EXISTS Heads (Head VARCHAR(500) NOT NULL );";
			st.executeUpdate(sql);

			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * private void populatePlayerTable() {
	 * Bukkit.getScheduler().runTaskLaterAsynchronously(this, new Runnable() {
	 * 
	 * @Override public void run() { try { Connection conn = getConnection();
	 * for (OfflinePlayer p : Bukkit.getOfflinePlayers()) { Statement st =
	 * conn.createStatement(); String sql = "INSERT INTO Player VALUES ('" +
	 * p.getUniqueId().toString() + "','" + p.getName() + "', null," +
	 * getPlayerMinutes(p.getName()) + "," + p.getFirstPlayed() + "," +
	 * p.getLastPlayed() + ",'" + getChatColor(p.getUniqueId()) + "',1033,0)";
	 * st.executeUpdate(sql); } serenityPlayers =
	 * loadSerenityPlayersFromDatabase(); if (conn != null) conn.close(); }
	 * catch (SQLException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } } }, 25L); }
	 */

	protected Connection getConnection() {
		try {
			return DriverManager.getConnection(
					this.getConfig().getString("database"), this.getConfig()
							.getString("dbUser"),
					this.getConfig().getString("dbPass"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@EventHandler
	public void CraftItemEvent(org.bukkit.event.inventory.CraftItemEvent event) {
		if (event.getRecipe().getResult().hasItemMeta()
				&& event.getRecipe().getResult().getItemMeta().hasDisplayName()
				&& event.getRecipe().getResult().getItemMeta().getDisplayName()
						.contains("§dParty Armor")) {
			if (event.getWhoClicked() instanceof Player) {
				Player p = (Player) event.getWhoClicked();
				if (getPlayerMinutes(p.getUniqueId()) < 2880) {
					event.setCancelled(true);
					p.sendMessage("§cYou need more than 48 hours on the server to party");
					return;
				}
				int hash = getArmorHash(p.getDisplayName());
				Material m = possiblePartyArmors.get(hash);

				for (int i = 0; i < event.getInventory().getContents().length; i++) {
					if (i > 0) {
						ItemStack is = event.getInventory().getItem(i);
						if (is != null && is.getType() != m) {
							p.damage(0);
							event.setCancelled(true);
							return;
						}
					}
				}

				String owner = p.getDisplayName();
				ItemStack is = event.getInventory().getItem(0);
				ItemMeta im = is.getItemMeta();
				im.setDisplayName("§dParty Armor");
				List<String> lore = new ArrayList<String>();
				lore.add("May only be worn by");
				lore.add(owner);
				im.setLore(lore);
				is.setItemMeta(im);
				event.getInventory().setItem(0, is);
				getLogger().info(
						"§c" + p.getDisplayName()
								+ " crafted some party armor!");
			}
		}
	}

	private int getArmorHash(String displayName) {
		int hash = 0;
		for (int i = 0; i < displayName.length(); i++) {
			hash += displayName.charAt(i);
		}

		return hash % possiblePartyArmors.size();
	}

	protected void setListNames() {
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			@Override
			public void run() {
				for (Player p : Bukkit.getOnlinePlayers()) {
					if (!p.isOp()) {
						SerenityPlayer sp = serenityPlayers.get(p.getUniqueId());
						p.setPlayerListName(getChatColor(p.getUniqueId())
								+ p.getDisplayName()
								+ (sp.isAFK() ? "§8 (AFK)" : ""));
					}
				}
			}
		}, 5L);
	}

	private List<SerenityPlayer> getOnlineSerenityPlayers() {
		List<SerenityPlayer> sps = new ArrayList<SerenityPlayer>();
		for (Map.Entry<UUID, SerenityPlayer> entry : serenityPlayers.entrySet()) {
			if (entry.getValue().isOnline()) {
				sps.add(entry.getValue());
			}
		}
		return sps;
	}

	private void PartyLeather() {
		for (int i = 0; i < 10; i++) {
			Bukkit.getServer().getScheduler()
					.runTaskLaterAsynchronously(this, new Runnable() {
						@Override
						public void run() {
							for (Player p : Bukkit.getOnlinePlayers()) {
								tryToParty(p);
							}

						}
					}, i * 2L);
		}
	}

	private void tryToParty(Player p) {
		if (isPartyItemStack(p.getInventory().getHelmet(), p)) {
			p.getInventory().setHelmet(
					getPartyEquipment(Material.LEATHER_HELMET,
							p.getDisplayName()));
		}
		if (isPartyItemStack(p.getInventory().getChestplate(), p)) {
			p.getInventory().setChestplate(
					getPartyEquipment(Material.LEATHER_CHESTPLATE,
							p.getDisplayName()));
		}
		if (isPartyItemStack(p.getInventory().getLeggings(), p)) {
			p.getInventory().setLeggings(
					getPartyEquipment(Material.LEATHER_LEGGINGS,
							p.getDisplayName()));
		}
		if (isPartyItemStack(p.getInventory().getBoots(), p)) {
			p.getInventory().setBoots(
					getPartyEquipment(Material.LEATHER_BOOTS,
							p.getDisplayName()));
		}

		for (Entity e : p.getNearbyEntities(5, 5, 5)) {
			if (e instanceof ArmorStand) {
				if (isPartyItemStack(((ArmorStand) e).getHelmet(), null)) {
					((ArmorStand) e).setHelmet(getPartyEquipment(
							Material.LEATHER_HELMET,
							getPartyOwner(((ArmorStand) e).getHelmet())));
				}
				if (isPartyItemStack(((ArmorStand) e).getChestplate(), null)) {
					((ArmorStand) e).setChestplate(getPartyEquipment(
							Material.LEATHER_CHESTPLATE,
							getPartyOwner(((ArmorStand) e).getChestplate())));
				}
				if (isPartyItemStack(((ArmorStand) e).getLeggings(), null)) {
					((ArmorStand) e).setLeggings(getPartyEquipment(
							Material.LEATHER_LEGGINGS,
							getPartyOwner(((ArmorStand) e).getLeggings())));
				}
				if (isPartyItemStack(((ArmorStand) e).getBoots(), null)) {
					((ArmorStand) e).setBoots(getPartyEquipment(
							Material.LEATHER_BOOTS,
							getPartyOwner(((ArmorStand) e).getBoots())));
				}
			}
		}

	}

	private ItemStack getPartyEquipment(Material mat, String owner) {
		ItemStack is = new ItemStack(mat);
		LeatherArmorMeta meta = (LeatherArmorMeta) is.getItemMeta();
		float prog = (Bukkit.getWorld("world").getTime() % 100);
		prog /= 100;
		Color c = Rainbow(prog);
		meta.setColor(c);
		meta.setDisplayName("§dParty Armor");
		List<String> lore = new ArrayList<String>();
		lore.add("May only be worn by");
		lore.add(owner);
		meta.setLore(lore);
		is.setItemMeta(meta);
		return is;
	}

	public static Color Rainbow(float progress) {
		float div = (Math.abs(progress % 1) * 6);
		int ascending = (int) ((div % 1) * 255);
		int descending = 255 - ascending;

		switch ((int) div) {
		case 0:
			return Color.fromRGB(255, ascending, 0);
		case 1:
			return Color.fromRGB(descending, 255, 0);
		case 2:
			return Color.fromRGB(0, 255, ascending);
		case 3:
			return Color.fromRGB(0, descending, 255);
		case 4:
			return Color.fromRGB(ascending, 0, 255);
		default: // case 5:
			return Color.fromRGB(255, 0, descending);
		}
	}

	private boolean isPartyItemStack(ItemStack item, Player wearer) {
		if (item != null && item.hasItemMeta()
				&& item.getItemMeta().hasDisplayName()
				&& item.getItemMeta().getDisplayName().equals("§dParty Armor")) {
			if (wearer == null) {
				return true;
			}
			if (item.getItemMeta().getLore().get(1)
					.equals(wearer.getDisplayName())) {
				return true;
			} else {
				wearer.damage(0);
			}
		}
		return false;
	}

	private String getPartyOwner(ItemStack item) {
		if (item != null && item.hasItemMeta()
				&& item.getItemMeta().hasDisplayName()
				&& item.getItemMeta().getDisplayName().equals("§dParty Armor")) {
			return item.getItemMeta().getLore().get(1);
		}
		return null;
	}

	/*
	 * protected void doSerenitySpook(Player p) {
	 * 
	 * final Player pf = p;
	 * 
	 * for (int i = 0; i < 10; i++) {
	 * Bukkit.getScheduler().runTaskLaterAsynchronously(this, new Runnable() {
	 * 
	 * @Override public void run() {
	 * 
	 * List<Player> players = new ArrayList<Player>(); for (Player p :
	 * Bukkit.getOnlinePlayers()) { if (!p.equals(pf)) { players.add(p); } }
	 * 
	 * if (!players.isEmpty()) { Location loc = pf.getLocation();
	 * loc.setY(loc.getY() + 1); ParticleEffect.EXPLOSION_NORMAL.display(.125F,
	 * .5F, .125F, 0, 50, loc, players); } } }, i * 2L); } }
	 */

	protected void unloadChunks() {

		long now = System.currentTimeMillis();
		short count = 0;
		short totalCount = 0;
		for (World w : Bukkit.getWorlds()) {
			for (Chunk c : w.getLoadedChunks()) {
				/*
				 * boolean nobodyIsClose = false; Location l = c.getBlock(0, 0,
				 * 0).getLocation(); for (Player p : Bukkit.getOnlinePlayers())
				 * { if (l.getWorld().equals(p.getWorld())) { if
				 * (l.distanceSquared(p.getLocation()) < 300) { nobodyIsClose =
				 * true; } } }
				 */
				// if (!nobodyIsClose) {
				totalCount++;
				if (c.unload(true, true))
					count++;
				// }
			}
		}/*
		 * if (debugTickTimings) { Bukkit.getLogger().info( "§6" + count +
		 * " chunks were unloaded! §d(of " + totalCount + ")"); long time =
		 * System.currentTimeMillis() - now;
		 * Bukkit.getLogger().info("§6It took " + time + "ms"); }
		 */

	}

	/*
	 * protected void addScores(int currentDay) { for (SerenityPlayer sp :
	 * getOnlineSerenityPlayers()) { if (!sp.isOp()) { if (!sp.isAFK()) { int
	 * currentScore = leaderboardCfg.getConfig().getInt( sp.getName() + ".Day" +
	 * currentDay, 0); leaderboardCfg.getConfig().set( sp.getName() + ".Day" +
	 * currentDay, currentScore + 1); for (int i = 0; i < 7; i++) { if (i !=
	 * currentDay) { leaderboardCfg.getConfig().set( sp.getName() + ".Day" + i,
	 * leaderboardCfg.getConfig().get( sp.getName() + ".Day" + i, 0)); } } } } }
	 * Bukkit.getScheduler().runTaskAsynchronously(this, new Runnable() {
	 * 
	 * @Override public void run() { leaderboardCfg.saveConfig();
	 * diamondFoundCfg.saveConfig(); monsterCfg.saveConfig(); } }); }
	 */

	/*
	 * private void addScoreToCfg(ConfigAccessor config, String name) { int
	 * currentScore = config.getConfig().getInt(name, 0); currentScore++;
	 * config.getConfig().set(name, currentScore); }
	 */

	@EventHandler
	public void EatSecretSomethingEvent(PlayerItemConsumeEvent event) {
		ItemStack item = event.getItem();
		if (item.getItemMeta().hasDisplayName()) {
			if (item.getItemMeta().getDisplayName()
					.equals(Secret.SECRETITEM2NAME)) {
				event.setCancelled(true);
				event.getPlayer().kickPlayer(Secret.SECRETMESSAGE);
			}
		}
		Location l = Secret.SECRETSTANDINGLOCATION;
		if (event.getPlayer().getLocation().getWorld().getName()
				.equals("world")) {
			if (event.getPlayer().getLocation().distance(l) < 3) {
				event.getPlayer().sendTitle("§d-WARNING-",
						"§cDo NOT get crumbs on my desk!");
				Location dest = Secret.SECRETDESTINATION;
				event.setCancelled(true);
				event.getPlayer().teleport(dest);
				Bukkit.getLogger().info(
						event.getPlayer().getDisplayName()
								+ " went to the secret room!");
			}
		}
	}

	private void addTrustsToChunks(String name) {
		try {
			ConfigurationSection namedChunksFromConfig = protectedAreasCfg
					.getConfig().getConfigurationSection(
							"ProtectedAreas." + name);
			for (String subkey : namedChunksFromConfig.getKeys(true)) {
				ArrayList<String> coords = new ArrayList<String>();
				if (subkey.equals("Trusts")) {
					for (Object subSubkey : namedChunksFromConfig
							.getList(subkey)) {
						coords.add(subSubkey.toString());
					}

					for (ProtectedArea pa : areas) {
						if (pa.owner.equals(name)) {
							for (String names : coords) {
								pa.trustedPlayers.add(names);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			getLogger()
					.info("Exception thrown while trying to add protected area trust");
		}

	}

	@EventHandler
	private void onPortalCreation(BlockIgniteEvent event) {
		if (event.getBlock().getRelative(BlockFace.DOWN).getType()
				.equals(Material.OBSIDIAN)
				|| event.getBlock().getRelative(BlockFace.UP).getType()
						.equals(Material.OBSIDIAN)
				|| event.getBlock().getRelative(BlockFace.SOUTH).getType()
						.equals(Material.OBSIDIAN)
				|| event.getBlock().getRelative(BlockFace.EAST).getType()
						.equals(Material.OBSIDIAN)
				|| event.getBlock().getRelative(BlockFace.WEST).getType()
						.equals(Material.OBSIDIAN)
				|| event.getBlock().getRelative(BlockFace.NORTH).getType()
						.equals(Material.OBSIDIAN)) {
			Location l = event.getBlock().getLocation();
			if (l.getWorld().getName().equals("world_nether")) {
				Location dest = new Location(Bukkit.getWorld("world"),
						l.getX() * 8, l.getY(), l.getZ() * 8);
				for (ProtectedArea pa : areas) {
					if (pa.equals(dest)) {
						if (!pa.hasPermission(event.getPlayer()
								.getDisplayName())) {
							event.setCancelled(true);
							event.getPlayer()
									.sendMessage(
											getTranslationLanguage(event
													.getPlayer(),
													stringKeys.PORTBADPORTAL
															.toString()));
						}
					}
				}
			}
		}
	}

	@EventHandler
	private void onProtAreaFire(BlockIgniteEvent event) {
		if (!(event.getCause() == IgniteCause.FLINT_AND_STEEL)) {
			for (ProtectedArea pa : areas) {
				if (pa.equals(event.getBlock().getLocation())) {
					event.setCancelled(true);
					return;
				}
			}
		}
	}

	@EventHandler
	public void dragonEggPickup(PlayerPickupItemEvent event) {
		if (event.getItem().getItemStack().getType()
				.equals(Material.DRAGON_EGG)) {
			getLogger().info(
					"§c" + event.getPlayer().getName()
							+ " §6 picked up a dragon egg at \n"
							+ event.getPlayer().getLocation());
		}
	}

	@EventHandler
	public void dragonEggClick(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (event.getClickedBlock().getType().equals(Material.DRAGON_EGG)) {
				getLogger().info(
						"§c" + event.getPlayer().getName()
								+ "§2 clicked a dragon egg at\n"
								+ event.getClickedBlock().getLocation());
			}
		}
	}

	@EventHandler
	public void onHangingBreak(HangingBreakByEntityEvent event) {
		if (event.getRemover() instanceof Player) {
			Player p = (Player) event.getRemover();
			for (ProtectedArea pa : areas) {
				if (pa.equals(event.getEntity().getLocation())) {
					if (!pa.hasPermission(p.getDisplayName())) {
						event.setCancelled(true);
						return;
					}
				}
			}
		}
	}

	@EventHandler
	public void paintingClick(PlayerInteractEntityEvent event) {

		for (ProtectedArea p : areas) {
			if (p.equals(event.getRightClicked().getLocation())) {
				if (!p.hasPermission(event.getPlayer().getDisplayName())) {
					if (event.getRightClicked() instanceof Hanging)
						event.setCancelled(true);
					return;
				}
			}
		}

		if (event.getRightClicked() instanceof Hanging) {

			Hanging h = (Hanging) event.getRightClicked();
			if (h instanceof Painting) {
				Painting p = (Painting) h;
				int pid = p.getArt().getId();
				pid += 1;
				int newmaybe = pid;
				if (pid > Art.values().length - 1)
					pid = 0;
				p.setArt(Art.getById(pid));

				if (p.getArt().getId() != newmaybe) {
					if (p.getArt().getId() != 0)
						p.setArt(Art.getById(0));
				}
				// event.getPlayer().sendMessage("Painting ID = " + pid);
			}
		}
	}

	protected void printDebugTimings(String string, long debugtime) {
		if (false) {
			long time = (System.currentTimeMillis() - debugtime);
			if (time < 5) {
				return;
			}
			if (time > 50) {
				getLogger().info(
						"§c" + string + ": "
								+ (System.currentTimeMillis() - debugtime)
								+ "ms");
			} else {
				getLogger().info(
						"§6" + string + ": "
								+ (System.currentTimeMillis() - debugtime)
								+ "ms");
			}
		}

	}

	protected void checkParty() {
		if (isAParty) {
			if (partyMode == 0)
				partyRandomSolid();
			if (partyMode == 1)
				partyRandomDifferent();
			if (partyMode == 2) {
				partyRainbow(partyOffset);
				partyOffset++;
				if (partyOffset == 10) {
					partyOffset = 0;
				}
			}
			if (partyMode == 3) {
				partyPlaid();
			}
		}
	}

	protected void highlightSticks() {
		Bukkit.getScheduler().runTaskAsynchronously(this, new Runnable() {
			@Override
			public void run() {
				for (Player p : Bukkit.getOnlinePlayers()) {
					if (p.getItemInHand().getType() == Material.STICK) {
						for (ProtectedArea pa : areas) {
							if (pa.owner.equals(p.getDisplayName())) {

								final ProtectedArea paf = pa;
								Bukkit.getScheduler().scheduleSyncDelayedTask(
										SerenityPlugins.this, new Runnable() {
											@Override
											public void run() {

												paf.highlightArea();

											}
										});

								Bukkit.getScheduler().scheduleSyncDelayedTask(
										SerenityPlugins.this, new Runnable() {
											@Override
											public void run() {
												paf.highlightArea();
											}
										}, 10L);
							}
						}
					}
				}
			}
		});
	}

	@EventHandler
	public void halsEffects(PlayerInteractEvent event) {
		if (event.getPlayer().isOp()) {
			if (event.getPlayer().getItemInHand().getType() == Material.YELLOW_FLOWER) {
				if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)
						|| event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
					event.setCancelled(true);
					specEff++;
					if (specEff > 12) {
						specEff = 0;
					}
				}

				if (event.getAction().equals(Action.LEFT_CLICK_AIR)
						|| event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
					event.setCancelled(true);
					specEff--;
					if (specEff < 0) {
						specEff = 12;
					}
				}

				switch (specEff) {
				case 0:
					event.getPlayer().sendMessage("§cNone");
					break;
				case 1:
					event.getPlayer().sendMessage("§dPurple");
					break;
				case 2:
					event.getPlayer().sendMessage("§cHeart");
					break;
				case 3:
					event.getPlayer().sendMessage("§cFlames");
					break;
				case 4:
					event.getPlayer().sendMessage("§cVillager Hate");
					break;
				case 5:
					event.getPlayer().sendMessage("§cRedstone");
					break;
				case 6:
					event.getPlayer().sendMessage("§cEnchant");
					break;
				case 7:
					event.getPlayer().sendMessage("§cMusic");
					break;
				case 8:
					event.getPlayer().sendMessage("§cExplosion Huge");
					break;
				case 9:
					event.getPlayer().sendMessage("§cExplosion Normal");
					break;
				case 10:
					event.getPlayer().sendMessage("§cLava");
					break;
				case 11:
					event.getPlayer().sendMessage("§8Smoke monster from LOST");
					break;
				case 12:
					event.getPlayer().sendMessage("§cSquiggles");
					break;
				}
			}
		}
	}

	protected void doHalSpook(Player p) {

		final Player pf = p;

		for (int i = 0; i < 10; i++) {
			Bukkit.getScheduler().runTaskLaterAsynchronously(this,
					new Runnable() {
						@Override
						public void run() {

							List<Player> players = new ArrayList<Player>();
							for (Player p : Bukkit.getOnlinePlayers()) {
								if (!p.isOp() || opParticlesDeb) {
									players.add(p);
								}
							}

							if (!players.isEmpty()) {
								Location loc = pf.getLocation();
								loc.setY(loc.getY() + .5);

								switch (specEff) {
								case 0:
									break;
								case 1:
									ParticleEffect.SPELL_WITCH.display(.125F,
											.25F, .125F, 0, 25, loc, players);
									break;
								case 2:
									ParticleEffect.HEART.display(.125F, .25F,
											.125F, 50F, 5, loc, players);
									break;
								case 3:
									ParticleEffect.FLAME.display(.125F, .5F,
											.125F, 0, 25, loc, players);
									break;
								case 4:
									ParticleEffect.VILLAGER_ANGRY.display(
											.125F, .25F, .125F, 0, 25, loc,
											players);
									break;
								case 5:
									ParticleEffect.REDSTONE.display(.125F,
											.50F, .125F, 25, 25, loc, players);
									break;
								case 6:
									ParticleEffect.ENCHANTMENT_TABLE.display(
											.125F, .5F, .125F, 0, 25, loc,
											players);
									break;
								case 7:
									ParticleEffect.NOTE.display(.125F, .25F,
											.125F, 25, 25, loc, players);
									break;
								case 8:
									ParticleEffect.EXPLOSION_HUGE.display(
											.125F, .25F, .125F, 25, 25, loc,
											players);
									break;
								case 9:
									ParticleEffect.EXPLOSION_NORMAL.display(
											.125F, .50F, .125F, 0, 25, loc,
											players);
									break;
								case 10:
									ParticleEffect.LAVA.display(.125F, .25F,
											.125F, 0, 25, loc, players);
									break;
								case 11:
									ParticleEffect.SMOKE_LARGE.display(.125F,
											.50F, .125F, 0, 0, loc, players);
									break;
								case 12:
									ParticleEffect.SPELL_MOB.display(.25F,
											.125F, .25F, 50, 25, loc, players);
									break;
								}
							}
						}
					}, i * 2L);
		}
	}

	/*
	 * protected void CheckForHorses() { Location gate2 = new
	 * Location(Bukkit.getWorld("world"), -1388.910, 64.0, -489.01); Location
	 * gate1 = new Location(gate2.getWorld(), -1388.91, 64, -492.06); Location
	 * gate3 = new Location(gate2.getWorld(), -1388.91, 64, -485.96); for
	 * (Entity e : getNearbyEntities(gate2, 15, 8, 15)) { if (e.getCustomName()
	 * != null) { if (e.getType() == EntityType.HORSE) { if
	 * (e.getLocation().distance(gate1) < 2) { for(int i = 0; i < horses.length;
	 * i++){ if(horses[i] !=null && horses[i].equals(e.getCustomName())){
	 * horses[i] = null; } } horses[0] = e.getCustomName(); updateHorseSigns();
	 * } if (e.getLocation().distance(gate2) < 2) {
	 * 
	 * for(int i = 0; i < horses.length; i++){ if(horses[i] !=null &&
	 * horses[i].equals(e.getCustomName())){ horses[i] = null; } } horses[1] =
	 * e.getCustomName(); updateHorseSigns(); } if
	 * (e.getLocation().distance(gate3) < 2) { for(int i = 0; i < horses.length;
	 * i++){ if(horses[i] !=null && horses[i].equals(e.getCustomName())){
	 * horses[i] = null; } } horses[2] = e.getCustomName(); updateHorseSigns();
	 * } } } } }
	 * 
	 * private void updateHorseSigns() { World w = Bukkit.getWorld("world");
	 * Sign s1 = (Sign) w.getBlockAt(-1312, 65, -518) .getState(); Sign s2 =
	 * (Sign) w.getBlockAt(-1312, 65, -517) .getState(); Sign s3 = (Sign)
	 * w.getBlockAt(-1312, 65, -516) .getState();
	 * 
	 * s1.setLine(0, horses[0] == null ? "§cNONE" : "§3" + horses[0]);
	 * s2.setLine(0, horses[1] == null ? "§cNONE" : "§3" + horses[1]);
	 * s3.setLine(0, horses[2] == null ? "§cNONE" : "§3" + horses[2]);
	 * 
	 * s1.update(); s2.update(); s3.update();
	 * 
	 * }
	 */

	protected void celebrate(SerenityPlayer sp) {
		final Player pf = Bukkit.getPlayer(sp.getUUID());
		if (pf.isOnline()) {
			final Short num = sp.getCelebrateEffect();
			for (int i = 0; i < 10; i++) {
				Bukkit.getScheduler().runTaskLaterAsynchronously(global,
						new Runnable() {
							@Override
							public void run() {
								Location loc = pf.getLocation();
								loc.setY(loc.getY() + .125);

								switch (num) {
								case 0:
									loc.setY(loc.getY() + 3);
									ParticleEffect.FIREWORKS_SPARK.display(1F,
											.5F, 1F, .0001F, 4, loc, 15);
									break;
								case 1:
									ParticleEffect.FLAME.display(.25F, .125F,
											.25F, .0001F, 4, loc, 15);
									break;
								case 2:
									ParticleEffect.NOTE.display(.25F, .125F,
											.25F, 50, 4, loc, 15);
									break;
								case 3:
									ParticleEffect.SPELL_MOB.display(.25F,
											.125F, .25F, 50, 4, loc, 15);
									break;

								case 4:
									ParticleEffect.HEART.display(.25F, .125F,
											.25F, 50, 4, loc, 15);
									break;
								case 5:
									ParticleEffect.SMOKE_LARGE.display(.25F,
											.125F, .25F, .002F, 4, loc, 15);
									break;
								case 6:
									ParticleEffect.REDSTONE.display(.25F,
											.125F, .25F, 25, 4, loc, 15);
									break;
								case 7:
									ParticleEffect.PORTAL.display(.25F, .125F,
											.25F, .002F, 4, loc, 15);
									break;
								case 8:
									ParticleEffect.LAVA.display(.25F, .125F,
											.25F, .002F, 4, loc, 15);
									break;
								case 9:
									ParticleEffect.VILLAGER_HAPPY.display(.25F,
											.125F, .25F, .002F, 4, loc, 15);
									break;
								case 10:
									ParticleEffect.SPELL_WITCH.display(.25F,
											.125F, .25F, .002F, 4, loc, 15);
									break;
								}

							}
						}, i * 2L);
			}
		}
	}

	protected void partyPlaid() {
		final World w = Bukkit.getWorld("world_nether");
		final Random rand = new Random();
		final DyeColor dcf1 = allDyes.get(rand.nextInt(16));
		final DyeColor dcf2 = allDyes.get(rand.nextInt(16));
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			@Override
			public void run() {
				for (int i = -5; i < 6; i++) {
					for (int j = -5; j < 6; j++) {
						for (int k = 128; k < 133; k++) {
							Block b = w.getBlockAt(i, k, j);
							if (b.getState().getData() instanceof Wool) {
								BlockState bs = b.getState();
								Wool wol = (Wool) bs.getData();
								if (Math.abs(i) % 2 == 0
										&& Math.abs(j) % 2 == 1) {
									wol.setColor(dcf1);
								} else if (Math.abs(j) % 2 == 0
										&& Math.abs(i) % 2 == 1) {
									wol.setColor(dcf2);
								}

								bs.setData(wol);
								bs.update();
							}
						}
					}
				}
			}
		});
	}

	protected void partyRainbow(short partyOffset2) {
		final int offset = partyOffset2;
		final World w = Bukkit.getWorld("world_nether");
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			int colorRain = 0;

			@Override
			public void run() {
				for (int i = -5; i < 6; i++) {
					colorRain++;
					if (colorRain == 10) {
						colorRain = 0;
					}
					for (int j = -5; j < 6; j++) {
						for (int k = 128; k < 133; k++) {
							Block b = w.getBlockAt(i, k, j);
							if (b.getState().getData() instanceof Wool) {
								BlockState bs = b.getState();
								Wool wol = (Wool) bs.getData();
								wol.setColor(allDyes
										.get((colorRain + offset) % 10));
								bs.setData(wol);
								bs.update();
							}
						}
					}
				}
			}
		});

	}

	protected void party() {
		final World w = Bukkit.getWorld("world_nether");
		final Random rand = new Random();
		final DyeColor dcf = allDyes.get(rand.nextInt(16));
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			@Override
			public void run() {
				for (int i = -5; i < 6; i++) {
					for (int j = -5; j < 6; j++) {
						for (int k = 128; k < 133; k++) {
							Block b = w.getBlockAt(i, k, j);
							if (b.getState().getData() instanceof Wool) {
								BlockState bs = b.getState();
								Wool wol = (Wool) bs.getData();
								wol.setColor(dcf);
								bs.setData(wol);
								bs.update();
							}
						}
					}
				}
			}
		});
	}

	private void afkTest() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (!p.isOp()) {
				SerenityPlayer sp = serenityPlayers.get(p.getUniqueId());
				if (p.getLocation().getDirection().hashCode() == sp
						.getPlayerVectorHash()) {
					sp.setAFK(true);
					setListNames();
				}
				sp.setPlayerVectorHash(p.getLocation().getDirection()
						.hashCode());
			}
		}
	}

	private void setAfk(SerenityPlayer player, boolean wasAuto) {
		Player p = Bukkit.getPlayer(player.getUUID());
		if (player.isAFK()) {
			return;
		}

		player.setPlayerVectorHash(p.getLocation().hashCode());
		player.setAFK(true);

		p.setSleepingIgnored(true);
		if (!wasAuto) {
			p.sendMessage("§7You have been set §8AFK");
		}

		setListNames();

		for (Player pl : Bukkit.getOnlinePlayers()) {
			if (pl.isSleeping()) {
				checkHalfIgnored();
				return;
			}
		}
	}

	private void unAfk(SerenityPlayer player) {
		player.setAFK(false);
		player.setAfkTime(0);
		player.setPlayerVectorHash(0);
		Player p = Bukkit.getPlayer(player.getUUID());
		if (!votingForDay)
			p.setSleepingIgnored(false);
		setListNames();
	}

	protected void updateFireworksBlocks() {
		for (FireWorkShow fws : fireworkShowLocations) {
			if (fws.isActive) {
				fws.getLocation().getBlock().setType(Material.REDSTONE_BLOCK);
				fws.getLocation().getBlock().getState().update();
				if (new GregorianCalendar().getTimeInMillis()
						- fws.lastShow.getTimeInMillis() > 600000) {
					fws.getLocation().getBlock().setType(Material.GOLD_BLOCK);
					fws.getLocation().getBlock().getState().update();
					fws.setActive(false);
				}
			}
		}
	}

	protected void fireOnMailBoxes() {
		for (Mailbox mb : mailBoxes) {
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (p.getWorld().equals(mb.location.getWorld())) {
					if (p.getLocation().distanceSquared(mb.location) < 30) {
						final Mailbox mbf = mb;
						Bukkit.getScheduler().scheduleSyncDelayedTask(global,
								new Runnable() {
									@Override
									public void run() {
										if (mbf.hasMail()) {
											final Location l = new Location(
													mbf.location.getWorld(),
													mbf.location.getX() + .5,
													mbf.location.getY() + 1.25,
													mbf.location.getZ() + .5);
											Bukkit.getScheduler()
													.scheduleSyncDelayedTask(
															global,
															new Runnable() {
																@Override
																public void run() {
																	ParticleEffect.HEART
																			.display(
																					(float) .25,
																					(float) .25,
																					(float) .25,
																					0,
																					5,
																					l,
																					50);
																}
															});
										}
									}
								}, 1L);
					}
				}
			}
		}
	}

	protected void addAMinuteToEachPlayer() {

		Date d = new Date();
		d.setYear(2552);
		String date = sdtf.format(d);
		for (SerenityPlayer p : getOnlineSerenityPlayers()) {
			/*
			 * if (!p.isOp()) { whoIsOnline.getConfig().set(p.getName(), date);
			 * }
			 */
			if (!p.isAFK()) {
				checkMilestone(p);
				addAMinute(p.getUUID());
			} else {
				int t = p.getAfkTime();
				t++;
				p.setAfkTime(t);
				if (t > 45) {
					p.setAfkTime(0);
					Player pl = Bukkit.getPlayer(p.getUUID());
					pl.kickPlayer("You have been idle for too long!");
				}
			}
		}
	}

	private void checkMilestone(SerenityPlayer pl) {

		/*
		 * if (!pl.isOp()) { if (getPlayerMinutes(pl.getUUID()) % 5 == 0) {
		 * putBoneInMailbox(pl.getUUID()); } }
		 */
		if (getPlayerMinutes(pl.getUUID()) == 59) {
			Player p = Bukkit.getPlayer(pl.getUUID());
			p.sendMessage(getTranslationLanguage(p,
					stringKeys.TIMEONEHOUR.toString()));
			Bukkit.getLogger().info(p.getDisplayName() + " has reached 1 hour");
			doRandomFirework(p.getWorld(), p.getLocation());
			return;
		}
		/*
		 * 
		 * if (getPlayerMinutes(pl.getUUID()) == 179) { Player p =
		 * Bukkit.getPlayer(pl.getUUID());
		 * p.sendMessage(getTranslationLanguage(p,
		 * stringKeys.TIMETHREEHOUR.toString())); // p.sendMessage(
		 * "§2\nThanks for playing!  \nYou may now vote for server events with /vote !\n"
		 * ); Bukkit.getLogger() .info(p.getDisplayName() +
		 * " has reached 3 hours"); return; }
		 */

		if (getPlayerMinutes(pl.getUUID()) == 719) {
			Player p = Bukkit.getPlayer(pl.getUUID());
			p.sendMessage(getTranslationLanguage(p,
					stringKeys.TIMETWELVEHOUR.toString()));
			Bukkit.getLogger().info(
					p.getDisplayName() + " has reached 12 hours");
			for (int j = 0; j < 15; j++) {
				doRandomFirework(p.getWorld(), p.getLocation());
			}
			return;
		}

		if (getPlayerMinutes(pl.getUUID()) == 1439) {
			Player p = Bukkit.getPlayer(pl.getUUID());
			for (ProtectedArea pa : areas) {
				if (pa.owner.equals("[Server]")) {
					ArrayList<String> list = new ArrayList<String>();
					for (ProtectedArea pas : areas) {
						if (pas.owner.equals("[Server]")) {
							pas.addTrust(p.getDisplayName());
							list = pa.trustedPlayers;
						}
					}

					String path = "ProtectedAreas." + "[Server].Trusts";

					String[] loc = new String[list.size()];
					for (int j = 0; j < list.size(); j++) {
						loc[j] = list.get(j);
					}

					protectedAreasCfg.getConfig().set(path, loc);
					protectedAreasCfg.saveConfig();
					protectedAreasCfg.reloadConfig();

					p.sendMessage("§2\nThanks for being a dedicated player!  \nYou may now edit the spawn area!\n");
					getLogger().info(
							"§c " + p.getDisplayName() + " §6reached 24 hours");

					for (int j = 0; j < 24; j++) {
						doRandomFirework(p.getWorld(), p.getLocation());
					}
				}
			}
		}
	}

	protected void saveDirtySerenityPlayers() {

		Bukkit.getScheduler().runTaskLaterAsynchronously(this, new Runnable() {
			@Override
			public void run() {
				for (Map.Entry<UUID, SerenityPlayer> entry : serenityPlayers
						.entrySet()) {
					if (entry.getValue().isDirty()) {
						executeSQLAsync(entry.getValue().getUpdateString());
						executeSQLAsync(entry.getValue().getLeaderboardUpdate());
						entry.getValue().clearSerenityLeader();
						entry.getValue().setDirty(false);
					}
				}
			}
		}, 0L);
	}

	protected void saveDirtySerenityPlayersBlocking() {
		for (Map.Entry<UUID, SerenityPlayer> entry : serenityPlayers.entrySet()) {
			SerenityPlayer sp = entry.getValue();
			if (sp.isDirty()) {
				executeSQLBlocking(sp.getUpdateString());
				executeSQLBlocking(sp.getLeaderboardUpdate());
				sp.clearSerenityLeader();
				sp.setDirty(false);
			}
		}
	}

	private void addAMinute(UUID uuid) {
		serenityPlayers.get(uuid).setMinutes(
				serenityPlayers.get(uuid).getMinutes() + 1);
		serenityPlayers.get(uuid).setOnline(true);
		int amt = serenityPlayers.get(uuid).getSerenityLeader().getOnline();
		amt++;
		serenityPlayers.get(uuid).getSerenityLeader().setOnline(amt);

		Long life = 0L;
		life += Bukkit.getPlayer(uuid).getStatistic(Statistic.TIME_SINCE_DEATH);
		serenityPlayers.get(uuid).getSerenityLeader().setLifeInTicks(life);
	}

	@Override
	public void onDisable() {
		saveDirtySerenityPlayersBlocking();
		getLogger().info("Serenity Plugins disabled");
	}

	/*
	 * @EventHandler public void onPing(ServerListPingEvent e) { if
	 * (!pluginReady) return; String player = ""; String ip =
	 * e.getAddress().getHostAddress(); boolean hasMessage = false; for
	 * (Map.Entry<UUID, SerenityPlayer> entry : serenityPlayers.entrySet()) { if
	 * (entry.getValue().getIP().equals(ip)) { player =
	 * entry.getValue().getName(); hasMessage =
	 * hasNewMessages(entry.getValue().getUUID()); } }
	 * 
	 * if (motdsCfg.getConfig().getBoolean("Pingers", false)) { if
	 * (player.length() > 0) { getLogger().info("Pinged by " + player); } }
	 * 
	 * if (motdsCfg.getConfig().getBoolean("randomMotd", false)) { String
	 * newMotd = getRandomNonBlackColor() + "§lSerenity"; if (player.length() >
	 * 0) { newMotd += ":§r " + getRandomNonBlackColor(); if (!hasMessage) {
	 * newMotd += String.format(motdGreetings.get(rand
	 * .nextInt(motdGreetings.size())), player); } else { newMotd +=
	 * "You have a new message!!!"; } } newMotd += "\n";
	 * 
	 * 
	 * newMotd += getRandomNonBlackColor(); newMotd +=
	 * allMotds.get(rand.nextInt(allMotds.size())); e.setMotd(newMotd); }
	 * 
	 * Iterator<Player> it = e.iterator();
	 * 
	 * while (it.hasNext()) { if (it.next().isOp()) it.remove(); } }
	 */

	public boolean hasNewMessages(UUID uuid) {
		SerenityPlayer sp = serenityPlayers.get(uuid);
		if (sp != null) {
			if (sp.getOfflineMessages() != null) {
				for (OfflineMessage om : serenityPlayers.get(uuid)
						.getOfflineMessages()) {
					if (!om.isRead()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public ChatColor getRandomNonBlackColor() {
		while (true) {
			ChatColor c = allChatColors.get(rand.nextInt(allChatColors.size()));
			if (c != ChatColor.BLACK) {
				return c;
			}
		}
	}

	@EventHandler
	public void onPortal(PlayerPortalEvent evt) {
		getLogger().info("§d" + evt.getPlayer().getName() + " §5used a portal");

		/*
		 * if (evt.getTo().getWorld().getName().equals("world_nether")) { int
		 * currentCount = portalAnalytics.getConfig().getInt( (int)
		 * evt.getTo().getX() + "_" + (int) evt.getTo().getY() + "_" + (int)
		 * evt.getTo().getZ(), 0); currentCount++;
		 * portalAnalytics.getConfig().set( (int) evt.getTo().getX() + "_" +
		 * (int) evt.getTo().getY() + "_" + (int) evt.getTo().getZ(),
		 * currentCount); portalAnalytics.saveConfig();
		 * portalAnalytics.reloadConfig(); }
		 */

		if (evt.getTo().getWorld().getName().equals("world_the_end")) {
			String msg = getTranslationLanguage(evt.getPlayer(),
					stringKeys.PORTENDPORTALWARNING.toString());
			evt.getPlayer().sendMessage(msg);
			/*
			 * evt.getPlayer() .sendMessage(
			 * "§c-----[WARNING]-----\nI RESET THE END EVERY FRIDAY MORNING (5:00 AM GMT-5)  \nIf you are in here when it is deleted, you may be killed when you re-log in! (fall damage, suffocation, spawn over the edge)  Do not disconnect until you are back in the overworld!  You have been warned!"
			 * );
			 */
		}
		/*
		 * else { // evt.getPlayer().sendMessage( //
		 * "§cYou cannot travel through portals yet!");
		 * 
		 * String msg = getTranslationLanguage(evt.getPlayer(),
		 * stringKeys.PORTEARLYPORTALATTEMPT.toString());
		 * evt.getPlayer().sendMessage(msg);
		 * 
		 * evt.setCancelled(true); }
		 */
	}

	@EventHandler
	public void PlayerLoginEvent(PlayerLoginEvent event) {
		if (!pluginReady)
			event.disallow(PlayerLoginEvent.Result.KICK_OTHER,
					"Serenity Plugins is not yet loaded.  Just wait a little.");
	}

	@EventHandler
	public void onPLayerJoin(PlayerJoinEvent event) {

		SerenityPlayer sp = serenityPlayers
				.get(event.getPlayer().getUniqueId());
		if (sp != null) {
			sp.setIP(event.getPlayer().getAddress().getAddress()
					.getHostAddress());
			sp.setOp(event.getPlayer().isOp());
			sp.setName((sp.isOp()) ? "[Server]" : event.getPlayer().getName());
			sp.setOnline(true);
		} else {
			sp = new SerenityPlayer();
			sp.setIP(event.getPlayer().getAddress().getAddress()
					.getHostAddress());
			sp.setName(event.getPlayer().getName());
			sp.setMinutes(0);
			sp.setFirstPlayed(event.getPlayer().getFirstPlayed());
			sp.setOp(event.getPlayer().isOp());
			sp.setWhitelisted(event.getPlayer().isWhitelisted());
			sp.setOnline(true);
			sp.setChatColor("");
			sp.setBanned(event.getPlayer().isBanned());
			sp.setMuted(false);
			sp.setLocalChatting(false);
			sp.setLastPlayed(System.currentTimeMillis());
			sp.setUUID(event.getPlayer().getUniqueId());

			insertSerenityPlayer(event.getPlayer());
			serenityPlayers.put(event.getPlayer().getUniqueId(), sp);
		}

		if (!event.getPlayer().hasPlayedBefore()) {
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (!event.getPlayer().equals(p)) {
					if (getPlayerMinutes(p.getUniqueId()) > 720) {
						// p.sendMessage("§b" + "It's " + "§e"
						// + event.getPlayer().getDisplayName() + "§b"
						// + "'s first time on the server!  " + "§c"
						// + "(Hint: /mailto #Spawn)");

						p.sendMessage(String.format(
								getTranslationLanguage(p,
										stringKeys.JOINPLAYERFIRSTLOGIN
												.toString()), event.getPlayer()
										.getDisplayName()));
					}
				}
			}

			event.getPlayer().teleport(
					event.getPlayer().getWorld().getSpawnLocation());
		}

		if (!event.getPlayer().isOp()) {
			event.setJoinMessage(getChatColor(event.getPlayer().getUniqueId())
					+ event.getJoinMessage().substring(2));
		}

		if (event.getPlayer().isOp()) {
			event.getPlayer().setSleepingIgnored(true);

			for (Player p : Bukkit.getOnlinePlayers()) {
				p.hidePlayer(event.getPlayer());
				event.getPlayer().sendMessage("Hidden from " + p.getName());
			}

			specEff = 0;
			opParticlesDeb = false;
			opParticles = false;

			event.getPlayer().setGameMode(GameMode.CREATIVE);
			event.getPlayer().setDisplayName("[Server]");
			event.setJoinMessage(null);
		}

		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.isOp()) {
				event.getPlayer().hidePlayer(p);
				p.sendMessage("Hidden from " + event.getPlayer().getName());
			}
		}

		if (hasNewMessages(sp.getUUID())) {
			final UUID uuid = sp.getUUID();
			Bukkit.getScheduler().runTaskLater(this, new Runnable() {
				@Override
				public void run() {
					Bukkit.getPlayer(uuid)
							.sendMessage(
									"§3Type §e/msg read §3to read messages §7(click on them)");
				}
			}, 40L);
		}

		setListNames();

		event.setJoinMessage(null);

		if (!event.getPlayer().isOp()) {
			for (Mailbox mb : mailBoxes) {
				if (mb.uuid.equals(sp.getUUID())) {
					if (mb.hasMail()) {
						String msg = getTranslationLanguage(event.getPlayer(),
								stringKeys.MAILHASMAIL.toString());
						event.getPlayer().sendMessage(msg);
						// event.getPlayer().sendMessage("§2You have mail!");
						return;
					}
				}
			}
		}
		return;
	}

	private void insertSerenityPlayer(Player p) {
		String chatColor = "";
		chatColor += getChatColor(p.getUniqueId());

		String sql = "INSERT INTO Player VALUES ('"
				+ p.getUniqueId().toString() + "','" + p.getName() + "','"
				+ p.getAddress().getAddress().getHostAddress() + "'," + 0 + ","
				+ p.getFirstPlayed() + "," + p.getLastPlayed() + ",'"
				+ chatColor + "',1033,0," + (p.isWhitelisted() ? "1" : "0")
				+ ",0,0,1,0)";
		getLogger().info(sql);
		Connection conn = getConnection();
		Statement st;
		try {
			st = conn.createStatement();
			st.executeUpdate(sql);
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		SerenityPlayer sp = serenityPlayers
				.get(event.getPlayer().getUniqueId());

		if (event.getPlayer().isOp()) {
			event.setQuitMessage(null);
		}

		if (!event.getPlayer().isOp()) {
			event.setQuitMessage(getChatColor(event.getPlayer().getUniqueId())
					+ event.getQuitMessage().substring(2));
		}

		sp.setOnline(false);
		sp.setLastPlayed(System.currentTimeMillis());

		event.setQuitMessage(null);

	}

	@EventHandler
	public void onPlayerChatEvent(AsyncPlayerChatEvent event) {
		SerenityPlayer sp = serenityPlayers
				.get(event.getPlayer().getUniqueId());
		if (sp.isAFK()) {
			unAfk(sp);
		}
		sp.setPlayerVectorHash(0);

		if (event.getMessage().toUpperCase().equals(Secret.MAGICPHRASE)) {
			if (event.getPlayer().getWorld().getName().equals("world")) {
				if (event.getPlayer().getLocation()
						.distance(CENTERBLOCKSINFINALDUNGEON) < 25) {

					if (podrickCfg.getConfig().getBoolean(
							event.getPlayer().getDisplayName()
									+ ".ReadyToFight", false)
							&& !podrickCfg.getConfig().getBoolean(
									event.getPlayer().getDisplayName()
											+ ".Finished", false)) {
						event.setMessage("§k" + event.getMessage());
						event.setCancelled(true);
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
								"server startbattle");
						sendATextToHal("SerenityPlugins", event.getPlayer()
								.getName() + " has started the final battle!");
					}
				}
			}
		}

		if (event.getMessage().toLowerCase().contains("stuck")
				|| event.getMessage().toLowerCase().contains("trapped")
				|| event.getMessage().toLowerCase().contains("help")
				|| event.getMessage().toLowerCase().contains("halp")) {
			for (ProtectedArea pa : areas) {
				if (pa.equals(event.getPlayer().getLocation())) {
					if (!pa.hasPermission(event.getPlayer().getDisplayName())) {

						String msg = getTranslationLanguage(event.getPlayer(),
								stringKeys.PROTISSTUCK.toString());
						event.getPlayer().sendMessage(msg);
					}
				}
			}
		}

		if (sp.isMuted()) {
			event.getRecipients().clear();
			event.getRecipients().add(event.getPlayer());
		}

		event.setFormat("<" + getChatColor(sp.getUUID()) + "%s§r> %s");

		if (sp.isLocalChatting()) {
			event.setFormat("§7§o(%1s) %2$s");
			event.setMessage("§o" + event.getMessage());
			event.getRecipients().clear();
			event.getRecipients().add(event.getPlayer());

			World w = event.getPlayer().getWorld();
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (w.equals(p.getWorld())) {
					if (p.getLocation().distance(
							event.getPlayer().getLocation()) <= 100) {
						if (p.isOp()) {
							if (opParticles) {
								event.getRecipients().add(p);
							}
						} else {
							event.getRecipients().add(p);
						}
					}
				}
			}
			if (event.getRecipients().size() == 1) {
				event.getPlayer().sendMessage(
						"§cNobody is in local chat range!");
				event.setCancelled(true);
			}
			String periods = "";
			for (int i = 0; i < event.getRecipients().size() - 1; i++) {
				periods += ".";
			}
			event.setFormat(periods + event.getFormat());
		}

		for (int i = 0; i < ignorers.size(); i++) {
			if (ignorers.get(i).ignoreList.contains(event.getPlayer()
					.getDisplayName())) {
				event.getRecipients().remove(ignorers.get(i).player);
			}
		}

		// i am purple
		if (event.getPlayer().isOp()) {
			event.setFormat("§d" + "[Server] " + "%2$s");
		}

		if (rand.nextInt(1000) == 0 && event.getMessage().length() > 10) {
			int r = rand.nextInt(3);
			String s = "";
			switch (r) {
			case 0:
				s = rainbow(event.getMessage());
				getLogger().info("IT HAPPENED");
				break;
			case 1:
				ChatColor color1 = allChatColors.get(rand.nextInt(allChatColors
						.size()));
				ChatColor color2 = allChatColors.get(rand.nextInt(allChatColors
						.size()));
				for (int i = 0; i < event.getMessage().length(); i++) {
					if (i % 2 == 0) {
						s += color1;
					} else {
						s += color2;
					}
					s += event.getMessage().charAt(i);
				}
				getLogger().info("IT HAPPENED TWO COLORS");
				break;

			case 2:
				for (int i = 0; i < event.getMessage().length(); i++) {
					s += allChatColors.get(rand.nextInt(allChatColors.size()));
					s += event.getMessage().charAt(i);
				}
				getLogger().info("IT HAPPENED RANDOM");
				break;

			}
			event.setMessage(s);
			return;
		}

		if (rand.nextInt(5000) == 0 && event.getMessage().length() > 10) {
			String s = "";
			for (int i = 0; i < event.getMessage().length(); i++) {
				if (rand.nextInt(4) == 0) {
					s += "§k" + event.getMessage().charAt(i) + "§r"
							+ getChatColor(sp.getUUID());
				} else {
					s += event.getMessage().charAt(i);
				}
			}

			event.setMessage(s);
			getLogger().info("It happened spooooky");
		}

		event.setMessage(getChatColor(sp.getUUID()) + event.getMessage());
		event.setMessage(event.getMessage().replace("[i]", "§o"));
		event.setMessage(event.getMessage().replace("[b]", "§l"));
		event.setMessage(event.getMessage().replace("[s]", "§m"));
		event.setMessage(event.getMessage().replace("[u]", "§n"));

		event.setMessage(event.getMessage().replace("[/i]",
				"§r" + getChatColor(sp.getUUID())));
		event.setMessage(event.getMessage().replace("[/b]",
				"§r" + getChatColor(sp.getUUID())));
		event.setMessage(event.getMessage().replace("[/s]",
				"§r" + getChatColor(sp.getUUID())));
		event.setMessage(event.getMessage().replace("[/u]",
				"§r" + getChatColor(sp.getUUID())));

		if (!sp.isMuted())
			sendChatMessageToBungeeServers(event);
	}

	private String rainbow(String message) {
		String ret = "";
		for (int i = 0; i < message.length(); i++) {
			ret += allChatColors.get(i % 12);
			ret += message.charAt(i);
		}
		return ret;
	}

	private void sendChatMessageToBungeeServers(AsyncPlayerChatEvent event) {

		String sender = "";
		if (event.getPlayer().isOp()) {
			sender = "§d" + "[Server]";
		} else {
			sender = "<" + getChatColor(event.getPlayer().getUniqueId())
					+ event.getPlayer().getDisplayName() + "§r>";
		}
		String message = event.getMessage();

		sendMessageToBungee(sender, message);
	}

	private void sendMessageToBungee(String sender, String message) {
		String fullMessage = "";
		if (sender.length() > 0) {
			fullMessage += sender + " " + message;
		} else {
			fullMessage = message;
		}

		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Forward"); // So BungeeCord knows to forward it
		out.writeUTF("ALL");
		out.writeUTF("Chat"); // The channel name to check if this your data

		ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
		DataOutputStream msgout = new DataOutputStream(msgbytes);
		try {
			msgout.writeUTF(fullMessage);
			msgout.writeLong(System.currentTimeMillis());

			out.writeShort(msgbytes.toByteArray().length);
			out.write(msgbytes.toByteArray());

			// If you don't care about the player
			Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
			player.sendPluginMessage(this, "BungeeCord", out.toByteArray());

			// msgout.writeShort(123);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // You can do anything you want with msgout
	}

	protected void setPlayerUpForQuest(Player p) {
		String name = p.getDisplayName();

		Random rand = new Random();
		podrickCfg.getConfig().set(name + ".Seed", rand.nextInt(1000000));
		podrickCfg.getConfig().set(name + ".EnkisFood", rand.nextInt(8));
		podrickCfg.getConfig().set(name + ".ArgosFood", rand.nextInt(7));
		podrickCfg.getConfig().set(name + ".Started", true);

		Location l;

		boolean safe = false;
		do {
			l = new Location(Bukkit.getWorld("world"),
					(double) rand.nextInt(10000) - 5000, 5.0,
					(double) rand.nextInt(10000) - 5000);

			if (l.getWorld().getHighestBlockAt(l).getRelative(BlockFace.DOWN)
					.getType() != Material.WATER
					&& l.getWorld().getHighestBlockAt(l)
							.getRelative(BlockFace.DOWN).getType() != Material.STATIONARY_WATER) {
				boolean isprotected = false;
				for (ProtectedArea pa : areas) {
					if (pa.equals(l)) {
						isprotected = true;
					}
				}
				if (!isprotected) {
					safe = true;
				}
			}
		} while (!safe);

		podrickCfg.getConfig().set(name + ".XValue", (int) l.getX());
		podrickCfg.getConfig().set(name + ".ZValue", (int) l.getZ());
		podrickCfg.getConfig().set(name + ".YValue",
				(int) l.getWorld().getHighestBlockYAt(l));

		podrickCfg.saveConfig();
		podrickCfg.reloadConfig();

	}

	protected void putJournal1InPlayerInventory(Player p) {
		ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
		BookMeta meta = (BookMeta) book.getItemMeta();

		meta.setTitle(Secret.JOURNAL1TITLE);
		meta.setAuthor(Secret.JOURNALAUTHOR);

		String bookText = podrickCfg.getConfig().getString("Journal1");
		String hint = "";
		int type = podrickCfg.getConfig().getInt(
				p.getDisplayName() + ".EnkisFood");

		switch (type) {
		case 0:
			hint = Secret.JOURNAL1HINT0;
			break;
		case 1:
			hint = Secret.JOURNAL1HINT1;
			break;
		case 2:
			hint = Secret.JOURNAL1HINT2;
			break;
		case 3:
			hint = Secret.JOURNAL1HINT3;
			break;
		case 4:
			hint = Secret.JOURNAL1HINT4;
			break;
		case 5:
			hint = Secret.JOURNAL1HINT5;
			break;
		case 6:
			hint = Secret.JOURNAL1HINT6;
			break;
		case 7:
			hint = Secret.JOURNAL1HINT7;
			break;
		}

		bookText.replace("%", hint);
		bookText.replace('^', '§');

		List<String> pages = new ArrayList<String>();
		int l = 0;
		int lineCount = 0;
		String page = "";
		for (int i = 0; i < bookText.length(); i++) {
			if (bookText.charAt(i) == '%') {
				page += hint;
			} else if (bookText.charAt(i) == '^') {
				page += '§';
			} else if (bookText.charAt(i) == '~') {
				pages.add(page);
				page = "";
				lineCount = 0;
			} else {
				page += bookText.charAt(i);

				l++;
				if (bookText.charAt(i) == '\n' || l > 19) {
					l = 0;
					lineCount++;
				}

				if (lineCount > 12
						&& (bookText.charAt(i) == ' ' || bookText.charAt(i) == '\n')) {
					pages.add(page);
					page = "";
					lineCount = 0;
				}
			}
		}
		pages.add(page);

		meta.setPages(pages);

		List<String> lores = new ArrayList<String>();
		int seed = podrickCfg.getConfig().getInt(p.getDisplayName() + ".Seed",
				0);

		lores.add(seed + "");
		meta.setLore(lores);
		book.setItemMeta(meta);

		safelyDropItemStack(p.getLocation(), p.getInventory().addItem(book));

	}

	private String getChatColor(UUID uuid) {
		SerenityPlayer sp = serenityPlayers.get(uuid);
		if (sp != null) {
			return sp.getChatColor();
		} else {
			return "";
		}
	}

	/*
	 * @EventHandler public void onBoneAttack(EntityDamageByEntityEvent event) {
	 * if (event.getCause().equals(DamageCause.ENTITY_ATTACK)) { if
	 * (event.getDamager() instanceof Player) { Player p = (Player)
	 * event.getDamager(); if (p.getItemInHand() != null) { if
	 * (p.getItemInHand().getType() == Material.BONE) { if
	 * (p.getItemInHand().hasItemMeta() && p.getItemInHand().getItemMeta()
	 * .hasDisplayName()) { if (p.getItemInHand().getItemMeta()
	 * .getDisplayName().equals("§dSpooky Bone")) {
	 * p.sendMessage("§cNice try!  I fixed this :)"); event.setDamage(0); } } }
	 * } } } }
	 */

	@EventHandler
	public void onSecretVillagerDamage(EntityDamageEvent event) {
		if (event.getEntity().getCustomName() != null) {
			if (event.getEntity().getCustomName().contains("§6")
					|| event.getEntity().getCustomName().contains("§d")
					|| (event.getEntity().getCustomName()
							.contains(Secret.secretName) && event
							.getEntityType().equals(EntityType.HORSE))) {
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onMailboxCreate(BlockPlaceEvent event) {
		if (event.getBlock().getType().equals(Material.CHEST)
				|| event.getBlock().getType().equals(Material.TRAPPED_CHEST)) {
			Location chestLoc = event.getBlock().getLocation();
			Location fenceLoc = new Location(chestLoc.getWorld(),
					chestLoc.getX(), chestLoc.getY() - 1, chestLoc.getZ());
			if (fenceLoc.getBlock().getType().equals(Material.FENCE)
					|| fenceLoc.getBlock().getType()
							.equals(Material.ACACIA_FENCE)
					|| fenceLoc.getBlock().getType()
							.equals(Material.BIRCH_FENCE)
					|| fenceLoc.getBlock().getType()
							.equals(Material.DARK_OAK_FENCE)
					|| fenceLoc.getBlock().getType()
							.equals(Material.SPRUCE_FENCE)) {
				if (chestLoc.getBlock().getRelative(BlockFace.NORTH).getType()
						.equals(Material.CHEST)
						|| chestLoc.getBlock().getRelative(BlockFace.SOUTH)
								.getType().equals(Material.CHEST)
						|| chestLoc.getBlock().getRelative(BlockFace.EAST)
								.getType().equals(Material.CHEST)
						|| chestLoc.getBlock().getRelative(BlockFace.WEST)
								.getType().equals(Material.CHEST)
						|| chestLoc.getBlock().getRelative(BlockFace.NORTH)
								.getType().equals(Material.TRAPPED_CHEST)
						|| chestLoc.getBlock().getRelative(BlockFace.SOUTH)
								.getType().equals(Material.TRAPPED_CHEST)
						|| chestLoc.getBlock().getRelative(BlockFace.EAST)
								.getType().equals(Material.TRAPPED_CHEST)
						|| chestLoc.getBlock().getRelative(BlockFace.WEST)
								.getType().equals(Material.TRAPPED_CHEST)) {
					// event.getPlayer()
					// .sendMessage(
					// "§cYou cannot make a mailbox connected to a chest!");
					event.getPlayer().sendMessage(
							getTranslationLanguage(event.getPlayer(),
									stringKeys.MAILTRIEDTOEXPANDMAILBOX
											.toString()));
					event.setCancelled(true);
					return;
				}

				if (!hasAMailbox(event.getPlayer().getUniqueId())
						|| event.getPlayer().isOp()) {
					Mailbox mb = new Mailbox();
					mb.name = event.getPlayer().getDisplayName();
					mb.uuid = event.getPlayer().getUniqueId();
					mb.location = chestLoc;
					mb.isPublic = event.getPlayer().isOp();
					mailBoxes.add(mb);
					String sql = "INSERT INTO Mailbox (Owner, World, X, Y, Z, Public, Name) VALUES ('"
							+ mb.uuid.toString()
							+ "','"
							+ mb.location.getWorld().getName()
							+ "','"
							+ mb.location.getBlockX()
							+ "','"
							+ mb.location.getBlockY()
							+ "','"
							+ mb.location.getBlockZ()
							+ "',"
							+ ((mb.isPublic) ? 1 : 0) + ",'" + mb.name + "');";
					event.getPlayer().sendMessage(
							getTranslationLanguage(event.getPlayer(),
									stringKeys.MAILCREATEDAMAILBOX.toString()));
					executeSQLAsync(sql);

					// event.getPlayer().sendMessage("§2You made a mailbox!");
				} else {
					Mailbox mb = new Mailbox(null, "AHHHH", chestLoc);
					for (int i = 0; i < mailBoxes.size(); i++) {
						if (mailBoxes.get(i).uuid.equals(event.getPlayer()
								.getUniqueId())) {
							mb = mailBoxes.get(i);
						}
					}
					Location l = mb.location;

					String msg = getTranslationLanguage(event.getPlayer(),
							stringKeys.MAILALREADYHASAMAILBOX.toString());

					event.getPlayer().sendMessage(
							String.format(msg,
									mb.location.getWorld().getName(),
									(int) l.getX(), (int) l.getY(),
									(int) l.getZ()));
					/*
					 * event.getPlayer().sendMessage(
					 * "§cYou already have a mailbox in the §3" +
					 * mb.getLocation().getWorld().getName() + "§c at: " +
					 * "\n§3X:§2 " + l.getX() + "\n§3Y:§2 " + l.getY() +
					 * "\n§3Z:§2 " + l.getZ() +
					 * "\n§cPlease destroy that one first!");
					 */
					event.setCancelled(true);
				}
			}
		}
	}

	private boolean hasAMailbox(UUID uniqueId) {
		for (Mailbox mb : mailBoxes) {
			if (mb.uuid.equals(uniqueId)) {
				return true;
			}
		}
		return false;
	}

	@EventHandler
	public void onSecretThing(PlayerInteractEvent event) {
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)
				|| event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
			if (event.getPlayer().getItemInHand().getType() == Material.GOLD_AXE) {
				if (event.getPlayer().getItemInHand().hasItemMeta()) {
					if (event.getPlayer().getItemInHand().getItemMeta()
							.getDisplayName() != null) {
						if (event.getPlayer().getItemInHand().getItemMeta()
								.getDisplayName()
								.equals(Secret.SECRETITEM1NAME)) {
							Block target = event.getPlayer().getTargetBlock(
									(Set<Material>) null, MAX_DISTANCE);
							Location location = target.getLocation();
							doRandomFirework(event.getPlayer().getWorld(),
									location);
							event.setCancelled(true);
							return;

						}
					}
				}
			}
		}
	}

	/*
	 * @EventHandler public void onClearWater(PlayerInteractEvent event) { if
	 * (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) ||
	 * event.getAction().equals(Action.RIGHT_CLICK_AIR)) { if
	 * (event.getPlayer().getItemInHand().getType() == Material.GOLD_AXE) {
	 * Block target = event.getPlayer().getTargetBlock( (Set<Material>) null,
	 * MAX_DISTANCE); Location location = target.getLocation(); for (int i = -1;
	 * i < 2; i++) { for (int j = -1; j < 2; j++) { for (int k = -1; k < 2; k++)
	 * { Block l = location.getWorld().getBlockAt( (int) (location.getX() + i),
	 * (int) (location.getY() + j), (int) (location.getZ() + k)); if
	 * (l.getType() == Material.WATER || l.getType() ==
	 * Material.STATIONARY_WATER) { l.setType(Material.AIR); } } } } } } }
	 */

	@EventHandler
	public void onCelebrateFW(PlayerInteractEvent event) {
		SerenityPlayer sp = serenityPlayers
				.get(event.getPlayer().getUniqueId());
		if (sp.isCelebrating()) {
			if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)
					|| event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
				if (event.getPlayer().getItemInHand().getType() == Material.YELLOW_FLOWER) {
					Block target = event.getPlayer().getTargetBlock(
							(Set<Material>) null, MAX_DISTANCE);
					Location location = target.getLocation();

					doRandomFirework(event.getPlayer().getWorld(), location);
					event.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void onCelebrateSomethingElse(PlayerInteractEvent event) {
		SerenityPlayer sp = serenityPlayers
				.get(event.getPlayer().getUniqueId());
		if (sp.isCelebrating()) {
			if (event.getAction().equals(Action.LEFT_CLICK_AIR)
					|| event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
				if (event.getPlayer().getItemInHand().getType() == Material.YELLOW_FLOWER) {
					event.setCancelled(true);
					Short num = sp.getCelebrateEffect();
					sp.setCelebrateEffect((short) (num + 1));
					if (sp.getCelebrateEffect() > 11) {
						sp.setCelebrateEffect((short) 0);
					}
				}
			}
		}
	}

	@EventHandler
	public void onMailboxOpen(PlayerInteractEvent event) {
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if (event.getClickedBlock().getType().equals(Material.CHEST)
					|| event.getClickedBlock().getType()
							.equals(Material.TRAPPED_CHEST)) {
				for (Mailbox mb : mailBoxes) {
					if (mb.location.equals(event.getClickedBlock()
							.getLocation())) {
						if (mb.isPublic) {
							String msg = getTranslationLanguage(
									event.getPlayer(),
									stringKeys.MAILOPENEDAPUBLICMAILBOX
											.toString());
							event.getPlayer().sendMessage(
									String.format(msg, mb.name));

							return;
						}
						if (!mb.uuid.equals(event.getPlayer().getUniqueId())) {

							String msg = getTranslationLanguage(
									event.getPlayer(),
									stringKeys.MAILINTERRACTEDWITHAMAILBOX
											.toString());
							event.getPlayer().sendMessage(
									String.format(msg, mb.name));

							if (!event.getPlayer().isOp())
								event.setCancelled(true);
							return;
						}
					}
				}
			}
		}
	}

	// HORSE BETTING CODE... might implement in future

	/*
	 * @EventHandler public void onHorseClaimEvent(PlayerInteractEvent event) {
	 * if (event.getAction() == Action.RIGHT_CLICK_BLOCK) { if
	 * (event.getClickedBlock() != null) { if
	 * (event.getClickedBlock().getState() instanceof Sign) { double x =
	 * event.getClickedBlock().getLocation().getX(); double y =
	 * event.getClickedBlock().getLocation().getY(); double z =
	 * event.getClickedBlock().getLocation().getZ(); World w =
	 * Bukkit.getWorld("world"); if (y == 65 && x == -1312) { if (z == -518 || z
	 * == -517 || z == -516) { int zInt = (int) z; switch (zInt) { case -518: if
	 * (betters[0] != null) { if (betters[0].equals(event.getPlayer()
	 * .getDisplayName())) { Sign s = (Sign) event.getClickedBlock()
	 * .getState(); s.setLine(1, "§c[Empty]"); s.update(); betters[0] = null;
	 * event.getPlayer() .sendMessage(
	 * "§3You are no longer betting on this horse"); } else { event.getPlayer()
	 * .sendMessage( "§cSorry... someone is already betting on that horse"); } }
	 * else { if (!isBetting(event.getPlayer() .getDisplayName())) { betters[0]
	 * = event.getPlayer() .getDisplayName(); Sign s = (Sign)
	 * event.getClickedBlock() .getState(); s.setLine(1, "§1" +
	 * event.getPlayer() .getDisplayName()); event.getPlayer() .sendMessage(
	 * "§3You are now betting on this horse"); s.update(); } else {
	 * event.getPlayer() .sendMessage(
	 * "§cYou are already betting on a horse!  Right click that sign to stop betting on it!"
	 * ); } } break;
	 * 
	 * case -517: if (betters[1] != null) { if
	 * (betters[1].equals(event.getPlayer() .getDisplayName())) { Sign s =
	 * (Sign) event.getClickedBlock() .getState(); s.setLine(1, "§c[Empty]");
	 * s.update(); betters[1] = null; event.getPlayer() .sendMessage(
	 * "§3You are no longer betting on this horse"); } else { event.getPlayer()
	 * .sendMessage( "§cSorry... someone is already betting on that horse"); } }
	 * else { if (!isBetting(event.getPlayer() .getDisplayName())) { betters[1]
	 * = event.getPlayer() .getDisplayName(); Sign s = (Sign)
	 * event.getClickedBlock() .getState(); s.setLine(1, "§1" +
	 * event.getPlayer() .getDisplayName()); event.getPlayer() .sendMessage(
	 * "§3You are now betting on this horse"); s.update(); } else {
	 * event.getPlayer() .sendMessage(
	 * "§cYou are already betting on a horse!  Right click that sign to stop betting on it!"
	 * ); } } break;
	 * 
	 * case -516: if (betters[2] != null) { if
	 * (betters[2].equals(event.getPlayer() .getDisplayName())) { Sign s =
	 * (Sign) event.getClickedBlock() .getState(); s.setLine(1, "§c[Empty]");
	 * s.update(); betters[2] = null; event.getPlayer() .sendMessage(
	 * "§3You are no longer betting on this horse"); } else { event.getPlayer()
	 * .sendMessage( "§cSorry... someone is already betting on that horse"); } }
	 * else { if (!isBetting(event.getPlayer() .getDisplayName())) { betters[2]
	 * = event.getPlayer() .getDisplayName(); Sign s = (Sign)
	 * event.getClickedBlock() .getState(); s.setLine(1, "§1" +
	 * event.getPlayer() .getDisplayName()); event.getPlayer() .sendMessage(
	 * "§3You are now betting on this horse"); s.update(); } else {
	 * event.getPlayer() .sendMessage(
	 * "§cYou are already betting on a horse!  Right click that sign to stop betting on it!"
	 * ); } } break; } } } } } } }
	 */

	/*
	 * @EventHandler public void onBetItemEvent(InventoryClickEvent event) { if
	 * (event.getInventory().getType() == InventoryType.CHEST) { InventoryHolder
	 * holder = event.getInventory().getHolder(); if (holder != null && holder
	 * instanceof Chest) { Chest c = (Chest) holder; int x = c.getX(); int y =
	 * c.getY(); int z = c.getZ(); if (x == -1312 && y == 64) { if (z == -518 ||
	 * z == -517 || z == -516) { if (event.getWhoClicked() instanceof Player) {
	 * Player player = (Player) event.getWhoClicked(); switch (z) { case -518:
	 * if (betters[0] != null) { if (!betters[0].equals(player
	 * .getDisplayName())) {
	 * player.sendMessage("§cYou must be betting on this horse to change your bet"
	 * ); event.setCancelled(true); return; } } case -517: if (betters[1] !=
	 * null) { if (!betters[1].equals(player .getDisplayName())) {
	 * player.sendMessage
	 * ("§cYou must be betting on this horse to change your bet");
	 * event.setCancelled(true); return; } } case -516: if (betters[2] != null)
	 * { if (!betters[2].equals(player .getDisplayName())) {
	 * player.sendMessage("§cYou must be betting on this horse to change your bet"
	 * ); event.setCancelled(true); return; } } } } } } } } }
	 * 
	 * private boolean isBetting(String displayName) { for (int i = 0; i <
	 * betters.length; i++) { if (betters[i] != null) { if
	 * (betters[i].equals(displayName)) return true; } } return false; }
	 */

	@EventHandler
	public void partyInteract(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {

			if (event.getClickedBlock().getY() == 131
					&& event.getClickedBlock().getType() == Material.STONE_BUTTON
					&& event.getPlayer().getLocation().getWorld().getName()
							.equals("world_nether")) {
				isAParty = !isAParty;
				if (!isAParty) {
					event.getPlayer().sendMessage("§6Stopping the party :(");
					stopTheParty();
					for (Player p : Bukkit.getOnlinePlayers()) {
						if (p.getLocation().getWorld().getName()
								.equals("world_nether")) {
							if (p.getLocation().distance(
									event.getClickedBlock().getLocation()) < 25) {
								p.playEffect(event.getClickedBlock()
										.getLocation(), Effect.RECORD_PLAY, 0);
							}
						}
					}
				} else {
					event.getPlayer()
							.sendMessage(
									"§6It's party time!  Right click a wall!  To go home, right click the floor.");
					for (Player p : Bukkit.getOnlinePlayers()) {
						if (p.getLocation().getWorld().getName()
								.equals("world_nether")) {
							if (p.getLocation().distance(
									event.getClickedBlock().getLocation()) < 25) {

								p.playEffect(event.getClickedBlock()
										.getLocation(), Effect.RECORD_PLAY,
										(int) allRecordIds.get(rand
												.nextInt(allRecordIds.size())));
							}
						}
					}

				}
			}

			if (event.getClickedBlock().getType() == Material.WOOL) {
				if (event.getClickedBlock().getLocation().getWorld().getName()
						.equals("world_nether")) {
					if (event.getClickedBlock().getX() == 5) {
						event.getPlayer().sendMessage(
								allChatColors.get(rand.nextInt(16))
										+ "Random solid color");
						partyMode = 0;
						partyRandomSolid();
					}

					if (event.getClickedBlock().getX() == -5) {
						String s = "Random color for each block";
						String result = "";
						partyMode = 2;
						partyMode = 1;
						for (int i = 0; i < s.length(); i++) {
							result += allChatColors.get(rand.nextInt(16)) + ""
									+ s.charAt(i);
						}
						partyRandomDifferent();
						event.getPlayer().sendMessage(result);
					}

					if (event.getClickedBlock().getZ() == 5) {
						String s = "Rainbow fun!!";
						String result = "";
						partyMode = 2;
						partyRainbow(partyOffset);
						for (int i = 0; i < s.length(); i++) {
							result += allChatColors.get(i % 12);
							result += s.charAt(i);
						}
						event.getPlayer().sendMessage(result);
					}

					if (event.getClickedBlock().getZ() == -5) {
						String s = "Plaid fun!!";
						partyPlaid();
						String result = "";
						partyMode = 3;
						int one = rand.nextInt(16);
						int two = rand.nextInt(16);
						for (int i = 0; i < s.length(); i++) {
							if (i % 2 == 0) {
								result += allChatColors.get(one);
							} else {
								result += allChatColors.get(two);
							}
							result += s.charAt(i);
						}
						event.getPlayer().sendMessage(result);
					}

					if (event.getClickedBlock().getY() == 128) {
						Location exit = new Location(Bukkit.getWorld("world"),
								-14.582, 75.500, -73.894, -179.2F, 11.4F);
						event.getPlayer().teleport(exit);
						stopTheParty();
					}
				}
			}
		}
	}

	private void partyRandomDifferent() {
		final World w = Bukkit.getWorld("world_nether");
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			@Override
			public void run() {
				for (int i = -5; i < 6; i++) {
					for (int j = -5; j < 6; j++) {
						for (int k = 128; k < 133; k++) {
							Block b = w.getBlockAt(i, k, j);
							if (b.getState().getData() instanceof Wool) {
								BlockState bs = b.getState();
								Wool wol = (Wool) bs.getData();
								wol.setColor(allDyes.get(rand.nextInt(16)));
								bs.setData(wol);
								bs.update();
							}
						}
					}
				}
			}
		});
	}

	private void partyRandomSolid() {
		final World w = Bukkit.getWorld("world_nether");
		final DyeColor dc = allDyes.get(rand.nextInt(16));
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			@Override
			public void run() {
				for (int i = -5; i < 6; i++) {
					for (int j = -5; j < 6; j++) {
						for (int k = 128; k < 133; k++) {
							Block b = w.getBlockAt(i, k, j);
							if (b.getState().getData() instanceof Wool) {
								BlockState bs = b.getState();
								Wool wol = (Wool) bs.getData();
								wol.setColor(dc);
								bs.setData(wol);
								bs.update();
							}
						}
					}
				}
			}
		});
	}

	private void stopTheParty() {
		final World w = Bukkit.getWorld("world_nether");
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			@Override
			public void run() {
				for (int i = -5; i < 6; i++) {
					for (int j = -5; j < 6; j++) {
						for (int k = 128; k < 133; k++) {
							Block b = w.getBlockAt(i, k, j);
							if (b.getState().getData() instanceof Wool) {
								BlockState bs = b.getState();
								Wool wol = (Wool) bs.getData();
								wol.setColor(DyeColor.WHITE);
								bs.setData(wol);
								bs.update();
							}
						}
					}
				}
			}
		}, 25L);
	}

	@EventHandler
	public void onDaleBlock(PlayerInteractEvent event) {
		if (event.getAction() == Action.PHYSICAL) {
			if (racers.containsKey(event.getPlayer().getDisplayName())) {
				if (!event.getClickedBlock().isBlockFacePowered(BlockFace.SELF)) {
					Long time = System.currentTimeMillis()
							- racers.get(event.getPlayer().getDisplayName());
					String text = new SimpleDateFormat("mm:ss.SSS")
							.format(new Date(time));
					String result = "§3Current time: §b" + text;
					event.getPlayer().sendMessage(result);
				}
			}
		}

		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {

			/*
			 * if (event.getClickedBlock().getType() == Material.STONE_BUTTON) {
			 * if (event.getClickedBlock().getX() == -1314 &&
			 * event.getClickedBlock().getY() == 64 &&
			 * event.getClickedBlock().getZ() == -650) {
			 * Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ncp exempt " +
			 * event.getPlayer().getDisplayName() + " MOVING_SURVIVALFLY"); } }
			 */

			if (event.getClickedBlock().getType() == Material.GOLD_BLOCK) {
				if (event.getClickedBlock().getX() == -1403
						&& event.getClickedBlock().getY() == 65
						&& event.getClickedBlock().getZ() == -647) {
					racers.remove(event.getPlayer().getDisplayName());
					if (!racers.containsKey(event.getPlayer().getDisplayName())) {
						racers.put(event.getPlayer().getDisplayName(),
								System.currentTimeMillis());
						Location to = new Location(
								event.getPlayer().getWorld(), -1399.263, 65.0,
								-642.595, -100.7F, 24.8F);
						event.getPlayer().teleport(to);
						event.getPlayer().sendMessage("§6Go!");

						return;
					}
				}
			}

			if (event.getClickedBlock().getX() == -1403
					&& event.getClickedBlock().getY() == 65
					&& event.getClickedBlock().getZ() == -653) {
				if (racers.containsKey(event.getPlayer().getDisplayName())) {
					Long time = System.currentTimeMillis()
							- racers.get(event.getPlayer().getDisplayName());
					racers.put(event.getPlayer().getDisplayName(),
							System.currentTimeMillis());

					String text = new SimpleDateFormat("mm:ss.SSS")
							.format(new Date(time));
					String result = "§3Your time: §b" + text;

					event.getPlayer().sendMessage(result);

					if (daleCfg.getConfig().getLong(
							event.getPlayer().getDisplayName(), 900000000) > time) {
						daleCfg.getConfig().set(
								event.getPlayer().getDisplayName(), time);
						event.getPlayer()
								.sendMessage(
										"§dX§5X§1X§9X §6§lNEW PERSONAL BEST!§r §9X§1X§5X§dX");
						for (int i = 0; i < 10; i++) {
							safelySpawnExperienceOrb(event.getPlayer(), 250, 5);
						}

						daleCfg.saveConfig();
						daleCfg.reloadConfig();
					}

					/*
					 * Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
					 * "ncp unexempt " + event.getPlayer().getDisplayName() +
					 * " MOVING_SURVIVALFLY");
					 */

					racers.remove(event.getPlayer().getDisplayName());

					return;
				}
			}

			if (event.getClickedBlock().getX() == -1407
					&& event.getClickedBlock().getY() == 65
					&& event.getClickedBlock().getZ() == -650) {
				TreeMap<Long, String> m = new TreeMap<Long, String>();
				for (OfflinePlayer op : Bukkit.getOfflinePlayers()) {
					if (daleCfg.getConfig().getLong(op.getName(), -1) != -1) {
						m.put(daleCfg.getConfig().getLong(op.getName()),
								op.getName());
					}
				}

				Stack<String> results = new Stack<String>();
				for (Long key : m.keySet()) {
					results.push(" §b"
							+ new SimpleDateFormat("mm:ss.SSS")
									.format(new Date(key)) + " §3" + m.get(key));
				}

				int count = results.size();
				String result = "";

				while (!results.isEmpty()) {
					result += "§6#" + count + results.pop() + "\n";
					count--;
				}

				event.getPlayer().sendMessage(result);

			}
		}
	}

	@EventHandler
	public void onMailboxDestroy(BlockBreakEvent event) {
		if (event.getBlock().getType().equals(Material.CHEST)
				|| event.getBlock().getType().equals(Material.TRAPPED_CHEST)) {

			for (Mailbox mb : mailBoxes) {
				if (mb.location.equals(event.getBlock().getLocation())) {
					if (mb.uuid.toString().equals(
							event.getPlayer().getUniqueId().toString())) {
						String sql = "DELETE FROM Mailbox where Owner = '"
								+ event.getPlayer().getUniqueId().toString()
								+ "' AND World = '"
								+ mb.location.getWorld().getName()
								+ "' AND X = " + mb.location.getBlockX()
								+ " AND " + " Y = " + mb.location.getBlockY()
								+ " AND Z = " + mb.location.getBlockZ();
						executeSQLAsync(sql);
						String msg = getTranslationLanguage(event.getPlayer(),
								stringKeys.MAILDESTROYEDMAILBOX.toString());
						event.getPlayer().sendMessage(msg);
						mailBoxes.remove(mb);
						return;
					} else {
						event.setCancelled(true);
						return;
					}
				}
			}/*
			 * for (int i = 0; i < mailBoxes.size(); i++) { Mailbox mb =
			 * mailBoxes.get(i); if (mb.uuid == event.getPlayer().getUniqueId())
			 * { String sql = "DELETE FROM Mailbox where Owner = '" +
			 * event.getPlayer().getUniqueId().toString() + "' AND World = '" +
			 * mb.location.getWorld().getName() + "' AND X = " +
			 * mb.location.getBlockX() + " AND " + " Y = " +
			 * mb.location.getBlockY() + " AND Z = " + mb.location.getBlockZ();
			 * executeSQLAsync(sql); String msg =
			 * getTranslationLanguage(event.getPlayer(),
			 * stringKeys.MAILDESTROYEDMAILBOX.toString());
			 * event.getPlayer().sendMessage(msg); mailBoxes.remove(i); return;
			 * } }
			 */
		}
	}

	private void executeSQLAsync(String sql) {
		if (showingSql) {
			getLogger().info(sql);
		}
		final String sqlf = sql;
		Bukkit.getScheduler().runTaskLaterAsynchronously(this, new Runnable() {
			@Override
			public void run() {
				try {
					Connection conn = getConnection();
					Statement st = conn.createStatement();
					st.executeUpdate(sqlf);
					if (conn != null)
						conn.close();
					return;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, 0L);
	}

	private void executeSQLBlocking(String sql) {
		try {
			Connection conn = getConnection();
			Statement st = conn.createStatement();
			if (showingSql)
				getLogger().info(sql);
			st.executeUpdate(sql);

			if (conn != null)
				conn.close();
			return;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@EventHandler
	public void onDiamondFound(BlockBreakEvent event) {
		if (event.getBlock().getType().equals(Material.DIAMOND_ORE)) {
			final String s = event.getPlayer().getDisplayName();
			final int x = (int) event.getBlock().getLocation().getX();
			final int y = (int) event.getBlock().getLocation().getY();
			final int z = (int) event.getBlock().getLocation().getZ();

			if (y < 30) {
				Bukkit.getScheduler().runTaskLaterAsynchronously(this,
						new Runnable() {
							@Override
							public void run() {
								Bukkit.getLogger().info(
										"§6" + s + " found §bdiamond at §3" + x
												+ " " + y + " " + z);
							}
						}, 0L);
			}
		}

		if (event.getBlock().getType().equals(Material.EMERALD_ORE)) {
			final String s = event.getPlayer().getDisplayName();
			final int x = (int) event.getBlock().getLocation().getX();
			final int y = (int) event.getBlock().getLocation().getY();
			final int z = (int) event.getBlock().getLocation().getZ();

			if (y < 30) {
				Bukkit.getScheduler().runTaskLaterAsynchronously(this,
						new Runnable() {
							@Override
							public void run() {
								Bukkit.getLogger().info(
										"§6" + s + " found §2emerald at §a" + x
												+ " " + y + " " + z);
							}
						}, 0L);
			}
		}

		if (event.getBlock().getType().equals(Material.DIAMOND_ORE)) {
			if (!event.getPlayer().isOp()) {
				if (!event.getPlayer().getItemInHand().getEnchantments()
						.containsKey(Enchantment.SILK_TOUCH)) {

					int amt = serenityPlayers
							.get(event.getPlayer().getUniqueId())
							.getSerenityLeader().getDiamondsFound();
					amt++;
					serenityPlayers.get(event.getPlayer().getUniqueId())
							.getSerenityLeader().setDiamondsFound(amt);

					/*
					 * final Player p = event.getPlayer();
					 * Bukkit.getScheduler().runTaskLaterAsynchronously(this,
					 * new Runnable() {
					 * 
					 * @Override public void run() {
					 * getLogger().info("§2It counts for score!"); int
					 * currentDay = new Date().getDay();
					 * 
					 * for (int i = 0; i < 7; i++) { if (i != currentDay) {
					 * diamondFoundCfg .getConfig() .set(p.getDisplayName() +
					 * ".Day" + i, diamondFoundCfg .getConfig()
					 * .get(p.getDisplayName() + ".Day" + i, 0)); } }
					 * 
					 * addScoreToCfg(diamondFoundCfg, p.getDisplayName() +
					 * ".Day" + currentDay); diamondFoundCfg.saveConfig(); } },
					 * 0L);
					 */
				}
			}
		}
	}

	@EventHandler
	public void onMonsterKill(EntityDeathEvent event) {
		LivingEntity e = event.getEntity();
		if (e.getKiller() != null) {
			if (entityIsHostile(event.getEntity())) {

				addMonsterKill(e.getKiller().getUniqueId());

			}
		}
	}

	private boolean entityIsHostile(LivingEntity entity) {
		if (entity.getType() == EntityType.ZOMBIE
				|| entity.getType() == EntityType.BLAZE
				|| entity.getType() == EntityType.CAVE_SPIDER
				|| entity.getType() == EntityType.CREEPER
				|| entity.getType() == EntityType.ENDER_DRAGON
				|| entity.getType() == EntityType.ENDERMAN
				|| entity.getType() == EntityType.ENDERMITE
				|| entity.getType() == EntityType.GHAST
				|| entity.getType() == EntityType.GUARDIAN
				|| entity.getType() == EntityType.MAGMA_CUBE
				|| entity.getType() == EntityType.PIG_ZOMBIE
				|| entity.getType() == EntityType.SILVERFISH
				|| entity.getType() == EntityType.SKELETON
				|| entity.getType() == EntityType.SLIME
				|| entity.getType() == EntityType.SPIDER
				|| entity.getType() == EntityType.WITCH
				|| entity.getType() == EntityType.WITHER) {
			return true;
		}

		return false;
	}

	private void addMonsterKill(UUID uuid) {

		int amt = serenityPlayers.get(uuid).getSerenityLeader()
				.getMonstersKilled();
		amt++;
		serenityPlayers.get(uuid).getSerenityLeader().setMonstersKilled(amt);
		/*
		 * String displayName = Bukkit.getPlayer(uuid).getName();
		 * 
		 * Random rand = new Random(); final String displayNameF = displayName;
		 * 
		 * Calendar now = Calendar.getInstance(); int hour =
		 * now.get(Calendar.HOUR_OF_DAY); for (int i = 0; i < 24; i++) { if (i
		 * != hour) { monsterCfg.getConfig().set( displayNameF + ".Hour" + i,
		 * monsterCfg.getConfig().get(displayNameF + ".Hour" + i, 0)); } }
		 * 
		 * int prevScore = monsterCfg.getConfig().getInt( displayNameF + ".Hour"
		 * + hour);
		 * 
		 * monsterCfg.getConfig() .set(displayNameF + ".Hour" + hour, prevScore
		 * + 1);
		 */

	}

	@EventHandler
	public void onBreedEvent(CreatureSpawnEvent event) {
		if (event.getSpawnReason().equals(SpawnReason.BREEDING)) {
			for (Entity e : event.getEntity().getNearbyEntities(15, 15, 15)) {
				if (e instanceof Player) {
					SerenityPlayer sp = serenityPlayers.get(e.getUniqueId());
					int amt = sp.getSerenityLeader().getAnimalsBred();
					amt++;
					sp.getSerenityLeader().setAnimalsBred(amt);
					return;
				}
			}
		}
	}

	@EventHandler
	public void onVillagerTrade(InventoryClickEvent event) {
		if (event.getInventory().getType().equals(InventoryType.MERCHANT)) {
			if (event.getRawSlot() == 2) {
				if (event.getInventory().getItem(2) != null) {
					SerenityPlayer sp = serenityPlayers.get(event
							.getWhoClicked().getUniqueId());
					if (sp != null) {
						int amt = sp.getSerenityLeader().getVillagerTrades();
						amt++;
						sp.getSerenityLeader().setVillagerTrades(amt);
					}
				}
			}
		}
	}

	/*
	 * @EventHandler public void onMailboxBreak(PlayerInteractEvent event) { if
	 * (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) { if
	 * (event.getClickedBlock().getType().equals(Material.CHEST) ||
	 * event.getClickedBlock().getType() .equals(Material.TRAPPED_CHEST)) { for
	 * (Mailbox mb : mailBoxes) { if (mb.location.equals(event.getClickedBlock()
	 * .getLocation())) { if (mb.isPublic) { event.setCancelled(true); return; }
	 * if (!mb.uuid.equals(event.getPlayer().getUniqueId())) {
	 * event.setCancelled(true); return; } } } } } }
	 */

	@EventHandler
	public void onPlayerBedLeaveEvent(PlayerBedLeaveEvent event) {

		if (Bukkit.getOnlinePlayers().size() == 1) {
			votingForDay = false;
			return;
		}

		if (event.getBed().getLocation().getWorld().getTime() >= 23000
				|| event.getBed().getLocation().getWorld().getTime() < 500) {
			votingForDay = false;
			for (Player p : Bukkit.getServer().getOnlinePlayers()) {
				SerenityPlayer sp = serenityPlayers.get(p.getUniqueId());
				p.setSleepingIgnored(p.isOp() || sp.isAFK());

			}

			return;
		}

		boolean someoneIsSleeping = false;
		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			if (!p.equals(event.getPlayer())) {
				if (p.isSleeping()) {
					someoneIsSleeping = true;
				}
			}
		}

		if (!someoneIsSleeping) {
			for (Player p : Bukkit.getServer().getOnlinePlayers()) {
				SerenityPlayer sp = serenityPlayers.get(p.getUniqueId());
				p.setSleepingIgnored(p.isOp() || sp.isAFK());
			}

			Bukkit.getServer().broadcastMessage(
					"§cNobody is in bed anymore! Vote cancelled");
			votingForDay = false;
		}

	}

	@EventHandler
	public void onPlayerBedEnterEvent(PlayerBedEnterEvent event) {
		if (Bukkit.getOnlinePlayers().size() > 1) {
			if (!votingForDay) {
				for (Player p : Bukkit.getServer().getOnlinePlayers()) {
					if (!event.getPlayer().equals(p)) {
						if (!p.isSleeping()) {
							if (!p.isSleepingIgnored()) {

								String msg = getTranslationLanguage(
										p.getPlayer(),
										stringKeys.BEDSOMEONEENTEREDABED
												.toString());
								p.sendMessage(msg);

								/*
								 * p.sendMessage("§c" + "Someone is in a bed. "
								 * + "§e" + "Want to go to day? " + "§c" +
								 * "Type " + "§e" + "/ok " + "§c" +
								 * "or get in bed");
								 */
							}
						}
					}
				}
				getLogger().info(
						event.getPlayer().getDisplayName() + " got in bed");
			}

			votingForDay = true;

			Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
				@Override
				public void run() {
					checkHalfIgnored();
				}
			}, 3L);
		}
	}

	private void checkHalfIgnored() {
		int onlineCount = 0;
		int sleepingIgnored = 0;

		List<String> sleepers = new ArrayList<String>();

		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			if (!p.isOp()) {
				onlineCount++;
				SerenityPlayer sp = serenityPlayers.get(p.getUniqueId());
				if (p.isSleeping() || p.isSleepingIgnored() || sp.isAFK()) {
					if (p.isSleeping()) {
						sleepers.add(p.getDisplayName());
					}
					sleepingIgnored++;
				}
			}
		}

		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			if (p.isSleeping()) {
				double percentage = (double) sleepingIgnored / onlineCount;
				percentage *= 100.0;

				String result = new DecimalFormat("##.##").format(percentage);
				result += "%";
				if (percentage <= 25) {
					result = "§c" + result;
					p.sendTitle("§4Voting for §eDay!", result);
				} else if (percentage <= 50) {
					result = "§e" + result;
					p.sendTitle("§4§4Voting for §eDay!", result);
				} else if (percentage < 100) {
					result = "§a" + result;
					p.sendTitle("§4Vote passed!", result);
				} else {
					result = "§2§l" + result;
					p.sendTitle("§4Vote passed!", result);
				}
			}
		}

		getLogger().info(
				"Current: " + ((double) sleepingIgnored / (double) onlineCount)
						+ "");

		if (!sleepers.isEmpty()) {
			if ((double) sleepingIgnored / (double) onlineCount > 0.5) {
				Bukkit.getLogger().info(
						"Vote passed: setting all to sleep ignored");
				for (Player p : Bukkit.getServer().getOnlinePlayers()) {
					p.setSleepingIgnored(true);
					if (p.isOp()) {
						if (p.getGameMode().equals(GameMode.SPECTATOR)) {
							final Player pf = p;
							Bukkit.getScheduler().scheduleSyncDelayedTask(this,
									new Runnable() {
										@Override
										public void run() {
											pf.setGameMode(GameMode.SPECTATOR);
										}
									}, 80L);
						}
						p.setGameMode(GameMode.CREATIVE);
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onChunkTestEventBlockClick(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		if (p.getItemInHand().getType() == Material.STICK
				&& (event.getAction() == Action.RIGHT_CLICK_AIR || event
						.getAction() == Action.RIGHT_CLICK_BLOCK)) {

			event.setCancelled(true);
			Long before = System.currentTimeMillis();
			World world = event.getPlayer().getWorld();

			Block target = event.getPlayer().getTargetBlock(
					(Set<Material>) null, MAX_DISTANCE);
			for (ProtectedArea pa : areas) {
				if (pa.equals(target.getLocation())) {
					if (pa.owner.equals(event.getPlayer().getDisplayName())) {
						Location l = new Location(target.getLocation()
								.getWorld(), target.getLocation().getX() + .5,
								target.getLocation().getY() + .5, target
										.getLocation().getZ() + .5);
						ParticleEffect.VILLAGER_HAPPY.display((float) .5,
								(float) .5, (float) .5, 1, 50, l, 50);
						// event.getPlayer().sendMessage("§3You own that block");

						String msg = getTranslationLanguage(event.getPlayer(),
								stringKeys.PROTOWNBLOCK.toString());
						event.getPlayer().sendMessage(msg);

						return;
					}
					if (pa.hasPermission(event.getPlayer().getDisplayName())) {
						Location l = new Location(target.getLocation()
								.getWorld(), target.getLocation().getX() + .5,
								target.getLocation().getY() + .5, target
										.getLocation().getZ() + .5);
						ParticleEffect.VILLAGER_HAPPY.display((float) .5,
								(float) .5, (float) .5, 1, 50, l, 50);

						/*
						 * event.getPlayer() .sendMessage( "§2" + pa.owner +
						 * "§3 owns that block but you have permission for it");
						 */

						String msg = getTranslationLanguage(event.getPlayer(),
								stringKeys.PROTDOESNOTOWNBUTHASPERMISSION
										.toString());
						event.getPlayer().sendMessage(
								String.format(msg, pa.owner));
						return;
					}
					Location l = new Location(target.getLocation().getWorld(),
							target.getLocation().getX() + .5, target
									.getLocation().getY() + .5, target
									.getLocation().getZ() + .5);

					ParticleEffect.FLAME.display((float) .5, (float) .5,
							(float) .5, 0, 50, l, 50);
					/*
					 * event.getPlayer().sendMessage(
					 * "§cYou do not have permission for that block");
					 */
					/*
					 * String msg = getTranslationLanguage(event.getPlayer(),
					 * stringKeys.PROTDOESNOTOWNBLOCK.toString());
					 * event.getPlayer().sendMessage(msg);
					 */

					String msg = getTranslationLanguage(event.getPlayer(),
							stringKeys.PROTDOESNOTOWNBLOCK.toString());
					event.getPlayer().sendMessage(String.format(msg, pa.owner));
					return;
				}
			}
			Long after = System.currentTimeMillis();
			// event.getPlayer().sendMessage("§4Nobody owns that block");

			String msg = getTranslationLanguage(event.getPlayer(),
					stringKeys.PROTNOBODYOWNSBLOCK.toString());
			event.getPlayer().sendMessage(msg);

			/*
			 * if (event.getPlayer().isOp()) { event.getPlayer().sendMessage(
			 * "Time to iterate: " + (after - before) + "ms. " + areas.size());
			 * }
			 */

			Location l = new Location(target.getLocation().getWorld(), target
					.getLocation().getX() + .5,
					target.getLocation().getY() + .5, target.getLocation()
							.getZ() + .5);
			ParticleEffect.NOTE.display((float) .5, (float) .5, (float) .5, 0,
					30, l, 50);
		}
	}

	private void doRandomFirework(World world, Location location) {

		final Location l = location;
		Bukkit.getScheduler().runTaskAsynchronously(this, new Runnable() {
			@Override
			public void run() {

				rand = new Random();

				List<Color> colors1 = new ArrayList<Color>();
				List<Color> colors2 = new ArrayList<Color>();

				int numberOfRandColors = rand.nextInt(10) + 1;
				int numberOfRandFades = rand.nextInt(10) + 1;

				for (int i = 0; i < numberOfRandColors; i++) {
					int c1 = rand.nextInt(256);
					int c2 = rand.nextInt(256);
					int c3 = rand.nextInt(256);
					colors1.add(Color.fromRGB(c1, c2, c3));
				}

				for (int i = 0; i < numberOfRandFades; i++) {
					int c1 = rand.nextInt(256);
					int c2 = rand.nextInt(256);
					int c3 = rand.nextInt(256);
					colors2.add(Color.fromRGB(c1, c2, c3));
				}

				int r = rand.nextInt(3) + 2;

				Boolean b1 = rand.nextBoolean();
				Boolean b2 = rand.nextBoolean();

				final boolean b1f = b1;
				final boolean b2f = b2;
				final List<Color> colors1f = colors1;
				final List<Color> colors2f = colors2;
				final int rf = r;

				Bukkit.getScheduler().scheduleSyncDelayedTask(global,
						new Runnable() {

							@Override
							public void run() {
								Firework fw = (Firework) l.getWorld().spawn(l,
										Firework.class);

								FireworkEffect effect = FireworkEffect
										.builder().trail(b1f).flicker(b2f)
										.withColor(colors1f).withFade(colors2f)
										.with(getRandType()).build();

								FireworkMeta fwm = fw.getFireworkMeta();
								fwm.setPower(rf);

								fwm.clearEffects();
								fwm.addEffect(effect);

								fw.setFireworkMeta(fwm);
							}
						});
			}
		});

		return;
	}

	private Type getRandType() {
		int r = rand.nextInt(5);
		if (r == 0)
			return Type.BALL;
		if (r == 1)
			return Type.BALL_LARGE;
		if (r == 2)
			return Type.BURST;
		if (r == 3)
			return Type.CREEPER;
		return Type.STAR;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String commandLabel, String[] arg3) {

		if (commandLabel.equalsIgnoreCase("coords")) {
			return coords(sender);
		}

		else if (commandLabel.equalsIgnoreCase("server")) {
			return server(sender, arg3);
		}

		else if (commandLabel.equalsIgnoreCase("lc")) {
			return chat(sender, arg3);
		}

		else if (commandLabel.equalsIgnoreCase("status")) {
			return status(sender, arg3);
		}

		else if (commandLabel.equalsIgnoreCase("portal")) {
			return getPortal(sender);
		}

		else if (commandLabel.equalsIgnoreCase("lastseen")) {
			return lastLogin(sender, arg3);
		}

		else if (commandLabel.equalsIgnoreCase("afk")) {
			return afk(sender, arg3);
		}

		else if (commandLabel.equalsIgnoreCase("help")) {
			return help(sender, arg3);
		}

		else if (commandLabel.equalsIgnoreCase("as")) {
			return armorStand(sender, arg3);
		}

		else if (commandLabel.equalsIgnoreCase("vote")) {
			return vote(sender, arg3);
		}

		else if (commandLabel.equalsIgnoreCase("gettime")) {
			return getTime(sender, arg3);
		}

		else if (commandLabel.equalsIgnoreCase("mailto")) {
			return mailTo(sender, arg3);
		}

		else if (commandLabel.equalsIgnoreCase("setcompass")) {
			return setCompass(sender, arg3);

		}

		else if (commandLabel.equalsIgnoreCase("setchatcolor")) {
			return setChatColor(sender, arg3);
		}

		else if (commandLabel.equalsIgnoreCase("lag")) {
			return lag(sender);
		}

		else if (commandLabel.equalsIgnoreCase("ignore")) {
			return ignore(sender, arg3);
		}

		else if (commandLabel.equalsIgnoreCase("protect")) {
			return chunk(sender, arg3);
		}

		/*
		 * else if (commandLabel.equalsIgnoreCase("language")) { return
		 * language(sender, arg3); }
		 */

		else if (commandLabel.equalsIgnoreCase("msg")) {
			return msg(sender, arg3);
		}

		else if (commandLabel.equalsIgnoreCase("text")) {
			return text(sender, arg3);
		}

		else if (commandLabel.equalsIgnoreCase("links")) {
			return links(sender, arg3);
		}

		else if (commandLabel.equalsIgnoreCase("mytime")) {
			return mytime(sender, arg3);
		}

		else if (commandLabel.equalsIgnoreCase("map")) {
			return map(sender, arg3);
		}

		else if (commandLabel.equalsIgnoreCase("move")) {
			return move(sender, arg3);
		}

		return false;
	}

	private boolean help(CommandSender sender, String[] arg3) {
		if (arg3.length == 0) {
			String[] newArr = new String[1];
			newArr[0] = "1";
			arg3 = newArr;
		}
		if (sender instanceof Player) {
			Player p = ((Player) sender).getPlayer();
			SerenityPlayer sp = serenityPlayers.get(p.getUniqueId());

			List<SerenityCommand> thisPlayerCommandList = new ArrayList<SerenityCommand>();

			for (SerenityCommand sc : allCommands) {
				if (sc.secret) {
					if (sp.getMinutes() >= sc.timeRequired) {
						thisPlayerCommandList.add(sc);
					}
				} else {
					thisPlayerCommandList.add(sc);
				}
			}

			int maxPage = (thisPlayerCommandList.size() - 1) / 9;
			maxPage++;

			int page = 0;
			boolean searching = false;
			String searchTerm = "";
			try {
				page = Integer.parseInt(arg3[0]);
			} catch (NumberFormatException e) {
				searching = true;
				searchTerm = arg3[0];
			}
			page--;

			if (!searching) {

				int displayPage = page + 1;

				if (displayPage < maxPage) {
					String prev = FancyText.GenerateFancyText(
							"§5§lSerenity Commands §7(hover for info) §dPage "
									+ (page + 1) + "/" + maxPage + "§5",
							FancyText.RUN_COMMAND,
							"/help " + (displayPage + 1), FancyText.SHOW_TEXT,
							"Go to next page");
					sendRawPacket(p, prev);
				} else {
					String prev = FancyText.GenerateFancyText(
							"§5§lSerenity Commands §7(hover for info) §dPage "
									+ (page + 1) + "/" + maxPage + "§5",
							FancyText.RUN_COMMAND,
							"/help " + (displayPage - 1), FancyText.SHOW_TEXT,
							"Go to previous page");
					sendRawPacket(p, prev);
				}

				for (int i = page * 9; i < 9 + (page * 9); i++) {
					if (i < thisPlayerCommandList.size()) {
						sendCommandHelp(thisPlayerCommandList.get(i), p);
					} else {
						p.sendMessage("");
					}

				}
			} else {
				thisPlayerCommandList = new ArrayList<SerenityCommand>();
				for (SerenityCommand sc : allCommands) {
					if (sc.command.contains(searchTerm.toLowerCase())) {
						thisPlayerCommandList.add(sc);
					}
				}

				String prev = FancyText.GenerateFancyText(
						"§5§lSerenity Commands §7(hover for info) §dSearch: §7"
								+ searchTerm, FancyText.RUN_COMMAND, "/help ",
						FancyText.SHOW_TEXT, "Hello!");
				sendRawPacket(p, prev);
				for (SerenityCommand sc : thisPlayerCommandList) {
					sendCommandHelp(sc, p);
				}

			}
			return true;
		}
		return false;
	}

	private void sendCommandHelp(SerenityCommand sc, Player p) {
		String ft = FancyText.GenerateFancyText("§a/" + sc.command + ": §7"
				+ sc.description, FancyText.SUGGEST_COMMAND, "/" + sc.command
				+ " ", FancyText.SHOW_TEXT, sc.longDescription + "\n\nUsage:\n"
				+ sc.example);
		sendRawPacket(p, ft);
	}

	private boolean move(CommandSender sender, String[] arg3) {
		if (sender instanceof Player && arg3.length > 0) {
			Player p = ((Player) sender);
			String server = arg3[0];
			sendTo(p, server);
			return true;
		}
		return false;
	}

	private void sendTo(Player p, String string) {
		SerenityPlayer sp = serenityPlayers.get(p.getUniqueId());

		if (sp.isPendingMove()) {
			p.sendMessage("§cBe patient...");
			return;
		}
		if (!p.isOnGround()) {
			p.sendMessage("§cYou cannot do that while flying!");
			return;
		}
		int monsterXZDist = 10;
		for (Entity e : p.getNearbyEntities(monsterXZDist, 6, monsterXZDist)) {
			if (e instanceof Monster) {
				p.sendMessage("§cYou cannot do that while monsters are near!");
				return;
			}
		}
		p.sendMessage("§aPreparing to move to §d" + string
				+ "§a!  Stand still for a bit");
		final Location startLoc = p.getLocation();
		final Player pf = p;
		sp.setPendingMove(true);
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			@Override
			public void run() {
				sp.setPendingMove(false);

				if (pf.getLocation().distance(startLoc) > .5) {
					p.sendMessage("§cYou did not stand still!");
				} else {
					ByteArrayDataOutput out = ByteStreams.newDataOutput();
					out.writeUTF("ConnectOther");
					out.writeUTF(pf.getName());
					out.writeUTF(string);
					Player player = Iterables.getFirst(
							Bukkit.getOnlinePlayers(), null);
					player.sendPluginMessage(global, "BungeeCord",
							out.toByteArray());
				}
			}
		}, 100L);
	}

	private boolean map(CommandSender sender, String[] arg3) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (p.getLocation().getWorld().getName().contains("world_nether")) {
				int x = (int) p.getLocation().getX() * 8;
				int z = (int) p.getLocation().getZ() * 8;

				String html = "http://serenity-mc.org/map/#map_world/0/10/" + x
						+ "/" + z + "/64";
				p.sendMessage("§3If you build a portal, you should exit near this area:  \n§6"
						+ html);
				return true;
			}

			if (p.getLocation().getWorld().getName().equals("world")) {
				int x = (int) p.getLocation().getX();
				int z = (int) p.getLocation().getZ();
				int y = (int) p.getLocation().getY();
				String html = "http://serenity-mc.org/map/#map_world/0/10/" + x
						+ "/" + z + "/" + y;
				p.sendMessage("§3You are here:  \n§6" + html);
				return true;
			}

			return true;
		}
		return false;
	}

	private boolean mytime(CommandSender sender, String[] arg3) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			sender.sendMessage(getHoursAndMinutes(p.getUniqueId()));
		}

		return true;
	}

	private boolean links(CommandSender sender, String[] arg3) {
		String s = "";
		for (String key : linksCfg.getConfig().getKeys(false)) {
			s += "§6" + key + ": §e" + linksCfg.getConfig().getString(key)
					+ "\n";
		}
		sender.sendMessage(s);
		return true;
	}

	private boolean text(CommandSender sender, String[] arg3) {

		String message = "";
		for (int i = 0; i < arg3.length; i++) {
			message += arg3[i] + " ";
		}
		if (sender instanceof Player) {
			SerenityPlayer sp = serenityPlayers.get(((Player) sender)
					.getUniqueId());

			if (sp.getMinutes() > 720) {
				if (arg3.length < 1) {
					return false;
				}

				if (sp.getLastText() != null) {
					Long now = System.currentTimeMillis();
					Long then = sp.getLastText();
					if (now - then < 10000) {
						sender.sendMessage("§cYou must wait to text Hal again");
						return true;
					}
				}

				sender.sendMessage("§4Attempting to send text message...");
				sendATextToHal(sender.getName(), message);
				sp.setLastText(System.currentTimeMillis());
				return true;
			} else {
				sender.sendMessage("§cYou can only text Hal after you've played for 12 hours");
				return true;
			}
		} else {
			sendATextToHal("Yourself", message);
			return true;
		}
	}

	private void sendATextToHal(String from, String message) {

		final String str = message;
		final String sendName = from;
		final String smtp = emailCfg.getConfig().getString("SMTP");
		final String emailFrom = emailCfg.getConfig().getString("From");
		final String emailTo = emailCfg.getConfig().getString("To");
		final String password = emailCfg.getConfig().getString("Password");

		Bukkit.getScheduler().runTaskAsynchronously(this, new Runnable() {
			@Override
			public void run() {
				SMTP.Email email = SMTP.createEmptyEmail();
				email.add("Content-Type", "text/html");
				email.from(sendName, emailFrom);
				email.to("Hal", emailTo);
				email.subject(sendName);
				email.body(str);

				SMTP.sendEmail(smtp, emailFrom, password, email, false);

				Bukkit.getScheduler().scheduleSyncDelayedTask(global,
						new Runnable() {
							@Override
							public void run() {
								for (Player p : Bukkit.getOnlinePlayers()) {
									if (p.getName().equals(sendName)) {
										p.sendMessage("§aText sent successfully: §2"
												+ str);
										return;
									}
								}
							}
						});
			}
		});
	}

	private boolean msg(CommandSender sender, String[] arg3) {
		if (arg3.length > 0) {
			if (arg3[0].equalsIgnoreCase(Secret.SOMEONESNAME)) {

				String s = "§r";

				if (sender instanceof Player) {
					Player p = (Player) sender;
					s += getChatColor(p.getUniqueId());
				}

				for (int i = 1; i < arg3.length; i++) {
					s += arg3[i] + " ";
				}

				sender.sendMessage("§oTo §6§o§l" + Secret.SOMEONESNAMEPROPER
						+ ": " + s);
				if (sender instanceof Player) {
					Player p = (Player) sender;
					if (arg3.length > 1) {
						if (arg3[1].equalsIgnoreCase("reset")) {
							podrickCfg.getConfig()
									.set(p.getDisplayName(), null);
							podrickCfg.saveConfig();
							podrickCfg.reloadConfig();
							p.sendMessage("§cYou reset the quest!  /msg "
									+ Secret.SOMEONESNAMEPROPER
									+ " again to start over");
							return true;
						}
					}
					askSomeone(p);
					return true;
				}
			}

			if (arg3[0].equalsIgnoreCase("read")
					|| arg3[0].equalsIgnoreCase("~")
					|| arg3[0].equalsIgnoreCase("^")) {
				readPrivateMessages(sender, arg3);
				return true;
			}
		}

		if (arg3.length > 1) {
			if (arg3[0].equals("r")) {
				if (sender instanceof Player) {
					SerenityPlayer sp = serenityPlayers.get(((Player) sender)
							.getUniqueId());
					if (sp.getLastToSendMessage() != null) {
						arg3[0] = sp.getLastToSendMessage();
						String s = "";
						for (int i = 0; i < arg3.length; i++) {
							s += arg3[i] + " ";
						}
						Bukkit.dispatchCommand(sender, "msg " + s);
						return true;
					}
					sender.sendMessage("§cNobody has sent you a message recently!");
					return true;
				}
			} else {
				return sendPrivateMessage(sender, arg3);
			}
		}
		return false;
	}

	private void readPrivateMessages(CommandSender sender, String[] arg3) {
		if (arg3[0].equalsIgnoreCase("read")) {
			if (sender instanceof Player) {
				SerenityPlayer sp = serenityPlayers.get(((Player) sender)
						.getUniqueId());
				Player p = (Player) sender;
				Collections.sort(sp.getOfflineMessages());
				for (OfflineMessage s : sp.getOfflineMessages()) {

					String json = s.constructString(getChatColor(s.getFrom()),
							serenityPlayers.get(s.getFrom()).getName());
					Packet packet = new PacketPlayOutChat(
							ChatSerializer.a(json));

					((CraftPlayer) p).getHandle().playerConnection
							.sendPacket(packet);
				}
			}
		}

		if (arg3[0].equalsIgnoreCase("~") && arg3.length > 1) {
			if (sender instanceof Player) {
				SerenityPlayer sp = serenityPlayers.get(((Player) sender)
						.getUniqueId());
				for (OfflineMessage s : sp.getOfflineMessages()) {
					if (s.getID() == Integer.parseInt(arg3[1])) {
						SerenityPlayer from = serenityPlayers.get(s.getFrom());
						sender.sendMessage("==§nMessage from "
								+ from.getChatColor() + "§n" + from.getName()
								+ "§r==\n \n" + "§r"
								+ s.getMessage().replace('`', '\'') + "\n ");
						String sql = "Update Messages set ReadStatus = 1 Where M_ID = "
								+ s.getID();
						s.setRead(true);
						String del = FancyText.GenerateFancyText("§4(delete)",
								FancyText.RUN_COMMAND, "/msg ^ " + s.getID(),
								FancyText.SHOW_TEXT, "Click to delete");
						String more = FancyText.GenerateFancyText(
								"  §6(all messages)", FancyText.RUN_COMMAND,
								"/msg read", FancyText.SHOW_TEXT, "/msg read");
						String reply = FancyText.GenerateFancyText(
								"  §2(reply)", FancyText.SUGGEST_COMMAND,
								"/msg "
										+ serenityPlayers.get(s.getFrom())
												.getName() + " ",
								FancyText.SHOW_TEXT, "/msg "
										+ serenityPlayers.get(s.getFrom())
												.getName());
						sendRawPacket(((Player) sender).getPlayer(), "[" + del
								+ "," + more + "," + reply + "]");
						executeSQLAsync(sql);
						return;
					}
				}
			}
		}

		if (arg3[0].equalsIgnoreCase("^") && arg3.length > 1) {
			if (sender instanceof Player) {
				SerenityPlayer sp = serenityPlayers.get(((Player) sender)
						.getUniqueId());
				for (OfflineMessage s : sp.getOfflineMessages()) {
					if (s.getID() == Integer.parseInt(arg3[1])) {
						sender.sendMessage("§cMessage deleted");
						String sql = "Delete From Messages Where M_ID = "
								+ s.getID();
						sp.getOfflineMessages().remove(s);
						executeSQLAsync(sql);
						Bukkit.dispatchCommand(sender, "msg read");
						return;
					}
				}
			}
		}
	}

	@EventHandler
	public void onHorseDismountEvent(VehicleExitEvent event) {
		if (event.getExited() instanceof Player) {
			Player p = (Player) event.getExited();

			if (event.getVehicle().getType() == EntityType.HORSE
					|| event.getVehicle().getType() == EntityType.PIG
					|| event.getVehicle().getType() == EntityType.BOAT) {
				for (ProtectedArea pa : areas) {
					if (pa.equals(p.getLocation())) {
						if (pa.owner.contains("ayground")) {
							return;
						}
						if (!pa.hasPermission(p.getDisplayName())) {
							event.setCancelled(true);
							p.sendMessage("§cSorry, you can't dismount in a protected area of which you don't have permission");
						}
					}
				}
			}
		}
	}

	private boolean sendPrivateMessage(CommandSender sender, String[] arg3) {

		String name = arg3[0];
		String message = "";
		for (int i = 1; i < arg3.length; i++) {
			message += arg3[i] + " ";
		}

		if (name.equalsIgnoreCase("Server")) {
			String fromColor = "";
			if (sender instanceof Player) {
				fromColor = serenityPlayers
						.get(((Player) sender).getUniqueId()).getChatColor();
			}
			sender.sendMessage(fromColor + "§o" + "To [Server]: " + message);
			getLogger().info(
					"§cMsg from " + sender.getName() + ": §4" + message);
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (p.isOp()) {
					p.sendMessage("§c§oFrom " + sender.getName() + message);
				}
			}
			return true;
		}

		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.getName().toUpperCase().contains(arg3[0].toUpperCase())) {
				String senderName = "";
				String recipient = p.getDisplayName();
				if (sender instanceof Player) {
					message += getChatColor(((Player) sender).getUniqueId());
					senderName = ((Player) sender).getDisplayName();
				} else {
					senderName = "[Server]";
				}

				if (sender instanceof ConsoleCommandSender) {
					serenityPlayers.get(p.getUniqueId()).setLastToSendMessage(
							"Server");
				} else {
					serenityPlayers.get(p.getUniqueId()).setLastToSendMessage(
							sender.getName());
				}

				String fromColor = "";

				if (sender instanceof Player) {
					fromColor = serenityPlayers.get(
							((Player) sender).getUniqueId()).getChatColor();
				}

				p.sendMessage(fromColor + "§o" + "From " + senderName + ": "
						+ message);
				sender.sendMessage(fromColor + "§o" + "To " + recipient + ": "
						+ message);

				return true;
			}
		}

		OfflineMessage om = new OfflineMessage();
		om.setMessage(message.replace('\'', '`'));
		om.setTime(System.currentTimeMillis());
		om.setRead(false);

		for (Map.Entry<UUID, SerenityPlayer> entry : serenityPlayers.entrySet()) {
			if (entry.getValue().getName().toUpperCase()
					.equals(name.toUpperCase())) {
				om.setTo(entry.getValue().getUUID());
			}
		}

		if (om.getTo() == null) {
			return false;
		}

		boolean wasConsole = false;
		if (sender instanceof Player) {
			UUID uid = ((Player) sender).getUniqueId();
			om.setFrom(uid);
		} else {
			wasConsole = true;
			for (Map.Entry<UUID, SerenityPlayer> entry : serenityPlayers
					.entrySet()) {
				if (entry.getValue().isOp()) {
					om.setFrom(entry.getValue().getUUID());
				}
			}
		}

		final boolean wasConsolef = wasConsole;
		final OfflineMessage omf = om;
		Bukkit.getScheduler().runTaskLaterAsynchronously(this, new Runnable() {

			@Override
			public void run() {
				String sql = "INSERT INTO Messages (FromUUID, ToUUID, Message, ReadStatus, Time) VALUES ('"
						+ omf.getFrom().toString()
						+ "','"
						+ omf.getTo().toString()
						+ "','"
						+ omf.getMessage()
						+ "'," + "0," + omf.getTime() + ");";
				executeSQLBlocking(sql);
				sql = "Select * From Messages Where Time = " + omf.getTime()
						+ " AND Message = '" + omf.getMessage() + "';";
				queryAndAddTheOM(sql, omf, wasConsolef);
			}
		}, 0L);

		return true;
	}

	protected void queryAndAddTheOM(String sql, OfflineMessage omf,
			boolean wasConsole) {
		try {
			Connection conn = getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("M_ID");
				omf.setID(id);
				serenityPlayers.get(omf.getTo()).getOfflineMessages().add(omf);

				if (!wasConsole) {
					Bukkit.getPlayer(omf.getFrom())
							.sendMessage(
									"§aSent! §2"
											+ serenityPlayers.get(omf.getTo())
													.getName()
											+ " §acan read your message when he/she logs in!");
				} else {
					getLogger().info(
							"Sent to "
									+ serenityPlayers.get(omf.getTo())
											.getName());
				}
			}
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return;
	}

	private boolean MsgViaText(String rec, String msg) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.getName().toUpperCase().contains(rec.toUpperCase())) {
				p.sendMessage("§d§oText from Hal §7(reply with /text): §r§d"
						+ msg);
				sendATextToHal("Received", p.getName() + " received the text!");
				return true;
			}
		}

		return true;
	}

	private void givePlayerARandomSkull(Player p) {
		int r = rand.nextInt(allHolidaySkulls.size());
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "give " + p.getName()
				+ " " + String.format(allHolidaySkulls.get(r), p.getName()));
		p.sendMessage("§6§lHappy Holidays!");
	}

	public void putBoneInMailbox(UUID uid) {
		for (Mailbox mb : mailBoxes) {
			if (mb.uuid.equals(uid)) {
				Chest receivingChest = (Chest) mb.location.getBlock()
						.getState();

				ItemStack bone = new ItemStack(Material.COOKIE, 1);
				ItemMeta boneMeta = bone.getItemMeta();
				boneMeta.setDisplayName("§dHoliday Cookie");
				boneMeta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 2015, true);
				bone.setItemMeta(boneMeta);
				receivingChest.getInventory().addItem(bone);
				doRandomFirework(receivingChest.getWorld(),
						receivingChest.getLocation());
			}
		}
	}

	public void putRewardHeadInMailbox(UUID uid, int count) {
		for (Mailbox mb : mailBoxes) {
			if (mb.uuid.equals(uid)) {
				Chest receivingChest = (Chest) mb.location.getBlock()
						.getState();

				ItemStack rewardItem = new ItemStack(Material.DEAD_BUSH, count);
				ItemMeta rewardMeta = rewardItem.getItemMeta();
				rewardMeta.setDisplayName("§dHead Reward!");
				rewardMeta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 1, true);
				rewardItem.setItemMeta(rewardMeta);
				receivingChest.getInventory().addItem(rewardItem);
				doRandomFirework(receivingChest.getWorld(),
						receivingChest.getLocation());
			}
		}
	}

	@EventHandler
	public void onPlayerRedeemHead(PlayerInteractEvent event) {
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)
				|| event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
			if (event.getPlayer().getItemInHand().getType() == Material.DEAD_BUSH) {
				if (event.getPlayer().getItemInHand().hasItemMeta()) {
					if (event.getPlayer().getItemInHand().getItemMeta()
							.hasDisplayName()) {
						if (event.getPlayer().getItemInHand().getItemMeta()
								.getDisplayName().equals("§dHead Reward!")) {
							Player player = event.getPlayer();
							event.setCancelled(true);
							if (player.getItemInHand().getAmount() != 1) {
								player.getItemInHand().setAmount(
										player.getItemInHand().getAmount() - 1);
							} else {
								player.setItemInHand(new ItemStack(Material.AIR));
							}
							givePlayerARandomHead(player);
							player.getWorld().playSound(player.getLocation(),
									Sound.LEVEL_UP, 10F, .025F);
							ParticleEffect.SPELL_MOB.display(.5f, .5f, .5f, 50,
									60, player.getLocation(), 25);
						}
					}
				}
			}
		}
	}

	private void givePlayerARandomHead(Player player) {
		List<String> temp = new ArrayList<String>(rewardHeads.keySet());
		String key = temp.get(rand.nextInt(temp.size()));
		String name = rewardHeads.get(key);
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
				String.format(key, player.getName()));
		player.sendMessage("§aYou got §d" + name);
	}

	@EventHandler
	public void onPlayerRedeemBone(PlayerInteractEvent event) {
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)
				|| event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
			if (event.getPlayer().getItemInHand().getType() == Material.COOKIE) {
				if (event.getPlayer().getItemInHand().hasItemMeta()) {
					if (event.getPlayer().getItemInHand().getItemMeta()
							.hasDisplayName()) {
						if (event.getPlayer().getItemInHand().getItemMeta()
								.getDisplayName().equals("§dHoliday Cookie")) {
							Player player = event.getPlayer();
							if (Math.abs(player.getLocation().getX()) < 150
									&& Math.abs(player.getLocation().getZ()) < 150) {

								event.setCancelled(true);
								if (player.getItemInHand().getAmount() != 1) {
									player.getItemInHand()
											.setAmount(
													player.getItemInHand()
															.getAmount() - 1);
								} else {
									player.setItemInHand(new ItemStack(
											Material.AIR));
								}
								givePlayerARandomSkull(player);
								player.getWorld().playSound(
										player.getLocation(), Sound.LEVEL_UP,
										10F, .025F);
								ParticleEffect.SPELL_MOB.display(.5f, .5f, .5f,
										50, 60, player.getLocation(), 25);
							} else {
								player.sendMessage("§cYou can only redeem a §dHoliday Cookie §cat spawn!");
								event.setCancelled(true);
							}
						}
					}
				}
			}
		}
	}

	/*
	 * private boolean language(CommandSender sender, String[] arg3) { if
	 * (arg3.length == 0) { return false; }
	 * 
	 * if (arg3[0].equalsIgnoreCase("dutch")) { if
	 * (!dutchSpeakers.contains(sender.getName())) {
	 * dutchSpeakers.add(sender.getName()); String path = "Language.Dutch";
	 * 
	 * String[] loc = new String[dutchSpeakers.size()]; for (int i = 0; i <
	 * dutchSpeakers.size(); i++) { loc[i] = dutchSpeakers.get(i); }
	 * 
	 * languagesCfg.getConfig().set(path, loc); languagesCfg.saveConfig();
	 * languagesCfg.reloadConfig();
	 * sender.sendMessage("Je zal nu plugin berichten in het Nederlands te zien"
	 * );
	 * 
	 * return true; } else {
	 * sender.sendMessage("U bent al in het Nederlands -modus"); return true; }
	 * }
	 * 
	 * if (arg3[0].equalsIgnoreCase("english")) { if
	 * (dutchSpeakers.contains(sender.getName())) {
	 * dutchSpeakers.remove(sender.getName()); String path = "Language.Dutch";
	 * 
	 * String[] loc = new String[dutchSpeakers.size()]; for (int i = 0; i <
	 * dutchSpeakers.size(); i++) { loc[i] = dutchSpeakers.get(i); }
	 * 
	 * languagesCfg.getConfig().set(path, loc);
	 * sender.sendMessage("You will now see plugin messages in English");
	 * languagesCfg.saveConfig(); languagesCfg.reloadConfig();
	 * 
	 * return true; } else {
	 * sender.sendMessage("You are already in English mode"); return true; } }
	 * 
	 * sender.sendMessage(
	 * "§cOnly available languages are English and Dutch... for now.  Go on the forums to help translate!"
	 * ); return true; }
	 */

	private boolean chat(CommandSender sender, String[] arg3) {
		if (arg3.length == 1) {
			if (arg3[0].equals("tp")) {
				doRandomTeleport(sender);
				return true;
			}

			if (arg3[0].equalsIgnoreCase("celebrate")) {
				celebrate(sender);
				return true;
			}
		}

		if (arg3.length == 1) {
			if (arg3[0].equalsIgnoreCase("on")) {
				return enableLocalChat(sender, arg3);
			}

			if (arg3[0].equalsIgnoreCase("off")) {
				return enableGlobalChat(sender, arg3);
			}

			if (arg3[0].equalsIgnoreCase("g")) {
				return globalChat(sender, arg3);
			}

		}

		if (arg3.length > 1) {
			if (arg3[0].equalsIgnoreCase("g")) {
				return globalChat(sender, arg3);
			}
		}

		String msg = getTranslationLanguage(sender,
				stringKeys.CHATHELP.toString());
		sender.sendMessage(msg);
		return true;

	}

	private boolean globalChat(CommandSender sender, String[] arg3) {
		if (sender instanceof Player) {
			SerenityPlayer sp = serenityPlayers.get(((Player) sender)
					.getUniqueId());

			if (sp.isMuted()) {
				return true;
			}

			String txt = "";

			for (int i = 1; i < arg3.length; i++) {
				txt += (arg3[i] + " ");
			}

			String message = "<" + getChatColor(sp.getUUID()) + sp.getName()
					+ "§r" + "> " + getChatColor(sp.getUUID()) + txt;

			List<Player> rec = new ArrayList<Player>();

			for (Player play : Bukkit.getOnlinePlayers()) {
				rec.add(play);
			}

			for (int i = 0; i < ignorers.size(); i++) {
				if (ignorers.get(i).ignoreList.contains(sp.getName())) {
					rec.remove(ignorers.get(i).player);
				}
			}

			for (Player plr : rec) {
				plr.sendMessage(message);
			}
			return true;
		}
		return false;
	}

	private boolean enableGlobalChat(CommandSender sender, String[] arg3) {
		if (sender instanceof Player) {
			SerenityPlayer sp = serenityPlayers.get(((Player) sender)
					.getUniqueId());
			sp.setLocalChatting(false);
			String msg = getTranslationLanguage(sender,
					stringKeys.CHATPUBLICNOW.toString());
			sender.sendMessage(msg);
			return true;
		}
		return false;
	}

	private boolean enableLocalChat(CommandSender sender, String[] arg3) {
		if (sender instanceof Player) {
			SerenityPlayer sp = serenityPlayers.get(((Player) sender)
					.getUniqueId());
			sp.setLocalChatting(true);
			String msg = getTranslationLanguage(sender,
					stringKeys.CHATLOCALNOW.toString());
			sender.sendMessage(msg);
			return true;
		}
		return false;
	}

	private void celebrate(CommandSender sender) {
		if (sender instanceof Player) {
			sender.sendMessage("§cIt's not time to celebrate");
			/*
			 * SerenityPlayer sp = serenityPlayers.get(((Player) sender)
			 * .getUniqueId()); sp.setCelebrating(!sp.isCelebrating()); if
			 * (sp.isCelebrating()) { sender.sendMessage(
			 * "§dHappy Holidays!  §7Left and right click with a §6dandelion!");
			 * } else { sender.sendMessage("§7You are no longer celebrating"); }
			 */
		}
	}

	/*
	 * private void celebrate(CommandSender sender) {
	 * 
	 * Date anniversaryMin = new Date(); Date anniversaryMax = new Date(); Date
	 * canadaDayMin = new Date(); Date independenceDayMin = new Date(); Date
	 * halloweenMin = new Date(); Date halloweenMax = new Date(); try {
	 * anniversaryMin = psdf.parse("06/14"); anniversaryMax =
	 * psdf.parse("06/22"); canadaDayMin = psdf.parse("07/01");
	 * independenceDayMin = psdf.parse("07/04"); halloweenMin =
	 * psdf.parse("10/24"); halloweenMax = psdf.parse("11/01"); } catch
	 * (ParseException e) { }
	 * 
	 * Date now = new Date(); anniversaryMin.setYear(now.getYear());
	 * anniversaryMax.setYear(now.getYear());
	 * canadaDayMin.setYear(now.getYear());
	 * independenceDayMin.setYear(now.getYear());
	 * halloweenMin.setYear(now.getYear()); halloweenMax.setYear(now.getYear());
	 * 
	 * if (isDateBetween(now, anniversaryMin, anniversaryMax)) { if (sender
	 * instanceof Player) { Player p = (Player) sender; if
	 * (celebrators.keySet().contains(p.getName())) {
	 * celebrators.remove(p.getName());
	 * p.sendMessage("§6You are no longer celebrating"); return; }
	 * celebrators.put(p.getName(), (short) 11); p.sendMessage(
	 * "§bHappy Anniversary to §3Serenity!\n§7(Right and left click while holding a §edandelion§7)"
	 * ); return; } } else if (isDateBetween(now, canadaDayMin, canadaDayMin)) {
	 * if (sender instanceof Player) { Player p = (Player) sender; if
	 * (celebrators.keySet().contains(p.getName())) {
	 * celebrators.remove(p.getName());
	 * p.sendMessage("§6You are no longer celebrating"); return; }
	 * celebrators.put(p.getName(), (short) 11); p.sendMessage(
	 * "§4Happy §fCanada §4Day!\n§7(Right and left click while holding a §edandelion§7)"
	 * ); return; } } else if (isDateBetween(now, independenceDayMin,
	 * independenceDayMin)) { if (sender instanceof Player) { Player p =
	 * (Player) sender; if (celebrators.keySet().contains(p.getName())) {
	 * celebrators.remove(p.getName());
	 * p.sendMessage("§6You are no longer celebrating"); return; }
	 * celebrators.put(p.getName(), (short) 11); p.sendMessage(
	 * "§4Happy §fUS §fIndependence §9Day!\n§7(Right and left click while holding a §edandelion§7)"
	 * ); return; } } else if (isDateBetween(now, halloweenMin, halloweenMax)) {
	 * if (sender instanceof Player) { Player p = (Player) sender; if
	 * (celebrators.keySet().contains(p.getName())) {
	 * celebrators.remove(p.getName());
	 * p.sendMessage("§6You are no longer celebrating"); return; }
	 * celebrators.put(p.getName(), (short) 11); p.sendMessage(
	 * "§6Happy §4Halloween!\n§7(Right and left click while holding a §edandelion§7)"
	 * ); return; } } else {
	 * sender.sendMessage("§cIt's not time to celebrate yet..."); return; } }
	 */

	private void doRandomTeleport(CommandSender sender) {

		if (sender instanceof Player) {
			SerenityPlayer sp = serenityPlayers.get(((Player) sender)
					.getUniqueId());
			Location l = Bukkit.getPlayer(sender.getName()).getLocation();

			if (l.getWorld().getName().equalsIgnoreCase("world")) {
				if (Math.abs(l.getX()) < 50 && Math.abs(l.getZ()) < 50) {
					boolean ready = true;
					if (sp.getLastRandomTP() != null) {

						if (System.currentTimeMillis() - sp.getLastRandomTP() < 1800000) {
							ready = false;
						}

					}
					if (ready) {
						Random rand = new Random();
						Location teleLoc;

						boolean safe = false;
						do {
							teleLoc = new Location(Bukkit.getWorld("world"),
									(double) rand.nextInt(15000) - 7500, 5.0,
									(double) rand.nextInt(15000) - 7500);

							if (teleLoc.getWorld().getHighestBlockAt(teleLoc)
									.getRelative(BlockFace.DOWN).getType() != Material.WATER
									&& teleLoc.getWorld()
											.getHighestBlockAt(teleLoc)
											.getRelative(BlockFace.DOWN)
											.getType() != Material.STATIONARY_WATER) {
								boolean isprotected = false;
								for (ProtectedArea pa : areas) {
									if (pa.equals(teleLoc)) {
										isprotected = true;
									}
								}
								if (!isprotected) {
									safe = true;
								}
							}
						} while (!safe);

						teleLoc.setY(teleLoc.getWorld().getHighestBlockYAt(
								teleLoc) + 1);
						Bukkit.getPlayer(sender.getName()).teleport(teleLoc);

						sp.setLastRandomTP(System.currentTimeMillis());
						return;
					} else {

						String msg = getTranslationLanguage(sender,
								stringKeys.RANDOMTPWAIT.toString());
						sender.sendMessage(msg);
						// sender.sendMessage("§cYou must wait to use that again!");
						return;
					}
				}

				String msg = getTranslationLanguage(sender,
						stringKeys.RANDOMTPTOOFAR.toString());
				sender.sendMessage(msg);
				// sender.sendMessage("§cYou can only do this at spawn!");
				return;
			} else {
				String msg = getTranslationLanguage(sender,
						stringKeys.RANDOMTPWRONGWORLD.toString());
				sender.sendMessage(msg);

				// sender.sendMessage("§cYou can only do this in the overworld!");
				return;
			}
		}

	}

	private boolean chunk(CommandSender sender, String[] arg3) {
		if (arg3.length > 0) {
			if (arg3[0].equalsIgnoreCase("claim")) {

				Player p = Bukkit.getPlayer(sender.getName());

				if (getPlayerMinutes(p.getUniqueId()) < 60) {
					p.sendMessage("§cYou cannot claim an area yet!  Stick around");
					return true;
				}

				boolean alreadyPrepped = false;
				for (PlayerProtect pp : preppedToProtectArea) {
					if (pp.player.equals(p)) {
						alreadyPrepped = true;
					}
				}
				if (alreadyPrepped) {
					String msg = getTranslationLanguage(p,
							stringKeys.PROTPREPPEDTOPROTECT.toString());
					p.sendMessage(msg);

					// p.sendMessage("§2You are already prepped to protect!  Right click somewhere");
					return true;
				}

				if (arg3.length == 1) {
					preppedToProtectArea.add(new PlayerProtect(Bukkit
							.getPlayer(sender.getName()), 0));

					String msg = getTranslationLanguage(sender,
							stringKeys.PROTPREPPEDTOPROTECT.toString());
					sender.sendMessage(msg);

					// sender.sendMessage("§2You are ready to claim an area!  Right click your first corner");
					return true;
				} else {
					int length = -1;
					if (arg3.length > 1) {
						try {
							length = Integer.parseInt(arg3[1]);
						} catch (Exception e) {
							// sender.sendMessage("§cThe length must be an integer value!  §3Ex:  /protect claim 5");

							String msg = getTranslationLanguage(sender,
									stringKeys.PROTAREABADARG.toString());
							sender.sendMessage(msg);

							return true;
						}
					}

					if (length == -1) {
						return false;
					}
					if (length < 5) {

						String msg = getTranslationLanguage(sender,
								stringKeys.PROTAREATOOSMALL.toString());
						sender.sendMessage(msg);

						// sender.sendMessage("§cThe minimum size is 5! (11x11 blocks)");
						return true;
					}
					if (length >= 75) {

						String msg = getTranslationLanguage(sender,
								stringKeys.PROTAREATOOBIG.toString());
						sender.sendMessage(msg);

						// sender.sendMessage("§cThat is too much!");
						return true;
					}

					preppedToProtectArea.add(new PlayerProtect(Bukkit
							.getPlayer(sender.getName()), length));

					String msg = getTranslationLanguage(sender,
							stringKeys.PROTAREAREADY.toString());
					sender.sendMessage(msg);

					// sender.sendMessage("§2You are ready to claim an area!  Right click the center block");
					return true;

				}
			} else if (arg3[0].equalsIgnoreCase("unclaim")) {
				if (!preppedToUnProtectChunk.contains(sender.getServer()
						.getPlayer(sender.getName()))) {
					preppedToUnProtectChunk.add(Bukkit.getServer().getPlayer(
							sender.getName()));

					String msg = getTranslationLanguage(sender,
							stringKeys.PROTUNCLAIMPREPPED.toString());
					sender.sendMessage(msg);

					// sender.sendMessage("§2You are ready to unclaim an area!  Right click on a block to unclaim its area.");
					return true;
				} else {

					String msg = getTranslationLanguage(sender,
							stringKeys.PROTUNCLAIMSTILLPREPPED.toString());
					sender.sendMessage(msg);

					// sender.sendMessage("§2You are still ready to unclaim an area... Right click on a block.");
					return true;
				}

			}

			else if (arg3[0].equalsIgnoreCase("trustlist")) {
				if (blocksClaimed(Bukkit.getServer()
						.getPlayer(sender.getName()).getDisplayName()) == 0) {

					String msg = getTranslationLanguage(sender,
							stringKeys.PROTNOAREAS.toString());
					sender.sendMessage(msg);

					// sender.sendMessage("§cYou don't own any areas!");
					return true;
				}
				for (ProtectedArea pa : areas) {
					if (pa.owner.equals(Bukkit.getServer()
							.getPlayer(sender.getName()).getDisplayName())) {
						/*
						 * sender.sendMessage("§3Trustlist: \n" +
						 * pa.trustList());
						 */

						String msg = getTranslationLanguage(sender,
								stringKeys.PROTTRUSTLIST.toString())
								+ "\n"
								+ pa.trustList();
						sender.sendMessage(msg);

						return true;
					}
				}
			}

			else if (arg3[0].equalsIgnoreCase("list")) {
				if (blocksClaimed(Bukkit.getServer()
						.getPlayer(sender.getName()).getDisplayName()) == 0) {

					String msg = getTranslationLanguage(sender,
							stringKeys.PROTNOAREAS.toString());
					sender.sendMessage(msg);

					// sender.sendMessage("§cYou don't own any areas!");
					return true;
				}

				String msg = getTranslationLanguage(sender,
						stringKeys.PROTAREALIST.toString());
				sender.sendMessage(msg);

				// sender.sendMessage("§3Your area list:");

				for (ProtectedArea pa : areas) {
					if (pa.owner.equals(Bukkit.getServer()
							.getPlayer(sender.getName()).getDisplayName())) {
						sender.sendMessage(" - §3 X: " + pa.location1.getX()
								+ " Z: " + pa.location1.getZ() + " ("
								+ pa.xDiff() + "x" + pa.zDiff() + " = "
								+ pa.area() + " blocks)");
					}
				}
				UUID uuid = Bukkit.getServer().getPlayer(sender.getName())
						.getUniqueId();
				String name = Bukkit.getServer().getPlayer(sender.getName())
						.getDisplayName();

				String message = getTranslationLanguage(sender,
						stringKeys.PROTEXTENDEDLISTDATA.toString());

				sender.sendMessage(String.format(
						message,
						blocksAllowed(uuid),
						blocksClaimed(name),
						(blocksAllowed(uuid) - blocksClaimed(name)),
						(int) Math
								.sqrt((blocksAllowed(uuid) - blocksClaimed(name))),
						(int) Math
								.sqrt((blocksAllowed(uuid) - blocksClaimed(name)))));

				return true;
			} else if (arg3[0].equalsIgnoreCase("cri")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;

					final Player pf = p;

					for (int i = 0; i < 5; i++) {
						Bukkit.getScheduler().scheduleSyncDelayedTask(this,
								new Runnable() {
									@Override
									public void run() {
										ParticleEffect.WATER_SPLASH.display(
												.15F, .025F, .15F, .001F, 25,
												pf.getEyeLocation(), 50.0);
									}
								}, i * 10L);
					}

					return true;
				}
			}

			else if (arg3[0].equalsIgnoreCase("stuck")) {
				racers.remove(sender.getName());
				Player p = Bukkit.getPlayer(sender.getName());
				Location l = Bukkit.getPlayer(sender.getName()).getLocation();
				getLogger().info("from: " + l);
				for (ProtectedArea pa : areas) {
					if (pa.equals(l)) {
						if (pa.owner.contains("secret1")) {
							if (p.getLocation().getY() < 25) {
								if (aBattleIsRaging) {
									sender.sendMessage("§cNice try");
								} else {

								}
								return true;
							}
						}
						if (!pa.hasPermission(p.getDisplayName())) {
							while (true) {
								l.setX(l.getX() + 5);
								boolean safe = true;
								for (ProtectedArea pcs : areas) {
									if (pcs.equals(l)) {
										safe = false;
									}
								}
								if (safe) {
									getLogger().info(
											"to: "
													+ l.getWorld()
															.getHighestBlockAt(
																	l)
															.getLocation());

									p.teleport(l.getWorld()
											.getHighestBlockAt(l).getLocation());
									return true;
								}
							}
						} else {
							String msg = getTranslationLanguage(sender,
									stringKeys.PROTSTUCKABUSEATTEMPT.toString());
							sender.sendMessage(msg);

							// sender.sendMessage("§cYou can only use this where you don't have build permission");
							return true;
						}
					}
				}

				String msg = getTranslationLanguage(sender,
						stringKeys.PROTSTUCKABUSEATTEMPTNOTEVENPROT.toString());
				sender.sendMessage(msg);

				// sender.sendMessage("§cYou can only use this in a protected area");
				return true;
			}
		}
		if (arg3.length > 1) {
			if (arg3[0].equalsIgnoreCase("trust")) {
				if (blocksClaimed(Bukkit.getServer()
						.getPlayer(sender.getName()).getDisplayName()) == 0) {
					// sender.sendMessage("§cYou don't own any areas!");
					String msg = getTranslationLanguage(sender,
							stringKeys.PROTNOAREAS.toString());
					sender.sendMessage(msg);

					return true;
				}

				ArrayList<String> list = new ArrayList<String>();

				if (arg3[1].equalsIgnoreCase("doors")) {
					arg3[1] = "doors";
				}
				if (arg3[1].equalsIgnoreCase("buttons")) {
					arg3[1] = "buttons";
				}
				if (arg3[1].equalsIgnoreCase("plates")) {
					arg3[1] = "plates";
				}
				if (arg3[1].equalsIgnoreCase("chests")) {
					arg3[1] = "chests";
				}
				if (arg3[1].equalsIgnoreCase("levers")) {
					arg3[1] = "levers";
				}
				if (arg3[1].equalsIgnoreCase("gates")) {
					arg3[1] = "gates";
				}

				for (ProtectedArea pa : areas) {
					if (pa.owner.equals(Bukkit.getServer()
							.getPlayer(sender.getName()).getDisplayName())) {
						pa.addTrust(arg3[1]);
						list = pa.trustedPlayers;
					}
				}

				String path = "ProtectedAreas."
						+ Bukkit.getServer().getPlayer(sender.getName())
								.getDisplayName() + ".Trusts";

				String[] loc = new String[list.size()];
				for (int i = 0; i < list.size(); i++) {
					loc[i] = list.get(i);
				}

				protectedAreasCfg.getConfig().set(path, loc);
				protectedAreasCfg.saveConfig();
				protectedAreasCfg.reloadConfig();

				/*
				 * sender.sendMessage("§2" + arg3[1] +
				 * "§3 now has full permissions in all of your protected areas"
				 * );
				 */

				if (arg3[1].equalsIgnoreCase("doors")) {
					sender.sendMessage("§2Doors §3are now publicly clickable in all of your claims");
					return true;
				}
				if (arg3[1].equalsIgnoreCase("buttons")) {
					sender.sendMessage("§2Buttons §3are now publicly clickable in all of your claims");
					return true;
				}
				if (arg3[1].equalsIgnoreCase("plates")) {
					sender.sendMessage("§2Pressure plates §3are now public in all of your claims");
					return true;
				}
				if (arg3[1].equalsIgnoreCase("chests")) {
					sender.sendMessage("§2Chests, furnaces, brewing stands and hoppers §3are now public in all of your claims");
					return true;
				}
				if (arg3[1].equalsIgnoreCase("levers")) {
					sender.sendMessage("§2Levers §3are now public in all of your claims");
					return true;
				}
				if (arg3[1].equalsIgnoreCase("gates")) {
					sender.sendMessage("§2Fence gates §3are now public in all of your claims");
					return true;
				}

				String msg = getTranslationLanguage(sender,
						stringKeys.PROTADDEDTRUST.toString());
				sender.sendMessage(String.format(msg, arg3[1]));

				return true;

			} else if (arg3[0].equalsIgnoreCase("untrust")) {
				if (blocksClaimed(Bukkit.getServer()
						.getPlayer(sender.getName()).getDisplayName()) == 0) {

					String msg = getTranslationLanguage(sender,
							stringKeys.PROTNOAREAS.toString());
					sender.sendMessage(msg);

					// sender.sendMessage("§cYou don't own any areas!");
					return true;
				}

				if (arg3[1].equalsIgnoreCase("doors")) {
					arg3[1] = "doors";
				}
				if (arg3[1].equalsIgnoreCase("buttons")) {
					arg3[1] = "buttons";
				}
				if (arg3[1].equalsIgnoreCase("plates")) {
					arg3[1] = "plates";
				}
				if (arg3[1].equalsIgnoreCase("chests")) {
					arg3[1] = "chests";
				}
				if (arg3[1].equalsIgnoreCase("levers")) {
					arg3[1] = "levers";
				}
				if (arg3[1].equalsIgnoreCase("gates")) {
					arg3[1] = "gates";
				}

				if (arg3[1].equalsIgnoreCase("all")) {
					ArrayList<String> list = new ArrayList<String>();
					for (ProtectedArea pa : areas) {
						if (pa.owner.equals(Bukkit.getServer()
								.getPlayer(sender.getName()).getDisplayName())) {

							ArrayList<String> untrustList = new ArrayList<String>();
							for (String s : pa.trustedPlayers) {
								untrustList.add(s);
							}

							for (String s : untrustList) {
								pa.unTrust(s);
							}
						}
					}

					String path = "ProtectedAreas."
							+ Bukkit.getServer().getPlayer(sender.getName())
									.getDisplayName() + ".Trusts";

					String[] loc = new String[list.size()];
					for (int i = 0; i < list.size(); i++) {
						loc[i] = list.get(i);
					}

					protectedAreasCfg.getConfig().set(path, loc);
					protectedAreasCfg.saveConfig();
					protectedAreasCfg.reloadConfig();

					// sender.sendMessage("§c" +
					// "You no longer trust anybody");

					String msg = getTranslationLanguage(sender,
							stringKeys.PROTTRUSTISSUES.toString());
					sender.sendMessage(msg);
					return true;

				}

				ArrayList<String> list = new ArrayList<String>();
				boolean happened = false;
				for (ProtectedArea pa : areas) {
					if (pa.owner.equals(Bukkit.getServer()
							.getPlayer(sender.getName()).getDisplayName())) {
						if (pa.trustedPlayers.contains(arg3[1])) {
							happened = true;
							pa.unTrust(arg3[1]);
							list = pa.trustedPlayers;
						}
					}
				}

				if (!happened) {

					// sender.sendMessage("§cYou do not trust anybody named §2"
					// + arg3[1]);

					String msg = getTranslationLanguage(sender,
							stringKeys.PROTCANTFINDTRUST.toString());
					sender.sendMessage(String.format(msg, arg3[1]));
					return true;
				}

				String path = "ProtectedAreas."
						+ Bukkit.getServer().getPlayer(sender.getName())
								.getDisplayName() + ".Trusts";

				String[] loc = new String[list.size()];
				for (int i = 0; i < list.size(); i++) {
					loc[i] = list.get(i);
				}

				protectedAreasCfg.getConfig().set(path, loc);
				protectedAreasCfg.saveConfig();
				protectedAreasCfg.reloadConfig();

				/*
				 * sender.sendMessage("§2" + arg3[1] +
				 * "§3 no longer has permissions in any of your protected areas"
				 * );
				 */

				if (arg3[1].equalsIgnoreCase("doors")) {
					sender.sendMessage("§2Doors §3are now longer publicly clickable in your claims");
					return true;
				}
				if (arg3[1].equalsIgnoreCase("buttons")) {
					sender.sendMessage("§2Buttons §3are no longer publicly clickable in your claims");
					return true;
				}
				if (arg3[1].equalsIgnoreCase("plates")) {
					sender.sendMessage("§2Pressure plates §3are no longer public in your claims");
					return true;
				}
				if (arg3[1].equalsIgnoreCase("chests")) {
					sender.sendMessage("§2Chests, furnaces, brewing stands and hoppers §3are no longer public in your claims");
					return true;
				}
				if (arg3[1].equalsIgnoreCase("levers")) {
					sender.sendMessage("§2Levers §3are no longer public in your claims");
					return true;
				}
				if (arg3[1].equalsIgnoreCase("gates")) {
					sender.sendMessage("§2Fence gates §3are no longer public in your claims");
					return true;
				}

				String msg = getTranslationLanguage(sender,
						stringKeys.PROTUNTRUSTED.toString());
				sender.sendMessage(String.format(msg, arg3[1]));
				return true;
			}

		}

		String msg = getTranslationLanguage(sender,
				stringKeys.PROTHELP.toString());
		sender.sendMessage(msg);

		/*
		 * sender.sendMessage(
		 * "§2/protect claim §3to claim an area based on corners\n" +
		 * "§2/protect claim <number> §3to claim an area based on center block §eEx: §6/protect claim 5\n"
		 * + "§2/protect unclaim §3to unclaim an area\n" +
		 * "§2/protect trust <PlayerName> §3to trust another player (to all of your claims!)\n"
		 * +
		 * "§2/protect untrust <PlayerName> §3to remove that player's access to your claims\n"
		 * + "§2/protect trustlist <PlayerName> §3to see all trusted players\n"
		 * + "§2/protect list §3to list the locations of your claims\n" +
		 * "§eRight click anywhere with a §6stick §eto test a block's ownership! Make sure your entire build is protected!"
		 * );
		 */

		return true;
	}

	@EventHandler
	public void onBlockClick(PlayerInteractEvent event) {
		Player p = event.getPlayer();

		// todo!!!
		if (preppedToUnProtectChunk.contains(event.getPlayer())
				&& preppedToProtectArea.contains(event.getPlayer())) {
			preppedToUnProtectChunk.remove(event.getPlayer());
		}

		for (int i = 0; i < preppedToProtectArea.size(); i++) {
			if (preppedToProtectArea.get(i).player.equals(event.getPlayer())) {
				event.setCancelled(true);
				if (event.getPlayer().getLocation().getWorld().getName()
						.equalsIgnoreCase("world")) {
					if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
						World world = event.getPlayer().getWorld();
						Block target = event.getPlayer().getTargetBlock(
								(Set<Material>) null, MAX_DISTANCE);
						if (target.getLocation().distance(
								event.getPlayer().getLocation()) > 35) {

							String msg = getTranslationLanguage(
									event.getPlayer(),
									stringKeys.PROTTOOFAR.toString());
							event.getPlayer().sendMessage(msg);

							/*
							 * event.getPlayer() .sendMessage(
							 * "§cThat block is too far away.  Get closer before claiming it!"
							 * );
							 */
							preppedToProtectArea.remove(i);
							return;
						}

						for (ProtectedArea pa : areas) {
							if (pa.equals(target.getLocation())) {
								if (pa.owner.equals(Bukkit.getServer()
										.getPlayer(p.getName())
										.getDisplayName())) {
									// p.sendMessage("§cYou already own that area!");

									String msg = getTranslationLanguage(
											event.getPlayer(),
											stringKeys.PROTALREADYOWN
													.toString());
									event.getPlayer().sendMessage(msg);

									preppedToProtectArea.remove(i);
									return;
								}
								String msg = getTranslationLanguage(
										event.getPlayer(),
										stringKeys.PROTAREACLAIMED.toString());
								event.getPlayer().sendMessage(msg);

								// p.sendMessage("§cThat area is already claimed!");
								preppedToProtectArea.remove(i);
								return;
							}
						}

						ProtectedArea newProtArea = new ProtectedArea();
						newProtArea.owner = Bukkit.getServer()
								.getPlayer(p.getName()).getDisplayName();

						if (!preppedToProtectArea.get(i).isCenter()) {
							if (preppedToProtectArea.get(i).location1 == null) {
								preppedToProtectArea.get(i).setLoc1(
										target.getLocation());

								String msg = getTranslationLanguage(
										event.getPlayer(),
										stringKeys.PROTFIRSTCORNER.toString());
								event.getPlayer().sendMessage(msg);

								/*
								 * event.getPlayer() .sendMessage( "§a" +
								 * "First corner is set!  Now click the " + "§e"
								 * + "OPPOSITE " + "§a" +
								 * "corner of your area!");
								 */
								return;
							}
							if (preppedToProtectArea.get(i).location2 == null) {
								preppedToProtectArea.get(i).setLoc2(
										target.getLocation());

								newProtArea.location1 = preppedToProtectArea
										.get(i).location1;
								newProtArea.location2 = preppedToProtectArea
										.get(i).location2;
							}
						} else {

							Location location1 = new Location(
									target.getWorld(),
									target.getX()
											- preppedToProtectArea.get(i).length,
									1,
									target.getZ()
											- preppedToProtectArea.get(i).length);
							Location location2 = new Location(
									target.getWorld(),
									target.getX()
											+ preppedToProtectArea.get(i).length,
									1,
									target.getZ()
											+ preppedToProtectArea.get(i).length);

							newProtArea.location1 = location1;
							newProtArea.location2 = location2;

						}

						for (ProtectedArea pa : areas) {
							if (newProtArea.intersects(pa)) {
								pa.highlightArea();

								String msg = getTranslationLanguage(p,
										stringKeys.PROTINTERSECT.toString());
								msg = String.format(msg, "§6X: "
										+ (int) pa.location1.getX() + " Z: "
										+ (int) pa.location1.getZ());
								p.sendMessage(msg);

								// p.sendMessage("§cThat area intersects with another area!");*/
								preppedToProtectArea.remove(i);
								return;
							}
						}

						if (blocksAllowed(event.getPlayer().getUniqueId()) < blocksClaimed(event
								.getPlayer().getDisplayName())
								+ newProtArea.area()) {
							if (blocksAllowed(event.getPlayer().getUniqueId()) == 0) {

								String msg = getTranslationLanguage(
										event.getPlayer(),
										stringKeys.PROTPREMATURE.toString());
								event.getPlayer().sendMessage(msg);

								/*
								 * event.getPlayer().sendMessage(
								 * "§cYou can't claim an area yet!");
								 */
								return;
							}

							String msg = getTranslationLanguage(
									event.getPlayer(),
									stringKeys.PROTNOTYET.toString());
							event.getPlayer()
									.sendMessage(
											String.format(
													msg,
													(blocksAllowed(event
															.getPlayer()
															.getUniqueId()) - blocksClaimed(event
															.getPlayer()
															.getDisplayName())),
													newProtArea.area()));

							/*
							 * event.getPlayer() .sendMessage(
							 * "§cYou can't claim that many blocks yet!  \nYou can only claim §3"
							 * + (blocksAllowed(event .getPlayer()
							 * .getDisplayName()) - blocksClaimed(event
							 * .getPlayer() .getDisplayName())) +
							 * "§c more blocks\n" + "You just tried to claim §3"
							 * + newProtArea.area() + "§c blocks");
							 */
							preppedToProtectArea.remove(i);
							return;
						}

						if (newProtArea.xDiff() < 10
								|| newProtArea.zDiff() < 10) {

							String msg = getTranslationLanguage(
									event.getPlayer(),
									stringKeys.PROTAREATOOSMALL.toString());
							event.getPlayer().sendMessage(msg);
							/*
							 * event.getPlayer() .sendMessage(
							 * "§cThat area is too small!  Both length and width must be greater than 10"
							 * );
							 */
							preppedToProtectArea.remove(i);
							return;
						}

						if (newProtArea.zDiff() > 150
								|| newProtArea.xDiff() > 150) {
							String msg = getTranslationLanguage(
									event.getPlayer(),
									stringKeys.PROTAREATOOBIG.toString());
							event.getPlayer().sendMessage(msg);
							preppedToProtectArea.remove(i);
							return;
						}

						String path = "ProtectedAreas."
								+ Bukkit.getServer().getPlayer(p.getName())
										.getDisplayName() + "."
								+ newProtArea.getId();

						String[] loc = {
								newProtArea.location1.getWorld().getName(),
								"" + (int) newProtArea.location1.getX(),
								"" + (int) newProtArea.location1.getZ(),
								"" + (int) newProtArea.location2.getX(),
								"" + (int) newProtArea.location2.getZ() };

						protectedAreasCfg.getConfig().set(path, loc);
						protectedAreasCfg.saveConfig();
						protectedAreasCfg.reloadConfig();

						areas.add(newProtArea);
						addTrustsToChunks(Bukkit.getServer()
								.getPlayer(p.getName()).getDisplayName());
						this.getLogger().info(
								p.getDisplayName() + " protected an area: "
										+ target.getLocation().toString());

						String msg = getTranslationLanguage(event.getPlayer(),
								stringKeys.PROTSUCCESS.toString());
						event.getPlayer().sendMessage(msg);

						// p.sendMessage("§3You now own that area!  Right click with a §2stick §3to test an individual block for protection.  Make sure your entire build is protected");
						newProtArea.highlightArea();
						preppedToProtectArea.remove(i);
						// highlightChunk(target.getChunk());
						return;
					}
				} else {

					String msg = getTranslationLanguage(event.getPlayer(),
							stringKeys.PROTNOTOVERWORLD.toString());
					event.getPlayer().sendMessage(msg);

					/*
					 * event.getPlayer() .sendMessage(
					 * "§cYou can only protect stuff in the overworld... for now."
					 * );
					 */
					return;
				}
			}
		}

		if (preppedToUnProtectChunk.contains(event.getPlayer())) {
			event.setCancelled(true);
			if (event.getAction().equals(Action.RIGHT_CLICK_AIR)
					|| event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				World world = event.getPlayer().getWorld();
				Block target = event.getPlayer().getTargetBlock(
						(Set<Material>) null, MAX_DISTANCE);
				if (target.getLocation().distance(
						event.getPlayer().getLocation()) > 35) {

					String msg = getTranslationLanguage(event.getPlayer(),
							stringKeys.PROTTOOFARAWAYTOUNCLAIM.toString());
					event.getPlayer().sendMessage(msg);

					/*
					 * event.getPlayer() .sendMessage(
					 * "§cThat block is too far away.  Get closer before unclaiming it!"
					 * );
					 */
					preppedToUnProtectChunk.remove(event.getPlayer());
					return;
				}

				int i = 0;
				for (ProtectedArea pa : areas) {
					if (pa.equals(target.getLocation())) {
						if (pa.owner.equals(Bukkit.getServer()
								.getPlayer(p.getName()).getDisplayName())) {

							protectedAreasCfg.getConfig().set(
									"ProtectedAreas."
											+ Bukkit.getServer()
													.getPlayer(p.getName())
													.getDisplayName() + "."
											+ pa.getId(), null);

							// protectedAreasCfg.getConfig().options().copyDefaults();
							protectedAreasCfg.saveConfig();
							protectedAreasCfg.reloadConfig();

							areas.remove(i);
							this.getLogger().info(
									p.getDisplayName()
											+ " unprotected an area: "
											+ target.getLocation().toString());

							String msg = getTranslationLanguage(p,
									stringKeys.PROTUNCLAIMSUCCESS.toString());
							p.sendMessage(msg);

							// p.sendMessage("§cYou no longer own that area!  It is editable by anybody!");
							preppedToUnProtectChunk.remove(event.getPlayer());
							return;
						}

						String msg = getTranslationLanguage(event.getPlayer(),
								stringKeys.PROTUNCLAIMDOESNOTOWN.toString());
						event.getPlayer().sendMessage(msg);

						// p.sendMessage("§cYou do not own that area!");
						preppedToUnProtectChunk.remove(event.getPlayer());
						return;
					}
					i++;
				}
				preppedToUnProtectChunk.remove(event.getPlayer());

				String msg = getTranslationLanguage(p,
						stringKeys.PROTUNCLAIMNOBODYOWNS.toString());
				p.sendMessage(msg);

				// p.sendMessage("§cNobody owns that area!");
			}
		}
	}

	private int blocksClaimed(String playerName) {
		int i = 0;
		for (ProtectedArea pa : areas) {
			if (pa.owner.equals(playerName)) {
				i += pa.area();
			}
		}

		return i;
	}

	private int blocksAllowed(UUID uuid) {
		return (getPlayerMinutes(uuid) / 60) * 256;
	}

	@EventHandler
	private void onPlayerInteractEvent(PlayerInteractEvent event) {
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			for (Mailbox mb : mailBoxes) {
				if (event.getClickedBlock().getLocation().equals(mb.location)) {
					return;
				}
			}
		}

		if (event.getPlayer().getLocation().getWorld().getName()
				.contains("nether")) {
			if (!event.getPlayer().isOp()) {
				if (event.getPlayer().getLocation().getY() > 127) {
					event.setCancelled(true);
					return;
				}
			}
		}

		for (ProtectedArea pa : areas) {
			if (event.getClickedBlock() != null) {
				if (pa.equals(event.getClickedBlock().getLocation())) {
					if (!pa.hasPermission(event.getPlayer().getDisplayName())) {

						if (event.getClickedBlock().getType()
								.equals(Material.STONE_BUTTON)
								&& pa.owner.equalsIgnoreCase("playground")) {
							return;
						}

						if (event.getPlayer().getItemInHand().getType() == Material.BOAT
								&& pa.owner.equalsIgnoreCase("playground")
								&& event.getAction() == Action.RIGHT_CLICK_BLOCK) {
							return;
						}

						if (pa.trustedPlayers.contains("doors")
								&& event.getAction() == Action.RIGHT_CLICK_BLOCK) {
							if (isADoor(event.getClickedBlock().getType())) {
								return;
							}
						}

						if (pa.trustedPlayers.contains("gates")
								&& event.getAction() == Action.RIGHT_CLICK_BLOCK) {
							if (isAGate(event.getClickedBlock().getType())) {
								return;
							}
						}

						if (pa.trustedPlayers.contains("plates")
								&& event.getAction() == Action.PHYSICAL) {
							return;
						}

						if (pa.trustedPlayers.contains("buttons")
								&& event.getAction() == Action.RIGHT_CLICK_BLOCK) {
							if (event.getClickedBlock().getType() == Material.STONE_BUTTON
									|| event.getClickedBlock().getType() == Material.WOOD_BUTTON)
								return;
						}

						if (pa.trustedPlayers.contains("chests")
								&& event.getAction() == Action.RIGHT_CLICK_BLOCK) {
							if (event.getClickedBlock().getType() == Material.TRAPPED_CHEST
									|| event.getClickedBlock().getType() == Material.CHEST
									|| event.getClickedBlock().getType() == Material.FURNACE
									|| event.getClickedBlock().getType() == Material.BREWING_STAND
									|| event.getClickedBlock().getType() == Material.BURNING_FURNACE
									|| event.getClickedBlock().getType() == Material.HOPPER)
								return;
						}

						if (pa.trustedPlayers.contains("levers")
								&& event.getAction() == Action.RIGHT_CLICK_BLOCK) {
							if (event.getClickedBlock().getType() == Material.LEVER)
								return;
						}

						if (isHoldingFood(event.getPlayer().getItemInHand())) {
							event.setUseInteractedBlock(Result.DENY);
							return;
						}

						event.setCancelled(true);
						return;
					}
				}
			}
		}
	}

	private boolean isADoor(Material type) {
		if (type == Material.ACACIA_DOOR || type == Material.ACACIA_DOOR_ITEM
				|| type == Material.BIRCH_DOOR
				|| type == Material.BIRCH_DOOR_ITEM
				|| type == Material.DARK_OAK_DOOR
				|| type == Material.DARK_OAK_DOOR_ITEM
				|| type == Material.JUNGLE_DOOR
				|| type == Material.JUNGLE_DOOR_ITEM
				|| type == Material.TRAP_DOOR || type == Material.SPRUCE_DOOR
				|| type == Material.SPRUCE_DOOR_ITEM
				|| type == Material.WOODEN_DOOR)

			return true;

		return false;
	}

	private boolean isAGate(Material type) {
		if (type == Material.ACACIA_FENCE_GATE
				|| type == Material.BIRCH_FENCE_GATE
				|| type == Material.DARK_OAK_FENCE_GATE
				|| type == Material.FENCE_GATE
				|| type == Material.JUNGLE_FENCE_GATE
				|| type == Material.SPRUCE_FENCE_GATE
				|| type == Material.FENCE_GATE)
			return true;
		return false;
	}

	private boolean isHoldingFood(ItemStack itemInHand) {
		if (itemInHand.getType().isEdible())
			return true;
		if (itemInHand.getType() == Material.POTION)
			return true;

		return false;
	}

	@EventHandler
	private void onBlockPlaceEvent(BlockPlaceEvent event) {
		for (ProtectedArea pa : areas) {
			if (pa.equals(event.getBlock().getLocation())
					|| pa.equals(event.getBlockAgainst().getLocation())) {
				if (!pa.hasPermission(event.getPlayer().getDisplayName())) {
					event.setCancelled(true);
					return;
				}
			}
		}
	}

	@EventHandler
	private void onEnderPearlTeleport(PlayerTeleportEvent event) {
		if (event.getCause().equals(TeleportCause.ENDER_PEARL)) {
			for (ProtectedArea pa : areas) {
				if (pa.owner.equalsIgnoreCase("playground")
						&& pa.equals(event.getPlayer().getLocation())) {
					event.setCancelled(true);
					event.getPlayer().sendMessage("No cheating!");
					return;
				}
			}
		}

		// GolfPro's target thing
		if (event.getFrom().getWorld().getName().equals("world")) {
			if (event.getCause().equals(TeleportCause.ENDER_PEARL)) {
				double distance = event.getTo()
						.distance(
								new Location(event.getTo().getWorld(), 581.5,
										66, 402.5));
				if (distance < 25) {
					event.getPlayer().sendMessage(
							"§6" + distance + " meters from center");
					event.setCancelled(true);
					event.getPlayer().teleport(event.getTo());
					event.getPlayer().getInventory()
							.addItem(new ItemStack(Material.ENDER_PEARL));
				}
			}
		}
	}

	private void SendTranslateLine(String c, String string2, Player p,
			String command) {
		String hxp = FancyText.GenerateFancyText("  §a( + )",
				FancyText.RUN_COMMAND, command + " " + c + "x+",
				FancyText.SHOW_TEXT, "Move X+");
		String hxm = FancyText.GenerateFancyText("§c( - )",
				FancyText.RUN_COMMAND, command + " " + c + "x-",
				FancyText.SHOW_TEXT, "Move X-");
		String hyp = FancyText.GenerateFancyText("§a   ( + )",
				FancyText.RUN_COMMAND, command + " " + c + "y+",
				FancyText.SHOW_TEXT, "Move Y+");
		String hym = FancyText.GenerateFancyText("§c( - )",
				FancyText.RUN_COMMAND, command + " " + c + "y-",
				FancyText.SHOW_TEXT, "Move Y-");
		String hzp = FancyText.GenerateFancyText("§a   ( + )",
				FancyText.RUN_COMMAND, command + " " + c + "z+",
				FancyText.SHOW_TEXT, "Move Z+");
		String hzm = FancyText.GenerateFancyText("§c( - )",
				FancyText.RUN_COMMAND, command + " " + c + "z-",
				FancyText.SHOW_TEXT, "Move Z-");
		String head = FancyText.GenerateFancyText("    §7" + string2,
				FancyText.SHOW_TEXT, "", FancyText.SHOW_TEXT, string2);
		sendRawPacket(p, "[" + hxp + "," + hxm + "," + hyp + "," + hym + ","
				+ hzp + "," + hzm + "," + head + "]");

	}

	private void SendRotateLine(String c, String string, String string2,
			Player p, String command) {
		String hxp = FancyText.GenerateFancyText("  §a( + )",
				FancyText.RUN_COMMAND, command + " " + c + "x+",
				FancyText.SHOW_TEXT, "Rotate X+");
		String hxm = FancyText.GenerateFancyText("§c( - )",
				FancyText.RUN_COMMAND, command + " " + c + "x-",
				FancyText.SHOW_TEXT, "Rotate X-");
		String hyp = FancyText.GenerateFancyText("§a   ( + )",
				FancyText.RUN_COMMAND, command + " " + c + "y+",
				FancyText.SHOW_TEXT, "Rotate Y+");
		String hym = FancyText.GenerateFancyText("§c( - )",
				FancyText.RUN_COMMAND, command + " " + c + "y-",
				FancyText.SHOW_TEXT, "Rotate Y-");
		String hzp = FancyText.GenerateFancyText("§a   ( + )",
				FancyText.RUN_COMMAND, command + " " + c + "z+",
				FancyText.SHOW_TEXT, "Rotate Z+");
		String hzm = FancyText.GenerateFancyText("§c( - )",
				FancyText.RUN_COMMAND, command + " " + c + "z-",
				FancyText.SHOW_TEXT, "Rotate Z-");
		String head = FancyText.GenerateFancyText("    §7" + string,
				FancyText.RUN_COMMAND, command + " " + c + "r",
				FancyText.SHOW_TEXT, string2);
		sendRawPacket(p, "[" + hxp + "," + hxm + "," + hyp + "," + hym + ","
				+ hzp + "," + hzm + "," + head + "]");

	}

	private boolean armorStand(CommandSender sender, String[] arg3) {
		if (sender instanceof Player) {

			Player p = ((Player) sender).getPlayer();

			if (arg3.length == 0) {
				String command = "/as";

				String arms = FancyText.GenerateFancyText(
						"§d§lToggle: §7Arms§r | ", FancyText.RUN_COMMAND,
						command + " a", FancyText.SHOW_TEXT,
						"Adds or removes arms");
				String base = FancyText.GenerateFancyText("§7BasePlate§r | ",
						FancyText.RUN_COMMAND, command + " b",
						FancyText.SHOW_TEXT, "Adds or removes the baseplate");
				String grav = FancyText.GenerateFancyText("§7Gravity§r | ",
						FancyText.RUN_COMMAND, command + " g",
						FancyText.SHOW_TEXT,
						"Enables or disables gravity for this");
				String size = FancyText.GenerateFancyText("§7Size§r | ",
						FancyText.RUN_COMMAND, command + " s",
						FancyText.SHOW_TEXT, "Toggles large or small");
				String visi = FancyText.GenerateFancyText("§7Visible | ",
						FancyText.RUN_COMMAND, command + " v",
						FancyText.SHOW_TEXT, "Toggles visibility");

				String item = FancyText.GenerateFancyText("§7Item",
						FancyText.RUN_COMMAND, command + " i",
						FancyText.SHOW_TEXT,
						"Drops item in hand (if available)");
				sendRawPacket(p, "[" + arms + "," + base + "," + grav + ","
						+ size + "," + visi + "," + item + "]");
				p.sendMessage("§d§lRotation:");
				SendRotateLine(
						"h",
						"Head",
						"Rotate the head by clicking the preceding items.  Click here to reset the rotation",
						p, command);
				SendRotateLine(
						"b",
						"Body",
						"Rotate the body by clicking the preceding items. Click here to reset the rotation",
						p, command);
				SendRotateLine(
						"la",
						"Left Arm",
						"Rotate the Left Arm by clicking the preceding items. Click here to reset the rotation",
						p, command);
				SendRotateLine(
						"ra",
						"Right Arm",
						"Rotate the Right Arm by clicking the preceding items. Click here to reset the rotation",
						p, command);
				SendRotateLine(
						"ll",
						"Left Leg",
						"Rotate the Left Leg by clicking the preceding items. Click here to reset the rotation",
						p, command);
				SendRotateLine(
						"rl",
						"Right Leg",
						"Rotate the Right Leg by clicking the preceding items. Click here to reset the rotation",
						p, command);
				p.sendMessage("§d§lLocation:");
				SendTranslateLine("m", "Move the armor stand", p, command);

			} else {
				List<ArmorStand> nearbyArmorStands = new ArrayList<ArmorStand>();
				for (Entity e : p.getNearbyEntities(5, 5, 5)) {
					if (e instanceof ArmorStand) {
						nearbyArmorStands.add((ArmorStand) e);
					}
				}

				double distance = 500.0;
				ArmorStand arm = null;
				for (ArmorStand am : nearbyArmorStands) {
					if (am.getLocation().distance(p.getLocation()) < distance) {
						distance = am.getLocation().distance(p.getLocation());
						arm = am;
					}
				}

				if (arm == null) {
					sender.sendMessage("§cNo nearby armor stands!");
					return true;
				}

				if (arm != null) {
					if (hasPermissionInThisArea(p, arm.getLocation())) {
						if (arg3[0].equals("a")) {
							arm.setArms(!arm.hasArms());
						}
						if (arg3[0].equals("b")) {
							arm.setBasePlate(!arm.hasBasePlate());
						}
						if (arg3[0].equals("g")) {
							arm.setGravity(!arm.hasGravity());
							p.sendMessage("§dGravity = §7" + arm.hasGravity());
						}
						if (arg3[0].equals("s")) {
							arm.setSmall(!arm.isSmall());
						}
						if (arg3[0].equals("v")) {
							arm.setVisible(!arm.isVisible());
						}
						if (arg3[0].equals("i")) {
							if (arm.getItemInHand().getType() != Material.AIR) {
								arm.getWorld().dropItem(arm.getLocation(),
										arm.getItemInHand());
								arm.setItemInHand(null);
							}
						}

						if (arg3[0].equals("hx+")) {
							arm.setHeadPose(arm.getHeadPose().setX(
									arm.getHeadPose().getX() + .1));
						}
						if (arg3[0].equals("hx-")) {
							arm.setHeadPose(arm.getHeadPose().setX(
									arm.getHeadPose().getX() - .1));
						}
						if (arg3[0].equals("hz+")) {
							arm.setHeadPose(arm.getHeadPose().setZ(
									arm.getHeadPose().getZ() + .1));
						}
						if (arg3[0].equals("hz-")) {
							arm.setHeadPose(arm.getHeadPose().setZ(
									arm.getHeadPose().getZ() - .1));
						}
						if (arg3[0].equals("hy+")) {
							arm.setHeadPose(arm.getHeadPose().setY(
									arm.getHeadPose().getY() + .1));
						}
						if (arg3[0].equals("hy-")) {
							arm.setHeadPose(arm.getHeadPose().setY(
									arm.getHeadPose().getY() - .1));
						}
						if (arg3[0].equals("bx+")) {
							arm.setBodyPose(arm.getBodyPose().setX(
									arm.getBodyPose().getX() + .1));
						}
						if (arg3[0].equals("bx-")) {
							arm.setBodyPose(arm.getBodyPose().setX(
									arm.getBodyPose().getX() - .1));
						}
						if (arg3[0].equals("bz+")) {
							arm.setBodyPose(arm.getBodyPose().setZ(
									arm.getBodyPose().getZ() + .1));
						}
						if (arg3[0].equals("bz-")) {
							arm.setBodyPose(arm.getBodyPose().setZ(
									arm.getBodyPose().getZ() - .1));
						}
						if (arg3[0].equals("by+")) {
							arm.setBodyPose(arm.getBodyPose().setY(
									arm.getBodyPose().getY() + .1));
						}
						if (arg3[0].equals("by-")) {
							arm.setBodyPose(arm.getBodyPose().setY(
									arm.getBodyPose().getY() - .1));
						}

						if (arg3[0].equals("lax+")) {
							arm.setLeftArmPose(arm.getLeftArmPose().setX(
									arm.getLeftArmPose().getX() + .1));
						}
						if (arg3[0].equals("lax-")) {
							arm.setLeftArmPose(arm.getLeftArmPose().setX(
									arm.getLeftArmPose().getX() - .1));
						}
						if (arg3[0].equals("laz+")) {
							arm.setLeftArmPose(arm.getLeftArmPose().setZ(
									arm.getLeftArmPose().getZ() + .1));
						}
						if (arg3[0].equals("laz-")) {
							arm.setLeftArmPose(arm.getLeftArmPose().setZ(
									arm.getLeftArmPose().getZ() - .1));
						}
						if (arg3[0].equals("lay+")) {
							arm.setLeftArmPose(arm.getLeftArmPose().setY(
									arm.getLeftArmPose().getY() + .1));
						}
						if (arg3[0].equals("lay-")) {
							arm.setLeftArmPose(arm.getLeftArmPose().setY(
									arm.getLeftArmPose().getY() - .1));
						}

						if (arg3[0].equals("rax+")) {
							arm.setRightArmPose(arm.getRightArmPose().setX(
									arm.getRightArmPose().getX() + .1));
						}
						if (arg3[0].equals("rax-")) {
							arm.setRightArmPose(arm.getRightArmPose().setX(
									arm.getRightArmPose().getX() - .1));
						}
						if (arg3[0].equals("raz+")) {
							arm.setRightArmPose(arm.getRightArmPose().setZ(
									arm.getRightArmPose().getZ() + .1));
						}
						if (arg3[0].equals("raz-")) {
							arm.setRightArmPose(arm.getRightArmPose().setZ(
									arm.getRightArmPose().getZ() - .1));
						}
						if (arg3[0].equals("ray+")) {
							arm.setRightArmPose(arm.getRightArmPose().setY(
									arm.getRightArmPose().getY() + .1));
						}
						if (arg3[0].equals("ray-")) {
							arm.setRightArmPose(arm.getRightArmPose().setY(
									arm.getRightArmPose().getY() - .1));
						}

						if (arg3[0].equals("rlx+")) {
							arm.setRightLegPose(arm.getRightLegPose().setX(
									arm.getRightLegPose().getX() + .1));
						}
						if (arg3[0].equals("rlx-")) {
							arm.setRightLegPose(arm.getRightLegPose().setX(
									arm.getRightLegPose().getX() - .1));
						}
						if (arg3[0].equals("rlz+")) {
							arm.setRightLegPose(arm.getRightLegPose().setZ(
									arm.getRightLegPose().getZ() + .1));
						}
						if (arg3[0].equals("rlz-")) {
							arm.setRightLegPose(arm.getRightLegPose().setZ(
									arm.getRightLegPose().getZ() - .1));
						}
						if (arg3[0].equals("rly+")) {
							arm.setRightLegPose(arm.getRightLegPose().setY(
									arm.getRightLegPose().getY() + .1));
						}
						if (arg3[0].equals("rly-")) {
							arm.setRightLegPose(arm.getRightLegPose().setY(
									arm.getRightLegPose().getY() - .1));
						}

						if (arg3[0].equals("llx+")) {
							arm.setLeftLegPose(arm.getLeftLegPose().setX(
									arm.getLeftLegPose().getX() + .1));
						}
						if (arg3[0].equals("llx-")) {
							arm.setLeftLegPose(arm.getLeftLegPose().setX(
									arm.getLeftLegPose().getX() - .1));
						}
						if (arg3[0].equals("llz+")) {
							arm.setLeftLegPose(arm.getLeftLegPose().setZ(
									arm.getLeftLegPose().getZ() + .1));
						}
						if (arg3[0].equals("llz-")) {
							arm.setLeftLegPose(arm.getLeftLegPose().setZ(
									arm.getLeftLegPose().getZ() - .1));
						}
						if (arg3[0].equals("lly+")) {
							arm.setLeftLegPose(arm.getLeftLegPose().setY(
									arm.getLeftLegPose().getY() + .1));
						}
						if (arg3[0].equals("lly-")) {
							arm.setLeftLegPose(arm.getLeftLegPose().setY(
									arm.getLeftLegPose().getY() - .1));
						}

						if (arg3[0].equals("mx+")) {
							Location l = arm.getLocation();
							l.setX(l.getX() + .1);
							arm.teleport(l);
						}
						if (arg3[0].equals("mx-")) {
							Location l = arm.getLocation();
							l.setX(l.getX() - .1);
							arm.teleport(l);
						}
						if (arg3[0].equals("mz+")) {
							Location l = arm.getLocation();
							l.setZ(l.getZ() + .1);
							arm.teleport(l);
						}
						if (arg3[0].equals("mz-")) {
							Location l = arm.getLocation();
							l.setZ(l.getZ() - .1);
							arm.teleport(l);
						}
						if (arg3[0].equals("my+")) {
							Location l = arm.getLocation();
							l.setY(l.getY() + .1);
							arm.teleport(l);
						}
						if (arg3[0].equals("my-")) {
							Location l = arm.getLocation();
							l.setY(l.getY() - .1);
							arm.teleport(l);
						}

					} else {
						p.sendMessage("§cYou do not have permission to edit that armor stand");
						return true;
					}
				}
			}
		}
		return true;
	}

	private boolean hasPermissionInThisArea(Player p, Location location) {
		String name = p.getDisplayName();
		for (ProtectedArea pa : areas) {
			if (pa.equals(location)) {
				if (pa.hasPermission(name)) {
					return true;
				}
			}
		}
		return false;
	}

	@EventHandler
	private void onBucketEvent(PlayerBucketEmptyEvent event) {
		if (!hasPermissionInThisArea(event.getPlayer(), event.getBlockClicked()
				.getRelative(BlockFace.EAST).getLocation())
				|| !hasPermissionInThisArea(event.getPlayer(), event
						.getBlockClicked().getRelative(BlockFace.NORTH)
						.getLocation())
				|| !hasPermissionInThisArea(event.getPlayer(), event
						.getBlockClicked().getRelative(BlockFace.SOUTH)
						.getLocation())
				|| !hasPermissionInThisArea(event.getPlayer(), event
						.getBlockClicked().getRelative(BlockFace.WEST)
						.getLocation())) {
			event.setCancelled(true);
			return;
		}
	}

	@EventHandler
	private void onBucketFillEvent(PlayerBucketFillEvent event) {
		if(!hasPermissionInThisArea(event.getPlayer(), event.getBlockClicked().getLocation())){
			event.setCancelled(true);
			return;
		}
	}

	@EventHandler
	private void onFlowEvent(BlockFromToEvent event) {
		for (ProtectedArea pa : areas) {
			if (pa.equals(event.getToBlock().getLocation())) {
				if (pa.equals(event.getBlock().getLocation())) {
					return;
				}
				event.setCancelled(true);
				return;
			}
		}
	}

	@EventHandler
	private void onDispenserEvent(BlockDispenseEvent event) {
		try {
			Dispenser dispenser = (Dispenser) event.getBlock().getState()
					.getData();
			dispenser.getFacing();
			for (ProtectedArea pa : areas) {
				Location output = event.getBlock()
						.getRelative(dispenser.getFacing()).getLocation();
				if (pa.equals(output)) {
					if (!pa.equals(event.getBlock().getLocation())) {
						event.setCancelled(true);
						return;
					}
				}
			}
		} catch (Exception e) {

		}
	}

	@EventHandler
	public void onPistonExtend(BlockPistonExtendEvent event) {
		for (ProtectedArea pa : areas) {
			for (Block b : event.getBlocks()) {
				if (pa.equals(b.getLocation())) {
					if (!pa.equals(event.getBlock().getLocation())) {
						event.setCancelled(true);
						return;
					}
				}
			}
			if (pa.equals(event.getBlock().getRelative(event.getDirection())
					.getLocation())) {
				if (!pa.equals(event.getBlock().getLocation())) {
					event.setCancelled(true);
					return;
				}
			}

			if (pa.equals(event.getBlock().getRelative(event.getDirection())
					.getRelative(event.getDirection()).getLocation())) {
				if (!pa.equals(event.getBlock().getLocation())) {
					event.setCancelled(true);
					return;
				}
			}
		}
	}

	@EventHandler
	public void onPistonRetract(BlockPistonRetractEvent event) {
		for (ProtectedArea pa : areas) {
			if (pa.equals(event.getBlock().getRelative(event.getDirection())
					.getLocation())) {
				if (!pa.equals(event.getBlock().getLocation())) {
					event.setCancelled(true);
					return;
				}
			}
		}
	}

	@EventHandler
	private void onAnimalHurtEvent(EntityDamageByEntityEvent event) {
		for (ProtectedArea pa : areas) {
			if (pa.equals(event.getEntity().getLocation())) {
				if (!event.getEntityType().equals(EntityType.ZOMBIE)
						&& !event.getEntityType().equals(EntityType.PIG_ZOMBIE)
						&& !event.getEntityType().equals(EntityType.SKELETON)
						&& !event.getEntityType().equals(EntityType.CREEPER)
						&& !event.getEntityType()
								.equals(EntityType.CAVE_SPIDER)
						&& !event.getEntityType().equals(EntityType.SPIDER)
						&& !event.getEntityType().equals(EntityType.BAT)
						&& !event.getEntityType().equals(EntityType.ENDERMAN)
						&& !event.getEntityType().equals(EntityType.ENDERMITE)
						&& !event.getEntityType().equals(EntityType.GUARDIAN)
						&& !event.getEntityType().equals(EntityType.WITCH)
						&& !event.getEntityType().equals(EntityType.GHAST)
						&& !event.getEntityType().equals(EntityType.WITHER)
						&& !event.getEntityType().equals(EntityType.SILVERFISH)
						&& !event.getEntityType().equals(EntityType.BLAZE)
						&& !event.getEntityType().equals(EntityType.SLIME)) {
					if (event.getDamager() instanceof Player) {
						Player p = (Player) event.getDamager();
						if (!pa.hasPermission(p.getDisplayName())) {
							event.setCancelled(true);
							return;
						}
					}
					if (event.getDamager() instanceof Arrow) {
						Arrow a = (Arrow) event.getDamager();
						if (a.getShooter() instanceof Player) {
							Player p = (Player) a.getShooter();
							if (!pa.hasPermission(p.getDisplayName())) {
								event.setCancelled(true);
								return;
							}
						}
					}
				}
			}
		}
	}

	@EventHandler
	private void onPotionThrowEvent(PotionSplashEvent event) {
		if (event.getEntity().getShooter() instanceof Player) {
			Player p = (Player) event.getEntity().getShooter();
			for (LivingEntity e : event.getAffectedEntities()) {
				for (ProtectedArea pa : areas) {
					if (pa.equals(e.getLocation())) {
						if (!pa.hasPermission(p.getDisplayName())
								&& !pa.owner.equals("secret1")) {
							event.setCancelled(true);
							return;
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void onPlayerFinalSecretEvent(PlayerInteractEvent event) {
		if (event.getClickedBlock() != null) {
			if (event.getClickedBlock().getType() == Material.STONE_BUTTON) {
				if (event.getClickedBlock().getLocation().getX() == Secret.SECRETX
						&& event.getClickedBlock().getLocation().getZ() == Secret.SECRETZ) {
					event.getPlayer().sendMessage(
							"§d"
									+ possiblePartyArmors
											.get(getArmorHash(event.getPlayer()
													.getDisplayName())));
				}
			}
		}
	}

	private boolean coords(CommandSender sender) {
		Location l = Bukkit.getServer().getPlayer(sender.getName())
				.getLocation();
		this.getServer().broadcastMessage(
				"§3"
						+ Bukkit.getServer().getPlayer(sender.getName())
								.getDisplayName()
						+ "§r§9 is in the §3"
						+ sender.getServer().getPlayer(sender.getName())
								.getWorld().getName() + "§9 at:" + "§9 X: §3"
						+ (int) l.getX() + "§9 Y: §3" + (int) l.getY()
						+ "§9 Z: §3" + (int) l.getZ());
		return true;
	}

	private boolean server(CommandSender sender, String[] arg3) {
		if (sender.isOp() || sender instanceof ConsoleCommandSender) {
			if (arg3[0].equalsIgnoreCase("say")) {
				String s = "";
				for (int i = 1; i < arg3.length; i++) {
					s += arg3[i] + " ";
				}

				Bukkit.broadcastMessage("§d[Server] " + s);
				sendMessageToBungee("§d[Server]", s);
				return true;
			}

			if (arg3[0].equalsIgnoreCase("txt")) {
				String rec = arg3[1];
				String msg = "";
				for (int i = 2; i < arg3.length; i++) {
					msg += arg3[i] + " ";
				}
				return MsgViaText(rec, msg);
			}

			if (arg3[0].equalsIgnoreCase("title")) {
				String rec = arg3[1];
				String msg = "";
				for (int i = 2; i < arg3.length; i++) {
					msg += arg3[i] + " ";
				}

				Bukkit.getPlayer(rec).sendTitle(msg, msg);
				return true;
			}

			if (arg3[0].equalsIgnoreCase("msgall")) {

				String msg = "";
				for (int i = 1; i < arg3.length; i++) {
					msg += arg3[i] + " ";
				}

				int i = 0;

				for (Map.Entry<UUID, SerenityPlayer> entry : serenityPlayers
						.entrySet()) {
					final String name = entry.getValue().getName();
					final String m = msg;
					if (TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis()
							- entry.getValue().getLastPlayed()) < 75) {
						i += 5;
						Bukkit.getScheduler().runTaskLaterAsynchronously(this,
								new Runnable() {
									@Override
									public void run() {
										Bukkit.dispatchCommand(
												Bukkit.getConsoleSender(),
												"msg " + name + " " + m);
									}
								}, i + 0L);
					}
				}

				return true;
			}

			if (arg3.length == 1) {

				if (arg3[0].equals("startbattle")) {
					if (aBattleIsRaging) {
						return true;
					}

					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
							"server remove");
					aBattleIsRaging = true;
					finalDungeonKillCount = 0;

					TELEPORTDESTINATIONFORSOMETHING.getWorld()
							.strikeLightningEffect(
									TELEPORTDESTINATIONFORSOMETHING);

					Bukkit.getScheduler().scheduleSyncDelayedTask(this,
							new Runnable() {
								@Override
								public void run() {
									for (Player player : Bukkit
											.getOnlinePlayers()) {
										if (player.getLocation().getWorld()
												.getName().equals("world")
												&& player
														.getLocation()
														.distance(
																CENTERBLOCKSINFINALDUNGEON) < 75) {

											player.playEffect(
													CENTERBLOCKSINFINALDUNGEON,
													Effect.RECORD_PLAY, 0);

											player.playEffect(
													CENTERBLOCKSINFINALDUNGEON,
													Effect.RECORD_PLAY,
													Material.RECORD_6.getId());
										}
									}

									CENTERBLOCKSINFINALDUNGEON.getWorld()
											.strikeLightningEffect(
													CENTERBLOCKSINFINALDUNGEON);

									for (int i = 0; i < 5; i++) {
										spawn1EntityInEachCorner(
												EntityType.ZOMBIE,
												new PotionEffect(
														PotionEffectType.SLOW,
														99999, 0), "§c"
														+ "ZOMBIE");
									}
								};
							}, 100L);

				}

				if (arg3[0].equals("baby")) {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						for (Entity e : p.getNearbyEntities(5, 5, 5)) {
							if (e instanceof Sheep) {
								Sheep sh = (Sheep) e;
								sh.setBaby();
								sh.setAgeLock(true);
							}
						}
					}
				}

				if (arg3[0].equals("remove")) {
					finalDungeonKillCount = 0;
					aBattleIsRaging = false;
					for (Player p : Bukkit.getOnlinePlayers()) {
						if (p.getWorld().getName().equals("world")) {
							if (p.getLocation().distance(
									CENTERBLOCKSINFINALDUNGEON) < 75) {
								for (Entity e : p.getNearbyEntities(75, 20, 75)) {
									if (e.getCustomName() != null) {
										if (e.getCustomName().contains("§c")) {
											e.remove();
										}
									}
								}
								return true;
							}
						}
					}
				}

				if (arg3[0].equals("burn")) {
					for (Player p : Bukkit.getOnlinePlayers()) {
						if (p.getLocation()
								.distance(CENTERBLOCKSINFINALDUNGEON) < 75) {
							for (Entity e : p.getNearbyEntities(75, 20, 75)) {
								e.setFireTicks(50000);
							}
							return true;
						}
					}
				}

				if (arg3[0].equals("kill")) {
					for (Player p : Bukkit.getOnlinePlayers()) {
						if (p.getLocation().getWorld().getName()
								.equals("world")) {
							if (p.getLocation().distance(
									CENTERBLOCKSINFINALDUNGEON) < 75) {
								for (Entity e : p.getNearbyEntities(75, 20, 75)) {
									if (e instanceof LivingEntity) {
										LivingEntity le = (LivingEntity) e;
										le.damage(500000);
									}
								}
								return true;
							}
						}
					}
				}

				if (arg3[0].equals("chunks")) {

					int area = 0;
					String s = "";
					for (ProtectedArea pa : areas) {
						s += pa.owner + ": " + (int) pa.location1.getX() + " "
								+ (int) pa.location1.getZ() + " Area: "
								+ pa.area() + "\n";
						area += pa.area();
					}
					s += "Total Area: " + area;
					sender.sendMessage(s);

				}

				if (arg3[0].equals("test")) {
					if (sender instanceof Player)
						putRewardHeadInMailbox(((Player) sender).getUniqueId(),
								20);
				}

				if (arg3[0].equals("npe")) {
					for (Player p : Bukkit.getOnlinePlayers()) {
						Player pl = Bukkit.getPlayer(p.getUniqueId());
						int x = pl.getStatistic(Statistic.TIME_SINCE_DEATH);
						Bukkit.broadcastMessage(x + "");

					}
				}

				if (arg3[0].equals("updateSPs")) {
					Bukkit.getScheduler().runTaskLaterAsynchronously(this,
							new Runnable() {
								@Override
								public void run() {
									for (Map.Entry<UUID, SerenityPlayer> entry : serenityPlayers
											.entrySet()) {
										sender.sendMessage("Checking "
												+ entry.getValue().getName());
										OfflinePlayer op = Bukkit
												.getOfflinePlayer(entry
														.getValue().getUUID());
										if (op.isBanned()) {
											entry.getValue().setBanned(true);
										}
										if (!op.isWhitelisted()) {
											entry.getValue().setWhitelisted(
													false);
										}
									}
									sender.sendMessage("Done");

								}
							}, 0L);
					return true;
				}

				if (arg3[0].equals("reloadmailboxes")) {
					mailBoxes = new ArrayList<Mailbox>();
					loadSerenityMailboxesFromDatabase();
				}

				if (arg3[0].equals("showsql")) {
					showingSql = !showingSql;
					sender.sendMessage("Showing sql = " + showingSql);
				}

				/*
				 * if (arg3[0].equals("populateP")) { populatePlayerTable(); }
				 */
				if (arg3[0].equals("gethash")) {
					if (sender instanceof Player) {

						Player p = (Player) sender;
						int hash = getArmorHash(p.getDisplayName());
						Material mat = possiblePartyArmors.get(hash);
						sender.sendMessage("Your int is " + hash);
						sender.sendMessage("Your mat is " + mat.toString());

					}
				}

				if (arg3[0].equals("afktimes")) {
					for (SerenityPlayer sp : getOnlineSerenityPlayers()) {
						sender.sendMessage(sp.getName() + ": "
								+ sp.getAfkTime() + " minutes");
					}
				}

				if (arg3[0].equals("motd")) {
					boolean mo = motdsCfg.getConfig().getBoolean("randomMotd",
							true);
					motdsCfg.getConfig().set("randomMotd", !mo);
					sender.sendMessage("Random MOTD = " + !mo);
				}

				if (arg3[0].equals("pingers")) {
					motdsCfg.getConfig().set("Pingers",
							!motdsCfg.getConfig().getBoolean("Pingers", true));
					sender.sendMessage("Pingers = "
							+ motdsCfg.getConfig().getBoolean("Pingers"));
					motdsCfg.saveConfig();
				}

				if (arg3[0].equals("bots")) {
					motdsCfg.getConfig().set("Bots",
							!motdsCfg.getConfig().getBoolean("Bots", false));
					sender.sendMessage("Bots = "
							+ motdsCfg.getConfig().getBoolean("Bots"));
					motdsCfg.saveConfig();
				}

				if (arg3[0].equals("unload")) {
					unloadChunks();
					return true;
				}

				if (arg3[0].equals("eff")) {
					opParticles = !opParticles;
					sender.sendMessage("Particles on = " + opParticles);
					return true;
				}

				if (arg3[0].equals("deb")) {
					opParticlesDeb = !opParticlesDeb;
					sender.sendMessage("You will see = " + opParticlesDeb);
					return true;
				}

				if (arg3[0].equals("remove")) {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						for (Entity e : p.getNearbyEntities(5, 5, 5)) {
							e.remove();
						}
					}
				}

				if (arg3[0].equals("ping")) {
					for (Player p : Bukkit.getOnlinePlayers()) {
						CraftPlayer cp = (CraftPlayer) p;
						sender.sendMessage(p.getDisplayName() + ": "
								+ cp.getHandle().ping + "ms");
					}
				}

				if (arg3[0].equals("cte")) {
					TreeMap<Integer, String> chunks = new TreeMap<Integer, String>();
					for (World w : Bukkit.getWorlds()) {
						for (Chunk c : w.getLoadedChunks()) {
							chunks.put(c.getTileEntities().length, "§a"
									+ c.getWorld().getName() + ": " + c.getX()
									* 16 + ", " + c.getZ() * 16);
						}
					}
					sender.sendMessage("§cTile Entities:");
					if (chunks.size() >= 10) {
						for (int i = 0; i < 10; i++) {
							sender.sendMessage(chunks.lastEntry().getValue()
									+ ": §6" + chunks.lastEntry().getKey());
							chunks.remove(chunks.lastKey());
						}
					}
					return true;
				}

				if (arg3[0].equals("ce")) {
					TreeMap<Integer, String> chunks = new TreeMap<Integer, String>();
					for (World w : Bukkit.getWorlds()) {
						for (Chunk c : w.getLoadedChunks()) {
							chunks.put(c.getEntities().length, "§a"
									+ c.getWorld().getName() + ": " + c.getX()
									* 16 + ", " + c.getZ() * 16);
						}
					}
					sender.sendMessage("§cEntities:");
					if (chunks.size() >= 10) {
						for (int i = 0; i < 10; i++) {
							sender.sendMessage(chunks.lastEntry().getValue()
									+ ": §6" + chunks.lastEntry().getKey());
							chunks.remove(chunks.lastKey());
						}
					}
					return true;
				}

				if (arg3[0].equals("online")) {
					String s = "";
					if (Bukkit.getOnlinePlayers().size() > 0) {

						for (Player p : Bukkit.getOnlinePlayers()) {
							s += p.getName() + " ";
						}
					} else {
						s = "Nobody is online";
					}

					sendATextToHal("SerenityPlugins", s);

				}

				if (arg3[0].equals("reloadcfg")) {
					podrickCfg.reloadConfig();
					emailCfg.reloadConfig();
					bookCfg.reloadConfig();
					linksCfg.reloadConfig();
					daleCfg.reloadConfig();
					// mailboxCfg.reloadConfig();
					// /leaderboardCfg.reloadConfig();
					// diamondFoundCfg.reloadConfig();
					motdsCfg.reloadConfig();
					sender.sendMessage("Reloaded pod, email, books, links, it, dale, mailbox, who is onilne, diamond, leaderboard");
					updateRandomMotds();
					return true;
				}

				if (arg3[0].equals("sleepers")) {
					for (Player p : Bukkit.getOnlinePlayers()) {
						sender.sendMessage(p.getDisplayName()
								+ " sleeping ignored = "
								+ p.isSleepingIgnored());
						sender.sendMessage(p.getDisplayName() + " sleeping = "
								+ p.isSleeping());
					}
				}

				if (arg3[0].equals("clear")) {
					for (int i = 0; i < 50; i++)
						sender.sendMessage("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
					return true;
				}

				if (arg3[0].equals("party")) {

					party();
					return true;
				}

				if (arg3[0].equals("reload")) {
					Long time = System.currentTimeMillis();
					Bukkit.getServer().broadcastMessage(
							"§cReloading plugins... There might be lag");
					Bukkit.getServer().dispatchCommand(
							Bukkit.getConsoleSender(), "reload");
					Long dur = System.currentTimeMillis() - time;
					Bukkit.getServer().broadcastMessage(
							"§2Done. (" + (double) dur / 1000 + "s)");
					return true;
				}

				if (arg3[0].equals("createbook")) {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
						BookMeta meta = (BookMeta) book.getItemMeta();
						bookCfg.reloadConfig();

						meta.setTitle(bookCfg.getConfig().getString("Title"));
						meta.setAuthor("§6"
								+ bookCfg.getConfig().getString("Author"));

						String bookText = bookCfg.getConfig().getString("Text");
						List<String> pages = new ArrayList<String>();
						int l = 0;
						int lineCount = 0;
						String page = "";
						for (int i = 0; i < bookText.length(); i++) {
							if (bookText.charAt(i) == '~') {
								pages.add(page);
								page = "";
								lineCount = 0;
							} else {
								page += bookText.charAt(i);

								l++;
								if (bookText.charAt(i) == '\n' || l > 19) {
									l = 0;
									lineCount++;
								}

								if (lineCount > 12
										&& (bookText.charAt(i) == ' ' || bookText
												.charAt(i) == '\n')) {
									pages.add(page);
									page = "";
									lineCount = 0;
								}
							}
						}
						pages.add(page);

						meta.setPages(pages);
						book.setItemMeta(meta);

						safelyDropItemStack(p.getLocation(), p.getInventory()
								.addItem(book));

						return true;
					}
				}

				if (arg3[0].equals("showscores")) {
					Scoreboard board = Bukkit.getScoreboardManager()
							.getMainScoreboard();
					Objective objective = (Objective) board.getObjectives()
							.toArray()[0];
					objective.setDisplaySlot(DisplaySlot.SIDEBAR);
					objective.setDisplayName("Exploration Challenge");

					for (Player p : Bukkit.getOnlinePlayers()) {
						p.setScoreboard(board);
					}
					return true;
				}

			} else if (arg3.length == 2) {
				if (arg3[0].equals("secret")) {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						p.getLocation()
								.getWorld()
								.spawnEntity(p.getLocation(),
										EntityType.VILLAGER)
								.setCustomName("§6" + arg3[1]);
					}
				}

				if (arg3[0].equals("secretpig")) {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						p.getLocation().getWorld()
								.spawnEntity(p.getLocation(), EntityType.PIG)
								.setCustomName("§6" + arg3[1]);
					}
				}

				if (arg3[0].equals("changemyname")) {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						p.setDisplayName(arg3[1]);
						p.sendMessage("§6Identity changed to §3" + arg3[1]);
						return true;

					}
				}

				if (arg3[0].equals("secretcow")) {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						p.getLocation().getWorld()
								.spawnEntity(p.getLocation(), EntityType.COW)
								.setCustomName("§6" + arg3[1]);
					}
				}

				if (arg3[0].equals("horse")) {
					Player p = (Player) sender;
					Horse horse = (Horse) p.getLocation().getWorld()
							.spawnEntity(p.getLocation(), EntityType.HORSE);
					horse.setDomestication(1);
					horse.setStyle(Style.NONE);
					horse.setVariant(Horse.Variant.SKELETON_HORSE);
					horse.setJumpStrength(30);
					horse.setTamed(true);
					horse.setAdult();
					horse.setCustomName("§d" + arg3[1]);

					/*
					 * CraftLivingEntity h = (CraftLivingEntity)horse;
					 * AttributeInstance a =
					 * h.getHandle().getAttributeInstance(GenericAttributes.d);
					 * a.setValue(8);
					 */
				}

				if (arg3[0].equals("mule")) {
					Player p = (Player) sender;
					Horse horse = (Horse) p.getLocation().getWorld()
							.spawnEntity(p.getLocation(), EntityType.HORSE);
					horse.setDomestication(1);
					horse.setStyle(Style.NONE);
					horse.setVariant(Horse.Variant.MULE);
					horse.setCarryingChest(true);
					horse.setTamed(true);
					horse.setAdult();
					horse.setCustomName(Secret.secretName);
					/*
					 * CraftLivingEntity h = (CraftLivingEntity)horse;
					 * AttributeInstance a =
					 * h.getHandle().getAttributeInstance(GenericAttributes.d);
					 * a.setValue(8);
					 */
				}

				if (arg3[0].equals("secretcat")) {
					if (sender instanceof Player) {
						Player p = (Player) sender;

						Ocelot ocelot = (Ocelot) p
								.getLocation()
								.getWorld()
								.spawnEntity(p.getLocation(), EntityType.OCELOT);

						ocelot.setCustomName("§6" + arg3[1]);
						ocelot.setTamed(true);
						ocelot.setRemoveWhenFarAway(false);
						ocelot.setCatType(Ocelot.Type.SIAMESE_CAT);
					}
				}

				if (arg3[0].equals("secretgolem")) {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						p.getLocation()
								.getWorld()
								.spawnEntity(p.getLocation(),
										EntityType.IRON_GOLEM)
								.setCustomName("§6" + arg3[1]);
					}
				}

				if (arg3[0].equals("secretmoosh")) {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						p.getLocation()
								.getWorld()
								.spawnEntity(p.getLocation(),
										EntityType.MUSHROOM_COW)
								.setCustomName("§6" + arg3[1]);
					}
				}

				if (arg3[0].equals("secretsheep")) {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						p.getLocation().getWorld()
								.spawnEntity(p.getLocation(), EntityType.SHEEP)
								.setCustomName("§6" + arg3[1]);
					}
				}

				if (arg3[0].equals("mute")) {

					SerenityPlayer sp = serenityPlayers.get((Bukkit
							.getPlayer(arg3[1]).getUniqueId()));
					sp.setMuted(!sp.isMuted());
					sender.sendMessage(sp.getName() + "muted = " + sp.isMuted());
					return true;
				}

				if (arg3[0].equals("find")) {
					try {
						Location l = Bukkit.getPlayer(arg3[1]).getLocation();
						sender.sendMessage(l.getWorld().getName() + ": "
								+ (int) l.getX() + ", " + (int) l.getY() + ", "
								+ (int) l.getZ());
						return true;
					} catch (Exception e) {
						sender.sendMessage("Something went wrong");
					}
				}

				if (arg3[0].equals("tpabove")) {
					try {
						Location l = Bukkit.getPlayer(arg3[1]).getLocation();
						if (sender instanceof Player) {
							Player p = (Player) sender;
							l.setY(150);
							p.teleport(l);
							return true;
						}
						return true;
					} catch (Exception e) {
						sender.sendMessage("Something went wrong");
					}
				}

				if (arg3[0].equals("exp")) {
					try {
						Location l = Bukkit.getPlayer(arg3[1]).getLocation();
						if (sender instanceof Player) {
							Player p = (Player) sender;
							for (int i = 0; i < 70; i++) {
								safelySpawnExperienceOrb(p, 10000, i);
							}
							return true;
						}
						return true;
					} catch (Exception e) {
						sender.sendMessage("Something went wrong");
					}
				}

			}
			if (arg3[0].equals("cmd")) {
				if (arg3.length < 2) {
					return true;
				}
				String cmd = "";
				for (int i = 2; i < arg3.length; i++) {
					cmd += arg3[i] + " ";
				}
				Player p = Bukkit.getPlayer(arg3[1]);
				Bukkit.dispatchCommand((CommandSender) p, cmd);
				return true;
			}

			if (arg3[0].equals("ban")) {
				if (arg3.length < 2) {
					return true;
				}
				String name = arg3[1];
				String banMessage = "";
				for (int i = 2; i < arg3.length; i++) {
					banMessage += arg3[i] + " ";
				}
				OfflinePlayer p = Bukkit.getOfflinePlayer(name);
				p.setBanned(true);
				p.setWhitelisted(false);
				SerenityPlayer sp = serenityPlayers.get(p.getUniqueId());
				if (sp != null) {
					sp.setBanned(true);
					sp.setWhitelisted(false);
					sp.setOp(false);
				}

				if (p.isOnline()) {
					Player pl = p.getPlayer();
					pl.kickPlayer("You have been banned for: " + banMessage);
					pl.setBanned(true);
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ban-ip "
							+ pl.getAddress().getAddress().toString() + " "
							+ banMessage);
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ban "
							+ p.getName() + " " + banMessage);
				}
			}
		} else {
			noPerms(sender);
			return true;
		}
		return false;
	}

	private void sendPlayerList(Player p, String thisServerName,
			String otherServerName, List<String> otherServerNames,
			String otherServer2, List<String> otherServerNames2) {

		PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
		IChatBaseComponent header;

		header = ChatSerializer.a("{text:\"" + thisServerName + "\"}");

		String footerPrep = "";

		footerPrep = "{text:\"" + otherServerName;
		for (String s : otherServerNames) {
			footerPrep += "\n§r" + s;
		}
		if (otherServerNames2.size() > 0) {
			footerPrep += "\n" + otherServer2;
		}
		for (String s : otherServerNames2) {
			footerPrep += "\n§r" + s;
		}

		String timeStamp = new SimpleDateFormat("HH:mm:ss").format(Calendar
				.getInstance().getTime());

		footerPrep += "\n§7" + timeStamp;
		footerPrep += "\"}";

		if (otherServerNames.size() == 0) {
			footerPrep = footerPrep.replace(otherServerName, "");
		}
		if (otherServerNames2.size() == 0) {
			footerPrep = footerPrep.replace(otherServer2, "");
		}

		IChatBaseComponent footer = ChatSerializer.a(footerPrep);
		try {
			Field a = packet.getClass().getDeclaredField("a");
			a.setAccessible(true);
			a.set(packet, header);

			Field b = packet.getClass().getDeclaredField("b");
			b.setAccessible(true);
			b.set(packet, footer);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		((CraftPlayer) p.getPlayer()).getHandle().playerConnection
				.sendPacket(packet);

	}

	private void updateRandomMotds() {
		allMotds.clear();

		try {
			ConfigurationSection motds = motdsCfg.getConfig()
					.getConfigurationSection("MOTDS");
			for (String s : motds.getStringList("MOTD")) {
				allMotds.add(s);
			}
		} catch (Exception e) {
			getLogger().info("Exception thrown while trying to add MOTDs");
		}

		try {
			ConfigurationSection motds = motdsCfg.getConfig()
					.getConfigurationSection("Main");
			for (String s : motds.getStringList("MOTD")) {
				mainMotd = s;
			}
		} catch (Exception e) {
			getLogger().info("Exception thrown while trying to add MOTD main");
		}

	}

	private void safelyDropItemStack(Location location,
			HashMap<Integer, ItemStack> xo) {
		final Location l = location;
		final HashMap<Integer, ItemStack> x = xo;
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			@Override
			public void run() {
				for (Entry<Integer, ItemStack> items : x.entrySet()) {
					l.getWorld().dropItem(l, items.getValue());
				}
			}
		});
	}

	private void safelySpawnExperienceOrb(Player player, int i, int time) {
		final Player playerF = player;
		final int exp = i;
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			@Override
			public void run() {
				((ExperienceOrb) playerF.getWorld().spawn(
						playerF.getLocation(), ExperienceOrb.class))
						.setExperience(exp);
			}
		}, (long) time);
	}

	@EventHandler
	private void onEntityChangeBlockEvent(EntityChangeBlockEvent event) {
		if (event.getEntityType().equals(EntityType.ENDERMAN)
				|| event.getEntityType().equals(EntityType.WITHER)
				|| event.getEntityType().equals(EntityType.ENDER_DRAGON)) {
			for (ProtectedArea pa : areas) {
				if (pa.equals(event.getBlock().getLocation())) {
					event.setCancelled(true);
					return;
				}
			}
		}
	}

	@EventHandler
	private void onBlockBurnBlockBurnEvent(BlockBurnEvent event) {
		for (ProtectedArea pa : areas) {
			if (pa.equals(event.getBlock().getLocation())) {
				event.setCancelled(true);
			}
		}
	}

	private boolean status(CommandSender sender, String[] arg3) {

		final CommandSender senderF = sender;
		final String[] arg = arg3;

		Bukkit.getScheduler().runTaskAsynchronously(this, new Runnable() {
			@Override
			public void run() {
				if (arg.length == 0) {
					GregorianCalendar gc = new GregorianCalendar();

					Iterator<PlayerStatus> it = playerStatuses.iterator();
					List<PlayerStatus> statusesToDelete = new ArrayList<PlayerStatus>();
					while (it.hasNext()) {
						PlayerStatus ps = it.next();
						if (gc.getTimeInMillis()
								- ps.getTime().getTimeInMillis() > 604800000) {
							statusesToDelete.add(ps);
						}
					}

					for (PlayerStatus ps : statusesToDelete) {
						deleteStatusFromDB(ps);
					}

					String messageToSend = "";

					it = playerStatuses.iterator();

					while (it.hasNext()) {
						PlayerStatus ps = it.next();
						messageToSend += serenityPlayers.get(ps.getUuid())
								.getChatColor()
								+ serenityPlayers.get(ps.getUuid()).getName()
								+ ps.toString();
					}

					final String messageToSendF = messageToSend;

					Bukkit.getScheduler().scheduleSyncDelayedTask(global,
							new Runnable() {
								@Override
								public void run() {
									senderF.sendMessage(messageToSendF);
								}
							});

					return;
				} else {
					String status = "";
					for (int i = 0; i < arg.length; i++) {
						if (i != arg.length - 1) {
							status += arg[i] + " ";
						} else {
							status += arg[i];
						}
					}

					UUID uuid = null;

					if (senderF instanceof Player) {
						uuid = ((Player) senderF).getUniqueId();
					} else {
						for (Map.Entry<UUID, SerenityPlayer> entry : serenityPlayers
								.entrySet())
							if (entry.getValue().isOp()) {
								uuid = entry.getValue().getUUID();
							}
					}

					PlayerStatus ps = new PlayerStatus(status,
							new GregorianCalendar(), uuid);

					addPlayerStatus(ps);
					// sender.sendMessage("§2Your status was updated!");

					String msg = getTranslationLanguage(senderF,
							stringKeys.STATUSSUCCESS.toString());
					final String msgF = msg;

					Bukkit.getScheduler().scheduleSyncDelayedTask(global,
							new Runnable() {
								@Override
								public void run() {
									senderF.sendMessage(msgF);
								}
							});
				}
				return;

			}
		});
		return true;

	}

	protected void deleteStatusFromDB(PlayerStatus ps) {
		String sql = "DELETE FROM Status WHERE UUID='"
				+ ps.getUuid().toString() + "'";
		executeSQLAsync(sql);
	}

	private boolean getPortal(CommandSender sender) {
		Location l = Bukkit.getServer().getPlayer(sender.getName())
				.getLocation();
		if (l.getWorld().getName().equals(("world"))) {

			String msg = getTranslationLanguage(sender,
					stringKeys.PORTALNETHER.toString());
			sender.sendMessage(String.format(msg, (int) (l.getX() / 8),
					(int) (l.getZ() / 8)));

			/*
			 * sender.sendMessage(
			 * "§l§5If you want your portal to exit here, build it in the nether at: "
			 * + "§r§d\n   X: §l§3" + (int) (l.getX() / 8) + "§r§d\n   Z: §l§3"
			 * + (int) (l.getZ() / 8));
			 */
		} else {

			String msg = getTranslationLanguage(sender,
					stringKeys.PORTALOVERWORLD.toString());
			sender.sendMessage(String.format(msg, (int) (l.getX() * 8),
					(int) (l.getZ() * 8)));

			/*
			 * sender.sendMessage(
			 * "§l§5If you build your portal here you will end up at: " +
			 * "§r§d\n   X: §l§3" + (int) (l.getX() * 8) + "§r§d\n   Z: §l§3" +
			 * (int) (l.getZ() * 8));
			 */
		}
		return true;
	}

	private boolean lastLogin(CommandSender sender, String[] arg3) {
		if (arg3.length == 0) {
			arg3 = new String[1];
			arg3[0] = "1";
		}
		if (arg3.length > 0) {
			int page = 0;
			boolean searching = false;
			try {
				page = Integer.parseInt(arg3[0]);
				page--;
			} catch (NumberFormatException e) {
				searching = true;
			}

			if (!searching) {
				getPageNLogins(sender, page);
				return true;
			}

			final String searchTerm = arg3[0];
			final CommandSender senderf = sender;
			Bukkit.getScheduler().runTaskLaterAsynchronously(this,
					new Runnable() {
						@Override
						public void run() {
							for (Map.Entry<UUID, SerenityPlayer> entry : serenityPlayers
									.entrySet()) {
								if (entry.getValue().getName().toUpperCase()
										.contains(searchTerm.toUpperCase())) {
									if (entry.getValue().isWhitelisted() == true
											&& entry.getValue().isBanned() == false) {
										if (senderf instanceof Player) {
											sendRawPacket(
													((Player) senderf)
															.getPlayer(),
													FancyText
															.GenerateFancyText(
																	entry.getValue()
																			.getChatColor()
																			+ entry.getValue()
																					.getName()
																			+ ": §7"
																			+ convertToHoursAndMinutesSinceNoSeconds(entry
																					.getValue()
																					.getLastPlayed()),
																	FancyText.SUGGEST_COMMAND,
																	"/msg "
																			+ entry.getValue()
																					.getName()
																			+ " ",
																	FancyText.SHOW_TEXT,
																	"/msg "
																			+ entry.getValue()
																					.getName()));
										} else {
											senderf.sendMessage(entry
													.getValue().getChatColor()
													+ entry.getValue()
															.getName()
													+ ": §7"
													+ convertToHoursAndMinutesSinceNoSeconds(entry
															.getValue()
															.getLastPlayed()));
										}
									}
								}
							}
						}
					}, 0L);
		}

		return true;
	}

	private void getPageNLogins(CommandSender sender, int page) {
		final CommandSender senderf = sender;
		final int pagef = page;
		Bukkit.getScheduler().runTaskLaterAsynchronously(this, new Runnable() {
			@Override
			public void run() {
				List<SerenityPlayer> splist = new ArrayList();
				for (Map.Entry<UUID, SerenityPlayer> entry : serenityPlayers
						.entrySet()) {
					if (entry.getValue().isBanned() == false
							&& entry.getValue().isWhitelisted() == true)
						splist.add(entry.getValue());
				}
				Collections.sort(splist,
						Comparator.comparing(SerenityPlayer::getLastPlayed));
				Collections.reverse(splist);

				Player p = null;
				if (senderf instanceof Player) {
					p = (Player) senderf;
				}
				String ret = "";
				for (int i = pagef * 8; i < pagef * 8 + 8; i++) {
					if (i < splist.size()) {
						if (splist.get(i) != null) {
							ret += splist.get(i).getChatColor()
									+ splist.get(i).getName()
									+ ": §7"
									+ convertToHoursAndMinutesSinceNoSeconds(splist
											.get(i).getLastPlayed()) + "§r\n";

							if (p != null) {
								sendRawPacket(
										p,
										FancyText
												.GenerateFancyText(
														splist.get(i)
																.getChatColor()
																+ splist.get(i)
																		.getName()
																+ ": §7"
																+ convertToHoursAndMinutesSinceNoSeconds(splist
																		.get(i)
																		.getLastPlayed()),
														FancyText.SUGGEST_COMMAND,
														"/msg "
																+ splist.get(i)
																		.getName()
																+ " ",
														FancyText.SHOW_TEXT,
														"/msg "
																+ splist.get(i)
																		.getName()));
							}
						}
					}
				}
				if (p == null) {
					senderf.sendMessage(ret);
				}
				if (senderf instanceof Player) {
					int next = pagef + 2;
					int prev = pagef;
					int curr = pagef + 1;
					String nav = "  --  Page " + (pagef + 1) + " of "
							+ (splist.size() / 8);
					String fancyNext = FancyText.GenerateFancyText("§a  --->",
							FancyText.RUN_COMMAND, "/lastseen " + next,
							FancyText.SHOW_TEXT, "Next");
					String fancyPrev = FancyText.GenerateFancyText(nav
							+ "§a  <---", FancyText.RUN_COMMAND, "/lastseen "
							+ prev, FancyText.SHOW_TEXT, "Previous");
					String phonyNext = FancyText.GenerateFancyText("§7  --->",
							FancyText.RUN_COMMAND, "/lastseen " + curr, null,
							null);
					String phonyPrev = FancyText.GenerateFancyText(nav
							+ "§7  <---", null, null, FancyText.RUN_COMMAND,
							"/lastseen " + curr);

					if (curr == splist.size() / 8) {
						sendRawPacket(p, "[" + fancyPrev + "," + phonyNext
								+ "]");
						return;
					}
					if (curr == 1) {
						sendRawPacket(p, "[" + phonyPrev + "," + fancyNext
								+ "]");
						return;
					}
					if (curr > 1 && pagef + 1 < splist.size()) {
						sendRawPacket(p, "[" + fancyPrev + "," + fancyNext
								+ "]");
					}
				}

			}
		}, 0L);
	}

	private void sendRawPacket(Player p, String s) {
		String json = s;
		Packet<?> packet = new PacketPlayOutChat(ChatSerializer.a(json));

		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);

	}

	public static String getDurationBreakdownNoSeconds(long millis) {
		if (millis < 0) {
			return "";
		}

		long days = TimeUnit.MILLISECONDS.toDays(millis);
		millis -= TimeUnit.DAYS.toMillis(days);
		long hours = TimeUnit.MILLISECONDS.toHours(millis);
		millis -= TimeUnit.HOURS.toMillis(hours);
		long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);

		StringBuilder sb = new StringBuilder(128);

		if (days > 0) {
			sb.append(days);
			if (days == 1) {
				sb.append(" Day ");
			} else {
				sb.append(" Days ");
			}
		}

		if (hours > 0) {
			sb.append(hours);

			if (hours == 1) {
				sb.append(" Hour ");
			} else {
				sb.append(" Hours ");
			}
		}

		sb.append(minutes);
		sb.append(" Minutes ");

		return (sb.toString());
	}

	public static String getDurationBreakdown(long millis) {
		if (millis < 0) {
			return "";
		}

		long days = TimeUnit.MILLISECONDS.toDays(millis);
		millis -= TimeUnit.DAYS.toMillis(days);
		long hours = TimeUnit.MILLISECONDS.toHours(millis);
		millis -= TimeUnit.HOURS.toMillis(hours);
		long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
		millis -= TimeUnit.MINUTES.toMillis(minutes);
		long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

		StringBuilder sb = new StringBuilder(128);

		if (days > 0) {
			sb.append(days);
			if (days == 1) {
				sb.append(" Day ");
			} else {
				sb.append(" Days ");
			}
		}

		if (hours > 0) {
			sb.append(hours);

			if (hours == 1) {
				sb.append(" Hour ");
			} else {
				sb.append(" Hours ");
			}
		}

		sb.append(minutes);
		sb.append(" Minutes ");
		sb.append(seconds);
		sb.append(" Seconds");

		return (sb.toString());
	}

	private boolean afk(CommandSender sender, String[] arg3) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			SerenityPlayer sp = serenityPlayers.get(player.getUniqueId());

			if (arg3.length > 0) {
				if (votingForDay) {
					int total = 0;

					player.setSleepingIgnored(true);
					int count = 0;
					for (Player p : Bukkit.getOnlinePlayers()) {
						if (!p.isOp()) {
							total++;
							if (p.isSleepingIgnored() || p.isSleeping()) {
								count++;
							}
						}
					}

					double percentage = (double) count / total;
					percentage *= 100.0;

					String result = new DecimalFormat("##.##")
							.format(percentage);

					String msg = getTranslationLanguage(sender,
							stringKeys.BEDVOTING.toString());

					sender.sendMessage(msg + "§6" + result + "%");

					/*
					 * sender.sendMessage("§9" +
					 * "Waiting for >50% of players to get in bed or type /ok. Current: "
					 * + "§b" + result + "%");
					 */

					checkHalfIgnored();
					return true;

				} else {

					String msg = getTranslationLanguage(sender,
							stringKeys.BEDOKNOTVOTING.toString());
					sender.sendMessage(msg);

					/*
					 * sender.sendMessage("§c" +
					 * "This can only be done after somebody enters a bed");
					 */
					return true;

				}
			}

			if (sp.isAFK()) {

				String msg = getTranslationLanguage(sender,
						stringKeys.AFKALREADYAFK.toString());
				sender.sendMessage(msg);

				// sender.sendMessage("§cYou are already AFK!");
				return true;
			}

			setAfk(sp, false);
		}

		return true;
	}

	private boolean getTime(CommandSender sender, String[] arg3) {
		Long time = Bukkit.getWorld("world").getTime();
		String msg = "§7Minecraft time: §e" + time + " §7(";

		String s = "";
		if (time < 800) {
			s += "§emorning§7)";
		} else if (time < 10000) {
			s += "§6day§7)";
		} else if (time < 12500) {
			s += "§7dusk§7)";
		} else if (time < 22500) {
			s += "§8night§7)";
		} else {
			s += "§7pre-morning§7)";
		}
		sender.sendMessage(msg + s);
		String timeStamp = new SimpleDateFormat("HH:mm MMM dd, YYYY")
				.format(Calendar.getInstance().getTime());
		sender.sendMessage("§7Server time: §e" + timeStamp);
		return true;
	}

	/*
	 * private static String getTimeString(){ Long time =
	 * Bukkit.getWorld("world").getTime(); String msg = "§7Minecraft time: §e" +
	 * time + " §7(";
	 * 
	 * String s = ""; if (time < 800) { s += "§eMorning§7)"; } else if (time <
	 * 10000) { s += "§6Day§7)"; } else if (time < 12500) { s += "§7Dusk§7)"; }
	 * else if (time < 22500) { s += "§8Night§7)"; } else { s += "§7Dawn§7)"; }
	 * sender.sendMessage(msg + s); String timeStamp = new
	 * SimpleDateFormat("HH:mm MMM dd, YYYY")
	 * .format(Calendar.getInstance().getTime()); }
	 */

	private boolean mailTo(CommandSender sender, String[] arg3) {
		if (arg3.length < 1) {
			String s = "";
			s += "\n§cType a mailbox name!";
			sender.sendMessage(s);
			return false;
		}
		String mailto = arg3[0];
		/*
		 * if (mailto.equals("EVERYBODY18104")) { for (Mailbox mb : mailBoxes) {
		 * mailItemsTo(sender, mb.name, true); } return true; }
		 */

		return mailItemsTo(sender, mailto);
	}

	private boolean mailItemsTo(CommandSender sender, String mailto) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			boolean receivingMailBoxExists = false;
			boolean sendingMailBoxExists = hasAMailbox(p.getUniqueId());
			Mailbox sendingMailbox = new Mailbox();
			Mailbox receivingMailbox = new Mailbox();
			for (Mailbox mb : mailBoxes) {
				if (mb.name.toLowerCase().contains(mailto.toLowerCase())) {
					receivingMailBoxExists = true;
					receivingMailbox.uuid = mb.uuid;
					receivingMailbox.location = mb.location;
					receivingMailbox.name = mb.name;
				}
				if (mb.uuid.equals(p.getUniqueId())) {
					sendingMailbox.uuid = mb.uuid;
					sendingMailbox.location = mb.location;
				}
			}

			if (!sendingMailBoxExists) {

				String msg = getTranslationLanguage(sender,
						stringKeys.MAILDOESNTHAVEMAILBOX.toString());
				sender.sendMessage(msg);

				// sender.sendMessage("§cYou don't have a mailbox!  Put a chest on top of a fence post!");
				return true;
			}
			if (!receivingMailBoxExists) {
				sender.sendMessage("§cThere is no mailbox with §6\"" + mailto
						+ "\"§c in it.  Check your spelling!");
				return true;
			}

			if (sendingMailbox.uuid.equals(receivingMailbox.uuid)) {

				String msg = getTranslationLanguage(sender,
						stringKeys.MAILTOSELF.toString());
				sender.sendMessage(msg);

				// sender.sendMessage("§cYou can't send mail to yourself!");
				return true;
			}

			Chest sendingChest = (Chest) sendingMailbox.location.getBlock()
					.getState();
			Chest receivingChest = (Chest) receivingMailbox.location.getBlock()
					.getState();

			ItemStack[] sendingItems = sendingChest.getInventory()
					.getContents();
			ItemStack[] receivingItems = receivingChest.getInventory()
					.getContents();

			int sendingItemsCount = 0;
			int receivingItemsCount = 0;

			for (int i = 0; i < sendingItems.length; i++) {
				if (sendingItems[i] != null) {
					sendingItemsCount++;
				}
			}

			for (int i = 0; i < receivingItems.length; i++) {
				if (receivingItems[i] != null) {
					receivingItemsCount++;
				}
			}

			if (sendingItemsCount == 0) {
				// sender.sendMessage("§cYou don't have any items in your mailbox!");

				String msg = getTranslationLanguage(sender,
						stringKeys.MAILEMPTY.toString());
				sender.sendMessage(msg);

				return true;
			}

			if (receivingItemsCount == receivingItems.length) {

				String msg = getTranslationLanguage(sender,
						stringKeys.MAILFULL.toString());
				sender.sendMessage(String.format(msg, receivingMailbox.name));

				/*
				 * sender.sendMessage("§6" + receivingMailbox.name +
				 * "§2's mailbox is full!");
				 */
				return true;
			}

			if (receivingItems.length - receivingItemsCount < sendingItemsCount) {

				String msg = getTranslationLanguage(sender,
						stringKeys.MAILNOTENOUGHSPACE.toString());
				sender.sendMessage(String.format(msg, receivingMailbox.name,
						((receivingItems.length - receivingItemsCount) - 1)));

				/*
				 * sender.sendMessage("§6" + receivingMailbox.name +
				 * "§2's mailbox can only hold " + ((receivingItems.length -
				 * receivingItemsCount) - 1) +
				 * " more slots!  Send less items!");
				 */
				return true;
			}

			for (int i = 0; i < sendingItems.length; i++) {
				if (sendingItems[i] != null) {
					receivingChest.getInventory().addItem(sendingItems[i]);
					sendingChest.getInventory().remove(sendingItems[i]);
					receivingChest.update();
					sendingChest.update();
				}
			}

			String msg = getTranslationLanguage(sender,
					stringKeys.MAILSUCCESS.toString());
			sender.sendMessage(String.format(msg, receivingMailbox.name));
			/*
			 * sender.sendMessage("§2Your items were sent to §6" +
			 * receivingMailbox.name + "§2's mailbox!\n");
			 */
			doRandomFirework(receivingMailbox.location.getWorld(),
					receivingMailbox.location);

			return true;
		}
		return false;

	}

	private boolean setCompass(CommandSender sender, String[] arg3) {
		if (arg3.length < 1) {

			String msg = getTranslationLanguage(sender,
					stringKeys.COMPASSHELP.toString());
			sender.sendMessage(msg);

			/*
			 * sender.sendMessage(
			 * "§cNo destination specified.. try: §2\n  \"home\" §3for your bed\n"
			 * + "§2  \"spawn\" §3for the world's spawn\n" +
			 * "§2  \"mailbox\" §3for your mailbox\n" +
			 * "§2  \"here\" §3for your current location\n" +
			 * "  or manually type any <X> <Y> <Z> coordinates" +
			 * "\n§cExamples: §2" + "\n/setcompass home" +
			 * "\n/setcompass 200 64 -200");
			 */
			return true;
		}
		if (arg3.length < 2) {
			if (arg3[0].toLowerCase().equals("spawn")) {

				String msg = getTranslationLanguage(sender,
						stringKeys.COMPASSSPAWN.toString());
				sender.sendMessage(msg);

				// sender.sendMessage("§2Set compass to point to spawn");
				this.getServer()
						.getPlayer(sender.getName())
						.setCompassTarget(
								this.getServer().getPlayer(sender.getName())
										.getWorld().getSpawnLocation());
				return true;
			}
			if (arg3[0].toLowerCase().equals("home")
					|| arg3[0].toLowerCase().equals("bed")) {

				if (this.getServer().getPlayer(sender.getName())
						.getBedSpawnLocation() != null) {
					this.getServer()
							.getPlayer(sender.getName())
							.setCompassTarget(
									this.getServer()
											.getPlayer(sender.getName())
											.getBedSpawnLocation());
					// sender.sendMessage("§2Set compass to point to your bed");

					String msg = getTranslationLanguage(sender,
							stringKeys.COMPASSBED.toString());
					sender.sendMessage(msg);

					return true;
				}
				/*
				 * this.getServer() .getPlayer(sender.getName()) .sendMessage(
				 * "§cYou currently do not have a bed! (Was it destroyed or did someone else sleep in it?) \n"
				 * + "§2If you have a mailbox, you can try /setcompass mailbox"
				 * );
				 */

				String msg = getTranslationLanguage(sender,
						stringKeys.COMPASSBEDDESTROYED.toString());
				sender.sendMessage(msg);

				return true;
			}

			if (arg3[0].toLowerCase().equals("mailbox")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					for (Mailbox mb : mailBoxes) {
						if (p.getUniqueId().equals(mb.uuid)) {
							Bukkit.getServer().getPlayer(sender.getName())
									.setCompassTarget(mb.location);
							/*
							 * Bukkit.getServer() .getPlayer(sender.getName())
							 * .sendMessage(
							 * "§2Set compass to point to your mailbox!");
							 */

							String msg = getTranslationLanguage(sender,
									stringKeys.COMPASSMAILBOX.toString());
							sender.sendMessage(msg);

							return true;
						}

					}
				}

				String msg = getTranslationLanguage(sender,
						stringKeys.COMPASSNOMAILBOX.toString());
				sender.sendMessage(msg);

				/*
				 * Bukkit.getServer() .getPlayer(sender.getName()) .sendMessage(
				 * "§cYou don't have a mailbox!  \n§2Make one at your home!  (Chest on top of a fence post)"
				 * );
				 */
				return true;
			}

			if (arg3[0].toLowerCase().equals("here")) {
				Bukkit.getServer()
						.getPlayer(sender.getName())
						.setCompassTarget(
								Bukkit.getServer().getPlayer(sender.getName())
										.getLocation());

				String msg = getTranslationLanguage(sender,
						stringKeys.COMPASSHERE.toString());
				sender.sendMessage(msg);
				/*
				 * Bukkit.getServer() .getPlayer(sender.getName()) .sendMessage(
				 * "§2Set compass to point to your current location!");
				 */
				return true;
			}

		}
		if (arg3.length == 3) {
			Location l = new Location(this.getServer()
					.getPlayer(sender.getName()).getWorld(),
					Integer.parseInt(arg3[0]), Integer.parseInt(arg3[1]),
					Integer.parseInt(arg3[2]));

			String msg = getTranslationLanguage(sender,
					stringKeys.COMPASSCUST.toString());
			sender.sendMessage(String.format(msg, (int) l.getX(),
					(int) l.getY(), (int) l.getZ()));
			/*
			 * sender.sendMessage("§2Set compass to point to X: §3" + l.getX() +
			 * "§2 Y: §3" + l.getY() + "§2 Z: §3" + l.getZ());
			 */
			Bukkit.getPlayer(sender.getName()).setCompassTarget(l);
			return true;
		}
		return true;
	}

	private boolean setChatColor(CommandSender sender, String[] arg3) {
		if (sender instanceof Player) {
			if (serenityPlayers.get(((Player) sender).getUniqueId())
					.getMinutes() >= 720) {
				String color = "";
				Player p = Bukkit.getPlayerExact(sender.getName());
				SerenityPlayer sp = serenityPlayers.get(p.getUniqueId());
				if (arg3.length == 0) {
					sender.sendMessage("§aHere are your options:  \n"
							+ "§0black, §1dark blue, §2dark green, \n"
							+ "§3dark aqua, §4dark red, §5dark purple, \n"
							+ "§6gold, §7gray, §8dark gray, \n"
							+ "§9blue, §agreen, §baqua, \n"
							+ "§cred, §dlight purple, §eyellow, \n" + "§fwhite");
					return true;
				}

				for (int i = 0; i < arg3.length; i++) {
					color += arg3[i] + " ";
				}

				if (color.equalsIgnoreCase("black ")) {

					sp.setChatColor("§0");

				} else if (color.equalsIgnoreCase("dark blue ")) {
					sp.setChatColor("§1");

				} else if (color.equalsIgnoreCase("dark green ")) {
					sp.setChatColor("§2");

				} else if (color.equalsIgnoreCase("dark aqua ")) {
					sp.setChatColor("§3");

				} else if (color.equalsIgnoreCase("dark red ")) {
					sp.setChatColor("§4");

				} else if (color.equalsIgnoreCase("dark purple ")) {
					sp.setChatColor("§5");

				} else if (color.equalsIgnoreCase("gold ")) {
					sp.setChatColor("§6");

				} else if (color.equalsIgnoreCase("gray ")) {
					sp.setChatColor("§7");

				} else if (color.equalsIgnoreCase("dark gray ")) {
					sp.setChatColor("§8");

				} else if (color.equalsIgnoreCase("blue ")) {
					sp.setChatColor("§9");

				} else if (color.equalsIgnoreCase("green ")) {
					sp.setChatColor("§a");

				} else if (color.equalsIgnoreCase("aqua ")) {
					sp.setChatColor("§b");

				} else if (color.equalsIgnoreCase("red ")) {
					sp.setChatColor("§c");

				} else if (color.equalsIgnoreCase("light purple ")) {
					sp.setChatColor("§d");

				} else if (color.equalsIgnoreCase("yellow ")) {
					sp.setChatColor("§e");

				} else if (color.equalsIgnoreCase("white ")) {
					sp.setChatColor("§f");

				} else if (color.equalsIgnoreCase("scary ")) {
					sp.setChatColor("§k");

				} else {
					sender.sendMessage("§cInvalid color!  Pick one of these:\n"
							+ "§0black, §1dark blue, §2dark green, \n"
							+ "§3dark aqua, §4dark red, §5dark purple, \n"
							+ "§6gold, §7gray, §8dark gray, \n"
							+ "§9blue, §agreen, §baqua, \n"
							+ "§cred, §dlight purple, §eyellow, \n" + "§fwhite");
					return true;
					/*
					 * sender.sendMessage(
					 * "§4Invalid color!  Here are your options:\n§a" +
					 * 
					 * "black, dark blue, dark green,\n" +
					 * "dark aqua, dark red, dark purple,\n" +
					 * "gold, gray, dark gray, blue,\n" + "green, aqua, red,\n"
					 * + "light purple, yellow, white.");
					 */
				}
				if (sender instanceof Player) {
					Player play = (Player) sender;
					play.sendMessage(sp.getChatColor() + "Chat color set!");
					setListNames();
				}

				return true;
			} else {

				String msg = getTranslationLanguage(sender,
						stringKeys.CHATCOLOREARLY.toString());
				sender.sendMessage(msg);

				// sender.sendMessage("§2You can't set your chatcolor yet... Stick around and eventually you will!");
				return true;
			}
		}
		return false;
	}

	private boolean lag(CommandSender sender) {

		if (lags.size() < 10) {

			String msg = getTranslationLanguage(sender,
					stringKeys.LAGWAIT.toString());
			sender.sendMessage(msg);

			// sender.sendMessage("§cWait!");
			return true;
		}

		double tickrate = getTickrate();

		getLogger().info("tickrate:" + tickrate);
		if (tickrate > 20) {
			tickrate = 20.0;
		}

		String message = "§aAverage TPS: §e"
				+ new DecimalFormat("##.##").format(tickrate) + " §f| ";

		if (tickrate < 16) {

			message += "§4";
		} else if (tickrate < 19) {
			message += "§e";
		} else {
			message += "§2";
		}

		message += new DecimalFormat("##.##").format((tickrate / 20) * 100)
				+ "%§3 speed §f| ";

		if (sender instanceof Player) {
			Player p = (Player) sender;
			CraftPlayer cp = (CraftPlayer) p;
			message += "§2Your ping: §e" + cp.getHandle().ping + "§2ms";
		}

		sender.sendMessage(message);

		return true;

	}

	private double getTickrate() {

		if (lags.size() < 10) {
			return 20;
		}

		Long avg = 0L;

		for (int i = 9; i > 1; i--) {
			avg += lags.get(i) - lags.get(i - 1);
		}

		double average = avg / 8;
		average /= 1000;
		double tickrate = 20 / average;

		return tickrate;
	}

	private boolean ignore(CommandSender sender, String[] arg3) {
		if (sender instanceof Player) {
			if (arg3.length != 1) {
				return false;
			} else {
				try {
					Player p = Bukkit.getServer().getPlayerExact(arg3[0]);
					String s = p.getDisplayName();
				} catch (Exception e) {

					String msg = getTranslationLanguage(sender,
							stringKeys.IGNORECOULDNOTFIND.toString());
					sender.sendMessage(msg);

					// sender.sendMessage("§cI could not find that player");
					return true;
				}

				for (int i = 0; i < ignorers.size(); i++) {
					if (ignorers.get(i).player.equals(((Player) sender)
							.getPlayer())) {
						for (int j = 0; j < ignorers.get(i).ignoreList.size(); j++) {
							if (ignorers.get(i).ignoreList.get(j)
									.equalsIgnoreCase(arg3[0])) {
								ignorers.get(i).ignoreList.remove(j);

								String msg = getTranslationLanguage(sender,
										stringKeys.IGNORENOLONGERIGNORING
												.toString());
								sender.sendMessage(String.format(msg, arg3[0]));

								// sender.sendMessage("§2You are no longer ignoring §e"
								// + arg3[0]);
								return true;
							}
						}
						ignorers.get(i).ignoreList.add(arg3[0]);

						String msg = getTranslationLanguage(sender,
								stringKeys.IGNORESUCCESS.toString());
						sender.sendMessage(String.format(msg, arg3[0]));

						// sender.sendMessage("§2You are ignoring §e"
						// + arg3[0]);
						return true;
					}
				}

				Ignoring newIg = new Ignoring(((Player) sender).getPlayer());
				newIg.ignoreList.add(arg3[0]);
				ignorers.add(newIg);

				String msg = getTranslationLanguage(sender,
						stringKeys.IGNORESUCCESS.toString());
				sender.sendMessage(String.format(msg, arg3[0]));
				// sender.sendMessage("§2You are now ignoring §e" +
				// arg3[0]);
				return true;

			}
		}
		return false;
	}

	private boolean vote(CommandSender sender, String[] arg3) {
		/*
		 * if (sender.hasPermission("SerenityPlugins.vote")) { //
		 * sender.sendMessage("No current vote"); // return true; // } if
		 * (arg3.length == 0) { sender.sendMessage(
		 * "§3Do you want the end to continue being reset on Fridays?\n" +
		 * "§3Type §f/vote YES §3to keep resetting or\n" +
		 * "Type §f/vote NO §3to have a permanent End world"); return true; }
		 * 
		 * if (arg3.length > 0) { if (arg3[0].equalsIgnoreCase("yes")) {
		 * sender.sendMessage
		 * ("§aYour vote was cast!  Type §f/vote results §ato see current results"
		 * );
		 * 
		 * voteCfg.getConfig().set("VoteEnd." + sender.getName(), "yes");
		 * voteCfg.saveConfig(); voteCfg.reloadConfig(); return true; } if
		 * (arg3[0].equalsIgnoreCase("no")) { sender.sendMessage(
		 * "§aYour vote was cast!  Type §f/vote results §ato see current results"
		 * ); voteCfg.getConfig() .set("VoteEnd." + sender.getName(), "no");
		 * voteCfg.saveConfig(); voteCfg.reloadConfig(); return true; } if
		 * (arg3[0].equalsIgnoreCase("results")) { ConfigurationSection
		 * votesFromConfig = voteCfg.getConfig()
		 * .getConfigurationSection("VoteEnd");
		 * 
		 * ArrayList<String> votes = new ArrayList<String>(); for (String key :
		 * votesFromConfig.getKeys(false)) {
		 * votes.add(votesFromConfig.getString(key)); }
		 * 
		 * int votesToReset = 0; int votesToKeep = 0; for (int i = 0; i <
		 * votes.size(); i++) { if (votes.get(i).equalsIgnoreCase("yes")) {
		 * votesToReset++; } if (votes.get(i).equalsIgnoreCase("no")) {
		 * votesToKeep++; } }
		 * 
		 * sender.sendMessage("§3Total votes: §b" + (votesToReset + votesToKeep)
		 * + "\n§5Reset: §2" + new DecimalFormat("##.##") .format((double)
		 * ((double) votesToReset / (double) (votesToKeep + votesToReset) *
		 * 100.00)) + "%" + "\n§5Permanent:  §2" + new DecimalFormat("##.##")
		 * .format((double) ((double) votesToKeep / (double) (votesToKeep +
		 * votesToReset) * 100.00)) + "%"); return true; }
		 * 
		 * return true; } } else { noPerms(sender); return true; }
		 */

		return true;
	}

	private void addPlayerStatus(PlayerStatus ps) {

		for (Iterator<PlayerStatus> iterator = playerStatuses.iterator(); iterator
				.hasNext();) {
			PlayerStatus ps1 = iterator.next();
			if (ps1.getUuid().equals(ps.getUuid())) {
				iterator.remove();
			}
		}

		playerStatuses.add(ps);
		ps.setStatus(ps.getStatus().replace('\'', '`'));
		ps.setStatus(ps.getStatus().replace('\"', '|'));
		String sql = "DELETE From Status where UUID='"
				+ ps.getUuid().toString() + "';";
		executeSQLAsync(sql);

		sql = "INSERT INTO Status (UUID, Time, Status) VALUES ('"
				+ ps.getUuid() + "','" + System.currentTimeMillis() + "','"
				+ ps.getStatus() + "');";
		String sqlf = sql;

		Bukkit.getScheduler().runTaskLaterAsynchronously(this, new Runnable() {
			@Override
			public void run() {
				executeSQLAsync(sqlf);
			}
		}, 20L);
		ps.setStatus(ps.getStatus().replace('`', '\''));
		ps.setStatus(ps.getStatus().replace('|', '\"'));

	}

	public void noPerms(CommandSender p) {
		String msg = getTranslationLanguage(p, stringKeys.NOPERMS.toString());
		p.sendMessage(msg);
	}

	@EventHandler
	public void onExplosion(EntityExplodeEvent event) {
		for (Mailbox mb : mailBoxes) {
			for (Block b : event.blockList()) {
				if (b.getLocation().equals(mb.location)) {
					event.setCancelled(true);
					return;
				}
			}
		}

		for (ProtectedArea pa : areas) {
			for (Block b : event.blockList()) {
				if (pa.equals(b.getLocation())) {
					event.setCancelled(true);
					return;
				}
			}
		}
	}

	@EventHandler
	public void onChestPlace(BlockPlaceEvent event) {
		if (event.getBlock().getType().equals(Material.CHEST)
				|| event.getBlock().getType().equals(Material.TRAPPED_CHEST)) {
			for (Mailbox mb : mailBoxes) {
				if (event.getBlock().equals(
						mb.location.getBlock().getRelative(BlockFace.NORTH))
						|| event.getBlock().equals(
								mb.location.getBlock().getRelative(
										BlockFace.SOUTH))
						|| event.getBlock().equals(
								mb.location.getBlock().getRelative(
										BlockFace.EAST))
						|| event.getBlock().equals(
								mb.location.getBlock().getRelative(
										BlockFace.WEST))) {
					event.setCancelled(true);

					String msg = getTranslationLanguage(event.getPlayer(),
							stringKeys.MAILTRIEDTOEXPANDMAILBOX.toString());
					event.getPlayer().sendMessage(msg);

					/*
					 * event.getPlayer().sendMessage(
					 * "§cSorry, you cannot expand mailboxes!");
					 */
					return;
				}
			}
		}
	}

	@EventHandler
	public void onPossibleGriefPlace(BlockPlaceEvent event) {
		if (event.getBlock().getType().equals(Material.TNT)) {
			int x = (int) event.getBlock().getLocation().getX();
			int y = (int) event.getBlock().getLocation().getY();
			int z = (int) event.getBlock().getLocation().getZ();
			getLogger().info(
					"§4" + event.getPlayer().getName() + " §cplaced TNT at §6"
							+ x + " " + y + " " + z);

		}
	}

	@EventHandler
	public void onFireworksBlockPlace(BlockPlaceEvent event) {
		if (event.getBlock().getType().equals(Material.GOLD_BLOCK)) {
			if (event.getBlock().getRelative(BlockFace.DOWN).getType()
					.equals(Material.OBSIDIAN)) {
				Boolean exists = false;
				for (int i = 0; i < fireworkShowLocations.size(); i++) {
					if (fireworkShowLocations.get(i).name.equals(event
							.getPlayer().getDisplayName())) {
						exists = true;
					}
				}
				if (!exists) {
					String path = "Fireworks."
							+ event.getPlayer().getDisplayName();
					int x = (int) event.getBlock().getLocation().getX();
					int y = (int) event.getBlock().getLocation().getY();
					int z = (int) event.getBlock().getLocation().getZ();
					String[] loc = { event.getBlock().getWorld().getName(),
							"" + x, "" + y, "" + z };

					fireworksCfg.getConfig().set(path, loc);
					fireworksCfg.saveConfig();
					fireworksCfg.reloadConfig();
					FireWorkShow fw = new FireWorkShow(event.getPlayer()
							.getDisplayName(), event.getBlock().getLocation());
					fireworkShowLocations.add(fw);

					String msg = getTranslationLanguage(event.getPlayer(),
							stringKeys.FIREWORKSCREATE.toString());
					event.getPlayer().sendMessage(msg);

					/*
					 * event.getPlayer() .sendMessage(
					 * "§2You made a fireworks show block!  \nWhen it is gold, right click it for a show!"
					 * );
					 */
				} else {
					FireWorkShow fws = new FireWorkShow(
							"Something went terribly wrong", event.getBlock()
									.getLocation());
					for (int i = 0; i < fireworkShowLocations.size(); i++) {
						if (fireworkShowLocations.get(i).name.equals(event
								.getPlayer().getDisplayName())) {
							fws = fireworkShowLocations.get(i);
						}
					}
					Location l = fws.getLocation();

					String msg = getTranslationLanguage(event.getPlayer(),
							stringKeys.FIREWORKSEXISTS.toString());
					event.getPlayer().sendMessage(
							String.format(msg, fws.getLocation().getWorld()
									.getName(), (int) l.getX(), (int) l.getY(),
									(int) l.getZ()));
					/*
					 * event.getPlayer().sendMessage(
					 * "§cYou already have a fireworks show block in the §3" +
					 * fws.getLocation().getWorld().getName() + "§c at: " +
					 * "\n§3X:§2 " + l.getX() + "\n§3Y:§2 " + l.getY() +
					 * "\n§3Z:§2 " + l.getZ() +
					 * "\n§cPlease destroy that one first!");
					 */
					event.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void onFireworkBlockClick(PlayerInteractEvent event) {
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if (event.getClickedBlock().getType().equals(Material.GOLD_BLOCK)
					|| event.getClickedBlock().getType()
							.equals(Material.REDSTONE_BLOCK)) {
				boolean thisIsAShowBlock = false;
				FireWorkShow fw = new FireWorkShow();
				Location showLoc = new Location(Bukkit.getWorld("world"), 0, 0,
						0);
				for (int i = 0; i < fireworkShowLocations.size(); i++) {
					if (fireworkShowLocations.get(i).getLocation()
							.equals(event.getClickedBlock().getLocation())) {
						thisIsAShowBlock = true;
						showLoc = fireworkShowLocations.get(i).getLocation();
						if (fireworkShowLocations.get(i).isActive) {
							String s = "§cIt's cooling down!";
							Long lastShow = fireworkShowLocations.get(i).lastShow
									.getTimeInMillis();
							Long now = new GregorianCalendar()
									.getTimeInMillis();
							if (600000 - (now - lastShow) < 60000) {
								s += "  Please wait "
										+ (600000 - (now - lastShow)) / 1000
										+ " seconds!";
							} else {
								s += "  Please wait "
										+ (600000 - (now - lastShow)) / 60000
										+ " minutes!";
							}

							event.getPlayer().sendMessage(s);
							return;
						}
						fireworkShowLocations.get(i).setActive(true);
						getLogger().info(
								event.getPlayer().getDisplayName()
										+ " started a fireworks show!");
						fireworkShowLocations.get(i).setLastShow(
								new GregorianCalendar());
					}
				}

				if (thisIsAShowBlock) {
					startFireworkShow(showLoc);

				}
			}
		}
	}

	private void startFireworkShow(Location showLoc) {
		showLoc.getWorld().playSound(showLoc, Sound.AMBIENCE_THUNDER, 80, 1);

		ArrayList<FireworkLocation> locations = new ArrayList<FireworkLocation>();

		for (double i = 0.0; i < 360.0; i += 10) {
			double angle = i * Math.PI / 180;
			int x = (int) (showLoc.getX() + 30 * Math.cos(angle));
			int z = (int) (showLoc.getZ() + 30 * Math.sin(angle));
			Location l = new Location(showLoc.getWorld(), x, 0, z);
			locations.add(new FireworkLocation(l));
		}

		Random rand = new Random();

		for (int i = 80; i < 300; i += 20) {
			final int j = rand.nextInt(locations.size());
			final Location l = locations.get(j).getLocation();
			l.setY(l.getWorld().getHighestBlockYAt(l));
			Bukkit.getScheduler().runTaskLater(this, new Runnable() {
				@Override
				public void run() {
					doRandomFirework(l.getWorld(), l);
				}
			}, i);
		}

		int counter = 360;

		Iterator<FireworkLocation> it = locations.iterator();
		while (it.hasNext()) {
			counter += 5;
			FireworkLocation fl = it.next();
			final Location l = fl.getLocation();
			l.setY(l.getWorld().getHighestBlockYAt(l));
			Bukkit.getScheduler().runTaskLater(this, new Runnable() {
				@Override
				public void run() {
					doRandomFirework(l.getWorld(), l);
				}
			}, counter + 0L);
		}

		it = locations.iterator();
		while (it.hasNext()) {
			counter += 3;
			FireworkLocation fl = it.next();
			final Location l = fl.getLocation();
			l.setY(l.getWorld().getHighestBlockYAt(l));
			Bukkit.getScheduler().runTaskLater(this, new Runnable() {
				@Override
				public void run() {
					doRandomFirework(l.getWorld(), l);
				}
			}, counter + 0L);
		}

		for (int i = counter; i < 1200; i += 2) {
			final int j = rand.nextInt(locations.size());
			final Location l = locations.get(j).getLocation();
			l.setY(l.getWorld().getHighestBlockYAt(l));
			Bukkit.getScheduler().runTaskLater(this, new Runnable() {
				@Override
				public void run() {
					doRandomFirework(l.getWorld(), l);
				}
			}, i);
		}

		counter += 560;

		it = locations.iterator();
		while (it.hasNext()) {
			counter += 1;
			FireworkLocation fl = it.next();
			final Location l = fl.getLocation();
			l.setY(l.getWorld().getHighestBlockYAt(l));
			Bukkit.getScheduler().runTaskLater(this, new Runnable() {
				@Override
				public void run() {
					doRandomFirework(l.getWorld(), l);
				}
			}, counter + 0L);
		}

	}

	@EventHandler
	public void onPistonPush(BlockPistonExtendEvent event) {
		for (Block b : event.getBlocks()) {
			Location l = b.getLocation();
			for (FireWorkShow fl : fireworkShowLocations) {
				if (fl.getLocation().equals(b.getLocation())) {
					getLogger().info("§cAttempted to push a firework block");
					event.setCancelled(true);
				}
			}
		}/*
		 * for (int i = 0; i < fireworkShowLocations.size(); i++) { Block b =
		 * fireworkShowLocations.get(i).getLocation().getBlock(); if
		 * (event.getBlocks().contains(b)) {
		 * getLogger().info("Attempted to push a firework block");
		 * event.setCancelled(true); } }
		 */
	}

	@EventHandler
	public void onPlayerFireworksBlockLeftClick(BlockBreakEvent event) {
		if (event.getBlock().getType().equals(Material.REDSTONE_BLOCK)) {
			for (int i = 0; i < fireworkShowLocations.size(); i++) {
				if (fireworkShowLocations.get(i).getLocation()
						.equals(event.getBlock().getLocation())) {

					String msg = getTranslationLanguage(event.getPlayer(),
							stringKeys.FIREWORKSEARLYBREAKATTEMPT.toString());
					event.getPlayer().sendMessage(msg);
					/*
					 * event.getPlayer() .sendMessage(
					 * "§cYou may not break a fireworks block that is cooling down!"
					 * );
					 */
					event.setCancelled(true);
					return;
				}
			}
		}

		if (event.getBlock().getType().equals(Material.GOLD_BLOCK)) {
			for (int i = 0; i < fireworkShowLocations.size(); i++) {
				if (fireworkShowLocations.get(i).getLocation()
						.equals(event.getBlock().getLocation())) {
					if (!fireworkShowLocations.get(i).name.equals(event
							.getPlayer().getDisplayName())) {

						String msg = getTranslationLanguage(event.getPlayer(),
								stringKeys.FIREWORKNONOWNERBREAK.toString());
						event.getPlayer().sendMessage(msg);

						/*
						 * event.getPlayer() .sendMessage(
						 * "§cOnly this fireworks block's creator can destroy this block!"
						 * );
						 */
						event.setCancelled(true);
						return;
					} else {
						String path = "Fireworks."
								+ event.getPlayer().getDisplayName();
						fireworksCfg.getConfig().set(path, null);
						fireworksCfg.getConfig().options().copyDefaults();

						fireworksCfg.saveConfig();
						fireworksCfg.reloadConfig();
						fireworkShowLocations.remove(fireworkShowLocations
								.get(i));

						String msg = getTranslationLanguage(event.getPlayer(),
								stringKeys.FIREWORKBREAKSUCCESS.toString());
						event.getPlayer().sendMessage(msg);
						/*
						 * event.getPlayer().sendMessage(
						 * "§2You destroyed your fireworks show block");
						 */
					}
				}
			}
		}
	}

	@EventHandler
	public void onDragonDeath(EntityDeathEvent event) {
		if (event.getEntity().getType().equals(EntityType.ENDER_DRAGON)) {
			List<String> slayers = new ArrayList<String>();

			for (Iterator<? extends Player> i = Bukkit.getServer()
					.getOnlinePlayers().iterator(); i.hasNext();) {
				Player p = i.next();

				if (p.getWorld().getName().equals("world_the_end")) {
					if (!p.isOp()) {
						slayers.add(p.getDisplayName());
					}
				}
			}

			final List<String> finalSlayers = slayers;
			Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {

				@Override
				public void run() {
					if (finalSlayers.size() == 0) {
						return;
					}
					if (finalSlayers.size() == 1) {
						Bukkit.getServer().broadcastMessage(
								"§4" + finalSlayers.get(0)
										+ "§5 is a dragon slayer!");
					} else {
						String s = "§4";
						for (int i = 0; i < finalSlayers.size(); i++) {
							s += finalSlayers.get(i) + " & ";
						}

						s = s.substring(0, s.length() - 2);

						s += "§5are dragon slayers!";
						Bukkit.getServer().broadcastMessage(s);
					}
				}

			}, 400L);

		}
	}

	private void checkAndClearAllChunks() {

		Long before = System.currentTimeMillis();
		Boolean hadToRun = false;
		// Bukkit.getLogger().info("Starting to check all chunks!");
		List<World> worlds = Bukkit.getServer().getWorlds();
		for (int i = 0; i < worlds.size(); i++) {
			Chunk[] chunks = worlds.get(i).getLoadedChunks();
			for (int j = 0; j < chunks.length; j++) {
				Entity[] entities = chunks[j].getEntities();
				if (entities.length > 70) {
					getLogger().info(
							"WOAH TOO MANY ENTITIES AT " + chunks[j].getX()
									* 16 + " , " + chunks[j].getZ() * 16);
					hadToRun = true;
					Random r = new Random();
					int count = 0;
					String s = "";
					for (int k = 0; k < entities.length; k++) {
						s += entities[k].toString() + ", ";
						if (r.nextBoolean()) {
							if (entities[k].getType()
									.equals(EntityType.CHICKEN)
									|| entities[k].getType().equals(
											EntityType.BLAZE)
									|| entities[k].getType().equals(
											EntityType.CAVE_SPIDER)
									|| entities[k].getType().equals(
											EntityType.ENDERMAN)
									|| entities[k].getType().equals(
											EntityType.COW)
									|| entities[k].getType().equals(
											EntityType.HORSE)
									|| entities[k].getType().equals(
											EntityType.PIG)
									|| entities[k].getType().equals(
											EntityType.SHEEP)
									|| entities[k].getType().equals(
											EntityType.ZOMBIE)
									|| entities[k].getType().equals(
											EntityType.SKELETON)
									|| entities[k].getType().equals(
											EntityType.WOLF)
									|| entities[k].getType().equals(
											EntityType.OCELOT)
									|| entities[k].getType().equals(
											EntityType.PIG_ZOMBIE)
									|| entities[k].getType().equals(
											EntityType.EXPERIENCE_ORB)) {
								if (entities[k].getCustomName() == null) {
									entities[k].remove();
									count++;
								}
							}
						}
					}
					// getLogger().info(s);
					getLogger().info("Deleted " + count + " entities.");

				}
			}
		}
		Long elapsed = System.currentTimeMillis() - before;

		if (hadToRun) {
			Bukkit.getLogger().info("It took: " + elapsed + "ms.  ");
		} else {
			// Bukkit.getLogger().info("All clear! (" + elapsed + "ms.)");
		}
	}

	public int getPlayerMinutes(UUID uuid) {
		return serenityPlayers.get(uuid).getMinutes();
	}

	public void addAreaToConfig(ProtectedArea pa) {
		String path = "ProtectedAreas." + pa.owner + "." + pa.getId();

		String[] loc = { pa.location1.getWorld().getName(),
				"" + (int) pa.location1.getX(), "" + (int) pa.location1.getZ(),
				"" + (int) pa.location2.getX(), "" + (int) pa.location2.getZ() };

		protectedAreasCfg.getConfig().set(path, loc);
		protectedAreasCfg.saveConfig();
		protectedAreasCfg.reloadConfig();
	}

	public String getHoursAndMinutes(UUID uuid) {
		int minutes = getPlayerMinutes(uuid);
		int hours = minutes / 60;
		minutes = minutes % 60;
		return "" + "§b" + hours + "§3" + " hours and " + "§b" + minutes + "§3"
				+ " minutes.";

	}

	public static String convertToHoursAndMinutesSince(Long time) {
		Long difference = System.currentTimeMillis() - time;
		return getDurationBreakdown(difference);
	}

	public static String convertToHoursAndMinutesSinceNoSeconds(Long time) {
		Long difference = System.currentTimeMillis() - time;
		return getDurationBreakdownNoSeconds(difference);
	}

	private String getTranslationLanguage(Player player, String key) {

		String result = "";
		/*
		 * if (dutchSpeakers.contains(player.getName())) { result =
		 * dutchStrings.get(key); if (result == "") {
		 * player.sendMessage("WARNING!  TELL HAL that DUTCH " + key +
		 * " string is not right!! Sorry!!! So I'm gonna send you the english version!"
		 * ); } else { return result; } }
		 */
		return englishStrings.get(key);
	}

	private String getTranslationLanguage(CommandSender player, String key) {
		String result = "";
		/*
		 * if (dutchSpeakers.contains(player.getName())) { result =
		 * dutchStrings.get(key); if (result == null || result == "") {
		 * player.sendMessage("WARNING!  TELL HAL that DUTCH " + key +
		 * " string is not right!! Sorry!!! So I'm gonna send you the english version!"
		 * ); } else { return result; } }
		 */
		if (englishStrings.get(key) == "" || englishStrings.get(key) == null) {
			player.sendMessage("SOMETHING WENT WRONG! TELL HAL A STRING IS MESSED UP!  MORE DATA: "
					+ key);
			getLogger().info(
					"SOMETHING WENT WRONG! TELL HAL A STRING IS MESSED UP!  MORE DATA: "
							+ key);
		}
		return englishStrings.get(key);
	}

	@EventHandler
	public void PlayerDeathColor(PlayerDeathEvent event) {
		event.setDeathMessage(getChatColor(event.getEntity().getUniqueId())
				+ event.getDeathMessage());
		SerenityPlayer sp = serenityPlayers
				.get(event.getEntity().getUniqueId());
		if (sp != null) {
			sp.getSerenityLeader().setDeaths(
					sp.getSerenityLeader().getDeaths() + 1);
		}
	}

	@EventHandler
	private void hopperMailboxAttempt(BlockPlaceEvent event) {
		if (event.getBlock().getType() == Material.HOPPER
				|| event.getBlock().getType() == Material.HOPPER_MINECART) {

			for (Mailbox mb : mailBoxes) {
				if (mb.location.equals(event.getBlock()
						.getRelative(BlockFace.UP).getLocation())) {
					if (!mb.uuid.equals(event.getPlayer().getUniqueId())) {
						event.setCancelled(true);
						return;
					}
				}
			}
		}
	}

	/*
	 * @EventHandler private void AFKInvEvent(InventoryClickEvent event) { if
	 * (event.getInventory().getName().contains("AFK INVENTORY")) {
	 * event.setCancelled(true); } }
	 */
	static boolean isDateBetween(Date check, Date min, Date max) {
		SimpleDateFormat fmt = new SimpleDateFormat("MMdd");
		if (fmt.format(check).equals(fmt.format(min))) {
			return true;
		}

		return check.after(min) && check.before(max);
	}

	// PODRICK CODE SECTION
	protected void teleportSomeone() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.getWorld().getName().equals("world")) {
				if (p.getLocation().distance(CENTERBLOCKSINFINALDUNGEON) < 75) {
					for (Entity e : p.getNearbyEntities(50, 70, 50)) {
						if (e.getCustomName() != null) {
							if (e.getCustomName().equals(Secret.BADGUYSNAME)) {
								int r = rand.nextInt(5);
								switch (r) {
								case 0:
									e.teleport(BLUEBLOCKSINFINALDUNGEON);
									break;
								case 1:
									e.teleport(CENTERBLOCKSINFINALDUNGEON);
									break;
								case 2:
									e.teleport(GREENBLOCKSINFINALDUNGEON);
									break;
								case 3:
									e.teleport(CLEARBLOCKSINFINALDUNGEON);
									break;
								case 4:
									e.teleport(REDBLOCKSINFINALDUNGEON);
									break;
								}
								return;
							}
						}
					}
				}
			}
		}

	}

	@EventHandler(priority = EventPriority.MONITOR)
	private void openDungeonDoor(PlayerInteractEvent event) {
		if (event.getAction() == Action.PHYSICAL) {
			for (ProtectedArea pa : areas) {
				if (pa.equals(event.getPlayer().getLocation())) {
					if (pa.owner.contains("secret1")) {
						if (podrickCfg.getConfig().getBoolean(
								event.getPlayer().getDisplayName()
										+ ".ReadyToFight", false)) {
							event.setCancelled(false);
							return;
						}
					}
				}
			}
		}

		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			for (ProtectedArea pa : areas) {
				if (pa.equals(event.getPlayer().getLocation())) {
					if (pa.owner.contains("secret1")) {
						if (event.getClickedBlock().getType() == Material.SPRUCE_DOOR) {
							if (podrickCfg.getConfig().getBoolean(
									event.getPlayer().getDisplayName()
											+ ".Finished", false)) {
								event.setCancelled(false);
								return;
							}
						}
					}
				}
			}
		}
	}

	private void sendSimulatedPrivateMessage(Player player, String displayName,
			String message) {
		player.sendMessage("§oFrom §6§o§l" + displayName + ": §r" + message);
	}

	private void successfulFeedArg(Player p) {
		final Player player = p;
		List<String> lores = new ArrayList<String>();

		int seed = podrickCfg.getConfig().getInt(
				player.getDisplayName() + ".Seed", 0);

		lores.add(seed + "");

		final List<String> lore = lores;
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {

			@Override
			public void run() {
				sendSimulatedPrivateMessage(player, Secret.NPCNETHNOCOL,
						Secret.SSA);
				if (player.getItemInHand().getAmount() != 1) {
					player.getItemInHand().setAmount(
							player.getItemInHand().getAmount() - 1);
				} else {
					player.setItemInHand(null);
				}

				ItemStack is = new ItemStack(Material.SOUL_SAND);
				ItemMeta im = is.getItemMeta();
				im.setDisplayName(Secret.ss);

				im.setLore(lore);
				is.setItemMeta(im);
				safelyDropItemStack(player.getLocation(), player.getInventory()
						.addItem(is));
				podrickCfg.getConfig().set(
						player.getDisplayName() + ".FinishedArgo", true);
				podrickCfg.saveConfig();
				podrickCfg.reloadConfig();

			}

		});
	}

	@EventHandler
	public void PlayerDeathSecret(EntityDamageEvent event) {
		for (ProtectedArea pa : areas) {
			if (pa.equals(event.getEntity().getLocation())) {
				if (pa.owner.contains("secret1")) {
					if (event.getCause() == DamageCause.FALL) {
						if (event.getEntity().getLocation().getY() < 20) {
							if (event.getEntity() instanceof Player) {
								Player p = (Player) event.getEntity();
								if (!podrickCfg.getConfig().getBoolean(
										p.getDisplayName() + ".ReadyToFight",
										false)) {
									p.teleport(DOORWAYTOFINALDUNGEON);
									p.sendMessage("§cYou must complete the rest of the quest first!");
									event.setCancelled(true);
									return;
								}
							}
						}
						event.setCancelled(true);
						return;
					}
				}
			}
		}
	}

	@EventHandler
	public void PlayerDeathInDungeon(PlayerDeathEvent event) {
		for (ProtectedArea pa : areas) {
			if (pa.equals(event.getEntity().getLastDamageCause().getEntity()
					.getLocation())) {
				if (pa.owner.contains("secret1")) {
					event.setKeepInventory(true);
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
							"server remove");
					for (Player p : Bukkit.getOnlinePlayers()) {
						if (p.getLocation().getWorld().getName()
								.equals("world")) {
							if (p.getLocation().distance(
									CENTERBLOCKSINFINALDUNGEON) < 75) {
								p.teleport(DOORWAYTOFINALDUNGEON);
							}
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void ArgInteractEvent(PlayerInteractEntityEvent event) {
		if (event.getRightClicked().getCustomName() != null) {

			if (event.getRightClicked().getCustomName()
					.equals(Secret.NPCNETHCOL)) {
				if (podrickCfg.getConfig()
						.getBoolean(
								event.getPlayer().getDisplayName()
										+ ".ReadyToTeleport", false)) {
					if (event.getPlayer().getItemInHand().getAmount() != 0) {
						int type = podrickCfg.getConfig().getInt(
								event.getPlayer().getDisplayName()
										+ ".ArgosFood");
						switch (type) {
						case 0:
							if (event.getPlayer().getItemInHand().getType()
									.equals(Material.APPLE)) {
								successfulFeedArg(event.getPlayer());
								return;
							}
							break;
						case 1:
							if (event.getPlayer().getItemInHand().getType()
									.equals(Material.CARROT_ITEM)) {
								successfulFeedArg(event.getPlayer());
								return;
							}
							break;
						case 2:
							if (event.getPlayer().getItemInHand().getType()
									.equals(Material.MUSHROOM_SOUP)) {
								successfulFeedArg(event.getPlayer());
								return;
							}
							break;
						case 3:
							if (event.getPlayer().getItemInHand().getType()
									.equals(Material.POISONOUS_POTATO)) {
								successfulFeedArg(event.getPlayer());
								return;
							}
							break;
						case 4:
							if (event.getPlayer().getItemInHand().getType()
									.equals(Material.POTATO_ITEM)) {
								successfulFeedArg(event.getPlayer());
								return;
							}
							break;
						case 5:
							if (event.getPlayer().getItemInHand().getType()
									.equals(Material.PUMPKIN_PIE)) {
								successfulFeedArg(event.getPlayer());
								return;
							}
							break;
						case 6:
							if (event.getPlayer().getItemInHand().getType()
									.equals(Material.MELON)) {
								successfulFeedArg(event.getPlayer());
								return;
							}
							break;
						}

						String hint = "";
						switch (type) {
						case 0:
							hint = Secret.H1;
							break;
						case 1:
							hint = Secret.H2;
							break;
						case 2:
							hint = Secret.H3;
							break;
						case 3:
							hint = Secret.H4;
							break;
						case 4:
							hint = Secret.H5;
							break;
						case 5:
							hint = Secret.H6;
							break;
						case 6:
							hint = Secret.H7;
							break;
						}

						sendSimulatedPrivateMessage(event.getPlayer(),
								Secret.NPCNETHNOCOL, Secret.IDW + hint);

					} else {
						sendSimulatedPrivateMessage(event.getPlayer(),
								Secret.NPCNETHNOCOL, Secret.BSE);
					}
				}
				event.setCancelled(true);
			}

		}
	}

	@EventHandler
	public void onArmorStandInteractEvent(PlayerArmorStandManipulateEvent event) {
		for (ProtectedArea pa : areas) {
			if (pa.equals(event.getRightClicked().getLocation())) {
				if (!pa.hasPermission(event.getPlayer().getDisplayName())) {
					event.setCancelled(true);
					return;
				}
			}
		}
	}

	@EventHandler
	public void onSpecialInteractEvent(PlayerInteractEntityEvent event) {
		for (ProtectedArea pa : areas) {
			if (pa.equals(event.getRightClicked().getLocation())) {
				if (!pa.hasPermission(event.getPlayer().getDisplayName())) {
					if (pa.owner.contains("layground")) {
						return;
					}
					event.setCancelled(true);
					return;
				}
			}
		}
		if (event.getRightClicked().getCustomName() != null) {
			if (event.getRightClicked().getCustomName().contains("§6")) {
				if (event.getPlayer().getItemInHand().getType() == Material.SHEARS) {
					event.setCancelled(true);
				}
				if (event.getPlayer().getItemInHand().getType() == Material.LEASH) {
					event.setCancelled(false);
					return;
				}
				if (event.getRightClicked().getType() == EntityType.OCELOT) {
					event.setCancelled(false);
					return;
				}

				event.setCancelled(true);
			}
		}
	}

	private void successfulFeedE(Player p) {
		final Player player = p;
		List<String> lores = new ArrayList<String>();

		int seed = podrickCfg.getConfig().getInt(
				player.getDisplayName() + ".Seed", 0);

		lores.add(seed + "");

		final List<String> lore = lores;
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {

			@Override
			public void run() {
				sendSimulatedPrivateMessage(player, Secret.NAMEOFANNPCWOCOLOR,
						Secret.SSE);
				if (player.getItemInHand().getAmount() != 1) {
					player.getItemInHand().setAmount(
							player.getItemInHand().getAmount() - 1);
				} else {
					player.setItemInHand(null);
				}

				ItemStack is = new ItemStack(Material.PRISMARINE_CRYSTALS);
				ItemMeta im = is.getItemMeta();
				im.setDisplayName(Secret.DECCRYST);

				im.setLore(lore);
				is.setItemMeta(im);
				safelyDropItemStack(player.getLocation(), player.getInventory()
						.addItem(is));
				podrickCfg.getConfig().set(
						player.getDisplayName() + ".FinishedEnki", true);
				podrickCfg.saveConfig();
				podrickCfg.reloadConfig();

			}

		});
	}

	@EventHandler
	public void onPlayerSSA(BlockPlaceEvent event) {

		if (event.getBlock().getType() == Material.SOUL_SAND) {
			if (event.getPlayer().getItemInHand().hasItemMeta()) {
				ItemStack is = event.getPlayer().getItemInHand();
				if (is.getItemMeta().hasDisplayName()
						&& is.getItemMeta().getDisplayName().equals(Secret.DSS)) {
					event.setCancelled(true);
					event.getPlayer().setItemInHand(null);
					List<String> names = new ArrayList<String>() {
						{
							add("Maris");
							add("Katherine");
							add("Carolee");
							add("Roselee");
							add("Lloyd");
							add("Marcelle");
							add("Gilma");
							add("Tosha");
							add("Florentino");
							add("Alfredia");
							add("Aimee");
							add("Anisa");
							add("Lonnie");
							add("Ernest");
							add("Clarice");
							add("Fatima");
							add("Celestina");
							add("Hortensia");
							add("Norene");
							add("Tinisha");
							add("Jeanine");
							add("Minh");
							add("Wanita");
							add("Galina");
							add("Tracy");
							add("Bert");
							add("Loyce");
							add("Candelaria");
							add("Jefferey");
							add("Mariella");
							add("Chana");
							add("Phung");
							add("Meggan");
							add("Merrill");
							add("Xiomara");
							add("Deeann");
							add("Lisandra");
							add("Sabine");
							add("Alberto");
							add("Hallie");
							add("Carmen");
							add("Marylee");
							add("Fernanda");
							add("Brian");
							add("Clyde");
							add("Sharice");
							add("Tam");
							add("Elsa");
							add("Teresia");
							add("Vicente");
							add("Jeremy");
							add("Shalanda");
							add("Lizabeth");
							add("Jene");
							add("Issac");
							add("Cinda");
							add("Shawanna");
							add("Siobhan");
							add("Thi");
							add("Ethel");
							add("Jana");
							add("Cathy");
							add("Kerri");
							add("Elvia");
							add("Marylee");
							add("Janice");
							add("Breanna");
							add("Verlie");
							add("Rolland");
							add("Charlie");
							add("Marin");
							add("Lilly");
							add("Cythia");
							add("Shanti");
							add("Les");
							add("Man");
							add("Rubie");
							add("Eleni");
							add("Freeman");
							add("Ha");
							add("Malisa");
							add("Rayna");
							add("Selina");
							add("Bettye");
							add("Crysta");
							add("Nina");
							add("Earline");
							add("Lilli");
							add("Rylan");
							add("Louis");
							add("Kimiko");
							add("Tarra");
							add("Jetta");
							add("Dani");
							add("Kandy");
							add("Waldo");
							add("Vernita");
							add("Zackary");
							add("Charlotte");
							add("Devin");
							add("Clora");
							add("Emelina");
							add("Tynisha");
							add("Carlena");
							add("Vicente");
							add("Majorie");
							add("Drusilla");
							add("Alexandra");
							add("Hyman");
							add("Euna");
							add("Belinda");
							add("Candie");
							add("Valencia");
							add("Merrilee");
							add("Ayana");
							add("Julieta");
							add("Marshall");
							add("Dale");
							add("Jenae");
							add("Nicolle");
							add("Sierra");
							add("Jake");
							add("Buddy");
							add("Claudia");
							add("Latasha");
							add("Eusebio");
							add("Analisa");
							add("Garnett");
							add("Sheila");
							add("Vernie");
							add("Trish");
							add("Donnette");
							add("Octavio");
							add("Nicolasa");
							add("Tenesha");
							add("Vina");
							add("Johnny");
							add("Antoine");
							add("Vicki");
							add("Zachary");
							add("Vanita");
							add("Meghann");
							add("Lynda");
							add("Shayna");
							add("Nicolette");
							add("Beverlee");
							add("Conchita");
							add("Karrie");
							add("Kimbery");
							add("Greta");
							add("Rueben");
							add("Salvatore");
							add("Nelson");
							add("Myrta");
							add("Cleo");
							add("Vashti");
							add("Caterina");
							add("Adele");
							add("Renita");
							add("Maira");
							add("Rita");
							add("Liberty");
							add("Earle");
							add("Suzanne");
							add("Mammie");
							add("Deangelo");
							add("Oliva");
							add("Mickie");
							add("Cruz");
							add("Lesa");
							add("Ellyn");
							add("Elton");
							add("Emely");
							add("Marcelo");
							add("Chastity");
							add("Heidi");
							add("Jocelyn");
							add("Christiana");
							add("Tomiko");
							add("Candra");
							add("Teressa");
							add("Donald");
							add("Felicidad");
							add("Krista");
							add("Melodee");
							add("Angelo");
							add("Malisa");
							add("Scotty");
							add("Lorri");
							add("Somer");
							add("Clayton");
							add("Carin");
							add("Suzette");
							add("Angel");
							add("Corina");
							add("Liana");
							add("Markus");
							add("Charlyn");
							add("Keena");
							add("Terina");
						}
					};

					event.getBlock()
							.getLocation()
							.getWorld()
							.playSound(event.getBlock().getLocation(),
									Sound.AMBIENCE_THUNDER, 20F, 20F);
					Random rand = new Random();
					String name = names.get(rand.nextInt(200));
					Player p = event.getPlayer();
					int r = rand.nextInt(8);

					switch (r) {
					case 0:
						Cow cow = (Cow) p.getLocation().getWorld()
								.spawnEntity(p.getLocation(), EntityType.COW);
						cow.setCustomName("§6" + name);

						cow.setBaby();
						cow.setAgeLock(true);

						break;
					case 1:
						Pig pig = (Pig) p.getLocation().getWorld()
								.spawnEntity(p.getLocation(), EntityType.PIG);
						pig.setCustomName("§6" + name);

						pig.setBaby();
						pig.setAgeLock(true);
						break;
					case 2:
						MushroomCow mc = (MushroomCow) p
								.getLocation()
								.getWorld()
								.spawnEntity(p.getLocation(),
										EntityType.MUSHROOM_COW);
						mc.setCustomName("§6" + name);

						mc.setBaby();
						mc.setAgeLock(true);
						break;
					case 3:
						Chicken c = (Chicken) p
								.getLocation()
								.getWorld()
								.spawnEntity(p.getLocation(),
										EntityType.CHICKEN);

						c.setCustomName("§6" + name);

						c.setBaby();
						c.setAgeLock(true);
						break;
					case 4:
						Rabbit rab = (Rabbit) p
								.getLocation()
								.getWorld()
								.spawnEntity(p.getLocation(), EntityType.RABBIT);
						rab.setCustomName("§6" + name);

						rab.setBaby();
						rab.setAgeLock(true);
						break;
					case 5:
						Sheep s = (Sheep) p.getLocation().getWorld()
								.spawnEntity(p.getLocation(), EntityType.SHEEP);
						s.setCustomName("§6" + name);

						s.setBaby();
						s.setAgeLock(true);
						break;
					case 6:
						Ocelot ocelot = (Ocelot) p
								.getLocation()
								.getWorld()
								.spawnEntity(p.getLocation(), EntityType.OCELOT);
						ocelot.setCustomName("§6" + name);
						ocelot.setTamed(true);
						AnimalTamer at = (AnimalTamer) p;
						ocelot.setBaby();
						ocelot.setAgeLock(true);
						ocelot.setOwner(at);
						ocelot.setSitting(false);
						ocelot.setRemoveWhenFarAway(false);

						int cat = rand.nextInt(3);
						if (cat == 0)
							ocelot.setCatType(Ocelot.Type.BLACK_CAT);
						if (cat == 1)
							ocelot.setCatType(Ocelot.Type.RED_CAT);
						if (cat == 2)
							ocelot.setCatType(Ocelot.Type.SIAMESE_CAT);
						break;
					case 7:
						Horse horse = (Horse) p.getLocation().getWorld()
								.spawnEntity(p.getLocation(), EntityType.HORSE);
						horse.setBaby();
						horse.setAgeLock(true);
						horse.setCustomName("§6" + name);

					}
				}
			}
		}
	}

	public void putBookAndStuffInMailbox(String name) {
		for (Mailbox mb : mailBoxes) {
			if (mb.uuid.equals(name)) {
				Chest receivingChest = (Chest) mb.location.getBlock()
						.getState();

				ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
				BookMeta meta = (BookMeta) book.getItemMeta();

				meta.setTitle(Secret.TFP);
				meta.setAuthor("§d[Server]");

				String bookText = podrickCfg.getConfig().getString(
						"ThankYouBook", "oh no");

				List<String> pages = new ArrayList<String>();
				int l = 0;
				int lineCount = 0;
				String page = "";
				for (int i = 0; i < bookText.length(); i++) {
					if (bookText.charAt(i) == '^') {
						page += '§';
					} else if (bookText.charAt(i) == '~') {
						pages.add(page);
						page = "";
						lineCount = 0;
					} else {
						page += bookText.charAt(i);

						l++;
						if (bookText.charAt(i) == '\n' || l > 19) {
							l = 0;
							lineCount++;
						}

						if (lineCount > 12
								&& (bookText.charAt(i) == ' ' || bookText
										.charAt(i) == '\n')) {
							pages.add(page);
							page = "";
							lineCount = 0;
						}
					}
				}
				pages.add(page);

				meta.setPages(pages);
				book.setItemMeta(meta);

				receivingChest.getInventory().addItem(book);
				ItemStack is = new ItemStack(Material.SOUL_SAND);
				ItemMeta im = is.getItemMeta();
				im.setDisplayName(Secret.DSS);
				is.setItemMeta(im);
				receivingChest.getInventory().addItem(is);

				ItemStack isl = new ItemStack(Material.LEASH);
				receivingChest.getInventory().addItem(isl);

			}
		}
	}

	@EventHandler
	public void onPlayerPlaceSS(BlockPlaceEvent event) {
		if (event.getBlock().getType() == Material.SOUL_SAND) {
			if (event.getPlayer().getItemInHand().hasItemMeta()) {
				if (event.getPlayer().getItemInHand().getItemMeta().hasLore()) {
					ItemStack is = event.getPlayer().getItemInHand();

					if (is.getItemMeta().hasDisplayName()
							&& is.getItemMeta().getDisplayName()
									.equals(Secret.ss)) {
						int playerSeed = podrickCfg.getConfig().getInt(
								event.getPlayer().getDisplayName() + ".Seed",
								-1);
						int thisItemSeed = Integer.parseInt(is.getItemMeta()
								.getLore().get(0));
						if (playerSeed == thisItemSeed) {
							int x = podrickCfg.getConfig().getInt(
									event.getPlayer().getDisplayName()
											+ ".XValue", -1);
							int z = podrickCfg.getConfig().getInt(
									event.getPlayer().getDisplayName()
											+ ".ZValue", -1);
							if (event.getBlock().getX() == x
									&& event.getBlock().getZ() == z) {
								sendSimulatedPrivateMessage(event.getPlayer(),
										Secret.MYSTERYBOOKAUTHOR,
										" §rTh§ka§ra§kll§rnk y§kl§rou§kljk");
								event.getBlock()
										.getLocation()
										.getWorld()
										.playSound(
												event.getBlock().getLocation(),
												Sound.AMBIENCE_THUNDER, 100, 5);
								podrickCfg.getConfig().set(
										event.getPlayer().getDisplayName()
												+ ".SummonedDave", true);
								podrickCfg.saveConfig();
								podrickCfg.reloadConfig();
								return;
							}

							event.setCancelled(true);
						}
					}
				}
			}
		}
	}

	private void startAThing(Player p) {

		final Player player = p;
		final Location scareLightning = new Location(Bukkit.getWorld("world"),
				-1105.003, 116.00, -2867.7);

		final World w = p.getWorld();
		final String name = Secret.NAMENPC2COLOR;
		final String scared = Secret.OHASNST;
		final String thndrsn = Secret.OHATSNST;
		final String done = Secret.MKSTOP;

		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			@Override
			public void run() {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Secret.WTHR);
				player.getWorld().playSound(player.getLocation(),
						Sound.AMBIENCE_CAVE, 100, 1);
			}
		});

		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			@Override
			public void run() {
				sendSimulatedPrivateMessage(player, name, scared);
			}

		}, 100L);

		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			@Override
			public void run() {
				w.strikeLightningEffect(scareLightning);
			}

		}, 160L);

		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			@Override
			public void run() {
				sendSimulatedPrivateMessage(player, name, thndrsn);
			}

		}, 200L);

		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			@Override
			public void run() {
				for (Entity e : player.getNearbyEntities(20.0, 20.0, 20.0)) {
					if (e instanceof LivingEntity) {
						if (e.getCustomName() != null) {
							if (e.getCustomName().equals(Secret.NAMENPC2COLOR)) {
								w.strikeLightningEffect(e.getLocation());
								return;
							}
						}
					}
				}
			}
		}, 300L);

		ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
		BookMeta meta = (BookMeta) book.getItemMeta();

		meta.setTitle(Secret.MYSTERYBOOK3TITLE);
		meta.setAuthor(Secret.MYSTERYBOOKAUTHOR);
		String bookText = podrickCfg.getConfig().getString("BookText3");

		List<String> pages = new ArrayList<String>();
		int l = 0;
		int lineCount = 0;
		String page = "";
		for (int i = 0; i < bookText.length(); i++) {
			if (bookText.charAt(i) == '~') {
				pages.add(page);
				page = "";
				lineCount = 0;
			} else {
				page += bookText.charAt(i);

				l++;
				if (bookText.charAt(i) == '\n' || l > 19) {
					l = 0;
					lineCount++;
				}

				if (lineCount > 12
						&& (bookText.charAt(i) == ' ' || bookText.charAt(i) == '\n')) {
					pages.add(page);
					page = "";
					lineCount = 0;
				}
			}
		}
		pages.add(page);

		meta.setPages(pages);
		book.setItemMeta(meta);

		book.setItemMeta(meta);

		final ItemStack bookF = book;
		final Location bookLoc = Secret.B3LOCLOC;
		final Location loc = Secret.B3LOC;
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			@Override
			public void run() {
				player.teleport(loc);
				player.setFallDistance(0F);
				loc.getWorld().strikeLightningEffect(loc);
				loc.getWorld().dropItem(bookLoc, bookF);
			}
		}, 400L);

		ItemStack is = new ItemStack(Material.GHAST_TEAR);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(Secret.TPA);

		List<String> lores = new ArrayList<String>();

		int seed = podrickCfg.getConfig().getInt(
				player.getDisplayName() + ".Seed", 0);

		lores.add(seed + "");

		im.setLore(lores);
		is.setItemMeta(im);

		final ItemStack imf = is;

		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {

			@Override
			public void run() {
				sendSimulatedPrivateMessage(player, name, done);
				safelyDropItemStack(player.getLocation(), player.getInventory()
						.addItem(imf));
				podrickCfg.getConfig().set(
						player.getDisplayName() + ".GotArtifact", true);
				podrickCfg.saveConfig();
				podrickCfg.reloadConfig();

			}
		}, 320L);
	}

	@EventHandler
	public void EInteractEvent(PlayerInteractEntityEvent event) {
		if (event.getRightClicked().getCustomName() != null) {
			if (event.getRightClicked().getCustomName()
					.equals(Secret.NAMEOFANNPC)) {

				if (podrickCfg.getConfig().getBoolean(
						event.getPlayer().getDisplayName() + ".Started", false) == true) {
					if (podrickCfg.getConfig().getBoolean(
							event.getPlayer().getDisplayName()
									+ ".FinishedEnki", false) == false) {

						if (event.getPlayer().getItemInHand().getAmount() != 0) {
							int type = podrickCfg.getConfig().getInt(
									event.getPlayer().getDisplayName()
											+ ".EnkisFood");

							switch (type) {
							case 0:
								if (event.getPlayer().getItemInHand().getType()
										.equals(Secret.F1)
										&& event.getPlayer().getItemInHand()
												.getDurability() == 2) {
									successfulFeedE(event.getPlayer());
									return;

								}
								break;
							case 1:
								if (event.getPlayer().getItemInHand().getType()
										.equals(Secret.F1)
										&& event.getPlayer().getItemInHand()
												.getDurability() == 1) {
									successfulFeedE(event.getPlayer());
									return;
								}
								break;
							case 2:
								if (event.getPlayer().getItemInHand().getType()
										.equals(Secret.F1)
										&& event.getPlayer().getItemInHand()
												.getDurability() == 3) {
									successfulFeedE(event.getPlayer());
									return;
								}
								break;
							case 3:
								if (event.getPlayer().getItemInHand().getType()
										.equals(Secret.F2)) {
									successfulFeedE(event.getPlayer());
									return;
								}
								break;
							case 4:
								if (event.getPlayer().getItemInHand().getType()
										.equals(Secret.F3)) {
									successfulFeedE(event.getPlayer());
									return;
								}
								break;
							case 5:
								if (event.getPlayer().getItemInHand().getType()
										.equals(Secret.F4)) {
									successfulFeedE(event.getPlayer());
									return;
								}
								break;
							case 6:
								if (event.getPlayer().getItemInHand().getType()
										.equals(Secret.F5)) {
									successfulFeedE(event.getPlayer());
									return;
								}
								break;

							case 7:
								if (event.getPlayer().getItemInHand().getType()
										.equals(Secret.F6)) {
									successfulFeedE(event.getPlayer());
									return;
								}
								break;
							}
							sendSimulatedPrivateMessage(event.getPlayer(),
									Secret.NAMEOFANNPCWOCOLOR, Secret.HGY);
							return;
						} else {
							sendSimulatedPrivateMessage(event.getPlayer(),
									Secret.NAMEOFANNPCWOCOLOR, Secret.BGY);
						}

					}
					event.setCancelled(true);
				}
			}
		}

	}

	@EventHandler
	public void AInteractEvent(PlayerInteractEntityEvent event) {

		if (event.getRightClicked().getCustomName() != null) {
			if (event.getRightClicked().getCustomName()
					.equals(Secret.NAMENPC2COLOR)) {
				if (podrickCfg.getConfig().getBoolean(
						event.getPlayer().getDisplayName() + ".GotJournal3",
						false)) {
					if (podrickCfg.getConfig().getBoolean(
							event.getPlayer().getDisplayName()
									+ ".FinishedAlphonse", false) == false) {
						if (event.getPlayer().getItemInHand() != null) {
							if (event.getPlayer().getItemInHand().hasItemMeta()) {
								if (event.getPlayer().getItemInHand()
										.getItemMeta().hasDisplayName()) {
									if (event.getPlayer().getItemInHand()
											.getItemMeta().getDisplayName()
											.equals(Secret.SECRETWCDNAME)) {
										if (event.getPlayer().getItemInHand()
												.getItemMeta().hasLore()) {
											int thisItemSeed = Integer
													.parseInt(event.getPlayer()
															.getItemInHand()
															.getItemMeta()
															.getLore().get(0));
											int seed = podrickCfg.getConfig()
													.getInt(event.getPlayer()
															.getDisplayName()
															+ ".Seed");
											if (thisItemSeed == seed) {
												event.getPlayer()
														.setItemInHand(null);
												startAThing(event.getPlayer());
												return;
											}
										}
									}
								}
							}
						}
					}
					sendSimulatedPrivateMessage(event.getPlayer(),
							Secret.NAMENPC2NOCOLOR, Secret.WONTGET);
					return;
				}
			}
		}
	}

	private void spawn1EntityInEachCorner(EntityType z, PotionEffect pe,
			PotionEffect pe2, String nombre) {

		final String name = nombre;
		final PotionEffect potionEffect = pe;
		final EntityType zombie = z;
		final PotionEffect potionEffect2 = pe2;

		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			@Override
			public void run() {
				Entity e = CENTERBLOCKSINFINALDUNGEON.getWorld().spawnEntity(
						GREENBLOCKSINFINALDUNGEON, zombie);
				e.setCustomName(name);
				if (e instanceof Creature) {
					Creature enemy = (Creature) e;
					enemy.addPotionEffect(potionEffect);
					enemy.addPotionEffect(potionEffect2);
				}

			}
		});

		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			@Override
			public void run() {
				Entity e = CENTERBLOCKSINFINALDUNGEON.getWorld().spawnEntity(
						REDBLOCKSINFINALDUNGEON, zombie);
				e.setCustomName(name);
				if (e instanceof Creature) {
					Creature enemy = (Creature) e;
					enemy.addPotionEffect(potionEffect);
					enemy.addPotionEffect(potionEffect2);
				}

			}
		});

		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			@Override
			public void run() {
				Entity e = CENTERBLOCKSINFINALDUNGEON.getWorld().spawnEntity(
						BLUEBLOCKSINFINALDUNGEON, zombie);
				e.setCustomName(name);
				if (e instanceof Creature) {
					Creature enemy = (Creature) e;
					enemy.addPotionEffect(potionEffect);
					enemy.addPotionEffect(potionEffect2);
				}

			}
		});

		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			@Override
			public void run() {
				Entity e = CENTERBLOCKSINFINALDUNGEON.getWorld().spawnEntity(
						CLEARBLOCKSINFINALDUNGEON, zombie);
				e.setCustomName(name);
				if (e instanceof Creature) {
					Creature enemy = (Creature) e;
					enemy.addPotionEffect(potionEffect);
					enemy.addPotionEffect(potionEffect2);
				}

			}
		});
	}

	@EventHandler
	public void onDungeonKill(EntityDeathEvent event) {
		if (event.getEntity().getCustomName() != null) {
			if (event.getEntity().getCustomName().contains("§c" + "")) {
				event.setDroppedExp(0);
				event.getDrops().clear();
				ParticleEffect.FIREWORKS_SPARK.display(.25F, .25F, .25F, .25F,
						20, event.getEntity().getEyeLocation(), 15);
				finalDungeonKillCount++;

				// round 2
				if (finalDungeonKillCount == 20) {
					for (int i = 0; i < 6; i++) {
						spawn1EntityInEachCorner(Secret.ENTITYTYPEROUND1,
								new PotionEffect(PotionEffectType.SPEED,
										999999, 1), new PotionEffect(
										PotionEffectType.HEAL, 10, 1), "§c"
										+ Secret.ENTITYROUND1NAME);
					}
				}

				if (finalDungeonKillCount == 44) {
					for (int i = 0; i < 6; i++) {
						spawn1EntityInEachCorner(Secret.ENTITYTYPEROUND2,
								new PotionEffect(PotionEffectType.SPEED,
										999999, 0), "§c"
										+ Secret.ENTITYROUND2NAME);
					}
				}

				if (finalDungeonKillCount == 68) {
					for (int i = 0; i < 4; i++) {
						spawn1EntityInEachCorner(Secret.ENTITYTYPEROUND3,
								new PotionEffect(PotionEffectType.SPEED,
										999999, 4), "§c"
										+ Secret.ENTITYROUND3NAME);
					}

				}

				if (finalDungeonKillCount == 84) {
					for (int i = 0; i < 2; i++) {
						spawn1EntityInEachCorner(Secret.ENTITYTYPEROUND4,
								new PotionEffect(
										PotionEffectType.INCREASE_DAMAGE,
										999999, 2), "§c"
										+ Secret.ENTITYROUND4NAME);
					}
				}

				if (finalDungeonKillCount == 92) {
					for (int i = 0; i < 2; i++) {
						spawn1EntityInEachCornerWithDiaArmor(
								Secret.ENTITYTYPEROUND5, new PotionEffect(
										PotionEffectType.SPEED, 999999, 0),
								"§c" + Secret.ENTITYROUND5NAME);
					}
				}

				if (finalDungeonKillCount == 100) {
					for (int i = 0; i < 3; i++) {
						spawn1EntityInEachCornerWithDiaArmor(
								Secret.ENTITYTYPEROUND6, new PotionEffect(
										PotionEffectType.SPEED, 999999, 0),
								"§c" + Secret.ENTITYTYPEROUND6);
					}
				}

				if (finalDungeonKillCount == 112) {
					for (int i = 0; i < 1; i++) {
						spawn1EntityInCenter(Secret.ENTITYTYPEROUND7,
								new PotionEffect(PotionEffectType.SPEED,
										999999, 0), "§c"
										+ Secret.ENTITYROUND7NAME);
					}
				}

				if (finalDungeonKillCount == 113) {
					CENTERBLOCKSINFINALDUNGEON.getWorld().playSound(
							CENTERBLOCKSINFINALDUNGEON, Sound.ENDERMAN_SCREAM,
							50, .25F);
					finalDungeonKillCount = 0;
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
							"server remove");
					for (int i = 0; i < 35; i++) {
						Bukkit.getScheduler().scheduleSyncDelayedTask(this,
								new Runnable() {
									@Override
									public void run() {
										doRandomFirework(
												CENTERBLOCKSINFINALDUNGEON
														.getWorld(),
												CENTERBLOCKSINFINALDUNGEON);
										doRandomFirework(
												CENTERBLOCKSINFINALDUNGEON
														.getWorld(),
												REDBLOCKSINFINALDUNGEON);
										doRandomFirework(
												CENTERBLOCKSINFINALDUNGEON
														.getWorld(),
												BLUEBLOCKSINFINALDUNGEON);
										doRandomFirework(
												CENTERBLOCKSINFINALDUNGEON
														.getWorld(),
												CLEARBLOCKSINFINALDUNGEON);
										doRandomFirework(
												CENTERBLOCKSINFINALDUNGEON
														.getWorld(),
												GREENBLOCKSINFINALDUNGEON);
									}
								}, i * 7L + 100L);
					}

					for (Player p : Bukkit.getOnlinePlayers()) {
						if (p.getLocation().getWorld().getName()
								.equals("world")
								&& p.getLocation().distance(
										CENTERBLOCKSINFINALDUNGEON) < 70) {
							if (podrickCfg.getConfig()
									.getBoolean(
											p.getDisplayName()
													+ ".ReadyToFight", false)) {

								podrickCfg.getConfig().set(
										p.getDisplayName() + ".Finished", true);

								for (int i = 0; i < 70; i++) {
									safelySpawnExperienceOrb(p, 10000, i);
								}

								p.setHealth(p.getMaxHealth());
								for (PotionEffect pe : p
										.getActivePotionEffects()) {
									p.removePotionEffect(pe.getType());
								}
							}
						}
					}

					podrickCfg.saveConfig();
					podrickCfg.reloadConfig();
				}
			}
		}
	}

	private void spawn1EntityInCenter(EntityType wither,
			PotionEffect potionEffect, String string) {
		final String name = string;
		final PotionEffect pe = potionEffect;
		final EntityType z = wither;

		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			@Override
			public void run() {
				Entity e = CENTERBLOCKSINFINALDUNGEON.getWorld().spawnEntity(
						CENTERBLOCKSINFINALDUNGEON, z);
				e.setCustomName(name);
				if (e instanceof Creature) {
					Creature enemy = (Creature) e;
					enemy.addPotionEffect(pe);
				}
			}
		});
	}

	private void spawn1EntityInEachCornerWithDiaArmor(EntityType zombie,
			PotionEffect potionEffect, String string) {
		final ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
		final ItemStack helmet = new ItemStack(Material.DIAMOND_HELMET);
		final ItemStack chest = new ItemStack(Material.DIAMOND_CHESTPLATE);
		final ItemStack legs = new ItemStack(Material.DIAMOND_LEGGINGS);
		final ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);

		final String name = string;
		final PotionEffect pe = potionEffect;
		final EntityType z = zombie;

		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			@Override
			public void run() {
				Entity e = CENTERBLOCKSINFINALDUNGEON.getWorld().spawnEntity(
						GREENBLOCKSINFINALDUNGEON, z);
				e.setCustomName(name);
				if (e instanceof Creature) {
					Creature enemy = (Creature) e;
					enemy.addPotionEffect(pe);
					if (enemy instanceof LivingEntity) {
						LivingEntity le = (LivingEntity) enemy;
						le.getEquipment().setHelmet(helmet);
						le.getEquipment().setBoots(boots);
						le.getEquipment().setChestplate(chest);
						le.getEquipment().setLeggings(legs);
						le.getEquipment().setItemInHand(sword);
					}
				}
			}
		});

		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			@Override
			public void run() {
				Entity e = CENTERBLOCKSINFINALDUNGEON.getWorld().spawnEntity(
						BLUEBLOCKSINFINALDUNGEON, z);
				e.setCustomName(name);
				if (e instanceof Creature) {
					Creature enemy = (Creature) e;
					enemy.addPotionEffect(pe);
					if (enemy instanceof LivingEntity) {
						LivingEntity le = (LivingEntity) enemy;
						le.getEquipment().setHelmet(helmet);
						le.getEquipment().setBoots(boots);
						le.getEquipment().setChestplate(chest);
						le.getEquipment().setLeggings(legs);
						le.getEquipment().setItemInHand(sword);
					}
				}
			}
		});

		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			@Override
			public void run() {
				Entity e = CENTERBLOCKSINFINALDUNGEON.getWorld().spawnEntity(
						REDBLOCKSINFINALDUNGEON, z);
				e.setCustomName(name);
				if (e instanceof Creature) {
					Creature enemy = (Creature) e;
					enemy.addPotionEffect(pe);
					if (enemy instanceof LivingEntity) {
						LivingEntity le = (LivingEntity) enemy;
						le.getEquipment().setHelmet(helmet);
						le.getEquipment().setBoots(boots);
						le.getEquipment().setChestplate(chest);
						le.getEquipment().setLeggings(legs);
						le.getEquipment().setItemInHand(sword);
					}
				}
			}
		});

		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			@Override
			public void run() {
				Entity e = CENTERBLOCKSINFINALDUNGEON.getWorld().spawnEntity(
						CLEARBLOCKSINFINALDUNGEON, z);
				e.setCustomName(name);
				if (e instanceof Creature) {
					Creature enemy = (Creature) e;
					enemy.addPotionEffect(pe);
					if (enemy instanceof LivingEntity) {
						LivingEntity le = (LivingEntity) enemy;
						le.getEquipment().setHelmet(helmet);
						le.getEquipment().setBoots(boots);
						le.getEquipment().setChestplate(chest);
						le.getEquipment().setLeggings(legs);
						le.getEquipment().setItemInHand(sword);
					}
				}
			}
		});

	}

	private void spawn1EntityInEachCorner(EntityType z, PotionEffect pe,
			String nombre) {

		final String name = nombre;
		final PotionEffect potionEffect = pe;
		final EntityType zombie = z;

		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			@Override
			public void run() {
				Entity e = CENTERBLOCKSINFINALDUNGEON.getWorld().spawnEntity(
						GREENBLOCKSINFINALDUNGEON, zombie);
				e.setCustomName(name);
				if (e instanceof Creature) {
					Creature enemy = (Creature) e;
					enemy.addPotionEffect(potionEffect);
				}

			}
		});

		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			@Override
			public void run() {
				Entity e = CENTERBLOCKSINFINALDUNGEON.getWorld().spawnEntity(
						REDBLOCKSINFINALDUNGEON, zombie);
				e.setCustomName(name);
				if (e instanceof Creature) {
					Creature enemy = (Creature) e;
					enemy.addPotionEffect(potionEffect);
				}

			}
		});

		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			@Override
			public void run() {
				Entity e = CENTERBLOCKSINFINALDUNGEON.getWorld().spawnEntity(
						BLUEBLOCKSINFINALDUNGEON, zombie);
				e.setCustomName(name);
				if (e instanceof Creature) {
					Creature enemy = (Creature) e;
					enemy.addPotionEffect(potionEffect);
				}

			}
		});

		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			@Override
			public void run() {
				Entity e = CENTERBLOCKSINFINALDUNGEON.getWorld().spawnEntity(
						CLEARBLOCKSINFINALDUNGEON, zombie);
				e.setCustomName(name);
				if (e instanceof Creature) {
					Creature enemy = (Creature) e;
					enemy.addPotionEffect(potionEffect);
				}

			}
		});
	}

	private void giveMysteryBook(Player p, String string) {
		ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
		BookMeta meta = (BookMeta) book.getItemMeta();

		meta.setTitle(Secret.MYSTERYBOOK1TITLE);
		meta.setAuthor(Secret.MYSTERYBOOKAUTHOR);
		String bookText = string;

		List<String> pages = new ArrayList<String>();
		int l = 0;
		int lineCount = 0;
		String page = "";
		for (int i = 0; i < bookText.length(); i++) {
			if (bookText.charAt(i) == '~') {
				pages.add(page);
				page = "";
				lineCount = 0;
			} else {
				page += bookText.charAt(i);

				l++;
				if (bookText.charAt(i) == '\n' || l > 19) {
					l = 0;
					lineCount++;
				}

				if (lineCount > 12
						&& (bookText.charAt(i) == ' ' || bookText.charAt(i) == '\n')) {
					pages.add(page);
					page = "";
					lineCount = 0;
				}
			}
		}
		pages.add(page);

		meta.setPages(pages);
		book.setItemMeta(meta);

		book.setItemMeta(meta);
		safelyDropItemStack(p.getLocation(), p.getInventory().addItem(book));
		podrickCfg.getConfig().set(p.getDisplayName() + ".GotMysteryBook1",
				true);
	}

	protected void giveJournal3(Player p) {
		ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
		BookMeta meta = (BookMeta) book.getItemMeta();

		meta.setTitle(Secret.JOURNAL3TITLE);
		meta.setAuthor(Secret.SOMEONESNAMEWITHCOLOR);

		String bookText = podrickCfg.getConfig().getString("Journal3");
		int x = podrickCfg.getConfig().getInt(p.getDisplayName() + ".YValue");

		bookText.replace("%", p.getDisplayName());
		bookText.replace("+", x + "");
		bookText.replace('^', '§');

		List<String> pages = new ArrayList<String>();
		int l = 0;
		int lineCount = 0;
		String page = "";
		for (int i = 0; i < bookText.length(); i++) {
			if (bookText.charAt(i) == '%') {
				page += p.getDisplayName();
			} else if (bookText.charAt(i) == '+') {
				page += x + "";
			} else if (bookText.charAt(i) == '^') {
				page += '§';
			} else if (bookText.charAt(i) == '~') {
				pages.add(page);
				page = "";
				lineCount = 0;
			} else {
				page += bookText.charAt(i);

				l++;
				if (bookText.charAt(i) == '\n' || l > 19) {
					l = 0;
					lineCount++;
				}

				if (lineCount > 12
						&& (bookText.charAt(i) == ' ' || bookText.charAt(i) == '\n')) {
					pages.add(page);
					page = "";
					lineCount = 0;
				}
			}
		}
		pages.add(page);

		meta.setPages(pages);

		List<String> lores = new ArrayList<String>();
		int seed = podrickCfg.getConfig().getInt(p.getDisplayName() + ".Seed",
				0);

		lores.add(seed + "");
		meta.setLore(lores);
		book.setItemMeta(meta);

		safelyDropItemStack(p.getLocation(), p.getInventory().addItem(book));
		podrickCfg.getConfig().set(p.getDisplayName() + ".GotJournal3", true);
		podrickCfg.saveConfig();
		podrickCfg.reloadConfig();

	}

	protected void giveJournal2(Player p) {
		ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
		BookMeta meta = (BookMeta) book.getItemMeta();

		meta.setTitle(Secret.JOURNAL2TITLE);
		meta.setAuthor(Secret.SOMEONESNAMEWITHCOLOR);

		String bookText = podrickCfg.getConfig().getString("Journal2");
		int x = podrickCfg.getConfig().getInt(p.getDisplayName() + ".XValue");

		bookText.replace("%", p.getDisplayName());
		bookText.replace("+", x + "");
		bookText.replace('^', '§');

		List<String> pages = new ArrayList<String>();
		int l = 0;
		int lineCount = 0;
		String page = "";
		for (int i = 0; i < bookText.length(); i++) {
			if (bookText.charAt(i) == '%') {
				page += p.getDisplayName();
			} else if (bookText.charAt(i) == '+') {
				page += x + "";
			} else if (bookText.charAt(i) == '^') {
				page += '§';
			} else if (bookText.charAt(i) == '~') {
				pages.add(page);
				page = "";
				lineCount = 0;
			} else {
				page += bookText.charAt(i);

				l++;
				if (bookText.charAt(i) == '\n' || l > 19) {
					l = 0;
					lineCount++;
				}

				if (lineCount > 12
						&& (bookText.charAt(i) == ' ' || bookText.charAt(i) == '\n')) {
					pages.add(page);
					page = "";
					lineCount = 0;
				}
			}
		}
		pages.add(page);

		meta.setPages(pages);

		List<String> lores = new ArrayList<String>();
		int seed = podrickCfg.getConfig().getInt(p.getDisplayName() + ".Seed",
				0);

		lores.add(seed + "");
		meta.setLore(lores);
		book.setItemMeta(meta);

		sendSimulatedPrivateMessage(p, Secret.SOMEONESNAME, Secret.HERESACOPY);

		safelyDropItemStack(p.getLocation(), p.getInventory().addItem(book));
		podrickCfg.getConfig().set(p.getDisplayName() + ".GotJournal2", true);
		podrickCfg.saveConfig();
		podrickCfg.reloadConfig();

	}

	protected void checkForDecoder(Player p) {
		for (ItemStack is : p.getInventory().getContents()) {
			if (is != null) {
				if (is.hasItemMeta()) {
					if (is.getItemMeta().hasLore()) {
						if (is.getItemMeta().hasDisplayName()) {
							if (is.getItemMeta().getDisplayName()
									.equals(Secret.DECCRYST)) {
								int playerSeed = podrickCfg.getConfig().getInt(
										p.getDisplayName() + ".Seed", -1);
								int thisItemSeed = Integer.parseInt(is
										.getItemMeta().getLore().get(0));
								if (playerSeed == thisItemSeed) {
									sendSimulatedPrivateMessage(p,
											Secret.SOMEONESNAME,
											Secret.YESTHATSIT);
									p.getInventory().remove(is);
									giveMysteryBook(p, podrickCfg.getConfig()
											.getString("BookText1"));
									podrickCfg.saveConfig();
									podrickCfg.reloadConfig();
									return;
								}
							}
						}
					}
				}
			}
		}

		sendSimulatedPrivateMessage(p, Secret.SOMEONESNAME,
				"You found something??  Where?");
	}

	protected void giveJournal5(Player p) {
		ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
		BookMeta meta = (BookMeta) book.getItemMeta();

		meta.setTitle(Secret.JOURNAL5TITLE);
		meta.setAuthor(Secret.SOMEONESNAMEWITHCOLOR);

		String bookText = podrickCfg.getConfig().getString("Journal5");
		int x = podrickCfg.getConfig().getInt(p.getDisplayName() + ".ZValue");

		bookText.replace("%", p.getDisplayName());
		bookText.replace("+", x + "");
		bookText.replace('^', '§');

		List<String> pages = new ArrayList<String>();
		int l = 0;
		int lineCount = 0;
		String page = "";
		for (int i = 0; i < bookText.length(); i++) {
			if (bookText.charAt(i) == '%') {
				page += p.getDisplayName();
			} else if (bookText.charAt(i) == '+') {
				page += x + "";
			} else if (bookText.charAt(i) == '^') {
				page += '§';
			} else if (bookText.charAt(i) == '~') {
				pages.add(page);
				page = "";
				lineCount = 0;
			} else {
				page += bookText.charAt(i);

				l++;
				if (bookText.charAt(i) == '\n' || l > 19) {
					l = 0;
					lineCount++;
				}

				if (lineCount > 12
						&& (bookText.charAt(i) == ' ' || bookText.charAt(i) == '\n')) {
					pages.add(page);
					page = "";
					lineCount = 0;
				}
			}
		}
		pages.add(page);

		meta.setPages(pages);

		List<String> lores = new ArrayList<String>();
		int seed = podrickCfg.getConfig().getInt(p.getDisplayName() + ".Seed",
				0);

		lores.add(seed + "");
		meta.setLore(lores);
		book.setItemMeta(meta);

		safelyDropItemStack(p.getLocation(), p.getInventory().addItem(book));
		podrickCfg.getConfig().set(p.getDisplayName() + ".ReadyToFight", true);
		podrickCfg.saveConfig();
		podrickCfg.reloadConfig();
	}

	protected void giveJournal4(Player p) {
		ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
		BookMeta meta = (BookMeta) book.getItemMeta();

		meta.setTitle(Secret.JOURNAL4TITLE);
		meta.setAuthor(Secret.SOMEONESNAMEWITHCOLOR);

		String bookText = podrickCfg.getConfig().getString("Journal4");
		int x = podrickCfg.getConfig().getInt(p.getDisplayName() + ".ZValue");

		bookText.replace("%", p.getDisplayName());
		bookText.replace("+", x + "");
		bookText.replace('^', '§');

		List<String> pages = new ArrayList<String>();
		int l = 0;
		int lineCount = 0;
		String page = "";
		for (int i = 0; i < bookText.length(); i++) {
			if (bookText.charAt(i) == '%') {
				page += p.getDisplayName();
			} else if (bookText.charAt(i) == '+') {
				page += x + "";
			} else if (bookText.charAt(i) == '^') {
				page += '§';
			} else if (bookText.charAt(i) == '~') {
				pages.add(page);
				page = "";
				lineCount = 0;
			} else {
				page += bookText.charAt(i);

				l++;
				if (bookText.charAt(i) == '\n' || l > 19) {
					l = 0;
					lineCount++;
				}

				if (lineCount > 12
						&& (bookText.charAt(i) == ' ' || bookText.charAt(i) == '\n')) {
					pages.add(page);
					page = "";
					lineCount = 0;
				}
			}
		}
		pages.add(page);

		meta.setPages(pages);

		List<String> lores = new ArrayList<String>();
		int seed = podrickCfg.getConfig().getInt(p.getDisplayName() + ".Seed",
				0);

		lores.add(seed + "");
		meta.setLore(lores);
		book.setItemMeta(meta);

		safelyDropItemStack(p.getLocation(), p.getInventory().addItem(book));
		podrickCfg.getConfig().set(p.getDisplayName() + ".GotJournal4", true);
		podrickCfg.saveConfig();
		podrickCfg.reloadConfig();

	}

	private void askSomeone(Player p) {
		for (Entity e : p.getNearbyEntities(15, 10, 15)) {
			if (e.getCustomName() != null) {
				if (e.getCustomName().equals(Secret.SOMEONESNAMEWITHCOLOR)) {

					boolean started = podrickCfg.getConfig().getBoolean(
							p.getDisplayName() + ".Started", false);
					boolean finishedEnki = podrickCfg.getConfig().getBoolean(
							p.getDisplayName() + ".FinishedEnki", false);
					boolean finishedArgo = podrickCfg.getConfig().getBoolean(
							p.getDisplayName() + ".FinishedArgo", false);
					boolean gotMysteryBook1 = podrickCfg.getConfig()
							.getBoolean(
									p.getDisplayName() + ".GotMysteryBook1",
									false);
					boolean gotWeatherControl = podrickCfg.getConfig()
							.getBoolean(
									p.getDisplayName() + ".GotWeatherControl",
									false);
					boolean gotJournal2 = podrickCfg.getConfig().getBoolean(
							p.getDisplayName() + ".GotJournal2", false);

					boolean gotJournal3 = podrickCfg.getConfig().getBoolean(
							p.getDisplayName() + ".GotJournal3", false);

					boolean gotJournal4 = podrickCfg.getConfig().getBoolean(
							p.getDisplayName() + ".GotJournal4", false);

					boolean gotArtifact = podrickCfg.getConfig().getBoolean(
							p.getDisplayName() + ".GotArtifact", false);

					boolean readyToTeleport = podrickCfg.getConfig()
							.getBoolean(
									p.getDisplayName() + ".ReadyToTeleport",
									false);

					boolean summonedDave = podrickCfg.getConfig().getBoolean(
							p.getDisplayName() + ".SummonedDave", false);

					boolean readyToFight = podrickCfg.getConfig().getBoolean(
							p.getDisplayName() + ".ReadyToFight", false);

					boolean done = podrickCfg.getConfig().getBoolean(
							p.getDisplayName() + ".Finished", false);

					if (!started) {

						Random rand = new Random();
						final String podGreeting = SOMEONEGREETINGPREFIX
								.get(rand.nextInt(SOMEONEGREETINGPREFIX.size()));
						final String podQuery = SOMEONETEXT1.get(rand
								.nextInt(SOMEONETEXT1.size()));

						final Player player = p;

						Bukkit.getScheduler().scheduleSyncDelayedTask(this,
								new Runnable() {

									@Override
									public void run() {
										sendSimulatedPrivateMessage(
												player,
												Secret.SOMEONESNAMEPROPER,
												podGreeting
														+ player.getDisplayName()
														+ "!  " + podQuery);
										setPlayerUpForQuest(player);
										putJournal1InPlayerInventory(player);
									}

								}, 50L);
						return;

					}

					if (!finishedEnki) {
						final Player player = p;

						Bukkit.getScheduler().scheduleSyncDelayedTask(this,
								new Runnable() {
									@Override
									public void run() {
										sendSimulatedPrivateMessage(player,
												Secret.SOMEONESNAMEPROPER,
												Secret.IFONLY);
									}

								}, 50L);
						return;
					}

					if (!gotMysteryBook1) {
						final Player player = p;

						Bukkit.getScheduler().scheduleSyncDelayedTask(this,
								new Runnable() {
									@Override
									public void run() {
										checkForDecoder(player);
									}

								}, 50L);
						return;
					}

					if (!gotJournal2) {
						final Player player = p;

						Bukkit.getScheduler().scheduleSyncDelayedTask(this,
								new Runnable() {
									@Override
									public void run() {
										giveJournal2(player);
									}

								}, 50L);
						return;
					}

					if (!gotWeatherControl) {
						final Player player = p;

						Bukkit.getScheduler().scheduleSyncDelayedTask(this,
								new Runnable() {
									@Override
									public void run() {
										sendSimulatedPrivateMessage(player,
												Secret.SOMEONESNAMEPROPER,
												Secret.DOINGRESEARCH);
									}

								}, 50L);
						return;
					}

					if (!gotJournal3) {
						final Player player = p;

						Bukkit.getScheduler().scheduleSyncDelayedTask(this,
								new Runnable() {
									@Override
									public void run() {
										sendSimulatedPrivateMessage(player,
												Secret.SOMEONESNAMEPROPER,
												Secret.FOUNDWCD);
										giveJournal3(player);
									}
								}, 50L);
						return;
					}

					if (!gotArtifact) {
						final Player player = p;
						Bukkit.getScheduler().scheduleSyncDelayedTask(this,
								new Runnable() {
									@Override
									public void run() {
										sendSimulatedPrivateMessage(player,
												Secret.SOMEONESNAMEPROPER,
												Secret.NOTHINGTOREPORT);
									}
								}, 50L);
						return;
					}

					if (!readyToTeleport) {
						final Player player = p;
						Bukkit.getScheduler().scheduleSyncDelayedTask(this,
								new Runnable() {
									@Override
									public void run() {
										checkForArtifact(player);
									}
								}, 50L);
						return;
					}

					if (!finishedArgo) {
						final Player player = p;
						final Location l = Secret.TOSHLOC;
						Bukkit.getScheduler().scheduleSyncDelayedTask(this,
								new Runnable() {
									@Override
									public void run() {
										sendSimulatedPrivateMessage(player,
												Secret.SOMEONESNAMEPROPER,
												"Woah!");
										player.teleport(l);
									}
								}, 50L);
						return;
					}

					if (!gotJournal4) {
						final Player player = p;
						Bukkit.getScheduler().scheduleSyncDelayedTask(this,
								new Runnable() {
									@Override
									public void run() {
										sendSimulatedPrivateMessage(player,
												Secret.SOMEONESNAMEPROPER,
												Secret.LATESTUPDS);
										giveJournal4(player);
									}
								}, 50L);
						return;
					}

					if (!summonedDave) {
						final Player player = p;
						Bukkit.getScheduler().scheduleSyncDelayedTask(this,
								new Runnable() {
									@Override
									public void run() {
										sendSimulatedPrivateMessage(player,
												Secret.SOMEONESNAMEPROPER,
												"No news yet...");
									}
								}, 50L);
						return;
					}

					if (!readyToFight) {
						final Player player = p;
						Bukkit.getScheduler().scheduleSyncDelayedTask(this,
								new Runnable() {
									@Override
									public void run() {
										sendSimulatedPrivateMessage(player,
												Secret.SOMEONESNAMEPROPER,
												Secret.WHATHAVEDONE);
										giveJournal5(player);
									}
								}, 50L);
						return;
					}

					if (!done) {
						final Player player = p;
						Bukkit.getScheduler().scheduleSyncDelayedTask(this,
								new Runnable() {
									@Override
									public void run() {
										sendSimulatedPrivateMessage(player,
												Secret.SOMEONESNAMEPROPER,
												"I just want to tell you good luck.  We're all counting on you");
										giveJournal4(player);
									}
								}, 50L);
						return;
					}
				}
			}
		}

		final Player player = p;
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			@Override
			public void run() {
				sendSimulatedPrivateMessage(player, Secret.SOMEONESNAMEPROPER,
						"Get closer to me!");
			}
		}, 50L);

	}

	@EventHandler
	public void onPlayerInteractWaterActivate(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (event.getClickedBlock().getType() == Material.GOLD_BLOCK) {

				Block nethBlock1 = Secret.NETHBLOCK1.getBlock();
				Block nethBlock2 = Secret.NETHBLOCK2.getBlock();
				Location someonesHouse = Secret.OVWORLDLOC;
				Block doneBlock = Secret.DONEBLOCK.getBlock();
				if (nethBlock2.equals(event.getClickedBlock())
						|| nethBlock1.equals(event.getClickedBlock())) {
					event.getPlayer().teleport(someonesHouse);
					return;
				}

				if (doneBlock.equals(event.getClickedBlock())) {
					if (podrickCfg.getConfig().getBoolean(
							event.getPlayer().getDisplayName() + ".Finished",
							false)) {

						sendSimulatedPrivateMessage(event.getPlayer(),
								"§r§d[Server]", Secret.CONGRATS);
						event.getPlayer()
								.teleport(
										event.getPlayer().getWorld()
												.getSpawnLocation());
						podrickCfg.getConfig().set(
								event.getPlayer().getDisplayName()
										+ "Completed"
										+ podrickCfg.getConfig().getInt(
												event.getPlayer()
														.getDisplayName()
														+ ".Seed", -1),
								System.currentTimeMillis());
						putBookAndStuffInMailbox(event.getPlayer()
								.getDisplayName());
						podrickCfg.getConfig().set(
								event.getPlayer().getDisplayName(), null);
						podrickCfg.saveConfig();
						podrickCfg.reloadConfig();
						return;
					}
				}

				if ((int) WATERROOMACTIVATE.getBlock().getLocation().getX() == (int) event
						.getClickedBlock().getLocation().getX()
						&& (int) WATERROOMACTIVATE.getBlock().getLocation()
								.getZ() == (int) event.getClickedBlock()
								.getLocation().getZ()) {
					if (!podrickCfg.getConfig().getBoolean(
							event.getPlayer().getDisplayName()
									+ ".GotWeatherControl", false)) {
						WATERROOMACTIVATE.getWorld().playSound(
								WATERROOMMINESHAFT, Sound.AMBIENCE_THUNDER,
								100, 1);

						ParticleEffect.FLAME.display((float) .15, (float) .5,
								(float) .15, (float) 0, 25, WATERROOMMINESHAFT,
								20);

						ItemStack is = new ItemStack(Material.PRISMARINE_SHARD,
								1);
						ItemMeta im = is.getItemMeta();
						im.setDisplayName(Secret.SECRETWCDNAME);

						List<String> lores = new ArrayList<String>();
						int seed = podrickCfg.getConfig()
								.getInt(event.getPlayer().getDisplayName()
										+ ".Seed", 0);

						lores.add(seed + "");
						im.setLore(lores);
						is.setItemMeta(im);

						final ItemStack imf = is;

						ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
						BookMeta meta = (BookMeta) book.getItemMeta();

						meta.setTitle(Secret.MYSTERYBOOK2TITLE);
						meta.setAuthor(Secret.MYSTERYBOOKAUTHOR);
						String bookText = podrickCfg.getConfig().getString(
								"BookText2");
						List<String> pages = new ArrayList<String>();
						int l = 0;
						int lineCount = 0;
						String page = "";
						for (int i = 0; i < bookText.length(); i++) {
							if (bookText.charAt(i) == '~') {
								pages.add(page);
								page = "";
								lineCount = 0;
							} else {
								if (bookText.charAt(i) == '^') {
									page += '§';
								} else {
									page += bookText.charAt(i);
								}

								l++;
								if (bookText.charAt(i) == '\n' || l > 19) {
									l = 0;
									lineCount++;
								}

								if (lineCount > 12
										&& (bookText.charAt(i) == ' ' || bookText
												.charAt(i) == '\n')) {
									pages.add(page);
									page = "";
									lineCount = 0;
								}
							}
						}
						pages.add(page);

						meta.setPages(pages);
						book.setItemMeta(meta);
						final ItemStack bookF = book;

						Bukkit.getScheduler().scheduleSyncDelayedTask(this,
								new Runnable() {
									@Override
									public void run() {
										WATERROOMMINESHAFT
												.getWorld()
												.dropItem(WATERROOMMINESHAFT,
														bookF)
												.setVelocity(
														new Vector(0, 0, .125));

										WATERROOMMINESHAFT
												.getWorld()
												.dropItem(WATERROOMMINESHAFT,
														imf)
												.setVelocity(
														new Vector(0, 0, .125));
									}
								});
						podrickCfg.getConfig().set(
								event.getPlayer().getDisplayName()
										+ ".GotWeatherControl", true);
						podrickCfg.saveConfig();
						podrickCfg.reloadConfig();
					}
				}
			}
		}
	}

	protected void checkForArtifact(Player p) {
		for (ItemStack is : p.getInventory().getContents()) {
			if (is != null) {
				if (is.hasItemMeta()) {
					if (is.getItemMeta().hasLore()) {
						if (is.getItemMeta().hasDisplayName()
								&& is.getItemMeta().getDisplayName()
										.equals(Secret.TPA)) {
							int playerSeed = podrickCfg.getConfig().getInt(
									p.getDisplayName() + ".Seed", -1);
							int thisItemSeed = Integer.parseInt(is
									.getItemMeta().getLore().get(0));
							if (playerSeed == thisItemSeed) {
								sendSimulatedPrivateMessage(p,
										Secret.SOMEONESNAME, Secret.ATPAM);
								p.getInventory().remove(is);
								podrickCfg.getConfig()
										.set(p.getDisplayName()
												+ ".ReadyToTeleport", true);
								podrickCfg.saveConfig();
								podrickCfg.reloadConfig();
								return;
							}
						}
					}
				}
			}
		}

		sendSimulatedPrivateMessage(p, Secret.SOMEONESNAME, Secret.HAVEDISC);
	}

	@Override
	public void onPluginMessageReceived(String channel, Player player,
			byte[] message) {
		if (!channel.equals("BungeeCord")) {
			return;
		}

		ByteArrayDataInput in = ByteStreams.newDataInput(message);
		String subchannel = in.readUTF();
		if (subchannel.equals("Chat")) {

			short len = in.readShort();
			byte[] msgbytes = new byte[len];
			in.readFully(msgbytes);

			DataInputStream msgin = new DataInputStream(
					new ByteArrayInputStream(msgbytes));
			try {
				String somedata = msgin.readUTF();
				Long time = msgin.readLong();
				if (System.currentTimeMillis() - time < 50)
					simulateChat(somedata);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // Read the data in the same way you wrote it

		}

		if (subchannel.equals("PlC")) {
			short len = in.readShort();
			byte[] msgbytes = new byte[len];
			in.readFully(msgbytes);

			DataInputStream msgin = new DataInputStream(
					new ByteArrayInputStream(msgbytes));
			try {
				lastCreativeList = msgin.readLong();
				int count = msgin.readInt();
				playersInCreative = new ArrayList<String>();
				for (int i = 0; i < count; i++) {
					playersInCreative.add(msgin.readUTF());
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // Read the data in the same way you wrote it
		}

		if (subchannel.equals("PlE")) {
			short len = in.readShort();
			byte[] msgbytes = new byte[len];
			in.readFully(msgbytes);

			DataInputStream msgin = new DataInputStream(
					new ByteArrayInputStream(msgbytes));
			try {
				lastEventList = msgin.readLong();
				int count = msgin.readInt();
				playersInEvent = new ArrayList<String>();
				for (int i = 0; i < count; i++) {
					playersInEvent.add(msgin.readUTF());
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // Read the data in the same way you wrote it
		}

		if (subchannel.equals("Victory")) {
			getLogger().info("Victory received!");
			short len = in.readShort();
			byte[] msgbytes = new byte[len];
			in.readFully(msgbytes);

			DataInputStream msgin = new DataInputStream(
					new ByteArrayInputStream(msgbytes));
			try {
				String uuids = msgin.readUTF();
				int count = msgin.readInt();
				UUID uuid = UUID.fromString(uuids);
				getLogger().info(uuids);
				getLogger().info(count + "");
				SerenityPlayer sp = serenityPlayers.get(uuid);
				sendATextToHal("Ember's Adventure", sp.getName() + " earned "
						+ count + " heads!");
				if (count > 0) {
					putRewardHeadInMailbox(uuid, count);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // Read the data in the same way you wrote it
		}
	}

	private void simulateChat(String s) {
		if (Bukkit.getOnlinePlayers().size() > 0)
			Bukkit.broadcastMessage(s);
		// todo.. ignore and stuff
	}

	public void sendPlayerListToBungee() {
		List<String> players = new ArrayList<String>();
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (!p.isOp()) {
				SerenityPlayer spc = serenityPlayers.get(p.getUniqueId());
				players.add(spc.getChatColor() + spc.getName());
			}
		}

		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Forward"); // So BungeeCord knows to forward it
		out.writeUTF("ALL");
		out.writeUTF("PlS"); // The channel name to check if this your data

		ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
		DataOutputStream msgout = new DataOutputStream(msgbytes);
		try {
			msgout.writeLong(System.currentTimeMillis());
			msgout.writeInt(players.size());
			for (String s : players) {
				msgout.writeUTF(s);
			}

			out.writeShort(msgbytes.toByteArray().length);
			out.write(msgbytes.toByteArray());
			Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
			player.sendPluginMessage(this, "BungeeCord", out.toByteArray());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // You can do anything you want with msgout
	}
}
