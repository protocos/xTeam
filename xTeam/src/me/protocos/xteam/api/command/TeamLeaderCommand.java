package me.protocos.xteam.api.command;

import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.exception.TeamException;
import org.bukkit.command.CommandSender;

public abstract class TeamLeaderCommand extends PlayerCommand
{
	@Override
	public void initData(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		super.initData(originalSender, parseCommand);
		Requirements.checkPlayerHasTeam(teamPlayer);
		Requirements.checkPlayerIsTeamLeader(teamPlayer);
	}
}
