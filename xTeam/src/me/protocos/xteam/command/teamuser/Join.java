package me.protocos.xteam.command.teamuser;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.BaseUserCommand;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.core.exception.*;
import me.protocos.xteam.util.PermissionUtil;
import me.protocos.xteam.util.StringUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Join extends BaseUserCommand
{
	private String desiredTeam;

	public Join()
	{
		super();
	}
	public Join(Player sender, String command)
	{
		super(sender, command);
	}
	@Override
	protected void act()
	{
		Team foundTeam = xTeam.tm.getTeam(desiredTeam);
		foundTeam.addPlayer(teamPlayer.getName());
		Data.invites.remove(teamPlayer.getName());
		//		Data.Teams.put(teamPlayer.getName(), foundTeam);
		for (String teammate : foundTeam.getPlayers())
		{
			TeamPlayer mate = new TeamPlayer(teammate);
			if (mate.isOnline() && !teamPlayer.getName().equals(mate.getName()))
				mate.sendMessage(teamPlayer.getName() + ChatColor.AQUA + " joined your team");
		}
		originalSender.sendMessage("You joined " + ChatColor.AQUA + desiredTeam);
	}
	@Override
	public void checkRequirements() throws TeamException
	{
		if (teamPlayer == null)
		{
			throw new TeamPlayerDoesNotExistException();
		}
		if (parseCommand.size() == 2)
		{
			desiredTeam = parseCommand.get(1);
		}
		else
		{
			throw new TeamInvalidCommandException();
		}
		if (!PermissionUtil.hasPermission(originalSender, getPermissionNode()))
		{
			throw new TeamPlayerPermissionException();
		}
		if (teamPlayer.hasTeam())
		{
			throw new TeamPlayerHasTeamException();
		}
		if (Data.DEFAULT_TEAM_ONLY && !StringUtil.toLowerCase(Data.DEFAULT_TEAM_NAMES).contains(desiredTeam.toLowerCase()) && Data.DEFAULT_TEAM_NAMES.size() > 0)
		{
			throw new TeamOnlyJoinDefaultException();
		}
		Team foundTeam = xTeam.tm.getTeam(desiredTeam);
		if (foundTeam == null)
		{
			throw new TeamDoesNotExistException();
		}
		if (!foundTeam.isOpenJoining() && (Data.invites.get(teamPlayer.getName()) == null || !Data.invites.get(teamPlayer.getName()).equals(foundTeam)))
		{
			throw new TeamPlayerHasNoInviteException();
		}
		if (foundTeam.getPlayers().size() >= Data.MAX_PLAYERS && Data.MAX_PLAYERS > 0)
		{
			throw new TeamPlayerMaxException();
		}
	}
	@Override
	public String getPattern()
	{
		return patternOneOrMore("join") + WHITE_SPACE + ANY_CHARS + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getPermissionNode()
	{
		return "xteam.player.core.join";
	}
	@Override
	public String getUsage()
	{
		return baseCommand + " join [ITeam]";
	}
}
