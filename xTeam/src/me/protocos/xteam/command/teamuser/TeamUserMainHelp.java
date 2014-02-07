package me.protocos.xteam.command.teamuser;

import me.protocos.xteam.XTeam;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.TeamUserCommand;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.PatternBuilder;
import org.bukkit.ChatColor;

public class TeamUserMainHelp extends TeamUserCommand
{
	String commandID;

	public TeamUserMainHelp()
	{
		super();
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		ChatColor temp;
		String message = (ChatColor.AQUA + "------------------ [xTeam v" + XTeam.getInstance().getVersion() + " Help] ------------------");
		message += "\n" + (ChatColor.GRAY + "xTeam is a team-based PvP plugin that allows for creating and joining teams, setting a headquarters and various other features. It is meant for use on hardcore PvP servers where land is not protected!");
		// line 6 begin
		message += "\n" + (ChatColor.AQUA + "Type '" + commandID + " help [Page Number]' to get started");
		message += "\n" + ((temp = ChatColor.GRAY) + commandID + " [command]" + ChatColor.RESET + " = command for " + temp + "TEAM PLAYERS");
		message += "\n" + ((temp = ChatColor.YELLOW) + commandID + " [command]" + ChatColor.RESET + " = command for " + temp + "TEAM ADMINS");
		message += "\n" + ((temp = ChatColor.LIGHT_PURPLE) + commandID + " [command]" + ChatColor.RESET + " = command for " + temp + "TEAM LEADERS");
		message += "\n" + (ChatColor.DARK_RED + "Report BUGS to " + ChatColor.GRAY + "http://dev.bukkit.org/server-mods/xteam/");
		teamPlayer.sendMessage(message);
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		commandID = commandContainer.getCommandID();
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.optional(new PatternBuilder()
						.or(new PatternBuilder()
								.oneOrMore("help"), new PatternBuilder()
								.append("\\?+")))
				.whiteSpaceOptional()
				.toString();
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
