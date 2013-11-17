package me.protocos.xteam.command.teamuser;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.api.command.TeamUserCommand;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.command.action.TeleportScheduler;
import me.protocos.xteam.core.Locatable;
import me.protocos.xteam.core.exception.TeamException;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

public class TeamUserRally extends TeamUserCommand
{
	public TeamUserRally()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		TeleportScheduler teleporter = TeleportScheduler.getInstance();
		Location rallyLocation = team.getRally();
		teleporter.teleport(teamPlayer, new Locatable("the rally point", rallyLocation));
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
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
		return "r" + patternOneOrMore("ally") + OPTIONAL_WHITE_SPACE;
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
