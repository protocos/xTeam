package me.protocos.xteam.command.serveradmin;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.core.exception.*;
import me.protocos.xteam.util.PermissionUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class AdminPromote extends ServerAdminCommand
{
	private String teamName, playerName;

	public AdminPromote(Player sender, String command)
	{
		super(sender, command);
	}
	@Override
	protected void act()
	{
		Team team = xTeam.tm.getTeam(teamName);
		team.promote(playerName);
		if (!team.containsPlayer(sender.getName()))
			sender.sendMessage("You " + ChatColor.GREEN + "promoted" + ChatColor.RESET + " " + playerName);
		TeamPlayer other = new TeamPlayer(playerName);
		other.sendMessage("You've been " + ChatColor.GREEN + "promoted" + ChatColor.RESET + " by an admin");
	}
	@Override
	public void checkRequirements() throws TeamException
	{
		if (sender == null)
		{
			throw new TeamPlayerDoesNotExistException();
		}
		if (parseCommand.size() == 3)
		{
			teamName = parseCommand.get(1);
			playerName = parseCommand.get(2);
		}
		else
		{
			throw new TeamInvalidCommandException();
		}
		if (!PermissionUtil.hasPermission(originalSender, getPermissionNode()))
		{
			throw new TeamPlayerPermissionException();
		}
		Team desiredTeam = xTeam.tm.getTeam(teamName);
		TeamPlayer playerPromote = new TeamPlayer(playerName);
		Team team = playerPromote.getTeam();
		if (!playerPromote.hasPlayedBefore())
		{
			throw new TeamPlayerNeverPlayedException();
		}
		if (desiredTeam == null)
		{
			throw new TeamDoesNotExistException();
		}
		if (team == null)
		{
			throw new TeamPlayerHasNoTeamException();
		}
		if (!desiredTeam.equals(team))
		{
			throw new TeamPlayerNotOnTeamException();
		}
	}
	@Override
	public String getPattern()
	{
		return patternOneOrMore("promote") + WHITE_SPACE + ANY_CHARS + WHITE_SPACE + ANY_CHARS + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getPermissionNode()
	{
		return "xteam.serveradmin.core.promote";
	}
	@Override
	public String getUsage()
	{
		return baseCommand + " promote [Team] [Player]";
	}
}
