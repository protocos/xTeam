package me.protocos.xteam.command.serveradmin;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.core.exception.TeamInvalidCommandException;
import me.protocos.xteam.core.exception.TeamPlayerDoesNotExistException;
import me.protocos.xteam.core.exception.TeamPlayerPermissionException;
import me.protocos.xteam.util.PermissionUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class AdminOpen extends ServerAdminCommand
{
	String teamName;

	public AdminOpen(Player sender, String command)
	{
		super(sender, command);
	}
	@Override
	protected void act()
	{
		Team team = xTeam.tm.getTeam(teamName);
		team.setOpenJoining(!team.isOpenJoining());
		if (team.isOpenJoining())
			originalSender.sendMessage("UserOpen joining is now " + ChatColor.GREEN + "enabled" + ChatColor.RESET + " for team " + teamName);
		else
			originalSender.sendMessage("UserOpen joining is now " + ChatColor.RED + "disabled" + ChatColor.RESET + " for team " + teamName);
	}
	@Override
	public void checkRequirements() throws TeamException
	{
		if (sender == null)
		{
			throw new TeamPlayerDoesNotExistException();
		}
		if (parseCommand.size() == 2)
		{
			teamName = parseCommand.get(1);
		}
		else
		{
			throw new TeamInvalidCommandException();
		}
		if (!PermissionUtil.hasPermission(originalSender, getPermissionNode()))
		{
			throw new TeamPlayerPermissionException();
		}
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
		return baseCommand + " open [Team]";
	}
}
