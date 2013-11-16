package me.protocos.xteam.command;

import me.protocos.xteam.api.command.IPermissible;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.CommonUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class PlayerCommand extends BaseCommand implements IPermissible
{
	protected Player player;

	public PlayerCommand()
	{
		super();
	}

	@Override
	public void initData(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		player = CommonUtil.assignFromType(originalSender, Player.class);
		Requirements.checkPlayerHasPermission(originalSender, this.getPermissionNode());
		Requirements.checkPlayerCommandIsValid(parseCommand, this.getPattern());
	}
}