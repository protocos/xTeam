package me.protocos.xteam.command.serveradmin;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.message.MessageUtil;
import me.protocos.xteam.model.Headquarters;
import me.protocos.xteam.util.PatternBuilder;

public class ServerAdminSetHeadquarters extends ServerAdminCommand
{
	private String teamName;

	public ServerAdminSetHeadquarters(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		ITeam changeTeam = teamCoordinator.getTeam(teamName);
		changeTeam.setHeadquarters(new Headquarters(teamPlugin, teamPlayer.getLocation()));
		player.sendMessage("You " + MessageUtil.green("set") + " the team headquarters for team " + teamName);
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		teamName = commandContainer.getArgument(1);
		Requirements.checkTeamExists(teamCoordinator, teamName);
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.oneOrMore("set")
				.oneOrMore("head")
				.oneOrMore("quarters")
				.whiteSpace()
				.anyString()
				.whiteSpaceOptional()
				.toString();
	}

	@Override
	public String getPermissionNode()
	{
		return "xteam.core.serveradmin.sethq";
	}

	@Override
	public String getUsage()
	{
		return "/team sethq [Team]";
	}

	@Override
	public String getDescription()
	{
		return "set team headquarters for team";
	}
}
