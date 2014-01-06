package me.protocos.xteam;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import me.protocos.xteam.api.TeamPlugin;
import me.protocos.xteam.api.collections.HashList;
import me.protocos.xteam.api.command.ICommandManager;
import me.protocos.xteam.api.core.IPlayerManager;
import me.protocos.xteam.api.core.ITeamManager;
import me.protocos.xteam.api.entity.ITeam;
import me.protocos.xteam.api.model.ILog;
import me.protocos.xteam.command.CommandManager;
import me.protocos.xteam.configuration.Configuration;
import me.protocos.xteam.core.PlayerManager;
import me.protocos.xteam.core.TeamManager;
import me.protocos.xteam.entity.Team;
import me.protocos.xteam.model.Headquarters;
import me.protocos.xteam.model.Log;
import me.protocos.xteam.util.BukkitUtil;
import me.protocos.xteam.util.CommonUtil;
import me.protocos.xteam.util.StringUtil;
import me.protocos.xteam.util.SystemUtil;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.permissions.Permission;

public final class xTeam
{
	private static xTeam xteam;
	private String version;
	private ILog logger;
	private IPlayerManager playerManager;
	private ITeamManager teamManager;
	private ICommandManager commandManager;
	private List<Permission> permissions;
	private Configuration configLoader;

	private xTeam()
	{
		this.playerManager = new PlayerManager();
		this.teamManager = new TeamManager();
		this.commandManager = new CommandManager();
	}

	public static xTeam getInstance()
	{
		if (xteam == null)
		{
			xteam = new xTeam();
		}
		return xteam;
	}

	public ILog getLog()
	{
		return logger;
	}

	public ICommandManager getCommandManager()
	{
		return commandManager;
	}

	public ITeamManager getTeamManager()
	{
		return teamManager;
	}

	public IPlayerManager getPlayerManager()
	{
		return playerManager;
	}

	public List<Permission> getPermissions()
	{
		return permissions;
	}

	public String getVersion()
	{
		return version;
	}

	public void load(TeamPlugin plugin)
	{
		if ("xTeamPlugin".equals(plugin.getPluginName()) || "FakeTeamPlugin".equals(plugin.getPluginName()))
		{
			this.version = plugin.getVersion();
			this.logger = new Log(plugin.getFolder() + "/xTeam.log", plugin);
			this.permissions = new ArrayList<Permission>(plugin.getPermissions());
			this.initFileSystem(plugin);
		}
		else
		{
			this.permissions.addAll(plugin.getPermissions());
		}
		this.commandManager.register(plugin);
	}

	private void initFileSystem(TeamPlugin plugin)
	{
		SystemUtil.ensureFolder(plugin.getFolder());
		SystemUtil.ensureFile(plugin.getFolder() + "/teams.txt");
		this.configLoader = new Configuration(SystemUtil.ensureFile(plugin.getFolder() + "/xTeam.cfg"));
		this.configLoader.addAttribute("playersonteam", 10, "Amount of players that can be on a team");
		this.configLoader.addAttribute("sethqinterval", 0, "Delay in hours between use of /team sethq");
		this.configLoader.addAttribute("teleportradius", 500, "Maximum distance in blocks between team mates to teleport to one another");
		this.configLoader.addAttribute("canteamchat", true, "Allows/Disallows the use of team chat function completely");
		this.configLoader.addAttribute("hqondeath", true, "When a player dies, they are teleported to their headquarters when they respawn");
		this.configLoader.addAttribute("enemyproximity", 16, "When teleporting, if enemies are within this radius of blocks, the teleport is delayed");
		this.configLoader.addAttribute("teledelay", 10, "Delay in seconds for teleporting when enemies are near");
		this.configLoader.addAttribute("telerefreshdelay", 0, "Delay in seconds for when you can use team teleporting. Does not include /team return");
		this.configLoader.addAttribute("createteamdelay", 20, "Delay in minutes for creating teams");
		this.configLoader.addAttribute("teamwolves", true, "Protects your wolfies from you and your teammates from damaging them");
		this.configLoader.addAttribute("defaultteams", "", "Default list of teams for the server separated by commas  (e.g. defaultteams=red,green,blue,yellow)");
		this.configLoader.addAttribute("randomjointeam", false, "Player randomly joins one of the default teams on joining");
		this.configLoader.addAttribute("balanceteams", false, "Balance teams when someone randomly joins");
		this.configLoader.addAttribute("onlyjoindefaultteam", false, "When true, players can only join one of the default teams listed above");
		this.configLoader.addAttribute("defaulthqonjoin", false, "When true, players on default teams are teleported to their headquarters on join");
		//		this.configLoader.addAttribute("anonymouserrorreporting", true, "When true, sends anonymous error reports for faster debugging");
		this.configLoader.addAttribute("lastattackeddelay", 15, "How long a player has to wait after being attacked to teleport");
		this.configLoader.addAttribute("teamtagenabled", true, "When true, players have their team tag displayed when in chat");
		this.configLoader.addAttribute("teamtagmaxlength", 0, "Maximum length of a team tag (0 = unlimited)");
		this.configLoader.addAttribute("disabledworlds", "", "World names, separated by commas, that xTeam is disabled in (e.g. disabledworlds=world,world_nether,world_the_end)");
		this.configLoader.addAttribute("nopermissions", false, "When true, xTeam will give all regular commands to players and admin commands to OPs");
		this.configLoader.addAttribute("teamfriendlyfire", false, "When true, friendly fire will be enabled for all teams");
		this.configLoader.addAttribute("alphanumericnames", true, "When true, players can only create teams with alphanumeric names and no symbols (e.g. TeamAwesome123)");
		this.configLoader.addAttribute("displaycoordinates", true, "When true, players can see coordinates of other team mates in team info");
		this.configLoader.addAttribute("tagcolor", "green", "Color representing the color of the tag in game (e.g. green, dark_red, light_purple)");
		this.configLoader.addAttribute("chatnamecolor", "dark_green", "Color representing the color of player names in team chat (e.g. green, dark_red, light_purple)");
		this.configLoader.addAttribute("rallydelay", 2, "Delay in minutes that a team rally stays active");
		this.configLoader.addAttribute("newparam", 1, "Delay in minutes that a team rally stays active");
		this.configLoader.write();
		this.configLoader.load();
	}

	public void readTeamData(File f)
	{
		// < 1.6.4 format = name password world 1362778787799 hq: 158.01133435293434 66.0 204.6646064980176 -205.5249 28.20008 protocos~~
		// < 1.7.4 format = name:one world:world open:true leader:protocos timeHeadquartersSet:1362778406367 Headquarters:161.56076240936164,64.0,221.25238913113412,-206.87492,30.750084 players:protocos admins:protocos
		// = 1.7.4 format = name:one open:false default:false timeHeadquartersSet:0 Headquarters: leader:protocos admins:protocos players:protocos,kmlanglois
		try
		{
			Scanner input = new Scanner(f);
			String line;
			if (input.hasNext() && (line = input.nextLine()) != null && !line.contains("name:"))
			{
				readOldData(f);
				return;
			}
			//TODO fix the FileReader being from java.io.FileReader and local
			BufferedReader br = new BufferedReader(new FileReader(f));
			while ((line = br.readLine()) != null)
			{
				Team team = Team.generateTeamFromProperties(line);
				xTeam.getInstance().getTeamManager().createTeam(team);
			}
			br.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void writeTeamData(File f)
	{
		ArrayList<String> data = new ArrayList<String>();
		try
		{
			HashList<String, ITeam> teams = this.getTeamManager().getTeams();
			for (ITeam team : teams)
				data.add(team.toString());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		try
		{
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			for (String line : data)
			{
				bw.write(line + "\n");
			}
			bw.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Deprecated
	public void readOldData(File f) throws NumberFormatException, IOException
	{
		// team data as it was stored before 1.6.4
		BufferedReader br = new BufferedReader(new FileReader(f));
		String line;
		while ((line = br.readLine()) != null)
		{
			String[] s = line.split(" ");
			String teamName = s[0];
			// String pass = s[1];
			World world = BukkitUtil.getWorld(s[2]);
			long timeHeadquartersSet = Long.valueOf(s[3]).longValue();
			Location HQ = new Location(world, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
			Set<String> players = CommonUtil.emptySet();
			Set<String> admins = CommonUtil.emptySet();
			String leader = "";
			int startIndex = 4;
			if (s.length > 4)
			{
				if (s[4].equalsIgnoreCase("hq:"))
				{
					HQ.setWorld(world);
					HQ.setX(Double.valueOf(s[5]).doubleValue());
					HQ.setY(Double.valueOf(s[6]).doubleValue());
					HQ.setZ(Double.valueOf(s[7]).doubleValue());
					HQ.setYaw(Float.valueOf(s[8]).floatValue());
					HQ.setPitch(Float.valueOf(s[9]).floatValue());
					startIndex = 10;
				}
				for (int i = startIndex; i < s.length; i++)
				{
					if (s[i].contains("~~"))
						leader = s[i].replaceAll("~", "");
					if (s[i].contains("~"))
						admins.add(s[i].replaceAll("~", ""));
					players.add(s[i].replaceAll("~", ""));
				}
			}
			if (StringUtil.toLowerCase(Configuration.DEFAULT_TEAM_NAMES).contains(teamName.toLowerCase()))
			{
				for (ITeam team : this.getTeamManager().getDefaultTeams())
				{
					if (team.getName().toLowerCase().equalsIgnoreCase(teamName))
					{
						team.setPlayers(players);
						if (HQ.getY() != 0.0D)
							team.setHeadquarters(new Headquarters(HQ));
					}
				}
			}
			else
			{
				ITeam team = new Team.Builder(teamName).tag(teamName).leader(leader).players(players).admins(admins).hq(new Headquarters(HQ)).timeHeadquartersSet(timeHeadquartersSet).openJoining(false).defaultTeam(false).build();
				for (int i = startIndex; i < s.length; i++)
				{
					s[i] = s[i].replaceAll("~", "");
				}
				this.getTeamManager().createTeam(team);
			}
		}
		br.close();
	}

	@Deprecated
	public void writeOldData(File f)
	{
		try
		{
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			HashList<String, ITeam> teams = this.getTeamManager().getTeams();
			for (ITeam team : teams)
				bw.write(team.toString() + "\n");
			bw.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
