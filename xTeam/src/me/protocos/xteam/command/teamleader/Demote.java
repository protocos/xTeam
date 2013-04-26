package me.protocos.xteam.command.teamleader;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.command.BaseUserCommand;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.core.exception.*;
import me.protocos.xteam.util.PermissionUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Demote extends BaseUserCommand
{
	private String otherPlayer;

	public Demote()
	{
		super();
	}
	public Demote(Player sender, String command)
	{
		super(sender, command);
	}
	@Override
	protected void act()
	{
		team.demote(otherPlayer);
		Player other = Data.BUKKIT.getPlayer(otherPlayer);
		if (other != null)
			other.sendMessage("You've been " + ChatColor.RED + "demoted");
		originalSender.sendMessage("You" + ChatColor.RED + " demoted " + ChatColor.WHITE + otherPlayer);
	}
	@Override
	public void checkRequirements() throws TeamException
	{
		if (teamPlayer == null)
		{
			throw new TeamPlayerDoesNotExistException();
		}
		if (parseCommand.size() == 2)
		{
			otherPlayer = parseCommand.get(1);
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

		if (!team.getPlayers().contains(otherPlayer))
		{
			throw new TeamPlayerNotTeammateException();
		}
		TeamPlayer demotePlayer = new TeamPlayer(otherPlayer);
		if (demotePlayer.isLeader())
		{
			throw new TeamPlayerLeaderDemoteException();
		}
	}
	@Override
	public String getPattern()
	{
		return patternOneOrMore("demote") + WHITE_SPACE + ANY_CHARS + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getPermissionNode()
	{
		return "xteam.leader.core.demote";
	}
	@Override
	public String getUsage()
	{
		return baseCommand + " demote [Player]";
	}
}
