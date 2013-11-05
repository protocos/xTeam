package me.protocos.xteam.command.teamleader;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.api.core.ITeamPlayer;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.UserCommand;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.PlayerManager;
import me.protocos.xteam.core.exception.*;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UserDemote extends UserCommand
{
	private String otherPlayer;

	public UserDemote()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		team.demote(otherPlayer);
		Player other = Data.BUKKIT.getPlayer(otherPlayer);
		if (other != null)
			other.sendMessage("You've been " + ChatColor.RED + "demoted");
		originalSender.sendMessage("You" + ChatColor.RED + " demoted " + ChatColor.RESET + otherPlayer);
	}
	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		super.checkRequirements(originalSender, parseCommand);
		otherPlayer = parseCommand.get(1);
		ITeamPlayer other = PlayerManager.getPlayer(otherPlayer);
		ITeamPlayer demotePlayer = PlayerManager.getPlayer(otherPlayer);
		Requirements.checkPlayerHasTeam(teamPlayer);
		Requirements.checkPlayerIsTeamLeader(teamPlayer);
		Requirements.checkPlayerIsTeammate(teamPlayer, other);
		Requirements.checkPlayerLeaderDemote(demotePlayer);
	}
	@Override
	public String getPattern()
	{
		return patternOneOrMore("demote") + WHITE_SPACE + ANY_CHARS + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getPermissionNode()
	{
		return "xteam.leader.core.demote";
	}
	@Override
	public String getUsage()
	{
		return "/team demote [Player]";
	}
}
