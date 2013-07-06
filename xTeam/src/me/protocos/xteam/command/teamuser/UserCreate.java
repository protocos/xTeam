package me.protocos.xteam.command.teamuser;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.BaseUserCommand;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.exception.*;
import me.protocos.xteam.util.PermissionUtil;
import me.protocos.xteam.util.StringUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class UserCreate extends BaseUserCommand
{
	private String desiredTeam;

	public UserCreate(Player sender, String command)
	{
		super(sender, command);
	}
	@Override
	protected void act()
	{
		String leader = teamPlayer.getName();
		xTeam.tm.createTeamWithLeader(desiredTeam, leader);
		Data.lastCreated.put(leader, Long.valueOf(System.currentTimeMillis()));
		player.sendMessage("You created " + ChatColor.AQUA + desiredTeam);
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
			desiredTeam = parseCommand.get(1);
		}
		else
		{
			throw new TeamInvalidCommandException();
		}
		if (!PermissionUtil.hasPermission(player, getPermissionNode()))
		{
			throw new TeamPlayerPermissionException();
		}
		if (teamPlayer.hasTeam())
		{
			throw new TeamPlayerHasTeamException();
		}
		if (Data.DEFAULT_TEAM_ONLY && !StringUtil.toLowerCase(Data.DEFAULT_TEAM_NAMES).contains(desiredTeam.toLowerCase()) && Data.DEFAULT_TEAM_NAMES.size() > 0)
		{
			throw new TeamOnlyJoinDefaultException();
		}
		if (Data.TEAM_TAG_LENGTH != 0 && desiredTeam.length() > Data.TEAM_TAG_LENGTH)
		{
			throw new TeamNameTooLongException();
		}
		if (System.currentTimeMillis() - (Data.lastCreated.get(teamPlayer.getName()) == null ? 0L : Data.lastCreated.get(teamPlayer.getName()).longValue()) < Data.CREATE_INTERVAL * 60 * 1000)
		{
			throw new TeamCreatedRecentlyException();
		}
		if (Data.ALPHA_NUM && !desiredTeam.matches("[a-zA-Z0-9_]+"))
		{
			throw new TeamNameNotAlphaException();
		}
		if (toLowerCase(xTeam.tm.getAllTeamNames()).contains(desiredTeam.toLowerCase()))
		{
			throw new TeamAlreadyExistsException();
		}
	}
	@Override
	public String getPattern()
	{
		return "c" + patternOneOrMore("reate") + WHITE_SPACE + ANY_CHARS + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getPermissionNode()
	{
		return "xteam.player.core.create";
	}
	@Override
	public String getUsage()
	{
		return baseCommand + " create [Name]";
	}
}
