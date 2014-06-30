package me.protocos.xteam.command;

import me.protocos.xteam.core.ITeamCoordinator;
import me.protocos.xteam.core.InviteHandler;
import me.protocos.xteam.core.TeleportScheduler;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.entity.ITeamPlayer;
import me.protocos.xteam.entity.TeamPlayer;
import me.protocos.xteam.exception.*;
import me.protocos.xteam.model.HelpPages;
import me.protocos.xteam.util.CommonUtil;
import me.protocos.xteam.util.PatternBuilder;
import me.protocos.xteam.util.PermissionUtil;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Requirements
{
	public static void checkPlayerHasPermission(CommandSender sender, IPermissible permission) throws TeamPlayerPermissionException
	{
		if (!PermissionUtil.hasPermission(sender, permission))
		{
			throw new TeamPlayerPermissionException();
		}
	}

	public static void checkCommandIsValid(String command, String pattern) throws TeamInvalidCommandException
	{
		if (!new PatternBuilder(pattern).ignoreCase().matches(command))
		{
			throw new TeamInvalidCommandException();
		}
	}

	public static void checkTeamExists(ITeamCoordinator teamCoordinator, String teamName) throws TeamDoesNotExistException
	{
		if (!teamCoordinator.containsTeam(teamName))
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
		if (!(player.isAdmin() || player.isLeader()))
		{
			throw new TeamPlayerNotAdminException();
		}
	}

	public static void checkPlayerOnTeam(ITeamPlayer player, ITeam team) throws TeamPlayerNotOnTeamException
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

	public static void checkTeamIsDefault(ITeam team) throws TeamIsDefaultException
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

	public static void checkTeamNameInUse(ITeamCoordinator teamCoordinator, String desiredName) throws TeamNameAlreadyInUseException
	{
		if (teamCoordinator.containsTeam(desiredName))
		{
			throw new TeamNameAlreadyInUseException();
		}
	}

	public static void checkTeamRenameInUse(ITeamCoordinator teamCoordinator, ITeam team, String desiredName) throws TeamNameAlreadyInUseException
	{
		if (teamCoordinator.containsTeam(desiredName) && !desiredName.equalsIgnoreCase(team.getName()) && !desiredName.equalsIgnoreCase(team.getTag()))
		{
			throw new TeamNameAlreadyInUseException();
		}
	}

	public static void checkTeamNameAlphaNumeric(String desiredName) throws TeamNameNotAlphaException
	{
		if (Configuration.ALPHA_NUM && !desiredName.matches(new PatternBuilder().alphaNumeric().toString()))
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

	public static void checkTeamPlayerMax(ITeamCoordinator teamCoordinator, String teamName) throws TeamPlayerMaxException
	{
		if (teamCoordinator.containsTeam(teamName) && teamCoordinator.getTeam(teamName).size() >= Configuration.MAX_PLAYERS && Configuration.MAX_PLAYERS > 0)
		{
			throw new TeamPlayerMaxException();
		}
	}

	public static void checkTeamHasHeadquarters(ITeam changeTeam) throws TeamNoHeadquartersException
	{
		if (!changeTeam.hasHeadquarters())
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
		if (pageNum < 1 || pageNum > pages.getTotalPages())
		{
			throw new TeamInvalidPageException();
		}
	}

	public static void checkPlayerInviteSelf(TeamPlayer teamPlayer, ITeamPlayer otherPlayer) throws TeamPlayerInviteException
	{
		if (teamPlayer.getName().equalsIgnoreCase(otherPlayer.getName()))
		{
			throw new TeamPlayerInviteException("Player cannot invite self");
		}
	}

	public static void checkPlayerHasInvite(InviteHandler inviteHandler, ITeamPlayer teamPlayer) throws TeamPlayerHasInviteException
	{
		if (inviteHandler.hasInvite(teamPlayer.getName()))
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

	public static void checkTeamHeadquartersRecentlySet(ITeam team) throws TeamHqSetRecentlyException
	{
		long timeElapsedSinceLastSetHeadquarters = CommonUtil.getElapsedTimeSince(team.getTimeHeadquartersLastSet());
		if (timeElapsedSinceLastSetHeadquarters < Configuration.HQ_INTERVAL * 60 * 60 * 1000)
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
		if (Configuration.TEAM_NAME_LENGTH != 0 && desiredName.length() > Configuration.TEAM_NAME_LENGTH)
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

	public static void checkPlayerDoesNotHaveInvite(InviteHandler inviteHandler, TeamPlayer teamPlayer) throws TeamPlayerHasNoInviteException
	{
		if (!inviteHandler.hasInvite(teamPlayer.getName()))
		{
			throw new TeamPlayerHasNoInviteException();
		}
	}

	public static void checkPlayerDoesNotHaveInviteFromTeam(InviteHandler inviteHandler, TeamPlayer teamPlayer, ITeam desiredTeam) throws TeamPlayerHasNoInviteException
	{
		if (!inviteHandler.hasInvite(teamPlayer.getName()) || !inviteHandler.getInviteTeam(teamPlayer.getName()).equals(desiredTeam))
		{
			if (!desiredTeam.isOpenJoining())
				throw new TeamPlayerHasNoInviteException();
		}
	}

	public static void checkTeamOnlyJoinDefault(ITeamCoordinator teamCoordinator, String desiredName) throws TeamOnlyJoinDefaultException
	{
		if (Configuration.DEFAULT_TEAM_ONLY && !CommonUtil.toLowerCase(Configuration.DEFAULT_TEAM_NAMES).contains(desiredName.toLowerCase()) && Configuration.DEFAULT_TEAM_NAMES.size() > 0)
		{
			throw new TeamOnlyJoinDefaultException(teamCoordinator);
		}
	}

	public static void checkPlayerLastCreatedTeam(TeamPlayer teamPlayer) throws TeamCreatedRecentlyException
	{
		if (System.currentTimeMillis() - (Configuration.lastCreated.get(teamPlayer.getName()) == null ? 0L : Configuration.lastCreated.get(teamPlayer.getName()).longValue()) < Configuration.CREATE_INTERVAL * 60 * 1000)
		{
			throw new TeamCreatedRecentlyException();
		}
	}

	public static void checkPlayerCanTeleport(TeamPlayer teamPlayer) throws TeamPlayerTeleException, TeamPlayerHasNoTeamException, TeamPlayerDyingException
	{
		checkPlayerHasTeam(teamPlayer);
		checkPlayerNotDamaged(teamPlayer);
		checkPlayerLastTeleported(teamPlayer);
		checkPlayerLastAttacked(teamPlayer);
	}

	public static void checkPlayerLastTeleported(TeamPlayer teamPlayer) throws TeamPlayerTeleException
	{
		long timeSinceLastTeleport = CommonUtil.getElapsedTimeSince(teamPlayer.getLastTeleported());
		if (timeSinceLastTeleport < Configuration.TELE_REFRESH_DELAY)
		{
			String error = "Player cannot teleport within " + Configuration.TELE_REFRESH_DELAY + " seconds of last teleport\nPlayer must wait " + (Configuration.TELE_REFRESH_DELAY - timeSinceLastTeleport) + " more seconds";
			//			if (teamPlayer.hasReturnLocation() && (teamPlayer.hasPermission(new TeamUserReturn()) || Configuration.NO_PERMISSIONS))
			//				error += "\n/team return is still available";
			throw new TeamPlayerTeleException(error);
		}
	}

	public static void checkPlayerLastAttacked(TeamPlayer teamPlayer) throws TeamPlayerTeleException
	{
		long timeSinceLastAttacked = CommonUtil.getElapsedTimeSince(teamPlayer.getLastAttacked());
		if (timeSinceLastAttacked < Configuration.LAST_ATTACKED_DELAY)
		{
			throw new TeamPlayerTeleException("Player was attacked in the last " + Configuration.LAST_ATTACKED_DELAY + " seconds\nYou must wait " + (Configuration.LAST_ATTACKED_DELAY - timeSinceLastAttacked) + " more seconds");
		}
	}

	public static void checkPlayerTeleportRequested(TeleportScheduler teleportScheduler, TeamPlayer teamPlayer) throws TeamPlayerTeleRequestException
	{
		if (teleportScheduler.hasCurrentTask(teamPlayer))
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

	public static void checkPlayerTeammateIsOnline(ITeamPlayer teamMate) throws TeamPlayerTeammateException
	{
		if (!teamMate.isOnline())
		{
			throw new TeamPlayerTeammateException("Teammate is not online");
		}
	}

	public static void checkTeamNotHasRally(ITeam team) throws TeamAlreadyHasRallyException
	{
		if (team.hasRally())
		{
			throw new TeamAlreadyHasRallyException();
		}
	}

	public static void checkTeamHasRally(ITeam team) throws TeamDoesNotHaveRallyException
	{
		if (!team.hasRally())
		{
			throw new TeamDoesNotHaveRallyException();
		}
	}

	public static void checkPlayerCanRally(TeleportScheduler teleportScheduler, TeamPlayer player) throws TeamPlayerAlreadyUsedRallyException
	{
		if (!teleportScheduler.canRally(player))
		{
			throw new TeamPlayerAlreadyUsedRallyException();
		}
	}

	public static void checkPlayerIsOnline(ITeamPlayer other) throws TeamPlayerOfflineException
	{
		if (!other.isOnline())
		{
			throw new TeamPlayerOfflineException();
		}
	}

	public static void checkPlayerWorldDisabled(Player player) throws TeamPlayerDisabledWorldException
	{
		if (Configuration.DISABLED_WORLDS.contains(player.getWorld().getName()))
		{
			throw new TeamPlayerDisabledWorldException();
		}
	}
}
