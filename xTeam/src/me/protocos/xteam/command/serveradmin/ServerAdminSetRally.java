package me.protocos.xteam.command.serveradmin;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.message.MessageUtil;
import me.protocos.xteam.util.PatternBuilder;

public class ServerAdminSetRally extends ServerAdminCommand
{
	private String teamName;

	public ServerAdminSetRally(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		ITeam changeTeam = teamCoordinator.getTeam(teamName);
		changeTeam.setRally(player.getLocation());
		player.sendMessage("You " + MessageUtil.green("set") + " the rally point for team " + changeTeam.getName());
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
				.oneOrMore("rally")
				.whiteSpace()
				.anyString()
				.whiteSpaceOptional()
				.toString();
	}

	@Override
	public String getPermissionNode()
	{
		return "xteam.core.serveradmin.setrally";
	}

	@Override
	public String getUsage()
	{
		return "/team setrally [Team]";
	}

	@Override
	public String getDescription()
	{
		return "set team rally point for team";
	}
}
