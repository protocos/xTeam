package me.protocos.xteam.command.teamuser;

import static me.protocos.xteam.util.StringUtil.*;
import java.util.List;
import me.protocos.xteam.command.BaseUserCommand;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.core.exception.*;
import me.protocos.xteam.util.PermissionUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Message extends BaseUserCommand
{
	private List<String> list;

	public Message()
	{
		super();
	}
	public Message(Player sender, String command)
	{
		super(sender, command);
	}
	@Override
	protected void act()
	{
		String message = "";
		list = parseCommand.subList(1, parseCommand.size());
		for (String s : list)
		{
			message = message + " " + s;
		}
		for (String p : teamPlayer.getOnlineTeammates())
		{
			TeamPlayer teammate = new TeamPlayer(p);
			teammate.sendMessage("[" + ChatColor.DARK_GREEN + teamPlayer.getName() + ChatColor.WHITE + "]" + message);
		}
		originalSender.sendMessage("[" + ChatColor.DARK_GREEN + teamPlayer.getName() + ChatColor.WHITE + "]" + message);
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
		return baseCommand + " message [Message]";
	}
}
