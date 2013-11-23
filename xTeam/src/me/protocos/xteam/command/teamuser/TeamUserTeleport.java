package me.protocos.xteam.command.teamuser;

import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.TeamUserCommand;
import me.protocos.xteam.api.core.ITeamPlayer;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.command.action.TeleportScheduler;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.PatternBuilder;

public class TeamUserTeleport extends TeamUserCommand
{
	private TeamPlayer teamMate;

	public TeamUserTeleport()
	{
		super();
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		TeleportScheduler teleporter = TeleportScheduler.getInstance();
		teleporter.teleport(teamPlayer, teamMate);
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		String teammateName = null;
		if (commandContainer.size() == 2)
		{
			teammateName = commandContainer.getArgument(1);
		}
		Requirements.checkPlayerHasTeam(teamPlayer);
		Requirements.checkPlayerNotDamaged(teamPlayer);
		Requirements.checkPlayerCanTeleport(teamPlayer);
		Requirements.checkPlayerTeleportSelf(teamPlayer, teammateName);
		if (teammateName == null)
		{
			Requirements.checkPlayerHasTeammatesOnline(teamPlayer);
			teamMate = TeleportScheduler.getInstance().getClosestTeammate(teamPlayer);
		}
		else
		{
			ITeamPlayer other = xTeam.getInstance().getPlayerManager().getPlayer(teammateName);
			Requirements.checkPlayerHasPlayedBefore(other);
			Requirements.checkPlayerIsTeammate(teamPlayer, other);
			Requirements.checkPlayerIsOnline(other);
			for (TeamPlayer teammate : teamPlayer.getOnlineTeammates())
			{
				if (teammateName.equalsIgnoreCase(teammate.getName()))
				{
					teamMate = teammate;
					break;
				}
			}
		}
		Requirements.checkPlayerTeleportRequested(teamPlayer);
		Requirements.checkPlayerTeammateWorld(teamPlayer, teamMate);
		Requirements.checkPlayerTeammateNear(teamPlayer, teamMate);
		Requirements.checkPlayerTeammateIsOnline(teamMate);
		Requirements.checkPlayerTeammateTooFar(teamPlayer, teamMate);
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.oneOrMore("teleport")
				.optional(new PatternBuilder()
						.whiteSpace()
						.anyString())
				.whiteSpaceOptional()
				.toString();
	}

	@Override
	public String getPermissionNode()
	{
		return "xteam.core.player.tele";
	}

	@Override
	public String getUsage()
	{
		return "/team tele {Player}";
	}

	@Override
	public String getDescription()
	{
		return "teleport to nearest or specific teammate";
	}
}
