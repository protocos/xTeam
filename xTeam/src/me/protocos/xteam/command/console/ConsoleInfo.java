package me.protocos.xteam.command.console;

import static me.protocos.xteam.util.StringUtil.*;
import java.util.List;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.core.ITeamPlayer;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.OfflineTeamPlayer;
import me.protocos.xteam.core.PlayerManager;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.core.exception.TeamDoesNotExistException;
import me.protocos.xteam.core.exception.TeamException;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class ConsoleInfo extends ConsoleCommand
{
	private String other;
	ConsoleCommandSender sender;

	public ConsoleInfo()
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
			otherTeamInfo(otherTeam);
	}
	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		super.checkRequirements(originalSender, parseCommand);
		sender = (ConsoleCommandSender) originalSender;
		other = parseCommand.get(1);
		if (isTeam(other))
		{
			//do nothing
		}
		else if (isPlayer(other))
		{
			ITeamPlayer p = PlayerManager.getPlayer(other);
			Requirements.checkPlayerHasTeam(p);
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
	private String getLastOnline(OfflineTeamPlayer player)
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
		return "/team info [Player/Team]";
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
		sender.sendMessage("Team Name - " + otherTeam.getName());
		if (!otherTeam.getTag().equals(otherTeam.getName()))
			sender.sendMessage("Team Tag - " + otherTeam.getTag());
		if (otherTeam.hasLeader())
			sender.sendMessage("Team Leader - " + otherTeam.getLeader());
		if (otherTeam.getAdmins().size() > 1)
			sender.sendMessage("Team Admins - " + otherTeam.getAdmins().toString().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(otherTeam.getLeader() + ", ", ""));
		sender.sendMessage("Team Joining - " + (otherTeam.isOpenJoining() == false ? "Closed" : "Open"));
		if (otherTeam.hasHeadquarters())
			sender.sendMessage("Team Headquarters - " + "X:" + Math.round(otherTeam.getHeadquarters().getX()) + " Y:" + Math.round(otherTeam.getHeadquarters().getY()) + " Z:" + Math.round(otherTeam.getHeadquarters().getZ()));
		else
			sender.sendMessage("Team Headquarters - " + "none set");
		teammateStatus(otherTeam);
	}
	private void teammateStatus(Team team)
	{
		List<TeamPlayer> onlineMates = team.getOnlineTeammates();
		if (onlineMates.size() > 0)
		{
			if (onlineMates.size() > 0)
				sender.sendMessage("Teammates online:");
			for (TeamPlayer player : onlineMates)
			{
				sender.sendMessage("    " + getCurrentUserData(player));
			}
		}
		List<OfflineTeamPlayer> offlineMates = team.getOfflineTeammates();
		if (offlineMates.size() > 0)
		{
			if (offlineMates.size() > 0)
				sender.sendMessage("Teammates offline:");
			for (OfflineTeamPlayer player : offlineMates)
			{
				sender.sendMessage("    " + getLastOnline(player));
			}
		}
	}
}
