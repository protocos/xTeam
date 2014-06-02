package me.protocos.xteam.command.teamadmin;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.command.TeamAdminCommand;
import me.protocos.xteam.entity.ITeamPlayer;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.MessageUtil;
import me.protocos.xteam.util.PatternBuilder;

public class TeamAdminPromote extends TeamAdminCommand
{
	private String otherPlayer;

	public TeamAdminPromote(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		team.promote(otherPlayer);
		ITeamPlayer other = playerFactory.getPlayer(otherPlayer);
		if (other.isOnline())
			other.sendMessage("You've been " + MessageUtil.positiveMessage("promoted"));
		teamPlayer.sendMessage("You" + MessageUtil.positiveMessage(" promoted ") + otherPlayer);
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		otherPlayer = commandContainer.getArgument(1);
		ITeamPlayer other = playerFactory.getPlayer(otherPlayer);
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
		return "xteam.core.admin.promote";
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
