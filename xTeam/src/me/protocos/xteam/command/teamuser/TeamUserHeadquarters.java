package me.protocos.xteam.command.teamuser;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.command.TeamUserCommand;
import me.protocos.xteam.core.TeleportScheduler;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.PatternBuilder;

public class TeamUserHeadquarters extends TeamUserCommand
{
	private TeleportScheduler teleportScheduler;

	public TeamUserHeadquarters(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
		this.teleportScheduler = teamPlugin.getTeleportScheduler();
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		teleportScheduler.teleport(teamPlayer, teamPlayer.getTeam().getHeadquarters());
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		Requirements.checkPlayerCanTeleport(teamPlayer);
		Requirements.checkTeamHasHeadquarters(team);
		Requirements.checkPlayerTeleportRequested(teleportScheduler, teamPlayer);
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.oneOrMore("head")
				.oneOrMore("quarters")
				.whiteSpaceOptional()
				.toString();
	}

	@Override
	public String getPermissionNode()
	{
		return "xteam.core.user.hq";
	}

	@Override
	public String getUsage()
	{
		return "/team hq";
	}

	@Override
	public String getDescription()
	{
		return "teleport to the team headquarters";
	}
}
