package me.protocos.xteam.command.teamleader;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.command.BaseUserCommand;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.core.exception.*;
import me.protocos.xteam.util.PermissionUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class SetLeader extends BaseUserCommand
{
	private String otherPlayer;

	public SetLeader()
	{
		super();
	}
	public SetLeader(Player sender, String command)
	{
		super(sender, command);
	}
	@Override
	protected void act()
	{
		team.setLeader(otherPlayer);
		TeamPlayer other = new TeamPlayer(otherPlayer);
		if (other.isOnline())
			other.sendMessage(ChatColor.GREEN + "You" + ChatColor.RESET + " are now the team leader");
		teamPlayer.sendMessage(ChatColor.GREEN + otherPlayer + ChatColor.RESET + " is now the team leader (you are an admin)" +
				"\nYou can now leave the team");
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
	}
	@Override
	public String getPattern()
	{
		return "set" + patternOneOrMore("leader") + WHITE_SPACE + ANY_CHARS + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getPermissionNode()
	{
		return "xteam.leader.core.setleader";
	}
	@Override
	public String getUsage()
	{
		return baseCommand + " setleader [Player]";
	}
}
