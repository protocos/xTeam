package me.protocos.xteam.command.teamuser;

import me.protocos.xteam.api.command.TeamUserCommand;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.command.action.TeleportScheduler;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.PatternBuilder;

public class TeamUserHeadquarters extends TeamUserCommand
{
	public TeamUserHeadquarters()
	{
		super();
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		TeleportScheduler teleporter = TeleportScheduler.getInstance();
		teleporter.teleport(teamPlayer, teamPlayer.getTeam().getHeadquarters());
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		Requirements.checkPlayerHasTeam(teamPlayer);
		Requirements.checkTeamHasHeadquarters(team);
		Requirements.checkPlayerNotDamaged(teamPlayer);
		Requirements.checkPlayerCanTeleport(teamPlayer);
		Requirements.checkPlayerTeleportRequested(teamPlayer);
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
		return "xteam.player.core.hq";
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
