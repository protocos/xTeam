package me.protocos.xteam.command.teamuser;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.command.TeamUserCommand;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.entity.Team;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.message.Message;
import me.protocos.xteam.message.MessageUtil;
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
		String leader = teamUser.getName();
		Team newTeam = Team.createTeamWithLeader(teamPlugin, desiredName, leader);
		teamCoordinator.createTeam(newTeam);
		Configuration.lastCreated.put(leader, Long.valueOf(System.currentTimeMillis()));
		new Message.Builder("You " + MessageUtil.green("created") + " " + desiredName).addRecipients(teamUser).send(log);
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		desiredName = commandContainer.getArgument(1);
		Requirements.checkPlayerDoesNotHaveTeam(teamUser);
		Requirements.checkTeamOnlyJoinDefault(teamCoordinator, desiredName);
		Requirements.checkTeamNameTooLong(desiredName);
		Requirements.checkPlayerLastCreatedTeam(teamUser);
		Requirements.checkTeamNameAlphaNumeric(desiredName);
		Requirements.checkTeamNameInUse(teamCoordinator, desiredName);
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
