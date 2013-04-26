package me.protocos.xteam.command.teamuser;

import static me.protocos.xteam.util.StringUtil.*;
import java.util.List;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.BaseUserCommand;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.exception.*;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Info extends BaseUserCommand
{
	private String other;

	public Info()
	{
		super();
	}
	public Info(Player sender, String command)
	{
		super(sender, command);
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
			if (otherTeam.contains(teamPlayer.getName()))
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
		if (Data.DISPLAY_COORDINATES)
			location += " " + ChatColor.WHITE + "Location: " + ChatColor.RED + p.getRelativeX() + " " + ChatColor.GREEN + p.getRelativeY() + " " + ChatColor.BLUE + p.getRelativeZ() + ChatColor.WHITE + " in \"" + p.getWorld().getName() + "\"";
		if (p.isOnSameTeam(teamPlayer))
			return ChatColor.WHITE + " Health: " + (p.getHealth() >= 15 ? ChatColor.GREEN : ChatColor.RED) + p.getHealth() * 5 + "%" + location;
		return "";
	}
	private String getLastOnline(TeamPlayer p)
	{
		if (p.isOnSameTeam(teamPlayer))
			return ChatColor.WHITE + " was last online on " + p.getLastPlayed();
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
		return baseCommand + " info {ITeam/Player}";
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
		String message = (ChatColor.WHITE + "ITeam Name - " + ChatColor.GREEN + otherTeam.getName());
		if (!otherTeam.getTag().equals(otherTeam.getName()))
			message += "\n" + (ChatColor.WHITE + "ITeam Tag - " + ChatColor.GREEN + otherTeam.getTag());
		if (otherTeam.hasLeader())
			message += "\n" + (ChatColor.WHITE + "ITeam Leader - " + ChatColor.GREEN + otherTeam.getLeader());
		message += "\n" + (ChatColor.WHITE + "ITeam Joining - " + (otherTeam.isOpenJoining() == false ? ChatColor.RED + "Closed" : ChatColor.GREEN + "Open"));
		if (otherTeam.hasHQ())
			message += "\n" + (ChatColor.WHITE + "ITeam Headquarters - " + ChatColor.GREEN + "Set");
		else
			message += "\n" + (ChatColor.WHITE + "ITeam Headquarters - " + ChatColor.RED + "None set");
		message += teammateStatus(otherTeam);
		originalSender.sendMessage(message);
	}
	private void teamInfo()
	{
		String message = (ChatColor.WHITE + "ITeam Name - " + ChatColor.GREEN + team.getName());
		if (!team.getTag().equals(team.getName()))
			message += "\n" + (ChatColor.WHITE + "ITeam Tag - " + ChatColor.GREEN + team.getTag());
		message += "\n" + (ChatColor.WHITE + "ITeam Leader - " + ChatColor.GREEN + team.getLeader());
		if (team.getAdmins().size() > 1)
			message += "\n" + (ChatColor.WHITE + "ITeam Admins - " + ChatColor.GREEN + team.getAdmins().toString().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(team.getLeader() + ", ", ""));
		message += "\n" + (ChatColor.WHITE + "ITeam Joining - " + (team.isOpenJoining() == false ? ChatColor.RED + "Closed" : ChatColor.GREEN + "Open"));
		if (team.hasHQ())
			message += "\n" + (ChatColor.WHITE + "ITeam Headquarters - " + ChatColor.GREEN + "X:" + Math.round(team.getHeadquarters().getX()) + " Y:" + Math.round(team.getHeadquarters().getY()) + " Z:" + Math.round(team.getHeadquarters().getZ()));
		else
			message += "\n" + (ChatColor.WHITE + "ITeam Headquarters - " + ChatColor.RED + "None set");
		message += teammateStatus(team);
		originalSender.sendMessage(message);
	}
	private String teammateStatus(Team t)
	{
		String message = "";
		List<String> teammates = t.getPlayers();
		if (t.getOnlinePlayers().size() > 0)
			message += "\n" + (ChatColor.WHITE + "Teammates online:");
		for (String s : teammates)
		{
			TeamPlayer p = new TeamPlayer(s);
			if (p.isOnline())
				message += "\n" + (ChatColor.GREEN + "    " + s + getCurrentUserData(p));
		}
		if (teammates.size() > t.getOnlinePlayers().size())
			message += "\n" + (ChatColor.WHITE + "Teammates offline:");
		for (String s : teammates)
		{
			TeamPlayer p = new TeamPlayer(s);
			if (!p.isOnline())
				message += "\n" + (ChatColor.RED + "    " + s + getLastOnline(p));
		}
		return message;
	}
}
