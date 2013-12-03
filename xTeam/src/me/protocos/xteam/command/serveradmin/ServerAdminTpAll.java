package me.protocos.xteam.command.serveradmin;

import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.ServerAdminCommand;
import me.protocos.xteam.api.model.ITeam;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.entity.TeamPlayer;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.PatternBuilder;

public class ServerAdminTpAll extends ServerAdminCommand
{
	private String teamName;

	public ServerAdminTpAll()
	{
		super();
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		ITeam changeTeam = xTeam.getInstance().getTeamManager().getTeam(teamName);
		for (TeamPlayer teammate : changeTeam.getOnlineTeammates())
		{
			if (teammate.isOnline())
			{
				teammate.teleport(teamPlayer.getLocation());
				teammate.sendMessage("You have been teleported to " + player.getName());
			}
		}
		player.sendMessage("Players teleported");
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
				.oneOrMore("tele")
				.oneOrMore("port")
				.oneOrMore("all")
				.whiteSpace()
				.anyString()
				.whiteSpaceOptional()
				.toString();
	}

	@Override
	public String getPermissionNode()
	{
		return "xteam.core.serveradmin.tpall";
	}

	@Override
	public String getUsage()
	{
		return "/team tpall [Team]";
	}

	@Override
	public String getDescription()
	{
		return "teleports a team to yourself";
	}
}
