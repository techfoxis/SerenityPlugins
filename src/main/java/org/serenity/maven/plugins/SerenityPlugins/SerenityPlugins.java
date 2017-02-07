package org.serenity.maven.plugins.SerenityPlugins;

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
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import net.minecraft.server.v1_11_R1.Enchantment.Rarity;
import net.minecraft.server.v1_11_R1.EnchantmentSlotType;
import net.minecraft.server.v1_11_R1.EnumItemSlot;
import net.minecraft.server.v1_11_R1.IChatBaseComponent;
import net.minecraft.server.v1_11_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_11_R1.NBTTagCompound;
import net.minecraft.server.v1_11_R1.NBTTagList;
import net.minecraft.server.v1_11_R1.Packet;
import net.minecraft.server.v1_11_R1.PacketPlayOutChat;
import net.minecraft.server.v1_11_R1.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_11_R1.PlayerConnection;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.message.Message;
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
import org.bukkit.Particle;
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
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_11_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Hanging;
import org.bukkit.entity.Horse;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Horse.Style;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.entity.Rabbit;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Snowball;
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
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerFishEvent;
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
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.Dispenser;
import org.bukkit.material.Wool;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;
import org.serenity.maven.plugins.SerenityPlugins.Command.SerenityCommand;

import com.avaje.ebeaninternal.server.deploy.generatedproperty.GeneratedCounterLong;
import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public final class SerenityPlugins extends JavaPlugin implements Listener, PluginMessageListener {

	
	
	
	  ///////////////
	 // Task List //
	///////////////
	
	// TODO Finish Refactoring o_0
	// TODO Reference previous commits to identify useful disabled features
	// TODO Party Intensity
	// TODO MultiMessage
	// TODO Trapped Chests are Public? 
	// TODO Party Horse
	// TODO /Coords has link to map
	
	
	
	
	  ////////////////////////
	 // Field Declarations //
	////////////////////////
	
	// State
	public SerenityPlugins global = this;
	public boolean votingForDay;
	public boolean pluginReady = false;
	private boolean showingSql; // TODO Extract DB management into its own class
	
	// Configuration
	private ConfigAccessor protectedAreasCfg; // TODO Migrate ProtectedAreas to DB
	public ConfigAccessor stringsCfg; // TODO WTF
	public ConfigAccessor emailCfg;
	public ConfigAccessor bookCfg; // TODO WTF
	public ConfigAccessor linksCfg;
	public ConfigAccessor voteCfg; // TODO WTF
	
	// Special Effects
	public boolean opParticles;
	public boolean opParticlesDeb;
	public short specEff = 0;

	// Caches
	public List<Mailbox> mailboxes;
	public List<ProtectedArea> protectedAreas;
	public List<Player> playersPreppedToUnProtectChunk;
	public List<PlayerProtect> playersPreppedToProtectArea;
	public List<Long> playerLags;
	public Set<PlayerStatus> playerStatuses;
	public HashMap<UUID, SerenityPlayer> serenityPlayers;
	public List<String> creativePlayers;
	public List<String> eventPlayers;
	public Long lastCreativeList;
	public Long lastEventList;
	
	// Misc.
	public String mainMotd = "";
	public Random rand = new Random();
	
	// Finals
	public final String FIREWORK_SHOW_HEAD = "/give @p skull 1 3 {display:{Name:\"Fireworks Show\"},SkullOwner:{Id:\"4871fc40-b2c7-431d-9eb8-b54cd666dca7\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzg0MGI4N2Q1MjI3MWQyYTc1NWRlZGM4Mjg3N2UwZWQzZGY2N2RjYzQyZWE0NzllYzE0NjE3NmIwMjc3OWE1In19fQ==\"}]}}}";
	public final String SURVIVE_AND_THRIVE_HEAD = "/give @p skull 1 3 {display:{Name:\"Survive And Thrive II\"},SkullOwner:{Id:\"a7319ab0-26a7-4992-8aa8-27abb86937f1\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTA5ZDM4Y2ExNGRkMjRkNWU1ODYyMjkyOWM0MmM3YTAyMzU0ODYxNmQ2NTA2YWY5ZGNiMzFlNGE1M2IyOTMzIn19fQ==\"}]}}}";
	public final Long FW_COOLDOWN_TIME = 480000L;
	public final int MAX_DISTANCE = 700;

	public final List<DyeColor> ALL_DYES = new ArrayList<DyeColor>() { // WTF
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

	public final List<SerenityCommand> ALL_COMMANDS = new ArrayList<SerenityCommand>() {
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

	public final List<Color> ALL_COLORS = new ArrayList<Color>() {
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

	public final List<Integer> ALL_RECORD_IDS = new ArrayList<Integer>() {
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

	public final List<ChatColor> ALL_CHAT_COLORS = new ArrayList<ChatColor>() {
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

	// Strings
	public HashMap<String, String> englishStrings;
	public enum stringKeys {
		PORTBADPORTAL, TIMEONEHOUR, TIMETHREEHOUR, TIMETWELVEHOUR, PORTENDPORTALWARNING, PORTEARLYPORTALATTEMPT, MAILHASMAIL, PROTISSTUCK, CHATTRIEDTOCHATWITHGLOBALCHATDISABLED, JOINPLAYERFIRSTLOGIN, MAILTRIEDTOEXPANDMAILBOX, MAILCREATEDAMAILBOX, MAILALREADYHASAMAILBOX, MAILOPENEDAPUBLICMAILBOX, MAILINTERRACTEDWITHAMAILBOX, MAILDESTROYEDMAILBOX, MAILTRIEDTOBREAKPUBLICMAILBOX, BEDSOMEONEENTEREDABED, PROTOWNBLOCK, PROTDOESNOTOWNBUTHASPERMISSION, PROTDOESNOTOWNBLOCK, PROTNOBODYOWNSBLOCK, RANDOMTPWAIT, RANDOMTPTOOFAR, RANDOMTPWRONGWORLD, CHATHELP, PROTALREADYPREPPED, PROTPREPPEDTOPROTECT, PROTAREABADARG, PROTAREATOOSMALL, PROTAREATOOBIG, PROTAREAREADY, PROTUNCLAIMPREPPED, PROTUNCLAIMSTILLPREPPED, PROTNOAREAS, PROTTRUSTLIST, PROTAREALIST, PROTEXTENDEDLISTDATA, PROTSTUCKABUSEATTEMPT, PROTSTUCKABUSEATTEMPTNOTEVENPROT, PROTTIMEBADARG, PROTADDEDTRUST, PROTTRUSTISSUES, PROTCANTFINDTRUST, PROTUNTRUSTED, PROTHELP, PROTTOOFAR, PROTALREADYOWN, PROTAREACLAIMED, PROTFIRSTCORNER, PROTINTERSECT, PROTPREMATURE, PROTNOTYET, PROTSUCCESS, PROTNOTOVERWORLD, PROTTOOFARAWAYTOUNCLAIM, PROTUNCLAIMSUCCESS, PROTUNCLAIMDOESNOTOWN, PROTUNCLAIMNOBODYOWNS, CHATCANSEEGLOBAL, CHATCANTSEEGLOBAL, STATUSSUCCESS, PORTALOVERWORLD, PORTALNETHER, LASTSEENNOTFOUND, BEDVOTING, BEDOKNOTVOTING, AFKALREADYAFK, GETTIMETICKS, MAILDOESNTHAVEMAILBOX, MAILCOULDNOTFINDBOX, MAILTOSELF, MAILEMPTY, MAILFULL, MAILNOTENOUGHSPACE, MAILSUCCESS, COMPASSHELP, COMPASSSPAWN, COMPASSBED, COMPASSBEDDESTROYED, COMPASSMAILBOX, COMPASSNOMAILBOX, COMPASSHERE, COMPASSCUST, CHATCOLORINVALID, CHATCOLOREARLY, CHATPUBLICNOW, CHATLOCALNOW, LAGWAIT, IGNORECOULDNOTFIND, IGNORENOLONGERIGNORING, IGNORESUCCESS, NOPERMS, FIREWORKSCREATE, FIREWORKSEXISTS, FIREWORKSEARLYBREAKATTEMPT, FIREWORKNONOWNERBREAK, FIREWORKBREAKSUCCESS
	}

	// TODO Extract all head related things into its own class
	// Heads
	public List<Head> allRewardHeads;
	public HashMap<String, String> halloweenHeads;
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

	////
	// Initialization
	////

	@Override
	public void onEnable() {
		lastCreativeList = System.currentTimeMillis();
		lastEventList = System.currentTimeMillis();
		creativePlayers = new ArrayList<String>();
		eventPlayers = new ArrayList<String>();
		sdtf.setTimeZone(TimeZone.getTimeZone("UTC"));
		ignoreArmor();
		getAllHeads();

		ItemStack fw = new ItemStack(Material.FIREWORK, 1);
		ItemMeta im = fw.getItemMeta();
		im.setDisplayName("§dFirework Show!");
		List<String> lore = new ArrayList<String>();
		lore.add("Put the head on an armor stand after crafting");
		im.setLore(lore);
		fw.setItemMeta(im);
		ShapelessRecipe recipe = new ShapelessRecipe(fw);
		recipe.addIngredient(1, Material.GOLD_BLOCK);
		recipe.addIngredient(1, Material.OBSIDIAN);
		Bukkit.getServer().addRecipe(recipe);

		getServer().getPluginManager().registerEvents(this, this);
		this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);
		serenityPlayers = new HashMap<UUID, SerenityPlayer>();

		createTables();
		loadSerenityPlayersFromDatabase();
		loadIgnoringFromDatabase();
		setOnlinePlayersOnline();
		loadSerenityMailboxesFromDatabase();
		loadMessagesFromDatabase();
		loadStatusesFromDatabase();
		// Secret = new Secrets();
		protectedAreasCfg = new ConfigAccessor(this, "protectedareas.yml");
		stringsCfg = new ConfigAccessor(this, "strings.yml");
		emailCfg = new ConfigAccessor(this, "email.yml");
		bookCfg = new ConfigAccessor(this, "book.yml");
		linksCfg = new ConfigAccessor(this, "links.yml");
		voteCfg = new ConfigAccessor(this, "vote.yml");
		playersPreppedToProtectArea = new ArrayList<PlayerProtect>();
		playersPreppedToUnProtectChunk = new ArrayList<Player>();
		playerLags = new ArrayList<Long>();
		protectedAreas = new ArrayList<ProtectedArea>();
		votingForDay = false;
		protectedAreasCfg.saveDefaultConfig();
		getLogger().info("Added " + playerStatuses.size() + " statuses");

		ConfigurationSection protectedChunksFromConfig = protectedAreasCfg.getConfig()
				.getConfigurationSection("ProtectedAreas");

		try {
			for (String key : protectedChunksFromConfig.getKeys(false)) {
				ConfigurationSection namedChunksFromConfig = protectedAreasCfg.getConfig()
						.getConfigurationSection("ProtectedAreas." + key);
				for (String subkey : namedChunksFromConfig.getKeys(true)) {
					ArrayList<String> coords = new ArrayList<String>();

					if (!subkey.equals("Trusts")) {
						for (Object subSubkey : namedChunksFromConfig.getList(subkey)) {
							coords.add(subSubkey.toString());
						}

						Location l = new Location(Bukkit.getWorld(coords.get(0)),
								(double) Integer.parseInt(coords.get(1)), (double) 1.0,
								(double) Integer.parseInt(coords.get(2)));

						Location l2 = new Location(Bukkit.getWorld(coords.get(0)),
								(double) Integer.parseInt(coords.get(3)), (double) 1.0,
								(double) Integer.parseInt(coords.get(4)));

						ProtectedArea pa = new ProtectedArea(l, l2, Bukkit.getOfflinePlayer(key).getName());

						protectedAreas.add(pa);
					}
				}
			}
		} catch (Exception e) {
			getLogger().info("Exception thrown while trying to add protected areas\n" + e.toString());
		}

		getLogger().info("Added " + protectedAreas.size() + " protected areas");

		try {
			for (String key : protectedChunksFromConfig.getKeys(false)) {
				ConfigurationSection namedChunksFromConfig = protectedAreasCfg.getConfig()
						.getConfigurationSection("ProtectedAreas." + key);
				for (String subkey : namedChunksFromConfig.getKeys(true)) {
					ArrayList<String> coords = new ArrayList<String>();
					if (subkey.equals("Trusts")) {
						for (Object subSubkey : namedChunksFromConfig.getList(subkey)) {
							coords.add(subSubkey.toString());
						}

						for (ProtectedArea pa : protectedAreas) {
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
			getLogger().info("Exception thrown while trying to add protected areas trust");
		}

		englishStrings = new HashMap<String, String>();

		try {
			ConfigurationSection englishStringsFromConfig = stringsCfg.getConfig()
					.getConfigurationSection("Language.English");
			for (String subkey : englishStringsFromConfig.getKeys(true)) {
				String translation = englishStringsFromConfig.getString(subkey);
				translation = translation.replace('@', '§');
				translation = translation.replace('^', '\n');
				englishStrings.put(subkey, translation);
			}
		} catch (Exception e) {
			getLogger().info("Exception thrown while trying to english translations");
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
		runMailboxHearts();
		runEntityCountWatchdog();

		pluginReady = true;
		getLogger().info("Serenity Plugins enabled");
	}

	
	
	
	  /////////////
	 // Methods //
	/////////////
	
	// DB
	protected Connection getConnection() {
		
		try {
			return DriverManager.getConnection(this.getConfig().getString("database"),
					this.getConfig().getString("dbUser"), this.getConfig().getString("dbPass"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	private void loadIgnoringFromDatabase() {
		try {
			Long now = System.currentTimeMillis();
			Connection conn = getConnection();
			Statement st = conn.createStatement();

			ResultSet rs = st.executeQuery("Select * FROM Ignores;");
			while (rs.next()) {
				String annoyed = rs.getString("Annoyed");
				String annoying = rs.getString("Annoying");
				serenityPlayers.get(UUID.fromString(annoyed)).getIgnoreList().add(UUID.fromString(annoying));
			}
			if (conn != null)
				conn.close();
			getLogger().info("Time to get Serenity Ignorers cache: " + (System.currentTimeMillis() - now) + "ms");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void getAllHeads() {
		allRewardHeads = new ArrayList<Head>();
		try {
			Long now = System.currentTimeMillis();
			Connection conn = getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("Select * FROM heads");
			while (rs.next()) {
				String raw = rs.getString("Head");
				String name = rs.getString("Name");
				int rarity = rs.getInt("Rarity");
				int id = rs.getInt("ID");

				switch (rarity) {
				case 0:
					name = "§f" + name;
					break;
				case 1:
					name = "§e" + name;
					break;
				case 2:
					name = "§a" + name;
					break;
				case 3:
					name = "§d" + name;
					break;
				case 5:
					name = "§4" + name;
					break;
				}

				String actualCommand = raw.replace("/give @p", "give %s");
				actualCommand = actualCommand.replaceFirst("\\{.*?\\},", "\\{");
				actualCommand = actualCommand.replaceFirst("SkullOwner:\\{", "SkullOwner:\\{Name:\"" + name + "\",");
				Head newHead = new Head(name, actualCommand, id, rarity);
				allRewardHeads.add(newHead);
			}
			if (conn != null)
				conn.close();
			getLogger().info("Time to get Status: " + (System.currentTimeMillis() - now) + "ms");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	private void insertSerenityPlayer(Player p) {
		String chatColor = "";
		chatColor += getChatColor(p.getUniqueId());

		String sql = "INSERT INTO Player VALUES ('" + p.getUniqueId().toString() + "','" + p.getName() + "','"
				+ p.getAddress().getAddress().getHostAddress() + "'," + 0 + "," + p.getFirstPlayed() + ","
				+ p.getLastPlayed() + ",'" + chatColor + "',1033,0," + (p.isWhitelisted() ? "1" : "0") + ",0,0,1,0)";

		executeSQLAsync(sql);
	}
	protected void saveDirtySerenityPlayers() {

		Bukkit.getScheduler().runTaskLaterAsynchronously(this, new Runnable() {
			@Override
			public void run() {
				for (Map.Entry<UUID, SerenityPlayer> entry : serenityPlayers.entrySet()) {
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
	protected boolean addDatabaseMessage(OfflineMessage omf) {
		Bukkit.getScheduler().runTaskLaterAsynchronously(this, new Runnable() {
			@Override
			public void run() {
				String sql = "INSERT INTO Messages (FromUUID, ToUUID, Message, ReadStatus, Time) VALUES ('"
						+ omf.getFrom().toString() + "','" + omf.getTo().toString() + "','" + omf.getMessage() + "',"
						+ (omf.isRead() ? "1" : "0") + "," + omf.getTime() + ");";
				executeSQLBlocking(sql);
				sql = "Select * From Messages Where Time = " + omf.getTime() + " AND Message = '" + omf.getMessage()
						+ "';";
				queryAndAddTheOM(sql, omf);
			}
		}, 0L);
		return true;
	}
	protected void queryAndAddTheOM(String sql, OfflineMessage omf) {
		try {
			Connection conn = getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("M_ID");
				omf.setID(id);
				serenityPlayers.get(omf.getTo()).getOfflineMessages().add(omf);
			}
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}
	private ResultSet getResults(String sql) {
		try {
			Connection conn = getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			return rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	// Party
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
		if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName()
				&& item.getItemMeta().getDisplayName().equals("§dParty Armor")) {
			if (wearer == null) {
				return true;
			}
			if (item.getItemMeta().getLore().get(1).equals(wearer.getDisplayName())) {
				return true;
			} else {
				wearer.damage(1);
			}
		}
		return false;
	}
	private boolean isPartyItemStack(ItemStack item) {
		if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName()
				&& item.getItemMeta().getDisplayName().equals("§dParty Armor")) {
			return true;
		}
		return false;
	}
	private String getPartyOwner(ItemStack item) {
		if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName()
				&& item.getItemMeta().getDisplayName().equals("§dParty Armor")) {
			return item.getItemMeta().getLore().get(1);
		}
		return null;
	}
	protected void celebrate(SerenityPlayer sp) {
		final Player pf = Bukkit.getPlayer(sp.getUUID());
		if (pf.isOnline()) {
			final Short num = sp.getCelebrateEffect();
			for (int i = 0; i < 10; i++) {
				Bukkit.getScheduler().runTaskLaterAsynchronously(global, new Runnable() {
					@Override
					public void run() {
						Location loc = pf.getLocation();
						loc.setY(loc.getY() + .125);

						switch (num) {
						case 4:
							loc.setY(loc.getY() + 3);
							loc.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc, 4, 1f, .5f, 1f, .0001);
							break;
						case 1:
							loc.getWorld().spawnParticle(Particle.FLAME, loc, 4, .25f, .125f, .25f, .0001);
							break;
						case 2:
							loc.getWorld().spawnParticle(Particle.NOTE, loc, 4, .25f, .125f, .25f, 50);
							break;
						case 3:
							loc.getWorld().spawnParticle(Particle.SPELL_MOB, loc, 4, .25f, .125f, .25f, 25);
							break;

						case 0:
							loc.getWorld().spawnParticle(Particle.HEART, loc, 4, .25f, .125f, .25f, 25);
							break;
						case 5:
							loc.getWorld().spawnParticle(Particle.SMOKE_LARGE, loc, 4, .25f, .125f, .25f, .002);
							break;
						case 6:
							loc.getWorld().spawnParticle(Particle.REDSTONE, loc, 4, .25f, .125f, .25f, 25);
							break;
						case 7:
							loc.getWorld().spawnParticle(Particle.PORTAL, loc, 4, .25f, .125f, .25f, .002f);
							break;
						case 8:
							loc.getWorld().spawnParticle(Particle.LAVA, loc, 4, .25f, .125f, .25f, .002f);
							break;
						case 9:
							loc.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, loc, 4, .25f, .125f, .25f, .002f);
							break;
						case 10:
							loc.getWorld().spawnParticle(Particle.SPELL_WITCH, loc, 4, .25f, .125f, .25f, .002f);
							break;
						}

					}
				}, i * 2L);
			}
		}
	}
	@EventHandler
	public void onCelebrateFW(PlayerInteractEvent event) {
		SerenityPlayer sp = serenityPlayers.get(event.getPlayer().getUniqueId());
		if (sp.isCelebrating()) {
			if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)
					|| event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
				if (event.getPlayer().getItemInHand().getType() == Material.YELLOW_FLOWER) {
					Block target = event.getPlayer().getTargetBlock((Set<Material>) null, MAX_DISTANCE);
					Location location = target.getLocation();

					doRandomFirework(location);
					event.setCancelled(true);
				}
			}
		}
	}
	@EventHandler
	public void onCelebrateSomethingElse(PlayerInteractEvent event) {
		SerenityPlayer sp = serenityPlayers.get(event.getPlayer().getUniqueId());
		if (sp.isCelebrating()) {
			if (event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
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
	private void celebrateNewYears(CommandSender sender) {
		
		  if (sender instanceof Player) { SerenityPlayer sp =
			  serenityPlayers.get(((Player) sender) .getUniqueId());
			  sp.setCelebrating(!sp.isCelebrating()); 
			  if (sp.isCelebrating()) { 
				  sp.setCelebrateEffect((short) 11);
				  sender.sendMessage( ChatColor.DARK_RED + "Happy New Year" + "\n§7Left and right click with a §6dandelion!"); 
			  } else {
				  sender.sendMessage("§7You are no longer celebrating"); 
			  }
		  }
		}
	
	// TODO Party Intensity (More party items, more party)
	// Party Armor
	@EventHandler
	public void CraftItemEvent(org.bukkit.event.inventory.CraftItemEvent event) {
		if (event.getRecipe().getResult().hasItemMeta() && event.getRecipe().getResult().getItemMeta().hasDisplayName()
				&& event.getRecipe().getResult().getItemMeta().getDisplayName().contains("§dParty Armor")) {
			if (event.getWhoClicked() instanceof Player) {
				Player p = (Player) event.getWhoClicked();
				if (getPlayerMinutes(p.getUniqueId()) < 2880) {
					event.setCancelled(true);
					p.sendMessage("§cYou need more than 48 hours on the server to party");
					return;
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
				getLogger().info("§c" + p.getDisplayName() + " crafted some party armor!");
			}
		}
	}
	private void PartyLeather() {
		for (int i = 0; i < 20; i++) {
			Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(this, new Runnable() {
				@Override
				public void run() {
					for (Player p : Bukkit.getOnlinePlayers()) {
						tryToParty(p);
						tryToTrophy(p);
					}

				}
			}, i + 1L);
		}
	}
	private void tryToParty(Player p) {
		if (isPartyItemStack(p.getInventory().getHelmet(), p)) {
			p.getInventory().setHelmet(getPartyEquipment(Material.LEATHER_HELMET, p.getDisplayName()));
		}
		if (isPartyItemStack(p.getInventory().getChestplate(), p)) {
			p.getInventory().setChestplate(getPartyEquipment(Material.LEATHER_CHESTPLATE, p.getDisplayName()));
		}
		if (isPartyItemStack(p.getInventory().getLeggings(), p)) {
			p.getInventory().setLeggings(getPartyEquipment(Material.LEATHER_LEGGINGS, p.getDisplayName()));
		}
		if (isPartyItemStack(p.getInventory().getBoots(), p)) {
			p.getInventory().setBoots(getPartyEquipment(Material.LEATHER_BOOTS, p.getDisplayName()));
		}

		for (Entity e : p.getNearbyEntities(5, 5, 5)) {
			if (e instanceof ArmorStand) {
				ArmorStand as = (ArmorStand) e;

				ArmorParty(as, as.getEquipment().getHelmet(), 0);
				ArmorParty(as, as.getEquipment().getChestplate(), 1);
				ArmorParty(as, as.getEquipment().getLeggings(), 2);
				ArmorParty(as, as.getEquipment().getBoots(), 3);
			}
		}
	}
	private void ArmorParty(ArmorStand as, ItemStack helmet, int type) {
		return;/*
				 * if (isPartyItemStack(helmet)) { LeatherArmorMeta meta =
				 * (LeatherArmorMeta) helmet.getItemMeta(); float prog =
				 * (Bukkit.getWorld("world").getTime() % 100); prog /= 100;
				 * Color c = Rainbow(prog); meta.setColor(c); ItemStack is =
				 * null;
				 * 
				 * switch (type) { case 0: {
				 * is.setType(Material.LEATHER_HELMET); as.getEquipment().sethe
				 * break; } case 1: { is.setType(Material.LEATHER_BOOTS); break;
				 * } case 2: { is.setType(Material.LEATHER_LEGGINGS); break; }
				 * default: { is.setType(Material.LEATHER_CHESTPLATE); break; }
				 * }
				 * 
				 * 
				 * 
				 * }
				 */
	}

	// Firework Show
	@EventHandler
	public void CraftFWEvent(org.bukkit.event.inventory.CraftItemEvent event) {
		if (event.getRecipe().getResult().hasItemMeta() && event.getRecipe().getResult().getItemMeta().hasDisplayName()
				&& event.getRecipe().getResult().getItemMeta().getDisplayName().contains("Firework Show!")) {
			if (event.getWhoClicked() instanceof Player) {
				Player p = (Player) event.getWhoClicked();
				givePlayerFwHead(p);
				event.setCancelled(true);
				event.getInventory().clear();
				p.sendMessage("§aYou made a Fireworks show item!  §7Put it on an armor stand");
			}
		}
	}
	@EventHandler
	public void onFwDamage(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof ArmorStand) {
			ArmorStand a = (ArmorStand) event.getEntity();
			if (a.getEquipment().getHelmet().getItemMeta() instanceof SkullMeta) {
				SkullMeta sm = (SkullMeta) a.getEquipment().getHelmet().getItemMeta();
				if (sm.hasOwner()) {
					if (sm.getOwner().contains("§6Fireworks Show")) {
						Long diff = getTimeSinceLastShow(a);
						if (diff > FW_COOLDOWN_TIME) {

						} else {
							if (event.getDamager() instanceof Player) {
								Player p = (Player) event.getDamager();
								p.sendMessage("§cYou cannot destroy a cooling down Fireworks Show");
								event.setCancelled(true);
							}
						}
					}
				}
			}
		}
	}
	@EventHandler
	public void onRightClickFw(PlayerArmorStandManipulateEvent event) {
		if (event.getRightClicked() instanceof ArmorStand) {
			ArmorStand a = (ArmorStand) event.getRightClicked();
			if (a.getEquipment().getHelmet().getItemMeta() instanceof SkullMeta) {
				SkullMeta sm = (SkullMeta) a.getEquipment().getHelmet().getItemMeta();
				if (sm.hasOwner()) {
					if (sm.getOwner().contains("§6Fireworks Show")) {
						event.setCancelled(true);
						Long diff = getTimeSinceLastShow(a);
						if (diff > FW_COOLDOWN_TIME) {
							startFireworkShow(a.getLocation(), 30, .80);
							a.removeMetadata("lastshow", this);
							a.setMetadata("lastshow", new FixedMetadataValue(this, System.currentTimeMillis()));
						} else {
							event.getPlayer()
									.sendMessage(String.format(
											"§cThe firework show is cooling down!  You must wait %s more seconds",
											(FW_COOLDOWN_TIME - diff) / 1000));
						}
					}
				}
			}
		}
	}
	private static Long getTimeSinceLastShow(ArmorStand a) {
		Long lastShow = 0L;
		for (MetadataValue mdv : a.getMetadata("lastshow")) {
			lastShow = mdv.asLong();
		}
		Long now = System.currentTimeMillis();
		Long diff = now - lastShow;
		return diff;
	}

	
	// Trophies
	private void tryToTrophy(Player p) {
		int range = 15;
		for (Entity e : p.getNearbyEntities(range, range, range)) {
			if (e instanceof ArmorStand) {
				ArmorStand a = (ArmorStand) e;

				if (a.getEquipment().getHelmet().getItemMeta() instanceof SkullMeta) {
					SkullMeta sm = (SkullMeta) a.getEquipment().getHelmet().getItemMeta();
					if (sm.hasOwner()) {
						if (sm.getOwner().contains("§dWitch-king of Angmar")) {
							witchKing(a);
						}
						if (sm.getOwner().contains("§dWater Orb")) {
							waterOrb(a);
						}
						if (sm.getOwner().contains("§dDirt Orb")) {
							dirtOrb(a);
						}
						if (sm.getOwner().contains("§dAir Orb")) {
							airOrb(a);
						}

						if (sm.getOwner().contains("§dFire Orb")) {
							lavaOrb(a);
						}

						if (sm.getOwner().contains("§6Fireworks Show")) {
							fireworkAnimation(a);
						}

						if (sm.getOwner().contains("§9Survive and Thrive II")) {
							surviveAndThrive(a);
						}

					}
				}

				if (a.getEquipment().getHelmet().hasItemMeta()) {
					if (a.getEquipment().getHelmet().getItemMeta().hasDisplayName()) {
						if (a.getEquipment().getHelmet().getItemMeta().getDisplayName().contains("§6Winner")) {
							trophy(a);
						}
						if (a.getEquipment().getHelmet().getItemMeta().getDisplayName().contains("§7Participant")) {
							trophy2(a);
						}
						if (a.getEquipment().getHelmet().getItemMeta().getDisplayName().contains("§7Most")) {
							trophy2(a);
						}
						if (a.getEquipment().getHelmet().getItemMeta().getDisplayName().contains("§9Globe")) {
							globe(a);
						}

						if (a.getEquipment().getHelmet().getItemMeta().getDisplayName().contains("§9Multi-Ore")) {
							multiOre(a);
						}

						if (a.getEquipment().getHelmet().getItemMeta().getDisplayName().contains("§9Snow Globe")) {
							snowGlobe(a);
						}

						if (a.getEquipment().getHelmet().getItemMeta().getDisplayName().contains("§9Money Bag")) {
							moneyBag(a);
						}

						if (a.getEquipment().getHelmet().getItemMeta().getDisplayName()
								.contains("§6Best Haunted House 2016")) {
							hauntedHouse(a);
						}

						if (a.hasGravity()) {
							a.setGravity(false);
						}
					}
				}
			}
		}
	}
	private void hauntedHouse(ArmorStand a) {
		double y = a.getHeadPose().getY();
		y += .025;
		if (y < 0) {
			y = y + 6.25;
		}

		a.setHeadPose(new EulerAngle(0, y, 0));
		if (rand.nextDouble() < .3) {
			a.getWorld().spawnParticle(Particle.SMOKE_LARGE, a.getEyeLocation(), 1, .005, .005, .005, 0);
			a.getWorld().spawnParticle(Particle.FLAME, a.getEyeLocation(), 3, 1, 1, 1, 0);
		}
	}
	private void surviveAndThrive(ArmorStand a) {
		double y = a.getHeadPose().getY();
		y += .025;
		if (y < 0) {
			y = y + 6.25;
		}

		a.setHeadPose(new EulerAngle(0, y, 0));
		if (rand.nextDouble() < .3) {
			a.getWorld().spawnParticle(Particle.SMOKE_LARGE, a.getEyeLocation(), 1, .005, .005, .005, 0);
			a.getWorld().spawnParticle(Particle.SPELL_WITCH, a.getEyeLocation(), 7, 2, 2, 5, .3);
		}
	}
	private void fireworkAnimation(ArmorStand a) {
		if (a.hasMetadata("lastshow")) {
			Long diff = getTimeSinceLastShow(a);
			if (diff > FW_COOLDOWN_TIME) {
				if (rand.nextDouble() < .3) {
					a.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, a.getEyeLocation(), 1, .2, .2, .2, .01);
					double y = a.getHeadPose().getY();
					y -= .025;
					if (y < 0) {
						y = y + 6.25;
					}

					a.setHeadPose(new EulerAngle(0, y, 0));
				}
			} else {
				if (rand.nextDouble() < .8) {
					a.getWorld().spawnParticle(Particle.REDSTONE, a.getEyeLocation(), 1, .25, .25, .25, 0);
				}
			}
		} else {
			a.setMetadata("lastshow", new FixedMetadataValue(this, System.currentTimeMillis() - FW_COOLDOWN_TIME));
		}
	}
	private void lavaOrb(ArmorStand a) {
		double y = a.getHeadPose().getY();
		y -= .025;
		if (y < 0) {
			y = y + 6.25;
		}

		a.setHeadPose(new EulerAngle(0, y, 0));
		if (rand.nextDouble() < .3) {
			a.getWorld().spawnParticle(Particle.LAVA, a.getEyeLocation(), 1, .5, .5, .5, .05);
		}
	}
	private void airOrb(ArmorStand a) {
		double y = a.getHeadPose().getY();
		y -= .025;
		if (y < 0) {
			y = y + 6.25;
		}

		a.setHeadPose(new EulerAngle(0, y, 0));
		if (rand.nextDouble() < .8) {
			a.getWorld().spawnParticle(Particle.CLOUD, a.getEyeLocation(), 1, 2, 2, 2, .05);
		}
	}
	private void dirtOrb(ArmorStand a) {
		double y = a.getHeadPose().getY();
		y -= .025;
		if (y < 0) {
			y = y + 6.25;
		}

		a.setHeadPose(new EulerAngle(0, y, 0));
		if (rand.nextDouble() < .8) {
			a.getWorld().spawnParticle(Particle.TOWN_AURA, a.getEyeLocation(), 6, 2, 2, 2, .3);
		}
	}
	private void waterOrb(ArmorStand a) {
		double y = a.getHeadPose().getY();
		y -= .025;
		if (y < 0) {
			y = y + 6.25;
		}

		a.setHeadPose(new EulerAngle(0, y, 0));
		if (rand.nextDouble() < .5) {
			a.getWorld().spawnParticle(Particle.WATER_WAKE, a.getEyeLocation(), 1, .25, .25, .25, .03);
		}
	}
	private void witchKing(ArmorStand a) {
		if (rand.nextDouble() < .01) {
			a.setHeadPose(new EulerAngle(0, .2, 0));
		}
		if (rand.nextDouble() < .01) {
			a.setHeadPose(new EulerAngle(0, -.2, 0));
		}

		if (rand.nextDouble() < .5) {
			a.getWorld().spawnParticle(Particle.SMOKE_LARGE, a.getEyeLocation(), 1, 1, 2, 1, .03);
		}
		if (rand.nextDouble() < .2) {
			a.getWorld().spawnParticle(Particle.FLAME, a.getEyeLocation(), 1, 1, 2, 1, .003);
		}
	}
	@EventHandler
	private void tryToPlaceTrophy(BlockPlaceEvent event) {
		if (event.getPlayer().getItemInHand().hasItemMeta()) {
			if (event.getPlayer().getItemInHand().getItemMeta().hasDisplayName()) {
				if (event.getPlayer().getItemInHand().getItemMeta().getDisplayName().contains("§6Best In Show")
						|| event.getPlayer().getItemInHand().getItemMeta().getDisplayName().contains("§7Most")
						|| event.getPlayer().getItemInHand().getItemMeta().getDisplayName().contains("§6Winner")
						|| event.getPlayer().getItemInHand().getItemMeta().getDisplayName().contains("§7Participant")
						|| event.getPlayer().getItemInHand().getItemMeta().getDisplayName().contains("§9")) {
					event.setCancelled(true);
					event.getPlayer().sendMessage("§cThis must be placed on an armor stand!");
				}
			}
		}
	}
	private void trophy(ArmorStand a) {
		double y = a.getHeadPose().getY();
		y -= .025;
		if (y < 0) {
			y = y + 6.25;
		}

		a.setHeadPose(new EulerAngle(0, y, 0));
		if (a.isCustomNameVisible()) {
			String raw = a.getEquipment().getHelmet().getItemMeta().getDisplayName();
			a.setCustomName(raw);
		}
		if (rand.nextDouble() < .1) {
			a.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, a.getEyeLocation(), 1, .25, .25, .25, 0);
		}
		a.getWorld().spawnParticle(Particle.SMOKE_LARGE, a.getEyeLocation(), 1, .005, .005, .005, 0);
	}
	private void trophy2(ArmorStand a) {
		double y = a.getHeadPose().getY();
		y -= .025;
		if (y < 0) {
			y = y + 6.25;
		}

		a.setHeadPose(new EulerAngle(0, y, 0));
		if (a.isCustomNameVisible()) {
			String raw = a.getEquipment().getHelmet().getItemMeta().getDisplayName();
			a.setCustomName(raw);
		}
		if (rand.nextDouble() < .1) {
			a.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, a.getEyeLocation(), 1, .25, .25, .25, 0);
		}
	}
	private void globe(ArmorStand a) {
		double y = a.getHeadPose().getY();
		y -= .005;

		if (y < 0) {
			y = y + 6.25;
		}

		a.setHeadPose(new EulerAngle(a.getHeadPose().getX(), y, a.getHeadPose().getZ()));

		if (a.isCustomNameVisible()) {
			String raw = a.getEquipment().getHelmet().getItemMeta().getDisplayName();
			a.setCustomName(raw);
		}
		if (rand.nextDouble() < .125) {
			a.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, a.getEyeLocation(), 1, .75, .75, .75, 0);
			a.getWorld().spawnParticle(Particle.WATER_WAKE, a.getEyeLocation(), 1, .75, .75, .75, 0);
		}
	}
	private void multiOre(ArmorStand a) {
		double y = a.getHeadPose().getY();
		y += .05;

		if (y > 6.25) {
			y = y - 6.25;
		}

		a.setHeadPose(new EulerAngle(a.getHeadPose().getX(), y, a.getHeadPose().getZ()));

		if (a.isCustomNameVisible()) {
			String raw = a.getEquipment().getHelmet().getItemMeta().getDisplayName();
			a.setCustomName(raw);
		}

		if (rand.nextDouble() < .75) {
			a.getWorld().spawnParticle(Particle.REDSTONE, a.getEyeLocation(), 7, .5, .5, .5, 15);
		}
	}
	private void moneyBag(ArmorStand a) {
		double y = a.getHeadPose().getY();
		y -= .025;

		if (y < 0) {
			y = y + 6.25;
		}
		a.setGravity(false);
		Location l = a.getLocation();
		boolean hasMeta = false;
		int particle = -1;
		double range = -1;
		int intensity = -1;
		double extra = -1;
		for (MetadataValue mv : a.getMetadata("up")) {
			hasMeta = true;
			if ((boolean) mv.value() == true) {
				a.setMetadata("up", new FixedMetadataValue(this, false));
				l.setY(l.getY() + .1);
			} else {
				a.setMetadata("up", new FixedMetadataValue(this, true));
				l.setY(l.getY() - .1);
			}
			a.teleport(l);
		}

		for (MetadataValue mv : a.getMetadata("particle")) {
			particle = (int) mv.value();
			break;
		}
		for (MetadataValue mv : a.getMetadata("range")) {
			range = (double) mv.value();
			break;
		}
		for (MetadataValue mv : a.getMetadata("intensity")) {
			intensity = (int) mv.value();
			break;
		}
		for (MetadataValue mv : a.getMetadata("extra")) {
			extra = (double) mv.value();
			break;
		}

		if (!hasMeta) {
			a.setMetadata("up", new FixedMetadataValue(this, true));
		}
		if (particle == -1) {
			particle = 0;
			a.setMetadata("particle", new FixedMetadataValue(this, 0));
		}
		if (range == -1) {
			range = 5;
			a.setMetadata("range", new FixedMetadataValue(this, range));
		}
		if (intensity == -1) {
			intensity = 5;
			a.setMetadata("intensity", new FixedMetadataValue(this, intensity));
		}
		if (extra == -1) {
			extra = 0.0;
			a.setMetadata("extra", new FixedMetadataValue(this, extra));
		}

		a.setHeadPose(new EulerAngle(0, y, 0));

		if (a.isCustomNameVisible()) {
			String raw = a.getEquipment().getHelmet().getItemMeta().getDisplayName();
			a.setCustomName(raw);
		}

		a.getWorld().spawnParticle(Particle.values()[particle], a.getEyeLocation(), intensity, range, range, range,
				extra);

	}
	private void snowGlobe(ArmorStand a) {
		double y = a.getHeadPose().getY();
		y -= .025;

		if (y < 0) {
			y = y + 6.25;
		}

		Location l = a.getLocation();
		boolean hasMeta = false;
		for (MetadataValue mv : a.getMetadata("up")) {
			hasMeta = true;
			if ((boolean) mv.value() == true) {
				a.setMetadata("up", new FixedMetadataValue(this, false));
				l.setY(l.getY() + .25);
			} else {
				a.setMetadata("up", new FixedMetadataValue(this, true));
				l.setY(l.getY() - .25);
			}
			a.teleport(l);
		}

		if (!hasMeta) {
			a.setMetadata("up", new FixedMetadataValue(this, true));
		}

		a.setHeadPose(new EulerAngle(0, y, 0));

		if (a.isCustomNameVisible()) {
			String raw = a.getEquipment().getHelmet().getItemMeta().getDisplayName();
			a.setCustomName(raw);
		}
		if (rand.nextDouble() < .5) {
			a.getWorld().spawnParticle(Particle.SNOW_SHOVEL, a.getEyeLocation(), 1, .05, .05, .05, .09);
			a.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, a.getEyeLocation(), 50, 10, 10, 10, 0);

		}
	}

	// Utility
	// TODO Unused
	protected void unloadChunks() {
		long now = System.currentTimeMillis();
		short count = 0;
		short totalCount = 0;
		for (World w : Bukkit.getWorlds()) {
			for (Chunk c : w.getLoadedChunks()) {
				totalCount++;
				if (c.unload(true, true))
					count++;
				// }
			}
		}
	}
	// TODO Unused
	boolean isWood(Material m) {
		if (m.equals(Material.LOG)) {
			return true;
		}
		return false;
	}
	protected void printDebugTimings(String string, long debugtime) {
		if (false) {
			long time = (System.currentTimeMillis() - debugtime);
			if (time < 5) {
				return;
			}
			if (time > 50) {
				getLogger().info("§c" + string + ": " + (System.currentTimeMillis() - debugtime) + "ms");
			} else {
				getLogger().info("§6" + string + ": " + (System.currentTimeMillis() - debugtime) + "ms");
			}
		}

	}
	// TODO WTF? Prevent logging of annoying bits?
	private void ignoreArmor() {
			org.apache.logging.log4j.core.Logger coreLogger = (org.apache.logging.log4j.core.Logger) LogManager
					.getRootLogger();
			coreLogger.addFilter(new Filter() {
				@Override
				public Result filter(LogEvent arg0) {
					if (arg0.getMessage().toString().toLowerCase().contains("command: /as")
							|| arg0.getMessage().toString().contains("'DeffoNotASlave'")
							|| arg0.getMessage().toString().contains("| null")
							|| arg0.getMessage().toString().contains("moved wrongly!")) {
						return Result.DENY;
					}
					return null;
				}

				@Override
				public Result filter(Logger arg0, Level arg1, Marker arg2, String arg3, Object... arg4) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Result filter(Logger arg0, Level arg1, Marker arg2, Object arg3, Throwable arg4) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Result filter(Logger arg0, Level arg1, Marker arg2, Message arg3, Throwable arg4) {
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
	private boolean isEntityHostile(LivingEntity entity) {
		if (entity.getType() == EntityType.ZOMBIE || entity.getType() == EntityType.BLAZE
				|| entity.getType() == EntityType.CAVE_SPIDER || entity.getType() == EntityType.CREEPER
				|| entity.getType() == EntityType.ENDER_DRAGON || entity.getType() == EntityType.ENDERMAN
				|| entity.getType() == EntityType.ENDERMITE || entity.getType() == EntityType.GHAST
				|| entity.getType() == EntityType.GUARDIAN || entity.getType() == EntityType.MAGMA_CUBE
				|| entity.getType() == EntityType.PIG_ZOMBIE || entity.getType() == EntityType.SILVERFISH
				|| entity.getType() == EntityType.SKELETON || entity.getType() == EntityType.SLIME
				|| entity.getType() == EntityType.SPIDER || entity.getType() == EntityType.WITCH
				|| entity.getType() == EntityType.WITHER) {
			return true;
		}

		return false;
	}
	private void doRandomFirework(Location location) {

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

				Bukkit.getScheduler().scheduleSyncDelayedTask(global, new Runnable() {

					@Override
					public void run() {
						Firework fw = (Firework) l.getWorld().spawnEntity(l, EntityType.FIREWORK);
						fw.getUniqueId();

						FireworkEffect effect = FireworkEffect.builder().trail(b1f).flicker(b2f).withColor(colors1f)
								.withFade(colors2f).with(getRandType()).build();

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
	// TODO Unused
	private static String stripFormatting(String s) {
		s = s.replace("§0", "");
		s = s.replace("§1", "");
		s = s.replace("§2", "");
		s = s.replace("§3", "");
		s = s.replace("§4", "");
		s = s.replace("§5", "");
		s = s.replace("§6", "");
		s = s.replace("§7", "");
		s = s.replace("§8", "");
		s = s.replace("§9", "");
		s = s.replace("§a", "");
		s = s.replace("§b", "");
		s = s.replace("§c", "");
		s = s.replace("§d", "");
		s = s.replace("§e", "");
		s = s.replace("§f", "");
		s = s.replace("§", "");
		return s;
	}
	public void takeOneItemFromPlayerHand(Player player) {
		if (player.getItemInHand().getAmount() != 1) {
			player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
		} else {
			player.setItemInHand(new ItemStack(Material.AIR));
		}
	}
	// TODO Unused
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
		String timeStamp = new SimpleDateFormat("HH:mm MMM dd, YYYY").format(Calendar.getInstance().getTime());
		sender.sendMessage("§7Server time: §e" + timeStamp);
		return true;
	}
	static boolean isDateBetween(Date check, Date min, Date max) {
		SimpleDateFormat fmt = new SimpleDateFormat("MMdd");
		if (fmt.format(check).equals(fmt.format(min))) {
			return true;
		}

		return check.after(min) && check.before(max);
	}
	private static boolean ignoring(SerenityPlayer sp1, SerenityPlayer sp2) {
		if (sp1 != null && sp2 != null) {
			if (sp1.getIgnoreList() != null && sp1.getIgnoreList().contains(sp2.getUUID()))
				return true;
			if (sp2.getIgnoreList() != null && sp2.getIgnoreList().contains(sp1.getUUID()))
				return true;
			return false;
		}
		return false;
	}
	
	// In-Game Utility
	@EventHandler
	public void eraserTools(PlayerInteractEvent event) {
		if (event.getPlayer().isOp()) {
			if (event.getPlayer().getItemInHand().getType() == Material.RED_ROSE) {
				if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)
						|| event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
					Block target = event.getPlayer().getTargetBlock((Set<Material>) null, MAX_DISTANCE);
					for (int x = -1; x < 2; x++) {
						for (int z = -1; z < 2; z++) {
							for (int y = -1; y < 2; y++) {
								target.getRelative(x, y, z).setType(Material.AIR);
							}
						}
					}
				}
			}
		}
	}
	@EventHandler
	public void onHalLlamaStick(PlayerInteractEvent event) {
		if (event.getPlayer().isOp()) {
			if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)
					|| event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
				if (event.getPlayer().getItemInHand().getType() == Material.REDSTONE_TORCH_ON) {

					Block target = event.getPlayer().getTargetBlock((Set<Material>) null, MAX_DISTANCE);
					Location location = target.getLocation();
					location.getWorld().spawnEntity(location, EntityType.LLAMA);
					event.setCancelled(true);
					return;
				}
			}
		}
	}
	
	// Gag
	@EventHandler
	public void EatSecretSomethingEvent(PlayerItemConsumeEvent event) {
		ItemStack item = event.getItem();
		if (item.getItemMeta().hasDisplayName()) {
			if (item.getItemMeta().getDisplayName().equals("§dForbidden Fruit")) {
				event.setCancelled(true);
				event.getPlayer().kickPlayer("You are banned from the server");
			}
		}
	}
	@EventHandler
	public void onSecretThing(PlayerInteractEvent event) {
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
			if (event.getPlayer().getItemInHand().getType() == Material.GOLD_AXE) {
				if (event.getPlayer().getItemInHand().hasItemMeta()) {
					if (event.getPlayer().getItemInHand().getItemMeta().getDisplayName() != null) {
						if (event.getPlayer().getItemInHand().getItemMeta().getDisplayName()
								.equals("§6The Firework Axe")) {
							Block target = event.getPlayer().getTargetBlock((Set<Material>) null, MAX_DISTANCE);
							Location location = target.getLocation();
							doRandomFirework(location);
							event.setCancelled(true);
							return;

						}
					}
				}
			}
		}
	}
	@EventHandler
	public void onSpecialInteractEvent(PlayerInteractEntityEvent event) {
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
	
	// Protection
	private void addTrustsToChunks(String name) {
		try {
			ConfigurationSection namedChunksFromConfig = protectedAreasCfg.getConfig()
					.getConfigurationSection("ProtectedAreas." + name);
			for (String subkey : namedChunksFromConfig.getKeys(true)) {
				ArrayList<String> coords = new ArrayList<String>();
				if (subkey.equals("Trusts")) {
					for (Object subSubkey : namedChunksFromConfig.getList(subkey)) {
						coords.add(subSubkey.toString());
					}

					for (ProtectedArea pa : protectedAreas) {
						if (pa.owner.equals(name)) {
							for (String names : coords) {
								pa.trustedPlayers.add(names);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			getLogger().info("Exception thrown while trying to add protected area trust");
		}

	}
	@EventHandler
	private void onPortalCreation(BlockIgniteEvent event) {
		if (event.getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.OBSIDIAN)
				|| event.getBlock().getRelative(BlockFace.UP).getType().equals(Material.OBSIDIAN)
				|| event.getBlock().getRelative(BlockFace.SOUTH).getType().equals(Material.OBSIDIAN)
				|| event.getBlock().getRelative(BlockFace.EAST).getType().equals(Material.OBSIDIAN)
				|| event.getBlock().getRelative(BlockFace.WEST).getType().equals(Material.OBSIDIAN)
				|| event.getBlock().getRelative(BlockFace.NORTH).getType().equals(Material.OBSIDIAN)) {
			Location l = event.getBlock().getLocation();
			if (l.getWorld().getName().equals("world_nether")) {
				Location dest = new Location(Bukkit.getWorld("world"), l.getX() * 8, l.getY(), l.getZ() * 8);
				for (ProtectedArea pa : protectedAreas) {
					if (pa.equals(dest)) {
						if (!pa.hasPermission(event.getPlayer().getDisplayName())) {
							event.setCancelled(true);
							event.getPlayer().sendMessage(
									getTranslationLanguage(event.getPlayer(), stringKeys.PORTBADPORTAL.toString()));
						}
					}
				}
			}
		}
	}
	@EventHandler
	private void onProtAreaFire(BlockIgniteEvent event) {
		if (!(event.getCause() == IgniteCause.FLINT_AND_STEEL)) {
			for (ProtectedArea pa : protectedAreas) {
				if (pa.equals(event.getBlock().getLocation())) {
					event.setCancelled(true);
					return;
				}
			}
		}
	}
	@EventHandler
	public void dragonEggPickup(PlayerPickupItemEvent event) {
		if (event.getItem().getItemStack().getType().equals(Material.DRAGON_EGG)) {
			getLogger().info("§c" + event.getPlayer().getName() + " §6 picked up a dragon egg at \n"
					+ event.getPlayer().getLocation());
		}
	}
	@EventHandler
	public void dragonEggClick(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (event.getClickedBlock().getType().equals(Material.DRAGON_EGG)) {
				getLogger().info("§c" + event.getPlayer().getName() + "§2 clicked a dragon egg at\n"
						+ event.getClickedBlock().getLocation());
			}
		}
	}
	@EventHandler
	public void onHangingBreak(HangingBreakByEntityEvent event) {
		if (event.getRemover() instanceof Player) {
			Player p = (Player) event.getRemover();
			for (ProtectedArea pa : protectedAreas) {
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
		for (ProtectedArea p : protectedAreas) {
			if (p.equals(event.getRightClicked().getLocation())) {
				if (!p.hasPermission(event.getPlayer().getDisplayName())) {
					if (event.getRightClicked() instanceof Hanging) {
						event.setCancelled(true);
						return;
					}
				}
			}
		}
	}
	protected void highlightSticks() {
		Bukkit.getScheduler().runTaskAsynchronously(this, new Runnable() {
			@Override
			public void run() {
				for (Player p : Bukkit.getOnlinePlayers()) {
					if (p.getItemInHand().getType() == Material.STICK) {
						for (ProtectedArea pa : protectedAreas) {
							if (pa.owner.equals(p.getDisplayName())) {

								final ProtectedArea paf = pa;
								Bukkit.getScheduler().scheduleSyncDelayedTask(SerenityPlugins.this, new Runnable() {
									@Override
									public void run() {

										paf.highlightArea();

									}
								});

								Bukkit.getScheduler().scheduleSyncDelayedTask(SerenityPlugins.this, new Runnable() {
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
	public void onArmorOrEntityDamageEvent(EntityDamageEvent event) {
		if (event.getCause().equals(DamageCause.BLOCK_EXPLOSION)
				|| event.getCause().equals(DamageCause.ENTITY_EXPLOSION)) {
			if (event.getEntity() instanceof ArmorStand || event.getEntity() instanceof ItemFrame) {
				if (AreaIsClaimed(event.getEntity().getLocation())) {
					event.setCancelled(true);
					return;
				}
			}
		}
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onChunkTestEventBlockClick(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		if (p.getItemInHand().getType() == Material.STICK
				&& (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {

			event.setCancelled(true);
			Long before = System.currentTimeMillis();
			World world = event.getPlayer().getWorld();

			Block target = event.getPlayer().getTargetBlock((Set<Material>) null, MAX_DISTANCE);
			for (ProtectedArea pa : protectedAreas) {
				if (pa.equals(target.getLocation())) {
					if (pa.owner.equals(event.getPlayer().getDisplayName())) {
						Location l = new Location(target.getLocation().getWorld(), target.getLocation().getX() + .5,
								target.getLocation().getY() + .5, target.getLocation().getZ() + .5);
						l.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, l, 50, .5f, .5f, .5f);
						// event.getPlayer().sendMessage("§3You own that
						// block");

						String msg = getTranslationLanguage(event.getPlayer(), stringKeys.PROTOWNBLOCK.toString());
						event.getPlayer().sendMessage(msg);

						return;
					}
					if (pa.hasPermission(event.getPlayer().getDisplayName())) {
						Location l = new Location(target.getLocation().getWorld(), target.getLocation().getX() + .5,
								target.getLocation().getY() + .5, target.getLocation().getZ() + .5);
						l.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, l, 50, .5f, .5f, .5f);

						/*
						 * event.getPlayer() .sendMessage( "§2" + pa.owner +
						 * "§3 owns that block but you have permission for it");
						 */

						String msg = getTranslationLanguage(event.getPlayer(),
								stringKeys.PROTDOESNOTOWNBUTHASPERMISSION.toString());
						event.getPlayer().sendMessage(String.format(msg, pa.owner));
						return;
					}
					Location l = new Location(target.getLocation().getWorld(), target.getLocation().getX() + .5,
							target.getLocation().getY() + .5, target.getLocation().getZ() + .5);

					l.getWorld().spawnParticle(Particle.FLAME, l, 50, .5f, .5f, .5f);
					/*
					 * event.getPlayer().sendMessage(
					 * "§cYou do not have permission for that block");
					 */
					/*
					 * String msg = getTranslationLanguage(event.getPlayer(),
					 * stringKeys.PROTDOESNOTOWNBLOCK.toString());
					 * event.getPlayer().sendMessage(msg);
					 */

					String msg = getTranslationLanguage(event.getPlayer(), stringKeys.PROTDOESNOTOWNBLOCK.toString());
					event.getPlayer().sendMessage(String.format(msg, pa.owner));
					return;
				}
			}
			Long after = System.currentTimeMillis();
			// event.getPlayer().sendMessage("§4Nobody owns that block");

			String msg = getTranslationLanguage(event.getPlayer(), stringKeys.PROTNOBODYOWNSBLOCK.toString());
			event.getPlayer().sendMessage(msg);

			/*
			 * if (event.getPlayer().isOp()) { event.getPlayer().sendMessage(
			 * "Time to iterate: " + (after - before) + "ms. " + areas.size());
			 * }
			 */

			Location l = new Location(target.getLocation().getWorld(), target.getLocation().getX() + .5,
					target.getLocation().getY() + .5, target.getLocation().getZ() + .5);
			l.getWorld().spawnParticle(Particle.NOTE, l, 30, .5f, .5f, .5f, 0);
		}
	}
	@EventHandler
	public void onHorseDismountEvent(VehicleExitEvent event) {
		if (event.getExited() instanceof Player) {
			Player p = (Player) event.getExited();

			if (event.getVehicle().getType() == EntityType.HORSE) {
				for (ProtectedArea pa : protectedAreas) {
					if (pa.equals(p.getLocation())) {
						if (!pa.hasPermission(p.getDisplayName())) {
							event.setCancelled(true);
							p.sendMessage(
									"§cSorry, you can't dismount in a protected area of which you don't have permission");
						}
					}
				}
			}
		}
	}
	@EventHandler
	public void onHorseMountEvent(VehicleEnterEvent event) {
		if (event.getEntered() instanceof Player) {
			Player p = (Player) event.getEntered();

			if (event.getVehicle().getType() == EntityType.HORSE) {
				for (ProtectedArea pa : protectedAreas) {
					if (pa.equals(p.getLocation())) {
						if (!pa.hasPermission(p.getDisplayName())) {
							event.setCancelled(true);
						}
					}
				}
			}
		}
	}
	@EventHandler
	private void onEntityChangeBlockEvent(EntityChangeBlockEvent event) {
		if (event.getEntityType().equals(EntityType.ENDERMAN) || event.getEntityType().equals(EntityType.WITHER)
				|| event.getEntityType().equals(EntityType.ENDER_DRAGON)) {
			for (ProtectedArea pa : protectedAreas) {
				if (pa.equals(event.getBlock().getLocation())) {
					event.setCancelled(true);
					return;
				}
			}
		}
	}
	@EventHandler
	private void onBlockBurnBlockBurnEvent(BlockBurnEvent event) {
		for (ProtectedArea pa : protectedAreas) {
			if (pa.equals(event.getBlock().getLocation())) {
				event.setCancelled(true);
			}
		}
	}
	@EventHandler
	public void onArmorStandInteractEvent(PlayerArmorStandManipulateEvent event) {
		for (ProtectedArea pa : protectedAreas) {
			if (pa.equals(event.getRightClicked().getLocation())) {
				if (!pa.hasPermission(event.getPlayer().getDisplayName())) {
					event.setCancelled(true);
					return;
				}
			}
		}
	}
	
	// Hal Voodoo
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
					event.getPlayer().sendMessage("§8Smoke monster from LOST"); // LEL -Tech
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
			Bukkit.getScheduler().runTaskLaterAsynchronously(this, new Runnable() {
				@Override
				public void run() {

					List<Player> players = new ArrayList<Player>();
					for (Player p : Bukkit.getOnlinePlayers()) {
						if (!p.isOp() || opParticlesDeb) {
							players.add(p);
						}
					}

					if (!players.isEmpty()) {
						for (Player pl : players) {
							pl.spawnParticle(Particle.SPELL_WITCH, pf.getLocation(), 25, .125f, .25f, .125f);
						}
					}
				}
			}, i * 2L);
		}
	}

	// TODO WTF
	// Nether Party
	protected void partyPlaid() {
		final World w = Bukkit.getWorld("world_nether");
		final Random rand = new Random();
		final DyeColor dcf1 = ALL_DYES.get(rand.nextInt(16));
		final DyeColor dcf2 = ALL_DYES.get(rand.nextInt(16));
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
								if (Math.abs(i) % 2 == 0 && Math.abs(j) % 2 == 1) {
									wol.setColor(dcf1);
								} else if (Math.abs(j) % 2 == 0 && Math.abs(i) % 2 == 1) {
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
								wol.setColor(ALL_DYES.get((colorRain + offset) % 10));
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
		final DyeColor dcf = ALL_DYES.get(rand.nextInt(16));
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

	// AFK
	private void afkTest() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (!p.isOp()) {
				SerenityPlayer sp = serenityPlayers.get(p.getUniqueId());
				if (p.getLocation().getDirection().hashCode() == sp.getPlayerVectorHash()
						|| p.getLocation().hashCode() == sp.getPlayerLocationHash()) {
					if (votingForDay) {
						getLogger().info(sp.getName() + " went AFK so they voted for day");
						Bukkit.dispatchCommand(p, "ok");
					}
					sp.setAFK(true);
					// setListNames();
				}
				sp.setPlayerVectorHash(p.getLocation().getDirection().hashCode());
				sp.setPlayerLocationHash(p.getLocation().hashCode());
			}
		}
	}
	private void setAfk(SerenityPlayer player, boolean wasAuto) {
		Player p = Bukkit.getPlayer(player.getUUID());
		if (player.isAFK()) {
			return;
		}

		player.setPlayerVectorHash(p.getLocation().hashCode());
		player.setPlayerLocationHash(p.getLocation().hashCode());
		player.setAFK(true);

		p.setSleepingIgnored(true);
		if (!wasAuto) {
			p.sendMessage("§7You have been set §8AFK");
		}

		// setListNames();

		for (Player pl : Bukkit.getOnlinePlayers()) {
			if (pl.isSleeping()) {
				checkHalfIgnored();
				return;
			}
		}
	}
	private void unAfk(SerenityPlayer player) {
		player.setAFK(false);
		player.setPlayerVectorHash(0);
		player.setPlayerLocationHash(0);
		Player p = Bukkit.getPlayer(player.getUUID());
		if (!votingForDay)
			p.setSleepingIgnored(false);
		// setListNames();
	}
	
	// Play Time
	protected void addAMinuteToEachPlayer() {
		for (Player play : Bukkit.getOnlinePlayers()) {
			SerenityPlayer p = serenityPlayers.get(play.getUniqueId());
			if (!p.isAFK()) {
				checkMilestone(p);
				addAMinute(p.getUUID());
				p.setAfkTime(p.getAfkTime() - 1);
				if (p.getAfkTime() < 0)
					p.setAfkTime(0);
			} else {
				int t = p.getAfkTime();
				t++;
				p.setAfkTime(t);
				int playerCount = Bukkit.getServer().getOnlinePlayers().size();
				double idleTimeoutTime = -.1 * (playerCount * playerCount) + 30;
				if (t > idleTimeoutTime) {
					p.setAfkTime(25);
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
			p.sendMessage(getTranslationLanguage(p, stringKeys.TIMEONEHOUR.toString()));
			Bukkit.getLogger().info(p.getDisplayName() + " has reached 1 hour");
			doRandomFirework(p.getLocation());
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
			p.sendMessage(getTranslationLanguage(p, stringKeys.TIMETWELVEHOUR.toString()));
			Bukkit.getLogger().info(p.getDisplayName() + " has reached 12 hours");
			for (int j = 0; j < 15; j++) {
				doRandomFirework(p.getLocation());
			}
			return;
		}

		if (getPlayerMinutes(pl.getUUID()) == 1439) {
			Player p = Bukkit.getPlayer(pl.getUUID());
			for (ProtectedArea pa : protectedAreas) {
				if (pa.owner.equals("[Server]")) {
					ArrayList<String> list = new ArrayList<String>();
					for (ProtectedArea pas : protectedAreas) {
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
					p.sendMessage("§2\nYou can also manipulate armor stands with /as !");
					getLogger().info("§c " + p.getDisplayName() + " §6reached 24 hours");

					for (int j = 0; j < 24; j++) {
						doRandomFirework(p.getLocation());
					}
				}
			}
		}
	}
	private void addAMinute(UUID uuid) {
		serenityPlayers.get(uuid).setMinutes(serenityPlayers.get(uuid).getMinutes() + 1);
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

	// Messages
	public boolean hasNewMessages(UUID uuid) {
		SerenityPlayer sp = serenityPlayers.get(uuid);
		if (sp != null) {
			if (sp.getOfflineMessages() != null) {
				for (OfflineMessage om : serenityPlayers.get(uuid).getOfflineMessages()) {
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
			ChatColor c = ALL_CHAT_COLORS.get(rand.nextInt(ALL_CHAT_COLORS.size()));
			if (c != ChatColor.BLACK) {
				return c;
			}
		}
	}
	
	// Major Player Events
	@EventHandler
	public void onPortal(PlayerPortalEvent evt) {
		getLogger().info("§d" + evt.getPlayer().getName() + " §5used a portal");
		if (evt.getTo().getWorld().getName().equals("world_the_end")) {
			evt.getPlayer().sendMessage(ChatColor.RED
					+ "New Rule:  Please do NOT hoard elytras or shulker-shells.  Be courteous to other server users!  Disregarding this rule may result in a ban.");
		}
	}
	@EventHandler
	public void PlayerLoginEvent(PlayerLoginEvent event) {
		if (!pluginReady)
			event.disallow(PlayerLoginEvent.Result.KICK_OTHER,
					"Serenity Plugins is not yet loaded.  Just wait a little.");
	}
	@EventHandler
	public void onPLayerJoin(PlayerJoinEvent event) {
		SerenityPlayer sp = serenityPlayers.get(event.getPlayer().getUniqueId());
		if (sp != null) {
			sp.setIP(event.getPlayer().getAddress().getAddress().getHostAddress());
			sp.setOp(event.getPlayer().isOp());
			sp.setName((sp.isOp()) ? "[Server]" : event.getPlayer().getName());
			sp.setOnline(true);
		} else {
			sp = new SerenityPlayer();
			sp.setIP(event.getPlayer().getAddress().getAddress().getHostAddress());
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
						// + "'s first time on the server! " + "§c"
						// + "(Hint: /mailto #Spawn)");

						p.sendMessage(
								String.format(getTranslationLanguage(p, stringKeys.JOINPLAYERFIRSTLOGIN.toString()),
										event.getPlayer().getDisplayName()));
					}
				}
			}

			event.getPlayer().teleport(event.getPlayer().getWorld().getSpawnLocation());
		}

		if (!event.getPlayer().isOp()) {
			event.setJoinMessage(getChatColor(event.getPlayer().getUniqueId()) + event.getJoinMessage().substring(2));
		}

		if (event.getPlayer().isOp()) {
			event.getPlayer().setSleepingIgnored(true);

			for (Player p : Bukkit.getOnlinePlayers()) {
				p.hidePlayer(event.getPlayer());
				// event.getPlayer().sendMessage("Hidden from " + p.getName());
			}

			specEff = 0;
			opParticlesDeb = false;
			opParticles = false;

			event.getPlayer().setGameMode(GameMode.CREATIVE);
			event.getPlayer().setDisplayName("[Server]");
			if (event.getPlayer().hasPlayedBefore()) {
				event.setJoinMessage(null);
			}
		}

		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.isOp()) {
				event.getPlayer().hidePlayer(p);
				// p.sendMessage("Hidden from " + event.getPlayer().getName());
			}
		}

		if (hasNewMessages(sp.getUUID())) {
			final UUID uuid = sp.getUUID();
			Bukkit.getScheduler().runTaskLater(this, new Runnable() {
				@Override
				public void run() {
					Bukkit.getPlayer(uuid).sendMessage("§3Type §e/msg read §3to read messages §e(CLICK on them)");
				}
			}, 40L);
		}

		// setListNames();

		if (event.getPlayer().hasPlayedBefore())
			event.setJoinMessage(null);

		if (!event.getPlayer().isOp()) {
			for (Mailbox mb : mailboxes) {
				if (mb.uuid.equals(sp.getUUID())) {
					if (mb.hasMail()) {
						String msg = getTranslationLanguage(event.getPlayer(), stringKeys.MAILHASMAIL.toString());
						event.getPlayer().sendMessage(msg);
						// event.getPlayer().sendMessage("§2You have mail!");
						return;
					}
				}
			}
		}
		return;
	}
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		SerenityPlayer sp = serenityPlayers.get(event.getPlayer().getUniqueId());

		if (event.getPlayer().isOp()) {
			event.setQuitMessage(null);
		}

		if (!event.getPlayer().isOp()) {
			event.setQuitMessage(getChatColor(event.getPlayer().getUniqueId()) + event.getQuitMessage().substring(2));
		}

		sp.setOnline(false);
		sp.setLastPlayed(System.currentTimeMillis());

		event.setQuitMessage(null);

	}

	// Chat
	// TODO *SIGH* Look into fixing this
	@EventHandler
	public void onPlayerChatEvent(AsyncPlayerChatEvent event) {
		if (event.getMessage().startsWith("!queue ") || event.getMessage().startsWith("!play ")) {
			String s = event.getMessage();
			s = s.replaceAll("'", "");
			s = s.replaceAll("\\\\", "");
			s = s.replaceAll("\"", "");
			s = s.replaceAll("!queue ", "");
			s = s.replaceAll("!play ", "");
			s = s.replaceAll("!play", "");
			s = s.replaceAll("!queue", "");
			s = s.replaceAll("!", "");
			s = s.replaceAll("`", "");
			s = s.replaceAll("~", "");
			s = s.replaceAll(";", "");
			String[] result = s.split(" ");
			String input = "";
			for (int i = 0; i < result.length; i++) {
				input += result[i] + " ";
			}
			getLogger().info(input);
			sendRequest(input, event.getPlayer().getDisplayName());
			event.getPlayer().sendMessage(ChatColor.GREEN + "Message sent to Discord: §7!play " + input);
			event.setCancelled(true);
		}

		SerenityPlayer sp = serenityPlayers.get(event.getPlayer().getUniqueId());

		unAfk(sp);

		if (event.getMessage().toLowerCase().contains("stuck") || event.getMessage().toLowerCase().contains("trapped")
				|| event.getMessage().toLowerCase().contains("help")
				|| event.getMessage().toLowerCase().contains("halp")) {
			for (ProtectedArea pa : protectedAreas) {
				if (pa.equals(event.getPlayer().getLocation())) {
					if (!pa.hasPermission(event.getPlayer().getDisplayName())) {
						String msg = getTranslationLanguage(event.getPlayer(), stringKeys.PROTISSTUCK.toString());
						event.getPlayer().sendMessage(msg);
					}
				}
			}
		}

		/*
		 * if (event.getMessage().contains("!queue")) {
		 * Bukkit.getScheduler().runTaskLaterAsynchronously(this, new Runnable()
		 * {
		 * 
		 * @Override public void run() { try { System.out.println(getToken()); }
		 * catch (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } // sendDiscordQueue(event.getMessage()); }
		 * 
		 * }, 0L); }
		 */

		if (sp.isMuted()) {
			event.getRecipients().clear();
			event.getRecipients().add(event.getPlayer());
		}

		if(sp.getMinutes() > 60000){
			event.setFormat("<" + getChatColor(sp.getUUID()) + "§l%s§r> %s");	
		}else{
			event.setFormat("<" + getChatColor(sp.getUUID()) + "%s§r> %s");
		}

		if (sp.isLocalChatting()) {
			event.setFormat("§7§o(%1s) %2$s");
			event.setMessage("§o" + event.getMessage());
			event.getRecipients().clear();
			event.getRecipients().add(event.getPlayer());

			World w = event.getPlayer().getWorld();
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (w.equals(p.getWorld())) {
					if (p.getLocation().distance(event.getPlayer().getLocation()) <= 100) {
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
				event.getPlayer().sendMessage("§cNobody is in local chat range!");
				event.setCancelled(true);
			}
			String periods = "";
			for (int i = 0; i < event.getRecipients().size() - 1; i++) {
				periods += ".";
			}
			event.setFormat(periods + event.getFormat());
		}

		// i am purple
		if (event.getPlayer().isOp()) {
			event.setFormat("§d" + "[Server] " + "%2$s");
		}

		doFunChatStuff(event);

		if (!sp.isMuted() && !sp.isLocalChatting()) {
			sendChatMessageToBungeeServers(event);
		}

		for (Player p : Bukkit.getOnlinePlayers()) {
			SerenityPlayer sp2 = serenityPlayers.get(p.getUniqueId());
			if (ignoring(sp, sp2)) {
				event.getRecipients().remove(p);
			}
		}

	}
	private void doFunChatStuff(AsyncPlayerChatEvent event) {
		SerenityPlayer sp = serenityPlayers.get(event.getPlayer().getUniqueId());
		if (rand.nextInt(999) == 0 && event.getMessage().length() > 10) {
			int r = rand.nextInt(3);
			String s = "";
			switch (r) {
				case 0:
					s = turnTextRainbow(event.getMessage());
					getLogger().info("IT HAPPENED");
					break;
				case 1:
					ChatColor color1 = ALL_CHAT_COLORS.get(rand.nextInt(ALL_CHAT_COLORS.size()));
					ChatColor color2 = ALL_CHAT_COLORS.get(rand.nextInt(ALL_CHAT_COLORS.size()));
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
						s += ALL_CHAT_COLORS.get(rand.nextInt(ALL_CHAT_COLORS.size()));
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
					s += "§k" + event.getMessage().charAt(i) + "§r" + getChatColor(sp.getUUID());
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

		event.setMessage(event.getMessage().replace("[/i]", "§r" + getChatColor(sp.getUUID())));
		event.setMessage(event.getMessage().replace("[/b]", "§r" + getChatColor(sp.getUUID())));
		event.setMessage(event.getMessage().replace("[/s]", "§r" + getChatColor(sp.getUUID())));
		event.setMessage(event.getMessage().replace("[/u]", "§r" + getChatColor(sp.getUUID())));
	}
	private String turnTextRainbow(String message) {
		String ret = "";
		for (int i = 0; i < message.length(); i++) {
			ret += ALL_CHAT_COLORS.get(i % 12);
			ret += message.charAt(i);
		}
		return ret;
	}
	private void sendChatMessageToBungeeServers(AsyncPlayerChatEvent event) {
		String message = event.getMessage();
		sendMessageToBungee(serenityPlayers.get(event.getPlayer().getUniqueId()), message);
	}
	private void sendMessageToBungee(SerenityPlayer sp, String message) {
		String fullMessage = message;

		String uuid = "";
		if (sp != null) {
			uuid = sp.getUUID().toString();
		} else {
			for (SerenityPlayer spe : serenityPlayers.values()) {
				if (spe.isOp()) {
					uuid = spe.getUUID().toString();
				}
			}
		}
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Forward"); // So BungeeCord knows to forward it
		out.writeUTF("ALL");
		out.writeUTF("Chat"); // The channel name to check if this your data

		ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
		DataOutputStream msgout = new DataOutputStream(msgbytes);
		try {
			msgout.writeUTF(uuid);
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
	private String getChatColor(UUID uuid) {
		SerenityPlayer sp = serenityPlayers.get(uuid);
		if (sp != null) {
			return sp.getChatColor();
		} else {
			return "";
		}
	}
	
	// Grief Prevention
	@EventHandler
	public void onSecretVillagerDamage(EntityDamageEvent event) {
		if (event.getEntity().getCustomName() != null) {
			if (event.getEntity().getCustomName().contains("§6") || event.getEntity().getCustomName().contains("§d")) {
				event.setCancelled(true);
			}
		}
	}
	
	// Mailboxes
	@EventHandler
	public void onMailboxCreate(BlockPlaceEvent event) {
		if (event.getBlock().getType().equals(Material.CHEST)
				|| event.getBlock().getType().equals(Material.TRAPPED_CHEST)) {
			Location chestLoc = event.getBlock().getLocation();
			Location fenceLoc = new Location(chestLoc.getWorld(), chestLoc.getX(), chestLoc.getY() - 1,
					chestLoc.getZ());
			if(fenceLoc.getWorld().getName().contains("end")){
				event.setCancelled(true);
				event.getPlayer().sendMessage("§cYou cannot create a mailbox in the end.  This has been abused.");
				return;
			}
			if (fenceLoc.getBlock().getType().equals(Material.FENCE)
					|| fenceLoc.getBlock().getType().equals(Material.ACACIA_FENCE)
					|| fenceLoc.getBlock().getType().equals(Material.BIRCH_FENCE)
					|| fenceLoc.getBlock().getType().equals(Material.DARK_OAK_FENCE)
					|| fenceLoc.getBlock().getType().equals(Material.SPRUCE_FENCE)
					|| fenceLoc.getBlock().getType().equals(Material.JUNGLE_FENCE)) {
				if (chestLoc.getBlock().getRelative(BlockFace.NORTH).getType().equals(Material.CHEST)
						|| chestLoc.getBlock().getRelative(BlockFace.SOUTH).getType().equals(Material.CHEST)
						|| chestLoc.getBlock().getRelative(BlockFace.EAST).getType().equals(Material.CHEST)
						|| chestLoc.getBlock().getRelative(BlockFace.WEST).getType().equals(Material.CHEST)
						|| chestLoc.getBlock().getRelative(BlockFace.NORTH).getType().equals(Material.TRAPPED_CHEST)
						|| chestLoc.getBlock().getRelative(BlockFace.SOUTH).getType().equals(Material.TRAPPED_CHEST)
						|| chestLoc.getBlock().getRelative(BlockFace.EAST).getType().equals(Material.TRAPPED_CHEST)
						|| chestLoc.getBlock().getRelative(BlockFace.WEST).getType().equals(Material.TRAPPED_CHEST)) {
					// event.getPlayer()
					// .sendMessage(
					// "§cYou cannot make a mailbox connected to a chest!");
					event.getPlayer().sendMessage(
							getTranslationLanguage(event.getPlayer(), stringKeys.MAILTRIEDTOEXPANDMAILBOX.toString()));
					event.setCancelled(true);
					return;
				}

				if (!hasAMailbox(event.getPlayer().getUniqueId()) || event.getPlayer().isOp()) {
					Mailbox mb = new Mailbox();
					mb.name = event.getPlayer().getDisplayName();
					mb.uuid = event.getPlayer().getUniqueId();
					mb.location = chestLoc;
					mb.isPublic = event.getPlayer().isOp();
					mailboxes.add(mb);
					String sql = "INSERT INTO Mailbox (Owner, World, X, Y, Z, Public, Name) VALUES ('"
							+ mb.uuid.toString() + "','" + mb.location.getWorld().getName() + "','"
							+ mb.location.getBlockX() + "','" + mb.location.getBlockY() + "','"
							+ mb.location.getBlockZ() + "'," + ((mb.isPublic) ? 1 : 0) + ",'" + mb.name + "');";
					event.getPlayer().sendMessage(
							getTranslationLanguage(event.getPlayer(), stringKeys.MAILCREATEDAMAILBOX.toString()));
					executeSQLAsync(sql);

					// event.getPlayer().sendMessage("§2You made a mailbox!");
				} else {
					Mailbox mb = new Mailbox(null, "AHHHH", chestLoc);
					for (int i = 0; i < mailboxes.size(); i++) {
						if (mailboxes.get(i).uuid.equals(event.getPlayer().getUniqueId())) {
							mb = mailboxes.get(i);
						}
					}
					Location l = mb.location;

					String msg = getTranslationLanguage(event.getPlayer(),
							stringKeys.MAILALREADYHASAMAILBOX.toString());

					event.getPlayer().sendMessage(String.format(msg, mb.location.getWorld().getName(), (int) l.getX(),
							(int) l.getY(), (int) l.getZ()));
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
		for (Mailbox mb : mailboxes) {
			if (mb.uuid.equals(uniqueId)) {
				return true;
			}
		}
		return false;
	}
	protected void fireOnMailBoxes() {
		for (Mailbox mb : mailboxes) {
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (p.getWorld().equals(mb.location.getWorld())) {
					if (p.getLocation().distanceSquared(mb.location) < 30) {
						final Mailbox mbf = mb;
						Bukkit.getScheduler().scheduleSyncDelayedTask(global, new Runnable() {
							@Override
							public void run() {
								if (mbf.hasMail()) {
									final Location l = new Location(mbf.location.getWorld(), mbf.location.getX() + .5,
											mbf.location.getY() + 1.25, mbf.location.getZ() + .5);
									Bukkit.getScheduler().scheduleSyncDelayedTask(global, new Runnable() {
										@Override
										public void run() {
											l.getWorld().spawnParticle(Particle.HEART, l, 50, .25f, .25f, .25f);
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
	@EventHandler
	public void onMailboxOpen(PlayerInteractEvent event) {
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if (event.getClickedBlock().getType().equals(Material.CHEST)
					|| event.getClickedBlock().getType().equals(Material.TRAPPED_CHEST)) {
				for (Mailbox mb : mailboxes) {
					if (mb.location.equals(event.getClickedBlock().getLocation())) {
						if (mb.isPublic) {
							String msg = getTranslationLanguage(event.getPlayer(),
									stringKeys.MAILOPENEDAPUBLICMAILBOX.toString());
							event.getPlayer().sendMessage(String.format(msg, mb.name));

							return;
						}
						if (!mb.uuid.equals(event.getPlayer().getUniqueId())) {

							String msg = getTranslationLanguage(event.getPlayer(),
									stringKeys.MAILINTERRACTEDWITHAMAILBOX.toString());
							event.getPlayer().sendMessage(String.format(msg, mb.name));

							if (!event.getPlayer().isOp())
								event.setCancelled(true);
							return;
						}
					}
				}
			}
		}
	}
	@EventHandler
	public void onMailboxDestroy(BlockBreakEvent event) {
		if (event.getBlock().getType().equals(Material.CHEST)
				|| event.getBlock().getType().equals(Material.TRAPPED_CHEST)) {

			for (Mailbox mb : mailboxes) {
				if (mb.location.equals(event.getBlock().getLocation())) {
					if (mb.uuid.toString().equals(event.getPlayer().getUniqueId().toString())) {
						String sql = "DELETE FROM Mailbox where Owner = '" + event.getPlayer().getUniqueId().toString()
								+ "' AND World = '" + mb.location.getWorld().getName() + "' AND X = "
								+ mb.location.getBlockX() + " AND " + " Y = " + mb.location.getBlockY() + " AND Z = "
								+ mb.location.getBlockZ();
						executeSQLAsync(sql);
						String msg = getTranslationLanguage(event.getPlayer(),
								stringKeys.MAILDESTROYEDMAILBOX.toString());
						event.getPlayer().sendMessage(msg);
						mailboxes.remove(mb);
						return;
					} else {
						event.setCancelled(true);
						return;
					}
				}
			}
		}
	}
	private void putItemInMailbox(UUID uuid, ItemStack deserializeItemStack) {
		Player p = Bukkit.getOfflinePlayer(uuid).getPlayer();
		p.loadData();
		p.getLocation().getWorld().dropItem(p.getLocation(), deserializeItemStack);
	}
	
	// Suspicious behavior warnings
	@EventHandler
	public void onDiamondFound(BlockBreakEvent event) {
		if (event.getBlock().getType().equals(Material.DIAMOND_ORE)) {
			final String s = event.getPlayer().getDisplayName();
			final int x = (int) event.getBlock().getLocation().getX();
			final int y = (int) event.getBlock().getLocation().getY();
			final int z = (int) event.getBlock().getLocation().getZ();

			if (y < 30) {
				Bukkit.getScheduler().runTaskLaterAsynchronously(this, new Runnable() {
					@Override
					public void run() {
						Bukkit.getLogger().info("§6" + s + " found §bdiamond at §3" + x + " " + y + " " + z);
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
				Bukkit.getScheduler().runTaskLaterAsynchronously(this, new Runnable() {

					@Override
					public void run() {
						Bukkit.getLogger().info("§6" + s + " found §2emerald at §a" + x + " " + y + " " + z);
					}
				}, 0L);
			}
		}

		if (event.getBlock().getType().equals(Material.DIAMOND_ORE)) {
			if (!event.getPlayer().isOp()) {
				if (!event.getPlayer().getItemInHand().getEnchantments().containsKey(Enchantment.SILK_TOUCH)) {

					int amt = serenityPlayers.get(event.getPlayer().getUniqueId()).getSerenityLeader()
							.getDiamondsFound();
					amt++;
					serenityPlayers.get(event.getPlayer().getUniqueId()).getSerenityLeader().setDiamondsFound(amt);
				}
			}
		}
	}
	@EventHandler
	public void onShulkerKill(EntityDeathEvent event) {
		LivingEntity e = event.getEntity();
		if (e.getType() == EntityType.SHULKER) {
			getLogger().info(e.getKiller().getName() + " killed a shulker");
		}
	}
	@EventHandler
	private void onItemFrameElytra(EntityDamageByEntityEvent event) {
		if (event.getEntity().getLocation().getWorld().getName().equals("world_the_end")) {
			if (event.getEntity() instanceof ItemFrame) {
				getLogger().info(event.getDamager().getName() + " found elytra at "
						+ event.getEntity().getLocation().getX() + " " + event.getEntity().getLocation().getY() + " "
						+ event.getEntity().getLocation().getZ());
			}
		}
	}

	// Leader boards
	@EventHandler
	public void onMonsterKill(EntityDeathEvent event) {
		LivingEntity e = event.getEntity();
		if (e.getKiller() != null) {
			if (isEntityHostile(event.getEntity())) {
				addMonsterKill(e.getKiller().getUniqueId());
			}
		}
	}
	private void addMonsterKill(UUID uuid) {

		int amt = serenityPlayers.get(uuid).getSerenityLeader().getMonstersKilled();
		amt++;
		serenityPlayers.get(uuid).getSerenityLeader().setMonstersKilled(amt);
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
					SerenityPlayer sp = serenityPlayers.get(event.getWhoClicked().getUniqueId());
					if (sp != null) {
						int amt = sp.getSerenityLeader().getVillagerTrades();
						amt++;
						sp.getSerenityLeader().setVillagerTrades(amt);
					}
				}
			}
		}
	}
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

			Bukkit.getServer().broadcastMessage("§cNobody is in bed anymore! Vote cancelled");
			votingForDay = false;
		}

	}
	@EventHandler
	public void onPlayerLeaveBed(PlayerBedLeaveEvent event) {
		checkIfBedSpawn(event.getPlayer());
	}
	
	// Sleep
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerBedEnterEvent(PlayerBedEnterEvent event) {
		if (Bukkit.getOnlinePlayers().size() > 1) {
			if (!votingForDay) {
				for (Player p : Bukkit.getServer().getOnlinePlayers()) {
					if (!event.getPlayer().equals(p)) {
						if (!p.isSleeping()) {
							if (!p.isSleepingIgnored()) {

								String msg = getTranslationLanguage(p.getPlayer(),
										stringKeys.BEDSOMEONEENTEREDABED.toString());

								p.sendMessage(msg);
							}
						}
					}
				}
				getLogger().info(event.getPlayer().getDisplayName() + " got in bed");
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
	private void checkIfBedSpawn(Player player) {
		final Player pf = player;
		final Location l = pf.getLocation();
		final Location originalBed = pf.getBedSpawnLocation();

		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			@Override
			public void run() {
				if (pf.getBedSpawnLocation() == null) {
					pf.sendTitle("§cWARNING!", "Read chat");
					pf.sendMessage("§cYour respawn location was NOT set!\n§7Is your bed obstructed or on slabs?");
					return;
				}

				if (originalBed == null || pf.getBedSpawnLocation().distance(originalBed) > 1) {
					pf.sendMessage(ChatColor.GREEN + "Respawn location set (don't destroy your bed!)");
				}
			}
		}, 5L);
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

		getLogger().info("Current: " + ((double) sleepingIgnored / (double) onlineCount) + "");

		if (!sleepers.isEmpty()) {
			if ((double) sleepingIgnored / (double) onlineCount > 0.5) {
				Bukkit.getLogger().info("Vote passed: setting all to sleep ignored");
				for (Player p : Bukkit.getServer().getOnlinePlayers()) {
					p.setSleepingIgnored(true);
					if (p.isOp()) {
						if (p.getGameMode().equals(GameMode.SPECTATOR)) {
							final Player pf = p;
							Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
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
	
	// Mail Gifts
	private void givePlayerARandomSkull(Player p) {
		int r = rand.nextInt(ALL_HOLIDAY_SKULLS.size());
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
				"give " + p.getName() + " " + String.format(ALL_HOLIDAY_SKULLS.get(r), p.getName()));
		p.sendMessage("§6§lHappy Holidays!");
	}
	public void putBoneInMailbox(UUID uid) {
		for (Mailbox mb : mailboxes) {
			if (mb.uuid.equals(uid)) {
				Chest receivingChest = (Chest) mb.location.getBlock().getState();

				ItemStack bone = new ItemStack(Material.COOKIE, 1);
				ItemMeta boneMeta = bone.getItemMeta();
				boneMeta.setDisplayName("§dHoliday Cookie");
				boneMeta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 2015, true);
				bone.setItemMeta(boneMeta);
				receivingChest.getInventory().addItem(bone);
				doRandomFirework(receivingChest.getLocation());
			}
		}
	}
	public ItemStack generateHeadBush() {
		ItemStack rewardItem = new ItemStack(Material.DEAD_BUSH);
		ItemMeta rewardMeta = rewardItem.getItemMeta();
		rewardMeta.setDisplayName("§dHead Reward!");
		rewardMeta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 1, true);
		List<String> lore = new ArrayList<String>();
		lore.add("§aHappy Holidays!");
		lore.add("Right click while holding this for a random head!");
		lore.add("Holiday heads are more common for a limited time!");
		lore.add("See a list of all heads on http://serenity-mc.org/heads");
		rewardMeta.setLore(lore);
		rewardItem.setItemMeta(rewardMeta);
		return rewardItem;
	}
	public void putRewardHeadInMailbox(UUID uid, int count, boolean type) {
		for (Mailbox mb : mailboxes) {
			if (mb.uuid.equals(uid)) {
				Chest receivingChest = (Chest) mb.location.getBlock().getState();
				for (int i = 0; i < count; i++) {
					if (type) {
						ItemStack rewardItem = new ItemStack(Material.DEAD_BUSH);
						ItemMeta rewardMeta = rewardItem.getItemMeta();
						rewardMeta.setDisplayName("§dHead Reward!");
						rewardMeta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 1, true);
						List<String> lore = new ArrayList<String>();
						lore.add("§aHappy Holidays!");
						lore.add("Right click while holding this for a random head!");
						lore.add("Holiday heads are more common for a limited time!");
						lore.add("See a list of all heads on http://serenity-mc.org/heads");
						rewardMeta.setLore(lore);
						rewardItem.setItemMeta(rewardMeta);
						receivingChest.getInventory().addItem(rewardItem);
						// doRandomFirework(receivingChest.getLocation());
					} else {
						ItemStack rewardItem = new ItemStack(Material.DEAD_BUSH, 1);
						ItemMeta rewardMeta = rewardItem.getItemMeta();
						rewardMeta.setDisplayName("§dRandom recent player head!");
						rewardMeta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 1, true);
						List<String> lore = new ArrayList<String>();
						lore.add("Thanks for playing the Event!");
						lore.add("Right click while holding this for a random recent player head!");
						rewardMeta.setLore(lore);
						rewardItem.setItemMeta(rewardMeta);
						receivingChest.getInventory().addItem(rewardItem);
						doRandomFirework(receivingChest.getLocation());
					}
				}
			}
		}
	}
	public void putRewardHeadInMailbox(UUID uid, int count) {
		for (Mailbox mb : mailboxes) {
			if (mb.uuid.equals(uid)) {
				Chest receivingChest = (Chest) mb.location.getBlock().getState();
				for (int i = 0; i < count; i++) {
					if (rand.nextDouble() < .9) {
						ItemStack rewardItem = new ItemStack(Material.DEAD_BUSH);
						ItemMeta rewardMeta = rewardItem.getItemMeta();
						rewardMeta.setDisplayName("§dHead Reward!");
						rewardMeta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 1, true);
						List<String> lore = new ArrayList<String>();
						lore.add("Thanks for playing the Event!");
						lore.add("Right click while holding this for a random head!");
						rewardMeta.setLore(lore);
						rewardItem.setItemMeta(rewardMeta);
						receivingChest.getInventory().addItem(rewardItem);
						doRandomFirework(receivingChest.getLocation());
					} else {
						ItemStack rewardItem = new ItemStack(Material.DEAD_BUSH, 1);
						ItemMeta rewardMeta = rewardItem.getItemMeta();
						rewardMeta.setDisplayName("§dRandom recent player head!");
						rewardMeta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 1, true);
						List<String> lore = new ArrayList<String>();
						lore.add("Thanks for playing the Event!");
						lore.add("Right click while holding this for a random recent player head!");
						rewardMeta.setLore(lore);
						rewardItem.setItemMeta(rewardMeta);
						receivingChest.getInventory().addItem(rewardItem);
						doRandomFirework(receivingChest.getLocation());
					}
				}
			}
		}
	}

	// Head Bush
	@EventHandler
	public void onPlayerRedeemHead(PlayerInteractEvent event) {
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
			if (event.getPlayer().getItemInHand().getType() == Material.DEAD_BUSH) {
				if (event.getPlayer().getItemInHand().hasItemMeta()) {
					if (event.getPlayer().getItemInHand().getItemMeta().hasDisplayName()) {
						if (event.getPlayer().getItemInHand().getItemMeta().getDisplayName().equals("§dHead Reward!")) {
							Player player = event.getPlayer();
							event.setCancelled(true);

							player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1F, .025F);
							if (player.getItemInHand().getItemMeta().hasLore()) {
								List<String> lore = player.getItemInHand().getItemMeta().getLore();
								if (lore.size() > 1) {
									for (String lo : lore) {
										if (lo.toUpperCase().contains("DAISY")) {
											Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "give " + player.getName()
													+ " skull 1 3 {display:{Name:\"Pumpkin\"},SkullOwner:{Id:\"fd871fd3-7d88-4c58-bfe8-eeafc9a5c586\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjcxNzVhM2JkYzI5YzViMTE0ZTc1NmIyMmRhYWUzZWIyMWFkNWJhZWVmNjQ3NjIzNzQ3OTcyMTJhOWIwNDcifX19\"}]}}}");
											takeOneItemFromPlayerHand(player);
											return;
										}
										if (lo.toUpperCase().contains("BREEZE")) {
											Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "give " + player.getName()
													+ " skull 1 3 {display:{Name:\"Candy Bowl\"},SkullOwner:{Id:\"601b82ee-9718-4c49-b932-10e82671b607\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzY4ZjFjMzM2MjViNGI0NGMzNjdiNTRjNTRhYmQxY2VhMDg4MzQ1MWYxN2Q0ZjcwM2JhM2E4N2JiOWI4MDM3In19fQ==\"}]}}}");
											takeOneItemFromPlayerHand(player);
											return;
										}
									}
									if (lore.get(0).contains("Specific head:")) {
										givePlayerSpecificHead(Integer.parseInt(lore.get(2)), player);
										takeOneItemFromPlayerHand(player);
										return;
									}
								}
							}

							takeOneItemFromPlayerHand(player);
							givePlayerARandomHead(player);
							return;
						}

						if (event.getPlayer().getItemInHand().getItemMeta().getDisplayName().equals("§dYour head!")) {
							Player player = event.getPlayer();
							event.setCancelled(true);
							if (player.getItemInHand().getAmount() != 1) {
								player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
							} else {
								player.setItemInHand(new ItemStack(Material.AIR));
							}
							givePlayerTheirOwnHead(player);
							player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10F, .025F);
							/*
							 * ParticleEffect.SPELL_MOB.display(.5f, .5f, .5f,
							 * 50, 60, player.getLocation(), 25);
							 */
							return;
						}

						if (event.getPlayer().getItemInHand().getItemMeta().getDisplayName()
								.equals("§dRandom recent player head!")) {
							Player player = event.getPlayer();
							event.setCancelled(true);
							if (player.getItemInHand().getAmount() != 1) {
								player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
							} else {
								player.setItemInHand(new ItemStack(Material.AIR));
							}
							givePlayerRandomPlayerHead(player);
							player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10F, .025F);
							/*
							 * ParticleEffect.SPELL_MOB.display(.5f, .5f, .5f,
							 * 50, 60, player.getLocation(), 25);
							 */
							return;
						}
					}
				}
			}
		}
	}
	private void givePlayerSpecificHead(int parseInt, Player player) {
		for (Head h : allRewardHeads) {
			if (h.ID == parseInt) {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), String.format(h.Command, player.getName()));
				player.sendMessage("§7You got §r" + h.Name + " §7(common)");
			}
		}
	}
	private void givePlayerRandomPlayerHead(Player player) {
		List<SerenityPlayer> sps = new ArrayList<SerenityPlayer>(serenityPlayers.values());
		SerenityPlayer sp = sps.get(rand.nextInt(sps.size()));
		while (System.currentTimeMillis() - sp.getLastPlayed() > 1296000000 || sp.isBanned()
				|| sp.isOp()/*
							 * || sp.getName().equals(player.getName ())
							 */) {
			sp = sps.get(rand.nextInt(sps.size()));
		}

		Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
				"give " + player.getName() + " minecraft:skull 1 3 {SkullOwner:" + sp.getName() + "}");
		player.sendMessage("§aYou got §d" + sp.getName());
	}
	private void givePlayerTheirOwnHead(Player player) {
		player.sendMessage("give " + player.getName() + " minecraft:skull 1 3 {SkullOwner:" + player.getName() + "}");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
				"give " + player.getName() + " minecraft:skull 1 3 {SkullOwner:" + player.getName() + "}");
		player.sendMessage("§aYou got §d" + player.getName());
	}
	private void givePlayerFwHead(Player player) {
		String raw = FIREWORK_SHOW_HEAD;
		String name = "§6Fireworks Show";
		String actualCommand = raw.replace("/give @p", "give %s");
		actualCommand = actualCommand.replaceFirst("\\{.*?\\},", "\\{");
		actualCommand = actualCommand.replaceFirst("SkullOwner:\\{", "SkullOwner:\\{Name:\"" + name + "\",");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), String.format(actualCommand, player.getName()));
	}
	private static void giveRawHeadCommand(String cmd, String newName, String recipient) {
		String actualCommand = cmd.replace("/give @p", "give %s");
		actualCommand = actualCommand.replaceFirst("\\{.*?\\},", "\\{");
		actualCommand = actualCommand.replaceFirst("SkullOwner:\\{", "SkullOwner:\\{Name:\"" + newName + "\",");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), String.format(actualCommand, recipient));
	}
	private Head getRandomRewardHead(int rarity) {
		boolean foundOne = false;
		Head h = new Head("Dummy", "dummy", -1, -1);
		do {
			h = allRewardHeads.get(rand.nextInt(allRewardHeads.size()));
			if (h.Rarity == rarity) {
				foundOne = true;
			}
		} while (!foundOne);
		return h;
	}
	private Head getRewardHead(int id) {
		for (Head h : allRewardHeads) {
			if (h.ID == id) {
				return h;
			}
		}
		return new Head("Dummy", "Dummy", -1, -1);
	}
	private void givePlayerARandomHead(Player player) {
		double chance = rand.nextDouble();
		if (chance < .8) {
			Head h = getRandomRewardHead(5);
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), String.format(h.Command, player.getName()));
			player.sendMessage("§7You got §d" + h.Name + " §d(Limited time)");
			return;
		}
		if (chance < .9) {
			Head h = getRandomRewardHead(0);
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), String.format(h.Command, player.getName()));
			player.sendMessage("§7You got §r" + h.Name + " §7(common)");
			return;
		} else if (chance < .95) {
			Head h = getRandomRewardHead(1);
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), String.format(h.Command, player.getName()));
			player.sendMessage("§7You got " + h.Name + " §7(uncommon)");
			return;
		} else if (chance < .995) {
			Head h = getRandomRewardHead(2);
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), String.format(h.Command, player.getName()));
			player.sendMessage("§7You got " + h.Name + " §7(rare)");
			return;
		} else {
			Head h = getRandomRewardHead(3);
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), String.format(h.Command, player.getName()));
			player.sendMessage("§7You got " + h.Name + " §7(super rare) §rPut it on an armor stand!");
			SerenityPlayer sp = serenityPlayers.get(player.getUniqueId());
			Bukkit.broadcastMessage(sp.getChatColor() + sp.getName() + " §rgot a §dSuper Rare §rhead!!");
		}
	}

	// Cross Server Reward system
	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message) {
		if (!channel.equals("BungeeCord")) {
			return;
		}

		ByteArrayDataInput in = ByteStreams.newDataInput(message);
		String subchannel = in.readUTF();
		if (subchannel.equals("Chat")) {

			short len = in.readShort();
			byte[] msgbytes = new byte[len];
			in.readFully(msgbytes);

			DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgbytes));
			try {
				UUID uuid = UUID.fromString(msgin.readUTF());
				String somedata = msgin.readUTF();
				Long time = msgin.readLong();
				if (System.currentTimeMillis() - time < 500)
					simulateChat(uuid, somedata);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // Read the data in the same way you wrote it
		}

		if (subchannel.equals("PlC")) {
			short len = in.readShort();
			byte[] msgbytes = new byte[len];
			in.readFully(msgbytes);
			DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgbytes));
			try {
				lastCreativeList = msgin.readLong();
				int count = msgin.readInt();
				creativePlayers = new ArrayList<String>();
				for (int i = 0; i < count; i++) {
					creativePlayers.add(msgin.readUTF());
				}
			} catch (IOException e) {
				//
			}
		}

		if (subchannel.equals("PlE")) {
			short len = in.readShort();
			byte[] msgbytes = new byte[len];
			in.readFully(msgbytes);

			DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgbytes));
			try {
				lastEventList = msgin.readLong();
				int count = msgin.readInt();
				eventPlayers = new ArrayList<String>();
				for (int i = 0; i < count; i++) {
					eventPlayers.add(msgin.readUTF());
				}
			} catch (IOException e) {
			}
		}

		if (subchannel.equals("Victory")) {
			short len = in.readShort();
			byte[] msgbytes = new byte[len];
			in.readFully(msgbytes);

			DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgbytes));
			try {
				String uuids = msgin.readUTF();
				int count = msgin.readInt();
				UUID uuid = UUID.fromString(uuids);
				// getLogger().info(uuids);
				// getLogger().info(count + "");
				SerenityPlayer sp = serenityPlayers.get(uuid);
				/*
				 * sendATextToHal("Ember's Adventure", sp.getName() + " earned "
				 * + count + " heads!");
				 */
				if (count > 0) {
					putRewardHeadInMailbox(uuid, count);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // Read the data in the same way you wrote it
		}

		if (subchannel.equals("VictoryE")) {
			short len = in.readShort();
			byte[] msgbytes = new byte[len];
			in.readFully(msgbytes);

			DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgbytes));
			try {
				String uuids = msgin.readUTF();
				String name = msgin.readUTF();
				UUID uuid = UUID.fromString(uuids);
				// getLogger().info(uuids);
				// getLogger().info(count + "");
				SerenityPlayer sp = serenityPlayers.get(uuid);
				/*
				 * sendATextToHal("Ember's Adventure", sp.getName() + " earned "
				 * + count + " heads!");
				 */

				putExclusiveRewardHeadInMailbox(uuid, name);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // Read the data in the same way you wrote it
		}

		if (subchannel.equals("Item")) {
			short len = in.readShort();
			byte[] msgbytes = new byte[len];
			in.readFully(msgbytes);

			DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgbytes));
			try {
				String uuids = msgin.readUTF();
				String item = msgin.readUTF();

				UUID uuid = UUID.fromString(uuids);
				Bukkit.getScheduler().runTaskLater(this, new Runnable() {
					@Override
					public void run() {
						putItemInMailbox(uuid, deserializeItemStack(item));
					}
				}, 40L);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // Read the data in the same way you wrote it
		}

		if (subchannel.equals("Entity")) {
			short len = in.readShort();
			byte[] msgbytes = new byte[len];
			in.readFully(msgbytes);

			DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgbytes));
			try {
				String uuids = msgin.readUTF();
				String name = msgin.readUTF();
				String type = msgin.readUTF();

				UUID uuid = UUID.fromString(uuids);

				EntityType e = EntityType.CHICKEN;
				for (EntityType es : EntityType.values()) {
					if (es.toString().equals(type)) {
						e = es;
					}
				}
				final EntityType ef = e;
				Bukkit.getScheduler().runTaskLater(this, new Runnable() {

					@Override
					public void run() {
						spawnEntity(uuid, name, ef);
					}
				}, 40L);

			} catch (

			IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // Read the data in the same way you wrote it
		}

		if (subchannel.equals("rhc")) {
			short len = in.readShort();
			byte[] msgbytes = new byte[len];
			in.readFully(msgbytes);

			DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgbytes));
			try {
				String uuids = msgin.readUTF();
				String name = msgin.readUTF();
				String type = msgin.readUTF();
				String type2 = msgin.readUTF();

				UUID uuid = UUID.fromString(uuids);

				Bukkit.getScheduler().runTaskLater(this, new Runnable() {
					@Override
					public void run() {
						spawnSpecialEntity(uuid, name, type, type2);
					}
				}, 40L);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // Read the data in the same way you wrote it
		}
	}

	// Halloween head
	private void putExclusiveRewardHeadInMailbox(UUID uid, String name) {
		for (Mailbox mb : mailboxes) {
			if (mb.uuid.equals(uid)) {
				Chest receivingChest = (Chest) mb.location.getBlock().getState();

				ItemStack rewardItem = new ItemStack(Material.DEAD_BUSH);
				ItemMeta rewardMeta = rewardItem.getItemMeta();
				rewardMeta.setDisplayName("§dHead Reward!");
				rewardMeta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 1, true);
				List<String> lore = new ArrayList<String>();
				lore.add("Exclusive Halloween head!");
				lore.add(name);
				rewardMeta.setLore(lore);
				rewardItem.setItemMeta(rewardMeta);
				receivingChest.getInventory().addItem(rewardItem);
				doRandomFirework(receivingChest.getLocation());
			}
		}
	}

	// Spawn Cuties
	private void spawnSpecialEntity(UUID uuid, String name, String type, String type2) {

		World w = Bukkit.getWorld("world");
		Player p = Bukkit.getOfflinePlayer(uuid).getPlayer();
		p.loadData();

		EntityType e = EntityType.CHICKEN;
		for (EntityType es : EntityType.values()) {
			if (es.toString().equals(type.toString())) {
				e = es;
				break;
			}
		}

		if (e.equals(EntityType.HORSE)) {
			Horse horse = (Horse) w.spawnEntity(p.getLocation(), e);
			horse.setCustomName(name);
			horse.setBaby();
			horse.setAgeLock(true);
			for (Horse.Color color : Horse.Color.values()) {
				if (color.toString().equals(type2)) {
					horse.setColor(color);
				}
			}
		}

		if (e.equals(EntityType.OCELOT)) {
			Ocelot oce = (Ocelot) w.spawnEntity(p.getLocation(), e);
			oce.setCustomName(name);
			oce.setBaby();
			oce.setAgeLock(true);
			for (Ocelot.Type color : Ocelot.Type.values()) {
				if (color.toString().equals(type2)) {
					oce.setCatType(color);
				}
			}
		}

		if (e.equals(EntityType.RABBIT)) {
			Rabbit rab = (Rabbit) w.spawnEntity(p.getLocation(), e);
			rab.setCustomName(name);
			rab.setBaby();
			rab.setAgeLock(true);
			for (Rabbit.Type color : Rabbit.Type.values()) {
				if (color.toString().equals(type2)) {
					rab.setRabbitType(color);
				}
			}
		}

	}
	private void spawnEntity(UUID uuid, String name, EntityType e) {
		World w = Bukkit.getWorld("world");
		Player p = Bukkit.getOfflinePlayer(uuid).getPlayer();
		p.loadData();
		Entity et = w.spawnEntity(p.getLocation(), e);
		et.setCustomName(name);

		if (et instanceof Cow) {
			Cow ets = (Cow) et;
			ets.setBaby();
			ets.setAgeLock(true);
		}
		if (et instanceof Pig) {
			Pig ets = (Pig) et;
			ets.setBaby();
			ets.setAgeLock(true);
		}
		if (et instanceof MushroomCow) {
			MushroomCow ets = (MushroomCow) et;
			ets.setBaby();
			ets.setAgeLock(true);
		}
		if (et instanceof Chicken) {
			Chicken ets = (Chicken) et;
			ets.setBaby();
			ets.setAgeLock(true);
		}
		if (et instanceof Sheep) {
			Sheep ets = (Sheep) et;
			ets.setBaby();
			ets.setAgeLock(true);
		}
	}

	// Sneaky Hal Malarky
	private void simulateChat(UUID uuid, String s) {
		SerenityPlayer sp = serenityPlayers.get(uuid);
		if (!sp.isMuted()) {
			String name = "";
			if (sp != null) {
				if (sp.isOp()) {
					name = "§d[Server]";
				} else {
					name = "<" + sp.getChatColor() + sp.getName() + "§r>";
				}
			}
			String message = sp.getChatColor() + s;
			getLogger().info(name + " " + message);
			for (Player p : Bukkit.getOnlinePlayers()) {
				SerenityPlayer spig = serenityPlayers.get(p.getUniqueId());
				if (!ignoring(spig, sp)) {
					p.sendMessage(name + " " + message);
				}
			}
		}
	}
	private void sendSimulatedPrivateMessage(Player player, String displayName, String message) {
		player.sendMessage("§oFrom §6§o§l" + displayName + ": §r" + message);
	}
	
	// Cross Server TP
	private void moveEntityToOther(Player p, Entity e) {
		EntityType et = e.getType();
		if (et.equals(EntityType.COW) || et.equals(EntityType.PIG) || et.equals(EntityType.CHICKEN)
				|| et.equals(EntityType.SHEEP) || et.equals(EntityType.MUSHROOM_COW) || et.equals(EntityType.VILLAGER)
				|| et.equals(EntityType.IRON_GOLEM)) {
			sendEntityToSurvival(p.getUniqueId().toString(), e.getCustomName(), e.getType().toString());
		}
		if (et.equals(EntityType.RABBIT)) {
			Rabbit r = (Rabbit) e;
			sendRabbitHorseCatToSurvival(p.getUniqueId().toString(), e.getCustomName(), e.getType().toString(),
					r.getRabbitType().toString());
		}
		if (et.equals(EntityType.HORSE)) {
			Horse h = (Horse) e;
			sendRabbitHorseCatToSurvival(p.getUniqueId().toString(), e.getCustomName(), e.getType().toString(),
					h.getColor().toString());
		}
		if (et.equals(EntityType.OCELOT)) {
			Ocelot o = (Ocelot) e;
			sendRabbitHorseCatToSurvival(p.getUniqueId().toString(), e.getCustomName(), e.getType().toString(),
					o.getCatType().toString());
		}
		p.sendMessage(e.getCustomName() + " §7was sent to the new world!");
		e.remove();
	}
	private void sendRabbitHorseCatToSurvival(String UUID, String name, String type, String type2) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Forward"); // So BungeeCord knows to forward it
		out.writeUTF("survival");
		out.writeUTF("rhc"); // The channel name to check if this your data

		ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
		DataOutputStream msgout = new DataOutputStream(msgbytes);
		try {
			msgout.writeUTF(UUID);
			msgout.writeUTF(name);
			msgout.writeUTF(type);
			msgout.writeUTF(type2);

			out.writeShort(msgbytes.toByteArray().length);
			out.write(msgbytes.toByteArray());

			Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
			player.sendPluginMessage(this, "BungeeCord", out.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void sendEntityToSurvival(String UUID, String name, String type) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Forward"); // So BungeeCord knows to forward it
		out.writeUTF("survival");
		out.writeUTF("Entity"); // The channel name to check if this your data

		ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
		DataOutputStream msgout = new DataOutputStream(msgbytes);
		try {
			msgout.writeUTF(UUID);
			msgout.writeUTF(name);
			msgout.writeUTF(type);

			out.writeShort(msgbytes.toByteArray().length);
			out.write(msgbytes.toByteArray());

			Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
			player.sendPluginMessage(this, "BungeeCord", out.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void moveItemToOther(Player p, ItemStack is) {
		sendItemStackToSurvival(p.getUniqueId().toString(), serializeItemStack(is));
	}
	private void sendItemStackToSurvival(String UUID, String itemstack) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Forward"); // So BungeeCord knows to forward it
		out.writeUTF("survival");
		out.writeUTF("Item"); // The channel name to check if this your data

		ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
		DataOutputStream msgout = new DataOutputStream(msgbytes);
		try {
			msgout.writeUTF(UUID);
			msgout.writeUTF(itemstack);

			out.writeShort(msgbytes.toByteArray().length);
			out.write(msgbytes.toByteArray());

			Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
			player.sendPluginMessage(this, "BungeeCord", out.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private static ItemStack deserializeItemStack(String s) {
		YamlConfiguration config = new YamlConfiguration();
		try {
			config.loadFromString(s);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return config.getItemStack("i", null);
	}
	private static String serializeItemStack(ItemStack is) {
		YamlConfiguration config = new YamlConfiguration();
		config.set("i", is);
		return config.saveToString();
	}
	private void safelyDropItemStack(Location location, HashMap<Integer, ItemStack> xo) {
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
	private void safelyDropItemStack(Location location, ItemStack is) {
		final Location l = location;
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			@Override
			public void run() {

				l.getWorld().dropItem(l, is);

			}
		});
	}
	private void safelySpawnExperienceOrb(Player player, int i, int time) {
		final Player playerF = player;
		final int exp = i;
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			@Override
			public void run() {
				((ExperienceOrb) playerF.getWorld().spawn(playerF.getLocation(), ExperienceOrb.class))
						.setExperience(exp);
			}
		}, (long) time);
	}
	
	  //////////////
	 // Commands //
	//////////////
	
	// Router
	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] arg3) {
		if (sender instanceof Player) {
			SerenityPlayer sp = serenityPlayers.get(((Player) sender).getUniqueId());
			unAfk(sp);
		}

		if (commandLabel.equalsIgnoreCase("coords")) {
			return coords(sender);
		}

		else if (commandLabel.equalsIgnoreCase("server")) {
			return server(sender, arg3);
		}

		else if (commandLabel.equalsIgnoreCase("password")) {
			return login(sender, arg3);
		}

		else if (commandLabel.equalsIgnoreCase("deposit")) {
			return deposit(sender, arg3);
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

	// Deposit
	private boolean deposit(CommandSender sender, String[] arg3) {
		if (sender instanceof Player) {
			int count = 0;
			Player p = (Player) sender;
			p.updateInventory();
			HashMap<Head, Integer> headsToStore = new HashMap<Head, Integer>();
			for (ItemStack is : p.getInventory().getStorageContents()) {
				count = 0;
				if (is != null && is.hasItemMeta() && is.getItemMeta() instanceof SkullMeta) {
					SkullMeta sm = (SkullMeta) is.getItemMeta();
					if (sm.hasOwner()) {
						for (Head h : allRewardHeads) {
							if (stripFormatting(h.Name).equals(stripFormatting(sm.getOwner()))) {
								count += is.getAmount();
								int existing = headsToStore.getOrDefault(h, 0);
								existing += count;
								headsToStore.put(h, count);
								p.getInventory().remove(is);
							}
						}
					}
				}
			}
			int totalCount = 0;
			for (Map.Entry<Head, Integer> entry : headsToStore.entrySet()) {
				scheduleHeadInsert(p.getUniqueId().toString(), entry.getKey().ID, entry.getValue());
				getLogger().info(sender.getName() + " stored " + entry.getValue() + "   " + entry.getKey().Name);
				totalCount += entry.getValue();
			}
			if (totalCount > 0) {
				sender.sendMessage("§7Stored §e" + totalCount + "§7 " + (totalCount == 1 ? "head" : "heads")
						+ " at §ahttps://serenity-mc.org/heads");
			} else {
				sender.sendMessage("§cYou don't have any items to deposit in your inventory");
			}
		}
		return true;
	}
	private int countHeadsInInventory(Player p, String name) {
		int count = 0;
		for (ItemStack is : p.getInventory().getContents()) {
			if (is != null && is.hasItemMeta() && is.getItemMeta() instanceof SkullMeta) {
				SkullMeta sm = (SkullMeta) is.getItemMeta();
				if (sm.hasOwner() && sm.getOwner().equals(name)) {
					count += is.getAmount();
					p.getInventory().remove(is);
				}
			}
		}
		return count;
	}
	private void scheduleHeadInsert(String uuid, int id, int amount) {
		Bukkit.getScheduler().runTaskLaterAsynchronously(this, new Runnable() {
			@Override
			public void run() {
				insertHead(uuid, id, amount);
			}
		}, 0L);
	}
	private void insertHead(String uuid, int id, int amount) {
		int amt = 0;
		Connection conn = getConnection();
		Statement st;
		try {
			st = conn.createStatement();
			ResultSet rs = st.executeQuery("Select * FROM HeadBank where Owner='" + uuid + "' and HeadID = " + id);
			while (rs.next()) {
				amt += rs.getInt("Amount");
			}
			amt += amount;
			String sql = "REPLACE into HeadBank Values('" + uuid + "'," + id + "," + amt + ")";
			executeSQLBlocking(sql);
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	private boolean login(CommandSender sender, String[] arg3) {
		if (sender instanceof Player) {
			final Player p = (Player) sender;
			Bukkit.getScheduler().runTaskLaterAsynchronously(this, new Runnable() {

				@Override
				public void run() {
					ResultSet check = getResults(
							"Select * from PlayerProfile where UUID = '" + p.getUniqueId().toString() + "'");

					try {
						String guid = UUID.randomUUID().toString();
						if (check == null || !check.next()) {
							executeSQLAsync("INSERT INTO PlayerProfile " + "(UUID,GUID)" + "VALUES ('"
									+ p.getUniqueId().toString() + "','" + guid + "');");
						} else {
							if (check.getString("PasswordHash") != null) {
								executeSQLAsync("UPDATE PlayerProfile set GUID='" + guid
										+ "', PasswordHash = NULL where UUID='" + p.getUniqueId().toString() + "';");
							} else {
								guid = check.getString("GUID");
							}
						}
						sender.sendMessage(ChatColor.GOLD + "Click the following link to set your password:");
						sender.sendMessage(
								ChatColor.GREEN + "https://serenity-mc.org/login/setPassword.php?guid=" + guid);
						check.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();

					}
				}
			}, 0L);
		}
		return true;
	}

	// Help
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

			for (SerenityCommand sc : ALL_COMMANDS) {
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
							"§5§lSerenity Commands §7(hover for info) §dPage " + (page + 1) + "/" + maxPage + "§5",
							FancyText.RUN_COMMAND, "/help " + (displayPage + 1), FancyText.SHOW_TEXT,
							"Go to next page");
					sendRawPacket(p, prev);
				} else {
					String prev = FancyText.GenerateFancyText(
							"§5§lSerenity Commands §7(hover for info) §dPage " + (page + 1) + "/" + maxPage + "§5",
							FancyText.RUN_COMMAND, "/help " + (displayPage - 1), FancyText.SHOW_TEXT,
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
				for (SerenityCommand sc : ALL_COMMANDS) {
					if (sc.command.contains(searchTerm.toLowerCase())) {
						thisPlayerCommandList.add(sc);
					}
				}

				String prev = FancyText.GenerateFancyText(
						"§5§lSerenity Commands §7(hover for info) §dSearch: §7" + searchTerm, FancyText.RUN_COMMAND,
						"/help ", FancyText.SHOW_TEXT, "Hello!");
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
		String ft = FancyText.GenerateFancyText("§a/" + sc.command + ": §7" + sc.description, FancyText.SUGGEST_COMMAND,
				"/" + sc.command + " ", FancyText.SHOW_TEXT, sc.longDescription + "\n\nUsage:\n" + sc.example);
		sendRawPacket(p, ft);
	}

	// Move
	private boolean move(CommandSender sender, String[] arg3) {
		if (sender instanceof Player && arg3.length > 0) {
			if (sender instanceof Player) {
				Player p = ((Player) sender).getPlayer();
				SerenityPlayer sp = serenityPlayers.get(p.getUniqueId());
				if (sp.getMinutes() < 180) {
					p.sendMessage("§cSorry, only players with more than 3 hours can move between Serenity Servers");
					return true;
				}
			}

			Player p = ((Player) sender);
			String server = arg3[0];
			if (server.equalsIgnoreCase("creative")
					|| (this.getConfig().getBoolean("EventOn", false) && server.equalsIgnoreCase("event"))) {
				sendTo(p, server);
			}
			if (!this.getConfig().getBoolean("EventOn", false) && server.equalsIgnoreCase("event")) {
				p.sendMessage("§cSorry, there is no event right now.");
			}
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
		p.sendMessage("§aPreparing to move to §d" + string + "§a!  Stand still for a bit");
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
					Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
					player.sendPluginMessage(global, "BungeeCord", out.toByteArray());
				}
			}
		}, 100L);
	}

	// Map
	private boolean map(CommandSender sender, String[] arg3) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (p.getLocation().getWorld().getName().contains("world_nether")) {
				int x = (int) p.getLocation().getX() * 8;
				int z = (int) p.getLocation().getZ() * 8;

				String html = "http://serenity-mc.org/map/#map_world/0/10/" + x + "/" + z + "/64";
				p.sendMessage("§3If you build a portal, you should exit near this area:  \n§6" + html);
				return true;
			}

			if (p.getLocation().getWorld().getName().equals("world")) {
				int x = (int) p.getLocation().getX();
				int z = (int) p.getLocation().getZ();
				int y = (int) p.getLocation().getY();
				String html = "http://serenity-mc.org/map/#map_world/0/10/" + x + "/" + z + "/" + y;
				p.sendMessage("§3You are here:  \n§6" + html);
				return true;
			}

			return true;
		}
		return false;
	}

	// Mytime
	private boolean mytime(CommandSender sender, String[] arg3) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			sender.sendMessage(getHoursAndMinutes(p.getUniqueId()));
		}

		return true;
	}

	// Links
	private boolean links(CommandSender sender, String[] arg3) {
		String s = "";
		for (String key : linksCfg.getConfig().getKeys(false)) {
			s += "§6" + key + ": §e" + linksCfg.getConfig().getString(key) + "\n";
		}
		sender.sendMessage(s);
		return true;
	}

	// Text
	private boolean text(CommandSender sender, String[] arg3) {

		String message = "";
		for (int i = 0; i < arg3.length; i++) {
			message += arg3[i] + " ";
		}
		if (sender instanceof Player) {
			SerenityPlayer sp = serenityPlayers.get(((Player) sender).getUniqueId());

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

				Bukkit.getScheduler().scheduleSyncDelayedTask(global, new Runnable() {
					@Override
					public void run() {
						for (Player p : Bukkit.getOnlinePlayers()) {
							if (p.getName().equals(sendName)) {
								p.sendMessage("§aText sent successfully: §2" + str);
								return;
							}
						}
					}
				});
			}
		});
	}

	// Msg
	private boolean msg(CommandSender sender, String[] arg3) {
		if (arg3.length > 0) {
			if (arg3[0].equalsIgnoreCase("read") || arg3[0].equalsIgnoreCase("~") || arg3[0].equalsIgnoreCase("^")) {
				readPrivateMessages(sender, arg3);
				return true;
			}
		}

		if (arg3.length > 1) {
			if (arg3[0].equals("r")) {
				if (sender instanceof Player) {
					SerenityPlayer sp = serenityPlayers.get(((Player) sender).getUniqueId());
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
				SerenityPlayer sp = serenityPlayers.get(((Player) sender).getUniqueId());
				Player p = (Player) sender;
				Collections.sort(sp.getOfflineMessages());
				if (sp.getOfflineMessages().size() > 0) {
					for (OfflineMessage s : sp.getOfflineMessages()) {

						String json = s.constructString(getChatColor(s.getFrom()),
								serenityPlayers.get(s.getFrom()).getName());
						if (json.length() > 0) {
							Packet packet = new PacketPlayOutChat(ChatSerializer.a(json));

							((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
						}
					}
				} else {
					sender.sendMessage(
							"§cYou have §a0 §cunread messages.§r\n§aVisit §dhttps://serenity-mc.org/messages §ato read old messages");
				}
			}
		}

		if (arg3[0].equalsIgnoreCase("~") && arg3.length > 1) {
			if (sender instanceof Player) {
				SerenityPlayer sp = serenityPlayers.get(((Player) sender).getUniqueId());
				for (OfflineMessage s : sp.getOfflineMessages()) {
					if (s.getID() == Integer.parseInt(arg3[1])) {
						SerenityPlayer from = serenityPlayers.get(s.getFrom());
						sender.sendMessage("==§nMessage from " + from.getChatColor() + "§n" + from.getName()
								+ "§r==\n \n" + "§r" + s.getMessage().replace('`', '\'') + "\n ");
						String sql = "Update Messages set ReadStatus = 1 Where M_ID = " + s.getID();
						s.setRead(true);
						String context = FancyText.GenerateFancyText("§a(context)", FancyText.OPEN_URL,
								"https://serenity-mc.org/message/?name=" + from.getName(), FancyText.SHOW_TEXT,
								"Read all messages to/from " + from.getChatColor() + from.getName());
						String more = FancyText.GenerateFancyText("  §6(all messages)", FancyText.RUN_COMMAND,
								"/msg read", FancyText.SHOW_TEXT, "/msg read");
						String reply = FancyText.GenerateFancyText("  §2(reply)", FancyText.SUGGEST_COMMAND,
								"/msg " + serenityPlayers.get(s.getFrom()).getName() + " ", FancyText.SHOW_TEXT,
								"/msg " + serenityPlayers.get(s.getFrom()).getName());
						sendRawPacket(((Player) sender).getPlayer(), "[" + context + "," + more + "," + reply + "]");
						executeSQLAsync(sql);
						return;
					}
				}
			}
		}
	}
	private boolean sendOfflineMessage(OfflineMessage om) {
		SerenityPlayer author = serenityPlayers.get(om.getFrom());
		SerenityPlayer recipient = serenityPlayers.get(om.getTo());
		recipient.setLastToSendMessage(author.getName());

		Player recipientB = Bukkit.getPlayer(recipient.getUUID());
		Player authorA = Bukkit.getPlayer(author.getUUID());
		if (recipientB != null && recipientB.isOnline()) {
			recipientB.sendMessage(author.getChatColor() + "§o" + "From " + author.getName() + ": "
					+ om.getMessage().replace("\\", ""));
			om.setRead(true);
		}
		if (authorA != null && authorA.isOnline()) {
			authorA.sendMessage(author.getChatColor() + "§o" + "To " + recipient.getName() + ": "
					+ om.getMessage().replace("\\", ""));
		}
		addDatabaseMessage(om);
		return true;
	}
	private boolean sendPrivateMessage(CommandSender sender, String[] arg3) {
		String name = arg3[0];
		String message = "";
		for (int i = 1; i < arg3.length; i++) {
			message += arg3[i] + " ";
		}

		OfflineMessage om = new OfflineMessage();
		om.setMessage(getMysqlRealScapeString(message));
		om.setTime(System.currentTimeMillis());
		om.setRead(false);
		if (sender instanceof Player) {
			om.setFrom(((Player) sender).getUniqueId());
		} else {
			for (SerenityPlayer sp : serenityPlayers.values()) {
				if (sp.isOp()) {
					om.setFrom(sp.getUUID());
					break;
				}
			}
		}

		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.getDisplayName().toUpperCase().contains(name.toUpperCase())) {
				om.setTo(p.getUniqueId());
				break;
			}
		}

		if (om.getTo() == null) {
			for (Map.Entry<UUID, SerenityPlayer> entry : serenityPlayers.entrySet()) {
				if (entry.getValue().getName().toUpperCase().equals(name.toUpperCase())) {
					om.setTo(entry.getValue().getUUID());
					break;
				}
			}
		}

		if (om.getTo() == null) {
			for (Map.Entry<UUID, SerenityPlayer> entry : serenityPlayers.entrySet()) {
				if (entry.getValue().getName().toUpperCase().contains(name.toUpperCase())) {
					om.setTo(entry.getValue().getUUID());
					break;
				}
			}
		}

		if (om.getTo() == null) {
			return false;
		}

		return sendOfflineMessage(om);
	}
	private boolean MsgViaText(String rec, String msg) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.getName().toUpperCase().contains(rec.toUpperCase())) {
				p.sendMessage("§d§oText from Hal §7(reply with /text): §r§d" + msg);
				sendATextToHal("Received", p.getName() + " received the text!");
				return true;
			}
		}

		return true;
	}

	// Bone
	@EventHandler
	public void onPlayerRedeemBone(PlayerInteractEvent event) {
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
			if (event.getPlayer().getItemInHand().getType() == Material.COOKIE) {
				if (event.getPlayer().getItemInHand().hasItemMeta()) {
					if (event.getPlayer().getItemInHand().getItemMeta().hasDisplayName()) {
						if (event.getPlayer().getItemInHand().getItemMeta().getDisplayName()
								.equals("§dHoliday Cookie")) {
							Player player = event.getPlayer();
							event.setCancelled(true);
							if (player.getItemInHand().getAmount() != 1) {
								player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
							} else {
								player.setItemInHand(new ItemStack(Material.AIR));
							}
							givePlayerARandomSkull(player);
							player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 3F, .025F);
							player.getWorld().spawnParticle(Particle.SPELL_MOB, player.getLocation(), 25, .5f, .5f, .5f,
									50);
						}
					}
				}
			}
		}
	}

	// LC
	// TODO Fix
	private boolean chat(CommandSender sender, String[] arg3) {
		if (arg3.length == 1) {
			if (arg3[0].equals("tp")) {
				doRandomTeleport(sender);
				return true;
			}

			if (arg3[0].equalsIgnoreCase("celebrate")) {
				celebrateNewYears(sender);
				return true;
			}

			if (arg3[0].equalsIgnoreCase("moon")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					p.sendMessage(getMoonPhase(p));
					return true;
				}
			}

			// Lel -Tech
			if (arg3[0].equalsIgnoreCase("magic")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					ItemStack is = p.getItemInHand();
					ItemMeta im = is.getItemMeta();
					im.addEnchant(Enchantment.LUCK, -1, true);
					im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
					is.setItemMeta(im);
					p.setItemInHand(is);
					return true;
				}
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

		String msg = getTranslationLanguage(sender, stringKeys.CHATHELP.toString());
		sender.sendMessage(msg);
		return true;

	}
	private boolean globalChat(CommandSender sender, String[] arg3) {
		if (sender instanceof Player) {
			SerenityPlayer sp = serenityPlayers.get(((Player) sender).getUniqueId());

			if (sp.isMuted()) {
				return true;
			}

			String txt = "";

			for (int i = 1; i < arg3.length; i++) {
				txt += (arg3[i] + " ");
			}

			simulateChat(sp.getUUID(), txt);

			return true;
		}
		return false;
	}
	private boolean enableGlobalChat(CommandSender sender, String[] arg3) {
		if (sender instanceof Player) {
			SerenityPlayer sp = serenityPlayers.get(((Player) sender).getUniqueId());
			sp.setLocalChatting(false);
			String msg = getTranslationLanguage(sender, stringKeys.CHATPUBLICNOW.toString());
			sender.sendMessage(msg);
			return true;
		}
		return false;
	}
	private boolean enableLocalChat(CommandSender sender, String[] arg3) {
		if (sender instanceof Player) {
			SerenityPlayer sp = serenityPlayers.get(((Player) sender).getUniqueId());
			sp.setLocalChatting(true);
			String msg = getTranslationLanguage(sender, stringKeys.CHATLOCALNOW.toString());
			sender.sendMessage(msg);
			return true;
		}
		return false;
	}

	// Status
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
						if (gc.getTimeInMillis() - ps.getTime().getTimeInMillis() > 604800000) {
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
						messageToSend += serenityPlayers.get(ps.getUuid()).getChatColor()
								+ serenityPlayers.get(ps.getUuid()).getName() + ps.toString();
					}

					final String messageToSendF = messageToSend;

					Bukkit.getScheduler().scheduleSyncDelayedTask(global, new Runnable() {
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
						for (Map.Entry<UUID, SerenityPlayer> entry : serenityPlayers.entrySet())
							if (entry.getValue().isOp()) {
								uuid = entry.getValue().getUUID();
							}
					}

					PlayerStatus ps = new PlayerStatus(status, new GregorianCalendar(), uuid);

					addPlayerStatus(ps);
					// sender.sendMessage("§2Your status was updated!");

					String msg = getTranslationLanguage(senderF, stringKeys.STATUSSUCCESS.toString());
					final String msgF = msg;

					Bukkit.getScheduler().scheduleSyncDelayedTask(global, new Runnable() {
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
		String sql = "DELETE FROM Status WHERE UUID='" + ps.getUuid().toString() + "'";
		executeSQLAsync(sql);
	}

	// Portal
	private boolean getPortal(CommandSender sender) {
		Location l = Bukkit.getServer().getPlayer(sender.getName()).getLocation();
		if (l.getWorld().getName().equals(("world"))) {

			String msg = getTranslationLanguage(sender, stringKeys.PORTALNETHER.toString());
			sender.sendMessage(String.format(msg, (int) (l.getX() / 8), (int) (l.getZ() / 8)));

			/*
			 * sender.sendMessage(
			 * "§l§5If you want your portal to exit here, build it in the nether at: "
			 * + "§r§d\n   X: §l§3" + (int) (l.getX() / 8) + "§r§d\n   Z: §l§3"
			 * + (int) (l.getZ() / 8));
			 */
		} else {

			String msg = getTranslationLanguage(sender, stringKeys.PORTALOVERWORLD.toString());
			sender.sendMessage(String.format(msg, (int) (l.getX() * 8), (int) (l.getZ() * 8)));

			/*
			 * sender.sendMessage(
			 * "§l§5If you build your portal here you will end up at: " +
			 * "§r§d\n   X: §l§3" + (int) (l.getX() * 8) + "§r§d\n   Z: §l§3" +
			 * (int) (l.getZ() * 8));
			 */
		}
		return true;
	}

	// Seen
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
			Bukkit.getScheduler().runTaskLaterAsynchronously(this, new Runnable() {
				@Override
				public void run() {
					for (Map.Entry<UUID, SerenityPlayer> entry : serenityPlayers.entrySet()) {
						if (entry.getValue().getName().toUpperCase().contains(searchTerm.toUpperCase())) {
							if (entry.getValue().isWhitelisted() == true && entry.getValue().isBanned() == false) {
								if (senderf instanceof Player) {
									sendRawPacket(((Player) senderf).getPlayer(), FancyText.GenerateFancyText(
											entry.getValue().getChatColor() + entry.getValue().getName() + ": §7"
													+ convertToHoursAndMinutesSinceNoSeconds(
															entry.getValue().getLastPlayed()),
											FancyText.SUGGEST_COMMAND, "/msg " + entry.getValue().getName() + " ",
											FancyText.SHOW_TEXT, "/msg " + entry.getValue().getName()));
								} else {
									senderf.sendMessage(entry.getValue().getChatColor() + entry.getValue().getName()
											+ ": §7"
											+ convertToHoursAndMinutesSinceNoSeconds(entry.getValue().getLastPlayed()));
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
				for (Map.Entry<UUID, SerenityPlayer> entry : serenityPlayers.entrySet()) {
					if (entry.getValue().isBanned() == false && entry.getValue().isWhitelisted() == true)
						splist.add(entry.getValue());
				}
				Collections.sort(splist, Comparator.comparing(SerenityPlayer::getLastPlayed));
				Collections.reverse(splist);

				Player p = null;
				if (senderf instanceof Player) {
					p = (Player) senderf;
				}
				String ret = "";
				for (int i = pagef * 8; i < pagef * 8 + 8; i++) {
					if (i < splist.size()) {
						if (splist.get(i) != null) {
							ret += splist.get(i).getChatColor() + splist.get(i).getName() + ": §7"
									+ convertToHoursAndMinutesSinceNoSeconds(splist.get(i).getLastPlayed()) + "§r\n";

							if (p != null) {
								sendRawPacket(p,
										FancyText.GenerateFancyText(
												splist.get(i).getChatColor() + splist.get(i).getName() + ": §7"
														+ convertToHoursAndMinutesSinceNoSeconds(
																splist.get(i).getLastPlayed()),
												FancyText.SUGGEST_COMMAND, "/msg " + splist.get(i).getName() + " ",
												FancyText.SHOW_TEXT, "/msg " + splist.get(i).getName()));
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
					String nav = "  --  Page " + (pagef + 1) + " of " + (splist.size() / 8);
					String fancyNext = FancyText.GenerateFancyText("§a  --->", FancyText.RUN_COMMAND,
							"/lastseen " + next, FancyText.SHOW_TEXT, "Next");
					String fancyPrev = FancyText.GenerateFancyText(nav + "§a  <---", FancyText.RUN_COMMAND,
							"/lastseen " + prev, FancyText.SHOW_TEXT, "Previous");
					String phonyNext = FancyText.GenerateFancyText("§7  --->", FancyText.RUN_COMMAND,
							"/lastseen " + curr, null, null);
					String phonyPrev = FancyText.GenerateFancyText(nav + "§7  <---", null, null, FancyText.RUN_COMMAND,
							"/lastseen " + curr);

					if (curr == splist.size() / 8) {
						sendRawPacket(p, "[" + fancyPrev + "," + phonyNext + "]");
						return;
					}
					if (curr == 1) {
						sendRawPacket(p, "[" + phonyPrev + "," + fancyNext + "]");
						return;
					}
					if (curr > 1 && pagef + 1 < splist.size()) {
						sendRawPacket(p, "[" + fancyPrev + "," + fancyNext + "]");
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

	// AFK
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

					String result = new DecimalFormat("##.##").format(percentage);

					String msg = getTranslationLanguage(sender, stringKeys.BEDVOTING.toString());

					sender.sendMessage(msg + "§6" + result + "%");

					((Player) sender).getPlayer().resetTitle();

					/*
					 * sender.sendMessage("§9" +
					 * "Waiting for >50% of players to get in bed or type /ok. Current: "
					 * + "§b" + result + "%");
					 */

					checkHalfIgnored();
					return true;

				} else {

					String msg = getTranslationLanguage(sender, stringKeys.BEDOKNOTVOTING.toString());
					sender.sendMessage(msg);

					/*
					 * sender.sendMessage("§c" +
					 * "This can only be done after somebody enters a bed");
					 */
					return true;

				}
			}

			if (sp.isAFK()) {

				String msg = getTranslationLanguage(sender, stringKeys.AFKALREADYAFK.toString());
				sender.sendMessage(msg);

				// sender.sendMessage("§cYou are already AFK!");
				return true;
			}

			setAfk(sp, false);
		}

		return true;
	}
}
