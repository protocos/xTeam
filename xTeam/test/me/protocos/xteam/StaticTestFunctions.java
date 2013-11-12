package me.protocos.xteam;

import me.protocos.xteam.api.TeamPlugin;
import me.protocos.xteam.api.fakeobjects.*;
import me.protocos.xteam.command.action.TeleportScheduler;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.InviteHandler;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.util.BukkitUtil;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.junit.Test;

public class StaticTestFunctions
{
	private static World mockWorld = new FakeWorld();
	private static Location mockLocation = new FakeLocation(mockWorld, 0.0D, 64.0D, 0.0D);

	@Test
	public void ShouldBeMockData()
	{
		mockData();
	}

	private static void initData(TeamPlugin teamPlugin)
	{
		InviteHandler.clear();
		TeleportScheduler.getInstance().clearTasks();
		xTeamPlugin.getInstance().getTeamManager().clearData();
		xTeamPlugin.getInstance().getPlayerManager().clearData();
		Data.chatStatus.clear();
		Data.spies.clear();
		Data.lastCreated.clear();
		teamPlugin.initFileSystem("test");
	}

	public static void mockData()
	{
		//MOCK variables
		FakeServer fakeServer = new FakeServer();

		//MOCK server
		BukkitUtil.setServer(fakeServer);
		//		try
		//		{
		//			InputStream fileInput = new FileInputStream("plugin.yml");
		//			PluginDescriptionFile pdf = new PluginDescriptionFile(fileInput);
		//		}
		//		catch (FileNotFoundException e)
		//		{
		//			e.printStackTrace();
		//		}
		//		catch (InvalidDescriptionException e)
		//		{
		//			e.printStackTrace();
		//		}
		initData(xTeamPlugin.getInstance());

		Team team1 = Team.generateTeamFromProperties("name:ONE tag:TeamAwesome world:world open:false leader:kmlanglois timeHeadquartersSet:1361318508899 Headquarters:169.92906931820792,65.0,209.31066111932847,22.049545,36.14993 players:kmlanglois,protocos admins:kmlanglois");
		xTeamPlugin.getInstance().getTeamManager().addTeam(team1);
		Team team2 = Team.generateTeamFromProperties("name:two world:world open:false leader:mastermind timeHeadquartersSet:0 Headquarters:0.0,0.0,0.0,0.0,0.0 players:mastermind admins:mastermind");
		xTeamPlugin.getInstance().getTeamManager().addTeam(team2);
		/////////////////////////////////////////////////
		Data.DEFAULT_TEAM_NAMES.add("red");
		Data.DEFAULT_TEAM_NAMES.add("blue");
		/////////////////////////////////////////////////
		Team team3 = Team.generateTeamFromProperties("name:red tag:REDONE world:world open:true timeHeadquartersSet:0 Headquarters:0.0,0.0,0.0,0.0,0.0 leader:default admins: players:strandedhelix");
		xTeamPlugin.getInstance().getTeamManager().addTeam(team3);
		Team team4 = Team.generateTeamFromProperties("name:blue world:world open:true timeHeadquartersSet:0 Headquarters:0.0,0.0,0.0,0.0,0.0 leader:default admins: players:");
		xTeamPlugin.getInstance().getTeamManager().addTeam(team4);

		//MOCK players
		mockPlayers(fakeServer);
	}

	public static void mockPlayers(FakeServer server)
	{
		//FAKE protocos
		FakeOfflinePlayer protocosOffline = new FakeOfflinePlayer("protocos", true, true, true);
		FakePlayer protocosOnline = new FakePlayer("protocos", true, true, 20, mockLocation);

		//FAKE kmlanglois
		FakeOfflinePlayer kmlangloisOffline = new FakeOfflinePlayer("kmlanglois", true, true, true);
		FakePlayer kmlangloisOnline = new FakePlayer("kmlanglois", true, true, 20, mockLocation);

		//FAKE mastermind
		FakeOfflinePlayer mastermindOffline = new FakeOfflinePlayer("mastermind", true, true, true);
		FakePlayer mastermindOnline = new FakePlayer("mastermind", true, true, 20, mockLocation);

		//FAKE Lonely
		FakeOfflinePlayer LonelyOffline = new FakeOfflinePlayer("Lonely", true, true, true);
		FakePlayer LonelyOnline = new FakePlayer("Lonely", true, true, 20, mockLocation);

		//FAKE strandedhelix
		FakeOfflinePlayer strandedhelixOffline = new FakeOfflinePlayer("strandedhelix", false, false, true);
		//		FakePlayer strandedhelixOnline = new FakePlayer("strandedhelix", false, false, 20, mockLocation);

		//FAKE kestra
		FakeOfflinePlayer kestraOffline = new FakeOfflinePlayer("kestra", false, false, true);
		//		FakePlayer kestraOnline = new FakePlayer("kestra", false, false, 20, mockLocation);

		//FAKE unreachable players
		FakeOfflinePlayer newbieOffline = new FakeOfflinePlayer("newbie", false, false, false);
		FakeOfflinePlayer threeOffline = new FakeOfflinePlayer("three", false, false, false);
		FakePlayer oneOnline = new FakePlayer("one", true, true, 20, mockLocation);
		FakeOfflinePlayer oneOffline = new FakeOfflinePlayer("one", true, true, true);
		FakePlayer twoOnline = new FakePlayer("two", true, true, 20, mockLocation);
		FakeOfflinePlayer twoOffline = new FakeOfflinePlayer("two", true, true, true);
		FakeOfflinePlayer thrOffline = new FakeOfflinePlayer("thr", true, false, true);

		//FAKE onlinePlayers
		server.setOnlinePlayers(new Player[] { protocosOnline, kmlangloisOnline, mastermindOnline, LonelyOnline, oneOnline, twoOnline });
		server.setOfflinePlayers(new OfflinePlayer[] { protocosOffline, kmlangloisOffline, mastermindOffline, LonelyOffline, strandedhelixOffline, kestraOffline, newbieOffline, threeOffline, oneOffline, twoOffline, thrOffline });
	}
}
