package me.protocos.xteam.command.serveradmin;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.message.Message;
import me.protocos.xteam.message.MessageUtil;
import me.protocos.xteam.util.PatternBuilder;

public class ServerAdminChatSpy extends ServerAdminCommand
{
	public ServerAdminChatSpy(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		if (!Configuration.spies.contains(serverAdmin.getName()))
		{
			Configuration.spies.add(serverAdmin.getName());
			new Message.Builder("You are " + MessageUtil.red("now") + " spying on team chat").addRecipients(serverAdmin).send(log);
		}
		else
		{
			Configuration.spies.remove(serverAdmin.getName());
			new Message.Builder("You are " + MessageUtil.green("no longer") + " spying on team chat").addRecipients(serverAdmin).send(log);
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
