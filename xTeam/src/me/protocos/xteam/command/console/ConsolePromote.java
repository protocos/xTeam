package me.protocos.xteam.command.console;

import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.ConsoleCommand;
import me.protocos.xteam.api.core.ITeamPlayer;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import me.protocos.xteam.util.PatternBuilder;
import org.bukkit.command.CommandSender;

public class ConsolePromote extends ConsoleCommand
{
	private String teamName, playerName;

	public ConsolePromote()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		Team team = xTeam.getInstance().getTeamManager().getTeam(teamName);
		team.promote(playerName);
		originalSender.sendMessage("You " + ChatColorUtil.positiveMessage("promoted ") + playerName);
		ITeamPlayer other = xTeam.getInstance().getPlayerManager().getPlayer(playerName);
		if (other.isOnline())
			other.sendMessage("You've been " + ChatColorUtil.positiveMessage("promoted"));
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		teamName = parseCommand.get(1);
		playerName = parseCommand.get(2);
		ITeamPlayer player = xTeam.getInstance().getPlayerManager().getPlayer(playerName);
		Team team = xTeam.getInstance().getTeamManager().getTeam(teamName);
		Requirements.checkTeamExists(teamName);
		Requirements.checkPlayerHasPlayedBefore(player);
		Requirements.checkPlayerHasTeam(player);
		Requirements.checkPlayerOnTeam(player, team);
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
