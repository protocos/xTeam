package me.protocos.xteam.command.serveradmin;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.BaseServerAdminCommand;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.exception.*;
import me.protocos.xteam.util.PermissionUtil;
import org.bukkit.entity.Player;

public class AdminHeadquarters extends BaseServerAdminCommand
{
	private String teamName;

	public AdminHeadquarters()
	{
		super();
	}
	public AdminHeadquarters(Player sender, String command)
	{
		super(sender, command);
	}
	@Override
	protected void act()
	{
		Team team = xTeam.tm.getTeam(teamName);
		player.teleport(team.getHeadquarters());
		player.sendMessage("You have been teleported to the headquarters of team " + teamName);
	}
	@Override
	public void checkRequirements() throws TeamException
	{
		if (!PermissionUtil.hasPermission(originalSender, getPermissionNode()))
		{
			throw new TeamPlayerPermissionException();
		}
		if (player == null)
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
		Team team = xTeam.tm.getTeam(teamName);
		if (team == null)
		{
			throw new TeamDoesNotExistException();
		}
		if (!team.hasHQ())
		{
			throw new TeamNoHeadquartersException();
		}
	}
	@Override
	public String getPattern()
	{
		return "hq" + WHITE_SPACE + ANY_CHARS + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getPermissionNode()
	{
		return "xteam.serveradmin.core.hq";
	}
	@Override
	public String getUsage()
	{
		return baseCommand + " hq [Team]";
	}
}
