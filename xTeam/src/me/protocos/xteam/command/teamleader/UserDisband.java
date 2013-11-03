package me.protocos.xteam.command.teamleader;

import static me.protocos.xteam.util.StringUtil.OPTIONAL_WHITE_SPACE;
import java.io.InvalidClassException;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.core.ITeamPlayer;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.UserCommand;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.core.exception.TeamPlayerHasNoTeamException;
import me.protocos.xteam.core.exception.TeamPlayerNotLeaderException;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class UserDisband extends UserCommand
{
	public UserDisband()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		for (ITeamPlayer playerDisband : teamPlayer.getOnlineTeammates())
		{
			playerDisband.sendMessage("Team has been " + ChatColor.RED + "disbanded" + ChatColor.RESET + " by the leader");
		}
		xTeam.getTeamManager().removeTeam(team.getName());
		originalSender.sendMessage("You " + ChatColor.RED + "disbanded" + ChatColor.RESET + " your team");
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, InvalidClassException
	{
		super.checkRequirements(originalSender, parseCommand);
		if (!teamPlayer.hasTeam())
		{
			throw new TeamPlayerHasNoTeamException();
		}
		if (!teamPlayer.isLeader())
		{
			throw new TeamPlayerNotLeaderException();
		}
	}

	@Override
	public String getPattern()
	{
		return "disband" + OPTIONAL_WHITE_SPACE;
	}

	@Override
	public String getPermissionNode()
	{
		return "xteam.leader.core.disband";
	}

	@Override
	public String getUsage()
	{
		return "/team disband";
	}
}
