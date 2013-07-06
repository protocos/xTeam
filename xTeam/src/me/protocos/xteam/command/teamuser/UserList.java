package me.protocos.xteam.command.teamuser;

import static me.protocos.xteam.util.StringUtil.*;
import java.util.ArrayList;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.UserCommand;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.core.exception.TeamInvalidCommandException;
import me.protocos.xteam.core.exception.TeamPlayerDoesNotExistException;
import me.protocos.xteam.core.exception.TeamPlayerPermissionException;
import me.protocos.xteam.util.PermissionUtil;
import org.bukkit.entity.Player;

public class UserList extends UserCommand
{
	public UserList(Player sender, String command)
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
