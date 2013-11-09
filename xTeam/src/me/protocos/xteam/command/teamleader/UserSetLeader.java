package me.protocos.xteam.command.teamleader;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.core.ITeamPlayer;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.UserCommand;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
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
		ITeamPlayer other = xTeam.getPlayerManager().getPlayer(otherPlayer);
		if (other.isOnline())
			other.sendMessage("You are now the " + ChatColorUtil.positiveMessage("team leader"));
		teamPlayer.sendMessage(otherPlayer + " is now the " + ChatColorUtil.positiveMessage("team leader") + " (you are an admin)" +
				"\nYou can now " + ChatColorUtil.negativeMessage("leave") + " the team");
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		otherPlayer = parseCommand.get(1);
		ITeamPlayer other = xTeam.getPlayerManager().getPlayer(otherPlayer);
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
