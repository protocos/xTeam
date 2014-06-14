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

public class ServerAdminDisband extends ServerAdminCommand
{
	private String teamName;
	private ITeam changeTeam;

	public ServerAdminDisband(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		new Message.Builder("Your team has been " + MessageUtil.red("disbanded") + " by an admin").addRecipients(changeTeam).send(log);
		teamCoordinator.disbandTeam(teamName);
		new Message.Builder("You " + MessageUtil.red("disbanded") + " " + changeTeam.getName() + (changeTeam.hasTag() ? " [" + changeTeam.getTag() + "]" : "")).addRecipients(serverAdmin).send(log);
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		teamName = commandContainer.getArgument(1);
		changeTeam = teamCoordinator.getTeam(teamName);
		Requirements.checkTeamExists(teamCoordinator, teamName);
		Requirements.checkTeamIsDefault(changeTeam);
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.oneOrMore("dis")
				.oneOrMore("band")
				.whiteSpace()
				.anyString()
				.whiteSpaceOptional()
				.toString();
	}

	@Override
	public String getPermissionNode()
	{
		return "xteam.core.serveradmin.disband";
	}

	@Override
	public String getUsage()
	{
		return "/team disband [Team]";
	}

	@Override
	public String getDescription()
	{
		return "disband a team";
	}
}
