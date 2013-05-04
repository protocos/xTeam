package me.protocos.xteam.command.teamuser;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.command.BaseUserCommand;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.exception.*;
import me.protocos.xteam.util.PermissionUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Chat extends BaseUserCommand
{
	private String option;

	public Chat()
	{
		super();
	}
	public Chat(Player sender, String command)
	{
		super(sender, command);
	}
	@Override
	protected void act()
	{
		if (option.equalsIgnoreCase("ON"))
		{
			Data.chatStatus.add(teamPlayer.getName());
			player.sendMessage("You are now only chatting with " + ChatColor.GREEN + "your team");
		}
		if (option.equalsIgnoreCase("OFF"))
		{
			Data.chatStatus.remove(teamPlayer.getName());
			player.sendMessage("You are now chatting with " + ChatColor.RED + "everyone");
		}
	}
	@Override
	public void checkRequirements() throws TeamException
	{
		if (teamPlayer == null)
		{
			throw new TeamPlayerDoesNotExistException();
		}
		//FIX move this if statememt to the act method
		if (parseCommand.size() == 1)
		{
			if (Data.chatStatus.contains(teamPlayer.getName()))
				option = "OFF";
			else
				option = "ON";
		}
		else if (parseCommand.size() == 2 && (parseCommand.get(1).equalsIgnoreCase("ON") || parseCommand.get(1).equalsIgnoreCase("OFF")))
		{
			option = parseCommand.get(1);
		}
		else
		{
			throw new TeamInvalidCommandException();
		}
		if (!PermissionUtil.hasPermission(player, getPermissionNode()))
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
		return "c" + patternOneOrMore("hat") + "(" + WHITE_SPACE + ANY_CHARS + ")?" + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getPermissionNode()
	{
		return "xteam.player.core.chat";
	}
	@Override
	public String getUsage()
	{
		return baseCommand + " chat {On/Off}";
	}
}
