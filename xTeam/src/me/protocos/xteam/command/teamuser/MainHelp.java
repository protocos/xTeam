package me.protocos.xteam.command.teamuser;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.BaseUserCommand;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.exception.TeamInvalidCommandException;
import me.protocos.xteam.exception.TeamPlayerDoesNotExistException;
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
		String message = (ChatColor.GREEN + "xTeam v" + xTeam.VERSION + " Info/Help Page");
		message += "\n" + (ChatColor.AQUA + "xTeam is a team-based PvP plugin that allows for creating and joining teams, setting a headquarters and various other features. It is meant for use on hardcore PvP servers where land is not protected!");
		// line 6 begin
		message += "\n" + (ChatColor.GREEN + "Type '" + commandID + " help [Page Number]' to get started");
		message += "\n" + (ChatColor.GRAY + commandID + " COMMAND" + ChatColor.WHITE + " = ICommand for EVERYONE");
		message += "\n" + (ChatColor.YELLOW + commandID + " COMMAND" + ChatColor.WHITE + " = ICommand for team ADMINS");
		message += "\n" + (ChatColor.LIGHT_PURPLE + commandID + " COMMAND" + ChatColor.WHITE + " = ICommand for team LEADERS");
		message += "\n" + (ChatColor.RED + "Report BUGS to " + ChatColor.WHITE + "http://dev.bukkit.org/server-mods/xteam/");
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
