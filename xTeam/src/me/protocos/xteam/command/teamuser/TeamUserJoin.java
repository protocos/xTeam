package me.protocos.xteam.command.teamuser;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.TeamUserCommand;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.InviteHandler;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import org.bukkit.command.CommandSender;

public class TeamUserJoin extends TeamUserCommand
{
	private String desiredName;

	public TeamUserJoin()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		Team foundTeam = xTeam.getInstance().getTeamManager().getTeam(desiredName);
		foundTeam.addPlayer(teamPlayer.getName());
		InviteHandler.removeInvite(teamPlayer.getName());
		teamPlayer.sendMessageToTeam(teamPlayer.getName() + " " + ChatColorUtil.positiveMessage("joined") + " your team");
		originalSender.sendMessage("You " + ChatColorUtil.positiveMessage("joined") + " " + foundTeam.getName());
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		desiredName = parseCommand.get(1);
		Team desiredTeam = xTeam.getInstance().getTeamManager().getTeam(desiredName);
		Requirements.checkPlayerDoesNotHaveTeam(teamPlayer);
		Requirements.checkTeamOnlyJoinDefault(desiredName);
		Requirements.checkTeamExists(desiredName);
		Requirements.checkPlayerDoesNotHaveInviteFromTeam(teamPlayer, desiredTeam);
		Requirements.checkTeamPlayerMax(desiredName);
	}

	@Override
	public String getPattern()
	{
		return patternOneOrMore("join") + WHITE_SPACE + ANY_CHARS + OPTIONAL_WHITE_SPACE;
	}

	@Override
	public String getPermissionNode()
	{
		return "xteam.player.core.join";
	}

	@Override
	public String getUsage()
	{
		return "/team join [Team]";
	}

	@Override
	public String getDescription()
	{
		return "join a team";
	}
}
