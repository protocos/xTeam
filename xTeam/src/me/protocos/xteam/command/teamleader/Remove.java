package me.protocos.xteam.command.teamleader;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.command.BaseUserCommand;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.exception.*;
import me.protocos.xteam.util.PermissionUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Remove extends BaseUserCommand
{
	private String otherPlayer;

	public Remove()
	{
		super();
	}
	public Remove(Player sender, String command)
	{
		super(sender, command);
	}
	@Override
	protected void act()
	{
		team.removePlayer(otherPlayer);
		//		Data.Teams.remove(otherPlayer);
		Player other = Data.BUKKIT.getPlayer(otherPlayer);
		if (other != null)
			other.sendMessage("You've been " + ChatColor.RED + "removed" + ChatColor.WHITE + " from " + team.getName());
		originalSender.sendMessage("You" + ChatColor.RED + " removed " + ChatColor.WHITE + otherPlayer + " from your team");
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
		if (teamPlayer.getName().equals(otherPlayer) && (team.getPlayers().size() > 1))
		{
			throw new TeamPlayerLeaderLeavingException();
		}
	}
	@Override
	public String getPattern()
	{
		return patternOneOrMore("re") + patternOneOrMore("move") + WHITE_SPACE + ANY_CHARS + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getPermissionNode()
	{
		return "xteam.leader.core.remove";
	}
	@Override
	public String getUsage()
	{
		return baseCommand + " remove [Player]";
	}
}
