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

public class ConsoleSetLeader extends ConsoleCommand
{
	private String teamName, playerName;

	public ConsoleSetLeader()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		ITeamPlayer player = xTeam.getInstance().getPlayerManager().getPlayer(playerName);
		Team team = player.getTeam();
		team.setLeader(playerName);
		if (player.isOnline())
			player.sendMessage("You are now the " + ChatColorUtil.positiveMessage("team leader"));
		if (!team.isDefaultTeam())
		{
			ITeamPlayer previousLeader = xTeam.getInstance().getPlayerManager().getPlayer(team.getLeader());
			if (previousLeader.isOnline())
				previousLeader.sendMessage(playerName + " is now the " + ChatColorUtil.positiveMessage("team leader"));
		}
		originalSender.sendMessage(playerName + " is now the " + ChatColorUtil.positiveMessage("team leader") + " for " + team.getName());
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		teamName = parseCommand.get(1);
		playerName = parseCommand.get(2);
		ITeamPlayer player = xTeam.getInstance().getPlayerManager().getPlayer(playerName);
		Team team = xTeam.getInstance().getTeamManager().getTeam(teamName);
		Requirements.checkPlayerHasPlayedBefore(player);
		Requirements.checkTeamExists(teamName);
		Requirements.checkPlayerHasTeam(player);
		Requirements.checkPlayerOnTeam(player, team);
		Requirements.checkTeamIsDefault(team);
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.oneOrMore("set")
				.oneOrMore("leader")
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
		return "/team setleader [Team] [Player]";
	}

	@Override
	public String getDescription()
	{
		return "set leader of team";
	}
}
