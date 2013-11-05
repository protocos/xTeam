package me.protocos.xteam.command.console;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.exception.TeamException;
import org.bukkit.ChatColor;
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
		Team team = xTeam.getTeamManager().getTeam(teamName);
		xTeam.getTeamManager().removeTeam(teamName);
		team.setName(desiredName);
		xTeam.getTeamManager().addTeam(team);
		originalSender.sendMessage("You renamed the team to " + desiredName);
		team.sendMessage("The team has been renamed to " + ChatColor.AQUA + desiredName);
	}
	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		super.checkRequirements(originalSender, parseCommand);
		teamName = parseCommand.get(1);
		desiredName = parseCommand.get(2);
		Requirements.checkTeamExists(teamName);
		Requirements.checkTeamAlreadyExists(desiredName);
		Requirements.checkTeamNameAlphaNumeric(desiredName);
	}
	@Override
	public String getPattern()
	{
		return "re" + patternOneOrMore("name") + WHITE_SPACE + ANY_CHARS + WHITE_SPACE + ANY_CHARS + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getUsage()
	{
		return "/team rename [Team] [Name]";
	}
}
