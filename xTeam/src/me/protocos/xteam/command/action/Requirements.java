package me.protocos.xteam.command.action;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.core.ITeamPlayer;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.InviteHandler;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.core.exception.*;
import me.protocos.xteam.util.CommonUtil;
import me.protocos.xteam.util.HelpPages;
import me.protocos.xteam.util.PermissionUtil;
import me.protocos.xteam.util.StringUtil;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

public class Requirements
{
	private Requirements()
	{
	}

	public static void checkPlayerHasPermission(CommandSender originalSender, String permissionNode) throws TeamPlayerPermissionException
	{
		if (!PermissionUtil.hasPermission(originalSender, permissionNode))
		{
			throw new TeamPlayerPermissionException();
		}
	}

	public static void checkPlayerCommandIsValid(CommandParser parseCommand, String pattern) throws TeamInvalidCommandException
	{
		if (!parseCommand.getCommandWithoutID().matches(StringUtil.IGNORE_CASE + pattern))
		{
			throw new TeamInvalidCommandException();
		}
	}

	public static void checkTeamExists(String teamName) throws TeamDoesNotExistException
	{
		if (!xTeam.getTeamManager().contains(teamName))
		{
			throw new TeamDoesNotExistException();
		}
	}

	public static void checkPlayerHasTeam(ITeamPlayer teamPlayer) throws TeamPlayerHasNoTeamException
	{
		if (!teamPlayer.hasTeam())
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

	public static void checkPlayerIsTeamAdmin(ITeamPlayer player) throws TeamPlayerNotAdminException
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

	public static void checkPlayerLeaderLeaving(ITeamPlayer p) throws TeamPlayerLeaderLeavingException
	{
		if (p.hasTeam() && p.isLeader() && p.getTeam().size() > 1)
		{
			throw new TeamPlayerLeaderLeavingException();
		}
	}

	public static void checkTeamAlreadyExists(String desiredName) throws TeamAlreadyExistsException
	{
		if (xTeam.getTeamManager().contains(desiredName))
		{
			throw new TeamAlreadyExistsException();
		}
	}

	public static void checkTeamNameAlphaNumeric(String desiredName) throws TeamNameNotAlphaException
	{
		if (Data.ALPHA_NUM && !desiredName.matches(ALPHA_NUMERIC))
		{
			throw new TeamNameNotAlphaException();
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

	public static void checkTeamNameAlreadyUsed(String desiredName, Team team) throws TeamNameConflictsWithNameException
	{
		if (!desiredName.equalsIgnoreCase(team.getName()) && xTeam.getTeamManager().contains(desiredName))
		{
			throw new TeamNameConflictsWithNameException();
		}
	}

	public static void checkTeamHasHeadquarters(Team team) throws TeamNoHeadquartersException
	{
		if (!team.hasHeadquarters())
		{
			throw new TeamNoHeadquartersException();
		}
	}

	public static void checkPlayerHasCommands(HelpPages pages) throws TeamPlayerPermissionException
	{
		if (pages.getTotalPages() == 0)
		{
			throw new TeamPlayerPermissionException();
		}
	}

	public static void checkPlayerCommandPageRange(HelpPages pages, int pageNum) throws TeamInvalidPageException
	{
		if (pageNum > pages.getTotalPages())
		{
			throw new TeamInvalidPageException();
		}
	}

	public static void checkPlayerInviteSelf(TeamPlayer teamPlayer, String otherPlayer) throws TeamPlayerInviteException
	{
		if (teamPlayer.getName().equalsIgnoreCase(otherPlayer))
		{
			throw new TeamPlayerInviteException("Player cannot invite self");
		}
	}

	public static void checkPlayerHasInvite(ITeamPlayer teamPlayer) throws TeamPlayerHasInviteException
	{
		if (InviteHandler.hasInvite(teamPlayer.getName()))
		{
			throw new TeamPlayerHasInviteException();
		}
	}

	public static void checkPlayerIsTeammate(ITeamPlayer teamPlayer, ITeamPlayer other) throws TeamPlayerNotTeammateException
	{
		if (!teamPlayer.isOnSameTeam(other))
		{
			throw new TeamPlayerNotTeammateException();
		}
	}

	public static void checkPlayerNotDamaged(TeamPlayer teamPlayer) throws TeamPlayerDyingException
	{
		if (teamPlayer.isDamaged())
		{
			throw new TeamPlayerDyingException();
		}
	}

	public static void checkTeamHeadquartersRecentlySet(Team team) throws TeamHqSetRecentlyException
	{
		long timeElapsedSinceLastSetHeadquarters = CommonUtil.getElapsedTimeSince(team.getTimeLastSet());
		if (timeElapsedSinceLastSetHeadquarters < Data.HQ_INTERVAL * 60 * 60 * 1000)
		{
			throw new TeamHqSetRecentlyException();
		}
	}

	public static void checkPlayerIsTeamLeader(TeamPlayer teamPlayer) throws TeamPlayerNotLeaderException
	{
		if (!teamPlayer.isLeader())
		{
			throw new TeamPlayerNotLeaderException();
		}
	}

	public static void checkTeamNameTooLong(String desiredName) throws TeamNameTooLongException
	{
		if (Data.TEAM_TAG_LENGTH != 0 && desiredName.length() > Data.TEAM_TAG_LENGTH)
		{
			throw new TeamNameTooLongException();
		}
	}

	public static void checkPlayerDoesNotHaveTeam(TeamPlayer teamPlayer) throws TeamPlayerHasTeamException
	{
		if (teamPlayer.hasTeam())
		{
			throw new TeamPlayerHasTeamException();
		}
	}

	public static void checkPlayerDoesNotHaveInvite(TeamPlayer teamPlayer) throws TeamPlayerHasNoInviteException
	{
		if (!InviteHandler.hasInvite(teamPlayer.getName()))
		{
			throw new TeamPlayerHasNoInviteException();
		}
	}

	public static void checkPlayerDoesNotHaveInviteFromTeam(TeamPlayer teamPlayer, Team desiredTeam) throws TeamPlayerHasNoInviteException
	{
		if (!InviteHandler.hasInvite(teamPlayer.getName()) || !InviteHandler.getInviteTeam(teamPlayer.getName()).equals(desiredTeam))
		{
			if (!desiredTeam.isOpenJoining())
				throw new TeamPlayerHasNoInviteException();
		}
	}

	public static void checkTeamOnlyJoinDefault(String desiredName) throws TeamOnlyJoinDefaultException
	{
		if (Data.DEFAULT_TEAM_ONLY && !StringUtil.toLowerCase(Data.DEFAULT_TEAM_NAMES).contains(desiredName.toLowerCase()) && Data.DEFAULT_TEAM_NAMES.size() > 0)
		{
			throw new TeamOnlyJoinDefaultException();
		}
	}

	public static void checkPlayerLastCreatedTeam(TeamPlayer teamPlayer) throws TeamCreatedRecentlyException
	{
		if (System.currentTimeMillis() - (Data.lastCreated.get(teamPlayer.getName()) == null ? 0L : Data.lastCreated.get(teamPlayer.getName()).longValue()) < Data.CREATE_INTERVAL * 60 * 1000)
		{
			throw new TeamCreatedRecentlyException();
		}
	}

	public static void checkPlayerCanTeleport(TeamPlayer teamPlayer, String permissionNode) throws TeamPlayerTeleException
	{
		checkPlayerLastTeleported(teamPlayer, permissionNode);
		checkPlayerLastAttacked(teamPlayer);
	}

	public static void checkPlayerLastTeleported(TeamPlayer teamPlayer, String permissionNode) throws TeamPlayerTeleException
	{
		long timeSinceLastTeleport = CommonUtil.getElapsedTimeSince(teamPlayer.getLastTeleported());
		if (timeSinceLastTeleport < Data.TELE_REFRESH_DELAY)
		{
			String error = "Player cannot teleport within " + Data.TELE_REFRESH_DELAY + " seconds of last teleport\nPlayer must wait " + (Data.TELE_REFRESH_DELAY - timeSinceLastTeleport) + " more seconds";
			if (teamPlayer.hasReturnLocation() && (teamPlayer.hasPermission(permissionNode) || Data.NO_PERMISSIONS))
				error += "\n'/team return' is still available";
			throw new TeamPlayerTeleException(error);
		}
	}

	public static void checkPlayerLastAttacked(TeamPlayer teamPlayer) throws TeamPlayerTeleException
	{
		long timeSinceLastAttacked = CommonUtil.getElapsedTimeSince(teamPlayer.getLastAttacked());
		if (timeSinceLastAttacked < Data.LAST_ATTACKED_DELAY)
		{
			throw new TeamPlayerTeleException("Player was attacked in the last " + Data.LAST_ATTACKED_DELAY + " seconds\nYou must wait " + (Data.LAST_ATTACKED_DELAY - timeSinceLastAttacked) + " more seconds");
		}
	}

	public static void checkPlayerTeleportRequested(TeamPlayer teamPlayer) throws TeamPlayerTeleRequestException
	{
		if (TeleportScheduler.getInstance().hasCurrentTask(teamPlayer))
		{
			throw new TeamPlayerTeleRequestException();
		}
	}

	public static void checkPlayerHasReturnLocation(TeamPlayer teamPlayer) throws TeamPlayerHasNoReturnException
	{
		Location loc = teamPlayer.getReturnLocation();
		if (loc == null)
		{
			throw new TeamPlayerHasNoReturnException();
		}
	}

	public static void checkPlayerTeleportSelf(TeamPlayer teamPlayer, String teammateName) throws TeamPlayerTeleException
	{
		if (teamPlayer.getName().equals(teammateName))
		{
			throw new TeamPlayerTeleException("Player cannot teleport to themselves");
		}
	}

	public static void checkPlayerHasTeammatesOnline(TeamPlayer teamPlayer) throws TeamPlayerTeammateException
	{
		if (teamPlayer.getOnlineTeammates().isEmpty())
		{
			throw new TeamPlayerTeammateException("Player has no teammates online");
		}
	}

	public static void checkPlayerTeammateWorld(TeamPlayer teamPlayer, TeamPlayer teamMate) throws TeamPlayerTeammateException
	{
		if (!teamPlayer.getLocation().getWorld().equals(teamMate.getLocation().getWorld()) && Data.TELE_RADIUS > 0)
		{
			throw new TeamPlayerTeammateException("Teammate is in a different world");
		}
	}

	public static void checkPlayerTeammateNear(TeamPlayer teamPlayer, TeamPlayer teamMate) throws TeamPlayerTeammateException
	{
		if (teamPlayer.getLocation().distance(teamMate.getLocation()) > Data.TELE_RADIUS && Data.TELE_RADIUS > 0)
		{
			throw new TeamPlayerTeammateException("There are no teammates near you\nClosest teammate: " + teamMate.getName() + " @ " + (int) Math.ceil(teamPlayer.getLocation().distance(teamMate.getLocation())) + " blocks away");
		}
	}

	public static void checkPlayerTeammateIsOnline(TeamPlayer teamMate) throws TeamPlayerTeammateException
	{
		if (!teamMate.isOnline())
		{
			throw new TeamPlayerTeammateException("Player teammate is not online");
		}
	}

	public static void checkPlayerTeammateTooFar(TeamPlayer teamPlayer, TeamPlayer teamMate) throws TeamPlayerTeleException
	{
		if (teamPlayer.getLocation().distance(teamMate.getLocation()) > Data.TELE_RADIUS && Data.TELE_RADIUS > 0)
		{
			throw new TeamPlayerTeleException(teamMate.getName() + " is too far away @ " + (int) Math.ceil(teamPlayer.getLocation().distance(teamMate.getLocation())) + " blocks away");
		}
	}
}
