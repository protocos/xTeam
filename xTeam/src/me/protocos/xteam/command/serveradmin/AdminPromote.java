package me.protocos.xteam.command.serveradmin;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.core.ITeamPlayer;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.PlayerManager;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.exception.TeamException;
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
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		super.checkRequirements(originalSender, parseCommand);
		teamName = parseCommand.get(1);
		playerName = parseCommand.get(2);
		changeTeam = xTeam.getTeamManager().getTeam(teamName);
		ITeamPlayer playerPromote = PlayerManager.getPlayer(playerName);
		Requirements.checkPlayerHasPlayedBefore(playerPromote);
		Requirements.checkTeamExists(teamName);
		Requirements.checkPlayerHasTeam(playerPromote);
		Requirements.checkPlayerOnTeam(playerPromote, changeTeam);
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
