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

public class AdminRemove extends ServerAdminCommand
{
	private String teamName, playerName;

	public AdminRemove()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		TeamPlayer teamPlayer = new TeamPlayer(playerName);
		Team team = teamPlayer.getTeam();
		team.removePlayer(playerName);
		if (!playerName.equals(originalSender.getName()))
			originalSender.sendMessage("You " + ChatColor.RED + "removed" + ChatColor.RESET + " " + playerName + " from " + teamName);
		teamPlayer.sendMessage("You have been " + ChatColor.RED + "removed" + ChatColor.RESET + " from " + team.getName() + " by an admin");
		if (team.isEmpty())
		{
			originalSender.sendMessage(teamName + " has been " + ChatColor.RED + "disbanded");
			xTeam.tm.removeTeam(team.getName());
		}
	}
	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, InvalidClassException
	{
		super.checkRequirements(originalSender, parseCommand);
		teamName = parseCommand.get(1);
		playerName = parseCommand.get(2);
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
		return "/team remove [Team] [Player]";
	}
}
