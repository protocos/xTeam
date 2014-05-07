package me.protocos.xteam.command.teamadmin;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.command.TeamAdminCommand;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.model.Headquarters;
import me.protocos.xteam.util.MessageUtil;
import me.protocos.xteam.util.PatternBuilder;

public class TeamAdminSetHeadquarters extends TeamAdminCommand
{
	public TeamAdminSetHeadquarters(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		team.setHeadquarters(new Headquarters(teamPlugin, teamPlayer.getLocation()));
		team.setTimeHeadquartersLastSet(System.currentTimeMillis());
		teamPlayer.sendMessage("You " + MessageUtil.positiveMessage("set") + " the team headquarters");
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
