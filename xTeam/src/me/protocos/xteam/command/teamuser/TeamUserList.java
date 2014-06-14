package me.protocos.xteam.command.teamuser;

import java.util.List;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.TeamUserCommand;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.message.Message;
import me.protocos.xteam.message.MessageUtil;
import me.protocos.xteam.util.PatternBuilder;

public class TeamUserList extends TeamUserCommand
{
	public TeamUserList(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		List<String> teams = teamCoordinator.getTeams().getOrder();
		String message = "Teams: " + teams.toString().replaceAll("\\[|\\]", "");
		if (teams.isEmpty())
			new Message.Builder("There are " + MessageUtil.red("no") + " teams").addRecipients(teamUser).send(log);
		else
			new Message.Builder(message).addRecipients(teamUser).send(log);
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.oneOrMore("list")
				.whiteSpaceOptional()
				.toString();
	}

	@Override
	public String getPermissionNode()
	{
		return "xteam.core.user.list";
	}

	@Override
	public String getUsage()
	{
		return "/team list";
	}

	@Override
	public String getDescription()
	{
		return "list all teams on the server";
	}
}
