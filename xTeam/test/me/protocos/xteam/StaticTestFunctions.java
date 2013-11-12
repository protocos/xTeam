package me.protocos.xteam;

import static org.mockito.Mockito.*;
import me.protocos.xteam.api.TeamPlugin;
import me.protocos.xteam.api.fakeobjects.FakeLocation;
import me.protocos.xteam.api.fakeobjects.FakeOfflinePlayer;
import me.protocos.xteam.api.fakeobjects.FakePlayer;
import me.protocos.xteam.api.fakeobjects.FakeWorld;
import me.protocos.xteam.command.action.TeleportScheduler;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.InviteHandler;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.util.BukkitUtil;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitScheduler;

public class StaticTestFunctions
{
	private static World mockWorld = new FakeWorld();
	private static Location mockLocation = new FakeLocation(mockWorld, 0.0D, 64.0D, 0.0D);

	private static void initData(TeamPlugin teamPlugin)
	{
		InviteHandler.clear();
		Data.chatStatus.clear();
		Data.spies.clear();
		Data.lastCreated.clear();
		teamPlugin.initFileSystem("test");
		//		Data.LOCATIONS_ENABLED = true;
		//		Data.CAN_CHAT = true;
		//		Data.HQ_ON_DEATH = true;
		//		Data.TEAM_WOLVES = true;
		//		Data.RANDOM_TEAM = false;
		//		Data.BALANCE_TEAMS = false;
		//		Data.DEFAULT_TEAM_ONLY = false;
		//		Data.DEFAULT_HQ_ON_JOIN = false;
		//		Data.TEAM_TAG_ENABLED = true;
		//		Data.TEAM_FRIENDLY_FIRE = false;
		//		Data.NO_PERMISSIONS = false;
		//		Data.ALPHA_NUM = true;
		//		Data.DISPLAY_COORDINATES = true;
		//		Data.MAX_PLAYERS = 0;
		//		Data.HQ_INTERVAL = 1;
		//		Data.TELE_RADIUS = 500;
		//		Data.ENEMY_PROX = 20;
		//		Data.TELE_DELAY = 60;
		//		Data.CREATE_INTERVAL = 0;
		//		Data.LAST_ATTACKED_DELAY = 15;
		//		Data.TEAM_TAG_LENGTH = 20;
		//		Data.MAX_NUM_LOCATIONS = 10;
		//		Data.TELE_REFRESH_DELAY = 60;
		//		Data.TAG_COLOR = "dark_green";
	}

	public static void mockData()
	{
		//MOCK variables
		TeamPlugin mockxTeam = mock(TeamPlugin.class);
		PluginManager mockPM = mock(PluginManager.class);
		BukkitScheduler mockScheduler = mock(BukkitScheduler.class);
		Server mockServer = mock(Server.class);

		//MOCK server
		BukkitUtil.setServer(mockServer);
		when(BukkitUtil.getScheduler()).thenReturn(mockScheduler);
		when(BukkitUtil.getPluginManager()).thenReturn(mockPM);
		when(BukkitUtil.getPlugin("xTeam")).thenReturn(mockxTeam);
		when(BukkitUtil.getWorld("world")).thenReturn(mockLocation.getWorld());
		initData(xTeam.getInstance());

		//MOCK main data
		TeleportScheduler.getInstance().clearTasks();
		xTeam.getInstance().getTeamManager().clearData();
		xTeam.getInstance().getPlayerManager().clearData();

		//MOCK team
		Team team1 = Team.generateTeamFromProperties("name:ONE tag:TeamAwesome world:world open:false leader:kmlanglois timeHeadquartersSet:1361318508899 Headquarters:169.92906931820792,65.0,209.31066111932847,22.049545,36.14993 players:kmlanglois,protocos admins:kmlanglois");
		xTeam.getInstance().getTeamManager().addTeam(team1);
		Team team2 = Team.generateTeamFromProperties("name:two world:world open:false leader:mastermind timeHeadquartersSet:0 Headquarters:0.0,0.0,0.0,0.0,0.0 players:mastermind admins:mastermind");
		xTeam.getInstance().getTeamManager().addTeam(team2);
		/////////////////////////////////////////////////
		Data.DEFAULT_TEAM_NAMES.add("red");
		Data.DEFAULT_TEAM_NAMES.add("blue");
		/////////////////////////////////////////////////
		Team team3 = Team.generateTeamFromProperties("name:red tag:REDONE world:world open:true timeHeadquartersSet:0 Headquarters:0.0,0.0,0.0,0.0,0.0 leader:default admins: players:strandedhelix");
		xTeam.getInstance().getTeamManager().addTeam(team3);
		Team team4 = Team.generateTeamFromProperties("name:blue world:world open:true timeHeadquartersSet:0 Headquarters:0.0,0.0,0.0,0.0,0.0 leader:default admins: players:");
		xTeam.getInstance().getTeamManager().addTeam(team4);

		//MOCK players
		mockPlayers();
	}

	public static void mockPlayers()
	{
		//MOCK protocos
		FakeOfflinePlayer protocosOffline = new FakeOfflinePlayer("protocos", true, true, true);
		when(BukkitUtil.getOfflinePlayer("protocos")).thenReturn(protocosOffline);
		FakePlayer protocosOnline = new FakePlayer("protocos", true, true, 20, mockLocation);
		when(BukkitUtil.getPlayer("protocos")).thenReturn(protocosOnline);

		//MOCK kmlanglois
		FakeOfflinePlayer kmlangloisOffline = new FakeOfflinePlayer("kmlanglois", true, true, true);
		when(BukkitUtil.getOfflinePlayer("kmlanglois")).thenReturn(kmlangloisOffline);
		FakePlayer kmlangloisOnline = new FakePlayer("kmlanglois", true, true, 20, mockLocation);
		when(BukkitUtil.getPlayer("kmlanglois")).thenReturn(kmlangloisOnline);

		//MOCK mastermind
		FakeOfflinePlayer mastermindOffline = new FakeOfflinePlayer("mastermind", true, true, true);
		when(BukkitUtil.getOfflinePlayer("mastermind")).thenReturn(mastermindOffline);
		FakePlayer mastermindOnline = new FakePlayer("mastermind", true, true, 20, mockLocation);
		when(BukkitUtil.getPlayer("mastermind")).thenReturn(mastermindOnline);

		//MOCK Lonely
		FakeOfflinePlayer LonelyOffline = new FakeOfflinePlayer("Lonely", true, true, true);
		when(BukkitUtil.getOfflinePlayer("Lonely")).thenReturn(LonelyOffline);
		FakePlayer LonelyOnline = new FakePlayer("Lonely", true, true, 20, mockLocation);
		when(BukkitUtil.getPlayer("Lonely")).thenReturn(LonelyOnline);

		//MOCK strandedhelix
		FakeOfflinePlayer strandedhelixOffline = new FakeOfflinePlayer("strandedhelix", false, false, true);
		when(BukkitUtil.getOfflinePlayer("strandedhelix")).thenReturn(strandedhelixOffline);
		//		FakePlayer strandedhelixOnline = new FakePlayer("strandedhelix", false, false, 20, mockLocation);
		//		when(BukkitUtil.getPlayer("strandedhelix")).thenReturn(strandedhelixOnline);

		//MOCK kestra
		FakeOfflinePlayer kestraOffline = new FakeOfflinePlayer("kestra", false, false, true);
		when(BukkitUtil.getOfflinePlayer("kestra")).thenReturn(kestraOffline);
		FakePlayer kestraOnline = new FakePlayer("kestra", false, false, 20, mockLocation);
		when(BukkitUtil.getPlayer("kestra")).thenReturn(kestraOnline);

		//MOCK unreachable players
		FakeOfflinePlayer newbieOffline = new FakeOfflinePlayer("newbie", false, false, false);
		when(BukkitUtil.getOfflinePlayer("newbie")).thenReturn(newbieOffline);
		FakeOfflinePlayer threeOffline = new FakeOfflinePlayer("three", false, false, false);
		when(BukkitUtil.getOfflinePlayer("three")).thenReturn(threeOffline);

		FakeOfflinePlayer oneOffline = new FakeOfflinePlayer("one", true, true, true);
		when(BukkitUtil.getOfflinePlayer("one")).thenReturn(oneOffline);
		FakeOfflinePlayer twoOffline = new FakeOfflinePlayer("two", true, true, true);
		when(BukkitUtil.getOfflinePlayer("two")).thenReturn(twoOffline);
		FakeOfflinePlayer thrOffline = new FakeOfflinePlayer("thr", true, false, true);
		when(BukkitUtil.getOfflinePlayer("thr")).thenReturn(thrOffline);

		//MOCK onlinePlayers
		when(BukkitUtil.getOnlinePlayers()).thenReturn(new Player[] { protocosOnline, kmlangloisOnline, mastermindOnline, LonelyOnline });
		when(BukkitUtil.getOfflinePlayers()).thenReturn(new OfflinePlayer[] { protocosOffline, kmlangloisOffline, mastermindOffline, LonelyOffline, strandedhelixOffline, kestraOffline, newbieOffline, threeOffline, oneOffline, twoOffline, thrOffline });

		//MOCK teamPlayerManager
		//		xTeam.getInstance().getPlayerManager().addPlayer(TeamPlayer.teamPlayerFromOnlinePlayer(protocosOnline));
		//		xTeam.getInstance().getPlayerManager().addPlayer(TeamPlayer.teamPlayerFromOnlinePlayer(kmlangloisOnline));
		//		xTeam.getInstance().getPlayerManager().addPlayer(TeamPlayer.teamPlayerFromOnlinePlayer(mastermindOnline));
		//		xTeam.getInstance().getPlayerManager().addPlayer(TeamPlayer.teamPlayerFromOnlinePlayer(strandedhelixOnline));
		//		xTeam.getInstance().getPlayerManager().addPlayer(TeamPlayer.teamPlayerFromOnlinePlayer(LonelyOnline));
	}
}
