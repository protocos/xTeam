package me.protocos.xteam.command.teamadmin;

import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.TeamAdminCommand;
import me.protocos.xteam.api.core.ITeamPlayer;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.InviteHandler;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import me.protocos.xteam.util.PatternBuilder;
import org.bukkit.command.CommandSender;

public class TeamAdminInvite extends TeamAdminCommand
{
	private String otherPlayer;

	public TeamAdminInvite()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		InviteHandler.addInvite(otherPlayer, team);
		ITeamPlayer other = xTeam.getInstance().getPlayerManager().getPlayer(otherPlayer);
		if (other.isOnline())
			other.sendMessage("You've been " + ChatColorUtil.positiveMessage("invited ") + "to join " + team.getName());
		originalSender.sendMessage("You " + ChatColorUtil.positiveMessage("invited ") + other.getName());
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		otherPlayer = parseCommand.get(1);
		ITeamPlayer other = xTeam.getInstance().getPlayerManager().getPlayer(otherPlayer);
		Requirements.checkPlayerHasTeam(teamPlayer);
		Requirements.checkPlayerInviteSelf(teamPlayer, otherPlayer);
		Requirements.checkPlayerHasPlayedBefore(other);
		Requirements.checkPlayerHasInvite(other);
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.append("in")
				.oneOrMore("vite")
				.whiteSpace()
				.anyString()
				.whiteSpaceOptional()
				.toString();
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

	@Override
	public String getDescription()
	{
		return "invite player to your team";
	}
}
