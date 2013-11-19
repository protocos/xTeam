package me.protocos.xteam.command.serveradmin;

import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.ServerAdminCommand;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import me.protocos.xteam.util.PatternBuilder;
import org.bukkit.command.CommandSender;

public class ServerAdminRename extends ServerAdminCommand
{
	private String teamName, desiredName;

	public ServerAdminRename()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		Team changeTeam = xTeam.getInstance().getTeamManager().getTeam(teamName);
		xTeam.getInstance().getTeamManager().removeTeam(teamName);
		changeTeam.setName(desiredName);
		xTeam.getInstance().getTeamManager().addTeam(changeTeam);
		if (!changeTeam.containsPlayer(originalSender.getName()))
			originalSender.sendMessage("You " + ChatColorUtil.positiveMessage("renamed") + " the team to " + desiredName);
		changeTeam.sendMessage("The team has been " + ChatColorUtil.positiveMessage("renamed") + " to " + desiredName + " by an admin");
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		teamName = parseCommand.get(1);
		desiredName = parseCommand.get(2);
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
		return "xteam.serveradmin.core.rename";
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
