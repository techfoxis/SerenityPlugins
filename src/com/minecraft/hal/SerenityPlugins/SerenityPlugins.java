package com.minecraft.hal.SerenityPlugins;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Horse.Style;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.Ocelot;
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
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
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
import org.bukkit.material.Dispenser;
import org.bukkit.material.Wool;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.Vector;

public final class SerenityPlugins extends JavaPlugin implements Listener,
		CommandExecutor {

	public ConfigAccessor playtimeCfg;
	public ConfigAccessor mailboxCfg;
	public ConfigAccessor statusCfg;
	public ConfigAccessor chatcolorCfg;
	public ConfigAccessor fireworksCfg;
	public ConfigAccessor protectedAreasCfg;
	public ConfigAccessor podrickCfg;
	public ConfigAccessor stringsCfg;
	public ConfigAccessor languagesCfg;
	public ConfigAccessor teamsCfg;
	public ConfigAccessor emailCfg;
	public ConfigAccessor bookCfg;
	public ConfigAccessor linksCfg;
	public ConfigAccessor daleCfg;
	// public ConfigAccessor expansionCfg;

	public HashMap<String, Location> playerLocations;
	public List<Player> afkPlayers;
	public List<Mailbox> mailBoxes;
	public List<String> mailablePlayers;
	public List<Player> preppedToUnProtectChunk;
	public List<PlayerProtect> preppedToProtectArea;
	public List<Player> localChatters;
	public Set<PlayerStatus> playerStatuses;
	public List<FireWorkShow> fireworkShowLocations;
	public boolean flameOn;
	public final List<String> PUBLIC_MAILBOXES = new ArrayList<String>() {
		{
			//todo: make mailboxes automatically public if they begin with #...
			add("#Spawn");
			add("#Library");
			add("#Nether_Hub");
			add("#Exchange");
			add("#PotatoChips");
		}
	};

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
			add(ChatColor.DARK_GREEN);
			add(ChatColor.GREEN);
			add(ChatColor.AQUA);
			add(ChatColor.DARK_AQUA);
			add(ChatColor.DARK_BLUE);
			add(ChatColor.BLUE);
			add(ChatColor.LIGHT_PURPLE);
			add(ChatColor.DARK_PURPLE);
			add(ChatColor.BLACK);
			add(ChatColor.DARK_GRAY);
			add(ChatColor.GRAY);
			add(ChatColor.WHITE);
		}
	};

	public List<Player> invisiblePlayers;
	public List<Player> flamingPlayers;
	public boolean readyToLogInAsServer;
	public List<Player> deafPlayers;
	public boolean serverChatting;
	public List<Ignoring> ignorers;
	public List<Long> lags;
	public List<TeleportClicker> teleportClickers;
	public List<ProtectedArea> areas;
	public boolean votingForDay;
	public final int MAX_DISTANCE = 700;
	public Random rand = new Random();
	public List<String> mutedNames;
	public HashMap<UUID, PermissionAttachment> attachments;
	public boolean everyOtherMinute;
	public boolean wasLagging = false;
	public boolean thisHour = false;
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

	public final List<String> SOMEONEGREETINGPREFIX = new ArrayList<String>() {
		{
			add(Secrets.Secret.someoneGreeting1);
			add(Secrets.Secret.someoneGreeting2);
			add(Secrets.Secret.someoneGreeting3);
			add(Secrets.Secret.someoneGreeting4);
			add(Secrets.Secret.someoneGreeting5);
		}
	};

	public final List<String> SOMEONETEXT1 = new ArrayList<String>() {
		{
			add(Secrets.Secret.someoneFirstText1);
			add(Secrets.Secret.someoneFirstText2);
			add(Secrets.Secret.someoneFirstText3);
		}

	};

	public HashMap<String, Long> racers;
	public HashMap<String, String> englishStrings;
	public HashMap<String, String> playerRecentMessages;
	public HashMap<String, String> teamList;

	public enum stringKeys {
		PORTBADPORTAL, TIMEONEHOUR, TIMETHREEHOUR, TIMETWELVEHOUR, PORTENDPORTALWARNING, PORTEARLYPORTALATTEMPT, MAILHASMAIL, PROTISSTUCK, CHATTRIEDTOCHATWITHGLOBALCHATDISABLED, JOINPLAYERFIRSTLOGIN, MAILTRIEDTOEXPANDMAILBOX, MAILCREATEDAMAILBOX, MAILALREADYHASAMAILBOX, MAILOPENEDAPUBLICMAILBOX, MAILINTERRACTEDWITHAMAILBOX, MAILDESTROYEDMAILBOX, MAILTRIEDTOBREAKPUBLICMAILBOX, BEDSOMEONEENTEREDABED, PROTOWNBLOCK, PROTDOESNOTOWNBUTHASPERMISSION, PROTDOESNOTOWNBLOCK, PROTNOBODYOWNSBLOCK, RANDOMTPWAIT, RANDOMTPTOOFAR, RANDOMTPWRONGWORLD, CHATHELP, PROTALREADYPREPPED, PROTPREPPEDTOPROTECT, PROTAREABADARG, PROTAREATOOSMALL, PROTAREATOOBIG, PROTAREAREADY, PROTUNCLAIMPREPPED, PROTUNCLAIMSTILLPREPPED, PROTNOAREAS, PROTTRUSTLIST, PROTAREALIST, PROTEXTENDEDLISTDATA, PROTSTUCKABUSEATTEMPT, PROTSTUCKABUSEATTEMPTNOTEVENPROT, PROTTIMEBADARG, PROTADDEDTRUST, PROTTRUSTISSUES, PROTCANTFINDTRUST, PROTUNTRUSTED, PROTHELP, PROTTOOFAR, PROTALREADYOWN, PROTAREACLAIMED, PROTFIRSTCORNER, PROTINTERSECT, PROTPREMATURE, PROTNOTYET, PROTSUCCESS, PROTNOTOVERWORLD, PROTTOOFARAWAYTOUNCLAIM, PROTUNCLAIMSUCCESS, PROTUNCLAIMDOESNOTOWN, PROTUNCLAIMNOBODYOWNS, CHATCANSEEGLOBAL, CHATCANTSEEGLOBAL, STATUSSUCCESS, PORTALOVERWORLD, PORTALNETHER, LASTSEENNOTFOUND, BEDVOTING, BEDOKNOTVOTING, AFKALREADYAFK, GETTIMETICKS, MAILDOESNTHAVEMAILBOX, MAILCOULDNOTFINDBOX, MAILTOSELF, MAILEMPTY, MAILFULL, MAILNOTENOUGHSPACE, MAILSUCCESS, COMPASSHELP, COMPASSSPAWN, COMPASSBED, COMPASSBEDDESTROYED, COMPASSMAILBOX, COMPASSNOMAILBOX, COMPASSHERE, COMPASSCUST, CHATCOLORINVALID, CHATCOLOREARLY, CHATPUBLICNOW, CHATLOCALNOW, LAGWAIT, IGNORECOULDNOTFIND, IGNORENOLONGERIGNORING, IGNORESUCCESS, NOPERMS, FIREWORKSCREATE, FIREWORKSEXISTS, FIREWORKSEARLYBREAKATTEMPT, FIREWORKNONOWNERBREAK, FIREWORKBREAKSUCCESS
	}

	public boolean scoreboardIsDisplayed = false;

	public HashMap<String, Long> textCooldown;

	public boolean isAParty = false;
	public short partyMode = -1;
	public short partyOffset = 0;

	// public String[] betters;
	// public String[] horses;

	@Override
	public void onEnable() {
		stopTheParty();
		getServer().getPluginManager().registerEvents(this, this);

		// betters = new String[3];
		// horses = new String[3];
		TELEPORTDESTINATIONFORSOMETHING = Secrets.Secret.TELEPORTDESTINATIONFORSOMETHING;
		GREENBLOCKSINFINALDUNGEON = Secrets.Secret.GREENBLOCKSINFINALDUNGEON;
		BLUEBLOCKSINFINALDUNGEON = Secrets.Secret.BLUEBLOCKSINFINALDUNGEON;
		REDBLOCKSINFINALDUNGEON = Secrets.Secret.REDBLOCKSINFINALDUNGEON;
		CLEARBLOCKSINFINALDUNGEON = Secrets.Secret.CLEARBLOCKSINFINALDUNGEON;
		CENTERBLOCKSINFINALDUNGEON = Secrets.Secret.CENTERBLOCKSINFINALDUNGEON;
		WATERROOMMINESHAFT = Secrets.Secret.WATERROOMMINESHAFT;
		WATERROOMACTIVATE = Secrets.Secret.WATERROOMACTIVATE;
		DOORWAYTOFINALDUNGEON = Secrets.Secret.DOORWAYTOFINALDUNGEON;

		racers = new HashMap<String, Long>();

		flameOn = false;

		mailboxCfg = new ConfigAccessor(this, "mailboxes.yml");
		playtimeCfg = new ConfigAccessor(this, "playtime.yml");
		statusCfg = new ConfigAccessor(this, "status.yml");
		fireworksCfg = new ConfigAccessor(this, "fireworks.yml");
		protectedAreasCfg = new ConfigAccessor(this, "protectedareas.yml");
		chatcolorCfg = new ConfigAccessor(this, "chatcolor.yml");
		podrickCfg = new ConfigAccessor(this, "podrick.yml");
		stringsCfg = new ConfigAccessor(this, "strings.yml");
		languagesCfg = new ConfigAccessor(this, "languages.yml");
		teamsCfg = new ConfigAccessor(this, "teams.yml");
		emailCfg = new ConfigAccessor(this, "email.yml");
		bookCfg = new ConfigAccessor(this, "book.yml");
		linksCfg = new ConfigAccessor(this, "links.yml");
		daleCfg = new ConfigAccessor(this, "dale.yml");

		mailablePlayers = new ArrayList<String>();
		mailBoxes = new ArrayList<Mailbox>();
		preppedToProtectArea = new ArrayList<PlayerProtect>();
		preppedToUnProtectChunk = new ArrayList<Player>();
		localChatters = new ArrayList<Player>();
		playerStatuses = new TreeSet<PlayerStatus>();
		fireworkShowLocations = new ArrayList<FireWorkShow>();
		invisiblePlayers = new ArrayList<Player>();
		flamingPlayers = new ArrayList<Player>();
		readyToLogInAsServer = true;
		deafPlayers = new ArrayList<Player>();
		serverChatting = false;
		ignorers = new ArrayList<Ignoring>();
		lags = new ArrayList<Long>();
		playerLocations = new HashMap<String, Location>();
		teleportClickers = new ArrayList<TeleportClicker>();
		areas = new ArrayList<ProtectedArea>();
		votingForDay = false;
		afkPlayers = new ArrayList<Player>();
		mutedNames = new ArrayList<String>();
		attachments = new HashMap<UUID, PermissionAttachment>();
		everyOtherMinute = false;
		playerRecentMessages = new HashMap<String, String>();
		teamList = new HashMap<String, String>();
		textCooldown = new HashMap<String, Long>();

		ItemStack stick = Secrets.Secret.SECRETITEMSTACK1;
		ItemMeta item = stick.getItemMeta();
		item.setDisplayName(Secrets.Secret.SECRETITEM1NAME);
		item.addEnchant(Enchantment.LURE, -500, true);
		stick.setItemMeta(item);

		ShapedRecipe secretitem2 = new ShapedRecipe(new ItemStack(stick));
		secretitem2.shape("ABA", "BAB", "ABA");
		secretitem2.setIngredient('A', Secrets.Secret.SECRETITEM1MAT1);
		secretitem2.setIngredient('B', Secrets.Secret.SECRETITEM1MAT2);
		this.getServer().addRecipe(secretitem2);

		ItemStack secretItemSpookyOne = new ItemStack(Secrets.Secret.SECRETITEM2RESULT, 1, (short) 1);
		ItemMeta im = secretItemSpookyOne.getItemMeta();
		im.setDisplayName(Secrets.Secret.SECRETITEM2NAME);
		secretItemSpookyOne.setItemMeta(im);

		ShapedRecipe spookyFruitRecipe = new ShapedRecipe(new ItemStack(secretItemSpookyOne));
		spookyFruitRecipe.shape("AAA", "ABA", "AAA");
		spookyFruitRecipe.setIngredient('A', Secrets.Secret.SECRETITEM2MAT);
		spookyFruitRecipe.setIngredient('B', secretItemSpookyOne.getType());
		this.getServer().addRecipe(spookyFruitRecipe);

		for (Player p : Bukkit.getOnlinePlayers()) {
			addAndAttachAppropraitePermissionToPlayer(p);
		}

		ConfigurationSection mailBoxesFromConfig = mailboxCfg.getConfig()
				.getConfigurationSection("Mailboxes");

		try {
			for (String key : mailBoxesFromConfig.getKeys(false)) {

				ArrayList<String> coords = new ArrayList<String>();
				for (Object object : mailBoxesFromConfig.getList(key)) {
					coords.add(object.toString());
				}

				int x = Integer.parseInt(coords.get(1));
				int y = Integer.parseInt(coords.get(2));
				int z = Integer.parseInt(coords.get(3));
				String s = coords.get(0);
				Location l = new Location(Bukkit.getWorld(s), x, y, z);
				Mailbox mb = new Mailbox(key, l);
				mailablePlayers.add(key);
				mailBoxes.add(mb);

			}
		} catch (Exception e) {
			getLogger().info("Exception thrown while trying to add mailboxes!");

		}

		getLogger().info("Added " + mailBoxes.size() + " mailboxes");

		ConfigurationSection statusesFromConfig = statusCfg.getConfig()
				.getConfigurationSection("Status");

		try {
			for (String key : statusesFromConfig.getKeys(false)) {
				ArrayList<String> stat = new ArrayList<String>();
				for (Object subKey : statusesFromConfig.getList(key)) {
					stat.add(subKey + "");
				}
				String status = stat.get(1);
				GregorianCalendar time = new GregorianCalendar();
				time.setTimeInMillis(Long.parseLong(stat.get(0)));
				PlayerStatus ps = new PlayerStatus(key, status, time);
				playerStatuses.add(ps);
			}
		} catch (Exception e) {
			getLogger().info("Exception thrown while trying to add statuses");
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
				translation = translation.replace('@', '�');
				translation = translation.replace('^', '\n');
				englishStrings.put(subkey, translation);
			}
		} catch (Exception e) {
			getLogger().info(
					"Exception thrown while trying to english translations");
		}
		/*
		 * dutchStrings = new HashMap<String, String>();
		 * 
		 * try { ConfigurationSection englishStringsFromConfig = stringsCfg
		 * .getConfig().getConfigurationSection("Language.Dutch"); for (String
		 * subkey : englishStringsFromConfig.getKeys(true)) { String translation
		 * = englishStringsFromConfig.getString(subkey); translation =
		 * translation.replace('@', '�'); translation = translation.replace('^',
		 * '\n'); dutchStrings.put(subkey, translation); } } catch (Exception e)
		 * { getLogger().info(
		 * "Exception thrown while trying to dutch translations"); }
		 * 
		 * dutchSpeakers = new ArrayList<String>(); try { ConfigurationSection
		 * dutchSpeakersFromConfig = languagesCfg
		 * .getConfig().getConfigurationSection("Language");
		 * 
		 * for (Object subkey : dutchSpeakersFromConfig.getList("Dutch")) {
		 * Bukkit.broadcastMessage(subkey + " likes dutch");
		 * dutchSpeakers.add(subkey.toString()); } } catch (Exception e) {
		 * getLogger().info("Exception thrown while trying to dutch players"); }
		 */
		ConfigurationSection teamsFromConfig = teamsCfg.getConfig()
				.getConfigurationSection("Team");

		try {
			for (String key : teamsFromConfig.getKeys(false)) {
				for (Object subkey : teamsFromConfig.getList(key)) {
					teamList.put(subkey.toString(), key);
				}
			}
		} catch (Exception e) {
			getLogger().info("Exception thrown while trying to add teams");
		}

		for (Player p : Bukkit.getOnlinePlayers()) {
			playerLocations.put(p.getDisplayName(), p.getLocation());
		}

		emailCfg.saveDefaultConfig();

		runEverySecond();

		getLogger().info("Serenity Plugins enabled");

	}

	@EventHandler
	public void EatSecretSomethingEvent(PlayerItemConsumeEvent event) {
		ItemStack item = event.getItem();
		if (item.getItemMeta().hasDisplayName()) {
			if (item.getItemMeta().getDisplayName().equals(Secrets.Secret.SECRETITEM2NAME)) {
				event.setCancelled(true);
				event.getPlayer().kickPlayer(Secrets.Secret.SECRETMESSAGE);
			}
		}
		Location l = Secrets.Secret.SECRETSTANDINGLOCATION;
		if (event.getPlayer().getLocation().getWorld().getName()
				.equals("world")) {
			if (event.getPlayer().getLocation().distance(l) < 3) {
				Location dest = Secrets.Secret.SECRETDESTINATION;
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

	private void runEverySecond() {

		BukkitScheduler scheduler = Bukkit.getScheduler();
		scheduler.runTaskTimer(this, new Runnable() {
			Boolean b = false;
			int watchDogTimerEntities = 0;
			int minute = 0;
			boolean thisHalf = false;

			@Override
			public void run() {
				// Bukkit.getWorld("world").setTime(1000);
				Long now = System.currentTimeMillis();

				if (lags.size() > 9) {
					lags.remove(0);
				}

				lags.add(now);

				if (!b)
					fireOnMailBoxes();

				b = !b;

				updateFireworksBlocks();

				watchDogTimerEntities++;
				if (watchDogTimerEntities >= (60 * 3)) {
					if (getServer().getOnlinePlayers().size() != 0) {

						try {
							checkAndClearAllChunks();
						} catch (Exception e) {
							getLogger()
									.info("An exception was thrown in CheckAndClearChunks");
							Bukkit.getServer()
									.broadcastMessage(
											"�c[WARNING WARNING WARNING]\n\n\n*************Something went wrong!  Reloading the plugin!  Please email me if you read this and I will love you forever!  Type C&CC in the subject.  SerenityMCOwner@gmail.com\n[WARNING WARNING WARNING]\nThis might happen again in 10 minutes too!  But hopefully it won't!!!");

							Bukkit.getServer().dispatchCommand(
									Bukkit.getConsoleSender(), "server reload");
						}
						watchDogTimerEntities = 0;
					}
				}

				minute++;

				if (minute >= 60) {
					addAMinuteToEachPlayer();
					checkForTimes();
					minute = 0;
					if (everyOtherMinute) {
						afkTest();
					}
					everyOtherMinute = !everyOtherMinute;
				}

				if (minute % 5 == 0) {
					for (Player p : Bukkit.getOnlinePlayers()) {
						if (afkPlayers.contains(p)) {
							if (playerLocations.containsKey(p.getDisplayName())) {
								if (!playerLocations.get(p.getDisplayName())
										.getDirection()
										.equals(p.getLocation().getDirection())) {
									unAfk(p);
								}
							}
						}
					}
					// CheckForHorses();
				}

				if (minute % 10 == 0) {
					if (aBattleIsRaging)
						teleportSomeone();
				}

				for (Player p : Bukkit.getOnlinePlayers()) {
					if (p.getItemInHand().getType() == Material.STICK) {
						for (ProtectedArea pa : areas) {
							if (pa.owner.equals(p.getDisplayName())) {

								final ProtectedArea paf = pa;

								//Kaymak wanted squiggles and alyssa copied
								if (pa.owner.equals("Kaymaki")
										|| pa.owner.equals("__Alyssa__")) {
									paf.highlightAreaKayla();
								} else {
									paf.highlightArea();
								}
								Bukkit.getScheduler().scheduleSyncDelayedTask(
										SerenityPlugins.this, new Runnable() {
											@Override
											public void run() {
												if (paf.owner.equals("Kaymaki")) {
													paf.highlightAreaKayla();
												} else {
													paf.highlightArea();
												}
											}
										}, 10L);
							}
						}
					}

					/*
					 * if (p.isOp()) { if (flameOn) { Location l = new
					 * Location(p.getLocation() .getWorld(),
					 * p.getLocation().getX(), p .getLocation().getY() + .5,
					 * p.getLocation() .getZ());
					 * ParticleEffect.HEART.display((float) .15, (float) .5,
					 * (float) .15, (float) 0, 25, l, 20); } }
					 */
				}

				if (getTickrate() < 16) {
					Bukkit.getLogger().info("�cTickrate: " + getTickrate());
				}

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

		}, 0L, 20L);
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
	 * s1.setLine(0, horses[0] == null ? "�cNONE" : "�3" + horses[0]);
	 * s2.setLine(0, horses[1] == null ? "�cNONE" : "�3" + horses[1]);
	 * s3.setLine(0, horses[2] == null ? "�cNONE" : "�3" + horses[2]);
	 * 
	 * s1.update(); s2.update(); s3.update();
	 * 
	 * }
	 */

	protected void teleportSomeone() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.getWorld().getName().equals("world")) {
				if (p.getLocation().distance(CENTERBLOCKSINFINALDUNGEON) < 75) {
					for (Entity e : p.getNearbyEntities(50, 70, 50)) {
						if (e.getCustomName() != null) {
							if (e.getCustomName().equals(Secrets.Secret.BADGUYSNAME)) {
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
				if (playerLocations.containsKey(p.getDisplayName())) {
					if (playerLocations.get(p.getDisplayName()).getDirection()
							.equals(p.getLocation().getDirection())) {
						setAfk(p);
					}
				}
			}
		}

		playerLocations.clear();
		for (Player p : Bukkit.getOnlinePlayers()) {
			playerLocations.put(p.getDisplayName(), p.getLocation());
		}
	}

	private boolean isAfk(Player player) {
		for (Player p : afkPlayers) {
			if (p.equals(player))
				return true;
		}
		return false;
	}

	private void setAfk(Player player) {

		if (isAfk(player)) {
			return;
		}

		playerLocations.remove(player);
		playerLocations.put(player.getDisplayName(), player.getLocation());

		afkPlayers.add(player);
		player.setSleepingIgnored(true);
		Bukkit.getServer().broadcastMessage(
				"�2" + player.getDisplayName() + "�3 is AFK!");
	}

	private void unAfk(Player player) {
		afkPlayers.remove(player);
		player.setSleepingIgnored(false);
		Bukkit.broadcastMessage("�2" + player.getDisplayName() + "�3 is back!");
	}

	protected void updateFireworksBlocks() {
		for (int i = 0; i < fireworkShowLocations.size(); i++) {
			if (fireworkShowLocations.get(i).isActive) {
				fireworkShowLocations.get(i).getLocation().getBlock()
						.setType(Material.REDSTONE_BLOCK);
				fireworkShowLocations.get(i).getLocation().getBlock()
						.getState().update();
				if (new GregorianCalendar().getTimeInMillis()
						- fireworkShowLocations.get(i).lastShow
								.getTimeInMillis() > 600000) {
					fireworkShowLocations.get(i).getLocation().getBlock()
							.setType(Material.GOLD_BLOCK);
					fireworkShowLocations.get(i).getLocation().getBlock()
							.getState().update();
					fireworkShowLocations.get(i).setActive(false);
				}
			}
		}
	}

	protected void fireOnMailBoxes() {
		for (Mailbox mb : mailBoxes) {
			try {
				Chest chest = (Chest) mb.getLocation().getBlock().getState();

				ItemStack[] items = chest.getInventory().getContents();

				for (ItemStack is : items) {
					if (is != null) {
						Location l = new Location(mb.getLocation().getWorld(),
								mb.getLocation().getX() + .5, mb.getLocation()
										.getY() + 1.25,
								mb.getLocation().getZ() + .5);
						ParticleEffect.HEART.display((float) .25, (float) .25,
								(float) .25, 0, 5, l, 50);
						break;
					}
				}
			} catch (Exception e) {

			}
		}
	}

	protected void checkForTimes() {

		for (Iterator<? extends Player> i = Bukkit.getServer()
				.getOnlinePlayers().iterator(); i.hasNext();) {
			Player p = i.next();

			if (!p.hasPermission("SerenityPlugins.oneHour")) {
				if (playtimeCfg.getConfig().getInt(
						"Playtime." + p.getUniqueId().toString()) > 60) {

					p.sendMessage(getTranslationLanguage(p,
							stringKeys.TIMEONEHOUR.toString()));
					attachments.remove(p.getUniqueId());
					addAndAttachAppropraitePermissionToPlayer(p);
					Bukkit.getLogger().info(
							p.getDisplayName() + " has reached 1 hour");
					doRandomFirework(p.getWorld(), p.getLocation());
				}
			}

			if (!p.hasPermission("SerenityPlugins.threeHour")) {
				if (playtimeCfg.getConfig().getInt(
						"Playtime." + p.getUniqueId().toString()) > 180) {
					p.sendMessage(getTranslationLanguage(p,
							stringKeys.TIMETHREEHOUR.toString()));
					// p.sendMessage("�2\nThanks for playing!  \nYou may now vote for server events with /vote !\n");
					Bukkit.getLogger().info(
							p.getDisplayName() + " has reached 3 hours");
					attachments.remove(p.getUniqueId());
					addAndAttachAppropraitePermissionToPlayer(p);
				}
			}

			if (!p.hasPermission("SerenityPlugins.twelveHour")) {
				if (playtimeCfg.getConfig().getInt(
						"Playtime." + p.getUniqueId().toString()) > 720) {
					// p.sendMessage("�2\nThanks for being a dedicated player!  \nYou may now change your chat color with /setchatcolor !\n");
					p.sendMessage(getTranslationLanguage(p,
							stringKeys.TIMETWELVEHOUR.toString()));
					attachments.remove(p.getUniqueId());
					addAndAttachAppropraitePermissionToPlayer(p);
					Bukkit.getLogger().info(
							p.getDisplayName() + " has reached 12 hours");
					for (int j = 0; j < 15; j++) {
						doRandomFirework(p.getWorld(), p.getLocation());
					}
				}
			}
		}

		for (ProtectedArea pa : areas) {
			if (pa.owner.equals("[Server]")) {
				for (Player player : Bukkit.getOnlinePlayers()) {
					if (!pa.hasPermission(player.getDisplayName())) {
						if (getPlayerMinutes(player.getDisplayName()) > 1439) {
							ArrayList<String> list = new ArrayList<String>();

							for (ProtectedArea pas : areas) {
								if (pas.owner.equals("[Server]")) {
									pas.addTrust(player.getDisplayName());
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

							player.sendMessage("�2\nThanks for being a dedicated player!  \nYou may now edit the spawn area!\n");

							for (int j = 0; j < 24; j++) {
								doRandomFirework(player.getWorld(),
										player.getLocation());
							}
						}
					}
				}
			}
		}
	}

	protected void addAMinuteToEachPlayer() {

		int count = 0;
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (!isAfk(p)) {
				addAMinute(p);
				if (!p.isOp())
					count++;
			}
		}

		/*
		 * if (count != 0) { int time =
		 * expansionCfg.getConfig().getInt("Minutes") + count;
		 * 
		 * expansionCfg.getConfig().set("Minutes", time);
		 * expansionCfg.saveConfig(); expansionCfg.reloadConfig(); if(time >=
		 * 20000){ time = 20000; } Bukkit.getWorld("world").getWorldBorder()
		 * .setSize(10000 + (time * .25));
		 * 
		 * }
		 */

		if (Bukkit.getOnlinePlayers().size() > 0) {
			playtimeCfg.saveConfig();
			playtimeCfg.reloadConfig();
		}
	}

	private void addAMinute(Player player) {
		int playTime = playtimeCfg.getConfig().getInt(
				"Playtime." + player.getUniqueId().toString());
		playtimeCfg.getConfig().set(
				"Playtime." + player.getUniqueId().toString(), ++playTime);
	}

	@Override
	public void onDisable() {

		attachments.clear();
		getLogger().info("Serenity Plugins disabled");
	}

	@EventHandler
	public void onPortal(PlayerPortalEvent evt) {

		if (evt.getTo().getWorld().getName().equals("world_the_end")) {
			String msg = getTranslationLanguage(evt.getPlayer(),
					stringKeys.PORTENDPORTALWARNING.toString());
			evt.getPlayer().sendMessage(msg);
			/*
			 * evt.getPlayer() .sendMessage(
			 * "�c-----[WARNING]-----\nI RESET THE END EVERY FRIDAY MORNING (5:00 AM GMT-5)  \nIf you are in here when it is deleted, you may be killed when you re-log in! (fall damage, suffocation, spawn over the edge)  Do not disconnect until you are back in the overworld!  You have been warned!"
			 * );
			 */
		}
		/*
		 * else { // evt.getPlayer().sendMessage( //
		 * "�cYou cannot travel through portals yet!");
		 * 
		 * String msg = getTranslationLanguage(evt.getPlayer(),
		 * stringKeys.PORTEARLYPORTALATTEMPT.toString());
		 * evt.getPlayer().sendMessage(msg);
		 * 
		 * evt.setCancelled(true); }
		 */
	}

	@EventHandler
	public void onPLayerJoinPerms(PlayerJoinEvent event) {
		if (!attachments.containsKey(event.getPlayer().getUniqueId())) {
			addAndAttachAppropraitePermissionToPlayer(event.getPlayer());
		}
	}

	private void addAndAttachAppropraitePermissionToPlayer(Player player) {
		PermissionAttachment attachment = player.addAttachment(this);
		attachments.put(player.getUniqueId(), attachment);

		if (getPlayerMinutes(player.getDisplayName()) > 720) {
			Bukkit.getLogger().info(player.getName() + " is in group 12 hours");
			attachments.get(player.getUniqueId()).setPermission(
					"SerenityPlugins.starter", true);
			attachments.get(player.getUniqueId()).setPermission(
					"SerenityPlugins.oneHour", true);
			attachments.get(player.getUniqueId()).setPermission(
					"SerenityPlugins.threeHour", true);
			attachments.get(player.getUniqueId()).setPermission(
					"SerenityPlugins.twelveHour", true);
			return;
		}
		if (getPlayerMinutes(player.getDisplayName()) > 180) {
			Bukkit.getLogger().info(player.getName() + " is in group 3 hours");
			attachments.get(player.getUniqueId()).setPermission(
					"SerenityPlugins.starter", true);
			attachments.get(player.getUniqueId()).setPermission(
					"SerenityPlugins.oneHour", true);
			attachments.get(player.getUniqueId()).setPermission(
					"SerenityPlugins.threeHour", true);
			return;
		}
		if (getPlayerMinutes(player.getDisplayName()) > 60) {
			Bukkit.getLogger().info(player.getName() + " is in group 1 hour");
			attachments.get(player.getUniqueId()).setPermission(
					"SerenityPlugins.oneHour", true);
			attachments.get(player.getUniqueId()).setPermission(
					"SerenityPlugins.starter", true);
			return;
		}
		Bukkit.getLogger().info(player.getName() + " is in group starter");
		attachments.get(player.getUniqueId()).setPermission(
				"SerenityPlugins.starter", true);

	}

	@EventHandler
	public void onPLayerJoin(PlayerJoinEvent event) {
		if (readyToLogInAsServer) {
			if (event.getPlayer().isOp()) {
				event.getPlayer().setSleepingIgnored(true);
				invisiblePlayers.add(event.getPlayer());

				//I'm the invisible man!
				for (Player p : Bukkit.getOnlinePlayers()) {
					//I'm the invisible man!
					hideInvisiblePlayers(p);
				}

				event.getPlayer().setGameMode(GameMode.CREATIVE);
				event.getPlayer().setDisplayName("[Server]");
				event.setJoinMessage(null);
			}
		}

		//Incredible how you can
		//see right through me!
		hideInvisiblePlayers(event.getPlayer());

		World world = event.getPlayer().getWorld();
		Location location = event.getPlayer().getLocation();

		if (!event.getPlayer().isOp()) {
			for (int i = 0; i < 5; i++) {
				doRandomFirework(world, location);
			}
		}

		for (Mailbox mb : mailBoxes) {
			if (mb.getName().equals(event.getPlayer().getName())) {
				Chest mbChest = (Chest) mb.getLocation().getBlock().getState();

				ItemStack[] mbItems = mbChest.getInventory().getContents();

				for (ItemStack is : mbItems) {
					if (is != null) {
						String msg = getTranslationLanguage(event.getPlayer(),
								stringKeys.MAILHASMAIL.toString());
						event.getPlayer().sendMessage(msg);
						// event.getPlayer().sendMessage("�2You have mail!");
						return;
					}
				}
			}
		}

		return;
	}

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		attachments.remove(event.getPlayer().getUniqueId());

		if (event.getPlayer().isOp()) {
			invisiblePlayers.remove(event.getPlayer());
			flamingPlayers.remove(event.getPlayer());
			event.setQuitMessage(null);
		}
	}

	@EventHandler
	public void onPlayerChatEvent(AsyncPlayerChatEvent event) {
		if (!isAfk(event.getPlayer())) {
			playerLocations.remove(event.getPlayer().getDisplayName());
		} else {
			unAfk(event.getPlayer());
		}

		if (event.getMessage().toUpperCase().equals(Secrets.Secret.MAGICPHRASE)) {
			if (event.getPlayer().getWorld().getName().equals("world")) {
				if (event.getPlayer().getLocation()
						.distance(CENTERBLOCKSINFINALDUNGEON) < 25) {

					if (podrickCfg.getConfig().getBoolean(
							event.getPlayer().getDisplayName()
									+ ".ReadyToFight", false)
							&& !podrickCfg.getConfig().getBoolean(
									event.getPlayer().getDisplayName()
											+ ".Finished", false)) {
						event.setMessage("�k" + event.getMessage());
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
								"server startbattle");
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

		for (int i = 0; i < deafPlayers.size(); i++) {
			if (!event.getPlayer().equals(deafPlayers.get(i))) {
				event.getRecipients().remove(deafPlayers.get(i));
			}
		}

		if (mutedNames.contains(event.getPlayer().getDisplayName())) {
			event.setCancelled(true);
			return;
		}

		event.setFormat("<" + getChatColor(event.getPlayer()) + "%s�r> %s");

		//todo ... make this less shitty
		if (localChatters.contains(event.getPlayer())) {
			event.setFormat("  �7�o(%1s) %2$s");
			event.setMessage("�o" + event.getMessage());
			event.getRecipients().clear();
			double x = event.getPlayer().getLocation().getX();
			double z = event.getPlayer().getLocation().getZ();
			event.getRecipients().add(event.getPlayer());

			for (Iterator<? extends Player> i = Bukkit.getServer()
					.getOnlinePlayers().iterator(); i.hasNext();) {
				Player p = i.next();

				if (Math.abs(p.getLocation().getX() - x) < 100) {
					if (Math.abs(p.getLocation().getZ() - z) < 100) {
						if (event.getPlayer().getLocation().getWorld()
								.equals(p.getLocation().getWorld())) {
							event.getRecipients().add(p);
						}
					}
				}
			}
		}

		for (int i = 0; i < ignorers.size(); i++) {
			if (ignorers.get(i).ignoreList.contains(event.getPlayer()
					.getDisplayName())) {
				event.getRecipients().remove(ignorers.get(i).player);
			}
		}

		if (deafPlayers.contains(event.getPlayer())) {
			if (!localChatters.contains(event.getPlayer())) {
				/*
				 * event.getPlayer() .sendMessage(
				 * "�cYou have global chat disabled!  Type �3/chat global �cto turn on global chat!  Or type �3/chat local �cto chat locally!"
				 * );
				 */

				String msg = getTranslationLanguage(event.getPlayer(),
						stringKeys.CHATTRIEDTOCHATWITHGLOBALCHATDISABLED
								.toString());
				event.getPlayer().sendMessage(msg);

				event.setCancelled(true);
			}
		}

		event.setFormat("<" + getChatColor(event.getPlayer()) + "%s�r> %s");

		//i am purple
		if (event.getPlayer().getDisplayName().contains("[Server]")) {
			event.setFormat("�d" + "[Server] " + "%2$s");
		}

		Random rand = new Random();

		if (rand.nextInt(1000) == 0 && event.getMessage().length() > 10) {
			int r = rand.nextInt(3);
			String s = "";
			switch (r) {
			case 0:
				for (int i = 0; i < event.getMessage().length(); i++) {
					ChatColor col = allChatColors.get(i % 12);
					s += col;
					s += event.getMessage().charAt(i);
				}
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
		event.setMessage(getChatColor(event.getPlayer()) + event.getMessage());
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

		meta.setTitle(Secrets.Secret.JOURNAL1TITLE);
		meta.setAuthor(Secrets.Secret.JOURNALAUTHOR);

		String bookText = podrickCfg.getConfig().getString("Journal1");
		String hint = "";
		int type = podrickCfg.getConfig().getInt(
				p.getDisplayName() + ".EnkisFood");

		switch (type) {
		case 0:
			hint = Secrets.Secret.JOURNAL1HINT0;
			break;
		case 1:
			hint = Secrets.Secret.JOURNAL1HINT1;
			break;
		case 2:
			hint = Secrets.Secret.JOURNAL1HINT2;
			break;
		case 3:
			hint = Secrets.Secret.JOURNAL1HINT3;
			break;
		case 4:
			hint = Secrets.Secret.JOURNAL1HINT4;
			break;
		case 5:
			hint = Secrets.Secret.JOURNAL1HINT5;
			break;
		case 6:
			hint = Secrets.Secret.JOURNAL1HINT6;
			break;
		case 7:
			hint = Secrets.Secret.JOURNAL1HINT7;
			break;
		}

		bookText.replace("%", hint);
		bookText.replace('^', '�');

		List<String> pages = new ArrayList<String>();
		int l = 0;
		int lineCount = 0;
		String page = "";
		for (int i = 0; i < bookText.length(); i++) {
			if (bookText.charAt(i) == '%') {
				page += hint;
			} else if (bookText.charAt(i) == '^') {
				page += '�';
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

	private String getChatColor(Player player) {
		if (chatcolorCfg.getConfig().getString(
				"ChatColor." + player.getUniqueId().toString()) != null) {
			return chatcolorCfg.getConfig().getString(
					"ChatColor." + player.getUniqueId().toString());
		}
		return "";
	}

	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent event) {
		if (!event.getPlayer().isOp()) {
			event.setJoinMessage(getChatColor(event.getPlayer())
					+ event.getJoinMessage().substring(2));
		}
	}

	@EventHandler
	public void onPlayerQuitEvent(PlayerQuitEvent event) {
		if (!event.getPlayer().isOp()) {
			event.setQuitMessage(getChatColor(event.getPlayer())
					+ event.getQuitMessage().substring(2));
		}
	}

	@EventHandler
	public void onPlayerFirstTime(PlayerJoinEvent event) {
		if (!event.getPlayer().hasPlayedBefore()) {
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (!event.getPlayer().equals(p)) {
					if (getPlayerMinutes(p.getDisplayName()) > 720) {
						// p.sendMessage("�b" + "It's " + "�e"
						// + event.getPlayer().getDisplayName() + "�b"
						// + "'s first time on the server!  " + "�c"
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
	}

	@EventHandler
	public void onSecretVillagerDamage(EntityDamageEvent event) {
		if (event.getEntity().getCustomName() != null) {
			if (event.getEntity().getCustomName().contains("�6")
					|| event.getEntity().getCustomName().contains("�d")) {
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
					// "�cYou cannot make a mailbox connected to a chest!");
					event.getPlayer().sendMessage(
							getTranslationLanguage(event.getPlayer(),
									stringKeys.MAILTRIEDTOEXPANDMAILBOX
											.toString()));
					event.setCancelled(true);
					return;
				}

				if (!mailablePlayers.contains(event.getPlayer()
						.getDisplayName())) {
					String path = "Mailboxes."
							+ event.getPlayer().getDisplayName();
					int x = (int) chestLoc.getX();
					int y = (int) chestLoc.getY();
					int z = (int) chestLoc.getZ();
					String[] loc = { chestLoc.getWorld().getName(), "" + x,
							"" + y, "" + z };

					mailboxCfg.getConfig().set(path, loc);
					mailboxCfg.saveConfig();
					mailboxCfg.reloadConfig();

					mailablePlayers.add(event.getPlayer().getDisplayName());
					mailBoxes.add(new Mailbox(event.getPlayer()
							.getDisplayName(), chestLoc));
					event.getPlayer().sendMessage(
							getTranslationLanguage(event.getPlayer(),
									stringKeys.MAILCREATEDAMAILBOX.toString()));
					// event.getPlayer().sendMessage("�2You made a mailbox!");
				} else {
					Mailbox mb = new Mailbox("Something went terribly wrong",
							chestLoc);
					for (int i = 0; i < mailBoxes.size(); i++) {
						if (mailBoxes.get(i).name.equals(event.getPlayer()
								.getDisplayName())) {
							mb = mailBoxes.get(i);
						}
					}
					Location l = mb.getLocation();

					String msg = getTranslationLanguage(event.getPlayer(),
							stringKeys.MAILALREADYHASAMAILBOX.toString());

					event.getPlayer().sendMessage(
							String.format(msg, mb.getLocation().getWorld()
									.getName(), (int) l.getX(), (int) l.getY(),
									(int) l.getZ()));
					/*
					 * event.getPlayer().sendMessage(
					 * "�cYou already have a mailbox in the �3" +
					 * mb.getLocation().getWorld().getName() + "�c at: " +
					 * "\n�3X:�2 " + l.getX() + "\n�3Y:�2 " + l.getY() +
					 * "\n�3Z:�2 " + l.getZ() +
					 * "\n�cPlease destroy that one first!");
					 */
					event.setCancelled(true);
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
				Mailbox mb = new Mailbox(event.getPlayer().getDisplayName(),
						event.getClickedBlock().getLocation());
				for (int i = 0; i < mailBoxes.size(); i++) {
					if (mb.getLocation().equals(mailBoxes.get(i).location)) {
						if (PUBLIC_MAILBOXES.contains(mailBoxes.get(i)
								.getName())) {

							String msg = getTranslationLanguage(
									event.getPlayer(),
									stringKeys.MAILOPENEDAPUBLICMAILBOX
											.toString());
							event.getPlayer().sendMessage(
									String.format(msg, mailBoxes.get(i)
											.getName()));

							return;
						}
						if (!mailBoxes.get(i).getName()
								.equals(event.getPlayer().getDisplayName())) {

							String msg = getTranslationLanguage(
									event.getPlayer(),
									stringKeys.MAILINTERRACTEDWITHAMAILBOX
											.toString());
							event.getPlayer().sendMessage(
									String.format(msg, mailBoxes.get(i)
											.getName()));

							event.setCancelled(true);
						}
					}
				}
			}
		}
	}
	
	//HORSE BETTING CODE... might implement in future

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
	 * .getState(); s.setLine(1, "�c[Empty]"); s.update(); betters[0] = null;
	 * event.getPlayer() .sendMessage(
	 * "�3You are no longer betting on this horse"); } else { event.getPlayer()
	 * .sendMessage( "�cSorry... someone is already betting on that horse"); } }
	 * else { if (!isBetting(event.getPlayer() .getDisplayName())) { betters[0]
	 * = event.getPlayer() .getDisplayName(); Sign s = (Sign)
	 * event.getClickedBlock() .getState(); s.setLine(1, "�1" +
	 * event.getPlayer() .getDisplayName()); event.getPlayer() .sendMessage(
	 * "�3You are now betting on this horse"); s.update(); } else {
	 * event.getPlayer() .sendMessage(
	 * "�cYou are already betting on a horse!  Right click that sign to stop betting on it!"
	 * ); } } break;
	 * 
	 * case -517: if (betters[1] != null) { if
	 * (betters[1].equals(event.getPlayer() .getDisplayName())) { Sign s =
	 * (Sign) event.getClickedBlock() .getState(); s.setLine(1, "�c[Empty]");
	 * s.update(); betters[1] = null; event.getPlayer() .sendMessage(
	 * "�3You are no longer betting on this horse"); } else { event.getPlayer()
	 * .sendMessage( "�cSorry... someone is already betting on that horse"); } }
	 * else { if (!isBetting(event.getPlayer() .getDisplayName())) { betters[1]
	 * = event.getPlayer() .getDisplayName(); Sign s = (Sign)
	 * event.getClickedBlock() .getState(); s.setLine(1, "�1" +
	 * event.getPlayer() .getDisplayName()); event.getPlayer() .sendMessage(
	 * "�3You are now betting on this horse"); s.update(); } else {
	 * event.getPlayer() .sendMessage(
	 * "�cYou are already betting on a horse!  Right click that sign to stop betting on it!"
	 * ); } } break;
	 * 
	 * case -516: if (betters[2] != null) { if
	 * (betters[2].equals(event.getPlayer() .getDisplayName())) { Sign s =
	 * (Sign) event.getClickedBlock() .getState(); s.setLine(1, "�c[Empty]");
	 * s.update(); betters[2] = null; event.getPlayer() .sendMessage(
	 * "�3You are no longer betting on this horse"); } else { event.getPlayer()
	 * .sendMessage( "�cSorry... someone is already betting on that horse"); } }
	 * else { if (!isBetting(event.getPlayer() .getDisplayName())) { betters[2]
	 * = event.getPlayer() .getDisplayName(); Sign s = (Sign)
	 * event.getClickedBlock() .getState(); s.setLine(1, "�1" +
	 * event.getPlayer() .getDisplayName()); event.getPlayer() .sendMessage(
	 * "�3You are now betting on this horse"); s.update(); } else {
	 * event.getPlayer() .sendMessage(
	 * "�cYou are already betting on a horse!  Right click that sign to stop betting on it!"
	 * ); } } break; } } } } } } }
	 */

	//Taken from https://bukkit.org/threads/faked-a-location-block-getnearbyentities.117428/
	//Thank you nathanaelps :)
	public List<Entity> getNearbyEntities(Location loc, double x, double y,
			double z) {
		FallingBlock ent = loc.getWorld().spawnFallingBlock(loc, 132, (byte) 0);
		List<Entity> out = ent.getNearbyEntities(x, y, z);
		ent.remove();
		return out;
	}

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
	 * player.sendMessage("�cYou must be betting on this horse to change your bet"
	 * ); event.setCancelled(true); return; } } case -517: if (betters[1] !=
	 * null) { if (!betters[1].equals(player .getDisplayName())) {
	 * player.sendMessage
	 * ("�cYou must be betting on this horse to change your bet");
	 * event.setCancelled(true); return; } } case -516: if (betters[2] != null)
	 * { if (!betters[2].equals(player .getDisplayName())) {
	 * player.sendMessage("�cYou must be betting on this horse to change your bet"
	 * ); event.setCancelled(true); return; } } } } } } } } }
	 * 
	 * private boolean isBetting(String displayName) { for (int i = 0; i <
	 * betters.length; i++) { if (betters[i] != null) { if
	 * (betters[i].equals(displayName)) return true; } } return false; }
	 */
	@EventHandler
	public void onPlayerInteractWaterActivate(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (event.getClickedBlock().getType() == Material.GOLD_BLOCK) {

				Block nethBlock1 = Secrets.Secret.NETHBLOCK1.getBlock();
				Block nethBlock2 = Secrets.Secret.NETHBLOCK2.getBlock();
				Location someonesHouse = Secrets.Secret.OVWORLDLOC;
				Block doneBlock = Secrets.Secret.DONEBLOCK.getBlock();
				if (nethBlock2.equals(event.getClickedBlock())
						|| nethBlock1.equals(event.getClickedBlock())) {
					event.getPlayer().teleport(someonesHouse);
					return;
				}

				if (doneBlock.equals(event.getClickedBlock())) {
					if (podrickCfg.getConfig().getBoolean(
							event.getPlayer().getDisplayName() + ".Finished",
							false)) {

						sendSimulatedPrivateMessage(
								event.getPlayer(),
								"�r�d[Server]",
								Secrets.Secret.CONGRATS
								);
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
						im.setDisplayName(Secrets.Secret.SECRETWCDNAME);

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

						meta.setTitle(Secrets.Secret.MYSTERYBOOK2TITLE);
						meta.setAuthor(Secrets.Secret.MYSTERYBOOKAUTHOR);
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
									page += '�';
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

	@EventHandler
	public void partyInteract(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {

			if (event.getClickedBlock().getY() == 131
					&& event.getClickedBlock().getType() == Material.STONE_BUTTON
					&& event.getPlayer().getLocation().getWorld().getName()
							.equals("world_nether")) {
				isAParty = !isAParty;
				if (!isAParty) {
					event.getPlayer().sendMessage("�6Stopping the party :(");
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
									"�6It's party time!  Right click a wall!  To go home, right click the floor.");
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
					String result = "�3Current time: �b" + text;
					event.getPlayer().sendMessage(result);
				}
			}
		}

		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (event.getClickedBlock().getType() == Material.STONE_BUTTON) {
				if (event.getClickedBlock().getX() == -1314
						&& event.getClickedBlock().getY() == 64
						&& event.getClickedBlock().getZ() == -650) {
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
							"ncp exempt " + event.getPlayer().getDisplayName()
									+ " MOVING_SURVIVALFLY");
				}
			}

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
						event.getPlayer().sendMessage("�6Go!");

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
					String result = "�3Your time: �b" + text;

					event.getPlayer().sendMessage(result);

					if (daleCfg.getConfig().getLong(
							event.getPlayer().getDisplayName(), 900000000) > time) {
						daleCfg.getConfig().set(
								event.getPlayer().getDisplayName(), time);
						event.getPlayer()
								.sendMessage(
										"�dX�5X�1X�9X �6�lNEW PERSONAL BEST!�r �9X�1X�5X�dX");
						for (int i = 0; i < 10; i++) {
							safelySpawnExperienceOrb(event.getPlayer(), 250, 5);
						}

						daleCfg.saveConfig();
						daleCfg.reloadConfig();
					}

					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
							"ncp unexempt "
									+ event.getPlayer().getDisplayName()
									+ " MOVING_SURVIVALFLY");
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
					results.push(" �b"
							+ new SimpleDateFormat("mm:ss.SSS")
									.format(new Date(key)) + " �3" + m.get(key));
				}

				int count = results.size();
				String result = "";

				while (!results.isEmpty()) {
					result += "�6#" + count + results.pop() + "\n";
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
			for (int i = 0; i < mailBoxes.size(); i++) {
				if (mailBoxes.get(i).location.equals(event.getBlock()
						.getLocation())) {
					String path = "Mailboxes."
							+ event.getPlayer().getDisplayName().toString();
					mailboxCfg.getConfig().set(path, null);
					// mailboxCfg.getConfig().options().copyDefaults();

					mailablePlayers.remove(mailBoxes.get(i).name);

					mailBoxes.remove(i);
					mailboxCfg.saveConfig();
					mailboxCfg.reloadConfig();
					/*
					 * event.getPlayer().sendMessage(
					 * "�2You destroyed your mailbox!");
					 */

					String msg = getTranslationLanguage(event.getPlayer(),
							stringKeys.MAILDESTROYEDMAILBOX.toString());
					event.getPlayer().sendMessage(msg);

				}
			}
		}
	}

	@EventHandler
	public void onMailboxBreak(PlayerInteractEvent event) {
		if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
			if (event.getClickedBlock().getType().equals(Material.CHEST)
					|| event.getClickedBlock().getType()
							.equals(Material.TRAPPED_CHEST)) {
				Mailbox mb = new Mailbox(event.getPlayer().getDisplayName(),
						event.getClickedBlock().getLocation());
				for (int i = 0; i < mailBoxes.size(); i++) {
					if (mb.getLocation().equals(mailBoxes.get(i).location)) {
						if (PUBLIC_MAILBOXES.contains(mailBoxes.get(i)
								.getName())) {
							/*
							 * event.getPlayer() .sendMessage(
							 * "�2Sorry, you cannot break a public mailbox!");
							 */

							String msg = getTranslationLanguage(
									event.getPlayer(),
									stringKeys.MAILTRIEDTOBREAKPUBLICMAILBOX
											.toString());
							event.getPlayer().sendMessage(msg);

							event.setCancelled(true);
							return;
						}
						if (!mailBoxes.get(i).getName()
								.equals(event.getPlayer().getDisplayName())) {

							String msg = getTranslationLanguage(
									event.getPlayer(),
									stringKeys.MAILINTERRACTEDWITHAMAILBOX
											.toString());
							event.getPlayer().sendMessage(
									String.format(msg, mailBoxes.get(i)
											.getName()));
							/*
							 * event.getPlayer().sendMessage(
							 * "�cSorry, that is �2" +
							 * mailBoxes.get(i).getName() +
							 * "�c's private mailbox!");
							 */
							event.setCancelled(true);
						}
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
				p.setSleepingIgnored(p.isOp());
			}

			for (Player ap : afkPlayers) {
				ap.setSleepingIgnored(true);
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
				p.setSleepingIgnored(p.isOp());
			}

			for (Player ap : afkPlayers) {
				ap.setSleepingIgnored(true);
			}

			Bukkit.getServer().broadcastMessage(
					"�cNobody is in bed anymore! Vote cancelled");
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
								 * p.sendMessage("�c" + "Someone is in a bed. "
								 * + "�e" + "Want to go to day? " + "�c" +
								 * "Type " + "�e" + "/ok " + "�c" +
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
		int onlineCount = Bukkit.getServer().getOnlinePlayers().size();
		int sleepingIgnored = 0;

		List<String> sleepers = new ArrayList<String>();

		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			if (p.isSleeping() || p.isSleepingIgnored()) {
				if (p.isSleeping()) {
					sleepers.add(p.getDisplayName());
				}
				sleepingIgnored++;
			}
		}

		if (!sleepers.isEmpty()) {
			if ((double) sleepingIgnored / onlineCount > 0.5) {
				Bukkit.getLogger().info(
						"Vote passed: setting all to sleep ignored");
				for (Player p : Bukkit.getServer().getOnlinePlayers()) {
					p.setSleepingIgnored(true);
					if (p.isOp()) {
						p.setGameMode(GameMode.CREATIVE);
					}
				}
			}
		}
	}


	@EventHandler
	public void onPlayerTorchClick(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		/*
		 * // remove this!!! if (p.getItemInHand().getType() ==
		 * Material.WOOD_SPADE) { event.setCancelled(true); Block target =
		 * event.getPlayer().getTargetBlock((Set<Material>) null, MAX_DISTANCE);
		 * Location location = target.getLocation();
		 * 
		 * Location l = location.getWorld().getHighestBlockAt(location)
		 * .getLocation();
		 * 
		 * return; } //
		 */

		if (p.getItemInHand().getType() == Material.GOLD_AXE
				&& p.getItemInHand().getItemMeta().getDisplayName() != null
				&& event.getAction() == Action.RIGHT_CLICK_AIR
				&& p.getItemInHand().getItemMeta().getDisplayName()
						.equals(Secrets.Secret.SECRETITEM1NAME)) {
			event.setCancelled(true);
			World world = event.getPlayer().getWorld();
			Block target = event.getPlayer().getTargetBlock(
					(Set<Material>) null, MAX_DISTANCE);
			Location location = target.getLocation();
			doRandomFirework(world, location);
			return;
		}
		
		//IF YOU ARE READING THE SOURCE CODE DISREGARD THE REST OF THIS METHOD!

		if (p.getItemInHand().getType() == Material.WOOD_HOE) {
			if (!p.isOp()) {
				return;
			}
			event.setCancelled(true);
			Block target = event.getPlayer().getTargetBlock(
					(Set<Material>) null, MAX_DISTANCE);
			Location location = target.getLocation();
			// location.getWorld().createExplosion(location, 10, false);
			/*
			 * location.getWorld().getBlockAt(location).breakNaturally();
			 * 
			 * location.setX(location.getX() + 1);
			 * location.getWorld().getBlockAt(location).breakNaturally();
			 * location.setZ(location.getZ() + 1);
			 * location.getWorld().getBlockAt(location).breakNaturally();
			 * location.setZ(location.getZ() - 2);
			 * location.getWorld().getBlockAt(location).breakNaturally();
			 * 
			 * location = target.getLocation(); location.setZ(location.getZ() -
			 * 1); location.getWorld().getBlockAt(location).breakNaturally();
			 * location.setZ(location.getZ() + 2);
			 * location.getWorld().getBlockAt(location).breakNaturally();
			 * 
			 * location = target.getLocation(); location.setX(location.getX() -
			 * 1); location.getWorld().getBlockAt(location).breakNaturally();
			 * location.setZ(location.getZ() + 1);
			 * location.getWorld().getBlockAt(location).breakNaturally();
			 * location.setZ(location.getZ() - 2);
			 * location.getWorld().getBlockAt(location).breakNaturally();
			 */
			//

			location.getWorld().getBlockAt(location)
					.setType(Material.SMOOTH_BRICK);

			location.setX(location.getX() + 1);
			location.getWorld().getBlockAt(location)
					.setType(Material.SMOOTH_BRICK);
			location.setZ(location.getZ() + 1);
			location.getWorld().getBlockAt(location)
					.setType(Material.SMOOTH_BRICK);
			location.setZ(location.getZ() - 2);
			location.getWorld().getBlockAt(location)
					.setType(Material.SMOOTH_BRICK);

			location = target.getLocation();
			location.setZ(location.getZ() - 1);
			location.getWorld().getBlockAt(location)
					.setType(Material.SMOOTH_BRICK);
			location.setZ(location.getZ() + 2);
			location.getWorld().getBlockAt(location)
					.setType(Material.SMOOTH_BRICK);

			location = target.getLocation();
			location.setX(location.getX() - 1);
			location.getWorld().getBlockAt(location)
					.setType(Material.SMOOTH_BRICK);
			location.setZ(location.getZ() + 1);
			location.getWorld().getBlockAt(location)
					.setType(Material.SMOOTH_BRICK);
			location.setZ(location.getZ() - 2);
			location.getWorld().getBlockAt(location)
					.setType(Material.SMOOTH_BRICK);

			/*
			 * for(int i = 0; i < 35; i++){ location = new
			 * Location(location.getWorld(), location.getX(), i,
			 * location.getZ());
			 * if(location.getWorld().getBlockAt(location).getType
			 * ()!=Material.AIR){
			 * location.getBlock().setType(Material.SMOOTH_BRICK); } }
			 */
			return;
		}

		if (p.getItemInHand().getType() == Material.STONE_HOE) {
			if (!p.isOp()) {
				return;
			}
			event.setCancelled(true);
			Block target = event.getPlayer().getTargetBlock(
					(Set<Material>) null, MAX_DISTANCE);
			Location location = target.getLocation();
			// location.getWorld().createExplosion(location, 10, false);

			location.getWorld().getBlockAt(location).breakNaturally();

			location.setX(location.getX() + 1);
			location.getWorld().getBlockAt(location).breakNaturally();
			location.setZ(location.getZ() + 1);
			location.getWorld().getBlockAt(location).breakNaturally();
			location.setZ(location.getZ() - 2);
			location.getWorld().getBlockAt(location).breakNaturally();

			location = target.getLocation();
			location.setZ(location.getZ() - 1);
			location.getWorld().getBlockAt(location).breakNaturally();
			location.setZ(location.getZ() + 2);
			location.getWorld().getBlockAt(location).breakNaturally();

			location = target.getLocation();
			location.setX(location.getX() - 1);
			location.getWorld().getBlockAt(location).breakNaturally();
			location.setZ(location.getZ() + 1);
			location.getWorld().getBlockAt(location).breakNaturally();
			location.setZ(location.getZ() - 2);
			location.getWorld().getBlockAt(location).breakNaturally();

			//
			/*
			 * location.getWorld().getBlockAt(location).setType(Material.
			 * SMOOTH_BRICK);
			 * 
			 * location.setX(location.getX() + 1);
			 * location.getWorld().getBlockAt
			 * (location).setType(Material.SMOOTH_BRICK);
			 * location.setZ(location.getZ() + 1);
			 * location.getWorld().getBlockAt
			 * (location).setType(Material.SMOOTH_BRICK);
			 * location.setZ(location.getZ() - 2);
			 * location.getWorld().getBlockAt
			 * (location).setType(Material.SMOOTH_BRICK);
			 * 
			 * location = target.getLocation(); location.setZ(location.getZ() -
			 * 1); location.getWorld().getBlockAt(location).setType(Material.
			 * SMOOTH_BRICK); location.setZ(location.getZ() + 2);
			 * location.getWorld
			 * ().getBlockAt(location).setType(Material.SMOOTH_BRICK);
			 * 
			 * location = target.getLocation(); location.setX(location.getX() -
			 * 1); location.getWorld().getBlockAt(location).setType(Material.
			 * SMOOTH_BRICK); location.setZ(location.getZ() + 1);
			 * location.getWorld
			 * ().getBlockAt(location).setType(Material.SMOOTH_BRICK);
			 * location.setZ(location.getZ() - 2);
			 * location.getWorld().getBlockAt
			 * (location).setType(Material.SMOOTH_BRICK);
			 */
			/*
			 * for(int i = 0; i < 35; i++){ location = new
			 * Location(location.getWorld(), location.getX(), i,
			 * location.getZ());
			 * if(location.getWorld().getBlockAt(location).getType
			 * ()!=Material.AIR){
			 * location.getBlock().setType(Material.SMOOTH_BRICK); } }
			 */
			return;
		}

		if (p.getItemInHand().getType() == Material.IRON_HOE) {
			if (!p.isOp()) {
				return;
			}
			event.setCancelled(true);
			Block target = event.getPlayer().getTargetBlock(
					(Set<Material>) null, MAX_DISTANCE);
			Location location = target.getLocation();
			for (int i = 7; i < 45; i++) {
				location = new Location(location.getWorld(), location.getX(),
						i, location.getZ());
				if (location.getWorld().getBlockAt(location).getType() != Material.AIR) {
					location.getBlock().setType(Material.AIR);
				}
			}
			return;
		}

		if (p.getItemInHand().getType() == Material.GOLD_HOE) {
			if (!p.isOp()) {
				return;
			}
			event.setCancelled(true);
			Block target = event.getPlayer().getTargetBlock(
					(Set<Material>) null, MAX_DISTANCE);
			Location location = target.getLocation();
			for (int i = 5; i < 45; i++) {
				location = new Location(location.getWorld(), location.getX(),
						i, location.getZ());
				if (i % 10 == 0) {
					location.getBlock().setType(Material.GLOWSTONE);
				} else {
					location.getBlock().setType(Material.SMOOTH_BRICK);
				}
			}
			return;
		}

		if (p.getItemInHand().getType() == Material.DIAMOND_HOE) {
			if (!p.isOp()) {
				return;
			}

			event.setCancelled(true);
			Block target = event.getPlayer().getTargetBlock(
					(Set<Material>) null, MAX_DISTANCE);
			Location location = target.getLocation();
			location.getWorld().strikeLightningEffect(location);
			return;

		}
	}

	//lol disregard this too... it was dumb
	@EventHandler
	public void onWaterGun(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		if (p.getItemInHand().getType() == Material.BEDROCK
				&& (event.getAction() == Action.RIGHT_CLICK_AIR || event
						.getAction() == Action.RIGHT_CLICK_BLOCK)) {

			final Player pf = p;
			Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
				@Override
				public void run() {
					ParticleEffect.LAVA.display(
							pf.getLocation().getDirection(), .3F,
							pf.getEyeLocation(), 25);

				}
			});
			event.setCancelled(true);

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
						// event.getPlayer().sendMessage("�3You own that block");

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
						 * event.getPlayer() .sendMessage( "�2" + pa.owner +
						 * "�3 owns that block but you have permission for it");
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
					 * "�cYou do not have permission for that block");
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
			// event.getPlayer().sendMessage("�4Nobody owns that block");

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

		final Location l = location;
		final boolean b1f = b1;
		final boolean b2f = b2;
		final List<Color> colors1f = colors1;
		final List<Color> colors2f = colors2;
		final int rf = r;

		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {

			@Override
			public void run() {
				Firework fw = (Firework) l.getWorld().spawn(l, Firework.class);

				FireworkEffect effect = FireworkEffect.builder().trail(b1f)
						.flicker(b2f).withColor(colors1f).withFade(colors2f)
						.with(getRandType()).build();

				FireworkMeta fwm = fw.getFireworkMeta();
				fwm.setPower(rf);

				fwm.clearEffects();
				fwm.addEffect(effect);

				fw.setFireworkMeta(fwm);
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

		else if (commandLabel.equalsIgnoreCase("chat")) {
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

		return false;
	}

	private boolean links(CommandSender sender, String[] arg3) {
		String s = "";
		for (String key : linksCfg.getConfig().getKeys(false)) {
			s += "�6" + key + ": �e" + linksCfg.getConfig().getString(key)
					+ "\n";
		}
		sender.sendMessage(s);
		return true;
	}

	private boolean text(CommandSender sender, String[] arg3) {

		if (sender.hasPermission("SerenityPlugins.text")) {
			if (arg3.length < 1) {
				return false;
			}
			if (textCooldown.containsKey(sender.getName())) {
				Long now = System.currentTimeMillis();
				Long then = textCooldown.get(sender.getName());
				if (now - then < 600000) {
					sender.sendMessage("�cYou must wait to text Hal again");
					return true;
				}
			}
			String s = "";
			for (int i = 0; i < arg3.length; i++) {
				s += arg3[i] + " ";
			}

			final String str = s;
			final String sendName = sender.getName();
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
				}
			});

			sender.sendMessage("�6You texted Hal's cell phone! He should receive your message soon!");
			textCooldown.put(sendName, System.currentTimeMillis());
			return true;
		} else {
			sender.sendMessage("�cYou can only text Hal after you've played for 12 hours");
			return true;
		}
	}

	private boolean msg(CommandSender sender, String[] arg3) {
		if (arg3.length > 0) {
			if (arg3[0].equalsIgnoreCase(Secrets.Secret.SOMEONESNAME)) {

				String s = "�r";

				if (sender instanceof Player) {
					Player p = (Player) sender;
					s += getChatColor(p);
				}

				for (int i = 1; i < arg3.length; i++) {
					s += arg3[i] + " ";
				}

				sender.sendMessage("�oTo �6�o�l" + Secrets.Secret.SOMEONESNAMEPROPER + ": " + s);
				if (sender instanceof Player) {
					Player p = (Player) sender;
					if (arg3.length > 1) {
						if (arg3[1].equalsIgnoreCase("reset")) {
							podrickCfg.getConfig()
									.set(p.getDisplayName(), null);
							podrickCfg.saveConfig();
							podrickCfg.reloadConfig();
							p.sendMessage("�cYou reset the quest!  /msg " + Secrets.Secret.SOMEONESNAMEPROPER + " again to start over");
							return true;
						}
					}
					askSomeone(p);
					return true;
				}
			}
		}

		if (arg3.length > 1) {
			if (arg3[0].equals("r")) {
				if (playerRecentMessages.containsKey(sender.getName())) {
					arg3[0] = playerRecentMessages.get(sender.getName());
					String s = "";
					for (int i = 0; i < arg3.length; i++) {
						s += arg3[i] + " ";
					}
					Bukkit.dispatchCommand(sender, "msg " + s);
					return true;
				}
				sender.sendMessage("�cNobody has sent you a message recently!");
				return true;
			} else {
				return sendPrivateMessage(sender, arg3);
			}
		}
		return false;
	}

	private void askSomeone(Player p) {
		for (Entity e : p.getNearbyEntities(15, 10, 15)) {
			if (e.getCustomName() != null) {
				if (e.getCustomName().equals(Secrets.Secret.SOMEONESNAMEWITHCOLOR)) {

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
												Secrets.Secret.SOMEONESNAMEPROPER
												,
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
												Secrets.Secret.SOMEONESNAMEPROPER,
												Secrets.Secret.IFONLY);
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
										sendSimulatedPrivateMessage(
												player,
												Secrets.Secret.SOMEONESNAMEPROPER,
												Secrets.Secret.DOINGRESEARCH);
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
										sendSimulatedPrivateMessage(
												player,
												Secrets.Secret.SOMEONESNAMEPROPER,
												Secrets.Secret.FOUNDWCD);
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
												Secrets.Secret.SOMEONESNAMEPROPER,
												Secrets.Secret.NOTHINGTOREPORT);
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
						final Location l = Secrets.Secret.TOSHLOC;
						Bukkit.getScheduler().scheduleSyncDelayedTask(this,
								new Runnable() {
									@Override
									public void run() {
										sendSimulatedPrivateMessage(player,
												Secrets.Secret.SOMEONESNAMEPROPER, "Woah!");
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
												Secrets.Secret.SOMEONESNAMEPROPER,
												Secrets.Secret.LATESTUPDS);
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
												Secrets.Secret.SOMEONESNAMEPROPER, "No news yet...");
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
												Secrets.Secret.SOMEONESNAMEPROPER,
												Secrets.Secret.WHATHAVEDONE);
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
												Secrets.Secret.SOMEONESNAMEPROPER,
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
				sendSimulatedPrivateMessage(player, Secrets.Secret.SOMEONESNAMEPROPER,
						"Get closer to me!");
			}
		}, 50L);

	}

	protected void giveJournal4(Player p) {
		ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
		BookMeta meta = (BookMeta) book.getItemMeta();

		meta.setTitle(Secrets.Secret.JOURNAL4TITLE);
		meta.setAuthor(Secrets.Secret.SOMEONESNAMEWITHCOLOR);

		String bookText = podrickCfg.getConfig().getString("Journal4");
		int x = podrickCfg.getConfig().getInt(p.getDisplayName() + ".ZValue");

		bookText.replace("%", p.getDisplayName());
		bookText.replace("+", x + "");
		bookText.replace('^', '�');

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
				page += '�';
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

	protected void giveJournal5(Player p) {
		ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
		BookMeta meta = (BookMeta) book.getItemMeta();

		meta.setTitle(Secrets.Secret.JOURNAL5TITLE);
		meta.setAuthor(Secrets.Secret.SOMEONESNAMEWITHCOLOR);

		String bookText = podrickCfg.getConfig().getString("Journal5");
		int x = podrickCfg.getConfig().getInt(p.getDisplayName() + ".ZValue");

		bookText.replace("%", p.getDisplayName());
		bookText.replace("+", x + "");
		bookText.replace('^', '�');

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
				page += '�';
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

	protected void checkForArtifact(Player p) {
		for (ItemStack is : p.getInventory().getContents()) {
			if (is != null) {
				if (is.hasItemMeta()) {
					if (is.getItemMeta().hasLore()) {
						if (is.getItemMeta().hasDisplayName()
								&& is.getItemMeta().getDisplayName()
										.equals(Secrets.Secret.TPA)) {
							int playerSeed = podrickCfg.getConfig().getInt(
									p.getDisplayName() + ".Seed", -1);
							int thisItemSeed = Integer.parseInt(is
									.getItemMeta().getLore().get(0));
							if (playerSeed == thisItemSeed) {
								sendSimulatedPrivateMessage(
										p,
										Secrets.Secret.SOMEONESNAME,
										Secrets.Secret.ATPAM);
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

		sendSimulatedPrivateMessage(p, Secrets.Secret.SOMEONESNAME,
				Secrets.Secret.HAVEDISC);
	}

	protected void giveJournal3(Player p) {
		ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
		BookMeta meta = (BookMeta) book.getItemMeta();

		meta.setTitle(Secrets.Secret.JOURNAL3TITLE);
		meta.setAuthor(Secrets.Secret.SOMEONESNAMEWITHCOLOR);

		String bookText = podrickCfg.getConfig().getString("Journal3");
		int x = podrickCfg.getConfig().getInt(p.getDisplayName() + ".YValue");

		bookText.replace("%", p.getDisplayName());
		bookText.replace("+", x + "");
		bookText.replace('^', '�');

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
				page += '�';
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
							p.sendMessage("�cSorry, you can't dismount in a protected area of which you don't have permission");
						}
					}
				}
			}
		}
	}

	protected void giveJournal2(Player p) {
		ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
		BookMeta meta = (BookMeta) book.getItemMeta();

		meta.setTitle(Secrets.Secret.JOURNAL2TITLE);
		meta.setAuthor(Secrets.Secret.SOMEONESNAMEWITHCOLOR);

		String bookText = podrickCfg.getConfig().getString("Journal2");
		int x = podrickCfg.getConfig().getInt(p.getDisplayName() + ".XValue");

		bookText.replace("%", p.getDisplayName());
		bookText.replace("+", x + "");
		bookText.replace('^', '�');

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
				page += '�';
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

		sendSimulatedPrivateMessage(p, Secrets.Secret.SOMEONESNAME,
				Secrets.Secret.HERESACOPY);

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
									.equals(Secrets.Secret.DECCRYST)) {
								int playerSeed = podrickCfg.getConfig().getInt(
										p.getDisplayName() + ".Seed", -1);
								int thisItemSeed = Integer.parseInt(is
										.getItemMeta().getLore().get(0));
								if (playerSeed == thisItemSeed) {
									sendSimulatedPrivateMessage(
											p,
											Secrets.Secret.SOMEONESNAME,
											Secrets.Secret.YESTHATSIT);
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

		sendSimulatedPrivateMessage(p, Secrets.Secret.SOMEONESNAME,
				"You found something??  Where?");
	}

	private void giveMysteryBook(Player p, String string) {
		ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
		BookMeta meta = (BookMeta) book.getItemMeta();

		meta.setTitle(Secrets.Secret.MYSTERYBOOK1TITLE);
		meta.setAuthor(Secrets.Secret.MYSTERYBOOKAUTHOR);
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

	private boolean sendPrivateMessage(CommandSender sender, String[] arg3) {
		if (arg3[0].equals("[Server]")) {
			String s = "";
			String senderName = "";
			String recipient = "[Server]";
			if (sender instanceof Player) {
				s += getChatColor((Player) sender);
				senderName = ((Player) sender).getDisplayName();
			} else {
				senderName = "�d[Server]";
			}

			for (int i = 1; i < arg3.length; i++) {
				s += arg3[i] + " ";
			}

			getLogger().info("�oFrom " + senderName + ": " + s);
			sender.sendMessage("�oTo " + recipient + ": " + s);

			if (playerRecentMessages.containsKey(recipient))
				playerRecentMessages.remove(recipient);
			playerRecentMessages.put(recipient, senderName);

			return true;
		}

		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.getName().toUpperCase().contains(arg3[0].toUpperCase())) {
				String s = "";
				String senderName = "";
				String recipient = p.getDisplayName();
				if (sender instanceof Player) {
					s += getChatColor((Player) sender);
					senderName = ((Player) sender).getDisplayName();
				} else {
					senderName = "[Server]";
				}

				for (int i = 1; i < arg3.length; i++) {
					s += arg3[i] + " ";
				}

				p.sendMessage("�oFrom " + senderName + ": " + s);
				sender.sendMessage("�oTo " + recipient + ": " + s);

				if (playerRecentMessages.containsKey(recipient))
					playerRecentMessages.remove(recipient);
				playerRecentMessages.put(recipient, senderName);

				return true;
			}
		}
		return false;
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
	 * "�cOnly available languages are English and Dutch... for now.  Go on the forums to help translate!"
	 * ); return true; }
	 */

	private boolean chat(CommandSender sender, String[] arg3) {
		if (sender.hasPermission("SerenityPlugins.chat")) {
			if (arg3.length == 1) {
				if (arg3[0].equals("tp")) {
					Location l = Bukkit.getPlayer(sender.getName())
							.getLocation();
					if (l.getWorld().getName().equalsIgnoreCase("world")) {
						if (Math.abs(l.getX()) < 50 && Math.abs(l.getZ()) < 50) {
							boolean ready = true;
							for (TeleportClicker tc : teleportClickers) {
								if (tc.name.equals(sender.getName())) {
									if (System.currentTimeMillis()
											- tc.lastClick < 1800000) {
										ready = false;
									}
								}
							}
							if (ready) {
								Random rand = new Random();
								Location teleLoc;

								boolean safe = false;
								do {
									teleLoc = new Location(
											Bukkit.getWorld("world"),
											(double) rand.nextInt(15000) - 7500,
											5.0,
											(double) rand.nextInt(15000) - 7500);

									if (teleLoc.getWorld()
											.getHighestBlockAt(teleLoc)
											.getRelative(BlockFace.DOWN)
											.getType() != Material.WATER
											&& teleLoc
													.getWorld()
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

								teleLoc.setY(teleLoc.getWorld()
										.getHighestBlockYAt(teleLoc) + 1);
								Bukkit.getPlayer(sender.getName()).teleport(
										teleLoc);

								for (int i = 0; i < teleportClickers.size(); i++) {
									if (teleportClickers.get(i).name
											.equals(sender.getName())) {
										teleportClickers.remove(i);
									}
								}

								teleportClickers
										.add(new TeleportClicker(sender
												.getName(), System
												.currentTimeMillis()));

								return true;
							} else {

								String msg = getTranslationLanguage(sender,
										stringKeys.RANDOMTPWAIT.toString());
								sender.sendMessage(msg);
								// sender.sendMessage("�cYou must wait to use that again!");
								return true;
							}
						}

						String msg = getTranslationLanguage(sender,
								stringKeys.RANDOMTPTOOFAR.toString());
						sender.sendMessage(msg);
						// sender.sendMessage("�cYou can only do this at spawn!");
						return true;
					} else {
						String msg = getTranslationLanguage(sender,
								stringKeys.RANDOMTPWRONGWORLD.toString());
						sender.sendMessage(msg);

						// sender.sendMessage("�cYou can only do this in the overworld!");
						return true;
					}
				}
				if (arg3[0].equals("wb")) {
					sender.sendMessage(/*
										 * "�bCollective Minutes �7(Since May 3, Noon Eastern US time): �3"
										 * +
										 * expansionCfg.getConfig().getInt("Minutes"
										 * ) + "\n
										 */"�bWorld border is now �d"
							+ (Bukkit.getWorld("world").getWorldBorder()
									.getSize() / 2) + "�b blocks");
					return true;
				}
			}

			if (arg3.length == 1) {
				if (arg3[0].equalsIgnoreCase("global")) {
					return gcToggle(sender, arg3);
				}

				if (arg3[0].equalsIgnoreCase("local")) {
					return lctoggle(sender, arg3);
				}
			}

			String msg = getTranslationLanguage(sender,
					stringKeys.CHATHELP.toString());
			sender.sendMessage(msg);

			// sender.sendMessage("�3Type �2/chat global�3 to toggle global chat\n�3Type �2/chat local �3to toggle local chat");
			return true;
		} else {
			noPerms(sender);
			return true;
		}
	}

	private boolean chunk(CommandSender sender, String[] arg3) {
		if (arg3.length > 0) {
			if (arg3[0].equalsIgnoreCase("claim")) {

				Player p = Bukkit.getPlayer(sender.getName());

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

					// p.sendMessage("�2You are already prepped to protect!  Right click somewhere");
					return true;
				}

				if (arg3.length == 1) {
					preppedToProtectArea.add(new PlayerProtect(Bukkit
							.getPlayer(sender.getName()), 0));

					String msg = getTranslationLanguage(sender,
							stringKeys.PROTPREPPEDTOPROTECT.toString());
					sender.sendMessage(msg);

					// sender.sendMessage("�2You are ready to claim an area!  Right click your first corner");
					return true;
				} else {
					int length = -1;
					if (arg3.length > 1) {
						try {
							length = Integer.parseInt(arg3[1]);
						} catch (Exception e) {
							// sender.sendMessage("�cThe length must be an integer value!  �3Ex:  /protect claim 5");

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

						// sender.sendMessage("�cThe minimum size is 5! (11x11 blocks)");
						return true;
					}
					if (length >= 75) {

						String msg = getTranslationLanguage(sender,
								stringKeys.PROTAREATOOBIG.toString());
						sender.sendMessage(msg);

						// sender.sendMessage("�cThat is too much!");
						return true;
					}

					preppedToProtectArea.add(new PlayerProtect(Bukkit
							.getPlayer(sender.getName()), length));

					String msg = getTranslationLanguage(sender,
							stringKeys.PROTAREAREADY.toString());
					sender.sendMessage(msg);

					// sender.sendMessage("�2You are ready to claim an area!  Right click the center block");
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

					// sender.sendMessage("�2You are ready to unclaim an area!  Right click on a block to unclaim its area.");
					return true;
				} else {

					String msg = getTranslationLanguage(sender,
							stringKeys.PROTUNCLAIMSTILLPREPPED.toString());
					sender.sendMessage(msg);

					// sender.sendMessage("�2You are still ready to unclaim an area... Right click on a block.");
					return true;
				}

			}

			else if (arg3[0].equalsIgnoreCase("trustlist")) {
				if (blocksClaimed(Bukkit.getServer()
						.getPlayer(sender.getName()).getDisplayName()) == 0) {

					String msg = getTranslationLanguage(sender,
							stringKeys.PROTNOAREAS.toString());
					sender.sendMessage(msg);

					// sender.sendMessage("�cYou don't own any areas!");
					return true;
				}
				for (ProtectedArea pa : areas) {
					if (pa.owner.equals(Bukkit.getServer()
							.getPlayer(sender.getName()).getDisplayName())) {
						/*
						 * sender.sendMessage("�3Trustlist: \n" +
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

					// sender.sendMessage("�cYou don't own any areas!");
					return true;
				}

				String msg = getTranslationLanguage(sender,
						stringKeys.PROTAREALIST.toString());
				sender.sendMessage(msg);

				// sender.sendMessage("�3Your area list:");

				for (ProtectedArea pa : areas) {
					if (pa.owner.equals(Bukkit.getServer()
							.getPlayer(sender.getName()).getDisplayName())) {
						sender.sendMessage(" - �3 X: " + pa.location1.getX()
								+ " Z: " + pa.location1.getZ() + " ("
								+ pa.xDiff() + "x" + pa.zDiff() + " = "
								+ pa.area() + " blocks)");
					}
				}
				String name = Bukkit.getServer().getPlayer(sender.getName())
						.getDisplayName();

				String message = getTranslationLanguage(sender,
						stringKeys.PROTEXTENDEDLISTDATA.toString());

				sender.sendMessage(String.format(
						message,
						blocksAllowed(name),
						blocksClaimed(name),
						(blocksAllowed(name) - blocksClaimed(name)),
						(int) Math
								.sqrt((blocksAllowed(name) - blocksClaimed(name))),
						(int) Math
								.sqrt((blocksAllowed(name) - blocksClaimed(name)))));

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
								sender.sendMessage("�cNice try");
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

							// sender.sendMessage("�cYou can only use this where you don't have build permission");
							return true;
						}
					}
				}

				String msg = getTranslationLanguage(sender,
						stringKeys.PROTSTUCKABUSEATTEMPTNOTEVENPROT.toString());
				sender.sendMessage(msg);

				// sender.sendMessage("�cYou can only use this in a protected area");
				return true;
			}
		}
		if (arg3.length > 1) {
			if (arg3[0].equalsIgnoreCase("trust")) {
				if (blocksClaimed(Bukkit.getServer()
						.getPlayer(sender.getName()).getDisplayName()) == 0) {
					// sender.sendMessage("�cYou don't own any areas!");
					String msg = getTranslationLanguage(sender,
							stringKeys.PROTNOAREAS.toString());
					sender.sendMessage(msg);

					return true;
				}

				if (arg3.length > 2) {
					if (arg3[1].equalsIgnoreCase("time")) {
						int time = 0;
						try {
							time = Integer.parseInt(arg3[2]);
						} catch (Exception e) {
							// sender.sendMessage("�cTime must be an integer value");

							String msg = getTranslationLanguage(sender,
									stringKeys.PROTTIMEBADARG.toString());
							sender.sendMessage(msg);
							return true;
						}

						for (OfflinePlayer op : Bukkit.getOfflinePlayers()) {
							if (getPlayerMinutes(op.getName()) > time) {

								ArrayList<String> list = new ArrayList<String>();

								for (ProtectedArea pa : areas) {
									if (pa.owner.equals(Bukkit.getServer()
											.getPlayer(sender.getName())
											.getDisplayName())) {
										pa.addTrust(op.getName());
										list = pa.trustedPlayers;
									}
								}

								String path = "ProtectedAreas."
										+ Bukkit.getServer()
												.getPlayer(sender.getName())
												.getDisplayName() + ".Trusts";

								String[] loc = new String[list.size()];
								for (int i = 0; i < list.size(); i++) {
									loc[i] = list.get(i);
								}

								protectedAreasCfg.getConfig().set(path, loc);

								/*
								 * sender.sendMessage("�2" + op.getName() +
								 * "�3 now has full permissions in all of your protected areas"
								 * );
								 */

								String msg = getTranslationLanguage(sender,
										stringKeys.PROTADDEDTRUST.toString());
								sender.sendMessage(String.format(msg,
										op.getName()));
							}
						}
						protectedAreasCfg.saveConfig();
						protectedAreasCfg.reloadConfig();
						return true;
					}
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
				 * sender.sendMessage("�2" + arg3[1] +
				 * "�3 now has full permissions in all of your protected areas"
				 * );
				 */

				if (arg3[1].equalsIgnoreCase("doors")) {
					sender.sendMessage("�2Doors �3are now publicly clickable in all of your claims");
					return true;
				}
				if (arg3[1].equalsIgnoreCase("buttons")) {
					sender.sendMessage("�2Buttons �3are now publicly clickable in all of your claims");
					return true;
				}
				if (arg3[1].equalsIgnoreCase("plates")) {
					sender.sendMessage("�2Pressure plates �3are now public in all of your claims");
					return true;
				}
				if (arg3[1].equalsIgnoreCase("chests")) {
					sender.sendMessage("�2Chests, furnaces, brewing stands and hoppers �3are now public in all of your claims");
					return true;
				}
				if (arg3[1].equalsIgnoreCase("levers")) {
					sender.sendMessage("�2Levers �3are now public in all of your claims");
					return true;
				}
				if (arg3[1].equalsIgnoreCase("gates")) {
					sender.sendMessage("�2Fence gates �3are now public in all of your claims");
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

					// sender.sendMessage("�cYou don't own any areas!");
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

					// sender.sendMessage("�c" +
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

					// sender.sendMessage("�cYou do not trust anybody named �2"
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
				 * sender.sendMessage("�2" + arg3[1] +
				 * "�3 no longer has permissions in any of your protected areas"
				 * );
				 */

				if (arg3[1].equalsIgnoreCase("doors")) {
					sender.sendMessage("�2Doors �3are now longer publicly clickable in your claims");
					return true;
				}
				if (arg3[1].equalsIgnoreCase("buttons")) {
					sender.sendMessage("�2Buttons �3are no longer publicly clickable in your claims");
					return true;
				}
				if (arg3[1].equalsIgnoreCase("plates")) {
					sender.sendMessage("�2Pressure plates �3are no longer public in your claims");
					return true;
				}
				if (arg3[1].equalsIgnoreCase("chests")) {
					sender.sendMessage("�2Chests, furnaces, brewing stands and hoppers �3are no longer public in your claims");
					return true;
				}
				if (arg3[1].equalsIgnoreCase("levers")) {
					sender.sendMessage("�2Levers �3are no longer public in your claims");
					return true;
				}
				if (arg3[1].equalsIgnoreCase("gates")) {
					sender.sendMessage("�2Fence gates �3are no longer public in your claims");
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
		 * "�2/protect claim �3to claim an area based on corners\n" +
		 * "�2/protect claim <number> �3to claim an area based on center block �eEx: �6/protect claim 5\n"
		 * + "�2/protect unclaim �3to unclaim an area\n" +
		 * "�2/protect trust <PlayerName> �3to trust another player (to all of your claims!)\n"
		 * +
		 * "�2/protect untrust <PlayerName> �3to remove that player's access to your claims\n"
		 * + "�2/protect trustlist <PlayerName> �3to see all trusted players\n"
		 * + "�2/protect list �3to list the locations of your claims\n" +
		 * "�eRight click anywhere with a �6stick �eto test a block's ownership! Make sure your entire build is protected!"
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
							 * "�cThat block is too far away.  Get closer before claiming it!"
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
									// p.sendMessage("�cYou already own that area!");

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

								// p.sendMessage("�cThat area is already claimed!");
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
								 * event.getPlayer() .sendMessage( "�a" +
								 * "First corner is set!  Now click the " + "�e"
								 * + "OPPOSITE " + "�a" +
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
								p.sendMessage(msg);

								// p.sendMessage("�cThat area intersects with another area!");*/
								preppedToProtectArea.remove(i);
								return;
							}
						}

						if (blocksAllowed(event.getPlayer().getDisplayName()) <= blocksClaimed(event
								.getPlayer().getDisplayName())
								+ newProtArea.area()) {
							if (blocksAllowed(event.getPlayer()
									.getDisplayName()) == 0) {

								String msg = getTranslationLanguage(
										event.getPlayer(),
										stringKeys.PROTPREMATURE.toString());
								event.getPlayer().sendMessage(msg);

								/*
								 * event.getPlayer().sendMessage(
								 * "�cYou can't claim an area yet!");
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
															.getDisplayName()) - blocksClaimed(event
															.getPlayer()
															.getDisplayName())),
													newProtArea.area()));

							/*
							 * event.getPlayer() .sendMessage(
							 * "�cYou can't claim that many blocks yet!  \nYou can only claim �3"
							 * + (blocksAllowed(event .getPlayer()
							 * .getDisplayName()) - blocksClaimed(event
							 * .getPlayer() .getDisplayName())) +
							 * "�c more blocks\n" + "You just tried to claim �3"
							 * + newProtArea.area() + "�c blocks");
							 */
							preppedToProtectArea.remove(i);
							return;
						}

						if (newProtArea.xDiff() < 11
								|| newProtArea.zDiff() < 11) {

							String msg = getTranslationLanguage(
									event.getPlayer(),
									stringKeys.PROTAREATOOSMALL.toString());
							event.getPlayer().sendMessage(msg);
							/*
							 * event.getPlayer() .sendMessage(
							 * "�cThat area is too small!  Both length and width must be greater than 10"
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

						// p.sendMessage("�3You now own that area!  Right click with a �2stick �3to test an individual block for protection.  Make sure your entire build is protected");
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
					 * "�cYou can only protect stuff in the overworld... for now."
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
					 * "�cThat block is too far away.  Get closer before unclaiming it!"
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

							// p.sendMessage("�cYou no longer own that area!  It is editable by anybody!");
							preppedToUnProtectChunk.remove(event.getPlayer());
							return;
						}

						String msg = getTranslationLanguage(event.getPlayer(),
								stringKeys.PROTUNCLAIMDOESNOTOWN.toString());
						event.getPlayer().sendMessage(msg);

						// p.sendMessage("�cYou do not own that area!");
						preppedToUnProtectChunk.remove(event.getPlayer());
						return;
					}
					i++;
				}
				preppedToUnProtectChunk.remove(event.getPlayer());

				String msg = getTranslationLanguage(p,
						stringKeys.PROTUNCLAIMNOBODYOWNS.toString());
				p.sendMessage(msg);

				// p.sendMessage("�cNobody owns that area!");
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

	private int blocksAllowed(String playerName) {
		if (playerName.equals("secret1")) {
			return 9000000;
		}

		if (playerName.contains("[Server]")) {
			return 595000565;
		}

		String UUID = Bukkit.getOfflinePlayer(playerName).getUniqueId()
				.toString();
		return (playtimeCfg.getConfig().getInt("Playtime." + UUID) / 60) * 256;
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

		//GolfPro's target thing
		if (event.getFrom().getWorld().getName().equals("world")) {
			if (event.getCause().equals(TeleportCause.ENDER_PEARL)) {
				double distance = event.getTo()
						.distance(
								new Location(event.getTo().getWorld(), 581.5,
										66, 402.5));
				if (distance < 25) {
					event.getPlayer().sendMessage(
							"�6" + distance + " meters from center");
					event.setCancelled(true);
					event.getPlayer().teleport(event.getTo());
					event.getPlayer().getInventory()
							.addItem(new ItemStack(Material.ENDER_PEARL));
				}
			}
		}
	}

	@EventHandler
	private void onBucketEvent(PlayerBucketEmptyEvent event) {
		for (ProtectedArea pa : areas) {
			if (pa.equals(event.getBlockClicked().getLocation())
					|| pa.equals(event.getBlockClicked()
							.getRelative(BlockFace.EAST).getLocation())
					|| pa.equals(event.getBlockClicked()
							.getRelative(BlockFace.NORTH).getLocation())
					|| pa.equals(event.getBlockClicked()
							.getRelative(BlockFace.SOUTH).getLocation())
					|| pa.equals(event.getBlockClicked()
							.getRelative(BlockFace.WEST).getLocation())) {
				if (!pa.hasPermission(event.getPlayer().getDisplayName())) {
					event.setCancelled(true);
					return;
				}
			}
		}
	}

	@EventHandler
	private void onBucketFillEvent(PlayerBucketFillEvent event) {
		for (ProtectedArea pa : areas) {
			if (pa.equals(event.getBlockClicked().getLocation())) {
				if (!pa.hasPermission(event.getPlayer().getDisplayName())) {
					event.setCancelled(true);
					return;
				}
			}
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
	private void onChunkExplosion(EntityExplodeEvent event) {
		for (ProtectedArea pa : areas) {
			for (int j = 0; j < event.blockList().size(); j++) {
				if (pa.equals(event.blockList().get(j).getLocation())) {
					event.setCancelled(true);
				}
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

	private boolean coords(CommandSender sender) {
		if (sender.hasPermission("SerenityPlugins.coords")) {
			Location l = Bukkit.getServer().getPlayer(sender.getName())
					.getLocation();
			this.getServer().broadcastMessage(
					"�3"
							+ Bukkit.getServer().getPlayer(sender.getName())
									.getDisplayName()
							+ "�r�9 is in the �3"
							+ sender.getServer().getPlayer(sender.getName())
									.getWorld().getName() + "�9 at:"
							+ "�9 X: �3" + (int) l.getX() + "�9 Y: �3"
							+ (int) l.getY() + "�9 Z: �3" + (int) l.getZ());
			return true;
		} else {
			noPerms(sender);
			return true;
		}
	}

	private boolean server(CommandSender sender, String[] arg3) {

		if (sender.hasPermission("SerenityPlugins.server")) {
			if (arg3.length == 0) {
				serverChatting = !serverChatting;
				sender.sendMessage("Serverchatting = " + serverChatting);
				return true;
			}

			if (arg3[0].equalsIgnoreCase("say")) {
				String s = "�d[Server] ";
				for (int i = 1; i < arg3.length; i++) {
					s += arg3[i] + " ";
				}

				for (Player p : Bukkit.getOnlinePlayers()) {
					if (!deafPlayers.contains(p)) {
						p.sendMessage(s);
					}
				}

				getLogger().info(s);
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
														99999, 0), "�c"
														+ "ZOMBIE");
									}
								};
							}, 100L);

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
										if (e.getCustomName().contains("�c")) {
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

				if (arg3[0].equals("remove")) {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						for (Entity e : p.getNearbyEntities(5, 5, 5)) {
							e.remove();
						}
					}
				}

				if (arg3[0].equals("golf")) {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						ItemStack is = new ItemStack(Material.SNOW_BALL);
						ItemMeta im = is.getItemMeta();
						im.setDisplayName("�6" + p.getDisplayName()
								+ "'s Golf Ball");
						is.setItemMeta(im);
						p.getInventory().addItem(is);
						p.setWalkSpeed(.2F);
					}
				}

				if (arg3[0].equals("ping")) {
					for (Player p : Bukkit.getOnlinePlayers()) {
						CraftPlayer cp = (CraftPlayer) p;
						sender.sendMessage(p.getDisplayName() + ": "
								+ cp.getHandle().ping + "ms");
					}
				}

				if (arg3[0].equals("reloadcfg")) {
					podrickCfg.reloadConfig();
					teamsCfg.reloadConfig();
					emailCfg.reloadConfig();
					bookCfg.reloadConfig();
					linksCfg.reloadConfig();
					sender.sendMessage("Reloaded pod, teams, email, book, and links");
				}

				if (arg3[0].equals("flames")) {
					flameOn = !flameOn;
					sender.sendMessage("Flame on = " + flameOn);
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

				if (arg3[0].equals("time")) {
					for (OfflinePlayer op : Bukkit.getOfflinePlayers()) {
						if (op.isWhitelisted()) {
							sender.sendMessage(op.getName() + ": "
									+ getHoursAndMinutes(op.getName()));
						}
					}
					return true;
				}

				if (arg3[0].equals("clean")) {
					cleanConfig();
					return true;
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
							"�cReloading plugins... There might be lag");
					Bukkit.getServer().dispatchCommand(
							Bukkit.getConsoleSender(), "reload");
					Long dur = System.currentTimeMillis() - time;
					Bukkit.getServer().broadcastMessage(
							"�2Done. (" + (double) dur / 1000 + "s)");
					return true;
				}

				if (arg3[0].equals("createbook")) {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
						BookMeta meta = (BookMeta) book.getItemMeta();
						bookCfg.reloadConfig();

						meta.setTitle(bookCfg.getConfig().getString("Title"));
						meta.setAuthor("�6"
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

					Bukkit.getScheduler().scheduleSyncDelayedTask(this,
							new Runnable() {
								@Override
								public void run() {
									for (Player p : Bukkit.getOnlinePlayers()) {
										p.setScoreboard(Bukkit
												.getScoreboardManager()
												.getNewScoreboard());
									}
								}
							}, 20L * Integer.parseInt(arg3[1]));
					return true;
				}

				if (arg3[0].equals("secret")) {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						p.getLocation()
								.getWorld()
								.spawnEntity(p.getLocation(),
										EntityType.VILLAGER)
								.setCustomName("�6" + arg3[1]);
					}
				}

				if (arg3[0].equals("secretpig")) {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						p.getLocation().getWorld()
								.spawnEntity(p.getLocation(), EntityType.PIG)
								.setCustomName("�6" + arg3[1]);
					}
				}

				if (arg3[0].equals("changemyname")) {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						p.setDisplayName(arg3[1]);
						p.sendMessage("�6Identity changed to �3" + arg3[1]);
						return true;

					}
				}

				if (arg3[0].equals("secretcow")) {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						p.getLocation().getWorld()
								.spawnEntity(p.getLocation(), EntityType.COW)
								.setCustomName("�6" + arg3[1]);
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
					horse.setCustomName("�d" + arg3[1]);
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

						ocelot.setCustomName("�6" + arg3[1]);
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
								.setCustomName("�6" + arg3[1]);
					}
				}

				if (arg3[0].equals("secretmoosh")) {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						p.getLocation()
								.getWorld()
								.spawnEntity(p.getLocation(),
										EntityType.MUSHROOM_COW)
								.setCustomName("�6" + arg3[1]);
					}
				}

				if (arg3[0].equals("secretsheep")) {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						p.getLocation().getWorld()
								.spawnEntity(p.getLocation(), EntityType.SHEEP)
								.setCustomName("�6" + arg3[1]);
					}
				}

				if (arg3[0].equals("clean")) {
					cleanConfig(arg3[1]);
					return true;
				}

				if (arg3[0].equals("funban")) {
					funBan(arg3[1]);
					return true;
				}

				if (arg3[0].equals("time")) {
					sender.sendMessage(arg3[1] + ": "
							+ getHoursAndMinutes(arg3[1]));
					return true;
				}

				if (arg3[0].equals("mute")) {
					if (mutedNames.contains(arg3[1])) {
						mutedNames.remove(arg3[1]);
						sender.sendMessage("Un-muted " + arg3[1]);
						return true;
					}
					mutedNames.add(arg3[1]);
					sender.sendMessage("Muted " + arg3[1]);
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
			}
		} else {
			noPerms(sender);
			return true;
		}
		return false;
	}

	/*
	@EventHandler
	public void onGolfHitEvent(PlayerDropItemEvent event) {

		if (event.getItemDrop().getItemStack().hasItemMeta()
				&& event.getItemDrop().getItemStack().getItemMeta()
						.hasDisplayName()) {
			if (event
					.getItemDrop()
					.getItemStack()
					.getItemMeta()
					.getDisplayName()
					.equals("�6" + event.getPlayer().getDisplayName()
							+ "'s Golf Ball")) {

				event.getPlayer().setItemInHand(null);
				final World w = event.getPlayer().getWorld();
				final Location l = event.getPlayer().getLocation();
				final Vector v = l.getDirection();
				ItemStack is = new ItemStack(Material.SNOW_BALL);
				ItemMeta im = is.getItemMeta();
				im.setDisplayName("�6" + event.getPlayer().getDisplayName()
						+ "'s Golf Ball");
				is.setItemMeta(im);
				final ItemStack isf = is;
				event.setCancelled(true);
				Bukkit.getScheduler().scheduleSyncDelayedTask(this,
						new Runnable() {
							@Override
							public void run() {
								w.dropItem(l, isf).setVelocity(
										new Vector(v.getX() * .5,
												v.getY() * .5, v.getZ() * .5));
							}
						});
			}
		}
	}*/

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

	private void funBan(String string) {
		OfflinePlayer op = Bukkit.getOfflinePlayer(string);
		op.setBanned(true);
		op.setWhitelisted(false);
		if (op.isOnline()) {
			Player p = Bukkit.getPlayer(string);
			p.kickPlayer("You've been banned");
		}
		cleanConfig(string);
	}

	private boolean gcToggle(CommandSender sender, String[] arg3) {
		if (sender instanceof Player) {
			if (deafPlayers.contains(((Player) sender).getPlayer())) {
				deafPlayers.remove(((Player) sender).getPlayer());
				// sender.sendMessage("�2You can now see global chat!");

				String msg = getTranslationLanguage(sender,
						stringKeys.CHATCANSEEGLOBAL.toString());
				sender.sendMessage(msg);

			} else {
				deafPlayers.add(((Player) sender).getPlayer());

				String msg = getTranslationLanguage(sender,
						stringKeys.CHATCANTSEEGLOBAL.toString());
				sender.sendMessage(msg);

				// sender.sendMessage("�2You will no longer see global chat!  But you can still see /msg chat and local chat");
			}
			return true;
		}
		return true;
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
		if (sender.hasPermission("SerenityPlugins.coords")) {
			if (arg3.length == 0) {
				GregorianCalendar gc = new GregorianCalendar();

				Iterator<PlayerStatus> it = playerStatuses.iterator();
				List<PlayerStatus> statusesToDelete = new ArrayList<PlayerStatus>();
				while (it.hasNext()) {
					PlayerStatus ps = it.next();
					if (gc.getTimeInMillis() - ps.getTime().getTimeInMillis() > 259200000) {
						statusesToDelete.add(ps);
					}
				}

				for (int i = 0; i < statusesToDelete.size(); i++) {
					deletePlayerStatus(statusesToDelete.get(i).getName());
				}

				String messageToSend = "";

				it = playerStatuses.iterator();

				while (it.hasNext()) {
					PlayerStatus ps = it.next();
					messageToSend += ps.toString();
				}

				sender.sendMessage(messageToSend);
				return true;
			} else {
				deletePlayerStatus(sender.getName());
				String status = "";
				for (int i = 0; i < arg3.length; i++) {
					if (i != arg3.length - 1) {
						status += arg3[i] + " ";
					} else {
						status += arg3[i];
					}
				}
				PlayerStatus ps = new PlayerStatus(sender.getName(), status,
						new GregorianCalendar());

				addPlayerStatus(ps);
				// sender.sendMessage("�2Your status was updated!");

				String msg = getTranslationLanguage(sender,
						stringKeys.STATUSSUCCESS.toString());
				sender.sendMessage(msg);
			}
			return true;
		} else {
			noPerms(sender);
			return true;
		}
	}

	private boolean getPortal(CommandSender sender) {
		if (sender.hasPermission("SerenityPlugins.portal")) {
			Location l = Bukkit.getServer().getPlayer(sender.getName())
					.getLocation();
			if (l.getWorld().getName().equals(("world"))) {

				String msg = getTranslationLanguage(sender,
						stringKeys.PORTALNETHER.toString());
				sender.sendMessage(String.format(msg, (int) (l.getX() / 8),
						(int) (l.getZ() / 8)));

				/*
				 * sender.sendMessage(
				 * "�l�5If you want your portal to exit here, build it in the nether at: "
				 * + "�r�d\n   X: �l�3" + (int) (l.getX() / 8) +
				 * "�r�d\n   Z: �l�3" + (int) (l.getZ() / 8));
				 */
			} else {

				String msg = getTranslationLanguage(sender,
						stringKeys.PORTALOVERWORLD.toString());
				sender.sendMessage(String.format(msg, (int) (l.getX() * 8),
						(int) (l.getZ() * 8)));

				/*
				 * sender.sendMessage(
				 * "�l�5If you build your portal here you will end up at: " +
				 * "�r�d\n   X: �l�3" + (int) (l.getX() * 8) +
				 * "�r�d\n   Z: �l�3" + (int) (l.getZ() * 8));
				 */
			}
			return true;
		} else {
			noPerms(sender);
			return true;
		}
	}

	private boolean lastLogin(CommandSender sender, String[] arg3) {
		if (sender.hasPermission("SerenityPlugins.lastseen")) {
			TreeSet<LastLogin> logins = new TreeSet<LastLogin>();
			for (OfflinePlayer op : Bukkit.getServer().getOfflinePlayers()) {
				if (!op.isOp()) {
					if (op.isWhitelisted()) {
						logins.add(new LastLogin(op.getName(), op
								.getLastPlayed()));
					}
				}
			}

			Stack<String> ll = new Stack<String>();

			for (LastLogin key : logins) {
				ll.push("�b"
						+ key.name
						+ " �3"
						+ getDurationBreakdown(System.currentTimeMillis()
								- key.timeInMilliseconds));
			}

			Stack<String> result = new Stack<String>();

			if (arg3.length < 1) {
				int count = 0;
				int totalPages = (ll.size() / 6) + 1;
				result.push("�7Page 1/" + totalPages
						+ "  �6/lastseen 2 �efor next page");
				while (!ll.isEmpty()) {
					String s = ll.pop();
					if (count < 6)
						result.push(s + "\n");
					count++;
				}
				String results = "";

				while (!result.isEmpty()) {
					results += result.pop();
				}
				sender.sendMessage(results);
				return true;
			}

			int page = -1;

			try {
				page = Integer.parseInt(arg3[0]);
			} catch (Exception e) {
				ll.clear();

				for (LastLogin key : logins) {
					if (key.name.toUpperCase().contains(arg3[0].toUpperCase())) {
						ll.push("�b"
								+ key.name
								+ " �3"
								+ getDurationBreakdown(System
										.currentTimeMillis()
										- key.timeInMilliseconds));
					}
				}

				String resultser = "";

				while (!ll.isEmpty()) {
					String s = ll.pop();
					resultser += s + "\n";
				}
				sender.sendMessage(resultser);
				return true;
			}
			int count = 0;
			page--;
			int nextPage = page + 2;
			int totalPages = (ll.size() / 6) + 1;
			if (totalPages > nextPage - 1)
				result.push("�7Page " + arg3[0] + "/" + totalPages
						+ " �6/lastseen " + nextPage + " �efor next page");
			else if (totalPages == nextPage - 1) {
				result.push("�7Page " + arg3[0] + "/" + totalPages);
			} else {
				return false;
			}
			while (!ll.isEmpty()) {
				String s = ll.pop();
				if (page * 6 <= count && count < 6 * page + 6)
					result.push(s + "\n");
				count++;
			}

			String results = "";
			while (!result.isEmpty()) {
				results += result.pop();
			}
			sender.sendMessage(results);
			return true;

		}
		/*
		 * int count = results.size(); String result = "";
		 * 
		 * while (!results.isEmpty()) { result += "�6#" + count + results.pop()
		 * + "\n"; count--; }
		 * 
		 * if (arg3.length > 0) { Long now = System.currentTimeMillis(); String
		 * name = arg3[0];
		 * 
		 * Iterator<LastLogin> it = logins.iterator(); String returnVal =
		 * "�3Last seen:"; boolean foundOne = false;
		 * 
		 * while (it.hasNext()) { LastLogin ll = it.next(); if
		 * (ll.name.toLowerCase().contains(name.toLowerCase())) { returnVal +=
		 * "\n�3"; foundOne = true; if (now - ll.timeInMilliseconds < 60000) {
		 * returnVal += ll.name + ": �b" + (now - ll.timeInMilliseconds) / 1000
		 * + " seconds ago"; } else if (now - ll.timeInMilliseconds < 3600000) {
		 * if ((now - ll.timeInMilliseconds) / 60000 == 1) { returnVal +=
		 * ll.name + ": �b" + (now - ll.timeInMilliseconds) / 60000 +
		 * " minute ago"; } else { returnVal += ll.name + ": �b" + (now -
		 * ll.timeInMilliseconds) / 60000 + " minutes ago"; } } else if (now -
		 * ll.timeInMilliseconds < 86400000) { if ((now - ll.timeInMilliseconds)
		 * / 3600000 == 1) { returnVal += ll.name + ": �b" + (now -
		 * ll.timeInMilliseconds) / 3600000 + " hour ago"; } else { returnVal +=
		 * ll.name + ": �b" + (now - ll.timeInMilliseconds) / 3600000 +
		 * " hours ago"; } } else { if ((now - ll.timeInMilliseconds) / 86400000
		 * == 1) { returnVal += ll.name + ": �b" + (now - ll.timeInMilliseconds)
		 * / 86400000 + " day ago"; } else { returnVal += ll.name + ": �b" +
		 * (now - ll.timeInMilliseconds) / 86400000 + " days ago"; } } } }
		 * 
		 * if (foundOne) { sender.sendMessage(returnVal); return true; }
		 * 
		 * String msg = getTranslationLanguage(sender,
		 * stringKeys.LASTSEENNOTFOUND.toString());
		 * sender.sendMessage(String.format(msg, name));
		 * 
		 * // sender.sendMessage("�cI could not find any players with \"" // +
		 * name + "\" in their name!"); return true; }
		 * 
		 * String returnVal =
		 * "�3Last seen (type /lastseen <playername> to check for one player: \n"
		 * ; Long now = System.currentTimeMillis(); Iterator<LastLogin> it =
		 * logins.iterator(); int count = 0; while (it.hasNext()) { LastLogin ll
		 * = it.next(); if (now - ll.timeInMilliseconds < 60000) { returnVal +=
		 * ll.name + ": �b" + (now - ll.timeInMilliseconds) / 1000 +
		 * " seconds ago"; } else if (now - ll.timeInMilliseconds < 3600000) {
		 * if ((now - ll.timeInMilliseconds) / 60000 == 1) { returnVal +=
		 * ll.name + ": �b" + (now - ll.timeInMilliseconds) / 60000 +
		 * " minute ago"; } else { returnVal += ll.name + ": �b" + (now -
		 * ll.timeInMilliseconds) / 60000 + " minutes ago"; } } else if (now -
		 * ll.timeInMilliseconds < 86400000) { if ((now - ll.timeInMilliseconds)
		 * / 3600000 == 1) { returnVal += ll.name + ": �b" + (now -
		 * ll.timeInMilliseconds) / 3600000 + " hour ago"; } else { returnVal +=
		 * ll.name + ": �b" + (now - ll.timeInMilliseconds) / 3600000 +
		 * " hours ago"; } } else if (now - ll.timeInMilliseconds < 2592000000L)
		 * { if ((now - ll.timeInMilliseconds) / 86400000 == 1) { returnVal +=
		 * ll.name + ": �b" + (now - ll.timeInMilliseconds) / 86400000 +
		 * " day ago"; } else { returnVal += ll.name + ": �b" + (now -
		 * ll.timeInMilliseconds) / 86400000 + " days ago"; } }
		 * 
		 * if (count != logins.size() - 1) { returnVal += ", �3"; } count++; }
		 * sender.sendMessage(returnVal); return true; } else { noPerms(sender);
		 * return true; }
		 */

		return false;
	}

	public String getDurationBreakdown(long millis) {
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
		if (sender.hasPermission("SerenityPlugins.afk")) {
			Player player = Bukkit.getPlayer(sender.getName());

			/*
			 * if (sender instanceof Conversable) {
			 * conversationFactory.buildConversation((Conversable) sender)
			 * .begin(); return true; } else { // return false; }
			 */

			if (sender instanceof Player) {
				player = (Player) sender;
			}

			if (arg3.length > 0) {
				if (votingForDay) {
					if (sender instanceof Player) {
						((Player) sender).getPlayer().setSleepingIgnored(true);
						int count = 0;
						for (Player p : Bukkit.getOnlinePlayers()) {
							if (p.isSleepingIgnored() || p.isSleeping()) {
								count++;
							}
						}

						double percentage = (double) count
								/ Bukkit.getOnlinePlayers().size();
						percentage *= 100.0;

						String result = new DecimalFormat("##.##")
								.format(percentage);

						String msg = getTranslationLanguage(sender,
								stringKeys.BEDVOTING.toString());

						sender.sendMessage(msg + result + "%");

						/*
						 * sender.sendMessage("�9" +
						 * "Waiting for >50% of players to get in bed or type /ok. Current: "
						 * + "�b" + result + "%");
						 */

						checkHalfIgnored();
						return true;
					}

				} else {

					String msg = getTranslationLanguage(sender,
							stringKeys.BEDOKNOTVOTING.toString());
					sender.sendMessage(msg);

					/*
					 * sender.sendMessage("�c" +
					 * "This can only be done after somebody enters a bed");
					 */
					return true;

				}
			}

			if (isAfk(player)) {

				String msg = getTranslationLanguage(sender,
						stringKeys.AFKALREADYAFK.toString());
				sender.sendMessage(msg);

				// sender.sendMessage("�cYou are already AFK!");
				return true;
			}

			setAfk(player);

			return true;
		} else {
			noPerms(sender);
			return true;
		}
	}

	private boolean getTime(CommandSender sender, String[] arg3) {
		if (sender.hasPermission("SerenityPlugins.gettime")) {
			if (arg3.length > 0) {
				sender.sendMessage(getHoursAndMinutes(sender.getName()));
				return true;
			}
			Long time = Bukkit.getWorld("world").getTime();

			String msg = getTranslationLanguage(sender,
					stringKeys.GETTIMETICKS.toString());
			sender.sendMessage(String.format(msg, time));

			// sender.sendMessage("�7Time in ticks: " + time);
			String s = "�9It is ";
			if (time < 800) {
				s += "�emorning.";
			} else if (time < 10000) {
				s += "�6daytime.";
			} else if (time < 12500) {
				s += "�7dusk.";
			} else if (time < 22500) {
				s += "�8night.";
			} else {
				s += "�7almost morning.";
			}
			sender.sendMessage(s);
			return true;
		} else {
			noPerms(sender);
			return true;
		}
	}

	private boolean mailTo(CommandSender sender, String[] arg3) {
		if (sender.hasPermission("SerenityPlugins.mailto")) {
			if (arg3.length < 1) {
				String s = "";
				s += "\n�cType a mailbox name!  \nHere are all of the currently active mailboxes\n�2A '#' means it's a public mailbox:\n";
				for (int i = 0; i < mailBoxes.size(); i++) {
					if (mailBoxes.get(i).name.startsWith("#")) {
						s += "�2" + mailBoxes.get(i).name + ", ";
					} else {
						s += "�3" + mailBoxes.get(i).name + ", ";
					}
					if (i % 3 == 0 && i != 0) {
						s += "\n";
					}
				}
				s = s.substring(0, s.length() - 2);

				sender.sendMessage(s);
				return false;
			}
			String mailto = arg3[0];

			if (mailto.equals("EVERYBODY18104")) {
				for (Mailbox mb : mailBoxes) {
					mailItemsTo(sender, mb.name, true);
				}
				return true;
			}

			return mailItemsTo(sender, mailto, false);
		} else {
			noPerms(sender);
			return true;
		}
	}

	private boolean mailItemsTo(CommandSender sender, String mailto, boolean op) {
		boolean receivingMailBoxExists = false;
		boolean sendingMailBoxExists = false;
		Mailbox sendingMailbox = new Mailbox();
		Mailbox receivingMailbox = new Mailbox();
		for (int i = 0; i < mailBoxes.size(); i++) {
			if (mailBoxes.get(i).name.toLowerCase().contains(
					mailto.toLowerCase())) {
				receivingMailBoxExists = true;
				receivingMailbox.setName(mailBoxes.get(i).getName());
				receivingMailbox.setLocation(mailBoxes.get(i).getLocation());
			}
			if (mailBoxes.get(i).name.equals(Bukkit.getServer()
					.getPlayer(sender.getName()).getDisplayName())) {
				sendingMailBoxExists = true;
				sendingMailbox.setName(mailBoxes.get(i).getName());
				sendingMailbox.setLocation(mailBoxes.get(i).getLocation());
			}
		}

		if (!sendingMailBoxExists) {

			String msg = getTranslationLanguage(sender,
					stringKeys.MAILDOESNTHAVEMAILBOX.toString());
			sender.sendMessage(msg);

			// sender.sendMessage("�cYou don't have a mailbox!  Put a chest on top of a fence post!");
			return true;
		}
		if (!receivingMailBoxExists) {

			String s = getTranslationLanguage(sender,
					stringKeys.MAILCOULDNOTFINDBOX.toString());

			// String s = "�cI couldn't find that mailbox!";
			s += "\n�cHere are all of the currently active mailboxes\n�2A '#' means it's a public mailbox:\n";

			for (int i = 0; i < mailBoxes.size(); i++) {
				if (mailBoxes.get(i).name.startsWith("#")) {
					s += "�2" + mailBoxes.get(i).name + ", ";
				} else {
					s += "�3" + mailBoxes.get(i).name + ", ";
				}
				if (i % 3 == 0) {
					s += "\n";
				}
			}
			s = s.substring(0, s.length() - 2);

			sender.sendMessage(s);
			return false;
		}

		if (sendingMailbox.getName().equals(receivingMailbox.getName())) {

			String msg = getTranslationLanguage(sender,
					stringKeys.MAILTOSELF.toString());
			sender.sendMessage(msg);

			// sender.sendMessage("�cYou can't send mail to yourself!");
			return true;
		}

		Chest sendingChest = (Chest) sendingMailbox.getLocation().getBlock()
				.getState();
		Chest receivingChest = (Chest) receivingMailbox.getLocation()
				.getBlock().getState();

		ItemStack[] sendingItems = sendingChest.getInventory().getContents();
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
			// sender.sendMessage("�cYou don't have any items in your mailbox!");

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
			 * sender.sendMessage("�6" + receivingMailbox.name +
			 * "�2's mailbox is full!");
			 */
			return true;
		}

		if (receivingItems.length - receivingItemsCount < sendingItemsCount) {

			String msg = getTranslationLanguage(sender,
					stringKeys.MAILNOTENOUGHSPACE.toString());
			sender.sendMessage(String.format(msg, receivingMailbox.name,
					((receivingItems.length - receivingItemsCount) - 1)));

			/*
			 * sender.sendMessage("�6" + receivingMailbox.name +
			 * "�2's mailbox can only hold " + ((receivingItems.length -
			 * receivingItemsCount) - 1) + " more slots!  Send less items!");
			 */
			return true;
		}

		for (int i = 0; i < sendingItems.length; i++) {
			if (sendingItems[i] != null) {
				receivingChest.getInventory().addItem(sendingItems[i]);
				if (!op) {
					sendingChest.getInventory().remove(sendingItems[i]);
				}
				sendingChest.update();
				receivingChest.update();
			}
		}

		String msg = getTranslationLanguage(sender,
				stringKeys.MAILSUCCESS.toString());
		sender.sendMessage(String.format(msg, receivingMailbox.name));
		/*
		 * sender.sendMessage("�2Your items were sent to �6" +
		 * receivingMailbox.name + "�2's mailbox!\n");
		 */
		doRandomFirework(receivingMailbox.getLocation().getWorld(),
				receivingMailbox.getLocation());

		return true;

	}

	private boolean setCompass(CommandSender sender, String[] arg3) {
		if (sender.hasPermission("SerenityPlugins.setcompass")) {
			if (arg3.length < 1) {

				String msg = getTranslationLanguage(sender,
						stringKeys.COMPASSHELP.toString());
				sender.sendMessage(msg);

				/*
				 * sender.sendMessage(
				 * "�cNo destination specified.. try: �2\n  \"home\" �3for your bed\n"
				 * + "�2  \"spawn\" �3for the world's spawn\n" +
				 * "�2  \"mailbox\" �3for your mailbox\n" +
				 * "�2  \"here\" �3for your current location\n" +
				 * "  or manually type any <X> <Y> <Z> coordinates" +
				 * "\n�cExamples: �2" + "\n/setcompass home" +
				 * "\n/setcompass 200 64 -200");
				 */
				return true;
			}
			if (arg3.length < 2) {
				if (arg3[0].toLowerCase().equals("spawn")) {

					String msg = getTranslationLanguage(sender,
							stringKeys.COMPASSSPAWN.toString());
					sender.sendMessage(msg);

					// sender.sendMessage("�2Set compass to point to spawn");
					this.getServer()
							.getPlayer(sender.getName())
							.setCompassTarget(
									this.getServer()
											.getPlayer(sender.getName())
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
						// sender.sendMessage("�2Set compass to point to your bed");

						String msg = getTranslationLanguage(sender,
								stringKeys.COMPASSBED.toString());
						sender.sendMessage(msg);

						return true;
					}
					/*
					 * this.getServer() .getPlayer(sender.getName())
					 * .sendMessage(
					 * "�cYou currently do not have a bed! (Was it destroyed or did someone else sleep in it?) \n"
					 * +
					 * "�2If you have a mailbox, you can try /setcompass mailbox"
					 * );
					 */

					String msg = getTranslationLanguage(sender,
							stringKeys.COMPASSBEDDESTROYED.toString());
					sender.sendMessage(msg);

					return true;
				}

				if (arg3[0].toLowerCase().equals("mailbox")) {
					for (int i = 0; i < mailBoxes.size(); i++) {
						if (sender.getName().equals(mailBoxes.get(i).name)) {
							Bukkit.getServer()
									.getPlayer(sender.getName())
									.setCompassTarget(mailBoxes.get(i).location);
							/*
							 * Bukkit.getServer() .getPlayer(sender.getName())
							 * .sendMessage(
							 * "�2Set compass to point to your mailbox!");
							 */

							String msg = getTranslationLanguage(sender,
									stringKeys.COMPASSMAILBOX.toString());
							sender.sendMessage(msg);

							return true;
						}
					}

					String msg = getTranslationLanguage(sender,
							stringKeys.COMPASSNOMAILBOX.toString());
					sender.sendMessage(msg);

					/*
					 * Bukkit.getServer() .getPlayer(sender.getName())
					 * .sendMessage(
					 * "�cYou don't have a mailbox!  \n�2Make one at your home!  (Chest on top of a fence post)"
					 * );
					 */
					return true;
				}

				if (arg3[0].toLowerCase().equals("here")) {
					Bukkit.getServer()
							.getPlayer(sender.getName())
							.setCompassTarget(
									Bukkit.getServer()
											.getPlayer(sender.getName())
											.getLocation());

					String msg = getTranslationLanguage(sender,
							stringKeys.COMPASSHERE.toString());
					sender.sendMessage(msg);
					/*
					 * Bukkit.getServer() .getPlayer(sender.getName())
					 * .sendMessage(
					 * "�2Set compass to point to your current location!");
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
				 * sender.sendMessage("�2Set compass to point to X: �3" +
				 * l.getX() + "�2 Y: �3" + l.getY() + "�2 Z: �3" + l.getZ());
				 */
				Bukkit.getPlayer(sender.getName()).setCompassTarget(l);
				return true;
			}
		} else {
			noPerms(sender);
			return true;
		}
		return false;
	}

	private boolean setChatColor(CommandSender sender, String[] arg3) {

		if (sender.hasPermission("SerenityPlugins.setchatcolor")) {
			String color = "";
			Player p = Bukkit.getPlayerExact(sender.getName());
			if (arg3.length == 0) {
				return false;
			}

			for (int i = 0; i < arg3.length; i++) {
				color += arg3[i] + " ";
			}

			if (color.equalsIgnoreCase("black ")) {
				chatcolorCfg.getConfig().set(
						"ChatColor." + p.getUniqueId().toString(), "�0");

			} else if (color.equalsIgnoreCase("dark blue ")) {
				chatcolorCfg.getConfig().set(
						"ChatColor." + p.getUniqueId().toString(), "�1");

			} else if (color.equalsIgnoreCase("dark green ")) {
				chatcolorCfg.getConfig().set(
						"ChatColor." + p.getUniqueId().toString(), "�2");

			} else if (color.equalsIgnoreCase("dark aqua ")) {
				chatcolorCfg.getConfig().set(
						"ChatColor." + p.getUniqueId().toString(), "�3");

			} else if (color.equalsIgnoreCase("dark red ")) {
				chatcolorCfg.getConfig().set(
						"ChatColor." + p.getUniqueId().toString(), "�4");

			} else if (color.equalsIgnoreCase("dark purple ")) {
				chatcolorCfg.getConfig().set(
						"ChatColor." + p.getUniqueId().toString(), "�5");

			} else if (color.equalsIgnoreCase("gold ")) {
				chatcolorCfg.getConfig().set(
						"ChatColor." + p.getUniqueId().toString(), "�6");

			} else if (color.equalsIgnoreCase("gray ")) {
				chatcolorCfg.getConfig().set(
						"ChatColor." + p.getUniqueId().toString(), "�7");

			} else if (color.equalsIgnoreCase("dark gray ")) {
				chatcolorCfg.getConfig().set(
						"ChatColor." + p.getUniqueId().toString(), "�8");

			} else if (color.equalsIgnoreCase("blue ")) {
				chatcolorCfg.getConfig().set(
						"ChatColor." + p.getUniqueId().toString(), "�9");

			} else if (color.equalsIgnoreCase("green ")) {
				chatcolorCfg.getConfig().set(
						"ChatColor." + p.getUniqueId().toString(), "�a");

			} else if (color.equalsIgnoreCase("aqua ")) {
				chatcolorCfg.getConfig().set(
						"ChatColor." + p.getUniqueId().toString(), "�b");

			} else if (color.equalsIgnoreCase("red ")) {
				chatcolorCfg.getConfig().set(
						"ChatColor." + p.getUniqueId().toString(), "�c");

			} else if (color.equalsIgnoreCase("light purple ")) {
				chatcolorCfg.getConfig().set(
						"ChatColor." + p.getUniqueId().toString(), "�d");

			} else if (color.equalsIgnoreCase("yellow ")) {
				chatcolorCfg.getConfig().set(
						"ChatColor." + p.getUniqueId().toString(), "�e");

			} else if (color.equalsIgnoreCase("white ")) {
				chatcolorCfg.getConfig().set(
						"ChatColor." + p.getUniqueId().toString(), "�f");

			} else if (color.equalsIgnoreCase("scary ")) {
				chatcolorCfg.getConfig().set(
						"ChatColor." + p.getUniqueId().toString(), "�k");

			} else {
				String msg = getTranslationLanguage(sender,
						stringKeys.CHATCOLORINVALID.toString());
				sender.sendMessage(msg);
				/*
				 * sender.sendMessage("�4Invalid color!  Here are your options:\n�a"
				 * +
				 * 
				 * "black, dark blue, dark green,\n" +
				 * "dark aqua, dark red, dark purple,\n" +
				 * "gold, gray, dark gray, blue,\n" + "green, aqua, red,\n" +
				 * "light purple, yellow, white.");
				 */
			}

			chatcolorCfg.saveConfig();
			chatcolorCfg.reloadConfig();

			return true;
		} else {

			String msg = getTranslationLanguage(sender,
					stringKeys.CHATCOLOREARLY.toString());
			sender.sendMessage(msg);

			// sender.sendMessage("�2You can't set your chatcolor yet... Stick around and eventually you will!");
			return true;
		}
	}

	private boolean lctoggle(CommandSender sender, String[] arg3) {
		Player p = Bukkit.getPlayer(sender.getName());
		if (localChatters.contains(p)) {
			localChatters.remove(p);

			String msg = getTranslationLanguage(sender,
					stringKeys.CHATPUBLICNOW.toString());
			sender.sendMessage(msg);

			// p.sendMessage("�2You are now chatting publicly!");
			return true;
		}
		localChatters.add(p);

		String msg = getTranslationLanguage(sender,
				stringKeys.CHATLOCALNOW.toString());
		sender.sendMessage(msg);

		// p.sendMessage("�2You are now chatting locally (100 blocks or closer)!");
		return true;
	}

	private boolean lag(CommandSender sender) {

		if (lags.size() < 10) {

			String msg = getTranslationLanguage(sender,
					stringKeys.LAGWAIT.toString());
			sender.sendMessage(msg);

			// sender.sendMessage("�cWait!");
			return true;
		}

		double tickrate = getTickrate();

		getLogger().info("tickrate:" + tickrate);
		if (tickrate > 20) {
			tickrate = 20.0;
		}

		String message = "�aAverage TPS: �e"
				+ new DecimalFormat("##.##").format(tickrate) + " �f| ";

		if (tickrate < 16) {

			message += "�4"
					+ new DecimalFormat("##.##").format((tickrate / 20) * 100)
					+ "%�c speed �f| ";
			return true;
		}
		if (tickrate < 19) {
			message += "�e"
					+ new DecimalFormat("##.##").format((tickrate / 20) * 100)
					+ "%�3 speed �f| ";
			return true;
		} else {
			message += "�2"
					+ new DecimalFormat("##.##").format((tickrate / 20) * 100)
					+ "%�3 speed �f| ";
		}

		if (sender instanceof Player) {
			Player p = (Player) sender;
			CraftPlayer cp = (CraftPlayer) p;
			message += "�2Your ping: �e" + cp.getHandle().ping + "�2ms";
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
		if (sender.hasPermission("SerenityPlugins.ignore")) {
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

						// sender.sendMessage("�cI could not find that player");
						return true;
					}

					for (int i = 0; i < ignorers.size(); i++) {
						if (ignorers.get(i).player.equals(((Player) sender)
								.getPlayer())) {
							for (int j = 0; j < ignorers.get(i).ignoreList
									.size(); j++) {
								if (ignorers.get(i).ignoreList.get(j)
										.equalsIgnoreCase(arg3[0])) {
									ignorers.get(i).ignoreList.remove(j);

									String msg = getTranslationLanguage(sender,
											stringKeys.IGNORENOLONGERIGNORING
													.toString());
									sender.sendMessage(String.format(msg,
											arg3[0]));

									// sender.sendMessage("�2You are no longer ignoring �e"
									// + arg3[0]);
									return true;
								}
							}
							ignorers.get(i).ignoreList.add(arg3[0]);

							String msg = getTranslationLanguage(sender,
									stringKeys.IGNORESUCCESS.toString());
							sender.sendMessage(String.format(msg, arg3[0]));

							// sender.sendMessage("�2You are ignoring �e"
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
					// sender.sendMessage("�2You are now ignoring �e" +
					// arg3[0]);
					return true;

				}
			}
			return false;
		} else {
			noPerms(sender);
			return true;
		}
	}

	private boolean vote(CommandSender sender, String[] arg3) {

		if (sender.hasPermission("SerenityPlugins.vote")) {
			sender.sendMessage("No current vote");
			return true;
		}/*
		 * if (arg3.length == 0) { sender.sendMessage(
		 * "�3Do you want protected areas to still let players press buttons/levers/pressure plates?\n"
		 * + "�3Type �f/vote YES or\n" + "Type �f/vote NO"); return true; }
		 * 
		 * if (arg3.length > 0) { if (arg3[0].equalsIgnoreCase("yes")) {
		 * sender.sendMessage
		 * ("�aYour vote was cast!  Type �f/vote results �ato see current results"
		 * ); getConfig().set("VoteProt." + sender.getName(), "yes");
		 * saveConfig(); reloadConfig(); return true; } if
		 * (arg3[0].equalsIgnoreCase("no")) { sender.sendMessage(
		 * "�aYour vote was cast!  Type �f/vote results �ato see current results"
		 * ); getConfig().set("VoteProt." + sender.getName(), "no");
		 * saveConfig(); reloadConfig(); return true; } if
		 * (arg3[0].equalsIgnoreCase("results")) { ConfigurationSection
		 * votesFromConfig = this.getConfig()
		 * .getConfigurationSection("VoteProt");
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
		 * sender.sendMessage("�3Total votes: �b" + (votesToReset + votesToKeep)
		 * + "\n�5YES: �2" + new DecimalFormat("##.##") .format((double)
		 * ((double) votesToReset / (double) (votesToKeep + votesToReset) *
		 * 100.00)) + "%" + "\n�5NO:  �2" + new DecimalFormat("##.##")
		 * .format((double) ((double) votesToKeep / (double) (votesToKeep +
		 * votesToReset) * 100.00)) + "%"); return true; }
		 * 
		 * return true; } }
		 */else {
			noPerms(sender);
			return true;
		}
	}

	private void hideInvisiblePlayers(Player player) {
		for (int i = 0; i < invisiblePlayers.size(); i++) {
			player.hidePlayer(invisiblePlayers.get(i));
			invisiblePlayers.get(i).sendMessage(
					"You are hidden from " + player.getDisplayName());
		}
	}

	private void addPlayerStatus(PlayerStatus ps) {
		String[] status = { ps.getTime().getTimeInMillis() + "", ps.getStatus() };
		playerStatuses.add(ps);
		statusCfg.getConfig().set("Status." + ps.getName(), status);
		statusCfg.saveConfig();
		statusCfg.reloadConfig();
	}

	private void deletePlayerStatus(String playerName) {
		statusCfg.getConfig().set("Status." + playerName, null);
		Iterator<PlayerStatus> it = playerStatuses.iterator();
		PlayerStatus toDelete = new PlayerStatus("placeholder", "placeholder",
				new GregorianCalendar());
		while (it.hasNext()) {
			PlayerStatus ps = it.next();
			if (ps.getName().equals(playerName)) {
				toDelete = ps;
			}
		}

		playerStatuses.remove(toDelete);
		statusCfg.saveConfig();
		statusCfg.reloadConfig();
	}

	public void noPerms(CommandSender p) {

		String msg = getTranslationLanguage(p, stringKeys.NOPERMS.toString());
		p.sendMessage(msg);
	}

	@EventHandler
	public void onExplosion(EntityExplodeEvent event) {
		for (Mailbox mb : mailBoxes) {
			if (event.blockList().contains(mb.getLocation().getBlock())) {
				event.setCancelled(true);
				return;
			}
		}
	}

	@EventHandler
	public void onChestPlace(BlockPlaceEvent event) {
		if (event.getBlock().getType().equals(Material.CHEST)
				|| event.getBlock().getType().equals(Material.TRAPPED_CHEST)) {
			for (int i = 0; i < mailBoxes.size(); i++) {
				if (event.getBlock().equals(
						mailBoxes.get(i).getLocation().getBlock()
								.getRelative(BlockFace.NORTH))
						|| event.getBlock().equals(
								mailBoxes.get(i).getLocation().getBlock()
										.getRelative(BlockFace.SOUTH))
						|| event.getBlock().equals(
								mailBoxes.get(i).getLocation().getBlock()
										.getRelative(BlockFace.EAST))
						|| event.getBlock().equals(
								mailBoxes.get(i).getLocation().getBlock()
										.getRelative(BlockFace.WEST))) {
					event.setCancelled(true);

					String msg = getTranslationLanguage(event.getPlayer(),
							stringKeys.MAILTRIEDTOEXPANDMAILBOX.toString());
					event.getPlayer().sendMessage(msg);

					/*
					 * event.getPlayer().sendMessage(
					 * "�cSorry, you cannot expand mailboxes!");
					 */
					return;
				}
			}
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
					 * "�2You made a fireworks show block!  \nWhen it is gold, right click it for a show!"
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
					 * "�cYou already have a fireworks show block in the �3" +
					 * fws.getLocation().getWorld().getName() + "�c at: " +
					 * "\n�3X:�2 " + l.getX() + "\n�3Y:�2 " + l.getY() +
					 * "\n�3Z:�2 " + l.getZ() +
					 * "\n�cPlease destroy that one first!");
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
							String s = "�cIt's cooling down!";
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
		for (int i = 0; i < fireworkShowLocations.size(); i++) {
			Block b = fireworkShowLocations.get(i).getLocation().getBlock();
			if (event.getBlocks().contains(b)) {
				getLogger().info("Attempted to push a firework block");
				event.setCancelled(true);
			}
		}
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
					 * "�cYou may not break a fireworks block that is cooling down!"
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
						 * "�cOnly this fireworks block's creator can destroy this block!"
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
						 * "�2You destroyed your fireworks show block");
						 */
					}
				}
			}
		}
	}

	@EventHandler
	public void onDungeonKill(EntityDeathEvent event) {
		if (event.getEntity().getCustomName() != null) {
			if (event.getEntity().getCustomName().contains("�c" + "")) {
				event.setDroppedExp(0);
				event.getDrops().clear();
				ParticleEffect.FIREWORKS_SPARK.display(.25F, .25F, .25F, .25F,
						20, event.getEntity().getEyeLocation(), 15);
				finalDungeonKillCount++;

				// round 2
				if (finalDungeonKillCount == 20) {
					for (int i = 0; i < 6; i++) {
						spawn1EntityInEachCorner(Secrets.Secret.ENTITYTYPEROUND1,
								new PotionEffect(PotionEffectType.SPEED,
										999999, 1), new PotionEffect(
										PotionEffectType.HEAL, 10, 1), "�c"
										+ Secrets.Secret.ENTITYROUND1NAME);
					}
				}

				if (finalDungeonKillCount == 44) {
					for (int i = 0; i < 6; i++) {
						spawn1EntityInEachCorner(Secrets.Secret.ENTITYTYPEROUND2,
								new PotionEffect(PotionEffectType.SPEED,
										999999, 0), "�c" + Secrets.Secret.ENTITYROUND2NAME);
					}
				}

				if (finalDungeonKillCount == 68) {
					for (int i = 0; i < 4; i++) {
						spawn1EntityInEachCorner(Secrets.Secret.ENTITYTYPEROUND3,
								new PotionEffect(PotionEffectType.SPEED,
										999999, 4), "�c" + Secrets.Secret.ENTITYROUND3NAME);
					}

				}

				if (finalDungeonKillCount == 84) {
					for (int i = 0; i < 2; i++) {
						spawn1EntityInEachCorner(Secrets.Secret.ENTITYTYPEROUND4,
								new PotionEffect(
										PotionEffectType.INCREASE_DAMAGE,
										999999, 2), "�c" + Secrets.Secret.ENTITYROUND4NAME);
					}
				}

				if (finalDungeonKillCount == 92) {
					for (int i = 0; i < 2; i++) {
						spawn1EntityInEachCornerWithDiaArmor(
								Secrets.Secret.ENTITYTYPEROUND5, new PotionEffect(
										PotionEffectType.SPEED, 999999, 0),
								"�c" + Secrets.Secret.ENTITYROUND5NAME);
					}
				}

				if (finalDungeonKillCount == 100) {
					for (int i = 0; i < 3; i++) {
						spawn1EntityInEachCornerWithDiaArmor(Secrets.Secret.ENTITYTYPEROUND6,
								new PotionEffect(PotionEffectType.SPEED,
										999999, 0), "�c" + Secrets.Secret.ENTITYTYPEROUND6);
					}
				}

				if (finalDungeonKillCount == 112) {
					for (int i = 0; i < 1; i++) {
						spawn1EntityInCenter(Secrets.Secret.ENTITYTYPEROUND7,
								new PotionEffect(PotionEffectType.SPEED,
										999999, 0), "�c" + Secrets.Secret.ENTITYROUND7NAME);
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
					if (enemy instanceof LivingEntity) {
						LivingEntity le = (LivingEntity) enemy;
					}
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
								"�4" + finalSlayers.get(0)
										+ "�5 is a dragon slayer!");
					} else {
						String s = "�4";
						for (int i = 0; i < finalSlayers.size(); i++) {
							s += finalSlayers.get(i) + " & ";
						}

						s = s.substring(0, s.length() - 2);

						s += "�5are dragon slayers!";
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
								}

							}
							count++;
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

	public void cleanConfig() {
		for (OfflinePlayer op : Bukkit.getServer().getOfflinePlayers()) {
			if (!op.isWhitelisted()) {
				if (!op.isOp()) {

					if (getPlayerMinutes(op.getName()) != 0) {
						Bukkit.getLogger().info(
								"You should clean " + op.getName()
										+ " because they are not whitelisted");
						// cleanConfig(op.getName());
					}
				}
			}

			if (op.isWhitelisted()) {
				if (getPlayerMinutes(op.getName()) < 60) {
					if (getLastSeen(op.getName()) > 10080) {
						Bukkit.getLogger().info(
								"Unwhitelisting " + op.getName());
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
								"whitelist remove " + op.getName());
						// cleanConfig(op.getName());
					}
				}

				if (getPlayerMinutes(op.getName()) < 180) {
					if (getLastSeen(op.getName()) > 20160) {
						Bukkit.getLogger().info(
								"Unwhitelisting " + op.getName());
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
								"whitelist remove " + op.getName());
						// cleanConfig(op.getName());
					}
				}

				if (getPlayerMinutes(op.getName()) < 720) {
					if (getLastSeen(op.getName()) > 43200) {
						Bukkit.getLogger().info(
								"Unwhitelisting " + op.getName());
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
								"whitelist remove " + op.getName());
						// cleanConfig(op.getName());
					}
				}

				if (getPlayerMinutes(op.getName()) >= 720) {
					if (getLastSeen(op.getName()) > 86400) {
						Bukkit.getLogger().info(
								"Unwhitelisting " + op.getName());
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
								"whitelist remove " + op.getName());
						// cleanConfig(op.getName());
					}
				}
			}

		}

		saveConfig();
		reloadConfig();
	}

	public void cleanConfig(String name) {
		setNull(playtimeCfg, "Playtime", name);
		setNull(statusCfg, "Status", name);
		setNull(mailboxCfg, "Mailboxes", name);
		setNull(chatcolorCfg, "ChatColor", name);
		setNull(fireworksCfg, "Fireworks", name);
		setNull(protectedAreasCfg, "ProtectedAreas", name);
	}

	public void setNull(ConfigAccessor config, String category, String name) {
		config.getConfig().set(category + "." + name, null);
		Bukkit.broadcastMessage(category + "." + name);
		config.saveConfig();
		config.reloadConfig();
		// this.getConfig().set(category + "." + name, null);
	}

	public int getPlayerMinutes(String name) {
		if (name.equals("secret1")) {
			return 900000;
		}
		if (name.equals("[Server]")) {
			return 540;
		}
		String UUID = Bukkit.getOfflinePlayer(name).getUniqueId().toString();
		return playtimeCfg.getConfig().getInt("Playtime." + UUID);
	}

	public Long getLastSeen(String name) {
		OfflinePlayer op = Bukkit.getOfflinePlayer(name);
		return TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis()
				- op.getLastPlayed());

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

	public String getHoursAndMinutes(String name) {
		int minutes = getPlayerMinutes(name);
		int hours = minutes / 60;
		minutes = minutes % 60;
		return "" + "�b" + hours + "�3" + " hours and " + "�b" + minutes + "�3"
				+ " minutes.";

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

	private String getTeam(Player player) {
		if (teamList.containsKey(player.getDisplayName())) {
			return teamList.get(player.getDisplayName());
		}
		return null;
	}

	@EventHandler
	public void EInteractEvent(PlayerInteractEntityEvent event) {
		if (event.getRightClicked().getCustomName() != null) {
			if (event.getRightClicked().getCustomName().equals(Secrets.Secret.NAMEOFANNPC)) {

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
										.equals(Secrets.Secret.F1)
										&& event.getPlayer().getItemInHand()
												.getDurability() == 2) {
									successfulFeedE(event.getPlayer());
									return;

								}
								break;
							case 1:
								if (event.getPlayer().getItemInHand().getType()
										.equals(Secrets.Secret.F1)
										&& event.getPlayer().getItemInHand()
												.getDurability() == 1) {
									successfulFeedE(event.getPlayer());
									return;
								}
								break;
							case 2:
								if (event.getPlayer().getItemInHand().getType()
										.equals(Secrets.Secret.F1)
										&& event.getPlayer().getItemInHand()
												.getDurability() == 3) {
									successfulFeedE(event.getPlayer());
									return;
								}
								break;
							case 3:
								if (event.getPlayer().getItemInHand().getType()
										.equals(Secrets.Secret.F2)) {
									successfulFeedE(event.getPlayer());
									return;
								}
								break;
							case 4:
								if (event.getPlayer().getItemInHand().getType()
										.equals(Secrets.Secret.F3)) {
									successfulFeedE(event.getPlayer());
									return;
								}
								break;
							case 5:
								if (event.getPlayer().getItemInHand().getType()
										.equals(Secrets.Secret.F4)) {
									successfulFeedE(event.getPlayer());
									return;
								}
								break;
							case 6:
								if (event.getPlayer().getItemInHand().getType()
										.equals(Secrets.Secret.F5)) {
									successfulFeedE(event.getPlayer());
									return;
								}
								break;

							case 7:
								if (event.getPlayer().getItemInHand().getType()
										.equals(Secrets.Secret.F6)) {
									successfulFeedE(event.getPlayer());
									return;
								}
								break;
							}
							sendSimulatedPrivateMessage(event.getPlayer(),
									Secrets.Secret.NAMEOFANNPCWOCOLOR,
									Secrets.Secret.HGY);
							return;
						} else {
							sendSimulatedPrivateMessage(event.getPlayer(),
									Secrets.Secret.NAMEOFANNPCWOCOLOR, Secrets.Secret.BGY);
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
			if (event.getRightClicked().getCustomName().equals(Secrets.Secret.NAMENPC2COLOR)) {
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
											.equals(Secrets.Secret.SECRETWCDNAME)) {
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
												startAThing(event
														.getPlayer());
												return;
											}
										}
									}
								}
							}
						}
					}
					sendSimulatedPrivateMessage(event.getPlayer(), Secrets.Secret.NAMENPC2NOCOLOR,
							Secrets.Secret.WONTGET);
					return;
				}
			}
		}
	}

	private void startAThing(Player p) {

		final Player player = p;
		final Location scareLightning = new Location(Bukkit.getWorld("world"),
				-1105.003, 116.00, -2867.7);

		final World w = p.getWorld();
		final String name = Secrets.Secret.NAMENPC2COLOR;
		final String scared = Secrets.Secret.OHASNST;
		final String thndrsn =Secrets.Secret.OHATSNST;
		final String done = Secrets.Secret.MKSTOP;

		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			@Override
			public void run() {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
						Secrets.Secret.WTHR);
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
							if (e.getCustomName().equals(Secrets.Secret.NAMENPC2COLOR)) {
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

		meta.setTitle(Secrets.Secret.MYSTERYBOOK3TITLE);
		meta.setAuthor(Secrets.Secret.MYSTERYBOOKAUTHOR);
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
		final Location bookLoc = Secrets.Secret.B3LOCLOC;
		final Location loc = Secrets.Secret.B3LOC;
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
		im.setDisplayName(Secrets.Secret.TPA);

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
	public void onPlayerPlaceSS(BlockPlaceEvent event) {
		if (event.getBlock().getType() == Material.SOUL_SAND) {
			if (event.getPlayer().getItemInHand().hasItemMeta()) {
				if (event.getPlayer().getItemInHand().getItemMeta().hasLore()) {
					ItemStack is = event.getPlayer().getItemInHand();

					if (is.getItemMeta().hasDisplayName()
							&& is.getItemMeta().getDisplayName()
									.equals(Secrets.Secret.ss)) {
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
										Secrets.Secret.MYSTERYBOOKAUTHOR,
										" �rTh�ka�ra�kll�rnk y�kl�rou�kljk");
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

	public void putBookAndStuffInMailbox(String name) {
		for (Mailbox mb : mailBoxes) {
			if (mb.name.equals(name)) {
				Chest receivingChest = (Chest) mb.getLocation().getBlock()
						.getState();

				ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
				BookMeta meta = (BookMeta) book.getItemMeta();

				meta.setTitle(Secrets.Secret.TFP);
				meta.setAuthor("�d[Server]");

				String bookText = podrickCfg.getConfig().getString(
						"ThankYouBook", "oh no");

				List<String> pages = new ArrayList<String>();
				int l = 0;
				int lineCount = 0;
				String page = "";
				for (int i = 0; i < bookText.length(); i++) {
					if (bookText.charAt(i) == '^') {
						page += '�';
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
				im.setDisplayName(Secrets.Secret.DSS);
				is.setItemMeta(im);
				receivingChest.getInventory().addItem(is);

				ItemStack isl = new ItemStack(Material.LEASH);
				receivingChest.getInventory().addItem(isl);

			}
		}
	}

	@EventHandler
	public void onPlayerSSA(BlockPlaceEvent event) {
		if (event.getBlock().getType() == Material.SOUL_SAND) {
			if (event.getPlayer().getItemInHand().hasItemMeta()) {
				ItemStack is = event.getPlayer().getItemInHand();
				if (is.getItemMeta().hasDisplayName()
						&& is.getItemMeta().getDisplayName()
								.equals(Secrets.Secret.DSS)) {
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
						cow.setCustomName("�6" + name);
						if (rand.nextBoolean())
							cow.setBaby();
						cow.setAgeLock(true);

						break;
					case 1:
						Pig pig = (Pig) p.getLocation().getWorld()
								.spawnEntity(p.getLocation(), EntityType.PIG);
						pig.setCustomName("�6" + name);
						if (rand.nextBoolean())
							pig.setBaby();
						pig.setAgeLock(true);
						break;
					case 2:
						MushroomCow mc = (MushroomCow) p
								.getLocation()
								.getWorld()
								.spawnEntity(p.getLocation(),
										EntityType.MUSHROOM_COW);
						mc.setCustomName("�6" + name);
						if (rand.nextBoolean())
							mc.setBaby();
						mc.setAgeLock(true);
						break;
					case 3:
						Chicken c = (Chicken) p
								.getLocation()
								.getWorld()
								.spawnEntity(p.getLocation(),
										EntityType.CHICKEN);

						c.setCustomName("�6" + name);
						if (rand.nextBoolean())
							c.setBaby();
						c.setAgeLock(true);
						break;
					case 4:
						Rabbit rab = (Rabbit) p
								.getLocation()
								.getWorld()
								.spawnEntity(p.getLocation(), EntityType.RABBIT);
						rab.setCustomName("�6" + name);
						if (rand.nextBoolean())
							rab.setBaby();
						rab.setAgeLock(true);
						break;
					case 5:
						Sheep s = (Sheep) p.getLocation().getWorld()
								.spawnEntity(p.getLocation(), EntityType.SHEEP);
						s.setCustomName("�6" + name);
						if (rand.nextBoolean())
							s.setBaby();
						s.setAgeLock(true);
						break;
					case 6:
						Ocelot ocelot = (Ocelot) p
								.getLocation()
								.getWorld()
								.spawnEntity(p.getLocation(), EntityType.OCELOT);
						ocelot.setCustomName("�6" + name);
						ocelot.setTamed(true);
						AnimalTamer at = (AnimalTamer) p;
						if (rand.nextBoolean())
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

					}
				}
			}
		}
	}

	@EventHandler
	public void PlayerDeathColor(PlayerDeathEvent event) {
		event.setDeathMessage(getChatColor(event.getEntity())
				+ event.getDeathMessage());
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
									p.sendMessage("�cYou must complete the rest of the quest first!");
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
					event.setDeathMessage(event.getDeathMessage()
							+ "\n�cThe enemies vanished and revived their fallen comrades!");
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

			if (event.getRightClicked().getCustomName().equals(Secrets.Secret.NPCNETHCOL)) {
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
							hint = Secrets.Secret.H1;
							break;
						case 1:
							hint = Secrets.Secret.H2;
							break;
						case 2:
							hint = Secrets.Secret.H3;
							break;
						case 3:
							hint = Secrets.Secret.H4;
							break;
						case 4:
							hint = Secrets.Secret.H5;
							break;
						case 5:
							hint = Secrets.Secret.H6;
							break;
						case 6:
							hint = Secrets.Secret.H7;
							break;
						}

						sendSimulatedPrivateMessage(event.getPlayer(), Secrets.Secret.NPCNETHNOCOL,
								Secrets.Secret.IDW + hint);

					} else {
						sendSimulatedPrivateMessage(event.getPlayer(), Secrets.Secret.NPCNETHNOCOL,
								Secrets.Secret.BSE);
					}
				}
				event.setCancelled(true);
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
			if (event.getRightClicked().getCustomName().contains("�6")) {
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
				sendSimulatedPrivateMessage(player, Secrets.Secret.NAMEOFANNPCWOCOLOR,
						Secrets.Secret.SSE);
				if (player.getItemInHand().getAmount() != 1) {
					player.getItemInHand().setAmount(
							player.getItemInHand().getAmount() - 1);
				} else {
					player.setItemInHand(null);
				}

				ItemStack is = new ItemStack(Material.PRISMARINE_CRYSTALS);
				ItemMeta im = is.getItemMeta();
				im.setDisplayName(Secrets.Secret.DECCRYST);

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
				sendSimulatedPrivateMessage(player, Secrets.Secret.NPCNETHNOCOL,
						Secrets.Secret.SSA);
				if (player.getItemInHand().getAmount() != 1) {
					player.getItemInHand().setAmount(
							player.getItemInHand().getAmount() - 1);
				} else {
					player.setItemInHand(null);
				}

				ItemStack is = new ItemStack(Material.SOUL_SAND);
				ItemMeta im = is.getItemMeta();
				im.setDisplayName(Secrets.Secret.ss);

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
	private void hopperMailboxAttempt(BlockPlaceEvent event) {
		if (event.getBlock().getType() == Material.HOPPER
				|| event.getBlock().getType() == Material.HOPPER_MINECART) {

			for (Mailbox mb : mailBoxes) {
				if (mb.location.equals(event.getBlock()
						.getRelative(BlockFace.UP).getLocation())) {
					if (PUBLIC_MAILBOXES.contains(mb.name)) {
						return;
					}
					if (!mb.getName()
							.equals(event.getPlayer().getDisplayName())) {
						event.setCancelled(true);
						return;
					}
				}
			}
		}
	}

	private void sendSimulatedPrivateMessage(Player player, String displayName,
			String message) {
		player.sendMessage("�oFrom �6�o�l" + displayName + ": �r" + message);
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

}