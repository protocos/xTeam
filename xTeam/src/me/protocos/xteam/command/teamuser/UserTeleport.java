package me.protocos.xteam.command.teamuser;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.core.ITeamPlayer;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.UserCommand;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.command.action.TeleportScheduler;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.core.exception.TeamException;
import org.bukkit.command.CommandSender;

public class UserTeleport extends UserCommand
{
	private TeamPlayer teamMate;

	public UserTeleport()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		TeleportScheduler teleporter = TeleportScheduler.getInstance();
		teleporter.teleport(teamPlayer, teamMate);
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		String teammateName = null;
		if (parseCommand.size() == 2)
		{
			teammateName = parseCommand.get(1);
		}
		Requirements.checkPlayerHasTeam(teamPlayer);
		Requirements.checkPlayerNotDamaged(teamPlayer);
		Requirements.checkPlayerCanTeleport(teamPlayer, getPermissionNode());
		Requirements.checkPlayerTeleportSelf(teamPlayer, teammateName);
		if (teammateName == null)
		{
			Requirements.checkPlayerHasTeammatesOnline(teamPlayer);
			teamMate = TeleportScheduler.getInstance().getClosestTeammate(teamPlayer);
		}
		else
		{
			ITeamPlayer other = xTeam.getInstance().getPlayerManager().getPlayer(teammateName);
			Requirements.checkPlayerIsTeammate(teamPlayer, other);
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
		return patternOneOrMore("teleport") + "(" + WHITE_SPACE + ANY_CHARS + ")?" + OPTIONAL_WHITE_SPACE;
	}

	@Override
	public String getPermissionNode()
	{
		return "xteam.player.core.tele";
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
