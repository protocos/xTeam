package me.protocos.xteam.command.teamleader;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.BaseUserCommand;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.core.exception.*;
import me.protocos.xteam.util.PermissionUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class UserDisband extends BaseUserCommand
{
	public UserDisband(Player sender, String command)
	{
		super(sender, command);
	}
	@Override
	protected void act()
	{
		for (String p : teamPlayer.getOnlineTeammates())
		{
			TeamPlayer playerDisband = new TeamPlayer(p);
			playerDisband.sendMessage("Team has been " + ChatColor.RED + "disbanded" + ChatColor.RESET + " by the leader");
		}
		xTeam.tm.removeTeam(team.getName());
		originalSender.sendMessage("You " + ChatColor.RED + "disbanded" + ChatColor.RESET + " your team");
	}

	@Override
	protected void checkRequirements() throws TeamException
	{
		if (teamPlayer == null)
		{
			throw new TeamPlayerDoesNotExistException();
		}
		if (parseCommand.size() == 1)
		{

		}
		else
		{
			throw new TeamInvalidCommandException();
		}
		if (!PermissionUtil.hasPermission(originalSender, getPermissionNode()))
		{
			throw new TeamPlayerPermissionException();
		}
		if (!teamPlayer.hasTeam())
		{
			throw new TeamPlayerHasNoTeamException();
		}
		if (!teamPlayer.isLeader())
		{
			throw new TeamPlayerNotLeaderException();
		}
	}

	@Override
	public String getPattern()
	{
		return "disband" + OPTIONAL_WHITE_SPACE;
	}

	@Override
	public String getPermissionNode()
	{
		return "xteam.leader.core.disband";
	}

	@Override
	public String getUsage()
	{
		return baseCommand + " disband";
	}
}
