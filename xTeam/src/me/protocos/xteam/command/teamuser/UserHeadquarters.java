package me.protocos.xteam.command.teamuser;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.UserCommand;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.command.action.TeleportScheduler;
import me.protocos.xteam.core.exception.TeamException;
import org.bukkit.command.CommandSender;

public class UserHeadquarters extends UserCommand
{
	public UserHeadquarters()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		TeleportScheduler teleporter = TeleportScheduler.getInstance();
		teleporter.teleport(teamPlayer, teamPlayer.getTeam().getHeadquarters());
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		Requirements.checkPlayerHasTeam(teamPlayer);
		Requirements.checkTeamHasHeadquarters(team);
		Requirements.checkPlayerNotDamaged(teamPlayer);
		Requirements.checkPlayerCanTeleport(teamPlayer, getPermissionNode());
		Requirements.checkPlayerTeleportRequested(teamPlayer);
	}

	@Override
	public String getPattern()
	{
		return patternOneOrMore("head") + patternOneOrMore("quarters") + OPTIONAL_WHITE_SPACE;
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
