package me.protocos.xteam.command.serveradmin;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.core.ITeamPlayer;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
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
			originalSender.sendMessage("You " + ChatColorUtil.negativeMessage("demoted") + " " + playerName);
		ITeamPlayer other = xTeam.getPlayerManager().getPlayer(playerName);
		other.sendMessage("You have been " + ChatColorUtil.negativeMessage("demoted") + " by an admin");
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		teamName = parseCommand.get(1);
		playerName = parseCommand.get(2);
		changeTeam = xTeam.getTeamManager().getTeam(teamName);
		ITeamPlayer playerDemote = xTeam.getPlayerManager().getPlayer(playerName);
		Requirements.checkPlayerHasPlayedBefore(playerDemote);
		Requirements.checkTeamExists(teamName);
		Requirements.checkPlayerHasTeam(playerDemote);
		Requirements.checkPlayerIsTeamAdmin(playerDemote);
		Requirements.checkPlayerOnTeam(playerDemote, changeTeam);
		Requirements.checkPlayerLeaderDemote(playerDemote);
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
