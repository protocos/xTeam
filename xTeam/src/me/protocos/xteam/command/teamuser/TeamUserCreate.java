package me.protocos.xteam.command.teamuser;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.command.TeamUserCommand;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.entity.Team;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.MessageUtil;
import me.protocos.xteam.util.PatternBuilder;

public class TeamUserCreate extends TeamUserCommand
{
	private String desiredName;

	public TeamUserCreate(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		String leader = teamPlayer.getName();
		Team newTeam = Team.createTeamWithLeader(teamPlugin, desiredName, leader);
		teamManager.createTeam(newTeam);
		Configuration.lastCreated.put(leader, Long.valueOf(System.currentTimeMillis()));
		teamPlayer.sendMessage("You " + MessageUtil.green("created") + " " + desiredName);
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		desiredName = commandContainer.getArgument(1);
		Requirements.checkPlayerDoesNotHaveTeam(teamPlayer);
		Requirements.checkTeamOnlyJoinDefault(teamManager, desiredName);
		Requirements.checkTeamNameTooLong(desiredName);
		Requirements.checkPlayerLastCreatedTeam(teamPlayer);
		Requirements.checkTeamNameAlphaNumeric(desiredName);
		Requirements.checkTeamAlreadyExists(teamManager, desiredName);
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.oneOrMore("create")
				.whiteSpace()
				.anyString()
				.whiteSpaceOptional()
				.toString();
	}

	@Override
	public String getPermissionNode()
	{
		return "xteam.core.user.create";
	}

	@Override
	public String getUsage()
	{
		return "/team create [Name]";
	}

	@Override
	public String getDescription()
	{
		return "create a team";
	}
}
