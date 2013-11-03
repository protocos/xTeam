package me.protocos.xteam.command.serveradmin;

import static me.protocos.xteam.util.StringUtil.*;
import java.io.InvalidClassException;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.exception.TeamException;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class AdminOpen extends ServerAdminCommand
{
	private String teamName;
	private Team changeTeam;

	public AdminOpen()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		changeTeam.setOpenJoining(!changeTeam.isOpenJoining());
		if (changeTeam.isOpenJoining())
			originalSender.sendMessage("Open joining is now " + ChatColor.GREEN + "enabled" + ChatColor.RESET + " for team " + teamName);
		else
			originalSender.sendMessage("Open joining is now " + ChatColor.RED + "disabled" + ChatColor.RESET + " for team " + teamName);
	}
	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, InvalidClassException
	{
		super.checkRequirements(originalSender, parseCommand);
		teamName = parseCommand.get(1);
		changeTeam = xTeam.getTeamManager().getTeam(teamName);
	}
	@Override
	public String getPattern()
	{
		return patternOneOrMore("open") + WHITE_SPACE + ANY_CHARS + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getPermissionNode()
	{
		return "xteam.serveradmin.core.open";
	}
	@Override
	public String getUsage()
	{
		return "/team open [Team]";
	}
}
