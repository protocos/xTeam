package me.protocos.xteam.command.teamleader;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.command.BaseUserCommand;
import me.protocos.xteam.core.exception.*;
import me.protocos.xteam.util.PermissionUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class UserOpen extends BaseUserCommand
{
	public UserOpen(Player sender, String command)
	{
		super(sender, command);
	}
	@Override
	protected void act()
	{
		team.setOpenJoining(!team.isOpenJoining());
		if (team.isOpenJoining())
			originalSender.sendMessage("UserOpen joining is now " + ChatColor.GREEN + "enabled");
		else
			originalSender.sendMessage("UserOpen joining is now " + ChatColor.RED + "disabled");
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
		if (!teamPlayer.isLeader())
		{
			throw new TeamPlayerNotLeaderException();
		}
	}
	@Override
	public String getPattern()
	{
		return patternOneOrMore("open") + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getPermissionNode()
	{
		return "xteam.leader.core.open";
	}
	@Override
	public String getUsage()
	{
		return baseCommand + " open";
	}
}
