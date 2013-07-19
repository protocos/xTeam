package me.protocos.xteam.command.teamuser;

import static me.protocos.xteam.util.StringUtil.*;
import java.io.InvalidClassException;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.UserCommand;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.InviteHandler;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.exception.*;
import me.protocos.xteam.util.StringUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class UserJoin extends UserCommand
{
	private String desiredTeam;

	public UserJoin()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		Team foundTeam = xTeam.tm.getTeam(desiredTeam);
		foundTeam.addPlayer(teamPlayer.getName());
		InviteHandler.removeInvite(teamPlayer.getName());
		teamPlayer.sendMessageToTeam(ChatColor.AQUA + " joined your team");
		originalSender.sendMessage("You joined " + ChatColor.AQUA + foundTeam.getName());
	}
	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, InvalidClassException
	{
		super.checkRequirements(originalSender, parseCommand);
		desiredTeam = parseCommand.get(1);
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
		//if sender has not been invited to join a team OR sender has been invited to another team
		if (!InviteHandler.hasInvite(teamPlayer.getName()) || !InviteHandler.getInviteTeam(teamPlayer.getName()).equals(foundTeam))
		{
			if (!foundTeam.isOpenJoining())
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
		return "/team join [Team]";
	}
}
