package me.protocos.xteam.command.serveradmin;

import me.protocos.xteam.api.command.ServerAdminCommand;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import me.protocos.xteam.util.PatternBuilder;

public class ServerAdminChatSpy extends ServerAdminCommand
{
	public ServerAdminChatSpy()
	{
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		if (!Configuration.spies.contains(player.getName()))
		{
			Configuration.spies.add(player.getName());
			player.sendMessage("You are " + ChatColorUtil.negativeMessage("now") + " spying on team chat");
		}
		else
		{
			Configuration.spies.remove(player.getName());
			player.sendMessage("You are " + ChatColorUtil.positiveMessage("no longer") + " spying on team chat");
		}
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.oneOrMore("chat")
				.oneOrMore("spy")
				.whiteSpaceOptional()
				.toString();
	}

	@Override
	public String getPermissionNode()
	{
		return "xteam.core.serveradmin.chatspy";
	}

	@Override
	public String getUsage()
	{
		return "/team chatspy";
	}

	@Override
	public String getDescription()
	{
		return "spy on team chat";
	}
}
