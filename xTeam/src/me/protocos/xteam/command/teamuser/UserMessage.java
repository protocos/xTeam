package me.protocos.xteam.command.teamuser;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.UserCommand;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.core.exception.*;
import me.protocos.xteam.util.PermissionUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class UserMessage extends UserCommand
{
	public UserMessage(Player sender, CommandParser command)
	{
		super(sender, command);
	}
	public UserMessage()
	{
	}
	@Override
	protected void act()
	{
		String message = "";
		for (int i = 1; i < parseCommand.size(); i++)
		{
			message += " " + parseCommand.get(i);
		}
		for (String p : teamPlayer.getOnlineTeammates())
		{
			TeamPlayer teammate = new TeamPlayer(p);
			teammate.sendMessage("[" + ChatColor.DARK_GREEN + teamPlayer.getName() + ChatColor.RESET + "]" + message);
		}
		originalSender.sendMessage("[" + ChatColor.DARK_GREEN + teamPlayer.getName() + ChatColor.RESET + "]" + message);
	}
	@Override
	public void checkRequirements() throws TeamException
	{
		if (teamPlayer == null)
		{
			throw new TeamPlayerDoesNotExistException();
		}
		if (parseCommand.size() > 1)
		{
		}
		else
		{
			throw new TeamInvalidCommandException();
		}
		if (!PermissionUtil.hasPermission(originalSender, getPermissionNode()))
		{
			throw new TeamPlayerPermissionException();
		}
		if (!teamPlayer.hasTeam())
		{
			throw new TeamPlayerHasNoTeamException();
		}
	}
	@Override
	public String getPattern()
	{
		return "(" + patternOneOrMore("message") + "|" + "tell" + ")" + WHITE_SPACE + "[" + WHITE_SPACE + ANY_CHARS + "]+";
	}
	@Override
	public String getPermissionNode()
	{
		return "xteam.player.core.chat";
	}
	@Override
	public String getUsage()
	{
		return parseCommand.getBaseCommand() + " message [UserMessage]";
	}
}
