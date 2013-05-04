package me.protocos.xteam.testing;

import static org.mockito.Mockito.*;
import java.io.File;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.CommandManager;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.TeamManager;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitScheduler;

public class StaticTestFunctions
{
	private static World mockWorld = new FakeWorld();
	private static Location mockLocation = new FakeLocation(mockWorld, 0.0D, 64.0D, 0.0D);

	private static void initData()
	{
		Data.chatStatus.clear();
		Data.spies.clear();
		Data.damagedByPlayer.clear();
		Data.returnLocations.clear();
		Data.taskIDs.clear();
		Data.countWaitTime.clear();
		Data.hasTeleported.clear();
		Data.lastAttacked.clear();
		Data.lastCreated.clear();
		Data.invites.clear();
		Data.SPOUT_ENABLED = true;
		Data.LOCATIONS_ENABLED = true;
		Data.CAN_CHAT = true;
		Data.HIDE_NAMES = true;
		Data.HQ_ON_DEATH = true;
		Data.TEAM_WOLVES = true;
		Data.RANDOM_TEAM = false;
		Data.BALANCE_TEAMS = false;
		Data.DEFAULT_TEAM_ONLY = false;
		Data.DEFAULT_HQ_ON_JOIN = false;
		Data.TEAM_TAG_ENABLED = true;
		Data.TEAM_FRIENDLY_FIRE = false;
		Data.NO_PERMISSIONS = false;
		Data.ALPHA_NUM = true;
		Data.DISPLAY_COORDINATES = true;
		Data.MAX_PLAYERS = 0;
		Data.REVEAL_TIME = 10;
		Data.HQ_INTERVAL = 1;
		Data.TELE_RADIUS = 500;
		Data.ENEMY_PROX = 20;
		Data.TELE_DELAY = 60;
		Data.CREATE_INTERVAL = 0;
		Data.LAST_ATTACKED_DELAY = 15;
		Data.TEAM_TAG_LENGTH = 20;
		Data.MAX_NUM_LOCATIONS = 10;
		Data.REFRESH_DELAY = 60;
	}
	public static void mockData()
	{
		xTeam.cm = new CommandManager();
		//MOCK variables
		PluginManager mockPM = mock(PluginManager.class);
		Plugin mockSpout = mock(Plugin.class);
		Plugin mockxTeam = mock(Plugin.class);
		BukkitScheduler mockScheduler = mock(BukkitScheduler.class);
		Data.settings = new File("/Users/zjlanglois/Desktop/Bukkit Server/plugins/xTeam/xTeam.cfg");
		xTeam.VERSION = "CURRENT";
		initData();

		//MOCK server
		Data.BUKKIT = mock(Server.class);
		when(Data.BUKKIT.getScheduler()).thenReturn(mockScheduler);
		when(Data.BUKKIT.getPluginManager()).thenReturn(mockPM);
		when(Data.BUKKIT.getPluginManager().getPlugin("Spout")).thenReturn(mockSpout);
		when(Data.BUKKIT.getPluginManager().getPlugin("xTeam")).thenReturn(mockxTeam);
		when(Data.BUKKIT.getWorld("world")).thenReturn(mockLocation.getWorld());

		//MOCK players
		mockPlayers();

		//MOCK team
		xTeam.tm = new TeamManager();
		Team team1 = Team.generateTeamFromProperties("name:ONE tag:TeamAwesome world:world open:false leader:kmlanglois timeHeadquartersSet:1361318508899 Headquarters:169.92906931820792,65.0,209.31066111932847,22.049545,36.14993 players:kmlanglois,protocos admins:kmlanglois");
		xTeam.tm.addTeam(team1);
		Team team2 = Team.generateTeamFromProperties("name:two world:world open:false leader:mastermind timeHeadquartersSet:0 Headquarters:0.0,0.0,0.0,0.0,0.0 players:mastermind admins:mastermind");
		xTeam.tm.addTeam(team2);
		/////////////////////////////////////////////////
		Data.DEFAULT_TEAM_NAMES.add("red");
		Data.DEFAULT_TEAM_NAMES.add("blue");
		/////////////////////////////////////////////////
		Team team3 = Team.generateTeamFromProperties("name:red tag:RED world:world open:true timeHeadquartersSet:0 Headquarters:0.0,0.0,0.0,0.0,0.0 leader:default admins: players:strandedhelix");
		xTeam.tm.addTeam(team3);
		Team team4 = Team.generateTeamFromProperties("name:blue world:world open:true timeHeadquartersSet:0 Headquarters:0.0,0.0,0.0,0.0,0.0 leader:default admins: players:");
		xTeam.tm.addTeam(team4);
	}
	public static void mockPlayers()
	{
		//MOCK protocos
		FakeOfflinePlayer protocosOffline = new FakeOfflinePlayer("protocos", true, true, true);
		when(Data.BUKKIT.getOfflinePlayer("protocos")).thenReturn(protocosOffline);
		FakePlayer protocosOnline = new FakePlayer("protocos", true, true, 20, mockLocation);
		when(Data.BUKKIT.getPlayer("protocos")).thenReturn(protocosOnline);

		//MOCK kmlanglois
		FakeOfflinePlayer kmlangloisOffline = new FakeOfflinePlayer("kmlanglois", true, true, true);
		when(Data.BUKKIT.getOfflinePlayer("kmlanglois")).thenReturn(kmlangloisOffline);
		FakePlayer kmlangloisOnline = new FakePlayer("kmlanglois", true, true, 20, mockLocation);
		when(Data.BUKKIT.getPlayer("kmlanglois")).thenReturn(kmlangloisOnline);

		//MOCK mastermind
		FakeOfflinePlayer mastermindOffline = new FakeOfflinePlayer("mastermind", true, true, true);
		when(Data.BUKKIT.getOfflinePlayer("mastermind")).thenReturn(mastermindOffline);
		FakePlayer mastermindOnline = new FakePlayer("mastermind", true, true, 20, mockLocation);
		when(Data.BUKKIT.getPlayer("mastermind")).thenReturn(mastermindOnline);

		//MOCK lonely
		FakeOfflinePlayer lonelyOffline = new FakeOfflinePlayer("lonely", true, true, true);
		when(Data.BUKKIT.getOfflinePlayer("lonely")).thenReturn(lonelyOffline);
		FakePlayer lonelyOnline = new FakePlayer("lonely", true, true, 20, mockLocation);
		when(Data.BUKKIT.getPlayer("lonely")).thenReturn(lonelyOnline);

		//MOCK strandedhelix
		FakeOfflinePlayer strandedhelixOffline = new FakeOfflinePlayer("strandedhelix", false, false, true);
		when(Data.BUKKIT.getOfflinePlayer("strandedhelix")).thenReturn(strandedhelixOffline);
		FakePlayer strandedhelixOnline = new FakePlayer("strandedhelix", false, false, 20, mockLocation);
		when(Data.BUKKIT.getPlayer("strandedhelix")).thenReturn(strandedhelixOnline);

		//MOCK kestra
		FakeOfflinePlayer kestraOffline = new FakeOfflinePlayer("kestra", false, false, true);
		when(Data.BUKKIT.getOfflinePlayer("kestra")).thenReturn(kestraOffline);
		FakePlayer kestraOnline = new FakePlayer("kestra", false, false, 20, mockLocation);
		when(Data.BUKKIT.getPlayer("kestra")).thenReturn(kestraOnline);

		//MOCK unreachable players
		when(Data.BUKKIT.getOfflinePlayer("newbie")).thenReturn(new FakeOfflinePlayer("newbie"));
		when(Data.BUKKIT.getOfflinePlayer("three")).thenReturn(new FakeOfflinePlayer("three"));

		//MOCK onlinePlayers
		when(Data.BUKKIT.getOnlinePlayers()).thenReturn(new Player[0]);

		when(Data.BUKKIT.getOfflinePlayer("one")).thenReturn(mock(OfflinePlayer.class));
		when(Data.BUKKIT.getOfflinePlayer("one").hasPlayedBefore()).thenReturn(true);
		when(Data.BUKKIT.getOfflinePlayer("two")).thenReturn(mock(OfflinePlayer.class));
		when(Data.BUKKIT.getOfflinePlayer("two").hasPlayedBefore()).thenReturn(true);
		when(Data.BUKKIT.getOfflinePlayer("thr")).thenReturn(mock(OfflinePlayer.class));
		when(Data.BUKKIT.getOfflinePlayer("thr").hasPlayedBefore()).thenReturn(true);
	}
}
