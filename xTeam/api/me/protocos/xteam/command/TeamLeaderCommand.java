package me.protocos.xteam.command;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.entity.TeamPlayer;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.CommonUtil;
import org.bukkit.entity.Player;

public abstract class TeamLeaderCommand extends TeamPlayerCommand
{
	protected TeamPlayer teamLeader;

	public TeamLeaderCommand(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
	}

	@Override
	public final void preInitialize(CommandContainer commandContainer) throws TeamException
	{
		super.preInitialize(commandContainer);
		Player player = CommonUtil.assignFromType(commandContainer.getSender(), Player.class);
		teamLeader = playerFactory.getPlayer(player);
		Requirements.checkPlayerHasTeam(teamLeader);
		Requirements.checkPlayerIsTeamLeader(teamLeader);
	}
}
