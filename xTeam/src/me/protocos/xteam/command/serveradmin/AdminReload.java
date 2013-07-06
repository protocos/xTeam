package me.protocos.xteam.command.serveradmin;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.core.exception.TeamInvalidCommandException;
import me.protocos.xteam.core.exception.TeamPlayerDoesNotExistException;
import me.protocos.xteam.core.exception.TeamPlayerPermissionException;
import me.protocos.xteam.util.PermissionUtil;
import org.bukkit.entity.Player;

public class AdminReload extends ServerAdminCommand
{
	public AdminReload(Player sender, String command)
	{
		super(sender, command);
	}
	@Override
	protected void act()
	{
		Data.load();
		originalSender.sendMessage("Config reloaded");
	}
	@Override
	public void checkRequirements() throws TeamException
	{
		if (!PermissionUtil.hasPermission(originalSender, getPermissionNode()))
		{
			throw new TeamPlayerPermissionException();
		}
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
		return patternOneOrMore("re") + patternOneOrMore("load") + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getPermissionNode()
	{
		return "xteam.serveradmin.core.reload";
	}
	@Override
	public String getUsage()
	{
		return baseCommand + " reload";
	}
}
