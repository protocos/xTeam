package me.protocos.xteam.command.teamuser;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.command.BaseUserCommand;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.InviteHandler;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.core.exception.*;
import me.protocos.xteam.util.PermissionUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Accept extends BaseUserCommand
{
	public Accept()
	{
		super();
	}
	public Accept(Player sender, String command)
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
		player.sendMessage("You joined " + ChatColor.AQUA + inviteTeam.getName());
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
		if (!PermissionUtil.hasPermission(player, getPermissionNode()))
		{
			throw new TeamPlayerPermissionException();
		}
		if (teamPlayer.hasTeam())
		{
			throw new TeamPlayerHasTeamException();
		}
		Team inviteTeam = InviteHandler.getInviteTeam(teamPlayer.getName());
		if (inviteTeam == null)
		{
			throw new TeamPlayerHasNoInviteException();
		}
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
		return baseCommand + " accept";
	}
}
