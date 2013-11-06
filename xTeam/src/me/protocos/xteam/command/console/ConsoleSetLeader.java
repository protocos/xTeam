package me.protocos.xteam.command.console;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.core.ITeamPlayer;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.PlayerManager;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
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
		ITeamPlayer player = PlayerManager.getPlayer(playerName);
		Team team = player.getTeam();
		team.setLeader(playerName);
		if (player.isOnline())
			player.sendMessage("You are now the " + ChatColorUtil.positiveMessage("team leader"));
		if (!team.isDefaultTeam())
		{
			ITeamPlayer previousLeader = PlayerManager.getPlayer(team.getLeader());
			if (previousLeader.isOnline())
				previousLeader.sendMessage(playerName + " is now the " + ChatColorUtil.positiveMessage("team leader"));
		}
		originalSender.sendMessage(playerName + " is now the " + ChatColorUtil.positiveMessage("team leader") + " for " + team.getName());
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		super.checkRequirements(originalSender, parseCommand);
		teamName = parseCommand.get(1);
		playerName = parseCommand.get(2);
		ITeamPlayer player = PlayerManager.getPlayer(playerName);
		Team team = xTeam.getTeamManager().getTeam(teamName);
		Requirements.checkPlayerHasPlayedBefore(player);
		Requirements.checkTeamExists(teamName);
		Requirements.checkPlayerHasTeam(player);
		Requirements.checkPlayerOnTeam(player, team);
		Requirements.checkTeamIsDefault(team);
	}

	@Override
	public String getPattern()
	{
		return "set" + patternOneOrMore("leader") + WHITE_SPACE + ANY_CHARS + WHITE_SPACE + ANY_CHARS + OPTIONAL_WHITE_SPACE;
	}

	@Override
	public String getUsage()
	{
		return "/team setleader [Team] [Player]";
	}
}
