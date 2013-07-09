package me.protocos.xteam.command.teamuser;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.UserCommand;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.InviteHandler;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.core.exception.*;
import me.protocos.xteam.util.PermissionUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class UserAccept extends UserCommand
{
	public UserAccept()
	{
	}
	public UserAccept(Player sender, CommandParser command)
	{
		super(sender, command);
	}
	@Override
	protected void act()
	{
		Team inviteTeam = InviteHandler.getInviteTeam(teamPlayer.getName());
		inviteTeam.addPlayer(teamPlayer.getName());
		InviteHandler.removeInvite(teamPlayer.getName());
		for (String teammate : inviteTeam.getPlayers())
		{
			TeamPlayer mate = new TeamPlayer(teammate);
			if (mate.isOnline() && !teamPlayer.getName().equals(mate.getName()))
				mate.sendMessage(teamPlayer.getName() + ChatColor.AQUA + " joined your team");
		}
		sender.sendMessage("You joined " + ChatColor.AQUA + inviteTeam.getName());
	}
	@Override
	public void checkRequirements() throws TeamException
	{
		if (teamPlayer == null)
		{
			throw new TeamPlayerDoesNotExistException();
		}
		if (parseCommand.size() == 1)
		{
		}
		else
		{
			throw new TeamInvalidCommandException();
		}
		if (!PermissionUtil.hasPermission(sender, getPermissionNode()))
		{
			throw new TeamPlayerPermissionException();
		}
		if (teamPlayer.hasTeam())
		{
			throw new TeamPlayerHasTeamException();
		}
		if (!InviteHandler.hasInvite(teamPlayer.getName()))
		{
			throw new TeamPlayerHasNoInviteException();
		}
		Team inviteTeam = InviteHandler.getInviteTeam(teamPlayer.getName());
		if (inviteTeam.getPlayers().size() >= Data.MAX_PLAYERS && Data.MAX_PLAYERS > 0)
		{
			throw new TeamPlayerMaxException();
		}
	}
	@Override
	public String getPattern()
	{
		return patternOneOrMore("accept") + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getPermissionNode()
	{
		return "xteam.player.core.accept";
	}
	@Override
	public String getUsage()
	{
		return parseCommand.getBaseCommand() + " accept";
	}
}
