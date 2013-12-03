package me.protocos.xteam.api.command;

import org.bukkit.entity.Player;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.CommonUtil;

public abstract class ServerAdminCommand extends PlayerCommand
{
	protected Player player;

	@Override
	public final void preInitialize(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		super.preInitialize(commandContainer);
		player = CommonUtil.assignFromType(commandContainer.getSender(), Player.class);

	}
}
