package me.protocos.xteam.command.teamleader;

import me.protocos.xteam.api.command.TeamLeaderCommand;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import me.protocos.xteam.util.PatternBuilder;

public class TeamLeaderOpen extends TeamLeaderCommand
{
	public TeamLeaderOpen()
	{
		super();
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		team.setOpenJoining(!team.isOpenJoining());
		if (team.isOpenJoining())
			teamPlayer.sendMessage("Open joining is now " + ChatColorUtil.positiveMessage("enabled"));
		else
			teamPlayer.sendMessage("Open joining is now " + ChatColorUtil.negativeMessage("disabled"));
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.oneOrMore("open")
				.whiteSpaceOptional()
				.toString();
	}

	@Override
	public String getPermissionNode()
	{
		return "xteam.core.leader.open";
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
