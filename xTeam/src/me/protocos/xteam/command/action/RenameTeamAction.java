package me.protocos.xteam.command.action;

import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.core.ITeamCoordinator;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.message.Message;
import me.protocos.xteam.message.MessageSender;
import org.bukkit.command.CommandSender;

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

	public void actOn(CommandSender commandSender, String teamName, String desiredName)
	{
		MessageSender sender = new MessageSender(commandSender);
		ITeam team = teamCoordinator.getTeam(teamName);
		teamCoordinator.renameTeam(team, desiredName);
		Message message = new Message.Builder("You renamed the team to " + desiredName).addRecipients(sender).build();
		message.send();
		message = new Message.Builder("The team has been renamed to " + desiredName).addRecipients(team).excludeRecipients(sender).build();
		message.send();
	}
}
