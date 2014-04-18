package me.protocos.xteam.command;

import me.protocos.xteam.XTeam;
import me.protocos.xteam.command.action.InviteHandler;
import me.protocos.xteam.command.action.TeleportScheduler;
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

public class Requirements
{
	private Requirements()
	{
	}

	public static void checkPlayerHasPermission(CommandSender sender, IPermissible permission) throws TeamPlayerPermissionException
	{
		if (!PermissionUtil.hasPermission(sender, permission))
		{
			throw new TeamPlayerPermissionException();
		}
	}

	public static void checkPlayerCommandIsValid(String command, String pattern) throws TeamInvalidCommandException
	{
		if (!new PatternBuilder(pattern).ignoreCase().matches(command))
		{
			throw new TeamInvalidCommandException();
		}
	}

	public static void checkTeamExists(String teamName) throws TeamDoesNotExistException
	{
		if (!XTeam.getInstance().getTeamManager().containsTeam(teamName))
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

	public static void checkTeamAlreadyExists(String desiredName) throws TeamAlreadyExistsException
	{
		if (XTeam.getInstance().getTeamManager().containsTeam(desiredName))
		{
			throw new TeamAlreadyExistsException();
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

	public static void checkTeamPlayerMax(String teamName) throws TeamPlayerMaxException
	{
		if (XTeam.getInstance().getTeamManager().containsTeam(teamName) && XTeam.getInstance().getTeamManager().getTeam(teamName).size() >= Configuration.MAX_PLAYERS && Configuration.MAX_PLAYERS > 0)
		{
			throw new TeamPlayerMaxException();
		}
	}

	public static void checkTeamNameAlreadyUsed(String desiredName, ITeam team) throws TeamNameConflictsWithNameException
	{
		if (!desiredName.equalsIgnoreCase(team.getName()) && XTeam.getInstance().getTeamManager().containsTeam(desiredName))
		{
			throw new TeamNameConflictsWithNameException();
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
		if (pageNum > pages.getTotalPages())
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

	public static void checkPlayerHasInvite(ITeamPlayer teamPlayer) throws TeamPlayerHasInviteException
	{
		if (InviteHandler.getInstance().hasInvite(teamPlayer.getName()))
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

	public static void checkPlayerDoesNotHaveInvite(TeamPlayer teamPlayer) throws TeamPlayerHasNoInviteException
	{
		if (!InviteHandler.getInstance().hasInvite(teamPlayer.getName()))
		{
			throw new TeamPlayerHasNoInviteException();
		}
	}

	public static void checkPlayerDoesNotHaveInviteFromTeam(TeamPlayer teamPlayer, ITeam desiredTeam) throws TeamPlayerHasNoInviteException
	{
		if (!InviteHandler.getInstance().hasInvite(teamPlayer.getName()) || !InviteHandler.getInstance().getInviteTeam(teamPlayer.getName()).equals(desiredTeam))
		{
			if (!desiredTeam.isOpenJoining())
				throw new TeamPlayerHasNoInviteException();
		}
	}

	public static void checkTeamOnlyJoinDefault(String desiredName) throws TeamOnlyJoinDefaultException
	{
		if (Configuration.DEFAULT_TEAM_ONLY && !CommonUtil.toLowerCase(Configuration.DEFAULT_TEAM_NAMES).contains(desiredName.toLowerCase()) && Configuration.DEFAULT_TEAM_NAMES.size() > 0)
		{
			throw new TeamOnlyJoinDefaultException();
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
		if (!teamPlayer.getLocation().getWorld().equals(teamMate.getLocation().getWorld()) && Configuration.TELE_RADIUS > 0)
		{
			throw new TeamPlayerTeammateException("Teammate is in a different world");
		}
	}

	public static void checkPlayerTeammateNear(TeamPlayer teamPlayer, TeamPlayer teamMate) throws TeamPlayerTeammateException
	{
		if (teamPlayer.getLocation().distance(teamMate.getLocation()) > Configuration.TELE_RADIUS && Configuration.TELE_RADIUS > 0)
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
		if (teamPlayer.getLocation().distance(teamMate.getLocation()) > Configuration.TELE_RADIUS && Configuration.TELE_RADIUS > 0)
		{
			throw new TeamPlayerTeleException(teamMate.getName() + " is too far away @ " + (int) Math.ceil(teamPlayer.getLocation().distance(teamMate.getLocation())) + " blocks away");
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

	public static void checkPlayerCanRally(TeamPlayer player) throws TeamPlayerAlreadyUsedRallyException
	{
		if (!TeleportScheduler.getInstance().canRally(player))
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

	public static void checkPlayerWorldDisabled(TeamPlayer teamPlayer) throws TeamPlayerDisabledWorldException
	{
		if (Configuration.DISABLED_WORLDS.contains(teamPlayer.getWorld().getName()))
		{
			throw new TeamPlayerDisabledWorldException();
		}
	}
}
