package me.protocos.xteam.command.teamleader;

import static me.protocos.xteam.util.StringUtil.*;
import java.io.InvalidClassException;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.UserCommand;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.core.exception.TeamPlayerHasNoTeamException;
import me.protocos.xteam.core.exception.TeamPlayerNotLeaderException;
import me.protocos.xteam.core.exception.TeamPlayerNotTeammateException;
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
		TeamPlayer other = new TeamPlayer(otherPlayer);
		if (other.isOnline())
			other.sendMessage(ChatColor.GREEN + "You" + ChatColor.RESET + " are now the team leader");
		teamPlayer.sendMessage(ChatColor.GREEN + otherPlayer + ChatColor.RESET + " is now the team leader (you are an admin)" +
				"\nYou can now leave the team");
	}
	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, InvalidClassException
	{
		super.checkRequirements(originalSender, parseCommand);
		otherPlayer = parseCommand.get(1);
		if (!teamPlayer.hasTeam())
		{
			throw new TeamPlayerHasNoTeamException();
		}
		if (!teamPlayer.isLeader())
		{
			throw new TeamPlayerNotLeaderException();
		}
		if (!team.getPlayers().contains(otherPlayer))
		{
			throw new TeamPlayerNotTeammateException();
		}
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
