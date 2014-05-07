package me.protocos.xteam.command.serveradmin;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.MessageUtil;
import me.protocos.xteam.util.PatternBuilder;

public class ServerAdminRename extends ServerAdminCommand
{
	private String teamName, desiredName;

	public ServerAdminRename(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		ITeam changeTeam = teamManager.getTeam(teamName);
		teamManager.renameTeam(changeTeam, desiredName);
		if (!changeTeam.containsPlayer(player.getName()))
			player.sendMessage("You " + MessageUtil.positiveMessage("renamed") + " the team to " + desiredName);
		changeTeam.sendMessage("The team has been " + MessageUtil.positiveMessage("renamed") + " to " + desiredName + " by an admin");
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		teamName = commandContainer.getArgument(1);
		desiredName = commandContainer.getArgument(2);
		Requirements.checkTeamExists(teamManager, teamName);
		Requirements.checkTeamAlreadyExists(teamManager, desiredName);
		Requirements.checkTeamNameAlphaNumeric(desiredName);
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.oneOrMore("re")
				.oneOrMore("name")
				.whiteSpace()
				.anyString()
				.whiteSpace()
				.anyString()
				.whiteSpaceOptional()
				.toString();
	}

	@Override
	public String getPermissionNode()
	{
		return "xteam.core.serveradmin.rename";
	}

	@Override
	public String getUsage()
	{
		return "/team rename [Team] [Name]";
	}

	@Override
	public String getDescription()
	{
		return "rename a team";
	}
}
