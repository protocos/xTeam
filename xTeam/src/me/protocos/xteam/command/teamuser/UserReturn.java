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
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

public class UserReturn extends UserCommand
{
	public UserReturn()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		TeleportScheduler teleporter = TeleportScheduler.getInstance();
		teleporter.teleport(teamPlayer, teamPlayer.getReturnLocation());
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
		Location loc = teamPlayer.getReturnLocation();
		if (loc == null)
		{
			throw new TeamPlayerHasNoReturnException();
		}
		if (teamPlayer.isDamaged())
		{
			throw new TeamPlayerDyingException();
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
		return patternOneOrMore("return") + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getPermissionNode()
	{
		return "xteam.player.core.return";
	}
	@Override
	public String getUsage()
	{
		return "/team return";
	}
}
