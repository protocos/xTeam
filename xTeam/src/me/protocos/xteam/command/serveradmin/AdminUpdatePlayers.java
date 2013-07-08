package me.protocos.xteam.command.serveradmin;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.core.Functions;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.core.exception.TeamInvalidCommandException;
import me.protocos.xteam.core.exception.TeamPlayerDoesNotExistException;
import org.bukkit.entity.Player;

public class AdminUpdatePlayers extends ServerAdminCommand
{
	public AdminUpdatePlayers(Player sender, CommandParser command)
	{
		super(sender, command);
	}
	public AdminUpdatePlayers()
	{
	}
	@Override
	protected void act()
	{
		Functions.updatePlayers();
		originalSender.sendMessage("Players updated");
	}
	@Override
	public void checkRequirements() throws TeamException
	{
		if (sender == null)
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
	}
	@Override
	public String getPattern()
	{
		return patternOneOrMore("update") + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getPermissionNode()
	{
		return "xteam.serveradmin.core.update";
	}
	@Override
	public String getUsage()
	{
		return parseCommand.getBaseCommand() + " update";
	}
}
