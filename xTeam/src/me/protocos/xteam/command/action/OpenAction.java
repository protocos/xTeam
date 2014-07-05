package me.protocos.xteam.command.action;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.core.ITeamCoordinator;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.message.Message;
import me.protocos.xteam.model.ILog;
import org.bukkit.command.CommandSender;

public class OpenAction
{
	private ILog log;

	public OpenAction(TeamPlugin teamPlugin)
	{
		this.log = teamPlugin.getLog();
	}

	public void checkRequirements(ITeamCoordinator teamCoordinator, String teamName) throws TeamException
	{
		Requirements.checkTeamExists(teamCoordinator, teamName);
	}

	public void actOn(CommandSender sender, ITeam team)
	{
		team.setOpenJoining(!team.isOpenJoining());
		new Message.Builder("Open joining is now " + (team.isOpenJoining() ? "enabled" : "disabled") + " for " + team.getName()).addRecipients(sender).addRecipients(team).send(log);
	}
}
