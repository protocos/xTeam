package me.protocos.xteam.command.serveradmin;

import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.ServerAdminCommand;
import me.protocos.xteam.api.model.ITeam;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import me.protocos.xteam.util.PatternBuilder;

public class ServerAdminRename extends ServerAdminCommand
{
	private String teamName, desiredName;

	public ServerAdminRename()
	{
		super();
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		ITeam changeTeam = xTeam.getInstance().getTeamManager().getTeam(teamName);
		xTeam.getInstance().getTeamManager().renameTeam(changeTeam, desiredName);
		if (!changeTeam.containsPlayer(player.getName()))
			player.sendMessage("You " + ChatColorUtil.positiveMessage("renamed") + " the team to " + desiredName);
		changeTeam.sendMessage("The team has been " + ChatColorUtil.positiveMessage("renamed") + " to " + desiredName + " by an admin");
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		teamName = commandContainer.getArgument(1);
		desiredName = commandContainer.getArgument(2);
		Requirements.checkTeamExists(teamName);
		Requirements.checkTeamAlreadyExists(desiredName);
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
