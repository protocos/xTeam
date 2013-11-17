package me.protocos.xteam.command.serveradmin;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.api.command.ServerAdminCommand;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.action.SetTeamAction;
import me.protocos.xteam.core.exception.TeamException;
import org.bukkit.command.CommandSender;

public class ServerAdminSet extends ServerAdminCommand
{
	private String playerName, teamName;
	private SetTeamAction set;

	public ServerAdminSet()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		set.actOn(playerName, teamName);
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		playerName = parseCommand.get(1);
		teamName = parseCommand.get(2);
		set = new SetTeamAction(originalSender);
		set.checkRequirementsOn(playerName, teamName);
	}

	@Override
	public String getPattern()
	{
		return patternOneOrMore("set") + WHITE_SPACE + ANY_CHARS + WHITE_SPACE + ANY_CHARS + OPTIONAL_WHITE_SPACE;
	}

	@Override
	public String getPermissionNode()
	{
		return "xteam.serveradmin.core.set";
	}

	@Override
	public String getUsage()
	{
		return "/team set [Player] [Team]";
	}

	@Override
	public String getDescription()
	{
		return "set team of player";
	}
}
