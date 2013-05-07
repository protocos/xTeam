package me.protocos.xteam.command.serveradmin;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.BaseServerAdminCommand;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.core.exception.*;
import me.protocos.xteam.util.PermissionUtil;
import org.bukkit.entity.Player;

public class AdminDisband extends BaseServerAdminCommand
{
	private String teamName;

	public AdminDisband()
	{
		super();
	}
	public AdminDisband(Player sender, String command)
	{
		super(sender, command);
	}
	@Override
	protected void act()
	{
		Team team = xTeam.tm.getTeam(teamName);
		for (String p : team.getPlayers())
		{
			TeamPlayer playerDelete = new TeamPlayer(p);
			if (playerDelete.isOnline())
				playerDelete.sendMessage("Your team has been disbanded by an admin");
		}
		xTeam.tm.removeTeam(teamName);
		originalSender.sendMessage("You disbanded " + teamName);
	}
	@Override
	public void checkRequirements() throws TeamException
	{
		if (!PermissionUtil.hasPermission(originalSender, getPermissionNode()))
		{
			throw new TeamPlayerPermissionException();
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
		if (team.isDefaultTeam())
		{
			throw new TeamIsDefaultException();
		}
	}
	@Override
	public String getPattern()
	{
		return "disband" + WHITE_SPACE + ANY_CHARS + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getPermissionNode()
	{
		return "xteam.serveradmin.core.disband";
	}
	@Override
	public String getUsage()
	{
		return baseCommand + " disband [Team]";
	}
}
