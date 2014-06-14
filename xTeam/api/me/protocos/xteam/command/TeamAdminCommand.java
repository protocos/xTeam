package me.protocos.xteam.command;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.entity.TeamPlayer;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.CommonUtil;
import org.bukkit.entity.Player;

public abstract class TeamAdminCommand extends TeamPlayerCommand
{
	protected TeamPlayer teamAdmin;

	public TeamAdminCommand(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
	}

	@Override
	public final void preInitialize(CommandContainer commandContainer) throws TeamException
	{
		super.preInitialize(commandContainer);
		Player player = CommonUtil.assignFromType(commandContainer.getSender(), Player.class);
		teamAdmin = playerFactory.getPlayer(player);
		Requirements.checkPlayerHasTeam(teamAdmin);
		Requirements.checkPlayerIsTeamAdmin(teamAdmin);
	}
}
