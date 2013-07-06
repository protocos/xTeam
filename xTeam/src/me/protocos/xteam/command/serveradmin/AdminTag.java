package me.protocos.xteam.command.serveradmin;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.BaseServerAdminCommand;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.exception.*;
import me.protocos.xteam.util.StringUtil;
import org.bukkit.entity.Player;

public class AdminTag extends BaseServerAdminCommand
{
	private String teamName, newTag;

	public AdminTag(Player sender, String command)
	{
		super(sender, command);
	}
	@Override
	protected void act()
	{
		Team team = xTeam.tm.getTeam(teamName);
		team.setTag(newTag);
		if (!team.containsPlayer(player.getName()))
			player.sendMessage("The team tag has been set to " + newTag);
		team.sendMessage("The team tag has been set to " + newTag + " by an admin");
	}
	@Override
	public void checkRequirements() throws TeamException
	{
		if (!player.hasPermission(getPermissionNode()))
		{
			throw new TeamPlayerPermissionException();
		}
		if (parseCommand.size() == 3)
		{
			teamName = parseCommand.get(1);
			newTag = parseCommand.get(2);
		}
		else
		{
			throw new TeamInvalidCommandException();
		}
		Team desiredTeam = xTeam.tm.getTeam(teamName);
		if (desiredTeam == null)
		{
			throw new TeamDoesNotExistException();
		}
		if (!newTag.equalsIgnoreCase(desiredTeam.getName()) && StringUtil.toLowerCase(xTeam.tm.getAllTeamNames()).contains(newTag.toLowerCase()))
		{
			throw new TeamNameConflictsWithTagException();
		}
		if (Data.ALPHA_NUM && !newTag.matches(ALPHA_NUMERIC))
		{
			throw new TeamNameNotAlphaException();
		}
	}
	@Override
	public String getPattern()
	{
		return patternOneOrMore("tag") + WHITE_SPACE + ANY_CHARS + WHITE_SPACE + ANY_CHARS + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getPermissionNode()
	{
		return "xteam.serveradmin.core.tag";
	}
	@Override
	public String getUsage()
	{
		return baseCommand + " tag [Team] [UserTag]";
	}
}
