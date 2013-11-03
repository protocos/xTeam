package me.protocos.xteam.command.teamuser;

import static me.protocos.xteam.util.StringUtil.*;
import java.io.InvalidClassException;
import java.util.List;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.core.ITeamPlayer;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.UserCommand;
import me.protocos.xteam.core.*;
import me.protocos.xteam.core.exception.TeamDoesNotExistException;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.core.exception.TeamPlayerHasNoTeamException;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UserInfo extends UserCommand
{
	private String other;
	Player sender;

	public UserInfo()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		Team otherTeam = null;
		if (isTeam(other))
		{
			otherTeam = xTeam.getTeamManager().getTeam(other);
		}
		else if (isPlayer(other))
		{
			ITeamPlayer p = PlayerManager.getPlayer(other);
			otherTeam = p.getTeam();
		}
		if (otherTeam != null)
		{
			if (otherTeam.containsPlayer(teamPlayer.getName()))
				teamInfo();
			else
				otherTeamInfo(otherTeam);
		}
	}
	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, InvalidClassException
	{
		super.checkRequirements(originalSender, parseCommand);
		sender = (Player) originalSender;
		if (parseCommand.size() == 1)
		{
			other = teamPlayer.getName();
		}
		else if (parseCommand.size() == 2)
		{
			other = parseCommand.get(1);
		}
		if (isTeam(other))
		{
			// do nothing
		}
		else if (isPlayer(other))
		{
			ITeamPlayer p = PlayerManager.getPlayer(other);
			if (!p.hasTeam())
			{
				throw new TeamPlayerHasNoTeamException();
			}
		}
		else
		{
			throw new TeamDoesNotExistException();
		}
	}
	private String getCurrentUserData(TeamPlayer p)
	{
		String location = "";
		int health = (int) p.getHealth();
		if (Data.DISPLAY_COORDINATES)
			location += " " + ChatColor.RESET + "Location: " + ChatColor.RED + p.getRelativeX() + " " + ChatColor.GREEN + p.getRelativeY() + " " + ChatColor.BLUE + p.getRelativeZ() + ChatColor.RESET + " in \"" + p.getWorld().getName() + "\"";
		if (p.isOnSameTeam(teamPlayer))
			return ChatColor.RESET + " Health: " + (health >= 15 ? ChatColor.GREEN : ChatColor.RED) + health * 5 + "%" + location;
		return "";
	}
	private String getLastOnline(ITeamPlayer p)
	{
		if (p.isOnSameTeam(teamPlayer))
			return ChatColor.RESET + " was last online on " + p.getLastPlayed();
		return "";
	}
	@Override
	public String getPattern()
	{
		return patternOneOrMore("info") + "(" + WHITE_SPACE + ANY_CHARS + ")?" + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getPermissionNode()
	{
		return null;
	}
	@Override
	public String getUsage()
	{
		return "/team info {Team/Player}";
	}
	private boolean isPlayer(String playerName)
	{
		ITeamPlayer p = PlayerManager.getPlayer(playerName);
		return p.hasPlayedBefore();
	}
	private boolean isTeam(String teamName)
	{
		return xTeam.getTeamManager().contains(teamName);
	}
	private void otherTeamInfo(Team otherTeam)
	{
		String message = (ChatColor.RESET + "Team Name - " + ChatColor.GREEN + otherTeam.getName());
		if (!otherTeam.getTag().equals(otherTeam.getName()))
			message += "\n" + (ChatColor.RESET + "Team Tag - " + ChatColor.GREEN + otherTeam.getTag());
		if (otherTeam.hasLeader())
			message += "\n" + (ChatColor.RESET + "Team Leader - " + ChatColor.GREEN + otherTeam.getLeader());
		message += "\n" + (ChatColor.RESET + "Team Joining - " + (otherTeam.isOpenJoining() == false ? ChatColor.RED + "Closed" : ChatColor.GREEN + "Open"));
		if (otherTeam.hasHeadquarters())
			message += "\n" + (ChatColor.RESET + "Team Headquarters - " + ChatColor.GREEN + "Set");
		else
			message += "\n" + (ChatColor.RESET + "Team Headquarters - " + ChatColor.RED + "None set");
		message += teammateStatus(otherTeam);
		sender.sendMessage(message);
	}
	private void teamInfo()
	{
		String message = (ChatColor.RESET + "Team Name - " + ChatColor.GREEN + team.getName());
		if (!team.getTag().equals(team.getName()))
			message += "\n" + (ChatColor.RESET + "Team Tag - " + ChatColor.GREEN + team.getTag());
		if (team.hasLeader())
			message += "\n" + (ChatColor.RESET + "Team Leader - " + ChatColor.GREEN + team.getLeader());
		if (team.getAdmins().size() > 1)
			message += "\n" + (ChatColor.RESET + "Team Admins - " + ChatColor.GREEN + team.getAdmins().toString().replaceAll("\\[|\\]" + (team.hasLeader() ? "|" + team.getLeader() + ", " : ""), ""));
		message += "\n" + (ChatColor.RESET + "Team Joining - " + (team.isOpenJoining() == false ? ChatColor.RED + "Closed" : ChatColor.GREEN + "Open"));
		if (team.hasHeadquarters())
			message += "\n" + (ChatColor.RESET + "Team Headquarters - " + ChatColor.GREEN + "X:" + Math.round(team.getHeadquarters().getX()) + " Y:" + Math.round(team.getHeadquarters().getY()) + " Z:" + Math.round(team.getHeadquarters().getZ()));
		else
			message += "\n" + (ChatColor.RESET + "Team Headquarters - " + ChatColor.RED + "None set");
		message += teammateStatus(team);
		sender.sendMessage(message);
	}
	private String teammateStatus(Team t)
	{
		String message = "";
		List<TeamPlayer> onlineTeammates = t.getOnlineTeammates();
		if (onlineTeammates.size() > 0)
		{
			message += "\n" + (ChatColor.RESET + "Teammates online:");
			for (TeamPlayer p : onlineTeammates)
			{
				message += "\n" + (ChatColor.GREEN + "    " + p.getName() + getCurrentUserData(p));
			}
		}
		List<OfflineTeamPlayer> offlineTeammates = t.getOfflineTeammates();
		if (offlineTeammates.size() > 0)
		{
			message += "\n" + (ChatColor.RESET + "Teammates offline:");
			for (OfflineTeamPlayer p : offlineTeammates)
			{
				message += "\n" + (ChatColor.RED + "    " + p.getName() + getLastOnline(p));
			}
		}
		return message;
	}
}
