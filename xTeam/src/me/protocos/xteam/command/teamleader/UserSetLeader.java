package me.protocos.xteam.command.teamleader;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.api.core.ITeamPlayer;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.UserCommand;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.PlayerManager;
import me.protocos.xteam.core.exception.TeamException;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class UserSetLeader extends UserCommand
{
	private String otherPlayer;

	public UserSetLeader()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		team.setLeader(otherPlayer);
		ITeamPlayer other = PlayerManager.getPlayer(otherPlayer);
		if (other.isOnline())
			other.sendMessage(ChatColor.GREEN + "You" + ChatColor.RESET + " are now the team leader");
		teamPlayer.sendMessage(ChatColor.GREEN + otherPlayer + ChatColor.RESET + " is now the team leader (you are an admin)" +
				"\nYou can now leave the team");
	}
	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		super.checkRequirements(originalSender, parseCommand);
		otherPlayer = parseCommand.get(1);
		ITeamPlayer other = PlayerManager.getPlayer(otherPlayer);
		Requirements.checkPlayerHasTeam(teamPlayer);
		Requirements.checkPlayerIsTeamLeader(teamPlayer);
		Requirements.checkPlayerIsTeammate(teamPlayer, other);
	}
	@Override
	public String getPattern()
	{
		return "set" + patternOneOrMore("leader") + WHITE_SPACE + ANY_CHARS + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getPermissionNode()
	{
		return "xteam.leader.core.setleader";
	}
	@Override
	public String getUsage()
	{
		return "/team setleader [Player]";
	}
}
