package me.protocos.xteam.command.teamuser;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.core.ITeamPlayer;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.UserCommand;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.PlayerManager;
import me.protocos.xteam.core.exception.TeamException;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class UserLeave extends UserCommand
{
	public UserLeave()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		team.removePlayer(teamPlayer.getName());
		if (team.size() == 0 && !team.isDefaultTeam())
			xTeam.getTeamManager().removeTeam(team.getName());
		Data.chatStatus.remove(teamPlayer.getName());
		for (String teammate : team.getPlayers())
		{
			ITeamPlayer mate = PlayerManager.getPlayer(teammate);
			if (mate.isOnline())
				mate.sendMessage(teamPlayer.getName() + ChatColor.RED + " left your team");
		}
		originalSender.sendMessage("You left " + ChatColor.AQUA + team.getName());
	}
	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		super.checkRequirements(originalSender, parseCommand);
		Requirements.checkPlayerHasTeam(teamPlayer);
		Requirements.checkPlayerLeaderLeaving(teamPlayer);
	}
	@Override
	public String getPattern()
	{
		return "l" + "(" + patternOneOrMore("eave") + "|" + patternOneOrMore("ve") + ")" + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getPermissionNode()
	{
		return "xteam.player.core.leave";
	}
	@Override
	public String getUsage()
	{
		return "/team leave";
	}
}
