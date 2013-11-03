package me.protocos.xteam.command.console;

import static me.protocos.xteam.util.StringUtil.*;
import java.io.InvalidClassException;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.exception.TeamException;
import org.bukkit.command.CommandSender;

public class ConsoleTag extends ConsoleCommand
{
	private String teamName, desiredTag;

	public ConsoleTag()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		Team team = xTeam.getTeamManager().getTeam(teamName);
		team.setTag(desiredTag);
		originalSender.sendMessage("The team tag has been set to " + desiredTag);
		team.sendMessage("The team tag has been set to " + desiredTag + " by an admin");
	}
	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, InvalidClassException
	{
		super.checkRequirements(originalSender, parseCommand);
		teamName = parseCommand.get(1);
		desiredTag = parseCommand.get(2);
		Team team = xTeam.getTeamManager().getTeam(teamName);
		Requirements.checkTeamExists(teamName);
		Requirements.checkTeamNameAgainstTags(desiredTag, team);
		Requirements.checkTeamAlphaNumericName(desiredTag);
	}
	@Override
	public String getPattern()
	{
		return patternOneOrMore("tag") + WHITE_SPACE + ANY_CHARS + WHITE_SPACE + ANY_CHARS + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getUsage()
	{
		return "/team tag [Team] [Tag]";
	}
}
