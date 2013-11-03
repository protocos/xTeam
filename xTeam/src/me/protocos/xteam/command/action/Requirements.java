package me.protocos.xteam.command.action;

import static me.protocos.xteam.util.StringUtil.ALPHA_NUMERIC;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.core.ITeamPlayer;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.exception.*;
import me.protocos.xteam.util.StringUtil;

public class Requirements
{
	public static void checkTeamExists(String teamName) throws TeamDoesNotExistException
	{
		if (!xTeam.getTeamManager().contains(teamName))
		{
			throw new TeamDoesNotExistException();
		}
	}
	public static void checkPlayerHasTeam(ITeamPlayer player) throws TeamPlayerHasNoTeamException
	{
		if (!player.hasTeam())
		{
			throw new TeamPlayerHasNoTeamException();
		}
	}

	public static void checkPlayerHasPlayedBefore(ITeamPlayer player) throws TeamPlayerNeverPlayedException
	{
		if (!player.hasPlayedBefore())
		{
			throw new TeamPlayerNeverPlayedException();
		}
	}

	public static void checkPlayerIsAdmin(ITeamPlayer player) throws TeamPlayerNotAdminException
	{
		if (!player.isAdmin())
		{
			throw new TeamPlayerNotAdminException();
		}
	}

	public static void checkPlayerOnTeam(ITeamPlayer player, Team team) throws TeamPlayerNotOnTeamException
	{
		if (!team.containsPlayer(player.getName()))
		{
			throw new TeamPlayerNotOnTeamException();
		}
	}

	public static void checkPlayerLeaderDemote(ITeamPlayer player) throws TeamPlayerLeaderDemoteException
	{
		if (player.isLeader())
		{
			throw new TeamPlayerLeaderDemoteException();
		}
	}

	public static void checkTeamIsDefault(Team team) throws TeamIsDefaultException
	{
		if (team.isDefaultTeam())
		{
			throw new TeamIsDefaultException();
		}
	}

	public static void checkLeaderLeaving(Team team, String playerName) throws TeamPlayerLeaderLeavingException
	{
		if (team.getLeader().equals(playerName) && team.getPlayers().size() > 1)
		{
			throw new TeamPlayerLeaderLeavingException();
		}
	}

	public static void checkTeamAlreadyExists(String name) throws TeamAlreadyExistsException
	{
		if (xTeam.getTeamManager().contains(name))
		{
			throw new TeamAlreadyExistsException();
		}
	}

	public static void checkTeamAlphaNumericName(String desiredName) throws TeamNameNotAlphaException
	{
		if (Data.ALPHA_NUM && !desiredName.matches(ALPHA_NUMERIC))
		{
			throw new TeamNameNotAlphaException();
		}
	}

	public static void checkPlayerLeaderLeaving(ITeamPlayer p) throws TeamPlayerLeaderLeavingException
	{
		if (p.hasTeam() && p.isLeader() && p.getTeam().size() > 1)
		{
			throw new TeamPlayerLeaderLeavingException();
		}
	}

	public static void checkPlayerAlreadyOnTeam(ITeamPlayer player, String teamName) throws TeamPlayerAlreadyOnTeamException
	{
		if (player.hasTeam() && player.getTeam().getName().equalsIgnoreCase(teamName))
		{
			throw new TeamPlayerAlreadyOnTeamException();
		}
	}

	public static void checkTeamPlayerMax(String teamName) throws TeamPlayerMaxException
	{
		if (xTeam.getTeamManager().contains(teamName) && xTeam.getTeamManager().getTeam(teamName).size() >= Data.MAX_PLAYERS && Data.MAX_PLAYERS > 0)
		{
			throw new TeamPlayerMaxException();
		}
	}

	public static void checkTeamNameAgainstTags(String desiredTag, Team team) throws TeamNameConflictsWithTagException
	{
		if (!desiredTag.equalsIgnoreCase(team.getName()) && StringUtil.toLowerCase(xTeam.getTeamManager().getAllTeamNames()).contains(desiredTag.toLowerCase()))
		{
			throw new TeamNameConflictsWithTagException();
		}
	}
}
