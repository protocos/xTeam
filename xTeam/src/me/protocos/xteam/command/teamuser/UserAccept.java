package me.protocos.xteam.command.teamuser;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.UserCommand;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.InviteHandler;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import org.bukkit.command.CommandSender;

public class UserAccept extends UserCommand
{
	public UserAccept()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		Team inviteTeam = InviteHandler.getInviteTeam(teamPlayer.getName());
		inviteTeam.addPlayer(teamPlayer.getName());
		InviteHandler.removeInvite(teamPlayer.getName());
		teamPlayer.sendMessageToTeam(teamPlayer.getName() + " " + ChatColorUtil.positiveMessage("joined") + " your team");
		originalSender.sendMessage("You " + ChatColorUtil.positiveMessage("joined") + " " + inviteTeam.getName());
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		super.checkRequirements(originalSender, parseCommand);
		Requirements.checkPlayerDoesNotHaveTeam(teamPlayer);
		Requirements.checkPlayerDoesNotHaveInvite(teamPlayer);
		Team inviteTeam = InviteHandler.getInviteTeam(teamPlayer.getName());
		Requirements.checkTeamPlayerMax(inviteTeam.getName());
	}

	@Override
	public String getPattern()
	{
		return patternOneOrMore("accept") + OPTIONAL_WHITE_SPACE;
	}

	@Override
	public String getPermissionNode()
	{
		return "xteam.player.core.accept";
	}

	@Override
	public String getUsage()
	{
		return "/team accept";
	}
}
