package me.protocos.xteam.command;

import me.protocos.api.util.CommonUtil;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.entity.TeamPlayer;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.message.MessageUtil;
import org.bukkit.entity.Player;

public abstract class TeamUserCommand extends TeamPlayerCommand
{
	protected TeamPlayer teamUser;

	public TeamUserCommand(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
	}

	@Override
	public final void preInitialize(CommandContainer commandContainer) throws TeamException
	{
		super.preInitialize(commandContainer);
		Player player = CommonUtil.assignFromType(commandContainer.getSender(), Player.class);
		teamUser = playerFactory.getPlayer(player);
	}

	@Override
	public String toString()
	{
		return MessageUtil.formatForUser(this.getUsage() + " - " + this.getDescription());
	}

	@Override
	public final Classification getClassification()
	{
		return Classification.TEAM_USER;
	}
}
