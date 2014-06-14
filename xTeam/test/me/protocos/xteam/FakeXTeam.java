package me.protocos.xteam;

import java.util.List;
import me.protocos.xteam.command.ICommandManager;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.entity.Team;
import me.protocos.xteam.fakeobjects.*;
import me.protocos.xteam.model.ILog;
import me.protocos.xteam.util.CommonUtil;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

public class FakeXTeam extends TeamPlugin
{
	private static final String FOLDER = "test/";
	private ILog fakeLog = new FakeLog();
	private FakeServer server;

	private FakeXTeam(FakeServer server)
	{
		super(server, FOLDER);
		this.server = server;
		generateDefaultData();
		this.registerCommands(this.getCommandManager());
	}

	private void generateDefaultData()
	{
		Configuration.DISPLAY_COORDINATES = true;
		Team team1 = Team.generateTeamFromProperties(this, "name:ONE tag:TeamAwesome world:world open:false leader:kmlanglois timeHeadquartersSet:1361318508899 Headquarters:169.92906931820792,65.0,209.31066111932847,22.049545,36.14993 players:kmlanglois,protocos admins:kmlanglois");
		teamCoordinator.createTeam(team1);
		Team team2 = Team.generateTeamFromProperties(this, "name:two world:world open:false leader:mastermind timeHeadquartersSet:0 Headquarters:0.0,0.0,0.0,0.0,0.0 players:mastermind admins:mastermind");
		teamCoordinator.createTeam(team2);
		/////////////////////////////////////////////////
		Configuration.DEFAULT_TEAM_NAMES.add("red");
		Configuration.DEFAULT_TEAM_NAMES.add("blue");
		/////////////////////////////////////////////////
		Team team3 = Team.generateTeamFromProperties(this, "name:red tag:REDONE world:world open:true timeHeadquartersSet:0 Headquarters:0.0,0.0,0.0,0.0,0.0 leader:default admins: players:strandedhelix,teammate");
		teamCoordinator.createTeam(team3);
		Team team4 = Team.generateTeamFromProperties(this, "name:blue world:world open:true timeHeadquartersSet:0 Headquarters:0.0,0.0,0.0,0.0,0.0 leader:default admins: players:");
		teamCoordinator.createTeam(team4);

		//MOCK players
		World mockWorld = new FakeWorld();
		Location mockLocation = new FakeLocation(mockWorld, 0.0D, 64.0D, 0.0D);
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
		FakeOfflinePlayer neverPlayedOffline = new FakeOfflinePlayer("neverplayed", false, false, false);
		FakeOfflinePlayer teammateOffline = new FakeOfflinePlayer("teammate", false, false, true);

		//FAKE onlinePlayers
		server.setOnlinePlayers(new Player[] { protocosOnline, kmlangloisOnline, mastermindOnline, LonelyOnline, oneOnline, twoOnline });
		server.setOfflinePlayers(new OfflinePlayer[] { protocosOffline, kmlangloisOffline, mastermindOffline, LonelyOffline, strandedhelixOffline, kestraOffline, newbieOffline, threeOffline, oneOffline, twoOffline, thrOffline, teammateOffline, neverPlayedOffline });
	}

	@Override
	public List<Permission> getPermissions()
	{
		return CommonUtil.emptyList();
	}

	@Override
	public String getVersion()
	{
		return "TEST";
	}

	@Override
	public void registerCommands(ICommandManager manager)
	{
		(new XTeam(server, FOLDER)).registerCommands(manager);
	}

	@Override
	public void read()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void write()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void load()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void enable()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void disable()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public ILog getLog()
	{
		return fakeLog;
	}

	public static TeamPlugin asTeamPlugin()
	{
		Configuration.STORAGE_TYPE = "file";
		FakeServer fakeServer = new FakeServer();
		FakeXTeam fakeXTeam = new FakeXTeam(fakeServer);
		fakeServer.setPlugin(fakeXTeam);
		fakeXTeam.load();
		return fakeXTeam;
	}

}
