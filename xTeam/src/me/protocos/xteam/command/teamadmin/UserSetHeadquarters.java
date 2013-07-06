package me.protocos.xteam.command.teamadmin;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.command.BaseUserCommand;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.TeamHeadquarters;
import me.protocos.xteam.core.exception.*;
import me.protocos.xteam.util.PermissionUtil;
import org.bukkit.entity.Player;

public class UserSetHeadquarters extends BaseUserCommand
{
	public UserSetHeadquarters(Player sender, String command)
	{
		super(sender, command);
	}
	@Override
	protected void act()
	{
		team.setHQ(new TeamHeadquarters(teamPlayer.getLocation()));
		team.setTimeLastSet(System.currentTimeMillis());
		originalSender.sendMessage("You set the team headquarters");
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
		if (!teamPlayer.isAdmin())
		{
			throw new TeamPlayerNotAdminException();
		}
		if (teamPlayer.getOnlinePlayer().getNoDamageTicks() > 0)
		{
			throw new TeamPlayerDyingException();
		}
		if (System.currentTimeMillis() - team.getTimeLastSet() < Data.HQ_INTERVAL * 60 * 60 * 1000)
		{
			throw new TeamHqSetRecentlyException();
		}
	}
	@Override
	public String getPattern()
	{
		return patternOneOrMore("sethq") + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getPermissionNode()
	{
		return "xteam.admin.core.sethq";
	}
	@Override
	public String getUsage()
	{
		return baseCommand + " sethq";
	}
}
