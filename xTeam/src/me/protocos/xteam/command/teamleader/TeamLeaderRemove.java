package me.protocos.xteam.command.teamleader;

import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.TeamLeaderCommand;
import me.protocos.xteam.api.core.ITeamPlayer;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.BukkitUtil;
import me.protocos.xteam.util.ChatColorUtil;
import me.protocos.xteam.util.PatternBuilder;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeamLeaderRemove extends TeamLeaderCommand
{
	private String otherPlayer;

	public TeamLeaderRemove()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		String teamName = teamPlayer.getTeam().getName();
		team.removePlayer(otherPlayer);
		Player other = BukkitUtil.getPlayer(otherPlayer);
		if (other != null)
			other.sendMessage("You've been " + ChatColorUtil.negativeMessage("removed") + " from " + team.getName());
		originalSender.sendMessage("You" + ChatColorUtil.negativeMessage(" removed ") + otherPlayer + " from your team");
		if (team.isEmpty())
		{
			originalSender.sendMessage(teamName + " has been disbanded");
			xTeam.getInstance().getTeamManager().removeTeam(team.getName());
		}
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		otherPlayer = parseCommand.get(1);
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
		return "xteam.leader.core.remove";
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
