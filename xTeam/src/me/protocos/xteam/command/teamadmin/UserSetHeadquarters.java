package me.protocos.xteam.command.teamadmin;

import static me.protocos.xteam.util.StringUtil.*;
import java.io.InvalidClassException;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.UserCommand;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.TeamHeadquarters;
import me.protocos.xteam.core.exception.*;
import org.bukkit.command.CommandSender;

public class UserSetHeadquarters extends UserCommand
{
	public UserSetHeadquarters()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		team.setHQ(new TeamHeadquarters(teamPlayer.getLocation()));
		team.setTimeLastSet(System.currentTimeMillis());
		originalSender.sendMessage("You set the team headquarters");
	}
	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, InvalidClassException
	{
		super.checkRequirements(originalSender, parseCommand);
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
		return "/team sethq";
	}
}
