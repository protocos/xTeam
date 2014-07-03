package me.protocos.xteam.command.teamuser;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.TeamUserCommand;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.message.Message;
import me.protocos.xteam.util.PatternBuilder;
import org.bukkit.ChatColor;

public class TeamUserMainHelp extends TeamUserCommand
{
	String commandID;

	public TeamUserMainHelp(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		ChatColor temp;
		String message = (ChatColor.AQUA + "------------------ [xTeam v" + teamPlugin.getVersion() + " Help] ------------------");
		message += "\n" + (ChatColor.GRAY + "xTeam is a team-based PvP plugin that allows for creating and joining teams, setting a headquarters and various other features. It is meant for use on hardcore PvP servers where land is not protected!");
		// line 6 begin
		message += "\n" + (ChatColor.AQUA + "Type '" + commandID + " [Page #]' to see commands");
		message += "\n" + ((temp = ChatColor.GRAY) + "[Gray Command]" + ChatColor.RESET + " = command for " + temp + "Team User");
		message += "\n" + ((temp = ChatColor.YELLOW) + "[Yellow Command]" + ChatColor.RESET + " = command for " + temp + "Team Admin");
		message += "\n" + ((temp = ChatColor.LIGHT_PURPLE) + "[Purple Command]" + ChatColor.RESET + " = command for " + temp + "Team Leader");
		message += "\n" + (ChatColor.DARK_RED + "Report BUGS to " + ChatColor.GRAY + "http://dev.bukkit.org/server-mods/xteam/");
		new Message.Builder(message).addRecipients(teamUser).disableFormatting().send(log);
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
		return "/team";
	}

	@Override
	public String getDescription()
	{
		return "main help menu for xTeam";
	}
}
