package me.protocos.xteam.command.teamleader;

import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.TeamLeaderCommand;
import me.protocos.xteam.api.entity.ITeamPlayer;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import me.protocos.xteam.util.PatternBuilder;

public class TeamLeaderDisband extends TeamLeaderCommand
{
	public TeamLeaderDisband()
	{
		super();
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		for (ITeamPlayer playerDisband : teamPlayer.getOnlineTeammates())
		{
			playerDisband.sendMessage("Team has been " + ChatColorUtil.negativeMessage("disbanded") + " by the team leader");
		}
		xTeam.getInstance().getTeamManager().disbandTeam(team.getName());
		teamPlayer.sendMessage("You " + ChatColorUtil.negativeMessage("disbanded") + " your team");
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.oneOrMore("dis")
				.oneOrMore("band")
				.whiteSpaceOptional()
				.toString();
	}

	@Override
	public String getPermissionNode()
	{
		return "xteam.core.leader.disband";
	}

	@Override
	public String getUsage()
	{
		return "/team disband";
	}

	@Override
	public String getDescription()
	{
		return "disband the team";
	}
}
