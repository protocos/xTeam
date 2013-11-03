package me.protocos.xteam.command.serveradmin;

import static me.protocos.xteam.util.StringUtil.*;
import java.io.InvalidClassException;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.core.ITeamPlayer;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.core.PlayerManager;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.exception.*;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class AdminDemote extends ServerAdminCommand
{
	private String teamName, playerName;
	private Team changeTeam;

	public AdminDemote()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		changeTeam.demote(playerName);
		if (!changeTeam.containsPlayer(originalSender.getName()))
			originalSender.sendMessage("You " + ChatColor.RED + "demoted" + ChatColor.RESET + " " + playerName);
		ITeamPlayer other = PlayerManager.getPlayer(playerName);
		other.sendMessage("You have been " + ChatColor.RED + "demoted" + ChatColor.RESET + " by an admin");
	}
	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, InvalidClassException
	{
		super.checkRequirements(originalSender, parseCommand);
		teamName = parseCommand.get(1);
		playerName = parseCommand.get(2);
		changeTeam = xTeam.getTeamManager().getTeam(teamName);
		ITeamPlayer playerDemote = PlayerManager.getPlayer(playerName);
		Team playerTeam = playerDemote.getTeam();
		if (!playerDemote.hasPlayedBefore())
		{
			throw new TeamPlayerNeverPlayedException();
		}
		if (changeTeam == null)
		{
			throw new TeamDoesNotExistException();
		}
		if (playerTeam == null)
		{
			throw new TeamPlayerHasNoTeamException();
		}
		if (!playerDemote.isAdmin())
		{
			throw new TeamPlayerNotAdminException();
		}
		if (!changeTeam.equals(playerTeam))
		{
			throw new TeamPlayerNotOnTeamException();
		}
		if (playerDemote.isLeader())
		{
			throw new TeamPlayerLeaderDemoteException();
		}
	}
	@Override
	public String getPattern()
	{
		return patternOneOrMore("demote") + WHITE_SPACE + ANY_CHARS + WHITE_SPACE + ANY_CHARS + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getPermissionNode()
	{
		return "xteam.serveradmin.core.demote";
	}
	@Override
	public String getUsage()
	{
		return "/team demote [Team] [Player]";
	}
}
