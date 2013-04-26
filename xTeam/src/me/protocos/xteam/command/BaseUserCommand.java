package me.protocos.xteam.command;

import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.TeamPlayer;
import org.bukkit.entity.Player;

public abstract class BaseUserCommand extends BasePlayerCommand
{
	protected TeamPlayer teamPlayer;
	protected Team team;

	public BaseUserCommand()
	{
		super();
	}
	public BaseUserCommand(Player sender, String command)
	{
		super(sender, command);
		teamPlayer = new TeamPlayer(player);
		team = teamPlayer.getTeam();
	}

	public TeamPlayer getTeamPlayer()
	{
		return teamPlayer;
	}

	public Team getTeam()
	{
		return team;
	}

	public void setTeamPlayer(TeamPlayer player)
	{
		this.teamPlayer = player;
	}

	public void setTeam(Team team)
	{
		this.team = team;
	}
}
