package me.protocos.xteam.command.action;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.core.IPlayerFactory;
import me.protocos.xteam.core.ITeamCoordinator;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.entity.ITeamPlayer;
import me.protocos.xteam.entity.Team;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.message.Message;
import me.protocos.xteam.message.MessageSender;
import org.bukkit.command.CommandSender;

public class SetTeamAction
{
	private TeamPlugin teamPlugin;
	private ITeamCoordinator teamCoordinator;
	private IPlayerFactory playerFactory;

	public SetTeamAction(TeamPlugin teamPlugin)
	{
		this.teamPlugin = teamPlugin;
		this.teamCoordinator = teamPlugin.getTeamCoordinator();
		this.playerFactory = teamPlugin.getPlayerFactory();
	}

	public void checkRequirementsOn(String playerName, String teamName) throws TeamException
	{
		ITeamPlayer player = playerFactory.getPlayer(playerName);
		Requirements.checkPlayerHasPlayedBefore(player);
		Requirements.checkPlayerLeaderLeaving(player);
		Requirements.checkPlayerAlreadyOnTeam(player, teamName);
		Requirements.checkTeamPlayerMax(teamCoordinator, teamName);
	}

	public void actOn(CommandSender commandSender, String playerName, String teamName)
	{
		MessageSender sender = new MessageSender(commandSender);
		ITeamPlayer p = playerFactory.getPlayer(playerName);
		if (p.hasTeam())
		{
			removePlayer(sender, p);
		}
		if (!teamCoordinator.containsTeam(teamName))
		{
			createTeamWithLeader(sender, teamName, p);
		}
		else
		{
			addPlayerToTeam(sender, p, teamCoordinator.getTeam(teamName));
		}
	}

	public void removePlayer(MessageSender sender, ITeamPlayer player)
	{
		ITeam playerTeam = player.getTeam();
		String teamName = playerTeam.getName();
		String playerName = player.getName();
		playerTeam.removePlayer(player.getName());
		Configuration.chatStatus.remove(playerName);
		player.removeReturnLocation();
		Message message = new Message.Builder("You have been removed from " + teamName).addRecipients(player).build();
		message.send();
		message = new Message.Builder(playerName + " has been removed from " + teamName).addRecipients(sender).addRecipients(playerTeam).excludeRecipients(player).build();
		message.send();
		if (playerTeam.isEmpty() && !playerTeam.isDefaultTeam())
		{
			teamCoordinator.disbandTeam(teamName);
			message = new Message.Builder(teamName + " has been disbanded").addRecipients(sender).build();
			message.send();
			message = new Message.Builder(teamName + " has been disbanded").addRecipients(player).excludeRecipients(sender).build();
			message.send();
		}
	}

	public void addPlayerToTeam(MessageSender sender, ITeamPlayer player, ITeam team)
	{
		String playerName = player.getName();
		String teamName = team.getName();
		team.addPlayer(playerName);
		Message message = new Message.Builder("You have been added to " + teamName).addRecipients(player).build();
		message.send();
		message = new Message.Builder(playerName + " has been added to " + teamName).addRecipients(sender).addRecipients(team).excludeRecipients(player).build();
		message.send();
	}

	public void createTeamWithLeader(MessageSender sender, String teamName, ITeamPlayer player)
	{
		String playerName = player.getName();
		Team newTeam = Team.createTeamWithLeader(teamPlugin, teamName, playerName);
		teamCoordinator.createTeam(newTeam);
		Message message = new Message.Builder(teamName + " has been created").addRecipients(player).build();
		message.send();
		message = new Message.Builder(teamName + " has been created").addRecipients(sender).excludeRecipients(player).build();
		message.send();
		message = new Message.Builder("You have been added to " + teamName).addRecipients(player).build();
		message.send();
		message = new Message.Builder(playerName + " has been added to " + teamName).addRecipients(sender).excludeRecipients(player).build();
		message.send();
	}
}
