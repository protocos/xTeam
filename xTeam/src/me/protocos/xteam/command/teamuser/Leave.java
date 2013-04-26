package me.protocos.xteam.command.teamuser;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.BaseUserCommand;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.core.exception.*;
import me.protocos.xteam.util.PermissionUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Leave extends BaseUserCommand
{
	public Leave()
	{
		super();
	}
	public Leave(Player sender, String command)
	{
		super(sender, command);
	}
	@Override
	protected void act()
	{
		team.removePlayer(teamPlayer.getName());
		if (team.size() == 0 && !team.isDefaultTeam())
			xTeam.tm.removeTeam(team.getName());
		Data.chatStatus.remove(teamPlayer.getName());
		for (String teammate : team.getPlayers())
		{
			TeamPlayer mate = new TeamPlayer(teammate);
			if (mate.isOnline())
				mate.sendMessage(teamPlayer.getName() + ChatColor.RED + " left your team");
		}
		originalSender.sendMessage("You left " + ChatColor.AQUA + team.getName());
	}
	@Override
	public void checkRequirements() throws TeamException
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
		if ((team.getLeader().equals(teamPlayer.getName())) && (team.getPlayers().size() > 1))
		{
			throw new TeamPlayerLeaderLeavingException();
		}
	}
	@Override
	public String getPattern()
	{
		return "l" + "(" + patternOneOrMore("eave") + "|" + patternOneOrMore("ve") + ")" + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getPermissionNode()
	{
		return "xteam.player.core.leave";
	}
	@Override
	public String getUsage()
	{
		return baseCommand + " leave";
	}
}
