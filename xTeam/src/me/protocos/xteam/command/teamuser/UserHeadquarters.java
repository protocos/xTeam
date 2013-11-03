package me.protocos.xteam.command.teamuser;

import static me.protocos.xteam.util.StringUtil.*;
import java.io.InvalidClassException;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.UserCommand;
import me.protocos.xteam.command.action.TeleportScheduler;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.exception.*;
import me.protocos.xteam.util.CommonUtil;
import org.bukkit.ChatColor;
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
		originalSender.sendMessage(ChatColor.GREEN + "WHOOSH!");
	}
	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, InvalidClassException
	{
		super.checkRequirements(originalSender, parseCommand);
		if (!teamPlayer.hasTeam())
		{
			throw new TeamPlayerHasNoTeamException();
		}
		if (!team.hasHeadquarters())
		{
			throw new TeamNoHeadquartersException();
		}
		if (teamPlayer.isDamaged())
		{
			throw new TeamPlayerDyingException();
		}
		//TODO why is teamPlayer.getLastTeleported() returning 0L?
		long timeSinceLastTeleport = CommonUtil.getElapsedTimeSince(teamPlayer.getLastTeleported());
		if (timeSinceLastTeleport < Data.TELE_REFRESH_DELAY)
		{
			String error = "Player cannot teleport within " + Data.TELE_REFRESH_DELAY + " seconds of last teleport\nYou must wait " + (Data.TELE_REFRESH_DELAY - timeSinceLastTeleport) + " more seconds";
			if (teamPlayer.hasReturnLocation() && teamPlayer.hasPermission(getPermissionNode()))
				error += "\nPlayer can still use /team return";
			throw new TeamPlayerTeleException(error);
		}
		long timeSinceLastAttacked = CommonUtil.getElapsedTimeSince(teamPlayer.getLastAttacked());
		if (timeSinceLastAttacked < Data.LAST_ATTACKED_DELAY)
		{
			throw new TeamPlayerTeleException("Player was attacked in the last " + Data.LAST_ATTACKED_DELAY + " seconds\nYou must wait " + (Data.LAST_ATTACKED_DELAY - timeSinceLastAttacked) + " more seconds");
		}
		if (TeleportScheduler.getInstance().hasCurrentTask(teamPlayer))
		{
			throw new TeamPlayerTeleRequestException();
		}
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
}
