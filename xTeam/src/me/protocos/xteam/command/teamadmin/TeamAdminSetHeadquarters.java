package me.protocos.xteam.command.teamadmin;

import me.protocos.xteam.api.command.TeamAdminCommand;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.Headquarters;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import me.protocos.xteam.util.PatternBuilder;

public class TeamAdminSetHeadquarters extends TeamAdminCommand
{
	public TeamAdminSetHeadquarters()
	{
		super();
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		team.setHQ(new Headquarters(teamPlayer.getLocation()));
		team.setTimeLastSet(System.currentTimeMillis());
		teamPlayer.sendMessage("You " + ChatColorUtil.positiveMessage("set") + " the team headquarters");
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		Requirements.checkPlayerNotDamaged(teamPlayer);
		Requirements.checkTeamHeadquartersRecentlySet(team);
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.oneOrMore("set")
				.oneOrMore("head")
				.oneOrMore("quarters")
				.whiteSpaceOptional()
				.toString();
	}

	@Override
	public String getPermissionNode()
	{
		return "xteam.core.admin.sethq";
	}

	@Override
	public String getUsage()
	{
		return "/team sethq";
	}

	@Override
	public String getDescription()
	{
		return "set headquarters of team";
	}
}
