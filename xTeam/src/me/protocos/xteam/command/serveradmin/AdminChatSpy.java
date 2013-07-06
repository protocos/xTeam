package me.protocos.xteam.command.serveradmin;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.core.exception.TeamInvalidCommandException;
import me.protocos.xteam.core.exception.TeamPlayerDoesNotExistException;
import me.protocos.xteam.core.exception.TeamPlayerPermissionException;
import me.protocos.xteam.util.PermissionUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class AdminChatSpy extends ServerAdminCommand
{
	public AdminChatSpy(Player sender, String command)
	{
		super(sender, command);
	}
	@Override
	protected void act()
	{
		if (!Data.spies.contains(sender.getName()))
		{
			Data.spies.add(sender.getName());
			sender.sendMessage("You are " + ChatColor.RED + "now" + ChatColor.RESET + " spying on team chat");
		}
		else
		{
			Data.spies.remove(sender.getName());
			sender.sendMessage("You are " + ChatColor.GREEN + "no longer" + ChatColor.RESET + " spying on team chat");
		}
	}
	@Override
	public void checkRequirements() throws TeamException
	{
		if (!PermissionUtil.hasPermission(originalSender, getPermissionNode()))
		{
			throw new TeamPlayerPermissionException();
		}
		if (sender == null)
		{
			throw new TeamPlayerDoesNotExistException();
		}
		if (parseCommand.size() == 1)
		{

		}
		else
		{
			throw new TeamInvalidCommandException();
		}
	}
	@Override
	public String getPattern()
	{
		return patternOneOrMore("chat") + patternOneOrMore("spy") + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getPermissionNode()
	{
		return "xteam.serveradmin.core.chatspy";
	}
	@Override
	public String getUsage()
	{
		return baseCommand + " chatspy";
	}
}
