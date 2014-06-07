package me.protocos.xteam.command.action;

import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.core.ITeamCoordinator;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.entity.ITeamEntity;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.message.MessageUtil;

public class RenameTeamAction
{
	private ITeamCoordinator teamCoordinator;

	public RenameTeamAction(ITeamCoordinator teamCoordinator)
	{
		this.teamCoordinator = teamCoordinator;
	}

	public void checkRequirementsOn(String teamName, String desiredName) throws TeamException
	{
		Requirements.checkTeamNameTooLong(desiredName);
		Requirements.checkTeamNameAlphaNumeric(desiredName);
		Requirements.checkTeamExists(teamCoordinator, teamName);
		Requirements.checkTeamRenameInUse(teamCoordinator, teamCoordinator.getTeam(teamName), desiredName);
	}

	public void actOn(ITeamEntity sender, String teamName, String desiredName)
	{
		ITeam team = teamCoordinator.getTeam(teamName);
		teamCoordinator.renameTeam(team, desiredName);
		if (!sender.isOnSameTeam(team) || team.isLeader(sender.getName()))
		{
			sender.sendMessage("You " + MessageUtil.green("renamed") + " the team to " + desiredName);
		}
		team.sendMessage("The team has been " + MessageUtil.green("renamed") + " to " + desiredName + " by an admin");
	}
}
