package me.protocos.xteam.command.serveradmin;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.core.ITeamPlayer;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.PlayerManager;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.exception.TeamException;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class AdminRemove extends ServerAdminCommand
{
	private String teamName, playerName;
	private ITeamPlayer changePlayer;

	public AdminRemove()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		Team changeTeam = changePlayer.getTeam();
		changeTeam.removePlayer(playerName);
		if (!playerName.equals(originalSender.getName()))
			originalSender.sendMessage("You " + ChatColor.RED + "removed" + ChatColor.RESET + " " + playerName + " from " + teamName);
		changePlayer.sendMessage("You have been " + ChatColor.RED + "removed" + ChatColor.RESET + " from " + changeTeam.getName() + " by an admin");
		if (changeTeam.isEmpty())
		{
			originalSender.sendMessage(teamName + " has been " + ChatColor.RED + "disbanded");
			xTeam.getTeamManager().removeTeam(changeTeam.getName());
		}
	}
	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		super.checkRequirements(originalSender, parseCommand);
		teamName = parseCommand.get(1);
		playerName = parseCommand.get(2);
		changePlayer = PlayerManager.getPlayer(playerName);
		Requirements.checkPlayerHasPlayedBefore(changePlayer);
		Requirements.checkPlayerHasTeam(changePlayer);
		Requirements.checkPlayerLeaderLeaving(changePlayer);
	}
	@Override
	public String getPattern()
	{
		return patternOneOrMore("re") + patternOneOrMore("move") + WHITE_SPACE + ANY_CHARS + WHITE_SPACE + ANY_CHARS + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getPermissionNode()
	{
		return "xteam.serveradmin.core.remove";
	}
	@Override
	public String getUsage()
	{
		return "/team remove [Team] [Player]";
	}
}
