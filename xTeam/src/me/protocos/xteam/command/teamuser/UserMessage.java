package me.protocos.xteam.command.teamuser;

import static me.protocos.xteam.util.StringUtil.*;
import java.io.InvalidClassException;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.UserCommand;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.core.exception.TeamPlayerHasNoTeamException;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class UserMessage extends UserCommand
{
	public UserMessage()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		String message = "";
		for (int i = 1; i < parseCommand.size(); i++)
		{
			message += " " + parseCommand.get(i);
		}
		for (TeamPlayer teammate : teamPlayer.getOnlineTeammates())
		{
			teammate.sendMessage("[" + ChatColor.DARK_GREEN + teamPlayer.getName() + ChatColor.RESET + "]" + message);
		}
		originalSender.sendMessage("[" + ChatColor.DARK_GREEN + teamPlayer.getName() + ChatColor.RESET + "]" + message);
	}
	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, InvalidClassException
	{
		super.checkRequirements(originalSender, parseCommand);
		if (!teamPlayer.hasTeam())
		{
			throw new TeamPlayerHasNoTeamException();
		}
	}
	@Override
	public String getPattern()
	{
		return "(" + patternOneOrMore("message") + "|" + "tell" + ")" + WHITE_SPACE + "[" + WHITE_SPACE + ANY_CHARS + "]+";
	}
	@Override
	public String getPermissionNode()
	{
		return "xteam.player.core.chat";
	}
	@Override
	public String getUsage()
	{
		return "/team message [Message]";
	}
}
