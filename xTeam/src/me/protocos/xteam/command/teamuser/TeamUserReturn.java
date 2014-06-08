package me.protocos.xteam.command.teamuser;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.command.TeamUserCommand;
import me.protocos.xteam.core.TeleportScheduler;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.model.Locatable;
import me.protocos.xteam.util.PatternBuilder;
import org.bukkit.Location;

public class TeamUserReturn extends TeamUserCommand
{
	private TeleportScheduler teleportScheduler;

	public TeamUserReturn(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
		this.teleportScheduler = teamPlugin.getTeleportScheduler();
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		Location returnLocation = teamPlayer.getReturnLocation();
		teleportScheduler.teleport(teamPlayer, new Locatable(teamPlugin, "your return location", returnLocation));
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		Requirements.checkPlayerCanTeleport(teamPlayer);
		Requirements.checkPlayerHasReturnLocation(teamPlayer);
		Requirements.checkPlayerLastAttacked(teamPlayer);
		Requirements.checkPlayerTeleportRequested(teleportScheduler, teamPlayer);
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.oneOrMore("return")
				.whiteSpaceOptional()
				.toString();
	}

	@Override
	public String getPermissionNode()
	{
		return "xteam.core.user.return";
	}

	@Override
	public String getUsage()
	{
		return "/team return";
	}

	@Override
	public String getDescription()
	{
		return "teleport to saved return location (1 use)";
	}
}
