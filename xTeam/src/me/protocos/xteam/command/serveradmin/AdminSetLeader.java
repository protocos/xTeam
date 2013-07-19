package me.protocos.xteam.command.serveradmin;

import static me.protocos.xteam.util.StringUtil.*;
import java.io.InvalidClassException;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.core.exception.*;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class AdminSetLeader extends ServerAdminCommand
{
	private String teamName, playerName;

	public AdminSetLeader()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		TeamPlayer playerSet = new TeamPlayer(playerName);
		Team team = playerSet.getTeam();
		team.setLeader(playerName);
		if (playerSet.isOnline() && !playerSet.getName().equals(originalSender.getName()))
			playerSet.sendMessage(ChatColor.GREEN + "You" + ChatColor.RESET + " are now the team leader");
		TeamPlayer previousLeader = new TeamPlayer(team.getLeader());
		if (previousLeader.isOnline() && !previousLeader.getName().equals(originalSender.getName()))
			previousLeader.sendMessage(ChatColor.GREEN + playerName + ChatColor.RESET + " is now the team leader");
		originalSender.sendMessage(ChatColor.GREEN + playerName + ChatColor.RESET + " is now the team leader for " + team.getName());
	}
	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, InvalidClassException
	{
		super.checkRequirements(originalSender, parseCommand);
		teamName = parseCommand.get(1);
		playerName = parseCommand.get(2);
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
		return "/team setleader [Team] [Player]";
	}
}
