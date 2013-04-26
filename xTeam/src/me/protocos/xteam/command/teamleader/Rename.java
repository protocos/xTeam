package me.protocos.xteam.command.teamleader;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.BaseUserCommand;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.ITeamPlayer;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.core.exception.*;
import me.protocos.xteam.util.PermissionUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Rename extends BaseUserCommand
{
	private String newName;

	public Rename()
	{
		super();
	}
	public Rename(Player sender, String command)
	{
		super(sender, command);
	}
	@Override
	protected void act()
	{
		xTeam.tm.removeTeam(team.getName());
		team.setName(newName);
		xTeam.tm.addTeam(team);
		for (String p : teamPlayer.getOnlineTeammates())
		{
			ITeamPlayer mate = new TeamPlayer(p);
			mate.sendMessage("The team has been renamed to " + ChatColor.AQUA + newName);
		}
		originalSender.sendMessage("You renamed the team to " + ChatColor.AQUA + newName);
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
			newName = parseCommand.get(1);
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
		if (Data.TEAM_TAG_LENGTH != 0 && newName.length() > Data.TEAM_TAG_LENGTH)
		{
			throw new TeamNameTooLongException();
		}
		if (Data.ALPHA_NUM && !newName.matches(ALPHA_NUMERIC))
		{
			throw new TeamNameNotAlphaException();
		}
		if (toLowerCase(xTeam.tm.getAllTeamNames()).contains(newName.toLowerCase()))
		{
			throw new TeamAlreadyExistsException();
		}
	}
	@Override
	public String getPattern()
	{
		return patternOneOrMore("re") + patternOneOrMore("name") + WHITE_SPACE + ANY_CHARS + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getPermissionNode()
	{
		return "xteam.leader.core.rename";
	}
	@Override
	public String getUsage()
	{
		return baseCommand + " rename [Name]";
	}
}
