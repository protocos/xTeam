package me.protocos.xteam.command;

import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.TeamPlayer;
import org.bukkit.entity.Player;

public abstract class UserCommand extends PlayerCommand
{
	protected TeamPlayer teamPlayer;
	protected Team team;

	public UserCommand()
	{
	}
	public UserCommand(Player sender, CommandParser command)
	{
		super(sender, command);
		teamPlayer = sender != null ? new TeamPlayer(sender) : null;
		team = teamPlayer != null ? teamPlayer.getTeam() : null;
	}

	public Team getTeam()
	{
		return team;
	}

	public TeamPlayer getTeamPlayer()
	{
		return teamPlayer;
	}

	public void setTeam(Team team)
	{
		this.team = team;
	}

	public void setTeamPlayer(TeamPlayer player)
	{
		this.teamPlayer = player;
	}
}
