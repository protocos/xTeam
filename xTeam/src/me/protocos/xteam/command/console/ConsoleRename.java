package me.protocos.xteam.command.console;

import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.ConsoleCommand;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import me.protocos.xteam.util.PatternBuilder;
import org.bukkit.command.CommandSender;

public class ConsoleRename extends ConsoleCommand
{
	private String teamName, desiredName;

	public ConsoleRename()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		Team team = xTeam.getInstance().getTeamManager().getTeam(teamName);
		xTeam.getInstance().getTeamManager().removeTeam(teamName);
		team.setName(desiredName);
		xTeam.getInstance().getTeamManager().addTeam(team);
		originalSender.sendMessage("You " + ChatColorUtil.positiveMessage("renamed") + " the team to " + desiredName);
		team.sendMessage("The team has been " + ChatColorUtil.positiveMessage("renamed") + " to " + desiredName);
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
