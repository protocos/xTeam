package me.protocos.xteam.command.serveradmin;

import me.protocos.xteam.api.command.ServerAdminCommand;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.core.Configuration;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import me.protocos.xteam.util.PatternBuilder;
import org.bukkit.command.CommandSender;

public class ServerAdminChatSpy extends ServerAdminCommand
{
	public ServerAdminChatSpy()
	{
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		if (!Configuration.spies.contains(originalSender.getName()))
		{
			Configuration.spies.add(originalSender.getName());
			originalSender.sendMessage("You are " + ChatColorUtil.negativeMessage("now") + " spying on team chat");
		}
		else
		{
			Configuration.spies.remove(originalSender.getName());
			originalSender.sendMessage("You are " + ChatColorUtil.positiveMessage("no longer") + " spying on team chat");
		}
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
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
		return "xteam.serveradmin.core.chatspy";
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
