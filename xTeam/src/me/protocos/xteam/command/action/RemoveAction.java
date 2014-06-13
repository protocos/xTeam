package me.protocos.xteam.command.action;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.core.IPlayerFactory;
import me.protocos.xteam.core.ITeamCoordinator;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.entity.ITeamPlayer;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.message.Message;
import me.protocos.xteam.model.ILog;
import org.bukkit.command.CommandSender;

public class RemoveAction
{
	private ILog log;
	private ITeamCoordinator teamCoordinator;
	private IPlayerFactory playerFactory;

	public RemoveAction(TeamPlugin teamPlugin)
	{
		this.log = teamPlugin.getLog();
		this.teamCoordinator = teamPlugin.getTeamCoordinator();
		this.playerFactory = teamPlugin.getPlayerFactory();
	}

	public void actOn(CommandSender sender, String teamName, String playerName)
	{
		ITeam team = teamCoordinator.getTeam(teamName);
		ITeamPlayer player = playerFactory.getPlayer(playerName);
		team.removePlayer(playerName);
		new Message.Builder("You have been removed from " + team.getName()).addRecipients(player).send(log);
		new Message.Builder(playerName + " has been removed from " + team.getName()).addRecipients(sender).addRecipients(team).send(log);
		if (team.isEmpty())
		{
			new Message.Builder(team.getName() + " has been disbanded").addRecipients(sender).addRecipients(player).send(log);
			teamCoordinator.disbandTeam(team.getName());
		}
	}

	public void checkRequirements(String teamName, String playerName) throws TeamException
	{
		ITeam team = teamCoordinator.getTeam(teamName);
		ITeamPlayer player = playerFactory.getPlayer(playerName);
		Requirements.checkTeamExists(teamCoordinator, teamName);
		Requirements.checkPlayerHasPlayedBefore(player);
		Requirements.checkPlayerHasTeam(player);
		Requirements.checkPlayerOnTeam(player, team);
		Requirements.checkPlayerLeaderLeaving(player);
	}
}
