package me.protocos.xteam.command.teamleader;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.xTeamPlugin;
import me.protocos.xteam.api.core.ITeamPlayer;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.UserCommand;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.BukkitUtil;
import me.protocos.xteam.util.ChatColorUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UserRemove extends UserCommand
{
	private String otherPlayer;

	public UserRemove()
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
			xTeamPlugin.getInstance().getTeamManager().removeTeam(team.getName());
		}
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		otherPlayer = parseCommand.get(1);
		ITeamPlayer other = xTeamPlugin.getInstance().getPlayerManager().getPlayer(otherPlayer);
		Requirements.checkPlayerHasTeam(teamPlayer);
		Requirements.checkPlayerIsTeamLeader(teamPlayer);
		Requirements.checkPlayerIsTeammate(teamPlayer, other);
		Requirements.checkPlayerLeaderLeaving(other);
	}

	@Override
	public String getPattern()
	{
		return patternOneOrMore("re") + patternOneOrMore("move") + WHITE_SPACE + ANY_CHARS + OPTIONAL_WHITE_SPACE;
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
