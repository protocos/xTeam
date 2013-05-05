package me.protocos.xteam.command.serveradmin;

import static me.protocos.xteam.util.StringUtil.*;
import java.util.ArrayList;
import java.util.Arrays;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.BaseServerAdminCommand;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.core.exception.*;
import me.protocos.xteam.util.PermissionUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class AdminSet extends BaseServerAdminCommand
{
	private String playerName, teamName;

	public AdminSet()
	{
		super();
	}
	public AdminSet(Player sender, String command)
	{
		super(sender, command);
	}
	@Override
	protected void act()
	{
		TeamPlayer teamPlayer = new TeamPlayer(playerName);
		Team playerTeam = teamPlayer.getTeam();
		Team desiredTeam = xTeam.tm.getTeam(teamName);
		if (teamPlayer.hasTeam() || teamName.equalsIgnoreCase("none"))
		{
			playerTeam.removePlayer(playerName);
			Data.chatStatus.remove(playerName);
			TeamPlayer other = new TeamPlayer(playerName);
			if (other.isOnline())
				other.sendMessage(ChatColor.RED + "You have been " + ChatColor.RED + "removed" + ChatColor.WHITE + " from " + playerTeam.getName());
			originalSender.sendMessage(playerName + " has been removed from " + playerTeam.getName());
			if (teamName.equalsIgnoreCase("none"))
			{
				if (playerTeam.isEmpty())
				{
					originalSender.sendMessage(playerTeam.getName() + " has been deleted");
					xTeam.tm.removeTeam(playerTeam.getName());
				}
				return;
			}
		}
		if (desiredTeam == null)
		{
			ArrayList<String> players = new ArrayList<String>(Arrays.asList(playerName));
			Team tempTeam = new Team.Builder(teamName).players(players).admins(players).leader(playerName).build();
			xTeam.tm.addTeam(tempTeam);
			TeamPlayer other = new TeamPlayer(playerName);
			if (other.isOnline())
				other.sendMessage("You have been " + ChatColor.GREEN + "added" + ChatColor.WHITE + " to " + tempTeam.getName());
			originalSender.sendMessage(teamName + " has been created");
			originalSender.sendMessage(playerName + " has been added to " + tempTeam.getName());
		}
		else
		{
			for (String pl : desiredTeam.getOnlinePlayers())
			{
				TeamPlayer teammate = new TeamPlayer(pl);
				teammate.sendMessage(playerName + " has been added to your team");
			}
			desiredTeam.addPlayer(playerName);
			originalSender.sendMessage(playerName + " has been added to " + teamName);
			TeamPlayer other = new TeamPlayer(playerName);
			if (other.isOnline())
				other.sendMessage("You have been added to " + ChatColor.GREEN + teamName);
		}
		if (playerTeam != null && playerTeam.isEmpty())
		{
			originalSender.sendMessage(playerTeam.getName() + " has been deleted");
			xTeam.tm.removeTeam(playerTeam.getName());
		}
	}
	@Override
	public void checkRequirements() throws TeamException
	{
		if (!PermissionUtil.hasPermission(originalSender, getPermissionNode()))
		{
			throw new TeamPlayerPermissionException();
		}
		if (player == null)
		{
			throw new TeamPlayerDoesNotExistException();
		}
		if (parseCommand.size() == 3)
		{
			playerName = parseCommand.get(1);
			teamName = parseCommand.get(2);
		}
		else
		{
			throw new TeamInvalidCommandException();
		}
		TeamPlayer p = new TeamPlayer(playerName);
		Team team = p.getTeam();
		if (!p.hasPlayedBefore())
		{
			throw new TeamPlayerNeverPlayedException();
		}
		if (team != null && team.getLeader().equals(playerName) && team.getPlayers().size() > 1)
		{
			throw new TeamPlayerLeaderLeavingException();
		}
	}
	@Override
	public String getPattern()
	{
		return patternOneOrMore("set") + WHITE_SPACE + ANY_CHARS + WHITE_SPACE + ANY_CHARS + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getPermissionNode()
	{
		return "xteam.serveradmin.core.set";
	}
	@Override
	public String getUsage()
	{
		return baseCommand + " set [Player] [Team]";
	}
}
