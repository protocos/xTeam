package me.protocos.xteam.command;

import java.io.InvalidClassException;
import me.protocos.xteam.api.command.IPermissionNode;
import me.protocos.xteam.core.PlayerManager;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.core.exception.TeamInvalidCommandException;
import me.protocos.xteam.core.exception.TeamPlayerPermissionException;
import me.protocos.xteam.util.PermissionUtil;
import me.protocos.xteam.util.StringUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class PlayerCommand extends BaseCommand implements IPermissionNode
{
	protected Player player;
	protected TeamPlayer teamPlayer;
	protected Team team;

	public PlayerCommand()
	{
		super();
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, InvalidClassException
	{
		if (!(originalSender instanceof Player))
			throw new InvalidClassException("Sender not an instance of Player");
		player = (Player) originalSender;
		teamPlayer = PlayerManager.getPlayer(player);
		team = teamPlayer.getTeam();
		if (!PermissionUtil.hasPermission(originalSender, getPermissionNode()))
			throw new TeamPlayerPermissionException();
		if (!parseCommand.getCommandWithoutID().matches(StringUtil.IGNORE_CASE + getPattern()))
			throw new TeamInvalidCommandException("Not a valid command: \"" + parseCommand.getCommandWithoutID() + "\" does not match \"" + getPattern() + "\"");
	}
}