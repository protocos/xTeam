package me.protocos.xteam.command.serveradmin;

import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.ServerAdminCommand;
import me.protocos.xteam.api.core.ITeam;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.Headquarters;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import me.protocos.xteam.util.PatternBuilder;

public class ServerAdminSetHeadquarters extends ServerAdminCommand
{
	private String teamName;

	public ServerAdminSetHeadquarters()
	{
		super();
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		ITeam changeTeam = xTeam.getInstance().getTeamManager().getTeam(teamName);
		changeTeam.setHeadquarters(new Headquarters(teamPlayer.getLocation()));
		player.sendMessage("You " + ChatColorUtil.positiveMessage("set") + " the team headquarters for team " + teamName);
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		teamName = commandContainer.getArgument(1);
		Requirements.checkTeamExists(teamName);
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
