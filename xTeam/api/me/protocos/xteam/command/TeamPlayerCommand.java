package me.protocos.xteam.command;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.exception.TeamException;

public abstract class TeamPlayerCommand extends PlayerCommand implements IPermissible
{
	protected ITeam team;

	public TeamPlayerCommand(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
	}

	@Override
	public void preInitialize(CommandContainer commandContainer) throws TeamException
	{
		super.preInitialize(commandContainer);
		team = teamCoordinator.getTeamByPlayer(commandContainer.getSender().getName());
	}
}