package me.protocos.xteam.command.teamuser;

import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.TeamUserCommand;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.Configuration;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import me.protocos.xteam.util.PatternBuilder;

public class TeamUserCreate extends TeamUserCommand
{
	private String desiredName;

	public TeamUserCreate()
	{
		super();
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		String leader = teamPlayer.getName();
		Team newTeam = Team.createTeamWithLeader(desiredName, leader);
		xTeam.getInstance().getTeamManager().addTeam(newTeam);
		Configuration.lastCreated.put(leader, Long.valueOf(System.currentTimeMillis()));
		teamPlayer.sendMessage("You " + ChatColorUtil.positiveMessage("created") + " " + desiredName);
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		desiredName = commandContainer.getArgument(1);
		Requirements.checkPlayerDoesNotHaveTeam(teamPlayer);
		Requirements.checkTeamOnlyJoinDefault(desiredName);
		Requirements.checkTeamNameTooLong(desiredName);
		Requirements.checkPlayerLastCreatedTeam(teamPlayer);
		Requirements.checkTeamNameAlphaNumeric(desiredName);
		Requirements.checkTeamAlreadyExists(desiredName);
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
		return "xteam.core.player.create";
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
