package me.protocos.xteam.command.serveradmin;

import me.protocos.xteam.api.command.ServerAdminCommand;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.action.SetTeamAction;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.PatternBuilder;

public class ServerAdminSet extends ServerAdminCommand
{
	private String playerName, teamName;
	private SetTeamAction set;

	public ServerAdminSet()
	{
		super();
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		set.actOn(playerName, teamName);
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		playerName = commandContainer.getArgument(1);
		teamName = commandContainer.getArgument(2);
		set = new SetTeamAction(player);
		set.checkRequirementsOn(playerName, teamName);
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.oneOrMore("set")
				.whiteSpace()
				.anyString()
				.whiteSpace()
				.anyString()
				.whiteSpaceOptional()
				.toString();
	}

	@Override
	public String getPermissionNode()
	{
		return "xteam.core.serveradmin.set";
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
