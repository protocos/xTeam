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
		this.getTeamCoordinator().createTeam(team1);
		Team team2 = Team.generateTeamFromProperties(this, "name:two world:world open:false leader:mastermind timeHeadquartersSet:0 Headquarters:0.0,0.0,0.0,0.0,0.0 players:mastermind admins:mastermind");
		this.getTeamCoordinator().createTeam(team2);
		/////////////////////////////////////////////////
		Configuration.DEFAULT_TEAM_NAMES.add("red");
		Configuration.DEFAULT_TEAM_NAMES.add("blue");
		/////////////////////////////////////////////////
		Team team3 = Team.generateTeamFromProperties(this, "name:red tag:REDONE world:world open:true timeHeadquartersSet:0 Headquarters:0.0,0.0,0.0,0.0,0.0 leader:default admins: players:strandedhelix,teammate");
		this.getTeamCoordinator().createTeam(team3);
		Team team4 = Team.generateTeamFromProperties(this, "name:blue world:world open:true timeHeadquartersSet:0 Headquarters:0.0,0.0,0.0,0.0,0.0 leader:default admins: players:");
		this.getTeamCoordinator().createTeam(team4);

		//MOCK players
		Location fakeLocation = new FakeLocation(0.0D, 64.0D, 0.0D);
		//FAKE protocos
		FakeOfflinePlayer protocosOffline = FakeOfflinePlayer.online("protocos");
		FakePlayer protocosOnline = new FakePlayer.Builder().name("protocos").location(fakeLocation).build();

		//FAKE kmlanglois
		FakeOfflinePlayer kmlangloisOffline = FakeOfflinePlayer.online("kmlanglois");
		FakePlayer kmlangloisOnline = new FakePlayer.Builder().name("kmlanglois").location(fakeLocation).build();

		//FAKE mastermind
		FakeOfflinePlayer mastermindOffline = FakeOfflinePlayer.online("mastermind");
		FakePlayer mastermindOnline = new FakePlayer.Builder().name("mastermind").location(fakeLocation).build();

		//FAKE Lonely
		FakeOfflinePlayer LonelyOffline = FakeOfflinePlayer.online("Lonely");
		FakePlayer LonelyOnline = new FakePlayer.Builder().name("Lonely").location(fakeLocation).build();

		//FAKE strandedhelix
		FakeOfflinePlayer strandedhelixOffline = new FakeOfflinePlayer.Builder().name("strandedhelix").isOp(false).isOnline(false).build();

		//FAKE kestra
		FakeOfflinePlayer kestraOffline = new FakeOfflinePlayer.Builder().name("kestra").isOp(false).isOnline(false).build();

		//FAKE unreachable players
		FakeOfflinePlayer newbieOffline = new FakeOfflinePlayer.Builder().name("newbie").isOp(false).isOnline(false).hasPlayedBefore(false).build();
		FakeOfflinePlayer threeOffline = new FakeOfflinePlayer.Builder().name("three").isOp(false).isOnline(false).hasPlayedBefore(false).build();
		FakePlayer oneOnline = new FakePlayer.Builder().name("one").location(fakeLocation).build();
		FakeOfflinePlayer oneOffline = FakeOfflinePlayer.online("one");
		FakePlayer twoOnline = new FakePlayer.Builder().name("two").location(fakeLocation).build();
		FakeOfflinePlayer twoOffline = FakeOfflinePlayer.online("two");
		FakeOfflinePlayer thrOffline = FakeOfflinePlayer.offline("thr");
		FakeOfflinePlayer neverPlayedOffline = new FakeOfflinePlayer.Builder().name("neverplayed").isOp(false).isOnline(false).hasPlayedBefore(false).build();
		FakeOfflinePlayer teammateOffline = new FakeOfflinePlayer.Builder().name("teammate").isOp(false).isOnline(false).build();

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
		FakePlayer.use(fakeXTeam, fakeServer);
		FakeOfflinePlayer.use(fakeXTeam);
		return fakeXTeam;
	}

}
