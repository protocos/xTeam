package me.protocos.xteam.command.teamuser;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.UserCommand;
import me.protocos.xteam.core.exception.TeamException;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class UserMainHelp extends UserCommand
{
	String commandID;

	public UserMainHelp()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		ChatColor temp;

		String message = (ChatColor.AQUA + "------------------ [xTeam v" + xTeam.getInstance().getVersion() + " Help] ------------------");
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
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		commandID = parseCommand.getBaseCommand();
	}

	@Override
	public String getPattern()
	{
		return "(h" + patternOneOrMore("elp") + "|\\?+)?" + OPTIONAL_WHITE_SPACE;
	}

	@Override
	public String getPermissionNode()
	{
		return "help";
	}

	@Override
	public String getUsage()
	{
		return "/team {help}";
	}

	@Override
	public String getDescription()
	{
		return "main help menu for xTeam";
	}
}
