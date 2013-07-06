package me.protocos.xteam.command.serveradmin;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.BaseServerAdminCommand;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.core.exception.*;
import me.protocos.xteam.util.PermissionUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class AdminSetLeader extends BaseServerAdminCommand
{
	private String teamName, playerName;

	public AdminSetLeader(Player sender, String command)
	{
		super(sender, command);
	}
	@Override
	protected void act()
	{
		TeamPlayer playerSet = new TeamPlayer(playerName);
		Team team = playerSet.getTeam();
		team.setLeader(playerName);
		if (playerSet.isOnline() && !playerSet.getName().equals(player.getName()))
			playerSet.sendMessage(ChatColor.GREEN + "You" + ChatColor.RESET + " are now the team leader");
		TeamPlayer previousLeader = new TeamPlayer(team.getLeader());
		if (previousLeader.isOnline() && !previousLeader.getName().equals(player.getName()))
			previousLeader.sendMessage(ChatColor.GREEN + playerName + ChatColor.RESET + " is now the team leader");
		player.sendMessage(ChatColor.GREEN + playerName + ChatColor.RESET + " is now the team leader for " + team.getName());
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
		Team desiredTeam = xTeam.tm.getTeam(teamName);
		TeamPlayer playerSet = new TeamPlayer(playerName);
		Team team = playerSet.getTeam();
		if (!playerSet.hasPlayedBefore())
		{
			throw new TeamPlayerNeverPlayedException();
		}
		if (desiredTeam == null)
		{
			throw new TeamDoesNotExistException();
		}
		if (team == null)
		{
			throw new TeamPlayerHasNoTeamException();
		}
		if (!desiredTeam.equals(team))
		{
			throw new TeamPlayerNotOnTeamException();
		}
		if (team.isDefaultTeam())
		{
			throw new TeamIsDefaultException();
		}
	}
	@Override
	public String getPattern()
	{
		return "set" + patternOneOrMore("leader") + WHITE_SPACE + ANY_CHARS + WHITE_SPACE + ANY_CHARS + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getPermissionNode()
	{
		return "xteam.serveradmin.core.setleader";
	}
	@Override
	public String getUsage()
	{
		return baseCommand + " setleader [Team] [Player]";
	}
}
