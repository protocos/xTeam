package me.protocos.xteam.command.serveradmin;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.BaseServerAdminCommand;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.TeamHeadquarters;
import me.protocos.xteam.exception.*;
import me.protocos.xteam.util.PermissionUtil;
import org.bukkit.entity.Player;

public class AdminSetHeadquarters extends BaseServerAdminCommand
{
	private String teamName;

	public AdminSetHeadquarters()
	{
		super();
	}
	public AdminSetHeadquarters(Player sender, String command)
	{
		super(sender, command);
	}
	@Override
	protected void act()
	{
		Team team = xTeam.tm.getTeam(teamName);
		team.setHQ(new TeamHeadquarters(player.getLocation()));
		player.sendMessage("You set the team headquarters for team " + teamName);
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
	}
	@Override
	public String getPattern()
	{
		return "set" + patternOneOrMore("hq") + WHITE_SPACE + ANY_CHARS + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getPermissionNode()
	{
		return "xteam.serveradmin.core.sethq";
	}
	@Override
	public String getUsage()
	{
		return baseCommand + " sethq [Team]";
	}
}
