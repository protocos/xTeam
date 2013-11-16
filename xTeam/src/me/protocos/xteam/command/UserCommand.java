package me.protocos.xteam.command;

import me.protocos.xteam.xTeam;
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
		teamPlayer = xTeam.getInstance().getPlayerManager().getPlayer(player);
		team = teamPlayer.getTeam();
	}
}
