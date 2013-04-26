package me.protocos.xteam.core.testing;

import static org.mockito.Mockito.*;
import java.io.File;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.Functions;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.testing.FakeWorld;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FunctionsTest
{
	@Before
	public void setup()
	{
		xTeam.tm.clear();
		Data.BUKKIT = mock(Server.class);
		PluginManager mockPM = mock(PluginManager.class);
		when(Data.BUKKIT.getPluginManager()).thenReturn(mockPM);
		Plugin mockSpout = mock(Plugin.class);
		when(Data.BUKKIT.getPluginManager().getPlugin("Spout")).thenReturn(mockSpout);
		Plugin mockxTeam = mock(Plugin.class);
		when(Data.BUKKIT.getPluginManager().getPlugin("xTeam")).thenReturn(mockxTeam);
		Data.settings = new File("/Users/zjlanglois/Desktop/Bukkit Server/plugins/xTeam/xTeam.cfg");
		World mockWorld = new FakeWorld();
		when(Data.BUKKIT.getWorld("world")).thenReturn(mockWorld);
	}
	@Test
	public void ShouldBeWriteThenRead()
	{
		//ASSEMBLE
		Data.DEFAULT_TEAM_NAMES.add("red");
		Data.DEFAULT_TEAM_NAMES.add("blue");
		Team team1 = Team.generateTeamFromProperties("name:one world:world open:false leader:kmlanglois timeHeadquartersSet:1361318508899 Headquarters:169.92906931820792,65.0,209.31066111932847,22.049545,36.14993 players:kmlanglois,protocos admins:kmlanglois");
		xTeam.tm.addTeam(team1);
		Team team2 = Team.generateTeamFromProperties("name:two open:false default:false timeHeadquartersSet:0 hq: leader:mastermind admins:mastermind players:mastermind");
		xTeam.tm.addTeam(team2);
		Team team3 = Team.generateTeamFromProperties("name:red world:world open:true timeHeadquartersSet:0 Headquarters:0.0,0.0,0.0,0.0,0.0 leader:default admins: players:strandedhelix");
		xTeam.tm.addTeam(team3);
		Team team4 = Team.generateTeamFromProperties("name:blue world:world open:true timeHeadquartersSet:0 Headquarters:0.0,0.0,0.0,0.0,0.0 leader:default admins: players:");
		xTeam.tm.addTeam(team4);
		//ACT
		Functions.writeTeamData(new File("teams.txt"));
		Functions.readTeamData(new File("teams.txt"));
		//ASSERT
		Assert.assertEquals("[name:one tag:one open:false default:false timeHeadquartersSet:1361318508899 hq:world,169.92906931820792,65.0,209.31066111932847,22.049545,36.14993 leader:kmlanglois admins:kmlanglois players:kmlanglois,protocos, " +
				"name:two tag:two open:false default:false timeHeadquartersSet:0 hq: leader:mastermind admins:mastermind players:mastermind, " +
				"name:red tag:red open:true default:true timeHeadquartersSet:0 hq: leader: admins: players:strandedhelix, " +
				"name:blue tag:blue open:true default:true timeHeadquartersSet:0 hq: leader: admins: players:]", xTeam.tm.getAllTeams().toString());
	}
	@Test
	public void ShouldBeDisableThenEnable()
	{
		//ASSEMBLE
		Data.DEFAULT_TEAM_NAMES.add("red");
		Data.DEFAULT_TEAM_NAMES.add("blue");
		Team team1 = Team.generateTeamFromProperties("name:one world:world open:false leader:kmlanglois timeHeadquartersSet:1361318508899 Headquarters:169.92906931820792,65.0,209.31066111932847,22.049545,36.14993 players:kmlanglois,protocos admins:kmlanglois");
		xTeam.tm.addTeam(team1);
		Team team2 = Team.generateTeamFromProperties("name:two open:false default:false timeHeadquartersSet:0 hq: leader:mastermind admins:mastermind players:mastermind");
		xTeam.tm.addTeam(team2);
		Team team3 = Team.generateTeamFromProperties("name:red world:world open:true timeHeadquartersSet:0 Headquarters:0.0,0.0,0.0,0.0,0.0 leader:default admins: players:strandedhelix");
		xTeam.tm.addTeam(team3);
		Team team4 = Team.generateTeamFromProperties("name:blue world:world open:true timeHeadquartersSet:0 Headquarters:0.0,0.0,0.0,0.0,0.0 leader:default admins: players:");
		xTeam.tm.addTeam(team4);
		//ACT
		onDisable();
		xTeam.tm.clear();
		onEnable();
		//ASSERT
		Assert.assertEquals("[name:one tag:one open:false default:false timeHeadquartersSet:1361318508899 hq:world,169.92906931820792,65.0,209.31066111932847,22.049545,36.14993 leader:kmlanglois admins:kmlanglois players:kmlanglois,protocos, " +
				"name:two tag:two open:false default:false timeHeadquartersSet:0 hq: leader:mastermind admins:mastermind players:mastermind, " +
				"name:red tag:red open:true default:true timeHeadquartersSet:0 hq: leader: admins: players:strandedhelix, " +
				"name:blue tag:blue open:true default:true timeHeadquartersSet:0 hq: leader: admins: players:]", xTeam.tm.getAllTeams().toString());
	}
	@Test
	public void ShouldBeWriteThenReadGenerateDefaultTeams()
	{
		//ASSEMBLE
		Team team1 = Team.generateTeamFromProperties("name:one world:world open:false leader:kmlanglois timeHeadquartersSet:1361318508899 Headquarters:169.92906931820792,65.0,209.31066111932847,22.049545,36.14993 players:kmlanglois,protocos admins:kmlanglois");
		xTeam.tm.addTeam(team1);
		Team team2 = Team.generateTeamFromProperties("name:two open:false default:false timeHeadquartersSet:0 hq: leader:mastermind admins:mastermind players:mastermind");
		xTeam.tm.addTeam(team2);
		//ACT
		onDisable();
		xTeam.tm.clear();
		onEnable();
		Data.DEFAULT_TEAM_NAMES.add("red");
		Data.DEFAULT_TEAM_NAMES.add("blue");
		Data.ensureDefaultTeams();
		//ASSERT
		Assert.assertEquals("[name:one tag:one open:false default:false timeHeadquartersSet:1361318508899 hq:world,169.92906931820792,65.0,209.31066111932847,22.049545,36.14993 leader:kmlanglois admins:kmlanglois players:kmlanglois,protocos, " +
				"name:two tag:two open:false default:false timeHeadquartersSet:0 hq: leader:mastermind admins:mastermind players:mastermind, " +
				"name:red tag:red open:true default:true timeHeadquartersSet:0 hq: leader: admins: players:, " +
				"name:blue tag:blue open:true default:true timeHeadquartersSet:0 hq: leader: admins: players:]", xTeam.tm.getAllTeams().toString());
	}
	@After
	public void takedown()
	{
		//		for (Team team : xTeam.tm.getAllTeams())
		//			System.out.println(team);
	}
	private void onDisable()
	{
		Functions.writeTeamData(new File("teams.txt"));
	}
	private void onEnable()
	{
		Data.load();
		Functions.readTeamData(new File("teams.txt"));
	}
}