package me.protocos.xteam.command;

import me.protocos.xteam.api.command.IPermissionNode;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.PlayerManager;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.CommonUtil;
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
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		player = CommonUtil.assignFromType(originalSender, Player.class);
		teamPlayer = PlayerManager.getPlayer(player);
		team = teamPlayer.getTeam();
		Requirements.checkPlayerHasPermission(originalSender, getPermissionNode());
		Requirements.checkPlayerCommandIsValid(parseCommand, getPattern());
	}
}