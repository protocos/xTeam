package me.protocos.xteam.command.teamleader;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.TeamLeaderCommand;
import me.protocos.xteam.entity.ITeamPlayer;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.message.Message;
import me.protocos.xteam.message.MessageUtil;
import me.protocos.xteam.util.PatternBuilder;

public class TeamLeaderDisband extends TeamLeaderCommand
{
	public TeamLeaderDisband(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		for (ITeamPlayer playerDisband : teamLeader.getOnlineTeammates())
		{
			new Message.Builder("Team has been " + MessageUtil.red("disbanded") + " by the team leader").addRecipients(playerDisband).send(log);
		}
		teamCoordinator.disbandTeam(team.getName());
		new Message.Builder("You " + MessageUtil.red("disbanded") + " your team").addRecipients(teamLeader).send(log);
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
