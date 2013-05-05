package me.protocos.xteam.command.teamuser;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.BaseUserCommand;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.core.exception.TeamInvalidCommandException;
import me.protocos.xteam.core.exception.TeamPlayerDoesNotExistException;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MainHelp extends BaseUserCommand
{
	String commandID;

	public MainHelp()
	{
		super();
	}
	public MainHelp(Player sender, String command, String id)
	{
		super(sender, command);
		this.commandID = id;
	}
	@Override
	protected void act()
	{
		ChatColor temp;
		String message = (ChatColor.AQUA + "------------------ [xTeam v" + xTeam.VERSION + " Help] ------------------");
		message += "\n" + (ChatColor.GRAY + "xTeam is a team-based PvP plugin that allows for creating and joining teams, setting a headquarters and various other features. It is meant for use on hardcore PvP servers where land is not protected!");
		// line 6 begin
		message += "\n" + (ChatColor.AQUA + "Type '" + commandID + " help [Page Number]' to get started");
		message += "\n" + ((temp = ChatColor.GRAY) + commandID + " [command]" + ChatColor.RESET + " = command for " + temp + "TEAM PLAYERS");
		message += "\n" + ((temp = ChatColor.YELLOW) + commandID + " [command]" + ChatColor.RESET + " = command for " + temp + "TEAM ADMINS");
		message += "\n" + ((temp = ChatColor.LIGHT_PURPLE) + commandID + " [command]" + ChatColor.RESET + " = command for " + temp + "TEAM LEADERS");
		message += "\n" + (ChatColor.DARK_RED + "Report BUGS to " + ChatColor.GRAY + "http://dev.bukkit.org/server-mods/xteam/");
		originalSender.sendMessage(message);
	}
	@Override
	public void checkRequirements() throws TeamException
	{
		if (teamPlayer == null)
		{
			throw new TeamPlayerDoesNotExistException();
		}
		if (parseCommand.size() == 0 || parseCommand.size() == 1)
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
		return "(h" + patternOneOrMore("elp") + "|\\?+)?" + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getPermissionNode()
	{
		return null;
	}
	@Override
	public String getUsage()
	{
		return baseCommand + " {help}";
	}
}
