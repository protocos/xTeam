package me.protocos.xteam.command.teamuser;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.command.TeamUserCommand;
import me.protocos.xteam.core.TeleportScheduler;
import me.protocos.xteam.entity.ITeamPlayer;
import me.protocos.xteam.entity.TeamPlayer;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.PatternBuilder;

public class TeamUserTeleport extends TeamUserCommand
{
	private TeleportScheduler teleportScheduler;
	private TeamPlayer teamMate;

	public TeamUserTeleport(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
		this.teleportScheduler = teamPlugin.getTeleportScheduler();
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		teleportScheduler.teleport(teamPlayer, teamMate);
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		String teammateName = null;
		if (commandContainer.size() == 2)
		{
			teammateName = commandContainer.getArgument(1);
		}
		Requirements.checkPlayerCanTeleport(teamPlayer);
		Requirements.checkPlayerTeleportSelf(teamPlayer, teammateName);
		if (teammateName == null)
		{
			Requirements.checkPlayerHasTeammatesOnline(teamPlayer);
			teamMate = teleportScheduler.getClosestTeammate(teamPlayer);
		}
		else
		{
			ITeamPlayer other = playerFactory.getPlayer(teammateName);
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
		Requirements.checkPlayerTeleportRequested(teleportScheduler, teamPlayer);
		Requirements.checkPlayerTeammateIsOnline(teamMate);
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
		return "xteam.core.user.tele";
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
