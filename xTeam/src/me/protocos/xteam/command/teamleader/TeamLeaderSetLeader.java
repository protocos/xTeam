package me.protocos.xteam.command.teamleader;

import me.protocos.xteam.XTeam;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.command.TeamLeaderCommand;
import me.protocos.xteam.entity.ITeamPlayer;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import me.protocos.xteam.util.PatternBuilder;

public class TeamLeaderSetLeader extends TeamLeaderCommand
{
	private String otherPlayer;

	public TeamLeaderSetLeader()
	{
		super();
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		team.setLeader(otherPlayer);
		//THIS IS A CRAPPY SOLUTION
		team.getAdmins().remove(otherPlayer);
		team.getAdmins().add(teamPlayer.getName());
		//Team.java NEEDS TO BE REFACTORED
		ITeamPlayer other = XTeam.getInstance().getPlayerManager().getPlayer(otherPlayer);
		if (other.isOnline())
			other.sendMessage("You are now the " + ChatColorUtil.positiveMessage("team leader"));
		teamPlayer.sendMessage(otherPlayer + " is now the " + ChatColorUtil.positiveMessage("team leader") + " (you are an admin)" +
				"\nYou can now " + ChatColorUtil.negativeMessage("leave") + " the team");
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		otherPlayer = commandContainer.getArgument(1);
		ITeamPlayer other = XTeam.getInstance().getPlayerManager().getPlayer(otherPlayer);
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
