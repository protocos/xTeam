package me.protocos.xteam.command.teamleader;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.command.TeamLeaderCommand;
import me.protocos.xteam.entity.ITeamPlayer;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.message.Message;
import me.protocos.xteam.util.PatternBuilder;

public class TeamLeaderDemote extends TeamLeaderCommand
{
	private String otherPlayer;

	public TeamLeaderDemote(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		team.demote(otherPlayer);
		ITeamPlayer demotePlayer = playerFactory.getPlayer(otherPlayer);
		new Message.Builder("You have been demoted").addRecipients(demotePlayer).send(log);
		new Message.Builder("You demoted " + otherPlayer).addRecipients(teamLeader).excludeRecipients(demotePlayer).send(log);
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		otherPlayer = commandContainer.getArgument(1);
		ITeamPlayer demotePlayer = playerFactory.getPlayer(otherPlayer);
		Requirements.checkPlayerHasTeam(demotePlayer);
		Requirements.checkPlayerIsTeammate(teamLeader, demotePlayer);
		Requirements.checkPlayerLeaderDemote(demotePlayer);
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.oneOrMore("demote")
				.whiteSpace()
				.anyString()
				.whiteSpaceOptional()
				.toString();
	}

	@Override
	public String getPermissionNode()
	{
		return "xteam.core.leader.demote";
	}

	@Override
	public String getUsage()
	{
		return "/team demote [Player]";
	}

	@Override
	public String getDescription()
	{
		return "demote team admin";
	}
}
