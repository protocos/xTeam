package me.protocos.xteam.command.console;

import static me.protocos.xteam.util.StringUtil.*;
import java.util.List;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.core.exception.TeamDoesNotExistException;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.core.exception.TeamInvalidCommandException;
import me.protocos.xteam.core.exception.TeamPlayerHasNoTeamException;
import org.bukkit.command.ConsoleCommandSender;

public class ConsoleInfo extends ConsoleCommand
{
	private String other;

	public ConsoleInfo(ConsoleCommandSender sender, String command)
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
			otherTeamInfo(otherTeam);
	}
	@Override
	public void checkRequirements() throws TeamException
	{
		if (parseCommand.size() == 2)
		{
			other = parseCommand.get(1);
		}
		else
		{
			throw new TeamInvalidCommandException();
		}
		if (isTeam(other))
		{
			//do nothing
		}
		else if (isPlayer(other))
		{
			TeamPlayer p = new TeamPlayer(other);
			Team t = p.getTeam();
			if (t == null)
			{
				throw new TeamPlayerHasNoTeamException();
			}
		}
		else
		{
			throw new TeamDoesNotExistException();
		}
	}
	private String getCurrentUserData(TeamPlayer player)
	{
		int health = (int) player.getHealth();
		return player.getName() + " Health: " + health * 5 + "% Location: " + player.getRelativeX() + " " + player.getRelativeY() + " " + player.getRelativeZ() + " in \"" + player.getWorld().getName() + "\"";
	}
	private String getLastOnline(TeamPlayer player)
	{
		return player.getName() + " was last online on " + player.getLastPlayed();
	}
	@Override
	public String getPattern()
	{
		return patternOneOrMore("info") + WHITE_SPACE + ANY_CHARS + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getUsage()
	{
		return baseCommand + " info [Player/Team]";
	}
	private boolean isTeam(String teamName)
	{
		return xTeam.tm.contains(teamName);
	}
	private boolean isPlayer(String playerName)
	{
		TeamPlayer p = new TeamPlayer(playerName);
		return p.hasPlayedBefore();
	}
	private void otherTeamInfo(Team otherTeam)
	{
		originalSender.sendMessage("Team Name - " + otherTeam.getName());
		if (!otherTeam.getTag().equals(otherTeam.getName()))
			originalSender.sendMessage("Team UserTag - " + otherTeam.getTag());
		if (otherTeam.hasLeader())
			originalSender.sendMessage("Team Leader - " + otherTeam.getLeader());
		if (otherTeam.getAdmins().size() > 1)
			originalSender.sendMessage("Team Admins - " + otherTeam.getAdmins().toString().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(otherTeam.getLeader() + ", ", ""));
		originalSender.sendMessage("Team Joining - " + (otherTeam.isOpenJoining() == false ? "Closed" : "UserOpen"));
		if (otherTeam.hasHQ())
			originalSender.sendMessage("Team UserHeadquarters - " + "X:" + Math.round(otherTeam.getHeadquarters().getX()) + " Y:" + Math.round(otherTeam.getHeadquarters().getY()) + " Z:" + Math.round(otherTeam.getHeadquarters().getZ()));
		else
			originalSender.sendMessage("Team UserHeadquarters - " + "none set");
		teammateStatus(otherTeam);
	}
	private void teammateStatus(Team team)
	{
		List<String> teammates = team.getPlayers();
		if (team.getOnlinePlayers().size() > 0)
			originalSender.sendMessage("Teammates online:");
		for (String s : teammates)
		{
			TeamPlayer p = new TeamPlayer(s);
			if (p.isOnline())
				originalSender.sendMessage("    " + getCurrentUserData(p));
		}
		if (teammates.size() > team.getOnlinePlayers().size())
			originalSender.sendMessage("Teammates offline:");
		for (String s : teammates)
		{
			TeamPlayer p = new TeamPlayer(s);
			if (!p.isOnline())
				originalSender.sendMessage("    " + getLastOnline(p));
		}
	}
}
