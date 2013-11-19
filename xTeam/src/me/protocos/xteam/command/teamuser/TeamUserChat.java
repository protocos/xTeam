package me.protocos.xteam.command.teamuser;

import me.protocos.xteam.api.command.TeamUserCommand;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.Configuration;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import me.protocos.xteam.util.PatternBuilder;
import org.bukkit.command.CommandSender;

public class TeamUserChat extends TeamUserCommand
{
	private String option;

	public TeamUserChat()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		if (option.equalsIgnoreCase("ON"))
		{
			Configuration.chatStatus.add(teamPlayer.getName());
			originalSender.sendMessage("You are now only chatting with " + ChatColorUtil.positiveMessage("your team"));
		}
		if (option.equalsIgnoreCase("OFF"))
		{
			Configuration.chatStatus.remove(teamPlayer.getName());
			originalSender.sendMessage("You are now chatting with " + ChatColorUtil.negativeMessage("everyone"));
		}
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		//FIX move this if statememt to the act method
		if (parseCommand.size() == 1)
		{
			if (Configuration.chatStatus.contains(teamPlayer.getName()))
				option = "OFF";
			else
				option = "ON";
		}
		else if (parseCommand.size() == 2 && (parseCommand.get(1).equalsIgnoreCase("ON") || parseCommand.get(1).equalsIgnoreCase("OFF")))
		{
			option = parseCommand.get(1);
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
		return "xteam.player.core.chat";
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
