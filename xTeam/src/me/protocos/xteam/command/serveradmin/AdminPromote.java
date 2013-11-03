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

public class AdminPromote extends ServerAdminCommand
{
	private String teamName, playerName;
	private Team changeTeam;

	public AdminPromote()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		changeTeam.promote(playerName);
		if (!changeTeam.containsPlayer(originalSender.getName()))
			originalSender.sendMessage("You " + ChatColor.GREEN + "promoted" + ChatColor.RESET + " " + playerName);
		ITeamPlayer other = PlayerManager.getPlayer(playerName);
		other.sendMessage("You've been " + ChatColor.GREEN + "promoted" + ChatColor.RESET + " by an admin");
	}
	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, InvalidClassException
	{
		super.checkRequirements(originalSender, parseCommand);
		teamName = parseCommand.get(1);
		playerName = parseCommand.get(2);
		changeTeam = xTeam.getTeamManager().getTeam(teamName);
		ITeamPlayer playerPromote = PlayerManager.getPlayer(playerName);
		Team playerTeam = playerPromote.getTeam();
		if (!playerPromote.hasPlayedBefore())
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
		if (!changeTeam.equals(playerTeam))
		{
			throw new TeamPlayerNotOnTeamException();
		}
	}
	@Override
	public String getPattern()
	{
		return patternOneOrMore("promote") + WHITE_SPACE + ANY_CHARS + WHITE_SPACE + ANY_CHARS + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getPermissionNode()
	{
		return "xteam.serveradmin.core.promote";
	}
	@Override
	public String getUsage()
	{
		return "/team promote [Team] [Player]";
	}
}
