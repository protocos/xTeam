package me.protocos.xteam.command;

import me.protocos.api.util.CommonUtil;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.entity.TeamPlayer;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.message.MessageUtil;
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

	@Override
	public String toString()
	{
		return MessageUtil.formatForLeader(this.getUsage() + " - " + this.getDescription());
	}

	@Override
	public final Classification getClassification()
	{
		return Classification.TEAM_LEADER;
	}
}
