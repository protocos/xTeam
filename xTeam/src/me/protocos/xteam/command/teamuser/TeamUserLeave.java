package me.protocos.xteam.command.teamuser;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.command.TeamUserCommand;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.message.Message;
import me.protocos.xteam.message.MessageUtil;
import me.protocos.xteam.util.PatternBuilder;

public class TeamUserLeave extends TeamUserCommand
{
	public TeamUserLeave(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		team.removePlayer(teamUser.getName());
		if (team.size() == 0 && !team.isDefaultTeam())
			teamCoordinator.disbandTeam(team.getName());
		Configuration.chatStatus.remove(teamUser.getName());
		new Message.Builder(teamUser.getName() + " " + MessageUtil.red("left") + " your team").addRecipients(team).send(log);
		new Message.Builder("You " + MessageUtil.red("left") + " " + team.getName()).addRecipients(teamUser).send(log);
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		Requirements.checkPlayerHasTeam(teamUser);
		Requirements.checkPlayerLeaderLeaving(teamUser);
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.oneOrMore("lea")
				.oneOrMore("ve")
				.whiteSpaceOptional()
				.toString();
	}

	@Override
	public String getPermissionNode()
	{
		return "xteam.core.user.leave";
	}

	@Override
	public String getUsage()
	{
		return "/team leave";
	}

	@Override
	public String getDescription()
	{
		return "leave your team";
	}
}
