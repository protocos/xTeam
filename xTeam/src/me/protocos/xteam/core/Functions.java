package me.protocos.xteam.core;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.util.SpoutUtil;
import me.protocos.xteam.util.StringUtil;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.getspout.spoutapi.SpoutManager;

public class Functions
{
	public static boolean isEnemy(TeamPlayer player, Entity entity)
	{
		String type = entity.toString();
		if (type.equalsIgnoreCase("CraftBlaze"))
			return true;
		if (type.equalsIgnoreCase("CraftCreeper"))
			return true;
		if (type.equalsIgnoreCase("CraftEnderman"))
			return true;
		if (type.equalsIgnoreCase("CraftGiant"))
			return true;
		if (type.equalsIgnoreCase("CraftSilverfish"))
			return true;
		if (type.equalsIgnoreCase("CraftSkeleton"))
			return true;
		if (type.equalsIgnoreCase("CraftSpider"))
			return true;
		if (type.equalsIgnoreCase("CraftCaveSpider"))
			return true;
		if (type.equalsIgnoreCase("CraftWitch"))
			return true;
		if (type.equalsIgnoreCase("CraftWither"))
			return true;
		if (type.equalsIgnoreCase("CraftZombie"))
			return true;
		if (type.equalsIgnoreCase("CraftPigZombie"))
			return true;
		if (type.equalsIgnoreCase("CraftSlime"))
			return true;
		if (type.equalsIgnoreCase("CraftGhast"))
			return true;
		if (type.equalsIgnoreCase("CraftMagmaCube"))
			return true;
		if (type.equalsIgnoreCase("CraftEnderDragon"))
			return true;
		if (type.startsWith("CraftPlayer"))
		{
			Player p = (Player) entity;
			TeamPlayer otherPlayer = new TeamPlayer(p);
			Team otherTeam = otherPlayer.getTeam();
			if (player.getTeam().equals(otherTeam))
				return false;
			return true;
		}
		if (type.startsWith("CraftWolf"))
		{
			Wolf w = (Wolf) entity;
			TeamWolf wolf = new TeamWolf(w);
			TeamPlayer owner = wolf.getOwner();
			if (owner != null && player.getTeam().containsPlayer(owner.getName()))
				return false;
			return true;
		}
		return false;
	}
	@Deprecated
	public static void readOldData(File f) throws NumberFormatException, IOException
	{
		// team data as it was stored before 1.6.4
		BufferedReader br = new BufferedReader(new FileReader(f));
		String line;
		while ((line = br.readLine()) != null)
		{
			String[] s = line.split(" ");
			String teamName = s[0];
			// String pass = s[1];
			World world = Data.BUKKIT.getWorld(s[2]);
			long timeHeadquartersSet = Long.valueOf(s[3]).longValue();
			TeamHeadquarters HQ = new TeamHeadquarters(world, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
			ArrayList<String> players = new ArrayList<String>();
			ArrayList<String> admins = new ArrayList<String>();
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
			if (StringUtil.toLowerCase(Data.DEFAULT_TEAM_NAMES).contains(teamName.toLowerCase()))
			{
				for (Team team : xTeam.tm.getDefaultTeams())
				{
					if (team.getName().toLowerCase().equalsIgnoreCase(teamName))
					{
						team.setPlayers(players);
						if (HQ.getY() != 0.0D)
							team.setHQ(HQ);
					}
				}
			}
			else
			{
				Team team = new Team.Builder(teamName).tag(teamName).leader(leader).players(players).admins(admins).hq(HQ).timeHeadquartersSet(timeHeadquartersSet).openJoining(false).defaultTeam(false).build();
				for (int i = startIndex; i < s.length; i++)
				{
					s[i] = s[i].replaceAll("~", "");
				}
				xTeam.tm.addTeam(team);
			}
		}
		br.close();
	}
	// /////////R/W FUNCTIONS
	public static void readTeamData(File f)
	{
		// < 1.6.4 format = name password world 1362778787799 hq: 158.01133435293434 66.0 204.6646064980176 -205.5249 28.20008 protocos~~
		// < 1.7.4 format = name:one world:world open:true leader:protocos timeHeadquartersSet:1362778406367 UserHeadquarters:161.56076240936164,64.0,221.25238913113412,-206.87492,30.750084 players:protocos admins:protocos
		// = 1.7.4 format = name:one open:false default:false timeHeadquartersSet:0 UserHeadquarters: leader:protocos admins:protocos players:protocos,kmlanglois
		try
		{
			//			File f = new File("plugins/xTeam/teams.txt");
			Scanner input = new Scanner(f);
			String line;
			if (input.hasNext() && (line = input.nextLine()) != null && !line.contains("name:"))
			{
				readOldData(f);
				return;
			}
			//			BufferedReader br = new BufferedReader(new FileReader(f));
			//TODO fix the FileReady being from java.io.FileReader and local
			BufferedReader br = new BufferedReader(new FileReader(f));
			while ((line = br.readLine()) != null)
			{
				Team team = Team.generateTeamFromProperties(line);
				xTeam.tm.addTeam(team);
			}
			br.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	public static void showPlayer(Player player)
	{
		Player[] OnlinePlayers = player.getServer().getOnlinePlayers();
		for (Player p : OnlinePlayers)
		{
			SpoutManager.getPlayer(player).resetTitleFor(SpoutManager.getPlayer(p));
		}
	}
	public static void updatePlayers()
	{
		if (Data.HIDE_NAMES && Data.SPOUT_ENABLED)
		{
			SpoutUtil.hidePlayerNames(Data.BUKKIT.getOnlinePlayers());
			for (Player player1 : Data.BUKKIT.getOnlinePlayers())
			{
				for (Player player2 : Data.BUKKIT.getOnlinePlayers())
				{
					TeamPlayer p1 = new TeamPlayer(player1);
					TeamPlayer p2 = new TeamPlayer(player2);
					if (p1.isOnSameTeam(p2))
					{
						try
						{
							SpoutManager.getPlayer(player1).setTitleFor(SpoutManager.getPlayer(player2), ChatColor.GREEN + p1.getName());
						}
						catch (NullPointerException e)
						{

						}
					}
				}
			}
		}
	}
	@Deprecated
	public static void writeOldData(File f)
	{
		try
		{
			//			File f = new File("plugins/xTeam/teams.txt");
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			List<Team> teams = xTeam.tm.getAllTeams();
			for (Team t : teams)
				bw.write(t.toString() + "\n");
			bw.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	public static void writeTeamData(File f)
	{
		ArrayList<String> data = new ArrayList<String>();
		try
		{
			List<Team> teams = xTeam.tm.getAllTeams();
			for (Team team : teams)
				data.add(team.toString());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		try
		{
			//			File f = new File("plugins/xTeam/teams.txt");
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
	private Functions()
	{
		throw new AssertionError();
	}
}
