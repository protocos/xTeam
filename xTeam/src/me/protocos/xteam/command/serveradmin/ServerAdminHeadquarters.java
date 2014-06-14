package me.protocos.xteam.command.serveradmin;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.message.Message;
import me.protocos.xteam.message.MessageUtil;
import me.protocos.xteam.util.PatternBuilder;

public class ServerAdminHeadquarters extends ServerAdminCommand
{
	private String teamName;
	private ITeam changeTeam;

	public ServerAdminHeadquarters(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		serverAdmin.teleport(changeTeam.getHeadquarters().getLocation());
		new Message.Builder("You have been " + MessageUtil.green("teleported") + " to the headquarters of team " + changeTeam.getName()).addRecipients(serverAdmin).send(log);
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		teamName = commandContainer.getArgument(1);
		changeTeam = teamCoordinator.getTeam(teamName);
		Requirements.checkTeamExists(teamCoordinator, teamName);
		Requirements.checkTeamHasHeadquarters(changeTeam);
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
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
		return "xteam.core.serveradmin.hq";
	}

	@Override
	public String getUsage()
	{
		return "/team hq [Team]";
	}

	@Override
	public String getDescription()
	{
		return "teleport to team headquarters for team";
	}
}
