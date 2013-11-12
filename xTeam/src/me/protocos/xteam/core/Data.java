package me.protocos.xteam.core;

import java.util.*;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.util.ConfigLoader;

public class Data
{
	public static TreeSet<String> chatStatus = new TreeSet<String>();
	public static HashSet<String> spies = new HashSet<String>();
	public static HashMap<String, Long> lastCreated = new HashMap<String, Long>();
	public static boolean LOCATIONS_ENABLED;
	public static boolean CAN_CHAT;
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
	public static boolean SEND_ANONYMOUS_ERROR_REPORTS;
	public static int MAX_PLAYERS;
	public static int HQ_INTERVAL;
	public static int TELE_RADIUS;
	public static int ENEMY_PROX;
	public static int TELE_DELAY;
	public static int CREATE_INTERVAL;
	public static int LAST_ATTACKED_DELAY;
	public static int TEAM_TAG_LENGTH;
	public static int MAX_NUM_LOCATIONS;
	public static int TELE_REFRESH_DELAY;
	public static int RALLY_DELAY;
	public static String TAG_COLOR;
	public static String NAME_COLOR;
	public static List<String> DEFAULT_TEAM_NAMES = new ArrayList<String>();
	public static List<String> DISABLED_WORLDS = new ArrayList<String>();

	private Data()
	{
		throw new AssertionError();
	}

	public static void ensureDefaultTeams()
	{
		for (String name : DEFAULT_TEAM_NAMES)
		{
			Team team = new Team.Builder(name).defaultTeam(true).openJoining(true).build();
			boolean contains = false;
			for (String teamName : xTeam.getInstance().getTeamManager().getDefaultTeamNames())
			{
				if (teamName.equals(name))
					contains = true;
			}
			if (!contains)
				xTeam.getInstance().getTeamManager().addTeam(team);
		}
	}

	public static void load(ConfigLoader config)
	{
		config.load();
	}
}
