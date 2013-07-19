package me.protocos.xteam.command.teamuser;

import static me.protocos.xteam.util.StringUtil.*;
import java.io.InvalidClassException;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.UserCommand;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.InviteHandler;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.core.exception.TeamPlayerHasNoInviteException;
import me.protocos.xteam.core.exception.TeamPlayerHasTeamException;
import me.protocos.xteam.core.exception.TeamPlayerMaxException;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class UserAccept extends UserCommand
{
	public UserAccept()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
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
		originalSender.sendMessage("You joined " + ChatColor.AQUA + inviteTeam.getName());
	}
	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, InvalidClassException
	{
		super.checkRequirements(originalSender, parseCommand);
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
		return "/team accept";
	}
}
