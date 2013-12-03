package me.protocos.xteam.command.teamleader;

import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.TeamLeaderCommand;
import me.protocos.xteam.api.entity.ITeamPlayer;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.BukkitUtil;
import me.protocos.xteam.util.ChatColorUtil;
import me.protocos.xteam.util.PatternBuilder;
import org.bukkit.entity.Player;

public class TeamLeaderRemove extends TeamLeaderCommand
{
	private String otherPlayer;

	public TeamLeaderRemove()
	{
		super();
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		String teamName = teamPlayer.getTeam().getName();
		team.removePlayer(otherPlayer);
		Player other = BukkitUtil.getPlayer(otherPlayer);
		if (other != null)
			other.sendMessage("You've been " + ChatColorUtil.negativeMessage("removed") + " from " + team.getName());
		teamPlayer.sendMessage("You" + ChatColorUtil.negativeMessage(" removed ") + otherPlayer + " from your team");
		if (team.isEmpty())
		{
			teamPlayer.sendMessage(teamName + " has been disbanded");
			xTeam.getInstance().getTeamManager().removeTeam(team.getName());
		}
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		otherPlayer = commandContainer.getArgument(1);
		ITeamPlayer other = xTeam.getInstance().getPlayerManager().getPlayer(otherPlayer);
		Requirements.checkPlayerIsTeammate(teamPlayer, other);
		Requirements.checkPlayerLeaderLeaving(other);
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.oneOrMore("re")
				.oneOrMore("move")
				.whiteSpace()
				.anyString()
				.whiteSpaceOptional()
				.toString();
	}

	@Override
	public String getPermissionNode()
	{
		return "xteam.core.leader.remove";
	}

	@Override
	public String getUsage()
	{
		return "/team remove [Player]";
	}

	@Override
	public String getDescription()
	{
		return "remove player from your team";
	}
}
