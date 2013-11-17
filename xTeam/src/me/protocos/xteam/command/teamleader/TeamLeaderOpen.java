package me.protocos.xteam.command.teamleader;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.api.command.TeamLeaderCommand;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import org.bukkit.command.CommandSender;

public class TeamLeaderOpen extends TeamLeaderCommand
{
	public TeamLeaderOpen()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		team.setOpenJoining(!team.isOpenJoining());
		if (team.isOpenJoining())
			originalSender.sendMessage("Open joining is now " + ChatColorUtil.positiveMessage("enabled"));
		else
			originalSender.sendMessage("Open joining is now " + ChatColorUtil.negativeMessage("disabled"));
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
	}

	@Override
	public String getPattern()
	{
		return patternOneOrMore("open") + OPTIONAL_WHITE_SPACE;
	}

	@Override
	public String getPermissionNode()
	{
		return "xteam.leader.core.open";
	}

	@Override
	public String getUsage()
	{
		return "/team open";
	}

	@Override
	public String getDescription()
	{
		return "open team to public joining";
	}
}
