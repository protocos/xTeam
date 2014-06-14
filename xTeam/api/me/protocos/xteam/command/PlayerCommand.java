package me.protocos.xteam.command;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.CommonUtil;
import org.bukkit.entity.Player;

public abstract class PlayerCommand extends BaseCommand implements IPermissible
{
	public PlayerCommand(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
	}

	@Override
	public void preInitialize(CommandContainer commandContainer) throws TeamException
	{
		Player player = CommonUtil.assignFromType(commandContainer.getSender(), Player.class);
		Requirements.checkPlayerWorldDisabled(player);
		Requirements.checkPlayerHasPermission(commandContainer.getSender(), this);
		Requirements.checkCommandIsValid(commandContainer.getCommandWithoutID(), this.getPattern());
	}
}