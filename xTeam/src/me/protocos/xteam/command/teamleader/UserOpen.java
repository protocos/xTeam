package me.protocos.xteam.command.teamleader;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.UserCommand;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.exception.TeamException;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class UserOpen extends UserCommand
{
	public UserOpen()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		team.setOpenJoining(!team.isOpenJoining());
		if (team.isOpenJoining())
			originalSender.sendMessage("Open joining is now " + ChatColor.GREEN + "enabled");
		else
			originalSender.sendMessage("Open joining is now " + ChatColor.RED + "disabled");
	}
	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		super.checkRequirements(originalSender, parseCommand);
		Requirements.checkPlayerHasTeam(teamPlayer);
		Requirements.checkPlayerIsTeamLeader(teamPlayer);
	}
	@Override
	public String getPattern()
	{
		return patternOneOrMore("open") + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getPermissionNode()
	{
		return "xteam.leader.core.open";
	}
	@Override
	public String getUsage()
	{
		return "/team open";
	}
}
