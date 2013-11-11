package me.protocos.xteam.command.teamuser;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.UserCommand;
import me.protocos.xteam.command.action.InfoAction;
import me.protocos.xteam.core.exception.TeamException;
import org.bukkit.command.CommandSender;

public class UserInfo extends UserCommand
{
	private String other;
	private InfoAction info;

	public UserInfo()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		info.actOn(originalSender, other);
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		other = teamPlayer.getName();
		if (parseCommand.size() == 2)
		{
			other = parseCommand.get(1);
		}
		info = new InfoAction();
		info.checkRequirements(other);
	}

	@Override
	public String getPattern()
	{
		return patternOneOrMore("info") + "(" + WHITE_SPACE + ANY_CHARS + ")?" + OPTIONAL_WHITE_SPACE;
	}

	@Override
	public String getPermissionNode()
	{
		return "info";
	}

	@Override
	public String getUsage()
	{
		return "/team info {Team/Player}";
	}

	@Override
	public String getDescription()
	{
		return "get team info or other team's info";
	}
}
