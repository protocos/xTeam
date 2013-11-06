package me.protocos.xteam.command.teamadmin;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.api.core.ITeamPlayer;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.UserCommand;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.InviteHandler;
import me.protocos.xteam.core.PlayerManager;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import org.bukkit.command.CommandSender;

public class UserInvite extends UserCommand
{
	private String otherPlayer;

	public UserInvite()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		InviteHandler.addInvite(otherPlayer, team);
		ITeamPlayer other = PlayerManager.getPlayer(otherPlayer);
		if (other.isOnline())
			other.sendMessage("You've been " + ChatColorUtil.positiveMessage("invited ") + "to join " + team.getName());
		originalSender.sendMessage("You " + ChatColorUtil.positiveMessage("invited ") + other.getName());
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		super.checkRequirements(originalSender, parseCommand);
		otherPlayer = parseCommand.get(1);
		ITeamPlayer other = PlayerManager.getPlayer(otherPlayer);
		Requirements.checkPlayerHasTeam(teamPlayer);
		Requirements.checkPlayerIsTeamAdmin(teamPlayer);
		Requirements.checkPlayerInviteSelf(teamPlayer, otherPlayer);
		Requirements.checkPlayerHasPlayedBefore(other);
		Requirements.checkPlayerHasInvite(other);
	}

	@Override
	public String getPattern()
	{
		return "in" + patternOneOrMore("vite") + WHITE_SPACE + ANY_CHARS + OPTIONAL_WHITE_SPACE;
	}

	@Override
	public String getPermissionNode()
	{
		return "xteam.admin.core.invite";
	}

	@Override
	public String getUsage()
	{
		return "/team invite [Player]";
	}
}
