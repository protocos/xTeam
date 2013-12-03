package me.protocos.xteam.command.teamuser;

import me.protocos.xteam.api.command.TeamUserCommand;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.command.action.TeleportScheduler;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.model.Locatable;
import me.protocos.xteam.util.PatternBuilder;
import org.bukkit.Location;

public class TeamUserReturn extends TeamUserCommand
{
	public TeamUserReturn()
	{
		super();
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		TeleportScheduler teleporter = TeleportScheduler.getInstance();
		Location returnLocation = teamPlayer.getReturnLocation();
		teleporter.teleport(teamPlayer, new Locatable("your return location", returnLocation));
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		Requirements.checkPlayerHasTeam(teamPlayer);
		Requirements.checkPlayerHasReturnLocation(teamPlayer);
		Requirements.checkPlayerNotDamaged(teamPlayer);
		Requirements.checkPlayerLastAttacked(teamPlayer);
		Requirements.checkPlayerTeleportRequested(teamPlayer);
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
