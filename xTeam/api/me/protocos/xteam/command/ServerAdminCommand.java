package me.protocos.xteam.command;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.CommonUtil;
import org.bukkit.entity.Player;

public abstract class ServerAdminCommand extends PlayerCommand
{
	protected Player serverAdmin;

	public ServerAdminCommand(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
	}

	@Override
	public final void preInitialize(CommandContainer commandContainer) throws TeamException
	{
		super.preInitialize(commandContainer);
		serverAdmin = CommonUtil.assignFromType(commandContainer.getSender(), Player.class);

	}
}
