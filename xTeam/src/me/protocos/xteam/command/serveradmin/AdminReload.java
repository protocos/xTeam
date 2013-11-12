package me.protocos.xteam.command.serveradmin;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.exception.TeamException;
import org.bukkit.command.CommandSender;

public class AdminReload extends ServerAdminCommand
{
	public AdminReload()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		Data.load(xTeam.getInstance().getConfigLoader());
		originalSender.sendMessage("Config reloaded");
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
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
		return "/team reload";
	}

	@Override
	public String getDescription()
	{
		return "reload configuration file";
	}
}
