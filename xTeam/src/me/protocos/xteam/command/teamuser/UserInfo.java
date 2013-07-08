package me.protocos.xteam.command.teamuser;

import static me.protocos.xteam.util.StringUtil.*;
import java.util.List;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.UserCommand;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.core.exception.*;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class UserInfo extends UserCommand
{
	private String other;

	public UserInfo(Player sender, CommandParser command)
	{
		super(sender, command);
	}
	public UserInfo()
	{
	}
	@Override
	protected void act()
	{
		Team otherTeam = null;
		if (isTeam(other))
		{
			otherTeam = xTeam.tm.getTeam(other);
		}
		else if (isPlayer(other))
		{
			TeamPlayer p = new TeamPlayer(other);
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
	public void checkRequirements() throws TeamException
	{
		if (teamPlayer == null)
		{
			throw new TeamPlayerDoesNotExistException();
		}
		if (parseCommand.size() == 1)
		{
			other = teamPlayer.getName();
		}
		else if (parseCommand.size() == 2)
		{
			other = parseCommand.get(1);
		}
		else
		{
			throw new TeamInvalidCommandException();
		}
		if (isTeam(other))
		{
			// do nothing
		}
		else if (isPlayer(other))
		{
			TeamPlayer p = new TeamPlayer(other);
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
	private String getLastOnline(TeamPlayer p)
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
		return parseCommand.getBaseCommand() + " info {Team/Player}";
	}
	private boolean isPlayer(String playerName)
	{
		TeamPlayer p = new TeamPlayer(playerName);
		return p.hasPlayedBefore();
	}
	private boolean isTeam(String teamName)
	{
		return xTeam.tm.contains(teamName);
	}
	private void otherTeamInfo(Team otherTeam)
	{
		String message = (ChatColor.RESET + "Team Name - " + ChatColor.GREEN + otherTeam.getName());
		if (!otherTeam.getTag().equals(otherTeam.getName()))
			message += "\n" + (ChatColor.RESET + "Team UserTag - " + ChatColor.GREEN + otherTeam.getTag());
		if (otherTeam.hasLeader())
			message += "\n" + (ChatColor.RESET + "Team Leader - " + ChatColor.GREEN + otherTeam.getLeader());
		message += "\n" + (ChatColor.RESET + "Team Joining - " + (otherTeam.isOpenJoining() == false ? ChatColor.RED + "Closed" : ChatColor.GREEN + "UserOpen"));
		if (otherTeam.hasHQ())
			message += "\n" + (ChatColor.RESET + "Team UserHeadquarters - " + ChatColor.GREEN + "Set");
		else
			message += "\n" + (ChatColor.RESET + "Team UserHeadquarters - " + ChatColor.RED + "None set");
		message += teammateStatus(otherTeam);
		originalSender.sendMessage(message);
	}
	private void teamInfo()
	{
		String message = (ChatColor.RESET + "Team Name - " + ChatColor.GREEN + team.getName());
		if (!team.getTag().equals(team.getName()))
			message += "\n" + (ChatColor.RESET + "Team UserTag - " + ChatColor.GREEN + team.getTag());
		if (team.hasLeader())
			message += "\n" + (ChatColor.RESET + "Team Leader - " + ChatColor.GREEN + team.getLeader());
		if (team.getAdmins().size() > 1)
			message += "\n" + (ChatColor.RESET + "Team Admins - " + ChatColor.GREEN + team.getAdmins().toString().replaceAll("\\[|\\]" + (team.hasLeader() ? "|" + team.getLeader() + ", " : ""), ""));
		message += "\n" + (ChatColor.RESET + "Team Joining - " + (team.isOpenJoining() == false ? ChatColor.RED + "Closed" : ChatColor.GREEN + "UserOpen"));
		if (team.hasHQ())
			message += "\n" + (ChatColor.RESET + "Team UserHeadquarters - " + ChatColor.GREEN + "X:" + Math.round(team.getHeadquarters().getX()) + " Y:" + Math.round(team.getHeadquarters().getY()) + " Z:" + Math.round(team.getHeadquarters().getZ()));
		else
			message += "\n" + (ChatColor.RESET + "Team UserHeadquarters - " + ChatColor.RED + "None set");
		message += teammateStatus(team);
		originalSender.sendMessage(message);
	}
	private String teammateStatus(Team t)
	{
		String message = "";
		List<String> teammates = t.getPlayers();
		if (t.getOnlinePlayers().size() > 0)
			message += "\n" + (ChatColor.RESET + "Teammates online:");
		for (String s : teammates)
		{
			TeamPlayer p = new TeamPlayer(s);
			if (p.isOnline())
				message += "\n" + (ChatColor.GREEN + "    " + s + getCurrentUserData(p));
		}
		if (teammates.size() > t.getOnlinePlayers().size())
			message += "\n" + (ChatColor.RESET + "Teammates offline:");
		for (String s : teammates)
		{
			TeamPlayer p = new TeamPlayer(s);
			if (!p.isOnline())
				message += "\n" + (ChatColor.RED + "    " + s + getLastOnline(p));
		}
		return message;
	}
}
