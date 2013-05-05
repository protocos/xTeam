package me.protocos.xteam.command.serveradmin;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.command.BaseServerAdminCommand;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.core.exception.*;
import me.protocos.xteam.util.PermissionUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class AdminRemove extends BaseServerAdminCommand
{
	private String teamName, playerName;

	public AdminRemove()
	{
		super();
	}
	public AdminRemove(Player sender, String command)
	{
		super(sender, command);
	}
	@Override
	protected void act()
	{
		TeamPlayer p = new TeamPlayer(playerName);
		Team team = p.getTeam();
		team.removePlayer(playerName);
		//		Data.Teams.remove(playerName);
		player.sendMessage("You" + ChatColor.RED + " removed " + ChatColor.RESET + playerName + " from " + teamName);
		if (p.isOnline())
			p.sendMessage("You've been " + ChatColor.RED + "removed" + ChatColor.RESET + " from " + team.getName());
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
			teamName = parseCommand.get(1);
			playerName = parseCommand.get(2);
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
		if (team == null)
		{
			throw new TeamPlayerHasNoTeamException();
		}
		if (team.getLeader().equals(playerName) && team.getPlayers().size() > 1)
		{
			throw new TeamPlayerLeaderLeavingException();
		}
	}
	@Override
	public String getPattern()
	{
		return patternOneOrMore("re") + patternOneOrMore("move") + WHITE_SPACE + ANY_CHARS + WHITE_SPACE + ANY_CHARS + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getPermissionNode()
	{
		return "xteam.serveradmin.core.remove";
	}
	@Override
	public String getUsage()
	{
		return baseCommand + " remove [Team] [Player]";
	}
}
