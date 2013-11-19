package me.protocos.xteam.command.serveradmin;

import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.ServerAdminCommand;
import me.protocos.xteam.api.core.ITeamPlayer;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import me.protocos.xteam.util.PatternBuilder;
import org.bukkit.command.CommandSender;

public class ServerAdminPromote extends ServerAdminCommand
{
	private String teamName, playerName;
	private Team changeTeam;

	public ServerAdminPromote()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		changeTeam.promote(playerName);
		if (!changeTeam.containsPlayer(originalSender.getName()))
			originalSender.sendMessage("You " + ChatColorUtil.positiveMessage("promoted") + " " + playerName);
		ITeamPlayer other = xTeam.getInstance().getPlayerManager().getPlayer(playerName);
		other.sendMessage("You've been " + ChatColorUtil.positiveMessage("promoted") + " by an admin");
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		teamName = parseCommand.get(1);
		playerName = parseCommand.get(2);
		changeTeam = xTeam.getInstance().getTeamManager().getTeam(teamName);
		ITeamPlayer playerPromote = xTeam.getInstance().getPlayerManager().getPlayer(playerName);
		Requirements.checkPlayerHasPlayedBefore(playerPromote);
		Requirements.checkTeamExists(teamName);
		Requirements.checkPlayerHasTeam(playerPromote);
		Requirements.checkPlayerOnTeam(playerPromote, changeTeam);
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.oneOrMore("promote")
				.whiteSpace()
				.anyString()
				.whiteSpace()
				.anyString()
				.whiteSpaceOptional()
				.toString();
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

	@Override
	public String getDescription()
	{
		return "promote player to admin";
	}
}
