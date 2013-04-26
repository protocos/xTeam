package me.protocos.xteam.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.util.CommonUtil;
import me.protocos.xteam.util.FileReader;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;

public class Data
{
	public static Server BUKKIT = Bukkit.getServer();
	public static boolean variable = false;
	public static File settings;
	public static TreeSet<String> chatStatus = new TreeSet<String>();
	public static HashSet<String> spies = new HashSet<String>();
	public static HashSet<String> damagedByPlayer = new HashSet<String>();
	public static HashMap<Player, Location> returnLocations = new HashMap<Player, Location>();
	public static HashMap<String, Integer> taskIDs = new HashMap<String, Integer>();
	public static HashMap<String, Integer> countWaitTime = new HashMap<String, Integer>();
	public static HashMap<String, Long> hasTeleported = new HashMap<String, Long>();
	public static HashMap<String, Long> lastAttacked = new HashMap<String, Long>();
	public static HashMap<String, Long> lastCreated = new HashMap<String, Long>();
	public static HashMap<String, Team> invites = new HashMap<String, Team>();
	public static boolean SPOUT_ENABLED;
	public static boolean LOCATIONS_ENABLED;
	public static boolean CAN_CHAT;
	public static boolean HIDE_NAMES;
	public static boolean HQ_ON_DEATH;
	public static boolean TEAM_WOLVES;
	public static boolean RANDOM_TEAM;
	public static boolean BALANCE_TEAMS;
	public static boolean DEFAULT_TEAM_ONLY;
	public static boolean DEFAULT_HQ_ON_JOIN;
	public static boolean TEAM_TAG_ENABLED;
	public static boolean TEAM_FRIENDLY_FIRE;
	public static boolean NO_PERMISSIONS;
	public static boolean ALPHA_NUM;
	public static boolean DISPLAY_COORDINATES;
	public static int MAX_PLAYERS;
	public static int REVEAL_TIME;
	public static int HQ_INTERVAL;
	public static int TELE_RADIUS;
	public static int ENEMY_PROX;
	public static int TELE_DELAY;
	public static int CREATE_INTERVAL;
	public static int LAST_ATTACKED_DELAY;
	public static int TEAM_TAG_LENGTH;
	public static int MAX_NUM_LOCATIONS;
	public static int REFRESH_DELAY;
	public static String TAG_COLOR;
	public static String NAME_COLOR;
	public static List<String> DEFAULT_TEAM_NAMES = new ArrayList<String>();
	public static List<String> DISABLED_WORLDS = new ArrayList<String>();

	private Data()
	{
		//we don't want the default constructor to be called from within the class either...
		throw new AssertionError();
	}
	public static void defaultConfig(File f) throws IOException
	{
		BufferedWriter bw = new BufferedWriter(new FileWriter(f));
		bw.write("############################################\n" +
				"# \n" +
				"# \n" +
				"# Bukkit preferences\n" +
				"# \n" +
				"# \n" +
				"### playersonteam - Amount of players that can be on a team. (default=10)\n" +
				"### sethqinterval - Delay in hours between use of /team sethq (default=0)\n" +
				"### teleportradius - Maximum distance in blocks between team mates to teleport to one another (default=500)\n" +
				"### canteamchat - Allows/Disallows the use of team chat function completely (default=true)\n" +
				"### enemyproximity - When teleporting, if enemies are within this radius of blocks, the teleport is delayed (default=16)\n" +
				"### teledelay - Delay in seconds for teleporting when enemies are near (default=10)\n" +
				"### telerefreshdelay - Delay in seconds for when you can use team teleporting. Does not include /team return (default=60)\n" +
				"### createteamdelay - Delay in minutes for creating teams (default=20)\n" +
				"### teamwolves - Protects your wolfies from you and your teammates from damaging them (default=true)\n" +
				"### defaultteams - Default list of teams for the server separated by commas (e.g. defaultteams=red,green,blue,yellow)\n" +
				"### randomjointeam - Player randomly joins one of the default teams on joining (default=false)\n" +
				"### balanceteams - Balance teams when someone randomly joins (default=false)\n" +
				"### onlyjoindefaultteam - When true, players can only join one of the default teams listed above (default=false)\n" +
				"### defaulthqonjoin - When true, players on default teams are teleported to their headqloaduarters on join (default=false)\n" +
				"### lastattackeddelay - How long a teamPlayer has to wait after being attacked to teleport (default=15)\n" +
				"### teamtagenabled - When true, players have their team tag displayed when in chat (default=true)\n" +
				"### teamtagmaxlength - Maximum length of a team tag (default=0 == no maximum tag length)\n" +
				"### disabledworlds - World names, separated by commas, that xTeam is disabled in (e.g. disabledworlds=world,world_nether,world_the_end)\n" +
				"### nopermissions - When true, xTeam will give all regular commands to players and admin commands to OPs (default=false)\n" +
				"### alphanumericnames - When true, players can only create teams with alphanumeric names and no symbols (i.e. TeamAwesome123) (default=false)\n" +
				"### displaycoordinates - When true, players can see coordinates of other team mates in team info (default=true)\n" +
				"### tagcolor - Color representing the color of the tag in game (e.g. green, dark_red, light_purple)\n" +
				"### chatnamecolor - Color representing the color of player names in team chat (e.g. green, dark_red, light_purple)\n" +
				"############################################\n" +
				"playersonteam=10\n" +
				"sethqinterval=0\n" +
				"hqondeath=true\n" +
				"teleportradius=500\n" +
				"canteamchat=true\n" +
				"enemyproximity=16\n" +
				"teledelay=10\n" +
				"telerefreshdelay=60\n" +
				"createteamdelay=20\n" +
				"teamwolves=true\n" +
				"defaultteams=\n" +
				"randomjointeam=false\n" +
				"balanceteams=false\n" +
				"onlyjoindefaultteam=false\n" +
				"defaulthqonjoin=false\n" +
				"teamtagenabled=true\n" +
				"teamtagmaxlength=0\n" +
				"teamfriendlyfire=false\n" +
				"lastattackeddelay=15\n" +
				"disabledworlds=\n" +
				"nopermissions=false\n" +
				"alphanumericnames=false\n" +
				"displaycoordinates=true\n" +
				"tagcolor=green\n" +
				"chatnamecolor=dark_green\n" +
				"############################################\n" +
				"# \n" +
				"# \n" +
				"# Locations preferences (should you choose to add xTeamLocations to your server)\n" +
				"# \n" +
				"# \n" +
				"### maxnumlocations - Maximum number of locations that can be stored per team (default=9)\n" +
				"############################################\n" +
				"maxnumlocations=9\n" +
				"############################################\n" +
				"# \n" +
				"# \n" +
				"# Spout preferences (should you choose to add spout to your server)\n" +
				"# \n" +
				"# \n" +
				"### teamhidename - If enabled players can only see teammates names and players with no team at all (default=true)\n" +
				"### namerevealtime - Amount in seconds that a teamPlayer name is revealed if attacked by another teamPlayer (default=5)\n" +
				"############################################\n" +
				"teamhidename=true\n" +
				"namerevealtime=5\n" +
				"############################################\n" +
				"# \n" +
				"# \n" +
				"# Permission Nodes\n" +
				"# \n" +
				"# \n" +
				"### xteam.player.core.accept - (Allows players to accept an invitation)\n" +
				"### xteam.player.core.chat - (Allows players to chat/msg their team)\n" +
				"### xteam.player.core.create - (Allows players to create a team)\n" +
				"### xteam.player.core.hq - (Allows players to teleport to headquarters)\n" +
				"### xteam.player.core.join - (Allows players to join a team)\n" +
				"### xteam.player.core.leave - (Allows players to leave a team)\n" +
				"### xteam.player.core.list - (Allows players to list all team names)\n" +
				"### xteam.player.core.return - (Allows players to return to teleport location)\n" +
				"### xteam.player.core.tele - (Allows players to teleport to teammates)\n" +
				"### xteam.admin.core.invite - (Allows players to invite other players)\n" +
				"### xteam.admin.core.promote - (Allows players to promote teammates)\n" +
				"### xteam.admin.core.sethq - (Allows players to set a headquarters)\n" +
				"### xteam.leader.core.demote - (Allows players to demote teammates)\n" +
				"### xteam.leader.core.open - (Allows players to open team to public)\n" +
				"### xteam.leader.core.remove - (Allows players to remove players)\n" +
				"### xteam.leader.core.rename - (Allows players to rename team)\n" +
				"### xteam.leader.core.tag - (Allows players to set team tag)\n" +
				"### xteam.leader.core.setleader - (Allows players to set leader of team)\n" +
				"### xteam.serveradmin.core.chatspy - (Allows players to spy on team chat)\n" +
				"### xteam.serveradmin.core.delete - (Allows players to delete a team)\n" +
				"### xteam.serveradmin.core.demote - (Allows players to demote a teamPlayer on a team)\n" +
				"### xteam.serveradmin.core.hq - (Allows players to teleport to any headquarters)\n" +
				"### xteam.serveradmin.core.promote - (Allows players to promote a teamPlayer on a team)\n" +
				"### xteam.serveradmin.core.reload - (Allows players to reload the configuration file)\n" +
				"### xteam.serveradmin.core.remove - (Allows players to remove teamPlayer from a team)\n" +
				"### xteam.serveradmin.core.rename - (Allows players to rename a team)\n" +
				"### xteam.serveradmin.core.tag - (Allows players to set team tag)\n" +
				"### xteam.serveradmin.core.open - (Allows players to open a team to public)\n" +
				"### xteam.serveradmin.core.set - (Allows players to set the team of a teamPlayer)\n" +
				"### xteam.serveradmin.core.sethq - (Allows players to set the headquarters of a team)\n" +
				"### xteam.serveradmin.core.setleader - (Allows players to set the leader of a team)\n" +
				"### xteam.serveradmin.core.teleallhq - (Allows players to teleport everyone to their headquarters)\n" +
				"### xteam.serveradmin.core.tpall - (Allows players to teleport a team to current location)\n" +
				"### xteam.serveradmin.core.update - (Allows players to update teamPlayer names for Spout features)\n" +
				"############################################\n");
		bw.close();
	}
	public static void initFileSystem()
	{
		File f = new File("plugins/xTeam/");
		if (!f.exists())
		{
			f.mkdir();
		}
		f = new File("plugins/xTeam/teams.txt");
		if (!f.exists())
		{
			try
			{
				f.createNewFile();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		f = new File("plugins/xTeam/xTeam.cfg");
		if (!f.exists())
		{
			try
			{
				f.createNewFile();
				defaultConfig(f);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	public static void ensureDefaultTeams()
	{
		for (String name : DEFAULT_TEAM_NAMES)
		{
			Team team = new Team.Builder(name).defaultTeam(true).openJoining(true).build();
			boolean contains = false;
			for (String teamName : xTeam.tm.getDefaultTeamNames())
			{
				if (teamName.equals(name))
					contains = true;
			}
			if (!contains)
				xTeam.tm.addTeam(team);
		}
	}
	public static void load()
	{
		readConfig(settings);
	}
	public static void readConfig(File file)
	{
		FileReader reader = new FileReader(file, false);
		SPOUT_ENABLED = Data.BUKKIT.getPluginManager().getPlugin("Spout") != null;
		LOCATIONS_ENABLED = Data.BUKKIT.getPluginManager().getPlugin("xTeamLocations") != null;
		CAN_CHAT = reader.getBoolean("canteamchat", true);
		HIDE_NAMES = reader.getBoolean("teamhidename", true);
		HQ_ON_DEATH = reader.getBoolean("hqondeath", true);
		TEAM_WOLVES = reader.getBoolean("teamwolves", true);
		RANDOM_TEAM = reader.getBoolean("randomjointeam", false);
		BALANCE_TEAMS = reader.getBoolean("balanceteams", false);
		DEFAULT_TEAM_ONLY = reader.getBoolean("onlyjoindefaultteam", false);
		DEFAULT_HQ_ON_JOIN = reader.getBoolean("defaulthqonjoin", false);
		TEAM_TAG_ENABLED = reader.getBoolean("teamtagenabled", true);
		TEAM_FRIENDLY_FIRE = reader.getBoolean("teamfriendlyfire", false);
		NO_PERMISSIONS = reader.getBoolean("nopermissions", false);
		ALPHA_NUM = reader.getBoolean("alphanumericnames", false);
		DISPLAY_COORDINATES = reader.getBoolean("displaycoordinates", true);
		MAX_PLAYERS = reader.getInteger("playersonteam", 10);
		REVEAL_TIME = reader.getInteger("namerevealtime", 5);
		HQ_INTERVAL = reader.getInteger("sethqinterval", 0);
		TELE_RADIUS = reader.getInteger("teleportradius", 500);
		ENEMY_PROX = reader.getInteger("enemyproximity", 16);
		TELE_DELAY = reader.getInteger("teledelay", 10);
		CREATE_INTERVAL = reader.getInteger("createteamdelay", 0);
		LAST_ATTACKED_DELAY = reader.getInteger("lastattackeddelay", 15);
		TEAM_TAG_LENGTH = reader.getInteger("teamtagmaxlength", 0);
		MAX_NUM_LOCATIONS = reader.getInteger("maxnumlocations", 9);
		REFRESH_DELAY = reader.getInteger("telerefreshdelay", 60);
		TAG_COLOR = reader.getString("tagcolor", "green");
		NAME_COLOR = reader.getString("chatnamecolor", "dark_green");
		DEFAULT_TEAM_NAMES = CommonUtil.toList(reader.getString("defaultteams", "").replace(" ", "").split(","));
		DISABLED_WORLDS = CommonUtil.toList(reader.getString("disabledworlds", "").replace(" ", "").split(","));
	}
}
