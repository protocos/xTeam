package me.protocos.xteam.command.teamuser;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.UserCommand;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import org.bukkit.command.CommandSender;

public class UserChat extends UserCommand
{
	private String option;

	public UserChat()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		if (option.equalsIgnoreCase("ON"))
		{
			Data.chatStatus.add(teamPlayer.getName());
			originalSender.sendMessage("You are now only chatting with " + ChatColorUtil.positiveMessage("your team"));
		}
		if (option.equalsIgnoreCase("OFF"))
		{
			Data.chatStatus.remove(teamPlayer.getName());
			originalSender.sendMessage("You are now chatting with " + ChatColorUtil.negativeMessage("everyone"));
		}
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		super.checkRequirements(originalSender, parseCommand);
		//FIX move this if statememt to the act method
		if (parseCommand.size() == 1)
		{
			if (Data.chatStatus.contains(teamPlayer.getName()))
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
		return "c" + patternOneOrMore("hat") + "(" + WHITE_SPACE + ANY_CHARS + ")?" + OPTIONAL_WHITE_SPACE;
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
}
