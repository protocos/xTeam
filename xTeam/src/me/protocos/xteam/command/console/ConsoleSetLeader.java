package me.protocos.xteam.command.console;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.entity.ITeamPlayer;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.message.Message;
import me.protocos.xteam.util.PatternBuilder;

public class ConsoleSetLeader extends ConsoleCommand
{
	private String teamName, playerName;

	public ConsoleSetLeader(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		ITeamPlayer player = playerFactory.getPlayer(playerName);
		ITeam team = player.getTeam();
		team.setLeader(playerName);
		new Message.Builder("You are now the team leader").addRecipients(player).send(log);
		if (!team.isDefaultTeam())
		{
			ITeamPlayer previousLeader = playerFactory.getPlayer(team.getLeader());
			new Message.Builder(playerName + " is now the team leader").addRecipients(previousLeader).send(log);
		}
		new Message.Builder(playerName + " is now the team leader for " + team.getName()).addRecipients(sender).send(log);
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		teamName = commandContainer.getArgument(1);
		playerName = commandContainer.getArgument(2);
		ITeamPlayer player = playerFactory.getPlayer(playerName);
		ITeam team = teamCoordinator.getTeam(teamName);
		Requirements.checkPlayerHasPlayedBefore(player);
		Requirements.checkTeamExists(teamCoordinator, teamName);
		Requirements.checkPlayerHasTeam(player);
		Requirements.checkPlayerOnTeam(player, team);
		Requirements.checkTeamIsDefault(team);
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.oneOrMore("set")
				.oneOrMore("leader")
				.whiteSpace()
				.anyString()
				.whiteSpace()
				.anyString()
				.whiteSpaceOptional()
				.toString();
	}

	@Override
	public String getUsage()
	{
		return "team setleader [Team] [Player]";
	}

	@Override
	public String getDescription()
	{
		return "set leader of team";
	}
}
