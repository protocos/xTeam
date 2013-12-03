package me.protocos.xteam.command.teamleader;

import me.protocos.xteam.api.command.TeamLeaderCommand;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import me.protocos.xteam.util.PatternBuilder;

public class TeamLeaderSetRally extends TeamLeaderCommand
{
	public TeamLeaderSetRally()
	{
		super();
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		team.setRally(teamPlayer.getLocation());
		teamPlayer.sendMessage("You " + ChatColorUtil.positiveMessage("set") + " the team rally point");
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		Requirements.checkTeamNotHasRally(team);
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.oneOrMore("set")
				.oneOrMore("rally")
				.whiteSpaceOptional()
				.toString();
	}

	@Override
	public String getPermissionNode()
	{
		return "xteam.core.leader.setrally";
	}

	@Override
	public String getUsage()
	{
		return "/team setrally";
	}

	@Override
	public String getDescription()
	{
		return "set rally point for the team";
	}
}
