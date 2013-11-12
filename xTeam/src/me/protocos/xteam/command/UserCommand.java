package me.protocos.xteam.command;

import me.protocos.xteam.xTeamPlugin;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.core.exception.TeamException;
import org.bukkit.command.CommandSender;

public abstract class UserCommand extends PlayerCommand
{
	protected TeamPlayer teamPlayer;
	protected Team team;

	public UserCommand()
	{
		super();
	}

	@Override
	public void initData(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		super.initData(originalSender, parseCommand);
		teamPlayer = xTeamPlugin.getInstance().getPlayerManager().getPlayer(player);
		team = teamPlayer.getTeam();
		Requirements.checkPlayerHasPermission(originalSender, getPermissionNode());
		Requirements.checkPlayerCommandIsValid(parseCommand, getPattern());
	}
}
