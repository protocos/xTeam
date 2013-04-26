package me.protocos.xteam.command.teamadmin;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.command.BaseUserCommand;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.core.exception.*;
import me.protocos.xteam.util.PermissionUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Promote extends BaseUserCommand
{
	private String otherPlayer;

	public Promote()
	{
		super();
	}
	public Promote(Player sender, String command)
	{
		super(sender, command);
	}
	@Override
	protected void act()
	{
		team.promote(otherPlayer);
		TeamPlayer other = new TeamPlayer(otherPlayer);
		if (other.isOnline())
			other.sendMessage("You've been " + ChatColor.GREEN + "promoted");
		originalSender.sendMessage("You" + ChatColor.GREEN + " promoted " + ChatColor.WHITE + otherPlayer);
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
		if (!teamPlayer.isAdmin())
		{
			throw new TeamPlayerNotAdminException();
		}
		if (!team.getPlayers().contains(otherPlayer))
		{
			throw new TeamPlayerNotTeammateException();
		}
	}
	@Override
	public String getPattern()
	{
		return patternOneOrMore("promote") + WHITE_SPACE + ANY_CHARS + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getPermissionNode()
	{
		return "xteam.admin.core.promote";
	}
	@Override
	public String getUsage()
	{
		return baseCommand + " promote [Player]";
	}
}
