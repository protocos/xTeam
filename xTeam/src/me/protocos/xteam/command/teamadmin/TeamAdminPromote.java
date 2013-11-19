package me.protocos.xteam.command.teamadmin;

import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.TeamAdminCommand;
import me.protocos.xteam.api.core.ITeamPlayer;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import me.protocos.xteam.util.PatternBuilder;
import org.bukkit.command.CommandSender;

public class TeamAdminPromote extends TeamAdminCommand
{
	private String otherPlayer;

	public TeamAdminPromote()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		team.promote(otherPlayer);
		ITeamPlayer other = xTeam.getInstance().getPlayerManager().getPlayer(otherPlayer);
		if (other.isOnline())
			other.sendMessage("You've been " + ChatColorUtil.positiveMessage("promoted"));
		originalSender.sendMessage("You" + ChatColorUtil.positiveMessage(" promoted ") + otherPlayer);
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		otherPlayer = parseCommand.get(1);
		ITeamPlayer other = xTeam.getInstance().getPlayerManager().getPlayer(otherPlayer);
		Requirements.checkPlayerIsTeammate(teamPlayer, other);
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.oneOrMore("promote")
				.whiteSpace()
				.anyString()
				.whiteSpaceOptional()
				.toString();
	}

	@Override
	public String getPermissionNode()
	{
		return "xteam.admin.core.promote";
	}

	@Override
	public String getUsage()
	{
		return "/team promote [Player]";
	}

	@Override
	public String getDescription()
	{
		return "promote player to team admin";
	}
}
