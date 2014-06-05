package me.protocos.xteam.command.teamuser;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.command.TeamUserCommand;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.MessageUtil;
import me.protocos.xteam.util.PatternBuilder;

public class TeamUserChat extends TeamUserCommand
{
	private String option;

	public TeamUserChat(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		if (option.equalsIgnoreCase("ON"))
		{
			Configuration.chatStatus.add(teamPlayer.getName());
			teamPlayer.sendMessage("You are now only chatting with " + MessageUtil.green("your team"));
		}
		if (option.equalsIgnoreCase("OFF"))
		{
			Configuration.chatStatus.remove(teamPlayer.getName());
			teamPlayer.sendMessage("You are now chatting with " + MessageUtil.red("everyone"));
		}
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		//FIX move this if statememt to the act method
		if (commandContainer.size() == 1)
		{
			if (Configuration.chatStatus.contains(teamPlayer.getName()))
				option = "OFF";
			else
				option = "ON";
		}
		else if (commandContainer.size() == 2 && (commandContainer.getArgument(1).equalsIgnoreCase("ON") || commandContainer.getArgument(1).equalsIgnoreCase("OFF")))
		{
			option = commandContainer.getArgument(1);
		}
		Requirements.checkPlayerHasTeam(teamPlayer);
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.append("c")
				.oneOrMore("hat")
				.optional(new PatternBuilder()
						.whiteSpace()
						.anyString())
				.whiteSpaceOptional()
				.toString();
	}

	@Override
	public String getPermissionNode()
	{
		return "xteam.core.user.chat";
	}

	@Override
	public String getUsage()
	{
		return "/team chat {On/Off}";
	}

	@Override
	public String getDescription()
	{
		return "toggle chatting with teammates";
	}
}
