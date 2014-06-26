package me.protocos.xteam.command.serveradmin;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.message.Message;
import me.protocos.xteam.model.Locatable;
import me.protocos.xteam.util.PatternBuilder;

public class ServerAdminTpAll extends ServerAdminCommand
{
	private String teamName;

	public ServerAdminTpAll(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		ITeam changeTeam = teamCoordinator.getTeam(teamName);
		changeTeam.teleportTo(new Locatable(teamPlugin, serverAdmin.getName(), serverAdmin.getLocation()));
		new Message.Builder("You have been teleported to " + serverAdmin.getName()).addRecipients(changeTeam).excludeRecipients(serverAdmin).send(log);
		new Message.Builder("Players have been teleported").addRecipients(serverAdmin).send(log);
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
