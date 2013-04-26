package me.protocos.xteam.command.teamuser;

import static me.protocos.xteam.util.StringUtil.*;
import java.util.ArrayList;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.BaseUserCommand;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.exception.TeamInvalidCommandException;
import me.protocos.xteam.exception.TeamPlayerDoesNotExistException;
import me.protocos.xteam.exception.TeamPlayerPermissionException;
import me.protocos.xteam.util.PermissionUtil;
import org.bukkit.entity.Player;

public class List extends BaseUserCommand
{
	public List()
	{
		super();
	}
	public List(Player sender, String command)
	{
		super(sender, command);

	}
	@Override
	protected void act()
	{
		ArrayList<String> teams = xTeam.tm.getAllTeamNames();
		String message = "Teams: " + teams.toString().replaceAll("[\\[\\]]", "");
		originalSender.sendMessage(message);
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
	}
	@Override
	public String getPattern()
	{
		return patternOneOrMore("list") + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getPermissionNode()
	{
		return "xteam.player.core.list";
	}
	@Override
	public String getUsage()
	{
		return baseCommand + " list";
	}
}