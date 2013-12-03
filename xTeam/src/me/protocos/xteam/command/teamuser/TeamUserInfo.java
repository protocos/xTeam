package me.protocos.xteam.command.teamuser;

import me.protocos.xteam.api.command.TeamUserCommand;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.action.InfoAction;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.PatternBuilder;

public class TeamUserInfo extends TeamUserCommand
{
	private String other;
	private InfoAction info;

	public TeamUserInfo()
	{
		super();
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		info.actOn(teamPlayer, other);
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		other = teamPlayer.getName();
		if (commandContainer.size() == 2)
		{
			other = commandContainer.getArgument(1);
		}
		info = new InfoAction();
		info.checkRequirements(other);
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.oneOrMore("info")
				.optional(new PatternBuilder()
						.whiteSpace()
						.anyString())
				.whiteSpaceOptional()
				.toString();
	}

	@Override
	public String getPermissionNode()
	{
		return "xteam.core.user.info";
	}

	@Override
	public String getUsage()
	{
		return "/team info {Team/Player}";
	}

	@Override
	public String getDescription()
	{
		return "get team info or other team's info";
	}
}
