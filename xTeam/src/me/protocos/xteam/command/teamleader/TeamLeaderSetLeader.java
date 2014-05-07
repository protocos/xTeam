package me.protocos.xteam.command.teamleader;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.command.TeamLeaderCommand;
import me.protocos.xteam.entity.ITeamPlayer;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.MessageUtil;
import me.protocos.xteam.util.PatternBuilder;

public class TeamLeaderSetLeader extends TeamLeaderCommand
{
	private String otherPlayer;

	public TeamLeaderSetLeader(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		team.setLeader(otherPlayer);
		team.promote(teamPlayer.getName());
		ITeamPlayer other = playerManager.getPlayer(otherPlayer);
		if (other.isOnline())
			other.sendMessage("You are now the " + MessageUtil.positiveMessage("team leader"));
		teamPlayer.sendMessage(otherPlayer + " is now the " + MessageUtil.positiveMessage("team leader") + " (you are an admin)" +
				"\nYou can now " + MessageUtil.negativeMessage("leave") + " the team");
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		otherPlayer = commandContainer.getArgument(1);
		ITeamPlayer other = playerManager.getPlayer(otherPlayer);
		Requirements.checkPlayerIsTeammate(teamPlayer, other);
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.oneOrMore("set")
				.oneOrMore("leader")
				.whiteSpace()
				.anyString()
				.whiteSpaceOptional()
				.toString();
	}

	@Override
	public String getPermissionNode()
	{
		return "xteam.core.leader.setleader";
	}

	@Override
	public String getUsage()
	{
		return "/team setleader [Player]";
	}

	@Override
	public String getDescription()
	{
		return "set new leader for the team";
	}
}
