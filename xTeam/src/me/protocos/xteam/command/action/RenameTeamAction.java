package me.protocos.xteam.command.action;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.core.ITeamCoordinator;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.message.Message;
import me.protocos.xteam.model.ILog;
import org.bukkit.command.CommandSender;

public class RenameTeamAction
{
	private ILog log;
	private ITeamCoordinator teamCoordinator;

	public RenameTeamAction(TeamPlugin teamPlugin)
	{
		this.teamCoordinator = teamPlugin.getTeamCoordinator();
		this.log = teamPlugin.getLog();
	}

	public void checkRequirementsOn(String teamName, String desiredName) throws TeamException
	{
		Requirements.checkTeamNameTooLong(desiredName);
		Requirements.checkTeamNameAlphaNumeric(desiredName);
		Requirements.checkTeamExists(teamCoordinator, teamName);
		Requirements.checkTeamRenameInUse(teamCoordinator, teamCoordinator.getTeam(teamName), desiredName);
	}

	public void actOn(CommandSender sender, String teamName, String desiredName)
	{
		ITeam team = teamCoordinator.getTeam(teamName);
		teamCoordinator.renameTeam(team, desiredName);
		new Message.Builder("You renamed the team to " + desiredName).addRecipients(sender).send(log);
		new Message.Builder("The team has been renamed to " + desiredName).addRecipients(team).excludeRecipients(sender).send(log);
	}
}
