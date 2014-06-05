package me.protocos.xteam.command.teamleader;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.command.TeamLeaderCommand;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.MessageUtil;
import me.protocos.xteam.util.PatternBuilder;

public class TeamLeaderSetRally extends TeamLeaderCommand
{
	public TeamLeaderSetRally(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		team.setRally(teamPlayer.getLocation());
		teamPlayer.sendMessage("You " + MessageUtil.green("set") + " the team rally point");
		teamPlayer.sendMessageToTeam("Team rally point has been " + MessageUtil.green("set") + " (expires in " + Configuration.RALLY_DELAY + " minutes)");
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
