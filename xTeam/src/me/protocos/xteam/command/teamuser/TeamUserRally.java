package me.protocos.xteam.command.teamuser;

import me.protocos.xteam.api.command.TeamUserCommand;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.command.action.TeleportScheduler;
import me.protocos.xteam.core.Locatable;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.PatternBuilder;
import org.bukkit.Location;

public class TeamUserRally extends TeamUserCommand
{
	public TeamUserRally()
	{
		super();
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		TeleportScheduler teleporter = TeleportScheduler.getInstance();
		Location rallyLocation = team.getRally();
		teleporter.teleport(teamPlayer, new Locatable("the rally point", rallyLocation));
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		Requirements.checkPlayerHasTeam(teamPlayer);
		Requirements.checkTeamHasRally(team);
		Requirements.checkPlayerCanRally(teamPlayer);
		Requirements.checkPlayerNotDamaged(teamPlayer);
		Requirements.checkPlayerLastAttacked(teamPlayer);
		Requirements.checkPlayerTeleportRequested(teamPlayer);
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.append("r")
				.oneOrMore("ally")
				.whiteSpaceOptional()
				.toString();
	}

	@Override
	public String getPermissionNode()
	{
		return "xteam.player.core.rally";
	}

	@Override
	public String getUsage()
	{
		return "/team rally";
	}

	@Override
	public String getDescription()
	{
		return "teleport to team rally location";
	}
}
